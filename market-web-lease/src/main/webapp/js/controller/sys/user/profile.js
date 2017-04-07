function _call(templateUrl,param){
	try{
		var params=Route.params;//获取页面传值对象
		var tab=params.tab;//取值方式：value=对象.key
		templateUrl= templateUrl + "?tab=" + tab;
	}catch (e) {
	}
	$("#main-wrapper").loadPage(templateUrl,
			["css/form-imput.css","css/system.css"],
			[],
			function(){
			var tab = $("#tab").val();
			if(tab==2){
				$("#tab-setting").addClass("active");
				$("#tab-userInfo").removeClass("active");
				$("#setting").addClass("active");
				$("#userInfo").removeClass("active");
			}
			
			// 手机号码验证
			jQuery.validator.addMethod("isMobile", function(value, element) {
				var length = value.length;
				var mobile = /^[1][34587][0-9]{9}$/;
				return this.optional(element) || (length == 11 && mobile.test(value));
			}, "请正确填写您的手机号码");
		
			$("#form-edit").validate({
				rules:{  
					name:{required:true,maxlength:20},  
					deptNo:{maxlength:20},
					landline:{maxlength:20},
					mobile:{required:true,maxlength:11,isMobile:true},
					mail:{email: true,maxlength:20}
				},  
				messages:{
					name:{required:'必填',maxlength:'最多允许输入20个中文字符'},
					deptNo:{maxlength:'最多允许输入20个字符'},
					landline:{maxlength:'最多允许输入20个字符'},
					mobile:{required:'必填',maxlength:'最多允许输入11位手机号码',isMobile:"请填写正确的手机号"},
					mail:{email:"请填写正确的邮箱",maxlength:"最多允许输入20个字符"}
				},
				submitHandler: function (form) {
					$(form).ajaxSubmit({
						type: "POST",
						dataType:"json",
						url:CONTEXT+"sysCompanyUser/profile",
						success: function (data) {
							if(data.success){
								$.eAlert("我的账号","修改账号信息成功");
							} else {
								$.eAlert("我的账号",data.msg);
							}
						}
					})
				}
			});
			
			// 密码格式验证
			jQuery.validator.addMethod("isPwd", function(value, element) {
				var pwd = /^[0-9a-zA-Z]{6,20}$/;
				return this.optional(element) || (pwd.test(value));
			}, "密码格式不符合要求");
			
			$("#form-pwd").validate({
				rules:{  
					cPwd:{required:true,maxlength:20,isPwd:true},  
					nPwd:{required: true,minlength: 6,maxlength:20,isPwd:true},
					confPwd:{required: true,maxlength:20,equalTo: "#nPwd",isPwd:true}
				},  
				messages:{
					cPwd:{required:'必填',maxlength:'最多允许输入20个字符',isPwd:"密码格式不符合要求"},
					nPwd:{required: "必填",minlength:'最少需输入6个字符',maxlength:'最多允许输入20个字符',isPwd:"密码格式不符合要求"},
					confPwd:{required: "必填",maxlength:'最多允许输入20个字符',equalTo: "两次输入的密码不一致",isPwd:"密码格式不符合要求"}
				},
				submitHandler: function (form) {
					$(form).ajaxSubmit({
						type: "POST",
						dataType:"json",
						url:CONTEXT+"sysCompanyUser/setting",
						success: function (data) {
							if(data.success){
//								$.eAlert("修改密码","修改密码成功,请重新登录");
								alert("修改密码成功,请重新登录!");
								window.location="logout"
							} else {
								$.eAlert("修改密码",data.msg);
							}
						}
					})
				}
			});
	});
}