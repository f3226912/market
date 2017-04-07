function _call(templateUrl,param){
	$("#main-wrapper").loadPage(templateUrl,
			["lib/css/DT_bootstrap.css","css/parameter.css","css/form-imput.css","css/resourMage.css"],
			["lib/jquery-migrate.js", "js/common/pageBar.js", "lib/jquery.tmpl.min.js",
			 'js/controller/settings/measureSetting/commonMeasureSetting.js',
			 'js/controller/settings/measureSetting/selectResource.js'],
			function(){
		$("#areaId").change(function(){
			loadBuilding($(this).val());
		});
		
		if(Route.market == "")
		{
			$("#add_btn").attr("disabled","disabled");
			return;
		}
		//加载计量表分类
		loadMeasureType();
		//计量表分类change事件处理
		$("#measureTypeId").change(changeMeasureType);
		//提交表单
		validateForm("addForm");
		
		//选择资源页面事件处理
		//点击选择按钮
		$("#btnSelectResource").click(showResourceDialog);
		//根据条件查询资源
		$("#query_btn").click(queryResource);
		//确认选择资源
		$("#enter_btn").click(selectResource);
	});
}

/**
 * 提交表单操作
 */
function clickSaveForm()
{
	$.eLoading(true);
	var param = $("#addForm").serializeJson();
	$.ajax({
		type: "POST",
		dataType: "json",
		url: CONTEXT+"measureSetting/add",
		data: param,
		success: function (data) {
			if(data.success){
				$.eAlert("提示信息", "添加成功");
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