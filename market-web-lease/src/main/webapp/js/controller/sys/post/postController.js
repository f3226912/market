function _call(templateUrl,param){
	$("#main-wrapper").loadPage(templateUrl,
			["css/form-imput.css","css/system.css"],
			["lib/jquery-migrate.js","js/common/pageBar.js","lib/jquery.tmpl.min.js"],
			function(){
		// 此处编写所有处理代码
//		refresh(false);
		var PAGE_SIZE = 20;
		
//		alert(Route.market);
//		alert(Route.company);
		
		// 删除操作
		$('#tb').on('click','.delete', function(){
			var _this = $(this);
			var id = _this.attr("postId");
			
			$.ajax({
    			type: "GET",
    			dataType: "json",
    			url: CONTEXT+"post/checkPost",
    			data: {"id":id},
    			async: false,
    			success: function (data) {
    				if(data.success){
    					$.eConfirm("删除记录","删除该行记录会导致相关业务数据丢失，确定删除该记录吗？", function(){
    						$.getJSON(CONTEXT+"post/deletePost",{"id":id},function(data){
    							if(data.success){
    								$.eAlert("删除记录","删除成功");
    								loadData({"pageNum":1,"pageSize":PAGE_SIZE}, true);
    								_this.parent().parent().remove();
    							} else {
    								$.eAlert("删除记录",data.msg);
    							}
    						});
    					});
    				}else {
    					$.eAlert("删除记录",data.msg);
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
				callback : function(pageNum,pageSize){
					$('#templateBody').html("");
					loadData({"pageNum":pageNum,"pageSize":pageSize}, false);
				}
			});
			PAGE_SIZE = $("#sizeSelect").val();
		}
		
		//加载数据
		function loadData(page, isInit){
			$.getJSON(CONTEXT+"post/list",page,function(data){
				if(data.success){
					if(isInit){
						initPageBar(data.result);
					}
					$("#templateBody").html("");
					if(data.result.total > 0){
						$('#template').tmpl({posts:data.result.recordList}).appendTo('#templateBody');
					}else{
						$('#noDataScript').tmpl({posts:data.result.recordList}).appendTo('#templateBody');
					}
//					initSelectChangeEvent();
				} else {
					//$.eAlert(data.msg);
				}
			});
		}

		//绑定查询
		$("#query").click(function(){
			var name=$("#name").val();
			loadData({
				"pageNum":1,
				"pageSize":PAGE_SIZE,
				"name":name
				}, true);
		});

		//默认初始化数据
		loadData({"pageNum":1,"pageSize":PAGE_SIZE}, true);
	});
}