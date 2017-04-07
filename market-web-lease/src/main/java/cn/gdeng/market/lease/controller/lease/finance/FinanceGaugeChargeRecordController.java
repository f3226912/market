package cn.gdeng.market.lease.controller.lease.finance;

import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.alibaba.fastjson.JSON;

import cn.gdeng.market.dto.admin.SysUserDTO;
import cn.gdeng.market.dto.bean.GuDengPage;
import cn.gdeng.market.dto.lease.finance.FinanceGaugeChargeRecordDTO;
import cn.gdeng.market.dto.lease.resources.MarketAreaBuildingDTO;
import cn.gdeng.market.dto.lease.resources.MarketAreaDTO;
import cn.gdeng.market.dto.lease.resources.MarketSectionalChargeDTO;
import cn.gdeng.market.dto.lease.settings.MarketMeasureTypeDTO;
import cn.gdeng.market.dto.lease.settings.MarketResourceTypeDTO;
import cn.gdeng.market.enums.Const;
import cn.gdeng.market.enums.MsgCons;
import cn.gdeng.market.exception.BizException;
import cn.gdeng.market.lease.bean.AjaxResult;
import cn.gdeng.market.lease.controller.base.BaseController;
import cn.gdeng.market.lease.enums.StatusEnum;
import cn.gdeng.market.service.lease.finance.FinanceGaugeChargeRecordService;
import cn.gdeng.market.service.lease.settings.MarketMeasureTypeService;
import cn.gdeng.market.util.DateUtil;
import jxl.Workbook;
import jxl.write.Label;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;
/**
 * 计量表抄表收费记录Controller
 * @author lf
 *
 */
@Controller
@RequestMapping("financeGaugeCharge")
public class FinanceGaugeChargeRecordController extends BaseController {
	

	private Logger logger = LoggerFactory.getLogger(FinanceGaugeChargeRecordController.class);
	
	@Resource
	private FinanceGaugeChargeRecordService financeGaugeChargeRecordService;
	
	@Resource
	private MarketMeasureTypeService marketMeasureTypeService;
	
	
	/**
	 * 计量表抄表页面初使化
	 * @param mv
	 * @return
	 * @throws BizException
	 */
	@RequestMapping("meterReadingIndex")
	public ModelAndView viewMeterReading(ModelAndView mv) throws BizException {
		mv.setViewName("finance/financeGaugeCharge/meterReadingIndex");
		return mv;
	}
	
	/**
	 * 财务收款-计量表抄表-列表
	 * @param request
	 * @author caixu
	 * @return
	 * @throws Exception 
	 */
	@RequestMapping("queryMeterReadingList")
	@ResponseBody
	public AjaxResult<GuDengPage<FinanceGaugeChargeRecordDTO>> queryMeterReadingList(HttpServletRequest request, FinanceGaugeChargeRecordDTO entity) throws Exception{
		AjaxResult<GuDengPage<FinanceGaugeChargeRecordDTO>> res = new AjaxResult<>();
		//当前登录的用户
		//SysUserDTO user = getSysUser();
		//获取分页信息
		GuDengPage<FinanceGaugeChargeRecordDTO> page = getPageInfoByRequest(entity);
		Map<String, Object> paraMap = page.getParaMap();
		//设置查询参数 
		if (getCurrentMarket() != null){
			paraMap.put("marketId", getCurrentMarket().getId());
		}else{
			paraMap.put("marketId", -1);
		}
		paraMap.put("mmsStatus", 1);
		page.setParaMap(paraMap);
		page = financeGaugeChargeRecordService.queryMeterReadingList(page);
		res.setResult(page);
		return res;
	}
	
	
	/**
	 * 计量表抄表记录列表
	 * @param request
	 * @return
	 * @throws Exception 
	 */
	@RequestMapping("query")
	@ResponseBody
	public AjaxResult<GuDengPage<FinanceGaugeChargeRecordDTO>> queryByPage(HttpServletRequest request, FinanceGaugeChargeRecordDTO entity) throws Exception{
		AjaxResult<GuDengPage<FinanceGaugeChargeRecordDTO>> res = new AjaxResult<>();
		//当前登录的用户
		//SysUserDTO user = getSysUser();
		//获取分页信息
		GuDengPage<FinanceGaugeChargeRecordDTO> page = getPageInfoByRequest(entity);
		Map<String, Object> paraMap = page.getParaMap();
		//设置查询参数 
		//paraMap.put("marketId", user.getMarketId());
		page.setParaMap(paraMap);
		page = financeGaugeChargeRecordService.queryByPage(page);
		res.setResult(page);
		return res;
	}
	
	/**
	 * 查询抄表页面的上次读数和损耗率
	 * @param measureId
	 */
	@RequestMapping("showMeterReadingInfo/{measureId}")
	@ResponseBody
	public AjaxResult<FinanceGaugeChargeRecordDTO> showWastageRate(@PathVariable String measureId) throws Exception{
		AjaxResult<FinanceGaugeChargeRecordDTO> res = new AjaxResult<>();
		Map<String,Object> paramMap = new HashMap<String,Object>();
		paramMap.put("measureId", measureId);
		FinanceGaugeChargeRecordDTO dto = financeGaugeChargeRecordService.findMeterReadingInfo(paramMap);
		res.setResult(dto);
		return res;
	}
	
	/**
	 * 查询当前市场的所有区域
	 * @param marketId
	 */
	@RequestMapping("showAreaList")
//	@RequestMapping("showAreaList/{marketId}")
	@ResponseBody
	public AjaxResult<List<MarketAreaDTO>> showAreaList(/*@PathVariable String marketId*/) throws Exception{
		AjaxResult<List<MarketAreaDTO>> res = new AjaxResult<>();
		Map<String,Object> paramMap = new HashMap<String,Object>();
		if (getCurrentMarket() != null){
			paramMap.put("marketId", getCurrentMarket().getId());
		}else{
			paramMap.put("marketId", -1);
		}
		List<MarketAreaDTO> list = financeGaugeChargeRecordService.findCurrentAreaList(paramMap);
		res.setResult(list);
		return res;
	}
	
	/**
	 * 查询当前市场的所有区域
	 */
	@RequestMapping("showBuildList/{areaId}")
	@ResponseBody
	public AjaxResult<List<MarketAreaBuildingDTO>> showBuildList(@PathVariable String areaId) throws Exception{
		AjaxResult<List<MarketAreaBuildingDTO>> res = new AjaxResult<>();
		Map<String,Object> paramMap = new HashMap<String,Object>();
		paramMap.put("areaId", areaId);
		List<MarketAreaBuildingDTO> list = financeGaugeChargeRecordService.findCurrentBuildList(paramMap);
		res.setResult(list);
		return res;
	}
	/**
	 * 批量结转为待付款
	 * @param list
	 */
	@RequestMapping(value = "batchSettlement", method = {RequestMethod.POST }) 
    @ResponseBody  
    public AjaxResult<Integer> batchSettlement(@RequestBody List<FinanceGaugeChargeRecordDTO> list) throws Exception {
		logger.info("查询财务收款列表入参:"+JSON.toJSONString(list));
		AjaxResult<Integer> result = new AjaxResult<>();
		if(null == list || list.size() == 0){
			result.setIsSuccess(false);
			result.setCodeMsg(MsgCons.C_30001, MsgCons.M_30001);
			return result;
		}
		if(getCurrentMarket() == null){
			result.setIsSuccess(false);
			result.setCodeMsg(MsgCons.C_20000, MsgCons.M_20000);
			return result;
		}
		try{
			//当前登录的用户
			SysUserDTO user = getSysUser();
			Integer marketId = getCurrentMarket().getId();
			//当前市场ID
			int count = financeGaugeChargeRecordService.batchSettlement(list,user,marketId);
			if(count > 0){
				result.setIsSuccess(true);
				result.setResult(count);
			}else{
				result.setIsSuccess(false);
				result.setCodeMsg(MsgCons.C_30101, MsgCons.M_30101);
			}
		}catch(BizException e){
			logger.info("batchSettlement with ex : {}, userId {} ",
					new Object[]{e, getSysUser().getUserId()});
			result.setIsSuccess(false);
			result.setCodeMsg(MsgCons.C_30101, MsgCons.M_30101);
		}
		return result;
    }
	/**
	 * 计量表抄表收费记录菜单初使化
	 * @param mv
	 * @return
	 * @throws BizException
	 */
	@RequestMapping("financeGuageChargeIndex")
	public ModelAndView viewFinanceGaugeCharge(ModelAndView mv) throws BizException {
		mv.setViewName("finance/financeGaugeCharge/financeGuageChargeIndex");
		return mv;
	}
	
	/**
	 * 计量表抄表收费记录-列表
	 * @param request
	 * @return
	 * @throws Exception 
	 */
	@RequestMapping("queryFinanceGaugeChargeList")
	@ResponseBody
	public AjaxResult<GuDengPage<FinanceGaugeChargeRecordDTO>> queryFinanceGaugeChargeList(HttpServletRequest request, FinanceGaugeChargeRecordDTO entity) throws Exception{
		AjaxResult<GuDengPage<FinanceGaugeChargeRecordDTO>> res = new AjaxResult<>();
		//获取分页信息
		GuDengPage<FinanceGaugeChargeRecordDTO> page = getPageInfoByRequest(entity);
		Map<String, Object> paraMap = page.getParaMap();
		//设置查询参数 
		if (getCurrentMarket() != null){
			paraMap.put("marketId", getCurrentMarket().getId());
		}else{
			paraMap.put("marketId", -1);
		}
		page.setParaMap(paraMap);
		page = financeGaugeChargeRecordService.queryFinanceGaugeChargeList(page);
		res.setResult(page);
		return res;
	}
	/**
	 * 将求收款的变为已收款
	 * @param ids
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "batchUpdateStatus")
	@ResponseBody
	public AjaxResult<Integer> batchUpdateStatus(String ids, HttpServletRequest request)throws Exception {
		AjaxResult<Integer> result = new AjaxResult<>();
		try {
			if (StringUtils.isEmpty(ids)) {
				result.setIsSuccess(false);
				result.setCodeMsg(MsgCons.C_20000, MsgCons.M_20000);
			} else {
				List<String> list = Arrays.asList(ids.split(","));
				//当前登录的用户
				SysUserDTO user = getSysUser();
				int i = financeGaugeChargeRecordService.batchUpdateStatus(list,user);
				result.setIsSuccess(true);
				result.setResult(i);
			}
		} catch (Exception e) {
			result.setIsSuccess(false);
			result.setCodeMsg(MsgCons.C_30101, MsgCons.M_30101);
		}
		return result;
	}
	
	/**
	 * 更新收费记录详情
	 * @param dto
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "update")
	@ResponseBody
	public AjaxResult<Integer> update(FinanceGaugeChargeRecordDTO dto, HttpServletRequest request)throws Exception {
		AjaxResult<Integer> result = new AjaxResult<>();
		try {
			if (null == dto.getId()) {
				result.setIsSuccess(false);
				result.setCodeMsg(MsgCons.C_20000, MsgCons.M_20000);
			} else {
				Map<String,Object> paramMap = new HashMap<String,Object>();
				paramMap.put("id", dto.getId());
				paramMap.put("current", dto.getCurrent());
				paramMap.put("wastage", dto.getWastage());
				paramMap.put("amount", dto.getAmount());
				paramMap.put("noteDateStr", dto.getNoteDateStr());
				//当前登录的用户
				SysUserDTO user = getSysUser();
				paramMap.put("updateUserId", user.getUserId());
				int i = financeGaugeChargeRecordService.update(paramMap);
				result.setIsSuccess(true);
				result.setResult(i);
			}
		} catch (Exception e) {
			result.setIsSuccess(false);
			result.setCodeMsg(MsgCons.C_30101, MsgCons.M_30101);
		}
		return result;
	}
	
	/**
	 * 根据条件查询抄表收费记录详情
	 * @param id
	 */
	@RequestMapping("queryFinanceGaugeChargeById/{id}")
	public ModelAndView queryFinanceGaugeChargeById(ModelAndView mv,@PathVariable String id) throws Exception{
		Map<String,Object> paramMap = new HashMap<String,Object>();
		paramMap.put("id", id);
		FinanceGaugeChargeRecordDTO dto = financeGaugeChargeRecordService.queryFinanceGaugeChargeById(paramMap);
		if(dto != null && dto.getMeasureId() != null){
			FinanceGaugeChargeRecordDTO dtoInfo = financeGaugeChargeRecordService.findContractInfoById(paramMap);
			if(dtoInfo != null){
				dto.setSectionalCharge(dtoInfo.getSectionalCharge());
				dto.setNewPrice(dtoInfo.getPrice());
				dto.setExpStandardId(dtoInfo.getExpStandardId());
				
			}
		}
		List<String> priceList = getPriceList(dto);
		if(priceList != null &&  priceList.size()>0){
			mv.addObject("priceList", priceList);
		}
		mv.addObject("financeGaugeChargeDto", dto);
		mv.setViewName("finance/financeGaugeCharge/financeGuageChargeDetail");
		return mv;
	}
	/**
	 * 查询计量表所在合同的计费信息
	 * @param dto
	 * @return
	 * @throws Exception
	 */
	public FinanceGaugeChargeRecordDTO findMeterReadingInfo(FinanceGaugeChargeRecordDTO dto) throws Exception{
		Map<String,Object> paramMap = new HashMap<String,Object>();
		paramMap.put("measureId", dto.getMeasureId());
		//根据mesureId查询当前计量表在当前合同中的的计费方式
		FinanceGaugeChargeRecordDTO dtoInfo = financeGaugeChargeRecordService.findMeterReadingInfo(paramMap);
		return dtoInfo;
	}

	/**
	 * 计算分段价格费用
	 * @param dto
	 * @return
	 * @throws Exception
	 */
	public List<String> getPriceList(FinanceGaugeChargeRecordDTO dto) throws Exception{
		Map<String,Object> paramMap = new HashMap<String,Object>();
		List<String> priceList = new ArrayList<String>();
		if(dto != null && null != dto.getSectionalCharge()){
			//总的用量 = 本次读数-上次计数+损耗用量
			double totalQuantity = dto.getCurrent()-(dto.getLast()==null?0:dto.getLast()) + (dto.getWastage()==null?0:dto.getWastage());
			if(dto.getSectionalCharge().intValue() == Const.MEASURE_SECTIONALCHARGE_SEGMENTATION) {
				paramMap.put("expStandardId", dto.getExpStandardId());
				List<MarketSectionalChargeDTO> marketSectionalChargeList = financeGaugeChargeRecordService.findSectionalchargeList(paramMap);
				if(null != marketSectionalChargeList && marketSectionalChargeList.size()>0){
					priceList = financeGaugeChargeRecordService.getPrice(totalQuantity,marketSectionalChargeList);
				}
			}
		}
		return priceList;
	}
	
	/**
	 * 更新收费金额和分段价格计算
	 * @param dto
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "updateAmount")
	@ResponseBody
	public AjaxResult<List<String>> updateAmount(FinanceGaugeChargeRecordDTO dto, HttpServletRequest request)throws Exception {
		AjaxResult<List<String>> result = new AjaxResult<>();
		try {
			if (dto.getCurrent() == null || dto.getLast() == null
					|| dto.getNewPrice() == null
					|| dto.getSectionalCharge() == null
					|| dto.getExpStandardId() == null) {
				logger.info("dto的current,last,newprice,sectionalcharge,expstandardId其中有为空");
				result.setIsSuccess(false);
				result.setCodeMsg(MsgCons.C_30001, MsgCons.M_30001);
			}
			List<String> priceList = new ArrayList<String>();
			if(dto.getSectionalCharge().intValue() == Const.MEASURE_SECTIONALCHARGE_SEGMENTATION){
				//分段计费时，总收费金额为list的最后一个值
				priceList = getPriceList(dto); 
			}else{
				double totalQuantity = dto.getCurrent()-(dto.getLast()==null?0:dto.getLast()) + (dto.getWastage()==null?0:dto.getWastage());
				//保留2位小数向上取
				double totalMoney = Math.ceil(dto.getNewPrice()*totalQuantity*100)/100;
				//不是分段计费时，总收费金额为list中唯一一个值
				priceList.add(String.valueOf(totalMoney));
			}
			result.setIsSuccess(true);
			result.setResult(priceList);
		} catch (Exception e) {
			result.setIsSuccess(false);
			result.setCodeMsg(MsgCons.C_30101, MsgCons.M_30101);
		}
		return result;
	}
	
	
	/**
	 * 计量表分类列表
	 * @param request
	 * @return
	 * @throws Exception 
	 */
	@ResponseBody
	@RequestMapping("findAllMeasureType")
	public AjaxResult<GuDengPage<MarketMeasureTypeDTO>> findAllMeasureType(HttpServletRequest request, MarketMeasureTypeDTO entity) throws Exception{
		AjaxResult<GuDengPage<MarketMeasureTypeDTO>> res = new AjaxResult<>();
		//获取分页信息
		GuDengPage<MarketMeasureTypeDTO> page = super.getPageInfoByRequest(entity);
		Map<String, Object> paraMap = page.getParaMap();
		//设置查询参数 
		if(null != getCurrentMarket()){
			paraMap.put("marketId", super.getCurrentMarket().getId());
		}else{
			paraMap.put("marketId", -1);
		}
		Set<String> parentIds =  new HashSet<String>();
		//水表, id固定为1
		parentIds.add("1");
		//电表, id固定为2
		parentIds.add("2");
		paraMap.put("parentIds", parentIds);
		paraMap.put("status", StatusEnum.NORMAL.getKey());
		page.setParaMap(paraMap);
		List<MarketMeasureTypeDTO> measureTypes = financeGaugeChargeRecordService.findAllMeasureType(paraMap);
		page.setRecordList(measureTypes);
		res.setResult(page);
		return res;
	}
	
	
	@RequestMapping("measureSettingResourceForward")	
	public ModelAndView measureSettingResourceForward(ModelAndView mv) throws BizException {
		mv.setViewName("finance/financeGaugeCharge/resourceForward");
		return mv;
	}
	
	@ResponseBody
	@RequestMapping("resourceTypeDetail")
	public AjaxResult<MarketResourceTypeDTO> findMarketResourceType(int resourceId)throws Exception{
		AjaxResult<MarketResourceTypeDTO> result = new AjaxResult<MarketResourceTypeDTO>();
		Map<String, Object> param = new HashMap<String, Object>();
		param.put("id", resourceId);
		MarketResourceTypeDTO resourceType = financeGaugeChargeRecordService.findMarketResourceType(param);
		result.setResult(resourceType);
		return result;
	}
	
 	/**
	 * 检查导出数据
	 * @param request
	 * @return
	 */
	@RequestMapping(value="checkExportParams", produces="application/json;charset=utf-8")
	@ResponseBody
	public AjaxResult<String> checkExportParams(HttpServletRequest request){
		AjaxResult<String> re =new AjaxResult<String> ();
		try{
			// 设置查询参数
			Map<String, Object> map = getParametersMap(request);
			if (getCurrentMarket() != null){
				map.put("marketId", getCurrentMarket().getId());
			}else{
				map.put("marketId", -1);
			}
			int total = financeGaugeChargeRecordService.queryFinanceGaugeChargeListCount(map);
			if (total == 0){
				re.setIsSuccess(false);
				re.setCode(MsgCons.C_30001);
				re.setMsg( "查询没有符合的结果 ,请修改其他查询条件...");
				return re;
			}
			if (total > Const.EXPORT_MAX_SIZE){
				re.setIsSuccess(false);
				re.setCode(MsgCons.C_30002);
				re.setMsg("查询结果集太大(>"+Const.EXPORT_MAX_SIZE+"条), 请缩减日期范围, 或者修改其他查询条件...");
				return re;
			}
			re.setIsSuccess(true);
			re.setResult(String.valueOf(total));
		}catch(Exception e){
			logger.warn(e.getMessage());
			re.setIsSuccess(false);
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
	@RequestMapping(value = "exportData")
	public String exportData(HttpServletRequest request) throws BizException {
		
		Map<String, Object> map = getParametersMap(request);
		//设置查询参数 
		if (getCurrentMarket() != null){
			map.put("marketId", getCurrentMarket().getId());
		}else{
			map.put("marketId", -1);
		}
		// 设置查询参数
		map.put("startRow", 0);
		map.put("endRow", Const.EXPORT_MAX_SIZE);
		WritableWorkbook wwb = null;
		OutputStream ouputStream = null;
		try {
			// 设置输出响应头信息，
			response.setContentType("application/vnd.ms-excel");
			String fileName = new String("计量表抄表历史记录".getBytes(), "ISO-8859-1")+DateUtil.toString(new Date(), "yyyy-MM-dd_HH:mm:ss");
			response.setHeader("Content-disposition", "attachment;filename=" + fileName + ".xls");
			ouputStream = response.getOutputStream();
			// 在输出流中创建一个新的写入工作簿
			wwb = Workbook.createWorkbook(ouputStream);
			if (wwb != null) {
				WritableSheet sheet = wwb.createSheet("计量表抄表历史记录", 0);// 创建一个工作页，第一个参数的页名，第二个参数表示该工作页在excel中处于哪一页
				// 第一个参数表示列，第二个参数表示行
				Label label00 = new Label(0, 0, "计量表编号");// 填充第一行第一个单元格的内容
				Label label10 = new Label(1, 0, "计量表分类");//填充第一行第二个单元格的内容
				Label label20 = new Label(2, 0, "抄表日期");//填充第一行第三个单元格的内容
				Label label30 = new Label(3, 0, "本次读数");//填充第一行第四个单元格的内容
				Label label40 = new Label(4, 0, "上次读数 ");//
				Label label50 = new Label(5, 0, "损耗用量");//
				
				Label label60 = new Label(6, 0, "计费单价");
				Label label70 = new Label(7, 0, "收费金额");
				Label label80 = new Label(8, 0, "是否收款");
				Label label90 = new Label(9, 0, "客户名称 ");
				Label label010 = new Label(10, 0, "乙方");
				Label label011 = new Label(11, 0, "关联资源 ");
				Label label012 = new Label(12, 0, "抄表人 ");
				sheet.addCell(label00);// 将单元格加入表格
				sheet.addCell(label10);// 将单元格加入表格
				sheet.addCell(label20);
				sheet.addCell(label30);
				sheet.addCell(label40);
				sheet.addCell(label50);
				sheet.addCell(label60);
				sheet.addCell(label70);
				sheet.addCell(label80);
				sheet.addCell(label90);
				sheet.addCell(label010);
				sheet.addCell(label011);
				sheet.addCell(label012);
				//查询数据
				List<FinanceGaugeChargeRecordDTO> list = financeGaugeChargeRecordService.queryFinanceGaugeChargeRecordList(map);
				if (list != null && list.size() > 0) {
					for (int j = 0; j < list.size(); j++) {
						FinanceGaugeChargeRecordDTO dto = list.get(j);
						String status = dto.getStatus();
						if("1".equals(status)){
							status = "已收款";
						}else{
							status = "未收款";
						}
						
						Label label0 = new Label(0,  j + 1, String.valueOf(dto.getMeasureCode()==null?"":dto.getMeasureCode()));
						Label label1 = new Label(1,  j + 1, String.valueOf(dto.getMeasureTypeName()==null?"":dto.getMeasureTypeName()));
						Label label2 = new Label(2,  j + 1, String.valueOf(dto.getNoteDateStr()));
						Label label3 = new Label(3,  j + 1, String.valueOf(dto.getCurrent()==null?0:dto.getCurrent()));
						Label label4 = new Label(4,  j + 1,  String.valueOf(dto.getLast()==null?0:dto.getLast()));
						Label label5 = new Label(5,  j + 1,  String.valueOf(dto.getWastage()==null?0:dto.getWastage()));
						Label label6 = new Label(6,  j + 1,  String.valueOf(dto.getPrice()==null?0:dto.getPrice()));
						Label label7 = new Label(7,  j + 1,  String.valueOf(dto.getAmount()==null?0:dto.getAmount()));
						Label label8 = new Label(8,  j + 1,  String.valueOf(status));
						Label label9 = new Label(9,  j + 1,  String.valueOf(dto.getCustomerName()==null?"":dto.getCustomerName()));
						Label label0010 = new Label(10,  j + 1,  String.valueOf(dto.getPartyB()==null?"":dto.getPartyB()));
						Label label0011 = new Label(11,  j + 1,  String.valueOf(dto.getResourceNames()==null?"":dto.getResourceNames()));
						Label label0012 = new Label(12,  j + 1,  String.valueOf(dto.getNoteUser()==null?"":dto.getNoteUser()));
						sheet.addCell(label0);//将单元格加入表格
						sheet.addCell(label1);// 
						sheet.addCell(label2);
						sheet.addCell(label3);
						sheet.addCell(label4);
						sheet.addCell(label5);
						sheet.addCell(label6);
						sheet.addCell(label7);
						sheet.addCell(label8);
						sheet.addCell(label9);
						sheet.addCell(label0010);
						sheet.addCell(label0011);
						sheet.addCell(label0012);
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
	
}
