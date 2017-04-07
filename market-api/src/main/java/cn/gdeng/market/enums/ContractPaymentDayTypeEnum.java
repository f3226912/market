package cn.gdeng.market.enums;

/**
 * 合同缴费日期类型
 * @author dengjianfeng
 *
 */
public enum ContractPaymentDayTypeEnum {

	BEFORE((byte)0, "缴费期初"),
	LAST((byte)1, "缴费期末"),
	NEXT_BEFORE((byte)2, "下一个缴费期初");
	
	private Byte code;
	
	private String name;

	private ContractPaymentDayTypeEnum(Byte code, String name) {
		this.code = code;
		this.name = name;
	}

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
		if(code == null){
			return "";
		}
		ContractPaymentDayTypeEnum[] values = ContractPaymentDayTypeEnum.values();
		for(ContractPaymentDayTypeEnum val : values){
			if(val.getCode().equals(code)){
				return val.getName();
			}
		}
		return "";
	}
	
}
