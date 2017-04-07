package cn.gdeng.market.lease.controller.admin;

import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.alibaba.fastjson.JSON;

import cn.gdeng.market.dto.admin.SysOrgDTO;
import cn.gdeng.market.dto.admin.SysUserDTO;
import cn.gdeng.market.dto.bean.GuDengPage;
import cn.gdeng.market.dto.bean.Node;
import cn.gdeng.market.entity.admin.SysOrgEntity;
import cn.gdeng.market.enums.SysOrgEnum;
import cn.gdeng.market.enums.SysOrgTreeEnum;
import cn.gdeng.market.exception.BizException;
import cn.gdeng.market.lease.bean.AjaxResult;
import cn.gdeng.market.lease.controller.base.BaseController;
import cn.gdeng.market.service.admin.SysOrgService;

/**
 * 组织架构控制器
 * @author sss
 *
 */
@Controller
@RequestMapping("sysOrg")
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
		mv.setViewName("sys/org/orgManager");
		return mv;
	}
	
	@RequestMapping("toAddOrg")
	public ModelAndView toAddOrg(ModelAndView mv) throws BizException {
		mv.setViewName("sys/org/addOrg");
		return mv;
	}

	@RequestMapping("addSysOrg")
	@ResponseBody
	public AjaxResult<Integer> addSysOrg(HttpServletRequest request,SysOrgDTO entity) throws Exception{
		logger.info("添加组织入参:"+JSON.toJSONString(entity));

		AjaxResult<Integer> res = new AjaxResult<>();
		
		int id = 0;
		//获取用户
		if(entity.getId() != null){
			//获取用户
			fillUpdateUser(entity);
			sysOrgService.updateSysOrg(entity);
		} else {
			fillCreateAndUpdateUser(entity);
			entity.setStatus(SysOrgEnum.STATUS.NORMAL);
			id = sysOrgService.addSysOrg(entity);
		}
		
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
	
	@RequestMapping("queryUserPage")
	@ResponseBody
	public AjaxResult<GuDengPage<SysUserDTO>> queryUserPage(HttpServletRequest request,Integer orgId) throws Exception{

		logger.info("根据组织查询用户:组织ID:"+orgId);
		AjaxResult<GuDengPage<SysUserDTO>> res = new AjaxResult<>();
		GuDengPage<SysUserDTO> page = getPageInfoByRequest();
		page = sysOrgService.queryUserPageByOrgId(page, orgId);
		res.setResult(page);
		return res;
	}
	
	@RequestMapping("initOrgTree")
	@ResponseBody
	public AjaxResult<List<Node>> initOrgTree() throws Exception{
		logger.info("初始化用户组织树");
		AjaxResult<List<Node>> res = new AjaxResult<>();
		//获取用户的顶级公司
		SysOrgDTO topGroup = getUserGroup();
		Assert.notNull(topGroup,"当前Session中不存在用户的顶级公司");
		List<Node> nodes = sysOrgService.initOrgTree(topGroup.getId()+"",SysOrgTreeEnum.MARKET);
		res.setResult(nodes);
		return res;
	}

	/**
	 * 初始化组织树，默认将集团展开
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("initGroupTree")
	@ResponseBody
	public AjaxResult<List<Node>> initGroupTree() throws Exception{
		logger.info("初始化用户组织树,展开第一节点");
		AjaxResult<List<Node>> res = new AjaxResult<>();
		//获取用户的顶级公司
		SysOrgDTO topGroup = getUserGroup();
		Assert.notNull(topGroup,"当前Session中不存在用户的顶级公司");
		List<Node> nodes = sysOrgService.initOrgTree(topGroup.getId()+"",SysOrgTreeEnum.GROUP);
		res.setResult(nodes);
		return res;
	}

}
