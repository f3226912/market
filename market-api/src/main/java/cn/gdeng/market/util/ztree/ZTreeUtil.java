package cn.gdeng.market.util.ztree;

import java.util.Iterator;
import java.util.List;

import cn.gdeng.market.dto.bean.ztree.ZTreeNode;

/**
 * ztree树工具类
 * 
 * @author lidong dli@gdeng.cn
 * @time 2016年10月15日 上午10:23:31
 */
public class ZTreeUtil {

	/**
	 * 从树中选出指定id的节点
	 * 
	 * @param nodes
	 * @param id
	 * @return
	 * @author lidong dli@gdeng.cn
	 * @time 2016年10月15日 上午10:23:27
	 */
	public static ZTreeNode getById(List<ZTreeNode> nodes, String id) {
		for (ZTreeNode zTreeNode : nodes) {
			if (zTreeNode.getId().equals(id)) {
				return zTreeNode;
			}
		}
		return null;
	}

	/**
	 * 根据id移除树中指定节点
	 * 
	 * @param nodes
	 * @param id
	 *            需要移除的节点id
	 * @return
	 * @author lidong dli@gdeng.cn
	 * @time 2016年10月15日 上午10:24:54
	 */
	public static List<ZTreeNode> removeById(List<ZTreeNode> nodes, String id) {
		for (Iterator<ZTreeNode> it = nodes.iterator(); it.hasNext();) {
			ZTreeNode node = (ZTreeNode) it.next();
			if (node.getId().equals(id)) {
				it.remove();
			}
		}
		return nodes;
	}

	/**
	 * 移除树中节点有pid值，而父节点不存在的节点
	 * 
	 * @param nodes
	 *            树
	 * @return
	 * @author lidong dli@gdeng.cn
	 * @time 2016年10月15日 上午10:29:33
	 */
	public static List<ZTreeNode> removeInvalidNode(List<ZTreeNode> nodes) {
		for (Iterator<ZTreeNode> it = nodes.iterator(); it.hasNext();) {
			ZTreeNode node = (ZTreeNode) it.next();
			if (node.getpId() != null) {
				ZTreeNode pNode = getById(nodes, node.getpId());
				if (pNode == null) {
					it.remove();
				}
			}
		}
		return nodes;
	}

}
