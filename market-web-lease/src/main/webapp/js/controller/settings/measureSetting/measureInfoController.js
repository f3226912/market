function _call(templateUrl,param){
	$("#main-wrapper").loadPage(templateUrl,
			["lib/css/DT_bootstrap.css","css/parameter.css","css/form-imput.css","css/resourMage.css"],
			["lib/jquery-migrate.js", "js/common/pageBar.js", "lib/jquery.tmpl.min.js",
			 'js/controller/settings/measureSetting/commonMeasureSetting.js',
			 'js/controller/settings/measureSetting/selectResource.js',"js/common/gd-util.js"],
			function(){
		$("#areaId").change(function(){
			loadBuilding($(this).val());
		});
		
		var params= Route.params;
		//编辑页面没有取到参数，直接跳转到列表页面
		if(params == undefined || params.id == undefined || Route.market == undefined)
		{
			location.href='index#measureSetting';
			return;
		}
		//加载计量表分类
		loadMeasureType();
		
		//计量表分类change事件处理
		$("#measureTypeId").change(changeMeasureType);
		//提交表单
		validateForm("editForm");
		
		//选择资源页面事件处理
		//点击选择按钮
		$("#btnSelectResource").click(showResourceDialog);
		//根据条件查询资源
		$("#query_btn").click(queryResource);
		//确认选择资源
		$("#enter_btn").click(selectResource);
		
		//取列表页面传过来的数据
		var params=Route.params;//获取页面传值对象
		var id,resourceId;
		if(params){
			$("#resourceName").val(params.resourceName);
			$("#maxVal").val(params.maxVal);
			$("#resourceId").val(params.resourceId);
			$("#code").val(params.code);
			$("#expId").val(params.expId);
			$("#expNameTemp").val(params.expName);
			$("#expName").val(params.expName);
			$("#id").val(params.id);
			$("#measureTypeId").val(params.measureTypeId);
			
			id = params.id;
			resourceId = params.resourceId;
			if(params.status == "0"){
				$("input:radio[name='status']").eq(1).attr("checked",true);
			}
		}
		
		if(resourceId)
		{
			//是否可以修改计量表数据
			initSaveFormBtn(resourceId);
		}
		
		//历史抄表
		loadHistoryData(id,{"pageNum":1,"pageSize":PAGE_SIZE}, true);
	});
}

/**
 * 判断计量表资源关联的合同状态，如果关联合同，且状态为执行中，则不能修改该计量表数据
 */
function initSaveFormBtn(resourceId)
{
	$.getJSON(CONTEXT+"measureSetting/queryContractByResourceId",{"resourceId":resourceId},function(data){
		if(data.success)
		{
			var resultList = data.result;
			var flag = false;
			for (var i = 0; i < resultList.length; i++) {
				var contractStatus = resultList[i].contractStatus;
				//根据资源获取到执行中的合同,则不能修改
				if(contractStatus == 1)
				{
					flag = true;
					$("#edit_btn").attr("disabled","disabled");
					break;
				}
			}
			
			if(!flag)
			{
				$("#edit_btn").attr("disabled",false);
			}
		}
	});
}

/**
 * 保存表单数据
 */
function clickSaveForm()
{
	$.eLoading(true);
	var param = $("#editForm").serializeJson();
	$.ajax({
		type: "POST",
		dataType: "json",
		url: CONTEXT+"measureSetting/edit",
		data: param,
		success: function (data) {
			if(data.success){
				$.eAlert("提示信息", "修改成功!");
				window.location="index#measureSetting";
			}else{
				$.eAlert("提示信息", data.msg);
			}
			$.eLoading(false);
		},
		error: function(data) {
			$.eAlert("提示信息", data.msg);
			$.eLoading(false);
		}
	});
}


/**
 * 加载历史抄表数据
 * @param id
 * @param page
 * @param isInit
 */
function loadHistoryData(id,page, isInit)
{
	$.ajax({
		type: "POST",
		dataType: "json",
		url: CONTEXT+"financeGaugeCharge/query",
		data: {"measureId":id},
		success: function (data) {
			if(data.success){
				$("#historyTemplateBody").html("");
				$('#historyTemplate').tmpl({rows:data.result.recordList}).appendTo('#historyTemplateBody');
			}else {
				$.eAlert("提示信息", data.msg);
			}
			if(isInit){
				initHistoryPageBar(id,data.result);
			}
		},
		error: function(data) {
			$.eAlert("提示信息", data.msg);
		}
	});
}

/**
 * 历史抄表记录的
 */
function initHistoryPageBar(id,result){
	// 分页工具组件
	$("#historyPagebar").page({
		pageIndex : 1,
		pageSize : PAGE_SIZE,
		total : result.total,
		callback : function(index,pageSize){
			$('#historyTemplateBody').html(""); // 清空内容
			// 点击回调处理
			PAGE_SIZE = pageSize;
			var param = {"pageNum":index,"pageSize":pageSize};
			loadHistoryData(id,param, false);
		}
	});
}
