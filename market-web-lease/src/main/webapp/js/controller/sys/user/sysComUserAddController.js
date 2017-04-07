function _call(templateUrl,param){
	$("#main-wrapper").loadPage(templateUrl,
			["css/form-imput.css","css/system.css","lib/css/metroStyle.css"],
			["lib/jquery.ztree.all.js"],
			function(){
		// 此处编写所有处理代码
		var setting = {
			view: {
				showIcon: false,
				showLine: false,
				select: true // 当容器为下拉列表框时需要配置此项
			},
			data: {
				simpleData: {
					enable: true
				}
			},
			callback: {
				onClick: onClick
			}
		};
		
		var setting2 = {
				view: {
					showIcon: false,
					showLine: false,
					select: true // 当容器为下拉列表框时需要配置此项
				},
				data: {
					simpleData: {
						enable: true
					}
				},
				callback: {
					onClick: onClick2
				}
			};
			
		//部门选择岗位树
		$(document).ready(function(){
			$.ajax({
				type: "GET",
				dataType: "json",
				url: CONTEXT+"tree/dept",
				data: null,
				async: true,
				success: function (data) {
					if(data.success){
						var zNodes=data.result;
						$.fn.zTree.init($("#tree2"), setting2, zNodes);
					}else {
						$.eAlert("初始化组织结构",data.msg);
					}
				},
				error: function(data) {
					$.eAlert("初始化组织结构",data.msg);
				}
			});
		});
		//点击部门，查询所有岗位
		function onClick2(event, treeId, treeNode, clickFlag) {
			var attr = treeNode.attributs;
			var orgType = attr.orgType;//组织类型
			if(orgType=="2"){
				var id=treeNode.id;
				var name=treeNode.name;
				var pid=treeNode.pId;
				$("#orgId2").val(id);
				$("#orgName2").val(name);
				loadPost({"orgId":id});
			}
			$(".input-group-btn.open").removeClass("open");
		}
		
		$(document).ready(function(){
			$.ajax({
				type: "GET",
				dataType: "json",
				url: CONTEXT+"tree/dept",
				data: null,
				async: true,
				success: function (data) {
					if(data.success){
						var zNodes=data.result;
						$.fn.zTree.init($("#tree"), setting, zNodes);
					}else {
						$.eAlert("初始化组织结构",data.msg);
					}
				},
				error: function(data) {
					$.eAlert("初始化组织结构",data.msg);
				}
			});
		});
		
		
		function loadPost(params){
			$.ajax({
				type: "GET",
				dataType: "json",
				url: CONTEXT+"post/query",
				data: params,
				async: true,
				success: function (data) {
					if(data.success){
						var posts = data.result;
						if(posts){
							$(".post-lt").html("");
							$.each(posts, function(i, post){
								var postId=post.id;
								var name=post.name;
								$(".post-lt").append("<li data-id='"+postId+"' data-name='"+name+"' class='post_lefT'>"+name+"</li>");
							});
						}
					}else {
					}
				}
			});
		}
		
		//激活右侧岗位点击事件
		function removePost(){
			//移除右侧已经分配的岗位
			$(".post_righT").click(function(){
				$(this).remove();
			});
		}
		
		/*
		 * 定制zTree节点点击事件
		 * 该方法作用是将点击到的部门节点数据填充到form表单
		 */
		function onClick(event, treeId, treeNode, clickFlag) {
			var attr = treeNode.attributs;
			var orgType = attr.orgType;//组织类型
			if(orgType=="2"){
				var id=treeNode.id;
				var name=treeNode.name;
				var pid=treeNode.pId;
				$("#orgId").val(id);
				$("#orgName").val(name);

				$(".orgName-error").hide();
				$("#orgName").addClass("valid");
				$("#orgName").removeClass("error");
				$("#orgName").attr("class","form-control valid");


			}
			$(".input-group-btn.open").removeClass("open");
		}
		
		var positionLis = $('.post-lt');  //岗位名称
		var allocaPosts = $('.post-rt')   //已分配岗位
		
		//检测该岗位是否已经分配在右侧
		function checkPost(id){
			var flag=false;
			$('.post_righT').each(function() {
				if(id == $(this).data("id")){
					flag = true;
				}
			})
			return flag;//未分配
		}
		
		//左侧岗位向右容器加文本
		positionLis.on('click','.post_lefT',function() {
			var postId = $(this).data("id"); 
			var name = $(this).data("name"); 
			if(!checkPost(postId)){
				//未被分配则添加到右侧
				$(".post-rt").append("<li data-id='"+postId+"' data-name='"+name+"'class='post_righT'>"+name+"</li>");
				removePost();
			}
		});
		
		//触发左箭头添加全部内容
		$(".postRight").click(function(){
//			$(".post-rt").html("");
			$(".post_lefT").each(function() {
				var postId = $(this).data("id");
				var name = $(this).data("name");
				if(!checkPost(postId)){
					//未被分配则添加到右侧
					$(".post-rt").append("<li data-id='"+postId+"' data-name='"+name+"'class='post_righT'>"+name+"</li>");
				}
			})
			removePost();
		});
		
		//触发右箭头添加全部内容
		$(".postLeft").click(function(){
			$(".post-rt").html("");
		});

		// 手机号码验证
		jQuery.validator.addMethod("isMobile", function(value, element) {
			var length = value.length;
			var mobile = /^[1][34587][0-9]{9}$/;
			return this.optional(element) || (length == 11 && mobile.test(value));
		}, "请正确填写您的手机号码");
		
		// 帐号格式验证
		jQuery.validator.addMethod("isCode", function(value, element) {
			var code = /^[0-9a-zA-Z\-_]{1,20}$/;
			return this.optional(element) || (code.test(value));
		}, "只能包含字母、数字、中划线、下划线");
		
		
		$(function(){
			$("#form-addComUser").validate({
				rules:{
					code:{required:true,maxlength:20,isCode:true},
					name:{required:true,maxlength:20},
					// orgName:{required:true},
					mobile:{required:true,maxlength:11,isMobile:true},
					mail:{email: true,maxlength:20},
					deptNo:{maxlength:20},
					landline:{maxlength:20}
				},
				messages:{
					code:{required:"必填",maxlength:'最多允许输入20个字符',isCode:"只能包含字母、数字、中划线、下划线"},
					name:{required:'必填',maxlength:'最多允许输入20个字符'},
					// orgName:{required:'请选择部门'},
					mobile:{required:'必填',maxlength:'最多允许输入11位手机号码',isMobile:"请填写正确的手机号"},
					mail:{email: "请填写正确的邮箱",maxlength:"最多允许输入20个字符"},
					deptNo:{maxlength:"最多允许输入20个字符"},
					landline:{maxlength:"最多允许输入20个字符"}
				},
				submitHandler: function (form) {
					$.eLoading(true);
					if(!$("#orgName").val()){
						$(".orgName-error").show();
						$("#orgName").addClass("error");
						return;
					}
					//拼接岗位
					var postsNodes = $('.post_righT')   //已分配岗位
					var posts="";//岗位字符串
					$('.post_righT').each(function() {//已分配岗位
						posts += $(this).data("id") + ",";//拼接岗位
					})
					$("#posts").val(posts);
					$(form).ajaxSubmit({
						type: "POST",
						dataType:"json",
						url:CONTEXT+"sysCompanyUser/addCompUser",
						success: function (data) {
							if(data.success){
								window.location="index#companyUser";
								$.eAlert("添加用户","添加成功");
							} else {
								$.eAlert("添加用户",data.msg);
							}
							$.eLoading(false);
						}
					})
				}
			});
		});
	});
}