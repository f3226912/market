package cn.gdeng.market.lease.controller.lease.settings;

import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.alibaba.fastjson.JSON;

import cn.gdeng.market.dto.admin.SysOrgDTO;
import cn.gdeng.market.dto.admin.SysUserDTO;
import cn.gdeng.market.dto.bean.GuDengPage;
import cn.gdeng.market.dto.lease.settings.MarketExpenditureDTO;
import cn.gdeng.market.dto.lease.settings.MarketMeasureTypeDTO;
import cn.gdeng.market.entity.lease.settings.MarketMeasureTypeEntity;
import cn.gdeng.market.enums.MsgCons;
import cn.gdeng.market.exception.BizException;
import cn.gdeng.market.lease.bean.AjaxResult;
import cn.gdeng.market.lease.controller.base.BaseController;
import cn.gdeng.market.lease.enums.ExpType;
import cn.gdeng.market.lease.enums.StatusEnum;
import cn.gdeng.market.service.lease.settings.MarketExpenditureService;
import cn.gdeng.market.service.lease.settings.MarketMeasureTypeService;

/**
 * 计量表分类controller
 * @author cai.xu
 *
 */
@Controller
@RequestMapping("measureType")
public class MarketMeasureTypeController extends BaseController {
	@Resource
	MarketMeasureTypeService marketMeasureTypeService;
	@Resource
	MarketExpenditureService marketExpenditureService;
	
	/**
	 * 列表页面
	 * @param mv
	 * @return
	 * @throws BizException
	 */
	@RequestMapping("view")
	public ModelAndView chart(ModelAndView mv) throws BizException {
		mv.setViewName("settings/marketMeasureType/index");
		return mv;
	}
	
	/**
	 * 进入添加页面
	 * @param mv
	 * @return
	 * @throws BizException
	 */
	@RequestMapping(value = "view-add")
	public ModelAndView addView(ModelAndView mv) throws BizException {
		mv.setViewName("settings/marketMeasureType/add");
		return mv;
	}
	
	/**
	 * 进入编辑页面
	 * @param mv
	 * @return
	 * @throws BizException
	 */
	@RequestMapping(value = "view-edit")
	public ModelAndView editView(ModelAndView mv) throws BizException {
		mv.setViewName("settings/marketMeasureType/edit");
		return mv;
	}
	
	/**
	 * 计量表分类列表
	 * @param request
	 * @return
	 * @throws BizException 
	 */
	@RequestMapping("query")
	@ResponseBody
	public AjaxResult<GuDengPage<MarketMeasureTypeDTO>> query(HttpServletRequest request, MarketMeasureTypeDTO entity) throws BizException{
		AjaxResult<GuDengPage<MarketMeasureTypeDTO>> res = new AjaxResult<>();
		//获取分页信息
		GuDengPage<MarketMeasureTypeDTO> page = super.getPageInfoByRequest(entity);
		Map<String, Object> paraMap = page.getParaMap();
		//设置查询参数 
		if(null != super.getCurrentMarket()){
			paraMap.put("marketId", super.getCurrentMarket().getId());
			paraMap.put("status", StatusEnum.NORMAL.getKey());
			page.setParaMap(paraMap);
			page = marketMeasureTypeService.queryListByPage(page);
			res.setResult(page);
		}
		return res;
	}
	
	/**
	 * 计量表分类列表
	 * @param request
	 * @return
	 * @throws BizException 
	 */
	@RequestMapping("queryAll")
	@ResponseBody
	public AjaxResult<GuDengPage<MarketMeasureTypeDTO>> queryAll(HttpServletRequest request, MarketMeasureTypeDTO entity) throws BizException{
		AjaxResult<GuDengPage<MarketMeasureTypeDTO>> res = new AjaxResult<>();
		//获取分页信息
		GuDengPage<MarketMeasureTypeDTO> page = super.getPageInfoByRequest(entity);
		Map<String, Object> paraMap = page.getParaMap();
		//设置查询参数 
		if(null != super.getCurrentMarket()){
			paraMap.put("marketId", super.getCurrentMarket().getId());
			paraMap.put("status", StatusEnum.NORMAL.getKey());
			page.setParaMap(paraMap);
			page = marketMeasureTypeService.queryAllList(page);
			res.setResult(page);
		}
		return res;
	}
	
	/**
	 * 新增计量分类
	 * @param request
	 * @param entity
	 * @return
	 * @throws BizException
	 */
	@RequestMapping("add")
	@ResponseBody
	public AjaxResult<Integer> addMeasureType(HttpServletRequest request, MarketMeasureTypeEntity entity) throws BizException{
		logger.info("新增计量分类入参:"+JSON.toJSONString(entity));
		if(getCurrentMarket() == null)
		{
			throw new BizException(MsgCons.C_30006,MsgCons.M_30006);
		}
		
		AjaxResult<Integer> res = new AjaxResult<>();
		
		entity.setStatus(StatusEnum.NORMAL.getKey());
		entity.setCreateUserId(getUserIdStr());
		entity.setCreateTime(new Date());
		entity.setMarketId(super.getCurrentMarket().getId());
		entity.setUpdateUserId(getUserIdStr());
		entity.setUpdateTime(new Date());
		
		int id = marketMeasureTypeService.addMeasureType(entity);
		res.setResult(id);
		return res;
	}
	
	/**
	 * 编辑计量表分类
	 * @param request
	 * @param id
	 * @return
	 * @throws BizException
	 */
	@RequestMapping("edit")
	@ResponseBody
	public AjaxResult<Integer> editMeasureType(HttpServletRequest request,MarketMeasureTypeEntity entity) throws BizException{
		AjaxResult<Integer> result = new AjaxResult<>();
		//当前登录的用户
		SysUserDTO user = super.getSysUser();
		if(null == user){
			throw new BizException(MsgCons.C_30000,"请重新登录!");
		}
		SysOrgDTO sysOrgDTO = super.getCurrentMarket();
		if(null == sysOrgDTO){
			throw new BizException(MsgCons.C_30006,MsgCons.M_30006);
		}
		entity.setMarketId(sysOrgDTO.getId());
		entity.setUpdateUserId(getUserIdStr());
		entity.setUpdateTime(new Date());
		int res = marketMeasureTypeService.editMeasureType(entity);
		if (res < 0){
			result.setIsSuccess(false);
			result.setCodeMsg(MsgCons.C_30101, MsgCons.M_30101);
		}
		return result;
	}
	
	/**
	 * 删除计量表分类
	 * @param request
	 * @param id
	 * @return
	 * @throws BizException
	 */
	@RequestMapping("del")
	@ResponseBody
	public AjaxResult<Integer> delMeasureType(HttpServletRequest request, int id) throws BizException{
		AjaxResult<Integer> result = new AjaxResult<>();
		//当前登录的用户
		SysUserDTO user = super.getSysUser();
		if(null == user){
			throw new BizException(MsgCons.C_30000,"请重新登录!");
		}
		MarketMeasureTypeEntity entity = new MarketMeasureTypeEntity();
		entity.setId(id);
		entity.setStatus(StatusEnum.DELETED.getKey());
		entity.setUpdateUserId(getUserIdStr());
		entity.setUpdateTime(new Date());
		int res = marketMeasureTypeService.delMeasureType(entity);
		if (res < 0){
			result.setIsSuccess(false);
			result.setCodeMsg(MsgCons.C_30101, MsgCons.M_30101);
		}
		return result;
	}
	/**
	 * 取所有走表类费项
	 * @return
	 * @throws BizException
	 */
	@RequestMapping("expList")
	@ResponseBody
	public AjaxResult<List<MarketExpenditureDTO>> expList() throws BizException {
		AjaxResult<List<MarketExpenditureDTO>> result = new AjaxResult<>();
		//当前登录的用户
		MarketExpenditureDTO entity = new MarketExpenditureDTO();
		if(null != super.getCurrentMarket()){
			entity.setMarketId(super.getCurrentMarket().getId());
			entity.setExpType(ExpType.TABLE.getKey());
			entity.setStatus(StatusEnum.NORMAL.getKey());
			List<MarketExpenditureDTO> list = marketExpenditureService.getExpList(entity);
			result.setResult(list);
		}
		return result;
	}
	
}
