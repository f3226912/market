function _call(templateUrl,param){
	var params=Route.params;//获取页面传值对象
	//var postId=params.id;//取值方式：value=对象.key
	
	$("#main-wrapper").loadPage(templateUrl,
			["css/form-imput.css","css/finance.css"],
			["lib/bootstrap-datepicker.js","lib/bootstrap-datetimepicker.js","lib/distpicker.data.js","js/common/pageBar.js","lib/jquery.tmpl.min.js"],
	function(){
		// 此处编写所有处理代码
		
		/*时间*/
		$('.form_datetime-component').datetimepicker({
			format: "yyyy mm dd - hh:ii",
			autoclose: true
		});
		$(".start-date").datepicker({
			format: "yyyy-mm-dd",
			autoclose: true
		  });
		
		
		/* ---------------------------------------------------- */
		
		//选择其他费项
		$("#feeItemName").on("click",function(){
			$("#feeItemDiv").modal();
			loadFeeItemAndStandards({});
		})
		//费项弹出框
		$("#feeItemDiv").popwin({
			titleText: "选择其他费项", //弹出框名
			popwidth:"900",//设置宽度
			btnText:["取消","确认"],//按钮的文本
			drag:true,//是否拖动
			//formValid: "", //表单验证方法
			popUrl: null,
			callback: function(){
				$("#feeItemDiv").modal('hide');
				$("#feeItemId").val($("#feeItemIdBack").val());
				//费项名称
				$("#feeItemName").val($("#feeItemNameBack").val());
				//计费标准id
				$("#freightBasisId").val($("#freightBasisIdBack").val());
			}
		});	
		
		//资源
		$("#resourceName").on("click",function(){
			$("#leasResources").modal();
			loadMarketResourceType({});
			loadAreaAndBuilding({});
		});
		//资源弹框
		$("#leasResources").popwin({
			titleText: "选择其他费项", //弹出框名
			popwidth:"900",//设置宽度
			btnText:["取消","确认"],//按钮的文本
			drag:true,//是否拖动
			popUrl: null,
			callback: function(){
				$(".modal").modal("hide");
/*				var selectName=$(".guaranSelect option:selected").text();//显示类型
				var qu=$(".leasBox .floorDong .canRentCls").parent().parent().find("b").text();//显示区
				var dong=$(".leasBox .floorDong .canRentCls").text();
				var textAll='';
				$(".leasBox .canRentCls2").each(function(){
					var lastButtomText=$(this).find('span').text();
					var textAll2=selectName+'-'+qu+'-'+dong+'-'+lastButtomText+',';
					textAll=textAll+textAll2;
				});
				console.log(textAll);
				alert(textAll);*/
				//$("#efristSelect option").append(textAll);
				console.log($(".canRentCls2"));
				console.log("length : " + $(".canRentCls2").length);
				var resourceId = $(".canRentCls2").attr("rid") ;
				if (resourceId){
					var resourceName = $(".canRentCls2").attr("rname") ;
					$("#resourceId").val(resourceId);
					$("#resourceName").val(resourceName);
					loadContractInfo(resourceId);
				}
			}
		});
		
		//加载合同信息
		function loadContractInfo(resourceId){
			$.getJSON(CONTEXT+"financeShould/findSpecialContract",{"resourceId" : resourceId},function(data){
				if(data.success){
					if (data.result.status == 0){
						$.eAlert("", "没有找到对应的合同信息");
						$("#resourceId").val("");
						$("#contractNo").val("");
						$("#contractVersion").val("");
					}else{
						var contract = data.result.contract ;
						$("#customerNameView").text(contract.customerName);
						$("#partyAView").text(contract.partyA);
						$("#startLeasingDayView").text(contract.startLeasingDayString);
						$("#leasingForfeitView").text(contract.leasingForfeit);
						$("#chargingWaysView").text(convertChargingWays(contract.chargingWays));
						$("#contractNoView").text(contract.contractNo);
						$("#partyBView").text(contract.partyB);
						$("#endLeasingDayView").text(contract.endLeasingDayString);
						$("#shopForfeitView").text(contract.shopForfeit);
						//
						$("#contractNo").val(contract.contractNo);
						$("#contractVersion").val(contract.id);
					}
				} else {
					$.eAlert("", data.msg);
				}
			});
		}
		
	
		
		function contentLi(){
			var num=$(".num"),
				tNum=1;
			num.each(function(){
				$(this).html(tNum);
				tNum++;
			})
		}
		
		function conManageDelete(){
			$("#agreementRem").click(function(){
				$(".color-eee").remove();
				contentLi();
			});
		}
		conManageDelete(); 
		
		function conManageColor(){
			$("#econManageDT .charges-tab-blank").click(function(){
				$("#econManageDT .charges-tab-blank").removeClass("color-eee");
				$(this).addClass("color-eee");
				//contentLi()
			})
		}
		conManageColor();		
		
/*		//资源选中状态切换
		$("body").on("click",".canRent",function(){
			if($(this).hasClass('canRentCls2')){
				$(this).removeClass('canRentCls2')
			}else{
				$(this).addClass('canRentCls2');
			}
		});*/
		
/*		//点击楼栋
		$("body").on("click",".leasBox .floorDong .floorls",function(){
			$(".leasBox .floorDong .floorls").removeClass('canRentCls');
			$(this).addClass('canRentCls');
			$(".leasBox .floor-num").css("display","block");
		});*/
/*		//点击楼层
		$("body").on("click",".leasBox .floor-num .floorls",function(){
			$(".leasBox .floor-num .floorls").removeClass('canRentCls');
			$(this).addClass('canRentCls');
			$(".leasBox .floorResources").css("display","block");
		});*/
		
/*		//资源类型
		$("body").on("change",".leasBox .guaranSelect",function(){
			var selectNum=$(".guaranSelect option:selected").val();
				if(selectNum=="1"){
					
				}
				if(selectNum==2){
					$(".floorLoudong").show();
				}
				if(selectNum==3){
					$(".floorLoudong").show();
				}
				if(selectNum==4){
					$(".floorLoudong").show();
				}
		});	*/	
		
		/* --------------------------------------------------------------------------- */

		//加载费项、计费标准数据
		function loadFeeItemAndStandards(params){
			$.getJSON(CONTEXT+"financeShould/getFeeItemAndStandards",params,function(data){
				if(data.success){
					$("#tempHeader").nextAll().remove();
					fillFeeItemAndStandards(data.result);
				} else {
					$.eAlert(data.msg);
				}
			});
		}
		//填充费项及其对应的计费标准
		function fillFeeItemAndStandards(records){
			for (var key in records){
				var $div = $('<div class="fees-checkbox-box"></div>');
				var $label = $('<label for="checkbox-item' + records[key].id + '"></label>');
				var $input = $('<input type="radio" rid="' + records[key].id + '" rname="' + records[key].name + '" id="checkbox-item' + records[key].id + '" name="tempItemCheckbox" class="checkbox-none Einput-m" />');
				$input.on("click",function(){
					if ($(this).is(":checked")){
						//费项id
						$("#feeItemIdBack").val($(this).attr("rid"));
						//费项名称
						$("#feeItemNameBack").val($(this).attr("rname"));
						//计费标准id
						var standardValue = $('#standard' + $(this).attr("rid")).val() ;
						$("#freightBasisIdBack").val(standardValue);
						//计费标准名称
						//var standardText = $('#standard' + $(this).attr("rid")).text() ;
					}
				})
				$label.append($input);
				var $titileSpan = $('<span class="checkbox-layer"></span><em class="checkbox-em" title="' + records[key].name + '">' + records[key].name + '</em>');
				$label.append($titileSpan);
				$div.append($label);
				var $contentSpan = $('<span class="span-select-down"></span>');
				var $select = $('<select id="standard' + records[key].id + '" class="guarantee-select"></select>');
				var standards = records[key].expStandards ;
				for (var skey in standards) {
					var $option = $('<option value="'+ standards[skey].id +'">'+ standards[skey].name +'</option>');
					$select.append($option);
				}
				$contentSpan.append($select);
				$div.append($contentSpan);
				$('#tempHeader').after($div);
			}
		}
		
		//加载市场资源类型
		function loadMarketResourceType(params){
			$.getJSON(CONTEXT+"financeShould/getMarketResourceType",params,function(data){
				if(data.success){
					$("#resourceType").empty();
					fillResourceType(data.result);
				} else {
					$.eAlert(data.msg);
				}
			});
		}
		//填充资源类型
		function fillResourceType(records){
			for (var key in records){
				var $option = $('<option value="' + records[key].id + '">' + records[key].name + '</option>');
				$("#resourceType").append($option);
			}
		}
		//资源类型变更触发事件
		$("#resourceType").on("change",function(){
			var $li = $("#floorUl .canRentCls");
			//没有选中楼层
			if ($li.length == 0){
				//$.eAlert("","请选择楼层..");
			}else{
				//加载单元-资源数据
				var params = {
							"buildingId" : $("#buildingDiv .canRentCls").attr("bid"),
							"floorId" : $li.attr("fid"),
							"resourceTypeId" : $(this).val() 
							};
				loadUnitAndResource(params);
			}
		});
		
		//加载区域-楼栋
		function loadAreaAndBuilding(params){
			$.getJSON(CONTEXT+"financeShould/getAreaAndBuilding",params,function(data){
				if(data.success){
					$("#buildingDiv").empty();
					fillAreaAndBuilding(data.result);
				} else {
					$.eAlert("", data.msg);
				}
			});
		}
		//填充区域-楼栋
		function fillAreaAndBuilding(records){
			for (var key in records){
				var $div = $('<div class="floorAZone"></div>');
				var $b = $('<b style="border-top:none;">' + records[key].name + '</b>');
				$div.append($b);
				var $ul = $('<ul class="floorDong"></ul>');
				$div.append($ul);
				var buildings = records[key].buildings ;
				for (var bkey in buildings) {
					var $li = $('<li class="floorls" id="building'+ buildings[bkey].id +'" bid="'+ buildings[bkey].id +'" >'+ buildings[bkey].name +'</li>');
					//楼栋单击触发事件
					$li.on("click",function(){
						//清除所有楼栋选中状态
						$(".leasBox .floorDong .floorls").removeClass('canRentCls');
						//选中当前楼栋
						$(this).addClass('canRentCls');
						//显示楼层区块
						$(".leasBox .floor-num").css("display","block");
						//加载楼层数据
						loadFloor({"buildingId" : $(this).attr("bid")});
					});
					$ul.append($li);
				}
				$("#buildingDiv").append($div);
			}
		}
		
		//加载楼层
		function loadFloor(params){
			var buildingId = params.buildingId ;
			$.getJSON(CONTEXT+"financeShould/getFloor",params,function(data){
				if(data.success){
					$("#floorUl").empty();
					fillFloor(data.result, buildingId);
				} else {
					$.eAlert("", data.msg);
				}
			});
		}
		//填充楼层
		function fillFloor(records, buildingId){
			for (var key in records){
				var $li = $('<li class="floorls"  fid="'+ records[key].id +'" >'+ records[key].name +'</li>');
				$li.on("click",function(){
					//清除所有楼层选中状态
					$(".leasBox .floor-num .floorls").removeClass('canRentCls');
					//选中当前楼层
					$(this).addClass('canRentCls');
					//显示资源区块
					$(".leasBox .floorResources").css("display","block");
					//加载单元-资源数据
					var params = {
								"buildingId" : buildingId,
								"floorId" : $(this).attr("fid"),
								"resourceTypeId" : $("#resourceType").val() 
								};
					loadUnitAndResource(params);
				});
				$("#floorUl").append($li);
			}
		}
		
		//加载单元-资源
		function loadUnitAndResource(params){
			$.getJSON(CONTEXT+"financeShould/getUnitAndResource",params,function(data){
				if(data.success){
					$("#resourceDiv").nextAll().remove();
					fillUnitAndResource(data.result);
				} else {
					$.eAlert("", data.msg);
				}
			});
		}
		//填充单元-资源
		function fillUnitAndResource(records){
			for (var key in records){
				var $div = $('<div class="resources-rows"></div>');
				var $p = $('<p class="resourcesUnit">'+ records[key].name +'</p>');
				$div.append($p);
				var $ul = $('<ul class="resourcesO"></ul>');
				$div.append($ul);
				
				$(".floorResources").width(records.length * 154 + "px");
				$(".floorDongUl .floorls").click(function(){
					var wh = $('.resources-rows');
					var whs = wh.length*154+'px';
					$(".floorResources").width(whs);
				})
				var resources = records[key].resources ;
				for (var rkey in resources) {
					var $li = $('<li class="canRent" rid="'+ resources[rkey].id +'" rname="'+ resources[rkey].name +'" ><span>'+ resources[rkey].name +'</span>  '+ convertLeaseStatus(resources[rkey].id) +'  '+ resources[rkey].leaseArea +'m²</li>');
					$li.on("click",function(){
						$(".canRent").removeClass('canRentCls2');
						$(this).addClass('canRentCls2');
					});
					$ul.append($li);
				}
				$('#resourceDiv').after($div);
			}
		}
		
		//转化出租状态 1 待放租  2  待租   3  已租
		function convertLeaseStatus(code){
			if (code == 1){
				return "待放租";
			}else if (code == 2){
				return "待租";
			}else if (code == 3){
				return "已租";
			}
			return "";
		}
		//转化收费方式  0 按周期收费 1 约定总金额
		function convertChargingWays(code){
			if (code == 0){
				return "按周期收费";
			}else if (code == 1){
				return "约定总金额";
			}
			return "";
		}
		//执行临时收款操作
		$("#saveTForm").click(function(){
			if(!$("#tempAddForm").valid()){
				return;
			}
			if (!$("#contractNo").val()){
				$.eAlert("", "您还未选择合同信息, 不能提交...");
				return ;
			}
			if (!$("#freightBasisId").val()){
				$.eAlert("", "计费标准为空, 请在选择费项时选择计费标准...");
				return ;
			}
			$.ajax({
				type: "POST",
				dataType: "json",
				url: CONTEXT+"financeShould/newTempItem",
				data: $('#tempAddForm').serialize(),
				success: function (data) {
					if(data.success){
						$.eAlert("","新增临时收款成功");
						window.location="index#financeReceived";
					}else {
						$.eAlert("", data.msg);
					}
				},
				error: function(data) {
					$.eAlert("", data.msg);
				}
			});
		});
		
		
		//打印
	    $("#printSettingSelectPage_should_temp").popwin({
			titleText: "选择套打设置", //弹出框名
			popwidth:"600",//设置宽度
			popCont:"#printSettingSelectPage_should_temp", 
			btnText:["关闭","确认"],//按钮的文本
			drag:true,//是否拖动
			popUrl: "",
			callback: function(){
				
			}
		});
	    
		//新增临时收款-详情-打印
	    $("#btn-print-should-temp").on("click",function(){
			if(!$("#tempAddForm").valid()){
				return;
			}
	    	//合同付款
			var bizType = 3; 
	    	$.getJSON(CONTEXT+"printSetting/queryList",{"bizType":bizType},function(data){
				if(data.success){
					var content = "";
					if(data.result == undefined || data.result.length == 0){
						$.eAlert("提示", "没有适用的套打设置");
					}
					else if(data.result.length == 1){
						var settingId = data.result[0].id;
						var paramString = getPrintParamsString(settingId);
/*				    	var width = window.screen.width;
				    	var height = window.screen.height;
						window.open("financeShould/printTemp?"+ paramString, "打印预览", 
				    			"width="+width+", height="+height+", top=0, left=0, toolbar=no, menubar=no, scrollbars=no, resizable=no, location=no, status=no");
						*/
						var url = "financeShould/printTemp?"+paramString;
						console.log("url : " + url);
						newWin(url, "print_a");
					}
					else{
						$(data.result).each(function(index,item){ 
							content += "<a class='selectPrintSetting' href='javascript:void(0)' id='"+item.id+"'>"+item.printName+"</a><br><br>";
						});
						$("#printSettingSelectPage_should_temp .modal-body").html(content);
						$("#printSettingSelectPage_should_temp").modal();
						
						$(".selectPrintSetting").on("click",function(){
					    	var settingId = $(this).attr("id");
					    	var paramString = getPrintParamsString(settingId);
/*					    	var width = window.screen.width;
					    	var height = window.screen.height;
					    	window.open("financeShould/printTemp?"+ paramString, "打印预览", 
					    			"width="+width+", height="+height+", top=0, left=0, toolbar=no, menubar=no, scrollbars=no, resizable=no, location=no, status=no");
					    	*/
					    	var url = "financeShould/printTemp?"+paramString;
					    	console.log("url : " + url);
							newWin(url, "print_a");
					    	$("#printSettingSelectPage_should_temp").modal("hide");
					    });
					}
				} else {
					$.eAlert("错误",data.msg);
				}
	    	});
	    });		
	    
	    function newWin(url, id) {
		    var a = document.createElement("a");
		    a.setAttribute("href", url);
		    a.setAttribute("target", "_blank");
		    a.setAttribute("id", id);
		    // 防止反复添加
		    if(!document.getElementById(id)) {                     
		    	document.body.appendChild(a);
		    }
		    a.click();
	    }
	    
		function getPrintParamsString(settingId){
			var params = {
					"settingId" : settingId,
					"feeItemName" : $("#feeItemName").val(),
					"reciever" : $("#reciever").val(),
					"accountPayed" : $("#account").val(),
					"phone" : $("#phone").val(),
					"remark" : $("#remark").val(),
					"agent" : $("#agent").val(),
					"agentTime" : $("#agentTime").val(),
					"contractVersion" : $("#contractVersion").val() 
			};
			return convertParamsToDelimitedList(params);
		}
		
		//将对象的属性转化成字符串形式(以&分隔的键值对(例如: key1=value1&key2=value2))
		function convertParamsToDelimitedList(params){
			var result = "";
			for (var item in params){
				if (params[item]){
					result += "&" + item + "=" + params[item];
				}
			}
			if (result){
				result = result.substring(1) ;
			}
			return result ;
		}
		
		
		//交款人验证
		$.validator.addMethod("validReceiverValue",function(value,element){
			return validReceiverValue(value);	
		},"超出长度限制(限20字)");
		
		//本次实收金额数值有效性验证
		$.validator.addMethod("validAccountPayedEffect",function(value,element){
			return !isNaN(value) && !validBlank(value);	
		},"请输入有效的数值");
		
		$.validator.addMethod("amountLength",function(value,element){
			return validAmountLength(value, 11);	
		},"数值长度过长");
		
/*		$.validator.addMethod("validBlank",function(value,element){
			return !validBlank(value);	
		},"不能输入空格");*/
		
		$.validator.addMethod("validPhoneValue",function(value,element){
			return validPhoneValue(value);	
		},"输入11位手机号");
		
		//验证金额整数部分长度
		function validAmountLength(value, len){
			var num = new Number(value);
			return !(num.toFixed().length > parseInt(len));
		}
		
		function validBlank(value){
			var pattern = /\s+/;
			return pattern.test(value);
		}
		
		//11位手机号验证
		function validPhoneValue(value){
			var pattern = /^[0-9]{11}$/;
			return pattern.test(value);
		}
		
		//交款人验证逻辑
		function validReceiverValue(value){
			if (value && value.length > 20){
				return false;
			}
			return true;
		}

		//自定义校验规则
		 $("#tempAddForm").validate({
			    rules: {
			    	reciever : {
				        required: true,
				        validReceiverValue:true
			    	},
			    	accountPayed : {
				        required: true,
				        validAccountPayedEffect : true,
				        amountLength : true
			    	},
			    	phone : {
				        required: true,
				        validPhoneValue : true
			    	},
			    	remark :{
			    		required: true
			    	}
			    },
			    messages: {
			    	reciever : {
				    	required:"必填",
				    	validReceiverValue : "超出长度限制(限20字)"		//此处的消息会覆盖addMethod时绑定的消息
			    	},
			    	accountPayed : {
				    	required:"必填",
				    	validAccountPayedEffect : "请输入有效的数值",
				    	amountLength : "数值长度过长"
			    	},
			    	phone : {
				        required: "必填",
				        validPhoneValue : "输入11位手机号"
			    	},
			    	remark : {
				    	required:"必填"
			    	}
			    }
			});
		
	});
}
