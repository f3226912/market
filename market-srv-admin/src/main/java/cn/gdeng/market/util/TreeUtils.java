package cn.gdeng.market.util;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;

import cn.gdeng.market.dto.bean.Node;
import cn.gdeng.market.entity.admin.SysOrgEntity;

public class TreeUtils {

	/**
	 * 组织树
	 * @param org
	 * @return
	 */
	public static Node convertNode(SysOrgEntity org){
		Node node = new Node();
		node.setId(org.getId()+"");
		node.setName(org.getFullName());
		node.setOpen(false);
		node.setPId(org.getParentId()+"");
		node.setType(org.getType());
		return node;
	}
	
	public static List<Node> convertNode(List<SysOrgEntity> list){
		List<Node> nodes = new ArrayList<>();
		if(null != list){
			for(SysOrgEntity org : list){
				Node node = convertNode(org);
				nodes.add(node);
			}
		}
		
		return nodes;
	}
	
	public static List<Node> buildTree(List<Node> list,String nodeId){
		List<Node> nodes = new ArrayList<>();
		for(Node node : list){
			if(StringUtils.equals(nodeId, node.getPId())){
				nodes.add(node);
				List<Node> children = buildTree(list, node.getId());
				node.setChildren(children);
			}
		}
		
		return nodes;
	}
}
