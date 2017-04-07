package cn.gdeng.market.admin.controller.admin;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import cn.gdeng.market.admin.controller.base.BaseController;
import cn.gdeng.market.exception.BizException;
@Controller
@RequestMapping("")
public class ErrorController extends BaseController {
	@RequestMapping("404")
	public ModelAndView login(ModelAndView mv) throws BizException {
		mv.setViewName("page/404");
		return mv;
	}
	

}
