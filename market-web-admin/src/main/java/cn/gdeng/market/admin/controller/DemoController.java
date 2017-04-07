package cn.gdeng.market.admin.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import cn.gdeng.market.admin.controller.base.BaseController;
import cn.gdeng.market.exception.BizException;

/**
 * 前端与后台结合示例程序
 * 
 * @author lidong dli@gdeng.cn
 * @time 2016年10月9日 下午3:35:30
 */
@Controller
@RequestMapping("demo")
public class DemoController extends BaseController {
	/**
	 * 访问路径对应前端路由js中配置的templateUrl参数值<br>
	 * "chart":{
	 * 		templateUrl: "demo/chart",
	 * 		controller: "chartController",
	 * 		title: "谷登ERP-统计图"
	 * }
	 * 
	 * @param mv
	 * @return
	 * @throws BizException
	 * @author lidong dli@gdeng.cn
	 * @time 2016年10月9日 下午3:36:18
	 */
	@RequestMapping("chart")
	public ModelAndView chart(ModelAndView mv) throws BizException {
		mv.setViewName("page/chart");
		return mv;
	}
}
