function _call(templateUrl,param){
	$("#main-wrapper").loadPage(templateUrl,
			["css/form-imput.css","css/contract.css",],[],
	function(){
		$.loadRouteModal("showContractSettlementDetail", "#settlementDetail");
		
		// 无参数时跳转回合同管理首页
		if(!Route.params){
			location.href = "index#contractSettlement";
			return;
		}
		
		var params=Route.params;
		var contractId=params.contractId;
		var contractNo = params.contractNo;
		var saveType = params.saveType;
		function setValueToForm(){
			$("#contractId").val(contractId);
			$("#contractNo").val(contractNo);
		}
		setValueToForm();
		
		
		
		$("#myModalPop").popwin({
			titleText: "合同结算", //弹出框名
			popwidth:"600",//设置宽度
			btnText:["取消","确认"],//按钮的文本
			drag:true,//是否拖动
			popUrl:CONTEXT + "contractSettlement/showAduitPage",
			//点击确认回掉
			callback: function(){
				if($("#settlementAudit").valid()){
					$("#myModalPop .savePop").attr("disabled", "disabled");
					$.ajax({
						type: "POST",
						dataType: "json",
						url: CONTEXT+"contractSettlement/auditByHuman",
						data: $('#settlementAudit').serialize(),
						success: function (data) {
							if(data.success){
								//$("#myModalPop").modal("hide");
								$.eAlert("提示", "操作成功!");
								setTimeout(function(){
									window.location="index#contractSettlement";
								},1000);
							}else {
								$.eAlert("保存合同结算审核信息",data.msg);
							}
						},
						error: function(data) {
							$.eAlert("保存合同结算审核信息",data.msg);
						}
					});
				}
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
	    
	    
	    
	    /**
		    * 判断是工作审核还是人工审核
		    * type: 1 合同审批模式  2  合同变更模式  3 合同结算审批模式,
		    */ 
		   var workType = 2
		   function getWorkflowSetting(){
			   $.ajax({
					type: "POST",
					url: CONTEXT+"contractManage/getWorkflowSetting",
					data: {type : 3},
					dataType: "json",
					async : false,
					success: function(data){
						if(data.success){
							//审批模式  1  工作流审批   2 线下审批
							if(data.result){
								//console.log("workType:"+data.result.workType);
								workType = data.result.workType;
							}
							//$.eAlert("提示信息", "操作成功!");
							//location.href="index#contractManage";
						} else {
							$.eAlert("提示信息", data.msg);
						}
					}
				});
		   }getWorkflowSetting();
		 //此方法被移到了JSP页面 
		 loadBtn(workType, saveType);
		    
		    /**
			 * 工作流审批 
		     * busType: C01首次审批 C02合同变更 C03审核
			 * orgId：当前市场ID
			 */
			var processId;
			function queryProcessList(){
				$.ajax({
					type: "POST",
					url: CONTEXT+"contractProcessQuery/queryProcessList",
					data: {busType : "C03", orgId : Route.market},
					dataType: "json",
					success: function(data){
						if(data.success){
							if(data.result == undefined || data.result.length == 0){
								$.eAlert("提示", "没有可以选择的审批流程");
								return;
							}
							var content = "";
							$(data.result).each(function(index,item){ 
								content += "<a class='selectProcessList' href='javascript:void(0)' id='"+item.id+"'>"+item.displayName+"</a><br><br>";
							});
							$("#processList .modal-body").html(content);
							$("#processList").modal();
							$("#processList .savePop").css({"display":"none"});
							
							$(".selectProcessList").on("click",function(){
						    	$("#processList").modal("hide");
						    	processId = $(this).attr("id");
						    	//选择工作流审核流程后的操作
						    	setTimeout(function(){
						    		selectProcessList();
						    	}, 1000)
						    });
							
						} else {
							$.eAlert("提示信息", data.msg);
						}
					}
				});
			}
			
			function selectProcessList(){
				Route.params = {id : contractId, processId : processId}
				window.location.href='index#wfConCloseStart';
			}
			
			$("#workflow_btn").on("click",function(){
				queryProcessList();
			});
		
		   $("#processList").popwin({
			   titleText: "选择流程", //弹出框名
			   popwidth:"600",//设置宽度
			   popCont:"#processList", 
			   btnText:["关闭"],//按钮的文本
			   drag:true,//是否拖动
			   popUrl: "",
			   callback: function(){
				   
			   }
		   });
		   
		   function saveSettlement(){
				$("#saveSettlement").click(function(){
					if($("#settlementDeatil").valid()){
						$.ajax({
							type: "POST",
							dataType: "json",
							url: CONTEXT+"contractSettlement/save",
							data: $('#settlementDeatil').serialize(),
							success: function (data) {
								if(data.success){
									$.eAlert("保存合同结算信息","保存成功");
									window.location="index#contractSettlement";
								}else {
									$.eAlert("保存合同结算信息",data.msg);
								}
							},
							error: function(data) {
								$.eAlert("保存合同结算信息",data.msg);
							}
						});
					}
				});
			}
			saveSettlement();
			
			function editSettlement(){
				$("#editSettlement").on("click", function(){
					if($("#settlementDeatil").valid()){
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
					}
				});
			}
			editSettlement();
			
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