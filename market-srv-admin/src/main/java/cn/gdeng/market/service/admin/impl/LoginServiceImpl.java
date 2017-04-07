package cn.gdeng.market.service.admin.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.gdeng.market.dao.base.BaseDao;
import cn.gdeng.market.dto.admin.SysMenuButtonDTO;
import cn.gdeng.market.dto.admin.SysMenuDTO;
import cn.gdeng.market.dto.admin.SysOrgDTO;
import cn.gdeng.market.dto.admin.SysPostDTO;
import cn.gdeng.market.dto.admin.SysUserDTO;
import cn.gdeng.market.dto.lease.settings.MarketResourceTypeDTO;
import cn.gdeng.market.enums.Const;
import cn.gdeng.market.enums.SysOrgEnum;
import cn.gdeng.market.enums.SysUserEnum;
import cn.gdeng.market.exception.BizException;
import cn.gdeng.market.redis.support.JodisTemplate;
import cn.gdeng.market.service.admin.LoginService;
import cn.gdeng.market.service.admin.SysDictionaryService;
import cn.gdeng.market.util.MD5;
import cn.gdeng.market.util.PropertyUtil;
import cn.gdeng.market.util.SerializeUtil;

/**
 * 用户登录相关服务
 * 
 * @author lidong dli@gdeng.cn
 * @time 2016年10月11日 上午11:16:28
 */
@Service(value = "loginService")
public class LoginServiceImpl implements LoginService {

	/**
	 * 登录用户常量表，存放已经登录的用户sessionid
	 */
	private final static String keyuserMap = "LoginServiceImplkeyuserMap";
	// private final static String PWD_ERROR_TIMES = "pwdErrorTimes";// 用户输错密码错误N次锁定
	private final static String NO_USER = "login.user.noExist";// 用户不存在
	private final static String NO_ROLE = "login.user.norole";// 用户没有登录权限
	private final static String ERROR_PASSWORD = "login.errorPassword";// 用户密码错误
	// private final static String USER_DELETE = "login.user.del";// 帐号已被注销，请联系管理员
	// private final static String USER_LOCK = "login.user.hasLocked";// 您的帐号已被锁定，请联系管理员
	private final static String USER_FORBID = "login.user.disabled";// 帐号已被禁用，请联系管理员
	// private final static String STATUS_DELETE = "0";// 删除状态
	// private final static String STATUS_NORMAL = "1";// 正常
	// private final static String STATUS_LOCK = "2";// 锁定
	// private final static String STATUS_FORBID = "3";// 禁用

	@Autowired
	private BaseDao<?> baseDao;
	@Autowired
	private PropertyUtil propertyUtil;
	@Autowired
	private SysDictionaryService sysDictionaryService;
	@Autowired
	private JodisTemplate jodisTemplate;

	/**
	 * 用户登录接口
	 * 
	 * @return
	 * @throws BizException
	 * @author lidong dli@gdeng.cn
	 * @time 2016年10月11日 上午11:14:39
	 */
	@Override
	public Map<String, Object> login(Map<String, Object> map) throws BizException {
		SysUserDTO user = (SysUserDTO) map.get("user");
		map.put("code", user.getCode());
		int total = baseDao.queryForObject("SysUser.getTotalByCode", map,Integer.class);;// 帐号查找用户
		if (total < 1) {
			map.put("msg", propertyUtil.getValue(NO_USER));// 用户不存在
		} else {
			SysUserDTO tUser = baseDao.queryForObject("SysUser.getUserByCode", map, SysUserDTO.class);
			map.put("type", "encode");
			map.put("code", "md5");
			map.put("status", "1");
			String key = sysDictionaryService.getList(map).get(0).getValue();// 字典表用户登录md5加密串
			if (!MD5.sign(user.getPwd(), key, Const.CHARSET_UTF8).equals(tUser.getPwd())) {
				map.put("msg", propertyUtil.getValue(ERROR_PASSWORD));// 用户密码错误
			} else {
				// 密码验证正确
				if (!tUser.getStatus().equals(SysOrgEnum.STATUS.NORMAL)) {
					map.put("msg", propertyUtil.getValue(USER_FORBID));// 用户被禁用
				} else {
					// 公司用户才可以登录此系统
					if (tUser.getType().equals("2") || tUser.getType().equals("3")) {
						map.put("user", bindUserData(tUser));
					} else {
						map.put("msg", propertyUtil.getValue(NO_ROLE));// 平台用户没有登录权限
					}
				}
			}
		}
		return map;
	}

	/**
	 * 查询并绑定用户相关的数据权限等
	 * 
	 * @param user
	 * @return
	 * @throws BizException
	 * @author lidong dli@gdeng.cn
	 * @time 2016年10月11日 下午8:54:43
	 */
	private SysUserDTO bindUserData(SysUserDTO user) throws BizException {
		Map<String, Object> map = new HashMap<>();
		map.put("status", SysOrgEnum.STATUS.NORMAL);
		map.put("userId", user.getId());

		Map<String, Object> map2 = new HashMap<>();
		map2.put("id", user.getGroupId());
		List<SysOrgDTO> group = baseDao.queryForList("SysOrg.queryByCondition", map2, SysOrgDTO.class);// 获取用户的集团
		List<SysOrgDTO> orgs = baseDao.queryForList("SysUserOrg.getOrgsByUserId", map, SysOrgDTO.class);// 获取用户的组织
		group.addAll(orgs);
		user.setOrgs(group);// 获取用户的组织
		user.setPosts(baseDao.queryForList("SysUserPost.getPostsByUserId", map, SysPostDTO.class));// 用户岗位
		if (user.getType().equals(SysUserEnum.TYPE.MANAGER)) {
			user.setMenus(baseDao.queryForList("SysMenu.queryByCondition", map, SysMenuDTO.class));// 管理员所有的菜单
			user.setButtons(baseDao.queryForList("SysMenuButton.queryByCondition", map, SysMenuButtonDTO.class));// 管理员所有的按钮
			Map<String, Object> param = new HashMap<>();
			param.put("type", SysOrgEnum.TYPE.COMPANY);
			param.put("topId", user.getGroupId());
			user.setpCompany(baseDao.queryForList("SysOrg.queryByCondition", param, SysOrgDTO.class));// 管理员所有的公司, 数据权限-公司
			param.put("type", SysOrgEnum.TYPE.MARKET);
			user.setpMarket(baseDao.queryForList("SysOrg.queryByCondition", param, SysOrgDTO.class));// 管理员所有的市场, 数据权限-市场
		} else {
			user.setMenus(baseDao.queryForList("SysPostFunction.getMenusByUserId", map, SysMenuDTO.class));// 用户拥有的菜单
			user.setButtons(baseDao.queryForList("SysPostFunction.getButtonsByUserId", map, SysMenuButtonDTO.class));// 用户所拥有的菜单按钮
			map.put("type", "1");
			user.setpCompany(baseDao.queryForList("SysPostData.getPOrgsByUserId", map, SysOrgDTO.class));// 数据权限-公司
			map.put("type", "2");
			user.setpMarket(baseDao.queryForList("SysPostData.getPOrgsByUserId", map, SysOrgDTO.class));// 数据权限-市场
			user.setpRes(baseDao.queryForList("SysPostData.getPResByUserId", map, MarketResourceTypeDTO.class));// 数据权限-资源类型
		}
		return user;
	}

	/**
	 * 将登录用户存入redis
	 * 
	 * @param key
	 *            根据spring-session生成的sessionid变换得到的key值
	 * @param user
	 *            登录用户
	 * @return
	 * @throws BizException
	 * @author lidong dli@gdeng.cn
	 * @time 2016年10月11日 下午6:47:02
	 */
	@SuppressWarnings("unchecked")
	@Override
	public String setUser(String key, SysUserDTO user) throws BizException {
		Object object = jodisTemplate.getObject(SerializeUtil.serialize(keyuserMap));// 登录用户常量表K-V,对应 用户id-sessionid
		Map<Integer, String> userMap = null;
		if (object == null) {
			userMap = new HashMap<>();
		} else {
			userMap = (Map<Integer, String>) object;
		}
		userMap.put(user.getId(), key);// 将登陆用户存入登录用户常量表K-V,对应 用户id-sessionid
		jodisTemplate.set(SerializeUtil.serialize(keyuserMap), SerializeUtil.serialize(userMap));// 更新登录用户常量表
		return jodisTemplate.setex(SerializeUtil.serialize(key), 3600 * 24, SerializeUtil.serialize(user));
	}

	/**
	 * 获取redis中登录用户的信息
	 * 
	 * @param key
	 * @return
	 * @throws BizException
	 * @author lidong dli@gdeng.cn
	 * @time 2016年10月11日 下午6:48:43
	 */
	@Override
	public SysUserDTO getUser(String key) throws BizException {
		if (StringUtils.isEmpty(key)) {
			return null;
		}
		Object object = jodisTemplate.getObject(SerializeUtil.serialize(key));
		if (object != null) {
			return (SysUserDTO) object;
		}
		return null;
	}

	/**
	 * 用户登出，清除用户信息
	 * 
	 * @param key
	 * @return
	 * @throws BizException
	 * @author lidong dli@gdeng.cn
	 * @time 2016年10月11日 下午7:01:51
	 */
	@SuppressWarnings("unchecked")
	@Override
	public Long removeUser(String key, Integer userId) throws BizException {
		if (StringUtils.isNotEmpty(key)) {
			Object object = jodisTemplate.getObject(SerializeUtil.serialize(keyuserMap));// 登录用户常量表
			if (object != null) {
				Map<Integer, String> userMap = (Map<Integer, String>) object;
				userMap.remove(userId);// 从登录用户常量表中移除该用户
				jodisTemplate.set(SerializeUtil.serialize(keyuserMap), SerializeUtil.serialize(userMap));// 更新登录用户常量表
			}
			return jodisTemplate.del(SerializeUtil.serialize(key));
		} else if (userId != null) {
			Object object = jodisTemplate.getObject(SerializeUtil.serialize(keyuserMap));// 登录用户常量表
			if (object != null) {
				Map<Integer, String> userMap = (Map<Integer, String>) object;
				key = userMap.get(userId);// sessionid
				jodisTemplate.del(SerializeUtil.serialize(key));
				userMap.remove(userId);// 从登录用户常量表中移除该用户
				jodisTemplate.set(SerializeUtil.serialize(keyuserMap), SerializeUtil.serialize(userMap));// 更新登录用户常量表
			}
		}
		return 0L;
	}

	@Override
	public Map<String, Object> login4Platform(Map<String, Object> map) throws BizException {
		SysUserDTO user = (SysUserDTO) map.get("user");
		map.put("code", user.getCode());
		int total = baseDao.queryForObject("SysUser.getTotalByCode", map,Integer.class);;// 帐号查找用户
		if (total < 1) {
			map.put("msg", propertyUtil.getValue(NO_USER));// 用户不存在
		} else {
			SysUserDTO tUser = baseDao.queryForObject("SysUser.getUserByCode", map, SysUserDTO.class);
			map.put("type", "encode");
			map.put("code", "md5");
			map.put("status", "1");
			String key = sysDictionaryService.getList(map).get(0).getValue();// 字典表用户登录md5加密串

			if (!StringUtils.equals(MD5.sign(user.getPwd(), key, Const.CHARSET_UTF8), tUser.getPwd())) {
				map.put("msg", propertyUtil.getValue(ERROR_PASSWORD));// 用户密码错误
			} else {
				// 密码验证正确
				if (!tUser.getStatus().equals(SysOrgEnum.STATUS.NORMAL)) {
					map.put("msg", propertyUtil.getValue(USER_FORBID));// 用户被禁用
				} else {
					//
					if (SysUserEnum.TYPE.PLATFORM.equals(tUser.getType())) {
						//
						map.put("user", tUser);
					} else {
						map.put("msg", propertyUtil.getValue(NO_ROLE));//
					}
				}
			}
		}
		return map;
	}

	public static void main(String[] args) {
		String sign = MD5.sign("abc123", "gudeng2016", "utf-8");
		System.out.println(StringUtils.equals("0e1ef2ef96a697f16a39effdd856cd0f", "0e1ef2ef96a697f16a39effdd856cd0f"));

		System.out.println(sign);
	}
}