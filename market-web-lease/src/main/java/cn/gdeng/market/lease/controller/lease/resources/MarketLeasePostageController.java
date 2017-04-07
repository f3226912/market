package cn.gdeng.market.lease.controller.lease.resources;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.gdeng.market.dto.bean.GuDengPage;
import cn.gdeng.market.dto.lease.resources.MarketLeasePostageDTO;
import cn.gdeng.market.lease.bean.AjaxResult;
import cn.gdeng.market.lease.controller.base.BaseController;
import cn.gdeng.market.service.lease.resources.MarketLeasePostageService;

/**
 * 资源和合同管理控制器
 *
 */
@Controller
@RequestMapping(value = "marketLeasePostage")
public class MarketLeasePostageController extends BaseController {
	
	@Autowired
	private MarketLeasePostageService marketLeasePostageService;
	
	/**
	 * 根据条件分页查询
	 * @param request
	 * @param response
	 * @param dto
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/findByPage", method = RequestMethod.POST)
	@ResponseBody
	public AjaxResult<GuDengPage<MarketLeasePostageDTO>> findByPage(HttpServletRequest request, 
			HttpServletResponse response, MarketLeasePostageDTO dto) throws Exception {
		AjaxResult<GuDengPage<MarketLeasePostageDTO>> ar = new AjaxResult<>();
		GuDengPage<MarketLeasePostageDTO> page = getPageInfoByRequest();
		ar.setIsSuccess(true);
		page = marketLeasePostageService.getPageByCondition(page, dto);
		ar.setResult(page);
		return ar;
	}

}
