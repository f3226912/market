package cn.gdeng.market.enums;

/**
 *  财务收款款项类型枚举
 *
 */
public enum FinFundTypeEnum {
	
	NORMAL("0", "正常款项"), TEMP("1", "临时款项"), BACK("2", "退款");
	
	private FinFundTypeEnum(String code, String name) {
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
