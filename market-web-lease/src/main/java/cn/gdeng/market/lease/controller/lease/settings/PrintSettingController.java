package cn.gdeng.market.lease.controller.lease.settings;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import cn.gdeng.market.dto.admin.SysOrgDTO;
import cn.gdeng.market.dto.bean.GuDengPage;
import cn.gdeng.market.dto.lease.settings.PrintSettingDTO;
import cn.gdeng.market.dto.lease.settings.PrintTemplateDTO;
import cn.gdeng.market.entity.lease.settings.PrintSettingEntity;
import cn.gdeng.market.enums.MsgCons;
import cn.gdeng.market.exception.BizException;
import cn.gdeng.market.lease.bean.AjaxResult;
import cn.gdeng.market.lease.controller.base.BaseController;
import cn.gdeng.market.service.lease.settings.PrintSetService;
import cn.gdeng.market.service.lease.settings.PrintTemplateService;

/**
 * 套打设置
 *
 */
@Controller
@RequestMapping("printSetting")
public class PrintSettingController extends BaseController{

	@Resource
	private PrintSetService printSetService;
	
	@Resource
	private PrintTemplateService printTemplateService;
	
	@RequestMapping("index")
	public ModelAndView index(ModelAndView mv) throws BizException {
		mv.setViewName("settings/printSetting/index");
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
	public AjaxResult<GuDengPage<PrintSettingDTO>> queryByPage(HttpServletRequest request) throws BizException {
		GuDengPage<PrintSettingDTO> page = new GuDengPage<PrintSettingDTO>();
		
		Map<String, Object> paraMap = getParametersMap(request);
		paraMap.put("isDeleted", 0);
		setCommParameters(request, paraMap);
		page.setParaMap(paraMap);
		page = printSetService.queryByPage(page);
		
		AjaxResult<GuDengPage<PrintSettingDTO>> ajaxResult = new AjaxResult<GuDengPage<PrintSettingDTO>>();
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
	public ModelAndView add(ModelAndView mv, HttpServletRequest request) throws BizException {
		// 当前登录的用户所属市场
		SysOrgDTO market = super.getCurrentMarket();
//		if(market == null) {
//			throw new BizException(MsgCons.C_20000, "获取当前登录用户所属市场失败！");
//		}
		
		// 模板列表
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("isDeleted", 0);
		List<PrintTemplateDTO> templateList = printTemplateService.queryListByCondition(paramMap);
		
		mv.addObject("templateList", templateList);
		mv.addObject("market", market);
		mv.setViewName("settings/printSetting/add");
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
	public AjaxResult<Integer> saveAdd(PrintSettingEntity entity) throws BizException {
		if(getUserId() != null) {
			entity.setCreateUserId(getUserId().toString());
		}
		printSetService.add(entity);
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
		printSetService.batchDelete(ids);
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
	public ModelAndView edit(@PathVariable("id") int id, ModelAndView mv) throws BizException {
		PrintSettingDTO dto = printSetService.queryById(id);
		
		// 模板列表
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("isDeleted", 0);
		List<PrintTemplateDTO> templateList = printTemplateService.queryListByCondition(paramMap);
				
		mv.addObject("templateList", templateList);
		mv.addObject("dto", dto);
		mv.setViewName("settings/printSetting/edit");
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
	public AjaxResult<Integer> saveEdit(PrintSettingDTO dto) throws BizException {
		if(getUserId() != null) {
			dto.setUpdateUserId(getUserId().toString());
		}
		printSetService.update(dto);
		return new AjaxResult<Integer>();
	}
	
	/**
	 * 套打设置列表（不分页）
	 * @param request
	 * @return
	 * @throws BizException
	 */
	@RequestMapping("queryList")
	@ResponseBody
	public AjaxResult<List<PrintSettingDTO>> queryList(HttpServletRequest request) throws BizException {
		Map<String, Object> paramMap = getParametersMap(request);
		SysOrgDTO market = super.getCurrentMarket();
		if(market == null) {
			throw new BizException(MsgCons.C_20000, "获取当前市场失败，无法获取套打设置！");
		}
		paramMap.put("marketId", market.getId());
		paramMap.put("isDeleted", 0);
		List<PrintSettingDTO> list = printSetService.queryListByCondition(paramMap);
		
		AjaxResult<List<PrintSettingDTO>> ajaxResult = new AjaxResult<List<PrintSettingDTO>>(); 
		ajaxResult.setResult(list);
		return ajaxResult;
	}
}
