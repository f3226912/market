package cn.gdeng.market.lease.controller.lease.finance;

import java.io.IOException;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
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

import cn.gdeng.market.dto.bean.GuDengPage;
import cn.gdeng.market.dto.lease.contract.ContractMainDTO;
import cn.gdeng.market.dto.lease.finance.FinanceFeeRecordDTO;
import cn.gdeng.market.dto.lease.resources.MarketAreaDTO;
import cn.gdeng.market.dto.lease.resources.MarketBuildingUnitDTO;
import cn.gdeng.market.dto.lease.resources.MarketUnitFloorDTO;
import cn.gdeng.market.dto.lease.settings.MarketExpenditureDTO;
import cn.gdeng.market.dto.lease.settings.MarketExpenditureStandardDTO;
import cn.gdeng.market.dto.lease.settings.MarketResourceTypeDTO;
import cn.gdeng.market.dto.lease.settings.PrintSettingDTO;
import cn.gdeng.market.dto.lease.settings.SysContractRemindSettingDTO;
import cn.gdeng.market.entity.lease.finance.FinanceFeeReceivedEntity;
import cn.gdeng.market.entity.lease.finance.FinanceFeeRecordEntity;
import cn.gdeng.market.enums.Const;
import cn.gdeng.market.enums.ContractRemindTimeTypeEnum;
import cn.gdeng.market.enums.ContractRemindTypeEnum;
import cn.gdeng.market.enums.FinEffecStatusEnum;
import cn.gdeng.market.enums.FinFundTypeEnum;
import cn.gdeng.market.enums.FinRemedyStatusEnum;
import cn.gdeng.market.enums.FinStatusEnum;
import cn.gdeng.market.enums.MsgCons;
import cn.gdeng.market.exception.BizException;
import cn.gdeng.market.lease.bean.AjaxResult;
import cn.gdeng.market.lease.controller.base.BaseController;
import cn.gdeng.market.lease.controller.lease.finance.querybean.FinanceFeeParamBean;
import cn.gdeng.market.lease.controller.lease.finance.querybean.PrintParamBean;
import cn.gdeng.market.lease.util.PrintConvertDataUtil;
import cn.gdeng.market.service.lease.contract.ContractManageService;
import cn.gdeng.market.service.lease.finance.FinanceFeeReceivedService;
import cn.gdeng.market.service.lease.finance.FinanceFeeService;
import cn.gdeng.market.service.lease.resources.MarketAreaService;
import cn.gdeng.market.service.lease.resources.MarketBuildingResourceService;
import cn.gdeng.market.service.lease.resources.MarketUnitFloorService;
import cn.gdeng.market.service.lease.settings.MarketExpenditureService;
import cn.gdeng.market.service.lease.settings.MarketExpenditureStandardService;
import cn.gdeng.market.service.lease.settings.MarketResourceTypeService;
import cn.gdeng.market.service.lease.settings.PrintService;
import cn.gdeng.market.service.lease.settings.PrintSetService;
import cn.gdeng.market.service.lease.settings.SysContractRemindSettingService;
import cn.gdeng.market.util.DateUtil;
import cn.gdeng.market.util.PropertyUtil;
import jxl.Workbook;
import jxl.write.Label;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;

@Controller
public class FinanceFeeController extends BaseController {

	private Logger logger = LoggerFactory.getLogger(FinanceFeeController.class);

	@Autowired
	private FinanceFeeService financeFeeService;
	
	@Autowired
	private FinanceFeeReceivedService financeFeeReceivedService;

	@Resource
	private ContractManageService contractManageService;
	
	@Autowired
	private MarketExpenditureService marketExpenditureService;
	
	@Autowired
	private MarketExpenditureStandardService marketExpenditureStandardService ;
	
	@Autowired
	private MarketResourceTypeService marketResourceTypeService ;
	
	@Autowired
	private MarketAreaService marketAreaService ;
	
	@Autowired
	private MarketUnitFloorService marketUnitFloorService ;
	
	@Autowired
	private MarketBuildingResourceService marketBuildingResourceService ;
	
	@Resource
	private PrintSetService printSetService;
	
	@Resource
	private PrintService printService;
	
	@Resource
	private PropertyUtil propertyUtil;
	
	@Resource
	private PrintConvertDataUtil printConvertDataUtil;
	
//	@Autowired
//	private SysUserService sysUserService ;
	
	@Resource	
	private SysContractRemindSettingService sysContractRemindSettingService;
	
	public static final String SIMPLE_QUERY_CONDITIONTYPE_CONTRACT = "1";
	public static final String SIMPLE_QUERY_CONDITIONTYPE_RESOURCE = "2";
	public static final String SIMPLE_QUERY_CONDITIONTYPE_FEEITEM = "3";

	@RequestMapping("financeShould/Index")
	public ModelAndView shouldIndex(ModelAndView mv) throws BizException {
		mv.setViewName("finance/fund/should/shouldRecList");
		return mv;
	}

/*	*//**
	 * 查询应收款详情
	 * @param mv
	 * @return
	 * @throws BizException
	 *//*
	@ResponseBody
	@RequestMapping("financeShould/receiveDetail")
	public AjaxResult<Map<String, Object>> shouldReceiveDetail(FinanceFeeRecordEntity entity) throws BizException {
		AjaxResult<Map<String, Object>> result = new AjaxResult<Map<String, Object>>();
		//根据合同编号以及版本号查询合同信息
		Map<String, Object> params = new HashMap<String, Object>();
		//查询收款记录
		params.put("id", entity.getId());
		FinanceFeeRecordDTO financeFee = financeFeeService.queryFeeRecordById(params);
		//查询该条收款记录对应的合同信息
		params.put("id", financeFee.getContractVersion());
		ContractMainDTO contract = contractManageService.showMainInfo(params);
		Map<String, Object> res = new HashMap<String, Object>();
		res.put("contract", contract);
		res.put("financeFee", financeFee);
		result.setResult(res);
		return result;
	}	*/
	
	/**
	 * 跳转财务应收款详情
	 * @param mv
	 * @param id
	 * @return
	 * @throws BizException
	 */
	@RequestMapping("financeShould/needReceivedDetail")
	public ModelAndView needReceivedDetail(ModelAndView mv, String id) throws BizException {
		
		//查询实收款记录
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("id", id);
		FinanceFeeRecordDTO financeFee = financeFeeService.queryFeeRecordById(params);
		
		//查询该条收款记录对应的合同信息
		params.put("id", financeFee.getContractVersion());
		ContractMainDTO contract = contractManageService.showMainInfo(params);
		try {
			financeFee.setAmount(financeFee.getAccountPayable() + financeFee.getAccountRebate());
			FinanceSupport.typeConvert(contract, null);
			FinanceSupport.typeConvert(financeFee, null);
		} catch (IllegalArgumentException e) {
			//throw new BizException(code, msg);
			logger.info("typeConvert with IllegalArgumentException : {} ", e);
		} catch (IllegalAccessException e) {
			logger.info("typeConvert with IllegalAccessException : {} ", e);
		} catch (NoSuchFieldException e) {
			logger.info("typeConvert with NoSuchFieldException : {} ", e);
		} catch (SecurityException e) {
			logger.info("typeConvert with SecurityException : {} ", e);
		}
		mv.addObject("financeFee", financeFee);
		mv.addObject("contract", contract);
		mv.addObject("sysUser", getSysUser().getName());
		mv.addObject("sysDate", FinanceSupport.DATETIME_FORMATTER.format(new Date()));
		mv.setViewName("finance/fund/should/needRecShould");
		
/*		//经过与产品沟通, 已经收过款的应收款不在"财务收款-应收款列表"中展示, 而退款、临时收款只有收款操作且
		//同时生成一条状态为已收的应收款记录和一条实收款记录, 所以退款、临时收款不会在"财务收款-应收款列表"中展示
		if (FinFundTypeEnum.BACK.getCode().equalsIgnoreCase(financeFee.getFundType())){
			//跳转到应收款-退款详情页面
			mv.setViewName("finance/fund/should/needRecBack");
		}else if (FinFundTypeEnum.TEMP.getCode().equalsIgnoreCase(financeFee.getFundType())){
			//跳转到应收款-临时收款详情页面
			mv.setViewName("finance/fund/should/needRecTemp");
		}else{
			//跳转normal-应收款详情页面
			mv.setViewName("finance/fund/should/needRecShould");
		}*/
		return mv;
	}
	
	/**
	 * 跳转到新增临时收款界面
	 * @param mv
	 * @return
	 * @throws BizException
	 */
	@RequestMapping("financeShould/toNewTempMoney")
	public ModelAndView toNewTempMoney(ModelAndView mv) throws BizException {
		
		mv.addObject("sysUser", getSysUser().getName());
		mv.addObject("sysDate", FinanceSupport.DATETIME_FORMATTER.format(new Date()));
		mv.setViewName("finance/fund/should/newTempMoney");
		return mv;
	}
	
	/**
	 * 跳转到新增退款界面
	 * @param mv
	 * @return
	 * @throws BizException
	 */
	@RequestMapping("financeShould/toNewBackMoney")
	public ModelAndView toNewBackMoney(ModelAndView mv) throws BizException {
		Map<String, Object> params = new HashMap<String, Object>();
		if (getCurrentMarket() != null) {
			params.put("marketId", getCurrentMarket().getId());
		}else{
			params.put("marketId", -1);
		}
		// 查询退款费项(id固定为2的费项)
		params.put("parentId", 2);
		MarketExpenditureDTO expenditure = marketExpenditureService.queryByMarketIdParentId(params);
		mv.addObject("expenditure", expenditure);
		mv.addObject("sysUser", getSysUser().getName());
		mv.addObject("sysDate", FinanceSupport.DATETIME_FORMATTER.format(new Date()));
		mv.setViewName("finance/fund/should/newBackMoney");
		return mv;
	}
	
	/**
	 * 跳转财务收款预警界面
	 * @param mv
	 * @return
	 * @throws BizException
	 */
	@RequestMapping("financeShould/toFinearlyWaring")
	public ModelAndView toFinearlyWaring(ModelAndView mv) throws BizException {
		mv.setViewName("finance/warning/financeEarlyWarning");
		return mv;
	}
	
	/**
	 * 应收款预警列表
	 * @return
	 * @throws BizException
	 */
	@ResponseBody
	@RequestMapping("financeShould/finearlyWaring")
	public AjaxResult<GuDengPage<FinanceFeeRecordDTO>> finearlyWaring(FinanceFeeParamBean paramBean) throws BizException{
		logger.info("查询财务预警列表入参:"+JSON.toJSONString(paramBean));
		AjaxResult<GuDengPage<FinanceFeeRecordDTO>> result = new AjaxResult<GuDengPage<FinanceFeeRecordDTO>>();

		Map<String, Object> settingParams = new HashMap<String, Object>();
		settingParams.put("type", ContractRemindTypeEnum.MONEY.getCode());
		//市场
		if (getCurrentMarket() != null){
			settingParams.put("marketId", getCurrentMarket().getId());
		}else{
			settingParams.put("marketId", -1);
		}
		SysContractRemindSettingDTO dto = sysContractRemindSettingService.queryContractRemindSettingByMarket(settingParams);
		//分页结果
		GuDengPage<FinanceFeeRecordDTO> page = getPageInfoByRequest(paramBean);
		if (dto == null){
			List<FinanceFeeRecordDTO> recordList = new ArrayList<FinanceFeeRecordDTO>();
			page.setRecordList(recordList);
			result.setResult(page);
			return result;
		}
		Map<String, Object> params = new HashMap<String, Object>();
		if (!StringUtils.isEmpty(paramBean.getFundType())){
			params.put("fundType", paramBean.getFundType());
		}
		if (ContractRemindTimeTypeEnum.MONTH.getCode() == dto.getRemindTimeType()){
			params.put("interForMonth", -dto.getRemindTime().intValue());
		}else {
			params.put("interForDay", -dto.getRemindTime().intValue());
		}
		//市场id
		if (getCurrentMarket() != null){
			params.put("marketId", getCurrentMarket().getId());
		}else{
			params.put("marketId", -1);
		}
		//收款状态
		params.put("status", FinStatusEnum.NOT_YET.getCode());
		//费项类型id
		params.put("feeItemTypeId", paramBean.getFeeItemTypeId());
		//计费起始日期
		params.put("startTime", paramBean.getStartTime());
		//计费结束日期
		params.put("endTime", paramBean.getEndTime());
		int total = financeFeeService.getFeeRecordCount(params);
		page.setTotal(total);
		params.put("startRow", page.getOffset());
		params.put("endRow", page.getPageSize());
		logger.info("财务预警查询参数:"+JSON.toJSONString(params));
		List<FinanceFeeRecordDTO> recordList = financeFeeService.queryFeeRecord(params);
		page.setRecordList(recordList);
		result.setResult(page);
		return result;
	}
	
	/**
	 * 应收款分页列表
	 * @return
	 * @throws BizException
	 */
	@ResponseBody
	@RequestMapping("financeShould/queryFeeRecord")
	public AjaxResult<GuDengPage<FinanceFeeRecordDTO>> queryFeeRecord(FinanceFeeParamBean paramBean) throws BizException{
		
		logger.info("查询财务收款列表入参:"+JSON.toJSONString(paramBean));
		AjaxResult<GuDengPage<FinanceFeeRecordDTO>> result = new AjaxResult<GuDengPage<FinanceFeeRecordDTO>>();
		//设置查询参数
		Map<String, Object> params = getParameters(paramBean);
		//排序
		params.put("specialSort", "1");
		//收款状态
		params.put("status", FinStatusEnum.NOT_YET.getCode());
		
		int total = financeFeeService.getFeeRecordCount(params);
		
		GuDengPage<FinanceFeeRecordDTO> page = getPageInfoByRequest(paramBean);
		page.setTotal(total);
		params.put("startRow", page.getOffset());
		params.put("endRow", page.getPageSize());
		List<FinanceFeeRecordDTO> recordList = financeFeeService.queryFeeRecord(params);
		page.setRecordList(recordList);
		result.setResult(page);
		return result;
	}
	
	/**
	 * 应收款-收款操作
	 * @return
	 * @throws BizException
	 */
	@ResponseBody
	@RequestMapping("financeShould/receiveMoney")
	public AjaxResult<String> receiveMoney(String id, String accountPayed, String reciever, String phone,
			String remark) throws BizException {
		
		AjaxResult<String> result = new AjaxResult<String>();
		//查询应收款记录
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("id", id);
		FinanceFeeRecordDTO old = financeFeeService.queryFeeRecordById(params);
		//更新原应收款为已收状态
		params.put("status", FinStatusEnum.ALREADY_REC.getCode());
		params.put("status", FinStatusEnum.ALREADY_REC.getCode());
		financeFeeService.updateFeeRecord(params);
		Integer marketId = 0;
		//市场
		if (getCurrentMarket() != null){
			marketId = getCurrentMarket().getId();
		}else{
			marketId = -1;
		}
		//生成实收款记录
		FinanceFeeReceivedEntity financeFeeReceived = FinanceSupport
				.generateFinanceFeeReceived(old, accountPayed, reciever, phone, remark, getUserIdStr(), marketId);
		financeFeeReceivedService.insertFeeReceivedRecord(financeFeeReceived);
		
		//生成新的应收款记录
		Double differ = Double.valueOf(old.getAccountPayable()) - Double.valueOf(accountPayed);
		if (differ.longValue() > 0){
			//插入财务收款记录
			FinanceFeeRecordEntity feeRecord = FinanceSupport.generateFinanceFeeRecord(old, getUserIdStr(), differ) ;
			financeFeeService.insertFeeRecord(feeRecord);
		}
		
		return result;
	}
	
	/**
	 * 新增临时收款(分别生成一条应收款和实收款)
	 * @return
	 * @throws BizException
	 */
	@ResponseBody
	@RequestMapping("financeShould/newTempItem")
	public AjaxResult<String> newTempItem(String contractNo, String contractVersion, String feeItemId, String freightBasisId,
			String accountPayed, String reciever, String phone, String remark) throws BizException {
		
		AjaxResult<String> result = new AjaxResult<String>();
		Integer marketId = 0;
		if (getCurrentMarket() == null){
			result.setIsSuccess(false);
			result.setCodeMsg(MsgCons.C_30006, MsgCons.M_30006);
			return result;
		}else{
			marketId = getCurrentMarket().getId();
		}
		
		//应收款记录
		FinanceFeeRecordEntity entity = new FinanceFeeRecordEntity();
		//实收款记录
		FinanceFeeReceivedEntity received = new FinanceFeeReceivedEntity();
		// 市场id
		entity.setMarketId(marketId);
		received.setMarketId(marketId);
		//合同编号、版本号
		entity.setContractNo(contractNo);
		entity.setContractVersion(Integer.valueOf(contractVersion));
		received.setContractNo(contractNo);
		received.setContractVersion(Integer.valueOf(contractVersion));
		//退款费项
		entity.setFeeItemId(Integer.valueOf(feeItemId));
		received.setFeeItemId(Integer.valueOf(feeItemId));
		//根据费项id查询费项类型Id
		MarketExpenditureDTO expenditureDTO = marketExpenditureService.getById(Integer.valueOf(feeItemId));
		entity.setFeeItemTypeId(expenditureDTO.getExpType().toString());
		received.setFeeItemTypeId(expenditureDTO.getExpType().toString());
		//计费标准id
		entity.setFreightBasisId(Integer.valueOf(freightBasisId));
		received.setFreightBasisId(Integer.valueOf(freightBasisId));
		//缴费期限
		entity.setTimeLimit(null);
		received.setTimeLimit(null);
		//计费起始期限
		entity.setStartTime(null);
		entity.setEndTime(null);
		received.setStartTime(null);
		received.setEndTime(null);
		//应付金额
		entity.setAccountPayable(Double.valueOf(accountPayed));
		received.setAccountPayable(Double.valueOf(accountPayed));
		//实收金额
		received.setAccountPayed(Double.valueOf(accountPayed));
		//收款状态
		entity.setStatus(FinStatusEnum.ALREADY_REC.getCode());
		//删除状态
		entity.setIsDeteled(FinEffecStatusEnum.NO.getCode());
		//款项类型-临时收款项
		entity.setFundType(FinFundTypeEnum.TEMP.getCode());
		received.setFundType(FinFundTypeEnum.TEMP.getCode());
		//是否补交记录
		entity.setIsRemedy(FinRemedyStatusEnum.NO.getCode());
		//收款说明
		received.setRemark(remark);
		//手机号
		received.setPhone(phone);
		//收款人
		received.setPayer(reciever);
		//经办人
		received.setAgent(getUserIdStr());
		Date now = new Date();
		//收款日期
		received.setReceiveDate(now);
		//创建/更新时间
		entity.setCreateTime(now);
		entity.setCreateUserId(getUserIdStr());
		entity.setUpdateTime(now);
		entity.setUpdateUserId(getUserIdStr());
		received.setCreateTime(now);
		received.setCreateUserId(getUserIdStr());
		received.setUpdateTime(now);
		received.setUpdateUserId(getUserIdStr());
		//应收款记录
		financeFeeService.insertFeeRecord(entity);
		//实收款记录
		financeFeeReceivedService.insertFeeReceivedRecord(received);
		return result;
	}	
	
	/**
	 * 新增退款
	 * @return
	 * @throws BizException
	 */
	@ResponseBody
	@RequestMapping("financeShould/newReturnItem")
	public AjaxResult<String> newReturnItem(String contractNo, String contractVersion, String feeItemId,  
			String accountPayed, String reciever, String phone, String remark) throws BizException {
		
		AjaxResult<String> result = new AjaxResult<String>();
		Integer marketId = 0;
		if (getCurrentMarket() == null){
			result.setIsSuccess(false);
			result.setCodeMsg(MsgCons.C_30006, MsgCons.M_30006);
			return result;
		}else {
			marketId = getCurrentMarket().getId();
		}
		//应收款记录
		FinanceFeeRecordEntity entity = new FinanceFeeRecordEntity();
		//实收款记录
		FinanceFeeReceivedEntity received = new FinanceFeeReceivedEntity();
		// 市场id
		entity.setMarketId(marketId);
		received.setMarketId(marketId);
		//合同编号、版本号
		entity.setContractNo(contractNo);
		entity.setContractVersion(Integer.valueOf(contractVersion));
		received.setContractNo(contractNo);
		received.setContractVersion(Integer.valueOf(contractVersion));
		//退款费项id
		entity.setFeeItemId(Integer.valueOf(feeItemId));
		received.setFeeItemId(Integer.valueOf(feeItemId));
		//根据费项id查询费项类型Id
		MarketExpenditureDTO expenditureDTO = marketExpenditureService.getById(Integer.valueOf(feeItemId));
		entity.setFeeItemTypeId(expenditureDTO.getExpType().toString());
		received.setFeeItemTypeId(expenditureDTO.getExpType().toString());
		//缴费期限
		entity.setTimeLimit(null);
		received.setTimeLimit(null);
		//计费起始期限
		entity.setStartTime(null);
		entity.setEndTime(null);
		received.setStartTime(null);
		received.setEndTime(null);
		//收款状态
		entity.setStatus(FinStatusEnum.ALREADY_REC.getCode());
		//删除状态
		entity.setIsDeteled(FinEffecStatusEnum.NO.getCode());
		//应付金额
		entity.setAccountPayable(-Double.valueOf(accountPayed));
		received.setAccountPayable(-Double.valueOf(accountPayed));
		//实收金额
		received.setAccountPayed(-Double.valueOf(accountPayed));
		//款项类型-退款项
		entity.setFundType(FinFundTypeEnum.BACK.getCode());
		received.setFundType(FinFundTypeEnum.BACK.getCode());
		//是否补交记录
		entity.setIsRemedy(FinRemedyStatusEnum.NO.getCode());
		//收款说明
		received.setRemark(remark);
		//手机号
		received.setPhone(phone);
		//收款人
		received.setPayer(reciever);
		//经办人
		received.setAgent(getUserIdStr());
		Date now = new Date();
		//收款日期
		received.setReceiveDate(now);
		//创建、更新时间
		entity.setCreateTime(now);
		entity.setCreateUserId(getUserIdStr());
		entity.setUpdateTime(now);
		entity.setUpdateUserId(getUserIdStr());
		received.setCreateTime(now);
		received.setCreateUserId(getUserIdStr());
		received.setUpdateTime(now);
		received.setUpdateUserId(getUserIdStr());
		//应收款记录
		financeFeeService.insertFeeRecord(entity);
		//实收款记录
		financeFeeReceivedService.insertFeeReceivedRecord(received);
		return result;
	}
	
	/**
	 * 获取费项及其计费标准
	 * @param mv
	 * @return
	 * @throws BizException
	 */
	@ResponseBody
	@RequestMapping("financeShould/getFeeItemAndStandards")
	public AjaxResult<List<MarketExpenditureDTO>> getFeeItemAndStandards(ModelAndView mv) throws BizException {
		
		AjaxResult<List<MarketExpenditureDTO>> result = new AjaxResult<List<MarketExpenditureDTO>>();
		Map<String,Object> map = new HashMap<>();
		if (getCurrentMarket() != null){
			map.put("marketId", getCurrentMarket().getId());
		}else{
			map.put("marketId", -1);
		}
		//临时性费项
		map.put("expType", 4);
		map.put("status", 1);
		List<MarketExpenditureDTO> expList = marketExpenditureService.getExpList(map);
		for (MarketExpenditureDTO item : expList){
			List<MarketExpenditureStandardDTO> expStandards = marketExpenditureStandardService.getByExpId(item.getId());
			item.setExpStandards(expStandards);
		}
		result.setResult(expList);
		return result;
	}
	
	/**
	 * 获取市场资源类型
	 * @param mv
	 * @return
	 * @throws BizException
	 */
	@ResponseBody
	@RequestMapping("financeShould/getMarketResourceType")
	public AjaxResult<List<MarketResourceTypeDTO>> getMarketResourceType(ModelAndView mv) throws BizException {
		
		AjaxResult<List<MarketResourceTypeDTO>> result = new AjaxResult<List<MarketResourceTypeDTO>>();
		Map<String,Object> map = new HashMap<>();
		//市场
		if (getCurrentMarket() != null){
			map.put("marketId", getCurrentMarket().getId());
		}else{
			map.put("marketId", -1);
		}
		map.put("status", 1);
		List<MarketResourceTypeDTO> marketResourceType = marketResourceTypeService.getMarketResourceType(map);
		result.setResult(marketResourceType);
		return result;
	}
	
	/**
	 * 根据市场，查找出所有的 区域+楼栋
	 * @param mv
	 * @return
	 * @throws BizException
	 */
	@ResponseBody
	@RequestMapping("financeShould/getAreaAndBuilding")
	public AjaxResult<List<MarketAreaDTO>> getAreaAndBuilding(ModelAndView mv) throws BizException {
		
		AjaxResult<List<MarketAreaDTO>> result = new AjaxResult<List<MarketAreaDTO>>();
		Map<String,Object> map = new HashMap<>();
		//市场
		if (getCurrentMarket() != null){
			map.put("marketId", getCurrentMarket().getId());
		}else{
			map.put("marketId", -1);
		}
		map.put("status", 1);
		List<MarketAreaDTO> areaAndBuilding = marketAreaService.getAreaAndBuilding(map);
		result.setResult(areaAndBuilding);
		return result;
	}
	
	/**
	 * 根据楼栋查找所有楼层，按照楼层倒序
	 * @param mv
	 * @param buildingId
	 * @return
	 * @throws BizException
	 */
	@ResponseBody
	@RequestMapping("financeShould/getFloor")
	public AjaxResult<List<MarketUnitFloorDTO>> getFloor(ModelAndView mv, String buildingId) throws BizException {
		
		AjaxResult<List<MarketUnitFloorDTO>> result = new AjaxResult<List<MarketUnitFloorDTO>>();
		Map<String,Object> map = new HashMap<>();
		map.put("buildingId", buildingId);
		map.put("status", 1);
		List<MarketUnitFloorDTO> floor = marketUnitFloorService.getFloor(map);
		result.setResult(floor);
		return result;
	}
	
	/**
	 * 获取单元-资源
	 * @param mv
	 * @param buildingId
	 * @param floorId
	 * @param resourceTypeId
	 * @return
	 * @throws BizException
	 */
	@ResponseBody
	@RequestMapping("financeShould/getUnitAndResource")
	public AjaxResult<List<MarketBuildingUnitDTO>> getUnitAndResource(ModelAndView mv, String buildingId,
			String floorId, String resourceTypeId) throws BizException {
		
		AjaxResult<List<MarketBuildingUnitDTO>> result = new AjaxResult<List<MarketBuildingUnitDTO>>();
		Map<String,Object> map = new HashMap<>();
		map.put("budingId", buildingId);
		map.put("status", 1);
		map.put("floorId",floorId);
		map.put("resourceTypeId",resourceTypeId);
		//1 待放租  2  待租   3  已租
		map.put("leaseStatus",3);
		List<MarketBuildingUnitDTO> unitAndResource = marketBuildingResourceService.getUnitAndResource(map);
		result.setResult(unitAndResource);
		return result;
	}
	
	/**
	 * 获取单元-资源 待租的 和已租的
	 * @return
	 * @throws BizException
	 */
	@ResponseBody
	@RequestMapping("financeShould/getSpareUnitAndResource")
	public AjaxResult<List<MarketBuildingUnitDTO>> getSpareUnitAndResource(String buildingId,
			String floorId, String resourceTypeId) throws BizException {
		AjaxResult<List<MarketBuildingUnitDTO>> result = new AjaxResult<List<MarketBuildingUnitDTO>>();
		Map<String,Object> map = new HashMap<>();
		map.put("budingId", buildingId);
		map.put("floorId",floorId);
		map.put("resourceTypeId",resourceTypeId);
		map.put("status", 1);
		map.put("extendleaseStatus", "OK");//不为空即为 待租的 和已租的
		//map.put("leaseStatus",MarketResourceLeaseStatusEnum.WAIT_FOR_RENT.getStatus());
		List<MarketBuildingUnitDTO> unitAndResource = marketBuildingResourceService.getUnitAndResource(map);
		result.setResult(unitAndResource);
		return result;
	}
	
	/**
	 * 根据资源id查找合同信息
	 * @param mv
	 * @param buildingId
	 * @return
	 * @throws BizException
	 */
	@ResponseBody
	@RequestMapping("financeShould/findSpecialContract")
	public AjaxResult<Map<String,Object>> findSpecialContract(ModelAndView mv, String resourceId) throws BizException {
		
		AjaxResult<Map<String,Object>> result = new AjaxResult<Map<String,Object>>();
		Map<String,Object> params = new HashMap<>();
		params.put("resourceId", resourceId);
		ContractMainDTO contract = contractManageService.findSpecialContract(params);
		try {
			FinanceSupport.typeConvert(contract, null);
		} catch (IllegalArgumentException e) {
			logger.info("findSpecialContract with IllegalArgumentException : {} ", e);
		} catch (IllegalAccessException e) {
			logger.info("findSpecialContract with IllegalAccessException : {} ", e);
		} catch (NoSuchFieldException e) {
			logger.info("findSpecialContract with NoSuchFieldException : {} ", e);
		} catch (SecurityException e) {
			logger.info("findSpecialContract with SecurityException : {} ", e);
		}
		Map<String,Object> res = new HashMap<>();
		if (contract != null){
			res.put("status", 1);
			res.put("contract", contract);
		}else{
			res.put("status", 0);
		}
		result.setResult(res);
		return result;
	}
	
	/**
	 * 打印应收款
	 * @param settingId
	 * @param feeId
	 * @return
	 * @throws BizException 
	 */
	@RequestMapping("financeShould/printShould")
	public ModelAndView printShould(int settingId, int feeId, PrintParamBean paramBean, ModelAndView mv) throws BizException {
		Map<String, Object> params = new HashMap<String, Object>();
		//查询应收款记录
		params.put("id", feeId);
		FinanceFeeRecordDTO financeFee = financeFeeService.queryFeeRecordById(params);
		
		//查询该条收款记录对应的合同信息
		params.put("id", financeFee.getContractVersion());
		ContractMainDTO contract = contractManageService.showMainInfo(params);
		
		// 数据转换
		Map<String, String> dataMap = new HashMap<String, String>();
		printConvertDataUtil.convertContractMainToMap(contract, dataMap);
		printConvertDataUtil.convertFeeRecordToMap(financeFee, dataMap);
		//保存页面填写的数据
//		SysUserDTO userDTO = sysUserService.getById(Integer.parseInt(getSysUser()));
		//本次实收金额
		dataMap.put("${accountPayed}", paramBean.getAccountPayed());
		//交款人
		dataMap.put("${payer}", paramBean.getReciever());
		//手机号
		dataMap.put("${payerPhone}", paramBean.getPhone());
		//经办人
		dataMap.put("${feeHandler}", getSysUser().getName());
		//经办时间
		dataMap.put("${feeReceiveDate}", paramBean.getAgentTime());
		//收款说明
		dataMap.put("${feeRemark}", paramBean.getRemark());
		
		String pdfName = printService.convertDocToPdf(settingId, dataMap);
		String pdfUrl = propertyUtil.getValue("printingFileHost") + pdfName;
		mv.addObject("fileUrl", pdfUrl);
		mv.setViewName("settings/print/preView");
		return mv;
	}
	
	/**
	 * 打印新增临时收款
	 * @param settingId
	 * @param feeId
	 * @return
	 * @throws BizException 
	 */
	@RequestMapping("financeShould/printTemp")
	public ModelAndView printTemp(int settingId,  PrintParamBean paramBean, ModelAndView mv) throws BizException {
		Map<String, Object> params = new HashMap<String, Object>();
		//查询该条收款记录对应的合同信息
		params.put("id", paramBean.getContractVersion());
		ContractMainDTO contract = contractManageService.showMainInfo(params);
		
		// 数据转换
		Map<String, String> dataMap = new HashMap<String, String>();
		
		printConvertDataUtil.convertContractMainToMap(contract, dataMap);
		
		//保存页面填写的数据
		//款项名称
		dataMap.put("${feeItemName}", paramBean.getFeeItemName());
		//本次实收金额
		dataMap.put("${accountPayed}", paramBean.getAccountPayed());
		//交款人
		dataMap.put("${payer}", paramBean.getReciever());
		//手机号
		dataMap.put("${payerPhone}", paramBean.getPhone());
		//经办人
//		dataMap.put("${feeHandler}", paramBean.getAgent());
		dataMap.put("${feeHandler}", getSysUser().getName());
		//经办时间
		dataMap.put("${feeReceiveDate}", paramBean.getAgentTime());
		//收款说明
		dataMap.put("${feeRemark}", paramBean.getRemark());
		
		String pdfName = printService.convertDocToPdf(settingId, dataMap);
		String pdfUrl = propertyUtil.getValue("printingFileHost") + pdfName;
		mv.addObject("fileUrl", pdfUrl);
		mv.setViewName("settings/print/preView");
		return mv;
	}
	
	/**
	 * 打印新增退款
	 * @param settingId
	 * @param feeId
	 * @return
	 * @throws BizException 
	 */
	@RequestMapping("financeShould/printBack")
	public ModelAndView printBack(int settingId, PrintParamBean paramBean, ModelAndView mv) throws BizException {
		PrintSettingDTO printSettingDTO = printSetService.queryById(settingId);
		if(printSettingDTO == null){
			throw new BizException(MsgCons.C_20000, "套打设置【"+ settingId +"】不存在");
		}
		
		Map<String, Object> params = new HashMap<String, Object>();
//		//查询该条收款记录对应的合同信息
		params.put("id", paramBean.getContractVersion());
		ContractMainDTO contract = contractManageService.showMainInfo(params);
		// 数据转换
		Map<String, String> dataMap = new HashMap<String, String>();
		printConvertDataUtil.convertContractMainToMap(contract, dataMap);
		
		//保存页面填写的数据
		//款项名称
		dataMap.put("${feeItemName}", "退款");
		//本次实收金额
		dataMap.put("${accountPayed}", paramBean.getAccountPayed());
		//交款人
		dataMap.put("${payer}", paramBean.getReciever());
		//手机号
		dataMap.put("${payerPhone}", paramBean.getPhone());
		//经办人
		dataMap.put("${feeHandler}", getSysUser().getName());
		//经办时间
		dataMap.put("${feeReceiveDate}", paramBean.getAgentTime());
		//收款说明
		dataMap.put("${feeRemark}", paramBean.getRemark());
		
//		String docUrl = propertyUtil.getValue("gd.uplaod.host") + printSettingDTO.getTemplateUrl();
		String pdfUrl = propertyUtil.getValue("printingFileHost") + printService.convertDocToPdf(settingId, dataMap);
		mv.addObject("fileUrl", pdfUrl);
		mv.setViewName("settings/print/preView");
		return mv;
	}
	
	
 	/**
	 * 检查导出数据
	 * @param request
	 * @return
	 */
	@RequestMapping(value="financeShould/checkExportParams", produces="application/json;charset=utf-8")
	@ResponseBody
	public AjaxResult<String> checkExportParams(HttpServletRequest request, FinanceFeeParamBean paramBean){
		AjaxResult<String> re =new AjaxResult<String> ();
		try{
			// 设置查询参数
			Map<String, Object> params = getParameters(paramBean);
			//收款状态
			params.put("status", FinStatusEnum.NOT_YET.getCode());
			
			int total = financeFeeService.getFeeRecordCount(params);
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
	@RequestMapping(value = "financeShould/exportData")
	public String exportData(HttpServletRequest request, FinanceFeeParamBean paramBean) throws BizException {
		
		// 设置查询参数
		Map<String, Object> params = getParameters(paramBean);
		//收款状态
		params.put("status", FinStatusEnum.NOT_YET.getCode());
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
				sheet.addCell(label00);// 将单元格加入表格
				sheet.addCell(label10);// 将单元格加入表格
				sheet.addCell(label20);
				sheet.addCell(label30);
				sheet.addCell(label40);
				sheet.addCell(label50);
				sheet.addCell(label60);
				sheet.addCell(label70);
				//查询数据
				List<FinanceFeeRecordDTO> recordList = financeFeeService.queryFeeRecord(params);
				//对部分字段做类型转化
				FinanceSupport.typeConvert(recordList);
				if (recordList != null && recordList.size() > 0) {
					for (int j = 0; j < recordList.size(); j++) {
						FinanceFeeRecordDTO dto = recordList.get(j);
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
				if (SIMPLE_QUERY_CONDITIONTYPE_CONTRACT.equalsIgnoreCase(paramBean.getConditionType())){
					params.put("contractNoLike", conditionValue);
				}else if (SIMPLE_QUERY_CONDITIONTYPE_RESOURCE.equalsIgnoreCase(paramBean.getConditionType())){
					params.put("resourceNameLike", conditionValue);
				}else if (SIMPLE_QUERY_CONDITIONTYPE_FEEITEM.equalsIgnoreCase(paramBean.getConditionType())){
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
			if (SIMPLE_QUERY_CONDITIONTYPE_CONTRACT.equalsIgnoreCase(paramBean.getConditionType())){
				params.put("contractNoLike", conditionValue);
			}else if (SIMPLE_QUERY_CONDITIONTYPE_RESOURCE.equalsIgnoreCase(paramBean.getConditionType())){
				params.put("resourceNameLike", conditionValue);
			}else if (SIMPLE_QUERY_CONDITIONTYPE_FEEITEM.equalsIgnoreCase(paramBean.getConditionType())){
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
