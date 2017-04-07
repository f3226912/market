package cn.gdeng.market.lease.controller.lease.settings;

import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import cn.gdeng.market.dto.admin.SysUserDTO;
import cn.gdeng.market.dto.bean.GuDengPage;
import cn.gdeng.market.dto.lease.settings.GaugeChargeRecordDTO;
import cn.gdeng.market.exception.BizException;
import cn.gdeng.market.lease.bean.AjaxResult;
import cn.gdeng.market.lease.controller.base.BaseController;
import cn.gdeng.market.lease.util.LoginUserUtil;
import cn.gdeng.market.service.lease.settings.GaugeChargeRecordService;

/**
 * 计量表抄表收费记录Controller
 * @author cai.x
 *
 */
@Controller
@RequestMapping("chargeRecord")
public class GaugeChargeRecordController extends BaseController {
	
	@Resource
	GaugeChargeRecordService gaugeChargeRecordService;
	
	@RequestMapping("view")
	public ModelAndView chart(ModelAndView mv) throws BizException {
		mv.setViewName("page/charge_record");
		return mv;
	}
	
	/**
	 * 计量表抄表收费记录列表
	 * @param request
	 * @return
	 * @throws Exception 
	 */
	@RequestMapping("query")
	@ResponseBody
	public AjaxResult<GuDengPage<GaugeChargeRecordDTO>> query(HttpServletRequest request, GaugeChargeRecordDTO entity) throws Exception{
		AjaxResult<GuDengPage<GaugeChargeRecordDTO>> res = new AjaxResult<>();
		//当前登录的用户
		SysUserDTO user = new LoginUserUtil().getSysUser(request);
		//获取分页信息
		GuDengPage<GaugeChargeRecordDTO> page = super.getPageInfoByRequest(entity);
		Map<String, Object> paraMap = page.getParaMap();
		//设置查询参数 
		paraMap.put("marketId", user.getMarketId());
		page.setParaMap(paraMap);
		page = gaugeChargeRecordService.queryList(page);
		res.setResult(page);
		return res;
	}
	
	
}
