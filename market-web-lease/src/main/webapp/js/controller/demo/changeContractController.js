function _call(templateUrl,param){
	$("#main-wrapper").loadPage(templateUrl,
			["css/form-imput.css","css/contract.css"],
			["lib/bootstrap-datepicker.js","lib/bootstrap-datetimepicker.js","lib/distpicker.data.js","js/common/pageBar.js","lib/jquery.tmpl.min.js"],
	function(){
		// 此处编写所有处理代码
		/*时间*/
	$('.form_datetime-component').datetimepicker({
		format: "yyyy mm dd - hh:ii",
		autoclose: true
	});
	$(".start-date").datepicker({
		format: "yyyy-mm-dd",
		autoclose: true
	});
	//下拉选项
	var selt = $('.dropdown-lt-20').find('li');
	var searchBol=0;
	selt .click(function() {
		var icont =' <span class="caret red-icon"></span>';
		$('#toggle-box').html($(this).children().html()+icont);
		//alert($(this).children().html())
		searchBol=$(this).val();
	})
	$("#btn-search").click(function(){
		alert(searchBol);
	})
    
    
    //搜索显示隐藏
    $('.high-search').click(function() {
    	if($('.main-ipt-types').is(':visible')){	
    		$('.main-ipt-types').slideUp();
    		$('.high-search').html(' 高级搜索<i class="fa fa-angle-up"></i>')
    	}else{
    		$('.main-ipt-types').slideDown();
    		$('.high-search').html(' 高级搜索<i class="fa fa-angle-down"></i>')
    	}
    });
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