function _call(templateUrl,param){
	$("#main-wrapper").loadPage(templateUrl,
			["lib/css/DT_bootstrap.css","css/form-imput.css","css/resourMage.css","css/parameter.css"],
			["lib/jquery-migrate.js","js/common/pageBar.js","lib/jquery.tmpl.min.js",'js/controller/settings/expenditure/expCommon.js'],
			function(){
				//加载费项类型下拉框数据
				loadExpTypes();
				//表单验证
				validateForm("form-add");
		});
}

function clickSaveForm()
{
	$.eLoading(true);
	$.ajax({
		type: "POST",
		dataType: "json",
		url: CONTEXT+"expenditure/addExpenditure",
		data: $('#form-add').serialize(),
		success: function (data) {
			if(data.success){
				$.eAlert("提示","保存成功");
				window.location="index#expenditure";
			}else {
				$.eAlert("提示",data.msg);
			}
			$.eLoading(false);
		},
		error: function(data) {
			$.eAlert("提示",data.msg);
			$.eLoading(false);
		}
	});
}