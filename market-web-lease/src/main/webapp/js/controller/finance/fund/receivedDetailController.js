function _call(templateUrl,param){
	var params=Route.params;//获取页面传值对象
	var postId=params.id;//取值方式：value=对象.key
	
	$("#main-wrapper").loadPage(templateUrl+"?&id=" + postId,
			["css/form-imput.css","css/finance.css","css/contract.css","css/parameter.css"],
			["lib/bootstrap-datepicker.js","lib/bootstrap-datetimepicker.js","lib/distpicker.data.js","js/common/pageBar.js","lib/jquery.tmpl.min.js"],
	function(){
		
	    $("#printSettingSelectPage_has").popwin({
			titleText: "选择套打设置", //弹出框名
			popwidth:"600",//设置宽度
			popCont:"#printSettingSelectPage_has", 
			btnText:["关闭"],//按钮的文本
			drag:true,//是否拖动
			popUrl: "",
			callback: function(){
				
			}
		});
	    
		//实收款-应收-打印
	    $("#btn-print-received-should").on("click",function(){
	    	//合同付款
			var bizType = 3; 
	    	$.getJSON(CONTEXT+"printSetting/queryList",{"bizType":bizType},function(data){
				if(data.success){
					var content = "";
					if(data.result == undefined || data.result.length == 0){
						$.eAlert("提示", "没有适用的套打设置");
					}
					else if(data.result.length == 1){
						var settingId = data.result[0].id;
						var receivedFeeId = $("#receivedFeeId").val();
				    	if(!receivedFeeId){
				    		receivedFeeId = -1;
				    	}
/*				    	var width = window.screen.width;
				    	var height = window.screen.height;
						window.open("financeHas/print?settingId="+settingId+"&feeId="+receivedFeeId, "打印预览", 
				    			"width="+width+", height="+height+", top=0, left=0, toolbar=no, menubar=no, scrollbars=no, resizable=no, location=no, status=no"); 
						*/
						var url = "financeHas/print?settingId="+settingId+"&feeId="+receivedFeeId;
						newWin(url, "print_a");
					}
					else{
						$(data.result).each(function(index,item){ 
							content += "<a class='selectPrintSetting' href='javascript:void(0)' id='"+item.id+"'>"+item.printName+"</a><br><br>";
						});
						$("#printSettingSelectPage_has .modal-body").html(content);
						$("#printSettingSelectPage_has").modal();
						$("#printSettingSelectPage_has .savePop").css({"display":"none"});

						
						$(".selectPrintSetting").on("click",function(){
					    	var settingId = $(this).attr("id");
					    	var receivedFeeId = $("#receivedFeeId").val();
					    	if(!receivedFeeId){
					    		receivedFeeId = -1;
					    	}
/*					    	var width = window.screen.width;
					    	var height = window.screen.height;
					    	window.open("financeHas/print?settingId="+settingId+"&feeId="+receivedFeeId, "打印预览", 
					    			"width="+width+", height="+height+", top=0, left=0, toolbar=no, menubar=no, scrollbars=no, resizable=no, location=no, status=no");
					    	*/
					    	var url = "financeHas/print?settingId="+settingId+"&feeId="+receivedFeeId;
							newWin(url, "print_a");
					    	$("#printSettingSelectPage_has").modal("hide");
					    });
					}
				} else {
					$.eAlert("错误",data.msg);
				}
	    	});
	    });	
	    
	    
	    $("#printSettingSelectPage_has_temp").popwin({
			titleText: "选择套打设置", //弹出框名
			popwidth:"600",//设置宽度
			popCont:"#printSettingSelectPage_has_temp", 
			btnText:["关闭"],//按钮的文本
			drag:true,//是否拖动
			popUrl: "",
			callback: function(){
				
			}
		});
	    
		//实收款-临时-打印
	    $("#btn-print-received-temp").on("click",function(){
	    	//合同付款
			var bizType = 3; 
	    	$.getJSON(CONTEXT+"printSetting/queryList",{"bizType":bizType},function(data){
				if(data.success){
					var content = "";
					if(data.result == undefined || data.result.length == 0){
						$.eAlert("提示", "没有适用的套打设置");
					}
					else if(data.result.length == 1){
						var settingId = data.result[0].id;
						var receivedFeeId = $("#receivedFeeId").val();
				    	if(!receivedFeeId){
				    		receivedFeeId = -1;
				    	}
/*				    	var width = window.screen.width;
				    	var height = window.screen.height;
						window.open("financeHas/print?settingId="+settingId+"&feeId="+receivedFeeId, "打印预览", 
				    			"width="+width+", height="+height+", top=0, left=0, toolbar=no, menubar=no, scrollbars=no, resizable=no, location=no, status=no"); 
						*/
						var url = "financeHas/print?settingId="+settingId+"&feeId="+receivedFeeId;
						newWin(url, "print_a");
					}
					else{
						$(data.result).each(function(index,item){ 
							content += "<a class='selectPrintSetting' href='javascript:void(0)' id='"+item.id+"'>"+item.printName+"</a><br><br>";
						});
						$("#printSettingSelectPage_has_temp .modal-body").html(content);
						$("#printSettingSelectPage_has_temp").modal();
						$("#printSettingSelectPage_has_temp .savePop").css({"display":"none"});
						
						$(".selectPrintSetting").on("click",function(){
					    	var settingId = $(this).attr("id");
					    	var receivedFeeId = $("#receivedFeeId").val();
					    	if(!receivedFeeId){
					    		receivedFeeId = -1;
					    	}
/*					    	var width = window.screen.width;
					    	var height = window.screen.height;
					    	window.open("financeHas/print?settingId="+settingId+"&feeId="+receivedFeeId, "打印预览", 
					    			"width="+width+", height="+height+", top=0, left=0, toolbar=no, menubar=no, scrollbars=no, resizable=no, location=no, status=no");
					    	*/
					    	var url = "financeHas/print?settingId="+settingId+"&feeId="+receivedFeeId;
							newWin(url, "print_a");
					    	$("#printSettingSelectPage_has_temp").modal("hide");
					    });
					}
				} else {
					$.eAlert("错误",data.msg);
				}
	    	});
	    });	
	    
	    function newWin(url, id) {
		    var a = document.createElement("a");
		    a.setAttribute("href", url);
		    a.setAttribute("target", "_blank");
		    a.setAttribute("id", id);
		    // 防止反复添加
		    if(!document.getElementById(id)) {                     
		    	document.body.appendChild(a);
		    }
		    a.click();
	    }
	});
}
