function _call(templateUrl,param){
	$("#main-wrapper").loadPage(templateUrl,
			["css/parameter.css","css/contract.css"],
			["lib/jquery-migrate.js","js/common/pageBar.js","lib/jquery.tmpl.min.js"],
			function(){
		// 此处编写所有处理代码
	
	});
}