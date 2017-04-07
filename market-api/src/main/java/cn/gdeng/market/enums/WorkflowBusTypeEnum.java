package cn.gdeng.market.enums;

/**工作流业务类型枚举
 * 
 * @author wjguo
 *
 * datetime:2016年10月11日 下午3:36:07
 */
public enum WorkflowBusTypeEnum {
	CONTRACT_MANAGER("C01", "合同管理"),
	CONTRACT_CHANGED("C02", "合同变更"),
	CONTRACT_CLOSE("C03", "合同结算");
	/**code码
	 * 
	 */
	private String code;
	/**描述
	 * 
	 */
	private String desc;
	private WorkflowBusTypeEnum(String code, String desc) {
		this.code = code;
		this.desc = desc;
	}
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public String getDesc() {
		return desc;
	}
	public void setDesc(String desc) {
		this.desc = desc;
	}
	
	/**根据code码获取对应的枚举
	 * @param code
	 * @return
	 */
	public static WorkflowBusTypeEnum getByCode(String code){
		WorkflowBusTypeEnum[] values = WorkflowBusTypeEnum.values();
		for(WorkflowBusTypeEnum val : values){
			if(val.getCode().equals(code)){
				return val;
			}
		}
		return null;
	}
	
	/**根据code码获取描述内容。
	 * @param code
	 * @return
	 */
	public static String getDescByCode(Byte code){
		WorkflowBusTypeEnum[] values = WorkflowBusTypeEnum.values();
		for(WorkflowBusTypeEnum val : values){
			if(val.getCode().equals(code)){
				return val.getDesc();
			}
		}
		return null;
	}
}
