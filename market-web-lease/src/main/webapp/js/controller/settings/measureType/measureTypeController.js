function _call(templateUrl,param){
	$("#main-wrapper").loadPage(templateUrl,
			["lib/css/DT_bootstrap.css","css/parameter.css","css/contract.css"],
			["lib/jquery-migrate.js","js/common/pageBar.js","lib/jquery.tmpl.min.js"],
			function(){
		// 此处编写所有处理代码
		var PAGE_SIZE = 20;
		var checked_row, res_data;
		//初始化分页控件
		function initPageBar(result){
			// 分页工具组件
			$("#pagebar").page({
				pageIndex : 1,
				pageSize : PAGE_SIZE,
				total : result.total,
				callback : function(index,pageSize){
					$('#templateBody').html(""); // 清空内容
					PAGE_SIZE = pageSize;
					// 点击回调处理
					loadData({"pageNum":index,"pageSize":pageSize}, false);
				}
			});
		}
		
		//加载数据
		function loadData(page, isInit){
			if(Route.market)
			{
			$.ajax({
	             type: "POST",
	             url: CONTEXT+"measureType/query",
	             data: page,
	             dataType: "json",
	             success: function(data){
	            	 if(data.success){
	 					if(isInit){
	 						initPageBar(data.result);
	 					}
	 					$("#templateBody").html("");
	 					$('#template').tmpl({rows:data.result.recordList}).appendTo('#templateBody');
	 					res_data = data.result.recordList;
	 					//initDeleteEvent();
	 					initTrClickEvent();
	 					//initSelectChangeEvent();
	 				} else {
	 					$.eAlert("提示信息", data.msg);
	 				}
	             }
			});
			}
		}
	
		//绑定行点击事件
		function initTrClickEvent(){
			$("#templateBody tr").click(function(){
				$("#templateBody tr").removeClass("color-eee");
				$(this).addClass("color-eee");
				checked_row = this.rowIndex - 1; 
			});
		}
		
		//绑定删除事件
		$("#delete_btn").click(function(){
			if(checked_row == undefined){
				$.eAlert("提示信息", "请先选中一行进行操作!");
				return;
			}
			var id = res_data[checked_row].id;
			//var id = $(this).attr("orgId");
			if(res_data[checked_row].sysType == "1" || res_data[checked_row].sysType == 1){
				$.eAlert("提示信息", "系统级的数据不能删除!");
				return;
			}
			//获取费项关联的计费标准资源、关联的未执行、执行中的合同、关联的计量表信息
			$.getJSON(CONTEXT+"measureSetting/queryCountByMeasureTypeId",{"measureTypeId":id},function(data){
				if(data.success){
					var measureSettingCount = data.result;
					//判断是否有关联资源
					if(measureSettingCount > 0)
					{
						$.eAlert("提示", "该计量表分类有关联计量表，不能删除！");
					}else
					{
						//没有关联资源，弹出确认框即删除
						$.eConfirm("删除记录","确定删除该记录吗？", function(){
							$.getJSON(CONTEXT+"measureType/del",{"id":id},function(data){
								if(data.success){
									loadData({"pageNum":1,"pageSize":PAGE_SIZE}, true);
									$.eAlert("提示", "删除成功");
									$.reloadRoute();
									//先这么写 删除操作会有多个弹出框
								} else {
									$.eAlert("提示", data.msg);
								}
							});
						});
					}
				} else {
					$.eAlert("提示", data.msg);
				}
			});
		});

		$("#add_btn").click(function(){
			if(Route.market){
				location.href='index#addMeasureType';
			} else {
				$.eAlert("提示", "请选择市场！");
			}	
		});
		
		//默认初始化数据
		loadData({"pageNum":1,"pageSize":PAGE_SIZE}, true);
	});
}