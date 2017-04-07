package cn.gdeng.market.enums;

/**合同状态
 * 
 * @author kwang
 *
 * datetime:2016年10月14日 下午3:16:07
 */
public enum ContractStatusEnum {
//'合同状态 0 待执行 1 执行中 2 已结算'
	DZX((byte)0, "待执行"),
	ZXZ((byte)1, "执行中"),
	JS((byte)2, "已结算");
	
	private Byte code;
	
	private String name;

	private ContractStatusEnum(Byte code, String name) {
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
		ContractStatusEnum[] values = ContractStatusEnum.values();
		for(ContractStatusEnum val : values){
			if(val.getCode().equals(code)){
				return val.getName();
			}
		}
		return null;
	}
	
}	