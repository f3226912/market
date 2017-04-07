package cn.gdeng.market.entity.lease.common;

import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

@Entity(name = "product_category")
public class ProductCategoryEntity  implements java.io.Serializable{
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
     *主键
     */
    private Long categoryId;
    /**
     *市场id
     */
    private Long marketId;
    /**
     *当前层次(1;2)
     */
    private Byte curLevel;
    /**
     *分类名称
     */
    private String cateName;
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
     *上层分类id
     */
    private Long parentId;
    /**
     *顺序
     */
    private Integer orderNum;
    /**
     *'状态(0.已删除;1未删除)',
     */
    private String status;
    /**
     *产品图片
     */
    private String typeIcon;
    /**
     *web图片
     */
    private String webTypeIcon;
    /**
     *
     */
    private Short lefts;
    /**
     *
     */
    private Short rights;
    @Id
    @Column(name = "categoryId")
    public Long getCategoryId(){
        return this.categoryId;
    }
    public void setCategoryId(Long categoryId){
        this.categoryId = categoryId;
    }
    @Column(name = "marketId")
    public Long getMarketId(){
        return this.marketId;
    }
    public void setMarketId(Long marketId){
        this.marketId = marketId;
    }
    @Column(name = "curLevel")
    public Byte getCurLevel(){
        return this.curLevel;
    }
    public void setCurLevel(Byte curLevel){
        this.curLevel = curLevel;
    }
    @Column(name = "cateName")
    public String getCateName(){
        return this.cateName;
    }
    public void setCateName(String cateName){
        this.cateName = cateName;
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
    @Column(name = "parentId")
    public Long getParentId(){
        return this.parentId;
    }
    public void setParentId(Long parentId){
        this.parentId = parentId;
    }
    @Column(name = "orderNum")
    public Integer getOrderNum(){
        return this.orderNum;
    }
    public void setOrderNum(Integer orderNum){
        this.orderNum = orderNum;
    }
    @Column(name = "status")
    public String getStatus(){
        return this.status;
    }
    public void setStatus(String status){
        this.status = status;
    }
    @Column(name = "typeIcon")
    public String getTypeIcon(){
        return this.typeIcon;
    }
    public void setTypeIcon(String typeIcon){
        this.typeIcon = typeIcon;
    }
    @Column(name = "webTypeIcon")
    public String getWebTypeIcon(){
        return this.webTypeIcon;
    }
    public void setWebTypeIcon(String webTypeIcon){
        this.webTypeIcon = webTypeIcon;
    }
    @Column(name = "lefts")
    public Short getLefts(){
        return this.lefts;
    }
    public void setLefts(Short lefts){
        this.lefts = lefts;
    }
    @Column(name = "rights")
    public Short getRights(){
        return this.rights;
    }
    public void setRights(Short rights){
        this.rights = rights;
    }
}
