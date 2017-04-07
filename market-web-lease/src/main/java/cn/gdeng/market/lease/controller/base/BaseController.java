package cn.gdeng.market.lease.controller.base;

import java.util.Date;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.beanutils.PropertyUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import cn.gdeng.market.dto.admin.SysOrgDTO;
import cn.gdeng.market.dto.admin.SysUserDTO;
import cn.gdeng.market.dto.bean.GuDengPage;
import cn.gdeng.market.enums.MsgCons;
import cn.gdeng.market.exception.BizException;
import cn.gdeng.market.lease.util.LoginUserUtil;

/**
 * 控制器基类
 * 
 * @author wjguo
 *
 *         datetime:2016年9月30日 下午6:35:59
 */
public abstract class BaseController {

	@Autowired
	protected LoginUserUtil loginUserUtil;
	protected HttpServletResponse response;
	/** 导出Excel时每次分页大小 */
	protected final int EXPORT_PAGE_SIZE = 1000;
	/** 导出结果集上限 */
	protected final int EXPORT_MAX_SIZE = 50000;
	/**
	 * 定义记录日志信息
	 */
	protected Logger logger = LoggerFactory.getLogger(getClass());

	/**
	 * 获取request请求对象
	 * 
	 * @return
	 */
	public HttpServletRequest getRequest() {
		HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
		return request;
	}

	@ModelAttribute
	public void setResp(HttpServletResponse response) {
		this.response = response;
	}

	/**
	 * 获取session
	 * 
	 * @return
	 */
	public HttpSession getSession() {
		return getRequest().getSession();
	}

	/**
	 * 获取当前登录用户的信息
	 * 
	 * @return
	 * @throws BizException
	 * @author lidong dli@gdeng.cn
	 * @time 2016年10月11日 下午7:49:44
	 */
	public SysUserDTO getSysUser() throws BizException {
		return loginUserUtil.getSysUser(getRequest());
	}

	/**
	 * 获取用户当前所选择的公司
	 * 
	 * @return
	 * @throws BizException
	 * @author lidong dli@gdeng.cn
	 * @time 2016年10月18日 下午4:32:49
	 */
	public SysOrgDTO getCurrentCompany() throws BizException {
		return loginUserUtil.getCurrentCompany(getRequest());
	}

	/**
	 * 获取用户当前所选择的市场
	 * 
	 * @return
	 * @throws BizException
	 * @author lidong dli@gdeng.cn
	 * @time 2016年10月18日 下午4:32:49
	 */
	public SysOrgDTO getCurrentMarket() throws BizException {
		return loginUserUtil.getCurrentMarket(getRequest());
	}

	/**
	 * 获取当前登录用户的id
	 * 
	 * @return
	 * @throws BizException
	 * @author lidong dli@gdeng.cn
	 * @time 2016年10月12日 下午2:36:16
	 */
	public Integer getUserId() throws BizException {
		return getSysUser().getId();
	}

	/**
	 * 获取当前登录用户的id（字符串格式）
	 * 
	 * @return
	 * @throws BizException
	 * @author lidong dli@gdeng.cn
	 * @time 2016年10月12日 下午2:38:00
	 */
	public String getUserIdStr() throws BizException {
		return getUserId().toString();
	}

	/**
	 * 获取当前用户所在的集团
	 * 
	 * @return
	 * @throws BizException
	 * @author lidong dli@gdeng.cn
	 * @time 2016年10月11日 下午8:22:17
	 */
	public SysOrgDTO getUserGroup() throws BizException {
		return loginUserUtil.getUserGroup(getRequest());
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
	public List<SysOrgDTO> getUserPCompany() throws BizException {
		return loginUserUtil.getUserPCompany(getRequest());
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
	public List<SysOrgDTO> getUserPMarket() throws BizException {
		return loginUserUtil.getUserPMarket(getRequest());
	}

	/**
	 * 存入属性到request中。
	 * 
	 * @param key
	 * @param result
	 */
	public void setAttrInRequest(String key, Object result) {
		getRequest().setAttribute(key, result);
	}

	/**
	 * 获取分页对象
	 * 
	 * @param javaBean
	 *            javaBean对象，转换为Map存入分页参数中。
	 * @return
	 */
	@SuppressWarnings("unchecked")
	protected <T> GuDengPage<T> getPageInfoByRequest(Object javaBean) throws BizException {
		HttpServletRequest request = getRequest();
		// 当前第几页
		String page = (String) request.getParameter("pageNum");
		// 每页显示的记录数
		String rows = (String) request.getParameter("pageSize");
		// 当前页
		int currentPage = Integer.parseInt((StringUtils.isBlank(page) || "0".equals(page)) ? "1" : page);
		// 每页显示条数
		int pageSize = Integer.parseInt((StringUtils.isBlank(rows) || "0".equals(rows)) ? "10" : rows);

		GuDengPage<T> guDengPage = new GuDengPage<T>(currentPage, pageSize);
		Map<String, Object> paraMap = null;
		try {
			if (javaBean != null) {
				paraMap = BeanUtils.describe(javaBean);
			} else {
				paraMap = new HashMap<String, Object>();
			}
		} catch (Exception e) {
			e.printStackTrace();
			throw (new BizException(MsgCons.C_30000, "bean对象转Map时异常"));
		}

		paraMap.put("startRow", guDengPage.getOffset());
		paraMap.put("endRow", guDengPage.getPageSize());
		guDengPage.setParaMap(paraMap);
		return guDengPage;

	}

	/**
	 * 获取分页对象
	 * 
	 * @return
	 */
	protected <T> GuDengPage<T> getPageInfoByRequest() throws BizException {
		return getPageInfoByRequest(null);
	}

	/**
	 * request取值转换为Map
	 * 
	 * @param request
	 * @return
	 */
	public Map<String, Object> getParametersMap(HttpServletRequest request) {
		Map<String, Object> map = new HashMap<String, Object>();
		Enumeration<?> parameterNames = request.getParameterNames();
		while (parameterNames.hasMoreElements()) {
			String paraName = (String) parameterNames.nextElement();
			map.put(paraName, StringUtils.trimToNull(request.getParameter(paraName)));
		}
		return map;
	}

	/**
	 * 根据总记录计算出 分页条件起始页 记录总页数
	 * 
	 * @param request
	 * @param map
	 */
	protected void setCommParameters(HttpServletRequest request, Map<String, Object> map) {
		// 当前第几页
		String page = request.getParameter("pageNum");
		// 每页显示的记录数
		String rows = request.getParameter("pageSize");
		// 当前页
		int currentPage = Integer.parseInt((StringUtils.isBlank(page) || "0".equals(page)) ? "1" : page);
		// 每页显示条数
		int pageSize = Integer.parseInt((StringUtils.isBlank(rows) || "0".equals(rows)) ? "10" : rows);
		// 每页的开始记录 第一页为1 第二页为number +1
		int startRow = (currentPage - 1) * pageSize;
		map.put("startRow", startRow);
		map.put("endRow", pageSize);
	}

	protected <T> void fillCreateAndUpdateUser(T t) throws BizException {
		SysUserDTO user = getSysUser();
		try {
			PropertyUtils.setProperty(t, "createUserId", user.getUserId());
			PropertyUtils.setProperty(t, "updateUserId", user.getUserId());
			PropertyUtils.setProperty(t, "createTime", new Date());
			PropertyUtils.setProperty(t, "updateTime", new Date());
		} catch (Exception e) {
			logger.error(t.getClass().getName() + ",设置用户属性出错");
		}
	}

	protected <T> void fillUpdateUser(T t) throws BizException {
		SysUserDTO user = getSysUser();
		try {
			PropertyUtils.setProperty(t, "updateUserId", user.getUserId());
			PropertyUtils.setProperty(t, "updateTime", new Date());
		} catch (Exception e) {
			logger.error(t.getClass().getName() + ",设置用户属性出错");
		}
	}

	/**
	 * 获取当前用户的基本信息
	 * 
	 * @return
	 * @throws BizException
	 */
	protected String getUserBaseInfo() throws BizException {
		StringBuilder baseInfo = new StringBuilder();
		SysUserDTO sysUser = getSysUser();
		//目前基本信息只需要显示 公司 + 部门 + 姓名
		SysOrgDTO userCompany = loginUserUtil.getUserCompany(getRequest());
		SysOrgDTO userDept = loginUserUtil.getUserDept(getRequest());
		
		if (userCompany != null && StringUtils.isNotEmpty(userCompany.getFullName())) {
			baseInfo.append(userCompany.getFullName()).append(">>");
		}

		if (userDept != null && StringUtils.isNotEmpty(userDept.getFullName())) {
			baseInfo.append(userDept.getFullName()).append(">>");
		}
		
		if (StringUtils.isNotEmpty(sysUser.getName())) {
			baseInfo.append(sysUser.getName()).append(">>");
		}

		// 删除多余的>>
		int len = baseInfo.length();
		if (len > 2) {
			baseInfo.delete(len - 2, len);
		}

		return baseInfo.toString();
	}
}
