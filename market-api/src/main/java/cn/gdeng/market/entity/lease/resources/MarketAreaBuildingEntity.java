package cn.gdeng.market.entity.lease.resources;

import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

@Entity(name = "market_area_building")
public class MarketAreaBuildingEntity  implements java.io.Serializable{
    /**
	 * 
	 */
	private static final long serialVersionUID = -4143588035759007116L;

	/**
     *
     */
    private Integer id;

     *区域id
     */
    private Integer areaId;

     *楼栋名称
     */
    private String name;

     *楼栋编号
     */
    private String code;

     *楼栋性质 1 底层  2  多层  3 小高层  4 高层
     */
    private Integer nature;

     *楼栋图片
     */
    private String buildingImage;

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
    @Column(name = "areaId")
    public Integer getAreaId(){

    }
    public void setAreaId(Integer areaId){

    }
    @Column(name = "name")
    public String getName(){

    }
    public void setName(String name){

    }
    @Column(name = "code")
    public String getCode(){

    }
    public void setCode(String code){

    }
    @Column(name = "nature")
    public Integer getNature(){

    }
    public void setNature(Integer nature){

    }
    @Column(name = "buildingImage")
    public String getBuildingImage(){

    }
    public void setBuildingImage(String buildingImage){

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