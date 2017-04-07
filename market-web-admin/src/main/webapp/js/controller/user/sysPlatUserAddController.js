function _call(templateUrl,param){
	$("#main-wrapper").loadPage(templateUrl,
			["lib/css/DT_bootstrap.css","css/form-imput.css"],
			["lib/jquery-migrate.js","lib/DT_bootstrap.js","js/common/pageBar.js","lib/jquery.tmpl.min.js"],
			function(){
		
		$.validator.addMethod("wordAndNumber",function(value,element,params){
			
			if($.trim(value) != ''){
				var re =  /^[0-9a-zA-Z]*$/g;  //判断字符串是否为数字和字母组合     //判断正整数 /^[1-9]+[0-9]*]*$/    
				if (re.test(value)){
					return true;
				}
			}
			return false;
		},"只允许输入数字和字母");
		
		$("#form-addPlatUser").validate({
			rules:{
				code:{
					wordAndNumber:true
				}
			}
		});
		
		//绑定保存事件
		$("#addForm").click(function(){
			
			if($("#form-addPlatUser").valid()){
				$.eLoading(true);
				var addData = $('#form-addPlatUser').serialize();
				$.getJSON(CONTEXT+"sysPlatUser/addUser",addData,function(data){
					if(data.success){
						$.eAlert("提示信息","保存成功");
						window.location="index#platFormUser";
					} else {
						$.eAlert("错误信息","保存失败:"+data.msg);
					}
					$.eLoading(false);
				});
			}
		});
		
		//选择平台，公司事件
		$("#form-addPlatUser").find("input[name='type']")
		.off("click")
		.on("click",function(){
			if($(this).val() == "1"){
				$("#groupId").html($("#selectPlat").html());
			} else if($(this).val() == "2"){
				$("#groupId").html($("#selectCompany").html());
			}
		});
		
		//填充表单数据
		var initForm = function(obj){
			if(obj){
				var sel = $("#selectCompany");
				for (var  i= 0;  i< obj.length; i++) {
					var opt = $("<option></option>");
					sel.append(opt.val(obj[i].id).text(obj[i].fullName));
				}
			}
		}
		
		//查询系统所有一级公司
		$.ajax({
			type: "GET",
			dataType: "json",
			url: CONTEXT+"company/queryTopOrgList",
			async: false,
			success: function (data) {
				if(data.success){
					var org=data.result;
					initForm(org);
					//选择公司
					$("#form-addPlatUser").find("input[name='type'][value='2']").click();
				}else {
					$.eAlert("添加账号",data.msg);
				}
			},
			error: function(data) {
				$.eAlert("添加账号",data.msg);
			}
		});
	});
}