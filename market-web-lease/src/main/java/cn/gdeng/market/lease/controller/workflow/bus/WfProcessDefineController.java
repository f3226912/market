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
import org.snaker.engine.entity.Process;
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
import cn.gdeng.market.dto.workflow.WfProcessDTO;
import cn.gdeng.market.enums.Const;
import cn.gdeng.market.enums.MsgCons;
import cn.gdeng.market.enums.WorkflowBusTypeEnum;
import cn.gdeng.market.exception.BizException;
import cn.gdeng.market.lease.bean.AjaxResult;
import cn.gdeng.market.lease.controller.admin.SysOrgController;
import cn.gdeng.market.lease.controller.base.BaseController;
import cn.gdeng.market.lease.util.OrgUserUtil;
import cn.gdeng.market.lease.util.SnakerHelper;
import cn.gdeng.market.service.admin.SysOrgService;
import cn.gdeng.market.service.admin.SysUserService;
import cn.gdeng.market.service.workflow.bus.WfProcessService;
import cn.gdeng.market.service.workflow.core.SnakerProcessService;
import cn.gdeng.market.util.DateUtil;
import jxl.Workbook;
import jxl.write.Label;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;

/**
 *	流程定义管理
 *	@author houxp
 **/
@Controller
@RequestMapping("wfProcessDefine")
public class WfProcessDefineController extends BaseController{

	private Logger logger = LoggerFactory.getLogger(SysOrgController.class);
	
	@Autowired
	private WfProcessService wfProcessService;
	
	@Autowired
	private SysUserService sysUserService;
	
	@Autowired
	private SysOrgService sysOrgService;
	/**
	 * snaker查询服务类
	 */
	@Autowired
	private SnakerProcessService snakerProcessService;
	/**
	 * 调转到流程定义管理界面
	 * @param mv
	 * @return
	 * @throws BizException
	 */
	@RequestMapping("index")
	public ModelAndView index(ModelAndView mv) throws BizException {
		mv.setViewName("workflow/define/index");
		return mv;
	}
	
	/**
	 * 调转到流程编辑界面
	 * @param mv
	 * @return
	 * @throws BizException
	 */
	@RequestMapping("jumpToEdit")
	public ModelAndView jumpToEdit(ModelAndView mv) throws BizException {
		mv.setViewName("workflow/define/updProcess");
		return mv;
	}
	
	/**
	 * 调转到工作流岗位选择的弹出框页面
	 * @param mv
	 * @return
	 * @throws BizException
	 */
	@RequestMapping("toWfSelectPostWin")
	public ModelAndView toWfSelectPostWin(ModelAndView mv) throws BizException {
		mv.setViewName("workflow/define/wfSelectPostWin");
		return mv;
	}
	
	/**
	 * 调转到工作流人员选择的弹出框页面
	 * @param mv
	 * @return
	 * @throws BizException
	 */
	@RequestMapping("toWfSelectPersonWin")
	public ModelAndView toWfSelectPersonWin(ModelAndView mv) throws BizException {
		mv.setViewName("workflow/define/wfSelectPersonWin");
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
	 * 查询流程定义列表-分页
	 * @param model
	 * @return
	 */
	@RequestMapping("getList4Page")
	@ResponseBody
	public AjaxResult<GuDengPage<WfProcessDTO>> getList4Page(Model model,WfProcessDTO dto) throws Exception {
		logger.info("分页流程定义入参:"+JSON.toJSONString(dto));
		
		AjaxResult<GuDengPage<WfProcessDTO>> result = new AjaxResult<GuDengPage<WfProcessDTO>>();
		
		//获取分页信息
		GuDengPage<WfProcessDTO> pageInfo = super.getPageInfoByRequest();
		//查询条件
		pageInfo.getParaMap().put("displayName", dto.getDisplayName());
		pageInfo.getParaMap().put("state", dto.getState());
		pageInfo.getParaMap().put("busType", dto.getBusType());
		pageInfo.getParaMap().put("orgIdList", getCurGroupMarkets());
		pageInfo = wfProcessService.queryByConditionPage(pageInfo);
		logger.info("分页查询平台用户结果:"+JSON.toJSONString(pageInfo));
		result.setResult(pageInfo);
		return result;
	}
	
	@RequestMapping(value="checkExportParams", produces="application/json;charset=utf-8")
	@ResponseBody
	public AjaxResult<String> checkExportParams(HttpServletRequest request,WfProcessDTO dto){
		AjaxResult<String> re =new AjaxResult<String> ();
		try{
			// 设置查询参数
			Map<String, Object> map = getParametersMap(request);
			map.put("displayName", dto.getDisplayName());
			map.put("state", dto.getState());
			map.put("busType", dto.getBusType());
			map.put("orgIdList", getCurGroupMarkets());
			map.put("orgIdList", getCurGroupMarkets());
			int total = wfProcessService.countByCondition(map);
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
	@RequestMapping(value = "exportData")
	public String exportData(HttpServletRequest request,WfProcessDTO dto) {
		Map<String, Object> map = getParametersMap(request);
		// 设置查询参数
		map.put("startRow", 0);
		map.put("endRow", EXPORT_PAGE_SIZE);
		WritableWorkbook wwb = null;
		OutputStream ouputStream = null;
		try {
			// 设置输出响应头信息，
			response.setContentType("application/vnd.ms-excel");
			String fileName = new String("流程定义管理".getBytes(), "ISO-8859-1")+DateUtil.toString(new Date(), "yyyy-MM-dd_HH:mm:ss");
			response.setHeader("Content-disposition", "attachment;filename=" + fileName + ".xls");
			ouputStream = response.getOutputStream();
			// 在输出流中创建一个新的写入工作簿
			wwb = Workbook.createWorkbook(ouputStream);
			if (wwb != null) {
				WritableSheet sheet = wwb.createSheet("流程定义管理", 0);// 创建一个工作页，第一个参数的页名，第二个参数表示该工作页在excel中处于哪一页
				// 第一个参数表示列，第二个参数表示行
				Label label00 = new Label(0, 0, "流程模板名称");// 填充第一行第一个单元格的内容
				Label label10 = new Label(1, 0, "业务对象");// 填充第一行第二个单元格的内容
				Label label20 = new Label(2, 0, "启用状态");//  填充第一行第三个单元格的内容
				Label label30 = new Label(3, 0, "所属市场");// 填充第一行第四个单元格的内容
				Label label40 = new Label(4, 0, "修改人 ");//填充第一行第无个单元格的内容
				Label label50 = new Label(5, 0, "修改时间");//填充第一行第六个单元格的内容
				sheet.addCell(label00);// 将单元格加入表格
				sheet.addCell(label10);// 将单元格加入表格
				sheet.addCell(label20);
				sheet.addCell(label30);
				sheet.addCell(label40);
				sheet.addCell(label50);
				/*** 循环添加数据到工作簿 ***/
				// 查询数据list
			    int	endRow=EXPORT_PAGE_SIZE;
			    map.put("displayName", dto.getDisplayName());
			    map.put("state", dto.getState());
			    map.put("busType", dto.getBusType());
			    map.put("orgIdList", getCurGroupMarkets());
				int totalCount = wfProcessService.countByCondition(map);
			    int exportCount = totalCount/EXPORT_PAGE_SIZE;//总条数/每页显示的条数=总页数 
			    int mod = totalCount % EXPORT_PAGE_SIZE;
			    if(mod != 0){
			    	exportCount++; 	
			    } 
			    int	startRow=0;
			    map.put("startRow", startRow);
			    map.put("endRow", endRow);
			    GuDengPage<WfProcessDTO> pageInfo = new GuDengPage<>();
			    pageInfo.getParaMap().put("displayName", dto.getDisplayName());
				pageInfo.getParaMap().put("state", dto.getState());
				pageInfo.getParaMap().put("busType", dto.getBusType());
				pageInfo.getParaMap().put("orgIdList", getCurGroupMarkets());
			    pageInfo.getParaMap().put("orgIdList", getCurGroupMarkets());
			    pageInfo.setParaMap(map);
			    for(int i=1;i<=exportCount;i++){
				List<WfProcessDTO> list = (List<WfProcessDTO>) wfProcessService.queryByConditionPage(pageInfo).getRecordList();
				for (int j = 0; j < list.size(); j++) {
					WfProcessDTO wfProcessDTO= list.get(j);
					Label label0 = new Label(0,  j+startRow  + 1, String.valueOf(wfProcessDTO.getDisplayName()));
					Label label1 = new Label(1,  j+startRow  + 1, String.valueOf(wfProcessDTO.getBusTypeDesc()));
					Label label2 = new Label(2,  j+startRow + 1, String.valueOf(wfProcessDTO.getStateDesc()));
					Label label3 = new Label(3,  j+startRow + 1, String.valueOf(wfProcessDTO.getOrgDesc()));
					Label label4 = new Label(4,  j+startRow  + 1,  wfProcessDTO.getModificator() == null ? "" : String.valueOf(wfProcessDTO.getModificator()));
					Label label5 = new Label(5,  j+startRow  + 1,  wfProcessDTO.getModifiedTime() == null ? "" : String.valueOf(wfProcessDTO.getModifiedTime()));
					sheet.addCell(label0);//将单元格加入表格
					sheet.addCell(label1);// 
					sheet.addCell(label2);
					sheet.addCell(label3);
					sheet.addCell(label4);
					sheet.addCell(label5);
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
	 * 调转到流程定义修改界面
	 * @param mv
	 * @return
	 * @throws BizException
	 */
	@RequestMapping("jumpToProcessAdd")
	public ModelAndView jumpToProcessUpd(ModelAndView mv) throws BizException {
		mv.setViewName("workflow/define/addProcess");
		return mv;
	}
	
	/**
	 * 调转到流程定义新增界面
	 * @param model
	 * @return
	 */
	@RequestMapping("jumpToProcessEdit")
	@ResponseBody
	public ModelAndView jumpToProcessEdit(ModelAndView mv,WfProcessDTO reqDto) throws Exception {
		WfProcessDTO dto = wfProcessService.getById(reqDto.getId());
		
		/*获取processModel，并转换为json格式供前端回显。
		 * ps：
		 * 	  snakerProcessService对象有使用缓存来存储Process对象，从而查询速度更快，
		 * 	     并且查询出来的Process对象是对ProcessModel有解析并存储到mode属性中的。
		 * 
		 */
		Process process = snakerProcessService.getProcessById(reqDto.getId());
		String modelJson = SnakerHelper.getModelJson(process.getModel());
		dto.setProcessModelJson(modelJson);
		//获取业务类型集合
		List<WfProcessDTO> busTypeList = new ArrayList<>();
		for(WorkflowBusTypeEnum workflowBusTypeEnum : WorkflowBusTypeEnum.values()){
			WfProcessDTO dto1 = new WfProcessDTO();
			dto1.setBusType(workflowBusTypeEnum.getCode());
			dto1.setBusTypeDesc(workflowBusTypeEnum.getDesc());
			busTypeList.add(dto1);
		}
		//获取当前集团所有市场
		List<SysOrgDTO> orgs = sysOrgService.queryByCondition(null);
		List<SysOrgDTO> marketList = OrgUserUtil.getOrgsByType(orgs, getUserGroup().getId(), Const.ORG_TYPE_MARKET);
		
		mv.addObject("dto",dto);
		mv.addObject("busTypeList",busTypeList);
		mv.addObject("marketList",marketList);
		mv.setViewName("workflow/define/updProcess");
		return mv;
	}
	
	/**
	 * 获取集团公司超级用户
	 * @param model
	 * @return
	 */
	@RequestMapping("getComSuper")
	@ResponseBody
	public AjaxResult<SysUserDTO> getComSuper() throws Exception {
		AjaxResult<SysUserDTO> result = new AjaxResult<>();
		Map<String,Object> map = new HashMap<>();
		map.put("groupId", super.getUserGroup().getId());
		map.put("status", Const.USER_STATUS_1);
		map.put("type", Const.USER_TYPE_2);
		List<SysUserDTO> list = sysUserService.getAdmin(map);
		if (list != null && list.size() > 0) {
			result.setResult(list.get(0));
		}
		return result;
	} 
	
	/**
	 * 获取当前集团下所有市场
	 * @param userList
	 */
	public List<Integer> getCurGroupMarkets() throws Exception {
		List<SysOrgDTO> orgs = sysOrgService.queryByCondition(null);
		List<SysOrgDTO> marketList = OrgUserUtil.getOrgsByType(orgs, getUserGroup().getId(), Const.ORG_TYPE_MARKET);
		if(marketList.size() == 0){
			throw new BizException(MsgCons.C_30000, MsgCons.M_30000);
		}
		List<Integer> marketIdList = new ArrayList<>();
		for(SysOrgDTO dto : marketList){
			marketIdList.add(dto.getId());
		}
		return marketIdList;
	}
}
