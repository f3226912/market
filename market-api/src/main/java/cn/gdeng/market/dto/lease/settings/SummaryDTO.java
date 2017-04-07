package cn.gdeng.market.dto.lease.settings;

import java.io.Serializable;

/**
 * 汇总统计DTO
 * @author youj 
 *
 */
public class SummaryDTO implements Serializable{

	private static final long serialVersionUID = -8370099432018012335L;
	
	
	private int total; //资源总数量
	
	private int alreadyRentedCnt; //已租数量
	
	private int forRentCnt; //待租数量
	
	private int ineffectiveCnt; //未生效数量

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
	
	
    
	
}

