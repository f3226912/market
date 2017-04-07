/**
 * 根据费项类型获取费项名称
 */
function loadExpenditures()
{
	var expType = $("#expType").val();
	var params = {expType:expType};
	$.ajax({
		type: "GET",
		dataType: "json",
		data:params,
		async: false,
		url: CONTEXT+"expenditure/getByParams",
		success: function (data) {
			if(data.success){
//				$("#expId").html("");
//				$('#template').tmpl({rows:data.result.recordList}).appendTo('#expId');
				var temp = "<option value=''>请选择</option>" ;
				for(var i=0; i<data.result.length;i++){
					temp +="<option value="+data.result[i].id+">"+data.result[i].name+"</option>";
				}
				$("#expId").html("");
				$("#expId").append(temp);
			}else {
				$.eAlert("提示",data.msg);
			}
		},
		error: function(data) {
			$.eAlert("提示",data.msg);
		}
	});
}

/**
 * 获取数据字典公共方法
 */
function loadDictionary(dicType,objId)
{
	$.ajax({
		type: "post",
		dataType: "json",
		async: false,
		url: CONTEXT+"dictionary/getByType?type="+dicType,
		success: function (data) {
			if(data.success){
				var temp = "<option value=''>请选择</option>" ;
				for(var i=0; i<data.result.length;i++){
					temp +="<option value="+data.result[i].value+">"+data.result[i].remark+"</option>";
				}
				$("#"+objId).append(temp);
			}else {
				$.eAlert("提示", data.msg);
			}
		},
		error: function(data) {
			$.eAlert("提示",data.msg);
		}
	});
}

/**
 * 费项类型下拉框值改变事件
 */
function changeExpType()
{
	var expType = $("#expType option:checked").text();
	if(expType == "走表类费项")
	{
		//计费单位
		$("#chargeUnitContent").hide();
		//计算方法
		$("#rentModeContent").hide();
		//收费金额
		$("#chargeAmount").hide();
		
		//损耗率、是否分段收费
		//$("#meterContent").show();
		$("#wastageRateLi").show();
		$("#sectionalChargeLi").show();
		
		//单价
		$("#chargeUnitPrice").show();
		
		//分段表格是否显示取决于是否分段收费
		changeIsSectionCharge();
	}else 
	{
		//计费单位
		if(expType == "周期性费项")
		{
			$("#chargeUnitContent").show();
		}else
		{
			$("#chargeUnitContent").hide();
		}
		//计算方法
		$("#rentModeContent").show();
		
		//走表：损耗率，是否分段收费、分段表格
		//$("#meterContent").hide();
		$("#wastageRateLi").hide();
		$("#sectionalChargeLi").hide();
		$("#meterTable").hide();
		//单价和收费金额是否显示取决于计算方法
		changeRentMode();
	}
	
	loadExpenditures();
}

/**
 * 计算方法下拉框值修改
 */
function changeRentMode()
{
	var rentMode = $("#rentMode option:checked").text();
	if(rentMode == "指定金额")
	{
		//收费金额
		$("#chargeAmount").show();
		//单价
		$("#chargeUnitPrice").hide();
	}else if(rentMode == "手工录入")
	{
		$("#chargeAmount").hide();
		$("#chargeUnitPrice").hide();
	}else
	{
		$("#chargeAmount").hide();
		$("#chargeUnitPrice").show();
	}
}

/**
 * 是否分段收费下拉框改变事件
 */
function changeIsSectionCharge()
{
	var value = $('input[name="sectionalCharge"]:checked').val();
	if(value == 1)
	{
		$("#meterTable").show();
		//添加默认行
		if($("#meterTableBody tr").length == 0)
		{
			var mainHtml ="";
			mainHtml+= '<tr>';
			mainHtml+='<td>1</td>';
			mainHtml+='<td><input type="number" name="secionalCharges[0].downValue" value="0.00" class="form-control wid150" readonly="readonly"></td>';
			mainHtml+='<td><input type="number" name="secionalCharges[0].upValue" class="form-control wid150"></td>';
			mainHtml+='<td><input type="number" name="secionalCharges[0].chargeUnitPrice" class="form-control wid150" ></td>';
			mainHtml+='</tr>';
			
			mainHtml+= '<tr>';
			mainHtml+='<td>2</td>';
			mainHtml+='<td><input type="number" name="secionalCharges[1].downValue" class="form-control wid150"></td>';
			mainHtml+='<td><input type="number" name="secionalCharges[1].upValue" value="99999999999.00" class="form-control wid150" readonly="readonly"></td>';
			mainHtml+='<td><input type="number" name="secionalCharges[1].chargeUnitPrice" class="form-control wid150"></td>';
			mainHtml+='</tr>';
			
			$("#meterTableBody").append(mainHtml);
		}
	}else
	{
		$("#meterTable").hide();
	}
}		

/**
 * 新增按钮事件：最后一行的下标在原来的基础上加1，新增的下标为原来最后一行的下标
 */
function clickAddChargeSection()
{
	var table = document.getElementById("meterTableBody");//获取表格对象
	//添加之前总行数
	var totalRow = $("#meterTableBody tr").length;
	//原 最后一行的下标
	var oldLastRowIndex = totalRow - 1;
	var newLastRowIndex = totalRow;
	//原最后一行的序号
	var oldLastRowNum = totalRow;
	var newLastRowNum = totalRow + 1;
	//修改最后一行的属性信息
	table.rows[oldLastRowIndex].cells[0].innerHTML = newLastRowNum;
	$('input[name="secionalCharges['+oldLastRowIndex+'].downValue"]').val("");
	$('input[name="secionalCharges['+oldLastRowIndex+'].downValue"]').attr('name','secionalCharges['+newLastRowIndex+'].downValue');  
	$('input[name="secionalCharges['+oldLastRowIndex+'].upValue"]').attr('name','secionalCharges['+newLastRowIndex+'].upValue');  
	$('input[name="secionalCharges['+oldLastRowIndex+'].chargeUnitPrice"]').attr('name','secionalCharges['+newLastRowIndex+'].chargeUnitPrice');  
	
	//新行的下标等于原最后一行的下标
	var newRowIndex = oldLastRowIndex;
	var newRowNum = oldLastRowNum;
	
	var new_tr = table.insertRow(newRowIndex);
	
	var tdNum = new_tr.insertCell(0);
	tdNum.innerHTML = newRowNum;
	
	var tdDownValue = new_tr.insertCell(1);
	//倒数第二行的上限
	var twoLastRowIndex = oldLastRowIndex - 1;
	var twoUpValue = $('input[name="secionalCharges['+twoLastRowIndex+'].upValue"]').val();  
	tdDownValue.innerHTML = '<input type="text" name="secionalCharges['+newRowIndex+'].downValue" value="'+twoUpValue+'" class="form-control wid150">';
	
	var tdUpValue = new_tr.insertCell(2);
	tdUpValue.innerHTML = '<input type="text" name="secionalCharges['+newRowIndex+'].upValue" class="form-control wid150">';
	
	var tdChargeUnitPrice = new_tr.insertCell(3);
	tdChargeUnitPrice.innerHTML = '<input type="text" name="secionalCharges['+newRowIndex+'].chargeUnitPrice" class="form-control wid150">';
	//样式
	thirdColor();
	//重新绑定表格点击事件
	initTrClickEvent();
	//输入上限用量时，同步上限用量到下一行的下限用量
	bindKeyup(newRowIndex);
}

/**
 * 删除按钮事件：删除一行，该行后面的行的下标在原来的基础上-1
 */
function clickDeleteChargeSection()
{
	if(checked_row == undefined)
	{
		$.eAlert("提示", "请选择需要删除的数据!");
		return;
	}
	
	var totalCount = $("#meterTableBody tr").length;
	var lastRow = totalCount-1;
	if(checked_row == 0 || checked_row == lastRow)
	{
		$.eAlert("提示", "第一行和最后一行数据不允许删除!");
		return;
	}
	
	var table = document.getElementById("meterTableBody");//获取表格对象
	
	//删除之前，先修改下标,从该行的下一行开始修改
	for(var i = checked_row+1;i<$("#meterTableBody tr").length;i++)
	{
		var oldIndex = i;
		var newIndex = i - 1;
		var oldNum = i + 1;
		var newNum = i;
		table.rows[oldIndex].cells[0].innerHTML = newNum;
		$('input[name="secionalCharges['+oldIndex+'].downValue"]').attr('name','secionalCharges['+newIndex+'].downValue');  
		$('input[name="secionalCharges['+oldIndex+'].upValue"]').attr('name','secionalCharges['+newIndex+'].upValue');  
		$('input[name="secionalCharges['+oldIndex+'].chargeUnitPrice"]').attr('name','secionalCharges['+newIndex+'].chargeUnitPrice');  
	}
	//进行表格行删除
	var table = document.getElementById("meterTableBody");//获取表格对象
	table.deleteRow(checked_row);
	thirdColor();
	
	checked_row = undefined;
}

function thirdColor()
{
	$("#meterTableBody>tr").click(function(){
		$("#meterTableBody>tr").removeClass("color-eee");
		$(this).addClass("color-eee");
	})
}

function initTrClickEvent()
{
	$("#meterTableBody tr").click(function(){
		$("#meterTableBody tr").removeClass("color-eee");
		$(this).addClass("color-eee");
		checked_row = this.rowIndex - 1;
	});
}
	
function bindKeyup(index){
	$('input[name="secionalCharges['+index+'].upValue"]').keyup(function(){
		var upValue = $(this).val();
		var nextIndex = index + 1;
		$('input[name="secionalCharges['+nextIndex+'].downValue"]').val(upValue);
	});
}
var rules = {
	    	expType:{  
	            required:true
	        },  
	        expId:{  
	            required:true
	        },  
	        code:{  
	            required:true,
	            maxlength:50
	        },  
	        name:{  
	            required:true,
	            maxlength:50
	        },  
	        rentMode:{  
	        	required:true
	        },  
	        chargeAmount:{  
	            required:true,
	            range:[0.01,10000000000.00]
	        },  
	        chargeUnitPrice:{  
	        	required:true,
	        	range:[0.01,10000000000.00]
	        },  
	        chargeUnit:{  
	        	required:true,
	        },
	        wastageRate:{
	        	range:[0,100]
	        }
}
//绑定表单验证方法
function validateForm(formId)
{
	createValidateRules();
	$("#"+formId).validate({
		rules: rules,
		debug: true,
	    messages:{
	    	expType:{  
	            required:"必填"
	        },  
	        expId:{  
	            required:"必填"
	        },  
	        code:{  
	            required:"必填",
	            maxlength:'限输入1-100个字符'
	        },  
	        name:{  
	        	required:"必填",
	            maxlength:'限输入1-100个字符'
	        },  
	        rentMode:{  
	        	required:"必填"
	        },  
	        chargeAmount:{  
	            required:"必填",
	            range:"范围：0.01,10000000000.00"
	        },  
	        chargeUnitPrice:{  
	        	required:"必填",
	            range:"范围：0.01,10000000000.00"
	        },  
	        chargeUnit:{  
	        	required:"必填"
	        }
	    },
	    submitHandler: function() {
	    	clickSaveForm();
	    }
	});
}

function createValidateRules()
{
	var tableRows = $("#meterTableBody tr").length;
	var downValueRules = {
			required:true,
            range:[0.00,100000000000.00],
            downValue:true
	};
	var upValueRules = {
			required:true,
            range:[0.01,100000000000.00],
            upValue:true
	};
	var chargeUnitPriceRules = {
			required:true,
            range:[0.01,100000000000.00]
	};

	for (var i = 0; i < tableRows; i++) 
	{
		rules["secionalCharges["+i+"].downValue"] = downValueRules;
		rules["secionalCharges["+i+"].upValue"] = upValueRules;
		rules["secionalCharges["+i+"].chargeUnitPrice"] = chargeUnitPriceRules;
	}
}

$.validator.addMethod("downValue",function(value,element,params){
	//获取当前元素的name属性
	var nameStr = $(element).attr("name");
	//根据name属性值获取当前元素所在行的下标
	var startIndex = nameStr.indexOf("[");
	var endIndex = nameStr.indexOf("]");
	var index = nameStr.substring(startIndex+1,endIndex);
	startIndex = nameStr.indexOf(".");
	var name = nameStr.substr(startIndex+1);
	if(name == "downValue")
	{
		var upValue = $('input[name="secionalCharges['+index+'].upValue"]').val();
		if(value >= upValue)
		{
			return false;
		}else
		{
			return true;
		}
	}
	},"下限用量必须小于上限用量");

$.validator.addMethod("upValue",function(value,element,params){
	//获取当前元素的name属性
	var nameStr = $(element).attr("name");
	//根据name属性值获取当前元素所在行的下标
	var startIndex = nameStr.indexOf("[");
	var endIndex = nameStr.indexOf("]");
	var index = nameStr.substring(startIndex+1,endIndex);
	startIndex = nameStr.indexOf(".");
	var name = nameStr.substr(startIndex+1);
	if(name == "upValue")
	{
		var downValue = $('input[name="secionalCharges['+index+'].downValue"]').val();
		if(downValue >= value)
		{
			return false;
		}else
		{
			return true;
		}
	}
	},"上限用量必须大于下限用量");

// 保存时调用注释
$("#save").click(function(e){
	validateForm("form-edit");
});