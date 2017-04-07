function _call(templateUrl,param){
	$("#main-wrapper").loadPage(templateUrl,
			["lib/css/DT_bootstrap.css","css/form-imput.css"],
			["lib/jquery-migrate.js","lib/DT_bootstrap.js","js/common/pageBar.js","lib/jquery.tmpl.min.js","js/common/gd-util.js"],
			function(){
		var marketId = $("#marketId").val();
		if(marketId == ""){
			$.eAlert("提示", "获取当前市场失败，无法进行新增操作！");
			Route.params={active:1};
			location.href='index#print';
		}
		
		function saveData(){
			$.ajax({
				type: "POST",
				dataType: "json",
				data:$('#form-addPrintSetting').serialize(),
				url: CONTEXT+"printSetting/saveAdd",
				async: false,
				success: function (data) {
					if(data.success){
						$.eAlert("提示", "新增成功");
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
			$("#form-addPrintSetting").validate({  
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