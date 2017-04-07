function _call(templateUrl,param){
	if (!Route.params) {
		location.href = "index#contractSettlement"
    	return;
	}
	
	var orderId = Route.params.orderId;
	var urlParamSuffix = "?orderId=" + orderId;
	$("#main-wrapper").loadPage(templateUrl + urlParamSuffix,
			["css/form-imput.css","css/workflow.css"],
			["js/controller/selectPerson.js"],
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
			Route.params = {"contractId": $("#contractMainId").val(), "isWfApproval": "true"};
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
			
			//添加tip鼠标事件
			$("[data-toggle='tooltip']").tooltip('show');
			
			//针对审批处理的事件
			var msgReceiverMapperJson = [];
			if (isNeedApproval()) {
				$("#msgReceiversName").click(function(){
					$.selectPerson({data:msgReceiverMapperJson, callBack: function(res){
							//存储到内存备用
							msgReceiverMapperJson = res;
							resolveMsgReceiverMapperJson(res);
					}});
				});
				
				bindFormValidate();
			}
			
		}
		
		
		/**
		 * 分解消息接收者映射的json对象
		 */
		function resolveMsgReceiverMapperJson(mapper) {
			var ids = new Array();
			var names = new Array();
			$.each(mapper, function(index, obj) {
				ids.push(obj.id);
				names.push(obj.name);
			});
			
			$("#msgReceivers").val(ids.join(","));
			$("#msgReceiversName").val(names.join("、"));
			
		}
		
		/**
		 * 绑定表单验证方法
		 * 
		 */
		function bindFormValidate(){
			$("#processApprovalForm").validate({  
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
			    	submitApproval();
			    }
			});
		}
		
		
		/**
		 * 提交流程的审批
		 */
		function submitApproval() {
			var para = $("#processApprovalForm").serializeJson();
			$.post(CONTEXT+"contractProcessUpdate/approveConManage",para,function(data){
				if(data.success){
					$.eAlert("提示","审批成功!");
					Route.params = {"id": $("#contractMainId").val()};
					//回退到上一步。
					window.history.go(-1);
				} else {
					 $.eAlert("提示",data.msg);
				}
			},"json");
		}
	

		/**
		 * 是否需要进行审批
		 * @return 返回true表示需要审批，反之false。
		 */
		function isNeedApproval() {
			return $("#processApprovalForm").length > 0;
		}
		
	
		
	});
}