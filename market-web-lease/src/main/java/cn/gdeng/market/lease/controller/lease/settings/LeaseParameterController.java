package cn.gdeng.market.lease.controller.lease.settings;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.alibaba.dubbo.config.annotation.Reference;
import com.alibaba.fastjson.JSON;

import cn.gdeng.market.dto.admin.SysOrgDTO;
import cn.gdeng.market.dto.bean.GuDengPage;
import cn.gdeng.market.dto.lease.settings.SysContractRemindSettingInteractionDTO;
import cn.gdeng.market.entity.admin.SysOrgEntity;
import cn.gdeng.market.exception.BizException;
import cn.gdeng.market.lease.bean.AjaxResult;
import cn.gdeng.market.lease.controller.base.BaseController;
import cn.gdeng.market.service.lease.settings.SysContractRemindSettingService;

/**
* @author DJB
* @version 创建时间：2016年10月19日 上午9:35:14
* 租约管理参数设置
*/

@Controller
@RequestMapping("leaseParameter")
public class LeaseParameterController extends BaseController{
	private Logger logger = LoggerFactory.getLogger(LeaseParameterController.class);
	

	@Reference
	private  SysContractRemindSettingService sysContractRemindSettingService;
	
	
	
	
	@RequestMapping("index")
	public ModelAndView index(ModelAndView mv) throws BizException {
		mv.setViewName("settings/leaseParameter/index");
		return mv;
	}
	
	@RequestMapping("update")
	@ResponseBody
	public AjaxResult<Boolean> update(SysContractRemindSettingInteractionDTO dto) throws Exception {
		AjaxResult<Boolean> result = new AjaxResult<>();
		result.setResult(false);
		if (dto==null) {
			result.setResult(false);
			result.setMsg("请勿保存空数据");
		} else {
			dto.setUpdateUserId(super.getUserId());
			if (dto.getMarketId()==null) {
				SysOrgDTO sysOrgDTO=super.getCurrentMarket();
				dto.setMarketId(sysOrgDTO==null?null:sysOrgDTO.getId());	
			}
			if (dto.getOrgId()==null) {
				SysOrgDTO orgSysOrgDTO=super.getCurrentCompany();
				dto.setOrgId(orgSysOrgDTO==null?null:orgSysOrgDTO.getId());
				
			}
			if (dto.getMarketId()!=null&&dto.getOrgId()!=null) {
				dto=sysContractRemindSettingService.update(dto);
				result.setIsSuccess(true);
				result.setResult(true);
			}else {
				result.setResult(false);
				result.setMsg("对不起，您所在的市场不存在");
			}
		}
		return result;
	}
	
	
	@RequestMapping("querySysCRS")
	@ResponseBody
	public AjaxResult<SysContractRemindSettingInteractionDTO> querySysCRS() throws Exception{
		AjaxResult<SysContractRemindSettingInteractionDTO> res = new AjaxResult<>();
		SysContractRemindSettingInteractionDTO dto=null;
		SysOrgDTO sysOrgDTO=super.getCurrentMarket();
		if (sysOrgDTO!=null) {
			Integer marketId=sysOrgDTO.getId();
			dto= sysContractRemindSettingService.queryByMarketId(marketId);
		}
		res.setResult(dto);
		return res;
	}


}
