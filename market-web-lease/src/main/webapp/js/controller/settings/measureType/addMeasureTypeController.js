function _call(templateUrl,param){
	$("#main-wrapper").loadPage(templateUrl,
			["lib/css/DT_bootstrap.css","css/form-imput.css","css/resourMage.css","css/parameter.css"],
			["lib/jquery-migrate.js", "lib/jquery.tmpl.min.js",'js/controller/settings/measureType/measureTypeCommon.js'],
			function(){
		if(Route.market == undefined)
    	{
    		$("#saveForm").attr("disabled","disabled");
    		return;
    	}
		//获取当前市场下走表类费项列表
		loadMeterTypeExp();
		
		//当前无市场时，不能提交
		var market = Route.market;
    	
    	//校验病提交表单
    	validateForm("form-add");
	});
}

function clickSaveForm()
{
	$.eLoading(true);
	$.ajax({
		type: "POST",
		dataType: "json",
		url: CONTEXT+"measureType/add",
		data: $('#form-add').serialize(),
		success: function (data) {
			if(data.success){
				$.eAlert("提示信息", "保存成功");
				window.location="index#measureType";
			}else {
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