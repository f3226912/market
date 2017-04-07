package cn.gdeng.market.lease.controller.workflow.core;

import java.io.InputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.snaker.engine.entity.Process;
import org.snaker.engine.helper.StreamHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.gdeng.market.dto.admin.SysOrgDTO;
import cn.gdeng.market.enums.Const;
import cn.gdeng.market.enums.MsgCons;
import cn.gdeng.market.exception.BizException;
import cn.gdeng.market.lease.bean.AjaxResult;
import cn.gdeng.market.lease.controller.base.BaseController;
import cn.gdeng.market.lease.util.OrgUserUtil;
import cn.gdeng.market.lease.util.SnakerHelper;
import cn.gdeng.market.service.admin.SysOrgService;
import cn.gdeng.market.service.workflow.bus.WfProcessService;
import cn.gdeng.market.service.workflow.bus.WfSequenceService;
import cn.gdeng.market.service.workflow.core.SnakerProcessService;

/**
 *  流程管理控制类
 * @author wjguo
 *
 * datetime:2016年10月12日 下午4:16:19
 */
@Controller
@RequestMapping("wf/process")
public class ProcessController extends BaseController{
	@Autowired
	private SnakerProcessService snakerProcessService;
	@Autowired
	private WfProcessService wfProcessService;
	@Autowired
	private WfSequenceService wfSequenceService;
	@Autowired
	private SysOrgService sysOrgService;
	
	/** 通过xml内容体部署流程
	 * @param processModel 流程模型
	 * @param processId 流程id，如果id不为空，则替换，反之新增流程。
	 * @param operator 操作人。如果是新增流程则为创建人，如果是更新流程则是修改者。
	 * @return
	 * @throws Exception 
	 */
	@RequestMapping(value = "deployByXmlBody", method=RequestMethod.POST)
	@ResponseBody
	public Object deployByXmlBody(String processModel, String processId, String operator) throws Exception {
		InputStream input = null;
		try {
			String xml = processModelConvertToXml(processModel);
			logger.info("流程部署，部署的xml为： \n" + xml);
			input = StreamHelper.getStreamFromString(xml);
			if(StringUtils.isNotEmpty(processId)) {
				snakerProcessService.redeploy(processId, input, operator);
			} else {
				snakerProcessService.deploy(input, operator);
			}
		} finally {
			if(input != null) {
				input.close();
			}
		}
		
		return new AjaxResult<>();
	}
	
	/**流程模型转换为xml格式
	 * @param processModel
	 * @return
	 */
	private String processModelConvertToXml(String processModel) {
		//添加上xml的文件头和转义特殊字符。
		String xml = "<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"no\"?>\n" + SnakerHelper.convertXmlEsc(processModel);
		return xml;
	}
	
	
	/**保存或更新流程
	 * @param process
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "saveOrUpdate", method=RequestMethod.POST)
	@ResponseBody
	public Object saveOrUpdate(Process process) throws Exception {
		logger.info("更新流程ID："+process.getId());
		if(!StringUtils.isNotEmpty(process.getId())) {
			doProcessSave(process);
		} else {
			doProcessUpdating(process);
		}
		
		return new AjaxResult<>();
	}
	
	/**执行流程的更新
	 * @param process
	 * @throws BizException 
	 */
	private void doProcessUpdating(Process process) throws BizException {
		String displayName = process.getDisplayName();
		if (!isUniqueForProcessDisplayName(displayName, process.getId())) {
			throw new BizException(MsgCons.C_31000, MsgCons.M_31000);
		}
		
		//补全参数
		Integer userId = super.getUserId();
		process.setModificator(userId.toString());
		//转换为xml格式
		process.setContent(processModelConvertToXml(process.getContent()));
				
		//更新流程对象
		try {
			snakerProcessService.updateProcessTemplate(process);
		} catch (Exception e) {
			throw new BizException(MsgCons.C_31003, MsgCons.M_31003);
		}
	}
	
	/**执行流程的保存
	 * @param process
	 * @throws BizException 
	 */
	private void doProcessSave(Process process) throws BizException {
		String displayName = process.getDisplayName();
		if (!isUniqueForProcessDisplayName(displayName, null)) {
			throw new BizException(MsgCons.C_31000, MsgCons.M_31000);
		}
		//补全参数
		Integer userId = super.getUserId();
		process.setCreator(userId.toString());
		//规定流程名称统一使用序列流水号
		String processName = wfSequenceService.generalProcessName();
		process.setName(processName);
		//转换为xml格式
		process.setContent(processModelConvertToXml(process.getContent()));
		
		//保存流程对象
		try {
			snakerProcessService.saveProcessTemplate(process);
		} catch (Exception e) {
			throw new BizException(MsgCons.C_31002, MsgCons.M_31002);
		}
	}
	
	/**是否唯一的流程名称
	 * @param displayName 流程名称
	 * @param excludeId 需要排除的流程id，如果此参数为空，则忽略此参数。
	 * @return true表示唯一，反之false。
	 * @throws BizException 
	 */
	private boolean isUniqueForProcessDisplayName(String displayName, String excludeId) throws BizException {
		Map<String, Object> params = new HashMap<String, Object>(2);
		params.put("displayName", displayName);
		params.put("excludeId", excludeId);
		int count = wfProcessService.isUniqueForProcessDisplayName(params);
		return count <= 0;
	}
	
	
	/**
	 * 获取集团下所有市场列表
	 * @param mv
	 * @return
	 * @throws BizException
	 */
	@RequestMapping("getMarketList")
	@ResponseBody
	public AjaxResult<List<SysOrgDTO>> getMarketList(HttpServletRequest getRequest) throws BizException {
		AjaxResult<List<SysOrgDTO>> result = new AjaxResult<>();	
		List<SysOrgDTO> orgs = sysOrgService.queryByCondition(null);
		List<SysOrgDTO> marketList = OrgUserUtil.getOrgsByType(orgs, getUserGroup().getId(), Const.ORG_TYPE_MARKET);
		result.setResult(marketList);
		return result;
	}
	
	/**删除流程
	 * @param process
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "deleteProcess", method=RequestMethod.POST)
	@ResponseBody
	public Object deleteProcess(Process process) throws Exception {
		Process processDto = snakerProcessService.getProcessById(process.getId());
		processDto.setState(Integer.valueOf(Const.WF_PROCESS_STATE_2));
		snakerProcessService.updateProcessTemplate(processDto);
		return new AjaxResult<>();
	}
	
}
