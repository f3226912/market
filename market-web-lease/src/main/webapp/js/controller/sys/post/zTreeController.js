function _call(templateUrl,param){
	$("#main-wrapper").loadPage(templateUrl,
			["lib/css/metroStyle.css"],
			["lib/jquery.ztree.core.min.js","lib/jquery.ztree.excheck.min.js","lib/jquery.ztree.exedit.min.js"],
			function(){
		// 此处编写所有处理代码
		var setting = {
            view: {
                addHoverDom: addHoverDom,
                removeHoverDom: removeHoverDom,
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
                enable: true
            },
            callback: {
                beforeRename: zTreeBeforeRename
            },
            check: {
                enable: true,
                chkStyle: "checkbox",
                chkboxType: { "Y" : "p", "N" : "ps" }
            }
        };

       var zNodes =[
            { id:1, pId:0, name:"父节点1", open:true,isParent:true},
            { id:11, pId:1, name:"父节点11"},
            { id:111, pId:11, name:"叶子节点111"},
            { id:112, pId:11, name:"叶子节点112"},
            { id:113, pId:11, name:"叶子节点113"},
            { id:114, pId:11, name:"叶子节点114"},
            { id:12, pId:1, name:"父节点12",isParent:true},
            { id:121, pId:12, name:"叶子节点121"},
            { id:122, pId:12, name:"叶子节点122"},
            { id:123, pId:12, name:"叶子节点123"},
            { id:124, pId:12, name:"叶子节点124"},
            { id:13, pId:1, name:"父节点13", isParent:true},
            { id:2, pId:0, name:"父节点2",isParent:true},
            { id:21, pId:2, name:"父节点21", open:true,isParent:true},
            { id:211, pId:21, name:"叶子节点211"},
            { id:212, pId:21, name:"叶子节点212"},
            { id:213, pId:21, name:"叶子节点213"},
            { id:214, pId:21, name:"叶子节点214"},
            { id:22, pId:2, name:"父节点22"},
            { id:221, pId:22, name:"叶子节点221"},
            { id:222, pId:22, name:"叶子节点222"},
            { id:223, pId:22, name:"叶子节点223"},
            { id:224, pId:22, name:"叶子节点224"},
            { id:23, pId:2, name:"父节点23"},
            { id:231, pId:23, name:"叶子节点231"},
            { id:232, pId:23, name:"叶子节点232"},
            { id:233, pId:23, name:"叶子节点233"},
            { id:234, pId:23, name:"叶子节点234"},
            { id:3, pId:0, name:"父节点3", isParent:true}
        ];

        $(document).ready(function(){
        	$.ajax({
    			type: "GET",
    			dataType: "json",
    			url: CONTEXT+"tree/demo",
    			data: null,
    			async: false,
    			success: function (data) {
    				if(data.success){
    					var zNodes=data.result;
    					$.fn.zTree.init($("#treeDemo"), setting, zNodes);
    				}else {
    					$.eAlert(data.msg);
    				}
    			},
    			error: function(data) {
    				$.eAlert(data.msg);
    			}
    		});
        });

        var newCount = 1;
        function addHoverDom(treeId, treeNode) {
            var sObj = $("#" + treeNode.tId + "_span");
            if (treeNode.editNameFlag || $("#addBtn_"+treeNode.tId).length>0) return;
            var addStr = "<span class='button add' id='addBtn_" + treeNode.tId
                + "' title='add node' onfocus='this.blur();'></span>";
            sObj.after(addStr);
            var btn = $("#addBtn_"+treeNode.tId);
            if (btn) btn.bind("click", function(){
                var zTree = $.fn.zTree.getZTreeObj("treeDemo");
                zTree.addNodes(treeNode, {id:(100 + newCount), pId:treeNode.id, name:"new node" + (newCount++)});
                return false;
            });
        };
        function removeHoverDom(treeId, treeNode) {
            $("#addBtn_"+treeNode.tId).unbind().remove();
        };
        function zTreeBeforeRename(treeId, newName){
            $.eAlert("修改提示", "修改成功");
        }
	});
}