package cn.gdeng.market.entity.lease.contract;

import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
/**
 * 其它费项
 * @author Administrator
 *
 */
@Entity(name = "contract_others_fee")
public class ContractOthersFeeEntity  implements java.io.Serializable{
    /**
	 * 
	 */
	private static final long serialVersionUID = -1951755979748488607L;

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
     *租赁资源
     */
    private String leasingResource;
    /**
     *费项类别
     */
    private String itemCategory;
    /**
     *费项类别ID
     */
    private Integer itemCategoryId;
    /**
     *费项名称
     */
    private String itemName;
    /**
     *费项类别ID
     */
    private Integer itemNameId;
    /**
     *计费标准
     */
    private String freightBasis;
    /**
     *计费标准ID
     */
    private Integer freightBasisId;
    /**
     *收租模式  1 指定金额  2 手工录入  3 建筑面积  4  套内面积  5 可出租面积
     */
    private Integer rentMode;
    /**
     *计费单价(计费标准表)
     */
    private Double chargeUnitPrice;
    /**
     *计费单位(计费标准表)  1 元/日  2 元/月  3 元/季  4  元/半年  5 元/年
     */
    private Integer chargeUnit;
    /**
     *总价
     */
    private String total;
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
    @Column(name = "leasingResource")
    public String getLeasingResource(){
        return this.leasingResource;
    }
    public void setLeasingResource(String leasingResource){
        this.leasingResource = leasingResource;
    }
    @Column(name = "itemCategory")
    public String getItemCategory(){
        return this.itemCategory;
    }
    public void setItemCategory(String itemCategory){
        this.itemCategory = itemCategory;
    }
    @Column(name = "itemCategoryId")
    public Integer getItemCategoryId(){
        return this.itemCategoryId;
    }
    public void setItemCategoryId(Integer itemCategoryId){
        this.itemCategoryId = itemCategoryId;
    }
    @Column(name = "itemName")
    public String getItemName(){
        return this.itemName;
    }
    public void setItemName(String itemName){
        this.itemName = itemName;
    }
    @Column(name = "itemNameId")
    public Integer getItemNameId(){
        return this.itemNameId;
    }
    public void setItemNameId(Integer itemNameId){
        this.itemNameId = itemNameId;
    }
    @Column(name = "freightBasis")
    public String getFreightBasis(){
        return this.freightBasis;
    }
    public void setFreightBasis(String freightBasis){
        this.freightBasis = freightBasis;
    }
    @Column(name = "freightBasisId")
    public Integer getFreightBasisId(){
        return this.freightBasisId;
    }
    public void setFreightBasisId(Integer freightBasisId){
        this.freightBasisId = freightBasisId;
    }
    @Column(name = "rentMode")
    public Integer getRentMode(){
        return this.rentMode;
    }
    public void setRentMode(Integer rentMode){
        this.rentMode = rentMode;
    }
    @Column(name = "chargeUnitPrice")
    public Double getChargeUnitPrice(){
        return this.chargeUnitPrice;
    }
    public void setChargeUnitPrice(Double chargeUnitPrice){
        this.chargeUnitPrice = chargeUnitPrice;
    }
    @Column(name = "chargeUnit")
    public Integer getChargeUnit(){
        return this.chargeUnit;
    }
    public void setChargeUnit(Integer chargeUnit){
        this.chargeUnit = chargeUnit;
    }
    @Column(name = "total")
    public String getTotal(){
        return this.total;
    }
    public void setTotal(String total){
        this.total = total;
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
