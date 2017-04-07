package cn.gdeng.market.lease.controller.lease.contract;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.gdeng.market.dto.bean.GuDengPage;
import cn.gdeng.market.dto.lease.contract.ContractMainDTO;
import cn.gdeng.market.dto.lease.settings.SysContractRemindSettingDTO;
import cn.gdeng.market.enums.ContractRemindTypeEnum;
import cn.gdeng.market.lease.bean.AjaxResult;
import cn.gdeng.market.lease.controller.base.BaseController;
import cn.gdeng.market.service.lease.contract.ContractManageService;
import cn.gdeng.market.service.lease.settings.SysContractRemindSettingService;

/**
 * 合同提醒
 *
 */
@Controller
@RequestMapping("contractRemind")
public class ContractRemindController extends BaseController {

	private Logger logger = LoggerFactory.getLogger(ContractRemindController.class);
	
    
    protected HttpServletResponse response = null;
    
	@Resource	
	private SysContractRemindSettingService sysContractRemindSettingService;


	@RequestMapping("index")
	public String index(){
		return "settings/contractRemind/index";
	}
	@RequestMapping("contractEndTimeRemind")
	public String contractEndTimeRemind(){
		return "settings/contractRemind/contractEndTimeRemind";
	}
	@Resource	
	private ContractManageService contractManageService;
	/**
	 * 分页查询 
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("endTimeRemindQueryPage")
	@ResponseBody
	public AjaxResult<GuDengPage<ContractMainDTO>>  queryPage(HttpServletRequest request)
			throws Exception{
		Map<String, Object> paramMap = new HashMap<String, Object>();
		AjaxResult<GuDengPage<ContractMainDTO>> re = new AjaxResult<>();
		GuDengPage<ContractMainDTO> page=new GuDengPage<ContractMainDTO> ();
		if(null!=super.getCurrentMarket()){
		Integer marketId= super.getCurrentMarket().getId();
		if(marketId!=null&&!"".equals(marketId)){
		paramMap.put("type", ContractRemindTypeEnum.ENDTIME.getCode());
		paramMap.put("marketId", marketId);
		SysContractRemindSettingDTO dto=sysContractRemindSettingService.queryContractRemindSettingByMarket(paramMap);
		setCommParameters(request, paramMap);
		if(dto!=null){
		  if(dto.getEndDateSrt()!=null){
			  paramMap.put("endTime", dto.getEndDateSrt()); 
			  page.setParaMap(paramMap);
			  page=contractManageService.getByConditionPage(page);
		  }
		}
		}
		}
		page.setParaMap(paramMap);
		re.setResult(page);
		return  re;
	}
	
	
	
}
