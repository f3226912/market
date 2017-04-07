package cn.gdeng.market.enums;

/**
 * 合同计费面积
 * @author dengjianfeng
 *
 */
public enum ContractBillingAreaEnum {
	
	FREE_AREA((byte)0, "可租面积"),
	FLOOR_AREA((byte)1, "建筑面积"),
	INNER_AREA((byte)2, "套内面积");

	private Byte code;
	
	private String name;

	private ContractBillingAreaEnum(Byte code, String name) {
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
		ContractBillingAreaEnum[] values = ContractBillingAreaEnum.values();
		for(ContractBillingAreaEnum val : values){
			if(val.getCode().equals(code)){
				return val.getName();
			}
		}
		return "";
	}
}
