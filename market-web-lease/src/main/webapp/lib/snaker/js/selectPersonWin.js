/**
 * 选择岗位弹出框js
 */
$(function(){
	//当前点击的树id
    var CURRENT_CLICK_TREE_ID="";
    
	//点击取消
    $('#selectPersonWinCancelBtn').click(function(){
    	$(window.parent.document.getElementById("wfSelectPersonWin")).hide();
    });
    
    //点击保存
    $('#selectPersonWinSavelBtn').click(function(){
    	//获取选择的岗位
    	var person = getSelectPost();
    	$("#monitorDescAdd",window.parent.document).val(person[0].name);
    	$("#monitorIdAdd",window.parent.document).val(person[0].id);
    	$(window.parent.document.getElementById("wfSelectPersonWin")).hide();
    });
    
    

    
    
	

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
		var url = "sysCompanyUser/getUserListByOrg";

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
			}
		}
		
		// 放在已选用户里面
		var ztreNm = $('.ztree_list');
		ztreNm.on('click', '.listName', function() {
			//如果是单选
			$(this).parent().children().removeClass("selected");
			$(this).addClass("selected");
		})
		

		// 获取已选择的岗位，格式为： [{"id":11,"name":"aaa"}]
		var getSelectPost = function(){
			var res = [];
			var $li = $('.ztree_list').find("li.selected");
			if($li.length > 0){
				var id = $li.attr("uid");
				var name = $li.find(".list_sp").text();
				res.push({"id":id,"name":name});
			}
			return res;
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
