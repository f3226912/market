package cn.gdeng.market.entity.lease.contract;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
/**
 * 免租约定
 * @author Administrator
 *
 */
@Entity(name = "contract_free")
public class ContractFreeEntity  implements java.io.Serializable{
    /**
	 * 
	 */
	private static final long serialVersionUID = -6428987222146797191L;

	/**
     *主键id
     */
    private Integer id;
    /**
     *合同ID
     */
    private Integer contractId;
    /**
     *合同编号
     */
    private String contractNo;
    /**
     *起始日期
     */
    private Date startDay;
    /**
     *截止日期
     */
    private Date endDay;
    /**
     *免租月数
     */
    private Byte freeMonths;
    /**
     *免租天数
     */
    private Byte freeDays;
    /**
     *是否删除(0:未删除;1:已删除)
     */
    private Byte isDeleted;
    /**
     *创建人员ID
     */
    private String createUserId;
    /**
     *创建时间
     */
    private Date createTime;
    /**
     *修改人员ID
     */
    private String updateUserId;
    /**
     *修改时间
     */
    private Date updateTime;
    /**
     *起始日期 json 转换代替
     */
    private String startDayStr;
    /**
     *截止日期json 转换代替
     */
    private String endDayStr;
	public String getStartDayStr() {
		return startDayStr;
	}
	public void setStartDayStr(String startDayStr) {
		this.startDayStr = startDayStr;
	}
	public String getEndDayStr() {
		return endDayStr;
	}
	public void setEndDayStr(String endDayStr) {
		this.endDayStr = endDayStr;
	}
    @Id
    @Column(name = "id")
    public Integer getId(){
        return this.id;
    }
    public void setId(Integer id){
        this.id = id;
    }
    @Column(name = "contractId")
    public Integer getContractId(){
        return this.contractId;
    }
    public void setContractId(Integer contractId){
        this.contractId = contractId;
    }
    @Column(name = "contractNo")
    public String getContractNo(){
        return this.contractNo;
    }
    public void setContractNo(String contractNo){
        this.contractNo = contractNo;
    }
    @Column(name = "startDay")
    public Date getStartDay(){
        return this.startDay;
    }
    public void setStartDay(Date startDay){
        this.startDay = startDay;
    }
    @Column(name = "endDay")
    public Date getEndDay(){
        return this.endDay;
    }
    public void setEndDay(Date endDay){
        this.endDay = endDay;
    }
    @Column(name = "freeMonths")
    public Byte getFreeMonths(){
        return this.freeMonths;
    }
    public void setFreeMonths(Byte freeMonths){
        this.freeMonths = freeMonths;
    }
    @Column(name = "freeDays")
    public Byte getFreeDays(){
        return this.freeDays;
    }
    public void setFreeDays(Byte freeDays){
        this.freeDays = freeDays;
    }
    @Column(name = "isDeleted")
    public Byte getIsDeleted(){
        return this.isDeleted;
    }
    public void setIsDeleted(Byte isDeleted){
        this.isDeleted = isDeleted;
    }
    @Column(name = "createUserId")
    public String getCreateUserId(){
        return this.createUserId;
    }
    public void setCreateUserId(String createUserId){
        this.createUserId = createUserId;
    }
    @Column(name = "createTime")
    public Date getCreateTime(){
        return this.createTime;
    }
    public void setCreateTime(Date createTime){
        this.createTime = createTime;
    }
    @Column(name = "updateUserId")
    public String getUpdateUserId(){
        return this.updateUserId;
    }
    public void setUpdateUserId(String updateUserId){
        this.updateUserId = updateUserId;
    }
    @Column(name = "updateTime")
    public Date getUpdateTime(){
        return this.updateTime;
    }
    public void setUpdateTime(Date updateTime){
        this.updateTime = updateTime;
    }
}
