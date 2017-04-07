function _call(templateUrl,param){
	$("#main-wrapper").loadPage(templateUrl,
			["lib/css/DT_bootstrap.css","css/parameter.css","css/form-imput.css"],
			["lib/jquery-migrate.js", "js/common/pageBar.js", "lib/jquery.tmpl.min.js","js/common/gd-util.js"],
			function(){
		var PAGE_SIZE = 20;
		/**
		 * 历史抄表记录的
		 */
		function initHistoryPageBar(result){
			// 分页工具组件
			$("#historyPagebar").page({
				pageIndex : 1,
				pageSize : PAGE_SIZE,
				total : result.total,
				callback : function(index){
					$('#historyTemplateBody').html(""); // 清空内容
					// 点击回调处理
					var param = {"areaId":$("#areaId").val(), "budingId":$("#budingId").val(), "name":$("#resourceName_query").val(), "pageSize":PAGE_SIZE};
					param.pageNum = index;
					historyLoadData({"pageNum":1,"pageSize":PAGE_SIZE}, false);
				}
			});
			PAGE_SIZE = $("#sizeSelect").val();
		}
		
		//加载数据
		function historyLoadData(page, isInit){
			//加载历史抄表记录
			$.ajax({
				type: "POST",
				dataType: "json",
				url: CONTEXT+"financeGaugeCharge/query",
				data: {"measureId":id},
				success: function (data) {
					if(data.success){
						$("#historyTemplateBody").html("");
						$('#historyTemplate').tmpl({rows:data.result.recordList}).appendTo('#historyTemplateBody');
					}else {
						$.eAlert("提示信息", data.msg);
					}
					if(isInit){
						initHistoryPageBar(data.result);
					}
					$("#sizeSelect").css({"display":"none"});
				},
				error: function(data) {
					$.eAlert("提示信息", data.msg);
				}
			});
		}
		
		historyLoadData({"pageNum":1,"pageSize":PAGE_SIZE}, true);
	});
	
}