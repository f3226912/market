package cn.gdeng.market.lease.controller.lease.settings;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import cn.gdeng.market.dto.bean.GuDengPage;
import cn.gdeng.market.dto.lease.settings.MarketExpenditureDTO;
import cn.gdeng.market.entity.lease.settings.MarketExpenditureEntity;
import cn.gdeng.market.enums.Const;
import cn.gdeng.market.enums.MsgCons;
import cn.gdeng.market.exception.BizException;
import cn.gdeng.market.lease.bean.AjaxResult;
import cn.gdeng.market.lease.controller.base.BaseController;
import cn.gdeng.market.lease.enums.StatusEnum;
import cn.gdeng.market.lease.enums.SystemTypeEnum;
import cn.gdeng.market.service.lease.contract.ContractManageService;
import cn.gdeng.market.service.lease.settings.MarketExpenditureService;
import cn.gdeng.market.service.lease.settings.MarketExpenditureStandardService;
import cn.gdeng.market.service.lease.settings.MarketMeasureSettingService;

/**
 * 费项管理
 *
 */
@Controller
@RequestMapping("expenditure")
public class MarketExpenditureController extends BaseController{
	
	private Logger logger = LoggerFactory.getLogger(MarketExpenditureController.class);

	@Resource
	private MarketExpenditureService expenditureService;
	
	@Resource
	private MarketMeasureSettingService measureSettingService;
	
	@Resource
	private MarketExpenditureStandardService expStandardService;
	
	@Resource	
	private ContractManageService contractManageService;
	
	/**
	 * 分页获取费项列表
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("listPageExpenditure")
	@ResponseBody
	public AjaxResult<GuDengPage<MarketExpenditureDTO>> listPageExpenditure() throws Exception{
		
		AjaxResult<GuDengPage<MarketExpenditureDTO>> result = new AjaxResult<>();
		//获取分页信息
		GuDengPage<MarketExpenditureDTO> page = getPageInfoByRequest();
		
		Map<String, Object> paraMap = page.getParaMap();
		//设置查询参数 
		if(getCurrentMarket() == null)
		{
			paraMap.put("marketId", -1);
		}else
		{
			paraMap.put("marketId", getCurrentMarket().getId());
		}
		paraMap.put("status", StatusEnum.NORMAL.getKey());
		page.setParaMap(paraMap);
		//查询
		page = expenditureService.queryByPage(page);
		result.setResult(page);
		return result;
	}	
	
	/**
	 * 一次性获取有所费项，费项类型：该类型下的所有费项，以及费项包含的所有计费标准，提供给合同的接口
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("listExpenditure")
	@ResponseBody
	public AjaxResult<Map<String,Object>> listExpenditure() throws Exception{
		
		AjaxResult<Map<String,Object>> result = new AjaxResult<>();
		//设置查询参数
		Map<String,Object> paramMap = new HashMap<>();
		paramMap.put("marketId", super.getCurrentMarket().getId());
		paramMap.put("status", StatusEnum.NORMAL.getKey());
		//查询
		Map<String,Object> map = expenditureService.queryAllExpList(paramMap);
		result.setResult(map);
		return result;
	}	
	
	/**
	 * 根据ID获取费项信息
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("getById")
	@ResponseBody
	public AjaxResult<MarketExpenditureDTO> getById(int id) throws Exception{
		
		AjaxResult<MarketExpenditureDTO> result = new AjaxResult<>();
		
		MarketExpenditureDTO exp = expenditureService.getById(id);
		result.setResult(exp);
		return result;
	}	
	
	/**
	 * 根据动态条件获取费项信息
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("getByParams")
	@ResponseBody
	public AjaxResult<List<MarketExpenditureDTO>> getByParams(MarketExpenditureDTO params) throws BizException{
		
		AjaxResult<List<MarketExpenditureDTO>> result = new AjaxResult<>();
		
		params.setStatus(StatusEnum.NORMAL.getKey());
		params.setMarketId(getCurrentMarket().getId());
		
		List<MarketExpenditureDTO> resultList = expenditureService.getExpList(params);
		result.setResult(resultList);
		return result;
	}	
	
	/**
	 * 根据ID删除
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("deleteById")
	@ResponseBody
	public AjaxResult<Integer> deleteById(int id) throws Exception{
		AjaxResult<Integer> result = new AjaxResult<>();
		
		MarketExpenditureEntity param = new MarketExpenditureEntity();
		param.setId(id);
		param.setUpdateUserId(getUserIdStr());
		param.setUpdateTime(new Date());
		param.setStatus(Const.DELETED);
		int rows = expenditureService.deleteExpenditure(param);
		result.setResult(rows);
		return result;
	}	
	
	/**
	 * 添加费项
	 * @param request
	 * @param entity
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("addExpenditure")
	@ResponseBody
	public AjaxResult<Long> addExpenditure(HttpServletRequest request,MarketExpenditureEntity entity) throws BizException{
		logger.info("添加费项入参:"+entity.toString());
		if(getCurrentMarket() == null)
		{
			throw new BizException(MsgCons.C_30006,MsgCons.M_30006);
		}
		AjaxResult<Long> result = new AjaxResult<>();
		entity.setMarketId(getCurrentMarket().getId());
		entity.setStatus(Const.NORMAL);
		entity.setSysType(SystemTypeEnum.CUSTOMER.getStatus());
		entity.setCreateUserId(getUserIdStr());
		entity.setCreateTime(new Date());
		entity.setUpdateUserId(getUserIdStr());
		entity.setUpdateTime(new Date());
		
		Long id = expenditureService.insert(entity);
		result.setResult(id);
		logger.info("添加费项成功，生成ID="+id);
		return result;
	}
	
	/**
	 * 修改
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("updateExp")
	@ResponseBody
	public AjaxResult<Integer> update(MarketExpenditureEntity entity) throws Exception{
		AjaxResult<Integer> result = new AjaxResult<>();
		entity.setUpdateUserId(getUserIdStr());
		entity.setUpdateTime(new Date());
		int rows = expenditureService.updateExpenditure(entity);
		result.setResult(rows);
		return result;
	}
	
	/**
	 * 获取费项关联的 计费标准、计量表信息、未执行/执行中的合同信息
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("validateDelExp")
	@ResponseBody
	public AjaxResult<Map<String,Object>> validateDelExp(int expId) throws Exception{
		AjaxResult<Map<String,Object>> result = new AjaxResult<>();
		
		Map<String,Object> param = new HashMap<>();
		param.put("expId", expId);
		param.put("status", StatusEnum.NORMAL.getKey());
		param.put("itemCategoryId", expId);
		
		int expStandardCount = expStandardService.queryCountByCondition(param);
		int measureSettingCount = measureSettingService.queryCount(param);
		int contractCount = contractManageService.getCountByMap(param);
		
		Map<String,Object> resultMap = new HashMap<>();
		resultMap.put("expStandardCount", expStandardCount);
		resultMap.put("measureSettingCount", measureSettingCount);
		resultMap.put("contractCount", contractCount);
		
		result.setResult(resultMap);
		return result;
	}
	
	@RequestMapping("index")
	public ModelAndView index(ModelAndView mv) throws BizException {
		mv.setViewName("settings/expenditure/index");
		return mv;
	}
	
	@RequestMapping("addExp")
	public ModelAndView addExp(ModelAndView mv) throws BizException {
		mv.setViewName("settings/expenditure/addExp");
		return mv;
	}
	
	@RequestMapping("editExp")
	public ModelAndView editExp(ModelAndView mv) throws BizException {
		mv.setViewName("settings/expenditure/editExp");
		return mv;
	}
}
