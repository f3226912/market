package cn.gdeng.market.enums;


/**
 * 合同是否作废
 * @author wj
 *
 * datetime:2016年10月18日 上午11:15:25
 */
public enum ContractIsCancelEnum {
	NOT((byte)0, "未作废"),
	IS((byte)1, "已作废");
	
	private Byte code;
	
	private String name;

	private ContractIsCancelEnum(Byte code, String name) {
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
		ContractIsCancelEnum[] values = ContractIsCancelEnum.values();
		for(ContractIsCancelEnum val : values){
			if(val.getCode().equals(code)){
				return val.getName();
			}
		}
		return null;
	}
	
	/**根据code码获取对应的枚举
	 * @param code
	 * @return
	 */
	public static ContractIsCancelEnum getByCode(Byte code){
		ContractIsCancelEnum[] values = ContractIsCancelEnum.values();
		for(ContractIsCancelEnum val : values){
			if(val.getCode().equals(code)){
				return val;
			}
		}
		return null;
	}
	
}	