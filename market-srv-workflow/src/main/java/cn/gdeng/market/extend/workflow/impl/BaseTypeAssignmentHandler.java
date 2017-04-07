package cn.gdeng.market.extend.workflow.impl;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.snaker.engine.SnakerException;
import org.snaker.engine.TaskAssignmentHandler;
import org.snaker.engine.core.Execution;
import org.snaker.engine.entity.Order;
import org.snaker.engine.entity.Process;
import org.snaker.engine.helper.DateHelper;
import org.snaker.engine.model.TaskModel;

import com.alibaba.dubbo.rpc.RpcException;

import cn.gdeng.market.dto.admin.SysUserPostDTO;
import cn.gdeng.market.entity.admin.SysMessageEntity;
import cn.gdeng.market.enums.Const;
import cn.gdeng.market.enums.SysMessageTypeEnum;
import cn.gdeng.market.exception.BizException;
import cn.gdeng.market.service.admin.SysMessageService;
import cn.gdeng.market.service.admin.SysPostService;
import cn.gdeng.market.service.admin.SysUserPostService;
import cn.gdeng.market.util.SysMessageHelper;

/** 基于类型的人员分配处理器
 * @author wjguo
 * datetime 2016年9月28日 下午6:44:14  
 *
 */
public class BaseTypeAssignmentHandler implements TaskAssignmentHandler {
	/**用户id的标识前缀
	 * 
	 */
	private final static String USER_ID_FLAG_PREFIX = "U_";
	
	/**岗位id的标识前缀
	 * 
	 */
	private final static String POST_ID_FLAG_PREFIX = "P_";
	
	
	/**
	 * 定义记录日志信息
	 */
	private Logger logger = LoggerFactory.getLogger(BaseTypeAssignmentHandler.class);	
	
	/**
	 * 用户岗位映射服务类
	 */
	private SysUserPostService sysUserPostService;
	/**
	 * 系统消息服务类
	 */
	private SysMessageService sysMessageService;
	/**
	 * 岗位服务类
	 */
	private SysPostService sysPostService;
	
	public void setSysUserPostService(SysUserPostService sysUserPostService) {
		this.sysUserPostService = sysUserPostService;
	}
	public void setSysMessageService(SysMessageService sysMessageService) {
		this.sysMessageService = sysMessageService;
	}
	public void setSysPostService(SysPostService sysPostService) {
		this.sysPostService = sysPostService;
	}
	
	@Override
	public Object assign(TaskModel model, Execution execution) {
		//分配的id（可能是基于岗位的id，或者是基于人员的id）
		String assignee = model.getAssignee();
		
		if (StringUtils.isBlank(assignee)) {
			throw new IllegalArgumentException("This assignee is illegal");
		}
		
		//岗位id集合
		List<Integer> postIds = new ArrayList<Integer>();
		//保存任务的参与者id
		List<String> actorIds = new ArrayList<String>();
		String[] assignedIds = resolveAssignee(assignee);
		for (String assId : assignedIds) {
			//基于岗位分配的格式： 岗位名称(P_岗位id)
			if (assignee.contains(POST_ID_FLAG_PREFIX)) {
				//基于岗位id的分配
				Integer postId = extractPostId(assId);
				postIds.add(postId);
				storeActorsByPostId(postId, actorIds);
				
			//基于人员分配的格式：U_人员id
			} else if (assignee.startsWith(USER_ID_FLAG_PREFIX)) {
				//基于人员id的分配
				storeActorsByUserId(extractUserId(assId), actorIds);
				
			} else {
				logger.warn("不能确定指派的 assignee(" + assId + ")是属于什么类型的id,直接忽略此指派值");
			}
		}
		
		//如果最终无参与者，则需要做异常处理
		if (actorIds.size() == 0) {
			if (postIds.size() > 0) {
				try {
					if (sysPostService.queryValidByIds(postIds).size() <= 0) {
						handleAssignedIllegal(model, execution, "岗位不存在");
					} else {
						handleAssignedIllegal(model, execution, "无责任人");
					}
				} catch (BizException e) {
					throw new SnakerException(e);
				}
			} else {
				handleAssignedIllegal(model, execution, "无法确定责任人");
			}
		}
		
		//去除重复的参与者
		actorIds = distinctActorIds(actorIds);
		if (logger.isDebugEnabled()) {
			logger.debug(model.getName() + "最终确定的指派者有： " + actorIds);
		}
		//返回字符串形式的数组
		return actorIds.toArray(new String[actorIds.size()]);
	}
	
	/**通过postid获取参与者id，并存入集合中。
	 * @param postId
	 * @param actorIds
	 */
	private void storeActorsByPostId(Integer postId, List<String> actorIds) {
		try {
			List<SysUserPostDTO> sysUserPosts = sysUserPostService.
						queryValidByPostId(Integer.valueOf(postId));
			for (SysUserPostDTO sysUserPost : sysUserPosts) {
				actorIds.add(sysUserPost.getUserId().toString());
			}
		} catch (NumberFormatException | BizException e) {
			throw new SnakerException("BasePostAssignmentHandler assign failure", e);
		}
		
	}
	
	/** 通过usersid获取参与者id，并存入集合中。
	 * @param userId 
	 * @param actorIds
	 */
	private void storeActorsByUserId(Integer userId, List<String> actorIds) {
		//参与者id 就是 用户id，直接存入集合即可。
		actorIds.add(userId.toString());
	}
	
	
	/** 分解assignee
	 * @param assignedId
	 * @return
	 */
	private String[] resolveAssignee(String assignee) {
		String[] split = assignee.split("、");
		//去除多余的空格
		for (int i = 0 , len = split.length; i < len; i++) {
			split[i] = split[i].trim();
		}
		return split;
	}
	
	/**提取岗位id
	 * @param assignedIds
	 * @return
	 */
	private Integer extractPostId(String assignedIds) {
		String postId = assignedIds.substring(assignedIds.indexOf(POST_ID_FLAG_PREFIX) + 2, assignedIds.indexOf(")"));
		return Integer.valueOf(postId);
	}
	
	/**提取用户id
	 * @param assignedIds
	 * @return
	 */
	private Integer extractUserId(String assignedIds) {
		String postId = assignedIds.substring(assignedIds.indexOf(USER_ID_FLAG_PREFIX) + 2);
		return Integer.valueOf(postId);
	}
	
	private void handleAssignedIllegal(TaskModel model, Execution execution, String warnCase) {
		//发送消息给流程监控人和发起人
		sendWarnMsg(execution, model, warnCase);
		//记录日志便于开发排查
		logger.error("工作流出现{}的情况，请及时检查流程定义。流程定义的名称为{}，待执行的任务名称为{}",
				new Object[]{warnCase, execution.getProcess().getDisplayName(), model.getDisplayName()});
		/*抛出异常提示前端用户
		 * 注意：
		 * 此处抛出duboo定义的异常而不是snaker的异常，是因为框架中使用了dubbo，
		 * 会对非受检异常自动进行额外的封装处理，只有对RpcException这个非受检异常才不会额外封装。
		 */
		throw new RpcException(RpcException.BIZ_EXCEPTION, "下一步骤" + warnCase + "，请与流程监控人联系");
	}
	
	/** 发送警告消息
	 * @param execution 工作流执行对象
	 * @param model 任务模型
	 * @param warnCase 警告原因
	 */
	private void sendWarnMsg(Execution execution, TaskModel model,
			String warnCase){
		
		Process process = execution.getProcess();
		Order order = execution.getOrder();
		//前一个执行的任务名称。如果是发起，则不存在前一任务名称。
		String previousTaskName;
		if (execution.getTask() != null) {
			previousTaskName = execution.getTask().getDisplayName();
		} else {
			previousTaskName = "发起";
		}
		
		//消息接收者为 流程监控人、流程发起人
		String[] receivers = new String[]{process.getMonitorId(), order.getCreator()};
		
		//标题。格式： 流程实例名称 + 当前任务名称 + 警告原因
		String title = order.getOrderNo() + model.getDisplayName() + warnCase;
		/* 内容。格式
		 * 您参与的流程 + 当前任务名称 + 警告原因
		 * 导致 + 前一步骤名称 + 无法审批
		 * 发送时间： 系统当前时间
		 */
		String msg = String.format("您参与的流程%s%s。\n导致%s无法审批。\n发送时间：%s。", 
				model.getDisplayName(), warnCase, previousTaskName, DateHelper.getTime());
		
		List<SysMessageEntity> sysMsgList = new ArrayList<>();
		
		for (String receiver : receivers) {
			SysMessageEntity sysMessageEntity = new SysMessageEntity();
			//消息类型
			sysMessageEntity.setMessageType(SysMessageTypeEnum.WF_MESSAGE_ASSIGNED_ACTOR_ILLEGAL.getCode());
			//消息发送者 为流程发起人
			sysMessageEntity.setSender(order.getCreator());
			//消息接收者
			sysMessageEntity.setReceiver(receiver);
			//消息内容
			sysMessageEntity.setMessageContent(msg);
			//消息标题
			sysMessageEntity.setMessageTitile(title);
			//是否已经读 1 已读 0 未读 默认
			sysMessageEntity.setHasRead(Integer.valueOf(Const.MESSAGE_IS_READ_0));
			//状态:默认 1 正常 0 删除
			sysMessageEntity.setStatus(Const.NORMAL);
			sysMessageEntity.setCreateUserId(order.getCreator());
			//跳转到流程定义列表页面。
			sysMessageEntity.setForwardUrl(SysMessageHelper.getWfProcessDefineListRouteUrl());
			sysMsgList.add(sysMessageEntity);
		}
		
		try {
			sysMessageService.batchSaveSysMsg(sysMsgList);
		} catch (BizException e) {
			throw new SnakerException(e);
		}
	
	}
	

	@Override
	public Object assign(Execution execution) {
		//不支持此方法的分配
		throw new UnsupportedOperationException("BasePostAssignmentHandler unsupported assign(Execution execution) method!");
	}
	
	
	/**去除重复的参与者id
	 * @param actorIds 返回新的集合
	 * @return
	 */
	private List<String> distinctActorIds(List<String> actorIds) {
		Set<String> set = new HashSet<String>(actorIds);
		return new ArrayList<String>(set);
	}
	
}
