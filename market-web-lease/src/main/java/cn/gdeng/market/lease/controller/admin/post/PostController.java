package cn.gdeng.market.lease.controller.admin.post;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.alibaba.fastjson.JSON;

import cn.gdeng.market.dto.admin.SysPostDTO;
import cn.gdeng.market.dto.bean.GuDengPage;
import cn.gdeng.market.entity.admin.SysPostDataEntity;
import cn.gdeng.market.entity.admin.SysPostEntity;
import cn.gdeng.market.entity.admin.SysPostFunctionEntity;
import cn.gdeng.market.enums.Const;
import cn.gdeng.market.exception.BizException;
import cn.gdeng.market.lease.bean.AjaxResult;
import cn.gdeng.market.lease.controller.base.BaseController;
import cn.gdeng.market.service.admin.SysPostService;

/**
 * 岗位管理
 **/
@Controller
@RequestMapping("post")
public class PostController extends BaseController {
	@Autowired
	private SysPostService sysPostService;

	/**
	 * 进入岗位管理首页
	 * 
	 * @param mv
	 * @return
	 * @throws BizException
	 * @author lidong dli@gdeng.cn
	 * @time 2016年10月12日 下午12:07:17
	 */
	@RequestMapping("index")
	public ModelAndView index(ModelAndView mv) throws BizException {
		mv.setViewName("sys/post/index");
		return mv;
	}

	/**
	 * 分页列表查询
	 * 
	 * @param user
	 * @return
	 * @throws BizException
	 * @author lidong dli@gdeng.cn
	 * @time 2016年10月12日 上午11:53:32
	 */
	@RequestMapping(value = "list")
	@ResponseBody
	public AjaxResult<GuDengPage<SysPostDTO>> list(SysPostDTO post) throws Exception {
		AjaxResult<GuDengPage<SysPostDTO>> res = new AjaxResult<>();
		GuDengPage<SysPostDTO> page = getPageInfoByRequest();
		Map<String, Object> param = page.getParaMap();
		if (StringUtils.isNotEmpty(post.getName())) {
			param.put("name", post.getName());
		}
		if (post.getOrgId() != null) {
			param.put("orgId", post.getOrgId());
		}
		param.put("status", 1);
		param.put("topOrgId", getSysUser().getGroupId());
		page.setParaMap(param);
		// 获取分页信息
		page = sysPostService.getListPage(page);
		res.setResult(page);
		return res;
	}

	/**
	 * 查询岗位集合
	 * 
	 * @param post
	 * @return
	 * @throws Exception
	 * @author lidong dli@gdeng.cn
	 * @time 2016年10月18日 下午1:40:01
	 */
	@RequestMapping(value = "query")
	@ResponseBody
	public AjaxResult<List<SysPostDTO>> query(SysPostDTO post) throws Exception {
		AjaxResult<List<SysPostDTO>> res = new AjaxResult<>();
		Map<String, Object> param = new HashMap<>();
		if (post.getOrgId() != null) {
			param.put("orgId", post.getOrgId());
		}
		if (StringUtils.isNotEmpty(post.getName())) {
			param.put("name", post.getName());
		}
		param.put("status", 1);
		// 获取分页信息
		List<SysPostDTO> list = sysPostService.getList(param);
		res.setResult(list);
		return res;
	}

	@RequestMapping(value = "get/{id}")
	@ResponseBody
	public AjaxResult<SysPostDTO> get(@PathVariable Integer id) throws Exception {
		AjaxResult<SysPostDTO> res = new AjaxResult<>();
		Map<String, Object> map = new HashMap<>();
		map.put("id", id);
		List<SysPostDTO> posts = sysPostService.getList(map);
		if (posts != null && posts.size() > 0) {
			res.setResult(posts.get(0));
		}
		return res;
	}

	/**
	 * 进入添加页面
	 * 
	 * @param mv
	 * @return
	 * @throws BizException
	 * @author lidong dli@gdeng.cn
	 * @time 2016年10月13日 下午3:17:56
	 */
	@RequestMapping(value = "addPostBefore")
	public ModelAndView addPost(ModelAndView mv) throws BizException {
		mv.setViewName("sys/post/addPost");
		return mv;
	}

	/**
	 * 添加岗位
	 * 
	 * @param entity
	 * @return
	 * @throws Exception
	 * @author lidong dli@gdeng.cn
	 * @time 2016年10月12日 下午2:40:58
	 */
	@RequestMapping(value = "addPost", method = RequestMethod.POST)
	@ResponseBody
	public AjaxResult<Long> addSysPost(SysPostEntity entity, SysPostDTO dto) throws Exception {
		logger.info("添加岗位入参:" + JSON.toJSONString(entity));
		AjaxResult<Long> res = new AjaxResult<>();
		res.setIsSuccess(false);
		if (StringUtils.isEmpty(entity.getName())) {
			res.setMsg("请填写岗位名称");
			return res;
		} else if (entity.getOrgId() == null) {
			res.setIsSuccess(false);
			res.setMsg("请选择部门");
			return res;
		}
		/*else if (StringUtils.isEmpty(dto.getFuncStr())) {
			res.setMsg("请设置岗位的功能权限");
			return res;
		} else if (StringUtils.isEmpty(dto.getDataStr())) {
			res.setMsg("请设置岗位的数据权限");
			return res;
		}*/
		res.setIsSuccess(true);
		entity.setTopOrgId(getUserGroup().getId());
		entity.setCreateUserId(getUserIdStr());
		entity.setTopOrgId(getSysUser().getGroupId());
		dto.setPfuncs(getPfuncs(dto.getFuncStr()));
		dto.setPdatas(getPdatas(dto.getDataStr()));
		Long id = sysPostService.addPost(entity, dto);
		res.setResult(id);
		logger.info("添加岗位成功，生成ID=" + id);
		return res;
	}

	/**
	 * 进入修改岗位页面
	 * 
	 * @param mv
	 * @return
	 * @throws BizException
	 * @author lidong dli@gdeng.cn
	 * @time 2016年10月13日 下午4:25:13
	 */
	@RequestMapping(value = "updatePost/{id}")
	public ModelAndView updatePost(ModelAndView mv, @PathVariable Integer id) throws BizException {
		Map<String, Object> map = new HashMap<>();
		map.put("id", id);
		List<SysPostDTO> posts = sysPostService.getList(map);
		if (posts != null && posts.size() > 0) {
			mv.addObject("post", posts.get(0));
		}
		mv.setViewName("sys/post/editPost");
		return mv;
	}

	/**
	 * 修改岗位信息
	 * 
	 * @param dto
	 * @return
	 * @throws Exception
	 * @author lidong dli@gdeng.cn
	 * @time 2016年10月12日 下午2:44:25
	 */
	@RequestMapping(value = "updatePost", method = RequestMethod.POST)
	@ResponseBody
	public AjaxResult<Long> updatePost(SysPostEntity entity, SysPostDTO dto) throws Exception {
		logger.info("修改岗位入参:" + JSON.toJSONString(entity));
		AjaxResult<Long> res = new AjaxResult<>();
		res.setIsSuccess(false);
		if (dto.getId() == null) {
			res.setMsg("请选择要赋予权限的岗位");
			return res;
		}
		/*else if (StringUtils.isEmpty(dto.getFuncStr())) {
			res.setMsg("请设置岗位的功能权限");
			return res;
		} else if (StringUtils.isEmpty(dto.getDataStr())) {
			res.setMsg("请设置岗位的数据权限");
			return res;
		}*/
		res.setIsSuccess(true);
		entity.setUpdateUserId(getUserIdStr());
		dto.setPfuncs(getPfuncs(dto.getFuncStr()));
		dto.setPdatas(getPdatas(dto.getDataStr()));
		Long result = sysPostService.updatePost(entity, dto);
		res.setResult(result);
		logger.info("修改岗位成功");
		return res;
	}

	/**
	 * 逻辑删除岗位信息
	 * 
	 * @param entity
	 * @return
	 * @throws Exception
	 * @author lidong dli@gdeng.cn
	 * @time 2016年10月12日 下午3:01:24
	 */
	@RequestMapping("deletePost")
	@ResponseBody
	public AjaxResult<Integer> deletePost(SysPostEntity entity) throws Exception {
		logger.info("删除岗位入参:=" + entity);
		AjaxResult<Integer> res = new AjaxResult<>();
		Map<String, Object> map = new HashMap<>();
		map.put("postId", entity.getId());
		int total = sysPostService.checkPost(map);
		if (total < 1) {
			entity.setStatus("0");
			entity.setUpdateTime(new Date());
			entity.setUpdateUserId(getUserIdStr());
			int result = sysPostService.deleteById(entity);
			res.setResult(result);
			res.setMsg("删除成功");
		} else {
			res.setMsg("删除失败");
		}
		logger.info("删除岗位成功");
		return res;
	}

	/**
	 * 删除岗位之前，检测岗位下是否存在用户
	 * 
	 * @param id
	 * @return
	 * @throws Exception
	 * @author lidong dli@gdeng.cn
	 * @time 2016年10月12日 下午4:20:36
	 */
	@RequestMapping("checkPost")
	@ResponseBody
	public AjaxResult<Integer> checkPost(Integer id) throws Exception {
		AjaxResult<Integer> res = new AjaxResult<>();
		Map<String, Object> map = new HashMap<>();
		map.put("postId", id);
		int result = sysPostService.checkPost(map);
		if (result < 1) {
			res.setMsg("删除岗位会导致相关业务数据丢失，确定该组织吗？");
		} else {
			res.setIsSuccess(false);
			res.setMsg("当前岗位关联有用户信息，如需删除，请先将关联的用户移到其他岗位。");
		}
		res.setResult(result);
		return res;
	}

	/**
	 * 进入岗位权限管理首页
	 * 
	 * @param mv
	 * @return
	 * @throws BizException
	 * @author lidong dli@gdeng.cn
	 * @time 2016年10月12日 下午12:07:17
	 */
	@RequestMapping("auth")
	public ModelAndView index(ModelAndView mv, String postId) throws BizException {
		mv.setViewName("sys/post/post-auth");
		return mv;
	}

	/**
	 * 获取功能权限
	 * 
	 * @param funcStr
	 * @return
	 * @author lidong dli@gdeng.cn
	 * @time 2016年10月17日 下午4:26:29
	 */
	private List<SysPostFunctionEntity> getPfuncs(String funcStr) {
		List<SysPostFunctionEntity> list = new ArrayList<>();
		if (StringUtils.isNotEmpty(funcStr)) {
			String[] funcs = funcStr.split(Const.SPLIT_COMMA);
			if (funcs != null && funcs.length > 0) {
				for (String func : funcs) {
					if (StringUtils.isNotEmpty(func)) {
						String[] item = func.split(Const.SPLIT_LINE);
						if (Const.MENU_PRE.equals(item[0] + Const.SPLIT_LINE)) {
							SysPostFunctionEntity entity = new SysPostFunctionEntity();
							entity.setAuthId(Long.valueOf(item[1]));// 菜单id
							entity.setType("1");// 类型：菜单
							list.add(entity);
						} else if (Const.BUTTON_PRE.equals(item[0] + Const.SPLIT_LINE)) {
							SysPostFunctionEntity entity = new SysPostFunctionEntity();
							entity.setAuthId(Long.valueOf(item[1]));// 按钮id
							entity.setType("2");// 类型：按钮
							list.add(entity);
						}
					}
				}
			}
		}
		return list;
	}

	private List<SysPostDataEntity> getPdatas(String dataStr) {
		List<SysPostDataEntity> list = new ArrayList<>();
		if (StringUtils.isNotEmpty(dataStr)) {
			String[] datas = dataStr.split(Const.SPLIT_COMMA);
			if (datas != null && datas.length > 0) {
				for (String data : datas) {
					if (StringUtils.isNotEmpty(data)) {
						String[] item = data.split(Const.SPLIT_LINE);
						if (Const.COMPANY_PRE.equals(item[0] + Const.SPLIT_LINE)) {
							SysPostDataEntity entity = new SysPostDataEntity();
							entity.setAuthId(Long.valueOf(item[1]));// 公司id
							entity.setType("1");// 类型：公司
							list.add(entity);
						} else if (Const.MARKET_PRE.equals(item[0] + Const.SPLIT_LINE)) {
							SysPostDataEntity entity = new SysPostDataEntity();
							entity.setAuthId(Long.valueOf(item[1]));// 市场id
							entity.setType("2");// 类型：市场
							list.add(entity);
						} else if (Const.SOURCE_PRE.equals(item[0] + Const.SPLIT_LINE)) {
							SysPostDataEntity entity = new SysPostDataEntity();
							entity.setAuthId(Long.valueOf(item[1]));// 资源类型id
							entity.setType("3");// 类型：资源类型
							list.add(entity);
						}
					}
				}
			}
		}
		return list;
	}
	
	@RequestMapping(value = "getPostListByOrg")
	@ResponseBody
	public AjaxResult<List<SysPostEntity>> getPostListByOrg(Integer orgId) throws Exception {
		AjaxResult<List<SysPostEntity>> res = new AjaxResult<>();
		if(orgId == null){
			return res;
		}
		Map<String,Object> params = new HashMap<>();
		params.put("orgId", orgId);
 		List<SysPostEntity> list = sysPostService.getSourcePostListByPage(params);
		res.setResult(list);
		return res;
	}
}
