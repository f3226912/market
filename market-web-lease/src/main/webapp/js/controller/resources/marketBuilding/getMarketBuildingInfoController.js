function _call(templateUrl,param){
	$("#main-wrapper").loadPage(templateUrl,
			["css/system.css"],
			["lib/jquery-migrate.js","lib/DT_bootstrap.js","js/common/pageBar.js","lib/jquery.tmpl.min.js"],
			function(){
		
		var checked_row, res_data;
		var PAGE_SIZE = 20;
		
		//初始化分页控件
//		function initPageBar(result){
//			// 分页工具组件
//			$("#pagebar").page({
//				pageIndex : 1,
//				total : result.total,
//				callback : function(index, pageSize){
//					$('#templateBody').html(""); // 清空内容
//					 PAGE_SIZE=pageSize;
//					// 点击回调处理
//					loadData({"pageNum":index,"pageSize":pageSize}, false);
//				}
//			});
//		}
//					//初始化分页控件
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
			$.getJSON(CONTEXT+"marketBuilding/getMarketBuildingInfo",page,function(data){
				if(data.success){
					if(isInit){
						initPageBar(data.result);
					}
					$("#templateBody").html("");
					$('#template').tmpl({buildings:data.result.recordList}).appendTo('#templateBody');
					//将列表数据存在变量中
					res_data = data.result.recordList;
					initTrClickEvent();
				} else {
//					$.eAlert("提示信息",data.msg);
				}
			});
		}
	
		//绑定行点击事件
		function initTrClickEvent(){
			$("#templateBody tr").click(function(){
				$("#templateBody tr").css({"background":"white"}); 
				$(this).css({"background":"#eeeeee"});
				checked_row = this.rowIndex - 1; 
			});
		}
		
		
		$("#batch_btn").click(function(){
			if(checked_row == undefined){
				$.eAlert("提示信息","请选择楼栋操作");
				return;
			}else{
				var builCode = res_data[checked_row].code;
				var builName = res_data[checked_row].name;
				var builId = res_data[checked_row].id;
				var areaId = res_data[checked_row].areaId;

				$.ajax({
					url : CONTEXT + 'marketBuilding/isResource?builId='+builId,
					type : 'post',
					traditional : true,
					success : function(data) {
						if (data.success) {
							Route.params={builCode:builCode, builName:builName,builId:builId,areaId:areaId};
							location.href='index#addResource';
						} else {
							if(data.code==1){
								$.eAlert("提示信息",data.msg);
								
							}else if(data.code==2){
								$.eConfirm("提示信息",data.msg,function(){
									Route.params={builCode:builCode, builName:builName,builId:builId,areaId:areaId};
									location.href='index#addResource';
								})
							}
						}
					},
					error : function(data) {
						$.eAlert("提示信息",data.msg);
					}
				});
			}
	});
	// 平面图入口
	$("#mapBtn").click(function(){
		if(checked_row == undefined){
			$.eAlert("提示信息","请选择楼栋操作");
			return;
		}else{
			var builCode = res_data[checked_row].code;
			var builName = res_data[checked_row].name;
			var builId = res_data[checked_row].id;
			var areaId = res_data[checked_row].areaId;
			Route.params={builCode:builCode, builName:builName,builId:builId,areaId:areaId};
			location.href = 'index#viewImgBuilding';
		}
	});
		
		//绑定删除事件
		$("#delete").click(function(){
			if(checked_row == undefined){
				$.eAlert("提示信息","请选择要删除的楼栋");
				return;
			}
			var buildingId = res_data[checked_row].id;
			if($.eConfirm("删除记录","你确定要删除吗？",function(){
				$.getJSON(CONTEXT+"marketBuilding/delMarketBuilding",{"buildingId":buildingId},function(data){
					if(data.success){
						loadData({"pageNum":1,"pageSize":PAGE_SIZE}, true);
						$.eAlert("提示信息",data.msg);
						return;
					} else {
						$.eAlert("提示信息",data.msg);
						return;
					}
				});
			
			
			}));
		});
		//默认初始化数据
		loadData({"pageNum":1,"pageSize":PAGE_SIZE}, true);
		
		//点击 资源调整
		$("#resourceAdjust").on("click",function(){
			var checkedRow = res_data[checked_row];
			if(checkedRow != undefined){
				Route.params  = {id:checkedRow.id,name:checkedRow.name};
				location.href="index#resourceAdjust";
			}else{
				$.eAlert("提示","请选中楼栋，谢谢");
			}
		});
	});
}