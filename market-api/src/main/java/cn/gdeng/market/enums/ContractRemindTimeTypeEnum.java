package cn.gdeng.market.enums;

/**合同提醒时间类型
 * 
 * @author kwang
 *
 * datetime:2016年10月17日 下午3:16:07
 */
public enum ContractRemindTimeTypeEnum {

	DAY(1, "天"), 
	MONTH(2, "月");
	
	private Integer code;
	
	private String name;

	private ContractRemindTimeTypeEnum(Integer code, String name) {
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
		ContractRemindTimeTypeEnum[] values = ContractRemindTimeTypeEnum.values();
		for(ContractRemindTimeTypeEnum val : values){
			if(val.getCode().equals(code)){
				return val.getName();
			}
		}
		return null;
	}
	
}	