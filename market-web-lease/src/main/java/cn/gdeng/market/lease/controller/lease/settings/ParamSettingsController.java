package cn.gdeng.market.lease.controller.lease.settings;



import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import cn.gdeng.market.exception.BizException;
import cn.gdeng.market.lease.controller.base.BaseController;

/**
 * 参数设置controller
 * @author cai.xu
 *
 */
@Controller
@RequestMapping("parameter")
public class ParamSettingsController extends BaseController {
	
	@RequestMapping("view")
	public ModelAndView chart(ModelAndView mv) throws BizException {
		mv.setViewName("settings/parameter/index");
		return mv;
	}
	
}
