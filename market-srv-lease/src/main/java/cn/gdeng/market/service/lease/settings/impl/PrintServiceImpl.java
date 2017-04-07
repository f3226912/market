package cn.gdeng.market.service.lease.settings.impl;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.ConnectException;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.annotation.Resource;

import org.apache.poi.hwpf.HWPFDocument;
import org.apache.poi.hwpf.usermodel.Range;
import org.springframework.stereotype.Service;

import cn.gdeng.market.dao.base.BaseDao;
import cn.gdeng.market.dto.lease.settings.PrintSettingDTO;
import cn.gdeng.market.enums.Const;
import cn.gdeng.market.enums.MsgCons;
import cn.gdeng.market.exception.BizException;
import cn.gdeng.market.redis.support.JodisTemplate;
import cn.gdeng.market.service.lease.settings.PrintService;
import cn.gdeng.market.util.FileUploadUtil;
import cn.gdeng.market.util.HttpClientUtil;
import cn.gdeng.market.util.OpenOfficeConvertUtil;
import cn.gdeng.market.util.PropertyUtil;

import com.gudeng.framework.core2.GdLogger;
import com.gudeng.framework.core2.GdLoggerFactory;

/**
 * 套打生成文件服务
 * @author dengjianfeng
 *
 */
@Service(value="printService")
public class PrintServiceImpl implements PrintService{
	
	private final GdLogger logger = GdLoggerFactory.getLogger(PrintServiceImpl.class);
	
	@Resource
	private BaseDao<?> baseDao;
	
	@Resource
	private PropertyUtil propertyUtil;
	
	@Resource
	private OpenOfficeConvertUtil openOfficeConvertUtil;
	
	@Resource
	private JodisTemplate jodisTemplate;

	@Override
	public String convertDocToPdf(int settingId, Map<String, String> dataMap) throws BizException {
		// 获取套打设置
		PrintSettingDTO printSetting = getPrintSetting(settingId);
		if (printSetting == null) {
			throw new BizException(MsgCons.C_20000, "套打设置【" + settingId + "】不存在");
		}
		
		// 获取模板文件
		String url = propertyUtil.getValue("gd.uplaod.host") + printSetting.getTemplateUrl();
		String key = Const.PRINT_TEMPLATE_KEY_PRE + printSetting.getTemplateUrl();
		HWPFDocument templateDoc = createDoc(key, url);
		if(templateDoc == null) {
			throw new BizException(MsgCons.C_20000, "套打设置【" + settingId + "】模板不存在");
		}
		
		// 填充数据
		writeData(templateDoc, dataMap);
		
		// 生成doc文件
		String printingFilePath = propertyUtil.getValue("printingFilePath");
		String datePath = FileUploadUtil.createDatePath(printingFilePath);
		String desPath = printingFilePath + datePath; //存放打印文件目录
		File dir = new File(desPath);
		if(!dir.exists()){
			dir.mkdirs();
		}
		String docFullPath = generateDocFile(templateDoc, desPath);
		
		// 转换文件（doc转pdf）
		String pdfFileName = FileUploadUtil.generateUUID()+".pdf";
		String pdfFullPath = desPath + pdfFileName;
		File doc = new File(docFullPath);
		File pdf = new File(pdfFullPath);
		convertFile(doc, pdf);
		
		// 删除doc文件
		doc.delete();

		return datePath + pdfFileName;
	}
	
	/**
	 * 获取套打设置信息
	 * @param settingId
	 * @return
	 */
	private PrintSettingDTO getPrintSetting(int settingId) {
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("id", settingId);
		PrintSettingDTO dto = baseDao.queryForObject("PrintSetting.queryById", paramMap, PrintSettingDTO.class);
		return dto;
	}
	
	/**
	 * 创建模板文件（先从缓存中获取，获取不到再从网络资源上获取）
	 * @param bytes
	 * @return
	 * @throws BizException 
	 * @throws IOException
	 */
	private HWPFDocument createDoc(String key, String url) throws BizException{
		HWPFDocument doc = getCacheDoc(key);
		
		if (doc == null) {
			doc = getRemoteDoc(key, url);
		}
		return doc;
	}
	
	/**
	 * 从缓存中获取模板文件
	 * @param key
	 * @return
	 */
	public HWPFDocument getCacheDoc(String key) {
		byte[] cacheBytes = jodisTemplate.get(key.getBytes());
		if(cacheBytes == null) {
			return null;
		}

		InputStream is = new ByteArrayInputStream(cacheBytes);
		HWPFDocument doc = null;
		try {
			doc = new HWPFDocument(is);
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (is != null) {
				try {
					is.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		return doc;
	}
	
	/**
	 * 获取远程doc文档
	 * @param url
	 * @return
	 * @throws BizException 
	 * @throws IOException 
	 */
	private HWPFDocument getRemoteDoc(String key, String url) throws BizException{
		byte[] bytes = HttpClientUtil.getBytes(url);
		if (bytes == null) {
			throw null;
		}
		
		jodisTemplate.set(key.getBytes(), bytes);
		
		InputStream is = new ByteArrayInputStream(bytes);
		HWPFDocument doc = null;
		try {
			doc = new HWPFDocument(is);
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (is != null) {
				try {
					is.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		return doc;
	}
	
	/**
	 * 填写数据
	 * @param doc
	 * @param dataMap
	 */
	private void writeData(HWPFDocument doc, Map<String, String> dataMap){
		Range range = doc.getRange();
		String text = range.text();
		Pattern pattern = Pattern.compile("\\$\\{.+?\\}");  
        Matcher matcher = pattern.matcher(text);  
        while(matcher.find()){
        	String key = matcher.group();
        	String val = null;
        	if(dataMap != null){
        		val = dataMap.get(key);
        	}
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
	 * @throws BizException 
	 * @throws IOException
	 */
	private String generateDocFile(HWPFDocument doc, String desPath) throws BizException{
		String fullPath = desPath + FileUploadUtil.generateUUID()+".doc";
		OutputStream os = null; 
		try {
			os = new FileOutputStream(fullPath); 
			doc.write(os);
		} catch (Exception e) {
			e.printStackTrace();
			throw new BizException(MsgCons.C_20000, "生成doc文件异常");
		}finally{
			if(os != null){
				try{
					os.close(); 
				}catch(IOException e){
					e.printStackTrace();
				}
			}
		} 
		return fullPath;
	}
	
	/**
	 * 文件转换
	 * @param doc
	 * @param pdf
	 * @throws BizException 
	 */
	private void convertFile(File doc, File pdf) throws BizException {
		try {
			long convertStart = System.currentTimeMillis();
			openOfficeConvertUtil.convert(doc, pdf);
			logger.info("\nOpenOffice--转换耗时:"+(System.currentTimeMillis()-convertStart));
		} catch (ConnectException e) {
			logger.info("转换doc文件异常", e);
			throw new BizException(MsgCons.C_20000, "转换doc文件异常");
		}
	}
}
