function _call(templateUrl,param){
	$("#main-wrapper").loadPage(templateUrl,
			["css/form-imput.css","css/system.css"],
			["lib/jquery-migrate.js","js/common/pageBar.js","lib/jquery.tmpl.min.js"],
			function(){

		var PAGE_SIZE = 10;
		
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
			
			$.getJSON(CONTEXT+"sysMessage/queryMessage4Page",page,function(data){
				
				if(data.success){
					if(isInit){
						initPageBar(data.result);
					}
					$("#templateBody").html("");
					//console.log(data.result.data);
					$('#template').tmpl(data.result).appendTo('#templateBody');
					initEditEvent();
				} else {
					$.eAlert("提示信息",data.msg);
				}
				
			});
		}
		
		//绑定编辑事件
		function initEditEvent(){
			$("#tb").find("a[name='business']")
			.off("click")
			.on("click",function(){
				var json = $(this).attr("businessJson");
				var controller = $(this).attr("controller");
				if($.trim(json) != ''){
					var parms = JSON.parse(json);
					Route.params=parms;
				}
				
				window.location.href="index#"+controller;
				//设置已读
				var id =$(this).attr("messageId");
				$.getJSON(CONTEXT+"sysMessage/setRead/"+id,function(data){
					if(data.success){
						parent.setNoReadMessageCount();
					}
				});
			});
			
		}
		
		
		//绑定查询事件 
		$("#queryCompany").click(function(){
			loadData({"pageNum":1,"pageSize":PAGE_SIZE}, true);
		});
		
		//默认初始化数据
		loadData({"pageNum":1,"pageSize":PAGE_SIZE}, true);
		
		
	});
}
