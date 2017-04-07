package cn.gdeng.market.lease.controller.lease.contract;

import java.io.IOException;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import jxl.Workbook;
import jxl.write.Label;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import cn.gdeng.market.dto.bean.GuDengPage;
import cn.gdeng.market.dto.lease.contract.ContractChangeDTO;
import cn.gdeng.market.dto.lease.contract.ContractDTO;
import cn.gdeng.market.dto.lease.contract.ContractEntityDTO;
import cn.gdeng.market.dto.lease.contract.ContractFreeDTO;
import cn.gdeng.market.dto.lease.contract.ContractLeaseDTO;
import cn.gdeng.market.dto.lease.contract.ContractMainDTO;
import cn.gdeng.market.dto.lease.contract.ContractOthersFeeDTO;
import cn.gdeng.market.dto.lease.contract.ContractPaymentDTO;
import cn.gdeng.market.entity.lease.contract.ContractApprovalEntity;
import cn.gdeng.market.entity.lease.contract.ContractChangeEntity;
import cn.gdeng.market.entity.lease.contract.ContractFreeEntity;
import cn.gdeng.market.entity.lease.contract.ContractLeaseEntity;
import cn.gdeng.market.entity.lease.contract.ContractMainEntity;
import cn.gdeng.market.entity.lease.contract.ContractOthersFeeEntity;
import cn.gdeng.market.entity.lease.contract.ContractPaymentEntity;
import cn.gdeng.market.enums.ApprovalTypeEnum;
import cn.gdeng.market.enums.ContractChargingWaysEnum;
import cn.gdeng.market.enums.MsgCons;
import cn.gdeng.market.exception.BizException;
import cn.gdeng.market.lease.bean.AjaxResult;
import cn.gdeng.market.lease.controller.base.BaseController;
import cn.gdeng.market.lease.util.PrintConvertDataUtil;
import cn.gdeng.market.service.lease.contract.ContractChangeService;
import cn.gdeng.market.service.lease.contract.ContractManageService;
import cn.gdeng.market.service.lease.contract.ContractRentService;
import cn.gdeng.market.service.lease.settings.PrintService;
import cn.gdeng.market.service.lease.settings.PrintSetService;
import cn.gdeng.market.util.DateUtil;
import cn.gdeng.market.util.PropertyUtil;

import com.alibaba.fastjson.JSON;

/**
 * 合同变更
 *
 */
@Controller
@RequestMapping("contractChange")
public class ContractChangeController extends BaseController {
	
	private Logger logger = LoggerFactory.getLogger(ContractManageController.class);
	
	@Resource	
	private ContractManageService contractManageService;
	
	@Resource	
	private ContractChangeService contractChangeService;
	
	@Resource
	private PrintSetService printSetService;
	
	@Resource
	private PrintService printService;
	
	@Resource
	private PropertyUtil propertyUtil;
	
	@Resource
	private PrintConvertDataUtil printConvertDataUtil;
	
	@Resource
	private ContractRentService contractRentService;
	

	@RequestMapping("index")
	public String index(){
		return "contract/change/index";
	}
	/**
	 * 分页查询 
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("queryPage")
	@ResponseBody
	public AjaxResult<GuDengPage<ContractChangeDTO>> queryPage(HttpServletRequest request)throws Exception{
		AjaxResult<GuDengPage<ContractChangeDTO>> re = new AjaxResult<>();
		GuDengPage<ContractChangeDTO> page=new GuDengPage<ContractChangeDTO> ();
		if(null!=super.getCurrentMarket()){
		Integer marketId= super.getCurrentMarket().getId();
		Map<String, Object> paramMap = 	getParametersMap(request);
		paramMap.put("marketId", marketId);
		setCommParameters(request, paramMap);
		page.setParaMap(paramMap);
		page=contractChangeService.getByConditionPage(page);
		}
		re.setResult(page);
		return  re;
	}
	
	
	

	@RequestMapping(value="checkExportParams", produces="application/json;charset=utf-8")
	@ResponseBody
	public AjaxResult<String> checkExportParams(HttpServletRequest request){
		AjaxResult<String> re =new AjaxResult<String> ();
		try{
			// 设置查询参数
			Map<String, Object> map = getParametersMap(request);
			if(null!=super.getCurrentMarket()){
				map.put("marketId", super.getCurrentMarket().getId());
			int total = contractChangeService.getExpConditionCount(map);
			if (total ==0){
				re.setCode(202);
				re.setMsg( "查询没有符合的结果 ,请修改其他查询条件...");
				return re;
			}
			if (total > EXPORT_MAX_SIZE){
				re.setCode(202);
				re.setMsg("查询结果集太大(>"+EXPORT_MAX_SIZE+"条), 请缩减日期范围, 或者修改其他查询条件...");
				return re;
			}}else{
				re.setCode(202);
				re.setMsg("获取不到当前市场！");
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
	public String exportData(HttpServletRequest request) {
		Map<String, Object> map = getParametersMap(request);
		// 设置查询参数
		map.put("startRow", 0);
		map.put("endRow", EXPORT_PAGE_SIZE);
		WritableWorkbook wwb = null;
		OutputStream ouputStream = null;
		try {
			// 设置输出响应头信息，
			response.setContentType("application/vnd.ms-excel");
			String fileName = new String("合同变更".getBytes(), "ISO-8859-1")+DateUtil.toString(new Date(), "yyyy-MM-dd_HH:mm:ss");
			response.setHeader("Content-disposition", "attachment;filename=" + fileName + ".xls");
			ouputStream = response.getOutputStream();
			// 在输出流中创建一个新的写入工作簿
			wwb = Workbook.createWorkbook(ouputStream);
			if (wwb != null) {
				WritableSheet sheet = wwb.createSheet("合同变更", 0);// 创建一个工作页，第一个参数的页名，第二个参数表示该工作页在excel中处于哪一页
				// 第一个参数表示列，第二个参数表示行
				Label label00 = new Label(0, 0, "合同编号");// 填充第一行第一个单元格的内容
				Label label10 = new Label(1, 0, "租赁资源 ");//leasingResource 填充第一行第二个单元格的内容
				Label label20 = new Label(2, 0, "变列原因");// info 填充第一行第三个单元格的内容
				Label label30 = new Label(3, 0, "乙方");// partyB填充第一行第四个单元格的内容
				Label label40 = new Label(4, 0, "经办人 ");//Trustees
				Label label50 = new Label(5, 0, "审批状态");// approvalStatus
				Label label60 = new Label(6, 0, "客户名称");//customerName 填充第一行第五个单元格的内容
				Label label70 = new Label(7, 0, "经办日期");//CreateTime
				sheet.addCell(label00);// 将单元格加入表格
				sheet.addCell(label10);// 将单元格加入表格
				sheet.addCell(label20);
				sheet.addCell(label30);
				sheet.addCell(label40);
				sheet.addCell(label50);
				sheet.addCell(label60);
				sheet.addCell(label70);
				/*** 循环添加数据到工作簿 ***/
				// 查询数据list
			    int	endRow=EXPORT_PAGE_SIZE;
				if(null!=super.getCurrentMarket()){
					map.put("marketId", super.getCurrentMarket().getId());
				}else{
					return null;
				}
				int totalCount = contractChangeService.getExpConditionCount(map);
			    int exportCount = totalCount/EXPORT_PAGE_SIZE;//总条数/每页显示的条数=总页数 
			    int mod = totalCount % EXPORT_PAGE_SIZE;
			    if(mod != 0){
			    	exportCount++; 	
			    } 
			    int	startRow=0;
			    map.put("startRow", startRow);
			    map.put("endRow", endRow);
			    for(int i=1;i<=exportCount;i++){
				List<ContractChangeDTO> list = (List<ContractChangeDTO>) contractChangeService.getExpConditionList(map);
				for (int j = 0; j < list.size(); j++) {
					ContractChangeDTO contractChangeDTO= list.get(j);
					Label label0 = new Label(0,  j+startRow  + 1, String.valueOf(contractChangeDTO.getContractNo()));
					Label label1 = new Label(1,  j+startRow  + 1, String.valueOf(contractChangeDTO.getLeasingResource()));
					Label label2 = new Label(2,  j+startRow + 1, String.valueOf(contractChangeDTO.getChangeReasionValue()));
					Label label3 = new Label(3,  j+startRow + 1, String.valueOf(contractChangeDTO.getPartyB()));
					Label label4 = new Label(4,  j+startRow  + 1,  String.valueOf(contractChangeDTO.getTrustees()));
					Label label5 = new Label(5,  j+startRow  + 1,  String.valueOf(contractChangeDTO.getApprovalStatusValue()));
					Label label6 = new Label(6,  j+startRow  + 1,  String.valueOf(contractChangeDTO.getCustomerName()));
					Label label7 = new Label(7,  j+startRow  + 1,  String.valueOf(DateUtil.toString(contractChangeDTO.getCreateTime(), DateUtil.DATE_FORMAT_DATEONLY)));
					sheet.addCell(label0);//将单元格加入表格
					sheet.addCell(label1);// 
					sheet.addCell(label2);
					sheet.addCell(label3);
					sheet.addCell(label4);
					sheet.addCell(label5);
					sheet.addCell(label6);
					sheet.addCell(label7);
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
	 * 跳转人工审核页面
	 * @return
	 * @throws BizException 
	 */
	@RequestMapping("showAduitPage")
	public String showAduitPage(Model model) throws BizException{
		model.addAttribute("userName", getSysUser().getName());
		model.addAttribute("currentTime", DateUtil.getSysDateTimeString());
		return "contract/manage/auditPage";
	}
	
	/**
	 * 合同变更人工审核
	 * @param entity
	 * @return
	 * @throws BizException
	 */
	@RequestMapping("auditByHuman")
	@ResponseBody
	public AjaxResult<Integer> auditByHuman(ContractApprovalEntity entity) throws BizException{
		logger.info("合同变更人工审核入参:"+JSON.toJSONString(entity));
		AjaxResult<Integer> result = new AjaxResult<Integer>();
		entity.setTrustees(getUserIdStr());
		entity.setCreateUserId(getUserIdStr());
		entity.setUpdateUserId(getUserIdStr());
		contractChangeService.auditByHuman(entity, getUserIdStr());
		return result;
	}
	
	/**
	 * 合同变更表单
	 * @param id 合同ID
	 */
	@RequestMapping("/contractChangeInfo/{id}")
	public String contractForm(ModelMap model,@PathVariable int id){
		contractInfo(model,id);
		return "contract/change/contractChangeInfo";
	}
	
	/**
	 * 合同变更详情
	 * @param id 合同ID
	 */
	@RequestMapping("/contractChangeDetail")
	public String detail(){
		return "contract/change/contractChangeDetail";
	}
	
	@RequestMapping("/show/{id}")
	public String show(ModelMap model,@PathVariable int id){
		contractInfo(model,id);
		return "contract/baseInfoDetail_b";
	}
	
	private void contractInfo(ModelMap model,int id){
		Map<String,Object> paramMap = new LinkedHashMap<String,Object>();
		paramMap.put("contractId", id);
		ContractChangeDTO mainDTO = contractChangeService.getConditionChange(paramMap);
		List<ContractOthersFeeDTO> othersFeeList = contractManageService.findAllOthersFee(paramMap);
		List<ContractChangeDTO> changeList= contractChangeService.changeContractList(paramMap);
		if(mainDTO.getChargingWays().equals(ContractChargingWaysEnum.CYCLE.getCode())){
			List<ContractLeaseDTO> leaseList = contractManageService.findAllLease(paramMap);
			List<ContractFreeDTO> freeList = contractManageService.findAllFree(paramMap);
			model.addAttribute("freeList", freeList);
			model.addAttribute("leaseList", leaseList);
		}else{
			List<ContractPaymentDTO> paymentList = contractManageService.findAllPayment(paramMap);
			model.addAttribute("paymentList", paymentList);
		}
		model.addAttribute("mainDTO", mainDTO);
		model.addAttribute("othersFeeList", othersFeeList);
		model.addAttribute("changeList", changeList);
	}
	
	/**
	 * 保存变更合同
	 * @throws BizException 
	 */
	@SuppressWarnings({ "deprecation", "unchecked" })
	@ResponseBody
	@RequestMapping("/save")
	public Object save(String params) throws Exception{
		AjaxResult<Integer> result = new AjaxResult<Integer>();
		JSONObject jsonObj = JSONObject.fromObject(params);
		Date startLeasingDay=null;
		//1、使用JSONObject
		ContractMainEntity mainEntity = (ContractMainEntity)JSONObject.toBean(jsonObj.getJSONObject("mainDTO"), ContractMainEntity.class);
		ContractChangeEntity changeEntity = (ContractChangeEntity)JSONObject.toBean(jsonObj.getJSONObject("changeDTO"), ContractChangeEntity.class);
		mainEntity.setStartLeasingDay(startLeasingDay);
		mainEntity.setMarketId(super.getCurrentMarket().getId());
		List<ContractLeaseEntity> leaseList = null;
		if(null != jsonObj.getJSONArray("leaseList")){
			leaseList = JSONArray.toList(jsonObj.getJSONArray("leaseList"), ContractLeaseEntity.class);
		}
		List<ContractFreeEntity> freeList = null;
		if(null != jsonObj.getJSONArray("freeList")){
			freeList = JSONArray.toList(jsonObj.getJSONArray("freeList"), ContractFreeEntity.class);
		}
		List<ContractPaymentEntity> paymentList = null;
		if(null != jsonObj.getJSONArray("paymentList")){
			paymentList = JSONArray.toList(jsonObj.getJSONArray("paymentList"), ContractPaymentEntity.class);
		}
		List<ContractOthersFeeEntity> othersFeeList = null;
		if(null != jsonObj.getJSONArray("othersFeeList")){
			othersFeeList = JSONArray.toList(jsonObj.getJSONArray("othersFeeList"), ContractOthersFeeEntity.class);
		}
		ContractEntityDTO  dto = new ContractEntityDTO();
		dto.setFreeList(freeList);
		dto.setLeaseList(leaseList);
		deleteCommaByValue(mainEntity);
		dto.setMainEntity(mainEntity);
		dto.setChangeEntity(changeEntity);
		dto.setOthersFeeList(othersFeeList);
		dto.setPaymentList(paymentList);
		dto.getMainEntity().setApprovalType(ApprovalTypeEnum.CONTRACT_MANAGER.getCode());
		dto.getMainEntity().setCreateUserId(getSysUser().getId().toString());
		//1、使用JSONObject
		contractChangeService.updateSaveContract(dto);
		return result;
	}
	/**
	 * 编辑保存变更合同
	 * @param  String params{ ContractEntityDTO,leaseList,freeList,othersFeeList,paymentList,accessoriesList }
	 * @return AjaxResult
	 * @throws BizException 
	 */
	@ResponseBody
	@RequestMapping("/updateSave")
	public Object updateSave(String params) throws Exception{
		AjaxResult<Integer> result = new AjaxResult<Integer>();
		JSONObject jsonObj = JSONObject.fromObject(params);
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");                
		Date startLeasingDay=null;
		if(jsonObj.getJSONObject("mainDTO").containsKey("startLeasingDay")){
			startLeasingDay=sdf.parse((String) jsonObj.getJSONObject("mainDTO").get("startLeasingDay"));
		}
		Date endLeasingDay=null;
		if(jsonObj.getJSONObject("mainDTO").containsKey("endLeasingDay")){
			endLeasingDay=sdf.parse((String) jsonObj.getJSONObject("mainDTO").get("endLeasingDay"));
		}
		Date dateOfContract=null;
		if(jsonObj.getJSONObject("mainDTO").containsKey("dateOfContract")){
			dateOfContract=sdf.parse((String) jsonObj.getJSONObject("mainDTO").get("dateOfContract"));
		}
		//1、使用JSONObject
		ContractMainEntity mainEntity = (ContractMainEntity)JSONObject.toBean(jsonObj.getJSONObject("mainDTO"), ContractMainEntity.class);
		ContractChangeEntity changeEntity = (ContractChangeEntity)JSONObject.toBean(jsonObj.getJSONObject("changeDTO"), ContractChangeEntity.class);
		mainEntity.setStartLeasingDay(startLeasingDay);
		mainEntity.setEndLeasingDay(endLeasingDay);
		mainEntity.setDateOfContract(dateOfContract);
		mainEntity.setMarketId(super.getCurrentMarket().getId());
		List<ContractLeaseEntity> leaseList = null;
		if(null != jsonObj.getJSONArray("leaseList")){
			leaseList = JSONArray.toList(jsonObj.getJSONArray("leaseList"), ContractLeaseEntity.class);
		}
		List<ContractFreeEntity> freeList = null;
		if(null != jsonObj.getJSONArray("freeList")){
			freeList = JSONArray.toList(jsonObj.getJSONArray("freeList"), ContractFreeEntity.class);
		}
		List<ContractPaymentEntity> paymentList = null;
		if(null != jsonObj.getJSONArray("paymentList")){
			paymentList = JSONArray.toList(jsonObj.getJSONArray("paymentList"), ContractPaymentEntity.class);
		}
		List<ContractOthersFeeEntity> othersFeeList = null;
		if(null != jsonObj.getJSONArray("othersFeeList")){
			othersFeeList = JSONArray.toList(jsonObj.getJSONArray("othersFeeList"), ContractOthersFeeEntity.class);
		}
		ContractEntityDTO  dto = new ContractEntityDTO();
		dto.setFreeList(freeList);
		dto.setLeaseList(leaseList);
		dto.setChangeEntity(changeEntity);
		deleteCommaByValue(mainEntity);
		dto.setMainEntity(mainEntity);
		dto.setOthersFeeList(othersFeeList);
		dto.setPaymentList(paymentList);
		dto.getChangeEntity().setUpdateUserId(getSysUser().getId().toString());
		dto.getMainEntity().setUpdateUserId(getSysUser().getId().toString());
		contractChangeService.updateContractMainAndChangeInfo(dto);
		return result;
	}
	/**
	 * 合同变更打印
	 * @param mv
	 * @return
	 * @throws BizException 
	 */
	@RequestMapping("print")
	public ModelAndView print(int settingId, int changeId, ModelAndView mv) throws BizException{
		if(changeId < 0){
			throw new BizException(MsgCons.C_20000, "合同变更信息【"+ changeId +"】不存在");
		}
		
		ContractChangeEntity changeEntity = contractChangeService.getEntityById(changeId);
		if(changeEntity == null){
			throw new BizException(MsgCons.C_20000, "合同变更信息【"+ changeId +"】不存在");
		}
		
		// 查询合同信息
		Integer contractNewId = changeEntity.getContractNewId();
		Map<String,Object> paramMap = new LinkedHashMap<String,Object>();
		paramMap.put("id", contractNewId);
		paramMap.put("contractId", contractNewId);
		ContractDTO contractDTO = contractManageService.findContract(paramMap);
		if(contractDTO == null){
			throw new BizException(MsgCons.C_20000, "合同【"+ contractNewId +"】不存在");
		}
		
		// 按周期收费合同总金额
		ContractMainDTO mainDTO = contractDTO.getMainDTO();
		if(mainDTO != null && mainDTO.getChargingWays() != null 
				&& mainDTO.getChargingWays().byteValue() == ContractChargingWaysEnum.CYCLE.getCode().byteValue()){
			double totalAmt = contractRentService.getTotalAmt(mainDTO);
			mainDTO.setTotalAmt(totalAmt);
		}
		
		// 押金
		List<ContractOthersFeeDTO> othersFeeList = contractDTO.getOthersFeeList();
		if(othersFeeList != null) {
			for(ContractOthersFeeDTO fee : othersFeeList) {
				String feeName = fee.getItemName();
				if(feeName != null && feeName.indexOf("押金") != -1){
					mainDTO.setContractDeposit(fee.getTotal());
					break;
				}
			}
		}
	
		Map<String, String> dataMap = convertContractToPrintMap(contractDTO);
		printConvertDataUtil.convertContractChangeToMap(changeEntity, dataMap);
		
		String pdfName = printService.convertDocToPdf(settingId, dataMap);
		String pdfUrl = propertyUtil.getValue("printingFileHost") + pdfName;
		mv.addObject("fileUrl", pdfUrl);
		mv.setViewName("settings/print/preView");
		return mv;
	}
	
	/**
	 * 合同信息转为打印需要的Map格式
	 * @param contractMainDTO
	 * @return
	 */
	public Map<String, String> convertContractToPrintMap(ContractDTO contractDTO) {
		Map<String, String> dataMap = new HashMap<String, String>();
		if(contractDTO == null || contractDTO.getMainDTO() == null){
			return dataMap;
		}
		
		ContractMainDTO contractMainDTO = contractDTO.getMainDTO();
		// 合同基本信息
		printConvertDataUtil.convertContractMainToMap(contractMainDTO, dataMap);
		
		byte chargingWays = contractMainDTO.getChargingWays() == null ? -1 : contractMainDTO.getChargingWays();
		// 收费方式为：按周期收费时，租赁约定
		if(chargingWays == ContractChargingWaysEnum.CYCLE.getCode().byteValue()){
			printConvertDataUtil.convertLeaseListToMap(contractDTO.getLeaseList(), dataMap);
		}
		// 收费方式为：约定总金额时，租赁约定
		else if(chargingWays == ContractChargingWaysEnum.TOTAL.getCode().byteValue()){
			printConvertDataUtil.convertPaymentListToMap(contractDTO.getPaymentList(), dataMap);
		}
		
		// 免租约定
		printConvertDataUtil.convertFreeListToMap(contractDTO.getFreeList(), dataMap);
		
		// 其他费用约定
		printConvertDataUtil.convertOthersFeeListToMap(contractDTO.getOthersFeeList(), dataMap);
		
		return dataMap;
	}
	
	public void deleteCommaByValue(ContractMainEntity mainEntity){
		String ids= mainEntity.getLeasingResourceIds();
		String flag=ids.substring(ids.length()-1);
		if(flag.equals(",")){
			mainEntity.setLeasingResourceIds(ids.substring(0,ids.length()-1));	
		}
		String names= mainEntity.getLeasingResource();
		String nameFlag=names.substring(names.length()-1);
		if(nameFlag.equals(",")){
			mainEntity.setLeasingResource(names.substring(0,names.length()-1));	
		}
	}

}
