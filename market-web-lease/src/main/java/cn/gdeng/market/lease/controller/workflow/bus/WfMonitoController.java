package cn.gdeng.market.lease.controller.workflow.bus;

import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.alibaba.fastjson.JSON;

import cn.gdeng.market.dto.admin.SysOrgDTO;
import cn.gdeng.market.dto.admin.SysUserDTO;
import cn.gdeng.market.dto.bean.GuDengPage;
import cn.gdeng.market.dto.workflow.WfOrderDTO;
import cn.gdeng.market.dto.workflow.WfProcessDTO;
import cn.gdeng.market.dto.workflow.WfTaskActorDTO;
import cn.gdeng.market.enums.Const;
import cn.gdeng.market.enums.MsgCons;
import cn.gdeng.market.enums.WorkflowBusTypeEnum;
import cn.gdeng.market.exception.BizException;
import cn.gdeng.market.lease.bean.AjaxResult;
import cn.gdeng.market.lease.controller.admin.SysOrgController;
import cn.gdeng.market.lease.controller.base.BaseController;
import cn.gdeng.market.lease.util.OrgUserUtil;
import cn.gdeng.market.service.admin.SysOrgService;
import cn.gdeng.market.service.workflow.bus.WfOrderService;
import cn.gdeng.market.service.workflow.bus.WfProcessService;
import cn.gdeng.market.service.workflow.core.SnakerOrderService;
import cn.gdeng.market.util.DateUtil;
import jxl.Workbook;
import jxl.write.Label;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;

/**
 *	流程监控管理
 *	@author houxp
 **/
@Controller
@RequestMapping("wfMonito")
public class WfMonitoController extends BaseController{

	private Logger logger = LoggerFactory.getLogger(SysOrgController.class);
	
	@Autowired
	private WfOrderService wfOrderService;
	
	@Autowired
	private WfProcessService wfProcessService;
	
	@Autowired
	private SysOrgService sysOrgService;
	
	/**
	 * 调转到流程监控管理界面
	 * @param mv
	 * @return
	 * @throws BizException
	 */
	@RequestMapping("index")
	public ModelAndView index(ModelAndView mv) throws BizException {
		mv.setViewName("workflow/monitor/monitoIndex");
		return mv;
	}
	
	/**
	 * 获取流程模板业务类型列表
	 * @param model
	 * @return
	 */
	@RequestMapping("getWfBusTypeList")
	@ResponseBody
	public AjaxResult<List<WfProcessDTO>> getWfBusTypeList() throws Exception {
		AjaxResult<List<WfProcessDTO>> result = new AjaxResult<>();
		List<WfProcessDTO> list = new ArrayList<>();
		for(WorkflowBusTypeEnum workflowBusTypeEnum : WorkflowBusTypeEnum.values()){
			WfProcessDTO dto = new WfProcessDTO();
			dto.setBusType(workflowBusTypeEnum.getCode());
			dto.setBusTypeDesc(workflowBusTypeEnum.getDesc());
			list.add(dto);
		}
		result.setResult(list);
		return result;
	}
	
	/**
	 * 查询流程监控列表-分页
	 * @param model
	 * @return
	 */
	@RequestMapping("getList4Page")
	@ResponseBody
	public AjaxResult<GuDengPage<WfOrderDTO>> getList4Page(Model model,WfOrderDTO dto) throws Exception {
		logger.info("分页流程监控入参:"+JSON.toJSONString(dto));
		
		AjaxResult<GuDengPage<WfOrderDTO>> result = new AjaxResult<GuDengPage<WfOrderDTO>>();
		
		//获取分页信息
		GuDengPage<WfOrderDTO> pageInfo = super.getPageInfoByRequest();
		//查询条件
		pageInfo.getParaMap().put("orderNo", dto.getOrderNo());
		pageInfo.getParaMap().put("busType", dto.getBusType());
		List<String> processList = getCurMarketProcess();
		if(processList == null || processList.size() == 0){
			return result.setResult(pageInfo);
		}
		pageInfo.getParaMap().put("processIdList", processList);
		pageInfo = wfOrderService.queryByConditionPage(pageInfo);
		logger.info("分页流程监控结果:"+JSON.toJSONString(pageInfo));
		result.setResult(pageInfo);
		return result;
	}
	
	/**
	 * 调转到流程作废界面
	 * @param mv
	 * @return
	 * @throws BizException
	 */
	@RequestMapping("jumpToTerminate")
	public ModelAndView jumpToTerminate(ModelAndView mv) throws BizException {
		mv.setViewName("workflow/orderTerminate");
		return mv;
	}
	
	/**
	 * 流程作废
	 * @param model
	 * @return
	 */
	@RequestMapping("terminate")
	@ResponseBody
	public AjaxResult<String> terminate(Model model,WfOrderDTO dto,String remark) throws Exception {
		AjaxResult<String> result = new AjaxResult<>();
		logger.info("流程作废入参:"+JSON.toJSONString(dto));
		//备注(json格式存储，desc表示备注内容，operatorInfo表示备注人基本信息，operatorID操作人id)
		//获取operatorInfo表示备注人基本信息
		SysUserDTO userInfo = super.getSysUser();
		String operatorInfo = super.getUserBaseInfo();
		String operatorID = userInfo.getId().toString();
		Map<String, Object> map = new HashMap<>();
		map.put(SnakerOrderService.REMARK_DESC_VAR, remark);
		map.put(SnakerOrderService.REMARK_OPERATOR_INFO_VAR, operatorInfo);
		map.put(SnakerOrderService.REMARK_OPERATOR_ID_VAR, operatorID);
		String orderId = wfOrderService.terminate(String.valueOf(dto.getId()), map, userInfo);
		logger.info("流程作废结果:"+result.getMsg());
		result.setResult(orderId);
		return result;
	}
	
	/**
	 * 根据orderId获取task以及相对应的责任人
	 */
	/**
	 * 更改任务参与者
	 * @param map
	 * @param oldActor
	 * @param newActor
	 */
	@RequestMapping("getActorList")
	@ResponseBody
	public AjaxResult<List<WfTaskActorDTO>> getActorList(String orderId) throws Exception {
		AjaxResult<List<WfTaskActorDTO>> result = new AjaxResult<>();
		List<WfTaskActorDTO> taskActorList = wfOrderService.getActorsByOrderId(orderId);
		result.setResult(taskActorList);
		return result;
	}
	
	/**
	 * 更改任务参与者
	 * @param map
	 * @param oldActor
	 * @param newActor
	 */
	@RequestMapping("taskActorChanged")
	@ResponseBody
	public AjaxResult<int[]> taskActorChanged(String orderId,String val) throws Exception {
		logger.info("更改任务参与者入参:"+JSON.toJSONString(orderId));
		AjaxResult<int[]> result = new AjaxResult<>();
		List<Map<String, String>> list = new ArrayList<>();
		String[] valAry = val.split(",");
		for (int i = 0; i < valAry.length; i++) {
			Map<String, String> map = new HashMap<>();
			String[] valsAry = valAry[i].split("-");
			map.put("taskId", valsAry[0]);
			map.put("newActor", valsAry[1]);
			map.put("oldActor", valsAry[2]);
			list.add(map);
		}
		result.setResult(wfOrderService.taskActorChanged(list, super.getSysUser(),orderId));
		logger.info("更改任务参与者结果:"+result.getMsg());
		return result;
	}
	/**
	 * 数据导出之前检测数据合法性
	 * @param request
	 * @return
	 */
	@RequestMapping(value="checkExportParams", produces="application/json;charset=utf-8")
	@ResponseBody
	public AjaxResult<String> checkExportParams(HttpServletRequest request,WfOrderDTO dto){
		AjaxResult<String> re =new AjaxResult<String> ();
		try{
			// 设置查询参数
			Map<String, Object> map = getParametersMap(request);
			//查询条件
			map.put("orderNo", dto.getOrderNo());
			map.put("busType", dto.getBusType());
			List<String> processList = getCurMarketProcess();
			if(processList == null || processList.size() == 0){
				re.setCode(202);
				re.setMsg( "查询没有符合的结果 ,请修改其他查询条件...");
				return re;
			}
			map.put("processIdList", processList);
			int total = wfOrderService.countByCondition(map);
			if (total ==0){
				re.setCode(202);
				re.setMsg( "查询没有符合的结果 ,请修改其他查询条件...");
				return re;
			}
			if (total > EXPORT_MAX_SIZE){
				re.setCode(202);
				re.setMsg("查询结果集太大(>"+EXPORT_MAX_SIZE+"条), 请缩减日期范围, 或者修改其他查询条件...");
				return re;
			}
		}catch(Exception e){
			logger.warn(e.getMessage());
			re.setCode(202);
			re.setMsg("查询异常");
			return re;
		}
		return re;
	}
	/**
	 * 导出数据
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "exportData")
	public String exportData(HttpServletRequest request,WfOrderDTO dto) {
		Map<String, Object> map = getParametersMap(request);
		// 设置查询参数
		map.put("startRow", 0);
		map.put("endRow", EXPORT_PAGE_SIZE);
		WritableWorkbook wwb = null;
		OutputStream ouputStream = null;
		try {
			// 设置输出响应头信息，
			response.setContentType("application/vnd.ms-excel");
			String fileName = new String("流程实例".getBytes(), "ISO-8859-1")+DateUtil.toString(new Date(), "yyyy-MM-dd_HH:mm:ss");
			response.setHeader("Content-disposition", "attachment;filename=" + fileName + ".xls");
			ouputStream = response.getOutputStream();
			// 在输出流中创建一个新的写入工作簿
			wwb = Workbook.createWorkbook(ouputStream);
			if (wwb != null) {
				WritableSheet sheet = wwb.createSheet("流程实例", 0);// 创建一个工作页，第一个参数的页名，第二个参数表示该工作页在excel中处于哪一页
				// 第一个参数表示列，第二个参数表示行
				Label label00 = new Label(0, 0, "流程名称");// 填充第一行第一个单元格的内容
				Label label10 = new Label(1, 0, "业务对象");// 填充第一行第二个单元格的内容
				Label label20 = new Label(2, 0, "当前步骤");
				sheet.addCell(label00);// 将单元格加入表格
				sheet.addCell(label10);// 将单元格加入表格
				sheet.addCell(label20);// 将单元格加入表格
				/*** 循环添加数据到工作簿 ***/
				// 查询数据list
			    int	endRow=EXPORT_PAGE_SIZE;
			    //查询条件
				map.put("orderNo", dto.getOrderNo());
				map.put("busType", dto.getBusType());
				List<String> processList = getCurMarketProcess();
				map.put("processIdList", processList);
				int totalCount = wfOrderService.countByCondition(map);
			    int exportCount = totalCount/EXPORT_PAGE_SIZE;//总条数/每页显示的条数=总页数 
			    int mod = totalCount % EXPORT_PAGE_SIZE;
			    if(mod != 0){
			    	exportCount++; 	
			    } 
			    int	startRow=0;
			    map.put("startRow", startRow);
			    map.put("endRow", endRow);
			    GuDengPage<WfOrderDTO> pageInfo = new GuDengPage<>();
			    //查询条件
			    pageInfo.getParaMap().put("orderNo", dto.getOrderNo());
				pageInfo.getParaMap().put("busType", dto.getBusType());
			    pageInfo.getParaMap().put("processIdList", getCurGroupProcess());
			    pageInfo.setParaMap(map);
			    for(int i=1;i<=exportCount;i++){
				List<WfOrderDTO> list = (List<WfOrderDTO>) wfOrderService.queryByConditionPage(pageInfo).getRecordList();
				for (int j = 0; j < list.size(); j++) {
					WfOrderDTO wfOrderDTO= list.get(j);
					Label label0 = new Label(0,  j+startRow  + 1, String.valueOf(wfOrderDTO.getOrderNo()));
					Label label1 = new Label(1,  j+startRow  + 1, String.valueOf(wfOrderDTO.getWfTaskDTO().getDisplayName()));
					Label label2 = new Label(2,  j+startRow  + 1, String.valueOf(wfOrderDTO.getBusType()));
					sheet.addCell(label0);//将单元格加入表格
					sheet.addCell(label1);
					sheet.addCell(label2);
					}
				startRow=(endRow*i);
				map.put("startRow", startRow);
				}
				wwb.write();// 将数据写入工作簿
			}
			wwb.close();// 关闭				
		} catch (Exception e1) {
		   logger.warn(e1.getMessage());
		} finally {
			try {
				ouputStream.flush();
				ouputStream.close();
			} catch (IOException e) {
				logger.warn(e.getMessage());
			}
		}
		return null;
	}
	
	/**
	 * 获取当前集团下所有市场
	 * @param userList
	 */
	public List<String> getCurGroupMarkets() throws Exception {
		List<SysOrgDTO> orgs = sysOrgService.queryByCondition(null);
		List<SysOrgDTO> marketList = OrgUserUtil.getOrgsByType(orgs, getUserGroup().getId(), Const.ORG_TYPE_MARKET);
		if(marketList.size() == 0){
			throw new BizException(MsgCons.C_30000, MsgCons.M_30000);
		}
		List<String> marketIdList = new ArrayList<>();
		for(SysOrgDTO dto : marketList){
			marketIdList.add(dto.getId().toString());
		}
		return marketIdList;
	}
	
	/**
	 * 获取当前集团下流程模板
	 * @param userList
	 */
	public List<String> getCurGroupProcess() throws Exception {
		Map<String,Object> map = new HashMap<>();
		map.put("orgIdList", getCurGroupMarkets());
		map.put("monitorId", super.getSysUser().getId());
		List<WfProcessDTO> processList = wfProcessService.getCurGroupProcess(map);
		List<String> list = new ArrayList<>();
		for(WfProcessDTO dto : processList){
			list.add(dto.getId());
		}
		return list;
	}
	
	/**
	 * 获取当前市场下流程模板
	 * @param userList
	 */
	public List<String> getCurMarketProcess() throws Exception {
		Map<String,Object> map = new HashMap<>();
		SysOrgDTO orgDto = super.getCurrentMarket();
		if(orgDto == null){
			return null;
		}
		List<String> orgList = new ArrayList<>();
		orgList.add(orgDto.getId().toString());
		map.put("orgIdList", orgList);
		map.put("monitorId", super.getSysUser().getId());
		List<WfProcessDTO> processList = wfProcessService.getCurGroupProcess(map);
		List<String> list = new ArrayList<>();
		for(WfProcessDTO dto : processList){
			list.add(dto.getId());
		}
		return list;
	}
}
