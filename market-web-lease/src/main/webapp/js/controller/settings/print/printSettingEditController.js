function _call(templateUrl,param){
	if(!Route.params){
		location.href = "index#print";
		return;
	}
	
	var params=Route.params;//获取页面传值对象
	var id = params.id;
	$("#main-wrapper").loadPage(templateUrl+"/"+id,
			["lib/css/DT_bootstrap.css","css/form-imput.css"],
			["lib/jquery-migrate.js","lib/DT_bootstrap.js","js/common/pageBar.js","lib/jquery.tmpl.min.js"],
	function(){
		function saveData(){
			$.ajax({
				type: "POST",
				dataType: "json",
				data:$('#form-editPrintSetting').serialize(),
				url: CONTEXT+"printSetting/saveEdit",
				async: false,
				success: function (data) {
					if(data.success){
						$.eAlert("提示", "修改成功");
						window.location="index#print";
					}else {
						$.eAlert("错误", data.msg);
					}
				},
				error: function(data) {
					$.eAlert("错误", data.msg);
				}
			});
		}
		
		//绑定表单验证方法
		function validateForm(){
			$("#form-editPrintSetting").validate({  
			    rules:{  
			    	printName:{  
			            required:true
			        },  
			        bizType:{  
			            required:true
			        },  
			        templateId:{  
			            required:true
			        } 
			    },  
			    messages:{
			    	printName:{  
			            required:'必填'
			        },  
			        bizType:{  
			            required:'必选'
			        },  
			        templateId:{  
			            required:'必选'
			        }
			    }, 
			    submitHandler: function() {
			    	saveData();//ajax的提交方法
			    }
			});
		}
		validateForm();
	});
};