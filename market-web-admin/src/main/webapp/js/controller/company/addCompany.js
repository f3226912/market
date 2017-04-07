function _call(templateUrl,param){
	$("#main-wrapper").loadPage(templateUrl,
			["css/form-imput.css"],
			["lib/jquery-migrate.js","js/common/pageBar.js","lib/jquery.tmpl.min.js"],
			function(){

		//新增公司
		$("#saveForm").click(function(){
			if($("#form-addCompany").valid()){
				var param = $('#form-addCompany').serialize();
				$.eLoading(true);
				$.getJSON(CONTEXT+"company/addSysOrg",param,function(data){
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
		
	});
}
