package cn.gdeng.market.enums;


/** 工作流数据库流水序列值的枚举名称
 * 
 * @author wjguo
 *
 * datetime:2016年10月19日 上午11:17:32
 */
public enum WfDataBaseNextValEnum {
	CONTRACT_INFO_SEQUENCE("wf_c01", "合同管理流水号"),
	CONTRACT_CHANGED_SEQUENCE("wf_c02", "合同变更流水号"),
	CONTRACT_CLOSE_SEQUENCE("wf_c03", "合同结算流水号"),
    PROCESS_NAME_SEQUENCE("wf_p00", "流程定义名称流水号");
	
	private String code;
	
	private String name;

	private WfDataBaseNextValEnum(String code, String name) {
		this.code = code;
		this.name = name;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
}	