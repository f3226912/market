function _call(templateUrl,param){
	$("#main-wrapper").loadPage(templateUrl,[/*所需加载的css文件路径数组*/],[/*所需加载的js文件路径数组*/],function(){
		// 此处编写所有处理代码
	});
}