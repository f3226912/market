package cn.gdeng.market.lease.controller.lease.settings;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import cn.gdeng.market.lease.controller.base.BaseController;

@Controller
@RequestMapping("print")
public class PrintController extends BaseController {

	@RequestMapping("index")
	public ModelAndView index(ModelAndView mv){
		mv.setViewName("settings/print/index");
		return mv;
	}
}
