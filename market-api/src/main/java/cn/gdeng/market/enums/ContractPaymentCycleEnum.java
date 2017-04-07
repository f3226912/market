package cn.gdeng.market.enums;

/**
 * 合同缴费周期
 * @author dengjianfeng
 *
 */
public enum ContractPaymentCycleEnum {

	MONTH((byte)0, "月"),
	SEASON((byte)1, "季度"),
	HALF_YEAR((byte)2, "半年"),
	YEAR((byte)3, "年");
	
	private Byte code;
	
	private String name;

	private ContractPaymentCycleEnum(Byte code, String name) {
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
		ContractPaymentCycleEnum[] values = ContractPaymentCycleEnum.values();
		for(ContractPaymentCycleEnum val : values){
			if(val.getCode().equals(code)){
				return val.getName();
			}
		}
		return "";
	}
}
