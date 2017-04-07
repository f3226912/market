function _call(templateUrl,param){
	$("#main-wrapper").loadPage(templateUrl,
			["css/form-imput.css","css/system.css","lib/css/metroStyle.css"],
			["lib/jquery.ztree.all.js"],
			function(){
		
		
		var postId=null;
		// 此处编写所有处理代码
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
					chkboxType: { "Y" : "ps", "N" : "ps" }
				}
		};
		
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
		
		/**
		 * 刷新功能权限树
		 */
		function initFuncTree(){
			$.ajax({
				type: "GET",
				dataType: "json",
				url: CONTEXT+"tree/function",
				data: {"postId":postId},
				async: false,
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
		}
		
		/**
		 * 刷新数据权限树
		 */
		function initDataTree(){
			$.ajax({
				type: "GET",
				dataType: "json",
				url: CONTEXT+"tree/data",
				data: {"postId":postId},
				async: false,
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
		}
		
		
		function initTree(){
			initFuncTree();
			$("#tab-postFunAuth").addClass("active");
			$("#tab-postDataAuth").removeClass("active");
			$("#funAuth").addClass("active");
			$("#dataAuth").removeClass("active");
			initDataTree();
		}
		
		function initRadio(){
			// 单选按钮监听事件
			$("input[type=radio]").on("click", function(){
				postId = $(".regular-radio:checked").val();
				console.log(postId);
				$("#id").val(postId);
				initTree();
			});
		}
		
		/*
		 * 定制zTree节点点击事件
		 * 该方法作用是显示部门下的岗位集合,拼装岗位列表
		 */
		function onClick(event, treeId, treeNode, clickFlag) {
			var attr = treeNode.attributs;
			var orgType = attr.orgType;//组织类型
			if(orgType=="2"){
				var id=treeNode.id;
				var name=treeNode.name;
				$("#orgId").val(id);
				$("#orgName").val(name);
				$.ajax({
					type: "GET",
					dataType: "json",
					url: CONTEXT+"post/query",
					data: {"orgId":id},
					async: true,
					success: function (data) {
						$("#post-list").html("");
						if(data.success){
							var posts = data.result;
							if(posts){
								$.each(posts, function(i, post){
									var id = "radio-"+i;
									var postId=post.id;
									var name=post.name;
									var html = '<label for="'+id+'"><input value="'+postId+'" type="radio" id="'+id+'" name="radio-1-set" class="regular-radio" />';
									html += '<span class="radio-mt10"></span><em class="radio-em">'+name+'</em></label>';
									$("#post-list").append(html);
								});
							}
						}else {
						}
						initRadio();
					}
				});
				$(".input-group-btn.open").removeClass("open");
			}
		}
		
		// 提交表单
		$("#saveForm").click(function(){
			var treeObj1 = $.fn.zTree.getZTreeObj("tree2");//功能权限对象
			if(treeObj1){
				var nodes1 = treeObj1.getCheckedNodes(true);
				var funcStr="";//功能权限字符串
				$.each(nodes1, function(i, n){
					funcStr += n.id + ",";//拼接功能权限
				});
				$("#funcStr").val(funcStr);
			}
			if(!$("#funcStr")){
				$.eAlert("权限分配","请设置岗位的功能权限");
				return;
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
			if(!$("#dataStr")){
				$.eAlert("权限分配","请设置岗位的数据权限");
				return;
			}
			$.ajax({
				type: "POST",
				dataType: "json",
				url: CONTEXT+"post/updatePost",
				data: $('#form-edit').serialize(),
				success: function (data) {
					if(data.success){
						$.eAlert("权限分配","设置岗位权限成功");
					}else {
						$.eAlert("权限分配",data.msg);
					}
				},
				error: function(data) {
					$.eAlert("权限分配","服务异常");
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
		
		//重置，清空表单
		$("#cancel-form").click(function(){
			var treeObj1 = $.fn.zTree.getZTreeObj("tree2");//功能权限对象
			treeObj1.checkAllNodes(false);
			var treeObj2 = $.fn.zTree.getZTreeObj("tree3");//数据权限对象
			treeObj2.checkAllNodes(false);
			$("#id").val("");
			$("#orgId").val("");
			$("#orgName").val("");
			$("#post-list").html("");
			$("#funcStr").val("");
			$("#dataStr").val("");
			
		});
		
		initTree();
	});
}