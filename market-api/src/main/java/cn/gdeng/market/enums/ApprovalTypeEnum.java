package cn.gdeng.market.enums;


/**合同审批类型
 * 
 * @author wjguo
 *
 * datetime:2016年10月18日 上午11:15:25
 */
public enum ApprovalTypeEnum {
	CONTRACT_MANAGER((byte)1, "合同初次审批"),
	CONTRACT_CHANGED((byte)2, "合同变更审批"),
	CONTRACT_CLOSE((byte)3, "合同结算审批");
	
	private Byte code;
	
	private String name;

	private ApprovalTypeEnum(Byte code, String name) {
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
		ApprovalTypeEnum[] values = ApprovalTypeEnum.values();
		for(ApprovalTypeEnum val : values){
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
	public static ApprovalTypeEnum getByCode(Byte code){
		ApprovalTypeEnum[] values = ApprovalTypeEnum.values();
		for(ApprovalTypeEnum val : values){
			if(val.getCode().equals(code)){
				return val;
			}
		}
		return null;
	}
	
}	