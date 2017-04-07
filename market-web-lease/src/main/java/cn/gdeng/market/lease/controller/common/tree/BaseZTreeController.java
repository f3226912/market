package cn.gdeng.market.lease.controller.common.tree;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;

import cn.gdeng.market.dto.admin.SysMenuButtonDTO;
import cn.gdeng.market.dto.admin.SysMenuDTO;
import cn.gdeng.market.dto.admin.SysOrgDTO;
import cn.gdeng.market.dto.admin.SysUserDTO;
import cn.gdeng.market.dto.bean.ztree.ZTreeNode;
import cn.gdeng.market.dto.lease.settings.MarketResourceTypeDTO;
import cn.gdeng.market.entity.admin.SysPostDataEntity;
import cn.gdeng.market.entity.admin.SysPostFunctionEntity;
import cn.gdeng.market.enums.Const;
import cn.gdeng.market.enums.SysOrgEnum;
import cn.gdeng.market.exception.BizException;
import cn.gdeng.market.lease.controller.base.BaseController;
import cn.gdeng.market.service.admin.SysMenuButtonService;
import cn.gdeng.market.service.admin.SysMenuService;
import cn.gdeng.market.service.admin.SysOrgService;
import cn.gdeng.market.service.admin.SysPostService;
import cn.gdeng.market.service.admin.SysUserService;
import cn.gdeng.market.service.lease.settings.MarketResourceTypeService;
import cn.gdeng.market.util.ztree.ZTreeUtil;

/**
 * 树结构管理
 * 
 * @author lidong dli@gdeng.cn
 * @time 2016年10月10日 下午1:55:15
 */
abstract class BaseZTreeController extends BaseController {
	@Autowired
	protected SysOrgService sysOrgService;
	@Autowired
	protected SysMenuService sysMenuService;
	@Autowired
	protected SysMenuButtonService sysMenuButtonService;
	@Autowired
	protected MarketResourceTypeService marketResourceTypeService;
	@Autowired
	protected SysPostService sysPostService;
	@Autowired
	protected SysUserService sysUserService;

	/**
	 * 获取新增岗位选择部门树,最低层只展开到市场级别的部门<br>
	 * 该方法为新增岗位选择部门专用方法
	 * 
	 * @return
	 * @throws BizException
	 * @author lidong dli@gdeng.cn
	 * @time 2016年10月15日 上午10:06:58
	 */
	protected Object[] buildDeptTree() throws BizException {
		SysOrgDTO baseNode = getUserGroup();// 当前管理员所在集团，作为树根节点开始遍历数据
		List<ZTreeNode> nodeList = new ArrayList<>();
		Map<String, Object> attributs = new HashMap<>();// 节点自定义属性
		attributs.put("orgType", baseNode.getType());
		ZTreeNode root = new ZTreeNode(baseNode.getId(), baseNode.getFullName(), null);
		root.setAttributs(attributs);
		nodeList.add(root);// 根节点
		Map<String, Object> param = new HashMap<>();
		param.put("status", 1);
		List<SysOrgDTO> allOrgs = sysOrgService.queryByCondition(param);
		if (allOrgs != null && allOrgs.size() != 0) {
			List<SysOrgDTO> orgs = getOrgsByPid(allOrgs, baseNode.getId());
			for (SysOrgDTO node : orgs) {
				Map<String, Object> attr = new HashMap<>();// 节点自定义属性
				attr.put("orgType", node.getType());
				ZTreeNode treeNode = new ZTreeNode(node.getId(), node.getFullName(), node.getParentId());
				treeNode.setAttributs(attr);
				nodeList.add(treeNode);
			}
		}
		return filter1(nodeList).toArray();
	}

	/**
	 * 生成数据权限树
	 * 
	 * @return
	 * @throws BizException
	 * @author lidong dli@gdeng.cn
	 * @param postId
	 * @time 2016年10月15日 下午3:28:13
	 */
	protected Object[] buildDataTree(Integer postId) throws BizException {
		List<ZTreeNode> nodeList = new ArrayList<>();
		Map<String, Object> param = new HashMap<>();
		param.put("status", 1);
		List<MarketResourceTypeDTO> sources = marketResourceTypeService.getMarketResourceType(param);// 资源类型集合
		List<String> typeList = new ArrayList<>();
		typeList.add(SysOrgEnum.TYPE.COMPANY);
		typeList.add(SysOrgEnum.TYPE.MARKET);
		param.put("typeList", typeList);
		List<SysOrgDTO> allOrgs = sysOrgService.queryByCondition(param);// 查询出所有公司和市场
		SysOrgDTO group = getUserGroup();// 当前集团
		List<SysPostDataEntity> datas = null;
		if (postId != null) {
			Map<String, Object> map = new HashMap<>();
			map.put("postId", postId);
			datas = sysPostService.getDatasByPost(map);
		}
		ZTreeNode root = new ZTreeNode(Const.COMPANY_PRE + group.getId(), group.getFullName(), null);
		Map<String, Object> attrRoot = new HashMap<>();
		attrRoot.put("type", group.getType());
		root.setAttributs(attrRoot);
		root.setOpen(true);
		if (isNodeCheckedForData(Const.COMPANY_PRE + group.getId(), datas)) {
			root.setChecked(true);
		}
		nodeList.add(root);// 集团公司，根节点
		List<SysOrgDTO> orgs = getOrgsByPid(allOrgs, group.getId());
		// 公司、市场数据权限
		for (SysOrgDTO sysOrgDTO : orgs) {
			ZTreeNode treeNode = null;
			if (SysOrgEnum.TYPE.COMPANY.equals(sysOrgDTO.getType())) {
				treeNode = new ZTreeNode(Const.COMPANY_PRE + sysOrgDTO.getId(), sysOrgDTO.getFullName(), Const.COMPANY_PRE + sysOrgDTO.getParentId());
			} else {
				treeNode = new ZTreeNode(Const.MARKET_PRE + sysOrgDTO.getId(), sysOrgDTO.getFullName(), Const.COMPANY_PRE + sysOrgDTO.getParentId());
			}
			// 资源类型数据权限
			if (sysOrgDTO.getType().equals(SysOrgEnum.TYPE.MARKET)) {
				for (MarketResourceTypeDTO item : sources) {
					if (sysOrgDTO.getId().intValue() == item.getMarketId().intValue()) {
						ZTreeNode source = new ZTreeNode(Const.SOURCE_PRE + item.getId(), item.getName(), Const.MARKET_PRE + item.getMarketId());
						if (isNodeCheckedForData(Const.SOURCE_PRE + item.getId(), datas)) {
							source.setChecked(true);
						}
						nodeList.add(source);
					}
				}
			}
			if (isNodeCheckedForData(Const.COMPANY_PRE + sysOrgDTO.getId(), datas)) {
				treeNode.setChecked(true);
			}else if (isNodeCheckedForData(Const.MARKET_PRE + sysOrgDTO.getId(), datas)) {
				treeNode.setChecked(true);
			}
			treeNode.setOpen(true);
			nodeList.add(treeNode);
		}
		return nodeList.toArray();
	}

	/**
	 * 生成功能权限树（菜单按钮树）
	 * 
	 * @return
	 * @throws BizException
	 * @author lidong dli@gdeng.cn
	 * @param postId
	 * @time 2016年10月15日 下午4:08:19
	 */
	protected Object[] buildFunctionTree(Integer postId) throws BizException {
		List<ZTreeNode> nodeList = new ArrayList<>();
		Map<String, Object> param = new HashMap<>();
		List<SysMenuDTO> menus = sysMenuService.queryByCondition(param);
		List<SysMenuButtonDTO> buttons = sysMenuButtonService.queryByCondition(param);

		List<SysPostFunctionEntity> funcs = null;
		if (postId != null) {
			Map<String, Object> map = new HashMap<>();
			map.put("postId", postId);
			funcs = sysPostService.getFuncsByPost(map);
		}
		if (menus != null && menus.size() > 0) {
			for (SysMenuDTO menu : menus) {
				Map<String, Object> attr = new HashMap<>();
				attr.put("level", menu.getLevel());
				ZTreeNode treeNode = new ZTreeNode(Const.MENU_PRE + menu.getId(), menu.getName(), Const.MENU_PRE + menu.getPid());
				treeNode.setAttributs(attr);
				if (menu.getLevel() == 1) {
					treeNode.setOpen(true);
				}
				if (isNodeChecked(Const.MENU_PRE + menu.getId(), funcs)) {
					treeNode.setChecked(true);
				}
				nodeList.add(treeNode);
			}
		}
		if (buttons != null && buttons.size() > 0) {
			for (SysMenuButtonDTO button : buttons) {
				ZTreeNode treeNode = new ZTreeNode(Const.BUTTON_PRE + button.getId(), button.getName(), Const.MENU_PRE + button.getMenuId());
				if (isNodeChecked(Const.BUTTON_PRE + button.getId(), funcs)) {
					treeNode.setChecked(true);
				}
				nodeList.add(treeNode);
			}
		}
		return nodeList.toArray();
	}

	/**
	 * 生成功组织架构人员树
	 * 
	 * @return
	 * @throws BizException
	 * @author lidong dli@gdeng.cn
	 * @time 2016年10月23日 下午5:10:12
	 */
	protected Object[] buildOrgUserTree() throws BizException {
		SysOrgDTO baseNode = getUserGroup();// 当前管理员所在集团，作为树根节点开始遍历数据
		List<ZTreeNode> nodeList = new ArrayList<>();
		ZTreeNode root = new ZTreeNode(Const.ORG_PRE + baseNode.getId(), baseNode.getFullName(), null);
		nodeList.add(root);// 根节点
		Map<String, Object> param = new HashMap<>();
		param.put("status", 1);
		List<SysOrgDTO> allOrgs = sysOrgService.queryByCondition(param);
		if (allOrgs != null && allOrgs.size() != 0) {
			List<SysOrgDTO> orgs = getOrgsByPid(allOrgs, baseNode.getId());
			for (SysOrgDTO node : orgs) {
				ZTreeNode treeNode = new ZTreeNode(Const.ORG_PRE + node.getId(), node.getFullName(), Const.ORG_PRE + node.getParentId());
				nodeList.add(treeNode);
			}
		}
		Map<String, Object> map = new HashMap<>();
		map.put("groupId", getSysUser().getGroupId());// 用户所属集团
		map.put("status", 1);// 状态正常用户
		map.put("type", 2);// 组织类型，部门
		List<SysUserDTO> users = sysUserService.getList(map);
		if (users != null && users.size() != 0) {
			for (SysUserDTO user : users) {
				ZTreeNode treeNode = new ZTreeNode(Const.USER_PRE + user.getId(), user.getName(), Const.ORG_PRE + user.getDepartmentId());
				nodeList.add(treeNode);
			}
		}

		return nodeList.toArray();
	}

	/**
	 * 根据pid获取所有的组织，一直递归到最底层节点
	 * 
	 * @param orgs
	 * @param pid
	 * @return
	 * @author lidong dli@gdeng.cn
	 * @time 2016年10月14日 下午4:18:01
	 */
	protected List<SysOrgDTO> getOrgsByPid(List<SysOrgDTO> orgs, Integer pid) {
		List<SysOrgDTO> children = new ArrayList<>();
		if (orgs != null && orgs.size() > 0) {
			for (SysOrgDTO org : orgs) {
				if (org.getParentId() != null) {
					if (org.getParentId().intValue() == pid.intValue()) {
						children.add(org);
						List<SysOrgDTO> orgsSub = getOrgsByPid(orgs, org.getId());
						if (orgsSub.size() > 0) {
							for (SysOrgDTO sysOrgDTO : orgsSub) {
								children.add(sysOrgDTO);
							}
						}
					}
				}
			}
		}
		return children;
	}

	/**
	 * 根据pid获取下级组织列表
	 * 
	 * @param orgs
	 * @param pid
	 * @return
	 * @author lidong dli@gdeng.cn
	 * @time 2016年10月14日 上午11:30:52
	 */
	protected List<SysOrgDTO> getChildren(List<SysOrgDTO> orgs, Integer pid) {
		List<SysOrgDTO> children = new ArrayList<>();
		if (orgs != null && orgs.size() > 0) {
			for (SysOrgDTO org : orgs) {
				if (org.getParentId().intValue() == pid.intValue()) {
					children.add(org);
				}
			}
		}
		return children;
	}

	/**
	 * 根据组织类型筛选组织
	 * 
	 * @param orgs
	 * @param type
	 * @return
	 * @author lidong dli@gdeng.cn
	 * @time 2016年10月14日 上午11:37:27
	 */
	protected List<SysOrgDTO> getChildrenByType(List<SysOrgDTO> orgs, String type) {
		List<SysOrgDTO> children = new ArrayList<>();
		if (orgs != null && orgs.size() > 0) {
			for (SysOrgDTO org : orgs) {
				if (org.getType().equals(type)) {
					children.add(org);
				}
			}
		}
		return children;
	}

	/**
	 * 关闭掉市场下级部门的部门节点<br>
	 * 该方法为新增岗位选择部门专用
	 * 
	 * @param nodes
	 * @return
	 * @author lidong dli@gdeng.cn
	 * @time 2016年10月15日 上午10:16:57
	 */
	protected List<ZTreeNode> filter1(List<ZTreeNode> nodes) {
		List<ZTreeNode> zTreeNodes = new ArrayList<>();
		for (ZTreeNode node : nodes) {
			if (node != null) {
				ZTreeNode pNode = ZTreeUtil.getById(nodes, node.getpId());
				if (pNode != null) {
					Map<String, Object> attr = pNode.getAttributs();
					String orgType = (String) attr.get("orgType");// 组织类型
					if (orgType.equals(SysOrgEnum.TYPE.MARKET)) {
						node.setOpen(false);
					} else {
						node.setOpen(true);
					}
				} else {
					node.setOpen(true);
				}
			}
			zTreeNodes.add(node);
		}
		return ZTreeUtil.removeInvalidNode(nodes);// 移除掉无效节点之后，再返回
	}

	/**
	 * 检测岗位功能权限树指定id的节点是否被选中
	 * 
	 * @param id
	 *            指定节点的id
	 * @param funcs
	 *            该岗位所拥有的功能权限集合
	 * @return
	 * @author lidong dli@gdeng.cn
	 * @time 2016年10月17日 下午6:24:45
	 */
	protected boolean isNodeChecked(String nodeId, List<SysPostFunctionEntity> funcs) {
		if (funcs == null || funcs.size() == 0) {
			return false;
		}
		String[] funcIds = nodeId.split(Const.SPLIT_LINE);
		String funType = funcIds[0];// 功能类型,'权限类型 1菜单 2按钮',
		String id = funcIds[1];
		for (SysPostFunctionEntity func : funcs) {
			if (Const.MENU_PRE.contains(funType)) {
				if (func.getType().equals("1")) {
					if (func.getAuthId().longValue() == Long.valueOf(id).longValue()) {
						return true;
					}
				}
			} else if (Const.BUTTON_PRE.contains(funType)) {
				if (func.getType().equals("2")) {
					if (func.getAuthId().longValue() == Long.valueOf(id).longValue()) {
						return true;
					}
				}
			}
		}
		return false;
	}

	protected boolean isNodeCheckedForData(String nodeId, List<SysPostDataEntity> datas) {
		if (datas == null || datas.size() == 0) {
			return false;
		}
		String[] funcIds = nodeId.split(Const.SPLIT_LINE);
		String dataType = funcIds[0];// 数据权限类型,'权限类型 1公司 2市场 3资源类型',
		String id = funcIds[1];
		for (SysPostDataEntity data : datas) {
			if (Const.COMPANY_PRE.contains(dataType)) {
				if (data.getType().equals("1")) {
					if (data.getAuthId().longValue() == Long.valueOf(id).longValue()) {
						return true;
					}
				}
			} else if (Const.MARKET_PRE.contains(dataType)) {
				if (data.getType().equals("2")) {
					if (data.getAuthId().longValue() == Long.valueOf(id).longValue()) {
						return true;
					}
				}
			} else if (Const.SOURCE_PRE.contains(dataType)) {
				if (data.getType().equals("3")) {
					if (data.getAuthId().longValue() == Long.valueOf(id).longValue()) {
						return true;
					}
				}
			}
		}
		return false;
	}

}
