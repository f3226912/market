function _call(templateUrl,param){
	$("#main-wrapper").loadPage(templateUrl,
			["lib/css/DT_bootstrap.css","css/form-imput.css","lib/uploadify/uploadify.css"],
			["lib/jquery-migrate.js","lib/DT_bootstrap.js","js/common/pageBar.js",
			 "lib/jquery.tmpl.min.js","lib/uploadify/jquery.uploadify.js"],
			function(){
		
		 $("#uploadify").uploadify({
			 method:'post',
			 width:250,
		     height:30,
		     swf:'lib/uploadify/uploadify.swf',
		     uploader:'uploadFile',
		     buttonText:'请选择上传市场平面图',
		     fileTypeExts: '*.jpg;*.png;*.gif;*.jpeg;*.bmp',
		     fileObjName:'file',
		     onUploadSuccess:function(file,data,response){
		    	 // 由于返回json数据带斜杠，这里需要转换两次才能转为json对象
		    	 var obj = JSON.parse(data);
		    	 var dataObj = JSON.parse(obj); 
		    	 if(dataObj.status == 1){
		    		$("#marketFile").val(file.name);
		    		$("#marketImage").val(dataObj.message);
		    		$("#marketFileText").val(file.name);
					$.eAlert("提示", "文件上传成功");
		    	 }else{
					$.eAlert("错误", "文件上传失败");
		    	 }
		     }
		 });
		 
		 function saveData(){

			if ($("#marketFile").val() == "") {
				$.eAlert("提示", "请上传市场平面图");
				return;
			}
			$.ajax({
				type: "POST",
				dataType: "json",
				data:$('#form-addMarketImage').serialize(),
				url: CONTEXT+"graph/upload",
				async: false,
				success: function (data) {
					if(data.success){
						$.eAlert("提示", "保存成功");
						window.location="index#graph";
					}else {
						$.eAlert("错误", data.msg);
					}
				},
				error: function(data) {
					$.eAlert("错误", data.msg);
				}
			});
		 }
		 $("#saveForm").click(function(){
			 saveData();
		 })
	});
};