package cn.gdeng.market.lease.controller.lease.settings;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.alibaba.fastjson.JSON;

import cn.gdeng.market.dto.bean.GuDengPage;
import cn.gdeng.market.dto.lease.settings.MarketExpenditureStandardDTO;
import cn.gdeng.market.entity.lease.settings.MarketExpenditureStandardEntity;
import cn.gdeng.market.enums.Const;
import cn.gdeng.market.enums.MsgCons;
import cn.gdeng.market.exception.BizException;
import cn.gdeng.market.lease.bean.AjaxResult;
import cn.gdeng.market.lease.controller.base.BaseController;
import cn.gdeng.market.lease.enums.StatusEnum;
import cn.gdeng.market.service.lease.contract.ContractManageService;
import cn.gdeng.market.service.lease.settings.MarketExpenditureStandardService;

/**
 * 计费标准
 *
 */
@Controller
@RequestMapping("expenditureStandard")
public class MarketExpenditureStandardController extends BaseController{
	
	private Logger logger = LoggerFactory.getLogger(MarketExpenditureStandardController.class);

	@Resource
	private MarketExpenditureStandardService expenditureStandardService;
	
	@Resource	
	private ContractManageService contractManageService;
	
	/**
	 * 添加计费
	 * @param request
	 * @param entity
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("addExpenditureStandard")
	@ResponseBody
	public AjaxResult<Long> addExpenditureStandard(MarketExpenditureStandardDTO entity) throws Exception{
		logger.info("添加计费标准入参:"+ JSON.toJSONString(entity));
		if(getCurrentMarket() == null)
		{
			throw new BizException(MsgCons.C_30006,MsgCons.M_30006);
		}
		AjaxResult<Long> result = new AjaxResult<>();
		int userId = getUserId();
		entity.setUserId(userId);
		entity.setMarketId(getCurrentMarket().getId());
		entity.setCreateTime(new Date());
		entity.setUpdateUserId(String.valueOf(userId));
		entity.setUpdateTime(new Date());
		Long id = expenditureStandardService.insert(entity);
		result.setResult(id);
		logger.info("添加计费标准成功，生成ID="+id);
		return result;
	}
	
	/**
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("updateExpenditureStandard")
	@ResponseBody
	public AjaxResult<Number> updateExpenditureStandard(MarketExpenditureStandardDTO entity) throws Exception{
		AjaxResult<Number> result = new AjaxResult<>();
		if(getCurrentMarket() == null)
		{
			throw new BizException(MsgCons.C_30006,MsgCons.M_30006);
		}
		
		entity.setUpdateUserId(getUserIdStr());
		entity.setMarketId(getCurrentMarket().getId());
		Number rows = expenditureStandardService.updateExpStandard(entity);
		result.setResult(rows);
		
		logger.info("修改计费标准成功，修改行数 rows="+rows);
		return result;
	}	
	
	/**
	 * 分页获取所有计费标准
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("listExpStandard")
	@ResponseBody
	public AjaxResult<GuDengPage<MarketExpenditureStandardDTO>> listExpenditure(Integer expId) throws Exception{
		
		AjaxResult<GuDengPage<MarketExpenditureStandardDTO>> result = new AjaxResult<>();
		//获取分页信息
		GuDengPage<MarketExpenditureStandardDTO> page = getPageInfoByRequest();
		//设置查询参数 
		Map<String, Object> paraMap = page.getParaMap();
		if(expId !=null && expId != 0)
		{
			paraMap.put("expId", expId);
		}
		if(getCurrentMarket() == null)
		{
			paraMap.put("marketId", -1);
		}else
		{
			paraMap.put("marketId", getCurrentMarket().getId());
		}
		paraMap.put("status", StatusEnum.NORMAL.getKey());
		page.setParaMap(paraMap);
		page = expenditureStandardService.queryByPage(page);
		result.setResult(page);
		return result;
	}
	
	/**
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("getById")
	@ResponseBody
	public AjaxResult<MarketExpenditureStandardDTO> getById(int id) throws Exception{
		
		AjaxResult<MarketExpenditureStandardDTO> result = new AjaxResult<>();
		
		MarketExpenditureStandardDTO object = expenditureStandardService.getById(id);
		result.setResult(object);
		return result;
	}	
	
	/**
	 * 获取计费标准关联的未执行/执行中的合同信息
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("validateDelExpStandard")
	@ResponseBody
	public AjaxResult<Map<String,Object>> validateDelExpStandard(int expStandardId) throws Exception{
		AjaxResult<Map<String,Object>> result = new AjaxResult<>();
		
		Map<String,Object> param = new HashMap<>();
		param.put("freightBasisId", expStandardId);
		int contractCount = contractManageService.getCountByMap(param);
		
		Map<String,Object> resultMap = new HashMap<>();
		resultMap.put("contractCount", contractCount);
		
		result.setResult(resultMap);
		return result;
	}
	
	/**
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("deleteById")
	@ResponseBody
	public AjaxResult<Integer> deleteById(int id) throws Exception{
		AjaxResult<Integer> result = new AjaxResult<>();
		MarketExpenditureStandardEntity param = new MarketExpenditureStandardEntity();
		param.setId(id);
		param.setUpdateUserId(getUserIdStr());
		param.setUpdateTime(new Date());
		param.setStatus(Const.DELETED);
		int rows = expenditureStandardService.deleteById(param);
		result.setResult(rows);
		return result;
	}	
	
	@RequestMapping("index")
	public ModelAndView index(ModelAndView mv) throws BizException {
		mv.setViewName("settings/expenditureStandard/index");
		return mv;
	}
	
	@RequestMapping("add")
	public ModelAndView addExp(ModelAndView mv) throws BizException {
		mv.setViewName("settings/expenditureStandard/add");
		return mv;
	}
	
	@RequestMapping("edit")
	public ModelAndView editExp(ModelAndView mv) throws BizException {
		mv.setViewName("settings/expenditureStandard/edit");
		return mv;
	}
}
