package cn.gdeng.market.admin.controller.admin;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.alibaba.fastjson.JSON;

import cn.gdeng.market.admin.bean.AjaxResult;
import cn.gdeng.market.admin.controller.base.BaseController;
import cn.gdeng.market.dto.admin.SysOrgDTO;
import cn.gdeng.market.dto.admin.SysUserDTO;
import cn.gdeng.market.dto.bean.GuDengPage;
import cn.gdeng.market.entity.admin.SysOrgEntity;
import cn.gdeng.market.entity.admin.SysUserEntity;
import cn.gdeng.market.enums.Const;
import cn.gdeng.market.enums.SysUserEnum;
import cn.gdeng.market.exception.BizException;
import cn.gdeng.market.service.admin.LoginService;
import cn.gdeng.market.service.admin.SysDictionaryService;
import cn.gdeng.market.service.admin.SysOrgService;
import cn.gdeng.market.service.admin.SysUserService;
import cn.gdeng.market.util.MD5;

/**
 * 集团平台用户管理控制器
 * 
 * @author houxp
 **/
@Controller
@RequestMapping("sysPlatUser")
public class SysPlatUserController extends BaseController {

	private Logger logger = LoggerFactory.getLogger(SysOrgController.class);

	@Autowired
	private SysUserService sysUserService;

	@Resource
	private SysOrgService sysOrgService;

	@Resource
	private SysDictionaryService sysDictionaryService;
	@Autowired
	private LoginService loginService;

	/**
	 * 调转到平台用户管理界面
	 * 
	 * @param mv
	 * @return
	 * @throws BizException
	 */
	@RequestMapping("index")
	public ModelAndView index(ModelAndView mv) throws BizException {
		mv.setViewName("sys/user/sysPlatUser");
		return mv;
	}

	/**
	 * 我的账号
	 * 
	 * @param mv
	 * @return
	 * @throws BizException
	 */
	@RequestMapping(value = "profileBefore")
	public ModelAndView profile(ModelAndView mv, Integer tab) throws BizException {
		SysUserDTO user = getSysUser();
		SysOrgDTO userCompany = loginUserUtil.getUserCompany(getRequest());
		if (userCompany != null) {
			user.setCompanyName(userCompany.getFullName());
		}
		SysOrgDTO userMarket = loginUserUtil.getUserMarket(getRequest());
		if (userMarket != null) {
			user.setMarketName(userMarket.getFullName());
		}
		SysOrgDTO userDept = loginUserUtil.getUserDept(getRequest());
		if (userDept != null) {
			user.setDepartmentName((userDept.getFullName()));
		}
		mv.addObject("user", user);
		mv.addObject("tab", tab);// 进入页面打开的选项卡
		mv.setViewName("sys/user/profile");
		return mv;
	}

	/**
	 * 我的帐号修改资料
	 * 
	 * @param user
	 * @return
	 * @throws BizException
	 * @author lidong dli@gdeng.cn
	 * @time 2016年10月20日 下午8:49:28
	 */
	@ResponseBody
	@RequestMapping(value = "profile", method = RequestMethod.POST)
	public AjaxResult<Integer> profile(SysUserEntity entity) throws BizException {
		AjaxResult<Integer> res = new AjaxResult<Integer>();
		res.setIsSuccess(false);
		if (StringUtils.isEmpty(entity.getName())) {
			res.setMsg("请填写姓名");
			return res;
		} else if (StringUtils.isEmpty(entity.getMobile())) {
			res.setMsg("请填写手机号");
			return res;
		}
		res.setIsSuccess(true);
		entity.setUpdateTime(new Date());
		entity.setId(getUserId());
		entity.setUpdateUserId(getUserIdStr());
		int total = sysUserService.dynamicMerge(entity);
		res.setResult(total);
		// 更新登录用户缓存
		SysUserDTO user = getSysUser();
		user.setName(entity.getName());// 更新姓名
		user.setMobile(entity.getMobile());// 更新手机号
		user.setDeptNo(entity.getDeptNo());// 更新缓存工号
		user.setLandline(entity.getLandline());// 座机
		user.setMail(entity.getMail());// 邮箱
		loginService.setUser(getSession().getId(), user);
		return res;
	}

	/**
	 * 设置（修改密码）
	 * 
	 * @param entity
	 * @return
	 * @throws BizException
	 * @author lidong dli@gdeng.cn
	 * @time 2016年10月20日 下午8:53:42
	 */
	@ResponseBody
	@RequestMapping(value = "setting", method = RequestMethod.POST)
	public AjaxResult<Integer> setting(HttpServletRequest request) throws BizException {
		AjaxResult<Integer> res = new AjaxResult<Integer>();
		res.setIsSuccess(false);
		String cPwd = request.getParameter("cPwd");// 当前密码
		String nPwd = request.getParameter("nPwd");// 新密码
		String confPwd = request.getParameter("confPwd");// 确认新密码
		SysUserDTO user = getSysUser();
		if (StringUtils.isEmpty(cPwd)) {
			res.setMsg("请输入当前登录密码");
			return res;
		} else if (StringUtils.isEmpty(nPwd)) {
			res.setMsg("请设置新的登录密码");
			return res;
		} else if (StringUtils.isEmpty(confPwd)) {
			res.setMsg("请确认新的登录密码");
			return res;
		}
		Map<String, Object> map = new HashMap<>();
		map.put("type", "encode");
		map.put("code", "md5");
		map.put("status", "1");
		String key = sysDictionaryService.getList(map).get(0).getValue();// 字典表用户登录md5加密串
		if (!MD5.sign(cPwd, key, Const.CHARSET_UTF8).equals(user.getPwd())) {
			res.setMsg("当前登录密码错误");// 用户密码错误
			return res;
		} else if (MD5.sign(nPwd, key, Const.CHARSET_UTF8).equals(user.getPwd())) {
			res.setMsg("新密码不能与原密码相同");// 新密码不能与原密码相同
			return res;
		} else if (!nPwd.equals(confPwd)) {
			res.setMsg("两次输入的密码不一致");// 两次输入的密码不一致
			return res;
		}
		res.setIsSuccess(true);
		SysUserEntity entity = new SysUserEntity();
		entity.setId(getUserId());
		entity.setPwd(MD5.sign(nPwd, key, Const.CHARSET_UTF8));
		entity.setUpdateTime(new Date());
		entity.setUpdateUserId(getUserIdStr());
		int total = sysUserService.dynamicMerge(entity);
		// 更新登录用户缓存
		user.setPwd(MD5.sign(nPwd, key, Const.CHARSET_UTF8));// 密码
		loginService.setUser(getSession().getId(), user);
		res.setResult(total);
		return res;
	}

	@RequestMapping(value = "modify-pwdBefore")
	public ModelAndView modifypwd(ModelAndView mv) throws BizException {
		mv.setViewName("sys/user/modify-pwd");
		return mv;
	}

	@ResponseBody
	@RequestMapping(value = "modify-pwd", method = RequestMethod.POST)
	public AjaxResult<Integer> modifypwd(HttpServletRequest request) throws BizException {
		AjaxResult<Integer> res = new AjaxResult<Integer>();
		res.setIsSuccess(false);
		String nPwd = request.getParameter("nPwd");// 新密码
		String confPwd = request.getParameter("confPwd");// 确认新密码
		SysUserDTO user = getSysUser();
		if (StringUtils.isEmpty(nPwd)) {
			res.setMsg("请设置新的登录密码");
			return res;
		} else if (StringUtils.isEmpty(confPwd)) {
			res.setMsg("请确认新的登录密码");
			return res;
		}
		Map<String, Object> map = new HashMap<>();
		map.put("type", "encode");
		map.put("code", "md5");
		map.put("status", "1");
		String key = sysDictionaryService.getList(map).get(0).getValue();// 字典表用户登录md5加密串
		if (!nPwd.equals(confPwd)) {
			res.setMsg("两次输入的密码不一致");// 两次输入的密码不一致
			return res;
		} else if (MD5.sign(nPwd, key, Const.CHARSET_UTF8).equals(user.getPwd())) {
			res.setMsg("新密码不能与原密码相同");// 新密码不能与原密码相同
			return res;
		}
		res.setIsSuccess(true);
		SysUserEntity entity = new SysUserEntity();
		entity.setId(getUserId());
		entity.setPwd(MD5.sign(nPwd, key, Const.CHARSET_UTF8));
		entity.setUpdateTime(new Date());
		entity.setUpdateUserId(getUserIdStr());
		int total = sysUserService.dynamicMerge(entity);
		// 更新登录用户缓存
		user.setPwd(MD5.sign(nPwd, key, Const.CHARSET_UTF8));// 密码
		loginService.setUser(getSession().getId(), user);
		res.setResult(total);
		return res;
	}

	/**
	 * 查询平台用户列表-分页
	 * 
	 * @param model
	 * @return
	 */
	@RequestMapping("getPlatUserList4Page")
	@ResponseBody
	public AjaxResult<GuDengPage<SysUserDTO>> list(Model model, SysUserDTO dto) throws Exception {
		logger.info("分页查询平台用户入参:" + JSON.toJSONString(dto));

		AjaxResult<GuDengPage<SysUserDTO>> result = new AjaxResult<GuDengPage<SysUserDTO>>();

		// 获取分页信息
		GuDengPage<SysUserDTO> pageInfo = super.getPageInfoByRequest();
		pageInfo.getParaMap().put("likeCode", dto.getCode());
		pageInfo.getParaMap().put("likeName", dto.getName());
		pageInfo.getParaMap().put("mobile", dto.getMobile());
		pageInfo.getParaMap().put("companyName", dto.getCompanyName());
		pageInfo.getParaMap().put("type", dto.getType());
		pageInfo.getParaMap().put("plat", true);
		// 查询条件
		pageInfo = sysUserService.getDetailUserListPage(pageInfo);
		logger.info("分页查询平台用户结果:" + JSON.toJSONString(pageInfo));
		result.setResult(pageInfo);
		return result;
	}

	/**
	 * 禁用/启用用户账号
	 * 
	 * @param entity
	 * @return
	 * @throws BizException
	 */
	@RequestMapping("disablePlatUser")
	@ResponseBody
	public AjaxResult<Integer> disablePlatUser(SysUserEntity entity) throws BizException {
		logger.info("用户禁用入参:" + JSON.toJSONString(entity));
		AjaxResult<Integer> result = new AjaxResult<Integer>();
		Integer count = sysUserService.dynamicMerge(entity);
		result.setResult(count);
		if (Const.USER_STATUS_3.equals(entity.getStatus())) {
			loginService.removeUser(null, entity.getId());// 如果禁用该用户，则将登录用户常量表中的用户清除
		}
		logger.info("用户禁用成功，用户ID" + entity.getId());
		return result;
	}

	/**
	 * 删除用户账号
	 * 
	 * @param entity
	 * @return
	 * @throws BizException
	 */
	@RequestMapping("deletePlatUser")
	@ResponseBody
	public AjaxResult<Integer> deletePlatUser(SysUserEntity entity) throws BizException {
		entity.setStatus(Const.USER_STATUS_0);
		logger.info("用户删除入参:" + JSON.toJSONString(entity));
		AjaxResult<Integer> result = new AjaxResult<Integer>();
		Integer count = sysUserService.dynamicMerge(entity);
		result.setResult(count);
		logger.info("用户删除成功，用户ID" + entity.getId());
		return result;
	}

	/**
	 * 重置密码
	 * 
	 * @param entity
	 * @return
	 * @throws BizException
	 */
	@RequestMapping("resetUserPwd")
	@ResponseBody
	public AjaxResult<Integer> resetPwd(SysUserEntity entity) throws BizException {
		logger.info("用户重置密码入参:" + JSON.toJSONString(entity));
		AjaxResult<Integer> result = new AjaxResult<Integer>();
		// 获取加密方式
		String key = sysDictionaryService.getDicVal(Const.ENCRYPTION_TYPE_MD5);
		entity.setPwd(MD5.sign(Const.RESET_PASSWROD_VALUE, key, Const.CHARSET_UTF8));
		Integer count = sysUserService.dynamicMerge(entity);
		result.setResult(count);
		logger.info("用户重置成功，用户ID" + entity.getId());
		return result;
	}

	/**
	 * 调转到平台用户编辑界面
	 * 
	 * @param mv
	 * @return
	 * @throws BizException
	 */
	@RequestMapping("jumpToEdit")
	public ModelAndView jumpToEdit(ModelAndView mv) throws BizException {
		mv.setViewName("sys/user/sysPlatUserEdit");
		return mv;
	}

	/**
	 * 调转到平台用户详细界面
	 * 
	 * @param mv
	 * @return
	 * @throws BizException
	 */
	@RequestMapping("jumpToDetail")
	public ModelAndView jumpToDetail(ModelAndView mv) throws BizException {
		mv.setViewName("sys/user/sysPlatUserDetail");
		return mv;
	}

	/**
	 * 获取用户信息根据ID
	 * 
	 * @param entity
	 * @return
	 * @throws BizException
	 */
	@RequestMapping("getUserInfoById")
	@ResponseBody
	public AjaxResult<SysUserDTO> getUserInfoById(SysUserDTO dto) throws BizException {
		logger.info("获取用户信息入参:" + JSON.toJSONString(dto));
		AjaxResult<SysUserDTO> result = new AjaxResult<>();
		SysUserEntity resDto = sysUserService.findById(Integer.valueOf(dto.getUserId()));

		dto = new SysUserDTO();
		BeanUtils.copyProperties(resDto, dto);
		if (SysUserEnum.TYPE.MANAGER.equals(dto.getType())) {
			SysOrgEntity entity = new SysOrgEntity();
			entity.setId(resDto.getGroupId());
			entity = sysOrgService.querySysOrg(entity);
			dto.setGroupName(entity.getFullName());
		}
		result.setResult(dto);
		logger.info("获取用户信息成功，用户ID" + JSON.toJSONString(dto));
		return result;
	}

	/**
	 * 调转到平台用户新增界面
	 * 
	 * @param mv
	 * @return
	 * @throws BizException
	 */
	@RequestMapping("jumpToAdd")
	public ModelAndView jumpToAdd(ModelAndView mv) throws BizException {
		mv.setViewName("sys/user/sysPlatUserAdd");
		return mv;
	}

	/**
	 * 新增用户
	 * 
	 * @param entity
	 * @return
	 */
	@RequestMapping("addUser")
	@ResponseBody
	public AjaxResult<Integer> addUser(SysUserDTO dto) throws Exception {
		logger.info("添加用户基本信息入参:" + JSON.toJSONString(dto));
		AjaxResult<Integer> res = new AjaxResult<>();
		// 获取当前用户信息
		fillCreateAndUpdateUser(dto);
		int userId = sysUserService.addSysUser(dto);
		res.setResult(userId);
		logger.info("添加用户成功，生成ID=" + userId);
		return res;
	}

	/**
	 * 编辑用户信息
	 * 
	 * @param entity
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("editPlatUser")
	@ResponseBody
	public AjaxResult<Integer> editPlatUser(SysUserEntity entity) throws Exception {

		logger.info("用户编辑入参:" + JSON.toJSONString(entity));
		AjaxResult<Integer> result = new AjaxResult<Integer>();
		fillUpdateUser(entity);
		sysUserService.dynamicMerge(entity);
		return result;
	}

}
