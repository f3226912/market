package cn.gdeng.market.dto.lease.contract;

import java.io.Serializable;

import cn.gdeng.market.entity.lease.contract.ContractMainEntity;
import cn.gdeng.market.enums.ApprovalStatusEnum;
import cn.gdeng.market.enums.ContractBillingAreaEnum;
import cn.gdeng.market.enums.ContractChargingWaysEnum;
import cn.gdeng.market.enums.ContractPaymentCycleEnum;
import cn.gdeng.market.enums.ContractPaymentDayTypeEnum;
import cn.gdeng.market.enums.ContractStatusEnum;
import cn.gdeng.market.util.DateUtil;

public class ContractMainDTO extends ContractMainEntity implements Serializable {

	private static final long serialVersionUID = -294220833881650738L;
	
	/**
     *修改人员
     */
	private String updateUserName;
	
	 /**
     *创建人员
     */
    private String createUserId;

	/**
	 * 审核结果状态
	 */
	private String approvalStatusValue;

	/**
	 * 合同状态
	 */
	private String contractStatusValue;
	/**
	 * 合同面积
	 */
	private String contractArea;
	
	/**
	 * 起始日期字符串字面值
	 */
	private String startLeasingDayString ;
	
	/**
	 * 结束日期字符串字面值
	 */
	private String endLeasingDayString;
	/**
	 * 签约日期
	 */
	private String dateOfContractString;
	
	private String leasingForfeitString;
	
	private String shopForfeitString;
	
	//private String categoryName;
	
	/**
	 * 免租总天数
	 */
	private Integer freeDays;
	
	/**
	 * 合同变更原因
	 */
	private Byte changeReasion;
	
	/**
	 * 结算类型
	 */
	private Byte statementsType;
	
	/**
	 * 退还保证金
	 */
	private Double deposit;
	
	/**
	 * 罚款金额
	 */
	private Double forfeit;
	
	/**
	 * 合同保证金
	 */
	private String contractDeposit;
	
	public String getLeasingForfeitString() {
		return leasingForfeitString;
	}

	public void setLeasingForfeitString(String leasingForfeitString) {
		this.leasingForfeitString = leasingForfeitString;
	}

	public String getShopForfeitString() {
		return shopForfeitString;
	}

	public void setShopForfeitString(String shopForfeitString) {
		this.shopForfeitString = shopForfeitString;
	}
	
	public String getStartLeasingDayString() {
		if(null!=getStartLeasingDay()){
		startLeasingDayString=DateUtil.toString(getStartLeasingDay(), DateUtil.DATE_FORMAT_DATEONLY);
		}
		return startLeasingDayString;
	}

	public void setStartLeasingDayString(String startLeasingDayString) {
		this.startLeasingDayString = startLeasingDayString;
	}

	public String getEndLeasingDayString() {
		if(null!=getEndLeasingDay()){
			endLeasingDayString=DateUtil.toString(getEndLeasingDay(), DateUtil.DATE_FORMAT_DATEONLY);
			}
		return endLeasingDayString;
	}

	public void setEndLeasingDayString(String endLeasingDayString) {
		this.endLeasingDayString = endLeasingDayString;
	}
	

	public String getDateOfContractString() {
		if(null!=getDateOfContract()){
			dateOfContractString=DateUtil.toString(getDateOfContract(), DateUtil.DATE_FORMAT_DATEONLY);
			}
		return dateOfContractString;
	}

	public void setDateOfContractString(String dateOfContractString) {
		this.dateOfContractString = dateOfContractString;
	}
	

	/**
	 * 
	 * @作者 XieZhongGuo
	 * @创建时间 2016年10月12日
	 * @方法说明 根据审核状态码 返回审核结果
	 * @版本 V1.0
	 * @return
	 */
	public String getApprovalStatusValue() {
		return ApprovalStatusEnum.getNameByCode(getApprovalStatus());
	}

	public void setApprovalStatusValue(String approvalStatusValue) {
		this.approvalStatusValue = approvalStatusValue;
	}

	/**
	 * @author KWANG
	 * 根据合同状态码 返回合同状态结果
	 * 根据合同状态码 返回合同状态结果
	 * @return String
	 */
	public String getContractStatusValue() {
		return ContractStatusEnum.getNameByCode(getContractStatus());
	}

	public void setContractStatusValue(String contractStatusValue) {
		this.contractStatusValue = contractStatusValue;
	}


	public String getUpdateUserName() {
		return updateUserName;
	}

	public void setUpdateUserName(String updateUserName) {
		this.updateUserName = updateUserName;
	}

	public String getCreateUserId() {
		return createUserId;
	}

	public void setCreateUserId(String createUserId) {
		this.createUserId = createUserId;
	}

	public String getContractArea() {
		return contractArea;
	}

	public void setContractArea(String contractArea) {
		this.contractArea = contractArea;
	}
	
	/*public String getCategoryName() {
		return categoryName;
	}

	public void setCategoryName(String categoryName) {
		this.categoryName = categoryName;
	}*/

	public String getChargingWaysString(){
		return ContractChargingWaysEnum.getNameByCode(getChargingWays());
	}
	
	public String getBillAreaString(){
		return ContractBillingAreaEnum.getNameByCode(getBillingArea());
	}
	
	public String getPaymentCycleString(){
		return ContractPaymentCycleEnum.getNameByCode(getPaymentCycle());
	}
	
	public String getPaymentDayTypeString(){
		return ContractPaymentDayTypeEnum.getNameByCode(getPaymentDayType());
	}

	public Integer getFreeDays() {
		return freeDays;
	}

	public void setFreeDays(Integer freeDays) {
		this.freeDays = freeDays;
	}

	public Byte getChangeReasion() {
		return changeReasion;
	}

	public void setChangeReasion(Byte changeReasion) {
		this.changeReasion = changeReasion;
	}

	public Byte getStatementsType() {
		return statementsType;
	}

	public void setStatementsType(Byte statementsType) {
		this.statementsType = statementsType;
	}

	public Double getDeposit() {
		return deposit;
	}

	public void setDeposit(Double deposit) {
		this.deposit = deposit;
	}

	public Double getForfeit() {
		return forfeit;
	}

	public void setForfeit(Double forfeit) {
		this.forfeit = forfeit;
	}

	public String getContractDeposit() {
		return contractDeposit;
	}

	public void setContractDeposit(String contractDeposit) {
		this.contractDeposit = contractDeposit;
	}

}
