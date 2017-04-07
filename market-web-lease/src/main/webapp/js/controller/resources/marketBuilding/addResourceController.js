function _call(templateUrl, param) {
	$("#main-wrapper")
			.loadPage(
					templateUrl,
					["css/resourMage.css" ],
					[ "lib/jquery-migrate.js", "js/common/pageBar.js","lib/jquery.tmpl.min.js" ],
					function() {
						var params = Route.params;// 获取页面传值对象
						var builCode = params.builCode;// 取值方式：value=对象.key
						var builName = params.builName;
						var builId=params.builId;
						var areaId=params.areaId;
						$("#builCode").val(builCode);
						$("#buildName").val(builName);
						$(".step-table").on("click",".step-icon",function() {
									var $this = $(this);
									$this.addClass("active-step-icon");
									var inputVal = $this.prev().prev().val();
									var tdIndex = $this.parents("td").index();
									console.log("tdIndex: " + tdIndex);
									$this.parents("tr").nextAll("tr").find("td:eq(" + tdIndex + ") input").val(inputVal);
								})
				
			// 第一步文本框检验规则
			function layerVaid(obj){
					if($(obj).val()==""){
						$(obj).next().html("必填");
						$(obj).addClass("error");
						$(obj).next("label").addClass("error");
						return false;
						}else if(!/^[0-9]*$/.test($(obj).val())){
							$(obj).addClass("error");
							$(obj).next("label").addClass("error");
							$(obj).next().html("输入0到20的数值");
							$(obj).focus();
							$(obj).val("");
							return false;
						}else if($(obj).val()<0 || $(obj).val()>20){
							$(obj).addClass("error");
							$(obj).next("label").addClass("error");
							$(obj).next().html("输入0到20的数值");
							$(obj).focus();
							$(obj).val("");
							return false;
						}else {
							$(obj).next().html("");
							$(obj).removeClass("error");
							$(obj).next("label").removeClass("error");
							return true;
						}						
					}				
						var oldUnit, oldBuild;
						// 第一步点击下一步
						$("#step1Next").click(function(){
								var unitCount = $("#unitCount").val();// 单元总数
								var buildingCount = $("#buildingCount").val();// 楼层总数
								/* //无法理解此处是和逻辑，第二次的时候，单元和楼层数量为空的时候，会直接通过
								 if(oldUnit == unitCount && oldBuild == buildingCount){
									$(this).parents(".contract-info").addClass("hidden");
									$(this).parents(".contract-info").next(".contract-info").removeClass("hidden");
									return;
								}
								oldUnit = unitCount;
								oldBuild = buildingCount;*/
								var buildingArray = new Array();
								var unitArray = new Array();
								for (var i = 0; i < unitCount; i++) {
									unitArray[i] = i + 1;
								}
								var k=0;
								for (var j = buildingCount; j > 0; j--) {
									buildingArray[k] = j  ;
									k++;
								}
								$('#templateHead').empty();
								$('#templateBody').empty();
								$('#template02Tit').tmpl({unitCount : unitArray,buildingCount : buildingArray }).appendTo('#templateHead');// 第2步 列表
								$('#template').tmpl({unitCount : unitArray,buildingCount : buildingArray}).appendTo('#templateBody');// 第2步 列表
								if(layerVaid(".layerNum") && layerVaid(".unitNum")){
									//canvas斜线
									var c = document.getElementById("myCanvas");
									var cxt = c.getContext("2d");
									cxt.beginPath();
									cxt.moveTo(0, 0);
									cxt.strokeStyle="#eee";
									cxt.lineTo(100, 100);
									cxt.stroke();
									$(this).parents(".contract-info").addClass("hidden");
									$(this).parents(".contract-info").next(".contract-info").removeClass("hidden");
								}
						});

						// 第二步点击上一步
						$("#step2Prev").click(function() {
											var buildingName = $("#buildingName").val();
											$(this).parents(".contract-info").addClass("hidden");
											$(this).parents(".contract-info").prev(".contract-info").removeClass("hidden");
											$("#contTableBody").find("td").empty();});

						// 第二步点击下一步
						$("#step2Next").click(function() {
											// 加载上一步楼栋名称
											var bulidingAry = [];
											$("input[name='building']").each(function() {bulidingAry.push($(this).val());});
											// 加载上一步的单元名称
											var unitAry = [];
											$("input[name='unitName']").each(
													function() {unitAry.push($(this).val());});
											$('#contTableBody').empty();
											$("#rule").val("2");
											// 加载上一步的户数 内容你、列表 //k楼层，i单元 ,j房间序号，layerVal 单元中的房间数  roomCount 
											var i, j, k ;
											var floor = $("#templateBody tr").length-1;
											for (k = 0; k < $("#templateBody tr").length; k++) {
												var layerVal , roomCount=0;
												var roomNo=1,room;
												$('<tr></tr>').appendTo($("#contTableBody"));
//												for (i = 0; i < $("#templateBody tr:eq(0) td").length - 1; i++) {
//													layerVal = $("#templateBody tr:eq("+ (k)+ ") td:eq("+ (i + 1)+ ") input").val();
//													roomCount += Number(layerVal);
//												}
												for (i = 0; i < $("#templateBody tr:eq(0) td").length - 1; i++) {
													$('<td nowrap="nowrap"></td>').appendTo($("#contTableBody tr:eq("+ k+ ")"));
													layerVal = $("#templateBody tr:eq("+ (k)+ ") td:eq("+ (i + 1)+ ") input").val();
													for (j = 0; j < layerVal; j++) {
														var room=(floor+1)+ formatStr(j+1,2);
														$('<input class="layer-room" data-rooms="'+ (k+1) + '-'+ (i+1)+ '" value="'+room+'" name="resources['+j+'].code"><span class="error-tips"></span>')
																.appendTo($("#contTableBody tr:eq("+ k+ ") td:eq("+ i+ ")"))
													}
												}
												floor--;
											}
											// 返回上一步 再点击下一步的时候 清空数据
											$('#titTableBody').empty();
											$('#contUnitBody').empty();
											$('#templateTit').tmpl({buildingNames : bulidingAry}).appendTo('#titTableBody');// 第3步
											// 楼层列表
											$('#templateUit').tmpl({unitNames : unitAry}).appendTo('#contUnitBody');// 第3步
										
											if(validateUnit() && validateBuilding() && validateHouseholdsNum()){
												$(this).parents(".contract-info").addClass("hidden");
												$(this).parents(".contract-info").next(".contract-info").removeClass("hidden");
											}
											
										});
				$("#templateBody").on("blur","input[name='householdsNum']",function(){
						if($(this).val()==""){
							$(this).next().html("必填");
							$(this).addClass("error");
						}else if(!/^[0-9]*$/.test($(this).val())){
							$(this).next().html("输入0到500的数值");
							$(this).focus();
							$(this).val("");
						}else if($(this).val()<0 || $(this).val()>500){
							$(this).next().html("输入0到500的数值");
							$(this).focus();
							$(this).val("");
						}else{
							$(this).next().html("");
							$(this).removeClass("error");
						}
					})
						 //资源生成规则
						$("#rule").change(function(){changeRoomNo()});
						$("#formatNo").change(function(){changeRoomNo()});
						
						$("#step3Prev").on("click",function() {
									$(this).parents(".contract-info").addClass("hidden");
									$(this).parents(".contract-info").prev(".contract-info").removeClass("hidden");
									$("#contTableBody").find("td").empty()
								});
						// 点击确认
						$("#confirm").on("click",function() {
							
							if(!validateRoomNo()){
								return;
							}
							$.eConfirm("提示", "是否立即生成租赁资源？",function() {
								$.eLoading(true);
								var resource = [];
								$('.layer-room').each(function () {  
									  	var oldVal=$(this).val();
									  	var number=$(this).data("rooms");
									  	var house=number+'-'+oldVal;
									  	resource.push(house);
							     });
								var buildings = [];
								$("input[name='builName']").each(function () {  
								  	buildings.push($(this).val());
								});
								var units = [];
								$("input[name='unitNameTree']").each(function () {  
									units.push($(this).val());
								});
								
								var condtion = {
										"areaId":areaId,
										"resource":resource,
										"buildings":buildings,
										"units":units,
										"builId":builId,
										"builName":builName,
										"builCode":builCode
								};
								$.ajax({
									url : CONTEXT + 'marketBuilding/volumeProduction',
									data : condtion,
									type : 'post',
									traditional : true,
									success : function(data) {
										if (data.success) {
											$.eAlert("提示信息",data.msg);
											window.location="index#marketBuilding";
										} else {
											$.eAlert("提示信息",data.msg);
										}
										
									},
									error : function(data) {
										$.eAlert("提示信息",data.msg);
									}
								});
								$.eLoading(false);
							})
										});
						// 第3步确认 取消
						$("#cancel").click(function() {
							location.href = 'index#marketBuilding';
						});
						//第2步
						$("#step2NextCancel").click(function() {
							location.href = 'index#marketBuilding';
						});
						//第1步
						$("#step1NextCancel").click(function() {
							location.href = 'index#marketBuilding';
						});
						
						
						function validateHouseholdsNum(){
							var index=0, flag=true;
							$("input[name='householdsNum']").each(function () {  
								var inputVal;
								obj = $(this);
								if(obj.val()==""){
									obj.next().html("必填");
									obj.addClass("error");
									flag = false;
								}else if(!/^[0-9]*$/.test(obj.val())){
									obj.next().html("输入0到500的数值");
									obj.focus();
									obj.val("");
									flag = false;
								}else if(obj.val()<0 || obj.val()>500){
									obj.next().html("输入0到500的数值");
									obj.focus();
									obj.val("");
									flag = false;
								}else{
									obj.next().html("");
								}
							});
							return flag;
						}
						
						function validateUnit(){
							var index=0, flag=true;
							$("input[name='unitName']").each(function () {  
								if($(this).val() == ""){
								    $(this).focus()
//									$.eAlert("提示","单元名字不能为空！")
									$(this).next().html("必填");
								    $(this).addClass("error");
									flag=false;
							    }else{
							    	$(this).next().html("");
								    $(this).removeClass("error");
							    }
								if(flag){
									var inputVal,n,inputLen;
									inputVal = $(this).val();
									inputLen = $("input[name='unitName']").length;
									for(n = 0;n<inputLen;n++){
										if(index !=n && inputVal == $("input[name='unitName']").eq(n).val()){
												$(this).next().html("单元名字重复");
											    $(this).addClass("error");
												flag=false;
										}else{
									    	$(this).next().html("");
										    $(this).removeClass("error");
									    }
									}
									index++;
								}
							});
							return flag;
						}
						
						function validateBuilding(){
							var index=0, flag=true;
							$("input[name='building']").each(function () {  
								if($(this).val() == ""){
								    $(this).focus()
									$(this).next().html("必填");
								    $(this).addClass("error");
									flag=false;
							    }else{
							    	$(this).next().html("");
								    $(this).removeClass("error");
							    }
								if(flag){
									var inputVal,n,inputLen;
									inputVal = $(this).val();
									inputLen = $("input[name='building']").length;
									for(n = 0;n<inputLen;n++){
										if(index !=n && inputVal == $("input[name='building']").eq(n).val()){
												$(this).next().html("楼层名字重复");
											    $(this).addClass("error");
												flag=false;
										}else{
									    	$(this).next().html("");
										    $(this).removeClass("error");
									    }
									}
									index++;
								}
							});
							return flag;
						}
						/**
						 * 校验资源名字不能重复
						 * 
						 * */
						function validateRoomNo(){
							var rule = $("#rule");
							var flag = true;
							$(".layer-room").each(function(index){
								var a = $(this).data("rooms");
								var row = a.split("-")[0]; 
								var unit = a.split("-")[1]; 
								var inputVal,n,inputLen;
								inputVal = $(this).val();
								inputLen =$(".layer-room").length;
								for(n = 0;n<inputLen;n++){
									var b =  $(".layer-room").eq(n);
									var brow = b.data("rooms").split("-")[0]; 
									var bunit = b.data("rooms").split("-")[1]; 
									if (rule.val() == 1 || rule.val() == 2) {//校验规则3，当前单元
										if(index !=n && row == brow && unit==bunit  && inputVal == b.val()){
//								    	    $.eAlert("提示信息","资源名字存在！请重新命名");
//											$(this).val("");
											$(this).next().html("资源名字重复");
										    $(this).addClass("error");
											flag=false;
										}
									}else if(rule.val() == 3){//校验规则3，当前楼层
										if(index !=n && row == brow  && inputVal == b.val()){
//								    	    $.eAlert("提示信息","资源名字存在！请重新命名");
//											$(this).val("");
											$(this).next().html("资源名字重复");
										    $(this).addClass("error");
											flag=false;
										}
									}
								}
								if(flag){
							    	$(this).next().html("");
								    $(this).removeClass("error");
							    }
							});
							return flag;
						}
						
						
						
						function changeRoomNo(){
							var rule = $("#rule");
							var formatNo = $("#formatNo").val();
							if(rule.val()==1){
								$('#contTableBody').empty();
								// 加载上一步的户数 内容你、列表    //k楼层，j单元
								var i, j, k, layerVal;
								for (k = 0; k < $("#templateBody tr").length; k++) {
									$('<tr></tr>').appendTo($("#contTableBody"))
									for (i = 0; i < $("#templateBody tr:eq(0) td").length - 1; i++) {
										$('<td nowrap="nowrap"></td>').appendTo($("#contTableBody tr:eq("+ k+ ")"));
										layerVal = $("#templateBody tr:eq("+ (k)+ ") td:eq("+ (i + 1)+ ") input").val();
										console.log("layerVal"+ layerVal)
										for (j = 0; j < layerVal; j++) {
											var room=formatStr(j+1,formatNo);
											$('<input class="layer-room" data-rooms="'+ (k+1) + '-'+ (i+1)+ '" value="'+room+'" name="resources['+j+'].code"><span class="error-tips"></span>')
													.appendTo($("#contTableBody tr:eq("+ k+ ") td:eq("+ i+ ")"))
										}
									}
								}
							}else if(rule.val()==2){
								$('#contTableBody').empty();
								// 加载上一步的户数 内容你、列表 //k楼层，j单元
								var i, j, k, layerVal;
								var floor = $("#templateBody tr").length-1;
								for (k = 0; k < $("#templateBody tr").length; k++) {
									var layerVal , roomCount=0;
									$('<tr></tr>').appendTo($("#contTableBody"));
//									for (i = 0; i < $("#templateBody tr:eq(0) td").length - 1; i++) {
//										layerVal = $("#templateBody tr:eq("+ (k)+ ") td:eq("+ (i + 1)+ ") input").val();
//										roomCount += Number(layerVal);
//									}
									for (i = 0; i < $("#templateBody tr:eq(0) td").length - 1; i++) {
										$('<td nowrap="nowrap"></td>').appendTo($("#contTableBody tr:eq("+ k+ ")"));
										layerVal = $("#templateBody tr:eq("+ (k)+ ") td:eq("+ (i + 1)+ ") input").val();
										console.log("layerVal"+ layerVal)
										for (j = 0; j < layerVal; j++) {
											var room=(floor+1)+ formatStr(j+1,formatNo);
											$('<input class="layer-room" data-rooms="'+ (k+1) + '-'+ (i+1)+ '" value="'+room+'" name="resources['+j+'].code"><span class="error-tips"></span>')
													.appendTo($("#contTableBody tr:eq("+ k+ ") td:eq("+ i+ ")"))
										}
									}
									floor--;
								}
							}else{
								$('#contTableBody').empty();
								// 加载上一步的户数 内容你、列表 //k楼层，i单元 ,j房间序号，layerVal 单元中的房间数  roomCount 
								var i, j, k ;
								var floor = $("#templateBody tr").length-1;
								for (k = 0; k < $("#templateBody tr").length; k++) {
									var layerVal , roomCount=0;
									var roomNo=1,room;
									$('<tr></tr>').appendTo($("#contTableBody"));
//									for (i = 0; i < $("#templateBody tr:eq(0) td").length - 1; i++) {
//										layerVal = $("#templateBody tr:eq("+ (k)+ ") td:eq("+ (i + 1)+ ") input").val();
//										roomCount += Number(layerVal);
//									}
									for (i = 0; i < $("#templateBody tr:eq(0) td").length - 1; i++) {
										$('<td nowrap="nowrap"></td>').appendTo($("#contTableBody tr:eq("+ k+ ")"));
										layerVal = $("#templateBody tr:eq("+ (k)+ ") td:eq("+ (i + 1)+ ") input").val();
										for (j = 0; j < layerVal; j++) {
											room=(floor+1)+(formatStr(roomNo++,formatNo));
											$('<input class="layer-room" data-rooms="'+ (k+1) + '-'+ (i+1)+ '" value="'+room+'" name="resources['+j+'].code"><span class="error-tips"></span>')
													.appendTo($("#contTableBody tr:eq("+ k+ ") td:eq("+ i+ ")"))
										}
									}
									floor--;
								}
							}
						
						}
						
						/**
						 * 根据v2的位数，格式化v1
						 * 
						 * */
						function formatStr(v1,v2){
							if(v2 == 2 ){
								if(v1<10){
									return '0'+v1;
								}else {
								    return ''+v1 	
								}
							}else if (v2 == 3){
								if(v1 < 10){
									return '00'+v1;
								}else if(v1 < 100){
									return '0'+v1;
								}else {
								    return ''+v1 	
								}
							}else {
								return ''+v1;
							}
						}
					});

}