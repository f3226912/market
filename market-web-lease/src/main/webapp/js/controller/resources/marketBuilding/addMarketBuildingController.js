function _call(templateUrl,param){
	$("#main-wrapper").loadPage(templateUrl,
			["lib/css/DT_bootstrap.css","css/system.css","css/resourMage.css"],
			["lib/jquery-migrate.js","js/common/pageBar.js","lib/jquery.tmpl.min.js"],
			function(){
		// 加載下拉數據
		$.ajax({
			type: "POST",
			dataType: "json",
			url: CONTEXT+"marketBuilding/getAreaId",
			success: function (data) {
				if(data.success){
					var res = data.result;
					var str = "";
					for(var i =0; i <= res.length-1; i++){
						str+= "<option value='"+res[i].id+"'>"+res[i].name+"</option>";
					}
					$("#areaId").append(str);
					
				}else {
					$.eAlert("提示信息", data.msg);
				}
			},
			error: function(data) {
				$.eAlert("提示信息", data.msg);
			}
		});
		

		
		// 加载数据字典-楼栋性质
		$.ajax({
			type: "POST",
			dataType: "json",
			url: CONTEXT+"marketBuilding/getNature",
			success: function (data) {
				if(data.success){
					var res = data.result;
					var str = "";
					for(var i =0;i <= res.length-1;i++){
						str += "<option value='"+res[i].value+"'>"+res[i].remark+"</option>";
					}
					$("#nature").append(str);
				}else {
					$.eAlert("提示信息", data.msg);
				}
			},
			error: function(data) {
				$.eAlert("提示信息", data.msg);
			}
		});
		//	取消
		$("#cancel").click(function(){
			location.href='index#marketBuilding';
		});
		
		//绑定表单验证方法
		function validateForm(){
			$("#saveFrom").validate({  
			    rules:{  
			    	areaId:{  
			            required:true
			        },  
//			        nature:{  
//			            required:true
//			        },  
			        code:{  
			            required:true,
			            maxlength:100
			        },  
			        name:{  
			            required:true,
			            maxlength:100
			        }
			     
			    },  
			    messages:{
			    	areaId:{  
			            required:'必选'
			        },  
//			        nature:{  
//			            required:'性质必选'
//			        }, 
			        code:{  
			            required:'必填',
			            maxlength:'限输入1-100个字符'
			        },  
			        name:{  
			            required:'必填',
			            maxlength:'限输入1-100个字符'
			        }
			    },
			    submitHandler: function() {
			    	//alert("提交事件!");
			    	clickSaveForm();
			    }
			});
		}
		//保存
		function clickSaveForm(){
			$.eLoading(true);
			console.log($('#saveFrom').serializeJson());
			$.ajax({
				type: "POST",
				dataType: "json",
				data: $('#saveFrom').serializeJson(),
				url: CONTEXT+"marketBuilding/addMarketBuild",
				success: function (data) {
				
					if(data.success){
						$.eAlert("提示信息",data.msg);
						window.location="index#marketBuilding";
						
					}else{
						$.eAlert("提示信息",data.msg);
					}
					
				},
				
				error: function(data) {
					$.eAlert("提示信息",data.msg);
				}
				
			});
			$.eLoading(false);
		}
		validateForm();//绑定表单验证方法

	});
	
}