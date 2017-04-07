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
		
		$("#form-editComUser").validate({
			rules:{
				code:{
					wordAndNumber:true
				}
			}
		});
		var params=Route.params;//获取页面传值对象
		var id=params.id;//取值方式：value=对象.key
		//填充表单数据
		var initForm = function(obj){
			if(obj){
				$("input[name=type][value="+obj.type+"]").attr("checked",true);
				$("#code").val(obj.code);
				$("#name").val(obj.name);
				$("#mobile").val(obj.mobile);
				$("#landline").val(obj.landline);
				$("#mail").val(obj.mail);
				$("input[name=status][value="+obj.status+"]").attr("checked",true);
				if(obj.type == '1'){
					$("#groupId").html($("#selectPlat").html());
				} else {
					$("#groupId").html($("#selectCompany").html());
					$("#groupId").val(obj.groupId);
				}
			}
		}
		
		//初始化公司
		function initCompany(){
			var dtd = $.Deferred(); // 生成Deferred对象
			$.ajax({
				type: "GET",
				dataType: "json",
				url: CONTEXT+"company/queryTopOrgList",
				async: false,
				success: function (data) {
					if(data.success){
						var org=data.result;
						initCompany(org);
						dtd.resolve();
					}else {
						$.eAlert(data.msg);
						dtd.reject();
					}
				},
				error: function(data) {
					$.eAlert(data.msg);
					dtd.reject();
				}
			});
			
			//填充表单数据
			function initCompany(obj){
				if(obj){
					var sel = $("#selectCompany");
					for (var  i= 0;  i< obj.length; i++) {
						var opt = $("<option></option>");
						sel.append(opt.val(obj[i].id).text(obj[i].fullName));
					}
				}
			}
			return dtd.promise();
		}
		
		//获取修改对象数据
		$.when(initCompany())
		.done(function(){
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
		
		
		//绑定保存事件
		$("#editForm").click(function(){
			if($("#form-editComUser").valid()){
				$.eLoading(true);
				var addData = $('#form-editComUser').serialize()+"&id="+id;
				$.getJSON(CONTEXT+"sysPlatUser/editPlatUser",addData,function(data){
					if(data.success){
						alert("编辑成功");
						window.location="index#platFormUser";
					} else {
						alert(data.msg);
					}
					$.eLoading(false);
				});
			}
			
		});
	});
}