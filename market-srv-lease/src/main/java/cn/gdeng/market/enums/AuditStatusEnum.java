package cn.gdeng.market.enums;

/**
 * 合同审核结果枚举类
 * @author Administrator
 *
 */
public enum AuditStatusEnum {
	
	DSP((byte)0, "待审批"),
	SPZ((byte)1, "审批中"),
	YBH((byte)2, "已驳回"),
	YTG((byte)3, "已通过");

	private Byte code;
	
	private String name;
	
	AuditStatusEnum(Byte code, String name){
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
		AuditStatusEnum[] values = AuditStatusEnum.values();
		for(AuditStatusEnum val : values){
			if(val.getCode().equals(code)){
				return val.name;
			}
		}
		return null;
	}
}
