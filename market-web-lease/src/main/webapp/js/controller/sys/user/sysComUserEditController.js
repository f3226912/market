function _call(templateUrl,param){
	try{
		var params=Route.params;//获取页面传值对象
		var id=params.id;//取值方式：value=对象.key
		templateUrl = templateUrl + "?id="+id;
	}catch (e) {
		window.location.href="index#companyUser";
		return;
	}
	$("#main-wrapper").loadPage(templateUrl,
			["css/form-imput.css","css/system.css","lib/css/metroStyle.css"],
			["lib/jquery.ztree.all.js"],
			function(){
		
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
			
			//查询所选部门下的所有合法岗位
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
			
			//查询用户所有的岗位
			function loadPost2(params){
				$.ajax({
					type: "GET",
					dataType: "json",
					url: CONTEXT+"sysCompanyUser/query",
					data: params,
					async: true,
					success: function (data) {
						if(data.success){
							var posts = data.result;
							if(posts){
								$(".post-rt").html("");
								$.each(posts, function(i, post){
									var postId=post.id;
									var name=post.name;
									$(".post-rt").append("<li data-id='"+postId+"' data-name='"+name+"'class='post_righT'>"+name+"</li>");
								});
							}
							removePost();
						}else {
							
						}
					}
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
				}
				$(".input-group-btn.open").removeClass("open");
			}
			
			if($("#id").val()){
				loadPost2({"userId":$("#id").val()})
			}
			
			var positionLis = $('.post-lt');  //岗位名称
//			var allocaPosts = $('.post-rt')   //已分配岗位
			
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
			
			$(function(){
				$("#form-editComUser").validate({
					rules:{
						name:{required:true,maxlength:20},
						orgName:{required:true},
						mobile:{required:true,maxlength:11,isMobile:true},
						mail:{email: true,maxlength:20},
						deptNo:{maxlength:20},
						landline:{maxlength:20}
					},
					messages:{
						name:{required:'必填',maxlength:'最多允许输入20个字符'},
						orgName:{required:'请选择部门'},
						mobile:{required:'必填',maxlength:'最多允许输入11位手机号码',isMobile:"请填写正确的手机号"},
						mail:{email: "请填写正确的邮箱",maxlength:"最多允许输入20个字符"},
						deptNo:{maxlength:"最多允许输入20个字符"},
						landline:{maxlength:"最多允许输入20个字符"}
					},
					submitHandler: function (form) {
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
							url:CONTEXT+"sysCompanyUser/editCompUser",
							success: function (data) {
								if(data.success){
									$.eAlert("编辑用户","修改成功");
									window.location="index#companyUser";
								} else {
									$.eAlert("编辑用户",data.msg);
								}
							}
						})
					}
				});
			});
	});
}