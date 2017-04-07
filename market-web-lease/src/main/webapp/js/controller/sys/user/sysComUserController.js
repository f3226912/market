function _call(templateUrl,param){
	$("#main-wrapper").loadPage(templateUrl,
			["css/form-imput.css","css/system.css"],
			["js/common/pageBar.js","lib/jquery.tmpl.min.js"],
			function(){
		
		var PAGE_SIZE = 20;
		
		//查询条件
		var condition = {};
		
		//初始化分页控件
		function initPageBar(result){
			
			// 分页工具组件
			$("#pagebar").page({
				pageIndex : 1,
				pageSize : PAGE_SIZE,
				total : result.total,
				callback : function(pageNum,pageSize){
					
					$('#templateBody').html(""); // 清空内容
					// 点击回调处理
					loadData({"pageNum":pageNum,"pageSize":pageSize}, false);
				}
			});
		}
		
		//加载数据
		function loadData(page, isInit){
			$.getJSON(CONTEXT+"sysCompanyUser/getCompUserList4Page",page,function(data){
				if(data.success){
					if(isInit){
						initPageBar(data.result);
					}
					$("#userBody").html("");
					if(data.result.total > 0){
						$('#userInfoScript').tmpl({users:data.result.recordList}).appendTo('#userBody');
					}else{
						$('#noDataScript').tmpl({users:data.result.recordList}).appendTo('#userBody');
					}
					initResetPwdEvent();
					initDeleteEvent();
					initDisabledEvent();
				} else {
					$.eAlert("加载数据",data.msg);
				}
			});
		}
		
		
		//绑定查询事件
		function initQueryEvent(){
			$("#query").click(function(){
				var code = $("#code").val();
				var name = $("#name").val();
				var postName = $("#postName").val();
				var orgId = $("#orgId").val();
				condition = {
						"pageNum":1,"pageSize":PAGE_SIZE,
						"code":code,
						"name":name,
						"postName":postName,
						"orgId":orgId
				};
				loadData(condition, true);
			});
		};
		
		//绑定重置密码事件
		function initResetPwdEvent(){
			$("#tb").find(".reset").click(function(){
				var id = $(this).attr("userId");
				if(confirm("确认重置该用户的密码吗？")){
					$.getJSON(CONTEXT+"sysCompanyUser/resetUserPwd",{"id":id},function(data){
						if(data.success){
							$.eAlert("重置密码","重置成功");
						} else {
							$.eAlert("重置密码",data.msg);
						}
					});
				}
			});
		};
		
		//绑定删除事件
		function initDeleteEvent(){
			$("#tb").find(".delete").click(function(){
				var id = $(this).attr("userId");
				$.eConfirm("删除用户","删除用户，会导致相关业务数据丢失，确认删除该用户吗？", function(){
					$.getJSON(CONTEXT+"sysCompanyUser/deleteCompUser",{"id":id},function(data){
						if(data.success){
							$.eAlert("删除记录","删除成功");
							loadData({"pageNum":1,"pageSize":PAGE_SIZE}, true);
							_this.parent().parent().remove();
						} else {
							$.eAlert("删除记录",data.msg);
						}
					});
				});
			});
		};
		//绑定禁用/激活事件
		function initDisabledEvent(){
			$("#tb").find(".disabled").click(function(){
				var id = $(this).data("userid");
				var status = $(this).data("status");
				var desc = $(this).data("desc");
				console.log(id);
				console.log(status);
				console.log(desc);
				$.eConfirm(desc + "账号","确定要"+desc+"该用户的账号吗？", function(){
					$.getJSON(CONTEXT+"sysCompanyUser/disableCompUser",
							{"id":id,"status":status},function(data){
						if(data.success){
							$.eAlert(desc + "账号",desc + "成功");
							loadData(condition, true);
						} else {
							$.eAlert(desc + "账号",data.msg);
						}
					});
				});
			});
		};
		//默认初始化数据
		loadData({"pageNum":1,"pageSize":PAGE_SIZE}, true);
		initQueryEvent();
	});
}