
function _call(templateUrl,param){
	var params=Route.params;
	var id=params.contractId;
	var isWfApproval=params.isWfApproval;
	var targetUrl = templateUrl + "/" +id;
	if (isWfApproval) {
		targetUrl += "?isWfApproval=" + isWfApproval;
	}
	$("#main-wrapper").loadPage(targetUrl,
			["css/form-imput.css","css/contract.css"],[],
	function(){
		function dealAllButtons(){
			var settlementStatus =$("#settlementStatus").val();
			//发起审批/审核合同 :结算的审批状态为：待审批、已驳回时显示，其他状态不显示
			if(settlementStatus =="0" || settlementStatus=="2"){
				$("#launchApproveContractLi").css("display","block");
				$("#approveContractLi").css("display","block");
				$("#editSettlement").css("display","block");
			}else if(settlementStatus == null || settlementStatus == ""){
				$("#saveSettlement").css("display","block");
			}
		}
		dealAllButtons();
		
	   //绑定表单验证方法
	   function validateForm(){
		   $("#settlementDeatil").validate({ 
			    rules:{  
			    	deposit:{required:true, number:true, depositToFixed:true, maxlength:12},  
			    	forfeit:{required:true, number:true, forfeitToFixed:true, maxlength:12},
			    	info:{required:true}
			    },
			    messages:{
			    	deposit:{required:'必填', number:'请输入有效数字', maxlength:'最长输入11位数字'},  
			    	forfeit:{required:'必填', number:'请输入有效数字', maxlength:'最长输入11位数字'},  
			    	info:{required:'必填'}
			    }
			});
	   	}
	   	validateForm();
	   
		jQuery.validator.addMethod("depositToFixed", function(value, element) {
			$("input[name='deposit']").val(dealAmount(value));
			return true;
		}, "");
		jQuery.validator.addMethod("forfeitToFixed", function(value, element) {
			$("input[name='forfeit']").val(dealAmount(value));
			return true;
		}, "");
		
		function dealAmount(formatValue){
			return (Math.ceil(formatValue*100)/100).toFixed(2);
		}
	})
}