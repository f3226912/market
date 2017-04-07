package cn.gdeng.market.entity.lease.settings;

import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

@Entity(name = "market_measure_setting")
public class MarketMeasureSettingEntity implements java.io.Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 2841719561777062922L;

	/**
	*
	*/
	private Integer id;

	/**
	 * 计量分类id
	 */
	private Integer measureTypeId;

	/**
	 * 费项IDid
	 */
	private Integer expId;

	/**
	 * 资源id
	 */
	private Integer resourceId;

	/**
	 * 计量表名称
	 */
	private String name;

	/**
	 * 计量表编码
	 */
	private String code;

	/**
	 * 最大读数
	 */
	private Double maxVal;

	/**
	 * 状态: 默认 1 启用 0 不启用
	 */
	private Integer status;

	/**
	*
	*/
	private String createUserId;

	/**
	*
	*/
	private Date createTime;

	/**
	*
	*/
	private String updateUserId;

	/**
	*
	*/
	private Date updateTime;


	/**
	 * 是否删除(0:未删除;1:已删除)
	 */
	private int isDeleted;

	@Id
	@Column(name = "id")
	public Integer getId() {

		return this.id;
	}

	public void setId(Integer id) {

		this.id = id;
	}

	@Column(name = "measureTypeId")
	public Integer getMeasureTypeId() {

		return this.measureTypeId;
	}

	public void setMeasureTypeId(Integer measureTypeId) {

		this.measureTypeId = measureTypeId;
	}

	@Column(name = "expId")
	public Integer getExpId() {

		return this.expId;
	}

	public void setExpId(Integer expId) {

		this.expId = expId;
	}

	@Column(name = "resourceId")
	public Integer getResourceId() {

		return this.resourceId;
	}

	public void setResourceId(Integer resourceId) {

		this.resourceId = resourceId;
	}

	@Column(name = "name")
	public String getName() {

		return this.name;
	}

	public void setName(String name) {

		this.name = name;
	}

	@Column(name = "code")
	public String getCode() {

		return this.code;
	}

	public void setCode(String code) {

		this.code = code;
	}

	@Column(name = "maxVal")
	public Double getMaxVal() {

		return this.maxVal;
	}

	public void setMaxVal(Double maxVal) {

		this.maxVal = maxVal;
	}

	@Column(name = "status")
	public Integer getStatus() {

		return this.status;
	}

	public void setStatus(Integer status) {

		this.status = status;
	}

	@Column(name = "createUserId")
	public String getCreateUserId() {

		return this.createUserId;
	}

	public void setCreateUserId(String createUserId) {

		this.createUserId = createUserId;
	}

	@Column(name = "createTime")
	public Date getCreateTime() {

		return this.createTime;
	}

	public void setCreateTime(Date createTime) {

		this.createTime = createTime;
	}

	@Column(name = "updateUserId")
	public String getUpdateUserId() {

		return this.updateUserId;
	}

	public void setUpdateUserId(String updateUserId) {

		this.updateUserId = updateUserId;
	}

	@Column(name = "updateTime")
	public Date getUpdateTime() {

		return this.updateTime;
	}

	public void setUpdateTime(Date updateTime) {

		this.updateTime = updateTime;
	}

	@Column(name = "isDeleted")
	public int getIsDeleted() {
		return isDeleted;
	}

	public void setIsDeleted(int isDeleted) {
		this.isDeleted = isDeleted;
	}

}
