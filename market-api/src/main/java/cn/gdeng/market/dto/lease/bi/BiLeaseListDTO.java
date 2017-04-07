package cn.gdeng.market.dto.lease.bi;

import java.io.Serializable;
/**
 * 
 * @作者 XieZhongGuo
 * @创建时间 2016年10月17日
 * @说明 租赁情况一览表DTO类
 * @版本 v1.0
 */
public class BiLeaseListDTO implements Serializable{
	private static final long serialVersionUID = -294220833881650738L;
	
   /**
    * 扩展字段 start
    */
	private Integer marketId;
	/**
	 * 统计开始时间
	 */
	private String startTime;
	/**
	 * 统计结束时间
	 */
	private String endTime;

	   /**
	    * 扩展字段 end
	    */
   /**
    * 资源类型
    */
	private  String marketResourceTypeName;
	/**
	 * 资源类型ID
	 */
	private String marketResourceTypeId;
	/**
	 * 区域
	 */
	private  String areaName;
	/**
	 * 资源总数
	 */
	private Integer countResource;
	/**
	 * 已租数
	 */
	private Integer countRented;
	/**
	 * 出租率
	 */
	private Double rentalRate;
	/**
	 * 可租面积
	 */
	private Double areaAvailableForRent;
	/**
	 * 已租面积
	 */
	private Double leasedArea;
	/**
	 * 出租率面积
	 */
	private Double rentalRateArea;
	/**
	 * 租金收入
	 */
	private Double rentalIncome;
	/**
	 * 租赁坪效(总面积)
	 */
	private Double leasePxZmj;
	/**
	 * 租赁坪效(已租面积)
	 */
	private Double leasePxYzmj;
	
	public String getMarketResourceTypeName() {
		return marketResourceTypeName;
	}
	public void setMarketResourceTypeName(String marketResourceTypeName) {
		this.marketResourceTypeName = marketResourceTypeName;
	}
	public String getAreaName() {
		return areaName;
	}
	public void setAreaName(String areaName) {
		this.areaName = areaName;
	}
	public Integer getCountResource() {
		return countResource;
	}
	public void setCountResource(Integer countResource) {
		this.countResource = countResource;
	}
	public Integer getCountRented() {
		return countRented;
	}
	public void setCountRented(Integer countRented) {
		this.countRented = countRented;
	}
	public Double getRentalRate() {
		return rentalRate;
	}
	public void setRentalRate(Double rentalRate) {
		this.rentalRate = rentalRate;
	}
	public Double getAreaAvailableForRent() {
		return areaAvailableForRent;
	}
	public void setAreaAvailableForRent(Double areaAvailableForRent) {
		this.areaAvailableForRent = areaAvailableForRent;
	}
	public Double getLeasedArea() {
		return leasedArea;
	}
	public void setLeasedArea(Double leasedArea) {
		this.leasedArea = leasedArea;
	}
	public Double getRentalRateArea() {
		return rentalRateArea;
	}
	public void setRentalRateArea(Double rentalRateArea) {
		this.rentalRateArea = rentalRateArea;
	}
	public Double getRentalIncome() {
		return rentalIncome;
	}
	public void setRentalIncome(Double rentalIncome) {
		this.rentalIncome = rentalIncome;
	}
	public Double getLeasePxZmj() {
		return leasePxZmj;
	}
	public void setLeasePxZmj(Double leasePxZmj) {
		this.leasePxZmj = leasePxZmj;
	}
	public Double getLeasePxYzmj() {
		return leasePxYzmj;
	}
	public void setLeasePxYzmj(Double leasePxYzmj) {
		this.leasePxYzmj = leasePxYzmj;
	}
	public String getStartTime() {
		return startTime;
	}
	public void setStartTime(String startTime) {
		this.startTime = startTime;
	}
	public String getEndTime() {
		return endTime;
	}
	public void setEndTime(String endTime) {
		this.endTime = endTime;
	}
	public String getMarketResourceTypeId() {
		return marketResourceTypeId;
	}
	public void setMarketResourceTypeId(String marketResourceTypeId) {
		this.marketResourceTypeId = marketResourceTypeId;
	}
	public Integer getMarketId() {
		return marketId;
	}
	public void setMarketId(Integer marketId) {
		this.marketId = marketId;
	}
	
	
}
