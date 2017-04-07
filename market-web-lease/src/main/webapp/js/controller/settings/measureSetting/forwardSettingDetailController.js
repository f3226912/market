function _call(templateUrl,param){
	$("#main-wrapper").loadPage(templateUrl,
			["lib/css/DT_bootstrap.css","css/parameter.css","css/contract.css"],
			["lib/jquery-migrate.js","js/common/pageBar.js","lib/jquery.tmpl.min.js"],
			function(){
			var params=Route.params;//获取页面传值对象
			console.log("params : " + params);
			if (!params || !params.measureId){
				location.href="index#meterReading";
				return ;
			}
			console.log("measureId : "+ params.measureId);
			loadData();
			//加载数据
			function loadData(){
				$.ajax({
			         type: "POST",
			         url: CONTEXT+"measureSetting/forwardSettingDetail",
			         data: {"measureId":params.measureId},
			         dataType: "json",
			         success: function(data){
			        	 	if(data.success){
								var setting=data.result;
			        	 		Route.params={id:setting.id, code:setting.code, expId:setting.expId, 
			        	 				measureTypeId:setting.measureTypeId, resourceId:setting.resourceId, 
			        	 				maxVal:setting.maxVal, status:setting.status, resourceName:setting.resourceName,
			        	 				expName:setting.expName};
			        	 		location.href='index#measureSettingInfo';
							} else {
								$.eAlert("提示信息", data.msg);
							}
			         }
				});
			}
	});
		
}