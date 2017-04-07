/**
 * 
 */
function _call(templateUrl,param){
	var params=Route.params;
	var id=params.id;
	$("#main-wrapper").loadPage(templateUrl + "/" +id,
			["css/form-imput.css","css/contract.css"],
			["lib/bootstrap-datepicker.js","lib/bootstrap-datetimepicker.js","lib/jquery.tmpl.min.js"],
	function(){
		function dealAllButtons(){
			var approvalStatus = $("#changeApprovalStatus").val();
			//发起审批/审核合同/编辑:合同的审批状态为：待审批、已驳回时显示，其他状态不显示
			if(approvalStatus =="0" || approvalStatus=="2"){
				$("#launchApproveContractLi").css("display","block");
				$("#approveContractLi").css("display","block");
				$("#updateContractLi").css("display","block");
			}
		}
		dealAllButtons();
	});
}