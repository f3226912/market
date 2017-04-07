package cn.gdeng.market.lease.controller.common.tree;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import cn.gdeng.market.dto.admin.SysOrgDTO;
import cn.gdeng.market.dto.bean.ztree.ZTreeNode;
import cn.gdeng.market.exception.BizException;
import cn.gdeng.market.lease.bean.AjaxResult;

/**
 * ztree树管理
 * 
 * @author lidong dli@gdeng.cn
 * @time 2016年10月10日 下午1:55:15
 */
@Controller
@RequestMapping("tree")
public class ZTreeController extends BaseZTreeController {

	/**
	 * ztree组织结构树示例页面
	 * 
	 * @param mv
	 * @return
	 * @throws BizException
	 * @author lidong dli@gdeng.cn
	 * @time 2016年10月15日 下午2:38:47
	 */
	@RequestMapping("index")
	public ModelAndView index(ModelAndView mv) throws BizException {
		mv.setViewName("sys/post/zTree");
		return mv;
	}

	/**
	 * 组织结构树示例
	 * 
	 * @return
	 * @throws BizException
	 * @author lidong dli@gdeng.cn
	 * @time 2016年10月14日 上午11:00:51
	 */
	@RequestMapping("demo")
	@ResponseBody
	public AjaxResult<Object[]> getDemoTree() throws BizException {
		AjaxResult<Object[]> res = new AjaxResult<>();
		List<ZTreeNode> nodeList = new ArrayList<>();// ztree树结构对象
		Map<String, Object> param = new HashMap<>();
		List<SysOrgDTO> allOrgs = sysOrgService.queryByCondition(param);// 所有组织
		if (allOrgs != null && allOrgs.size() != 0) {
			for (SysOrgDTO node : allOrgs) {
				Map<String, Object> attr = new HashMap<>();// 节点自定义属性
				attr.put("orgType", node.getType());// 组织类型
				ZTreeNode treeNode = new ZTreeNode(node.getId(), node.getFullName(), node.getParentId());
				treeNode.setAttributs(attr);
				nodeList.add(treeNode);
			}
		}
		res.setResult(nodeList.toArray());
		return res;
	}

	/**
	 * 展开到市场级别的部门树<br>
	 * 新增岗位选择部门专用树
	 * 
	 * @return
	 * @throws BizException
	 * @author lidong dli@gdeng.cn
	 * @time 2016年10月14日 上午11:00:51
	 */
	@RequestMapping("dept")
	@ResponseBody
	public AjaxResult<Object[]> getDeptTree() throws BizException {
		AjaxResult<Object[]> res = new AjaxResult<>();
		Object[] nodeTree = buildDeptTree();
		res.setResult(nodeTree);
		return res;
	}

	/**
	 * 获取功能权限树<br>
	 * 岗位分配功能权限专用
	 * 
	 * @return
	 * @throws BizException
	 * @author lidong dli@gdeng.cn
	 * @time 2016年10月14日 上午11:00:51
	 */
	@RequestMapping("function")
	@ResponseBody
	public AjaxResult<Object[]> getFunctionTree(Integer postId) throws BizException {
		AjaxResult<Object[]> res = new AjaxResult<>();
		Object[] nodeTree = buildFunctionTree(postId);
		res.setResult(nodeTree);
		return res;
	}

	/**
	 * 获取数据权限树<br>
	 * 岗位分配数据权限专用
	 * 
	 * @return
	 * @throws BizException
	 * @author lidong dli@gdeng.cn
	 * @time 2016年10月15日 下午4:49:10
	 */
	@RequestMapping("data")
	@ResponseBody
	public AjaxResult<Object[]> getDateTree(Integer postId) throws BizException {
		AjaxResult<Object[]> res = new AjaxResult<>();
		Object[] nodeTree = buildDataTree(postId);
		res.setResult(nodeTree);
		return res;
	}

	/**
	 * 获取组织架构人员树<br>
	 * 工作流选择执行人员专用
	 * 
	 * @return
	 * @throws BizException
	 * @author lidong dli@gdeng.cn
	 * @time 2016年10月23日 下午5:08:27
	 */
	@RequestMapping("user")
	@ResponseBody
	public AjaxResult<Object[]> getOrgUserTree() throws BizException {
		AjaxResult<Object[]> res = new AjaxResult<>();
		Object[] nodeTree = buildOrgUserTree();
		res.setResult(nodeTree);
		return res;
	}
}
