function _call(templateUrl,param){
	$("#main-wrapper").loadPage(templateUrl,
			["lib/css/DT_bootstrap.css","css/parameter.css","css/form-imput.css","css/contract.css"],
			["lib/jquery-migrate.js", "lib/jquery.tmpl.min.js",'js/controller/settings/measureSetting/commonMeasureSetting.js'],
			function(){
		
		if(Route.market == "")
		{
			$("#add_btn").attr("disabled","disabled");
			return;
		}
		
		//批量新增时，默认为表状态为停用
		$("input[name=status]").attr("disabled","disabled");
		loadMeasureType();
		//计量表分类change事件处理
		$("#measureTypeId").change(changeMeasureType);
		
		function validateForm(formId)
		{
			$("#batchAddForm").validate({
				rules: {
					measureTypeId:{
						required:true
					},
					prefix:{
						required:true,
						maxlength:50
					},
					startNum:{
						required:true,
						range:[1,100000000]
					},
					endNum:{
						required:true,
						range:[1,100000000]
					},
					maxVal:{
						required:true,
						range:[0.00,100000000.00]
					},
					expNameTemp:{
						required:true
					},
					status:{
						required:true
					}
				},
				messages:{
					measureTypeId:{
						required:"必填"
					},
					prefix:{
						required:"必填",
						maxLength:"限输入1-50个字符"
					},
					startNum:{
						required:"必填",
						range:"输入范围为1-100000000"
					},
					endNum:{
						required:"必填",
						range:"输入范围为1-100000000"
					},
					maxVal:{
						required:"必填",
						range:"输入范围为0.00-100000000.00"
					},
					expNameTemp:{
						required:"必填"
					},
					status:{
						required:"必填"
					}
				},
			    submitHandler: function() {
			    	clickSaveForm(formId);
			    }
			});

		}
		function clickSaveForm()
		{
			$.eLoading(true);
			$("input[name=status]").attr("disabled",false);
			var param = $("#batchAddForm").serializeJson();
			$.ajax({
				type: "POST",
				dataType: "json",
				url: CONTEXT+"measureSetting/batchAdd",
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
		//提交表单
		validateForm();
	});
}
