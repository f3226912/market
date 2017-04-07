function _call(templateUrl,param){
	$("#main-wrapper").loadPage(templateUrl,
			["lib/css/DT_bootstrap.css","css/form-imput.css","css/resourMage.css","css/contract.css","css/parameter.css"],
			["lib/jquery-migrate.js","js/common/pageBar.js","lib/jquery.tmpl.min.js",'js/controller/settings/expenditure/expCommon.js'],
			function(){
		// 此处编写所有处理代码
		var params= Route.params;
		//编辑页面没有取到参数，直接跳转到列表页面
		if(params == undefined || params.id == undefined)
		{
			location.href='index#expenditure';
			return;
		}
		var id =params.id;//取值方式：value=对象.key
		//加载下拉框数据
		loadExpTypes();
		
		//渲染表单数据
		getExpData(id);
		
		//表单验证
		validateForm("form-edit");
	});
}

function getExpData(id)
{
	$.ajax({
		type: "GET",
		dataType: "json",
		url: CONTEXT+"expenditure/getById?id="+id,
		data: null,
		success: function (data) {
			if(data.success){
				var post=data.result;
				initForm(post);
			}else {
				$.eAlert("提示",data.msg);
			}
		},
		error: function(data) {
			$.eAlert("提示",data.msg);
		}
	});
}

function initForm(obj)
{
	if(obj){
		for(var k in obj) {
			$("#"+k).val(obj[k]);
			$('input[name="'+k+'"]').val(obj[k]);
			//系统级费项不允许修改保存
			if(k == "sysType" && obj[k] == "1")
			{
				$("#saveForm").attr("disabled","disabled");
			}
		}
	}
}

function clickSaveForm()
{
	$.eLoading(true);
	$.ajax({
		type: "POST",
		dataType: "json",
		url: CONTEXT+"expenditure/updateExp",
		data: $('#form-edit').serialize(),
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
