var PAGE_SIZE = 5;
/**
 * 弹出选择资源对话框
 */
function showResourceDialog()
{
	//清空弹出框原有的数据
	$("#areaId").val("");
	$("#buildingId").val("");
	$("#resourceName_query").val();
	//显示弹出框
	$('#myModal').modal();
	//默认加载所有资源数据
	loadData({"pageNum":1,"pageSize":PAGE_SIZE}, true);
	
	//加载查询资源的区域和楼栋下拉列表
	loadArea();
//	loadBuilding();
}


function initTrClickEvent(){
	$("#templateBody .ml-ct").click(function(){
		var bol=$(this).find(".Echeckbox").get(0).checked;
		if(bol==true){
			$(this).find(".Echeckbox").prop('checked', false);
			$(this).removeClass("color-eee");
		}else{
			$(".Echeckbox").attr('checked', false);
			$("#templateBody tr").removeClass("color-eee");
			$(this).find(".Echeckbox").prop('checked', true);
			$(this).addClass("color-eee");
		}
	});
	$(".Echeckbox").click(function(event){
		var bol=$(this).get(0).checked;
		if(bol==true){
			$(this).prop('checked', false);
		}else{
			$(".Echeckbox").attr('checked', false);
			$("#templateBody tr").removeClass("color-eee");
			$(this).prop('checked', true);
		}
	});
}

function initPageBar(result){
	// 分页工具组件
	$("#pagebar").page({
		pageIndex : 1,
		pageSize : PAGE_SIZE,
		total : result.total,
		callback : function(pageNum,pageSize){
			$('#templateBody').html("");
			var param =
			{
				"areaId":$("#areaId").val(),
				"budingId":$("#budingId").val(),
				"name":$("#resourceName_query").val(), 
				"pageNum":pageNum,
				"pageSize":pageSize
			};
			loadData(param, false);
		}
	});
}

/**
 * 加载资源数据
 */
function loadData(page, isInit){
	$.ajax({
         type: "POST",
         url: CONTEXT+"marketResource/query",
         data: page,
         dataType: "json",
         success: function(data){
        	 if(data.success){
					if(isInit){
						initPageBar(data.result);
					}
					$("#templateBody").html("");
					$('#template').tmpl({rows:data.result.recordList}).appendTo('#templateBody');
					
					initTrClickEvent();
				} else {
					$.eAlert("提示信息", data.msg);
				}
         }
	});
}

function queryResource()
{
	var param = {
			"areaId":$("#areaId option:selected").val(), 
			"budingId":$("#budingId option:selected").val(), 
			"name":$("#resourceName_query").val(), 
			"pageSize":PAGE_SIZE, "pageNum":1
			};
	loadData(param, true);
}

/**
 * 确认选择资源
 */
function selectResource()
{
	var str="";
    $("input[name='checkbox']:checkbox").each(function(){ 
        if($(this).attr("checked")){
            str += $(this).val()+","
        }
    });
	if(str == ""){
		$.eAlert("提示信息", "请先选中一行进行操作!");
		return;
	}
	var temp = str.split(",");
	var resourceId = temp[0].split("_")[0];
	var resourceName = temp[0].split("_")[1];
	$("#resourceId").val(resourceId);
	$("#resourceName").val(resourceName);
	$("#myModal").modal("hide");
}


/**
 * 加载区域下拉列表
 */
function loadArea()
{
	$("#areaId").empty();
	$("#budingId").empty();
	$.ajax({
		type: "POST",
		dataType: "json",
		url: CONTEXT+"marketArea/queryAll",
		data: {"status":1},
		sync: false,
		success: function (data) {
			if(data.success){
				var str = "<option value='' selected='selected'>请选择</option>";
				var res_data = data.result.recordList;
				for(var i=0; i < res_data.length; i++){
					str += "<option value='"+res_data[i].id+"'";
					str += ">"+res_data[i].name+"</option>";
				}
				$("#areaId").append(str);
			}else {
				$.eAlert("提示信息", data.msg);
			}
		},
		error: function(data) {
			$.eAlert("提示信息", data.msg);
		}
	});	
}

/**
 *加载楼栋下拉列表
 */
function loadBuilding(areaId)
{
	$("#budingId").empty();
	if(areaId==""){
		return;
	}
	$.ajax({
		type: "POST",
		dataType: "json",
		url: CONTEXT+"marketBuilding/queryAll",
		data: {"status":1,"areaId":areaId},
		sync: false,
		success: function (data) {
			if(data.success){
				var str = "<option value='' selected='selected'>请选择</option>";
				var res_data = data.result;
				for(var i=0; i < res_data.length; i++){
					str += "<option value='"+res_data[i].id+"'";
					str += ">"+res_data[i].name+"</option>";
				}
				$("#budingId").append(str);
			}else {
				$.eAlert("提示信息", data.msg);
			}
		},
		error: function(data) {
			$.eAlert("提示信息", data.msg);
		}
	});
}