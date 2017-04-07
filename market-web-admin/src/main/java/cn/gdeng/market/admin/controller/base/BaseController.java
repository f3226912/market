package cn.gdeng.market.admin.controller.base;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.beanutils.PropertyUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import cn.gdeng.market.admin.util.LoginUserUtil;
import cn.gdeng.market.dto.admin.SysUserDTO;
import cn.gdeng.market.dto.bean.GuDengPage;
import cn.gdeng.market.exception.BizException;


/**控制器基类
 * 
 * @author wjguo
 *
 * datetime:2016年9月30日 下午6:35:59
 */
public abstract class BaseController {
	/**
	 * 定义记录日志信息
	 */
	protected Logger logger = LoggerFactory.getLogger(getClass());	
	
	@Autowired
	protected LoginUserUtil loginUserUtil;
	
	
	/**获取request请求对象
	 * @return
	 */
	public HttpServletRequest getRequest() {
		HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
		return request;
	}
	
    /**获取session
     * @return
     */
    public HttpSession getSession() {
        return getRequest().getSession();
    }
    
    /** 存入属性到request中。
     * @param key
     * @param result
     */
    public void setAttrInRequest(String key, Object result) {
    	getRequest().setAttribute(key, result);
    }
    
	/**
	 * 获取分页对象
	 * @param javaBean javaBean对象，转换为Map存入分页参数中。
	 * @return
	 */
	@SuppressWarnings("unchecked")
	protected <T> GuDengPage<T> getPageInfoByRequest(Object javaBean)  throws Exception{
		HttpServletRequest request = getRequest();
		// 当前第几页
		String page = (String)request.getParameter("pageNum");
		// 每页显示的记录数
		String rows = (String)request.getParameter("pageSize");
		// 当前页
		int currentPage = Integer.parseInt((StringUtils.isBlank(page) || "0".equals(page)) ? "1" : page);
		// 每页显示条数
		int pageSize = Integer.parseInt((StringUtils.isBlank(rows) || "0".equals(rows)) ? "10" : rows);

		GuDengPage<T> guDengPage = new GuDengPage<T>(currentPage, pageSize);
		Map<String, Object>  paraMap = null;
		if (javaBean != null) {
			paraMap = BeanUtils.describe(javaBean);
		} else {
			paraMap = new  HashMap<String, Object> ();
		}
		paraMap.put("startRow", guDengPage.getOffset());
		paraMap.put("endRow", guDengPage.getPageSize());
		guDengPage.setParaMap(paraMap);
		return guDengPage;
	
	}
	
	/**
	 * 获取分页对象
	 * @return
	 */
	protected <T> GuDengPage<T> getPageInfoByRequest()  throws Exception{
		return getPageInfoByRequest(null);
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

	protected <T> void fillCreateAndUpdateUser(T t) throws BizException{
		SysUserDTO user = getSysUser();
		try {
			PropertyUtils.setProperty(t, "createUserId", user.getUserId());
			PropertyUtils.setProperty(t, "updateUserId", user.getUserId());
			PropertyUtils.setProperty(t, "createTime", new Date());
			PropertyUtils.setProperty(t, "updateTime", new Date());
		} catch (Exception e) {
			logger.error(t.getClass().getName()+",设置用户属性出错");
		}
	}
	
	protected <T> void fillUpdateUser(T t) throws BizException{
		SysUserDTO user = getSysUser();
		try {
			PropertyUtils.setProperty(t, "updateUserId", user.getUserId());
			PropertyUtils.setProperty(t, "updateTime", new Date());
		} catch (Exception e) {
			logger.error(t.getClass().getName()+",设置用户属性出错");
		}
	}
}
