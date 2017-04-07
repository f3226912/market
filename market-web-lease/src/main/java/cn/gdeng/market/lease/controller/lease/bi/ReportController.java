package cn.gdeng.market.lease.controller.lease.bi;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import cn.gdeng.market.dto.lease.bi.BiFeetakeInfoDTO;
import cn.gdeng.market.dto.lease.bi.BiKeyIndexDTO;
import cn.gdeng.market.dto.lease.settings.MarketExpenditureDTO;
import cn.gdeng.market.dto.lease.settings.MarketResourceTypeDTO;
import cn.gdeng.market.exception.BizException;
import cn.gdeng.market.lease.bean.AjaxResult;
import cn.gdeng.market.lease.controller.base.BaseController;
import cn.gdeng.market.lease.util.BiUtil;
import cn.gdeng.market.service.lease.bi.ReportService;
import cn.gdeng.market.service.lease.settings.MarketExpenditureService;
import cn.gdeng.market.service.lease.settings.MarketResourceTypeService;

/**
 * 报表控制器
 * @author gcwu
 *
 */
@Controller
@RequestMapping("report")
public class ReportController extends BaseController {
	@Autowired
	private ReportService reportService;
	
	@Autowired
	private MarketResourceTypeService marketResourceTypeService;
	@Autowired
	private MarketExpenditureService marketExpenditureService;
	
	@RequestMapping("index")
	public ModelAndView chart(ModelAndView mv) throws BizException {
		mv.setViewName("bi/report/index");
		return mv;
	}
	
	private final static String EXPID_ZJ="1";//租金
	/**
	 * bi 获取MarketResourceType 前6项按sort 升序
	 * @param request
	 * @param response
	 * @param dto
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/getMarketResourceType", method = RequestMethod.GET)
	@ResponseBody
	public AjaxResult<List<MarketResourceTypeDTO>> getMarketResourceType(HttpServletRequest request, 
			HttpServletResponse response) throws Exception {
		AjaxResult<List<MarketResourceTypeDTO>> params = new AjaxResult<>();
		if(super.getCurrentMarket()==null){
			params.setResult(null);
			return params;
		}
		Integer marketId=super.getCurrentMarket().getId();
		if(marketId==null||marketId.equals("")){
			params.setResult(null);
			return params;
		}
		Map<String,Object> map = new HashMap<String, Object>();
		
		map.put("marketId", marketId);
		map.put("status", 1);//有效
		map.put("number", 6);//显示条数
		List<MarketResourceTypeDTO> list = marketResourceTypeService.getMarketResourceType(map);
		
		params.setResult(list);
		params.setIsSuccess(true);
		return params;
	}
	/**
	 * bi 获取MarketResourceType 前6项按sort 升序
	 * @param request
	 * @param response
	 * @param dto
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/getExpIdList", method = RequestMethod.GET)
	@ResponseBody
	public AjaxResult<List<MarketExpenditureDTO>> getExpIdList(HttpServletRequest request, 
			HttpServletResponse response) throws Exception {
		AjaxResult<List<MarketExpenditureDTO>> params = new AjaxResult<>();
		if(super.getCurrentMarket()==null){
			params.setResult(null);
			return params;
		}
		Integer marketId=super.getCurrentMarket().getId();
		if(marketId==null||marketId.equals("")){
			params.setResult(null);
			return params;
		}
		Map<String,Object> map = new HashMap<String, Object>();
		List<String> parentIds = new ArrayList<>();
		parentIds.add(EXPID_ZJ);//租金
		map.put("marketId", marketId);
		map.put("parentIds", parentIds);
		List<MarketExpenditureDTO> list = marketExpenditureService.querySysTypeList(map);
		
		params.setResult(list);
		params.setIsSuccess(true);
		return params;
	}
	/**
	 * 关键指标
	 * @param resourceTypeId
	 * @return
	 * @throws BizException
	 */
	@ResponseBody
	@RequestMapping("getReportKeyIndex")
	public AjaxResult<BiKeyIndexDTO> getReportKeyIndex(HttpServletRequest request) throws BizException{
		AjaxResult<BiKeyIndexDTO> params = new AjaxResult<>();
		if(super.getCurrentMarket()==null){
			params.setResult(null);
			return params;
		}
		Integer marketId=super.getCurrentMarket().getId();
		if(marketId==null||marketId.equals("")){
			params.setResult(null);
			return params;
		}
		
		String resourceTypeId=request.getParameter("resourceTypeId");
		Map<Object, Object> map = new HashMap<>();
		map.put("marketId",marketId);
		map.put("resourceTypeId", resourceTypeId);
		BiKeyIndexDTO result=reportService.getReportKeyIndex(map);
		params.setResult(result);
		return params;
	}
	
	/**
	 * 费项收缴情况
	 * @param resourceTypeId
	 * @return
	 * @throws BizException
	 */
	@ResponseBody
	@RequestMapping("getReportExpfeeInfo")
	public AjaxResult<List<BiFeetakeInfoDTO>> getReportExpfeeInfo(HttpServletRequest request) throws BizException{
		AjaxResult<List<BiFeetakeInfoDTO>> params = new AjaxResult<>();
		if(super.getCurrentMarket()==null){
			params.setResult(null);
			return params;
		}
		Integer marketId=super.getCurrentMarket().getId();
		if(marketId==null||marketId.equals("")){
			params.setResult(null);
			return params;
		}
		Map<Object, Object> map = new HashMap<>();
		String resourceTypeId=request.getParameter("resourceType");
		String areaId=request.getParameter("areaId");
		String expId=request.getParameter("expId");
		String startDate=request.getParameter("startDate");
		String endDate=request.getParameter("endDate");
		map.put("marketId",marketId);//市场id
		map.put("resourceTypeId", resourceTypeId);//资源类别id
		map.put("areaId", areaId);//区域id
		map.put("expId", expId);//费项id
		map.put("startDate", startDate + "-01");
		map.put("endDate", BiUtil.dateByMonthUtil(endDate + "-01", 1));
		
		List<BiFeetakeInfoDTO> result=reportService.getReportExpfeeInfo(map);
		params.setResult(result);
		return params;
	}

	/**
	 * 	未收款情况饼图
	 * @param resourceTypeId
	 * @return
	 * @throws BizException
	 */
	@ResponseBody
	@RequestMapping("getReportDidfeeInfo")
	public AjaxResult<List<BiFeetakeInfoDTO>> getReportDidfeeInfo(HttpServletRequest request) throws BizException{
		AjaxResult<List<BiFeetakeInfoDTO>> params = new AjaxResult<>();
		if(super.getCurrentMarket()==null){
			params.setResult(null);
			return params;
		}
		Integer marketId=super.getCurrentMarket().getId();
		if(marketId==null||marketId.equals("")){
			params.setResult(null);
			return params;
		}
		Map<Object, Object> map = new HashMap<>();
		String resourceTypeId=request.getParameter("resourceType");
		String expId=request.getParameter("expId");
		String startDate=request.getParameter("startDate");
		String endDate=request.getParameter("endDate");
		map.put("marketId",marketId);//市场id
		map.put("resourceTypeId", resourceTypeId);//资源类别id
		map.put("expId", expId);//区域id
		map.put("startDate", startDate + "-01");
		map.put("endDate", BiUtil.dateByMonthUtil(endDate + "-01", 1));
		List<BiFeetakeInfoDTO> result=reportService.getReportDidfeeInfo(map);
		params.setResult(result);
		return params;
	}
	
	/**
	 * 	未收款情况表格
	 * @param resourceTypeId
	 * @return
	 * @throws BizException
	 */
	@ResponseBody
	@RequestMapping("getReportDidfeeTableInfo")
	public AjaxResult<List<BiFeetakeInfoDTO>> getReportDidfeeTableInfo(HttpServletRequest request) throws BizException{
		AjaxResult<List<BiFeetakeInfoDTO>> params = new AjaxResult<>();
		if(super.getCurrentMarket()==null){
			params.setResult(null);
			return params;
		}
		Integer marketId=super.getCurrentMarket().getId();
		if(marketId==null||marketId.equals("")){
			params.setResult(null);
			return params;
		}
		Map<Object, Object> map = new HashMap<>();
		String resourceTypeId=request.getParameter("resourceType");
		String expId=request.getParameter("expId");
		String startDate=request.getParameter("startDate");
		String endDate=request.getParameter("endDate");
		map.put("marketId",marketId);//市场id
		map.put("expId", expId);//资源类别id
		map.put("resourceTypeId", resourceTypeId);//资源类别id
		map.put("startDate", startDate + "-01");
		map.put("endDate",BiUtil.dateByMonthUtil(endDate + "-01", 1));
		map.put("topfive", 5);//table显示前5项
		List<BiFeetakeInfoDTO> result=reportService.getReportDidfeeInfo(map);
		params.setResult(result);
		return params;
	}
	/**
	 * 获取优惠情况分析
	 * @param resourceTypeId
	 * @return
	 * @throws BizException
	 */
	@ResponseBody
	@RequestMapping("getReportPreferentialInfo")
	public AjaxResult<List<BiFeetakeInfoDTO>> getReportPreferentialInfo(HttpServletRequest request) throws BizException{
		AjaxResult<List<BiFeetakeInfoDTO>> params = new AjaxResult<>();
		if(super.getCurrentMarket()==null){
			params.setResult(null);
			return params;
		}
		Integer marketId=super.getCurrentMarket().getId();
		if(marketId==null||marketId.equals("")){
			params.setResult(null);
			return params;
		}
		Map<Object, Object> map = new HashMap<>();
		String resourceTypeId=request.getParameter("resourceType");
		String areaId=request.getParameter("areaId");
		String expId=request.getParameter("expId");
		String startDate=request.getParameter("startDate");
		String endDate=request.getParameter("endDate");
		map.put("marketId",marketId);//市场id
		map.put("resourceTypeId", resourceTypeId);//资源类别id
		map.put("areaId", areaId);//区域id
		map.put("expId", expId);//费项id
		map.put("startDate", startDate + "-01");
		map.put("endDate", BiUtil.dateByMonthUtil(endDate + "-01", 1));
		List<BiFeetakeInfoDTO> result=reportService.getReportPreferentialInfo(map);
		params.setResult(result);
		return params;
	}
	
	/**
	 * 获取优惠情况分析
	 * @param resourceTypeId
	 * @return
	 * @throws BizException
	 */
	@ResponseBody
	@RequestMapping("getReportPreferentialInfoPie")
	public AjaxResult<List<BiFeetakeInfoDTO>> getReportPreferentialInfoPie(HttpServletRequest request) throws BizException{
		AjaxResult<List<BiFeetakeInfoDTO>> params = new AjaxResult<>();
		if(super.getCurrentMarket()==null){
			params.setResult(null);
			return params;
		}
		Integer marketId=super.getCurrentMarket().getId();
		if(marketId==null||marketId.equals("")){
			params.setResult(null);
			return params;
		}
		Map<Object, Object> map = new HashMap<>();
		String resourceTypeId=request.getParameter("resourceType");
		String areaId=request.getParameter("areaId");
		String expId=request.getParameter("expId");
		String startDate=request.getParameter("startDate");
		String endDate=request.getParameter("endDate");
		map.put("marketId",marketId);//市场id
		map.put("resourceTypeId", resourceTypeId);//资源类别id
		map.put("areaId", areaId);//区域id
		map.put("expId", expId);//费项id
		map.put("startDate", startDate + "-01");
		map.put("endDate", BiUtil.dateByMonthUtil(endDate + "-01", 1));
		List<BiFeetakeInfoDTO> result=reportService.getReportPreferentialInfoPie(map);
		params.setResult(result);
		return params;
	}
}
