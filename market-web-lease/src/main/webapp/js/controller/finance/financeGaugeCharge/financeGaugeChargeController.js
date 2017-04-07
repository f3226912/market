//数值格式化为2位小数
function dealFormatResult(formatValue){
	var result = (Math.ceil(formatValue*100)/100).toFixed(2);
	return result;
}
function _call(templateUrl,param){
	$("#main-wrapper").loadPage(templateUrl,
			["css/form-imput.css","css/finance.css","css/parameter.css"],
			["lib/bootstrap-datepicker.js","lib/bootstrap-datetimepicker.js","lib/distpicker.data.js","js/common/pageBar.js","lib/jquery.tmpl.min.js"],
			function(){
		
				//分页默认大小
				var PAGE_SIZE = 50;
				
				/*时间*/
				/*$(".start-date").datepicker({
					format: "yyyy-mm-dd",
					autoclose: true
				  });
				 */
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
					
					//查询当前市场下的所有区域
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
					//查询当前区域下的所有楼栋
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
							PAGE_SIZE = size ;
							$('#templateBody').html(""); // 清空内容
							// 点击回调处理
							var areaId = $("#areaId").val().trim();
							var buildingId = $("#buildingId").val().trim();
							var measureTypeId = $("#measureTypeId").val().trim();
							var status = $("#status").val().trim();
							var noteDateStart = $("#addNoteDateStart").val().trim();
							var noteDateEnd = $("#addNoteDateEnd").val().trim();
							var param = {
									"pageNum":index,"pageSize":PAGE_SIZE,
									"areaId":areaId,
									"buildingId":buildingId,
									"measureTypeId":measureTypeId,
									"status":status,
									"noteDateStart":noteDateStart,
									"noteDateEnd":noteDateEnd
							};
							loadData(param, false);
						}
					});
				}
				
				//加载数据
				function loadData(page, isInit){
					$.ajax({
						url:CONTEXT+"financeGaugeCharge/queryFinanceGaugeChargeList",
						data:page,
						type: "POST",
						dataType:'json',
						success:function(data){
						if(data.success){
							if(isInit){
								initPageBar(data.result);
							}
							$("#templateBody").html("");
							$('#template').tmpl({financeGuageCharges:data.result.recordList}).appendTo('#templateBody');
							//initQueryEvent();
							initCheckBox();
							} else {
								$.eAlert("操作提示",data.msg);
							}
						},
						error:function(data){
							$.eAlert("操作提示",data.msg);
			             }
					});
				}
				
/*				//绑定查询事件
				function initQueryEvent(){
					$("#query").click(function(){
						var areaId = $("#areaId").val().trim();
						var buildingId = $("#buildingId").val().trim();
						var measureTypeId = $("#measureTypeId").val().trim();
						var status = $("#status").val().trim();
						var noteDateStart = $("#addNoteDateStart").val().trim();
						var noteDateEnd = $("#addNoteDateEnd").val().trim();
						condition = {
								"pageNum":1,"pageSize":PAGE_SIZE,
								"areaId":areaId,
								"buildingId":buildingId,
								"measureTypeId":measureTypeId,
								"status":status,
								"noteDateStart":noteDateStart,
								"noteDateEnd":noteDateEnd
						};
						loadData(condition, true);
					});
				};*/
				
			//初使化checkbox
			function initCheckBox(){
				//全选/全不选
				$("#checkboxAll").click(function(){
					if($("#checkboxAll").get(0).checked){
						$(".Echeckbox").prop('checked', true);
						$(".costDefinition #templateBody .ml-ct").addClass("color-eee");
					}else{
						$(".Echeckbox").prop('checked', false);
						$(".costDefinition #templateBody .ml-ct").removeClass("color-eee");
					}
				});
				//单击某个checkbox触发事件
				$(".Echeckbox").click(function(event){
					var box = $(this).get(0) ;
					bol = box.checked;
					if(bol==true){
						$(this).prop('checked', false);
					}else{
						$(this).prop('checked', true);
					}
				});
				
				//单击某行
				$(".costDefinition #templateBody .ml-ct").click(function(){
					var box = $(this).find(".Echeckbox").get(0);
					var bol= box.checked;
					if(bol==true){
						$(this).find(".Echeckbox").prop('checked', false);
						$(this).removeClass("color-eee");
					}else{
						$(this).find(".Echeckbox").prop('checked', true);
						$(this).addClass("color-eee");
					}
					
				});
				
			}
			
			//查询事件
			$("#query").click(function(){
				var areaId = $("#areaId").val().trim();
				var buildingId = $("#buildingId").val().trim();
				var measureTypeId = $("#measureTypeId").val().trim();
				var status = $("#status").val().trim();
				var noteDateStart = $("#addNoteDateStart").val().trim();
				var noteDateEnd = $("#addNoteDateEnd").val().trim();
				condition = {
						"pageNum":1,"pageSize":PAGE_SIZE,
						"areaId":areaId,
						"buildingId":buildingId,
						"measureTypeId":measureTypeId,
						"status":status,
						"noteDateStart":noteDateStart,
						"noteDateEnd":noteDateEnd
				};
				loadData(condition, true);
			});
			
			//默认初始化数据
			loadData({"pageNum":1,"pageSize":PAGE_SIZE}, true);
		
			//绑定收款事件
			$("#financeGuageChargeCheques").click(function(){
				var idChecks = new Array();
				var idArr = new Array();
				$(".Echeckbox").each(function(index){
					if($(this).is(':checked')){
						var id = $(this).attr("data");
						idChecks.push(id);
						if($(this).parents("tr").find(".tdstatus").text().trim()=='未收款'){
							idArr.push(id);
						}
					}
				
				});
				 if(idArr.length < 1 ) {
					 $.eAlert("操作提示","请选择未收款数据!");
						return false;
				    }else{
				    	$.eLoading(true);
				    	var ids = idArr.join(",");
				    	$.ajax({
							url:CONTEXT+"financeGaugeCharge/batchUpdateStatus",
							data:{ids:ids},
							type: "POST",
							dataType:'json',
							success:function(data){
								if(data.success){
									$.eAlert("操作提示","操作成功!");
									//window.location="index#financeGaugeCharge";
									loadData({"pageNum":1,"pageSize":PAGE_SIZE}, true);
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
			
			
			var disableExport = false;
			//绑定导出事件
			$("#financeGuageChargeExport").click(function(){
				var areaId = $("#areaId").val();
				var buildingId = $("#buildingId").val();
				var measureTypeId = $("#measureTypeId").val();
				var status = $("#status").val().trim();
				var noteDateStart = $("#addNoteDateStart").val().trim();
				var noteDateEnd = $("#addNoteDateEnd").val().trim();
				condition = {
						"areaId":areaId,
						"buildingId":buildingId,
						"measureTypeId":measureTypeId,
						"status":status,
						"noteDateStart":noteDateStart,
						"noteDateEnd":noteDateEnd
				};
				var paramList = 'areaId=' + condition.areaId + '&buildingId=' + condition.buildingId
				+ '&measureTypeId=' + condition.measureTypeId+ '&status=' + condition.status
				+ '&noteDateStart=' + condition.noteDateStart+ '&noteDateEnd=' + condition.noteDateEnd;
				if (disableExport) {
					$.eAlert("操作提示","已进行过一次数据导出,导出功能已禁用,请10秒钟过后再点...");	
					return;
				}
				disableExport = true;
				$.ajax({
					url: CONTEXT+'financeGaugeCharge/checkExportParams',
					data : condition,
					type:'post',
					success : function(data){
						//检测通过
						if (data && data.success){
							//启动下载
							$.download(CONTEXT+'financeGaugeCharge/exportData',paramList,'post' );
						}else{
							$.eAlert("操作提示",data.msg);
						}
					},
					error : function(data){
						$.eAlert("操作提示",data.msg);
					}
				});
				// 10秒后导出按钮重新可用
				setInterval(function(){
					disableExport = false;
				}, 10000);
				
			});
			jQuery.download = function(url, data, method) {
				// 获得url和data
				if (url && data) {
					// data 是 string或者 array/object
					data = typeof data == 'string' ? data : jQuery.param(data);
					// 把参数组装成 form的  input
					var inputs = '';
			
					jQuery.each(data.split('&'), function() {
						var pair = this.split('=');
						inputs += '<input type="hidden" name="' + pair[0] + '" value="'
								+ pair[1] + '" />';
					});
					// request发送请求
					jQuery(
							'<form action="' + url + '" method="' + (method || 'post')
									+ '">' + inputs + '</form>').appendTo('body').submit()
							.remove();
				};
			};  
			
	});
}