function _call(templateUrl,param){
	$("#main-wrapper").loadPage(templateUrl,
			["lib/css/DT_bootstrap.css","css/parameter.css","css/contract.css"],
			["lib/jquery-migrate.js","js/common/pageBar.js","lib/jquery.tmpl.min.js"],
  function(){
   var params=Route.param;//获取页面传值对象
   var PAGE_SIZE = 10;
   if(params){
	   PAGE_SIZE=params.pageSize;//取值方式：value=对象.key
		}
  
	//初始化分页控件
	function initPageBar(result){
		// 分页工具组件
		$("#pagebar").page({
			pageIndex : 1,
			pageSize : PAGE_SIZE,
			total : result.total,
			callback : function(index,pageSize){
				$('#templateBody').html(""); // 清空内容
				PAGE_SIZE=pageSize;
				// 点击回调处理
				var tempjson = selectTypeData();
				tempjson.pageNum =index;
				loadData(tempjson, false);
			}
		});
		PAGE_SIZE = $("#sizeSelect").val();
	}
	
	//加载数据
	function loadData(page, isInit){
		$.ajax({
			url:CONTEXT+"contractRemind/endTimeRemindQueryPage",
			data:page,
			type: "POST",
			dataType:'json',
			success:function(data){
				if(data.success){
					if(isInit){
						initPageBar(data.result);
					}
					$("#templateBody").html("");
					console.log(data.result.total);
					$('#template').tmpl({rows:data.result.recordList}).appendTo('#templateBody');
				} else {
					alert(data.msg);
				}
			},
			error:function(er){
				alert(data.msg);
             }
			});
	}
		
		function selectTypeData(){
			
		  return {"pageNum":1,
			      "pageSize":PAGE_SIZE,
			      };
		}
		//默认初始化数据
		loadData(selectTypeData(), true);
		
	});
}