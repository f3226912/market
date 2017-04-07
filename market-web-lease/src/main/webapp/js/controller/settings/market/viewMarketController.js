function _call(templateUrl,param){
	$("#main-wrapper").loadPage(templateUrl,
			["lib/css/DT_bootstrap.css","css/parameter.css","css/contract.css"],
			["lib/jquery-migrate.js","lib/DT_bootstrap.js","js/common/pageBar.js","lib/jquery.tmpl.min.js"],
			function(){
		var params=Route.params;//获取页面传值对象
		var marketId=params.marketId;//取值方式：value=对象.key
		var marketStatus;

		//填充表单数据
		var initForm = function(obj){
			if(obj){
//				$("input[name=marketStatus][value="+obj.marketStatus+"]").attr("checked",true);
				$("#code").val(obj.code);
				$("#name").val(obj.name);
				$("#nameShort").val(obj.nameShort);
				$("#companyName").val(obj.companyName);
				$("#openTime").val(formateDate(obj.openTime,'yyyy-MM-dd'));
				$("#pca").val(obj.pca);
				$("#address").val(obj.address);
				if(obj.marketStatus==1){
					marketStatus="运营中";
				}else if (obj.marketStatus==2){
					marketStatus="筹备中";
				}else if (obj.marketStatus==3){
					marketStatus="关闭";
				}else{
					marketStatus="";
				}
				$("#marketStatus").val(marketStatus);


			}
		}
		
		//获取对象数据
		$.ajax({
			type: "post",
			dataType: "json",
			data:{marketId:marketId},
			url: CONTEXT+"market/getByMarketId",
			async: false,
			success: function (data) {
				if(data.success){
					var post=data.result;
					initForm(post);
				}else {
					$.eAlert(data.msg);
				}
			},
			error: function(data) {
				$.eAlert(data.msg);
			}
		});
		
		
	});
}