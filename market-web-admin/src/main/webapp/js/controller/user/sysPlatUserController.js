function _call(templateUrl,param){
	$("#main-wrapper").loadPage(templateUrl,
			["css/form-imput.css","css/system.css"],
			["lib/jquery-migrate.js","js/common/pageBar.js","lib/jquery.tmpl.min.js"],
			function(){
		var PAGE_SIZE = 50;
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
			var json = $("#searchForm").serializeJson();
			$.extend(page,json);
			$.getJSON(CONTEXT+"sysPlatUser/getPlatUserList4Page",page,function(data){
				if(data.success){
					if(isInit){
						initPageBar(data.result);
					}
					$("#userBody").html("");
					$('#userInfoScript').tmpl({users:data.result.recordList}).appendTo('#userBody');

					initResetPwdEvent();
					initDeleteEvent();
					initDisabledEvent();
				} else {
					alert(data.msg);
				}
			});
		}
		
		
		//绑定查询事件
		function initQueryEvent(){
			$("#query").click(function(){
				if($("#searchForm").valid()){
					loadData({"pageNum":1,"pageSize":PAGE_SIZE}, true);
				}
				
			});
		};
		
		//绑定重置密码事件
		function initResetPwdEvent(){
			$("#tb").find(".reset").click(function(){
				var id = $(this).attr("userId");
				if(confirm("确认重置该用户的密码吗？")){
					$.getJSON(CONTEXT+"sysPlatUser/resetUserPwd",{"id":id},function(data){
						if(data.success){
							alert("重置成功");
						} else {
							alert(data.msg);
						}
					});
				}
			});
		};
		
		//绑定删除事件
		function initDeleteEvent(){
			$("#tb").find(".delete").click(function(){
				var id = $(this).attr("userId");
				var type = $(this).parent().attr("type");
				if(type == '1' || type == '2'){
					$.eAlert("删除用户","不允许删除管理员账号");
					return;
				}
				//管理员状态不能被删除
				$.eConfirm("删除用户","删除用户，会导致相关业务数据丢失，确认删除该用户吗？",function(){
					$.getJSON(CONTEXT+"sysPlatUser/deletePlatUser",{"id":id},function(data){
						if(data.success){
							$.eAlert("删除用户","删除成功");
							loadData(condition, true);
						} else {
							alert(data.msg);
						}
					});
				});

			});
		}
		//绑定禁用/激活事件
		function initDisabledEvent(){
			$("#tb").find(".disabled").click(function(){
				var id = $(this).data("userid");
				var status = $(this).data("status");
				var desc = $(this).data("desc");
				var type = $(this).parent().attr("type");
				//平台管理员不能被禁用
				if(status == '3' && type == '1'){
					$.eAlert("提示信息","平台管理员不能被禁用");
					return;
				}
				$.eConfirm(desc + "账号","确定要"+desc+"该用户的账号吗？", function(){
					$.getJSON(CONTEXT+"sysPlatUser/disablePlatUser",
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