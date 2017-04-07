function _call(templateUrl,param){
	$("#main-wrapper").loadPage(templateUrl,
			["lib/css/DT_bootstrap.css","css/parameter.css","css/contract.css"],
			["lib/jquery-migrate.js","js/common/pageBar.js","lib/jquery.tmpl.min.js"],
			function(){
			var params = Route.params;//获取页面传值对象
			var resourceId = params.resourceId ;
			if (!params || !resourceId){
				location.href="index#meterReading";
				return ;
			}
			loadData();
			//加载数据
			function loadData(){
				$.ajax({
			         type: "POST",
			         url: CONTEXT+"financeGaugeCharge/resourceTypeDetail",
			         data: {"resourceId" : resourceId},
			         dataType: "json",
			         success: function(data){
			        	 	console.log(resourceId);
			        	 	if(data.success){
								var obj = data.result;
								var resourceTypeId = obj.resourceTypeId ;
								console.log("resourceId : " + resourceId + ", resourceTypeId : " + resourceTypeId);
								if (resourceTypeId == 1){//商铺资源
									Route.params={removeMarketType:'carResource',currentype:'marketResource',id : resourceId};
									location.href='index#viewMarketResource';
								}else if (resourceTypeId == 2){//停车位
									Route.params={removeMarketType:'marketResource',currentype:'carResource',id:resourceId};
									location.href='index#viewMarketResource';
								}else {//其他
									Route.params={id:resourceId};
									location.href='index#viewOtherMarketResource';
								}
							} else {
								$.eAlert("提示信息", data.msg);
							}
			         }
				});
			}
	});
		
}