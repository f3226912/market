function _call(templateUrl,param){
	$("#main-wrapper").loadPage(templateUrl,
			["css/form-imput.css","lib/css/metroStyle.css","css/system.css"],
			["lib/jquery-migrate.js",
			 "js/common/pageBar.js",
			 "lib/jquery.tmpl.min.js",
			 "lib/jquery.ztree.all.js"],
			function(){
		
		try{
			var params=Route.params;//获取页面传值对象
			var postId=params.p;//取值方式：value=对象.key
			var tab=params.tab;//默认打开的选项卡
			
			//查看权限,启用第三个选项卡
			if(tab==3){
				$("#postUser,#tab-postUser").removeClass("active")
				$("#funAuth,#tab-postFunAuth").addClass("active")
			}
		}catch (e) {
			window.location.href="index#post";
			return;
		}
		
		// 此处编写所有处理代码
		var PAGE_SIZE = 20;
		var setting2 = {
				view: {
					showIcon: false,
					showLine: false
				},
				data: {
					simpleData: {
						enable: true
					}
				},
				check: {
					enable: true,
					chkStyle: "checkbox",
					chkboxType: { "Y" : "p", "N" : "ps" }
				}
		};
		$(document).ready(function(){
			$.ajax({
				type: "GET",
				dataType: "json",
				url: CONTEXT+"tree/function",
				data: {"postId":postId},
				async: true,
				success: function (data) {
					if(data.success){
						var zNodes=data.result;
						$.fn.zTree.init($("#tree2"), setting2, zNodes);
					}else {
						$.eAlert("初始化功能权限",data.msg);
					}
				},
				error: function(data) {
					$.eAlert("初始化功能权限",data.msg);
				}
			});
		});
		$(document).ready(function(){
			$.ajax({
				type: "GET",
				dataType: "json",
				url: CONTEXT+"tree/data",
				data: {"postId":postId},
				async: true,
				success: function (data) {
					if(data.success){
						var zNodes=data.result;
						$.fn.zTree.init($("#tree3"), setting2, zNodes);
					}else {
						$.eAlert("初始化数据权限",data.msg);
					}
				},
				error: function(data) {
					$.eAlert("初始化数据权限",data.msg);
				}
			});
		});
		
		$(document).ready(function(){
			$.ajax({
				type: "GET",
				dataType: "json",
				url: CONTEXT+"post/get/"+postId,
				data: {"postId":postId},
				async: true,
				success: function (data) {
					if(data.success){
						var postObj=data.result;
						$("#name").html(postObj.name);
						$("#remark").html(postObj.remark);
						$("#orgFullName").html(postObj.orgFullName);
					}else {
						
					}
				}
			});
		});
		
		//初始化分页控件
		function initPageBar(result){
			// 分页工具组件
			$("#pagebar").page({
				pageIndex : 1,
				pageSize : PAGE_SIZE,
				total : result.total,
				callback : function(index){
					$('#templateBody').html(""); // 清空内容
					// 点击回调处理
					loadData({"pageNum":index,"pageSize":PAGE_SIZE}, false);
				}
			});
		}
		
		//加载数据
		function loadData(page, isInit){
			$.getJSON(CONTEXT+"post/user/"+postId,page,function(data){
				if(data.success){
					if(isInit){
						initPageBar(data.result);
					}
					$("#templateBody").html("");
					if(data.result.total > 0){
						$('#template').tmpl({users:data.result.recordList}).appendTo('#templateBody');
					}else{
						$('#noDataScript').tmpl({users:data.result.recordList}).appendTo('#templateBody');
					}
					initSelectChangeEvent();
				} else {
					// $.eAlert(data.msg);
				}
			});
		}
		function initSelectChangeEvent(){
			$("#sizeSelect").change(function(){
				PAGE_SIZE = $("#sizeSelect").val();
				loadData({ "pageNum":1, "pageSize":PAGE_SIZE}, true);
			});
		}
		
		//默认初始化数据
		loadData({"pageNum":1,"pageSize":PAGE_SIZE}, true);
	});
}