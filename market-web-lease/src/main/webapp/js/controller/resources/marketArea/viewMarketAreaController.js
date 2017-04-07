function _call(templateUrl,param){
	$("#main-wrapper").loadPage(templateUrl,
			["lib/css/DT_bootstrap.css","css/form-imput.css","css/resourMage.css"],
			["lib/jquery-migrate.js","lib/DT_bootstrap.js","js/common/pageBar.js","lib/jquery.tmpl.min.js"],
			function(){
		var params=Route.params;//获取页面传值对象
		var marketAreaId=params.marketAreaId;//取值方式：value=对象.key

		//填充表单数据
		var initForm = function(obj){
			if(obj){
				$("#id").val(obj.id);
				$("#code").val(obj.code);
				$("#name").val(obj.name);
				$("#sort").val(obj.sort);
			}
		}
		
		//获取对象数据
		$.ajax({
			type: "post",
			dataType: "json",
			data:{marketAreaId:marketAreaId},
			url: CONTEXT+"marketArea/getByMarketAreaId",
			async: false,
			success: function (data) {
				if(data.success){
					var post=data.result;
					initForm(post);
				}else {
					$.eAlert(data.msg);
				}
			},
			error: function(data) {
				$.eAlert(data.msg);
			}
		});
		
		$("#cancle").click(function(){
			window.location="index#marketArea";
		});
		
		function saveData(){
			$.ajax({
				type: "POST",
				dataType: "json",
				url: CONTEXT+"marketArea/update",
				data: $('#updateForm').serialize(),
				success: function (data) {
					if(data.success){
						$.eAlert("提示信息","保存成功");
						window.location="index#marketArea";
					}else {
						$.eAlert("提示信息",data.msg);
					}
				},
				error: function(data) {
					$.eAlert("提示信息",data.msg);
				}
			});
		}
		
//		$.validator.setDefaults({
//		    
//		});
		validateForm();//绑定表单验证方法
		function validateForm(){
			$("#updateForm").validate({
			    rules: {
			    	code: "required",
			    	name: "required",
			    	sort: {
			    		required:true,
			        	range:[1,10000]
			    	}
			    },
			    messages: {
			    	code: "必填",
			    	name: "必填",
			    	sort:{
			    		required:"必填",
			        	range:'范围：1-10000'
			    	}
			    },
			    submitHandler: function() {
			    	saveData();
			    }
			});
		}
	});
}