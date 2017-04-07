package cn.gdeng.market.lease.util;

import java.util.Arrays;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.TagSupport;

import org.apache.commons.lang3.StringUtils;
import org.springframework.web.context.ContextLoader;
import org.springframework.web.context.WebApplicationContext;

import cn.gdeng.market.dto.admin.SysMenuButtonDTO;
import cn.gdeng.market.dto.admin.SysUserDTO;
import cn.gdeng.market.exception.BizException;

/**
 * 页面button授权部分标签
 * 
 * @author lidong dli@gdeng.cn
 * @time 2016年10月26日 上午9:19:36
 */
public class BaseAuthorizationBtnTag extends TagSupport {

	private static final long serialVersionUID = 8091523755633986920L;
	/** 按钮code */
	private String btncode;
	private static LoginUserUtil loginUserUtil;

	/**
	 * 显示tag;如果存在按钮，则显示该按钮，否则不显
	 */
	public int doStartTag() throws JspException {
		/* 获取bean */
		if (loginUserUtil == null) {
			WebApplicationContext webApplicationContext = ContextLoader.getCurrentWebApplicationContext();
			loginUserUtil = (LoginUserUtil) webApplicationContext.getBean("loginUserUtil");
		}
		HttpServletRequest request = (HttpServletRequest) pageContext.getRequest();
		try {
			SysUserDTO user = loginUserUtil.getSysUser(request);
			// 如果有取得session的用户按钮权限集合，则进行button的权限的check;如果取到，则不显
			List<SysMenuButtonDTO> buttons = user.getButtons();
			if (checkAuth(buttons)) {
				return SKIP_PAGE;
			}
		} catch (BizException e) {
			e.printStackTrace();
		}
		// 跳过body,body部分不会显示
		return SKIP_BODY;
	}

	public String getBtncode() {
		return btncode;
	}

	public void setBtncode(String btncode) {
		this.btncode = btncode;
	}

	/**
	 * 检测用户是否具有该按钮权限
	 * 
	 * @param buttons
	 * @return
	 * @author lidong dli@gdeng.cn
	 * @time 2016年10月20日 上午11:39:15
	 */
	public boolean checkAuth(List<SysMenuButtonDTO> buttons) {
		if (buttons != null) {
			for (SysMenuButtonDTO button : buttons) {
				String btnCode = button.getCode();
				if (StringUtils.isNotEmpty(btnCode)) {
					if (btnCode.equalsIgnoreCase(btncode)) {
						return true;
					}
				}
			}
		}
		return false;
	}

	public static void main(String[] args) {
		String a = "ad||bggg";
		List<String> ar = Arrays.asList(a.split("\\|\\|"));
		System.out.println(ar);
	}

}
