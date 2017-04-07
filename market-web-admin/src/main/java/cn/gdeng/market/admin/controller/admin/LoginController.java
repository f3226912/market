package cn.gdeng.market.admin.controller.admin;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import cn.gdeng.market.admin.bean.AjaxResult;
import cn.gdeng.market.admin.controller.base.BaseController;
import cn.gdeng.market.dto.admin.SysUserDTO;
import cn.gdeng.market.enums.Const;
import cn.gdeng.market.exception.BizException;
import cn.gdeng.market.service.admin.LoginService;
import cn.gdeng.market.service.admin.SysDictionaryService;
import cn.gdeng.market.util.MD5;

/**
 * 用户登录
 **/
@Controller
@RequestMapping("")
public class LoginController extends BaseController {
	@Autowired
	private LoginService loginService;
	@Autowired
	private SysDictionaryService sysDictionaryService;

	@RequestMapping("")
	public ModelAndView login() throws BizException {
		ModelAndView mv = new ModelAndView();
		SysUserDTO user = getSysUser();
		mv.addObject("user", user);
		mv.setViewName("index");
		return mv;
	}

	/**
	 * 用户登录提交
	 * 
	 * @param user
	 * @return
	 * @throws BizException
	 * @author lidong dli@gdeng.cn
	 * @time 2016年10月11日 上午11:09:05
	 */
	@RequestMapping(value = "login")
	@ResponseBody
	public AjaxResult<Map<String, Object>> login(SysUserDTO user) throws BizException {
		AjaxResult<Map<String, Object>> res = new AjaxResult<>();
		Map<String, Object> map = new HashMap<>();
		res.setIsSuccess(false);
		if (StringUtils.isEmpty(user.getCode())) {
			res.setMsg("请输入帐号");
		} else if (StringUtils.isEmpty(user.getPwd())) {
			res.setMsg("请输入密码");
		} else {
			map.put("user", user);
			Map<String, Object> loginMsg = loginService.login4Platform(map);
			if (loginMsg.get("msg") == null) {
				user = (SysUserDTO) loginMsg.get("user");
				String flag = loginService.setUser(getSession().getId(), user);
				if (flag.equals(Const.CODIS_OK)) {
					res.setIsSuccess(true);
					map.put("user", user);
				}
			} else {
				res.setMsg(loginMsg.get("msg").toString());
			}
		}
		res.setResult(map);
		return res;
	}

	/**
	 * 登陆成功，跳转主页
	 * 
	 * @param mv
	 * @return 主页
	 * @throws BizException
	 * @author lidong dli@gdeng.cn
	 * @time 2016年10月9日 上午11:12:56
	 */
	@RequestMapping("index")
	public ModelAndView login(ModelAndView mv) throws BizException {
		SysUserDTO user = getSysUser();
		Map<String, Object> map = new HashMap<>();
		map.put("type", Const.DICTIONARY_ENCODE);
		map.put("code", Const.ENCRYPTION_TYPE_MD5);
		map.put("status", Const.USER_STATUS_1);
		String key = sysDictionaryService.getList(map).get(0).getValue();// 字典表用户登录md5加密串
		if (MD5.sign(Const.RESET_PASSWROD_VALUE, key, Const.CHARSET_UTF8).equals(user.getPwd())) {
			mv.addObject("modifyPwd", true);// 是否强制修改密码
		}
		mv.addObject("user", user);
		mv.setViewName("index");
		return mv;
	}

	/**
	 * 用户登出，清除相关信息
	 * 
	 * @param mv
	 * @return 首页
	 * @throws BizException
	 * @author lidong dli@gdeng.cn
	 * @time 2016年10月11日 下午8:03:35
	 */
	@RequestMapping(value = "logout")
	public ModelAndView logout(ModelAndView mv) throws BizException {
		loginService.removeUser(getSession().getId(), null);// 清除codis用户信息
		getRequest().getSession().invalidate();// 清除spring-session信息
		mv.setViewName("redirect:/");
		return mv;
	}
}
