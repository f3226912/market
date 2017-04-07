package cn.gdeng.market.enums;

/**
 * 计费单位
 * @author dengjianfeng
 *
 */
public enum BillingUnitEnum {
	
	PER_MONTH((byte)0, "元/月/平"),
	PER_SEASON((byte)1, "元/季/平"),
	PER_HALF_YEAR((byte)2, "元/半年/平"),
	PER_YEAR((byte)3, "元/年/平");

	private Byte code;
	
	private String name;
	

	private BillingUnitEnum(Byte code, String name) {
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
		BillingUnitEnum[] values = BillingUnitEnum.values();
		for(BillingUnitEnum val : values){
			if(val.getCode().equals(code)){
				return val.getName();
			}
		}
		return "";
	}
	
}
