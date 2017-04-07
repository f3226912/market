
package cn.gdeng.market.lease.util;

import java.beans.PropertyDescriptor;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.beanutils.PropertyUtils;
import org.apache.commons.lang3.StringUtils;
import org.snaker.engine.core.AccessService;
import org.snaker.engine.model.CustomModel;
import org.snaker.engine.model.DecisionModel;
import org.snaker.engine.model.EndModel;
import org.snaker.engine.model.ForkModel;
import org.snaker.engine.model.JoinModel;
import org.snaker.engine.model.NodeModel;
import org.snaker.engine.model.ProcessModel;
import org.snaker.engine.model.StartModel;
import org.snaker.engine.model.SubProcessModel;
import org.snaker.engine.model.TaskModel;
import org.snaker.engine.model.TransitionModel;

/**
 * Snaker的帮助类
 * @author wjguo
 *
 * datetime:2016年10月12日 下午4:29:01
 */
public final class SnakerHelper {
	/**
	 * 流程节点映射
	 */
	private static Map<Class<? extends NodeModel>, String> processNodeMapper = new HashMap<Class<? extends NodeModel>, String>();
	static {
		processNodeMapper.put(TaskModel.class, "task");
		processNodeMapper.put(CustomModel.class, "custom");
		processNodeMapper.put(DecisionModel.class, "decision");
		processNodeMapper.put(EndModel.class, "end");
		processNodeMapper.put(ForkModel.class, "fork");
		processNodeMapper.put(JoinModel.class, "join");
		processNodeMapper.put(StartModel.class, "start");
		processNodeMapper.put(SubProcessModel.class, "subprocess");
	}
	/**xml转义字符转换为特殊字符
	 * @param value
	 * @return
	 */
	public static String xmlEscConvert(String value) {
		if (StringUtils.isEmpty(value))
			return "";
		if (value.indexOf("'") != -1) {
			value = value.replaceAll("'", "#1");
		}
		if (value.indexOf("\"") != -1) {
			value = value.replaceAll("\"", "#2");
		}
		if (value.indexOf("\r\n") != -1) {
			value = value.replaceAll("\r\n", "#3");
		}
		if (value.indexOf("\n") != -1) {
			value = value.replaceAll("\n", "#4");
		}
		if (value.indexOf(">") != -1) {
			value = value.replaceAll(">", "#5");
		}
		if (value.indexOf("<") != -1) {
			value = value.replaceAll("<", "#6");
		}
        if (value.indexOf("&amp;") != -1) {
            value = value.replaceAll("&amp;", "#7");
        }
		return value;
	}
	
	/**特殊字符转换为xml转义字符
	 * @param value
	 * @return
	 */
	public static String convertXmlEsc(String value) {
		if(value.indexOf("#1") != -1) {
			value = value.replaceAll("#1", "'");
		}
		if(value.indexOf("#2") != -1) {
			value = value.replaceAll("#2", "\"");
		}
		if(value.indexOf("#5") != -1) {
			value = value.replaceAll("#5", "&gt;");
		}
		if(value.indexOf("#6") != -1) {
			value = value.replaceAll("#6", "&lt;");
		}
        if(value.indexOf("&") != -1) {
            value = value.replaceAll("#7", "&amp;");
        }
		return value;
	}
	
	/** 是否执行所有的任务，即是否是会签任务。
	 * @param performType
	 * @return 如果是会签任务，则返回true，反之false。
	 */
	public static boolean isPerformAll(Integer performType) {
		return TaskModel.PerformType.ANY.ordinal() == performType;
	}
	
	/** 任务是否驳回。
	 * @param taskState 任务状态
	 * @return true表示任务驳回，反之false。
	 */
	public static boolean isRejectTask(Integer taskState) {
		//任务终止的状态，就是表示任务的驳回。
		return AccessService.STATE_TERMINATION.equals(taskState);
	}
	
	/** 任务是否通过。
	 * @param taskState 任务状态
	 * @return true表示任务通过，反之false。
	 */
	public static boolean isPassedTask(Integer taskState) {
		//任务终止的状态，就是表示任务的驳回。
		return AccessService.STATE_FINISH.equals(taskState);
	}
	 
	/** 流程实例是否被作废。
	 * @param orderState 流程实例状态
	 * @return true表示作废，反之false。
	 */
	public static boolean isOrderAbolished(Integer orderState) {
		//流程实例终止的状态，就是表示流程实例的作废。
		return AccessService.STATE_TERMINATION.equals(orderState);
	}
	
	/** 流程实例是否完成。
	 * @param orderState 流程实例状态
	 * @return true表示流程实例已归档，反之false。
	 */
	public static boolean isOrderFinished(Integer orderState) {
		return AccessService.STATE_FINISH.equals(orderState);
	}
	
	/** 获取任务参与类型的名称
	 * @param performType 任务参与类型
	 * @return 如果没有对应的名称，返回空字符串。
	 */
	public static String getPerformTypeName(int performType) {
		String result = "";
		if (TaskModel.PerformType.ANY.ordinal() == performType) {
			result = "审批";
		} else if (TaskModel.PerformType.ALL.ordinal() == performType) {
			result = "会签";
		}
		return result;
	}
	
	/** 获取任务的审批结果名称
	 * @param taskState 任务状态
	 * @return
	 */
	public static String getTaskApprovalResultName(int taskState) {
		String result = "";
		if (isPassedTask(taskState)) {
			result = "通过";
		} else if (isRejectTask(taskState)) {
			result = "驳回";
		} else {
			result = "待审批";
		}
		return result;
	}
	
	/**通过流程模型获取模型的json字符串。
	 * @param model 流程模型。
	 * @return
	 */
	public static String getModelJson(ProcessModel model) {
		StringBuffer buffer = new StringBuffer();
		List<TransitionModel> tms = new ArrayList<TransitionModel>();
		for(NodeModel node : model.getNodes()) {
			for(TransitionModel tm : node.getOutputs()) {
				tms.add(tm);
			}
		}
		buffer.append("{");
		buffer.append(getNodeJson(model.getNodes()));
		buffer.append(getPathJson(tms));
		buffer.append("props:{props:{name:{name:'name',value:'");
		buffer.append(xmlEscConvert(model.getName()));
		buffer.append("'},displayName:{name:'displayName',value:'");
		buffer.append(xmlEscConvert(model.getDisplayName()));
		buffer.append("'},expireTime:{name:'expireTime',value:'");
		buffer.append(xmlEscConvert(model.getExpireTime()));
		buffer.append("'},instanceUrl:{name:'instanceUrl',value:'");
		buffer.append(xmlEscConvert(model.getInstanceUrl()));
		buffer.append("'},instanceNoClass:{name:'instanceNoClass',value:'");
		buffer.append(xmlEscConvert(model.getInstanceNoClass()));
		buffer.append("'}}}}");
		return buffer.toString();
	}
	
	/** 获取节点模型的json字符串
	 * @param nodes
	 * @return
	 */
	private static String getNodeJson(List<NodeModel> nodes) {
		StringBuffer buffer = new StringBuffer();
		buffer.append("states: {");
		for(NodeModel node : nodes) {
			buffer.append(node.getName());
			buffer.append(getBaseInfo(node));
			buffer.append(getLayout(node));
			buffer.append(getProperty(node));
			buffer.append(",");
		}
		buffer.deleteCharAt(buffer.length() - 1);
		buffer.append("},");
		return buffer.toString();
	}
	
	/** 通过变迁节点获取json格式的线路。
	 * @param tms 变迁节点集合
	 * @return
	 */
	private static String getPathJson(List<TransitionModel> tms) {
		StringBuffer buffer = new StringBuffer();
		buffer.append("paths:{");
		for(TransitionModel tm : tms) {
			buffer.append(tm.getName());
			buffer.append(":{from:'");
			buffer.append(tm.getSource().getName());
			buffer.append("',to:'");
			buffer.append(tm.getTarget().getName());
			buffer.append("', dots:[");
			if(StringUtils.isNotEmpty(tm.getG())) {
		        String[] bendpoints = tm.getG().split(";");
		        for (String bendpoint: bendpoints) {
		        	buffer.append("{");
		            String[] xy = bendpoint.split(",");
		            buffer.append("x:").append(getNodeModelAbscissaNumber(xy[0]));
		            buffer.append(",y:").append(xy[1]);
		            buffer.append("},");
		        }
		        buffer.deleteCharAt(buffer.length() - 1);
			}
			buffer.append("],text:{text:'");
			buffer.append(tm.getDisplayName());
			buffer.append("'},textPos:{");
			if(StringUtils.isNotEmpty(tm.getOffset())) {
				String[] values = tm.getOffset().split(",");
				buffer.append("x:").append(values[0]).append(",");
				buffer.append("y:").append(values[1]).append("");
			}
			buffer.append("}, props:{name:{value:'" + tm.getName() + "'},expr:{value:'" + tm.getExpr() + "'}}}");
			buffer.append(",");
		}
		buffer.deleteCharAt(buffer.length() - 1);
		buffer.append("},");
		return buffer.toString();
	}
	
	
	/**获取节点模型的基本信息
	 * @param node
	 * @return
	 */
	private static String getBaseInfo(NodeModel node) {
		StringBuffer buffer = new StringBuffer();
		buffer.append(":{type:'");
		buffer.append(processNodeMapper.get(node.getClass()));
		buffer.append("',text:{text:'");
		buffer.append(node.getDisplayName());
		buffer.append("'},");
		return buffer.toString();
	}
	
	/**获取节点模型的属性
	 * @param node
	 * @return
	 */
	private static String getProperty(NodeModel node) {
		StringBuffer buffer = new StringBuffer();
		buffer.append("props:{");
		try {
		PropertyDescriptor[] beanProperties = PropertyUtils.getPropertyDescriptors(node);
		for (PropertyDescriptor propertyDescriptor : beanProperties) {
			if(propertyDescriptor.getReadMethod() == null || propertyDescriptor.getWriteMethod() == null)
				continue;
			String name = propertyDescriptor.getName();
			String value = "";
			if(propertyDescriptor.getPropertyType() == String.class) {
				value = (String)BeanUtils.getProperty(node, name);
			} else {
				continue;
			}
			if(value == null || value.equals("")) continue;
			buffer.append(name);
			buffer.append(":{value:'");
			buffer.append(xmlEscConvert(value));
			buffer.append("'},");
		}
		} catch(Exception e) {
			e.printStackTrace();
		}
		buffer.deleteCharAt(buffer.length() - 1);
		buffer.append("}}");
		return buffer.toString();
	}
	
	/**获取节点模型的布局
	 * @param node
	 * @return
	 */
	private static String getLayout(NodeModel node) {
		StringBuffer buffer = new StringBuffer();
		buffer.append("attr:{");
		String[] values = node.getLayout().split(",");
		buffer.append("x:").append(getNodeModelAbscissaNumber(values[0])).append(",");
		buffer.append("y:").append(values[1]).append(",");
		if("-1".equals(values[2])) {
			if(node instanceof TaskModel || node instanceof CustomModel || node instanceof SubProcessModel) {
				values[2] = "100";
			} else {
				values[2] = "50";
			}
		}
		if("-1".equals(values[3])) {
			values[3] = "50";
		}
		buffer.append("width:").append(values[2]).append(",");
		buffer.append("height:").append(values[3]);
		buffer.append("},");
		return buffer.toString();
	}
	
	/** 获取节点模型横坐标数值
	 * @param value x坐标值
	 * @return
	 */
	private static int getNodeModelAbscissaNumber(String value) {
		if(value == null) return 0;
		try {
			return Integer.parseInt(value) + 180;
		} catch(Exception e) {
			return 0;
		}
	}
}
