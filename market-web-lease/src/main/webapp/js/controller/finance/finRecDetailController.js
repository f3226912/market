function _call(templateUrl,param){
	var params=Route.params;//获取页面传值对象
	var postId=params.id;//取值方式：value=对象.key
	
	$("#main-wrapper").loadPage(templateUrl+"?&id=" + postId,
			["css/form-imput.css","css/finance.css"],
			["lib/bootstrap-datepicker.js","lib/bootstrap-datetimepicker.js","lib/distpicker.data.js","js/common/pageBar.js","lib/jquery.tmpl.min.js"],
	function(){
		// 此处编写所有处理代码
	$(".querySwitch").click(function(){
	});
		/*时间*/
	$('.form_datetime-component').datetimepicker({
		format: "yyyy mm dd - hh:ii",
		autoclose: true
	});
	$(".start-date").datepicker({
		format: "yyyy-mm-dd",
		autoclose: true
	  });
	
	var selt = $('.dropdown-lt-20').find('li')
	selt .click(function() {
		var icont =' <span class="caret red-icon"></span>';
		$('#toggle-box').html($(this).children().html()+icont);
	})
	
	$('#template').tmpl().appendTo('.wrp-box');
		// 分页工具条
		$("#pagebar").page({
			pageIndex : 1,
			pageSize : 10,
			total : 50,
			callback : function(index){
			$('.wrp-box').html("");
			$('#template').tmpl().appendTo('.wrp-box'); // 渲染html模板
				// 获取数据
				// $.get(CONTEXT + 'sysOrg/queryTopSysOrg4Page', {
				// 		pageNum : index,
				// 		pageSize : 10
				// 	}, function(resp){
				// 	if(resp.code == '10000'){
				// 		$('#template').tmpl(users).appendTo('.wrp-box');
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
