var route={
	//路由配置，demo为菜单路由
	"company":{
		templateUrl: "company/index",//后台控制器访问路径
		controller: "company/companyManager",
		title: "公司管理"
	}
	,"addCompany":{
		templateUrl: "company/toAddCompany",//后台控制器访问路径
		controller: "company/addCompany",
		title: "新增公司"
	},"platFormUser":{	
		templateUrl: "sysPlatUser/index",
		controller: "user/sysPlatUserController",
	}
	,"editCompany":{
		templateUrl: "company/toAddCompany",//后台控制器访问路径
		controller: "company/editCompany",
		title: "新增公司"
	}
	,"user":{
		templateUrl: "sysusr/chart",//后台控制器访问路径
		controller: "chartController",
		title: "用户管理"
	},"jumpToPlatUserAdd" : {
		templateUrl: "sysPlatUser/jumpToAdd",
		controller: "user/sysPlatUserAddController",
		title: "新增平台用户"
	},
	"jumpToPlatUserEdit" : {
		templateUrl: "sysPlatUser/jumpToEdit",
		controller: "user/sysPlatUserEditController",
		title: "编辑平台用户"
	},
	"jumpToPlatUserDetail" : {
		templateUrl: "sysPlatUser/jumpToDetail",
		controller: "user/sysPlatUserDetailController",
		title: "平台用户详情"
	},
	"profile":{	
		templateUrl: "sysPlatUser/profileBefore",
		controller: "user/profile",
		title: "我的账号"
	},
	"setting":{	
		templateUrl: "sysPlatUser/profileBefore",
		controller: "user/profile",
		title: "修改密码"
	},
	"modify-pwd":{	
		templateUrl: "sysPlatUser/modify-pwdBefore",
		controller: "user/modify-pwd",
		title: "首次登录修改密码"
	},
};

$.extend(Route.config, route);