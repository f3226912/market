package cn.gdeng.market.lease.controller.lease.settings;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.alibaba.fastjson.JSON;

import cn.gdeng.market.dto.admin.SysUserDTO;
import cn.gdeng.market.dto.bean.GuDengPage;
import cn.gdeng.market.dto.lease.resources.MarketLeasePostageDTO;
import cn.gdeng.market.dto.lease.settings.MarketMeasureSettingDTO;
import cn.gdeng.market.entity.lease.settings.MarketExpenditureEntity;
import cn.gdeng.market.entity.lease.settings.MarketMeasureSettingEntity;
import cn.gdeng.market.enums.MsgCons;
import cn.gdeng.market.exception.BizException;
import cn.gdeng.market.lease.bean.AjaxResult;
import cn.gdeng.market.lease.controller.base.BaseController;
import cn.gdeng.market.lease.enums.StatusEnum;
import cn.gdeng.market.service.lease.contract.ContractManageService;
import cn.gdeng.market.service.lease.settings.MarketMeasureSettingService;

/**
 * 计量表管理controller
 * @author cai.xu
 *
 */
@Controller
@RequestMapping("measureSetting")
public class MarketMeasureSettingController extends BaseController {
	@Resource
	MarketMeasureSettingService marketMeasureSettingService;
	
	@Resource
	ContractManageService contractManageService;
	
	@RequestMapping("view")
	public ModelAndView view(ModelAndView mv) throws BizException {
		mv.setViewName("settings/marketMeasureSetting/index");
		return mv;
	}
	@RequestMapping("view-add")
	public ModelAndView viewAdd(ModelAndView mv) throws BizException {
		mv.setViewName("settings/marketMeasureSetting/add");
		return mv;
	}
	@RequestMapping("view-history")
	public ModelAndView viewHistory(ModelAndView mv) throws BizException {
		mv.setViewName("settings/marketMeasureSetting/history");
		return mv;
	}
	@RequestMapping("view-info")	
	public ModelAndView viewInfo(ModelAndView mv) throws BizException {
		mv.setViewName("settings/marketMeasureSetting/info");
		return mv;
	}
	@RequestMapping("view-batchAdd")
	public ModelAndView viewBatchAdd(ModelAndView mv) throws BizException {
		mv.setViewName("settings/marketMeasureSetting/batch_add");
		return mv;
	}
	
	/**
	 * 计量表管理列表
	 * @param request
	 * @return
	 * @throws Exception 
	 */
	@RequestMapping("query")
	@ResponseBody
	public AjaxResult<GuDengPage<MarketMeasureSettingDTO>> query(HttpServletRequest request, MarketMeasureSettingDTO entity) throws Exception{
		AjaxResult<GuDengPage<MarketMeasureSettingDTO>> res = new AjaxResult<>();
		//获取分页信息
		GuDengPage<MarketMeasureSettingDTO> page = super.getPageInfoByRequest(entity);
		Map<String, Object> paraMap = page.getParaMap();
		//设置查询参数 
		if(null != super.getCurrentMarket()){
			paraMap.put("marketId", super.getCurrentMarket().getId());
			paraMap.put("isDeleted", StatusEnum.NORMAL.getKey());
			page.setParaMap(paraMap);
			page = marketMeasureSettingService.queryListByPage(page);
			res.setResult(page);
		}
		return res;
	}
	
	/**
	 * 新增计量表
	 * @param request
	 * @param entity
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("add")
	@ResponseBody
	public AjaxResult<Integer> addMeasureSetting(MarketMeasureSettingEntity entity) throws Exception{
		logger.info("新增计量表入参:"+JSON.toJSONString(entity));
		//当前登录的用户
		SysUserDTO user = super.getSysUser();
		if(null == user){
			throw new Exception("请重新登录!");
		}
		if(getCurrentMarket() == null)
		{
			throw new BizException(MsgCons.C_30006,MsgCons.M_30006);
		}
		entity.setCreateUserId(getUserIdStr());
		entity.setCreateTime(new Date());
		entity.setIsDeleted(StatusEnum.NORMAL.getKey());
		entity.setUpdateUserId(getUserIdStr());
		entity.setUpdateTime(new Date());
		AjaxResult<Integer> res = new AjaxResult<>();
		int id = marketMeasureSettingService.addMeasureSetting(entity,getCurrentMarket().getId());
		res.setResult(id);
		return res;
	}
	
	
	/**
	 * 修改计量表
	 * @param request
	 * @param entity
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("edit")
	@ResponseBody
	public AjaxResult<Integer> editMeasureSetting(MarketMeasureSettingEntity entity) throws Exception{
		logger.info("新增计量表入参:"+JSON.toJSONString(entity));
		//当前登录的用户
		SysUserDTO user = super.getSysUser();
		if(null == user){
			throw new Exception("请重新登录!");
		}
		if(getCurrentMarket() == null)
		{
			throw new BizException(MsgCons.C_30006,MsgCons.M_30006);
		}
		entity.setIsDeleted(StatusEnum.NORMAL.getKey());
		entity.setUpdateUserId(getUserIdStr());
		entity.setUpdateTime(new Date());
		AjaxResult<Integer> res = new AjaxResult<>();
		int id = marketMeasureSettingService.editMeasureSetting(entity,getCurrentMarket().getId());
		res.setResult(id);
		return res;
	}
	/**
	 * 批量新增计量表
	 * @param request
	 * @param entity
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("batchAdd")
	@ResponseBody
	public AjaxResult<Integer> batchAddMeasureSetting(HttpServletRequest request, MarketMeasureSettingDTO entity) throws Exception{
		logger.info("新增计量表入参:"+JSON.toJSONString(entity));
		//当前登录的用户
		SysUserDTO user = super.getSysUser();
		if(null == user){
			throw new Exception("请重新登录!");
		}
		if(getCurrentMarket() == null)
		{
			throw new BizException(MsgCons.C_30006,MsgCons.M_30006);
		}
		entity.setIsDeleted(StatusEnum.NORMAL.getKey());
		entity.setCreateUserId(getUserIdStr());
		entity.setUpdateUserId(getUserIdStr());
		AjaxResult<Integer> res = new AjaxResult<>();
		int id = marketMeasureSettingService.batchAddMeasureSetting(entity,getCurrentMarket().getId());
		res.setResult(id);
		return res;
	}
	
	/**
	 * 删除
	 * @param request
	 * @param id
	 * @param optFlag 0:删除操
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("del")
	@ResponseBody
	public AjaxResult<Integer> delMeasureSetting(HttpServletRequest request, int id, int optFlag) throws Exception{
		AjaxResult<Integer> result = new AjaxResult<>();
		//当前登录的用户
		SysUserDTO user = super.getSysUser();
		if(null == user){
			throw new Exception("请重新登录!");
		}
		MarketMeasureSettingEntity entity = new MarketMeasureSettingEntity();
		entity.setId(id);
		if(optFlag == 0){
			MarketMeasureSettingDTO dto = marketMeasureSettingService.getBySettingId(id);
			if(StatusEnum.NORMAL.getKey() == dto.getStatus()){
				throw new BizException(MsgCons.C_20000, "不允许删除启用状态的计量表。");
			}
			entity.setIsDeleted(StatusEnum.DELETED.getKey());
		}else if(optFlag == 1){
			entity.setStatus(StatusEnum.NORMAL.getKey());
			entity.setIsDeleted(StatusEnum.NORMAL.getKey());
		}else if(optFlag == 2){
			entity.setStatus(StatusEnum.DELETED.getKey());
			entity.setIsDeleted(StatusEnum.NORMAL.getKey());
		}
		entity.setUpdateUserId(getUserIdStr());
		entity.setUpdateTime(new Date());
		int res = marketMeasureSettingService.delMeasureSetting(entity);
		if (res < 0){
			result.setIsSuccess(false);
			result.setCodeMsg(MsgCons.C_30101, MsgCons.M_30101);
		}
		return result;
	}
	
	/**
	 * 批量启用、禁用
	 * @param request
	 * @param ids
	 * @param optFlag 1:启用	2:禁用
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("enableOrDisable")
	@ResponseBody
	public AjaxResult<Integer> enableOrDisable(HttpServletRequest request, String ids, int optFlag) throws Exception{
		AjaxResult<Integer> result = new AjaxResult<>();
		/*MarketMeasureSettingEntity entity = new MarketMeasureSettingEntity();
		entity.setId(id);
		if(optFlag == 1){
			entity.setStatus(StatusEnum.NORMAL.getKey());
			entity.setIsDeleted(StatusEnum.NORMAL.getKey());
		}else if(optFlag == 2){
			entity.setStatus(StatusEnum.DELETED.getKey());
			entity.setIsDeleted(StatusEnum.NORMAL.getKey());
		}
		entity.setUpdateUserId(getUserIdStr());
		entity.setUpdateTime(new Date());*/
		int res = marketMeasureSettingService.enableOrDisable(ids, optFlag);
		if (res < 0){
			result.setIsSuccess(false);
			result.setCodeMsg(MsgCons.C_30101, MsgCons.M_30101);
		}
		return result;
	}
	
	/**
	 * 根据计量表分类ID 找到对应的费项名
	 * @param request
	 * @param measureType
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("queryExp")
	@ResponseBody
	public AjaxResult<MarketExpenditureEntity> queryExp(HttpServletRequest request, int measureTypeId)throws Exception{
		AjaxResult<MarketExpenditureEntity> res = new AjaxResult<MarketExpenditureEntity>();
		MarketExpenditureEntity entity = marketMeasureSettingService.findById(measureTypeId);
		res.setResult(entity);
		return res;
	}
	
	/**
	 * 根据计量表分类ID获取计量表数据的条数
	 * @param request
	 * @param measureType
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("queryCountByMeasureTypeId")
	@ResponseBody
	public AjaxResult<Integer> queryCountByMeasureTypeId(int measureTypeId)throws Exception{
		AjaxResult<Integer> res = new AjaxResult<Integer>();
		
		Map<String,Object> params = new HashMap<>();
		params.put("measureTypeId", measureTypeId);
		params.put("status", StatusEnum.NORMAL.getKey());
		
		int count = marketMeasureSettingService.queryCount(params);
		res.setResult(count);
		return res;
	}
	
	@RequestMapping("queryContractByResourceId")
	@ResponseBody
	public AjaxResult<List<MarketLeasePostageDTO>> queryContractByResourceId(int resourceId)throws Exception{
		AjaxResult<List<MarketLeasePostageDTO>> result = new AjaxResult<>();
		
		Map<String,Object> paramMap = new HashMap<>();
		paramMap.put("resourceId", resourceId);
		List<MarketLeasePostageDTO> list = contractManageService.getContractsByResAndStatus(paramMap);
		result.setResult(list);
		return result;
	}
	
	@RequestMapping("forward")	
	public ModelAndView forward(ModelAndView mv) throws BizException {
		mv.setViewName("settings/marketMeasureSetting/forward");
		return mv;
	}
	
	@ResponseBody
	@RequestMapping("forwardSettingDetail")
	public AjaxResult<MarketMeasureSettingDTO> forwardSettingDetail(int measureId)throws Exception{
		AjaxResult<MarketMeasureSettingDTO> result = new AjaxResult<>();
		MarketMeasureSettingDTO dto = marketMeasureSettingService.getBySettingId(measureId);
		result.setResult(dto);
		return result;
	}
}
