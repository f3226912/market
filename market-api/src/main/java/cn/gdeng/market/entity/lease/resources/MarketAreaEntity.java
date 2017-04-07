package cn.gdeng.market.entity.lease.resources;

import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

@Entity(name = "market_area")
public class MarketAreaEntity  implements java.io.Serializable{
    /**
	 * 
	 */
	private static final long serialVersionUID = 8992306699687362241L;

	/**
     *
     */
    private Integer id;

     *市场id
     */
    private Integer marketId;

     *区域名称
     */
    private String name;

     *区域编号
     */
    private String code;

     * 区域图片地址
     */
    private String areaImage;

     *状态: 默认 1  正常  0 禁用
     */
    private Integer status;

     *排序字段
     */
    private Integer sort;

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
    @Column(name = "marketId")
    public Integer getMarketId(){

    }
    public void setMarketId(Integer marketId){

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
    @Column(name = "sort")
    public Integer getSort() {
		return sort;
	}
	public void setSort(Integer sort) {
		this.sort = sort;
	}
    @Column(name = "areaImage")
    public String getAreaImage(){

    }
    public void setAreaImage(String areaImage){

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