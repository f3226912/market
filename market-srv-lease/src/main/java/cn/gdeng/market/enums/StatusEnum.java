package cn.gdeng.market.enums;

public enum StatusEnum {
	
	NORMAL(1),  //正常
	
	DELETE(0);  //删除
	
	private Integer status;
	
	private StatusEnum(Integer status) {
		this.status = status;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}
	
	

}
