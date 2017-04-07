package cn.gdeng.market.enums;

/**审批状态
 * 
 * @author kwang
 *
 * datetime:2016年10月14日 下午3:16:07
 */
public enum ApprovalStatusEnum {
//审批状态 0 待审批 1 审批中 2 已驳回 3 已通过'
    DSP((byte)0, "待审批"),
	SPZ((byte)1, "审批中"),
	YBH((byte)2, "已驳回"),
	YTG((byte)3, "已通过");
	
	private Byte code;
	
	private String name;

	private ApprovalStatusEnum(Byte code, String name) {
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
		ApprovalStatusEnum[] values = ApprovalStatusEnum.values();
		for(ApprovalStatusEnum val : values){
			if(val.getCode().equals(code)){
				return val.getName();
			}
		}
		return null;
	}
	
}	