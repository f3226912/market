function _call(templateUrl,param){
	$("#main-wrapper").loadPage(templateUrl,
			["css/form-imput.css","css/system.css"],
			["lib/bootstrap-datepicker.js","lib/bootstrap-datetimepicker.js","lib/distpicker.data.js","js/common/pageBar.js","lib/jquery.tmpl.min.js"],
			function(){
		var PAGE_SIZE = 20;
		var res_data;
		//初始化分页控件
		function initPageBar(result){
			// 分页工具组件
			$("#pagebar").page({
				pageIndex : 1,
				pageSize : PAGE_SIZE,
				total : result.total,
				callback : function(pageNum,pageSize){
					
					$('#taskBody').html(""); // 清空内容
					// 点击回调处理
					loadData({"pageNum":pageNum,"pageSize":pageSize}, false);
				}
			});
		}
		
		//加载数据
		function loadData(page, isInit){
			$.getJSON(CONTEXT+"wfTask/getGtasks4Page",page,function(data){
				if(data.success){
					if(isInit){
						initPageBar(data.result);
					}
					$("#taskBody").html("");
					$('#taskScript').tmpl({taskList:data.result.recordList}).appendTo('#taskBody');
				}
			});
		};
		loadData({"pageNum":1,"pageSize":PAGE_SIZE}, true);
	});
}