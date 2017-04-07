function _call(templateUrl,param){
	$("#main-wrapper").loadPage(templateUrl,
			["lib/css/DT_bootstrap.css","css/resourMage.css"],
			["lib/jquery-migrate.js","js/common/pageBar.js","lib/jquery.tmpl.min.js"],
			function(){

		var checked_row, res_data;
		var PAGE_SIZE ;
		//初始化分页控件
		function initPageBar(result){
			// 分页工具组件
			$("#pagebar").page({
				pageIndex : 1,
				total : result.total,
				callback : function(index, pageSize){
					$('#templateBody').html(""); // 清空内容
					PAGE_SIZE = pageSize;
					// 点击回调处理
					loadData({"pageNum":index,"pageSize":pageSize}, false);
				}
			});
		}
		
		//加载数据
		function loadData(page, isInit){
			$.getJSON(CONTEXT+"marketArea/query",page,function(data){
				
				if(data.success){
					if(isInit){
//						console.log(data.result);
						initPageBar(data.result);
					}
					$("#templateBody").html("");
					$('#template').tmpl({marketAreas:data.result.recordList}).appendTo('#templateBody');
 					res_data = data.result.recordList;
 				} else {
					$.eAlert("提示信息",data.msg);

				}
			});
		}

		
		//绑定行点击事件
		function initTrClickEvent(){
			$("#templateBody").on('click','tr', function(){
				$("#templateBody tr").css({"background":"white"}); 
				$(this).css({"background":"#eeeeee"});
				//$("#templateBody tr").css({"background":"white"}); 
				//$(this).css({"background":"blue"});
				checked_row = this.rowIndex - 1; 
				console.log(res_data[checked_row]);
				Route.params = {
					areaId : res_data[checked_row].id
				}
			});
		}
		
		//绑定删除事件
		function initDeleteEvent(){
			console.log(checked_row);
			$("#delete_btn").click(function(){
				if(checked_row == undefined){
					$.eAlert("提示信息","请先选中一行进行操作!");
				}
				var id = res_data[checked_row].id;
				
				$.eConfirm("提示", "你确定删除吗？",function() {
					$.getJSON(CONTEXT+"marketArea/delete",{"id":id},function(data){
						if(data.success){
							$.eAlert("提示信息","删除成功");
							$.reloadRoute();
						} else {
							$.eAlert("提示信息",data.msg);
						}
					});
				})
				
			});
		}
		
		initTrClickEvent();
		initDeleteEvent();
		
		//默认初始化数据
		loadData({"pageNum":1,"pageSize":PAGE_SIZE}, true);
		
		
	});
}
