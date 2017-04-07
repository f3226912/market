package cn.gdeng.market.entity.admin;

import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

@Entity(name = "sys_user")
public class SysUserEntity implements java.io.Serializable {
	private static final long serialVersionUID = -2288398675171695419L;

	/**
	 * 用户ID
	 */
	private Integer id;

	/**
	 * 帐号
	 */
	private String code;

	/**
	 * 用户名称
	 */
	private String name;

	/**
	 * 密码
	 */
	private String pwd;

	/**
	 * 手机
	 */
	private String mobile;
	
	/**
	 * 工号
	 */
	private String deptNo;

	/**
	 * 座机
	 */
	private String landline;

	/**
	 * 邮箱
	 */
	private String mail;

	/**
	 * 输入密码错误次数
	 */
	private Integer pwdErrorTimes;

	/**
	 * 最后错误时间
	 */
	private Date lastErrorTime;

	/**
	 * 用户类型 1 平台用户 2公司管理员 3 普通用户
	 */
	private String type;

	/**
	 * 用户状态(0:注销、删除，1：正常，2：锁定 3:禁用)
	 */
	private String status;
	
	/**
	 * 所属集团Id
	 */
	private Integer groupId;

	/**
	 *
	 */
	private Date createTime;

	/**
	 *
	 */
	private String createUserId;

	/**
	 *
	 */
	private Date updateTime;

	/**
	 *
	 */
	private String updateUserId;

	@Id
	@Column(name = "id")
	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}
	
	@Column(name = "groupId")
	public Integer getGroupId() {
		return groupId;
	}

	public void setGroupId(Integer groupId) {
		this.groupId = groupId;
	}

	@Column(name = "deptNo")
	public String getDeptNo() {
		return deptNo;
	}

	public void setDeptNo(String deptNo) {
		this.deptNo = deptNo;
	}

	@Column(name = "code")
	public String getCode() {
		return this.code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	@Column(name = "name")
	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Column(name = "pwd")
	public String getPwd() {
		return this.pwd;
	}

	public void setPwd(String pwd) {
		this.pwd = pwd;
	}

	@Column(name = "mobile")
	public String getMobile() {
		return this.mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	@Column(name = "landline")
	public String getLandline() {
		return this.landline;
	}

	public void setLandline(String landline) {
		this.landline = landline;
	}

	@Column(name = "mail")
	public String getMail() {
		return this.mail;
	}

	public void setMail(String mail) {
		this.mail = mail;
	}

	@Column(name = "pwdErrorTimes")
	public Integer getPwdErrorTimes() {
		return this.pwdErrorTimes;
	}

	public void setPwdErrorTimes(Integer pwdErrorTimes) {
		this.pwdErrorTimes = pwdErrorTimes;
	}

	@Column(name = "lastErrorTime")
	public Date getLastErrorTime() {
		return this.lastErrorTime;
	}

	public void setLastErrorTime(Date lastErrorTime) {
		this.lastErrorTime = lastErrorTime;
	}

	@Column(name = "type")
	public String getType() {
		return this.type;
	}

	public void setType(String type) {
		this.type = type;
	}

	@Column(name = "status")
	public String getStatus() {
		return this.status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	@Column(name = "createTime")
	public Date getCreateTime() {
		return this.createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	@Column(name = "createUserId")
	public String getCreateUserId() {
		return this.createUserId;
	}

	public void setCreateUserId(String createUserId) {
		this.createUserId = createUserId;
	}

	@Column(name = "updateTime")
	public Date getUpdateTime() {
		return this.updateTime;
	}

	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}

	@Column(name = "updateUserId")
	public String getUpdateUserId() {
		return this.updateUserId;
	}

	public void setUpdateUserId(String updateUserId) {
		this.updateUserId = updateUserId;
	}

}
