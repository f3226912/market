function _call(templateUrl,param){
	if(!Route.params){
		Route.params={active:2};
		location.href = "index#print";
		return;
	}
	
	var params=Route.params;//获取页面传值对象
	var id = params.id;
	$("#main-wrapper").loadPage(templateUrl+"/"+id,
			["lib/css/DT_bootstrap.css","css/form-imput.css","lib/uploadify/uploadify.css"],
			["lib/jquery-migrate.js","lib/DT_bootstrap.js","js/common/pageBar.js",
			 "lib/jquery.tmpl.min.js","lib/uploadify/jquery.uploadify.js"],
	function(){
		 $("#uploadify").uploadify({
			 method:'post',
			 width:250,
		     height:30,
		     swf:'lib/uploadify/uploadify.swf',
		     uploader:'printTemplate/upload',
		     formData:{"templateUrl": $("#templateUrl").val()},
		     buttonText:'请选择上传模板文件',
		     fileTypeExts: '*.doc',
		     fileObjName:'file',
		     onUploadSuccess:function(file,data,response){
		    	 var dataObj = JSON.parse(data); 
		    	 if(dataObj.success){
		    		 $.eAlert("提示", "文件上传成功！");
		    		 
		    		 $("#templateFile").val(file.name);
		    		 $("#templateUrl").val(dataObj.result.masterPath);
		    		
		    		 var url = dataObj.result.host + dataObj.result.masterPath;
		    		 /*var content = "<a href='"+url+"'>"+file.name+"</a>";*/
		    		 var content = file.name;
		    		 $(".einput-s1").val(content);
		    		 
		    		 $('#uploadify').uploadify('settings','formData',{"templateUrl": $("#templateUrl").val()});
					
		    	 }else{
					$.eAlert("错误", dataObj.msg);
		    	 }
		     },
		     onUploadError:function(file, errorCode, errorMsg, errorString) {
		    	 $.eAlert("错误", "上传文件失败！");
		     }
		 });
		 
		 function saveTemplateEditData(){
			 $.ajax({
				type: "POST",
				dataType: "json",
				data:$('#form-editPrintTemplate').serialize(),
				url: CONTEXT+"printTemplate/saveEdit",
				async: false,
				success: function (data) {
					if(data.success){
						$.eAlert("提示", "修改成功");
						Route.params={active:2};
						location.href='index#print';
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
			 $("#form-editPrintTemplate").validate({  
			    rules:{  
			    	templateCode:{  
			            required:true
			        },  
			        templateName:{  
			            required:true
			        }
			    },  
			    messages:{
			    	templateCode:{  
			            required:'必填'
			        },  
			        templateName:{  
			            required:'必填'
			        }
			    },
			    submitHandler: function() {
			    	if($("#templateFile").val() == ""){
			    		$.eAlert("提示", "请上传模板文件");
			    		return;
			    	}
			    	saveTemplateEditData();//ajax的提交方法
			    }
			});
		}
		validateForm();
	});
};