function _call(templateUrl,param){
	$("#main-wrapper").loadPage(templateUrl,
			["lib/css/DT_bootstrap.css","css/form-imput.css","css/resourMage.css"],
			["lib/jquery-migrate.js","js/common/pageBar.js","lib/jquery.tmpl.min.js"],

		function(){
			var index = $("#rowIndex").val();
			function saveData(){
				var ret=validateForm();
				if(ret!=0){
					return;
				}
				$.ajax({
					type: "POST",
					dataType: "json",
					url: CONTEXT+"marketArea/save",
					data: $('#form-addMarketArea').serialize(),
					success: function (data) {
						if(data.success){
							$.eAlert("提示信息","添加成功");
							window.location="index#marketArea";
						}else {
							$.eAlert("提示信息",data.msg);
						}
					},
					error: function(data) {
						$.eAlert("提示信息","保存异常");
					}
				});
			}
			
			$("#cancle").click(function(){
				window.location="index#marketArea";
			});

			$("#form-addMarketArea").on("click","#addAreaBtn",function(){
				index++;
				if($(".add-area-btn.delAreaBtn").length>=20){
					$.eAlert("提示信息","最多添加20行区域信息");
					return;
				}
				$(this).parents(".row").after($(".addAreaForm").html());
				$(this).after('<a class="add-area-btn delAreaBtn"><i class="fa fa-trash-o"></i>删除</a>');
				$(this).parents(".row").next(".row").find(".code").attr("id","code").attr("name","marketAreas["+index+"].code");
				$(this).parents(".row").next(".row").find(".name").attr("id","name").attr("name","marketAreas["+index+"].name");
				$(this).parents(".row").next(".row").find(".sort").attr("id","sort").attr("name","marketAreas["+index+"].sort");
				$(this).remove();
			});
			
			$("body").on("click",".delAreaBtn",function(){
				$(this).parents(".row").remove();
			})
			$("#saveForm").click(function(){
				saveData();
			});
			
		   function validateForm(){
			     var ret=0;
			     var codeArray = [];
			     var nameArray = [];
				 $('#form-addMarketArea .form-control').each(function (i) {  
//					 	var idValue= $(this).attr("class");
					  	var value= $(this).val();
					  	if($(this).hasClass("name")){
					  		nameArray.push(value);
					  		if(value == ""){
					  			ret = 2;
							    $(this).addClass("error");
					  			$(this).next("label").html("必填");
					  		}
					  	}
					  	if($(this).hasClass("code")){
//					  	if(idValue == "form-control code"){
					  		codeArray.push(value);
					  		if(value == ""){
					  			ret = 4;
							    $(this).addClass("error");
					  			$(this).next("label").html("必填");
					  		}
					  	}
					  	if($(this).hasClass("sort")){
//					  	if(idValue == "form-control sort"){
					  		if(value == ""){
					  			ret = 3;
							    $(this).addClass("error");
					  			$(this).next("label").html("必填且1-1000数值");
					  		}
					  	}
					  	if(typeof($(this).hasClass("sort"))!="undefined"){
					  		 if(!value){
						  	} else if($(this).hasClass("sort") && (isNaN(value) || (value >1000 || value <1))){
						  		ret= 1;
							    $(this).addClass("error");
						  		$(this).next("label").html("必填且1-1000数值");
						  	} else {
								$(this).removeClass("error");
						  		$(this).next("label").html("");
					  		}
					  	}
				 });
				
				 codeArray.sort();
				 nameArray.sort();
				 for(var i in codeArray){
					 if(codeArray[i] == codeArray[Number(i)+1]){	
						 console.log("codeArray[i] "+codeArray[i])
						 $("#form-addMarketArea input.code").each(function(k){
							  if($(this).val()=="") {
								  $(this).addClass("error");
						  		  $(this).next("label").html("必填");
							  }else if($(this).val() == codeArray[i]){
								  $(this).addClass("error");
								  $(this).next("label").html("区域编码已存在");
							  }else{
								  $(this).removeClass("error");
								  $(this).next("label").html("");
							  }
						  });						
						 ret = 5;
					 }
				 }
				 //区域名称相同
				 for(var i in nameArray){
					 if(nameArray[i] == nameArray[Number(i)+1]){
						 console.log("nameArray[i] "+nameArray[i])
						 ret = 6;
						 $("#form-addMarketArea input.name").each(function(j){
							  if($(this).val()=="") {
								  $(this).addClass("error");
						  		  $(this).next("label").html("必填");
							  }else if($(this).val() == nameArray[i]){
								  $(this).addClass("error");
								  $(this).next("label").html("区域名称已存在");
							  }else{
								  $(this).removeClass("error");
								  $(this).next("label").html("");
							  }
						  });
					 }
				 }
				 console.log("ret :"+ ret)
				 return ret;
			}
		   
	/*	 $("#form-addMarketArea input").each(function(i){
			 var codeArrayVal = [];
			 $("#form-addMarketArea").on("blur","input",function(){
				 validateForm();
			 });
		 });*/
			
		});
	

}