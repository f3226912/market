package cn.gdeng.market.lease.exception.handler;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.ModelAndView;

import com.alibaba.dubbo.rpc.RpcException;
import com.alibaba.fastjson.JSON;

import cn.gdeng.market.enums.MsgCons;
import cn.gdeng.market.exception.BizException;
import cn.gdeng.market.lease.bean.AjaxResult;

/**Spring MVC 公共异常拦截处理器
 * @author wjguo
 * datetime 2016年9月20日 下午3:10:32  
 *
 */
public class CommonExceptionHandler implements HandlerExceptionResolver{
	
	/**
	 * 定义记录日志信息
	 */
	private Logger logger = LoggerFactory.getLogger(CommonExceptionHandler.class);
	
	/**
	 * 加载页面的标识符。
	 */
	private static final String LOAD_PAGE_FLAG = "commonLoadPageFrame";
	
	@Override
	public ModelAndView resolveException(HttpServletRequest request, HttpServletResponse response, Object handlerMethod,
			Exception ex) {
		//异常处理。
		if (isAjax(request) && !isLoadPage(request)) {
			return processAjaxException(request, response, handlerMethod, ex);
		} else {
			return processGeneralException(request, response, handlerMethod, ex);
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
	
	/**是否加载页面。<br/>
	 * 前端对页面的加载，可能有些是通过ajax进行请求的。
	 * @param request
	 * @return
	 */
	private boolean isLoadPage(HttpServletRequest request) {
		return  request.getParameter("loadPageFlag") != null && 
				LOAD_PAGE_FLAG.equals(request.getParameter("loadPageFlag"));
	}
	
	
	/** 处理ajax请求的异常。
	 * @param request
	 * @param response
	 * @param handlerMethod
	 * @param ex
	 * @return
	 */
	private ModelAndView processAjaxException(HttpServletRequest request, HttpServletResponse response, Object handlerMethod,
			Exception ex) {
		doProcessAjaxException(request, response, handlerMethod, ex);
		//不能直接返回null，直接返回空模型对象。
		return new ModelAndView();
	}
	
	private void doProcessAjaxException(HttpServletRequest request, HttpServletResponse response, Object handlerMethod,
			Exception ex) {
		AjaxResult<?> result = new AjaxResult<>();
		if(ex instanceof BizException){
			BizException biz = (BizException)ex;
			result.withError(biz.getCode(), biz.getMsg());
			logger.debug(JSON.toJSONString(result), ex);
		} else if ( (ex instanceof RpcException) && ((RpcException)ex).isBiz()){
			result.withError(MsgCons.C_30000, ex.getMessage());
			logger.debug(JSON.toJSONString(result), ex);
		}  else {
			result.withError(MsgCons.C_30000, MsgCons.M_30000);
			logger.error(JSON.toJSONString(result), ex);
		}
		
		//转换json字符串
		String jsonStr = JSON.toJSONString(result);
		//设置json头
		response.setContentType("application/json; charset=UTF-8");
		try {
			response.getWriter().write(jsonStr);
		} catch (IOException e) {
			logger.error("request writer failure", e);
		}
	}
	
	
	
	
	/** 处理普通请求的异常。
	 * @param request
	 * @param response
	 * @param handlerMethod
	 * @param ex
	 * @return
	 */
	private ModelAndView processGeneralException(HttpServletRequest request, HttpServletResponse response, Object handlerMethod,
			Exception ex) {
		String errorMsg = null;
		if(ex instanceof BizException){
			BizException biz = (BizException)ex;
			errorMsg = biz.getMsg();
			logger.debug("", ex);
		} else if ( (ex instanceof RpcException) && ((RpcException)ex).isBiz()){
			errorMsg = ex.getMessage();
			logger.debug("", ex);
		}  else {
			errorMsg = MsgCons.M_30000;
			logger.error("", ex);
		}
		
		/* 响应结果码默认为500，必须手动修改响应结果码为200，
		 * 否则会出现如果是ajax请求页面加载的导致不会跳转页面的问题。
		 * 
		 */
		if (isLoadPage(request)) {
			response.setStatus(HttpServletResponse.SC_OK);
		}
		request.setAttribute("errorMsg", errorMsg);
		return new ModelAndView("page/500");
	}
	

}
