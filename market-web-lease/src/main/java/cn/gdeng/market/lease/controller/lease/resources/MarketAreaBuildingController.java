package cn.gdeng.market.lease.controller.lease.resources;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import cn.gdeng.market.dto.admin.SysOrgDTO;
import cn.gdeng.market.dto.bean.GuDengPage;
import cn.gdeng.market.dto.lease.resources.MarketAreaBuildingDTO;
import cn.gdeng.market.dto.lease.resources.MarketResourceParam;
import cn.gdeng.market.entity.lease.resources.MarketAreaBuildingEntity;
import cn.gdeng.market.enums.Const;
import cn.gdeng.market.enums.MsgCons;
import cn.gdeng.market.exception.BizException;
import cn.gdeng.market.lease.bean.AjaxResult;
import cn.gdeng.market.lease.controller.base.BaseController;
import cn.gdeng.market.service.lease.resources.MarketAreaBuildingService;
import cn.gdeng.market.service.lease.resources.MarketBuildingResourceService;

/**
 * 楼栋管理
 * 
 * @author qrxu
 *
 */
@Controller
@RequestMapping("marketBuilding")
public class MarketAreaBuildingController extends BaseController {
	@Resource
	private MarketAreaBuildingService marketAreaBuildingService;
	@Resource
	private MarketBuildingResourceService marketBuildingResourceService;

	/**
	 * 列表页面
	 * 
	 * @param mv
	 * @return
	 * @throws BizException
	 */
	@RequestMapping("index")
	public ModelAndView org(ModelAndView mv) throws BizException {
		mv.setViewName("lease/building/getMarketBuildingInfo");
		return mv;
	}

	/**
	 * 进入楼栋详情页面
	 * 
	 * @param mv
	 * @return
	 * @throws BizException
	 */
	@RequestMapping("marketBuildingDetailPage")
	public ModelAndView marketBuildingDetailPage(ModelAndView mv) throws BizException {
		mv.setViewName("lease/building/marketBuildingdetail");
		return mv;
	}

	/**
	 * 根据楼栋id 查询楼栋详情信息
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws BizException
	 */
	@RequestMapping("marketBuildingdetail")
	@ResponseBody
	public AjaxResult<MarketAreaBuildingDTO> getBuildingDetail(String id) throws BizException {
		AjaxResult<MarketAreaBuildingDTO> result = new AjaxResult<>();
		MarketAreaBuildingDTO entity = marketAreaBuildingService.queryAreaBuilding(id);
		result.setResult(entity);
		return result;
	}

	/**
	 * 修改楼栋信息
	 * 
	 * @param id
	 * @return
	 * @throws BizException
	 */
	@RequestMapping("marketBuildingdetailEdit")
	@ResponseBody
	public AjaxResult<Integer> marketBuildingdetailEdit(MarketAreaBuildingEntity entity) throws BizException {
		AjaxResult<Integer> result = new AjaxResult<>();
		MarketAreaBuildingDTO dto=new MarketAreaBuildingDTO();
		dto.setId(entity.getId());
		dto.setCode(entity.getCode());
		dto.setName(entity.getName());
		dto.setMarketId(super.getCurrentMarket().getId());

		//校验 楼栋编码   楼栋名称  唯一性
	   int detailName=marketAreaBuildingService.queryBybuilDetailName(dto);
	   int detailCode=marketAreaBuildingService.queryBybuilDetailCode(dto);

	   if(detailName == 0 && detailCode == 0){
			int res = marketAreaBuildingService.editBuilding(entity);
			result.setResult(res);
			return result;
		}else{
			if (detailCode > 0) {
				result.setIsSuccess(false);
				result.setCode(3000);
				result.setMsg("楼栋编码已经存在，请重新命名");
				return result;
			}
			if (detailName > 0) {
				result.setIsSuccess(false);
				result.setCode(3000);
				result.setMsg("楼栋名称已经存在，请重新命名");
				return result;
			}

		}
	   return result;
	}

	/**
	 * 进入新增楼栋页面
	 * 
	 * @param mv
	 * @return
	 * @throws BizException
	 */
	@RequestMapping("addMarketBuilding")
	public ModelAndView add(ModelAndView mv) throws BizException {
		mv.setViewName("lease/building/addMarketBuilding");
		return mv;
	}

	/**
	 * 新增楼栋信息
	 * 
	 * @param request
	 * @param resqonse
	 * @return
	 * @throws BizException
	 */
	@RequestMapping("addMarketBuild")
	@ResponseBody
	public AjaxResult<Integer> addBuilding(MarketAreaBuildingEntity entity) throws BizException {
		AjaxResult<Integer> ajaxResult = new AjaxResult<>();
		// 如果没有当前市场 不能新增楼栋
		SysOrgDTO orgDto = super.getCurrentMarket();
		// 判断当前市场是否为空
		if (orgDto == null) {
			ajaxResult.setIsSuccess(false);
			ajaxResult.setCode(3000);
			ajaxResult.setMsg("没有市场，不能新增楼栋");
			return ajaxResult;
		}
		//校验 楼栋编码   楼栋名称  唯一性
		MarketAreaBuildingDTO dto=new MarketAreaBuildingDTO();
		dto.setCode(entity.getCode().trim());
		dto.setName(entity.getName().trim());
		dto.setMarketId(orgDto.getId());
		   int detailName=marketAreaBuildingService.queryBybuilDetailName(dto);
		   int detailCode=marketAreaBuildingService.queryBybuilDetailCode(dto);
		
		   if (detailCode > 0) {
			   ajaxResult.setIsSuccess(false);
			   ajaxResult.setCode(3000);
			   ajaxResult.setMsg("楼栋编码已经存在，请重新命名");
				return ajaxResult;
			}
			if (detailName > 0) {
				ajaxResult.setIsSuccess(false);
				ajaxResult.setCode(3000);
				ajaxResult.setMsg("楼栋名称已经存在，请重新命名");
				return ajaxResult;
			}

		entity.setStatus(Const.NORMAL);// 状态
		entity.setBuildingImage(null);// 楼栋图片
		entity.setUpdateUserId(super.getUserId().toString());
		entity.setCreateUserId(super.getUserId().toString());
		// 保存楼栋信息
		int result = marketAreaBuildingService.addAreaBuilding(entity);
		ajaxResult.setResult(result);
		return ajaxResult;
	}

	/**
	 * 查询所有当前市场归属区域
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws BizException
	 */
	@RequestMapping("getMarketArea")
	public AjaxResult<List<MarketAreaBuildingDTO>> getMarketArea(HttpServletRequest request,
			HttpServletResponse response) throws BizException {
		// 查询当前市场下面的区域
		SysOrgDTO orgDto = super.getCurrentMarket();
		// 判断当前市场是否为空
		if (orgDto == null) {
			throw new BizException(MsgCons.C_30006, MsgCons.M_30006);
		}
		List<MarketAreaBuildingDTO> areas = marketAreaBuildingService.queryAreaByMarketId(orgDto.getId());
		AjaxResult<List<MarketAreaBuildingDTO>> ajaxResult = new AjaxResult<>();
		ajaxResult.setResult(areas);
		return ajaxResult;
	}

	/**
	 * 加载当前市场下所有区域下拉表
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws BizException
	 */
	@RequestMapping("getAreaId")
	@ResponseBody
	public AjaxResult<List<MarketAreaBuildingDTO>> getArea(HttpServletRequest request, HttpServletResponse response)
			throws BizException {
		// 得到當前市場ID
		SysOrgDTO orgDto = super.getCurrentMarket();
		// 判断当前市场是否为空
		if (orgDto == null) {
			throw new BizException(MsgCons.C_30006, MsgCons.M_30006);
		}
		List<MarketAreaBuildingDTO> areas = marketAreaBuildingService.queryAreaByMarketId(orgDto.getId());
		AjaxResult<List<MarketAreaBuildingDTO>> result = new AjaxResult<>();
		result.setResult(areas);
		return result;
	}

	/**
	 * 加载数据字典性质属性
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws BizException
	 */
	@RequestMapping("getNature")
	@ResponseBody
	public AjaxResult<List<MarketAreaBuildingDTO>> getNature(HttpServletRequest request, HttpServletResponse response)
			throws BizException {
		List<MarketAreaBuildingDTO> natures = marketAreaBuildingService.queryNature();
		AjaxResult<List<MarketAreaBuildingDTO>> result = new AjaxResult<>();
		result.setResult(natures);
		return result;
	}

	/**
	 * 查看所有楼栋信息
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("getMarketBuildingInfo")
	@ResponseBody
	public AjaxResult<GuDengPage<MarketAreaBuildingDTO>> getMarketBuildingInfo(HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		AjaxResult<GuDengPage<MarketAreaBuildingDTO>> res = new AjaxResult<>();
		// 获取分页信息
		GuDengPage<MarketAreaBuildingDTO> page = getPageInfoByRequest();
		SysOrgDTO orgDto = super.getCurrentMarket();
		if (orgDto == null) {
			// throw new BizException(MsgCons.C_30006, MsgCons.M_30006);
			res.setCode(3000);
			res.setIsSuccess(false);
			return res;
		}
		page.getParaMap().put("marketId", orgDto.getId());
		page = marketAreaBuildingService.queryBuilding(page);
		res.setResult(page);
		return res;

	}

	/**
	 * 删除楼栋信息
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("delMarketBuilding")
	@ResponseBody
	public AjaxResult<Integer> delMarketBuilding(int buildingId) throws Exception {
		// 逻辑删除 只是改变状态 不是物理删除
		MarketAreaBuildingEntity entity = new MarketAreaBuildingEntity();
		entity.setStatus(Const.DELETED);
		entity.setId(buildingId);
		Integer res = marketAreaBuildingService.deleteAreaBuilding(entity);
		AjaxResult<Integer> ajaxResult = new AjaxResult<>();
		if (res < 0) {
			ajaxResult.setCode(res);
			ajaxResult.setIsSuccess(false);
			ajaxResult.setMsg("该楼栋下有资源 ,不可删除");
			return ajaxResult;
		}
		ajaxResult.setIsSuccess(true);
		ajaxResult.setCode(res);
		ajaxResult.setResult(res);
		ajaxResult.setMsg("删除成功");
		return ajaxResult;

	}

	// 批量处理===========================================================

	/**
	 * 进入批量新增
	 * 
	 * @param mv
	 * @return
	 * @throws BizException
	 */
	@RequestMapping(value = "addResourcePage")
	public ModelAndView addResourcePage(ModelAndView mv) throws BizException {

		mv.setViewName("lease/building/addResource");
		return mv;
	}

	/**
	 * 处理已租 楼栋
	 * 
	 * @param builId
	 *            楼栋ID
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("isResource")
	@ResponseBody
	public AjaxResult<Integer> isResource(String builId) throws Exception {
		// 是否已租
		int isResource = marketBuildingResourceService.querybuildingResTwo(Integer.parseInt(builId));
		//待放租
		int isRes=marketBuildingResourceService.querybuildingResOne(Integer.parseInt(builId));
		AjaxResult<Integer> ajaxResult = new AjaxResult<>();
		if (isResource > 0) {
			ajaxResult.setCode(1);
			ajaxResult.setIsSuccess(false);
			ajaxResult.setMsg("该楼栋已有资源，无法新增");
			return ajaxResult;
		}
		if (isRes > 0) {
			ajaxResult.setCode(2);
		    ajaxResult.setIsSuccess(false);
		    ajaxResult.setMsg("重新生成资源将删除原有资源，是否继续");
		    return ajaxResult;
		}
		ajaxResult.setIsSuccess(true);
		return ajaxResult;
	}

	/**
	 * 处理批量生成楼层 第1步 设置楼层及单元数目 第2步 设置楼层名称及每层数 第3步 设置楼层名称及每层数
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("volumeProduction")
	@ResponseBody
	public AjaxResult<Integer> volumeProduction(String resource, String buildings, String units, String builId,
			String builName, String builCode,String areaId) throws Exception {

		SysOrgDTO orgDto = super.getCurrentMarket();
		if (orgDto == null) {
			throw new BizException(MsgCons.C_30006, MsgCons.M_30006);
		}

		MarketResourceParam param = new MarketResourceParam();
		param.setBuilId(builId);
		param.setBuilCode(builCode);
		param.setBuilName(builName);
		param.setBuildings(buildings);
		param.setResource(resource);
		param.setUnits(units);
		// 查詢市場編碼以及區域編碼
		MarketAreaBuildingDTO codes = marketBuildingResourceService.queryCodeById(orgDto.getId(),Integer.parseInt(areaId));
//		Integer areaId = marketBuildingResourceService.queryAreaBybId(builId);
		codes.setCreateUserId(super.getUserId().toString());// 设置当前用户ID
		codes.setMarketId(orgDto.getId());
		codes.setAreaId(Integer.parseInt(areaId));
		int result = marketBuildingResourceService.handlerResource(codes, param);
		AjaxResult<Integer> ajaxResult = new AjaxResult<>();
		if (result < 0) {
			ajaxResult.setCode(result);
			ajaxResult.setIsSuccess(false);
			ajaxResult.setMsg("新增资源失败");
			return ajaxResult;
		}
		ajaxResult.setResult(result);
		ajaxResult.setIsSuccess(true);
		ajaxResult.setMsg("新增成功");
		return ajaxResult;

	}

	/**
	 * 查询所有的市场区域楼栋(木有分页)
	 * 
	 * @param request
	 * @param response
	 * @author cai.xu
	 * @return
	 * @throws BizException
	 */
	@RequestMapping("queryAll")
	@ResponseBody
	public AjaxResult<List<MarketAreaBuildingDTO>> queryAll(HttpServletRequest request, MarketAreaBuildingDTO entity)
			throws BizException {
		AjaxResult<List<MarketAreaBuildingDTO>> res = new AjaxResult<List<MarketAreaBuildingDTO>>();
		Map<String, Object> param = new HashMap<String, Object>();
		if (super.getCurrentMarket() == null) {
			return res;
		}
		param.put("marketId", super.getCurrentMarket().getId());
		if(entity.getAreaId()!=null){
			param.put("areaId", entity.getAreaId());
		}
		// param.put("status", StatusEnum.NORMAL.getKey());
		List<MarketAreaBuildingDTO> list = marketAreaBuildingService.queryAll(param);
		res.setResult(list);
		return res;
	}

	@RequestMapping("viewImgBuilding")
	public ModelAndView viewImgBuilding(ModelAndView mv) throws BizException {
		mv.setViewName("lease/building/viewImgBuilding");
		return mv;
	}

	@RequestMapping("uploadImgBuilding")
	public ModelAndView uploadImgBuilding(ModelAndView mv) throws BizException {
		mv.setViewName("lease/building/uploadImgBuilding");
		return mv;
	}
}
