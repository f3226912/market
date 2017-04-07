/**
 * 加载计量表分类
 */
function loadMeasureType()
{
	$.ajax({
		type: "POST",
		dataType: "json",
		url: CONTEXT+"measureType/queryAll",
		async: false,
		data: {"status":1},
		success: function (data) {
			if(data.success){
				var str = "<option value=''>请选择</option>";
				var res_data = data.result.recordList;
				for(var i=0; i < res_data.length; i++){
					str += "<option value='"+res_data[i].id+"'>"+res_data[i].name+"</option>";
				}
				$("#measureTypeId").append(str);
			}else {
				$.eAlert("提示信息", data.msg);
			}
		},
		error: function(data) {
			$.eAlert("提示信息", data.msg);
		}
	});	
}

/**
 * 计量表分类change事件
 */
function changeMeasureType()
{
	var measureTypeId = $("#measureTypeId").val();
	if(measureTypeId == ""){
		return;
	}
	//根据计量表分类ID 找到费项名
	$.ajax({
		type: "POST",
		dataType: "json",
		url: CONTEXT+"measureSetting/queryExp",
		data: {"measureTypeId":measureTypeId},
		success: function (data) {
			if(data.success){
				$("#expName").val(data.result.name);
				$("#expNameTemp").val(data.result.name);
				$("#expId").val(data.result.id);
			}else {
				$.eAlert("提示信息", data.msg);
			}
		},
		error: function(data) {
			$.eAlert("提示信息", data.msg);
		}
	});
}

//绑定表单验证方法
function validateForm(formId)
{
	$("#"+formId).validate({
		rules: {
			code:{
				required:true,
				maxlength:50
			},
			measureTypeId:{
				required:true
			},
			maxVal:{
				required:true,
				range:[0.00,100000000.00]
			},
			expNameTemp:{
				required:true
			},
			resourceName:{
				required:true
			},
			status:{
				required:true
			}
		},
		messages:{
			code:{
				required:"必填",
				maxlength:"限输入1-50个字符"
			},
			measureTypeId:{
				required:"必填"
			},
			maxVal:{
				required:"必填",
				range:"输入最大为100000000"
			},
			expNameTemp:{
				required:"必填"
			},
			resourceName:{
				required:"必填"
			},
			status:{
				required:"必填"
			}
		},
	    submitHandler: function() {
	    	clickSaveForm();
	    }
	});
}