//绑定表单验证方法
function validateForm(formId){
	$("#"+formId).validate({  
	    rules:{  
	    	expType:{  
	            required:true
	        },  
	        name:{  
	            required:true,
	            maxlength:50
	        },  
	        expDetail:{  
	            maxlength:100
	        } 
	    },  
	    messages:{
	    	expType:{  
	            required:'必填'
	        },  
	        name:{  
	            required:'必填',
            	maxlength:'限输入1-50个字符'
	        },  
	        expDetail:{  
	            maxlength:'限输入1-100个字符'
	        }
	    },
	    submitHandler: function() {
	    	//alert("提交事件!");
	    	clickSaveForm();
	    }
	});
}

function loadExpTypes()
{
	$.ajax({
		type: "GET",
		dataType: "json",
		url: CONTEXT+"dictionary/getByType?type=expenditureType",
		success: function (data) {
			var expTypes = data.result;
			if(expTypes.length>0)
			{
				$("#expType").html("");
				$('#template').tmpl({rows:data.result}).appendTo('#expType');
			}
		},
		error: function(data) {
			$.eAlert("提示",data.msg);
		}
	});
}