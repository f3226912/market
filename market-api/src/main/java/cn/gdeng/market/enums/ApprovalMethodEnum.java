package cn.gdeng.market.enums;


/**
 * 合同审批方式
 * @author wj
 *
 * datetime:2016年10月18日 上午11:15:25
 */
public enum ApprovalMethodEnum {
	CONTRACT_WORKFLOW((byte)1, "工作流审批"),
	CONTRACT_HUMAN((byte)2, "线下审批");
	
	private Byte code;
	
	private String name;

	private ApprovalMethodEnum(Byte code, String name) {
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
		ApprovalMethodEnum[] values = ApprovalMethodEnum.values();
		for(ApprovalMethodEnum val : values){
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
	public static ApprovalMethodEnum getByCode(Byte code){
		ApprovalMethodEnum[] values = ApprovalMethodEnum.values();
		for(ApprovalMethodEnum val : values){
			if(val.getCode().equals(code)){
				return val;
			}
		}
		return null;
	}
	
}	