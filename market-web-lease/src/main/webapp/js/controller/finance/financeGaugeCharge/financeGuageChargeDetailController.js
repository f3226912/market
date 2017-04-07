function _call(templateUrl,param){
	var params=Route.params;//获取页面传值对象
	
	if(params && params.id){
		var id=params.id;//取值方式：value=对象.key
		var url=templateUrl+"/"+params.id;
/*		console.log("templateUrl : " + templateUrl);
		console.log("url : " + url);*/
    }else{
    	location.href = "index#financeGaugeCharge";
		return;
    }
	$("#main-wrapper").loadPage(url,
			["css/form-imput.css","css/finance.css","lib/css/datepicker.css"],
			["lib/bootstrap-datepicker.js","lib/bootstrap-datetimepicker.js","lib/distpicker.data.js","js/common/pageBar.js","lib/jquery.tmpl.min.js"],
			function(){
		   
	        
		//初使化本次用量
		initQuantity();
		function initQuantity(){
			var current = 0;
			//初使化时显示本次用量
			if($("#detailStatus").val()=="1"){
				current = $("#showDetailCurrent").text().trim();
			}else{
				current = $("#detailCurrent").val().trim();
			} 
			var last = $("#detailLast").text().trim();
			var quantity = parseFloat(current)-parseFloat(last);
			
			$("#detailQuantity").text(dealFormatResult(quantity));
		}
		$(".start-date").datepicker({
			format: "yyyy-mm-dd",
			autoclose: true
		});
		//点击取消后返回列表
		$("#cancel").click(function(){
			window.location="index#financeGaugeCharge";
		});
		//数值格式化为2位小数
		function dealFormatResult(formatValue){
			var result = (Math.ceil(formatValue*100)/100).toFixed(2);
			return result;
		}
		//计算收费金额和分段价格显示
		function getPriceAndAmount(current,wastage){
			var last = $("#detailLast").text().trim();
			var sectionalCharge = $("#detailSectionalCharge").val().trim();
			var newPrice = $("#detailNewPrice").val().trim();
			var expStandardId = $("#detailExpStandardId").val().trim();
			
			var dto={"current":current,
					"last":last,
					"wastage":wastage,
					"sectionalCharge":sectionalCharge,
					"newPrice":newPrice,
					"expStandardId":expStandardId
				};
		
			$.ajax({
				url:CONTEXT+"financeGaugeCharge/updateAmount",
				data:dto,
				type: "POST",
				dataType:'json',        
				success:function(data){
					if(data.success && data.result){
						var lengthStr = data.result.length-1;
						//设置收入金额
						$("#detailAmount").text(dealFormatResult(data.result[lengthStr]));
						if(dto.sectionalCharge =="1"){
							var priceList = "";
							for(var i =0; i<lengthStr;i++){
								priceList += data.result[i] + "<br/>";
							}
							$("#detailPriceList").html(priceList);
						}
					} else {
							$("#detailAmount").text("");
							//$.eAlert("操作提示",data.msg);
					}
				},
				error:function(data){
					//$("#detailAmount").text("");
					//$.eAlert("操作提示",data.msg);
	             }
			});
		}
		//自定义验证本次读数
		$.validator.addMethod("validCurrentValue",function(value,element){
			return this.optional(element) || validCurrentValue(value);	
		},"本次读数不能小于上次读数!");
		
		//自定义验证损耗用量
		$.validator.addMethod("validWastageValue",function(value,element){
			return validWastageValue(value);	
		},"");
		
		//真正验证本次读数的方法
		function validCurrentValue(value){
			var last = $("#detailLast").text().trim();
			if(parseFloat(value)<parseFloat(last)){
				return false;
			}else{
				var wastage = $("#detailWastage").val().trim();
				if(!wastage){
					wastage = 0;
				}
				//设置本次用量
				var quantity = parseFloat(value)-parseFloat(last);
				$("#detailQuantity").text(dealFormatResult(quantity));
				getPriceAndAmount(value,wastage);
				return true;
			}
		}
		//真正验证损耗用量的方法
		function validWastageValue(value){
			var current = $("#detailCurrent").val().trim();	
			if(!value){
				value = 0;
			}
			getPriceAndAmount(current,value);
			return true;
		}
		//自定义校验规则
		 $("#form-addMeterReading").validate({
			    rules: {
			      current: {
			        range:[0.00,999999999.00],
			        validCurrentValue:true
			      },
			      wastage: {
			    	  range:[0.00,999999999.00],
			    	  validWastageValue:true
			      }
			    },
			    messages: {
			      current: {  
			        range: "本次读数只能在[0.00-999999999.00]范围内"
			      },
			      wastage: {
			    	range: "损耗用量只能在[0.00-999999999.00]范围内"
			      }
			     
			    }
			});
		//绑定保存事件
		$("#saveForm").click(function(){
			//$("#form-addMeterReading").validate();
			//$("#form-addMeterReading").submit();
			if(!$("#form-addMeterReading").valid()){
				return;
			}
			//点击保存后，如果是已付款，则不需要编辑当前记录，返回列表
			if($("#detailStatus").val().trim()=="1"){
				window.location="index#financeGaugeCharge";
			}else{
				if(!$("#detailAmount").text().trim()){
					$.eAlert("操作提示","收费金额为空,请检查合同，资源，费项!");
					return;
				}
				$.eLoading(true);
				var wastage =$("#detailWastage").val().trim();
				var addData = {
						id:id,
						noteDateStr:$("#detailNoteDate").val(),
						current:dealFormatResult($("#detailCurrent").val().trim()),
						wastage:(wastage==null || wastage=="")?0:dealFormatResult(wastage),
						amount:$("#detailAmount").text().trim(),
				};
				$.ajax({
					url:CONTEXT+"financeGaugeCharge/update",
					data:addData,
					type: "POST",
					dataType:'json',        
					success:function(data){
						if(data.success){
							$.eAlert("操作提示","保存成功!");
								window.location="index#financeGaugeCharge";
							} else {
								$.eAlert("操作提示",data.msg);
							}
						$.eLoading(false);
						},
					error:function(data){
						$.eAlert("操作提示",data.msg);
						$.eLoading(false);
		             }
				});
			}
		});
	});
}