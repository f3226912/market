function  _call(templateUrl,param){
	$("#main-wrapper").loadPage(templateUrl,
			["css/form-imput.css"],
			["lib/jquery-migrate.js","js/common/pageBar.js","lib/jquery.tmpl.min.js"],
			function(){

		//修改公司
		$("#saveForm").click(function(){
			if($("#form-addCompany").valid()){
				var param = $('#form-addCompany').serialize();
				//取id
				param = "id="+Route.params.id+"&"+param;
			
				$.eLoading(true);
				$.getJSON(CONTEXT+"company/updateSysOrg",param,function(data){
					if(data.success){
						$.eAlert("提示信息","保存成功");
						//跳转
						$.eLoading(false);
						window.location.href="index#company";
					} else {
						$.eAlert("错误信息","保存失败,"+data.msg);
					}
					$.eLoading(false);
				});
			}
			
		});
		
		//取消
		$("#cancelForm").click(function(){
			window.location.href="index#company";
		});
		
		//初始化数据
		
		function initData(){
			$.eLoading(true);
			var param = Route.params;
			$.getJSON(CONTEXT+"company/querySysOrg",param,function(data){
				if(data.success){
					$("#fullName").val(data.result.fullName);
				} else {
					$.eAlert("错误信息","初始化数据失败,"+data.msg);
				}
				$.eLoading(false);
			});
		}
		initData();
	});
}
