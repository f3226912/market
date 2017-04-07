package cn.gdeng.market.extend.workflow.impl;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.snaker.engine.RunOutTrtansitionIllegalHandler;
import org.snaker.engine.core.Execution;
import org.snaker.engine.entity.Process;
import org.snaker.engine.model.TransitionModel;

import com.alibaba.dubbo.rpc.RpcException;

/**谷登专用运行输出变迁线路非法的处理。
 * 
 * @author wjguo
 *
 * datetime:2016年10月23日 上午11:56:33
 */
public class GuDengRunOutTransitionIllegalHandler implements RunOutTrtansitionIllegalHandler {
	/**
	 * 定义记录日志信息
	 */
	private Logger logger = LoggerFactory.getLogger(GuDengRunOutTransitionIllegalHandler.class);
	
	@Override
	public void handle(List<TransitionModel> enabledTransitionModels, Execution execution) {
		String illegalCase = "存在多条可执行线路";
		if (enabledTransitionModels.size() == 0) {
			illegalCase = "没有可执行的线路";
		} 
		
		Process process = execution.getProcess();
		if (process != null) {
			logger.error("工作流的流程名称{}{}，请注意检查流程图的设计。流程id为{}",new Object[]{process.getDisplayName(), illegalCase, process.getId()});
		}
		/*抛出异常提示前端用户
		 * 注意：
		 * 此处抛出duboo定义的异常而不是snaker的异常，是因为框架中使用了dubbo，
		 * 会对非受检异常自动进行额外的封装处理，只有对RpcException这个非受检异常才不会额外封装。
		 */
		throw new RpcException(RpcException.BIZ_EXCEPTION, "流程设计不合理，下一步骤" + illegalCase + "，请与流程监控人联系！");
		
	}

}
