package cn.gdeng.market.enums;

/**
 * 常量类;
 * 
 */
public class Const {

	/**
	 * @公用
	 */
	public static final int COMMON_PAGE_NUM = 10;
	public static final String COMMON_AJAX_SUCCESS = "success";
	public static final String COMMON_AJAX_ERROR = "error";
	public static final String COMMON_EXCEPTION_MESSAGE = "exception";
	/** 数据状态：正常 */
	public static final int NORMAL = 1;
	/** 数据状态：删除 */
	public static final int DELETED = 0;

	/** 数据状态：禁用 */
	public static final int DISABLED = 3;

	public static final String CHARSET_UTF8 = "UTF-8";

	/** 数据字典类型-encode **/
	public static final String DICTIONARY_ENCODE = "encode";
	/** 数据字典类型 -费项计算方法 */
	public static final String DICTIONARY_CALCULATION_METHOD = "calculationMethod";
	/** 数据字典类型 -费项类型 */
	public static final String DICTIONARY__EXPENDITURE_TYPE = "expenditureType";

	/** 加密方式-MD5 **/
	public static final String ENCRYPTION_TYPE_MD5 = "md5";

	/** 默认重置密码为123 **/
	public static final String RESET_PASSWROD_VALUE = "abc123";

	/** 合同审批类型:合同初次审核 **/
	public static final String CONTRACT_FIRST_AUDIT = "1";

	/** 合同审批类型:合同变更审核 **/
	public static final String CONTRACT_CHANGE_AUDIT = "2";

	/** 合同审批类型:合同结算审核 **/
	public static final String CONTRACT_STATEMENTS_AUDIT = "3";

	/** 合同审批方式:工作流审核 **/
	public static final String CONTRACT_WORKFLOW_AUDIT = "1";

	/** 合同审批方式:人工审核 **/
	public static final String CONTRACT_HUMAN_AUDIT = "2";

	/**
	 * codis操作返回的信号标志
	 */
	public static final String CODIS_OK = "OK";

	/** 组织类型-公司 **/
	public static final String ORG_TYPE_COMPANY = "1";

	/** 组织类型-市场 **/
	public static final String ORG_TYPE_MARKET = "3";

	/** 组织类型-部门 **/
	public static final String ORG_TYPE_DEPT = "2";

	public static final int MEASURE_SECTIONALCHARGE_SEGMENTATION = 1;

	/** 组织类型-集团 **/
	public static final String ORG_TYPE_GROUP = "4";

	/** 用户状态-0：注销 ，删除 **/
	public static final String USER_STATUS_0 = "0";
	/** 用户状态-1：正常 **/
	public static final String USER_STATUS_1 = "1";
	/** 用户状态-2：锁定 **/
	public static final String USER_STATUS_2 = "2";
	/** 用户状态-3：禁用 **/
	public static final String USER_STATUS_3 = "3";
	/** 用户状态说明-1：正常 **/
	public static final String USER_STATUS_DESC_1 = "启用";
	/** 用户状态说明-3：禁用 **/
	public static final String USER_STATUS_DESC_3 = "禁用";

	/** 用户类型-1：平台用户 **/
	public static final String USER_TYPE_1 = "1";
	/** 用户类型-2：公司管理员 **/
	public static final String USER_TYPE_2 = "2";
	/** 用户类型-3：普通用户 **/
	public static final String USER_TYPE_3 = "3";
	/**
	 * 导出最大条数
	 */
	public static final Integer EXPORT_MAX_SIZE = 10000;

	/** 用户类型说明-1：平台用户 **/
	public static final String USER_TYPE_DESC_1 = "平台";
	/** 用户类型说明-2：公司管理员 **/
	public static final String USER_TYPE_DESC_2 = "公司";
	/** 用户类型说明-3：普通用户 **/
	public static final String USER_TYPE_DESC_3 = "普通";

	/**
	 * 菜单节点前缀标识
	 */
	public static final String MENU_PRE = "menuId-";
	/**
	 * 按钮单节点前缀标识
	 */
	public static final String BUTTON_PRE = "buttonId-";
	/**
	 * 公司节点前缀标识
	 */
	public static final String COMPANY_PRE = "companyId-";
	/**
	 * 市场节点前缀标识
	 */
	public static final String MARKET_PRE = "marketId-";
	/**
	 * 资源类型节点前缀标识
	 */
	public static final String SOURCE_PRE = "sourceId-";
	/**
	 * 组织节点前缀标识
	 */
	public static final String ORG_PRE = "orgId-";
	/**
	 * 人员节点前缀标识
	 */
	public static final String USER_PRE = "userId-";
	/**
	 * 分割标识符
	 */
	public static final String SPLIT_LINE = "-";
	/**
	 * 分割标识符
	 */
	public static final String SPLIT_COMMA = ",";
	
	/**
	 * 流程模板状态-0：不可用
	 */
	public static final String WF_PROCESS_STATE_0 = "0";
	/**
	 * 流程模板状态说明-0：不可用
	 */
	public static final String WF_PROCESS_STATE_DESC_0 = "禁用";
	
	/**
	 * 流程模板状态-1：可用
	 */
	public static final String WF_PROCESS_STATE_1 = "1";
	/**
	 * 流程模板状态说明-1：可用
	 */
	public static final String WF_PROCESS_STATE_DESC_1 = "可用";
	
	/**
	 * 流程模板状态-2：已删除
	 */
	public static final String WF_PROCESS_STATE_2 = "2";
	/**
	 * 流程模板状态说明-2：已删除
	 */
	public static final String WF_PROCESS_STATE_DESC_2 = "已删除";
	
	/**
	 * 消息是否已读-0 未读
	 */
	public static final String MESSAGE_IS_READ_0 = "0";
	
	/**
	 * 消息是否已读-1 已读
	 */
	public static final String MESSAGE_IS_READ_1 = "1";
	
	/**
	 * 模板文件缓存key前缀
	 */
	public static final String PRINT_TEMPLATE_KEY_PRE = "leasing_printTemplate_";
}
