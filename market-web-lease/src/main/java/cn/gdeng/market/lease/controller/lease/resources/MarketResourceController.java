package cn.gdeng.market.lease.controller.lease.resources;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.beanutils.BeanUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.alibaba.dubbo.common.utils.StringUtils;
import com.alibaba.fastjson.JSON;

import cn.gdeng.market.dto.admin.SysUserDTO;
import cn.gdeng.market.dto.bean.GuDengPage;
import cn.gdeng.market.dto.lease.resources.MarketAreaBuildingDTO;
import cn.gdeng.market.dto.lease.resources.MarketAreaDTO;
import cn.gdeng.market.dto.lease.resources.MarketBuildingUnitDTO;
import cn.gdeng.market.dto.lease.resources.MarketResourceDTO;
import cn.gdeng.market.dto.lease.resources.MarketResourceMeasureUnionDTO;
import cn.gdeng.market.dto.lease.resources.MarketUnitFloorDTO;
import cn.gdeng.market.entity.lease.resources.MarketBuildingUnitEntity;
import cn.gdeng.market.entity.lease.resources.MarketResourceEntity;
import cn.gdeng.market.entity.lease.resources.MarketUnitFloorEntity;
import cn.gdeng.market.enums.MarketResourceLeaseStatusEnum;
import cn.gdeng.market.exception.BizException;
import cn.gdeng.market.lease.bean.AjaxResult;
import cn.gdeng.market.lease.controller.base.BaseController;
import cn.gdeng.market.lease.enums.StatusEnum;
import cn.gdeng.market.service.lease.contract.ContractManageService;
import cn.gdeng.market.service.lease.resources.MarketAreaBuildingService;
import cn.gdeng.market.service.lease.resources.MarketAreaService;
import cn.gdeng.market.service.lease.resources.MarketBuildingResourceService;
import cn.gdeng.market.service.lease.resources.MarketResourceService;
import cn.gdeng.market.service.lease.resources.MarketUnitFloorService;
import cn.gdeng.market.service.lease.settings.MarketResourceTypeService;

@Controller
@RequestMapping("marketResource")
public class MarketResourceController extends BaseController{

	private Logger logger = LoggerFactory.getLogger(MarketResourceController.class);

	@Resource(name="marketResourceService")
	private MarketResourceService marketResourceService;

	@Resource(name="marketAreaService")
	private MarketAreaService marketAreaService;

	@Resource(name = "marketAreaBuildingService")
	private MarketAreaBuildingService marketAreaBuildingService;

	@Resource(name= "marketBuildingResourceService")
	private MarketBuildingResourceService marketBuildingResourceService;

	@Resource(name="marketUnitFloorService")
	private MarketUnitFloorService marketUnitFloorService;

	@Resource(name="contractManageService")
	private ContractManageService contractManageService;

	@Resource(name="marketResourceTypeService")
	private MarketResourceTypeService marketResourceTypeService;
	/**
	 * 商铺资源首页
	 * @param mv
	 * @return
	 * @throws BizException
	 */
	@RequestMapping("market_index")
	public String marketResourcesIndex() throws BizException {
		return "resources/marketResource/market_index";
	}
	/**
	 * 商铺资源详情、编辑、修改
	 * @param mv
	 * @return
	 * @throws BizException
	 */
	@RequestMapping("viewMarketResource")
	public String viewMarketResource() throws BizException {

		return "resources/marketResource/viewMarketResource";
	}
	/**
	 * 停车位资源首页
	 * @param mv
	 * @return
	 * @throws BizException
	 */
	@RequestMapping("car_index")
	public String carResourcesIndex() throws BizException {
		return "resources/marketResource/car_index";
	}

	/**
	 * 验证当前资源的是否关联了合同，并且状态为待执行，或者执行中，如果关联则不能进行回收操作
	 * @return
	 * @throws BizException
	 */
	@RequestMapping("validContractStatus/{resourceId}")
	public @ResponseBody Map<String,Object> validContractStatus(@PathVariable("resourceId") String resourceId) throws BizException {
		Map<String,Object> paramMap =new HashMap<>();
		paramMap.put("resourceId", resourceId);
		int count=contractManageService.getContractsByResourceId(paramMap);
		paramMap.put("result", count>0?true:false);
		return paramMap;
	}

	/**
	 * 根据市场  获取所有区域和楼栋
	 * @return
	 * @throws BizException
	 * weiwenke
	 */
	@RequestMapping("area_build")
	private @ResponseBody Map<String,Object> getAllById() throws BizException{
		Map<String,Object> area_buildMaps =new HashMap<>();
		List paras=new ArrayList<>();
		if (null!=super.getCurrentMarket()) {
			area_buildMaps.put("marketId", super.getCurrentMarket().getId());
			List<MarketAreaDTO> areas=marketAreaService.getList(area_buildMaps);
			Iterator<MarketAreaDTO> iterators=areas.iterator();
			while(iterators.hasNext()){
				MarketAreaDTO marketArea=iterators.next(); 
				area_buildMaps.put("areaId", marketArea.getId());
				List<MarketAreaBuildingDTO> builds=marketAreaBuildingService.getBuilding(area_buildMaps);
				if (null!=builds&&builds.size()>0) {
					marketArea.setBuildings(builds);
				}
				paras.add(marketArea);
			}
			area_buildMaps.remove("areaId");
			area_buildMaps.put("result", paras);
		}
		return area_buildMaps;
	}

	/**
	 * 去往其它资源首页
	 * @param mav
	 * @return
	 * @throws BizException
	 */
	@RequestMapping(value = "toOtherResourceView")
	public ModelAndView toOtherResourceView(ModelAndView mav) throws BizException {
		mav.setViewName("resources/marketResource/other/otherIndex");
		return mav;
	} 

	/**
	 * 其它资源详情、编辑、修改
	 * @param mv
	 * @return
	 * @throws BizException
	 */
	@RequestMapping("viewOtherMarketResource")
	public String viewOtherMarketResource() throws BizException {
		return "resources/marketResource/other/viewOtherMarketResource";
	}

	/**
	 * 列表查询
	 * 
	 * @param request
	 * @return
	 * @author weiwenke
	 * @throws BizException 
	 */
	@RequestMapping("query")
	@ResponseBody
	public AjaxResult<GuDengPage<MarketResourceDTO>> query(HttpServletRequest request,MarketResourceDTO dto) throws BizException {
		logger.info("查询商铺资源信息:"+JSON.toJSONString(dto));
		AjaxResult<GuDengPage<MarketResourceDTO>> res = new AjaxResult<>();
		//获取分页信息
		if (null!=super.getCurrentMarket()) {
			dto.setMarketId(super.getCurrentMarket().getId());
			GuDengPage<MarketResourceDTO> page = getPageInfoByRequest(dto);
			page = marketResourceService.queryByPage(page);
			res.setResult(page);
		}
		return res;
	}

	/**
	 * 列表查询其它资源
	 * @param request
	 * @param dto
	 * @return
	 * @throws BizException
	 */
	@RequestMapping("queryOther")
	@ResponseBody
	public AjaxResult<GuDengPage<MarketResourceDTO>> queryOther(HttpServletRequest request,MarketResourceDTO dto) throws BizException {
		AjaxResult<GuDengPage<MarketResourceDTO>> res = new AjaxResult<>();
		//获取分页信息
		if (null != super.getCurrentMarket()) {
			dto.setMarketId(super.getCurrentMarket().getId());
			GuDengPage<MarketResourceDTO> page = getPageInfoByRequest(dto);
			page = marketResourceService.queryOtherByPage(page);
			res.setResult(page);
		}
		return res;
	}

	/**
	 * 删除商铺信息
	 * @param ids
	 * @return
	 * @throws BizException
	 */
	@RequestMapping("remove")
	public @ResponseBody Map<String,Object> remove (String ids) throws BizException {
		logger.info("删除商铺资源信息:"+ids);
		Map<String,Object> res=new HashMap<>();
		res.put("success", false);
		List<String> para=Arrays.asList(ids.split(","));
		if (para==null||para.size()<=0) {
			res.put("message", "删除参数为空");
			return res;
		}
		int count=marketResourceService.deleteBatch(para);
		if (count>0) {
			res.put("success", true);
			res.put("message", "成功删除"+count+"条");
			return res;
		}

		return res;
	}
	/**
	 * 放租，回收商铺信息
	 * @param ids
	 * @return
	 * @throws BizException
	 */
	@RequestMapping("release_receive")
	public @ResponseBody Map<String,Object> release_receive(HttpServletRequest request,String ids,String leaseStatus,String oldStatus) throws BizException {
		logger.info("房租、释放商铺资源信息:"+ids);
		Map<String,Object> res=new HashMap<>();
		res.put("success", false);
		List<Map<String,Object>> paraList=new ArrayList<>();
		for (String id : ids.split(",")) {
			Map<String, Object> map=new HashMap<>();
			map.put("id", id);
			map.put("oldStatus",oldStatus); //原始状态
			map.put("leaseStatus",leaseStatus);//修改的新状态
			SysUserDTO user= getSysUser();
			if (StringUtils.isNotEmpty(id)) {
				map.put("updateUserId", user.getUserId());

			} 
			paraList.add(map);
		}
		int count=marketResourceService.updateBatch(paraList);
		if (count>0) {
			res.put("success", true);
			res.put("message", "成功修改"+count+"条");
			return res;
		}
		return res;
	}

	/**
	 * 保存数据（新增、修改）
	 * 
	 * @param request
	 * @return
	 * @author huangbd
	 */
	@RequestMapping("save")
	public @ResponseBody Map<String,Object> save(HttpServletRequest request, MarketResourceEntity entity,String buildingUnitName,String unitFloorName,String parentId) {
		Map<String, Object> map = new HashMap<>();
		try {
			boolean result = false;

		//	map.put("buildingId",entity.getBudingId());
			
			map.put("budingId",entity.getBudingId());
			map.put("buildingId",entity.getBudingId());
			if(StringUtils.isNotEmpty(buildingUnitName)){
				List<MarketBuildingUnitDTO> marketBuildingUnits=marketBuildingResourceService.getUnitAndResource(map);
				for (MarketBuildingUnitDTO  marketBuildingUnitDTO : marketBuildingUnits) {
					if(marketBuildingUnitDTO.getName().equals(buildingUnitName)){
						entity.setUnitId(marketBuildingUnitDTO.getId());
						break;
					}
				}
				if(null== entity.getUnitId()){
					Integer  id=getFloorAndBuild(entity.getBudingId(),buildingUnitName,null).get("unitId");
					entity.setUnitId(id);
				}
			}else{
				entity.setUnitId(-1);
			}
			if(StringUtils.isNotEmpty(unitFloorName)){
				List<MarketUnitFloorDTO> marketUnitFloors=marketUnitFloorService.getFloor(map);
				for (MarketUnitFloorDTO  marketUnitFloorDTO : marketUnitFloors) {
					if(marketUnitFloorDTO.getName().equals(unitFloorName)){
						entity.setFloorId(marketUnitFloorDTO.getId());
						break;
					}
				}
				if(null== entity.getFloorId()){
					Integer  id= getFloorAndBuild(entity.getBudingId(),null,unitFloorName).get("floorId");
					entity.setFloorId(id);
				}
			}else{
				entity.setFloorId(-1);
			}
			map.put("marketId", super.getCurrentMarket().getId());
			map.put("parentId", parentId);
			if (null!=entity.getId()) {
				if(null==entity.getBudingId()){
					entity.setBudingId(-1);
				}
				entity.setUpdateUserId(getSysUser().getUserId());
				result=marketResourceService.update(entity);
			}else{
				int resourceTypeId= marketResourceTypeService.queryByMarketIdParentId(map);
				entity.setResourceTypeId(resourceTypeId);
				entity.setCreateUserId(getSysUser().getUserId());
				entity.setUpdateUserId(getSysUser().getUserId());
				entity.setMarketId(super.getCurrentMarket().getId());
				entity.setCreateTime(new Date());
				entity.setUpdateTime(new Date());
				result = marketResourceService.insert(entity);
			}
			if (result) {
				map.put("success", true);
			} else {
				map.put("msg", "操作失败");
			}
		} catch (Exception e) {
			map.put("msg", "操作失败");
			logger.trace("操作错误：", e);
		}
		return map;
	}

	/**
	 * 保存其它资源数据（新增、修改）
	 * 
	 * @param request
	 * @return
	 * @author huangbd
	 */
	@RequestMapping("saveOther")
	public @ResponseBody Map<String,Object> saveOther(HttpServletRequest request, MarketResourceEntity entity,String buildingUnitName,String unitFloorName) {
		Map<String, Object> map = new HashMap<>();
		try {
			boolean result = false;

			map.put("budingId",entity.getBudingId());
			map.put("buildingId",entity.getBudingId());
			List<MarketBuildingUnitDTO> marketBuildingUnits=marketBuildingResourceService.getUnitAndResource(map);
			List<MarketUnitFloorDTO> marketUnitFloors=marketUnitFloorService.getFloor(map);
			for (MarketBuildingUnitDTO  marketBuildingUnitDTO : marketBuildingUnits) {
				if(marketBuildingUnitDTO.getName().equals(buildingUnitName)){
					entity.setUnitId(marketBuildingUnitDTO.getId());
					break;
				}
			}
			for (MarketUnitFloorDTO  marketUnitFloorDTO : marketUnitFloors) {
				if(marketUnitFloorDTO.getName().equals(unitFloorName)){
					entity.setFloorId(marketUnitFloorDTO.getId());
					break;
				}
			}

			if(null== entity.getUnitId()){
				Integer  id=getFloorAndBuild(entity.getBudingId(),buildingUnitName,null).get("unitId");
				entity.setUnitId(id);
			}
			if(null== entity.getFloorId()){
				Integer  id= getFloorAndBuild(entity.getBudingId(),null,unitFloorName).get("floorId");
				entity.setFloorId(id);
			}
			map.put("marketId", super.getCurrentMarket().getId());
			if (null!=entity.getId()) {
				entity.setUpdateUserId(getSysUser().getUserId());
				result=marketResourceService.update(entity);
			}else{
				entity.setResourceTypeId(entity.getResourceTypeId());
				entity.setCreateUserId(getSysUser().getUserId());
				entity.setUpdateUserId(getSysUser().getUserId());
				entity.setMarketId(super.getCurrentMarket().getId());
				entity.setCreateTime(new Date());
				entity.setUpdateTime(new Date());
				result = marketResourceService.insert(entity);
			}
			if (result) {
				map.put("success", true);
			} else {
				map.put("msg", "操作失败");
			}
		} catch (Exception e) {
			map.put("msg", "操作失败");
			logger.trace("操作错误：", e);
		}
		return map;
	}


	/**
	 * 根据id查看数据
	 * 
	 * @param request
	 * @return
	 * @author weiwenke
	 */
	@RequestMapping("view/{id}")
	public @ResponseBody Map<String, Object> view(HttpServletRequest request, @PathVariable("id") String id) {
		Map<String, Object> map=new HashMap<>();
		try {
			map.put("id", id);

			MarketResourceDTO dto = new MarketResourceDTO();
			List<MarketResourceDTO> marketResourceDTOs  =marketResourceService.getList(map);
			if (!marketResourceDTOs.isEmpty()&&marketResourceDTOs.size()>0) {
				dto=marketResourceDTOs.get(0);
			}

			List<MarketResourceMeasureUnionDTO>  measureResources=marketResourceService.getMeasureResourceById(map);
			dto.setResourceMeasureUnions(measureResources);
			map.remove("id");
			map.put("buildingId",dto.getBudingId());
			map.put("result", dto); //获取资源信息
		} catch (Exception e) {
			map.put("msg", "查询失败");
			logger.trace("进入查看页面错误", e);
		}
		return map;
	}


	private Map<String, Integer> getFloorAndBuild(Integer buildingId, String buildName, String floorName)
			throws BizException {
		Map<String,Integer> res=new HashMap<>();
		if (StringUtils.isNotEmpty(buildName)) {
			MarketBuildingUnitEntity mb=new MarketBuildingUnitEntity();
			mb.setBuildingId(buildingId);
			mb.setName(buildName);
			mb.setCreateTime(new Date());
			mb.setCreateUserId(getUserId()+"");
			mb.setUnitNo(1);
			int id=marketBuildingResourceService.addMarketBuildingUnit(mb);
			res.put("unitId", id);
		}
		if (StringUtils.isNotEmpty(floorName)) {
			MarketUnitFloorEntity mf=new MarketUnitFloorEntity();
			mf.setBuildingId(buildingId);
			mf.setName(floorName);
			mf.setCreateUserId(super.getSysUser().getId().toString());
			mf.setCreateTime(new Date());
			mf.setFloorNo(1);
			int id=marketBuildingResourceService.addMarketUnitFloor(mf);
			res.put("floorId", id);
		}
		return res;
	}


	@RequestMapping(value = "/findByPage")
	@ResponseBody
	public AjaxResult<GuDengPage<MarketResourceDTO>> findByPage(HttpServletRequest request, 
			HttpServletResponse response, MarketResourceDTO dto) throws Exception {
		AjaxResult<GuDengPage<MarketResourceDTO>> ar = new AjaxResult<>();
		GuDengPage<MarketResourceDTO> page = getPageInfoByRequest();
		page = marketResourceService.selByPage(page, dto);
		ar.setResult(page);
		return ar;
	}

	/**
	 * 放租
	 * @param request
	 * @param response
	 * @param idList
	 * @return
	 * @throws BizException
	 */
	@RequestMapping(value = "/rent")
	@ResponseBody
	public AjaxResult<Boolean> rent(HttpServletRequest request, HttpServletResponse response, 
			Integer[] idArray) throws BizException {
		AjaxResult<Boolean> ar = new AjaxResult<>();
		List<Integer> idList = Arrays.asList(idArray);
		int feasibilityLength = marketResourceService.rentFeasibility(idList, MarketResourceLeaseStatusEnum.WAIT_FOR_RENT_OUT);
		if (idList.size() != feasibilityLength) {
			ar.setIsSuccess(false);
			ar.setMsg("放租失败，放租资源状态应全部为 '待放租' ");
			return ar;
		}
		if (marketResourceService.rentAndRecovery(idList, super.getSysUser().getId().toString(), 
				MarketResourceLeaseStatusEnum.WAIT_FOR_RENT)) {
			ar.setIsSuccess(true);
			ar.setMsg("放租成功");
		} else {
			ar.setIsSuccess(false);
			ar.setMsg("放租失败");
		}
		return ar;
	}

	/**
	 * 回收
	 * @param request
	 * @param response
	 * @param idList
	 * @return
	 * @throws BizException
	 */
	@RequestMapping(value = "/recovery")
	@ResponseBody
	public AjaxResult<Boolean> recovery(HttpServletRequest request, HttpServletResponse response, 
			Integer[] idArray) throws BizException {
		AjaxResult<Boolean> ar = new AjaxResult<>();
		List<Integer> idList = Arrays.asList(idArray);
		int feasibilityLength = marketResourceService.rentFeasibility(idList, MarketResourceLeaseStatusEnum.WAIT_FOR_RENT);
		if (idList.size() != feasibilityLength) {
			ar.setIsSuccess(false);
			ar.setMsg("回收失败，回收资源状态应全部为 '放租' ");
			return ar;
		}

		if (marketResourceService.rentAndRecovery(idList, super.getSysUser().getId().toString(), 
				MarketResourceLeaseStatusEnum.WAIT_FOR_RENT_OUT)) {
			ar.setIsSuccess(true);
			ar.setMsg("回收成功");
		} else {
			ar.setIsSuccess(false);
			ar.setMsg("回收失败");
		}
		return ar;
	}

	@RequestMapping(value = "/add")
	@ResponseBody
	public AjaxResult<Boolean> add(HttpServletRequest request, HttpServletResponse response, 
			MarketResourceEntity entity) throws Exception {
		entity.setCreateUserId(super.getSysUser().getId().toString());
		if (super.getCurrentMarket() != null) {
			entity.setMarketId(super.getCurrentMarket().getId());
		}
		AjaxResult<Boolean> ar = new AjaxResult<>();
		if (marketResourceService.insert(entity)) {
			ar.setResult(true);
		} else {
			ar.setResult(false);
		}
		return ar;
	}

	@RequestMapping(value = "/update")
	@ResponseBody
	public AjaxResult<Boolean> update(HttpServletRequest request, HttpServletResponse response, 
			MarketResourceEntity entity) throws Exception {
		entity.setUpdateUserId(super.getSysUser().getId().toString());
		AjaxResult<Boolean> ar = new AjaxResult<>();
		if (marketResourceService.update(entity)) {
			ar.setResult(true);
		} else {
			ar.setResult(false);
		}
		return ar;
	}

	/**
	 * 批量删除
	 * @param request
	 * @param response
	 * @param idArray
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/batchDelete")
	@ResponseBody
	public AjaxResult<Boolean> batchDelete(HttpServletRequest request, HttpServletResponse response, 
			Integer[] idArray) throws Exception {
		AjaxResult<Boolean> ar = new AjaxResult<>();
		List<Integer> idList = Arrays.asList(idArray);
		if (marketResourceService.batchDelete(idList, super.getSysUser().getId().toString())) {
			ar.setResult(true);
		} else {
			ar.setResult(false);
		}
		return ar;
	}
	
	/**
	 * 根据条件查询当前市场下所有资源类型总数
	 * @param request
	 * @param response
	 * @param dto
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/findAllCountByCondition")
	@ResponseBody
	public Map<String, Object> findAllCountByCondition(HttpServletRequest request, 
			HttpServletResponse response, MarketResourceDTO dto) throws Exception {
		Map<String, Object> returnMap = new HashMap<>();
		Map<String,Object> paramMap = new HashMap<>();
		if (super.getCurrentMarket() != null) {
			returnMap.put("result", true);
			dto.setMarketId(super.getCurrentMarket().getId());
			paramMap = BeanUtils.describe(dto);
			paramMap.put("status", StatusEnum.NORMAL.getKey());
			returnMap.put("data", marketResourceService.selCountByCondition(paramMap));
		} else {
			returnMap.put("result", false);
		}
		return returnMap;
	}
	
	/**
	 * 更新前校验
	 * @param dto
	 * @param type
	 * @return
	 * @throws BizException
	 * @throws NoSuchMethodException 
	 * @throws InvocationTargetException 
	 * @throws IllegalAccessException 
	 */
	@RequestMapping(value = "/updateValidator")
	@ResponseBody
	public Map<String, Object> updateValidator(MarketResourceDTO dto, String type) throws Exception {
		Map<String, Object> returnMap = new HashMap<>();
		if (super.getCurrentMarket() != null) {
			dto.setMarketId(super.getCurrentMarket().getId());
			returnMap.put("result", true);
			@SuppressWarnings("unchecked")
			Map<String, Object> paramMap = BeanUtils.describe(dto);
			switch(type) {
				case "code" :
					returnMap.put("data", marketResourceService.findCountByIdAndCodeAndMarketId(paramMap));
					break;
				case "shortCode" :
					returnMap.put("data", marketResourceService.findCountByIdAndShortCodeAndMarketId(paramMap));
					break;
				case "name" :
					returnMap.put("data", marketResourceService.findCountByIdAndNameAndMarketId(paramMap));
				break;
				default : 
					returnMap.put("data", -1);
				break;
			}
		} else {
			returnMap.put("result", false);
		}
		return returnMap;
	}

}

