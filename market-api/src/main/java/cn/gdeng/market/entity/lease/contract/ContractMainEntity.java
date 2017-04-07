package cn.gdeng.market.entity.lease.contract;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

import cn.gdeng.market.enums.ContractBillingAreaEnum;

@Entity(name = "contract_main")
public class ContractMainEntity  implements java.io.Serializable{
    /**
	 * 
	 */
	private static final long serialVersionUID = 1499638034013929754L;

	/**
     *主键id
     */
    private Integer id;

     *市场id
     */
    private Integer marketId;

     *租赁资源(中文描述)
     */
    private String leasingResource;

     *租赁资源ID(,逗号隔开)
     */
    private String leasingResourceIds;

     *市场资源类型表ID
     */
    private Integer marketResourceTypeId;

     *客户名称
     */
    private String customerName;

     *客户手机号
     */
    private String customerMobile;

     *合同编号
     */
    private String contractNo;

     *甲方
     */
    private String partyA;

     *乙方
     */
    private String partyB;

     *起租日期
     */
    private Date startLeasingDay;

     *结束日期
     */
    private Date endLeasingDay;

     *延迟交租罚金(元/日)
     */
    private Double leasingForfeit;

     *延迟还铺罚金(元/日)
     */
    private Double shopForfeit;

     *商铺名称
     */
    private String shopName;

     *经营范围 (产品分类表)
     */
    private Long categoryId;

     *经营范围(产品分类名称)
     */
    private String categoryName;

     *可租面积
     */
    private Double freeArea;

     *建筑面积
     */
    private Double floorArea;

     *套内面积
     */
    private Double innerArea;

     *经办人
     */
    private String trustees;

     *签约日期
     */
    private Date dateOfContract;

     *收费方式 0 按周期收费 1 约定总金额
     */
    private Byte chargingWays;

     *合同总金额
     */
    private Double totalAmt;

     *计费面积 0 可租面积 1 建筑面积 2 套内面积
     */
    private Byte billingArea=0;

     *已租面积(BI统计使用,根据计费面积同步)
     */
    private Double countArea;

     *缴费周期 0 月 1 季度 2 半年 3 年
     */
    private Byte paymentCycle;

     *缴费日期 0 缴费期初 1 缴费期未 2 下一个缴费期初
     */
    private Byte paymentDayType;

     *缴费日期(天)
     */
    private Byte paymentDays;

     *附加条款
     */
    private String additionalTerms;

     *合同状态 0 待执行 1 执行中 2 已结算
     */
    private Byte contractStatus;

     *审批状态 0 待审批 1 审批中 2 已驳回 3 已通过
     */
    private Byte approvalStatus;

     *审批类型 1 合同初次审批 2 合同变更审批 3 合同结算审批
     */
    private Byte approvalType;

     *审批方式 1 工作流审批 2 线下审批
     */
    private Byte approvalMethod;

     *合同初次审批时间
     */
    private Date approvalTimeA;

     *合同变更审批时间
     */
    private Date approvalTimeB;

     *合同结算审批时间
     */
    private Date approvalTimeC;

     *是否作废(0:未作废;1:已作废)
     */
    private Byte isCancel;

     *是否删除(0:未删除;1:已删除)
     */
    private Byte isDeleted;

     *创建人员ID
     */
    private String createUserId;

     *创建时间
     */
    private Date createTime;

     *修改人员ID
     */
    private String updateUserId;

     *修改时间
     */
    private Date updateTime;

     *版本控制(乐观锁)
     */
    private Integer version;
   
    /**
     * '定时任务处理状态 0 未处理 1 处理成功 2 处理失败'
     */
    private Integer taskStatus;

    @Column(name = "id")
    public Integer getId(){

    }
    public void setId(Integer id){

    }
	@Column(name = "taskStatus")
    public Integer getTaskStatus() {
		return taskStatus;
	}
	public void setTaskStatus(Integer taskStatus) {
		this.taskStatus = taskStatus;
	}
	@Column(name = "marketId")
    public Integer getMarketId(){

    }
    public void setMarketId(Integer marketId){

    }
    @Column(name = "leasingResource")
    public String getLeasingResource(){

    }
    public void setLeasingResource(String leasingResource){

    }
    @Column(name = "leasingResourceIds")
    public String getLeasingResourceIds(){

    }
    public void setLeasingResourceIds(String leasingResourceIds){

    }
    @Column(name = "marketResourceTypeId")
    public Integer getMarketResourceTypeId(){

    }
    public void setMarketResourceTypeId(Integer marketResourceTypeId){

    }
    @Column(name = "customerName")
    public String getCustomerName(){

    }
    public void setCustomerName(String customerName){

    }
    @Column(name = "customerMobile")
    public String getCustomerMobile(){

    }
    public void setCustomerMobile(String customerMobile){

    }
    @Column(name = "contractNo")
    public String getContractNo(){

    }
    public void setContractNo(String contractNo){

    }
    @Column(name = "partyA")
    public String getPartyA(){

    }
    public void setPartyA(String partyA){

    }
    @Column(name = "partyB")
    public String getPartyB(){

    }
    public void setPartyB(String partyB){

    }
    @Column(name = "startLeasingDay")
    public Date getStartLeasingDay(){

    }
    public void setStartLeasingDay(Date startLeasingDay){

    }
    @Column(name = "endLeasingDay")
    public Date getEndLeasingDay(){

    }
    public void setEndLeasingDay(Date endLeasingDay){

    }
    @Column(name = "leasingForfeit")
    public Double getLeasingForfeit(){

    }
    public void setLeasingForfeit(Double leasingForfeit){

    }
    @Column(name = "shopForfeit")
    public Double getShopForfeit(){

    }
    public void setShopForfeit(Double shopForfeit){

    }
    @Column(name = "shopName")
    public String getShopName(){

    }
    public void setShopName(String shopName){

    }
    @Column(name = "categoryId")
    public Long getCategoryId(){

    }
    public void setCategoryId(Long categoryId){

    }
    @Column(name = "categoryName")
    public String getCategoryName(){

    }
    public void setCategoryName(String categoryName){

    }
    @Column(name = "freeArea")
    public Double getFreeArea(){

    }
    public void setFreeArea(Double freeArea){

    }
    @Column(name = "floorArea")
    public Double getFloorArea(){

    }
    public void setFloorArea(Double floorArea){

    }
    @Column(name = "innerArea")
    public Double getInnerArea(){

    }
    public void setInnerArea(Double innerArea){

    }
    @Column(name = "trustees")
    public String getTrustees(){

    }
    public void setTrustees(String trustees){

    }
    @Column(name = "dateOfContract")
    public Date getDateOfContract(){

    }
    public void setDateOfContract(Date dateOfContract){

    }
    @Column(name = "chargingWays")
    public Byte getChargingWays(){

    }
    public void setChargingWays(Byte chargingWays){

    }
    @Column(name = "totalAmt")
    public Double getTotalAmt(){

    }
    public void setTotalAmt(Double totalAmt){

    }
    @Column(name = "billingArea")
    public Byte getBillingArea(){

    }
    public void setBillingArea(Byte billingArea){

    }
    @Column(name = "countArea")
    public Double getCountArea(){
    	if(null!=getBillingArea()){
		if(getBillingArea().equals(ContractBillingAreaEnum.FREE_AREA.getCode())){
			countArea=	getFreeArea();
		    	}
		if(getBillingArea().equals(ContractBillingAreaEnum.FLOOR_AREA.getCode())){
			countArea=	getFloorArea();
    	}
		if(getBillingArea().equals(ContractBillingAreaEnum.INNER_AREA.getCode())){
			countArea=	getInnerArea();
		}
		}else{
			countArea=getFreeArea();
		}
        return this.countArea;
    }
    public void setCountArea(Double countArea){

    }
    @Column(name = "paymentCycle")
    public Byte getPaymentCycle(){

    }
    public void setPaymentCycle(Byte paymentCycle){

    }
    @Column(name = "paymentDayType")
    public Byte getPaymentDayType(){

    }
    public void setPaymentDayType(Byte paymentDayType){

    }
    @Column(name = "paymentDays")
    public Byte getPaymentDays(){

    }
    public void setPaymentDays(Byte paymentDays){

    }
    @Column(name = "additionalTerms")
    public String getAdditionalTerms(){

    }
    public void setAdditionalTerms(String additionalTerms){

    }
    @Column(name = "contractStatus")
    public Byte getContractStatus(){

    }
    public void setContractStatus(Byte contractStatus){

    }
    @Column(name = "approvalStatus")
    public Byte getApprovalStatus(){

    }
    public void setApprovalStatus(Byte approvalStatus){

    }
    @Column(name = "approvalType")
    public Byte getApprovalType(){

    }
    public void setApprovalType(Byte approvalType){

    }
    @Column(name = "approvalMethod")
    public Byte getApprovalMethod(){

    }
    public void setApprovalMethod(Byte approvalMethod){

    }
    @Column(name = "approvalTimeA")
    public Date getApprovalTimeA(){

    }
    public void setApprovalTimeA(Date approvalTimeA){

    }
    @Column(name = "approvalTimeB")
    public Date getApprovalTimeB(){

    }
    public void setApprovalTimeB(Date approvalTimeB){

    }
    @Column(name = "approvalTimeC")
    public Date getApprovalTimeC(){

    }
    public void setApprovalTimeC(Date approvalTimeC){

    }
    @Column(name = "isCancel")
    public Byte getIsCancel(){

    }
    public void setIsCancel(Byte isCancel){

    }
    @Column(name = "isDeleted")
    public Byte getIsDeleted(){

    }
    public void setIsDeleted(Byte isDeleted){

    }
    @Column(name = "createUserId")
    public String getCreateUserId(){

    }
    public void setCreateUserId(String createUserId){

    }
    @Column(name = "createTime")
    public Date getCreateTime(){

    }
    public void setCreateTime(Date createTime){

    }
    @Column(name = "updateUserId")
    public String getUpdateUserId(){

    }
    public void setUpdateUserId(String updateUserId){

    }
    @Column(name = "updateTime")
    public Date getUpdateTime(){

    }
    public void setUpdateTime(Date updateTime){

    }
    @Column(name = "version")
    public Integer getVersion(){

    }
    public void setVersion(Integer version){

    }
}