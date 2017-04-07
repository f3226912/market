function _call(templateUrl,param){
	$("#main-wrapper").loadPage(templateUrl,
			["lib/css/datepicker.css","css/form-imput.css"],
			["lib/distpicker.data.js","lib/distpicker.js","lib/jquery.tmpl.min.js"],
		function(){
		// 此处编写所有处理代码
		$("#modalPop").modalPop({
			container:"popwintmpl",//
			titleText:"审核合同", //
			popUrl:"",
	        callback:function(){
	        	alert('点击保存')
	        }
		});
		$("#modalPop1").modalPop({
			container:"popwintmpl11",//
			titleText:"审核合同", //
			popUrl:"",
			formValid:modalValid,
	        callback:function(){
	        	
	        	alert('点击确认')
	        }
		});
		//$('#popwintmpl').tmpl().appendTo('#templatepop');
		/*$(".start-date").datepicker({
			format: "yyyy-mm-dd",
			autoclose: true
		});*/
		var $distpicker = $('#distpicker');
		$distpicker.distpicker({});
		/*表单验证*/
		$.validator.setDefaults({
		    submitHandler: function() {
		      alert("提交事件!");
		    }
		});
		/*弹出框表单中验证*/
		function modalValid(){
			$("#commentForm").validate({
				rules:{
					name:"required",
				},
				messages:{
					name: "名字 必填",
				}
			});
		}
		
		
	$().ready(function() {
		
	// 在键盘按下并释放及提交后验证提交表单
	  $("#signupForm").validate({
		    rules: {
		      firstname: "required",
		      lastname: "required",
		      username: {
		        required: true,
		        minlength: 2
		      },
		      password: {
		        required: true,
		        minlength: 5
		      },
		      confirm_password: {
		        required: true,
		        minlength: 5,
		        equalTo: "#password"
		      },
		      email: {
		        required: true,
		        email: true
		      },
		      "topic[]": {
		        required: "#newsletter:checked",
		        minlength: 2
		      },
		      agree: "required"
		    },
		    messages: {
		      firstname: "请输入您的名字",
		      lastname: "请输入您的姓氏",
		      username: {
		        required: "请输入用户名",
		        minlength: "用户名必需由两个字母组成"
		      },
		      password: {
		        required: "请输入密码",
		        minlength: "密码长度不能小于 5 个字母"
		      },
		      confirm_password: {
		        required: "请输入密码",
		        minlength: "密码长度不能小于 5 个字母",
		        equalTo: "两次密码输入不一致"
		      },
		      email: "请输入一个正确的邮箱",
		      agree: "请接受我们的声明",
		      topic: "请选择两个主题"
		    }
		});
	});
		});
	}