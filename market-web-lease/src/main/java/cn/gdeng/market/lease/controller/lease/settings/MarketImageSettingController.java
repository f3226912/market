package cn.gdeng.market.lease.controller.lease.settings;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import cn.gdeng.market.dto.admin.SysUserDTO;
import cn.gdeng.market.dto.bean.GuDengPage;
import cn.gdeng.market.dto.lease.common.StatisticsDTO;
import cn.gdeng.market.dto.lease.resources.MarketAreaBuildingDTO;
import cn.gdeng.market.dto.lease.resources.MarketAreaDTO;
import cn.gdeng.market.dto.lease.resources.MarketResourceDTO;
import cn.gdeng.market.dto.lease.resources.MarketUnitFloorDTO;
import cn.gdeng.market.dto.lease.settings.MarketDTO;
import cn.gdeng.market.dto.lease.settings.MarketImageSettingDTO;
import cn.gdeng.market.dto.lease.settings.MarketResourceTypeDTO;
import cn.gdeng.market.dto.lease.settings.ResourceContractDTO;
import cn.gdeng.market.dto.lease.settings.ResourceResponseDTO;
import cn.gdeng.market.dto.lease.settings.ResourceTypeDTO;
import cn.gdeng.market.entity.lease.resources.MarketAreaBuildingEntity;
import cn.gdeng.market.entity.lease.settings.MarketImageSettingEntity;
import cn.gdeng.market.exception.BizException;
import cn.gdeng.market.lease.bean.AjaxResult;
import cn.gdeng.market.lease.controller.base.BaseController;
import cn.gdeng.market.lease.enums.MyConst;
import cn.gdeng.market.service.lease.bi.BiContractMainService;
import cn.gdeng.market.service.lease.resources.MarketAreaBuildingService;
import cn.gdeng.market.service.lease.resources.MarketAreaService;
import cn.gdeng.market.service.lease.resources.MarketResourceService;
import cn.gdeng.market.service.lease.resources.MarketUnitFloorService;
import cn.gdeng.market.service.lease.settings.MarketImageSettingService;
import cn.gdeng.market.service.lease.settings.MarketResourceTypeService;
import cn.gdeng.market.service.lease.settings.MarketService;
import cn.gdeng.market.util.PropertyUtil;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
/**
 * 市场平面图管理controller
 * @author youj
 *
 */
@Controller
@RequestMapping("graph")
public class MarketImageSettingController extends BaseController {
	
	@Resource
	MarketImageSettingService marketImageSettingService;
	@Resource 
	MarketAreaService marketAreaService;
	@Resource
	MarketResourceTypeService marketResourceTypeService;
	@Resource
	MarketService marketSerivce;
	@Resource
	MarketAreaBuildingService marketAreaBuildingService;
	@Resource
	MarketUnitFloorService marketUnitFloorService;
	@Resource
	MarketResourceService marketResourceService;
	@Resource
	BiContractMainService biContractMainService;
	
	@Autowired
	private PropertyUtil gdProperties;
	
	@RequestMapping("view")
	public ModelAndView view(ModelAndView mv) throws BizException {
		mv.setViewName("settings/marketimage/index");
		return mv;
	}
	
	/**
	 * 上传市场平面图
	 * @param request
	 * @param file
	 * @return
	 * @throws Exception
	 */
	
	@RequestMapping("toUpload")
	public ModelAndView toUpload(ModelAndView mv) throws BizException {
		mv.setViewName("settings/marketimage/upload_file");
		return mv;
	}
	
	@RequestMapping("upload")
	@ResponseBody
	public  AjaxResult<Integer>  upload(MarketDTO dto, ModelAndView mv) throws BizException{
		AjaxResult<Integer> res = new AjaxResult<>();
		Integer marketId = super.getCurrentMarket().getId();
		SysUserDTO user = super.getSysUser();
		MarketDTO market = new MarketDTO();
		market.setId(marketId);
		market.setMarketImage(dto.getMarketImage());
		market.setUpdateUserId(user.getId().toString());
		int result=marketSerivce.update(market);
		res.setResult(result);
		return res;
	}
	
	/**
	 * 市场平面图主页面
	 * @param request
	 * @param file
	 * @return
	 * @throws Exception
	 */
	@ResponseBody
	@RequestMapping("listMarketImage")
	public Map<String, Object> listMarketImage(HttpServletRequest request, @RequestBody JSONObject jsonObject) throws BizException {
		Map<String, Object> retmap = new HashMap<String, Object>();
		Map<String, List<StatisticsDTO>> map = new HashMap<String, List<StatisticsDTO>>();
		Properties properties = gdProperties.getProperties();
		String host = properties.getProperty("gd.uplaod.host");
		String marketId = jsonObject.getString("marketId");
		MarketDTO market = marketSerivce.getById(marketId);
		List<MarketResourceTypeDTO> resTypes = marketResourceTypeService.queryByMarket(marketId);
		List<ResourceTypeDTO> rtds = new ArrayList<ResourceTypeDTO>();
		String imageUrl = null;
		// 查询市场下的所有区域
		List<MarketAreaDTO> areas = marketAreaService.queryByMarket(marketId);
		
		if(resTypes == null){
			resTypes = new ArrayList<MarketResourceTypeDTO>();
		}
		resTypes.add(new MarketResourceTypeDTO()); //用于统计所有资源类型数据
		// 资源使用情况统计
		for (MarketResourceTypeDTO type : resTypes) {
			List<StatisticsDTO> list = new ArrayList<StatisticsDTO>();
			String typeId = type.getId() == null ? "" : type.getId().toString();
			StatisticsDTO marketData = marketSerivce.marketResourceStatistics(marketId, typeId);
			list.add(marketData);
			for (MarketAreaDTO area : areas) {
				String areaId = area.getId().toString();
				StatisticsDTO areaData = marketAreaService.areaResourceStatistics(areaId, typeId, true); //已经描点的区域统计
				if(areaData == null){
					areaData = new StatisticsDTO();
				}
				areaData.setId(areaId);
				areaData.setName(area.getName());
				if (!StringUtils.isEmpty(areaData.getCoordinate())) {
					areaData.setX(areaData.getCoordinate().split(",")[0]);
					areaData.setY(areaData.getCoordinate().split(",")[1]);
				}
				list.add(areaData);
			}
			map.put(type.getCode() == null ? "All" : type.getCode(), list);
			
			//市场资源类型
			ResourceTypeDTO rtd = new ResourceTypeDTO();
			if(type.getId() != null){
			  rtd.setId(type.getId().toString());
			  rtd.setCode(type.getCode());
			  rtd.setName(type.getName());
			  rtds.add(rtd);
			}
		}
		if(market != null){
	        imageUrl = market.getMarketImage();
		    if(!StringUtils.isEmpty(imageUrl)){
			    imageUrl = host + imageUrl;
		    }
		}
		retmap.put("extra", "");
		retmap.put("msg", "success");
		retmap.put("imageUrl", imageUrl);
		retmap.put("statusCode", "0");
		retmap.put("object", map);
		retmap.put("resTypes", rtds);
		JSONObject json = JSONObject.fromObject(retmap); 
		logger.info("市场平面图主页面返回json:" + json.toString());
        return retmap;
	}
	
	/**
	 * 待选区域列表
	 * @param request
	 * @return
	 * @throws Exception 
	 */
	@RequestMapping("listAreas")
	@ResponseBody
	public AjaxResult<GuDengPage<MarketAreaDTO>> listAreas(HttpServletRequest request, MarketAreaDTO entity) throws BizException {
		AjaxResult<GuDengPage<MarketAreaDTO>> res = new AjaxResult<>();
		//获取分页信息
		GuDengPage<MarketAreaDTO> page = super.getPageInfoByRequest(entity);
		Map<String, Object> paraMap = page.getParaMap();
		//设置查询参数 
		paraMap.put("marketId", super.getCurrentMarket().getId());
		page.setParaMap(paraMap);
		page = marketAreaService.queryByPage(page);
		res.setResult(page);
		return res;
	}
	
	
	/**
	 * 保存区域资源描点
	 * @param request
	 * @return
	 * @throws Exception 
	 */
	@RequestMapping("saveAreaPoint")
	@ResponseBody
	public Map<String, String> saveAreaPoint(HttpServletRequest request, @RequestBody JSONObject jsonObject) throws BizException{
		Map<String, String> map = new HashMap<String, String>();
		String marketId = jsonObject.getString("marketId");
		JSONArray array = jsonObject.getJSONArray("areaList");
		for(int i = 0 ; i < array.size() ; i ++){
			MarketImageSettingEntity entity = new MarketImageSettingEntity();
			JSONObject obj = array.getJSONObject(i);
			String areaId = obj.getString("areaId");
			String coordinate = obj.getString("x") + "," + obj.getString("y");
			Map<String, Object> param = new HashMap<String, Object>();
			param.put("marketId", marketId);
			param.put("areaId", areaId);
			param.put("imageType", MyConst.IMG_MARKET); //在市场平面图上描的区域,图片类型属于市场类型.
			MarketImageSettingDTO marketImage = marketImageSettingService.queryByCondition(param);
			entity.setMarketId(new Integer(marketId));
			entity.setAreaId(new Integer(areaId));
			if(marketImage == null){ //新增资源描点
				Date date = new Date();
				SysUserDTO user = super.getSysUser();
				entity.setCoordinate(coordinate);
				entity.setCreateUserId(user.getId().toString());
				entity.setUpdateUserId(user.getId().toString());
				entity.setCreateTime(date);
				entity.setUpdateTime(date);
				entity.setImageType(MyConst.IMG_MARKET);
				marketImageSettingService.addMarketImageSetting(entity);
			}else{
				if(StringUtils.isEmpty(obj.getString("x"))
						&& StringUtils.isEmpty(obj.getString("y"))){ //无坐标信息则删除描点信息
					marketImageSettingService.delMarketImageSetting(param);
				}else{
					MarketImageSettingDTO image = new MarketImageSettingDTO();
					image.setMarketId(new Integer(marketId));
					image.setAreaId(new Integer(areaId));
					image.setImageType(MyConst.IMG_MARKET);
					image.setCoordinate(coordinate); //更新描点坐标
			        marketImageSettingService.updateCoordinate(image);
				}
			}
		}
		map.put("extra", "");
		map.put("msg", "success");
		map.put("statusCode", "0");
		return map;
	}
	
	
	/**
	 * 上传区域平面图
	 * @param request
	 * @param file
	 * @return
	 * @throws Exception
	 */
	
	@RequestMapping("toUploadArea")
	public ModelAndView toUploadArea(ModelAndView mv) throws BizException {
		mv.setViewName("settings/marketimage/upload_file");
		return mv;
	}
	
	@RequestMapping("uploadArea")
	@ResponseBody
	public AjaxResult<Integer> uploadArea(MarketAreaDTO dto) throws BizException {
		AjaxResult<Integer> result = new AjaxResult<Integer>();
		SysUserDTO user = super.getSysUser();
		MarketAreaDTO area = new MarketAreaDTO();
		area.setId(new Integer(dto.getAreaId()));
		area.setAreaImage(dto.getAreaImage());
		area.setUpdateUserId(user.getId().toString());
		marketAreaService.update(area);
		result.setIsSuccess(true);
		return result;
	}
	
	/**
	 * 区域平面图主页面
	 * @param request
	 * @param file
	 * @return
	 * @throws Exception
	 */
	@ResponseBody
	@RequestMapping("listAreaImage")
	public Map<String, Object> listAreaImage(HttpServletRequest request, @RequestBody JSONObject jsonObject) throws BizException {
		
		Map<String, Object> retmap = new HashMap<String, Object>();
		Properties properties = gdProperties.getProperties();
		String host = properties.getProperty("gd.uplaod.host");
		Map<String, List<Map<String, Object>>> map = new HashMap<String, List<Map<String, Object>>>();
		String marketId = jsonObject.getString("marketId");
		// 查询市场包括的资源类型
		List<MarketResourceTypeDTO> resTypes = marketResourceTypeService.queryByMarket(marketId);
		// 查询市场下的所有区域
		List<MarketAreaDTO> areas = marketAreaService.queryByMarket(marketId);
		List<ResourceTypeDTO> rtds = new ArrayList<ResourceTypeDTO>();
		// 资源使用情况统计
		for (MarketResourceTypeDTO type : resTypes) {
			List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
			String typeId = type.getId().toString();
			for (MarketAreaDTO area : areas) {
				List<Object> areaList = new ArrayList<Object>();
				Map<String, Object> ret_map = new HashMap<String, Object>();
				String areaId = area.getId().toString();
				StatisticsDTO areaData = marketAreaService.areaResourceStatistics(areaId, typeId, false);
				if(areaData == null){
					areaData = new StatisticsDTO();
				}
				if (!StringUtils.isEmpty(areaData.getCoordinate())) {
					areaData.setX(areaData.getCoordinate().split(",")[0]);
					areaData.setY(areaData.getCoordinate().split(",")[1]);
				}
				areaList.add(areaData);
				// 查询区域下所有楼栋
				Map<String, Object> param = new HashMap<String, Object>();
				param.put("areaId", area.getId());
				List<MarketAreaBuildingDTO> buildings = marketAreaBuildingService.getBuilding(param);
				for(MarketAreaBuildingDTO building: buildings){
					Map<String, Object> b_param = new HashMap<String, Object>();
					b_param.put("areaId", area.getId());
					b_param.put("budingId", building.getId());
					b_param.put("resourceTypeId", typeId);
					StatisticsDTO buildingData = marketAreaBuildingService.buildingResourceStatistics(b_param);
					if(buildingData == null){
						buildingData = new StatisticsDTO();
					}
					if (!StringUtils.isEmpty(buildingData.getCoordinate())) {
						buildingData.setX(buildingData.getCoordinate().split(",")[0]);
						buildingData.setY(buildingData.getCoordinate().split(",")[1]);
					}
					buildingData.setId(building.getId().toString());
					buildingData.setName(building.getName());
					areaList.add(buildingData);
				}
				String imageUrl = area.getAreaImage();
				if(!StringUtils.isEmpty(imageUrl)){
					imageUrl = host + imageUrl;
				}
				ret_map.put("id", areaId);
				ret_map.put("name", area.getName());
				ret_map.put("imageUrl", imageUrl);
				ret_map.put("areaList", areaList);
				list.add(ret_map);
			}
			map.put(type.getCode(), list);
			//市场资源类型
			ResourceTypeDTO rtd = new ResourceTypeDTO();
			rtd.setId(type.getId().toString());
			rtd.setCode(type.getCode());
			rtd.setName(type.getName());
			rtds.add(rtd);
		}
		retmap.put("extra", "");
		retmap.put("msg", "success");
		retmap.put("statusCode", "0");
		retmap.put("object", map);
		retmap.put("resTypes", rtds);
		JSONObject json = JSONObject.fromObject(retmap); 
		logger.info("区域平面图主页面返回json:" + json.toString());
        return retmap;
	}
	
	/**
	 * 待选楼栋列表
	 * @param request
	 * @return
	 * @throws Exception 
	 */
	@RequestMapping("listBuildings")
	@ResponseBody
	public AjaxResult<GuDengPage<MarketAreaBuildingDTO>> listBuildings(HttpServletRequest request, MarketAreaBuildingDTO entity) throws BizException {
		AjaxResult<GuDengPage<MarketAreaBuildingDTO>> res = new AjaxResult<>();
		//获取分页信息
		GuDengPage<MarketAreaBuildingDTO> page = super.getPageInfoByRequest(entity);
		Map<String, Object> paraMap = page.getParaMap();
		//设置查询参数 
		paraMap.put("marketId", super.getCurrentMarket().getId());
		paraMap.put("areaId", entity.getAreaId());
		page.setParaMap(paraMap);
		page = marketAreaBuildingService.queryBuilding(page);
		res.setResult(page);
		return res;
	}
	
	/**
	 * 保存楼栋资源描点
	 * @param request
	 * @return
	 * @throws Exception 
	 */
	@RequestMapping("saveBuildingPoint")
	@ResponseBody
	public Map<String, String> saveBuildingPoint(HttpServletRequest request, @RequestBody JSONObject jsonObject) throws BizException{
		Map<String, String> map = new HashMap<String, String>();
		String marketId = jsonObject.getString("marketId");
		String areaId = jsonObject.getString("areaId");
		JSONArray array = jsonObject.getJSONArray("buildingList");
		for(int i = 0 ; i < array.size() ; i ++){
			JSONObject obj = array.getJSONObject(i);
			String coordinate = obj.getString("x") + "," + obj.getString("y");
			String buildingId = obj.getString("id");
			Map<String, Object> param = new HashMap<String, Object>();
			param.put("marketId", marketId);
			param.put("areaId", areaId);
			param.put("buildingId", buildingId);
			param.put("imageType", MyConst.IMG_AREA);
			MarketImageSettingDTO image = marketImageSettingService.queryByCondition(param);
			if(image == null){
				Date date = new Date();
				MarketImageSettingEntity entity = new MarketImageSettingEntity();
				SysUserDTO user = super.getSysUser();
				entity.setMarketId(new Integer(marketId));
				entity.setAreaId(new Integer(areaId));
				entity.setBuildingId(new Integer(buildingId));
				entity.setCoordinate(coordinate);
				entity.setImageType(MyConst.IMG_AREA);
				entity.setCreateTime(date);
				entity.setUpdateTime(date);
				entity.setCreateUserId(user.getId().toString());
				entity.setUpdateUserId(user.getId().toString());
				marketImageSettingService.addMarketImageSetting(entity);
			}else{
				if(StringUtils.isEmpty(obj.getString("x"))
						&&StringUtils.isEmpty(obj.getString("y"))){
					marketImageSettingService.delMarketImageSetting(param);
				}else{
					MarketImageSettingDTO dto = new MarketImageSettingDTO();
					dto.setMarketId(new Integer(marketId));
					dto.setAreaId(new Integer(areaId));
					dto.setImageType(MyConst.IMG_AREA);
					dto.setCoordinate(coordinate);
					dto.setBuildingId(new Integer(buildingId));
			        marketImageSettingService.updateCoordinate(dto);
				}
			}
		}
		map.put("extra", "");
		map.put("msg", "success");
		map.put("statusCode", "0");
		return map;
	}
	
	
	/**
	 * 上传楼层平面图
	 * @param request
	 * @param file
	 * @return
	 * @throws Exception
	 */
	
	@RequestMapping("toUploadFloor")
	public ModelAndView toUploadFloor(ModelAndView mv) throws Exception {
		mv.setViewName("settings/marketimage/upload_file");
		return mv;
	}
	
	@RequestMapping("uploadFloor")
	public AjaxResult<Integer> uploadFloor(MarketUnitFloorDTO dto) throws BizException {
		AjaxResult<Integer> result = new AjaxResult<Integer>();
		SysUserDTO user = super.getSysUser();
		MarketUnitFloorDTO unitFloor = new MarketUnitFloorDTO();
		unitFloor.setId(new Integer(dto.getFloorId()));
		unitFloor.setFloorImage(dto.getFloorImage());
		unitFloor.setUpdateUserId(user.getId().toString());
		marketUnitFloorService.update(unitFloor);
		result.setIsSuccess(true);
		return result;
	}
	
	
	/**
	 * 楼层平面图主页面
	 * @param request
	 * @param file
	 * @return
	 * @throws Exception
	 */
	@ResponseBody
	@RequestMapping("listFloorImage") 
	public Map<String, Object> listFloorImage(HttpServletRequest request, @RequestBody JSONObject jsonObject) throws BizException {
		
		//获取系统属性
		Properties properties = gdProperties.getProperties();
		String host = properties.getProperty("gd.uplaod.host");
		
		String marketId = jsonObject.getString("marketId");
		String buildingId = jsonObject.getString("buildingId");
		Map<String, Object> retmap = new HashMap<String, Object>();
		Map<String, List<Map<String, Object>>> map = new HashMap<String, List<Map<String, Object>>>();
		MarketAreaBuildingEntity building = marketAreaBuildingService.queryBuildingById(buildingId);
		// 查询市场包括的资源类型
		List<MarketResourceTypeDTO> resTypes = marketResourceTypeService.queryByMarket(marketId);
		// 查询市场下的所有区域
		Map<String, Object> param = new HashMap<String, Object>();
		param.put("buildingId", buildingId);
		param.put("status", 1);
		List<MarketUnitFloorDTO> floors = marketUnitFloorService.getFloor(param);
		List<ResourceTypeDTO> rtds = new ArrayList<ResourceTypeDTO>();
		
		// 资源使用情况统计
		for (MarketResourceTypeDTO type : resTypes) {
			List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
			String typeId = type.getId().toString();
			for (MarketUnitFloorDTO floor : floors) {
				Map<String, Object> ret_map = new HashMap<String, Object>();
				List<Object> floorList = new ArrayList<Object>();
				String floorId = floor.getId().toString();
				StatisticsDTO floorData = marketUnitFloorService.floorResourceStatistics(floorId, typeId);
				floorList.add(floorData);
				//查询楼层下所有资源
				Map<String, Object> resparam = new HashMap<String, Object>();
				resparam.put("marketId", marketId);
				resparam.put("budingId", buildingId);
				resparam.put("floorId", floorId);
				resparam.put("resourceTypeId", typeId);
				resparam.put("originOperate", 0); //已创建的资源
				List<MarketResourceDTO> resources = marketResourceService.getResources(resparam);
				for(MarketResourceDTO dto : resources){
					ResourceContractDTO contract = marketResourceService.queryByResourceId(dto.getId().toString());
					ResourceResponseDTO res = new ResourceResponseDTO();
					res.setId(dto.getId().toString());
					res.setName(dto.getName());
					res.setShortCode(dto.getShortCode());
					res.setLeaseStatus(dto.getLeaseStatusName());
					res.setLeaseArea(dto.getLeaseArea() == null ? null : dto.getLeaseArea().toString());
					if (!StringUtils.isEmpty(dto.getCoordinate())) {
						res.setX(dto.getCoordinate().split(",")[0]);
						res.setY(dto.getCoordinate().split(",")[1]);
					}
					if(contract != null){
						res.setContractId(contract.getId().toString());
						res.setCustomerName(contract.getCustomerName());
						res.setCustomerMobile(contract.getCustomerMobile());
					}
					floorList.add(res);
				}
				
				String imageUrl = floor.getFloorImage();
				if(!StringUtils.isEmpty(imageUrl)){
					imageUrl = host + imageUrl;
				}
				ret_map.put("floorList", floorList);
				ret_map.put("id", floor.getId());
				ret_map.put("name", floor.getName());
				ret_map.put("imageUrl", imageUrl);
				list.add(ret_map);
			}
			map.put(type.getCode(), list);
			//市场资源类型
			ResourceTypeDTO rtd = new ResourceTypeDTO();
			rtd.setId(type.getId().toString());
			rtd.setCode(type.getCode());
			rtd.setName(type.getName());
			rtds.add(rtd);
		}
		retmap.put("extra", "");
		retmap.put("msg", "success");
		retmap.put("statusCode", "0");
		retmap.put("building", building.getName());
		retmap.put("object", map);
		retmap.put("resTypes", rtds);
		JSONObject json = JSONObject.fromObject(retmap); 
		logger.info("楼层平面图主页面返回json:" + json.toString());
        return retmap;
	}
	
	/**
	 * 待选资源列表
	 * @param request
	 * @return
	 * @throws Exception 
	 */
	@RequestMapping("listResources")
	@ResponseBody
	public AjaxResult<GuDengPage<MarketResourceDTO>> listResources(HttpServletRequest request, MarketResourceDTO entity) throws BizException {
		AjaxResult<GuDengPage<MarketResourceDTO>> res = new AjaxResult<>();
		//获取分页信息
		GuDengPage<MarketResourceDTO> page = super.getPageInfoByRequest(entity);
		Map<String, Object> paraMap = page.getParaMap();
		//设置查询参数 
		paraMap.put("buildingId", entity.getBudingId());
		paraMap.put("floorId", entity.getFloorId());
		paraMap.put("originOperate", 0);
		page.setParaMap(paraMap);
		page = marketResourceService.queryByPage(page);
		res.setResult(page);
		return res;
	}
	
	/**
	 * 保存资源描点
	 * @param request
	 * @return
	 * @throws Exception 
	 */
	@RequestMapping("saveResourcePoint")
	@ResponseBody
	public Map<String, String> saveResourcePoint(HttpServletRequest request, @RequestBody JSONObject jsonObject) throws BizException{
		Map<String, String> map = new HashMap<String, String>();
		Map<String, Object> param = new HashMap<String, Object>();
		String marketId = jsonObject.getString("marketId");
		String areaId = jsonObject.getString("areaId");
		String buildingId = jsonObject.getString("buildingId");
		String floorId = jsonObject.getString("floorId");
		param.put("marketId", marketId);
		param.put("areaId", areaId);
		param.put("buildingId", buildingId);
		param.put("floorId", floorId);
		param.put("imageType", MyConst.IMG_FLOOR);
		JSONArray array = jsonObject.getJSONArray("resList");
		for(int i = 0 ; i < array.size() ; i ++){
			MarketImageSettingEntity entity = new MarketImageSettingEntity();
			JSONObject obj = array.getJSONObject(i);
			String coordinate = obj.getString("x") + "," + obj.getString("y");
			int resourceId = obj.getInt("id");
			param.put("resourceId", resourceId);
			MarketImageSettingDTO image = marketImageSettingService.queryByCondition(param);
			if(image == null){
				Date date = new Date();
				SysUserDTO user = super.getSysUser();
				entity.setMarketId(new Integer(marketId));
				entity.setAreaId(new Integer(areaId));
				entity.setBuildingId(new Integer(buildingId));
				entity.setFloorId(new Integer(floorId));
				entity.setResourceId(resourceId);
				entity.setCoordinate(coordinate);
				entity.setImageType(MyConst.IMG_FLOOR);
				entity.setCreateTime(date);
				entity.setUpdateTime(date);
				entity.setCreateUserId(user.getId().toString());
				entity.setUpdateUserId(user.getId().toString());
				marketImageSettingService.addMarketImageSetting(entity);
			}else{
				if(StringUtils.isEmpty(obj.getString("x")) && 
						StringUtils.isEmpty(obj.getString("y"))){
					marketImageSettingService.delMarketImageSetting(param);
				}else{
					MarketImageSettingDTO dto = new MarketImageSettingDTO();
					dto.setMarketId(new Integer(marketId));
					dto.setAreaId(new Integer(areaId));
					dto.setBuildingId(new Integer(buildingId));
					dto.setFloorId(new Integer(floorId));
					dto.setResourceId(resourceId);
					dto.setCoordinate(coordinate);
					dto.setImageType(MyConst.IMG_FLOOR);
			        marketImageSettingService.updateCoordinate(dto);
				}
			}
		}
		map.put("extra", "");
		map.put("msg", "success");
		map.put("statusCode", "0");
		return map;
	}
}
