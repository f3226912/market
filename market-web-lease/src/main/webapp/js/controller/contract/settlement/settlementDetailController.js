function _call(templateUrl,param){
	var params=Route.params;
	var id=params.id;
	$("#main-wrapper").loadPage(templateUrl + "/" +id,
			["css/form-imput.css","css/contract.css"],
			[],
	function(){
		function saveStatement(){
			$(".save-xz").click(function(){
				$.ajax({
					type: "POST",
					dataType: "json",
					url: CONTEXT+"contractSettlement/save",
					data: $('#settlementDeatil').serialize(),
					success: function (data) {
						if(data.success){
							$.eAlert("保存合同结算信息","修改成功");
							window.location="index#contractSettlement";
						}else {
							$.eAlert("保存合同结算信息",data.msg);
						}
					},
					error: function(data) {
						$.eAlert("保存合同结算信息",data.msg);
					}
				});
			});
		}
		saveStatement();
		
		function editStatement(){
			$(".save-ct").click(function(){
				$.ajax({
					type: "POST",
					dataType: "json",
					url: CONTEXT+"contractSettlement/edit",
					data: $('#settlementDeatil').serialize(),
					success: function (data) {
						if(data.success){
							$.eAlert("修改合同结算信息","修改成功");
							window.location="index#contractSettlement";
						}else {
							$.eAlert("修改合同结算信息",data.msg);
						}
					},
					error: function(data) {
						$.eAlert("修改合同结算信息",data.msg);
					}
				});
			});
		}
		editStatement();
		
		$("#myModalPop").popwin({
			titleText: "弹出框", //弹出框名
			popwidth:"600",//设置宽度
			btnText:["取消","确认"],//按钮的文本
			drag:true,//是否拖动
			popUrl:CONTEXT + "contractSettlement/showAduitPage",
			//点击确认回掉
			callback: function(){
				$("#myModalPop .savePop").attr("disabled", "disabled");
				$.ajax({
					type: "POST",
					dataType: "json",
					url: CONTEXT+"contractSettlement/auditByHuman",
					data: $('#settlementAudit').serialize(),
					success: function (data) {
						if(data.success){
							//$("#myModalPop").modal("hide");
							window.location="index#contractSettlement";
						}else {
							$.eAlert("保存合同结算审核信息",data.msg);
						}
					},
					error: function(data) {
						$.eAlert("保存合同结算审核信息",data.msg);
					}
				});
			}
		});
		
		$("#printSettingSelectPage").popwin({
			titleText: "选择套打设置", //弹出框名
			popwidth:"600",//设置宽度
			popCont:"#printSettingSelectPage", 
			btnText:["关闭"],//按钮的文本
			drag:true,//是否拖动
			popUrl: "",
			callback: function(){
				
			}
		});
	    
	    $("#btn-print").on("click",function(){
			var bizType = 2; //合同变更
	    	$.getJSON(CONTEXT+"printSetting/queryList",{"bizType":bizType},function(data){
				if(data.success){
					var statementsId = $("#statementsId").val();
			    	if(statementsId == undefined){
			    		statementsId = -1;
			    	}
					
					if(data.result == undefined || data.result.length == 0){
						$.eAlert("提示", "没有适用的套打设置");
					}
					else if(data.result.length == 1){
						var settingId = data.result[0].id;
						var url = "contractSettlement/print?settingId="+settingId+"&statementsId="+statementsId;
						newWin(url, "print_a");
					}
					else{
						var content = "";
						$(data.result).each(function(index,item){ 
							content += "<a class='selectPrintSetting' href='javascript:void(0)' id='"+item.id+"'>"+item.printName+"</a><br><br>";
						});
						$("#printSettingSelectPage .modal-body").html(content);
						$("#printSettingSelectPage").modal();
						$("#printSettingSelectPage .savePop").css({"display":"none"});
						
						$(".selectPrintSetting").on("click",function(){
					    	var settingId = $(this).attr("id");
					    	var url = "contractSettlement/print?settingId="+settingId+"&statementsId="+statementsId;
					    	newWin(url, "print_a");
					    	
					    	$("#printSettingSelectPage").modal("hide");
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