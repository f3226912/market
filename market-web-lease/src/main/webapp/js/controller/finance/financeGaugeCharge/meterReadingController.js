//数值格式化为2位小数
function dealFormatResult(formatValue){
	var result = (Math.ceil(formatValue*100)/100).toFixed(2);
	return result;
}
function _call(templateUrl,param){
	$("#main-wrapper").loadPage(templateUrl,
			["css/form-imput.css","css/finance.css","lib/css/datepicker.css","css/resourMage.css"],
			["lib/bootstrap-datepicker.js","lib/jquery-migrate.js","lib/bootstrap-datetimepicker.js","lib/distpicker.data.js","js/common/pageBar.js","lib/jquery.tmpl.min.js"],
		function(){
		
			//分页默认大小
			var PAGE_SIZE = 20;
			
			var currentOld=0;
			/*初使化日期*/
			$('#addNoteDateStart').datepicker({format:'yyyy-mm-dd', 
			    autoclose:true,  
			}).on('changeDate',function(e){
				var startTime = e.date; 
				$('#addNoteDateEnd').datepicker('setStartDate',startTime);
				$('#addNoteDateEnd').datepicker('hide');
			});
				
			
			$('#addNoteDateEnd').datepicker({format:'yyyy-mm-dd',
			    autoclose: true, 
			    }).on('changeDate',function(e){  
			        var endTime = e.date;  
			        $('#addNoteDateStart').datepicker('setEndDate',endTime);  
			        $('#addNoteDateStart').datepicker('hide');
			   });
			
			//初使化查询条件的下拉值
			initSelect();
			
			function initSelect(){
				//计量表分类
				$.ajax({
					type: "POST",
					dataType: "json",
					url: CONTEXT+"financeGaugeCharge/findAllMeasureType",
					data: {"status":1},
					success: function (data) {
						if(data.success){
							$("#measureTypeId").html("");
							$('#measureTypeIdTemp').tmpl({rows:data.result.recordList}).appendTo('#measureTypeId');
						}else {
							$.eAlert("操作提示", data.msg);
						}
					},
					error: function(data) {
						$.eAlert("操作提示", data.msg);
					}
				});
					
				//当前市场下的所有区域
				$.ajax({
					type: "GET",
					url: CONTEXT+"financeGaugeCharge/showAreaList",
					dataType: "json",
					success: function(data) {
						if(data.success){
							$("#areaId").html("");
							$('#areaIdTemp').tmpl({rows:data.result}).appendTo('#areaId');
						}else {
							$.eAlert("操作提示", data.msg);
						}
					},
					error: function(data) {
						$.eAlert("操作提示", data.msg);
					}
				});
				
				//当前区域下的所有楼栋
				$("#areaId").change(function(){
					var areaId = $(this).val();
					if(areaId){
						$.ajax({
								 type: 'POST',
							     url:CONTEXT+"financeGaugeCharge/showBuildList/"+areaId,
							     dataType:'json',
							     success: function(data) {
										if(data.success){
											$("#buildingId").html("");
											$('#buildIdTemp').tmpl({rows:data.result}).appendTo('#buildingId');
										}else {
											$.eAlert("操作提示", data.msg);
										}
									},
									error: function(data) {
										$.eAlert("操作提示", data.msg);
									}
							});
					}else{
						//如果没有选择区域，楼栋下拉默认全部
						 $("#buildingId").html("");
				    	 $("#buildingId").append("<option value=''>全部</option>");
					}
				});

			}
				
			
			//查询条件
			var condition = {};
			
			//初始化分页控件
			function initPageBar(result){
				
				// 分页工具组件
				$("#pagebar").page({
					pageIndex : 1,
					pageSize : PAGE_SIZE,
					total : result.total,
					callback : function(index, size){
						PAGE_SIZE = size;
						$('#templateBody').html(""); // 清空内容
						// 点击回调处理
						var areaId = $("#areaId").val().trim();
						var buildingId = $("#buildingId").val().trim();
						var measureTypeId = $("#measureTypeId").val().trim();
						var noteDateStart = $("#addNoteDateStart").val().trim();
						var noteDateEnd = $("#addNoteDateEnd").val().trim();
						var param = {
								"pageNum":index,"pageSize":PAGE_SIZE,
								"areaId":areaId,
								"buildingId":buildingId,
								"measureTypeId":measureTypeId,
								"noteDateStart":noteDateStart,
								"noteDateEnd":noteDateEnd,
								"marketId":Route.market
						};
						loadData(param, false);
					}
				});
			}
				
			//加载数据
			function loadData(page, isInit){
				$.ajax({
					url:CONTEXT+"financeGaugeCharge/queryMeterReadingList",
					data:page,
					type: "POST",
					dataType:'json',
					success:function(data){
						if(data.success){
							if(isInit){
								initPageBar(data.result);
							}
							$("#templateBody").html("");
							$('#total').html("");
							$('#template').tmpl({meterReadings:data.result.recordList}).appendTo('#templateBody');
							$("#recordCount").html(data.result.total);
/*							if(data.result.total>0){
								$('#total').html("共"+data.result.total+"笔");
							}*/
							//initQueryEvent();
							initTableEdit();
							} else {
								$.eAlert("操作提示",data.msg);
							}
						},
					error:function(data){
						$.eAlert("操作提示",data.msg);
		             }
				});
			}
		
			//绑定查询事件
//			function initQueryEvent(){
//				$("#query").click(function(){
//					var areaId = $("#areaId").val().trim();
//					var buildingId = $("#buildingId").val().trim();
//					var measureTypeId = $("#measureTypeId").val().trim();
//					var noteDateStart = $("#addNoteDateStart").val().trim();
//					var noteDateEnd = $("#addNoteDateEnd").val().trim();
//					condition = {
//							"pageNum":1,"pageSize":PAGE_SIZE,
//							"areaId":areaId,
//							"buildingId":buildingId,
//							"measureTypeId":measureTypeId,
//							"noteDateStart":noteDateStart,
//							"noteDateEnd":noteDateEnd,
//							"marketId":Route.market
//					};
//					loadData(condition, true);
//				});
//			};
			
			//绑定查询事件
			$("#query").click(function(){
				var areaId = $("#areaId").val().trim();
				var buildingId = $("#buildingId").val().trim();
				var measureTypeId = $("#measureTypeId").val().trim();
				var noteDateStart = $("#addNoteDateStart").val().trim();
				var noteDateEnd = $("#addNoteDateEnd").val().trim();
				condition = {
						"pageNum":1,"pageSize":PAGE_SIZE,
						"areaId":areaId,
						"buildingId":buildingId,
						"measureTypeId":measureTypeId,
						"noteDateStart":noteDateStart,
						"noteDateEnd":noteDateEnd,
						"marketId":Route.market
				};
				console.log(condition);
				loadData(condition, true);
			});
			
			//默认初始化数据
			loadData({"pageNum":1,"pageSize":PAGE_SIZE,"marketId":Route.market}, true);
			
			//结转为待付款
			$("#statement").click(function(){
				var list = new Array();
				//循环所有表格数据
				$("#tb tbody tr").each(function(trindex,tritem){//遍历每一行
					var rowData={};
					$(tritem).find(".ed").each(function(tdindex,tditem){
			    	  //如果当前读数不是抄表时，将此数据转为待付款数据
			    	  if($(tditem).hasClass('lsCurrent')){
			    		  //去掉前后空格
			    		  var current = $(tditem).text().trim();
			    		  if( current== "抄表"){
			    			  return false;
			    		  }else{
					    	  rowData.current = current;
			    		  } 
			    	  }
			    	  
			    	  if($(tditem).hasClass('lsWastage')){
			    		  var wastage = $(tditem).text().trim();
			    		  rowData.wastage = wastage; 
			    	  }
			    	  if($(tditem).hasClass('lsNoteDate')){
			    		  var noteDate = $(tditem).val().trim();
			    		  rowData.noteDateStr = noteDate;
			    	  }
			    	  if($(tditem).hasClass('lsRemark')){
			    		  var remark = $(tditem).val().trim();
			    		  rowData.remark = remark;	
			    	  }
			    	  if($(tditem).hasClass('lsMeasureId')){
			    		  rowData.measureId = $(tditem).val();
			    		  list.push(rowData);
			    	  }
			    	  
					});
				});
				//提交数据
				if(list && list.length>0){
					$.eLoading(true);
					$.ajax({
						url:CONTEXT+"financeGaugeCharge/batchSettlement",
						data:JSON.stringify(list),
						type: "POST",
						dataType:'json',
						contentType:"application/json",           
						success:function(data){
							if(data.success){
									//window.location="index#meterReading";
									$.eAlert("操作提示","操作成功!");
									loadData({"pageNum":1,"pageSize":PAGE_SIZE,"marketId":Route.market}, true);
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
		
		//初使化弹出框
		function initTableEdit(){
			var $this,newText;
				//表格列编辑
				$(".tableEdit").click(function(){
					$this = $(this);
					$("#currentMaxValue").val($this.siblings(".lsMaxMeasureVal").val());
					var measureId = $(this).siblings(".lsMeasureId").val().trim();
					getLast(measureId);
					var d = new Date().Format("yyyy-MM-dd");
					if($(this).text() == "抄表"){
						$(".inputVal1").val("");		//本次读数
						$(".inputVal2").val("0.00");	//损耗用量
						//抄表时日期为当前日期
						$("#addNoteDate").val(d);		//抄表日期
						$(".inputVal4").val("");		//备注
						$(".inputVal5").val(measureId);	//计量表id
						$("#myModal").modal();			//弹出抄表框
					}else{
						var val1 = $(this).text();
						var val2 = $(this).next().text().trim();
						var dateVal = $(this).siblings(".lsNoteDate").val().trim();
						var val4 = $(this).siblings(".lsRemark").val().trim();
						var val5 = measureId;
						//console.log("dateVal"+dateVal)
						$(".inputVal1").val(val1);		//本次读数
						$(".inputVal2").val(val2);		//损耗用量
						$("#addNoteDate").val(dateVal);	//抄表日期
						$(".inputVal4").val(val4);		//备注
						$(".inputVal5").val(val5);		//计量表id
						$("#myModal").modal();			//弹出抄表框
					}
	
					//自定义校验规则
					$("#formMeterReading").validate({
						    rules: {
						      current: {
						        range:[0.00,999999999.00],
						        validCurrentValue:true,
						        maxValue :true
						      },
						      wastage: {
						    	  range:[0.00,999999999.00]
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
					
					//本次-上次 读数校验
					$.validator.addMethod("validCurrentValue",function(value,element){
						return this.optional(element) || validCurrentValue(value);	
					},"本次读数不能小于上次读数!");
					
					//校验的真正执行方法
					function validCurrentValue(value){
						var last = $("#addLast").val().trim();
						if(parseFloat(value)<parseFloat(last)){
							return false;
						}else{
							//因为表单的校验在失去焦点和保存后都后执行，在点击保存后，会重新计算一次，为解决此问题，
							//判断本次读数有没有修改，有修改才计算，没有修改时不计算，这样就解决了保存时没有修改就不会重新计算
							if(currentOld != value){
								var wastageRate = $("#addWastagerate").val().trim();	//损耗率
								$(".inputVal2").val((value-last)*wastageRate/100);			//损耗用量
								currentOld=value;
							}
							return true;
						}
					}
					
					//计量表最大值校验
					$.validator.addMethod("maxValue",function(value,element){
						currentMax = $this.siblings(".lsMaxMeasureVal").val();
						return !(parseFloat(value) > parseFloat(currentMax));	
					},"不允许超过最大值" + $("#currentMaxValue").val());
					
				});//tableEdit click
				
				//弹出框保存按钮回调执行方法
				$("#saveTbale").on("click",function(){
					if(!$("#formMeterReading").valid()){
						return;
					}
					newText = [];
					$(".form-horizontal .form-control").each(function(index, item) {
						newText.push($(this).val());
					});
					//console.log("v"+newText)
					$this.text(dealFormatResult(newText[0]));							//本次读数
					$this.next().text(dealFormatResult(newText[1]));					//损耗用量
					$this.siblings(".lsNoteDate").val(newText[2]);	//抄表日期
				    $this.siblings(".lsRemark").val(newText[3]);	//备注
				    $this.siblings(".lsMeasureId").val(newText[4]);	//计量表id
				    //$this.siblings(".lsMaxMeasureVal").val(newText[5]);	//计量表最大读数
					$("#myModal").modal("hide");
				});
			
				$("#addNoteDate").datepicker({
					format: "yyyy-mm-dd",
					autoclose: true
				  });
			
				function getLast(measureId){
					//根据measureId查询当前计量表的上次读数和损耗率
					$.ajax({
						url:CONTEXT+"financeGaugeCharge/showMeterReadingInfo/"+measureId,
						data:{},
						type: "POST",
						dataType:'json',          
						success:function(data){
							if(data.success){
								var row = data.result;
								if (row){
									console.log("row.last : " + row.last + ", row.wastageRate : " + row.wastageRate);
									var last = row.last;
									var wastageRate = row.wastageRate;
									if (wastageRate == null){
										$("#myModal").modal("hide");
										$.eAlert("操作提示", "找不到合同信息,或者在合同信息中没有该计量表对应的计费标准");
										return ;
									}
									last = last == null ? 0 : last ;
									wastageRate = wastageRate == null ? 0 : wastageRate ;
									console.log("last : " + last + ", wastageRate : " + wastageRate);
									$("#addLast").val(last);
									$("#addWastagerate").val(wastageRate);
								}else {
									//$.eAlert("操作提示", "找不到上次抄表记录,或者找不到合同信息,或者在合同信息中没有该计量表对应的计费标准");
								}
							} else {
								$.eAlert("操作提示",data.msg);
							}
						},
						error:function(data){
							$.eAlert("操作提示",data.msg);
			             }
					});
					
				}//getLast
				
		}//initTableEdit
			
	});
}