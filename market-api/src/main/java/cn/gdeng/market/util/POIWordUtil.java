package cn.gdeng.market.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.commons.httpclient.params.HttpMethodParams;
import org.apache.poi.hwpf.HWPFDocument;
import org.apache.poi.hwpf.usermodel.Range;
/**
 * POI操作doc文档工具类
 * @author dengjianfeng
 *
 */
public class POIWordUtil {

	/**
	 * 使用doc文档模板生成doc新文档(仅支持doc：2003版本)
	 * @param dataMap
	 * @param url：doc文档模板网络资源url
	 * @param desPath：生成doc新文档路径
	 * @throws IOException 
	 */
	public static void generateDocUsingTemplate(String url, Map<String, String> dataMap, String desPath) throws IOException{
		HWPFDocument templateDoc = getRemoteDoc(url);
		writeDataToDoc(templateDoc, dataMap);
		generateDocFile(templateDoc, desPath);
	}
	
	/**
	 * 获取远程doc文档(仅支持doc：2003版本)
	 * @param url
	 * @return
	 * @throws IOException 
	 */
	private static HWPFDocument getRemoteDoc(String url) throws IOException{
		HWPFDocument doc = null;
	    InputStream inputStream = null;
		HttpClient client = new HttpClient();
		client.getParams().setParameter(HttpMethodParams.HTTP_CONTENT_CHARSET, "UTF-8");
		client.getHttpConnectionManager().getParams().setConnectionTimeout(8000);
		GetMethod getMethod = new GetMethod(url);
		try {
			client.executeMethod(getMethod);
			inputStream = getMethod.getResponseBodyAsStream();
			doc = new HWPFDocument(inputStream);
		} finally {
			getMethod.releaseConnection();
			if(inputStream != null){
				try{
					inputStream.close();
				}catch(Exception e){
					e.printStackTrace();
				}
			}
		}
		return doc;
	}
	
	/**
	 * 数据填充到doc模板(仅支持doc：2003版本)
	 * @param doc
	 * @param dataMap
	 */
	private static void writeDataToDoc(HWPFDocument doc, Map<String, String> dataMap){
		if(dataMap == null){
			return;
		}
		Range range = doc.getRange();
		String text = range.text();
		Pattern pattern = Pattern.compile("\\$\\{.+?\\}");  
        Matcher matcher = pattern.matcher(text);  
        while(matcher.find()){
        	String key = matcher.group();
        	String val = dataMap.get(key);
        	if(val == null){
        		range.replaceText(key, "");
        	}else{
        		range.replaceText(key, val);
        	}
        }
	}
	
	/**
	 * 生成doc文件(仅支持doc：2003版本)
	 * @param sourceDoc
	 * @param desPath
	 * @throws IOException
	 */
	private static void generateDocFile(HWPFDocument sourceDoc, String desPath) throws IOException{
		OutputStream os = null; 
		try {
			os = new FileOutputStream(desPath); 
			sourceDoc.write(os);
		}finally{
			if(os != null){
				try{
					os.close(); 
				}catch(IOException e){
					e.printStackTrace();
				}
			}
		} 
	}
	
	public static void main(String[] args) throws IOException {
		File file = new File("d:\\租赁合同（书签）.doc");
		InputStream is = new FileInputStream(file);
		HWPFDocument doc = new HWPFDocument(is);
		HashMap<String, String> dataMap = new HashMap<String, String>();
		dataMap.put("partA", "张三");
		dataMap.put("partB", "李四");
		dataMap.put("contractNo", "AC000000101");
		dataMap.put("area", "武汉白沙洲市场武汉白沙洲市场武汉白沙洲市场武汉白沙洲市场武汉白沙洲市场");
		dataMap.put("building", "1");
		writeDataToDoc(doc, dataMap);
		generateDocFile(doc, "d:\\dmoc.doc");
	}
}
