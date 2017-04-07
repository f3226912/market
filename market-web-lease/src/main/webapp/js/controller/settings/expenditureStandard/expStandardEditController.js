var checked_row;
function _call(templateUrl,param){
	$("#main-wrapper").loadPage(templateUrl,
			["lib/css/DT_bootstrap.css","css/form-imput.css","css/resourMage.css","css/parameter.css"],
			["lib/jquery-migrate.js","js/common/pageBar.js","lib/jquery.tmpl.min.js",'js/controller/settings/expenditureStandard/expStandardCommon.js'],
			function()
			{
				var params=Route.params;//获取页面传值对象
				if(params == undefined || params.id == undefined)
				{
					location.href='index#expenditureStandard';
					return;
				}
				
				var id = params.id;
				//初始化页面，加载下拉框数据、绑定事件
				initData(id);
				
				//绑定事件
				initEvent();
				
			}
	)
}

function initData(id)
{
	//加载费项类型下拉框数据
	loadDictionary("expenditureType","expType");
	//加载费项类型下拉框数据
	loadDictionary("calculationMethod","rentMode");
	//加载计费单位
	loadDictionary("chargeUnit","chargeUnit");
	
	//加载表单数据，并渲染表单
	loadExpStandard(id);
	
	//获取计费标准关联的未执行、执行中的合同
	$.getJSON(CONTEXT+"expenditureStandard/validateDelExpStandard",{"expStandardId":id},function(data){
		if(data.success){
			var contarctCount = data.result.contractCount;
			//判断是否有关联资源
			if(contarctCount >0)
			{
				//不能修改
				$("#save").attr("disabled","disabled");
			}else
			{
				$("#save").attr("disabled",false);
				//表单验证
				// validateForm("form-edit");
			}
		} else {
			$.eAlert("提示", data.msg);
		}
	});
}

function loadExpStandard(id)
{
	if(id == undefined)
	{
		return;
	}
	//获取修改对象数据
	$.ajax({
		type: "GET",
		dataType: "json",
		url: CONTEXT+"expenditureStandard/getById?id="+id,
		data: null,
		async: false,
		success: function (data) {
			if(data.success){
				var expStandard = data.result;
				initForm(expStandard);
				
				//加载费项名称
				loadExpenditures();
				$("#expId").val(expStandard.expId);
				
				//控制页面的显示和隐藏
				initPage(expStandard);
			}else {
				$.eAlert("提示",data.msg);
			}
		},
		error: function(data) {
			$.eAlert("提示",data.msg);
		}
	});
}

function initEvent()
{
	//费项类型下拉框修改
	$("#expType").change(changeExpType);
	//计算方法下拉框修改
	$("#rentMode").change(changeRentMode);
	//是否分段收费change事件处理
	$("input:radio[name='sectionalCharge']").change(changeIsSectionCharge);

	//添加分段计费行
	$("#addChargeSection").click(clickAddChargeSection);
	//删除分段计费行
	$("#deleteChargeSection").click(clickDeleteChargeSection);
	
	initTrClickEvent();
}

function clickSaveForm()
{
	$.eLoading(true);
	//提交之前解除
	$("#expType").attr("disabled",false);
	$.ajax({
		type: "POST",
		dataType: "json",
		url: CONTEXT+"expenditureStandard/updateExpenditureStandard",
		data: $('#form-edit').serialize(),
		success: function (data) {
			if(data.success){
				$.eAlert("提示","保存成功");
				window.location="index#expenditureStandard";
			}else {
				$.eAlert("提示",data.msg);
			}
			$.eLoading(false);
		},
		error: function(data) {
			$.eAlert("提示",data.msg);
			$.eLoading(false);
		}
	});
}

function initForm(obj)
{
	if(obj){
		for(var k in obj) {
			//分段计费
			if(k == "secionalCharges")
			{
				if(obj[k] == null)
				{
					continue;
				}
				var mainHtml = "";
				for(var i=0;i<obj[k].length;i++)
				{
					var downValue = obj[k][i].downValue;
					var upValue = obj[k][i].upValue;
					var chargeUnitPrice = obj[k][i].chargeUnitPrice;
					
					var num = i + 1;
					mainHtml+= '<tr>';
					mainHtml+='<td>'+num+'</td>';
					if(i == 0)
					{
						mainHtml+='<td><input type="number" name="secionalCharges['+i+'].downValue" value="'+downValue+'" class="form-control wid150" readonly="readonly"></td>';
					}else
					{
						mainHtml+='<td><input type="number" name="secionalCharges['+i+'].downValue" value="'+downValue+'" class="form-control wid150" ></td>';
					}
					
					if(i == obj[k].length -1)
					{
						mainHtml+='<td><input type="number" name="secionalCharges['+i+'].upValue" value="'+upValue+'" class="form-control wid150" readonly="readonly"></td>';
					}else
					{
						mainHtml+='<td><input type="number" name="secionalCharges['+i+'].upValue" value="'+upValue+'" class="form-control wid150"></td>';
					}
					
					mainHtml+='<td><input type="number" name="secionalCharges['+i+'].chargeUnitPrice" value="'+chargeUnitPrice+'" class="form-control wid150"></td>';
					mainHtml+='</tr>';
					bindKeyup(i);
				}
				
				$("#meterTableBody").append(mainHtml);
			}
			//是否分段收费
			else if(k == "sectionalCharge" )
			{
				$("input[name=sectionalCharge][value="+obj[k]+"]").attr("checked",'checked')
			}else
			{
				$("#"+k).val(obj[k]);
				$('input[name="'+k+'"]').val(obj[k]);
			}
		}
	}
}

/**
 * 控制页面控件隐藏或者显示
 * @param expType
 * @param rentMode
 */
function initPage(expStandard)
{
	var expType = expStandard.expType;
	var sectionalCharge = expStandard.sectionalCharge;
	var rentMode = expStandard.rentMode;
	//走表类费项
	if(expType == 2)
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
		$("#chargeUnitPrice").show();
		
		if(sectionalCharge == 1)
		{
			//分段收费表格
			$("#meterTable").show();
		}else
		{
			$("#meterTable").hide();
		}
	}else 
	{
		//周期性费项，计费单位显示
		if(expType == 1)
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
		
		//指定金额
		if(rentMode == 1)
		{
			//收费金额
			$("#chargeAmount").show();
			//单价
			$("#chargeUnitPrice").hide();
		}else if(rentMode == 2)
		{
			$("#chargeAmount").hide();
			$("#chargeUnitPrice").hide();
		}else
		{
			$("#chargeAmount").hide();
			$("#chargeUnitPrice").show();
		}
	}
}
