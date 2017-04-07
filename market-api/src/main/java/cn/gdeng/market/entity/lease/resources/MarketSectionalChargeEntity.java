package cn.gdeng.market.entity.lease.resources;

import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

@Entity(name = "market_sectionalcharge")
public class MarketSectionalChargeEntity  implements java.io.Serializable{
    /**
	 * 
	 */
	private static final long serialVersionUID = 3390726083345907148L;

	/**
     *
     */
    private Integer id;

     *计费标准id
     */
    private Integer expStandardId;

     *计费单价
     */
    private Double chargeUnitPrice;

     *上限值
     */
    private Double upValue;

     *下限值
     */
    private Double downValue;

     *状态:  默认  1 正常  0  删除
     */
    private Integer status;

     *
     */
    private String createUserId;

     *
     */
    private Date createTime;

     *
     */
    private String updateUserId;

     *
     */
    private Date updateTime;

    @Column(name = "id")
    public Integer getId(){

    }
    public void setId(Integer id){

    }
    @Column(name = "expStandardId")
    public Integer getExpStandardId(){

    }
    public void setExpStandardId(Integer expStandardId){

    }
    @Column(name = "chargeUnitPrice")
    public Double getChargeUnitPrice(){

    }
    public void setChargeUnitPrice(Double chargeUnitPrice){

    }
    @Column(name = "upValue")
    public Double getUpValue(){

    }
    public void setUpValue(Double upValue){

    }
    @Column(name = "downValue")
    public Double getDownValue(){

    }
    public void setDownValue(Double downValue){

    }
    @Column(name = "status")
    public Integer getStatus(){

    }
    public void setStatus(Integer status){

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
}