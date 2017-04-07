function _call(templateUrl,param){
	$("#main-wrapper").loadPage(templateUrl,
			["lib/css/DT_bootstrap.css","css/resourMage.css"],
			["lib/jquery-migrate.js","js/common/pageBar.js","lib/jquery.tmpl.min.js"],
			function(){
		var params=Route.params;//获取页面传值对象
		var id=params.id;//取值方式：value=对象.key
		
		//	取消
		$("#cancel").click(function(){
			location.href='index#marketBuilding';
		});
		// 加载-区域
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
		//绑定表单验证方法
		function validateForm(){
			$("#form-addBuild").validate({  
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
			        buildName:{  
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
			        buildName:{  
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
					detail();
				}else {
					$.eAlert("提示信息", data.msg);
				}
			},
			error: function(data) {
				$.eAlert("提示信息", data.msg);
			}
		});
		
		// 点击楼栋名称带出楼栋详情
		function detail(){
			$.ajax({
				type: "POST",
				dataType: "json",
				url: CONTEXT+"marketBuilding/marketBuildingdetail?id="+id,
				success: function (data) {
					if(data.success){
						$("#code").val(data.result.code);
						$("#buildName").val(data.result.name);
						$("#areaId").val(data.result.areaId);
						$("#nature").val(data.result.builNature);
					}else {
						$.eAlert("提示信息",data.msg);
					}
				},
				error: function(data) {
					$.eAlert("提示信息",data.msg);
				}
			});
		}
		
		validateForm();//绑定表单验证方法
//		保存
		function clickSaveForm(){
//		$("#saveForm").click(function(){
			$.eLoading(true);
			$.ajax({
				type: "POST",
				dataType: "json",
				data: $('#form-addBuild').serialize()+"&id="+id,
				url: CONTEXT+"marketBuilding/marketBuildingdetailEdit",
				success: function (data) {
					if(data.success){
						//回到主页面
						window.location="index#marketBuilding";
					}else {
						$.eAlert("提示信息",data.msg);
					}
					
				},
				error: function(data) {
					$.eAlert("提示信息",data.msg);
				}
			});
			$.eLoading(false);
//		});
		
		
		}
		});
	
	
}