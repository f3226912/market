 package cn.gdeng.market.entity.lease.resources;

import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

/**
 * 市场楼栋单元表
 * */
@Entity(name = "market_building_unit")
public class MarketBuildingUnitEntity  implements java.io.Serializable{
    /**
	 * 
	 */
	private static final long serialVersionUID = 7654946592398595999L;

	/**
     *
     */
    private Integer id;

     *楼栋id
     */
    private Integer buildingId;

     *单元序号：比如1，2，3
     */
    private Integer unitNo;

     *单元名称
     */
    private String name;

     *楼层图
     */
    private String unitImage;

     *状态: 默认 1 正常  0 删除
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
    @Column(name = "buildingId")
    public Integer getBuildingId(){

    }
    public void setBuildingId(Integer buildingId){

    }
    @Column(name = "unitNo")
    public Integer getUnitNo() {
		return unitNo;
	}
	public void setUnitNo(Integer unitNo) {
		this.unitNo = unitNo;
	}
    @Column(name = "name")
    public String getName(){

    }
    public void setName(String name){

    }
    @Column(name = "unitImage")
    public String getUnitImage(){

    }
    public void setUnitImage(String unitImage){

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