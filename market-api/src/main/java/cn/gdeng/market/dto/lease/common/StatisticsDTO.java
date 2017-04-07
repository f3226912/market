package cn.gdeng.market.dto.lease.common;

import java.io.Serializable;

/**
 * 资源统计DTO
 * @author youj 
 *
 */
public class StatisticsDTO implements Serializable{

	private static final long serialVersionUID = -8370099432018019290L;
	
	private String id; 
	
	private int total; //资源总数量
	
	private int alreadyRentedCnt; //已租数量
	
	private int forRentCnt; //待租数量
	
	private int ineffectiveCnt; //未生效数量
	
	private String name = "";
	
	private String coordinate; //坐标
	
	private String x;
	
	private String y;
	
	public int getTotal() {
		return total;
	}

	public void setTotal(int total) {
		this.total = total;
	}

	public int getAlreadyRentedCnt() {
		return alreadyRentedCnt;
	}

	public void setAlreadyRentedCnt(int alreadyRentedCnt) {
		this.alreadyRentedCnt = alreadyRentedCnt;
	}

	public int getForRentCnt() {
		return forRentCnt;
	}

	public void setForRentCnt(int forRentCnt) {
		this.forRentCnt = forRentCnt;
	}

	public int getIneffectiveCnt() {
		return ineffectiveCnt;
	}

	public void setIneffectiveCnt(int ineffectiveCnt) {
		this.ineffectiveCnt = ineffectiveCnt;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getCoordinate() {
		return coordinate;
	}

	public void setCoordinate(String coordinate) {
		this.coordinate = coordinate;
	}

	public String getX() {
		return x;
	}

	public void setX(String x) {
		this.x = x;
	}

	public String getY() {
		return y;
	}

	public void setY(String y) {
		this.y = y;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	
}

