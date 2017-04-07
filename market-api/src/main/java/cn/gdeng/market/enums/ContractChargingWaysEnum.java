package cn.gdeng.market.enums;

/**
 * 合同：收费方式
 * @author dengjianfeng
 *
 */
public enum ContractChargingWaysEnum {
	
	CYCLE((byte)0, "按周期收费"),
	TOTAL((byte)1, "约定总金额");
	
	private Byte code;
	
	private String name;

	private ContractChargingWaysEnum(Byte code, String name) {
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
		ContractChargingWaysEnum[] values = ContractChargingWaysEnum.values();
		for(ContractChargingWaysEnum val : values){
			if(val.getCode().equals(code)){
				return val.getName();
			}
		}
		return "";
	}
}
