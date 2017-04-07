package cn.gdeng.market.enums;

/**审核记录状态
 * 
 * @author wujiang
 *
 * datetime:2016年10月14日 下午3:16:07
 */
public enum ApprovalRecordStatusEnum {
//审核记录状态
    TG((byte)0, "通过"),
	BH((byte)1, "驳回");
	
	private Byte code;
	
	private String name;

	private ApprovalRecordStatusEnum(Byte code, String name) {
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
		ApprovalRecordStatusEnum[] values = ApprovalRecordStatusEnum.values();
		for(ApprovalRecordStatusEnum val : values){
			if(val.getCode().equals(code)){
				return val.getName();
			}
		}
		return null;
	}
	
}	