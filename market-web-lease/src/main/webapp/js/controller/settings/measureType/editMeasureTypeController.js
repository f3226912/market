function _call(templateUrl,param){
	$("#main-wrapper").loadPage(templateUrl,
			["lib/css/DT_bootstrap.css","css/form-imput.css","css/resourMage.css","css/parameter.css"],
			["lib/jquery-migrate.js", "lib/jquery.tmpl.min.js",'js/controller/settings/measureType/measureTypeCommon.js'],
			function(){
		
			//页面初始化，当前未选中市场，不能提交数据
			var market = Route.market;
	    	if(!market)
	    	{
	    		$("#saveForm").attr("disabled","disabled");
	    		return;
	    	}
    	
			//加载走表类费项列表
			loadMeterTypeExp();
			//初始化表单
			var params=Route.params;//获取页面传值对象
			if(params.sysType ==1){
				$("#name").attr("readonly",true);
			}
			var id, expId;
			if(params){
				$("#id").val(params.id);
				$("#code").val(params.code);
				$("#name").val(params.name);
				$("#expId").val(params.expId);
			}
			//表单校验提交
			validateForm("form-edit");
	});
}

function clickSaveForm()
{
	$.eLoading(true);
	var param = $('#form-edit').serializeJson();
	$.ajax({
		type: "POST",
		dataType: "json",
		url: CONTEXT+"measureType/edit",
		data: param,
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