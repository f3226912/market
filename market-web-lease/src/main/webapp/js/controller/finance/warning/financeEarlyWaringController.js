function _call(templateUrl,param){
	$("#main-wrapper").loadPage(templateUrl,
			["lib/css/DT_bootstrap.css","css/parameter.css"],
			["lib/jquery-migrate.js","js/common/pageBar.js","lib/jquery.tmpl.min.js"],
  function(){
		
	   var params = Route.param;//获取页面传值对象
	   var PAGE_SIZE = 5;
	   if(params && params.pageSize){
		   PAGE_SIZE = params.pageSize;//取值方式：value=对象.key
		}
		//初始化分页控件
		function initPageBar(result){
			// 分页工具组件
			$("#pagebar2").page({
				pageIndex : 1,
				pageSize : PAGE_SIZE,
				total : result.total,
				callback : function(index, size){
					$('#moneyBody').html(""); // 清空内容
					PAGE_SIZE = size;
					// 点击回调处理
					var tempjson = selectTypeData();
					tempjson.pageNum =index;
					loadData(tempjson, true);
				}
			});
		}
		function selectTypeData(){
			  return {"pageNum":1, "pageSize":PAGE_SIZE};
		}
	
		//加载数据
		function loadData(page, isInit){
			$.ajax({
				url:CONTEXT+"financeShould/finearlyWaring",
				data:page,
				type: "POST",
				dataType:'json',
				success:function(data){
					if(data.success){
						if(isInit){
							initPageBar(data.result);
						}
						$("#moneyBody").html("");
						$('#moneytemplate').tmpl({recordList:data.result.recordList}).appendTo('#moneyBody');
					} else {
						alert(data.msg);
					}
				},
				error:function(er){
					alert(data.msg);
	             }
			});
		}
		
		//默认初始化数据
		loadData({"pageNum":1, "pageSize":PAGE_SIZE}, true);
		
	});
}