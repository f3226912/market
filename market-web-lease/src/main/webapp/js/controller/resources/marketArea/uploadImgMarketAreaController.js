function _call(templateUrl,param){
	$("#main-wrapper").loadPage(templateUrl,
			["lib/css/DT_bootstrap.css","css/form-imput.css","css/resourMage.css","lib/uploadify/uploadify.css"],
			["lib/jquery-migrate.js","lib/DT_bootstrap.js","js/common/pageBar.js","lib/jquery.tmpl.min.js","lib/uploadify/jquery.uploadify.js"],
			function(){
		 $("#uploadify").uploadify({
			 method:'post',
			 width:250,
		     height:30,
		     swf:'lib/uploadify/uploadify.swf',
		     uploader:'uploadFile',
		     buttonText:'请选择上传区域平面图',
		     fileTypeExts: '*.jpg;*.png;*.gif;*.jpeg;*.bmp',
		     fileObjName:'file',
		     onUploadSuccess:function(file,data,response){
		    	 // 由于返回json数据带斜杠，这里需要转换两次才能转为json对象
		    	 var obj = JSON.parse(data);
		    	 var dataObj = JSON.parse(obj); 
		    	 if(dataObj.status == 1){
		    		$("#areaFile").val(file.name);
		    		$("#areaImage").val(dataObj.message);
		    		$("#imgId").attr("src",dataObj.url);
					$.eAlert("提示", "文件上传成功");
		    	 }else{
					$.eAlert("错误", "文件上传失败");
		    	 }
		     }
		 });
		 
		 function saveData(){
			 if ($("#areaFile").val() == "") {
						$.eAlert("提示", "请上传区域平面图");
						return;
			 }
			$.ajax({
				type: "POST",
				dataType: "json",
				data:$('#form-addAreaImage').serialize(),
				url: CONTEXT+"planeGraph/uploadArea",
				async: false,
				success: function (data) {
					if(data.success){
						$.eAlert("提示", "保存成功");
						window.location="index#marketArea";
					}else {
						$.eAlert("提示", "上传失败，请重新上传");
					}
				},
				error: function(data) {
					$.eAlert("提示", "上传失败，请重新上传");
				}
			});
		 }
		 $("#saveForm").click(function(){
			 saveData();
		 })
		// console.log("Route.areaId : "+Route.areaId)
		$('#areaTemp').tmpl(Route.areaList).appendTo('#areaWrap');
		 $("#"+Route.areaId).addClass("curr-flow");
		 $("#areaId").val(Route.areaId)
		// 区域楼层切换效果
	    $("#areaWrap").on("click","li",function(){
	    	$(this).addClass("curr-flow").siblings().removeClass("curr-flow");
	    	Route.areaId = this.id;
	    	$("#areaId").val(Route.areaId)
	    	
	    })
		 
		/*$.validator.setDefaults({
		    submitHandler: function() {
		    	if($("#areaFile").val() == ""){
		    		$.eAlert("提示", "请上区域平面图");
		    	}
		    	saveData();//ajax的提交方法
		    }
		});
		validateForm();*/
		
		
	});
}