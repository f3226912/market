package cn.gdeng.market.service.workflow.core.impl;

import java.util.List;
import java.util.Map;

import org.snaker.engine.ITaskService;
import org.snaker.engine.core.Execution;
import org.snaker.engine.entity.HistoryTask;
import org.snaker.engine.entity.Task;
import org.snaker.engine.model.CustomModel;
import org.snaker.engine.model.ProcessModel;
import org.snaker.engine.model.TaskModel;

import cn.gdeng.market.service.workflow.core.SnakerTaskService;

public class SnakerTaskServiceImpl implements SnakerTaskService {
	/**
	 * snaker框架的内部服务类接口
	 */
	private ITaskService taskService;
	
	public void setTaskService(ITaskService taskService) {
		this.taskService = taskService;
	}

	@Override
	public Task complete(String taskId) {
		return taskService.complete(taskId);
	}

	@Override
	public Task complete(String taskId, String operator) {
		return taskService.complete(taskId, operator);
	}

	@Override
	public Task complete(String taskId, String operator, Map<String, Object> args) {
		return taskService.complete(taskId, operator, args);
	}

	@Override
	public void updateTask(Task task) {
		taskService.updateTask(task);
	}

	@Override
	public HistoryTask history(Execution execution, CustomModel model) {
		return taskService.history(execution, model);
	}

	@Override
	public Task take(String taskId, String operator) {
		return taskService.take(taskId, operator);
	}

	@Override
	public Task resume(String taskId, String operator) {
		return taskService.resume(taskId, operator);
	}

	@Override
	public void addTaskActor(String taskId, String... actors) {
		taskService.addTaskActor(taskId, actors);
	}

	@Override
	public void addTaskActor(String taskId, Integer performType, String... actors) {
		taskService.addTaskActor(taskId, performType, actors);
	}

	@Override
	public void removeTaskActor(String taskId, String... actors) {
		taskService.removeTaskActor(taskId, actors);
	}

	@Override
	public Task withdrawTask(String taskId, String operator) {
		return taskService.withdrawTask(taskId, operator);
	}

	@Override
	public Task rejectTask(ProcessModel model, Task currentTask) {
		return taskService.rejectTask(model, currentTask);
	}

	@Override
	public boolean isAllowed(Task task, String operator) {
		return taskService.isAllowed(task, operator);
	}

	@Override
	public List<Task> createTask(TaskModel taskModel, Execution execution) {
		return taskService.createTask(taskModel, execution);
	}

	@Override
	public List<Task> createNewTask(String taskId, int taskType, String... actors) {
		return taskService.createNewTask(taskId, taskType, actors);
	}

	@Override
	public TaskModel getTaskModel(String taskId) {
		return taskService.getTaskModel(taskId);
	}

	@Override
	public void delete(Task... tasks) {
		taskService.delete(tasks);
	}

	@Override
	public Task terminate(String taskId, String operator) {
		return taskService.terminate(taskId, operator);
	}

	@Override
	public Task terminate(String taskId, String operator, Map<String, Object> args) {
		return taskService.terminate(taskId, operator, args);
	}

	@Override
	public void taskActorChanged(String taskId, String oldActor, String newActor) {
		taskService.taskActorChanged(taskId, oldActor, newActor);
	}
	
}
