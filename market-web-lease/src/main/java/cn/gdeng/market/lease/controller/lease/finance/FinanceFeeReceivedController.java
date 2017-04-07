package cn.gdeng.market.lease.controller.lease.finance;

import java.io.IOException;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.alibaba.fastjson.JSON;

import cn.gdeng.market.dto.admin.SysUserDTO;
import cn.gdeng.market.dto.bean.GuDengPage;
import cn.gdeng.market.dto.lease.contract.ContractMainDTO;
import cn.gdeng.market.dto.lease.finance.FinanceFeeReceivedDTO;
import cn.gdeng.market.entity.lease.finance.FinanceFeeRecordEntity;
import cn.gdeng.market.enums.Const;
import cn.gdeng.market.enums.FinFundTypeEnum;
import cn.gdeng.market.enums.MsgCons;
import cn.gdeng.market.exception.BizException;
import cn.gdeng.market.lease.bean.AjaxResult;
import cn.gdeng.market.lease.controller.base.BaseController;
import cn.gdeng.market.lease.controller.lease.finance.querybean.FinanceFeeParamBean;
import cn.gdeng.market.lease.util.PrintConvertDataUtil;
import cn.gdeng.market.service.admin.SysUserService;
import cn.gdeng.market.service.lease.contract.ContractManageService;
import cn.gdeng.market.service.lease.finance.FinanceFeeReceivedService;
import cn.gdeng.market.service.lease.settings.PrintService;
import cn.gdeng.market.service.lease.settings.PrintSetService;
import cn.gdeng.market.util.DateUtil;
import cn.gdeng.market.util.PropertyUtil;
import jxl.Workbook;
import jxl.write.Label;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;

@Controller
public class FinanceFeeReceivedController extends BaseController {

	private Logger logger = LoggerFactory.getLogger(FinanceFeeReceivedController.class);

	@Autowired
	private FinanceFeeReceivedService financeFeeReceivedService;
	
	@Autowired
	private SysUserService sysUserService ;

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

	/**
	 * 实收款列表
	 * @param mv
	 * @return
	 * @throws BizException
	 */
	@RequestMapping("financeHas/Index")
	public ModelAndView shouldIndex(ModelAndView mv) throws BizException {
		mv.setViewName("finance/fund/received/hasReceiveList");
		return mv;
	}
	
	/**
	 * 实收款分页列表
	 * @return
	 * @throws BizException
	 */
	@ResponseBody
	@RequestMapping("financeHas/queryFeeReceived")
	public AjaxResult<GuDengPage<FinanceFeeReceivedDTO>> queryFeeReceived(FinanceFeeParamBean paramBean) throws BizException{
		
		logger.info("查询财务收款列表入参:"+JSON.toJSONString(paramBean));
		AjaxResult<GuDengPage<FinanceFeeReceivedDTO>> result = new AjaxResult<GuDengPage<FinanceFeeReceivedDTO>>();
		//设置查询参数
		Map<String, Object> params = getParameters(paramBean);
		//记录总数
		int total = financeFeeReceivedService.getFeeReceivedRecordCount(params);
		GuDengPage<FinanceFeeReceivedDTO> page = getPageInfoByRequest(paramBean);
		page.setTotal(total);
		params.put("startRow", page.getOffset());
		params.put("endRow", page.getPageSize());
		//排序
		params.put("specialSort", "1");
		List<FinanceFeeReceivedDTO> recordList = financeFeeReceivedService.queryFeeReceivedRecord(params);
		page.setRecordList(recordList);
		result.setResult(page);
		return result;
	}
	
	@RequestMapping("financeHas/toReceivedDetail")
	public ModelAndView turnToItemDetail(ModelAndView mv, String id) throws BizException {
		
		//查询实收款记录
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("id", id);
		FinanceFeeReceivedDTO financeFee = financeFeeReceivedService.queryFeeReceivedRecordById(params);
		
		//查询该条收款记录对应的合同信息
		params.put("id", financeFee.getContractVersion());
		ContractMainDTO contract = contractManageService.showMainInfo(params);
		try {
			financeFee.setAmount(financeFee.getAccountPayable() + financeFee.getAccountRebate());
			FinanceSupport.typeConvert(financeFee, null);
			FinanceSupport.typeConvert(contract, null);
		} catch (IllegalArgumentException e) {
			logger.info("turnToItemDetail typeConvert with IllegalArgumentException : {} ", e);
		} catch (IllegalAccessException e) {
			logger.info("turnToItemDetail typeConvert with IllegalAccessException : {} ", e);
		} catch (NoSuchFieldException e) {
			logger.info("turnToItemDetail typeConvert with NoSuchFieldException : {} ", e);
		} catch (SecurityException e) {
			logger.info("turnToItemDetail typeConvert with SecurityException : {} ", e);
		}
		SysUserDTO userDTO = sysUserService.getById(Integer.parseInt(financeFee.getAgent()));
		mv.addObject("financeFee", financeFee);
		mv.addObject("contract", contract);
		mv.addObject("user", userDTO);
		mv.setViewName("finance/fund/received/receivedShould");
		if (FinFundTypeEnum.NORMAL.getCode().equalsIgnoreCase(financeFee.getFundType())){
			//跳转实收款-应收款详情页面
			mv.setViewName("finance/fund/received/receivedShould");
		}else if(FinFundTypeEnum.TEMP.getCode().equalsIgnoreCase(financeFee.getFundType())){
			//跳转实收款-临时收款详情页面
			mv.setViewName("finance/fund/received/receivedTemp");
		}else{
			//跳转到实收款-退款详情页面
			mv.setViewName("finance/fund/received/receivedTemp");
		}
		return mv;
	}
	
	/**
	 * (正常应收款结算后的)实收款详情
	 * @param mv
	 * @return
	 * @throws BizException
	 */
	@ResponseBody
	@RequestMapping("financeHas/hasReceiveDetail")
	public AjaxResult<Map<String, Object>> shouldReceiveDetail(FinanceFeeRecordEntity entity) throws BizException {
		AjaxResult<Map<String, Object>> result = new AjaxResult<Map<String, Object>>();
		//根据合同编号以及版本号查询合同信息
		Map<String, Object> params = new HashMap<String, Object>();
		//查询收款记录
		params.put("id", entity.getId());
		FinanceFeeReceivedDTO feeReceived = financeFeeReceivedService.queryFeeReceivedRecordById(params);
		//查询该条收款记录对应的合同信息
		params.put("id", feeReceived.getContractVersion());
		ContractMainDTO contract = contractManageService.showMainInfo(params);
		Map<String, Object> res = new HashMap<String, Object>();
		res.put("contract", contract);
		res.put("feeReceived", feeReceived);
		result.setResult(res);
		return result;
	}	
	
	/**
	 * 实收款(临时收款)详情
	 * @param mv
	 * @return
	 * @throws BizException
	 */
	@ResponseBody
	@RequestMapping("financeHas/hasReceivedTempDetail")
	public AjaxResult<Map<String, Object>> hasReceivedTempDetail(FinanceFeeRecordEntity entity) throws BizException {
		AjaxResult<Map<String, Object>> result = new AjaxResult<Map<String, Object>>();
		//根据合同编号以及版本号查询合同信息
		Map<String, Object> params = new HashMap<String, Object>();
		//查询收款记录
		params.put("id", entity.getId());
		FinanceFeeReceivedDTO feeReceived = financeFeeReceivedService.queryFeeReceivedRecordById(params);
		//查询该条收款记录对应的合同信息
		params.put("id", feeReceived.getContractVersion());
		ContractMainDTO contract = contractManageService.showMainInfo(params);
		Map<String, Object> res = new HashMap<String, Object>();
		res.put("contract", contract);
		res.put("feeReceived", feeReceived);
		result.setResult(res);
		return result;
	}
	
	/**
	 * 打印
	 * @param settingId
	 * @param feeId
	 * @param mv
	 * @return
	 * @throws BizException
	 */
	@RequestMapping("financeHas/print")
	public ModelAndView print(int settingId, int feeId, ModelAndView mv) throws BizException {
		//查询实收款记录
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("id", feeId);
		FinanceFeeReceivedDTO feeReceived = financeFeeReceivedService.queryFeeReceivedRecordById(params);
		
		//查询该条收款记录对应的合同信息
		params.put("id", feeReceived.getContractVersion());
		ContractMainDTO contract = contractManageService.showMainInfo(params);
		
		// 数据转换
		Map<String, String> dataMap = new HashMap<String, String>();
		//转化实收款
		printConvertDataUtil.convertFeeReceived(feeReceived, dataMap);
		//经办人
		dataMap.put("${feeHandler}", getSysUser().getName());
		//转化合同信息
		printConvertDataUtil.convertContractMainToMap(contract, dataMap);
		
		String pdfName = printService.convertDocToPdf(settingId, dataMap);
		String pdfUrl = propertyUtil.getValue("printingFileHost") + pdfName;
		mv.addObject("fileUrl", pdfUrl);
		mv.setViewName("settings/print/preView");
		return mv;
	}
 	/**
	 * 检查导出数据
	 * @param request
	 * @return
	 */
	@RequestMapping(value="financeHas/checkExportParams", produces="application/json;charset=utf-8")
	@ResponseBody
	public AjaxResult<String> checkExportParams(HttpServletRequest request, FinanceFeeParamBean paramBean){
		AjaxResult<String> re =new AjaxResult<String> ();
		try{
			// 设置查询参数
			Map<String, Object> params = getParameters(paramBean);
			
			int total = financeFeeReceivedService.getFeeReceivedRecordCount(params);
			if (total == 0){
				re.setCode(MsgCons.C_30001);
				re.setMsg( "查询没有符合的结果 ,请修改其他查询条件...");
				return re;
			}
			if (total > Const.EXPORT_MAX_SIZE){
				re.setCode(MsgCons.C_30002);
				re.setMsg("查询结果集太大(>"+Const.EXPORT_MAX_SIZE+"条), 请缩减日期范围, 或者修改其他查询条件...");
				return re;
			}
		}catch(Exception e){
			logger.warn(e.getMessage());
			re.setCode(MsgCons.C_20000);
			re.setMsg("查询异常");
			return re;
		}
		return re;
	}

	
	/**
	 * 导出数据
	 * @param request
	 * @return
	 * @throws BizException 
	 */
	@RequestMapping(value = "financeHas/exportData")
	public String exportData(HttpServletRequest request, FinanceFeeParamBean paramBean) throws BizException {
		
		// 设置查询参数
		Map<String, Object> params = getParameters(paramBean);
		params.put("startRow", 0);
		params.put("endRow", Const.EXPORT_MAX_SIZE);
		//工作簿
		WritableWorkbook wwb = null;
		OutputStream ouputStream = null;
		try {
			// 设置输出响应头信息，
			response.setContentType("application/vnd.ms-excel");
			String fileName = new String("应收款管理".getBytes(), "ISO-8859-1") + DateUtil.toString(new Date(), "yyyy-MM-dd_HH:mm:ss");
			response.setHeader("Content-disposition", "attachment;filename=" + fileName + ".xls");
			//响应流(输出流)
			ouputStream = response.getOutputStream();
			// 在输出流中创建一个新的写入工作簿
			wwb = Workbook.createWorkbook(ouputStream);
			if (wwb != null) {
				// 创建一个工作页，第一个参数的页名，第二个参数表示该工作页在excel中处于哪一页
				WritableSheet sheet = wwb.createSheet("应收款管理", 0);
				// 第一个参数表示列，第二个参数表示行
				Label label00 = new Label(0, 0, "合同编号");// 填充第一行第一个单元格的内容
				Label label10 = new Label(1, 0, "租赁资源");//填充第一行第二个单元格的内容
				Label label20 = new Label(2, 0, "费项类型");//填充第一行第三个单元格的内容
				Label label30 = new Label(3, 0, "费项名称");//填充第一行第四个单元格的内容
				Label label40 = new Label(4, 0, "缴费期限 ");//
				Label label50 = new Label(5, 0, "计费起始日期");//
				Label label60 = new Label(6, 0, "计费结束日期");
				Label label70 = new Label(7, 0, "应收金额");
				Label label80 = new Label(8, 0, "实收金额");
				sheet.addCell(label00);// 将单元格加入表格
				sheet.addCell(label10);// 将单元格加入表格
				sheet.addCell(label20);
				sheet.addCell(label30);
				sheet.addCell(label40);
				sheet.addCell(label50);
				sheet.addCell(label60);
				sheet.addCell(label70);
				sheet.addCell(label80);
				//查询数据
				List<FinanceFeeReceivedDTO> recordList = financeFeeReceivedService.queryFeeReceivedRecord(params);
				//对部分字段做类型转化
				FinanceSupport.typeConvert(recordList);
				if (recordList != null && recordList.size() > 0) {
					for (int j = 0; j < recordList.size(); j++) {
						FinanceFeeReceivedDTO dto = recordList.get(j);
						//填充数据
						Label label0 = new Label(0,  j + 1, dto.getContractNo() == null ? "" : dto.getContractNo());
						Label label1 = new Label(1,  j + 1, dto.getResourceNames() == null ? "" : dto.getResourceNames());
						Label label2 = new Label(2,  j + 1, dto.getFeeItemTypeName() == null ? "" : dto.getFeeItemTypeName());
						Label label3 = new Label(3,  j + 1, dto.getFeeItemName() == null ? "" : dto.getFeeItemName());
						Label label4 = new Label(4,  j + 1, dto.getTimeLimitString() == null ? "" : dto.getTimeLimitString());
						Label label5 = new Label(5,  j + 1, dto.getStartTimeString() == null ? "" : dto.getStartTimeString());
						Label label6 = new Label(6,  j + 1, dto.getEndTimeString() == null ? "" : dto.getEndTimeString());
						Label label7 = new Label(7,  j + 1, dto.getAccountPayableString() == null ? "" : dto.getAccountPayableString());
						Label label8 = new Label(8,  j + 1, dto.getAccountPayedString() == null ? "" : dto.getAccountPayedString());
						//将单元格加入表格
						sheet.addCell(label0);
						sheet.addCell(label1);
						sheet.addCell(label2);
						sheet.addCell(label3);
						sheet.addCell(label4);
						sheet.addCell(label5);
						sheet.addCell(label6);
						sheet.addCell(label7);
						sheet.addCell(label8);
						}
					}
				wwb.write();// 将数据写入工作簿
			}
			wwb.close();// 关闭				
		} catch (Exception e1) {
			e1.printStackTrace();
		} finally {
			try {
				ouputStream.flush();
				ouputStream.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return null;
	}
	
	public Map<String, Object> getParameters(FinanceFeeParamBean paramBean) throws BizException {
		Map<String, Object> params = new HashMap<String, Object>();
/*		//简单搜索
		if ("1".equalsIgnoreCase(paramBean.getQueryType())){
			try {
				String conditionValue = StringUtils.isEmpty(paramBean.getConditionValue()) ? "" : java.net.URLDecoder.decode(paramBean.getConditionValue(), "UTF-8");
				conditionValue = conditionValue.trim();
				if (FinanceFeeController.SIMPLE_QUERY_CONDITIONTYPE_CONTRACT.equalsIgnoreCase(paramBean.getConditionType())){
					params.put("contractNoLike", conditionValue);
				}else if (FinanceFeeController.SIMPLE_QUERY_CONDITIONTYPE_RESOURCE.equalsIgnoreCase(paramBean.getConditionType())){
					params.put("resourceNameLike", conditionValue);
				}else if (FinanceFeeController.SIMPLE_QUERY_CONDITIONTYPE_FEEITEM.equalsIgnoreCase(paramBean.getConditionType())){
					params.put("feeItemNameLike", conditionValue);
				}
			} catch (UnsupportedEncodingException e) {
				logger.info("queryFeeRecord with ex : {} ", e);
			}
		}else{
			//高级搜索
			//款项类型
			if (!StringUtils.isEmpty(paramBean.getFundType())){
				params.put("fundType", paramBean.getFundType());
			}
			//费项类型id
			params.put("feeItemTypeId", paramBean.getFeeItemTypeId());
			Date start = DateUtil.getStartOfDay(paramBean.getStartTime(), 1) ;
			if ( start != null){
				//缴费期限起始日期
				params.put("limitStartTime", DateUtil.sdfDateTime.format(start));
			}
			Date end = DateUtil.getEndOfDay(paramBean.getEndTime(), 1) ;
			if ( end != null){
				//缴费期限结束日期
				params.put("limitEndTime", DateUtil.sdfDateTime.format(end));
			}
		}*/
		
		try {
			/*合同编号/租赁资源/费项名称*/ 
			String conditionValue = StringUtils.isEmpty(paramBean.getConditionValue()) ? "" : java.net.URLDecoder.decode(paramBean.getConditionValue(), "UTF-8");
			conditionValue = conditionValue.trim();
			if (FinanceFeeController.SIMPLE_QUERY_CONDITIONTYPE_CONTRACT.equalsIgnoreCase(paramBean.getConditionType())){
				params.put("contractNoLike", conditionValue);
			}else if (FinanceFeeController.SIMPLE_QUERY_CONDITIONTYPE_RESOURCE.equalsIgnoreCase(paramBean.getConditionType())){
				params.put("resourceNameLike", conditionValue);
			}else if (FinanceFeeController.SIMPLE_QUERY_CONDITIONTYPE_FEEITEM.equalsIgnoreCase(paramBean.getConditionType())){
				params.put("feeItemNameLike", conditionValue);
			}
			//款项类型
			if (!StringUtils.isEmpty(paramBean.getFundType())){
				params.put("fundType", paramBean.getFundType());
			}
			//费项类型id
			params.put("feeItemTypeId", paramBean.getFeeItemTypeId());
			Date start = DateUtil.getStartOfDay(paramBean.getStartTime(), 1) ;
			if ( start != null){
				//缴费期限起始日期
				params.put("limitStartTime", DateUtil.sdfDateTime.format(start));
			}
			Date end = DateUtil.getEndOfDay(paramBean.getEndTime(), 1) ;
			if ( end != null){
				//缴费期限结束日期
				params.put("limitEndTime", DateUtil.sdfDateTime.format(end));
			}
			//市场
			if (getCurrentMarket() != null){
				params.put("marketId", getCurrentMarket().getId());
			}else{
				params.put("marketId", -1);
			}
		} catch (UnsupportedEncodingException e) {
			logger.info("queryFeeRecord with ex : {} ", e);
		}
		return params;
	}
}
