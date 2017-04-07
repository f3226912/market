package cn.gdeng.market.lease.controller.lease.resources;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import cn.gdeng.market.entity.lease.resources.MarketAreaBuildingEntity;
import cn.gdeng.market.entity.lease.resources.MarketResourceEntity;
import cn.gdeng.market.exception.BizException;
import cn.gdeng.market.lease.bean.AjaxResult;
import cn.gdeng.market.lease.controller.base.BaseController;
import cn.gdeng.market.service.lease.resources.MarketResourceAdjustmentService;
import cn.gdeng.market.service.lease.settings.MarketResourceTypeService;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

/**
 * 资源调整控制类
 * author ： gaofeng
 * date:2016/10/9
 * */
@RequestMapping("resourceAdjust")
@Controller
public class MarketResourceAdjustmentController extends BaseController {
	
	public final static int   RESOURCE_SPLIT_FLAG = 1;  //资源被拆分标志
	
	public final static int   RESOURCE_MERGE_FLAG = 2;	//资源被合并标志
	
	public final static int   RESOURCE_DELETE_FLAG = 0; //资源删除表示
	
	public final static String   NO_MARKET = "当前市场为空"; 	//获取不到当前市场
	
	@Resource(name="marketResourceAdjustmentService")
	public MarketResourceAdjustmentService	marketResourceAdjustmentService;
	
	@Resource(name="marketResourceTypeService")
	public MarketResourceTypeService	marketResourceTypeService;
	
	
	/**
	 * 返回资源调整页面
	 * */
	@RequestMapping("index")
	public ModelAndView index(ModelAndView	mv){
		mv.setViewName("resources/resourceAdjust/index");
		return mv;
	}
	
	/**
	 * 资源调整，设置资源类型及面积
	 * @throws BizException 
	 * */
	@RequestMapping("setTypeAndArea")
	@ResponseBody
	public AjaxResult<Map<String,Object>> setResourceTypeAndArea(HttpServletRequest	request,HttpServletResponse	response) throws BizException{
		AjaxResult<Map<String,Object>> ajaxResult = new AjaxResult<>();
		if(null != getCurrentMarket()){
			//接受前端参数
			String ids = request.getParameter("resourceIds"); //操作的资源id 集合
			String builtArea = request.getParameter("builtArea");//建筑面积
			String innerArea = request.getParameter("innerArea");//内套面积
			String leaseArea = request.getParameter("leaseArea");//可出租面积
			String resourceTypeId = request.getParameter("resourceTypeId"); //资源类型id
			List<MarketResourceEntity>	marketResourceEntities = new ArrayList<>();
			for(String id:ids.split(",")){
				MarketResourceEntity	marketResourceEntity = new MarketResourceEntity();
				marketResourceEntity.setId(Integer.parseInt(id));
				if(null != builtArea && !"".equals(builtArea)){
					marketResourceEntity.setBuiltArea(Double.parseDouble(builtArea)); 
				}
				if(null != innerArea && !"".equals(innerArea)){
					marketResourceEntity.setInnerArea(Double.parseDouble(innerArea)); 
				}
				if(null != leaseArea && !"".equals(leaseArea)){
					marketResourceEntity.setLeaseArea(Double.parseDouble(leaseArea)); 
				}
				if(null != resourceTypeId && !"".equals(resourceTypeId)){
					marketResourceEntity.setResourceTypeId(getResourceTypeIdByMarketId(getCurrentMarket().getId(), resourceTypeId));
				}
				marketResourceEntities.add(marketResourceEntity);
			}
			try {
				//调用服务层，资源调整 ，资源类型及面积设置 方法
				this.marketResourceAdjustmentService.adjustmentResourceTypeAndArea(marketResourceEntities);
			} catch (BizException e) {
				e.printStackTrace();
			}
		}else{
			ajaxResult.setMsg(NO_MARKET);
		}
		return ajaxResult;
	}
	
	/**
	 * 获取当前市场的资源类型 id
	 * */
	public	Integer	getResourceTypeIdByMarketId(Integer marketId,String	typeId){
		Map<String,Object> map =new HashMap<>();
		map.put("marketId", marketId);
		map.put("parentId", typeId);
		try {
			return this.marketResourceTypeService.queryByMarketIdParentId(map);
		} catch (BizException e) {
			e.printStackTrace();
			return 0;
		}
	}

	/**
	 * 资源拆分，只能拆分一个资源，一个资源可以拆分成多个资源
	 * @throws ParseException 
	 * @throws BizException 
	 * */
	@RequestMapping("resourceSplit")
	@ResponseBody
	public AjaxResult<Map<String, Object>> resourceSplit(HttpServletRequest	request,HttpServletResponse response) throws ParseException, BizException{
		AjaxResult<Map<String,Object>> ajaxResult = new AjaxResult<>();
		if (null != getCurrentMarket()){
			//接受前端参数
			String leaseValidateTime = request.getParameter("leaseValidateTime"); //生效时间
			String id = request.getParameter("resourceId");  //要拆分的源资源id
			String srcs = request.getParameter("srcs");
			//被拆分源资源
			MarketResourceEntity marketResourceEntity = new  MarketResourceEntity();
			marketResourceEntity.setId(Integer.parseInt(id)); //id
			marketResourceEntity.setOriginOperate(RESOURCE_SPLIT_FLAG);  //被拆分标示
			//获取被拆分源资源
			MarketResourceEntity	originResource = new MarketResourceEntity();
			originResource.setId(marketResourceEntity.getId());
			originResource = this.marketResourceAdjustmentService.getResourceById(originResource);
			//拆分后的资源json 
			JSONArray jsonArray = JSONArray.fromObject(srcs);
			List<MarketResourceEntity> splitResources = new ArrayList<>();
			for(int i=0;i<jsonArray.size();i++){
				JSONObject jsonObject = jsonArray.getJSONObject(i);
				String shortCode = jsonObject.getString("shortCode");
				String builtArea = jsonObject.getString("builtArea");
				String innerArea = jsonObject.getString("innerArea");
				String rentMode  = jsonObject.getString("rentMode");
				String leaseArea = jsonObject.getString("leaseArea");
				String resourceTypeId = jsonObject.getString("resourceTypeId");//资源类型
				//拆分后的新资源
				MarketResourceEntity	splitResource = new MarketResourceEntity();
				splitResource.setCode(this.marketResourceAdjustmentService.getResourceCodeOrName(originResource.getMarketId(), originResource.getAreaId(), originResource.getBudingId(), originResource.getUnitId(), originResource.getFloorId(),shortCode));
				splitResource.setShortCode(shortCode);
				splitResource.setOrginCode(originResource.getCode());
				splitResource.setOriginOperate(0);
				splitResource.setName(this.marketResourceAdjustmentService.getResourceCodeOrName(originResource.getBudingId(),shortCode));
				splitResource.setMarketId(originResource.getMarketId());
				splitResource.setAreaId(originResource.getAreaId());
				splitResource.setBudingId(originResource.getBudingId());
				splitResource.setUnitId(originResource.getUnitId());
				splitResource.setFloorId(originResource.getFloorId());
				if(null != builtArea && !"".equals(builtArea)){
					splitResource.setBuiltArea(Double.parseDouble(builtArea));
				}
				if(null != innerArea && !"".equals(innerArea)){
					splitResource.setInnerArea(Double.parseDouble(innerArea));
				}
				if(null != leaseArea && !"".equals(leaseArea)){
					splitResource.setLeaseArea(Double.parseDouble(leaseArea));
				}
				if(null != resourceTypeId && !"".equals(resourceTypeId)){
					splitResource.setResourceTypeId(getResourceTypeIdByMarketId(getCurrentMarket().getId(), resourceTypeId)); 
				}
				splitResource.setRentMode(rentMode); //收租模式
				if(null != leaseValidateTime && !"".equals(leaseValidateTime)){
					splitResource.setLeaseValidateTime(new SimpleDateFormat("yyyy-MM-dd").parse(leaseValidateTime)); //生效时间
				}
				splitResource.setCreateUserId(super.getUserIdStr()); 
				splitResource.setCreateTime(new Date());
				splitResource.setUpdateUserId(super.getUserIdStr());
				splitResource.setUpdateTime(new Date());
				splitResources.add(splitResource);
			}
			try {
				//调用 资源调整 服务 ，资源拆分 方法
				this.marketResourceAdjustmentService.adjustmentResourceSplit(marketResourceEntity, splitResources);
			} catch (BizException e) {
				e.printStackTrace();
			}
		}else{
			ajaxResult.setMsg(NO_MARKET);
		}
		return ajaxResult;
	}
	
	/**
	 * 资源合并，多个资源合并成一个资源
	 * @throws ParseException 
	 * @throws BizException 
	 * */
	@RequestMapping("resourceMerge")
	@ResponseBody
	public AjaxResult<Map<String, Object>> resourceMerge(HttpServletRequest	request,HttpServletResponse	response) throws ParseException, BizException{
		AjaxResult<Map<String,Object>> ajaxResult = new AjaxResult<>();
		if(null != getCurrentMarket()){
			//接受前端参数
			String leaseValidateTime = request.getParameter("leaseValidateTime");  //生效时间
			String ids = request.getParameter("resourceIds"); //要合并的资源 id 集合
			String shortCode = request.getParameter("shortCode"); //资源简码
			String builtArea = request.getParameter("builtArea"); //建筑面积
			String innerArea = request.getParameter("innerArea"); //套内面积
			String leaseArea = request.getParameter("leaseArea"); //出租面积
			String resourceTypeId = request.getParameter("resourceTypeId"); //资源类型id
			String rentMode = request.getParameter("rentMode"); //计租模式
			//要合并的多个源资源
			List<MarketResourceEntity> mergeResources = new ArrayList<>();
			for(String id:ids.split(",")){
				MarketResourceEntity	marketResourceEntity = new MarketResourceEntity();
				marketResourceEntity.setId(Integer.parseInt(id));
				marketResourceEntity.setOriginOperate(RESOURCE_MERGE_FLAG);  //设置被拆分 
				mergeResources.add(marketResourceEntity);
			}
			// 左边第一个源资源
			MarketResourceEntity originResource = new MarketResourceEntity();
			originResource.setId(mergeResources.get(0).getId());
			//获取数据库源资源
			originResource = this.marketResourceAdjustmentService.getResourceById(originResource);
			//合并后产生的新资源
			MarketResourceEntity mergeResource = new MarketResourceEntity();
			mergeResource.setCode(this.marketResourceAdjustmentService.getResourceCodeOrName(originResource.getMarketId(), originResource.getAreaId(), originResource.getBudingId(), originResource.getUnitId(), originResource.getFloorId(), shortCode));
			mergeResource.setShortCode(shortCode);
			mergeResource.setOrginCode(originResource.getCode());
			mergeResource.setOriginOperate(0);
			mergeResource.setName(this.marketResourceAdjustmentService.getResourceCodeOrName(originResource.getBudingId(), shortCode));
			mergeResource.setMarketId(originResource.getMarketId());
			mergeResource.setAreaId(originResource.getAreaId());
			mergeResource.setBudingId(originResource.getBudingId());
			mergeResource.setUnitId(originResource.getUnitId());
			mergeResource.setFloorId(originResource.getFloorId());
			if(null != builtArea && !"".equals(builtArea)){
				mergeResource.setBuiltArea(Double.parseDouble(builtArea));
			}
			if(null != innerArea && !"".equals(innerArea)){
				mergeResource.setInnerArea(Double.parseDouble(innerArea));
			}
			if(null != leaseArea && !"".equals(leaseArea)){
				mergeResource.setLeaseArea(Double.parseDouble(leaseArea));
			}
			if(null != resourceTypeId && !"".equals(resourceTypeId)){
				mergeResource.setResourceTypeId(getResourceTypeIdByMarketId(getCurrentMarket().getId(), resourceTypeId)); //资源类型
			}
			mergeResource.setRentMode(rentMode); //收租模式
			if(null != leaseValidateTime && !"".equals(leaseValidateTime)){
				mergeResource.setLeaseValidateTime(new SimpleDateFormat("yyyy-MM-dd").parse(leaseValidateTime)); //生效时间
			}
			mergeResource.setCreateUserId(super.getUserIdStr()); 
			mergeResource.setCreateTime(new Date());
			mergeResource.setUpdateUserId(super.getUserIdStr());
			mergeResource.setUpdateTime(new Date());
			try {
				//调用资源调整服务，资源合并方法
				this.marketResourceAdjustmentService.adjustmentResourceMerge(mergeResources, mergeResource);
			} catch (BizException e) {
				e.printStackTrace();
			}
		}else{
			ajaxResult.setMsg(NO_MARKET);
		}
		return ajaxResult;
	}
	
	/**
	 * 删除资源
	 * */
	@RequestMapping("delResourcesByIds")
	@ResponseBody
	public AjaxResult<Map<String, Object>> deleteResources(HttpServletRequest	request,HttpServletResponse	response){
		AjaxResult<Map<String,Object>> ajaxResult = new AjaxResult<>();
		String resourceIds = request.getParameter("resourceIds");
		List<MarketResourceEntity>	delResources = new ArrayList<>();
		for(String id:resourceIds.split(",")){
			MarketResourceEntity	marketResourceEntity = new MarketResourceEntity();
			marketResourceEntity.setId(Integer.parseInt(id));
			marketResourceEntity.setStatus(RESOURCE_DELETE_FLAG);
			delResources.add(marketResourceEntity);
		}
		try {
			this.marketResourceAdjustmentService.deleteResourcesByIds(delResources);
		} catch (BizException e) {
			e.printStackTrace();
		}
		return ajaxResult;
	}
	
	/**
	 * 通过 id 获取资源详情
	 * */
	@RequestMapping("getResourceById")
	@ResponseBody
	public AjaxResult<MarketResourceEntity> getResourceById(HttpServletRequest	request,HttpServletResponse	response){
		AjaxResult<MarketResourceEntity> ajaxResult = new AjaxResult<>();
		String id = request.getParameter("resourceId");
		MarketResourceEntity marketResourceEntity = new MarketResourceEntity();
		marketResourceEntity.setId(Integer.parseInt(id));
		try {
			ajaxResult.setResult(this.marketResourceAdjustmentService.getResourceById(marketResourceEntity));
		} catch (BizException e) {
			e.printStackTrace();
		}
		return ajaxResult;
	}
	
	/**
	 * 通过 ids 获取资源详情
	 * */
	@RequestMapping("getResourceByIds")
	@ResponseBody
	public AjaxResult<List<MarketResourceEntity>> getResourceByIds(HttpServletRequest	request,HttpServletResponse	response){
		AjaxResult<List<MarketResourceEntity>> ajaxResult = new AjaxResult<>();
		String ids = request.getParameter("resourceIds");
		List<MarketResourceEntity> list = new ArrayList<>();
		for(String id:ids.split(",")){
			MarketResourceEntity marketResourceEntity = new MarketResourceEntity();
			marketResourceEntity.setId(Integer.parseInt(id));
			try {
				list.add(this.marketResourceAdjustmentService.getResourceById(marketResourceEntity));
			} catch (BizException e) {
				e.printStackTrace();
			}
		}
		ajaxResult.setResult(list);
		return ajaxResult;
	}
	
	/**
	 * 通过 楼栋 id 返回 楼栋资源调整的 表格 json 数据
	 * */
	@RequestMapping("getResourceAdjustTable")
	@ResponseBody
	public AjaxResult<Map<String, Object>> getResourceAdjustTable(HttpServletRequest	request,HttpServletResponse	response){
		AjaxResult<Map<String,Object>> ajaxResult = new AjaxResult<>();
		String buildId = request.getParameter("buildId");
		MarketAreaBuildingEntity	marketAreaBuildingEntity = new MarketAreaBuildingEntity();
		marketAreaBuildingEntity.setId(Integer.parseInt(buildId));
		try {
			ajaxResult.setResult(this.marketResourceAdjustmentService.getBuildResources(marketAreaBuildingEntity));
		} catch (BizException e) {
			e.printStackTrace();
		}
		return ajaxResult;
	}
}
 