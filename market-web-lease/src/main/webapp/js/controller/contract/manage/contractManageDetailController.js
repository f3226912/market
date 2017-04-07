/**
 * 
 */
function _call(templateUrl,param){
	$("#main-wrapper").loadPage(templateUrl,
			["css/form-imput.css","css/contract.css"],[],
	function(){
		// 无参数时跳转回合同管理首页
		if(!Route.params){
			location.href = "index#contractManage";
		}
		 $.loadRouteModal("showContractManageDetail", "#baseInfoDetail_a", function(){
		 	console.log("++++++++++++++");
		 	console.log($("#detailContractStatus").val());
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
	   
		//查看历史版本切换
		/*$('.select_Mar').on('click',function() {
			var nodeUl= $('.market_lis')
			var ft = '<i class="fa fa-sort-down down_ft"><i>';
			if(nodeUl.is(':hidden')){
				nodeUl.show();
				$('.font_max').show();
				//console.log(nodeUl.height())
				nodeUl.find('.market_lt').each(function() {
					$(this).on('click',function() {
						$('.select_Mar').html($(this).text()+ft);
						nodeUl.hide();
						$('.font_max').hide();
					})
				})
			}else{
				nodeUl.hide();
				$('.font_max').hide();
			}
		});*/
	    
		$('.witchingMarket').hover(function() {
	      var ft = '<i class="fa fa-sort-down down_ft"><i>';
	      $('.font_max').show();
		  $('.market_lis').show();
		},function(){
			$('.market_lis').hide();
			$('.font_max').hide();
		})
		//查看历史版本切换end
	   /**
	    * 判断是工作审核还是人工审核
	    * type: 1 合同审批模式  2  合同变更模式  3 合同结算审批模式,
	    */ 
	   var workType = 2
	   function getWorkflowSetting(){
		   $.ajax({
				type: "POST",
				url: CONTEXT+"contractManage/getWorkflowSetting",
				data: {type : 1},
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
	   
	   	//此方法被抽出来放到了JSP中
	    loadBtn(workType);
	    
	    $("#btn-print").on("click",function(){
			var bizType = 0; //合同管理
	    	$.getJSON(CONTEXT+"printSetting/queryList",{"bizType":bizType},function(data){
				if(data.success){
					var contractId = $("#contractId").val();
			    	if(contractId == undefined){
			    		contractId = -1;
			    	}
					
					if(data.result == undefined || data.result.length == 0){
						$.eAlert("提示", "没有适用的套打设置");
						return;
					}
					else if(data.result.length == 1){
						var settingId = data.result[0].id;
						var url = "contractManage/print?settingId="+settingId+"&contractId="+contractId;
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
					    	var url = "contractManage/print?settingId="+settingId+"&contractId="+contractId;
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
		    
	    var params=Route.params;
	    if(!params){
			location.href = "index#contractManage"
    		return;
		}
		var id=params.id;
		var contractId=params.id;
		var contractNo = params.contractNo;
		function setValueForm(){
			$("#contractId").val(contractId);
			$("#contractNo").val(contractNo);
		}setValueForm();
		
		
		$("#copy_btn").on("click",function(){
			Route.params = {id:id};
			location.href="index#copyContract";
		});
		$("#edit_btn").on("click",function(){
			Route.params = {id:id};
			location.href="index#editContract";
		});
		$("#change_btn").on("click",function(){
			$.eConfirm("提示","合同变更通过后将按变更的租金约定、其他费项约定重新生成应收款,确定要变更吗？",function(){
				/*$('#Delete').modal('hide');*/
				$("body").removeClass("modal-open");
				Route.params = {id:id};
				location.href="index#changeContract";
			});
			
		});
		$("#workflow_btn").on("click",function(){
			queryProcessList();
		});
		$("#statement_btn").on("click",function(){
			//queryProcessList();
			Route.params={id:contractId, contractId:contractId, contractNo:contractNo, saveType:1};
			location.href='index#contractSettlementDetail';
			
		});
		
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
				data: {busType : "C01", orgId : Route.market},
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
			window.location.href='index#wfConManagerStart';
		}
		
		
		$("#myModalPop").popwin({
			titleText: "合同审核", //弹出框名
			popwidth:"600",//设置宽度
			btnText:["取消","确认"],//按钮的文本
			drag:true,//是否拖动
			popUrl:CONTEXT + "contractManage/showAduitPage",
			//点击确认回掉
			callback: function(){
				if($("#settlementAudit").valid()){
					$("#myModalPop .savePop").attr("disabled", "disabled");
					$.ajax({
						type: "POST",
						dataType: "json",
						url: CONTEXT+"contractManage/auditByHuman",
						data: $('#settlementAudit').serialize(),
						success: function (data) {
							if(data.success){
								$("#myModalPop").modal("hide");
								$.eAlert("提示", "操作成功!");
								setTimeout(function(){
									window.location="index#contractManage";
								},1000);
							}else {
								$.eAlert("保存合同详情审核信息",data.msg);
							}
						},
						error: function(data) {
							$.eAlert("保存合同详情审核信息",data.msg);
						}
					});
				}
			}
		});
		
		/**
		 * 合同历史变更记录
		 */
		function loadChangeRecord(){
			$.ajax({
				type: "POST",
				dataType: "json",
				url: CONTEXT+"contractManage/changeContractList",
				data: {contractId:contractId},
				success: function (data) {
					if(data.success){
						if(data.result){
							$(".market_lis").empty();
							for(var i in data.result){
								$(".market_lis").append("<li class='market_lt' id="+data.result[i].contractId+"><a href='javascript:void(0);'>"+data.result[i].createTime.substring(0,10)+" "+data.result[i].contractNo+"</a></li>");
								$(".market_lis .market_lt").click(function(){
									Route.params={id: $(this).attr("id")};
									location.href="index#contractManageDetail";
									$.reloadRoute();
								})
							}
						}
					}
				},
				error: function(data) {
					$.eAlert("错误信息",data.msg);
				}
			});
		}loadChangeRecord();
		
		
	});
}