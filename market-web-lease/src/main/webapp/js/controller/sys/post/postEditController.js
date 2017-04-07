function _call(templateUrl,param){
	try{
		var params=Route.params;//获取页面传值对象
		var postId=params.p;//取值方式：value=对象.key
		templateUrl=templateUrl+"/"+params.p;
	}catch (e) {
		window.location.href="index#post";
		return;
	}
	$("#main-wrapper").loadPage(templateUrl,
			["css/form-imput.css","lib/css/metroStyle.css","css/system.css"],
			["lib/jquery-migrate.js",
			 "js/common/pageBar.js",
			 "lib/jquery.tmpl.min.js",
			 "lib/jquery.ztree.all.js"],
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
					showLine: false
				},
				data: {
					simpleData: {
						enable: true
					}
				},
				check: {
					enable: true,
					chkStyle: "checkbox",
					chkboxType: { "Y" : "p", "N" : "ps" }
				}
		};
	
		$(document).ready(function(){
			$.ajax({
				type: "GET",
				dataType: "json",
				url: CONTEXT+"tree/dept",
				data: {"postId":postId},
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
		
		$(document).ready(function(){
			$.ajax({
				type: "GET",
				dataType: "json",
				url: CONTEXT+"tree/function",
				data: {"postId":postId},
				async: true,
				success: function (data) {
					if(data.success){
						var zNodes=data.result;
						$.fn.zTree.init($("#tree2"), setting2, zNodes);
					}else {
						$.eAlert("初始化功能权限",data.msg);
					}
				},
				error: function(data) {
					$.eAlert("初始化功能权限",data.msg);
				}
			});
		});
		$(document).ready(function(){
			$.ajax({
				type: "GET",
				dataType: "json",
				url: CONTEXT+"tree/data",
				data: {"postId":postId},
				async: true,
				success: function (data) {
					if(data.success){
						var zNodes=data.result;
						$.fn.zTree.init($("#tree3"), setting2, zNodes);
					}else {
						$.eAlert("初始化数据权限",data.msg);
					}
				},
				error: function(data) {
					$.eAlert("初始化数据权限",data.msg);
				}
			});
		});
		
		/*
		 * 定制zTree节点点击事件
		 * 该方法作用是将点击到的部门节点数据填充到form表单
		 */
		function onClick(event, treeId, treeNode, clickFlag) {
			var attr = treeNode.attributs;
			var orgType = attr.orgType;//组织类型
			if(orgType=="2"){
				console.log(treeNode);
				var id=treeNode.id;
				var name=treeNode.name;
				$("#orgId").val(id);
				$("#orgName").val(name);
			}
		}
		
		$(function(){
			$("#form-edit").validate({
			    rules:{  
			    	orgName:{required:true},  
			        name:{required:true,maxlength:20}
			    },  
			    messages:{
			    	orgName:{required:'请选择部门'},  
			        name:{required:'必填',maxlength:'最多允许输入20个中文字符'}
			    },
			    submitHandler: function (form) {
			    	var treeObj1 = $.fn.zTree.getZTreeObj("tree2");//功能权限对象
					if(treeObj1){
						var nodes1 = treeObj1.getCheckedNodes(true);
						var funcStr="";//功能权限字符串
						$.each(nodes1, function(i, n){
							funcStr += n.id + ",";//拼接功能权限
						});
						$("#funcStr").val(funcStr);
					}
					
					var treeObj2 = $.fn.zTree.getZTreeObj("tree3");//数据权限对象
					if(treeObj2){
						var nodes2 = treeObj2.getCheckedNodes(true);
						var dataStr="";//数据权限字符串
						$.each(nodes2, function(i, n){
							dataStr += n.id + ",";//拼接数据权限
						});
						$("#dataStr").val(dataStr);
					}
					$(form).ajaxSubmit({
						type: "POST",
						url:CONTEXT+"post/updatePost",
						success: function (data) {
							if(data.success){
								$.eAlert("修改岗位","修改成功");
								window.location="index#post";
							}else {
								$.eAlert("修改岗位",data.msg);
							}
						},
						error: function(data) {
							$.eAlert("修改岗位",data.msg);
						}
					})
				}
			});
		});

        // 全选按钮-功能权限
        $("#check-funAuth").on('click', function(){
        	var treeObj1 = $.fn.zTree.getZTreeObj("tree2");//功能权限对象
        	if(this.checked){
        		treeObj1.checkAllNodes(true);
        	}else{
        		treeObj1.checkAllNodes(false);
        	}
        });
        
        // 全选按钮-数据权限
        $("#check-dataAuth").on('click', function(){
        	var treeObj2 = $.fn.zTree.getZTreeObj("tree3");//数据权限对象
        	if(this.checked){
        		treeObj2.checkAllNodes(true);
        	}else{
        		treeObj2.checkAllNodes(false);
        	}
        });
		
		//取消
		$("#cancel-form").click(function(){
			window.location="index#post";
		});
	});
}