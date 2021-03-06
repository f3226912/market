function _call(templateUrl,param){
	if (!Route.params) {
		location.href = "index#contractSettlement"
    	return;
	}
	var contractMainId = Route.params.id;
	var processId = Route.params.processId;
	var urlParamSuffix = "?contractMainId=" + contractMainId + "&processId=" + processId;
	$("#main-wrapper").loadPage(templateUrl + urlParamSuffix,
			["css/form-imput.css","css/workflow.css"],
			[],
	function(){
		//初始化
		init();
		
		/**
		 * 初始化方法
		 */
		function init(){
			initData();
			initEventBind();
		}
		
		/**
		 * 初始化数据
		 */
		function initData() {
			//添加工作流审批标识符
			Route.params.isWfApproval = "true";
			//id即为主合同id
			Route.params.contractId = Route.params.id;
			$.loadRouteModal("showContractSettlementDetail", "#baseInfoDetail");
		}
		
		/**
		 * 初始化绑定事件
		 */
		function initEventBind() {
			//关闭按钮点击事件
			$("#closeWinBtn").click(function(){
				//回退到上一步。
				window.history.go(-1);
			});
			
			bindFormValidate();
		}
		
		
		/**
		 * 绑定表单验证方法
		 * 
		 */
		function bindFormValidate() {
			$("#startProcessForm").validate({  
			    rules:{  
			    	opinion:{  
			            required:true,
			            maxlength:120
			        } 
			    },  
			    messages:{
			    	opinion:{  
			            required:'必填',
			            maxlength:"最多120个字符"
			        }
			    },
			    submitHandler: function() {
			    	submitStartProcess();
			    }
			});
		}
		
		
		/**
		 * 提交流程的发起
		 */
		function submitStartProcess() {
			var para = $("#startProcessForm").serializeJson();
			$.post(CONTEXT+"contractProcessUpdate/startConClose",para,function(data){
				if(data.success){
					$.eAlert("提示","发起成功!");
					//回退到上一步。
					window.history.go(-1);
				} else {
					 $.eAlert("提示",data.msg);
				}
			},"json");
		}
	

    
   
	
		
	});
}