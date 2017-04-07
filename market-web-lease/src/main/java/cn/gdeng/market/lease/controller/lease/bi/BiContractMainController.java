package cn.gdeng.market.lease.controller.lease.bi;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import cn.gdeng.market.dto.bean.GuDengPage;
import cn.gdeng.market.dto.lease.bi.BiContractMainDTO;
import cn.gdeng.market.exception.BizException;
import cn.gdeng.market.lease.bean.AjaxResult;
import cn.gdeng.market.lease.controller.base.BaseController;
import cn.gdeng.market.lease.util.ExcelUtil;
import cn.gdeng.market.service.lease.bi.BiContractMainService;
/**
 * 
 * @作者 XieZhongGuo
 * @创建时间 2016年10月13日
 * @说明 
 * @版本 v1.0
 */
@Controller
@RequestMapping("biContractMain")
public class BiContractMainController extends BaseController {
	
	private Logger logger = LoggerFactory.getLogger(BiContractMainController.class);

   //注册service服务
   @Resource(name="biContractMainService")
   private BiContractMainService biContractMainService;
   
   /** 导出Excel单个sheet的最大行数 */
   protected final int SHEET_MAX_ROW_NUM = 1048576;
	/** 导出Excel时每次分页大小*/
   protected final int EXPORT_PAGE_SIZE = 1000;
   /** 导出结果集上限*/
   protected final int EXPORT_MAX_SIZE = 50000;
   
	@RequestMapping(value = "/index")
	public ModelAndView index(ModelAndView mav,HttpServletRequest request) throws BizException {
	
			mav.setViewName("bi/contractMain/biContractMain");
	
	
		return mav;
	}
	@RequestMapping(value = "/toBiOverContractMain")
	public ModelAndView toBiOverContractMain(ModelAndView mav,HttpServletRequest request) throws BizException {
	
			mav.setViewName("bi/contractMain/biOverContractMain");
	
		return mav;
	}
	/**
	 * 
	 * @作者 XieZhongGuo
	 * @创建时间 2016年10月26日
	 * @方法说明 校验市场ID是否存在 
	 * @return
	 * @throws BizException
	 * @版本 V1.0
	 */
	@RequestMapping(value = "/checkMarket")
	public @ResponseBody String checkMarket() throws BizException{
		if(null==getCurrentMarket()||getCurrentMarket().getId()==null){
			return "0";
		}else{
			return "1";
		}
	}
   /**
    * 
    * @作者 XieZhongGuo
    * @创建时间 2016年10月13日
    * @方法说明 根据筛选条件查询租赁合同信息分页列表
    * @param request
    * @return
    * @throws BizException
    * @版本 V1.0
    */
   @RequestMapping(value = "/findByPage", method = RequestMethod.POST)
	@ResponseBody
	public AjaxResult<GuDengPage<BiContractMainDTO>> findByPage(HttpServletRequest request, 
			HttpServletResponse response, BiContractMainDTO dto) throws BizException{
		AjaxResult<GuDengPage<BiContractMainDTO>> params = new AjaxResult<>();
		GuDengPage<BiContractMainDTO> page = getPageInfoByRequest();
		Map<String,Object> map = new HashMap<>();
		if(super.getCurrentMarket()==null){
			params.setResult(null);
			return params;
		}
		Integer marketId=super.getCurrentMarket().getId();
		if(marketId==null||marketId.equals("")){
			params.setResult(null);
			return params;
		}
		if(dto.getType()!=null){
			map.put("type", dto.getType());
		}
		map.put("contractNo", dto.getContractNo());
		map.put("contractStatus", dto.getContractStatus());
		map.put("leasingResource", dto.getLeasingResource());
		map.put("marketId", marketId);
		map.put("startRow", page.getOffset());
		map.put("endRow", page.getPageSize());

		int count = biContractMainService.countTotal(map);
		page.setTotal(count);
		
		List<BiContractMainDTO> list=null;
		if(count>0){
			list= biContractMainService.getContractMainList(map);
		}
		page.setRecordList(list); 
		params.setResult(page);
		 return params;
	}
   /**
    * 
    * @作者 XieZhongGuo
    * @创建时间 2016年10月19日
    * @方法说明 根据所传参数校验导出的结果集大小 
    * @param request
    * @return
    * @版本 V1.0
    */
	@RequestMapping(value="checkExportParams", produces="application/json;charset=utf-8")
	@ResponseBody
	public AjaxResult<String> checkExportParams(HttpServletRequest request){
		AjaxResult<String> re =new AjaxResult<String> ();
		try{
			// 设置查询参数
			if(checkMarket().equals("1")){
			Map<String, Object> map = getParametersMap(request);
			map.put("marketId",getCurrentMarket().getId());
			int total = biContractMainService.countTotal(map);
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
			}else{
				re.setCode(202);
				re.setMsg("请选择市场！");
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
	 * 
	 * @作者 gcwu
	 * @创建时间 2016年10月13日
	 * @方法说明 excel导出函数  
	 * @param request
	 * @param response
	 * @throws BizException 
	 * @版本 V1.0
	 */
	@RequestMapping("export")
	public AjaxResult<?> export(HttpServletRequest request, HttpServletResponse response, BiContractMainDTO dto) throws BizException{
		AjaxResult<?> params = new AjaxResult<>();
		
		Map<String,Object> map = new HashMap<>();
		if(super.getCurrentMarket()==null){
			params.setResult(null);
			return params;
		}
		Integer marketId=super.getCurrentMarket().getId();
		if(marketId==null||marketId.equals("")){
			params.setResult(null);
			return params;
		}
		String fileName = "";
		if(dto.getType()!=null){
			if(dto.getType()==0){
				fileName="未过期合同";
			}else{
				fileName="已过期合同";
			}
			map.put("type", dto.getType());
		}
		map.put("contractNo", dto.getContractNo());
		map.put("contractStatus", dto.getContractStatus());
		map.put("leasingResource", dto.getLeasingResource());
		map.put("marketId", marketId);
		List<BiContractMainDTO> list = biContractMainService.getContractMainList(map);
		boolean res =ExcelUtil.exportExcel(response, fileName, list, BiContractMainDTO.class);
		params.setIsSuccess(res);
		return params;
	}
	
}
