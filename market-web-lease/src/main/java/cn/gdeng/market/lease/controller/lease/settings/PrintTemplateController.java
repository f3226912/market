package cn.gdeng.market.lease.controller.lease.settings;

import java.io.IOException;
import java.io.InputStream;
import java.io.PushbackInputStream;
import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.apache.poi.POIXMLDocument;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import cn.gdeng.market.dto.bean.GuDengPage;
import cn.gdeng.market.dto.lease.settings.PrintTemplateDTO;
import cn.gdeng.market.entity.lease.settings.PrintTemplateEntity;
import cn.gdeng.market.enums.Const;
import cn.gdeng.market.enums.MsgCons;
import cn.gdeng.market.exception.BizException;
import cn.gdeng.market.lease.bean.AjaxResult;
import cn.gdeng.market.lease.controller.base.BaseController;
import cn.gdeng.market.redis.support.JodisTemplate;
import cn.gdeng.market.service.lease.settings.PrintTemplateService;
import cn.gdeng.market.util.FileUploadUtil;
import cn.gdeng.market.util.PropertyUtil;

/**
 * 套打模板
 * @author dengjianfeng
 * 
 */
@Controller
@RequestMapping("printTemplate")
public class PrintTemplateController extends BaseController {

	@Resource
	private PrintTemplateService printTemplateService;
	
	@Resource
	private PropertyUtil propertyUtil;
	
	@Resource
	private JodisTemplate jodisTemplate;
	
	/**
	 * 进入列表页
	 * @param mv
	 * @return
	 */
	@RequestMapping("index")
	public ModelAndView index(ModelAndView mv){
		mv.setViewName("settings/printTemplate/index");
		return mv;
	}

	/**
	 * 分页查询
	 * @param request
	 * @return
	 * @throws BizException
	 */
	@RequestMapping("queryByPage")
	@ResponseBody
	public AjaxResult<GuDengPage<PrintTemplateDTO>> queryByPage(HttpServletRequest request) throws BizException {
		GuDengPage<PrintTemplateDTO> page = new GuDengPage<PrintTemplateDTO>();
		// 普通查询参数
		Map<String, Object> paraMap = getParametersMap(request);
		paraMap.put("isDeleted", 0);
		// 分页查询参数
		setCommParameters(request, paraMap);
		page.setParaMap(paraMap);
		page = printTemplateService.queryByPage(page);
		
		AjaxResult<GuDengPage<PrintTemplateDTO>> ajaxResult = new AjaxResult<GuDengPage<PrintTemplateDTO>>();
		ajaxResult.setResult(page);
		return ajaxResult;
	}
	
	/**
	 * 进入新增页面
	 * @param mv
	 * @return
	 * @throws BizException
	 */
	@RequestMapping("add")
	public ModelAndView add(ModelAndView mv) throws BizException {
		mv.setViewName("settings/printTemplate/add");
		return mv;
	}
	
	/**
	 * 新增
	 * @param entity
	 * @return
	 * @throws BizException
	 */
	@RequestMapping("saveAdd")
	@ResponseBody
	public AjaxResult<Integer> saveAdd(PrintTemplateEntity entity) throws BizException {
		if(StringUtils.isBlank(entity.getTemplateCode())){
			return new AjaxResult<Integer>().withError(MsgCons.C_20000, "模板编码不能为空");
		}
		
		if(StringUtils.isBlank(entity.getTemplateName())){
			return new AjaxResult<Integer>().withError(MsgCons.C_20000, "模板名称不能为空");
		}
		
		String templateCode = entity.getTemplateCode();
		int codeCount = printTemplateService.countByTemplateCode(templateCode);
		if(codeCount > 0){
			return new AjaxResult<Integer>().withError(MsgCons.C_20000, "模板编码已存在");
		}
		
		String templateName = entity.getTemplateName();
		int nameCount = printTemplateService.countByTemplateName(templateName);
		if(nameCount > 0) {
			return new AjaxResult<Integer>().withError(MsgCons.C_20000, "模板名称已存在");
		}
		
		if(getUserId() != null) {
			entity.setCreateUserId(getUserId().toString());
		}
		printTemplateService.add(entity);
		return new AjaxResult<Integer>();
	}
	
	/**
	 * 删除
	 * @param id
	 * @return
	 * @throws BizException
	 */
	@RequestMapping("delete")
	@ResponseBody
	public AjaxResult<Integer> delete(String ids) throws BizException {
		printTemplateService.batchDelete(ids);
		return new AjaxResult<Integer>();
	}
	
	/**
	 * 进入修改页面
	 * @param id
	 * @param mv
	 * @return
	 * @throws BizException 
	 */
	@RequestMapping("edit/{id}")
	public ModelAndView edit(@PathVariable("id")int id, ModelAndView mv) throws BizException {
		PrintTemplateDTO dto = printTemplateService.queryById(id);
		mv.addObject("dto", dto);
		mv.addObject("host", propertyUtil.getValue("gd.uplaod.host"));
		mv.setViewName("settings/printTemplate/edit");
		return mv;
	}
	
	/**
	 * 修改
	 * @param dto
	 * @return
	 * @throws BizException
	 */
	@RequestMapping("saveEdit")
	@ResponseBody
	public AjaxResult<Integer> saveEdit(PrintTemplateEntity entity) throws BizException {
		if(entity.getId() == null){
			return new AjaxResult<Integer>().withError(MsgCons.C_20000, "修改记录不存在");
		}
		if(StringUtils.isBlank(entity.getTemplateCode())){
			return new AjaxResult<Integer>().withError(MsgCons.C_20000, "模板编码不能为空");
		}
		
		if(StringUtils.isBlank(entity.getTemplateName())){
			return new AjaxResult<Integer>().withError(MsgCons.C_20000, "模板名称不能为空");
		}
		
		if(getUserId() != null) {
			entity.setUpdateUserId(getUserId().toString());
		}
		printTemplateService.update(entity);
		return new AjaxResult<Integer>();
	}
	
	@RequestMapping("upload")
	@ResponseBody
	public AjaxResult<Map<String, String>> upload(@RequestParam("templateUrl")String templateUrl, @RequestParam(value = "file", required = true) MultipartFile file) {
		AjaxResult<Map<String, String>> ajaxResult = new AjaxResult<Map<String, String>>();

		String masterPath = null;
		InputStream inp = null;
		try {
			inp = file.getInputStream();
			if(!inp.markSupported()) {
				inp = new PushbackInputStream(inp, 8);
			}
			if(POIXMLDocument.hasOOXMLHeader(inp)) {
				ajaxResult.withError(MsgCons.C_20000, "目前仅支持word 2003版本，请重新上传！");
				return ajaxResult;
			}
	
			masterPath = uploadToServer(file);
			if(masterPath == null){
				ajaxResult.withError(MsgCons.C_20000, "文件上传失败！");
				return ajaxResult;
			}
			Map<String, String> resultMap = new HashMap<String, String>();
			resultMap.put("masterPath", masterPath);
			resultMap.put("host", propertyUtil.getValue("gd.uplaod.host"));
			ajaxResult.setResult(resultMap);
		} catch (IOException e) {
			e.printStackTrace();
			ajaxResult.withError(MsgCons.C_20000, "模板文件上传失败！");
		}finally{
			if(inp != null){
				try {
					inp.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		
		// 清除原来上传的缓存文件
		if (templateUrl != null) {
			String key = Const.PRINT_TEMPLATE_KEY_PRE + templateUrl;
			jodisTemplate.del(key.getBytes());
		}
		
		// 缓存新的上传文件
		try {
			if (masterPath != null) {
				String key = Const.PRINT_TEMPLATE_KEY_PRE + masterPath;
				jodisTemplate.set(key.getBytes(), file.getBytes());
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return ajaxResult;
	}
	
	/**
	 * 上传模板文件到阿里云
	 * @param file
	 * @return
	 */
	private String uploadToServer(MultipartFile file) {
		String endpoint = propertyUtil.getValue("gd.upload.endpoint");
		String accessKeyId = propertyUtil.getValue("gd.upload.accessKeyId");
		String accessKeySecret = propertyUtil.getValue("gd.upload.accessKeySecret");
		String bucketName = propertyUtil.getValue("gd.upload.bucketName");
		String path = propertyUtil.getValue("gd.uplaod.path")+".market/";
		
		String fileName = FileUploadUtil.generateUUID()+".doc";
		String masterFilePath = null;
		try {
			masterFilePath = FileUploadUtil.uploadFile(endpoint, accessKeyId, accessKeySecret, bucketName, path, fileName, file.getBytes());
		} catch (IOException e) {
			e.printStackTrace();
		}
		return masterFilePath;
	}

}
