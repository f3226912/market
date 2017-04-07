function _call(templateUrl,param){
	
	try{
		var params=Route.params;//获取页面传值对象
		var id=params.id;//取值方式：value=对象.key
		templateUrl= templateUrl + "?id="+id;
	}catch (e) {
		window.location.href="index#companyUser";
		return;
	}
	
	
	$("#main-wrapper").loadPage(templateUrl,
			["lib/css/DT_bootstrap.css","css/form-imput.css"],
			["lib/jquery-migrate.js","lib/DT_bootstrap.js","js/common/pageBar.js","lib/jquery.tmpl.min.js"],
			function(){
		//填充表单数据
		var initForm = function(obj){
			if(obj){
				$("#code").val(obj.code);
				$("#name").val(obj.name);
				$("#mobile").val(obj.mobile);
				$("#orgId").val(obj.departmentName);
				$("#parentId").val(obj.parentId);
				$("#deptNo").val(obj.deptNo);
				$("#landline").val(obj.landline);
				$("#mail").val(obj.mail);
				$("#posts").val(obj.postName);
				$("input[name=status][value="+obj.status+"]").attr("checked",true);
			}
		}
		
		//获取对象数据
//		$.ajax({
//			type: "GET",
//			dataType: "json",
//			data:{userId:id},
//			url: CONTEXT+"sysCompanyUser/getUserInfoById",
//			async: false,
//			success: function (data) {
//				if(data.success){
//					var post=data.result;
//					initForm(post);
//				}else {
//					$.eAlert(data.msg);
//				}
//			},
//			error: function(data) {
//				$.eAlert(data.msg);
//			}
//		});
		
		//返回
		$("#back").click(function(){
			window.location="index#companyUser";
		});
	});
}