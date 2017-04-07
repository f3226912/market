 function _call(templateUrl,param){
	var params=Route.params;//获取页面传值对象
	var postId=params.id;//取值方式：value=对象.key
	
	$("#main-wrapper").loadPage(templateUrl+"?&id=" + postId,
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
		
		// 此处编写所有处理代码
		
		//应收款收款
		$("#saveSForm").click(function(){
			if(!$("#shouldAddForm").valid()){
				return;
			}
			//0-未收款, 1-已收款
			if ($("#recordStatus").val() == 1){
				$.eAlert("", "已经对该款项记录收过款了, 不能再次收款...");
				return ;
			}
			$.ajax({
				type: "POST",
				dataType: "json",
				url: CONTEXT+"financeShould/receiveMoney",
				data: $('#shouldAddForm').serialize(),
				success: function (data) {
					if(data.success){
						$.eAlert("收款成功","收款成功");
						window.location="index#financeShould";
					}else {
						$.eAlert("收款失败",data.msg);
					}
				},
				error: function(data) {
					$.eAlert("收款异常",data.msg);
				}
			});
		});		
		
		function dateFormat(source){
			if (source){
				return source.split(" ")[0];
			}
			return "";
		}
		
	    $("#printSettingSelectPage_should").popwin({
			titleText: "选择套打设置", //弹出框名
			popwidth:"600",//设置宽度
			popCont:"#printSettingSelectPage_should", 
			btnText:["关闭"],//按钮的文本
			drag:true,//是否拖动
			popUrl: "",
			callback: function(){
				
			}
		});
	    
		//应收款-详情-打印
	    $("#btn-print-should").on("click",function(){
			if(!$("#shouldAddForm").valid()){
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
						var shouldRecFeeId = $("#shouldRecFeeId").val();
				    	if(!shouldRecFeeId){
				    		shouldRecFeeId = -1;
				    	}
				    	var paramString = getPrintParamsString(settingId, shouldRecFeeId);
/*				    	var width = window.screen.width;
				    	var height = window.screen.height;
				    	window.open("financeShould/printShould?"+ paramString, "打印预览", 
				    			"width="+width+", height="+height+", top=0, left=0, toolbar=no, menubar=no, scrollbars=no, resizable=no, location=no, status=no");*/ 
				    	var url = "financeShould/printShould?"+paramString;
						newWin(url, "print_a");
					}
					else{
						$(data.result).each(function(index,item){ 
							content += "<a class='selectPrintSetting' href='javascript:void(0)' id='"+item.id+"'>"+item.printName+"</a><br><br>";
						});
						$("#printSettingSelectPage_should .modal-body").html(content);
						$("#printSettingSelectPage_should").modal();
						$("#printSettingSelectPage_should .savePop").css({"display":"none"});

						
						$(".selectPrintSetting").on("click",function(){
					    	var settingId = $(this).attr("id");
					    	var shouldRecFeeId = $("#shouldRecFeeId").val();
					    	if(!shouldRecFeeId){
					    		shouldRecFeeId = -1;
					    	}
					    	var paramString = getPrintParamsString(settingId, shouldRecFeeId);
/*					    	var width = window.screen.width;
					    	var height = window.screen.height;
					    	window.open("financeShould/printShould?"+ paramString, "打印预览", 
					    			"width="+width+", height="+height+", top=0, left=0, toolbar=no, menubar=no, scrollbars=no, resizable=no, location=no, status=no");*/
					    	var url = "financeShould/printShould?"+paramString;
							newWin(url, "print_a");
					    	$("#printSettingSelectPage_should").modal("hide");
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
	    
		function getPrintParamsString(settingId, shouldRecFeeId){
			var params = {
					"settingId" : settingId,
					"feeId" : shouldRecFeeId,
					"reciever" : $("#reciever").val(),
					"accountPayed" : $("#accountPayed").val(),
					"phone" : $("#phone").val(),
					"remark" : $("#remark").val(),
					"agent" : $("#agent").val(),
					"agentTime" : $("#agentTime").val()
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
		//本次实收金额大小验证
		$.validator.addMethod("validAccountPayedValue",function(value,element){
			var result = validAccountPayedValue(value);
			return result;	
		},"本次实收金额不允许大于应收金额");
		//本次实收金额数值有效性验证
		$.validator.addMethod("validAccountPayedEffect",function(value,element){
			return !isNaN(value);	
		},"请输入有效的数值");
		//本次实收金额不能输入空白字符
		$.validator.addMethod("validWhiteSpace",function(value,element){
			var result = validWhiteSpace(value);
			return !result;	
		},"不能输入空白字符");
		
		//手机号验证
		$.validator.addMethod("validPhoneValue",function(value,element){
			return validPhoneValue(value);	
		},"输入11位手机号");
		
		//空白字符验证
		function validWhiteSpace(value){
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
		//本次实收金额验证逻辑
		function validAccountPayedValue(value){
			if (value && parseFloat(value) > parseFloat($("#accountPayable").val()) ){
				return false;
			}
			return true;
		}
		//自定义校验规则
		 $("#shouldAddForm").validate({
			    rules: {
			    	reciever : {
				        required: true,
				        validReceiverValue:true
			    	},
			    	accountPayed : {
				        required: true,
				        validAccountPayedValue:true,
				        validAccountPayedEffect : true,
				        validWhiteSpace : true 
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
				    	validAccountPayedValue : "本次实收金额不允许大于应收金额",
				    	validAccountPayedEffect : "请输入有效的数值",
				    	validWhiteSpace : "不能输入空白字符"
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
