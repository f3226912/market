package cn.gdeng.market.enums;

/**合同提醒类型
 * 
 * @author kwang
 *
 * datetime:2016年10月17日 下午3:16:07
 */
public enum ContractRemindTypeEnum {
//'提醒类型: 1 合同到期提醒  2  合同收取租金提醒',
	ENDTIME(1, "合同到期提醒"), 
	MONEY(2, "合同收取租金提醒");
	
	private Integer code;
	
	private String name;

	private ContractRemindTypeEnum(Integer code, String name) {
		this.code = code;
		this.name = name;
	}

	public Integer getCode() {
		return code;
	}

	public void setCode(Integer code) {
		this.code = code;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	public static String getNameByCode(Integer code){
		ContractRemindTypeEnum[] values = ContractRemindTypeEnum.values();
		for(ContractRemindTypeEnum val : values){
			if(val.getCode().equals(code)){
				return val.getName();
			}
		}
		return null;
	}
	
}	