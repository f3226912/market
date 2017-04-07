function _call(templateUrl,param){
	$("#main-wrapper").loadPage(templateUrl,
			["css/form-imput.css","css/system.css"],
			[],
			function(){
		
		// 密码格式验证
		jQuery.validator.addMethod("isPwd", function(value, element) {
			var pwd = /^[0-9a-zA-Z]{6,20}$/;
			return this.optional(element) || (pwd.test(value));
		}, "密码格式不符合要求");
		
		$("#form-pwd").validate({
			rules:{  
				nPwd:{required: true,minlength: 6,maxlength:20,isPwd:true},
				confPwd:{required: true,maxlength:20,equalTo: "#nPwd",isPwd:true}
			},  
			messages:{
				nPwd:{required: "必填",minlength:'最少需输入6个字符',maxlength:'最多允许输入20个字符',isPwd:"密码格式不符合要求"},
				confPwd:{required: "必填",maxlength:'最多允许输入20个字符',equalTo: "两次输入的密码不一致",isPwd:"密码格式不符合要求"}
			},
			submitHandler: function (form) {
				$(form).ajaxSubmit({
					type: "POST",
					dataType:"json",
					url:CONTEXT+"sysPlatUser/modify-pwd",
					success: function (data) {
						if(data.success){
//							$.eAlert("修改密码","修改密码成功");
							alert("修改密码成功,请重新登录!");
							window.location="logout";
						} else {
							$.eAlert("修改密码",data.msg);
						}
					}
				})
			}
		});
		
		
		
//		//绑定保存事件
//		$("#savePwd").click(function(){
//			var addData = $('#form-pwd').serialize();
//			$.post(CONTEXT+"sysPlatUser/modify-pwd",addData,function(data){
//				if(data.success){
//					$.eAlert("修改密码","修改密码成功");
//					window.location="index#home";
//				} else {
//					$.eAlert("修改密码",data.msg);
//				}
//			});
//		});
	});
}