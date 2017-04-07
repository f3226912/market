package cn.gdeng.market.admin.interceptor;

import cn.gdeng.market.admin.util.LoginUserUtil;
import cn.gdeng.market.admin.util.PathUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class SessionInterceptor extends HandlerInterceptorAdapter {
    /**
     * 定义记录日志信息
     */
    private Logger logger = LoggerFactory.getLogger(SessionInterceptor.class);
    @Autowired
    protected LoginUserUtil loginUserUtil;

    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        if (loginUserUtil.getSysUser(request) == null) {
            if (isAjax(request)) {
                //如果是ajax请求
                doProcessAjaxException(request,response);
            } else {
                response.sendRedirect(PathUtil.getBasePath(request));
            }
            return false;
        } else {
            return super.preHandle(request, response, handler);
        }
    }




    /**是否ajax请求
     * @param request
     * @return
     */
    private boolean isAjax(HttpServletRequest request) {
        return  (request.getHeader("X-Requested-With") != null  &&
                "XMLHttpRequest".equals(request.getHeader("X-Requested-With")));
    }

    private void doProcessAjaxException(HttpServletRequest request,HttpServletResponse response) {
        try {
            String result ="<script>window.location.href='"+PathUtil.getBasePath(request)+"'</script>";
            response.getWriter().write(result);
        } catch (IOException e) {
            logger.error("request writer failure", e);
        }
    }
}
