		/**
		 * 
		 */
function _call(templateUrl,param){
	$("#main-wrapper").loadPage(templateUrl,
			["css/form-imput.css","css/parameter.css","lib/uploadify/uploadify.css"],
			["lib/bootstrap-datepicker.js","lib/bootstrap-datetimepicker.js","lib/distpicker.data.js","js/common/pageBar.js","lib/jquery.tmpl.min.js","lib/uploadify/jquery.uploadify.js"],
			function(){
				//var PAGE_SIZE = 50;
				//var id="";
				//var currentApproveStatus = "0";
				var checked_row;
				//其他费项约定的數組
				var arrayFee=[];
				//查询条件
				var condition = {};
				//初使化
				$("#costItemform").validate();
				conManageColor();
				conManageDelete();
				
				/*function initOtherFeeInfo(otherFeeData){
					$("#templateOthersFeeBody").html("");
					//$('#othersFeeTemplate').tmpl({rows:data.result}).appendTo('#templateOthersFeeBody');
					var mainHtml='';
					var total = "";
					for(var i=0;i<otherFeeData.length;i++){
						if(otherFeeData[i].rentMode=="2"){
							total= '<input type="text" class="rentMode2" required range="[0.00,999999999.00]" value="'+otherFeeData[i].total+'"/>';
						}else{
							total = otherFeeData[i].total;
						}
						var mainHtml2='<tr class="charges-tab-blank" dataOtherFeeId="'+otherFeeData[i].id+'">'
							+			    '<td class="num2">'+(i+1)+'</td>'
							+			    '<td>'+otherFeeData[i].leasingResource+'</td>'
							+			    '<td class="itemCategory">'+otherFeeData[i].itemCategory+'</td>'
							+			    '<td class="itemName">'+otherFeeData[i].itemName+'</td>'
							+			    '<td class="feeInfoCs" darentMode="'+otherFeeData[i].rentMode+'" daChargeUnit="'+otherFeeData[i].chargeUnit+'" daItemNameId="'+otherFeeData[i].itemNameId+'" daItemCategoryId="'+otherFeeData[i].itemCategoryId+'" daFreightBasisId="'+otherFeeData[i].freightBasisId+'">'+otherFeeData[i].freightBasis+'</td>'
							+			    '<td class="rentModeName">'+otherFeeData[i].rentModeName+'</td>'
							+			    '<td class="total">'+total+'</td>'
							+			  '</tr>';
									mainHtml=mainHtml+mainHtml2;
									
					}
					$("#templateOthersFeeBody").html(mainHtml);	
				}
				*/
				//加载数据
				/*function loadData(page, isInit){
					$.ajax({
						url:CONTEXT+"contractManage/showOtherFee/"+id,
						data:page,
						type: "POST",
						dataType:'json',
						success:function(data){
						if(data.success){
						
							$("#templateOthersFeeBody").html("");
							//$('#othersFeeTemplate').tmpl({rows:data.result}).appendTo('#templateOthersFeeBody');
							var mainHtml='';
							var total = "";
							for(var i=0;i<data.result.length;i++){
								if(data.result[i].rentMode=="2"){
									total= '<input type="text" class="rentMode2" required range="[0.00,999999999.00]" value="'+data.result[i].total+'"/>';
								}else{
									total = data.result[i].total;
								}
								var mainHtml2='<tr class="charges-tab-blank" dataOtherFeeId="'+data.result[i].id+'">'
									+			    '<td class="num2">'+(i+1)+'</td>'
									+			    '<td>'+data.result[i].leasingResource+'</td>'
									+			    '<td class="itemCategory">'+data.result[i].itemCategory+'</td>'
									+			    '<td class="itemName">'+data.result[i].itemName+'</td>'
									+			    '<td class="feeInfoCs" darentMode="'+data.result[i].rentMode+'" daChargeUnit="'+data.result[i].chargeUnit+'" daItemNameId="'+data.result[i].itemNameId+'" daItemCategoryId="'+data.result[i].itemCategoryId+'" daFreightBasisId="'+data.result[i].freightBasisId+'">'+data.result[i].freightBasis+'</td>'
									+			    '<td class="rentModeName">'+data.result[i].rentModeName+'</td>'
									+			    '<td class="total">'+total+'</td>'
									+			  '</tr>';
											mainHtml=mainHtml+mainHtml2;
											arrayFee.push(data.result[i].freightBasisId);
							}
							$("#templateOthersFeeBody").html(mainHtml);
							//初使化
							conManageColor();
							conManageDelete(); 
							
						} else {
								$.eAlert("操作提示",data.msg);
							}
						},
						error:function(data){
							$.eAlert("操作提示",data.msg);
			             }
					});
				}*/
	
				//默认初始化数据,如果是待审批和驳回状态的数据，是允许修改的，判断合同的ID是否存在
				//初使化新增和删除按钮,如果为待审批和驳回状态是可以新增和删除合同的数据的
				/*if(currentApproveStatus =='0' || currentApproveStatus =='2'){
					$("#agreementAdd").css("display","none");
					$("#agreementRem").css("display","none");
				}*/
				//如果合同已经存在，则是对信息进行修改，初使化其他费项信息列表
				//initOtherFeeInfo(otherFeeData);
			
			
			/* ---------------------------------------------------- */
				//建筑面积发生变化后，重新计算对应的其它费项的值
			       $("#floorArea").on("input",function(){
			    	   var floorAreaStr= $(this).val().trim();
			    	   var reg = /^[0-9]+.?[0-9]*$/; 
			    	    if (!floorAreaStr || (reg.test(floorAreaStr)&& floorAreaStr.length<12 && parseFloat(floorAreaStr)>=0)) {  
			    	    	   $("#econManageDT2 #templateOthersFeeBody .feeInfoCs").each(function(){
			    	    		   //收租模式名称
			    	   			   var rentMode = $(this).attr("darentMode");
			    	   			   var chargeUnitPrice = $(this).attr("daChargeUnitPrice");
			    	    		   if(rentMode=="3"){
			    	    			   if(!floorAreaStr){
			    	    				   $(this).parent().find(".total").text(0);
			    	    			   }else{
			        				  		//金额
			    			   				var amount="";
			    	   						amount =dealAmount(floorAreaStr*chargeUnitPrice);
			    	   						$(this).parent().find(".total").text(amount);
			    		
			    			    	   }
			    		    	   }
			    	    	   });
			    	    }  
			    	
			      });
			     //可租面积发生变化后，重新计算对应的其它费项的值
			       $("#freeArea").on("input",function(){
			    	   var freeAreaStr= $(this).val().trim();
			    	   var reg = /^[0-9]+.?[0-9]*$/; 
			    	   if (!freeAreaStr || (reg.test(freeAreaStr)&& freeAreaStr.length<12 && parseFloat(freeAreaStr)>=0)) {
				    	   $("#econManageDT2 #templateOthersFeeBody .feeInfoCs").each(function(){
				    	    //收租模式名称
							var rentMode = $(this).attr("darentMode");
							var chargeUnitPrice = $(this).attr("daChargeUnitPrice");
							if(rentMode=="5"){
					    	   if(!freeAreaStr){
					    		   $(this).parent().find(".total").text(0);
					    	   }else{
						    	   	//金额
					   				var amount="";
				   					amount =dealAmount(freeAreaStr*chargeUnitPrice);
				   					$(this).parent().find(".total").text(amount);
					    	   	}
					    	}
				    	 
				    	   });
			    	   }
			      });
			     //套内面积发生变化后，重新计算对应的其它费项的值
			       $("#innerArea").on("input",function(){
			    	   var innerAreaStr= $(this).val().trim();
			    	   var reg = /^[0-9]+.?[0-9]*$/; 
			    	   if (!innerAreaStr || (reg.test(innerAreaStr)&& innerAreaStr.length<12 && parseFloat(innerAreaStr)>=0)) {
				    	   $("#econManageDT2 #templateOthersFeeBody .feeInfoCs").each(function(){
			 		       //收租模式名称
						   var rentMode = $(this).attr("darentMode");
						   var chargeUnitPrice = $(this).attr("daChargeUnitPrice");
						   if(rentMode=="4"){
							  if(!innerAreaStr){
				    			   $(this).parent().find(".total").text(0);
							  }else{
							 	  //金额
								  var amount="";
								  amount =dealAmount(innerAreaStr*chargeUnitPrice);
								  $(this).parent().find(".total").text(amount);
							  }
						  	}
				    	   });
			    	   }
			      });
			//上传
			 $("#contractFile1").uploadify({
				 method:'post',
				 width:250,
			     height:30,
			     swf:'lib/uploadify/uploadify.swf',
			     uploader:'uploadFile',
			     buttonText:'请选择合同附件',
			     multi:true,
			     fileSizeLimit:'10MB',
			     fileTypeDesc:'请选择pdf,jpg,gif,zip,rar文件',
			     fileTypeExts: '*.pdf;*.jpg;*.gif;*.zip;*.rar',
			     fileObjName:'file',
	             removeCompleted : true,
	             overrideEvents: ['onSelectError', 'onDialogClose'],
	             onDialogClose:function(queueData){
	            	 //判断上传的总个数是否大于10个，如果大于，终止当前上传
	            	if((queueData.filesSelected+$("#uploadDiv .contractUpload").length)>10){
	            		$("#contractFile1").uploadify('cancel', '*');
	            		$.eAlert("操作提示","上传总数不能超过10个！");
			 		}
	             },
	             
	             onSelectError:function(file, errorCode, errorMsg){
	                 switch(errorCode) {
	                     case -110:
	                    	 $.eAlert("操作提示","文件 ["+file.name+"] 大小超出系统限制的"+$('#contractFile2').uploadify('settings','fileSizeLimit')+"大小！");
	                         break;
	                     case -130:
	                    	 $.eAlert("操作提示","文件 ["+file.name+"] 类型不正确！");
	                         break;
	                 	}
	                 },
			     onUploadSuccess:function(file,data,response){
			    	 // 由于返回json数据带斜杠，这里需要转换两次才能转为json对象
			    	 var obj = JSON.parse(data);
			    	 var dataObj = JSON.parse(obj); 
			    	 if(dataObj.status == 1){
			    		var uploadHtml ='<span style="width:48%; padding:2px 1%; display:inline-block;" class="contractUpload" dataUrl="'+dataObj.message+'"><a href="'+dataObj.url+'">'+file.name+'</a></span>';
			    		//$("#templateFile1").val(file.name);
			    		//$("#templateUrl1").val(dataObj.message);
			    		//$("#imgId").attr("src",dataObj.url);
			    		 $("#uploadDiv").append(uploadHtml);
						//$.eAlert("提示", "文件上传成功");
			    		
			    	 }else{
						$.eAlert("错误", "文件上传失败");
			    	 }
			     }
			 
			 });
			//封装附件数据信息
			/* function getUploadInfo(){
				 var accessoriesArray = new Array();
				 var fileUrl="";
				 var fileName="";
				 $("#uploadDiv").find(".contractUpload").each(function(){
					 fileUrl= $(this).attr("dataUrl").trim();
					 fileName = $(this).text().trim();
					 var rowAccess={
							 "fileUrl":fileUrl,
							 "fileName":fileName,
							 "isDeleted":0
					 };
					 accessoriesArray.push(rowAccess);
				 });
				 return accessoriesArray;
			 }
			 */
			 
			//选择其他费项
			$("#agreementAdd").on("click",function(){
		
				//租赁资源
				var leasingResource = $("#leasingResource").val().trim();
				if(!leasingResource){
					$.eAlert("操作提示","请先选择合同的租赁资源!");
					return;
				}
				arrayFee=[];
				$("#templateOthersFeeBody .feeInfoCs").each(function(){
					arrayFee.push($(this).attr("daitemnameid"));
				});
				$("#agreementAddPop").modal();
				loadFeeItemAndStandards({});
			});
			$("#agreementAddPop").popwin({
				titleText: "选择其他费项", //弹出框名
				popwidth:"900",//设置宽度
				btnText:["取消","确认"],//按钮的文本
				drag:true,//是否拖动
				//formValid: "", //表单验证方法
				popUrl: null,
				callback: function(){
					var isFlag=true;
					//$("#agreementAddPop").modal('hide');
					var efristSelect=$("#efristSelect").find("option:selected").text();
					var mainHtml3='';
					//租赁资源
					var leasingResource = $("#leasingResource").val().trim();
					$(".Einput-m").each(function(){
						if($(this).is(':checked')){
							//费项类别
							var itemCategory=$(this).parent().parent().parent().find(".ecm-tital").text();
							
							var $option = $(this).parent().parent().find(".guarantee-select").find("option:selected");
							//计费标准
							var freightBasis=$option.text();
							//计费标准ID
							var freightBasisId=$option.val();
							if(!freightBasisId){
								isFlag = false;
							}
							//费项类别ID
							var itemCategoryId = $option.attr("dataItemCategoryId");
							//收租模式
							var rentMode=$option.attr("datarentMode");
							//计费标准表里的收费金额
							var chargeAmount =$option.attr("dataChargeAmount");
							//计费单价
							var chargeUnitPrice = $option.attr("datachargeUnitPrice");
							//计费单位
							var chargeUnit = $option.attr("dataChargeUnit");
							//收租模式名称
							var rentModeName = $option.attr("dataRentModeName");
							//金额
							var amount="";
							if(rentMode=="1"){
								amount = dealAmount(chargeAmount);
							}else if(rentMode=="2"){
								amount = '<input type="text" class="rentMode2" name="total'+freightBasisId+'" required range="[0.00,999999999.00]"/>';
							}else if(rentMode=="3"){
								amount =dealAmount($("#floorArea").val().trim()*chargeUnitPrice);
							}else if(rentMode=="4"){
								amount =dealAmount($("#innerArea").val().trim()*chargeUnitPrice);
							}else if(rentMode=="5"){
								amount =dealAmount($("#freeArea").val().trim()*chargeUnitPrice);
							}else{
								amount = "";
							}
							//如果是走表类，不显示计算方法和金额，在走表类抄表处单独处理
							if(itemCategoryId=="2"){
								rentModeName="";
								amount="";
							}
							var itemName=$(this).parent().find(".checkbox-em").text().trim();
							var itemNameId=$(this).parent().find(".checkbox-em").attr("dataItemNameId").trim();
							var mainHtml4='<tr class="charges-tab-blank">'
								
					+			    '<td class="num2"></td>'
					+			    '<td class="leasingResourceOtherFee" title="'+leasingResource+'" style="width:250px; display: block; line-height:38px; overflow: hidden; white-space:nowrap; text-overflow:ellipsis;">'+leasingResource+'</td>'
					+			    '<td class="itemCategory">'+itemCategory+'</td>'
					+			    '<td class="itemName">'+itemName+'</td>'
					+			    '<td class="feeInfoCs" daChargeUnitPrice="'+chargeUnitPrice+'" darentMode="'+rentMode+'" daChargeUnit="'+chargeUnit+'" daItemNameId="'+itemNameId+'" daItemCategoryId="'+itemCategoryId+'" daFreightBasisId="'+freightBasisId+'">'+freightBasis+'</td>'
					+			    '<td class="rentModeName">'+rentModeName+'</td>'
					+			    '<td class="total">'+amount+'</td>'
					+			  '</tr>';
//							arrayFee.push(itemNameId);
							mainHtml3=mainHtml3+mainHtml4;
						}
					});
					if(isFlag){
						$("#agreementAddPop").modal('hide');
						$("#econManageDT2 #templateOthersFeeBody").append(mainHtml3);
						conManageColor();
						contentLi2();
						$("#costItemform").validate();
					}else{
					
						$.eAlert("操作提示","计费标准不能为空!");
					}
					
				}
			});

			/*function getOtherFeeData(){
				var otherFeeArray = new Array();
				var contractId = 1;
				var contractNo="001";
				var leasingResource=$("#leasingResource").val().trim();
				
				$("#econManageDT2 #templateOthersFeeBody .feeInfoCs").each(function(){
					var itemCategoryId=$(this).attr("daItemCategoryId"),
						freightBasisId=$(this).attr("daFreightBasisId");
						itemNameId=$(this).attr("daItemNameId");
						chargeUnitPrice=$(this).attr("daChargeUnitPrice");
						chargeUnit=$(this).attr("daChargeUnit");
						freightBasis = $(this).text();
						rentMode=$(this).attr("darentMode");
					var itemCategory=$(this).parent().find(".itemCategory").text();
					var itemName=$(this).parent().find(".itemName").text();
					//var rentMode=$(this).parent().find(".rentMode").text();
					var total=$(this).parent().find(".total").text();
					if(rentMode=="2"){
						total = $(this).parent().find(".rentMode2").val().trim();
					}
					var otherFeerow={
							"contractId":contractId,
							"contractNo":contractNo,
							"leasingResource":leasingResource,
							"itemCategory":itemCategory,
							"itemCategoryId":itemCategoryId,
							"itemName":itemName,
							"itemNameId":itemNameId,
							"freightBasis":freightBasis,
							"freightBasisId":freightBasisId,
							"rentMode":rentMode,
							"chargeUnitPrice":chargeUnitPrice,
							"chargeUnit":chargeUnit,
							"total":total,
							"isDeleted":0
					};
					otherFeeArray.push(otherFeerow);
				});
				return otherFeeArray;
			}
			*/
			
			function dealAmount(formatValue){
				 return (Math.ceil(formatValue*100)/100).toFixed(2);
			}
			function contentLi2(){
				var num=$(".num2"),
					tNum=1;
				num.each(function(){
					$(this).html(tNum);
					tNum++;
				});
			}
			
			function conManageDelete(){
				$("#agreementRem").click(function(){
					if(checked_row == undefined){
						$.eAlert("提示信息", "请选择其他费项约定记录!");
						return;
					}
					checked_row = undefined;
					$(".color-eee").remove();
					contentLi2();
				});
			};
			
			
			function conManageColor(){
				$("#econManageDT2 .charges-tab-blank").click(function(){
					$("#econManageDT2 .charges-tab-blank").removeClass("color-eee");
					$(this).addClass("color-eee");
					contentLi2();
					checked_row = this.rowIndex - 1; 
				});
			}
			conManageColor();

			//加载费项、计费标准数据
			function loadFeeItemAndStandards(params){
				$.getJSON(CONTEXT+"expenditure/listExpenditure",params,function(data){
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
				for (var key1 in records){
					//console.log(JSON.stringify(records))
					var temp = records[key1];
					var $bigDiv = $('<div class="fees-name-box"></div>');
					var $p = $('<p class="fee-items ecm-tital">'+key1+'</p>');
					
					for(var key in temp){
					
					var $div = $('<div class="fees-checkbox-box"></div>');
					var $label = $('<label for="checkbox-item' + temp[key].id + '"></label>');
					var $input = $('<input type="checkbox" id="checkbox-item' + temp[key].id + '" arr="' + temp[key].id + '" name="tempItemCheckbox" class="checkbox-none Einput-m" />');
					$input.on("click",function(){
						if ($(this).is(":checked")){
							//费项id
							//$("#feeItemIdBack").val($(this).attr("rid"));
							//费项名称
							//$("#feeItemNameBack").val($(this).attr("rname"));
							//计费标准id
							//var standardValue = $('#standard' + $(this).attr("rid")).val() ;
							//$("#freightBasisIdBack").val(standardValue);
							//计费标准名称
							//var standardText = $('#standard' + $(this).attr("rid")).text() ;
						}
					});
					$label.append($input);
					
					var $titileSpan = $('<span class="checkbox-layer"></span><em class="checkbox-em" title="'+ temp[key].name +'" dataItemNameId="'+temp[key].id+'">' + temp[key].name + '</em>');
					$label.append($titileSpan);
					$div.append($label);
					var $contentSpan = $('<span class="span-select-down"></span>');
					
					var $select = $('<select id="standard' + temp[key].id + '" class="guarantee-select"></select>');
					var standards = temp[key].expStandards;
					var standardName="";
					var chargeUnitPrice = "";
					for (var skey in standards) {
						standardName = standards[skey].name;
						chargeUnitPrice =standards[skey].chargeUnitPrice;
						//if(temp[key].expType=="1"){
						//	standardName=standardName+"("+chargeUnitPrice+standards[skey].chargeUnitName+")";
						//}
						var $option = $('<option dataRentModeName="'+standards[skey].rentModeName+'" dataChargeUnit="'+standards[skey].chargeUnit+'" dataItemCategoryId="'+temp[key].expType+'" datachargeUnitPrice="'
								+chargeUnitPrice+'" dataChargeAmount="'+standards[skey].chargeAmount+'" datarentMode="'
								+ standards[skey].rentMode+'" value="'+ standards[skey].id +'">'+ standardName +'</option>');
						$select.append($option);
					}
					$contentSpan.append($select);
					
					$div.append($contentSpan);
					$bigDiv.append($div);
					}
					
					$bigDiv.prepend($p);
					$('#tempHeader').after($bigDiv);
					/*$('#tempHeader').after($bigDiv);*/
					
				}
				
				$.each(arrayFee,function(index){
				    var eId=this;
				    $(".contract .fees-checkbox-box input").each(function(){
				    	if($(this).attr("arr")==eId){
				    		$(this).attr("disabled","disabled");
				    		$(this).parent().find(".checkbox-layer").css("background-color","#bfbfbf")
				    	}
				    })
				});
			}
			});
		} 