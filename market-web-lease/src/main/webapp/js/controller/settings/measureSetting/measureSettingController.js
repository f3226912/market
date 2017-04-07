// 此处编写所有处理代码
var PAGE_SIZE = 20;
var checked_row, res_data;

function _call(templateUrl,param){
	$("#main-wrapper").loadPage(templateUrl,
			["lib/css/DT_bootstrap.css","css/parameter.css","css/contract.css"],
			["lib/jquery-migrate.js","js/common/pageBar.js","lib/jquery.tmpl.min.js"],
			function(){
		
		//绑定删除事件
		$("#delete_btn").click(clickDelete);
		
		//绑定启用事件
		$("#enable_btn").click(clickEnable);
		
		//绑定禁用事件
		$("#disable_btn").click(clickDisable);
		
		//绑定查询事件
		$("#query_btn").click(queryData);
		
		$("#add_btn").click(clickAdd);
		$("#batchAdd_btn").click(clickBatchAdd);
		
		if(Route.market)
		{
			loadMeasureType();
			//默认初始化数据
			loadData({"pageNum":1,"pageSize":PAGE_SIZE}, true);
		}
	});
}

//加载数据
function loadData(page, isInit){
	$.ajax({
         type: "POST",
         url: CONTEXT+"measureSetting/query",
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
					initTrClickEvent();
				} else {
					$.eAlert("提示信息", data.msg);
				}
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
			var param = $("#queryForm").serializeJson();
			param.pageNum = index;
			param.pageSize = pageSize;
			// 点击回调处理
			loadData(param, false);
		}
	});
}

function queryData(){
	$("#query_btn").attr("disabled", true);
	var param = $("#queryForm").serializeJson();
	param.pageSize = PAGE_SIZE;
	loadData(param, true);
	setTimeout("$('#query_btn').attr('disabled', false)", 2000);
}

//绑定行点击事件
function initTrClickEvent(){
	$("#checkboxAll").click(function(){
		if($("#checkboxAll").get(0).checked){
			$(".Echeckbox").prop('checked', true);
			$(".costDefinition #templateBody .ml-ct").addClass("color-eee");
		}else{
			$(".Echeckbox").prop('checked', false);
			$(".costDefinition #templateBody .ml-ct").removeClass("color-eee");
		}
	});
	$("#templateBody .ml-ct").click(function(){
		var bol=$(this).find(".Echeckbox").get(0).checked;
		if(bol==true){
			$(this).find(".Echeckbox").prop('checked', false);
			$(this).removeClass("color-eee");
		}else{
			$(this).find(".Echeckbox").prop('checked', true);
			$(this).addClass("color-eee");
		}
	})
	
	$(".Echeckbox").click(function(event){
		var bol=$(this).get(0).checked;
		if(bol==true){
			$(this).prop('checked', false);
		}else{
			$(this).prop('checked', true);
		}
	})
}

function clickDelete(){
	var str="";
	var status = "";
    $("input[name='subBox']:checkbox").each(function(){ 
        if($(this).attr("checked")){
            str += $(this).val()+","
            status = $(this).attr("data-status");
        }
    })
    
    if(str == ""){
		$.eAlert("提示信息", "请选择操作对象!");
		return;
	}
    var ids = str.split(",");
    if(ids.length > 2){
    	$.eAlert("提示信息", "删除操作不支持批量!");
		return;
    }
	if(status == 1){
		$.eAlert("提示信息", "计量表启用中，不能删除!");
		return;
	}
	$.eConfirm("删除记录","确定删除该记录吗？", function(){
		$.getJSON(CONTEXT+"measureSetting/del",{"id":ids[0], "optFlag":"0"},function(data){
			if(data.success){
				var param = $("#queryForm").serializeJson();
				param.pageSize = PAGE_SIZE;
				loadData(param, true);
				$.eAlert("提示信息", "删除成功!");
			} else {
				$.eAlert("提示信息", data.msg);
			}
		});
	});
	
}

function clickEnable(){
	var str="";
    $("input[name='subBox']:checkbox").each(function(){ 
        if($(this).attr("checked")){
            str += $(this).val()+","
        }
    })
	if(str == ""){
		$.eAlert("提示信息", "请选择操作对象!");
		return;
	}
	//var id = res_data[checked_row].id;
	$.getJSON(CONTEXT+"measureSetting/enableOrDisable",{"ids":str, "optFlag":"1"},function(data){
		if(data.success){
			var param = $("#queryForm").serializeJson();
			param.pageSize = PAGE_SIZE;
			loadData(param, true);
			$.eAlert("提示信息", "操作成功!");
		} else {
			$.eAlert("提示信息", data.msg);
		}
	});
}

function clickDisable(){
	var str="";
    $("input[name='subBox']:checkbox").each(function(){ 
        if($(this).attr("checked")){
            str += $(this).val()+","
        }
    })
	if(str == ""){
		$.eAlert("提示信息", "请选择操作对象!");
		return;
	}
	$.getJSON(CONTEXT+"measureSetting/enableOrDisable",{"ids":str, "optFlag":"2"},function(data){
		if(data.success){
			var param = $("#queryForm").serializeJson();
			param.pageSize = PAGE_SIZE;
			loadData(param, true);
			$.eAlert("提示信息", "操作成功!");
		} else {
			$.eAlert("提示信息", data.msg);
		}
	});
}

function clickAdd(){
	if(Route.market != ""){
		location.href='index#addMeasureSetting';
	} else {
		$.eAlert("提示","请选择市场！");
	}
}

function clickBatchAdd(){
	if(Route.market){
		location.href='index#batchAddMeasureSetting';
	} else {
		$.eAlert("提示","请选择市场！");
	}
}

function loadMeasureType()
{
	//计量表分类
	$.ajax({
		type: "POST",
		dataType: "json",
		url: CONTEXT+"measureType/queryAll",
		data: {"status":1},
		success: function (data) {
			if(data.success){
				$("#measureTypeNameTemplateBody").html("");
				$('#measureTypeNameTemplate').tmpl({rows:data.result.recordList}).appendTo('#measureTypeNameTemplateBody');
			}else {
				$.eAlert("提示信息", data.msg);
			}
		},
		error: function(data) {
			$.eAlert("提示信息", data.msg);
		}
	});	
}