package cn.gdeng.market.lease.controller.admin.post;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import cn.gdeng.market.dto.admin.SysUserDTO;
import cn.gdeng.market.dto.bean.GuDengPage;
import cn.gdeng.market.exception.BizException;
import cn.gdeng.market.lease.bean.AjaxResult;
import cn.gdeng.market.lease.controller.base.BaseController;
import cn.gdeng.market.service.admin.SysPostService;

/**
 * 岗位下的用户管理
 **/
@Controller
@RequestMapping("post")
public class PostUserController extends BaseController {
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
	@RequestMapping("user")
	public ModelAndView index(ModelAndView mv,String postId) throws BizException {
		mv.setViewName("sys/post/user");
		return mv;
	}

	/**
	 * 获取岗位下的用户列表信息
	 * 
	 * @param post
	 * @return
	 * @throws Exception
	 * @author lidong dli@gdeng.cn
	 * @time 2016年10月12日 下午4:09:25
	 */
	@RequestMapping(value = "user/{postId}")
	@ResponseBody
	public AjaxResult<GuDengPage<SysUserDTO>> getUsers(@PathVariable Integer postId) throws Exception {
		AjaxResult<GuDengPage<SysUserDTO>> res = new AjaxResult<>();
		GuDengPage<SysUserDTO> page = getPageInfoByRequest();
		Map<String, Object> param = page.getParaMap();
		param.put("postId", postId);
		page.setParaMap(param);
		// 获取分页信息
		page = sysPostService.getPostUserListPage(page);
		res.setResult(page);
		return res;
	}

}
