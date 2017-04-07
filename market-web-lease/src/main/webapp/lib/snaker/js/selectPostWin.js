/**
 * 选择岗位弹出框js
 */
$(function(){
	//当前点击的树id
    var CURRENT_CLICK_TREE_ID="";
    //岗位id的前缀标识。
    var POST_ID_FLAG_PREFIX = "P_";
    
    
	//点击取消
    $('#selectPostWinCancelBtn').click(function(){
    	$(window.parent.document.getElementById("wfSelectPostWin")).hide();
    	//清除已选择岗位的列表，避免下次弹框出现脏数据。
    	cleanSelectedPostList();
    });
    
    //点击保存
    $('#selectPostWinSavelBtn').click(function(){
    	//获取选择的岗位
    	var posts = getSelectPost();
    	//解析为固定格式的字符串数据。
    	var nameIdMapStr = resolvePostMapperJsonAndGet(posts);
    	//父页面触发此弹出框的对象
    	$(window.parent.triggerSelectPostPopWinThisObj).val(nameIdMapStr);
    	$(window.parent.document.getElementById("wfSelectPostWin")).hide();
    	//清除已选择岗位的列表，避免下次弹框出现脏数据。
    	cleanSelectedPostList();
    });
    
    

	/**
	 * 分解消息接收者映射的json对象，并获取分解后的数据。
	 * @return 返回固定格式的名称id映射字符串。
	 */
	function resolvePostMapperJsonAndGet(mapper) {
		var nameIdMap = new Array();
		$.each(mapper, function(index, obj) {
			//格式： 岗位名称(P_岗位id)
			nameIdMap.push(obj.name + '(' + POST_ID_FLAG_PREFIX + obj.id + ')');
		});
		
		return nameIdMap.join("、");

	}
    
    
	//放在已选用户里面
    // 放在已选用户里面
	var ztreNm = $('.ztree_list');
	ztreNm.on('click', '.listName', function() {
		
		//多选
		var $userArea = $('.user_area');
		var uid = $(this).attr("uid");
		var count = $userArea.find("span[uid='"+uid+"']").length;
		if(count>0){
			return;
		}
		var name = $(this).find(".list_sp").text();
		var sp = $('<span class="sp_bg" uid="'+uid+'" uname="'+name+'">' + name
				+ '<i class="fa-times"><img src="' + CONTEXT  
				+'/lib/snaker/images/ic-1.png" /></i></span>');
		$userArea.append(sp);

		sp.on('click', function() {
			$(this).remove();
		})

		
	})

    //树的所代有码
     var setting = {
         view: {
             selectedMulti: true,
             showIcon: false,
             showLine: false
         },
         data: {
             simpleData: {
                 enable: true
             }
         },
         edit: {
             enable: false
         },
         callback: {
        	 onClick : function(event,treeId,treeNode){
					//如果已经被选中，不再刷新列表
					if(CURRENT_CLICK_TREE_ID == treeNode.id){
						return;
					}
					//刷新列表
					CURRENT_CLICK_TREE_ID = treeNode.id;
					if(treeNode.type == '2'){
						loadData();
					}
					
				}
         },
         check: {
             enable: true,
         }
     };

     var zNodes =[
      
         
     ];


     //初始化组织
	function initOrgTree(){
		var dtd = $.Deferred(); // 生成Deferred对象

		$.getJSON(CONTEXT+"sysOrg/initGroupTree",function(data){
			if(data.success){
				$.fn.zTree.init($("#treeDemo"), setting, data.result);
				dtd.resolve();
			} else {
				alert(data.msg);
			}
	
		});
		return dtd.promise();
	}

	$.when(initOrgTree())
	.done(function(){
		//选中集团
		
	})
	.done(function(){
		//获取选中的节点，初始化列表

	});
	
     function loadData(){
		//获取节点
	
		var treeObj = $.fn.zTree.getZTreeObj("treeDemo");
		var nodes = treeObj.getSelectedNodes();
		var orgId = nodes[0].id;
		var url = "post/getPostListByOrg";

		$.getJSON(CONTEXT+url,{"orgId":orgId},function(data){
			
			if(data.success){
				cleanRightPostList();
				var liAar = [];
				if(data.result){
					for(var i = 0;i<data.result.length;i++){
						var record = data.result[i];
						liAar.push('<li class="listName" uid="'+record.id+'"><span class="list_sp">'+record.name+'</span></li>');

					}
				}
				$("#selectPostContent").find("ul.ztree_list").html(liAar.join(''));
			} else {
				alert(data.msg);
			}
			
		});
	}
		//初始化已选择的人员
		var initSelected = function(data){
			if(data.length>0){
				
				var $userArea = $('.user_area');
				for(var i = 0;i<data.length;i++){
					var d = data[i];
					var sp = $('<span class="sp_bg" uid="'+d.id+'" uname="'+d.name+'">' + d.name
							+ '<i class="fa-times"><img src="' + CONTEXT 
							+'/lib/snaker/images/ic-1.png" /></i></span>');
					$userArea.append(sp);
					sp.on('click', function() {
						$(this).remove();
					})
				}

			}
		}
		
		/**
		 * 将初始化选择的岗位方法绑定到父窗口中，以便于父窗口调用弹框时进行回显。
		 * @param postNameIdMapStr 岗位名称和id的映射格式字符串
		 */
		window.parent.initSelectedPost = function(postNameIdMapStr) {
			var postJsonArr = new Array();
			var nameIds = postNameIdMapStr.split("、");
			$.each(nameIds, function(index, obj) {
				//根据格式分解出名称和id
				var id =obj.substring(obj.indexOf(POST_ID_FLAG_PREFIX) + 2, obj.indexOf(")"));
				var name = obj.substring(0, obj.indexOf("("));
				postJsonArr.push({"id": id, "name": name});
			});
			initSelected(postJsonArr);
		};
		
		
		
		// 获取已选择的岗位，格式为： [{"id":11,"name":"aaa"}]
		var getSelectPost = function(){
			var res = [];
			var $span = $('.user_area').children();
			if($span.length>0){
				$span.each(function(i){
					var id = $(this).attr("uid");
					var name = $(this).attr("uname");
					res.push({"id":id,"name":name});
				});
			}
			return res;
		}
		
		/**
		 * 重新初始化架构树
		 */
		function reInitTree() {
			//加载树的数据
			initOrgTree();
			//清除右边岗位列表
			cleanRightPostList();
			//清除下边已选择的列表。
			cleanSelectedPostList();
		}
		
		/**
		 * 清除右边岗位列表
		 */
		function cleanRightPostList() {
			$("#selectPostContent").find("ul.ztree_list").empty();
		}
		
		/**
		 * 清除下边已选择的列表。
		 */
		function cleanSelectedPostList() {
			$('.user_area').empty();
		}
		
});
