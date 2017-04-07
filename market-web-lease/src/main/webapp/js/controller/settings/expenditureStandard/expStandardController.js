var PAGE_SIZE = 10;
var checked_row, res_data;
var expId;

function _call(templateUrl,param){
	$("#main-wrapper").loadPage(templateUrl,
			["lib/css/DT_bootstrap.css","css/form-imput.css","css/resourMage.css","css/parameter.css"],
			["lib/jquery-migrate.js","js/common/pageBar.js","lib/jquery.tmpl.min.js"],
			
			function(){
		var params = Route.params;
		if(params != undefined && params.expId != undefined)
		{
			expId = params.expId;
			loadData({"pageNum":1,"pageSize":PAGE_SIZE,expId:expId}, true);
		}else
		{
			loadData({"pageNum":1,"pageSize":PAGE_SIZE}, true);
		}
		
		$("#addExpStandard").click(function(){
			if(Route.market){
				Route.params={flag:'fromExpStandard'};
				location.href='index#addExpStandard';
			} else {
				$.eAlert("提示","请选择市场！");
			}
		});
	});
}

//加载数据
function loadData(page, isInit){
	$.getJSON(CONTEXT+"expenditureStandard/listExpStandard",page,function(data){
		
		if(data.success){
			if(isInit){
				initPageBar(data.result);
			}
			$("#templateBody").html("");
			$('#template').tmpl({expStandards:data.result.recordList}).appendTo('#templateBody');
			
			//将列表数据存在变量中
			res_data = data.result.recordList;
			//绑定点击行事件
			initTrClickEvent();
			//绑定删除事件
			initDeleteEvent();
		} else {
			$.eAlert("提示",data.msg)
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
			if(expId != undefined)
			{
				loadData({"pageNum":index,"pageSize":pageSize,expId:expId}, false);
			}else
			{
				loadData({"pageNum":index,"pageSize":pageSize}, false);
			}
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
		//获取计费标准关联的未执行、执行中的合同
		$.getJSON(CONTEXT+"expenditureStandard/validateDelExpStandard",{"expStandardId":id},function(data){
			if(data.success){
				var contarctCount = data.result.contarctCount;
				//判断是否有关联资源
				if(contarctCount >0)
				{
					$.eAlert("提示", "该计费标关联了未执行/执行中的合同不能删除！");
				}else
				{
					//没有关联资源，弹出确认框即删除
					$.eConfirm("删除记录","确定删除该记录吗？", function(){
						$.getJSON(CONTEXT+"expenditureStandard/deleteById",{"id":id},function(data){
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

