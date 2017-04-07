package cn.gdeng.market.admin.controller.right;

import java.io.UnsupportedEncodingException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.session.ExpiringSession;
import org.springframework.session.MapSessionRepository;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import cn.gdeng.market.admin.controller.base.BaseController;
import cn.gdeng.market.admin.util.SessionUtil;

/**
 * 类说明 首页
 **/
@Controller
@RequestMapping("system1")
public class SpringsessionLeaseController extends BaseController{

	@Autowired
	private RedisTemplate redisTemplate;
	
	
	@RequestMapping("index")
	public ModelAndView index(HttpServletRequest request, HttpServletResponse response, ModelAndView mv) {
		mv.setViewName("system1/index");
		return mv;
	}

	@RequestMapping("loginSystem2")
	public ModelAndView login2(HttpServletRequest request, HttpServletResponse response, ModelAndView mv) {
		String sessionid = null;
		try {
			sessionid = SessionUtil.getSessionid(request);
			ExpiringSession session2 = new  MapSessionRepository().getSession(sessionid);
//			Object attribute = session2.getAttribute(Constants.SYSTEM_SMGRLOGINUSER);
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		mv.setViewName("redirect:http://www.c.com:8081/market-web-workflow/system1/login?_s=0&sessionid=" + sessionid);
		return mv;
	}
	
	@RequestMapping("login")
	public ModelAndView login(HttpServletRequest request, HttpServletResponse response, ModelAndView mv) {
		String sessionid = request.getParameter("sessionid");
		SessionUtil.setSessionid(response, sessionid);
		mv.setViewName("system1/index");
		return mv;
	}

}
