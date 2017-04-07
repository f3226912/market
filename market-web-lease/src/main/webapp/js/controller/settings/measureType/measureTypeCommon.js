//绑定表单验证方法
function validateForm(formId){
	$("#"+formId).validate({  
	    rules:{  
	    	code:{  
	            required:true,
	            maxlength:50
	        },  
	        name:{  
	            required:true,
	            maxlength:50
	        },  
	        expId:{  
	        	required:true
	        } 
	    },  
	    messages:{
	    	code:{  
	            required:'必填',
	            maxlength:'限输入1-50个字符'
	        },  
	        name:{  
	            required:'必填',
            	maxlength:'限输入1-50个字符'
	        },  
	        expId:{  
	        	required:'必填'
	        }
	    },
	    submitHandler: function() {
	    	//alert("提交事件!");
	    	clickSaveForm();
	    }
	});
}

/**
 * 获取走表类费项
 */
function loadMeterTypeExp()
{
	//取类型为走表类费项
	$.ajax({
		type: "POST",
		dataType: "json",
		url: CONTEXT+"measureType/expList",
		async: false,
		data: {"expType":2},
		success: function (data) {
			if(data.success && data.result != null){
				$("#expId").html("");
				$('#template').tmpl({rows:data.result}).appendTo('#expId');
			}else {
				$.eAlert("提示信息", data.msg);
			}
		},
		error: function(data) {
			$.eAlert("提示信息", data.msg);
		}
	});
}