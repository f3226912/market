
package cn.gdeng.market.dto.bean.ztree;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * zTree树节点数据结构
 * 
 * @author lidong dli@gdeng.cn
 * @time 2016年10月14日 下午2:02:47
 */
public class ZTreeNode implements Serializable {
	private static final long serialVersionUID = -5460419137197036175L;
	/** 节点id. */
	private String id;
	/** 节点名称. */
	private String name;
	/** 设置节点的 checkbox / radio 是否禁用. */
	private boolean chkDisabled;
	/** 父节点id. */
	private String pId;
	/** 图标. */
	private String icon;
	/**
	 * 父节点自定义展开时图标的 URL 路径
	 */
	private String iconOpen;
	/**
	 * 父节点自定义折叠时图标的 URL 路径
	 */
	private String iconClose;
	/**
	 * 节点自定义图标的 className
	 */
	private String iconSkin;
	/**
	 * 节点的 checkBox / radio 的 勾选状态
	 */
	private boolean checked;
	/**
	 * 强制节点的 checkBox / radio 的 半勾选状态。
	 */
	private boolean halfCheck;
	/**
	 * 设置节点是否隐藏 checkbox / radio
	 */
	private boolean nocheck;
	/**
	 * 设置点击节点后在何处打开 url。
	 */
	private String target;
	/**
	 * 最简单的 click 事件操作。相当于 onclick="..." 的内容。
	 */
	private String click;
	/** 节点访问路径. */
	private String url;
	private boolean open;
	private boolean isParent;

	private Map<String, Object> attributs;

	private List<ZTreeNode> children = new ArrayList<ZTreeNode>();

	public ZTreeNode() {
	}

	public ZTreeNode(String id, String text) {
		setId(id);
		setName(text);
	}

	public ZTreeNode(String id, String text, String fatherId) {
		setId(id);
		setName(text);
		setpId(fatherId);
	}

	public ZTreeNode(Integer id, String text, Integer fatherId) {
		setId(String.valueOf(id));
		setName(text);
		if (fatherId != null) {
			setpId(String.valueOf(fatherId));
		}
	}

	public ZTreeNode(String id, String text, String fatherId, String icon) {
		setId(id);
		setName(text);
		setpId(fatherId);
		setIcon(icon);
	}

	public ZTreeNode(String id, String text, String fatherId, String icon, String url) {
		setId(id);
		setName(text);
		setpId(fatherId);
		setIcon(icon);
		setUrl(url);
	}

	public boolean isChkDisabled() {
		return chkDisabled;
	}

	public void setChkDisabled(boolean chkDisabled) {
		this.chkDisabled = chkDisabled;
	}

	public String getIconOpen() {
		return iconOpen;
	}

	public void setIconOpen(String iconOpen) {
		this.iconOpen = iconOpen;
	}

	public String getIconClose() {
		return iconClose;
	}

	public void setIconClose(String iconClose) {
		this.iconClose = iconClose;
	}

	public String getIconSkin() {
		return iconSkin;
	}

	public void setIconSkin(String iconSkin) {
		this.iconSkin = iconSkin;
	}

	public boolean isChecked() {
		return checked;
	}

	public void setChecked(boolean checked) {
		this.checked = checked;
	}

	public boolean isHalfCheck() {
		return halfCheck;
	}

	public void setHalfCheck(boolean halfCheck) {
		this.halfCheck = halfCheck;
	}

	public boolean isNocheck() {
		return nocheck;
	}

	public void setNocheck(boolean nocheck) {
		this.nocheck = nocheck;
	}

	public String getTarget() {
		return target;
	}

	public void setTarget(String target) {
		this.target = target;
	}

	public String getClick() {
		return click;
	}

	public void setClick(String click) {
		this.click = click;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getpId() {
		return pId;
	}

	public void setpId(String pId) {
		this.pId = pId;
	}

	public String getIcon() {
		return icon;
	}

	public void setIcon(String icon) {
		this.icon = icon;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public boolean isOpen() {
		return open;
	}

	public void setOpen(boolean open) {
		this.open = open;
	}

	public Map<String, Object> getAttributs() {
		return attributs;
	}

	public void setAttributs(Map<String, Object> attributs) {
		this.attributs = attributs;
	}

	public List<ZTreeNode> getChildren() {
		return children;
	}

	public void setChildren(List<ZTreeNode> children) {
		this.children = children;
	}

	public boolean isParent() {
		return isParent;
	}

	public void setParent(boolean isParent) {
		this.isParent = isParent;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

}
