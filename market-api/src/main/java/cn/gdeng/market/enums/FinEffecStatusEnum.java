package cn.gdeng.market.enums;

/**
 *	财务收款删除标志枚举
 */
public enum FinEffecStatusEnum {
	
	NO("0", "否"), YES("1", "是");
	
	private FinEffecStatusEnum(String code, String name) {
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
