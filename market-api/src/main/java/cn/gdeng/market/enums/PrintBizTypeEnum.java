package cn.gdeng.market.enums;

/**
 * 套打应用业务
 * @author wind
 *
 */
public enum PrintBizTypeEnum {
	
	CONTRACT_SIGN((byte)0, "合同管理"),
	CONTRACT_CHANGE((byte)1, "合同变更"),
	CONTRACT_BALANCE((byte)2, "合同结算"),
	CONTRACT_PAY((byte)3, "合同付款");

	private PrintBizTypeEnum(Byte code, String name) {
		this.code = code;
		this.name = name;
	}

	private Byte code;
	
	private String name;

	public Byte getCode() {
		return code;
	}

	public void setCode(Byte code) {
		this.code = code;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	public static String getNameByCode(Byte code){
		PrintBizTypeEnum[] values = PrintBizTypeEnum.values();
		for(PrintBizTypeEnum val : values){
			if(val.getCode().equals(code)){
				return val.getName();
			}
		}
		return "";
	}
}
