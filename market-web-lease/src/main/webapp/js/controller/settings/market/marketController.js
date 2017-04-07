function _call(templateUrl,param){
	$("#main-wrapper").loadPage(templateUrl,
			["lib/css/DT_bootstrap.css","css/parameter.css","css/contract.css"],
			["lib/jquery-migrate.js","js/common/pageBar.js","lib/jquery.tmpl.min.js","js/common/gd-util.js"],
			function(){

				var PAGE_SIZE = 20;

				// 初始化分页控件
				function initPageBar(result) {
					// 分页工具组件
					$("#pagebar").page({
						pageIndex : 1,
						pageSize : PAGE_SIZE,
						total : result.total,
						callback : function(index) {
							$('#templateBody').html(""); // 清空内容
							// 点击回调处理
							loadData({
								"pageNum" : index,
								"pageSize" : PAGE_SIZE
							}, false);
						}
					});
				}

				// 加载数据
				function loadData(page, isInit) {
					$.getJSON(CONTEXT + "market/query", page, function(data) {

						if (data.success) {
							if (isInit) {
								initPageBar(data.result);
							}
							$("#templateBody").html("");
							$('#template').tmpl({markets:data.result.recordList}).appendTo('#templateBody');
		 					initSelectChangeEvent();
						} else {
							alert(data.msg);
						}
					});
				}

				function initSelectChangeEvent(){
					$("#sizeSelect").change(function(){
						PAGE_SIZE = $("#sizeSelect").val();
						loadData({ "pageNum":1, "pageSize":PAGE_SIZE, }, true);
					});
				}
				
				// 默认初始化数据
				loadData({
					"pageNum" : 1,
					"pageSize" : PAGE_SIZE
				}, true);

			});
}
