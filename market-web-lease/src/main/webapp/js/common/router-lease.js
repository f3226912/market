var route={
	//路由配置，demo为菜单路由
	"demo":{
		templateUrl: "demo/chart",//后台控制器访问路径
		controller: "chartController",
		title: "谷登ERP-统计图"
	},//计量表分类
	"measureType":{	
		templateUrl: "measureType/view",
		controller: "settings/measureType/measureTypeController",
		title: "计量表分类"
	},
	"addMeasureType":{	
		templateUrl: "measureType/view-add",
		controller: "settings/measureType/addMeasureTypeController",
		title: "添加计量表分类"
	},
	"editMeasureType":{	
		templateUrl: "measureType/view-edit",
		controller: "settings/measureType/editMeasureTypeController",
		title: "计量表分类详情"
	},
	"measureSetting":{	
		templateUrl: "measureSetting/view",
		controller: "settings/measureSetting/measureSettingController",
		title: "计量表管理"
	},
	"addMeasureSetting":{	
		templateUrl: "measureSetting/view-add",
		controller: "settings/measureSetting/addMeasureSettingController",
		title: "添加计量表"
	},
	"batchAddMeasureSetting":{	
		templateUrl: "measureSetting/view-batchAdd",
		controller: "settings/measureSetting/batchAddMeasureSettingController",
		title: "批量添加计量表"
	},
	"measureSettingInfo":{	
		templateUrl: "measureSetting/view-info",
		controller: "settings/measureSetting/measureInfoController",
		title: "加计量表详情"
	},
	"history":{	
		templateUrl: "measureSetting/view-history",
		controller: "settings/measureSetting/historyController",
		title: "历史抄表记录"
	},
	"market":{
		templateUrl: "market/index",
		controller: "settings/market/marketController",
		title: "市场管理"
	},
	"viewMarket":{
		templateUrl: "market/view",
		controller: "settings/market/viewMarketController",
		title: "市场详情"
	},
	"marketArea":{
		templateUrl: "marketArea/index",
		controller: "resources/marketArea/marketAreaController",
		title: "区域管理"
	},
	"addMarketArea":{
		templateUrl: "marketArea/addMarketArea",
		controller: "resources/marketArea/addMarketAreaController",
		title: "新增区域"
	},
	"viewMarketArea":{
		templateUrl: "marketArea/view",
		controller: "resources/marketArea/viewMarketAreaController",
		title: "区域详情"
	},
	"viewImgMarketArea":{
		templateUrl: "marketArea/viewImgMarketArea",
		controller: "resources/marketArea/viewImgMarketAreaController",
		title: "区域图片详情"
	},
	"uploadImgMarketArea":{
		templateUrl: "marketArea/uploadImgMarketArea",
		controller: "resources/marketArea/uploadImgMarketAreaController",
		title: "区域图片上传"
	},
	"marketResource":{
		templateUrl:"marketResource/market_index",
		controller: "resources/marketResource/marketResourceController",
		title: "商铺资源管理"
	},
	"carResource":{
		templateUrl:"marketResource/car_index",
		controller: "resources/marketResource/marketResourceController",
		title: "停车位资源管理"
	},
	"viewMarketResource":{
		templateUrl:"marketResource/viewMarketResource",
		controller: "resources/marketResource/viewMarketResourceController",
		title: "查看、新增、修改资源"
	},
	"otherResource":{
		templateUrl: "marketResource/toOtherResourceView",
		controller: "resources/marketResource/other/otherController",
		title: "其它资源管理"
	},
	"viewOtherMarketResource":{
		templateUrl:"marketResource/viewOtherMarketResource",
		controller: "resources/marketResource/other/viewOtherMarketResourceController",
		title: "查看、新增、修改其它资源"
	},
	"expenditure":{
		templateUrl: "expenditure/index",
		controller: "settings/expenditure/expenditureController",
		title: "费项管理"
	}, 
	"addExp":{
		templateUrl: "expenditure/addExp",
		controller: "settings/expenditure/expDetailController",
		title: "添加费项"
	}, 
	"editExp":{
		templateUrl: "expenditure/editExp",
		controller: "settings/expenditure/expEditController",
		title: "编辑费项"
	}, 
	"expenditureStandard":{
		templateUrl: "expenditureStandard/index",
		controller: "settings/expenditureStandard/expStandardController",
		title: "计费标准管理"
	}, 
	"addExpStandard":{
		templateUrl: "expenditureStandard/add",
		controller: "settings/expenditureStandard/expStandardDetailController",
		title: "添加计费标准"
	}, 
	"editExpStandard":{
		templateUrl: "expenditureStandard/edit",
		controller: "settings/expenditureStandard/expStandardEditController",
		title: "编辑计费标准"
	}, 
	"post":{
		templateUrl: "post/index",
		controller: "sys/post/postController",
		title: "岗位管理"
	},
	"marketResourceType":{	
		templateUrl: "marketResourceType/index",
		controller: "settings/resourceType/marketResourceTypeController",
		title: "资源类型管理"
	},
	"marketResourceTypeToAddView":{	
		templateUrl: "marketResourceType/toAddView",
		controller: "settings/resourceType/marketResourceTypeAddController",
		title: "资源类型管理添加"
	},
	"marketResourceTypeToEditView":{	
		templateUrl: "marketResourceType/toEditView",
		controller: "settings/resourceType/marketResourceTypeEditController",
		title: "资源类型管理编辑"
	},
	"contractManage":{	
		templateUrl: "contractManage/index",
		controller: "contract/manage/contractManageController",
		title: "合同管理"
	},
	"addContract":{	
		templateUrl: "contractManage/addContract",
		controller: "contract/manage/addContractController",
		title: "合同管理新增"
	},
	"editContract":{	
		templateUrl: "contractManage/editContract",
		controller: "contract/manage/editContractController",
		title: "修改合同"
	},
	"copyContract":{	
		templateUrl: "contractManage/copyContract",
		controller: "contract/manage/copyContractController",
		title: "复制合同"
	},
	"changeContract":{	
		templateUrl: "contractManage/changeContract",
		controller: "contract/change/changeContractController",
		title: "合同变更"
	},
	"contractManageDetail":{	
		templateUrl: "contractManage/detail",
		controller: "contract/manage/contractManageDetailController",
		title: "合同管理详情主页面"
	},
	"showContractManageDetail":{	
		templateUrl: "contractManage/show",
		controller: "contract/manage/showContractManageDetailController",
		title: "合同管理详情嵌入子页面"
	},
	"contractChange":{	
		templateUrl: "contractChange/index",
		controller: "contract/change/contractChangeController",
		title: "合同变更"
	},
	"constItem":{	
		templateUrl: "contractManage/costItem",
		controller: "contract/manage/costItemController",
		title: "其它费项页面"
	},
	"contractChangeDetail":{	
		templateUrl: "contractChange/contractChangeDetail",
		controller: "contract/change/contractChangeDetailController",
		title: "合同变更详情主体页面"
	},
	"showContractChangeDetail":{	
		templateUrl: "contractChange/show",
		controller: "contract/change/showContractChangeDetailController",
		title: "合同变更详情嵌入子页面"
	},
	"contractSettlement":{	
		templateUrl: "contractSettlement/index",
		controller: "contract/settlement/contractSettlementController",
		title: "合同结算"
	},
	"contractSettlementDetail":{	
		templateUrl: "contractSettlement/contractSettlementDetail",
		controller: "contract/settlement/contractSettlementDetailController",
		title: "合同结算详情主体页面"
	},
	"showContractSettlementDetail":{
		templateUrl: "contractSettlement/showContractSettlementDetail",
		controller: "contract/settlement/showContractSettlementDetailController",
		title: "合同结算详情子页面"
	},
	"post-user":{
		templateUrl: "post/user",
		controller: "sys/post/postUserController",
		title: "岗位用户管理"
	},
	"companyUser":{	
		templateUrl: "sysCompanyUser/index",
		controller: "sys/user/sysComUserController",
		title: "用户管理"
	},
	"marketBuilding":{	
		templateUrl: "marketBuilding/index",
		controller: "resources/marketBuilding/getMarketBuildingInfoController",
		title: "楼栋管理"
	},
	"uploadImgBuilding":{	
		templateUrl: "marketBuilding/uploadImgBuilding",
		controller: "resources/marketBuilding/uploadImgBuildingController",
		title: "上传楼栋各楼层图片"
	},
	"viewImgBuilding":{	
		templateUrl: "marketBuilding/viewImgBuilding",
		controller: "resources/marketBuilding/viewImgBuildingController",
		title: "查看楼栋各楼层图片"
	},
	"resourceAdjust":{
		templateUrl: "resourceAdjust/index",
		controller: "resources/resourceAdjust/resourceAdjustController",
		title:'资源调整'
	},
	"addMarketBuilding":{	
		templateUrl: "marketBuilding/addMarketBuilding",
		controller: "resources/marketBuilding/addMarketBuildingController",
		title: "添加楼栋"
	},
	"marketBuildingdetail":{	
		templateUrl: "marketBuilding/marketBuildingDetailPage",
		controller: "resources/marketBuilding/marketBuildingDetailController",
		title: "楼栋详情"
	},
	"addResource":{	
		templateUrl: "marketBuilding/addResourcePage",
		controller: "resources/marketBuilding/addResourceController",
		title: "批量生成商铺/停车位"
	},
	
	"jumpToCompUserAdd" : {
		templateUrl: "sysCompanyUser/jumpToAdd",
		controller: "sys/user/sysComUserAddController",
		title: "新增公司用户"
	},
	"jumpToCompUserEdit" : {
		templateUrl: "sysCompanyUser/jumpToEdit",
		controller: "sys/user/sysComUserEditController",
		title: "编辑公司用户"
	},
	"jumpToCompUserDetail" : {
		templateUrl: "sysCompanyUser/jumpToDetail",
		controller: "sys/user/sysComUserDetailController",
		title: "公司用户详情"
	},
	"addPost":{
		templateUrl: "post/addPostBefore",
		controller: "sys/post/postDetailController",
		title: "添加岗位"
	},
	"edit-post":{
		templateUrl: "post/updatePost",
		controller: "sys/post/postEditController",
		title: "修改岗位"
	},
	"financeShould":{	
		templateUrl: "financeShould/Index",
		controller: "finance/fund/financeShouldController",
		title: "应收款管理"
	},
	"financeReceived":{	
		templateUrl: "financeHas/Index",
		controller: "finance/fund/financeReceivedController",
		title: "实收款管理"
	},	
	"finHasRecDetail":{
		templateUrl: "financeHas/toReceivedDetail",
		controller: "finance/fund/receivedDetailController",
		title: "财务实收款详情"
	},	
	"finNeedRecDetail":{
		templateUrl: "financeShould/needReceivedDetail",
		controller: "finance/fund/needRecDetailController",
		title: "财务应收款详情"
	},
	"newTempMoney":{
		templateUrl: "financeShould/toNewTempMoney",
		controller: "finance/fund/newTempMoneyController",
		title: "财务新增临时收款"
	},	
	"newBackMoney":{
		templateUrl: "financeShould/toNewBackMoney",
		controller: "finance/fund/newBackMoneyController",
		title: "财务新增临时收款"
	},		
	"meterReading":{	
		templateUrl: "financeGaugeCharge/meterReadingIndex",
		controller: "finance/financeGaugeCharge/meterReadingController",
		title: "计量表抄表"
	},
	"financeGaugeCharge":{	
		templateUrl: "financeGaugeCharge/financeGuageChargeIndex",
		controller: "finance/financeGaugeCharge/financeGaugeChargeController",
		title: "计量表抄表收费记录"
	},
	"financeGuageChargeDetail":{	
		templateUrl: "financeGaugeCharge/queryFinanceGaugeChargeById",
		controller: "finance/financeGaugeCharge/financeGuageChargeDetailController",
		title: "计量表收费详情"
	},
	
	"parameter" : {
		templateUrl: "parameter/view",
		controller: "settings/parameterController",
		title: "参数设置"
	},

	"parameter" : {
		templateUrl: "parameter/view",
		controller: "settings/parameterController",
		title: "参数设置"
	},
	"contractRemind":{	
		templateUrl: "contractRemind/index",
		controller: "settings/contractRemind/index",
		title: "合同到期提醒"
	},
	"contractEndRemind":{	
		templateUrl: "contractRemind/contractEndTi" +
				"meRemind",
		controller: "settings/contractRemind/ContractEndTimeRemindController",
		title: "合同到期提醒"
	},
	"financeEarlyWaring":{	
		templateUrl: "financeShould/toFinearlyWaring",
		controller: "finance/warning/financeEarlyWaringController",
		title: "财务收款到期提醒"
	},	
	"tree" : {
		templateUrl: "tree/index",
		controller: "sys/post/zTreeController",
		title: "树结构demo"
	},
	"org" : {
		templateUrl: "sysOrg/index",
		controller: "sys/org/orgManager",
		title: "组织管理"
	},
	"biLeaseList":{	
		templateUrl: "biLeaseList/index",
		controller: "bi/biLeaseListController",
		title: "租赁情况一览表"
	},
	"printSetting":{	
		templateUrl: "printSetting/index",
		controller: "settings/print/printSettingController",
		title: "套打设置"
	},
	"printSettingAdd":{	
		templateUrl: "printSetting/add",
		controller: "settings/print/printSettingAddController",
		title: "新增套打设置"
	},
	"printSettingEdit":{	
		templateUrl: "printSetting/edit",
		controller: "settings/print/printSettingEditController",
		title: "编辑套打设置"
	},
	"printSettingSelect":{	
		templateUrl: "printSetting/select",
		controller: "settings/print/printSettingSelectController",
		title: "套打设置选择"
	},
	"printTemplate":{	
		templateUrl: "printTemplate/index",
		controller: "settings/print/printTemplateController",
		title: "套打模板"
	},
	"printTemplateAdd":{	
		templateUrl: "printTemplate/add",
		controller: "settings/print/printTemplateAddController",
		title: "新增套打模板"
	},
	"printTemplateEdit":{	
		templateUrl: "printTemplate/edit",
		controller: "settings/print/printTemplateEditController",
		title: "修改套打模板"
	},
	"print":{	
		templateUrl: "print/index",
		controller: "settings/print/printController",
		title: "套打"
	},
	"post-auth":{
		templateUrl: "post/auth",
		controller: "sys/post/postAuthController",
		title: "权限管理"
	},
	"wfProcessIndex":{	
		templateUrl: "wfProcessDefine/index",
		controller: "workflow/define/indexController",
		title: "流程定义管理"
	},
	"jumpToProcessAdd":{	
		templateUrl: "wf/process/jumpToProcessAdd",
		controller: "workflow/define/addController",
		title: "流程定义新增"
	},
	"jumpToProcessEdit":{	
		templateUrl: "wf/process/jumpToProcessUpd",
		controller: "workflow/define/updController",
		title: "流程定义修改"
	},
	"wfMonitoIndex":{	
		templateUrl: "wfMonito/index",
		controller: "workflow/monitor/indexController",
		title: "流程监控管理"
	},
	"jumpToGtasks":{	
		templateUrl: "wfTask/jumpToGtasks",
		controller: "workflow/monitor/mygtasksIndexController",
		title: "待办任务"
	},
	"biContractMain":{	
		templateUrl: "biContractMain/index",
		controller: "bi/biContractMainController",
		title: "未过期合同"
	},
	"biOverContractMain":{	
		templateUrl: "biContractMain/toBiOverContractMain",
		controller: "bi/biOverContractMainController",
		title: "已过期合同"
	},

	"leaseParameter":{	
		templateUrl: "leaseParameter/index",
		controller: "settings/leaseParameter/leaseParameterController",
		title: "租约管理参数设置 "
	},

	"jumpToTerminate":{	
		templateUrl: "wfMonito/jumpToTerminate",
		controller: "workflow/monitor/terminateController",
		title: "流程监控管理"
	},
	"wfProcessDesigner":{	
		templateUrl: "wfProcessDefine/processDesigner",
		controller: "workflow/define/processDesignerController",
		title: "工作流流程步骤定义"
	},
	"graph":{	
		templateUrl: "graph/view",
		controller: "settings/market/marketImageController",
		title: "市场平面图设置"
	},
	"planeGraph":{	
		templateUrl: "planeGraph/index",
		controller: "settings/market/marketImageController",
		title: "市场平面图设置"
	},
	"sysMessage":{	
		templateUrl: "sysMessage/list",
		controller: "sys/message/sysMessage",
		title: "我的消息"
	},
	"marketImageAdd":{	
		templateUrl: "graph/toUpload",
		controller: "settings/market/marketImageAddController",
		title: "上传市场平面图"
	},
	"listAreas":{	
		templateUrl: "graph/listAreas",
		controller: "settings/market/marketAreaController",
		title: "待选择区域列表"
	},
	"profile":{	
		templateUrl: "sysCompanyUser/profileBefore",
		controller: "sys/user/profile",
		title: "我的账号"
	},
	"setting":{	
		templateUrl: "sysCompanyUser/profileBefore",
		controller: "sys/user/profile",
		title: "修改密码"
	},
	"modify-pwd":{	
		templateUrl: "sysCompanyUser/modify-pwdBefore",
		controller: "sys/user/modify-pwd",
		title: "首次登录修改密码"
	},
	"wfConManagerStart":{	
		templateUrl: "contractProcessQuery/prepareStartConManage",
		controller: "workflow/approval/contract/wfConManagerStartController",
		title: "合同管理-工作流审批-发起"
	},
	"wfConManagerApproAndShow":{	
		templateUrl: "contractProcessQuery/toConManage",
		controller: "workflow/approval/contract/wfConManagerApproAndShowController",
		title: "合同管理-工作流审批-审批和查看"
	},
	"wfConChangedStart":{	
		templateUrl: "contractProcessQuery/prepareStartConChanged",
		controller: "workflow/approval/contract/wfConChangedStartController",
		title: "合同变更-工作流审批-发起"
	},
	"wfConChangedApproAndShow":{	
		templateUrl: "contractProcessQuery/toConChanged",
		controller: "workflow/approval/contract/wfConChangedApproAndShowController",
		title: "合同变更-工作流审批-审批和查看"
	},
	"wfConCloseStart":{	
		templateUrl: "contractProcessQuery/prepareStartConClose",
		controller: "workflow/approval/contract/wfConCloseStartController",
		title: "合同结算-工作流审批-发起"
	},
	"wfConCloseApproAndShow":{	
		templateUrl: "contractProcessQuery/toConClose",
		controller: "workflow/approval/contract/wfConCloseApproAndShowController",
		title: "合同结算-工作流审批-审批和查看"
	},
	"measureSettingForward":{	
		templateUrl: "measureSetting/forward",
		controller: "settings/measureSetting/forwardSettingDetailController",
		title: "计量表详情"
	},
	"measureSettingResourceForward":{	
		templateUrl: "financeGaugeCharge/measureSettingResourceForward",
		controller: "finance/financeGaugeCharge/resourceController",
		title: "计量表详情"
	},	
	"report" : {
		templateUrl: "report/index",
		controller: "bi/reportController", // 对应js控制器文件名，省略.js
		title: "谷登ERP后台管理系统首页"
	}
};

$.extend(Route.config, route);