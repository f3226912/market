package cn.gdeng.market.dto.admin;

import java.util.List;

import cn.gdeng.market.dto.lease.settings.MarketResourceTypeDTO;
import cn.gdeng.market.entity.admin.SysUserEntity;

public class SysUserDTO extends SysUserEntity {

	private static final long serialVersionUID = -4360312311105259568L;

	/**
	 * 用户类型文字说明
	 */
	private String typeDesc;
	/**
	 * 状态动作文字说明
	 */
	private String statusAction;

	/**
	 * 状态显示文字说明
	 */
	private String statusDesc;

	/**
	 * 需要修改的状态
	 */
	private String updStatus;

	/**
	 * 用户ID 查询条件
	 */
	private String userId;

	/**
	 * 架构ID 查询条件
	 */
	private String orgId;

	/**
	 * 所属公司ID
	 */
	private Integer companyId;

	/**
	 * 所属公司名称
	 */
	private String companyName;

	/**
	 * 所属市场ID
	 */
	private Integer marketId;

	/**
	 * 所属市场名称
	 */
	private String marketName;

	/**
	 * 所属部门ID
	 */
	private Integer departmentId;

	/**
	 * 所属部门名称
	 */
	private String departmentName;

	/**
	 * 岗位ID
	 */
	private String postId;

	/**
	 * 岗位名称
	 */
	private String postName;

	/**
	 * 所属集团名称
	 */
	private String groupName;

	/**
	 * 用户组织
	 */
	private List<SysOrgDTO> orgs;

	/**
	 * 用户岗位
	 */
	private List<SysPostDTO> posts;

	/**
	 * 用户拥有的菜单
	 */
	private List<SysMenuDTO> menus;

	/**
	 * 用户所拥有的菜单按钮
	 */
	private List<SysMenuButtonDTO> buttons;

	/**
	 * 数据权限-公司
	 */
	private List<SysOrgDTO> pCompany;
	/**
	 * 数据权限-市场
	 */
	private List<SysOrgDTO> pMarket;

	/**
	 * 数据权限-资源类型
	 */
	private List<MarketResourceTypeDTO> pRes;

	/**
	 * 用户当前所选择的公司
	 */
	private SysOrgDTO currentCompany;
	/**
	 * 用户当前所选择的市场
	 */
	private SysOrgDTO currentMarket;

	/**
	 * 用户当前所选择的公司下的联动市场集合
	 */
	private List<SysOrgDTO> currentMarketList;

	public List<SysOrgDTO> getCurrentMarketList() {
		return currentMarketList;
	}

	public void setCurrentMarketList(List<SysOrgDTO> currentMarketList) {
		this.currentMarketList = currentMarketList;
	}

	public SysOrgDTO getCurrentCompany() {
		return currentCompany;
	}

	public void setCurrentCompany(SysOrgDTO currentCompany) {
		this.currentCompany = currentCompany;
	}

	public SysOrgDTO getCurrentMarket() {
		return currentMarket;
	}

	public void setCurrentMarket(SysOrgDTO currentMarket) {
		this.currentMarket = currentMarket;
	}

	public List<SysOrgDTO> getpCompany() {
		return pCompany;
	}

	public void setpCompany(List<SysOrgDTO> pCompany) {
		this.pCompany = pCompany;
	}

	public List<SysOrgDTO> getpMarket() {
		return pMarket;
	}

	public void setpMarket(List<SysOrgDTO> pMarket) {
		this.pMarket = pMarket;
	}

	public List<MarketResourceTypeDTO> getpRes() {
		return pRes;
	}

	public void setpRes(List<MarketResourceTypeDTO> pRes) {
		this.pRes = pRes;
	}

	public List<SysOrgDTO> getOrgs() {
		return orgs;
	}

	public void setOrgs(List<SysOrgDTO> orgs) {
		this.orgs = orgs;
	}

	public List<SysPostDTO> getPosts() {
		return posts;
	}

	public void setPosts(List<SysPostDTO> posts) {
		this.posts = posts;
	}

	public List<SysMenuDTO> getMenus() {
		return menus;
	}

	public void setMenus(List<SysMenuDTO> menus) {
		this.menus = menus;
	}

	public List<SysMenuButtonDTO> getButtons() {
		return buttons;
	}

	public void setButtons(List<SysMenuButtonDTO> buttons) {
		this.buttons = buttons;
	}

	public Integer getCompanyId() {
		return companyId;
	}

	public void setCompanyId(Integer companyId) {
		this.companyId = companyId;
	}

	public String getCompanyName() {
		return companyName;
	}

	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}

	public Integer getMarketId() {
		return marketId;
	}

	public void setMarketId(Integer marketId) {
		this.marketId = marketId;
	}

	public String getMarketName() {
		return marketName;
	}

	public void setMarketName(String marketName) {
		this.marketName = marketName;
	}

	public Integer getDepartmentId() {
		return departmentId;
	}

	public void setDepartmentId(Integer departmentId) {
		this.departmentId = departmentId;
	}

	public String getDepartmentName() {
		return departmentName;
	}

	public void setDepartmentName(String departmentName) {
		this.departmentName = departmentName;
	}

	public String getPostId() {
		return postId;
	}

	public void setPostId(String postId) {
		this.postId = postId;
	}

	public String getPostName() {
		return postName;
	}

	public void setPostName(String postName) {
		this.postName = postName;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public String getUserId() {
		if (this.userId == null || "".equals(this.userId)) {
			return this.getId() + "";
		}
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getOrgId() {
		return orgId;
	}

	public void setOrgId(String orgId) {
		this.orgId = orgId;
	}

	public String getStatusDesc() {
		return statusDesc;
	}

	public void setStatusDesc(String statusDesc) {
		this.statusDesc = statusDesc;
	}

	public String getUpdStatus() {
		return updStatus;
	}

	public void setUpdStatus(String updStatus) {
		this.updStatus = updStatus;
	}

	public String getStatusAction() {
		return statusAction;
	}

	public void setStatusAction(String statusAction) {
		this.statusAction = statusAction;
	}

	public String getGroupName() {
		return groupName;
	}

	public void setGroupName(String groupName) {
		this.groupName = groupName;
	}

	public String getTypeDesc() {
		return typeDesc;
	}

	public void setTypeDesc(String typeDesc) {
		this.typeDesc = typeDesc;
	}

}
