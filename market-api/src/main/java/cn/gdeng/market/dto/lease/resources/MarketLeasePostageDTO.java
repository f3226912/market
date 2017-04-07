package cn.gdeng.market.dto.lease.resources;

import java.io.Serializable;
import java.util.Date;

import cn.gdeng.market.entity.lease.resources.MarketLeasePostageEntity;

public class MarketLeasePostageDTO extends MarketLeasePostageEntity implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 8324586821407274876L;
	
	/**
	 * 合同编号
	 */
	private String contractNo;
	
	/**
     *合同状态 0 待执行 1 执行中 2 已结算
     */
    private Byte contractStatus;
    
    /**
     * 商户
     */
    private String customerName;
    
    /**
	 * 资源类型名称
	 */
	private String resourceTypeName;
    
    /**
     *起租日期
     */
    private Date startLeasingDay;
    
    /**
     *创建时间(开业日期)
     */
    private Date contractCreateTime;
    
    /**
     *结束日期
     */
    private Date endLeasingDay;
    
    /**
     *签约日期
     */
    private Date dateOfContract;
    
    /**
     *经办人
     */
    private String trustees;

	public Byte getContractStatus() {
		return contractStatus;
	}

	public void setContractStatus(Byte contractStatus) {
		this.contractStatus = contractStatus;
	}

	public String getCustomerName() {
		return customerName;
	}

	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}

	public String getResourceTypeName() {
		return resourceTypeName;
	}

	public void setResourceTypeName(String resourceTypeName) {
		this.resourceTypeName = resourceTypeName;
	}

	public Date getStartLeasingDay() {
		return startLeasingDay;
	}

	public void setStartLeasingDay(Date startLeasingDay) {
		this.startLeasingDay = startLeasingDay;
	}

	public Date getContractCreateTime() {
		return contractCreateTime;
	}

	public void setContractCreateTime(Date contractCreateTime) {
		this.contractCreateTime = contractCreateTime;
	}

	public Date getEndLeasingDay() {
		return endLeasingDay;
	}

	public void setEndLeasingDay(Date endLeasingDay) {
		this.endLeasingDay = endLeasingDay;
	}

	public Date getDateOfContract() {
		return dateOfContract;
	}

	public void setDateOfContract(Date dateOfContract) {
		this.dateOfContract = dateOfContract;
	}

	public String getTrustees() {
		return trustees;
	}

	public void setTrustees(String trustees) {
		this.trustees = trustees;
	}

	public String getContractNo() {
		return contractNo;
	}

	public void setContractNo(String contractNo) {
		this.contractNo = contractNo;
	}
    
}
