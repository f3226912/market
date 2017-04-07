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
import cn.gdeng.market.dto.lease.resources.MarketAreaBuildingDTO;
import cn.gdeng.market.dto.lease.resources.MarketAreaDTO;
import cn.gdeng.market.dto.lease.resources.MarketResourceDTO;
import cn.gdeng.market.dto.lease.resources.MarketUnitFloorDTO;
import cn.gdeng.market.dto.lease.settings.BaseInfoDTO;
import cn.gdeng.market.dto.lease.settings.MarketDTO;
import cn.gdeng.market.dto.lease.settings.MarketImageSettingDTO;
import cn.gdeng.market.dto.lease.settings.MarketResourceTypeDTO;
import cn.gdeng.market.dto.lease.settings.PlaneDrawDot;
import cn.gdeng.market.dto.lease.settings.ResourceContractDTO;
import cn.gdeng.market.dto.lease.settings.ResourcePlaneDrawDot;
import cn.gdeng.market.dto.lease.settings.ResourceResponseDTO;
import cn.gdeng.market.dto.lease.settings.ResourceTypeDTO;
import cn.gdeng.market.dto.lease.settings.SummaryDTO;
import cn.gdeng.market.dto.lease.settings.SummaryNodeDTO;
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
@RequestMapping("planeGraph")
public class MarketGraphSettingController extends BaseController {
	
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
	
	
	@RequestMapping("index")
	public ModelAndView index(ModelAndView mv) throws BizException {
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
	public  AjaxResult<Integer> upload(MarketDTO dto, ModelAndView mv) throws BizException{
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
		Properties properties = gdProperties.getProperties();
		String host = properties.getProperty("gd.uplaod.host");
		String marketId = jsonObject.getString("marketId");
		Object typeIdObject = jsonObject.get("typeId");
		String typeId = typeIdObject == null ? "": typeIdObject.toString();
		MarketDTO market = marketSerivce.getById(marketId);
		List<MarketResourceTypeDTO> resTypes = marketResourceTypeService.queryByMarket(marketId);
		List<ResourceTypeDTO> rtds = new ArrayList<ResourceTypeDTO>();
		List<PlaneDrawDot> dots = new ArrayList<PlaneDrawDot>();
		String imageUrl = null;
		// 查询市场下已描点的所有区域
		if (StringUtils.isEmpty(typeId)) {
			List<MarketAreaDTO> areas = marketAreaService.queryAreaDotByMarket(marketId);
			for (MarketAreaDTO area : areas) {
				PlaneDrawDot drawDot = new PlaneDrawDot();
				drawDot.setId(area.getId().toString());
				drawDot.setName(area.getName());
				drawDot.setX(area.getCoordinate().split(",")[0]);
				drawDot.setY(area.getCoordinate().split(",")[1]);
				dots.add(drawDot);
			}
		}
		// 市场资源汇总统计
		SummaryDTO summaryData = marketSerivce.marketResourceSummary(marketId, typeId);
		//市场资源类型
		if (StringUtils.isEmpty(typeId)) {  //如果首次加载 没有resTypeId则返回当前市场下的所有资源类型
			for (MarketResourceTypeDTO type : resTypes) {
				ResourceTypeDTO rtd = new ResourceTypeDTO();
				if (type.getId() != null) {
					rtd.setId(type.getId().toString());
					rtd.setCode(type.getCode());
					rtd.setName(type.getName());
					rtds.add(rtd);
				}
			}
		}
		
		if (market != null) {
			imageUrl = market.getMarketImage();
			if (!StringUtils.isEmpty(imageUrl)) {
				imageUrl = host + imageUrl;
			}
		}
		retmap.put("statusCode", "0");
		retmap.put("msg", "success");
		retmap.put("imageUrl", imageUrl);
		if(rtds != null && rtds.size() > 0){
		    retmap.put("resTypes", rtds);
		}
		if(StringUtils.isEmpty(typeId)){
		    retmap.put("areas", dots);
		}
		retmap.put("summary", summaryData);
		JSONObject json = JSONObject.fromObject(retmap); 
		logger.info("市场平面图主页面返回json:" + json.toString());
        return retmap;
	}
	
	/**
	 * 市场平面图资源统计
	 * @param request
	 * @param file
	 * @return
	 * @throws Exception
	 */
	@ResponseBody
	@RequestMapping("marketResStatistics")
	public Map<String, Object> marketResStatistics(HttpServletRequest request, @RequestBody JSONObject jsonObject) throws BizException {
		Map<String, Object> retmap = new HashMap<String, Object>();
		String marketId = jsonObject.getString("marketId");
		Object typeIdObject = jsonObject.get("typeId");
		String typeId = typeIdObject == null ? "": typeIdObject.toString();
		List<SummaryNodeDTO> nodes = new ArrayList<SummaryNodeDTO>();
		// 区域资源统计
		if (jsonObject.get("areaIds") != null) { //areaIds不为空 统计相应区域的资源数据
			JSONArray areaIds = jsonObject.getJSONArray("areaIds");
			for (int i = 0; i < areaIds.size(); i++) {
				String areaId = areaIds.getString(i);
				composeMarketResource(typeId, nodes, areaId);
			}
		}else{ //areaIds为空统计该市场下所有已经描点的区域资源数据
			List<MarketAreaDTO> areas = marketAreaService.queryAreaDotByMarket(marketId);
			for(MarketAreaDTO area : areas){
				composeMarketResource(typeId, nodes, area.getId().toString());
			}
		}
		
		retmap.put("statusCode", "0");
		retmap.put("msg", "success");
		retmap.put("data", nodes);
		
		JSONObject json = JSONObject.fromObject(retmap); 
		logger.info("市场平面图资源统计返回json:" + json.toString());
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
	public Map<String, Object> listAreas(HttpServletRequest request, @RequestBody JSONObject jsonObject) throws BizException {
		Map<String, Object> retmap = new HashMap<String, Object>();
		List<BaseInfoDTO> list = new ArrayList<BaseInfoDTO>(); 
		String marketId = jsonObject.getString("marketId");
		List<MarketAreaDTO> areas = marketAreaService.queryNoDotByMarket(marketId);
		if (areas != null && areas.size() > 0) {
			for (MarketAreaDTO area : areas) {
				BaseInfoDTO info = new BaseInfoDTO();
				info.setId(area.getId().toString());
				info.setName(area.getName());
				list.add(info);
			}
		}
		retmap.put("statusCode", "0");
		retmap.put("msg", "success");
		retmap.put("areaList", list);
		JSONObject json = JSONObject.fromObject(retmap); 
		logger.info("待选区域列表返回json:" + json.toString());
        return retmap;
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
			String areaId = obj.getString("id");
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
	public Map<String, Object> listAreaImage(HttpServletRequest request, @RequestBody JSONObject jsonObject)
			throws BizException {
		Map<String, Object> retmap = new HashMap<String, Object>();
		Properties properties = gdProperties.getProperties();
		String host = properties.getProperty("gd.uplaod.host");
		String marketId = jsonObject.getString("marketId");
		Object typeIdObject = jsonObject.get("typeId");
		Object areaIdObject = jsonObject.get("areaId");
		// 查询市场包括的资源类型
		List<MarketResourceTypeDTO> resTypes = marketResourceTypeService.queryByMarket(marketId);
		// 查询市场下的所有区域
		List<MarketAreaDTO> areas = marketAreaService.queryByMarket(marketId);
		List<ResourceTypeDTO> types = new ArrayList<ResourceTypeDTO>();
		List<BaseInfoDTO> infos = new ArrayList<BaseInfoDTO>();
		List<PlaneDrawDot> buildingDots = new ArrayList<PlaneDrawDot>();
		String defaultTypeId = null;
		String defaultAreaId = null;
		String imageUrl = null;
		MarketAreaDTO marketArea = new MarketAreaDTO();
		// 默认情况下统计第一个区域的第一个资源类型.
		if (typeIdObject == null) {
			if (resTypes != null && resTypes.size() > 0) {
				defaultTypeId = resTypes.get(0).getId().toString();
			}
			for (MarketResourceTypeDTO type : resTypes) {// 市场资源类型初始化
				ResourceTypeDTO rtd = new ResourceTypeDTO();
				rtd.setId(type.getId().toString());
				rtd.setCode(type.getCode());
				rtd.setName(type.getName());
				types.add(rtd);
			}
		} else {
			defaultTypeId = typeIdObject.toString();
		}

		if (areaIdObject == null) {
			if (areas != null && areas.size() > 0) {
				defaultAreaId = areas.get(0).getId().toString();
			}
			for (MarketAreaDTO area : areas) {// 当前市场下的所有区域初始化
				BaseInfoDTO baseInfo = new BaseInfoDTO();
				baseInfo.setId(area.getId().toString());
				baseInfo.setName(area.getName());
				infos.add(baseInfo);
			}
		} else {
			defaultAreaId = areaIdObject.toString();
		}

		// 当前区域下已经描点的楼栋
		Map<String, Object> param = new HashMap<String, Object>();
		param.put("areaId", defaultAreaId);
		List<MarketAreaBuildingDTO> buildings = marketAreaBuildingService.queryBuildingDotByCondition(param);
		for (MarketAreaBuildingDTO building : buildings) {
			PlaneDrawDot buildingDot = new PlaneDrawDot();
			buildingDot.setId(building.getId().toString());
			buildingDot.setName(building.getName());
			buildingDot.setX(building.getCoordinate().split(",")[0]);
			buildingDot.setY(building.getCoordinate().split(",")[1]);
			buildingDots.add(buildingDot);
		}

		// 当前区域的汇总数据
		SummaryDTO summary = marketAreaService.areaResourceSummary(defaultAreaId, defaultTypeId, false);

		marketArea = marketAreaService.getById(defaultAreaId);
		if (marketArea != null) {
			imageUrl = marketArea.getAreaImage();
			if (!StringUtils.isEmpty(imageUrl)) {
				imageUrl = host + imageUrl;
			}
		}

		retmap.put("statusCode", "0");
		retmap.put("msg", "success");
		retmap.put("imageUrl", imageUrl);
		if (types.size() > 0) {
			retmap.put("resTypes", types);
		}
		if (infos.size() > 0) {
			retmap.put("areas", infos);
		}
		retmap.put("buildings", buildingDots);
		retmap.put("summary", summary);
		JSONObject json = JSONObject.fromObject(retmap);
		logger.info("区域平面图主页面返回json:" + json.toString());
		return retmap;
	}
	
	/**
	 * 区域平面图资源统计
	 * @param request
	 * @param file
	 * @return
	 * @throws Exception
	 */
	@ResponseBody
	@RequestMapping("areaResStatistics")
	public Map<String, Object> areaResStatistics(HttpServletRequest request, @RequestBody JSONObject jsonObject) throws BizException {
		Map<String, Object> retmap = new HashMap<String, Object>();
		String areaId = jsonObject.getString("areaId");
		Object typeIdObject = jsonObject.get("typeId");
		String typeId = typeIdObject == null ? "": typeIdObject.toString();
		JSONArray buildingIds = null;
		if(jsonObject.get("buildingIds") != null){
			buildingIds = jsonObject.getJSONArray("buildingIds");
		}
		List<SummaryNodeDTO> nodes = new ArrayList<SummaryNodeDTO>();
		// 楼栋资源统计
		if (buildingIds != null && buildingIds.size() > 0) { // buildingIds不为空 统计相应楼栋的资源数据
			for (int i = 0; i < buildingIds.size(); i++) {
				Map<String, Object> param = new HashMap<String, Object>();
				String buildingId = buildingIds.getString(i);
				param.put("areaId", areaId);
				param.put("budingId", buildingId);
				param.put("resourceTypeId", typeId);
				SummaryNodeDTO nodeDto = marketAreaBuildingService.buildingResourceSummary(param);
				if (nodeDto != null) {
					nodeDto.setId(buildingId);
					nodes.add(nodeDto);
				}
			}
		}else{ //buildingIds为空统计该区域下所有已经描点的楼栋资源数据
			Map<String, Object> param = new HashMap<String, Object>();
			param.put("areaId", areaId);
			List<MarketAreaBuildingDTO> buildings = marketAreaBuildingService.queryBuildingDotByCondition(param);
			param.put("resourceTypeId", typeId);
			for(MarketAreaBuildingDTO building : buildings){
				param.put("budingId", building.getId());
				SummaryNodeDTO nodeDto = marketAreaBuildingService.buildingResourceSummary(param);
				if (nodeDto != null) {
					nodeDto.setId(building.getId().toString());
					nodes.add(nodeDto);
				}
			}
		}
		
		retmap.put("statusCode", "0");
		retmap.put("msg", "success");
		retmap.put("data", nodes);
		
		JSONObject json = JSONObject.fromObject(retmap); 
		logger.info("区域平面图资源统计返回json:" + json.toString());
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
	public Map<String, Object> listBuildings(HttpServletRequest request, @RequestBody JSONObject jsonObject) throws BizException {
		Map<String, Object> retmap = new HashMap<String, Object>();
		Map<String, Object> param = new HashMap<String, Object>();
		List<BaseInfoDTO> list = new ArrayList<BaseInfoDTO>();
		String areaId = jsonObject.getString("areaId");
		param.put("areaId", areaId);
		List<MarketAreaBuildingDTO> buildings = marketAreaBuildingService.queryNoDotByCondition(param);
		if (buildings != null && buildings.size() > 0) {
			for (MarketAreaBuildingDTO building : buildings) {
				BaseInfoDTO info = new BaseInfoDTO();
				info.setId(building.getId().toString());
				info.setName(building.getName());
				list.add(info);
			}
		}
		retmap.put("statusCode", "0");
		retmap.put("msg", "success");
		retmap.put("buildingList", list);
		JSONObject json = JSONObject.fromObject(retmap); 
		logger.info("待选楼栋列表返回json:" + json.toString());
        return retmap;
		
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
	@ResponseBody
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
		Map<String, Object> retmap = new HashMap<String, Object>();
		//获取系统属性
		Properties properties = gdProperties.getProperties();
		String host = properties.getProperty("gd.uplaod.host");
		//接口参数
		String marketId = jsonObject.getString("marketId");
		String buildingId = jsonObject.getString("buildingId");
		Object floorIdObj = jsonObject.get("floorId");
		Object typeObj = jsonObject.get("typeId");
		// 查询市场包括的资源类型
		List<MarketResourceTypeDTO> resTypes = marketResourceTypeService.queryByMarket(marketId);
		List<ResourceTypeDTO> rtds = new ArrayList<ResourceTypeDTO>();
		List<BaseInfoDTO> floorInfos = new ArrayList<BaseInfoDTO>();
		List<PlaneDrawDot> resourceDots = new ArrayList<PlaneDrawDot>();
		String imageUrl = null;
		String defaultTypeId = null;
		String defaultFloorId = null;
		
		// 查询楼栋下所有楼层
		Map<String, Object> param = new HashMap<String, Object>();
		param.put("buildingId", buildingId);
		param.put("status", 1);
		List<MarketUnitFloorDTO> floors = marketUnitFloorService.getFloor(param);
		
		// 默认情况下统计第一个楼层的第一个资源类型.
		if (typeObj == null) {
			if (resTypes != null && resTypes.size() > 0) {
				defaultTypeId = resTypes.get(0).getId().toString();
			}
			for (MarketResourceTypeDTO type : resTypes) {// 市场资源类型初始化
				ResourceTypeDTO rtd = new ResourceTypeDTO();
				rtd.setId(type.getId().toString());
				rtd.setCode(type.getCode());
				rtd.setName(type.getName());
				rtds.add(rtd);
			}
		} else {
			defaultTypeId = typeObj.toString();
		}

		if (floorIdObj == null) {
			if (floors != null && floors.size() > 0) {
				defaultFloorId = floors.get(0).getId().toString();
				for (MarketUnitFloorDTO floor : floors) { // 楼层初始化
					BaseInfoDTO floorInfo = new BaseInfoDTO();
					floorInfo.setId(floor.getId().toString());
					floorInfo.setName(floor.getName());
					floorInfos.add(floorInfo);
				}
			}

		} else {
			defaultFloorId = floorIdObj.toString();
		}
		
		//当前楼层下已经描点的资源
		param.clear();
		param.put("floorId", defaultFloorId);
		List<MarketResourceDTO> resources = marketResourceService.queryResourceDotByCondition(param);
		for(MarketResourceDTO resource : resources){
			ResourcePlaneDrawDot resDot = new ResourcePlaneDrawDot();
			resDot.setId(resource.getId().toString());
			resDot.setName(resource.getName());
			resDot.setX(resource.getCoordinate().split(",")[0]);
			resDot.setY(resource.getCoordinate().split(",")[1]);
			resDot.setLeaseStatus(resource.getLeaseStatus()==null?"":resource.getLeaseStatus().toString());
			resourceDots.add(resDot);
		}
				
		//当前楼层的汇总数据
		SummaryDTO summary = marketUnitFloorService.floorResourceSummary(defaultFloorId, defaultTypeId);
		//楼层平面图URL
		MarketUnitFloorDTO floorUnit = marketUnitFloorService.getById(defaultFloorId);
		if (floorUnit != null) {
			imageUrl = floorUnit.getFloorImage();
			if (!StringUtils.isEmpty(imageUrl)) {
				imageUrl = host + imageUrl;
			}
		}
		retmap.put("statusCode", "0");
		retmap.put("msg", "success");
		retmap.put("imageUrl", imageUrl);
		if(rtds.size() > 0){
		   retmap.put("resTypes", rtds);
		}
		if(floorInfos.size() > 0){
		   retmap.put("floors", floorInfos);
		}
		retmap.put("resources", resourceDots);
		retmap.put("summary", summary);
		JSONObject json = JSONObject.fromObject(retmap); 
		logger.info("楼层平面图主页面返回json:" + json.toString());
        return retmap;
	}
	
	/**
	 * 楼层平面图资源统计
	 * @param request
	 * @param file
	 * @return
	 * @throws Exception
	 */
	@ResponseBody
	@RequestMapping("floorResStatistics")
	public Map<String, Object> floorResStatistics(HttpServletRequest request, @RequestBody JSONObject jsonObject) throws BizException {
		Map<String, Object> retmap = new HashMap<String, Object>();
		String floorId = jsonObject.getString("floorId");
		List<Object> resourceList = new ArrayList<Object>();
		
		// 楼栋资源统计
		if (jsonObject.get("resourceIds") != null) {
			JSONArray resourceIds = jsonObject.getJSONArray("resourceIds");
			if (resourceIds != null && resourceIds.size() > 0) { // resourceIds不为空 统计相应的资源数据
				for (int i = 0; i < resourceIds.size(); i++) {
					Map<String, Object> param = new HashMap<String, Object>();
					MarketResourceDTO resource = new MarketResourceDTO();
					String resourceId = resourceIds.getString(i);
					param.put("id", resourceId);
					composeResource(resourceId, resource, param, resourceList);
				}
			}
		}else{ //resourceIds为空统计该楼层下所有已经描点的资源数据
			Map<String, Object> param = new HashMap<String, Object>();
			param.put("floorId", floorId);
			List<MarketResourceDTO> resources = marketResourceService.queryResourceDotByCondition(param);
			
			for (MarketResourceDTO resource : resources) {
				String resourceId = resource.getId().toString();
				param.put("id", resourceId);
				composeResource(resourceId, resource, param, resourceList);
			}
		}
		
		retmap.put("statusCode", "0");
		retmap.put("msg", "success");
		retmap.put("data", resourceList);
		
		JSONObject json = JSONObject.fromObject(retmap); 
		logger.info("区域平面图资源统计返回json:" + json.toString());
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
	public Map<String, Object> listResources(HttpServletRequest request, @RequestBody JSONObject jsonObject) throws BizException {
		Map<String, Object> retmap = new HashMap<String, Object>();
		Map<String, Object> param = new HashMap<String, Object>();
		List<BaseInfoDTO> list = new ArrayList<BaseInfoDTO>();
		String floorId = jsonObject.getString("floorId");
		param.put("floorId", floorId);
		param.put("originOperate", 0); //只显示创建的资源
		List<MarketResourceDTO> resources = marketResourceService.getNoDotResources(param);
		if (resources != null && resources.size() > 0) {
			for (MarketResourceDTO res : resources) {
				BaseInfoDTO info = new BaseInfoDTO();
				info.setId(res.getId().toString());
				info.setName(res.getName());
				list.add(info);
			}
		}
		retmap.put("statusCode", "0");
		retmap.put("msg", "success");
		retmap.put("resources", list);
		return retmap;
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
		JSONArray array = jsonObject.getJSONArray("resourceList");
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
	
	private void composeMarketResource(String typeId, List<SummaryNodeDTO> nodes, String areaId) throws BizException {
		SummaryNodeDTO nodeDto = marketAreaService.areaResourceSummary(areaId, typeId, false);
		if (nodeDto != null) {
			nodeDto.setId(areaId);
			nodes.add(nodeDto);
		}
	}
	
	private void composeResource(String resourceId, MarketResourceDTO resource, Map<String, Object> param,
			List<Object> resourceList) throws BizException {
		List<MarketResourceDTO> resList = marketResourceService.getResources(param);
		if(resList != null && resList.size() > 0){
			resource = resList.get(0); //id精确数据只有一条数据
		}
		ResourceContractDTO contract = marketResourceService.queryByResourceId(resourceId);
		ResourceResponseDTO res = new ResourceResponseDTO();
		res.setId(resourceId);
		res.setName(resource.getName());
		res.setShortCode(resource.getShortCode());
		res.setLeaseStatus(resource.getLeaseStatusName());
		res.setLeaseArea(resource.getLeaseArea() == null ? null : resource.getLeaseArea().toString());
		if (!StringUtils.isEmpty(resource.getCoordinate())) {
			res.setX(resource.getCoordinate().split(",")[0]);
			res.setY(resource.getCoordinate().split(",")[1]);
		}
		if(contract != null){
			res.setContractId(contract.getId().toString());
			res.setCustomerName(contract.getCustomerName());
			res.setCustomerMobile(contract.getCustomerMobile());
		}
		resourceList.add(res);
	}
}
