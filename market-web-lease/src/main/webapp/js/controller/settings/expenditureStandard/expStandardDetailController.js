var checked_row;
function _call(templateUrl,param){
	$("#main-wrapper").loadPage(templateUrl,
			["lib/css/DT_bootstrap.css","css/form-imput.css","css/resourMage.css","css/parameter.css"],
			["lib/jquery-migrate.js","js/common/pageBar.js","lib/jquery.tmpl.min.js",'js/controller/settings/expenditureStandard/expStandardCommon.js'],
			function(){
				//加载费项类型下拉框数据
				loadDictionary("expenditureType","expType");
				//加载费项类型下拉框数据
				loadDictionary("calculationMethod","rentMode");
				//加载计费单位
				loadDictionary("chargeUnit","chargeUnit");
				
				//初始化参数
				var params = Route.params;
				//标识入口是计费标准管理列表页面
				if(params != undefined && params.flag != undefined && params.flag == "fromExp" && params.expType != undefined && params.expId != undefined)
				{
					var expType = params.expType;
					var expId = params.expId;
					$("#expType").val(expType);
					//加载费项名称
					loadExpenditures();
					
					$("#expId").val(expId);
					$("#expType").attr("disabled","disabled");
					
					//取到路由参数之后，把参数置为空
					Route.params = {};
					
					initPage(expType);
				}else
				{
					//加载费项名称
					loadExpenditures();
				}
				
				//费项类型下拉框修改
				$("#expType").change(changeExpType);
				//计算方法下拉框修改
				$("#rentMode").change(changeRentMode);
				//是否分段收费change事件处理
				$("input:radio[name='sectionalCharge']").change(changeIsSectionCharge);
			
				//添加分段计费行
				$("#addChargeSection").click(clickAddChargeSection);
				//添加分段计费行
				$("#deleteChargeSection").click(clickDeleteChargeSection);
				
				//表单验证
				validateForm("form-add");
				
				//输入上限用量时，同步上限用量到下一行的下限用量
				bindKeyup(0);
		}
	);
}

function clickSaveForm()
{
	$.eLoading(true);
	$("#expType").attr("disabled",false);
	$.ajax({
		type: "POST",
		dataType: "json",
		url: CONTEXT+"expenditureStandard/addExpenditureStandard",
		data: $('#form-add').serialize(),
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

/**
 * 控制页面控件隐藏或者显示
 * @param expType
 * @param rentMode
 */
function initPage(expType)
{
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
		
		$("#meterTable").show();
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
	}
}


