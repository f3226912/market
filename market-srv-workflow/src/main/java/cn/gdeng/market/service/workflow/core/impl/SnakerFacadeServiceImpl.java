package cn.gdeng.market.service.workflow.core.impl;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.lang3.StringUtils;
import org.snaker.engine.SnakerEngine;
import org.snaker.engine.access.Page;
import org.snaker.engine.access.QueryFilter;
import org.snaker.engine.entity.HistoryOrder;
import org.snaker.engine.entity.HistoryTask;
import org.snaker.engine.entity.Order;
import org.snaker.engine.entity.Process;
import org.snaker.engine.entity.Surrogate;
import org.snaker.engine.entity.Task;
import org.snaker.engine.entity.WorkItem;
import org.snaker.engine.helper.StreamHelper;
import org.snaker.engine.model.TaskModel.TaskType;

import cn.gdeng.market.service.workflow.core.SnakerFacadeService;

/**
 * 
 * @author wjguo
 *
 * datetime:2016年10月9日 下午12:22:47
 */
public class SnakerFacadeServiceImpl implements SnakerFacadeService {
	
	/**
	 * snaker引擎
	 */
	private SnakerEngine snakerEngine;
	
	public void setSnakerEngine(SnakerEngine snakerEngine) {
		this.snakerEngine = snakerEngine;
	}

	public List<String> getAllProcessNames() {
		List<Process> list = snakerEngine.process().getProcesss(new QueryFilter());
		//使用set集合，去除相同的流程名称。
		Set<String> names = new HashSet<String>();
		for(Process entity : list) {
			names.add(entity.getName());
		}
		return new ArrayList<>(names);
	}
	
	public Order startInstanceById(String processId, String operator, Map<String, Object> args) {
		return snakerEngine.startInstanceById(processId, operator, args);
	}
	
	public Order startInstanceByName(String name, Integer version, String operator, Map<String, Object> args) {
		return snakerEngine.startInstanceByName(name, version, operator, args);
	}
	
	public Order startAndExecute(String name, Integer version, String operator, Map<String, Object> args) {
		Order order = snakerEngine.startInstanceByName(name, version, operator, args);
		List<Task> tasks = snakerEngine.query().getActiveTasks(new QueryFilter().setOrderId(order.getId()));
		List<Task> newTasks = new ArrayList<Task>();
		if(tasks != null && tasks.size() > 0) {
			Task task = tasks.get(0);
			newTasks.addAll(snakerEngine.executeTask(task.getId(), operator, args));
		}
		return order;
	}
	
	public Order startAndExecute(String processId, String operator, Map<String, Object> args) {
		Order order = snakerEngine.startInstanceById(processId, operator, args);
		List<Task> tasks = snakerEngine.query().getActiveTasks(new QueryFilter().setOrderId(order.getId()));
		List<Task> newTasks = new ArrayList<Task>();
		if(tasks != null && tasks.size() > 0) {
			Task task = tasks.get(0);
			newTasks.addAll(snakerEngine.executeTask(task.getId(), operator, args));
		}
		return order;
	}
	
	public List<Task> executeTask(String taskId, String operator, Map<String, Object> args) {
		return snakerEngine.executeTask(taskId, operator, args);
	}
	
	public List<Task> executeAndJumpTask(String taskId, String operator, Map<String, Object> args, String nodeName) {
		return snakerEngine.executeAndJumpTask(taskId, operator, args, nodeName);
	}

    public List<Task> transferMajorTask(String taskId, String operator, String... actors) {
        List<Task> tasks = snakerEngine.task().createNewTask(taskId, TaskType.Major.ordinal(), actors);
        snakerEngine.task().complete(taskId, operator);
        return tasks;
    }

    public List<Task> transferAidantTask(String taskId, String operator, String... actors) {
        List<Task> tasks = snakerEngine.task().createNewTask(taskId, TaskType.Aidant.ordinal(), actors);
        snakerEngine.task().complete(taskId, operator);
        return tasks;
    }
    
    public Map<String, Object> queryTaskFlowData(String orderId, String taskName) {
    	Map<String, Object> data = new HashMap<String, Object>();
		if (StringUtils.isNotEmpty(orderId) && StringUtils.isNotEmpty(taskName)) {
			List<HistoryTask> histTasks = snakerEngine.query()
					.getHistoryTasks(
							new QueryFilter().setOrderId(orderId).setName(
									taskName));
			List<Map<String, Object>> vars = new ArrayList<Map<String,Object>>();
			for(HistoryTask hist : histTasks) {
				vars.add(hist.getVariableMap());
			}
			data.put("vars", vars);
			data.put("histTasks", histTasks);
		}
		return data;
	}
	
	public void addSurrogate(Surrogate entity) {
		if(entity.getState() == null) {
			entity.setState(1);
		}
		snakerEngine.manager().saveOrUpdate(entity);
	}
	
	public void deleteSurrogate(String id) {
		snakerEngine.manager().deleteSurrogate(id);
	}
	
	public Surrogate getSurrogate(String id) {
		return snakerEngine.manager().getSurrogate(id);
	}
	
	public List<Surrogate> searchSurrogate(Page<Surrogate> page, QueryFilter filter) {
		return snakerEngine.manager().getSurrogate(page, filter);
	}
	
	public Page<Process> findProcesss(Page<Process> page, QueryFilter filter){
		snakerEngine.process().getProcesss(page, filter);
		return page; 
	}
	
	public Process getProcessById(String id){
		return snakerEngine.process().getProcessById(id);
	}
	
	public void redeploy(String id, String xml){
		InputStream input = StreamHelper.getStreamFromString(xml);
		snakerEngine.process().redeploy(id,input);
	}
	
	public String deploy(String xml){
		return deploy(xml, null);
	}
	
	public String deploy(String xml, String creator){
		InputStream input = StreamHelper.getStreamFromString(xml);
		return snakerEngine.process().deploy(input,creator);
	}
	
	public void undeploy(String id){
		snakerEngine.process().undeploy(id);
	}
	
	public HistoryOrder getHistOrder(String orderId){
		return snakerEngine.query().getHistOrder(orderId);
	}
	
	public List<HistoryTask> getHistoryTasks(QueryFilter filter){
		return snakerEngine.query().getHistoryTasks(filter);
	}
	
	
	
	public List<Task> getActiveTasks(QueryFilter filter){
		return snakerEngine.query().getActiveTasks(filter);
	}
	
	public String[] getTaskActorsByTaskId(String taskId){
		return snakerEngine.query().getTaskActorsByTaskId(taskId);
	}
	
	public void createCCOrder(String orderId, String creator, String... actorIds){
		snakerEngine.order().createCCOrder(orderId, creator, actorIds);
	}
	
	public Page<WorkItem> getWorkItems(Page<WorkItem> page, QueryFilter filter){
		snakerEngine.query().getWorkItems(page, filter);
		return page;
	}
	
	public Page<HistoryOrder> getCCWorks(Page<HistoryOrder> page, QueryFilter filter){
		snakerEngine.query().getCCWorks(page, filter);
		return page;
	}
	
	public Order getOrder(String orderId){
		return snakerEngine.query().getOrder(orderId);
	}
	
	public Task getTask(String taskId){
		return snakerEngine.query().getTask(taskId);
	}
	
	public void updateCCStatus(String orderId, String... actorIds){
		snakerEngine.order().updateCCStatus(orderId, actorIds);
	}

	@Override
	public void redeploy(String id, InputStream input) {
		snakerEngine.process().redeploy(id, input);
	}

	@Override
	public String deploy(InputStream input) {
		return snakerEngine.process().deploy(input);
	}

	@Override
	public String deploy(InputStream input, String creator) {
		return snakerEngine.process().deploy(input, creator);
	}

	@Override
	public List<HistoryOrder> getHistoryOrders(Page<HistoryOrder> page, QueryFilter filter) {
		return snakerEngine.query().getHistoryOrders(page, filter);
	}
	
}
