package cn.gdeng.market.admin.util;

import java.io.UnsupportedEncodingException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import cn.gdeng.market.admin.enums.MyConst;
import cn.gdeng.market.util.CookieUtil;

public class SessionUtil {

	/**
	 * 给客户端设置spring-session自定义sessionid
	 * 
	 * @param response
	 * @param value
	 *            sessionid
	 * @author lidong dli@gdeng.cn
	 * @time 2016年9月21日 上午10:54:34
	 */
	public static void setSessionid(HttpServletResponse response, String value) {
		CookieUtil.addCookie(response, null, MyConst.SEESION_COOKIE_NAME, value, 1800);
	}

	/**
	 * 获取客户端spring-session自定义sessionid
	 * 
	 * @param request
	 * @return
	 * @throws UnsupportedEncodingException
	 * @author lidong dli@gdeng.cn
	 * @time 2016年9月21日 上午10:57:26
	 */
	public static String getSessionid(HttpServletRequest request) throws UnsupportedEncodingException {
		return CookieUtil.getCookie(request, MyConst.SEESION_COOKIE_NAME);
	}

}
