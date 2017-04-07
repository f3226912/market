package cn.gdeng.market.enums;

/**
 * 市场资源租赁状态枚举
 */
public enum MarketResourceLeaseStatusEnum {
	
	WAIT_FOR_RENT_OUT(1),  //待放租
	
	WAIT_FOR_RENT(2),  //待租
	
	RENTED(3);  //已租
	
	private Integer status;
	
	private MarketResourceLeaseStatusEnum(Integer status) {
		this.status = status;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}
	
}
