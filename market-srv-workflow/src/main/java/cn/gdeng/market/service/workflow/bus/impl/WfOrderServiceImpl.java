package cn.gdeng.market.service.workflow.bus.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.snaker.engine.SnakerException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;
import com.gudeng.framework.dba.transaction.annotation.Transactional;
import com.gudeng.framework.dba.util.DalUtils;

import cn.gdeng.market.dao.base.BaseDao;
import cn.gdeng.market.dto.admin.SysUserDTO;
import cn.gdeng.market.dto.bean.GuDengPage;
import cn.gdeng.market.dto.workflow.WfOrderDTO;
import cn.gdeng.market.dto.workflow.WfTaskActorDTO;
import cn.gdeng.market.dto.workflow.WfTaskDTO;
import cn.gdeng.market.entity.admin.SysMessageEntity;
import cn.gdeng.market.entity.workflow.bus.WfbActorChangeRecordEntity;
import cn.gdeng.market.enums.ApprovalStatusEnum;
import cn.gdeng.market.enums.Const;
import cn.gdeng.market.enums.SysMessageTypeEnum;
import cn.gdeng.market.enums.WorkflowBusTypeEnum;
import cn.gdeng.market.exception.BizException;
import cn.gdeng.market.service.admin.SysUserService;
import cn.gdeng.market.service.lease.contract.ContractWorkflowService;
import cn.gdeng.market.service.workflow.bus.WfOrderService;
import cn.gdeng.market.service.workflow.bus.WfTaskService;
import cn.gdeng.market.service.workflow.core.SnakerOrderService;
import cn.gdeng.market.service.workflow.core.SnakerTaskService;
import cn.gdeng.market.util.DateUtil;
import cn.gdeng.market.util.SysMessageHelper;
import cn.gdeng.market.util.WorkflowHelper;

/**
 * 流程监控管理
 * @author xphou
 */
@Service(value = "wfOrderService")
public class WfOrderServiceImpl implements WfOrderService{

	@Autowired
	private BaseDao<?> baseDao;
	
	@Autowired
	private WfTaskService wfTaskService;
	
	@Autowired
	private SnakerOrderService snakerOrderService;

	@Autowired
	private SnakerTaskService snakerTaskService;
	
	@Autowired
	private SysUserService sysUserService;
	
	@Autowired
	private ContractWorkflowService contractWorkflowService;
	/**
	 * 查询分页条数
	 * @param param
	 * @return
	 * @throws BizException
	 */
	@Override
	public int countByCondition(Map<String, Object> param) throws BizException {
		
		return baseDao.queryForObject("WfOrder.countByCondition", param, Integer.class);
	}

	/**
	 * 查询分页
	 * @param page
	 * @return
	 * @throws BizException
	 */
	@Override
	public GuDengPage<WfOrderDTO> queryByConditionPage(GuDengPage<WfOrderDTO> page) throws BizException {
		Map<String,Object> param = page.getParaMap();
		int total = countByCondition(param);
		List<WfOrderDTO>list = null;
		if(total > 0 ){
			list = baseDao.queryForList("WfOrder.queryByConditionPage", param, WfOrderDTO.class);
			for (int i = 0; i < list.size(); i++) {
				WfOrderDTO dto = list.get(i);
				WfTaskDTO reqTaskDto = new WfTaskDTO();
				reqTaskDto.setOrderId(dto.getId());
				//查询实例流程当前任务
				List<WfTaskDTO> taskDtoList = baseDao.queryForList("WfTask.queryByOrderId", reqTaskDto, WfTaskDTO.class);
				if(taskDtoList.size() > 0 ){
					list.get(i).setWfTaskDTO(taskDtoList.get(0));
				}
				
				if(dto.getBusType().equals(WorkflowBusTypeEnum.CONTRACT_MANAGER.getCode())){//流程模板业务类型：合同首次审批 
					list.get(i).setBusType(WorkflowBusTypeEnum.CONTRACT_MANAGER.getDesc());
					
				} else if(dto.getBusType().equals(WorkflowBusTypeEnum.CONTRACT_CHANGED.getCode())){//流程模板业务类型：合同变更 
					list.get(i).setBusType(WorkflowBusTypeEnum.CONTRACT_CHANGED.getDesc());
					
				} else if(dto.getBusType().equals(WorkflowBusTypeEnum.CONTRACT_CLOSE.getCode())){//流程模板业务类型：合同结算
					list.get(i).setBusType(WorkflowBusTypeEnum.CONTRACT_CLOSE.getDesc());
				}
			}
			
		}
		page.setRecordList(list).setTotal(total);
		return page;
	}

	/**
	 * 流程作废
	 * @param orderId
	 * @param userInfo
	 * @throws BizException
	 */
	@Override
	@Transactional
	public String terminate(String orderId,Map<String, Object> map,SysUserDTO userInfo) throws BizException {
		
		//根据流程ID获取流程发起人
		WfOrderDTO orderDto = getById(orderId);
		String creator = orderDto.getCreator();
		//业务类型
		String busType = orderDto.getBusType();
		//根据流程ID获取流程当前步骤责任人
		List<WfTaskActorDTO> wfTaskActorDTOList = getActorsByOrderId(orderId);
		List<WfTaskDTO> taskList = getActorList(orderId);
		map.put(SnakerOrderService.REMARK_CUR_TASK_DISPLAY_NAME_VAR, taskList.get(0).getDisplayName());
		map.put(SnakerOrderService.REMARK_CUR_TASK_PERFORM_TYPE_VAR, taskList.get(0).getPerformType());
		map.put(SnakerOrderService.REMARK_CUR_TASK_NAME_VAR, taskList.get(0).getTaskName());
		JSON json = (JSON) JSON.toJSON(map);
		//把流程创建人加到发送消息列表中
		WfTaskActorDTO creatorDto = new WfTaskActorDTO();
		creatorDto.setActorId(creator);
		wfTaskActorDTOList.add(creatorDto);
		/*给流程发起人、当前步骤责任人发放消息
		消息格式如下：
		消息 头：流程名称+“流程作废”
		消息内容：您参与的流程+流程名称+ 已作废
		作废人：当前操作人姓名
		作废时间：系统当前时间
		作废原因：录入的作废原因*/
		String title = getById(orderId).getOrderNo() + "流程作废";
		String messageContent = "您参与的流程"+getById(orderId).getOrderNo()+"已作废\n";
		messageContent +="作废人:"+userInfo.getName()+"\n";
		messageContent +="作废时间:"+DateUtil.toString(new Date(), DateUtil.DATE_FORMAT_DATETIME)+"\n";
		messageContent +="作废原因:"+ map.get(SnakerOrderService.REMARK_DESC_VAR).toString();
		//作废操作
		snakerOrderService.terminate(orderId, json.toString());
		
		WorkflowBusTypeEnum wfBusTypeEnum = WorkflowBusTypeEnum.getByCode(orderDto.getBusType());
		if (WorkflowHelper.isContractApproBusType(wfBusTypeEnum)) {
			try {
				//审批流程能走到结束节点，则说明前面的所有任务都审批通过，即等同于当前合同审批通过。
				Byte approvalStatus = ApprovalStatusEnum.YBH.getCode();
				Integer contractMainId = Integer.valueOf(orderDto.getBusId());
				switch (wfBusTypeEnum) {
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
			} catch (NumberFormatException | BizException e) {
				throw new SnakerException(e);
			}
		}
		
		List<SysMessageEntity> list = new ArrayList<>();
		for (WfTaskActorDTO dto : wfTaskActorDTOList) {
			SysMessageEntity sysMessageEntity = new SysMessageEntity();
			//消息类型
			sysMessageEntity.setMessageType(SysMessageTypeEnum.WF_MESSAGE_TERMINATE.getCode());
			//消息发送者-流程发起人
			sysMessageEntity.setSender(orderDto.getCreator());
			//消息接收者
			sysMessageEntity.setReceiver(dto.getActorId());
			//消息内容
			sysMessageEntity.setMessageContent(messageContent);
			//消息标题
			sysMessageEntity.setMessageTitile(title);
			//是否已经读 1 已读 0 未读 默认
			sysMessageEntity.setHasRead(Integer.valueOf(Const.MESSAGE_IS_READ_0));
			//业务id
			sysMessageEntity.setBusinessId(0);
			//业务json数据
			Map<String, Object> businessJsonMap = new HashMap<>();
			businessJsonMap.put("orderId", orderId);
			sysMessageEntity.setBusinessJson(JSON.toJSONString(businessJsonMap));
			//状态:默认 1 正常 0 删除
			sysMessageEntity.setStatus(Const.NORMAL);
			sysMessageEntity.setCreateUserId(userInfo.getId().toString());
			sysMessageEntity.setUpdateUserId(userInfo.getId().toString());
			//跳转的url地址，如果无需跳转，则为null即可。跳转地址会带上当前消息的id作为请求参数。
			sysMessageEntity.setForwardUrl(SysMessageHelper.getWfRouteUrl(busType));
			list.add(sysMessageEntity);
		}
		int len = list.size();
		Map<String, Object>[] batchValues = new HashMap[len];
		for (int i = 0; i < len; i++) {
			SysMessageEntity entity = list.get(i);
			batchValues[i] = DalUtils.convertToMap(entity);
		}
		baseDao.batchUpdate("SysMessage.insert", batchValues);
		return orderId;
	}

	/**
	 * 根据id查询
	 * @param id
	 * @return
	 * @throws BizException
	 */
	@Override
	public WfOrderDTO getById(String id) throws BizException {
		Map<String,Object> map = new HashMap<>();
		map.put("id", id);
		return baseDao.queryForObject("WfOrder.getById", map,WfOrderDTO.class);
	}
	
	/**
	 * 根据实例ID获取任务以及当前责任人
	 * @param orderId
	 * @return
	 */
	@Override
	public List<WfTaskDTO> getActorList(String orderId) throws BizException {
		//根据实例ID获取任务列表
		WfTaskDTO taskDto = new WfTaskDTO();
		taskDto.setOrderId(orderId);
		List<WfTaskDTO> list = wfTaskService.queryByOrderId(taskDto);
		return list;
	}


	/**
	 * 更改任务参与者
	 * @param map
	 * @param oldActor
	 * @param newActor
	 */
	@Override
	@Transactional
	public int[] taskActorChanged(List<Map<String,String>> list,SysUserDTO userInfo,String orderId) throws BizException{
		int len = list.size();
		Map<String, Object>[] batchValues = new HashMap[len];
		for (int i = 0; i < len; i++) {
			Map<String, String> map = list.get(i);
			WfbActorChangeRecordEntity entity = new WfbActorChangeRecordEntity();
			String taskId = map.get("taskId");
			String oldActor = map.get("oldActor");
			String newActorId = map.get("newActor");
			entity.setTaskId(taskId);
			entity.setOldActorId(Integer.valueOf(oldActor));
			entity.setNewActorId(Integer.valueOf(newActorId));
			entity.setOperatorId(userInfo.getId());
			batchValues[i] = DalUtils.convertToMap(entity);
			//更改任务参与者
			snakerTaskService.taskActorChanged(taskId, oldActor, newActorId);
		}
		//更新工作流任务责任人替换记录表
		int[] id = baseDao.batchUpdate("WfbActorChangeRecord.insert", 
				batchValues);
		
		//根据流程ID获取流程发起人
		WfOrderDTO orderDto = getById(orderId);
		String creator = orderDto.getCreator();
		//业务类型
		String busType = orderDto.getBusType();
		/*给流程发起人、当前步骤责任人发放消息
		消息格式如下：
		消息 头：流程名称+“流程作废”
		消息内容：您参与的流程+流程名称+ 已作废
		作废人：当前操作人姓名
		作废时间：系统当前时间
		作废原因：录入的作废原因*/		
		//流程名称
		String orderNo = orderDto.getOrderNo();
		//步骤名称
		WfTaskDTO taskDto = new WfTaskDTO();
		taskDto.setOrderId(orderId);
		String taskDisplayName = wfTaskService.queryByOrderId(taskDto).get(0).getDisplayName();
		
		String title = orderNo + "重设责任人";
		String messageContent = "您发起的流程" + orderNo + taskDisplayName + "\n";
		for (Map<String,String> map : list) {
			//旧责任人信息
			SysUserDTO oldUserDto = sysUserService.getById(Integer.valueOf(map.get("oldActor")));
			//旧责任人信息
			SysUserDTO newUserDto = sysUserService.getById(Integer.valueOf(map.get("newActor")));
			messageContent +="原责任人:"+oldUserDto.getName() + "-->";
			messageContent +="新责任人:"+newUserDto.getName() + "\n";
		}
		
		messageContent +="重置时间:"+DateUtil.toString(new Date(), DateUtil.DATE_FORMAT_DATETIME)+"\n";
		messageContent +="重置人:"+ userInfo.getName();
		//设置消息实体
		SysMessageEntity sysMessageEntity = new SysMessageEntity();
		//消息类型
		sysMessageEntity.setMessageType(SysMessageTypeEnum.WF_MESSAGE_RESET_ACTOR.getCode());
		//消息发送者-流程发起人
		sysMessageEntity.setSender(orderDto.getCreator());
		//消息接收者
		sysMessageEntity.setReceiver(creator);
		//消息内容
		sysMessageEntity.setMessageContent(messageContent);
		//消息标题
		sysMessageEntity.setMessageTitile(title);
		//是否已经读 1 已读 0 未读 默认
		sysMessageEntity.setHasRead(Integer.valueOf(Const.MESSAGE_IS_READ_0));
		//业务id
		sysMessageEntity.setBusinessId(0);
		//业务json数据
		Map<String, Object> businessJsonMap = new HashMap<>();
		businessJsonMap.put("orderId", orderId);
		sysMessageEntity.setBusinessJson(JSON.toJSONString(businessJsonMap));
		//状态:默认 1 正常 0 删除
		sysMessageEntity.setStatus(Const.NORMAL);
		sysMessageEntity.setCreateUserId(userInfo.getId().toString());
		sysMessageEntity.setUpdateUserId(userInfo.getId().toString());
		//跳转的url地址，如果无需跳转，则为null即可。跳转地址会带上当前消息的id作为请求参数。
		sysMessageEntity.setForwardUrl(SysMessageHelper.getWfRouteUrl(busType));
		baseDao.execute("SysMessage.insert", sysMessageEntity);
		return id;
	}

	@Override
	public List<WfTaskActorDTO> getActorsByOrderId(String orderId) throws BizException {
		Map<String, Object> map = new HashMap<>(1);
		map.put("orderId", orderId);
		return baseDao.queryForList("WfTask.getActorsByOrderId", map, WfTaskActorDTO.class);
	}
}