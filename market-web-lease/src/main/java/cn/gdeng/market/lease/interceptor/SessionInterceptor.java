package cn.gdeng.market.lease.interceptor;

import java.io.IOException;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import cn.gdeng.market.enums.MsgCons;
import cn.gdeng.market.exception.BizException;
import cn.gdeng.market.lease.bean.AjaxResult;
import cn.gdeng.market.lease.exception.handler.CommonExceptionHandler;
import cn.gdeng.market.lease.util.PathUtil;
import com.alibaba.dubbo.rpc.RpcException;
import com.alibaba.fastjson.JSON;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import cn.gdeng.market.dto.admin.SysMenuDTO;
import cn.gdeng.market.lease.util.LoginUserUtil;
import cn.gdeng.market.service.admin.SysMenuService;

/**
 * 请求连接拦截器过滤访问权限
 *
 * @author lidong dli@gdeng.cn
 * @time 2016年10月20日 上午11:27:47
 */
public class SessionInterceptor extends HandlerInterceptorAdapter {
    /**
     * 定义记录日志信息
     */
    private Logger logger = LoggerFactory.getLogger(SessionInterceptor.class);
    @Autowired
    private SysMenuService sysMenuService;
    @Autowired
    private LoginUserUtil loginUserUtil;

    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String url = request.getRequestURI();
        if (loginUserUtil.getSysUser(request) == null) {
            if (isAjax(request)) {
                //如果是ajax请求
                doProcessAjaxException(request, response);
            } else {
                response.sendRedirect(PathUtil.getBasePath(request));
            }
            return false;
        } else {
            if (checkUrl(url, request)) {
                // 用户拥有该链接访问权限
                return super.preHandle(request, response, handler);
            } else if (!checkUrl(url)) {
                // 如果请求的路径不存在于系统菜单中，则不进行拦截
                return super.preHandle(request, response, handler);
            } else {
                if (isAjax(request)) {
                    //如果是ajax请求
                    doProcessAjaxException(request, response);
                } else {
                    response.sendRedirect(PathUtil.getBasePath(request));
                }
                return false;
            }
        }
    }

    /**
     * 检测用户是否拥有该链接访问权限
     *
     * @param url
     * @param request
     * @return
     * @throws Exception
     * @author lidong dli@gdeng.cn
     * @time 2016年10月20日 下午5:53:54
     */
    private boolean checkUrl(String url, HttpServletRequest request) throws Exception {
        List<SysMenuDTO> allMenus = loginUserUtil.getSysUser(request).getMenus();
        return checkUrl(allMenus, url);
    }

    /**
     * @param url
     * @return 不存在为false，存在系统菜单中，为true
     * @throws Exception
     * @Description checkUrl
     * @CreationDate 2015年11月26日 下午3:29:01
     * @Author lidong(dli@gdeng.cn)
     */
    private boolean checkUrl(String url) throws Exception {
        List<SysMenuDTO> allMenus = sysMenuService.getAllMenus();
        return checkUrl(allMenus, url);
    }

    /**
     * 检测allMenus是否包含url
     *
     * @param allMenus
     * @param url
     * @return
     * @throws Exception
     */
    private boolean checkUrl(List<SysMenuDTO> allMenus, String url) throws Exception {
        if (allMenus != null && allMenus.size() > 0 && StringUtils.isNotEmpty(url)) {
            for (SysMenuDTO sysMenu : allMenus) {
                String menuUrl = sysMenu.getUrl();
                if (StringUtils.isNotEmpty(menuUrl)) {
                    if (url.toLowerCase().indexOf(menuUrl.toLowerCase()) > 0) {
                        return true;
                    }
                }
            }
        }
        return false;
    }


    /**
     * 是否ajax请求
     *
     * @param request
     * @return
     */
    private boolean isAjax(HttpServletRequest request) {
        return (request.getHeader("X-Requested-With") != null &&
                "XMLHttpRequest".equals(request.getHeader("X-Requested-With")));
    }

    private void doProcessAjaxException(HttpServletRequest request, HttpServletResponse response) {
        try {
            String result = "<script>window.location.href='" + PathUtil.getBasePath(request) + "'</script>";
            response.getWriter().write(result);
        } catch (IOException e) {
            logger.error("request writer failure", e);
        }
    }
}
