function _call(templateUrl,param){
	$("#main-wrapper").loadPage(templateUrl,
			["lib/css/DT_bootstrap.css","css/parameter.css"],
			["lib/jquery-migrate.js","js/common/pageBar.js","lib/jquery.tmpl.min.js"],
			function(){
		// 此处编写所有处理代码
		


$('#template').tmpl().appendTo('#templateBody');
		// 分页工具条
		$("#pagebar").page({
			pageIndex : 1,
			pageSize : 10,
			total : 50,
			callback : function(index){
			$('#templateBody').html("");
			$('#template').tmpl().appendTo('#templateBody'); // 渲染html模板
				// 获取数据
				// $.get(CONTEXT + 'sysOrg/queryTopSysOrg4Page', {
				// 		pageNum : index,
				// 		pageSize : 10
				// 	}, function(resp){
				// 	if(resp.code == '10000'){
				// 		$('#template').tmpl(users).appendTo('#templateBody');
				// 		dt = initTable();
				// 	}
				// 	else{
				// 		alert(resp.msg);
				// 	}
				// }, "json");
			}
		});

		 
	});
}