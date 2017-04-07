package cn.gdeng.market.lease.controller.admin;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.alibaba.fastjson.JSON;

import cn.gdeng.market.dto.admin.SysOrgDTO;
import cn.gdeng.market.dto.admin.SysUserDTO;
import cn.gdeng.market.enums.Const;
import cn.gdeng.market.exception.BizException;
import cn.gdeng.market.lease.bean.AjaxResult;
import cn.gdeng.market.lease.controller.base.BaseController;
import cn.gdeng.market.service.admin.LoginService;
import cn.gdeng.market.service.admin.SysDictionaryService;
import cn.gdeng.market.util.MD5;

@Controller
@RequestMapping("")
public class IndexController extends BaseController {
	@Autowired
	private LoginService loginService;

	@Autowired
	private SysDictionaryService sysDictionaryService;

	@RequestMapping("404")
	public ModelAndView login(ModelAndView mv) throws BizException {
		mv.setViewName("page/404");
		return mv;
	}

	@RequestMapping("home")
	public ModelAndView home(ModelAndView mv) throws BizException {
		mv.setViewName("home");
		return mv;
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
	public ModelAndView index(ModelAndView mv) throws BizException {
		SysUserDTO user = getSysUser();

		// 如果用户为空，跳转到登陆页
		if (null == user) {
			mv.setViewName("login");
			return mv;
		}
		user.setCurrentCompany(getCurrentCompany());
		user.setCurrentMarket(getCurrentMarket());
		if (user.getCurrentCompany() != null) {
			user.setCurrentMarketList(loginUserUtil.getCurrentMarketList(getRequest(), user.getCurrentCompany().getId()));
		}
		Map<String, Object> map = new HashMap<>();
		map.put("type", Const.DICTIONARY_ENCODE);
		map.put("code", Const.ENCRYPTION_TYPE_MD5);
		map.put("status", Const.USER_STATUS_1);
		String key = sysDictionaryService.getList(map).get(0).getValue();// 字典表用户登录md5加密串
		if (MD5.sign(Const.RESET_PASSWROD_VALUE, key, Const.CHARSET_UTF8).equals(user.getPwd())) {
			mv.addObject("modifyPwd", true);// 是否强制修改密码
		}
		mv.addObject("user", user);
		// 去除密码，缓存到index页面
		SysUserDTO dto = new SysUserDTO();
		BeanUtils.copyProperties(user, dto);
		dto.setPwd(null);
		String str = JSON.toJSONString(dto);
		mv.addObject("_user", str);
		mv.setViewName("index");
		return mv;
	}

	/**
	 * 页面头部选择公司,更新存放选择的公司
	 * 
	 * @param org
	 * @return
	 * @throws BizException
	 * @author lidong dli@gdeng.cn
	 * @time 2016年10月18日 下午6:25:17
	 */
	@RequestMapping(value = "refresh/company", method = RequestMethod.POST)
	@ResponseBody
	public AjaxResult<Map<String, Object>> refreshCompany(SysOrgDTO org) throws BizException {
		AjaxResult<Map<String, Object>> res = new AjaxResult<>();
		Map<String, Object> map = new HashMap<>();
		res.setIsSuccess(false);
		SysUserDTO user = getSysUser();
		if (user != null) {
			List<SysOrgDTO> pCompany = user.getpCompany();
			// 更新选择公司的信息
			if (pCompany != null && pCompany.size() > 0) {
				for (SysOrgDTO sysOrgDTO : pCompany) {
					if (sysOrgDTO.getId().intValue() == org.getId().intValue()) {
						user.setCurrentCompany(sysOrgDTO);
					}
				}
			}
		}
		Integer pId = org.getId();
		List<SysOrgDTO> userPMarket = getUserPMarket();
		if (userPMarket != null && userPMarket.size() > 0) {
			List<SysOrgDTO> currentMarketList = new ArrayList<>();
			// 更新选择公司的联动下级市场集合
			for (SysOrgDTO sysOrgDTO : userPMarket) {
				if (sysOrgDTO.getParentId() != null) {
					if (sysOrgDTO.getParentId().intValue() == pId.intValue()) {
						currentMarketList.add(sysOrgDTO);
					}
				}
			}
			user.setCurrentMarketList(currentMarketList);
			// 更新所选择市场
			if (currentMarketList.size() > 0) {
				user.setCurrentMarket(currentMarketList.get(0));
			} else {
				user.setCurrentMarket(null);
			}
		}
		String flag = loginService.setUser(getSession().getId(), user);
		if (flag.equals(Const.CODIS_OK)) {
			res.setIsSuccess(true);
			res.setMsg("选择公司成功");
			map.put("user", user);
		}
		res.setResult(map);
		return res;
	}

	/**
	 * 页面头部选择市场,更新存放选择的市场
	 * 
	 * @param org
	 * @return
	 * @throws BizException
	 * @author lidong dli@gdeng.cn
	 * @time 2016年10月18日 下午6:25:17
	 */
	@RequestMapping(value = "refresh/market", method = RequestMethod.POST)
	@ResponseBody
	public AjaxResult<Map<String, Object>> refreshMarket(SysOrgDTO org) throws BizException {
		AjaxResult<Map<String, Object>> res = new AjaxResult<>();
		Map<String, Object> map = new HashMap<>();
		res.setIsSuccess(false);
		SysUserDTO user = getSysUser();
		List<SysOrgDTO> userPMarket = getUserPMarket();
		if (userPMarket != null && userPMarket.size() > 0) {
			for (SysOrgDTO sysOrgDTO : userPMarket) {
				if (sysOrgDTO.getId().intValue() == org.getId().intValue()) {
					user.setCurrentMarket(sysOrgDTO);
				}
			}
		}
		String flag = loginService.setUser(getSession().getId(), user);
		if (flag.equals(Const.CODIS_OK)) {
			res.setIsSuccess(true);
			res.setMsg("选择市场成功");
			map.put("user", user);
		}
		res.setResult(map);
		return res;
	}

	/**
	 * 选择公司数据权限，获取市场列表
	 * 
	 * @param org
	 * @return
	 * @throws BizException
	 * @author lidong dli@gdeng.cn
	 * @time 2016年10月18日 下午6:39:51
	 */
	@RequestMapping(value = "select/company")
	@ResponseBody
	public AjaxResult<List<SysOrgDTO>> selectCompany(SysOrgDTO org) throws BizException {
		AjaxResult<List<SysOrgDTO>> res = new AjaxResult<>();
		List<SysOrgDTO> currentMarketList = new ArrayList<>();
		int pId = org.getId().intValue();
		List<SysOrgDTO> userPMarket = getUserPMarket();
		if (userPMarket != null && userPMarket.size() > 0) {
			for (SysOrgDTO sysOrgDTO : userPMarket) {
				if (sysOrgDTO.getParentId() != null) {
					if (sysOrgDTO.getParentId().intValue() == pId) {
						currentMarketList.add(sysOrgDTO);
					}
				}
			}
		}
		res.setResult(currentMarketList);
		return res;
	}

}
