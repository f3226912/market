function _call(templateUrl,param){
	$("#main-wrapper").loadPage(templateUrl,
			["css/parameter.css","css/contract.css"],
			["lib/jquery-migrate.js","js/common/pageBar.js","lib/jquery.tmpl.min.js",'js/controller/settings/leaseParameter/leaseParameterController.js'],
			function(){
		//绑定保存按钮事件
		//$("#saveForm").click(clickSaveForm);
		
		//保存                                                                                                                                                                                                                                                                                                                                                                                                                                                                      
		$("#saveForm").click(function(){
			if($("#form-edit").valid()){
				var param = $('#form-edit').serialize();
				$.eLoading(true);
				$.getJSON(CONTEXT+"leaseParameter/update",param,function(data){
					if(data.success&&data.result){
						$.eAlert("提示信息","保存成功");
						//清空
					} else {
						$.eAlert("错误信息","保存失败,"+data.msg);
					}
					$.eLoading(false);
				});
			}
			
		});
		//自定义校验规则
		$("#form-edit").validate({
			    rules: {
			    	deadlineTime: {
			        range:[1,100],
			        digits:true
			      },
			      rentTime: {
			    	  range:[1,1000],
			    	  digits:true
			      }
			    },
			    messages: {
			    	deadlineTime: {
			        range: "月数只能是1-100的整数"
			      },
			      rentTime: {
			    	  range: "天数只能是1-1000的整数"
			      }
			     
			    }
			});

		
		initData();
});
}
//初始化数据
function initData(){
	$.eLoading(true);
	var param = Route.params;
	$.getJSON(CONTEXT+"leaseParameter/querySysCRS",param,function(data){
		if(data.success){
			if(data.result!=null){
				$("#deadlineTime").val(data.result.deadlineTime);
				$("#rentTime").val(data.result.rentTime);
				$("#marketId").val(data.result.marketId);
				$("#orgId").val(data.result.orgId);
				if(data.result.checkWorkType===2){
					$("#radio-1-2").attr("checked",true);
				}
				if(data.result.changeWorkType===2){
					$("#radio-2-2").prop("checked",true);
				}
				if(data.result.settlementWorkType===2){
					$("#radio-3-2").prop("checked",true);
				}	
			}
		} else {
			$.eAlert("错误信息","初始化数据失败,"+data.msg);
		}
		$.eLoading(false);
	});
}








