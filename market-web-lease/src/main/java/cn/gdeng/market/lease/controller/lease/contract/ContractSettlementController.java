package cn.gdeng.market.lease.controller.lease.contract;

import java.io.IOException;
import java.io.OutputStream;
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

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import cn.gdeng.market.dto.bean.GuDengPage;
import cn.gdeng.market.dto.lease.contract.ContractDTO;
import cn.gdeng.market.dto.lease.contract.ContractMainDTO;
import cn.gdeng.market.dto.lease.contract.ContractOthersFeeDTO;
import cn.gdeng.market.dto.lease.contract.ContractStatementsDTO;
import cn.gdeng.market.entity.lease.contract.ContractApprovalEntity;
import cn.gdeng.market.entity.lease.contract.ContractStatementsEntity;
import cn.gdeng.market.enums.ContractChargingWaysEnum;
import cn.gdeng.market.enums.MsgCons;
import cn.gdeng.market.exception.BizException;
import cn.gdeng.market.lease.bean.AjaxResult;
import cn.gdeng.market.lease.controller.base.BaseController;
import cn.gdeng.market.lease.util.PrintConvertDataUtil;
import cn.gdeng.market.service.lease.contract.ContractManageService;
import cn.gdeng.market.service.lease.contract.ContractRentService;
import cn.gdeng.market.service.lease.contract.ContractSettlementService;
import cn.gdeng.market.service.lease.settings.PrintService;
import cn.gdeng.market.service.lease.settings.PrintSetService;
import cn.gdeng.market.util.DateUtil;
import cn.gdeng.market.util.PropertyUtil;

import com.alibaba.fastjson.JSON;

/**
 * 合同结算
 *
 */
@Controller
@RequestMapping("contractSettlement")
public class ContractSettlementController  extends BaseController{
private Logger logger = LoggerFactory.getLogger(ContractManageController.class);
	
	@Resource	
	private ContractSettlementService contractSettlementService;
	
	@Resource
	private ContractManageService contractManageService;
	
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
			return "contract/settlement/index";
		}
		/**
		 * 分页查询 
		 * @param request
		 * @return
		 * @throws Exception
		 */
		@RequestMapping("queryPage")
		@ResponseBody
		public AjaxResult<GuDengPage<ContractStatementsDTO>> queryPage(HttpServletRequest request)throws Exception{
			AjaxResult<GuDengPage<ContractStatementsDTO>> re = new AjaxResult<>();
			GuDengPage<ContractStatementsDTO> page=new GuDengPage<ContractStatementsDTO> ();
			if(null!=super.getCurrentMarket()){
			Integer marketId= super.getCurrentMarket().getId();
			Map<String, Object> paramMap = 	getParametersMap(request);
			paramMap.put("marketId", marketId);
			setCommParameters(request, paramMap);
			page.setParaMap(paramMap);
			page=contractSettlementService.getByConditionPage(page);
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
				int total = contractSettlementService.getExpConditionCount(map);
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
				String fileName = new String("合同管理".getBytes(), "ISO-8859-1")+DateUtil.toString(new Date(), "yyyy-MM-dd_HH:mm:ss");
				response.setHeader("Content-disposition", "attachment;filename=" + fileName + ".xls");
				ouputStream = response.getOutputStream();
				// 在输出流中创建一个新的写入工作簿
				wwb = Workbook.createWorkbook(ouputStream);
				if (wwb != null) {
					WritableSheet sheet = wwb.createSheet("合同管理", 0);// 创建一个工作页，第一个参数的页名，第二个参数表示该工作页在excel中处于哪一页
					// 第一个参数表示列，第二个参数表示行
					Label label00 = new Label(0, 0, "结算类型");// 填充第一行第一个单元格的内容
					Label label10 = new Label(1, 0, "审批状态");//approvalStatus 填充第一行第二个单元格的内容
					Label label20 = new Label(2, 0, "租赁资源");// leasingResource 填充第一行第三个单元格的内容
					Label label30 = new Label(3, 0, "乙方");// partyB填充第一行第四个单元格的内容
					Label label40 = new Label(4, 0, "合同编号");//customerMobile 
					Label label50 = new Label(5, 0, "客户名称");// customerName
					Label label60 = new Label(6, 0, "经办人");//dateOfContract 填充第一行第五个单元格的内容
					Label label70 = new Label(7, 0, "经办日期");//startLeasingDay
					
					sheet.addCell(label00);// 将单元格加入表格
					sheet.addCell(label10);// 将单元格加入表格
					sheet.addCell(label20);
					sheet.addCell(label30);
					sheet.addCell(label40);
					sheet.addCell(label50);
					sheet.addCell(label60);
					sheet.addCell(label70);
					/*** 循环添加数据到工作簿 ***/
					   int	endRow=EXPORT_PAGE_SIZE;
						if(null!=super.getCurrentMarket()){
							map.put("marketId", super.getCurrentMarket().getId());
						}else{
							return null;
				        }
					// 查询数据list
					int totalCount = contractSettlementService.getExpConditionCount(map);
				    int exportCount = totalCount/EXPORT_PAGE_SIZE;//总条数/每页显示的条数=总页数 
				    int mod = totalCount % EXPORT_PAGE_SIZE;
				    if(mod != 0){
				    	exportCount++; 	
				    } 
				    int	startRow=0;
				    map.put("startRow", startRow);
				    map.put("endRow", endRow);
				    for(int i=1;i<=exportCount;i++){
					List<ContractStatementsDTO> list = (List<ContractStatementsDTO>) contractSettlementService.getExpConditionList(map);
					for (int j = 0; j < list.size(); j++) {
						ContractStatementsDTO contractStatementsDTO= list.get(j);
						Label label0 = new Label(0,  j+startRow  + 1, String.valueOf(contractStatementsDTO.getStatementsTypeValue()));
						Label label1 = new Label(1,  j+startRow  + 1, String.valueOf(contractStatementsDTO.getApprovalStatusValue()));
						Label label2 = new Label(2,  j+startRow + 1, String.valueOf(contractStatementsDTO.getLeasingResource()));
						Label label3 = new Label(3,  j+startRow + 1, String.valueOf(contractStatementsDTO.getPartyB()));
						Label label4 = new Label(4,  j+startRow  + 1,  String.valueOf(contractStatementsDTO.getContractNo()));
						Label label5 = new Label(5,  j+startRow  + 1,  String.valueOf(contractStatementsDTO.getCustomerName()));
						Label label6 = new Label(6,  j+startRow  + 1,  String.valueOf(contractStatementsDTO.getTrustees()));
						Label label7 = new Label(7,  j+startRow  + 1,  String.valueOf(DateUtil.toString(contractStatementsDTO.getCreateTime(), DateUtil.DATE_FORMAT_DATEONLY)));
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
	 */
	@RequestMapping("showAduitPage")
	public String showAduitPage(Model model) throws BizException{
		model.addAttribute("userName", getSysUser().getName());
		model.addAttribute("currentTime", DateUtil.getSysDateTimeString());
		return "contract/manage/auditPage";
	}
	
	/**
	 * 合同结算人工审核
	 * @param entity
	 * @return
	 * @throws BizException
	 */
	@RequestMapping("auditByHuman")
	@ResponseBody
	public AjaxResult<Integer> auditByHuman(ContractApprovalEntity entity) throws BizException{
		logger.info("合同结算人工审核入参:"+JSON.toJSONString(entity));
		AjaxResult<Integer> result = new AjaxResult<Integer>();
		entity.setTrustees(getSysUser().getName());
		entity.setCreateUserId(getUserIdStr());
		entity.setUpdateUserId(getUserIdStr());
		contractSettlementService.auditByHuman(entity);
		return result;
	}
	
	/**
	 * 合同结算信息详情完整页面
	 */
	@RequestMapping("contractSettlementDetail")
	public String detail(){
		return "contract/settlement/contractSettlementDetail";
	}
	
	/**
	 * 合同结算信息子页面
	 * @param id
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "showContractSettlementDetail/{id}")
	public String showContractSettlementDetail(@PathVariable Integer id, String isWfApproval, Model model) throws BizException{
		Map<String, Object> settlementMap = new HashMap<String, Object>();
		settlementMap.put("contractId", id);
		ContractStatementsEntity entity = contractSettlementService.getByContractId(settlementMap);;
		Map<String,Object> paramMap = new LinkedHashMap<String,Object>();
		paramMap.put("id", id);
		ContractMainDTO mainDTO = contractManageService.showMainInfo(paramMap);
		model.addAttribute("entity", entity);
		model.addAttribute("mainDTO", mainDTO);
		model.addAttribute("isWfApproval", isWfApproval);
		model.addAttribute("trustees", getSysUser().getName());
		model.addAttribute("currentTime", new Date());
		return "contract/settlement/showContractSettlementDetail";
	}
	
	/**
	 * 合同结算信息保存
	 * @param entity
	 */
	@RequestMapping(value="edit")
	@ResponseBody
	public AjaxResult<Integer> edit(ContractStatementsEntity entity) throws Exception{
		logger.info("合同结算信息保存入参:"+JSON.toJSONString(entity));
		AjaxResult<Integer> result = new AjaxResult<Integer>();
		contractSettlementService.edit(entity);
		return result;
	}
	
	/**
	 * 合同打印
	 * @param mv
	 * @return
	 * @throws BizException 
	 */
	@RequestMapping("print")
	public ModelAndView print(int settingId, int statementsId, ModelAndView mv) throws BizException{
		// 结算信息
		ContractStatementsEntity statements = contractSettlementService.findById(statementsId);
		if(statements == null){
			throw new BizException(MsgCons.C_20000, "合同结算信息【"+ statementsId +"】不存在");
		}
		
		// 合同信息
		Map<String,Object> paramMap = new LinkedHashMap<String,Object>();
		Integer contractId = statements.getContractId();
		paramMap.put("id", contractId);
		paramMap.put("contractId", contractId);
		ContractDTO contractDTO = contractManageService.findContract(paramMap);
		if(contractDTO == null){
			throw new BizException(MsgCons.C_20000, "合同【"+ contractId +"】不存在");
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
		
		// 数据格式转换
		Map<String, String> dataMap = convertContractToPrintMap(contractDTO);
		printConvertDataUtil.convertContractStatementsToMap(statements, dataMap);
		
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
	
	/**
	 * 合同结算信息保存
	 * @param contractId
	 */
	@RequestMapping(value="save")
	@ResponseBody
	public AjaxResult<Integer> Save(ContractStatementsEntity entity) throws Exception{
		entity.setTrustees(getSysUser().getName());
		entity.setReviewedTime(new Date());
		entity.setCreateUserId(getUserIdStr());
		entity.setUpdateUserId(getUserIdStr());
		AjaxResult<Integer> result = new AjaxResult<Integer>();
		contractSettlementService.save(entity);
		return result;
	}
	
}
