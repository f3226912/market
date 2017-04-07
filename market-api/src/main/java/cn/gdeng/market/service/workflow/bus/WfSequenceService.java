package cn.gdeng.market.service.workflow.bus;

/**工作流序列号服务类
 * 
 * @author wjguo
 *
 * datetime:2016年10月19日 下午12:10:22
 */
public interface WfSequenceService {
	/**生成流程名称
	 * @return
	 */
	String generalProcessName();
	
	/** 生成合同管理的流程实例名称。
	 * @param processId 流程名称
	 * @return
	 */
	String generalOrderNoForContractInfo(String processId);
	/** 生成合同变更的流程实例名称。
	 * @param processId 流程名称
	 * @return
	 */
	String generalOrderNoForContractChanged(String processId);
	/** 生成合同结算的流程实例名称。
	 * @param processId 流程名称
	 * @return
	 */
	String generalOrderNoForContractClose(String processId);
}
