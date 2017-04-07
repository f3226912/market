package cn.gdeng.market.util;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.commons.httpclient.params.HttpMethodParams;

public class HttpClientUtil {

	public static byte[] getBytes(String url) {
		ByteArrayOutputStream bos = new ByteArrayOutputStream(); 
		InputStream inputStream = null;
		
		HttpClient client = new HttpClient();
		client.getParams().setParameter(HttpMethodParams.HTTP_CONTENT_CHARSET, "UTF-8");
		client.getHttpConnectionManager().getParams().setConnectionTimeout(8000);
		
		GetMethod getMethod = new GetMethod(url);
		try {
			client.executeMethod(getMethod);
			inputStream = getMethod.getResponseBodyAsStream();
			
			byte[] b = new byte[1000];  
	        int n;  
	        while ((n = inputStream.read(b)) != -1) {  
                bos.write(b, 0, n);  
            }  
	        return bos.toByteArray();
			 
		}catch( Exception e) {
			e.printStackTrace();
		} finally {
			if(inputStream != null) {
				try {
					inputStream.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			if (bos != null) {
				try {
					bos.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		return null;
	}
}
