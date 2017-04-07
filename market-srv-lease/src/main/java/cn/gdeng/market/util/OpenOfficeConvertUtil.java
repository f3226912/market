package cn.gdeng.market.util;

import java.io.File;
import java.net.ConnectException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.artofsolving.jodconverter.DocumentConverter;
import com.artofsolving.jodconverter.openoffice.connection.OpenOfficeConnection;
import com.artofsolving.jodconverter.openoffice.connection.SocketOpenOfficeConnection;
import com.artofsolving.jodconverter.openoffice.converter.OpenOfficeDocumentConverter;

/***
 * OpenOffice文件转换工具
 * @author dengjianfeng
 *
 */
public class OpenOfficeConvertUtil {
	
	private Logger logger = LoggerFactory.getLogger(OpenOfficeConvertUtil.class);
	
	private PropertyUtil propertyUtil;
	
	private OpenOfficeConnection connection;
	
	private DocumentConverter converter;
	
	public void init(){
		new Thread() {
			public void run(){
				String openOfficeHost = propertyUtil.getValue("openOfficeHost");
				String openOfficePort = propertyUtil.getValue("openOfficePort");
				int port = 8100; //端口默认8100
				if(openOfficePort != null){
					port = Integer.parseInt(openOfficePort);
				}
				if(connection == null){
					connection = new SocketOpenOfficeConnection(openOfficeHost, port);
					
				}
				try {
					if(!connection.isConnected()){
						connection.connect();
					}
					converter = new OpenOfficeDocumentConverter(connection);
				} catch (ConnectException e) {
					logger.info("OpenOffice初始化连接异常", e);
				}
				logger.info("\nOpenOffice--链接初始化.......................................");
			}
		}.start();
	}

	/**
	 * 调用此方法前需要启动OpenOffice服务
	 * @param host
	 * @param port
	 * @param inputFile
	 * @param outputFile
	 * @throws ConnectException
	 */
	public void convert(File inputFile, File outputFile) throws ConnectException{
		if(connection == null) {
			String openOfficeHost = propertyUtil.getValue("openOfficeHost");
			String openOfficePort = propertyUtil.getValue("openOfficePort");
			int port = 8100; //端口默认8100
			if(openOfficePort != null){
				port = Integer.parseInt(openOfficePort);
			}
			connection = new SocketOpenOfficeConnection(openOfficeHost, port);
			logger.info("\nOpenOffice进行了重连SocketOpenOfficeConnection.......................................");
		}
		if(!connection.isConnected()){
			connection.connect();
		}
		if(converter == null){
			converter = new OpenOfficeDocumentConverter(connection);
			logger.info("\nOpenOffice重建了OpenOfficeDocumentConverter.......................................");
		}
		converter.convert(inputFile, outputFile);
	}

	public PropertyUtil getPropertyUtil() {
		return propertyUtil;
	}

	public void setPropertyUtil(PropertyUtil propertyUtil) {
		this.propertyUtil = propertyUtil;
	}
	
}
