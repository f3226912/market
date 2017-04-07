package cn.gdeng.market.lease.controller.lease.settings;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.beanutils.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import cn.gdeng.market.dto.bean.GuDengPage;
import cn.gdeng.market.dto.lease.settings.MarketDTO;
import cn.gdeng.market.dto.lease.settings.MarketResourceTypeDTO;
import cn.gdeng.market.entity.lease.settings.MarketResourceTypeEntity;
import cn.gdeng.market.exception.BizException;
import cn.gdeng.market.lease.bean.AjaxResult;
import cn.gdeng.market.lease.controller.base.BaseController;
import cn.gdeng.market.lease.enums.StatusEnum;
import cn.gdeng.market.service.lease.resources.MarketResourceService;
import cn.gdeng.market.service.lease.settings.MarketResourceTypeService;
import cn.gdeng.market.service.lease.settings.MarketService;

/**
 * 市场资源类型
 */
@Controller
@RequestMapping("marketResourceType")
public class MarketResourceTypeController extends BaseController {
	
	@Autowired
	private MarketResourceTypeService marketResourceTypeService;
	
	@Autowired
	private MarketService marketService;
	
	@Autowired
	private MarketResourceService marketResourceService;
	
	/**
	 * 去往首页
	 * @param mav
	 * @return
	 * @throws BizException
	 */
	@RequestMapping(value = "/index")
	public ModelAndView index(ModelAndView mav) throws BizException {
		mav.setViewName("settings/marketResourceType/marketResourceType");
		return mav;
	}
	
	/**
	 * 去往添加页面
	 * @param mav
	 * @return
	 * @throws BizException
	 */
	@RequestMapping(value = "/toAddView")
	public ModelAndView toAddView(ModelAndView mav) throws BizException {
		mav.setViewName("settings/marketResourceType/marketResourceTypeAdd");
		return mav;
	}
	
	/**
	 * 得到所有市场列表(去往添加/修改页面填充数据用)
	 * @return
	 * @throws BizException
	 */
	@RequestMapping(value = "/getAllMarket")
	@ResponseBody
	public AjaxResult<List<MarketDTO>> getAllMarket() throws BizException {
		AjaxResult<List<MarketDTO>> ar = new AjaxResult<>();
		List<MarketDTO> marketList = marketService.getList(null);
		if (marketList.isEmpty()) {
			return null;
		} else {
			ar.setIsSuccess(true);
			ar.setResult(marketList);
		}
		return ar;
	}
	
	/**
	 * 新增资源类型
	 * @param request
	 * @param response
	 * @param entity
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/add")
	@ResponseBody
	public AjaxResult<Boolean> add(HttpServletRequest request, HttpServletResponse response, 
			MarketResourceTypeEntity entity) throws Exception {
		Map<String, Object> paramMap = new HashMap<>();
		entity.setCreateUserId(super.getSysUser().getId().toString());
		if (super.getCurrentMarket() != null) {
			entity.setMarketId(super.getCurrentMarket().getId());
			paramMap.put("marketId", super.getCurrentMarket().getId());
		}
		AjaxResult<Boolean> ar = new AjaxResult<>();
		paramMap.put("status", StatusEnum.NORMAL.getKey());
		paramMap.put("code", entity.getCode());
		//code只能有一个
		if (marketResourceTypeService.selCountByCondition(paramMap) != 0) {
			ar.setResult(false);
			ar.setMsg("已存在 " + entity.getCode() + " 资源类型编码");
			return ar;
		}
		//name只能有一个
		paramMap.remove("code");
		paramMap.put("name", entity.getName());
		if (marketResourceTypeService.selCountByCondition(paramMap) != 0) {
			ar.setResult(false);
			ar.setMsg("已存在 " + entity.getName() + " 资源类型名称");
			return ar;
		}
		
		if (marketResourceTypeService.insert(entity)) {
			ar.setResult(true);
		} else {
			ar.setResult(false);
		}
		
		return ar;
	}
	
	/**
	 * 去往编辑页面
	 * @param mav
	 * @return
	 * @throws BizException
	 */
	@RequestMapping(value = "/toEditView")
	public ModelAndView toEditView(ModelAndView mav) throws BizException {
		mav.setViewName("settings/marketResourceType/marketResourceTypeEdit");
		return mav;
	}
	
	/**
	 * 根据id获取资源类型
	 * @param id
	 * @return
	 * @throws BizException
	 */
	@RequestMapping(value = "/getMarketResTypeById")
	@ResponseBody
	public AjaxResult<MarketResourceTypeDTO> getMarketResTypeById(Integer id) throws BizException {
		AjaxResult<MarketResourceTypeDTO> ar = new AjaxResult<>();
		MarketResourceTypeDTO dto = marketResourceTypeService.selMarketResTypeById(id);
		ar.setResult(dto);
		return ar;
	}
	
	/**
	 * 删除资源类型前校验
	 * @param resourceTypeId
	 * @return true : 说明该资源类型关联了资源，不能删除
	 * @throws BizException
	 */
	@RequestMapping(value = "/validateDelete")
	@ResponseBody
	public Map<String, Object> validateDelete(Integer resourceTypeId) throws BizException {
		Map<String, Object> paramMap = new HashMap<>();
		paramMap.put("status", StatusEnum.NORMAL.getKey());
		paramMap.put("resourceTypeId", resourceTypeId);
		int count = marketResourceService.selCountByCondition(paramMap);
		paramMap.put("result", count > 0 ? true : false);
		return paramMap;
	}
	
	/**
	 * 逻辑删除资源类型
	 * @param request
	 * @param response
	 * @param id
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/delete")
	@ResponseBody
	public AjaxResult<Boolean> delete(HttpServletRequest request, HttpServletResponse response, 
			int[]  idArray) throws Exception {
		AjaxResult<Boolean> ar = new AjaxResult<>();
		if (marketResourceTypeService.batchDelete(idArray, super.getSysUser().getId().toString())) {
			ar.setResult(true);
		} else {
			ar.setResult(false);
		}
		return ar;
	}
	
	/**
	 * 修改资源类型
	 * @param request
	 * @param response
	 * @param entity
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/update")
	@ResponseBody
	public AjaxResult<Boolean> update(HttpServletRequest request, HttpServletResponse response, 
			MarketResourceTypeEntity entity) throws Exception {
		entity.setUpdateUserId(super.getSysUser().getId().toString());
		if (super.getCurrentMarket() != null) {
			entity.setMarketId(super.getCurrentMarket().getId());
		}
		AjaxResult<Boolean> ar = new AjaxResult<>();
		//code只能有一个
		if (marketResourceTypeService.selCountByIdAndCodeAndMarketId(super.getCurrentMarket().getId(), entity.getId(), entity.getCode()) != 0) {
			ar.setResult(false);
			ar.setMsg("已存在 " + entity.getCode() + " 资源类型编码");
			return ar;
		}
		//name只能有一个
		if (marketResourceTypeService.selCountByIdAndNameAndMarketId(super.getCurrentMarket().getId(), entity.getId(), entity.getName()) != 0) {
			ar.setResult(false);
			ar.setMsg("已存在 " + entity.getName() + " 资源类型名称");
			return ar;
		}
		if (marketResourceTypeService.update(entity)) {
			ar.setResult(true);
		} else {
			ar.setResult(false);
		}
		return ar;
	}
	
	/**
	 * 分页查询资源类型
	 * @param request
	 * @param response
	 * @param dto
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/findByPage")
	@ResponseBody
	public AjaxResult<GuDengPage<MarketResourceTypeDTO>> findByPage(HttpServletRequest request, 
			HttpServletResponse response, MarketResourceTypeDTO dto) throws Exception {
		AjaxResult<GuDengPage<MarketResourceTypeDTO>> ar = new AjaxResult<>();
		GuDengPage<MarketResourceTypeDTO> page = getPageInfoByRequest();
		if (null != super.getCurrentMarket()) {
			dto.setMarketId(super.getCurrentMarket().getId());
			page = marketResourceTypeService.selByPage(page, dto);
			ar.setResult(page);
		}
		return ar;
	}
	
	/**
	 * 根据条件查询当前市场下所有资源类型
	 * @param request
	 * @param response
	 * @param dto
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/findAllByCondition")
	@ResponseBody
	public AjaxResult<List<MarketResourceTypeDTO>> findAllByCondition(HttpServletRequest request, 
			HttpServletResponse response, MarketResourceTypeDTO dto) throws Exception {
		List<MarketResourceTypeDTO> list = null;
		if (super.getCurrentMarket() != null) {
			dto.setMarketId(super.getCurrentMarket().getId());
			list = marketResourceTypeService.selAllByCondition(dto);
		}
		AjaxResult<List<MarketResourceTypeDTO>> ar = new AjaxResult<>();
		ar.setResult(list);
		ar.setIsSuccess(true);
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
			HttpServletResponse response, MarketResourceTypeDTO dto) throws Exception {
		Map<String, Object> returnMap = new HashMap<>();
		Map<String,Object> paramMap = new HashMap<>();
		if (super.getCurrentMarket() != null) {
			returnMap.put("result", true);
			dto.setMarketId(super.getCurrentMarket().getId());
			paramMap = BeanUtils.describe(dto);
			paramMap.put("status", StatusEnum.NORMAL.getKey());
			returnMap.put("data", marketResourceTypeService.selCountByCondition(paramMap));
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
	 */
	@RequestMapping(value = "/updateValidator")
	@ResponseBody
	public Map<String, Object> updateValidator(MarketResourceTypeDTO dto, String type) throws BizException {
		Map<String, Object> returnMap = new HashMap<>();
		if (super.getCurrentMarket() != null) {
			returnMap.put("result", true);
			switch(type) {
				case "code" :
					returnMap.put("data", marketResourceTypeService.selCountByIdAndCodeAndMarketId(super.getCurrentMarket().getId(), dto.getId(), dto.getCode()));
					break;
				case "name" :
					returnMap.put("data", marketResourceTypeService.selCountByIdAndNameAndMarketId(super.getCurrentMarket().getId(), dto.getId(), dto.getName()));
					break;
				case "sort" :
					returnMap.put("data", marketResourceTypeService.selCountByIdAndSortAndMarketId(super.getCurrentMarket().getId(), dto.getId(), dto.getSort()));
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
