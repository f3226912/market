function _call(templateUrl,param){
	$("#main-wrapper").loadPage(templateUrl,
			["css/DT_bootstrap.css"],
			["lib/jquery-migrate.js","js/common/pageBar.js","lib/jquery.tmpl.min.js"],
			function(){

		var PAGE_SIZE = 20;
		
		//初始化分页控件
		function initPageBar(result){
			// 分页工具组件
			$("#printSettingPagebar").page({
				pageIndex : 1,
				pageSize : PAGE_SIZE,
				total : result.total,
				callback : function(pageNum, pageSize){
					$('#printSettingTemplateBody').html(""); // 清空内容
					PAGE_SIZE = pageSize;
					// 点击回调处理
					loadData({"pageNum":pageNum,"pageSize":pageSize}, false);
				}
			});
		}
		
		function getSearchParams(){
			var params = {
				printName:$("#printSettingSearchForm #printName").val(),
				bizType:$("#printSettingSearchForm #bizType").val(),
				marketId:$("#printSettingSearchForm #marketId").val(),
				pageNum:1,
				pageSize:PAGE_SIZE
				
			};
			return params;
		}
		
		//加载数据
		function loadData(page, isInit){
			$.ajax({
				url:CONTEXT+"printSetting/queryByPage",
				data:page,
				type: "POST",
				dataType:'json',
				success:function(data){
					if(data.success){
						if(isInit){
							initPageBar(data.result);
						}
						$("#printSettingTemplateBody").html("");
					
						if(data.result&&data.result.recordList&&data.result.recordList.length>0){
							$('#printSettingTemplate').tmpl({settings:data.result.recordList}).appendTo('#printSettingTemplateBody');
						} else {
							$("#printSettingTemplateBody").html("<tr><td colspan='7'>没有找到相关数据。</td></tr>");
						}
					} else {
						alert(data.msg);
					}
				}
			});
		}
		
		function initMarket(){
			$.ajax({
				url:CONTEXT+"market/queryListForSelect",
				type: "POST",
				dataType:'json',
				success:function(data){
					if(data.success){
						$.each(data.result, function(index, item){
							$("#marketId").append("<option value='"+item.id+"'>"+item.name+"</option>");
						});
					}
				}
			});
		}
		
		//默认初始化数据
		loadData({"pageNum":1,"pageSize":PAGE_SIZE}, true);
		
		//绑定全选、取消全选事件
		$("#printSettingCheckAll").click(function(){
			$("input[name='printSettingCheckbox']").prop("checked",this.checked);
		});
		
		$("#printSettingSearchForm #query").click(function(){
			var params = getSearchParams();
			loadData(params, true);
		});	
		initMarket();
	});
}