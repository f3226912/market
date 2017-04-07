package cn.gdeng.market.dto.admin;

import java.util.List;

import cn.gdeng.market.entity.admin.SysPostDataEntity;
import cn.gdeng.market.entity.admin.SysPostEntity;
import cn.gdeng.market.entity.admin.SysPostFunctionEntity;

public class SysPostDTO extends SysPostEntity {
	private static final long serialVersionUID = 4218687534697808170L;

	/**
	 * 岗位所属部门全称
	 */
	private String orgFullName;

	/**
	 * 岗位下分配的用户数
	 */
	private Integer userCount;

	/**
	 * 功能权限树提交的菜单、按钮混合结构字符串
	 */
	private String funcStr;
	/**
	 * 数据权限树提交的公司、市场、资源类型混合结构字符串
	 */
	private String dataStr;
	/**
	 * 根据funcStr字符串解析出来的功能权限集合
	 */
	private List<SysPostFunctionEntity> pfuncs;
	/**
	 * 根据dataStr字符串解析出来的数据权限集合
	 */
	private List<SysPostDataEntity> pdatas;

	public void setDataStr(String dataStr) {
		this.dataStr = dataStr;
	}

	public void setFuncStr(String funcStr) {
		this.funcStr = funcStr;
	}

	public List<SysPostFunctionEntity> getPfuncs() {
		return pfuncs;
	}

	public List<SysPostDataEntity> getPdatas() {
		return pdatas;
	}

	public void setPfuncs(List<SysPostFunctionEntity> pfuncs) {
		this.pfuncs = pfuncs;
	}

	public String getFuncStr() {
		return funcStr;
	}

	public String getDataStr() {
		return dataStr;
	}

	public void setPdatas(List<SysPostDataEntity> pdatas) {
		this.pdatas = pdatas;
	}

	public String getOrgFullName() {
		return orgFullName;
	}

	public void setOrgFullName(String orgFullName) {
		this.orgFullName = orgFullName;
	}

	public Integer getUserCount() {
		return userCount;
	}

	public void setUserCount(Integer userCount) {
		this.userCount = userCount;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}
}
