/**
 * 添加流程定义的js
 */

$(function() {
	//获取业务类型
	$.getJSON(CONTEXT+"wfProcessDefine/getWfBusTypeList",function(data){
		if(data.success){
			var obj = data.result;
			var selAdd = $("#busTypeAdd");
			selAdd.empty();
			for(var i=0;i<obj.length;i++){
				var optionAdd = $("<option></option>");
				optionAdd.val(data.result[i].busType).text(data.result[i].busTypeDesc).appendTo(selAdd);
			}
		} else {
			eAlert("提示信息",data.msg);
		}
	});

	//获取当前集团市场集合getMarketList
	$.getJSON(CONTEXT+"wf/process/getMarketList",function(data){
		if(data.success){
			var obj = data.result;
			var sel = $("#orgIdAdd");
			sel.empty();
			sel.append("<option value=''>请选择</option>");
			for(var i=0;i<obj.length;i++){
				var option = $("<option></option>");
				option.val(data.result[i].id).text(data.result[i].fullName).appendTo(sel);
			}
		} else {
			eAlert("提示信息",data.msg);
		}
	});
	//获取集团公司超级用户
	$.getJSON(CONTEXT+"wfProcessDefine/getComSuper",function(data){
			if(data.success){
				var obj = data.result;
				$("#monitorIdAdd").val(obj.id);
				$("#monitorDescAdd").val(obj.name);
			} else {
				eAlert("提示信息",data.msg);
			}
	});
	//选择用户pop
	$("#monitorDescAdd").click(function(thisObj){
		$("#wfSelectPersonWin").show();
	});
	

	//绑定保存事件
	$("#save").click(function(){
		var displayName = $("#displayNameAdd").val();
		var processDesc = $("#processDescAdd").val();
		var orgId = $("#orgIdAdd").val();
		if(displayName == ""){
			eAlert("提示信息","请输入流程模板名称(最多60个中文)");
			return;
		}
		if(orgId == ""){
			eAlert("提示信息","请选择所属市场");
			return;
		}
		
		//获取流程模板的定义内容。
		var content = getProcessDefineContent();
		//流程定义的内不符合规范，直接退出不提交到服务器。
		if (!content) {
			return false;
		}
		var pramas = {
				"displayName":displayName,
				"orgId":orgId,
				"busType":$("#busTypeAdd").val(),
				"monitorId":$("#monitorIdAdd").val(),
				"state":$('input[name="stateAdd"]:checked').val(),
				"processDesc":processDesc,
				"content":content
		};
		$.post(CONTEXT+"wf/process/saveOrUpdate",pramas,function(data){
			if(data.success){
				eAlertClose("提示信息","新增成功");
			}else{
				eAlert("提示信息",data.msg);
			}
		});
	});

	//绑定取消事件
	$("#close").click(function(){
		window.close();
	});
	

	//执行流程定义初始化
	initSnakerProcessDefine();
	
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