/**
 * 
 */
function _call(templateUrl,param){
	var params=Route.params;
	var id=params.id;
	$("#main-wrapper").loadPage(templateUrl + "/" +id,
			["css/form-imput.css","css/contract.css"],[],
	function(){
		function dealAllButtons(){
			var contractStatus = $("#detailContractStatus").val();
			var approvalStatus =$("#detailApprovalStatus").val();
			var updateButtonPower = $("#detailUpdateButtonPower").val().trim();
			var settlementButtonPower = $("#detailSettlementButtonPower").val().trim();
			//复制合同按钮：合同状态为：执行中、已结算”才显示，其他状态不显示
			if(contractStatus =="1" || contractStatus=="2"){
				$("#copyContractLi").css("display","block");
			}
			//发起审批/审核合同/编辑按钮:合同的审批状态为：待审批、已驳回时显示，其他状态不显示
			if(approvalStatus =="0" || approvalStatus=="2"){
				$("#launchApproveContractLi").css("display","block");
				$("#approveContractLi").css("display","block");
				$("#updateContractLi").css("display","block");
			}
			//合同变更按钮:合同状态为“执行中”时才显示合同变更按钮
			if(contractStatus=="1" && updateButtonPower){
				$("#changeContractLi").css("display","block");
			}
			//合同结算按钮:合同状态为“执行中”时才显示合同结算按钮
			if(contractStatus=="1" && settlementButtonPower){
				$("#statementsContractLi").css("display","block");
			}
		}
		dealAllButtons();
	});
}