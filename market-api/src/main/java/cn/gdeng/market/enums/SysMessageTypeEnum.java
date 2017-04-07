package cn.gdeng.market.enums;

/**消息类型枚举
 * 
 * @author hxp
 *
 * datetime:2016年10月18日 下午3:36:07
 */
public enum SysMessageTypeEnum {
	WF_MESSAGE_TERMINATE("WF_001", "流程作废"),
	WF_MESSAGE_RESET_ACTOR("WF_002", "重置责任人"),
	WF_MESSAGE_ASSIGNED_ACTOR_ILLEGAL("WF_003", "非法分配任务责任人"),
	WF_MESSAGE_APPROVAL_INFO("WF_004", "审批信息");
	/**code码
	 * 
	 */
	private String code;
	/**描述
	 * 
	 */
	private String desc;
	private SysMessageTypeEnum(String code, String desc) {
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
}
