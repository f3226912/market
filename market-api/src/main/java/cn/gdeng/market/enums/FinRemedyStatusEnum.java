package cn.gdeng.market.enums;

/**
 *  财务收款
 *  是否补交记录标志 枚举
 */
public enum FinRemedyStatusEnum {
	
	NO("1", "否"), YES("2", "是");
	
	private FinRemedyStatusEnum(String code, String name) {
		this.code = code;
		this.name = name;
	}

	private String code;
	
	private String name;

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
}
