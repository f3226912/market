/**
 * 修改流程定义的js
 */

$(function() {
	//绑定保存事件
	$("#save").click(function(){
		var displayName = $("#displayNameAdd").val();
		var processDesc = $("#processDescAdd").val();
		var orgId = $("#orgIdAdd").val();
		if(orgId == ""){
			eAlert("提示信息","请选择所属市场");
			return;
		}
		if(displayName == ""){
			eAlert("提示信息","请输入流程模板名称(最多60个中文)");
			return;
		}
		//获取流程模板的定义内容。
		var content = getProcessDefineContent();
		
		//流程定义的内不符合规范，直接退出不提交到服务器。
		if (!content) {
			return false;
		}
		var pramas = {
				"id":id,
				"displayName":displayName,
				"name":$("#name").val(),
				"orgId":orgId,
				"busType":$("#busTypeAdd").val(),
				"monitorId":$("#monitorIdAdd").val(),
				"state":$('input[name="stateAdd"]:checked').val(),
				"processDesc":processDesc,
				"content":content
		};
		console.info(pramas);
		$.post(CONTEXT + "wf/process/saveOrUpdate",pramas,function(data){
			if(data.success){
				eAlertClose("提示信息","更新成功");
			}else{
				eAlertClose("提示信息",data.msg);
			}
		});
	})

		//选择用户pop
	$("#monitorDescAdd").click(function(thisObj){
		$("#wfSelectPersonWin").show();
	});
	
	//删除事件
	$("#delete").click(function(){
		var pramas = {
				"id":id
		};
		//确认删除当前流程
		eConfirm("删除流程模板","确认删除当前流程",function(){
			$.post(CONTEXT + "wf/process/deleteProcess",pramas,function(data){
				if(data.success){
					eAlertClose("提示信息","删除成功");
				}else{
					eAlertClose("提示信息",data.msg);
				}
			});
		});
	});

	//绑定取消事件
	$("#close").click(function(){
		window.close();
	});
	
	//执行流程定义初始化
	initSnakerProcessDefine(processModelJson);
});

/**
 *初始化流程定义。
 * @param processXml 已存在的流程定义内容，如果是流程回显，则需要传入此参数。
 *
 */
function initSnakerProcessDefine (processXml) {
	//重新清理流程面板元素，以免出现重复累加显示流程图。
	$("#snakerflow").empty();
	var model;
	if(processXml) {
		//processXml.replace(new RegExp("@@","gm"), "\"")
		model=eval("(" + processXml + ")");
	} else {
		model="";
	}
	$('#snakerflow').snakerflow({
		basePath : CONTEXT + "/lib/snaker/js/snaker/",
              ctxPath : "${ctx}",
		restore : model,
        formPath : "forms/"
	});

}

/**
	 * 弹出岗位选择窗口
	 *
	 */
var triggerSelectPostPopWinThisObj;
function popSelectPostWin(thisObj) {
	this.triggerSelectPostPopWinThisObj = thisObj;
	var selectedPost = $(thisObj).val();
	//本身已经有选择的岗位，则需要做回显。
	if ($.trim(selectedPost).length > 0) {
		window.initSelectedPost(selectedPost);
	}
	
	$("#wfSelectPostWin").show();
}


/**
 * 获取流程定义图的内容
 */
function getProcessDefineContent() {
	/**
	 *确定流程模板的定义。
	 *ps:此方法主要是为了修复svg画图的bug，当操作了元素和修改了属性后，
	 *   如果没有点击svg画布，那么修改的属性值是并没有真正生效的，必须要
	 	  点击svg画布后，才能将新属性值设置成功。
	 */
	//触发元素点击对象，确定好每个流程节点的属性值。
	$("#snakerflow").trigger("click");
	return window.top.getSnakerProcessDefine();
}
