package cn.gdeng.market.lease.controller.workflow.bus;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.snaker.engine.SnakerSpecVariableKeys;
import org.snaker.engine.entity.Order;
import org.snaker.engine.entity.Task;
import org.snaker.engine.helper.DateHelper;
import org.snaker.engine.helper.JsonHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.gdeng.market.dto.lease.contract.ContractMainDTO;
import cn.gdeng.market.dto.workflow.ApprovalContractDTO;
import cn.gdeng.market.dto.workflow.StartContractApprovalDTO;
import cn.gdeng.market.dto.workflow.WfTaskActorDTO;
import cn.gdeng.market.entity.admin.SysMessageEntity;
import cn.gdeng.market.entity.lease.contract.ContractMainEntity;
import cn.gdeng.market.entity.lease.contract.ContractStatementsEntity;
import cn.gdeng.market.enums.ApprovalStatusEnum;
import cn.gdeng.market.enums.ApprovalTypeEnum;
import cn.gdeng.market.enums.Const;
import cn.gdeng.market.enums.MsgCons;
import cn.gdeng.market.enums.SysMessageTypeEnum;
import cn.gdeng.market.enums.WorkflowBusTypeEnum;
import cn.gdeng.market.exception.BizException;
import cn.gdeng.market.lease.bean.AjaxResult;
import cn.gdeng.market.lease.controller.base.BaseController;
import cn.gdeng.market.lease.util.SnakerHelper;
import cn.gdeng.market.service.admin.SysMessageService;
import cn.gdeng.market.service.lease.contract.ContractManageService;
import cn.gdeng.market.service.lease.contract.ContractWorkflowService;
import cn.gdeng.market.service.workflow.bus.WfContractApprovalService;
import cn.gdeng.market.service.workflow.bus.WfOrderService;
import cn.gdeng.market.service.workflow.core.SnakerFacadeService;
import cn.gdeng.market.service.workflow.core.SnakerQueryService;
import cn.gdeng.market.util.Assert;
import cn.gdeng.market.util.SysMessageHelper;

/**合同流程更新操作控制类
 * 
 * @author wjguo
 *
 * datetime:2016年10月17日 下午8:31:03
 */
@Controller
@RequestMapping("contractProcessUpdate")
public class ContractProcessUpdateController extends BaseController {
	
	/**
	 * snaker工作流门面服务类
	 */
	@Autowired
	private SnakerFacadeService snakerFacadeService;
	/**
	 * 合同审批服务类
	 */
	@Autowired
	private WfContractApprovalService wfContractApprovalService;
	/**
	 * 合同管理服务类
	 */
	@Autowired
	private ContractManageService contractManageService;
	/**
	 * 系统消息服务类
	 */
	@Autowired
	private SysMessageService sysMessageService;
	/**
	 * snaker工作流查询服务类
	 */
	@Autowired
	private SnakerQueryService snakerQueryService;
	/**
	 * 合同审批的工作流服务类
	 */
	@Autowired
	private ContractWorkflowService contractWorkflowService;
	/**
	 * 流程实例监控管理服务类
	 */
	@Autowired
	private WfOrderService wfOrderService;
	
	/** 首次合同审批的发起
	 * @param startContractParam 发起合同类型参数
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "startConManage")
	@ResponseBody
	public Object startConManage(StartContractApprovalDTO startContractParam) throws Exception {
		//显示设置发起的合同类型
		startContractParam.setApprovalType(ApprovalTypeEnum.CONTRACT_MANAGER.getCode());
		doStartProcess(startContractParam);
		return new AjaxResult<>();
	}


	
	/** 合同变更审批的发起
	 * @param startContractParam  发起合同类型参数
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "startConChanged")
	@ResponseBody
	public Object startConChanged(StartContractApprovalDTO startContractParam) throws Exception {
		//显示设置发起的合同类型
		startContractParam.setApprovalType(ApprovalTypeEnum.CONTRACT_CHANGED.getCode());
		doStartProcess(startContractParam);
		return new AjaxResult<>();
	}
	
	/** 合同结算审批的发起
	 * @param startContractParam 发起合同类型参数
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "startConClose")
	@ResponseBody
	public Object startConClose(StartContractApprovalDTO startContractParam) throws Exception {
		//显示设置发起的合同类型
		startContractParam.setApprovalType(ApprovalTypeEnum.CONTRACT_CLOSE.getCode());
		doStartProcess(startContractParam);
		return new AjaxResult<>();
	}
	

	
	/**开始流程的发起
	 * @param startConParam 合同参数
	 * @throws BizException
	 */
	private void doStartProcess(StartContractApprovalDTO startConParam) throws BizException {
		//1.数据校验
		checkBeforeStartProcess(startConParam);
		
		//2.生成流程发起参数
		Map<String, Object> variable = getContractVariableForStart(startConParam);
		fullSpeVariableForStart(startConParam, variable);
		
		//3.发起流程
		String userIdStr = super.getUserIdStr();
		snakerFacadeService.startInstanceById(startConParam.getProcessId(), userIdStr, variable);
	}
	
	
	/**发起流程前的数据校验
	 * @param startContractPararm
	 * @throws BizException
	 */
	private void checkBeforeStartProcess(StartContractApprovalDTO startContractPararm) throws BizException {
		//请求参数校验
		Assert.notNull(startContractPararm.getContractMainId(), "关联的合同ID不能为空");
		Assert.notBlankStr(startContractPararm.getProcessId(), "发起的流程ID不能为空");
		Assert.notBlankStr(startContractPararm.getOrderNo(), "流程名称不能为空");
		Assert.notBlankStr(startContractPararm.getOpinion(), "发起说明不能为空");
		
		/*
		 * 分析：
		 * 	合同结算是与合同管理和变更分开表来存储的，故合同结算的审批状态需要额外校验，
		 *  而合同管理和变更的审批状态则直接通过合同主表来判断。
		 * 
		 */
		if (ApprovalTypeEnum.CONTRACT_CLOSE.getCode().
				equals(startContractPararm.getApprovalType())) {
			//合同结算的校验
			ContractStatementsEntity contractStatements = contractWorkflowService.
					getStatementByContractId(startContractPararm.getContractMainId());
			Assert.notNull(contractStatements, "找不到对应的审批合同");
			Assert.notEquals(contractStatements.getApprovalStatus(), 1, "该合同已经发起了审批");
			Assert.notEquals(contractStatements.getApprovalStatus(), 3, "该合同已经审批通过");
		} else {
			//合同管理和合同变更的校验
			ContractMainEntity contractMainEntity = contractManageService.
					getById(startContractPararm.getContractMainId());
			Assert.notNull(contractMainEntity, "找不到对应的审批合同");
			Assert.notEquals(contractMainEntity.getApprovalStatus(), 1, "该合同已经发起了审批");
			Assert.notEquals(contractMainEntity.getApprovalStatus(), 3, "该合同已经审批通过");
		}
	}
	
	/**获取为发起流程的合同变量。
	 * @param startConParam
	 * @return
	 */
	@SuppressWarnings("unchecked")
	private Map<String, Object> getContractVariableForStart(StartContractApprovalDTO startConParam) {
		ApprovalTypeEnum apprTypeEnum = 
				ApprovalTypeEnum.getByCode(startConParam.getApprovalType());
		ContractMainDTO contractMain = null;
		switch (apprTypeEnum) {
			case CONTRACT_MANAGER:
				contractMain = contractWorkflowService.
						contractApprovalStatus(startConParam.getContractMainId());
				break;
			case CONTRACT_CHANGED:
				contractMain = contractWorkflowService.
						contractChangeApprovalStatus(startConParam.getContractMainId());
				break;
			case CONTRACT_CLOSE:
				contractMain = contractWorkflowService.
					contractStatementsApprovalStatus(startConParam.getContractMainId());
				break;
		}
		
		return JsonHelper.fromJson(JsonHelper.toJson(contractMain), Map.class);
		
	}
	
	
	/**为流程发起填充特殊变量
	 * @param startContractParam
	 * @param variable
	 * @throws BizException 
	 */
	private void fullSpeVariableForStart(StartContractApprovalDTO startContractParam, Map<String, Object> variable)
			throws BizException {
		//关联的业务
		variable.put(SnakerSpecVariableKeys.ORDER_BUS_ID_VAR, startContractParam.getContractMainId());
		//操作人基本信息
		variable.put(SnakerSpecVariableKeys.ORDER_OPERATOR_BASE_INFO_VAR, super.getUserBaseInfo());
		//发起的意见
		variable.put(SnakerSpecVariableKeys.ORDER_OPERATOR_OPINION_VAR, startContractParam.getOpinion());
		//发起的实例名称
		variable.put(SnakerSpecVariableKeys.ORDER_ORDER_NO_VAR, startContractParam.getOrderNo());
	}
	
	
	/** 审批 合同管理
	 * @param approConParam
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "approveConManage")
	@ResponseBody
	public Object approveConManage(ApprovalContractDTO approConParam) throws Exception {
		doContractApproval(approConParam);
		return new AjaxResult<>();
	}
	
	/** 审批 合同变更
	 * @param approConParam
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "approveConChanged")
	@ResponseBody
	public Object approveConChanged(ApprovalContractDTO approConParam) throws Exception {
		doContractApproval(approConParam);
		return new AjaxResult<>();
	}
	
	/** 审批 合同结算
	 * @param approConParam
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "approveConClose")
	@ResponseBody
	public Object approveConClose(ApprovalContractDTO approConParam) throws Exception {
		doContractApproval(approConParam);
		return new AjaxResult<>();
	}
	
	/**执行合同的审批
	 * @param approConParam
	 * @throws BizException
	 */
	private void doContractApproval(ApprovalContractDTO approConParam) throws BizException {
		//参数校验
		Assert.notNull(approConParam.getTaskId(), "审批任务ID不能为空");
		Assert.notNull(approConParam.getResult(), "审批结果不能为空");
		Assert.notBlankStr(approConParam.getOpinion(), "审批意见不能为空");
		
		Task task = snakerQueryService.getTask(approConParam.getTaskId());
		Assert.notNull(task, "找不到对应的审批任务");
		Order order = snakerQueryService.getOrder(task.getOrderId());
		Assert.notNull(order, "找不到对应的流程实例");
		//获取流程实例当前所有活动任务的参与者。ps：必须在执行任务审批前查询参与者，否则任务执行完后就无法查询参与者。
		List<WfTaskActorDTO> wfTaskActorList = wfOrderService.getActorsByOrderId(task.getOrderId());
		
		//构造变量
		Map<String, Object> variable = order.getVariableMap();
		fullSpeVariableForApproval(approConParam, variable);
		
		//执行审批
		boolean passedTask = SnakerHelper.isPassedTask(approConParam.getResult());
		if (passedTask) {
			snakerFacadeService.executeTask(approConParam.getTaskId(), super.getUserIdStr(), variable);
		} else if (SnakerHelper.isRejectTask(approConParam.getResult())) {
			//驳回，除需要完成当前task任务外，还需要作废当前整个流程实例
			wfContractApprovalService.terminateTaskAndOrder(approConParam.getTaskId(), super.getUserIdStr(), variable);
			//整个流程实例已作废，需要将结果同步到主合同
			synchApproResultToContractMain(order, false);
		} else {
			throw new BizException(MsgCons.C_31001, MsgCons.M_31001);
		}
		
		//确定发送审批消息的接收者
		List<String> approvalMsgReceivers = getApprovalMsgReceivers(order, approConParam, wfTaskActorList);
		
		//发送审批消息
		sendApprovalMsg(order, task, approConParam, approvalMsgReceivers);
	}
	
	
	/**为流程审批填充特殊变量
	 * @param startContractParam
	 * @param variable
	 * @throws BizException 
	 */
	private void fullSpeVariableForApproval(ApprovalContractDTO approConParam, Map<String, Object> variable)
			throws BizException {
		//操作人基本信息
		variable.put(SnakerSpecVariableKeys.TASK_OPERATOR_BASE_INFO_VAR, super.getUserBaseInfo());
		//发起的意见
		variable.put(SnakerSpecVariableKeys.TASK_OPERATOR_OPINION_VAR, approConParam.getOpinion());
	}
	
	/**同步审批结果到主合同
	 * @param order 流程实例id
	 * @param isPassed 合同最终审批结果是否通过，true表示通过，false表示不通过（即驳回）。
	 * @throws NumberFormatException
	 * @throws BizException
	 */
	private void synchApproResultToContractMain(Order order, boolean isPassed) throws NumberFormatException, BizException {
		WorkflowBusTypeEnum wfTypeEnum = WorkflowBusTypeEnum.getByCode(order.getBusType());
		Integer contractMainId = Integer.valueOf(order.getBusId());
		Byte approvalStatus = null;
		if (isPassed) {
			approvalStatus = ApprovalStatusEnum.YTG.getCode();
		} else {
			approvalStatus = ApprovalStatusEnum.YBH.getCode();
		}
		
		switch (wfTypeEnum) {
			case CONTRACT_MANAGER:
				contractWorkflowService.contractApproval(contractMainId, approvalStatus);
				break;
			case CONTRACT_CHANGED:
				contractWorkflowService.contractChangeApproval(contractMainId, approvalStatus);
				break;
			case CONTRACT_CLOSE:
				contractWorkflowService.contractSettlementApproval(contractMainId, approvalStatus);
				break;
		}
	}
	
	
	/** 获取审批消息的接收者
	 * @param order 流程实例
	 * @param approConParam 合同审批的请求参数
	 * @param wfTaskActorList 当前流程实例的所有活动任务参与者
	 * @throws BizException
	 */
	private List<String> getApprovalMsgReceivers(Order order, ApprovalContractDTO approConParam, List<WfTaskActorDTO> wfTaskActorList) {
		//消息接收者。发给 审批人、流程发起人、抄送人
		List<String> receivers = new ArrayList<String>();
		receivers.add(order.getCreator());
		//前端审批人指定的抄送人
		if (StringUtils.isNotBlank(approConParam.getMsgReceivers())) {
			String[] reces = approConParam.getMsgReceivers().split(",");
			receivers.addAll(Arrays.asList(reces));
		}
		
		for (WfTaskActorDTO actor : wfTaskActorList) {
			receivers.add(actor.getActorId());
		}
		
		return receivers;
	}
	
	/** 发送审批消息
	 * @param order 流程实例
	 * @param task 当前进行审批的任务
	 * @param approConParam 合同审批的请求参数
	 * @param receivers 消息接收者
	 * @throws BizException
	 */
	private void sendApprovalMsg(Order order, Task task, ApprovalContractDTO approConParam, List<String> receivers) throws BizException{
		
		String performTypeName = SnakerHelper.getPerformTypeName(task.getPerformType());
		String approvalResult = SnakerHelper.getTaskApprovalResultName(approConParam.getResult());
		String operatorName = super.getSysUser().getName();
		String orderId = order.getId();
		
		//消息标题，格式：当前流程步骤名称+ 流程类型 + 审批结果
		String title = task.getDisplayName() + performTypeName + approvalResult;
		 /*消息内容，格式如下:
		  *您参与的流程+流程名称+ 流程类型 + 审批结果
          *审批人：当前操作人姓名
		  *审批时间：系统当前时间
		  *审批意见：审批时录入的审批意见
		  */
		String msg = String.format("您参与的流程%s%s%s。\n审批人：%s。\n审批时间：%s。\n审批意见：%s。", 
				order.getOrderNo(), performTypeName, approvalResult, operatorName,
				DateHelper.getTime(), approConParam.getOpinion());
		
		List<SysMessageEntity> sysMsgList = new ArrayList<>();
		for (String receiver : receivers) {
			SysMessageEntity sysMessageEntity = new SysMessageEntity();
			//消息类型
			sysMessageEntity.setMessageType(SysMessageTypeEnum.WF_MESSAGE_APPROVAL_INFO.getCode());
			//消息发送者  为流程发起人
			sysMessageEntity.setSender(order.getCreator());
			//消息接收者
			sysMessageEntity.setReceiver(receiver);
			//消息内容
			sysMessageEntity.setMessageContent(msg);
			//消息标题
			sysMessageEntity.setMessageTitile(title);
			//是否已经读 1 已读 0 未读 默认
			sysMessageEntity.setHasRead(Integer.valueOf(Const.MESSAGE_IS_READ_0));
			//业务json数据
			Map<String, Object> businessMap = new HashMap<String, Object>(1);
			businessMap.put("orderId", orderId);
			sysMessageEntity.setBusinessJson(JsonHelper.toJson(businessMap));
			//状态:默认 1 正常 0 删除
			sysMessageEntity.setStatus(Const.NORMAL);
			sysMessageEntity.setCreateUserId(order.getCreator());
			sysMessageEntity.setForwardUrl(SysMessageHelper.getWfRouteUrl(order.getBusType()));
			sysMsgList.add(sysMessageEntity);
		}
		
		sysMessageService.batchSaveSysMsg(sysMsgList);
	
	}

}









