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
		
		
		//资源
		$("#resourceName").on("click",function(){
			$("#leasResources").modal();
			loadMarketResourceType({});
			loadAreaAndBuilding({});
		});
		//资源弹框
		$("#leasResources").popwin({
			titleText: "选择租赁资源", //弹出框名
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
					loadContractInfo($(".canRentCls2").attr("rid"));
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
						$("#startLeasingDayView").text(contract.startLeasingDay);
						$("#leasingForfeitView").text(contract.leasingForfeit);
						$("#chargingWaysView").text(convertChargingWays(contract.chargingWays));
						$("#contractNoView").text(contract.contractNo);
						$("#partyBView").text(contract.partyB);
						$("#endLeasingDayView").text(contract.endLeasingDay);
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
		
		/* ---------------------------------------------------- */
		//转化收费方式  0 按周期收费 1 约定总金额
		function convertChargingWays(code){
			if (code == 0){
				return "按周期收费";
			}else if (code == 1){
				return "约定总金额";
			}
			return "";
		}
		
		// 新增退款-保存
		$("#saveBForm").click(function(){
			if(!$("#backAddForm").valid()){
				return;
			}
			if (!$("#contractNo").val()){
				$.eAlert("", "您还未选择合同信息, 不能提交...");
				return ;
			}
			$.ajax({
				type: "POST",
				dataType: "json",
				url: CONTEXT+"financeShould/newReturnItem",
				data: $('#backAddForm').serialize(),
				success: function (data) {
					if(data.success){
						$.eAlert("", "新增退款成功");
						window.location="index#financeShould";
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
	    $("#printSettingSelectPage_should_back").popwin({
			titleText: "选择套打设置", //弹出框名
			popwidth:"600",//设置宽度
			popCont:"#printSettingSelectPage_should_back", 
			btnText:["关闭","确认"],//按钮的文本
			drag:true,//是否拖动
			popUrl: "",
			callback: function(){
				
			}
		});
	    
		//新增临时收款-详情-打印
	    $("#btn-print-should-back").on("click",function(){
			if(!$("#backAddForm").valid()){
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
						window.open("financeShould/printBack?"+ paramString, "打印预览", 
				    			"width="+width+", height="+height+", top=0, left=0, toolbar=no, menubar=no, scrollbars=no, resizable=no, location=no, status=no"); 
						*/
						var url = "financeShould/printBack?"+paramString;
						newWin(url, "print_a");
					}
					else{
						$(data.result).each(function(index,item){ 
							content += "<a class='selectPrintSetting' href='javascript:void(0)' id='"+item.id+"'>"+item.printName+"</a><br><br>";
						});
						$("#printSettingSelectPage_should_back .modal-body").html(content);
						$("#printSettingSelectPage_should_back").modal();
						
						$(".selectPrintSetting").on("click",function(){
					    	var settingId = $(this).attr("id");
					    	var paramString = getPrintParamsString(settingId);
/*					    	var width = window.screen.width;
					    	var height = window.screen.height;
					    	window.open("financeShould/printBack?"+ paramString, "打印预览", 
					    			"width="+width+", height="+height+", top=0, left=0, toolbar=no, menubar=no, scrollbars=no, resizable=no, location=no, status=no");
					    	*/
					    	var url = "financeShould/printBack?"+paramString;
							newWin(url, "print_a");
					    	$("#printSettingSelectPage_should_back").modal("hide");
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
					"feeItemId" : $("#feeItemId").val(),
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
			return !isNaN(value);	
		},"请输入有效的数值");
		
		$.validator.addMethod("validPhoneValue",function(value,element){
			return validPhoneValue(value);	
		},"输入11位手机号");
		
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
		 $("#backAddForm").validate({
			    rules: {
			    	reciever : {
				        required: true,
				        validReceiverValue:true
			    	},
			    	accountPayed : {
				        required: true,
				        validAccountPayedEffect : true
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
				    	validAccountPayedEffect : "请输入有效的数值"
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
