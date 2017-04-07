package cn.gdeng.market.util;

import cn.gdeng.market.enums.WorkflowBusTypeEnum;

/**系统消息助手类
 * 
 * @author wjguo
 *
 * datetime:2016年10月24日 下午8:21:05
 */
public class SysMessageHelper {
	/**根据工作流业务类型的code码获取对应的路由地址
	 * @param WorkflowBusTypeCode
	 * @return
	 */
	public static String getWfRouteUrl(String WorkflowBusTypeCode) {
		WorkflowBusTypeEnum type = WorkflowBusTypeEnum.getByCode(WorkflowBusTypeCode);
		String route = null;
		switch (type) {
		case CONTRACT_MANAGER:
			route = "wfConManagerApproAndShow";
			break;
		case CONTRACT_CHANGED:
			route = "wfConChangedApproAndShow";
			break;
		case CONTRACT_CLOSE:
			route = "wfConCloseApproAndShow";
			break;
		}
		return route;
	}
	
	/**获取工作流流程定义列表路由地址
	 * @return
	 */
	public static String getWfProcessDefineListRouteUrl() {
		return "wfProcessIndex";
	}
	
}
