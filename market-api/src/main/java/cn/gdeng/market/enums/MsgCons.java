package cn.gdeng.market.enums;


/**
 * 消息返回code msg定义
 * @author zhangnf
 *错误码定义成5位数
 *
 */
public class MsgCons {

	public static final Integer C_10000 = 10000;
	public static final String M_10000 = "成功";
	
	//20000 -30000 参数校验异常
	public static final Integer C_20000 = 20000;
	public static final String M_20000 = "失败";
	
	//30000~30100属于系统自定义异常
	public static final Integer C_30000 = 30000;
	public static final String M_30000 = "服务异常";
	public static final Integer C_30007 = 30007;
	public static final String M_30007 = "会话超时，请重新登录";//session过期
	
	//30100以上属于用户自定义的业务异常。
	public static final Integer C_30001 = 30001;
	public static final String M_30001 = "空对象";
	
	public static final Integer C_30101 = 30101;
	public static final String M_30101 = "更新失败";
	
	public static final Integer C_30002 = 30002;
	public static final String M_30002 = "导出数据过多";
	
	public static final Integer C_30003 = 30003;
	public static final String M_30003 = "当前费项名称已经存在！";
	
	public static final Integer C_30004 = 30004;
	public static final String M_30004 = "当前计费标准编码已经存在！";
	
	public static final Integer C_30005 = 30005;
	public static final String M_30005 = "当前计费标准名称已经存在！";
	
	public static final Integer C_30006 = 30006;
	public static final String M_30006 = "请选择市场！";
	
	//31000~31999 属于工作流模块异常。
	public static final Integer C_31000 = 31000;
	public static final String M_31000 = "当前流程模板名称已经存在!";
	
	public static final Integer C_31001 = 31001;
	public static final String M_31001 = "不能确定审批结果!";
	
	public static final Integer C_31002 = 31002;
	public static final String M_31002 = "流程定义保存失败，请检查流程定义图是否正确!";
	
	public static final Integer C_31003 = 31003;
	public static final String M_31003 = "流程定义更新失败，请检查流程定义图是否正确!";
	
}
