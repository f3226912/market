package cn.gdeng.market.dto.bean;

import java.io.Serializable;
import java.util.List;

/**
 * 树的节点
 * @author sss
 *
 */
public class Node implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * 节点ID
	 */
	private String id;
	
	/**
	 * 父节点ID
	 */
	private String pId;
	
	/**
	 * 节点类型
	 */
	private String type;
	
	/**
	 * 节点名称
	 */
	private String name;
	
	/**
	 * 是否展开当前节点
	 */
	private boolean open;
	
	/**
	 * 全部当父节点
	 */
	private boolean isParent;
	
	/**
	 * 子节点集合
	 */
	public List<Node> children;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getPId() {
		return pId;
	}

	public void setPId(String pId) {
		this.pId = pId;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public boolean isOpen() {
		return open;
	}

	public void setOpen(boolean open) {
		this.open = open;
	}

	public List<Node> getChildren() {
		return children;
	}

	public void setChildren(List<Node> children) {
		this.children = children;
	}

	public boolean getIsParent() {
		return isParent;
	}

	public void setParent(boolean isParent) {
		this.isParent = isParent;
	}
}
