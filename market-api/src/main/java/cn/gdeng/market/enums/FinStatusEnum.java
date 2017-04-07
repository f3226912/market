package cn.gdeng.market.enums;

/**
 *  财务应收款-收款状态
 * 
 */
public enum FinStatusEnum {
	
	NOT_YET("0", "未收款"), ALREADY_REC("1", "已收款");
	
	private FinStatusEnum(String code, String name) {
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
