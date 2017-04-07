package cn.gdeng.market.lease.controller.lease.resources;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.alibaba.fastjson.JSON;

import cn.gdeng.market.dto.admin.SysOrgDTO;
import cn.gdeng.market.dto.admin.SysUserDTO;
import cn.gdeng.market.dto.bean.GuDengPage;
import cn.gdeng.market.dto.lease.resources.MarketAreaBuildingDTO;
import cn.gdeng.market.dto.lease.resources.MarketAreaDTO;
import cn.gdeng.market.dto.lease.resources.MarketAreaParam;
import cn.gdeng.market.dto.lease.settings.MarketDTO;
import cn.gdeng.market.enums.MsgCons;
import cn.gdeng.market.exception.BizException;
import cn.gdeng.market.lease.bean.AjaxResult;
import cn.gdeng.market.lease.controller.base.BaseController;
import cn.gdeng.market.lease.enums.StatusEnum;
import cn.gdeng.market.service.lease.resources.MarketAreaBuildingService;
import cn.gdeng.market.service.lease.resources.MarketAreaService;

/**
 * 市场控制器
 * @author bdhuang
 *
 */
@Controller
@RequestMapping("marketArea")
public class MarketAreaController extends BaseController{
	
	private Logger logger = LoggerFactory.getLogger(MarketAreaController.class);
	
	@Resource
	private MarketAreaService marketAreaService;
	
	@Resource
	private MarketAreaBuildingService marketAreaBuildingService;
	
	
	@RequestMapping("index")
	public ModelAndView marketIndex(ModelAndView mv) throws BizException {
		mv.setViewName("resources/marketArea/index");
		return mv;
	} 	
	
	@RequestMapping("addMarketArea")
	public ModelAndView addMarketArea(ModelAndView mv) throws BizException {
//		SysOrgDTO sysOrgDTO = super.getCurrentMarket();
//		if(sysOrgDTO==null){
//			throw new BizException(MsgCons.C_20000, "请选择市场");
//		}
		mv.setViewName("resources/marketArea/addMarketArea");
		return mv;
	}
	
	/**
	 * 列表查询
	 * @param request
	 * @return
	 * @author huangbd
	 * @throws BizException 
	 */
	@RequestMapping("query")
	@ResponseBody
	public AjaxResult<GuDengPage<MarketAreaDTO>> query(HttpServletRequest request,MarketAreaDTO dto) throws BizException {
		AjaxResult<GuDengPage<MarketAreaDTO>> res = new AjaxResult<>();
		SysOrgDTO sysOrgDTO = super.getCurrentMarket();
		if(sysOrgDTO==null){
			return res;
		}
		Integer marketId =sysOrgDTO.getId();
		//获取分页信息
		GuDengPage<MarketAreaDTO> page = getPageInfoByRequest();
		page.getParaMap().put("marketId", marketId);
		page = marketAreaService.queryByPage(page);
		res.setResult(page);
		return res;
	}
	
	/**
	 * 查询所有区域
	 * @param request
	 * @return
	 * @author cai.xu
	 * @throws BizException 
	 */
	@RequestMapping("queryAll")
	@ResponseBody
	public AjaxResult<GuDengPage<MarketAreaDTO>> queryAll(HttpServletRequest request,MarketAreaDTO dto) throws BizException {
		AjaxResult<GuDengPage<MarketAreaDTO>> res = new AjaxResult<>();
		//获取分页信息
		GuDengPage<MarketAreaDTO> page = getPageInfoByRequest();
		SysOrgDTO sysOrgDTO = super.getCurrentMarket();
		if(sysOrgDTO==null){
			return res;
		}
		page.getParaMap().put("marketId", super.getCurrentMarket().getId());
		page = marketAreaService.queryAll(page);
		res.setResult(page);
		return res;
	}
	/**
	 * 保存数据
	 * 
	 * @param request
	 * @return
	 * @author huangbd
	 * @throws BizException 
	 */
	@RequestMapping(value = { "save" })
	@ResponseBody
	public AjaxResult<Integer> save(HttpServletRequest request,MarketAreaParam  marketAreas) throws BizException {
		SysOrgDTO sysOrgDTO = super.getCurrentMarket();
		SysUserDTO sysUser = super.getSysUser();
		if(sysOrgDTO==null){
			throw new BizException(MsgCons.C_20000, "请选择市场");
		}
		Integer marketId = sysOrgDTO.getId();
		AjaxResult<Integer>  result=new AjaxResult<Integer>();
		if(marketAreas!=null && marketAreas.getMarketAreas() !=null && marketAreas.getMarketAreas().size() >0){
			for(MarketAreaDTO area:marketAreas.getMarketAreas()){
				area.setMarketId(marketId);
				area.setStatus(1);//默认有效
				area.setCreateUserId(String.valueOf(sysUser.getId()));
				if(marketAreaService.ifExistName(area)>0){
					throw new BizException(MsgCons.C_20000, "'" + area.getName() + "'的区域名称已经存在，请修改区域名称");
				}
				if(marketAreaService.ifExistCode(area)>0){
					throw new BizException(MsgCons.C_20000,"'" + area.getCode() + "'的区域编码已经存在，请修改区域编码");
				}
			}
			int count = marketAreaService.insertBatch(marketAreas.getMarketAreas());
			return result.setResult(count);
		}else {
			throw new BizException(MsgCons.C_30000, "参数异常");
		}
		
	}
	
	/**
	 * 跳转到查看页面
	 */
	@RequestMapping("view")
	public ModelAndView view(ModelAndView mv) throws BizException {
		mv.setViewName("resources/marketArea/viewMarketArea");
		return mv;
	}
	
	/**
	 * 跳转到上传页面
	 */
	@RequestMapping("uploadImgMarketArea")
	public ModelAndView uploadImgMarketArea(ModelAndView mv) throws BizException {
		mv.setViewName("resources/marketArea/uploadImgMarketArea");
		return mv;
	}
	
	/**
	 * 跳转查看页面
	 */
	@RequestMapping("viewImgMarketArea")
	public ModelAndView viewImgMarketArea(ModelAndView mv) throws BizException {
		mv.setViewName("resources/marketArea/viewImgMarketArea");
		return mv;
	}
	
	
	/**
	 * 根据id查看数据
	 * 
	 * @param request
	 * @return
	 * @author hbd
	 * 
	 */
	@RequestMapping("getByMarketAreaId")
	@ResponseBody
	public AjaxResult<MarketAreaDTO> getByMarketId(String marketAreaId, HttpServletRequest request) throws BizException{
		AjaxResult<MarketAreaDTO> res=new AjaxResult<>();
		MarketAreaDTO dto = marketAreaService.getById(marketAreaId);
		res.setResult(dto);
		return res;
	}
	
	
	@RequestMapping(value = { "update" })
	@ResponseBody
	public AjaxResult<Integer> update(HttpServletRequest request,MarketAreaDTO  dto) throws BizException {
		AjaxResult<Integer>  result=new AjaxResult<Integer>();
		int count = marketAreaService.update(dto);
		return result.setResult(count);
	}
	

	/**
	 * 根据id删除数据
	 * 
	 * @param request
	 * @return
	 * @author huangbd
	 */
	@RequestMapping(value = "delete")
	@ResponseBody
	public AjaxResult<Integer> delete(String id, HttpServletRequest request) throws BizException {
		AjaxResult<Integer>  result=new AjaxResult<Integer>();
		if(!StringUtils.isEmpty(id)){
			Map map=new HashMap();
			map.put("areaId", id);
			map.put("status", StatusEnum.NORMAL.getKey());
			int total = marketAreaBuildingService.getTotal(map);
			if(total>0){
				throw new BizException(MsgCons.C_20000, "该区域下有楼栋，不能删除");
			}
			int count = marketAreaService.deleteById(id);
			return result.setResult(count);
		}else {
			throw new BizException(MsgCons.C_30000, "参数异常");
		}
	}
	
}
