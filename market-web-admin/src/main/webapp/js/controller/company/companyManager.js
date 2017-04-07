function _call(templateUrl,param){
	$("#main-wrapper").loadPage(templateUrl,
			["css/form-imput.css","css/system.css"],
			["lib/jquery-migrate.js","js/common/pageBar.js","lib/jquery.tmpl.min.js"],
			function(){

		var PAGE_SIZE = 50;
		
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
			//获取公司名称
			$.eLoading(true);
			var name = $("#fullName").val();
			page.fullName = name; 
			$.getJSON(CONTEXT+"company/queryTopSysOrg4Page",page,function(data){
				
				if(data.success){
					if(isInit){
						initPageBar(data.result);
					}
					$("#templateBody").html("");
					//console.log(data.result.data);
					$('#template').tmpl({orgs:data.result.recordList}).appendTo('#templateBody');
					initDeleteEvent();
					initEditEvent();
				} else {
					$.eAlert("提示信息",data.msg);
				}
				$.eLoading(false);
			});
		}
		
		//绑定编辑事件
		function initEditEvent(){
			$("#tb").find(".edit").click(function(){
				var id = $(this).attr("orgId");
				Route.params={id:id};
				window.location.href="index#editCompany";
			});
		}
		
		//绑定删除事件
		function initDeleteEvent(){
			$("#tb").find(".delete").click(function(){
				var id = $(this).attr("orgId");
				if($.eConfirm("删除记录","你确定要删除吗？")){
					$.getJSON(CONTEXT+"company/deleteSysOrg",{"id":id},function(data){
						
						if(data.success){
							loadData({"pageNum":1,"pageSize":PAGE_SIZE}, true);
							alert("删除成功");
						} else {
							alert(data.msg);
						}
					});
				}
			});
		}


		//绑定新增事件
		$("#addCompany").click(function(){
			//Route.params={id:3,name:'产品'};location.href='#postAuth';
			window.location.href="index#addCompany";
		});
		
		//绑定查询事件 
		$("#queryCompany").click(function(){
			if($("#querySearch").valid()){
				loadData({"pageNum":1,"pageSize":PAGE_SIZE}, true);
			}
			
		});
		
		//默认初始化数据
		loadData({"pageNum":1,"pageSize":PAGE_SIZE}, true);
		
		
	});
}
