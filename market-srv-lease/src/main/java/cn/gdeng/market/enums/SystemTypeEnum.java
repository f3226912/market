package cn.gdeng.market.enums;

public enum SystemTypeEnum {
	
	SYSTEM(1),  //系统类型
	
	CUSTOMER(0);  //市场自定义类型
	
	private Integer status;
	
	private SystemTypeEnum(Integer status) {
		this.status = status;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}
	
	

}
