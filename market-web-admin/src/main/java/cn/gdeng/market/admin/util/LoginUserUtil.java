package cn.gdeng.market.admin.util;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.gdeng.market.dto.admin.SysOrgDTO;
import cn.gdeng.market.dto.admin.SysUserDTO;
import cn.gdeng.market.exception.BizException;
import cn.gdeng.market.service.admin.LoginService;

/**
 * 登录用户的共通工具类
 * 
 * @author 胡勇
 * @date 2012-03-20
 * 
 */
@Service
public class LoginUserUtil {
	
	@Autowired
	private LoginService loginService;

	/**
	 * 获取用户登录对象
	 * 
	 * @param request
	 * @return SysUserDTO
	 * @throws BizException
	 * @author lidong dli@gdeng.cn
	 * @time 2016年10月11日 下午8:13:09
	 */
	public SysUserDTO getSysUser(HttpServletRequest request) throws BizException {
		return loginService.getUser(request.getSession().getId());
	}

	/**
	 * 取得登录用户的名称userName
	 * 
	 * @param request
	 * @return 用户的名称
	 * @throws BizException
	 * @throws Exception
	 */
	public String getUserName(HttpServletRequest request) throws BizException {
		SysUserDTO sysUser = getSysUser(request);
		if (sysUser != null) {
			return sysUser.getName();
		}
		return null;
	}

	/**
	 * 取得登录用户的ID;
	 * 
	 * @param request
	 * @return 登录用户的ID
	 * @throws Exception
	 */
	public Integer getUserId(HttpServletRequest request) throws BizException {
		SysUserDTO sysUser = getSysUser(request);
		if (sysUser != null) {
			return sysUser.getId();
		}
		return null;
	}

	/**
	 * 获取用户登录账号
	 * 
	 * @param request
	 * @return
	 * @throws BizException
	 * @author lidong dli@gdeng.cn
	 * @time 2016年10月11日 下午8:14:54
	 */
	public String getUserCode(HttpServletRequest request) throws BizException {
		SysUserDTO sysUser = getSysUser(request);
		if (sysUser != null) {
			return sysUser.getCode();
		}
		return null;
	}

	/**
	 * 获取用户的集团
	 * 
	 * @param request
	 * @return
	 * @throws BizException
	 * @author lidong dli@gdeng.cn
	 * @time 2016年10月11日 下午8:19:48
	 */
	public SysOrgDTO getUserGroup(HttpServletRequest request) throws BizException {
		SysUserDTO sysUser = getSysUser(request);
		if (sysUser != null) {
			List<SysOrgDTO> orgs = sysUser.getOrgs();
			if (orgs != null) {
				for (SysOrgDTO sysOrgDTO : orgs) {
					if (sysOrgDTO.getParentId() == null) {
						return sysOrgDTO;
					}
				}
			}
		}
		return null;
	}

	/**
	 * 根据类型获取组织
	 * 
	 * @param request
	 * @param type
	 * @return
	 * @throws BizException
	 * @author lidong dli@gdeng.cn
	 * @time 2016年10月11日 下午8:26:08
	 */
	private SysOrgDTO getOrg(HttpServletRequest request, String type) throws BizException {
		SysUserDTO sysUser = getSysUser(request);
		if (sysUser != null) {
			List<SysOrgDTO> orgs = sysUser.getOrgs();
			if (orgs != null) {
				for (SysOrgDTO sysOrgDTO : orgs) {
					if (sysOrgDTO.getType().equals(type)) {
						return sysOrgDTO;
					}
				}
			}
		}
		return null;
	}

	/**
	 * 获取用户的公司
	 * 
	 * @param request
	 * @return
	 * @throws BizException
	 * @author lidong dli@gdeng.cn
	 * @time 2016年10月11日 下午8:23:51
	 */
	public SysOrgDTO getUserCompany(HttpServletRequest request) throws BizException {
		return getOrg(request, "1");
	}

	/**
	 * 获取用户的部门
	 * 
	 * @param request
	 * @return
	 * @throws BizException
	 * @author lidong dli@gdeng.cn
	 * @time 2016年10月11日 下午8:26:42
	 */
	public SysOrgDTO getUserDept(HttpServletRequest request) throws BizException {
		return getOrg(request, "2");
	}

	/**
	 * 获取用户的市场
	 * 
	 * @param request
	 * @return
	 * @throws BizException
	 * @author lidong dli@gdeng.cn
	 * @time 2016年10月11日 下午8:27:14
	 */
	public SysOrgDTO getUserMarket(HttpServletRequest request) throws BizException {
		return getOrg(request, "3");
	}

	/**
	 * 获取用户数据权限-公司
	 * 
	 * @param request
	 * @return
	 * @throws BizException
	 * @author lidong dli@gdeng.cn
	 * @time 2016年10月11日 下午8:36:26
	 */
	public List<SysOrgDTO> getUserPCompany(HttpServletRequest request) throws BizException {
		SysUserDTO user = getSysUser(request);
		if (user != null) {
			return user.getpCompany();
		}
		return null;
	}

	/**
	 * 获取用户数据权限-市场
	 * 
	 * @param request
	 * @return
	 * @throws BizException
	 * @author lidong dli@gdeng.cn
	 * @time 2016年10月11日 下午8:46:32
	 */
	public List<SysOrgDTO> getUserPMarket(HttpServletRequest request) throws BizException {
		SysUserDTO user = getSysUser(request);
		if (user != null) {
			return user.getpMarket();
		}
		return null;
	}

	/**
	 * 通过HttpServletRequest返回IP地址
	 * 
	 * @param request
	 *            HttpServletRequest
	 * @return ip String
	 * @throws Exception
	 */
	public static String getIpAddr(HttpServletRequest request) throws Exception {
		String ip = request.getHeader("x-forwarded-for");
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getHeader("Proxy-Client-IP");
		}
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getHeader("WL-Proxy-Client-IP");
		}
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getHeader("HTTP_CLIENT_IP");
		}
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getHeader("HTTP_X_FORWARDED_FOR");
		}
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getRemoteAddr();
		}
		return ip;
	}
}
