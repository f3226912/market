function _call(templateUrl,param){
	$("#main-wrapper").loadPage(templateUrl,
			["lib/css/DT_bootstrap.css","css/form-imput.css"],
			["lib/jquery-migrate.js","lib/DT_bootstrap.js","js/common/pageBar.js","lib/jquery.tmpl.min.js"],
			function(){

		var params=Route.params;//获取页面传值对象
		var id=params.id;//取值方式：value=对象.key
		//填充表单数据
		var initForm = function(obj){
			if(obj){
				$("input[name=type][value="+obj.type+"]").attr("checked",true);
				$("#code").val(obj.code);
				if(obj.groupId == '0'){
					$("#groupName").val("谷登平台");
				} else if($.trim(obj.groupId) != ''){
					$("#groupName").val(obj.groupName);
				}
				
				$("#name").val(obj.name);
				$("#mobile").val(obj.mobile);
				$("#landline").val(obj.landline);
				$("#mail").val(obj.mail);
				$("input[name=status][value="+obj.status+"]").attr("checked",true);
			}
		}
		
		//获取对象数据
		$.ajax({
			type: "GET",
			dataType: "json",
			data:{userId:id},
			url: CONTEXT+"sysPlatUser/getUserInfoById",
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