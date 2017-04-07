package cn.gdeng.market.service.workflow.core;

import java.io.InputStream;
import java.util.List;
import java.util.Map;

import org.snaker.engine.access.Page;
import org.snaker.engine.access.QueryFilter;
import org.snaker.engine.entity.HistoryOrder;
import org.snaker.engine.entity.HistoryTask;
import org.snaker.engine.entity.Order;
import org.snaker.engine.entity.Process;
import org.snaker.engine.entity.Surrogate;
import org.snaker.engine.entity.Task;
import org.snaker.engine.entity.WorkItem;

/**
 * snaker工作流门面服务类，提供常用的工作流操作方法。
 * @author wjguo
 *
 * datetime:2016年10月9日 下午12:14:32
 */
public interface SnakerFacadeService {
	
	
	/**获取所有的流程名称
	 * @return
	 */
	public List<String> getAllProcessNames();
	
	/**
	 * 根据流程定义ID，操作人ID，参数列表启动流程实例
	 * @param id 流程定义ID
	 * @param operator 操作人ID
	 * @param args 参数列表
	 * @return Order 流程实例
	 */
	public Order startInstanceById(String processId, String operator, Map<String, Object> args);
	
	/**
	 * 根据流程名称、版本号、操作人、参数列表启动流程实例
	 * @param name 流程定义名称
	 * @param version 版本号
	 * @param operator 操作人
	 * @param args 参数列表
	 * @return Order 流程实例
	 */
	public Order startInstanceByName(String name, Integer version, String operator, Map<String, Object> args);
	
	/** 根据名称启动流程实例，并执行首个任务。
	 * @param name
	 * @param version
	 * @param operator
	 * @param args
	 * @return
	 */
	public Order startAndExecute(String name, Integer version, String operator, Map<String, Object> args);
	
	/**根据id启动流程实例，并执行首个任务。
	 * @param processId
	 * @param operator
	 * @param args
	 * @return
	 */
	public Order startAndExecute(String processId, String operator, Map<String, Object> args);
	
	/**
	 * 根据任务主键ID，操作人ID，参数列表执行任务
	 * @param taskId 任务主键ID
	 * @param operator 操作人主键ID
	 * @param args 参数列表
	 * @return List<Task> 任务集合
	 */
	public List<Task> executeTask(String taskId, String operator, Map<String, Object> args) ;
	/**
	 * 根据任务主键ID，操作人ID，参数列表执行任务，并且根据nodeName跳转到任意节点
	 * 1、nodeName为null时，则跳转至上一步处理
	 * 2、nodeName不为null时，则任意跳转，即动态创建转移
	 * @param taskId 任务主键ID
	 * @param operator 操作人主键ID
	 * @param args 参数列表
	 * @param nodeName 跳转的节点名称
	 * @return List<Task> 任务集合
	 */
	public List<Task> executeAndJumpTask(String taskId, String operator, Map<String, Object> args, String nodeName);
	
	/**
	 * 转换为主办任务。具体操作是：根据已有任务id，通过指定actors参与者，创建新的主办任务，并让operator办理者完成当前任务。
	 * @param taskId 主办任务id  
	 * @param operator 当前任务的处理人。
	 * @param actors 新任务的参与者集合
	 * @return List<Task> 新创建的任务集合
	 */
	public List<Task> transferMajorTask(String taskId, String operator, String... actors);
	
	/**
	 * 转换为协办任务。具体操作是：根据已有任务id，通过指定actors参与者，创建新协办任务，并让operator办理者完成此任务。
	 * @param taskId 主办任务id  
	 * @param operator 当前任务的处理人。
	 * @param actors 新任务的参与者集合
	 * @return List<Task> 新创建的任务集合
	 */
	public List<Task> transferAidantTask(String taskId, String operator, String... actors);
	
	/** 根据流程实例名称，查询指定任务名称的历史流程和变量。
	 * @param orderId
	 * @param taskName
	 * @return 返回map结果集，包含两个key分别为：vars表示历史变量，histTasks表示历史任务。
	 */
	public Map<String, Object> queryTaskFlowData(String orderId, String taskName);
	
	/**
	 * 保存或更新委托代理对象
	 * @param surrogate 委托代理对象
	 */
	public void addSurrogate(Surrogate entity);
	
	/**
	 * 删除委托代理对象
	 * @param id 委托代理主键id
	 */
	public void deleteSurrogate(String id);
	
	/**
	 * 根据主键id查询委托代理对象
	 * @param id 主键id
	 * @return surrogate 委托代理对象
	 */
	public Surrogate getSurrogate(String id);
	
	/**
	 * 根据过滤条件查询委托代理对象
	 * @param page 分页对象
	 * @param filter 查询过滤器
	 * @return List<Surrogate> 委托代理对象集合
	 */
	public List<Surrogate> searchSurrogate(Page<Surrogate> page, QueryFilter filter);
	
	
	/**
	 * 根据给定的参数列表args分页查询process
	 * @param page 分页对象
	 * @param filter 查询过滤器
	 * @return List<Process> 流程定义对象集合
	 */
	public Page<Process> findProcesss(Page<Process> page, QueryFilter filter);
	/**
	 * 根据主键ID获取流程定义对象
	 * @param id 流程定义id
	 * @return Process 流程定义对象
	 */
	public Process getProcessById(String id);
	/**
	 * 根據xml格式的字符串，重新部署流程定义
	 * @param id 流程定义id
	 * @param input 流程定义输入流
	 */
	public void redeploy(String id, String xml);
	
	/**
	 * 根據InputStream輸入流，重新部署流程定义
	 * @param id 流程定义id
	 * @param input 流程定义输入流
	 */
	void redeploy(String id, InputStream input);
	
	/**通过xml格式的字符串部署流程。
	 * @param xml
	 * @param creator 创建人
	 * @return
	 */
	public String deploy(String xml, String creator);
	
	
	/**通过xml格式的字符串部署流程。
	 * @param xml
	 * @return
	 */
	public String deploy(String xml);
	/**
	 * 卸载指定的流程定义。注意是只更新状态，不做物流删除。
	 * @param id 流程定义id
	 */
	public void undeploy(String id);
	/**
	 * 根據InputStream輸入流，部署流程定义
	 * @param input 流程定义输入流
	 * @return String 流程定义id
	 */
	String deploy(InputStream input);
	/**
	 * 根據InputStream輸入流，部署流程定义
	 * @param input 流程定义输入流
	 * @param creator 创建人
	 * @return String 流程定义id
	 */
	String deploy(InputStream input, String creator);
	
	
	/**
	 * 根据流程实例ID获取历史流程实例对象
	 * @param orderId 历史流程实例id
	 * @return HistoryOrder 历史流程实例对象
	 */
	public HistoryOrder getHistOrder(String orderId);
	
	
	/**
	 * 根据filter分页查询历史流程实例
	 * @param page 分页对象
	 * @param filter 查询过滤器
	 * @return List<HistoryOrder> 历史实例集合
	 */
	public List<HistoryOrder> getHistoryOrders(Page<HistoryOrder> page, QueryFilter filter);
	
	/**
	 * 根据filter查询所有已完成的任务
	 * @param filter 查询过滤器
	 * @return List<HistoryTask> 历史任务集合
	 */
	public List<HistoryTask> getHistoryTasks(QueryFilter filter);
	
	/**
	 * 根据filter查询活动任务
	 * @param filter 查询过滤器
	 * @return List<Task> 活动任务集合
	 */
	public List<Task> getActiveTasks(QueryFilter filter);
	
	
	/**
	 * 根据任务ID获取活动任务参与者数组
	 * @param taskId 任务id
	 * @return String[] 参与者id数组
	 */
	public String[] getTaskActorsByTaskId(String taskId);
	
	
	/**
	 * 根据filter分页查询工作项（包含process、order、task三个实体的字段集合）
	 * @param page 分页对象
	 * @param filter 查询过滤器
	 * @return List<WorkItem> 活动工作项集合
	 */
	public Page<WorkItem> getWorkItems(Page<WorkItem> page, QueryFilter filter);
	
	
	/**
	 * 根据filter分页查询抄送工作项（包含process、order）
	 * @param page 分页对象
	 * @param filter 查询过滤器
	 * @return List<WorkItem> 抄送工作项集合
	 */
	public Page<HistoryOrder> getCCWorks(Page<HistoryOrder> page, QueryFilter filter);
	
	/**
	 * 根据流程实例ID获取流程实例对象
	 * @param orderId 流程实例id
	 * @return Order 流程实例对象
	 */
	public Order getOrder(String orderId);
	/**
	 * 根据任务ID获取任务对象
	 * @param taskId 任务id
	 * @return Task 任务对象
	 */
	public Task getTask(String taskId);
	
	/**
	 * 创建抄送实例
	 * @param orderId 流程实例id
	 * @param actorIds 参与者id
     * @param creator 创建人id
	 */
	public void createCCOrder(String orderId, String creator, String... actorIds);
	
	/**
	 * 更新抄送记录为已阅
	 * @param orderId 流程实例id
	 * @param actorIds 参与者id
	 */
	public void updateCCStatus(String orderId, String... actorIds);

}
