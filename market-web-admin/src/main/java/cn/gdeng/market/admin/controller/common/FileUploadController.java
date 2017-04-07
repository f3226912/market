package cn.gdeng.market.admin.controller.common;

import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.alibaba.fastjson.JSONObject;

import cn.gdeng.market.util.CommonUtil;
import cn.gdeng.market.util.FileUploadUtil;
import cn.gdeng.market.util.PropertyUtil;

/**
 * 文件上传
 *
 */
@Controller
public class FileUploadController {
	
	@Autowired
	private PropertyUtil gdProperties;

	@RequestMapping(value="uploadFile",produces="application/json;charset=utf-8")
	@ResponseBody
	public String uploadFile(@RequestParam(value = "file", required = true) MultipartFile file){
		Map<String, Object> map = new HashMap<String, Object>();
		String fileName = CommonUtil.generateSimpleFileName(file.getOriginalFilename());
		Properties properties = gdProperties.getProperties();
		String endpoint = properties.getProperty("gd.upload.endpoint");
		String accessKeyId = properties.getProperty("gd.upload.accessKeyId");
		String accessKeySecret = properties.getProperty("gd.upload.accessKeySecret");
		String bucketName = properties.getProperty("gd.upload.bucketName");
		String path = properties.getProperty("gd.uplaod.path")+".market/";
		String host = properties.getProperty("gd.uplaod.host");
		try{
			String masterFilePath = FileUploadUtil.uploadFile(endpoint, accessKeyId, accessKeySecret, bucketName, path, fileName, file.getBytes());
			map.put("status", 1);
			map.put("message", masterFilePath);
			map.put("url", host + masterFilePath);
		}catch (Exception e) {
			e.printStackTrace();
			map.put("status", 0);
			map.put("message", "文件上传失败！");
		}
		return JSONObject.toJSONString(map);
	}
}
