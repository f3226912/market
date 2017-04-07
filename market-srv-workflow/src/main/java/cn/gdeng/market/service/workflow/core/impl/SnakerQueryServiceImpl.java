package cn.gdeng.market.service.workflow.core.impl;

import java.util.List;

import org.snaker.engine.IQueryService;
import org.snaker.engine.access.Page;
import org.snaker.engine.access.QueryFilter;
import org.snaker.engine.entity.HistoryOrder;
import org.snaker.engine.entity.HistoryTask;
import org.snaker.engine.entity.Order;
import org.snaker.engine.entity.Task;
import org.snaker.engine.entity.WorkItem;

import cn.gdeng.market.service.workflow.core.SnakerQueryService;

public class SnakerQueryServiceImpl implements SnakerQueryService {
	/**
	 * snaker框架的内部服务类接口
	 */
	private IQueryService queryService;
	
	public void setQueryService(IQueryService queryService) {
		this.queryService = queryService;
	}

	@Override
	public Order getOrder(String orderId) {
		return queryService.getOrder(orderId);
	}

	@Override
	public HistoryOrder getHistOrder(String orderId) {
		return queryService.getHistOrder(orderId);
	}


	@Override
	public Task getTask(String taskId) {
		return queryService.getTask(taskId);
	}

	@Override
	public HistoryTask getHistTask(String taskId) {
		return queryService.getHistTask(taskId);
	}

	@Override
	public String[] getTaskActorsByTaskId(String taskId) {
		return queryService.getTaskActorsByTaskId(taskId);
	}

	@Override
	public String[] getHistoryTaskActorsByTaskId(String taskId) {
		return queryService.getHistoryTaskActorsByTaskId(taskId);
	}

	@Override
	public List<Task> getActiveTasks(QueryFilter filter) {
		return queryService.getActiveTasks(filter);
	}

	@Override
	public List<Task> getActiveTasks(Page<Task> page, QueryFilter filter) {
		return queryService.getActiveTasks(page, filter);
	}

	@Override
	public List<Order> getActiveOrders(QueryFilter filter) {
		return queryService.getActiveOrders(filter);
	}

	@Override
	public List<Order> getActiveOrders(Page<Order> page, QueryFilter filter) {
		return queryService.getActiveOrders(page, filter);
	}

	@Override
	public List<HistoryOrder> getHistoryOrders(QueryFilter filter) {
		return queryService.getHistoryOrders(filter);
	}

	@Override
	public List<HistoryOrder> getHistoryOrders(Page<HistoryOrder> page, QueryFilter filter) {
		return queryService.getHistoryOrders(page, filter);
	}


	@Override
	public List<HistoryTask> getHistoryTasks(QueryFilter filter) {
		return queryService.getHistoryTasks(filter);
	}

	@Override
	public List<HistoryTask> getHistoryTasks(Page<HistoryTask> page, QueryFilter filter) {
		return queryService.getHistoryTasks(page, filter);
	}

	@Override
	public List<WorkItem> getWorkItems(Page<WorkItem> page, QueryFilter filter) {
		return queryService.getWorkItems(page, filter);
	}

	@Override
	public List<HistoryOrder> getCCWorks(Page<HistoryOrder> page, QueryFilter filter) {
		return queryService.getCCWorks(page, filter);
	}

	@Override
	public List<WorkItem> getHistoryWorkItems(Page<WorkItem> page, QueryFilter filter) {
		return queryService.getHistoryWorkItems(page, filter);
	}

	@Override
	public <T> T nativeQueryObject(Class<T> T, String sql, Object... args) {
		return queryService.nativeQueryObject(T, sql, args);
	}

	@Override
	public <T> List<T> nativeQueryList(Class<T> T, String sql, Object... args) {
		return queryService.nativeQueryList(T, sql, args);
	}

	@Override
	public <T> List<T> nativeQueryList(Page<T> page, Class<T> T, String sql, Object... args) {
		return queryService.nativeQueryList(page, T, sql, args);
	}
	
	
}
