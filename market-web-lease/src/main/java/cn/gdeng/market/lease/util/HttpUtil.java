package cn.gdeng.market.lease.util;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.NameValuePair;
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.httpclient.params.HttpMethodParams;

public final class HttpUtil {

	private HttpUtil() {
	}

	public static final String httpClientPost(String url) {
		String result = "";
		HttpClient client = new HttpClient();
		client.getParams().setParameter(HttpMethodParams.HTTP_CONTENT_CHARSET, "UTF-8");
		client.getHttpConnectionManager().getParams().setConnectionTimeout(8000);
		GetMethod getMethod = new GetMethod(url);
		try {
			client.executeMethod(getMethod);
			result = getMethod.getResponseBodyAsString();
		} catch (Exception e) {
		} finally {
			getMethod.releaseConnection();
		}
		return result;
	}

	public static final String httpClientPost(String url, ArrayList<NameValuePair> list) {
		String result = "";
		HttpClient client = new HttpClient();
		client.getParams().setParameter(HttpMethodParams.HTTP_CONTENT_CHARSET, "UTF-8");
		client.getHttpConnectionManager().getParams().setConnectionTimeout(8000);
		PostMethod postMethod = new PostMethod(url);
		try {
			NameValuePair[] params = new NameValuePair[list.size()];
			for (int i = 0; i < list.size(); i++) {
				params[i] = list.get(i);
			}
			postMethod.addParameters(params);
			
			client.executeMethod(postMethod);
			result = postMethod.getResponseBodyAsString();
		} catch (Exception e) {
		} finally {
			postMethod.releaseConnection();
		}
		return result;
	}
	
	public static final String httpClientPost(String url, Map<String,String> map) {
		String result = "";
		HttpClient client = new HttpClient();
		client.getParams().setParameter(HttpMethodParams.HTTP_CONTENT_CHARSET, "UTF-8");
		client.getHttpConnectionManager().getParams().setConnectionTimeout(8000); 
		PostMethod postMethod = new PostMethod(url);
		try {			
			List<NameValuePair> list = new ArrayList <NameValuePair>();  	          
	        Set<String> keySet = map.keySet();  
	        for(String key : keySet) {  
	        	list.add(new NameValuePair(key, map.get(key)));  
	        }  
			
			NameValuePair[] params = new NameValuePair[list.size()];
			for (int i = 0; i < list.size(); i++) {
				params[i] = list.get(i);
			}
			postMethod.addParameters(params);
			client.executeMethod(postMethod);
			result = postMethod.getResponseBodyAsString();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			postMethod.releaseConnection();
		}
		return result;
	}
	
	public static final String ajaxClientPost(String url, Map<String,String> map) {
		return ajaxClientPost(url,map,null);
	}
	
	public static final String ajaxClientPost(String url, Map<String,String> map,Map<String,String> cookies) {
		String result = "";
		HttpClient client = new HttpClient();
		client.getParams().setParameter(HttpMethodParams.HTTP_CONTENT_CHARSET, "UTF-8");
		client.getHttpConnectionManager().getParams().setConnectionTimeout(8000); 
		PostMethod postMethod = new PostMethod(url);
		//设置AJAX请求
		postMethod.setRequestHeader("X-Requested-With", "XMLHttpRequest");
		//设置cookie
		if(null != cookies){
			Set<String> keySet = map.keySet();
			for(String key : keySet) {
				postMethod.setRequestHeader("Cookie", key+"="+cookies.get(key));
			}
		}
		
		try {			
			List<NameValuePair> list = new ArrayList <NameValuePair>();
	        Set<String> keySet = map.keySet();  
	        for(String key : keySet) {  
	        	list.add(new NameValuePair(key, map.get(key)));  
	        }  
			
			NameValuePair[] params = new NameValuePair[list.size()];
			for (int i = 0; i < list.size(); i++) {
				params[i] = list.get(i);
			}
			postMethod.addParameters(params);
			client.executeMethod(postMethod);
			result = postMethod.getResponseBodyAsString();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			postMethod.releaseConnection();
		}
		return result;
	}
}
