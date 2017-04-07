function _call(templateUrl,param){
	$("#main-wrapper").loadPage(templateUrl,
			["lib/css/DT_bootstrap.css","css/form-imput.css","css/resourMage.css","css/parameter.css"],
			["lib/jquery-migrate.js","js/common/pageBar.js","lib/jquery.tmpl.min.js"],
			
			function(){
		var PAGE_SIZE = 10;
		var checked_row, res_data;
		
		//默认初始化数据
		loadData({"pageNum":1,"pageSize":PAGE_SIZE}, true);
		
		//加载数据
		function loadData(page, isInit){
			$.getJSON(CONTEXT+"expenditure/listPageExpenditure",page,function(data){
				
				if(data.success){
					if(isInit){
						initPageBar(data.result);
					}
					$("#templateBody").html("");
					$('#template').tmpl({exps:data.result.recordList}).appendTo('#templateBody');
					
					//将列表数据存在变量中
					res_data = data.result.recordList;
					//绑定点击行事件
					initTrClickEvent();
					//绑定删除事件
					initDeleteEvent();
				} else {
					$.eAlert("提示", data.msg);
				}
			});
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
					PAGE_SIZE = pageSize;
					// 点击回调处理
					loadData({"pageNum":index,"pageSize":pageSize}, false);
				}
			});
		}

		//绑定删除事件
		function initDeleteEvent(){
			$("#delete_btn").click(function(){
				if(checked_row == undefined){
					$.eAlert("提示", "请选择需要删除的数据!");
					return;
				}
				
				var id = res_data[checked_row].id;
				var sysType = res_data[checked_row].sysType;
				if(sysType == "1")
				{
					$.eAlert("提示", "系统级费项不允许删除！");
					return;
				}
				//获取费项关联的计费标准资源、关联的未执行、执行中的合同、关联的计量表信息
				$.getJSON(CONTEXT+"expenditure/validateDelExp",{"expId":id},function(data){
					if(data.success){
						var expStandardCount = data.result.expStandardCount;
						var measureSettingCount = data.result.measureSettingCount;
						var contractCount = data.result.contractCount;
						//判断是否有关联资源
						if(expStandardCount > 0 || measureSettingCount > 0 || contractCount > 0)
						{
							$.eAlert("提示", "该费项下可能关联计费标准，或未执行/执行中的合同，或关联计量表，不能删除！");
						}else
						{
							//没有关联资源，弹出确认框即删除
							$.eConfirm("删除记录","确定删除该记录吗？", function(){
								$.getJSON(CONTEXT+"expenditure/deleteById",{"id":id},function(data){
									if(data.success){
										loadData({"pageNum":1,"pageSize":PAGE_SIZE}, true);
										$.eAlert("提示", "删除成功");
										$.reloadRoute();
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
		}

		//绑定行点击事件
		function initTrClickEvent(){
			$("#templateBody tr").click(function(){
				$("#templateBody tr").removeClass("color-eee");
				$(this).addClass("color-eee");
				checked_row = this.rowIndex - 1; 
			});
		}
		
		$("#addExpStandard").click(function(){
			if(Route.market)
			{
				if(checked_row != null)
				{
					var expType = res_data[checked_row].expType;
					var expId = res_data[checked_row].id;
					Route.params={expType:expType,expId:expId,flag:'fromExp'};
				}
				location.href='index#addExpStandard';
			} else
			{
				$.eAlert("提示", "请选择市场！");
			}
		});
		
		$("#addExp").click(function(){
			if(Route.market){
				location.href='index#addExp';
			} else {
				$.eAlert("提示", "请选择市场！");
			}
		});
	});
}

