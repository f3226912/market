package cn.gdeng.market.admin.controller.admin;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.alibaba.fastjson.JSON;

import cn.gdeng.market.admin.bean.AjaxResult;
import cn.gdeng.market.admin.controller.base.BaseController;
import cn.gdeng.market.dto.admin.SysOrgDTO;
import cn.gdeng.market.dto.bean.GuDengPage;
import cn.gdeng.market.entity.admin.SysOrgEntity;
import cn.gdeng.market.enums.SysOrgEnum;
import cn.gdeng.market.exception.BizException;
import cn.gdeng.market.service.admin.SysOrgService;

/**
 * 组织架构控制器
 * @author sss
 *
 */
@Controller
@RequestMapping("company")
public class SysOrgController extends BaseController{
	
	private Logger logger = LoggerFactory.getLogger(SysOrgController.class);
	
	@Resource
	private SysOrgService sysOrgService;
	
	/**
	 * 进入公司管理页面
	 * @param mv
	 * @return
	 * @throws BizException
	 */
	@RequestMapping("index")
	public ModelAndView index(ModelAndView mv) throws BizException {
		mv.setViewName("sys/company/companyManager");
		return mv;
	}
	
	@RequestMapping("toAddCompany")
	public ModelAndView toAddCompany(ModelAndView mv) throws BizException {
		mv.setViewName("sys/company/addCompany");
		return mv;
	}

	@RequestMapping("addSysOrg")
	@ResponseBody
	public AjaxResult<Integer> addSysOrg(HttpServletRequest request,SysOrgDTO entity) throws Exception{
		logger.info("添加组织入参:"+JSON.toJSONString(entity));

		AjaxResult<Integer> res = new AjaxResult<>();
		//获取用户
		fillCreateAndUpdateUser(entity);
		entity.setStatus(SysOrgEnum.STATUS.NORMAL);
		int id = sysOrgService.addSysOrg(entity);
		res.setResult(id);
		logger.info("添加组织成功，生成ID="+id);
		return res;
	}
	
	@RequestMapping("updateSysOrg")
	@ResponseBody
	public AjaxResult<Integer> updateSysOrg(HttpServletRequest request,SysOrgDTO entity) throws Exception{
		logger.info("修改组织入参:"+JSON.toJSONString(entity));
		AjaxResult<Integer> res = new AjaxResult<>();
		//获取用户
		fillUpdateUser(entity);

		sysOrgService.updateSysOrg(entity);
		logger.info("修改组织成功");
		return res;
	}
	
	@RequestMapping("deleteSysOrg")
	@ResponseBody
	public AjaxResult<Integer> deleteSysOrg(HttpServletRequest request,SysOrgEntity entity) throws Exception{
		logger.info("删除组织入参:id="+entity.getId());
		AjaxResult<Integer> res = new AjaxResult<>();
		//获取用户
		fillUpdateUser(entity);	
		sysOrgService.deleteSysOrg(entity);
		logger.info("删除组织成功");
		return res;
	}
	
	@RequestMapping("querySysOrg")
	@ResponseBody
	public AjaxResult<SysOrgDTO> querySysOrg(SysOrgEntity entity) throws Exception{
		logger.info("查询组织入参:"+JSON.toJSONString(entity));
		AjaxResult<SysOrgDTO> res = new AjaxResult<>();
		SysOrgDTO dto = sysOrgService.querySysOrg(entity);
		res.setResult(dto);
		return res;
	}
	
	@RequestMapping("queryTopSysOrg4Page")
	@ResponseBody
	public AjaxResult<GuDengPage<SysOrgDTO>> queryTopSysOrg4Page(HttpServletRequest request,SysOrgDTO dto) throws Exception{
		logger.info("分页查询顶级公司入参:"+JSON.toJSONString(dto));

		AjaxResult<GuDengPage<SysOrgDTO>> res = new AjaxResult<>();
		//获取分页信息
		GuDengPage<SysOrgDTO> page = getPageInfoByRequest();
		page.getParaMap().put("likeFullName", dto.getFullName());
		page = sysOrgService.queryTopOrgByPage(page);
		res.setResult(page);
		return res;
	}
	
	/**
	 * 查询所有顶级公司
	 * @param entity
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("queryTopOrgList")
	@ResponseBody
	public AjaxResult<List<SysOrgDTO>> queryTopOrgList() throws Exception{
		
		AjaxResult<List<SysOrgDTO>> res = new AjaxResult<>();
		Map<String,Object> params = new HashMap<>();
		params.put("topOrg", true);
		List<SysOrgDTO> list = sysOrgService.queryByCondition(params);
		res.setResult(list);
		
		return res;
	}
	
}
