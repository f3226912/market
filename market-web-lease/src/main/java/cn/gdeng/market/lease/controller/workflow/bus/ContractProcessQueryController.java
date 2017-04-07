package cn.gdeng.market.lease.controller.workflow.bus;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.snaker.engine.SnakerSpecVariableKeys;
import org.snaker.engine.access.QueryFilter;
import org.snaker.engine.entity.HistoryOrder;
import org.snaker.engine.entity.HistoryTask;
import org.snaker.engine.entity.Process;
import org.snaker.engine.entity.Task;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import cn.gdeng.market.dto.admin.SysUserDTO;
import cn.gdeng.market.dto.workflow.ShowContractApproFlowDTO;
import cn.gdeng.market.dto.workflow.ShowContractApproFlowDTO.ApproFlowInfo;
import cn.gdeng.market.dto.workflow.WfProcessDTO;
import cn.gdeng.market.entity.admin.SysUserEntity;
import cn.gdeng.market.entity.lease.contract.ContractMainEntity;
import cn.gdeng.market.enums.ApprovalTypeEnum;
import cn.gdeng.market.enums.Const;
import cn.gdeng.market.exception.BizException;
import cn.gdeng.market.lease.bean.AjaxResult;
import cn.gdeng.market.lease.controller.base.BaseController;
import cn.gdeng.market.lease.util.SnakerHelper;
import cn.gdeng.market.service.admin.SysUserService;
import cn.gdeng.market.service.lease.contract.ContractManageService;
import cn.gdeng.market.service.workflow.bus.WfContractApprovalService;
import cn.gdeng.market.service.workflow.bus.WfSequenceService;
import cn.gdeng.market.service.workflow.core.SnakerFacadeService;
import cn.gdeng.market.service.workflow.core.SnakerOrderService;
import cn.gdeng.market.service.workflow.core.SnakerQueryService;
import cn.gdeng.market.util.Assert;

/**合同流程查询控制类
 * 
 * @author wjguo
 *
 * datetime:2016年10月17日 下午8:31:03
 */
@Controller
@RequestMapping("contractProcessQuery")
public class ContractProcessQueryController extends BaseController {
	/**
	 * snaker工作流门面服务类
	 */
	@Autowired
	private SnakerFacadeService snakerFacadeService;
	/**
	 * snaker工作流查询服务类
	 */
	@Autowired
	private SnakerQueryService snakerQueryService;
	/**
	 * 工作流序列服务类
	 */
	@Autowired
	private WfSequenceService wfSequenceService;
	/**
	 * 合同管理服务类
	 */
	@Autowired
	private ContractManageService contractManageService;
	/**
	 * 系统用信息服务类
	 */
	@Autowired
	private SysUserService sysUserService;
	/**
	 * 工作流合同审批服务类
	 */
	@Autowired
	private WfContractApprovalService wfContractApprovalService;
	
	
	/** 首次合同审批的发起预备
	 * @param contractMainId 主合同id
	 * @param processId 流程id
	 * @param mv 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "prepareStartConManage")
	public ModelAndView prepareStartConManage(Integer contractMainId, String processId, ModelAndView mv) 
			throws Exception {
		//显示设置发起的合同类型
		checkPrepareStartProcess(contractMainId, processId, ApprovalTypeEnum.CONTRACT_MANAGER.getCode());
		String orderNo = wfSequenceService.generalOrderNoForContractInfo(processId);
		
		//set属性
		mv.addObject("orderNo", orderNo);
		mv.addObject("contractMainId", contractMainId);
		mv.addObject("processId", processId);
		
		mv.setViewName("workflow/approval/contract/conManagerStart");
		return mv;
	}


	/** 合同变更审批的发起预备
	 * @param startContractParam  发起合同类型参数
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "prepareStartConChanged")
	public ModelAndView prepareStartConChanged(Integer contractMainId, String processId, ModelAndView mv) 
			throws Exception {
		//显示设置发起的合同类型
		checkPrepareStartProcess(contractMainId, processId, ApprovalTypeEnum.CONTRACT_CHANGED.getCode());
		String orderNo = wfSequenceService.generalOrderNoForContractInfo(processId);
		
		//set属性
		mv.addObject("orderNo", orderNo);
		mv.addObject("contractMainId", contractMainId);
		mv.addObject("processId", processId);
		
		mv.setViewName("workflow/approval/contract/conChangedStart");
		return mv;
	}
	
	/** 合同结算审批的发起预备
	 * @param startContractParam 发起合同类型参数
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "prepareStartConClose")
	public ModelAndView prepareStartConClose(Integer contractMainId, String processId, ModelAndView mv) 
			throws Exception {
		//显示设置发起的合同类型
		checkPrepareStartProcess(contractMainId, processId, ApprovalTypeEnum.CONTRACT_CLOSE.getCode());
		String orderNo = wfSequenceService.generalOrderNoForContractInfo(processId);
		
		//set属性
		mv.addObject("orderNo", orderNo);
		mv.addObject("contractMainId", contractMainId);
		mv.addObject("processId", processId);
		
		mv.setViewName("workflow/approval/contract/conCloseStart");
		return mv;
	}
	
	/**预备发起流程前的数据校验
	 * @param startContractPararm
	 * @throws BizException
	 */
	private void checkPrepareStartProcess(Integer contractMainId, String processId, Byte approvalType) 
			throws BizException {
		//请求参数校验
		Assert.notNull(contractMainId, "关联的合同ID不能为空");
		Assert.notBlankStr(processId, "发起的流程ID不能为空");
		
		//合同信息校验
		ContractMainEntity contractMainEntity = contractManageService.
				getById(contractMainId);
		Assert.notNull(contractMainEntity, "找不到对应的审批合同");
		Assert.isEquals(contractMainEntity.getIsCancel(), 0, "审批的合同已经作废");
		Assert.isEquals(contractMainEntity.getIsDeleted(), 0, "审批的合同已经删除");
		
		//流程校验
		Process process = snakerFacadeService.getProcessById(processId);
		Assert.notNull(process, "找不到对应的发起流程");
		Assert.notEquals(process.getState(), 2, "发起的流程已经被删除");
		Assert.notEquals(process.getState(), 0, "发起的流程已经被禁用");
	}
	

	/**跳转到合同变更审批页面。
	 * ps:判断不同身份者进入此页面，会跳转至不同结构的审批页面。
	 * @param orderId 流程实例id
	 * @param taskId 指定需要进行审批的任务id
	 * @param mv
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "toConChanged")
	public ModelAndView toConChanged(String orderId, String taskId, ModelAndView mv) 
			throws Exception {
		boolean isNeedApproval = builderFlowAndApproDataIfNecessary(orderId, taskId, mv);
		if (isNeedApproval) {
			mv.setViewName("workflow/approval/contract/conChangedAppro");
		} else {
			mv.setViewName("workflow/approval/contract/conChangedShow");
		}
		return mv;
	}
	
	/**跳转到合同结算审批页面。
	 * ps:判断不同身份者进入此页面，会跳转至不同结构的审批页面。
	 * @param orderId 流程实例id
	 * @param taskId 指定需要进行审批的任务id
	 * @param mv
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "toConClose")
	public ModelAndView toConClose(String orderId, String taskId, ModelAndView mv) 
			throws Exception {
		
		boolean isNeedApproval = builderFlowAndApproDataIfNecessary(orderId, taskId, mv);
		if (isNeedApproval) {
			mv.setViewName("workflow/approval/contract/conCloseAppro");
		} else {
			mv.setViewName("workflow/approval/contract/conCloseShow");
		}
		return mv;
	}
	
	/**跳转到合同管理审批页面。
	 * ps:判断不同身份者进入此页面，会跳转至不同结构的审批页面。
	 * @param orderId 流程实例id
	 * @param taskId 指定需要进行审批的任务id
	 * @param mv
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "toConManage")
	public ModelAndView toConManage(String orderId, String taskId, ModelAndView mv) 
			throws Exception {
		boolean isNeedApproval = builderFlowAndApproDataIfNecessary(orderId, taskId, mv);
		if (isNeedApproval) {
			mv.setViewName("workflow/approval/contract/conManagerAppro");
		} else {
			mv.setViewName("workflow/approval/contract/conManagerShow");
		}
		return mv;
	}
	
	
	/**构建工作流的审批步骤流数据，如果需要则继续构建审批流程需要的数据。
	 * @param orderId 流程实例id
	 * @param mv
	 * @return
	 * @throws BizException
	 */
	private boolean builderFlowAndApproDataIfNecessary(String orderId, String taskId, ModelAndView mv) throws BizException {
		/*查询流程实例历史，是为了防止流程实例结束无法查询到结果的情况。
		 *ps：流程发起时，会同时存储流程实例表和流程实例历史表。
		 */
		HistoryOrder histOrder = snakerFacadeService.getHistOrder(orderId);
		List<ShowContractApproFlowDTO> approFlows = constructApproFlow(histOrder);
		//主合同id
		mv.addObject("contractMainId", histOrder.getBusId());
		mv.addObject("approFlows", approFlows);
		
		//是否需要进行审批操作
		boolean isNeedApproval = false;
		
		//判断是否有活动任务，如果有则需要进行审批
		Task activeTask = null;
		if (StringUtils.isNotBlank(taskId)) {
			activeTask = getActiveTaskAndPrivilegeJudge(taskId);
		} else {
			//如果没有指定审批任务，则获取当前用户可以审批该流程实例的一个任务进行审批。
			activeTask = getMyOneActiveTask(histOrder);
		}
		
		if (activeTask != null) {
			//需要做审批操作，查询出审批需要的相关信息。
			Map<String, Object> approvalBaseInfo = packageApprovalBaseInfo(histOrder, activeTask);
			mv.addObject("approvalBaseInfo", approvalBaseInfo);
			isNeedApproval = true;
		}
		
		return isNeedApproval;
	}
	

	
	
	/**构建审批流信息
	 * @param histOrder 历史流程实例
	 * @return
	 */
	private List<ShowContractApproFlowDTO> constructApproFlow(HistoryOrder histOrder) {

		//审批的节点流集合，元素按审批顺序排列。
		List<ShowContractApproFlowDTO> nodeFlowList = new ArrayList<ShowContractApproFlowDTO>();
		
		//构建起始节点
		constructStartNodeInfo(histOrder, nodeFlowList);
		//构建审批过程节点
		constructApproNodeInfo(histOrder, nodeFlowList);
		//构造流程作废节点
		constructAbolishedNodeInfo(histOrder, nodeFlowList);
		//构建结束节点
		constructEndNodeInfo(histOrder, nodeFlowList);
		return nodeFlowList;
	}
	
	
	/**构建流程发起节点信息
	 * @param histOrder 历史流程实例
	 * @param nodeFlowList 节点流集合。
	 */
	private void constructStartNodeInfo(HistoryOrder histOrder , List<ShowContractApproFlowDTO> nodeFlowList) {
		Map<String, Object> histOrderVarMap = histOrder.getVariableMap();
		
		//构造节点对象数据
		ShowContractApproFlowDTO swhowContractApproFlow = new ShowContractApproFlowDTO(1);
		ApproFlowInfo approFlowInfo = new ApproFlowInfo();
		approFlowInfo.setOperateBaseInfo(histOrderVarMap.
				get(SnakerSpecVariableKeys.ORDER_OPERATOR_BASE_INFO_VAR).toString());
		approFlowInfo.setOpinion(histOrderVarMap.
				get(SnakerSpecVariableKeys.ORDER_OPERATOR_OPINION_VAR).toString());
		approFlowInfo.setTaskDisplayName("发起");
		//流程的发起，相当于发起节点的完成时间。
		approFlowInfo.setFinshTime(trimTimeSecond(histOrder.getCreateTime()));
		swhowContractApproFlow.addApproFlowInfo(approFlowInfo);
		
		//保存到总集合中
		nodeFlowList.add(swhowContractApproFlow);
	}
	
	
	/**构建流程审批节点信息
	 * @param histOrder 历史流程实例
	 * @param nodeFlowList 节点流集合。
	 */
	private void constructApproNodeInfo(HistoryOrder histOrder , List<ShowContractApproFlowDTO> nodeFlowList) {
		//查询所有的历史任务。查询结果按完成时间升序。
		QueryFilter queryFilter = new QueryFilter();
		queryFilter.setOrderId(histOrder.getId());
		queryFilter.setOrder(QueryFilter.ASC);
		queryFilter.setOrderBy("finish_Time");
		List<HistoryTask> historyTasks = snakerQueryService.getHistoryTasks(queryFilter);
		
		//封装审批任务节点的信息。ps：一个任务节点可能存在多个任务，需要统一封装在一起。
		ShowContractApproFlowDTO swhowContractApproFlow = new ShowContractApproFlowDTO();
		for (int i = 0, len = historyTasks.size(); i < len; i++) {
			HistoryTask hisTask = historyTasks.get(i);
			//因为驳回的同时会终止流程，故驳回任务一定是当前流程审批的最后个执行任务，故直接跳出循环即可。
			if (SnakerHelper.isRejectTask(hisTask.getTaskState())) {
				//加个null标识，提示任务流程有驳回操作。
				nodeFlowList.add(null);
				break;
			}
			
			ApproFlowInfo approFlowInfo = constructApproNodeInfo(hisTask);
			swhowContractApproFlow.addApproFlowInfo(approFlowInfo);
			
			//预判断当前任务与下一个任务是否需要进行合并，如果需要则共用swhowContractApproFlow任务节点对象。
			boolean isNeedMergeTask = false;
			if ((i + 1) < len) {
				HistoryTask nextHisask = historyTasks.get( i + 1);
				isNeedMergeTask = isNeedMergeTask(hisTask, nextHisask);
			}
			if (!isNeedMergeTask) {
				nodeFlowList.add(swhowContractApproFlow);
				//重新生成审批流对象，装载下一个审批任务节点的信息。
				swhowContractApproFlow = new ShowContractApproFlowDTO(5);
				
			}
		
		}
	}

	/**构造流程审批节点信息
	 * @param hisTask 历史任务
	 * @return
	 */
	private ApproFlowInfo constructApproNodeInfo(HistoryTask hisTask) {
		Map<String, Object> histTaskVarMap = hisTask.getVariableMap();
		
		ApproFlowInfo approFlowInfo = new ApproFlowInfo();
		
		approFlowInfo.setFinshTime(trimTimeSecond(hisTask.getFinishTime()));
		
		approFlowInfo.setOperateBaseInfo((String) histTaskVarMap.
				get(SnakerSpecVariableKeys.TASK_OPERATOR_BASE_INFO_VAR));
		
		approFlowInfo.setOpinion((String) histTaskVarMap.
				get(SnakerSpecVariableKeys.TASK_OPERATOR_OPINION_VAR));
		
		approFlowInfo.setTaskDisplayName(hisTask.getDisplayName());
		
		return approFlowInfo;
	}
	

	
	/**判断两个任务是否同属于一个任务节点
	 * @param one
	 * @param two
	 * @return true表示同属于一个任务，反之false。
	 */
	private boolean isSameTaskNode(HistoryTask one, HistoryTask two) {
		/*
		 * 两个任务是否同属于一个任务节点的要求：
		 * 	1、必须都是会签任务(一个会签任务节点，可能存在多个任务，如果是审批任务节点，则一定只有一个任务)。
		 * 	2、任务名称必须相同。
		 * 	3、任务显示名称必须相同。
		 * 
		 */
		if (!SnakerHelper.isPerformAll(one.getPerformType()) || !SnakerHelper.isPerformAll(two.getPerformType())) {
			return false;
		}
		return one.getTaskName().equals(two.getTaskName()) && one.getDisplayName().equals(two.getDisplayName());
	}
	
	
	
	
	/**判断两个任务之间是否需要进行合并
	 * @param one 当前任务
	 * @param two 下一个任务
	 * @return true表示需要合并，反之false。
	 */
	private boolean isNeedMergeTask(HistoryTask one, HistoryTask two) {
		//合并要求：必须是同属于相同的任务节点，并且两个任务都不允许是驳回状态的任务。
		return isSameTaskNode(one, two) 
				&& !SnakerHelper.isRejectTask(two.getTaskState()) 
				&& !SnakerHelper.isRejectTask(one.getTaskState());
	}
	
	
	/**构建流程实例作废的节点信息
	 * @param histOrder 历史流程实例
	 * @param nodeFlowList 节点流集合。
	 */
	private void constructAbolishedNodeInfo(HistoryOrder histOrder , List<ShowContractApproFlowDTO> nodeFlowList) {
		//流程未作废，不需要构造作废的节点信息。
		if (!SnakerHelper.isOrderAbolished(histOrder.getOrderState())) {
			return ;
		}
		
		//作废信息都以json格式存储在remakr备注字段中。
		Map<String, Object> remarkMap = histOrder.getRemarkMap();
		
		String operateBaseInfo = (String) remarkMap.get(SnakerOrderService.REMARK_OPERATOR_INFO_VAR);
		String opinion = (String) remarkMap.get(SnakerOrderService.REMARK_DESC_VAR);
		
		//构造节点对象数据
		ShowContractApproFlowDTO showContractApproFlow = new ShowContractApproFlowDTO();
		ApproFlowInfo approFlowInfo = new ApproFlowInfo();
		approFlowInfo.setOperateBaseInfo(operateBaseInfo);
		approFlowInfo.setOpinion(opinion);
		//流程终止时间，相当于作废的完成时间。
		approFlowInfo.setFinshTime(trimTimeSecond(histOrder.getEndTime()));
		
		//如果默认元素为null标识，则说明是驳回任务导致流程实例作废
		if (nodeFlowList.get(nodeFlowList.size() - 1) == null) {
			approFlowInfo.setTaskDisplayName("<font color='#cd0000'>流程驳回（作废）</font>");
			//移除null标识 。
			nodeFlowList.remove(nodeFlowList.size() - 1);
		} else {
			approFlowInfo.setTaskDisplayName("<font color='#cd0000'>流程作废</font>");
		}
		
		showContractApproFlow.addApproFlowInfo(approFlowInfo);
		//保存到总集合中
		nodeFlowList.add(showContractApproFlow);
	}
	
	/**构建流程实例的结束节点信息
	 * @param histOrder 历史流程实例
	 * @param nodeFlowList 节点流集合。
	 */
	private void constructEndNodeInfo(HistoryOrder histOrder , List<ShowContractApproFlowDTO> nodeFlowList) {
		//流程未归档，不需要构造结束节点信息。
		if (!SnakerHelper.isOrderFinished(histOrder.getOrderState())) {
			return ;
		}
		//构造节点对象数据
		ShowContractApproFlowDTO swhowContractApproFlow = new ShowContractApproFlowDTO(1);
		ApproFlowInfo approFlowInfo = new ApproFlowInfo();
		approFlowInfo.setOperateBaseInfo("工作流引擎");
		//系统自动归档，无任何的审核意见。
		approFlowInfo.setOpinion("");
		approFlowInfo.setTaskDisplayName("归档");
		approFlowInfo.setFinshTime(trimTimeSecond(histOrder.getEndTime()));
		swhowContractApproFlow.addApproFlowInfo(approFlowInfo);
		
		//保存到总集合中
		nodeFlowList.add(swhowContractApproFlow);
	}
	
	
	/** 根据taskId获取活动任务，并校验当前用户是否有权限进行审批操作。
	 * @param taskId
	 * @return
	 * @throws BizException
	 */
	private Task getActiveTaskAndPrivilegeJudge(String taskId) throws BizException {
		//如果是指定审批任务 ，则必须要找到当前任务数据
		Task activeTask = snakerQueryService.getTask(taskId);
		Assert.notNull(activeTask, "找不到指定的任务！");
		String[] actors = snakerQueryService.getTaskActorsByTaskId(taskId);
		Assert.notNull(actors, "当前用户没权限审批该任务！");
		String userIdStr = super.getUserIdStr();
		boolean hasPrivilegeApproval = false;
		for (String actor : actors) {
			if (userIdStr.equals(actor)) {
				hasPrivilegeApproval = true;
				break;
			}
		}
		Assert.isTrue(hasPrivilegeApproval, "当前用户没权限审批该任务！");
		return activeTask;
	}
	
	/** 获取需要当前用户做审批的活动任务,如果存在多个活动任务，则只返回一个。
	 * @param histOrder 历史流程实例
	 * @return
	 * @throws BizException 
	 */
	private Task getMyOneActiveTask(HistoryOrder histOrder) throws BizException {
		if (!SnakerHelper.isOrderAbolished(histOrder.getOrderState())) {
			QueryFilter queryFilter = new QueryFilter();
			queryFilter.setOrderId(histOrder.getId());
			queryFilter.setOperator(super.getUserIdStr());
			List<Task> activeTasks = snakerQueryService.getActiveTasks(queryFilter);
			if (activeTasks.size() > 0) {
				//对当前流程实例有审批的任务，需要做审批操作。
				return activeTasks.get(0);
			}
		}
		return null;
	}
	
	/**封装审批操作需要的基本信息
	 * @param histOrder
	 * @param Task 需要审批的活动任务
	 * @return
	 * @throws BizException 
	 */
	private Map<String, Object> packageApprovalBaseInfo(HistoryOrder histOrder, Task activeTask) throws BizException {
		Map<String, Object> baseInfoMap = new HashMap<String, Object>();
		
		baseInfoMap.put("orderNo", histOrder.getOrderNo());
		
		SysUserEntity creator = sysUserService.getEntityById(Integer.valueOf(histOrder.getCreator()));
		baseInfoMap.put("creatorName", creator.getName());
		
		List<SysUserDTO> taskActors = 
				wfContractApprovalService.queryOrderActiveTaskActor(histOrder.getId());
		if (taskActors.size() > 0) {
			StringBuilder taskActorsStr = new StringBuilder();
			for (SysUserDTO user : taskActors) {
				taskActorsStr.append(user.getName()).append("、");
			}
			//删除多余的逗号
			taskActorsStr.deleteCharAt(taskActorsStr.length() -1);
			baseInfoMap.put("taskActors", taskActorsStr.toString());
		}
		
		baseInfoMap.put("taskDisplayName", activeTask.getDisplayName());
		baseInfoMap.put("taskPerformType", SnakerHelper.getPerformTypeName(activeTask.getPerformType()));
		baseInfoMap.put("taskId", activeTask.getId());
		return baseInfoMap;
	}
	
	/**截取掉时间的秒数部分
	 * @param dateTime
	 * @return
	 */
	private String trimTimeSecond(String dateTime) {
		if (StringUtils.isBlank(dateTime)) {
			return "";
		}
		return dateTime.substring(0, dateTime.length() - 3);
	}
	
	/**获取流程列表
	 * @param busType 工作流业务类型code码
	 * @param orgId 组织架构id
	 * @return
	 * @throws BizException
	 */
	@RequestMapping(value = "queryProcessList")
	@ResponseBody
	public Object queryProcessList(String busType, Integer orgId) throws BizException {
		//数据校验
		Assert.notNull(busType, "工作流业务类型不能为空");
		Assert.notNull(orgId, "组织架构ID不能为空");
		
		AjaxResult<List<Map<String ,Object>>> result = new AjaxResult<>();
		Map<String, Object> map = new HashMap<>();
		//只查询可用流程模板
		map.put("state", Const.WF_PROCESS_STATE_1);
		//查询的组织架构id
		List<Integer> orgIdList = new ArrayList<Integer>(1);
		orgIdList.add(orgId);
		map.put("orgIdList",orgIdList);
		//业务类型
		map.put("busType", busType);
		List<WfProcessDTO> list = contractManageService.queryProcessList(map);
		List<Map<String ,Object>> resultList = new ArrayList<>();
		for (WfProcessDTO dto : list) {
			Map<String ,Object> resultMap = new HashMap<>();
			resultMap.put("id", dto.getId());
			resultMap.put("displayName", dto.getDisplayName());
			resultList.add(resultMap);
		}
		result.setResult(resultList);
		return result;
	}
	
}



















