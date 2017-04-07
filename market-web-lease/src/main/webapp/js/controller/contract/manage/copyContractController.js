function _call(templateUrl,param){
	$("#main-wrapper").loadPage(templateUrl,
			["css/form-imput.css","css/contract.css","css/parameter.css","lib/uploadify/uploadify.css"],
			["lib/bootstrap-datepicker.js","lib/bootstrap-datetimepicker.js","lib/jquery.tmpl.min.js","lib/uploadify/jquery.uploadify.js"],
	
	function(){
		//加载刘凡写的页面
		//$.loadRouteModal("constItem", "#constItem");
		
        var params=Route.params;
        var contractId, contractInfo;
        if(params){
        	//合同ID
        	contractId = params.id;
        }else{
        	location.href = "index#contractManage"
    		return;
        }
        var checked_row;
		//其他费项约定的數組
		var arrayFee=[];
		//查询条件
		var condition = {};
		//初使化
		$("#costItemform").validate();
		conManageColor();
		conManageDelete();
		resourceIds = [];
		resourceNames = [];
        //加载合同详情数据
        //$("#click_btn").click(function(){
    	getContractInfo(contractId);
        //})
    	
        
    	function getContractInfo(contractId){
    		$.ajax({
    	          type: "POST",
    	          url: CONTEXT+"contractManage/contractInfo",
    	          dataType: "json",
    	          async:false,
    	          data:{id : contractId},
    	          success: function(data){
    	         	 if(data.success){
    						var res = data.result;
    						contractInfo = data.result;
    						
    						//加载主营分类
    						getCategory(data.result.mainDTO.categoryId);
    						//设置合同基本信息
    						setMainDto(data.result.mainDTO);
    						//租赁约定
    						setLeaseList(data.result.leaseList);
    						//免租约定
    						setFreeList(data.result.freeList);
    						//按约定总金额-缴费约定记录
    						setPaymentList(data.result.paymentList);
    						
							 //初使化其它费项记录
							 initOtherFeeInfo(data.result.othersFeeList);
							 //初使化附件信息
							 initAccessoriesInfo(data.result.accessoriesList,data.result.fileCompleteUrl);
							 //设置按钮新增删除是否可用
							 //setButtonStatus(data.result.mainDTO.approvalStatus);
    					} else {
    						$.eAlert("提示信息", data.msg);
    					}
    	          }
			});
    	}
        
        /**
    	 * 其它费项信息
    	 */
        function initOtherFeeInfo(otherFeeData){
			$("#templateOthersFeeBody").html("");
			//$('#othersFeeTemplate').tmpl({rows:data.result}).appendTo('#templateOthersFeeBody');
			var mainHtml='';
			var total = "";
			var rentModeName = "";
			for(var i=0;i<otherFeeData.length;i++){
				if(otherFeeData[i].rentMode=="2"){
					total= '<input type="text" class="rentMode2" name="total'+otherFeeData[i].freightBasisId+'" required range="[0.00,999999999.00]" value="'+dealAmount(otherFeeData[i].total)+'"/>';
				}else{
					total = dealAmount(otherFeeData[i].total);
				}
				rentModeName = otherFeeData[i].rentModeName;
				//如果是走表类，计算方法和金额不显示
				if(otherFeeData[i].itemCategoryId=="2"){
					total="";
					rentModeName="";
				}
				var mainHtml2='<tr class="charges-tab-blank" dataOtherFeeId="'+otherFeeData[i].id+'">'
					+			    '<td class="num2">'+(i+1)+'</td>'
					+			    '<td class="leasingResourceOtherFee" title="'+otherFeeData[i].leasingResource+'" style="width:250px; display: block; line-height:38px; overflow: hidden; white-space:nowrap; text-overflow:ellipsis;">'+otherFeeData[i].leasingResource+'</td>'
					+			    '<td class="itemCategory">'+otherFeeData[i].itemCategory+'</td>'
					+			    '<td class="itemName">'+otherFeeData[i].itemName+'</td>'
					+			    '<td class="feeInfoCs" daChargeUnitPrice="'+otherFeeData[i].chargeUnitPrice+'" darentMode="'+otherFeeData[i].rentMode+'" daChargeUnit="'+otherFeeData[i].chargeUnit+'" daItemNameId="'+otherFeeData[i].itemNameId+'" daItemCategoryId="'+otherFeeData[i].itemCategoryId+'" daFreightBasisId="'+otherFeeData[i].freightBasisId+'">'+otherFeeData[i].freightBasis+'</td>'
					+			    '<td class="rentModeName">'+rentModeName+'</td>'
					+			    '<td class="total">'+total+'</td>'
					+			  '</tr>';
							mainHtml=mainHtml+mainHtml2;
							/*arrayFee.push(data.result[i].itemNameId);*/
			}
			$("#templateOthersFeeBody").html(mainHtml);	
		}
        
		/**
		 * 附件信息
		 */
        function initAccessoriesInfo(accessoriesData,fileCompleteUrl){
        	var accessoriesHtml = "";
        	for(var i=0;i<accessoriesData.length;i++){
        		accessoriesHtml += '<span style="width:48%; padding:2px 1%; display:inline-block;" class="contractUpload" dataUrl="'+accessoriesData[i].fileUrl+'"><a href="'+fileCompleteUrl+accessoriesData[i].fileUrl+'">'+accessoriesData[i].fileName+'</a></span>';
        	}
        	 $("#uploadDiv").append(accessoriesHtml);
        }
        function setButtonStatus(approvalStatus){
	        //默认初始化数据,如果是待审批和驳回状态的数据，是允许修改的，判断合同的ID是否存在
			//初使化新增和删除按钮,如果为待审批和驳回状态是可以新增和删除合同的数据的
			if(approvalStatus =='0' || approvalStatus =='2'){
				$("#agreementAdd").css("display","block");
				$("#agreementRem").css("display","block");
			}else{
				$("#agreementAdd").css("display","none");
				$("#agreementRem").css("display","none");
			}
    	}
    	
    	/**
    	 * 设置合同基本信息
    	 */
    	function setMainDto(obj){
			$("#leasingResource").val(obj.leasingResource);
			//$("#leasingResource_hidden").val(obj.leasingResource);
			$("#leasingResourceIds").val(obj.leasingResourceIds);
			var ids = obj.leasingResourceIds.split(",");
			for(var i in ids){
				resourceIds.push(ids[i]);
			}
			var names = obj.leasingResource.split(",");
			for(var i in names){
				resourceNames.push(names[i]);
			}
    		$("input[name='customerName']").val(obj.customerName);
    		$("input[name='partyA']").val(obj.partyA);
    		$("input[name='partyB']").val(obj.partyB);
    		$("input[name='startLeasingDay']").val((obj.startLeasingDay+"").substring(0,10));
    		$("input[name='endLeasingDay']").val((obj.endLeasingDay+"").substring(0,10));
    		$("input[name='leasingForfeit']").val(obj.leasingForfeit);
    		$("input[name='shopForfeit']").val(obj.shopForfeit);
    		$("input[name='shopName']").val(obj.shopName);
    		$("input[name='customerMobile']").val(obj.customerMobile);
    		$("input[name='trustees']").val(obj.trustees);
    		$("input[name='dateOfContract']").val(obj.dateOfContract.substring(0,10));
    		$("#contractNo").val(obj.contractNo);
    		$("#freeArea").val(obj.freeArea);
    		$("#floorArea").val(obj.floorArea);
    		$("#innerArea").val(obj.innerArea);
    		$("#totalAmt").val(obj.totalAmt);
    		$("#additionalTerms").val(obj.additionalTerms);
    		$(":radio[name='billingArea'][value='" + obj.billingArea + "']").prop("checked", "checked");
    		$(":radio[name='chargingWays'][value='" + obj.chargingWays + "']").prop("checked", "checked");
    		$(":radio[name='paymentCycle'][value='" + obj.paymentCycle + "']").prop("checked", "checked");
    		$(":radio[name='paymentDayType'][value='" + obj.paymentDayType + "']").prop("checked", "checked");
    		$(":radio[name='paymentDayType'][value='" + obj.paymentDayType + "']").parent().find("input[name='paymentDays']").val(obj.paymentDays);
    		if(obj.chargingWays == 0){
    			$('.contract-rents').hide();
    	      	$('.billing_area').show()
    		}else{
    			$('.contract-rents').show();
    	      	$('.billing_area').hide()
    		}
    	}
    	
    	/**
    	 * 租赁约定
    	 */
    	function setLeaseList(list){
    		for(var i in list){
    			addRos_edit(i, list[i]);
    			//addChargesColor();
    		}
    	}
    	
    	/**
    	 * 免租约定
    	 */
    	function setFreeList(list){
    		for(var i in list){
    			leaseDad_edit(i, list[i]);
    			//addLeaseColors();
    		}
    	}
    	
    	/**
    	 * 按约定总金额-缴费约定记录
    	 */
    	function setPaymentList(list){
    		for(var i in list){
    			paymentAdd_edit(i, list[i]);
    			//addPaymentColors();
    		}
    	}
    	
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
		//发起审批
		$(".approval-s").on("click",function(){
			$("#popwintmpl").modal()
		})
		$("#popwintmpl").popwin({
			titleText: "发起审批", //弹出框名
			popwidth:"900",//设置宽度
			btnText:["取消","确认"],//按钮的文本
			drag:true,//是否拖动
			popUrl: null,
			callback: function(){
				//$(".modal").modal("hide");
			}
		});
		
		/***************	选择租赁资源BEGIN	****************/
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
				//租赁约定改变
				if($("input[name='billingArea']:checked").val() == "0"){
					$(".charges-tab-blank").find(".area").html($(this).val() +'<input value="'+$(this).val()+'" name="leaseBillingArea" type="hidden" readonly="readonly">');
					$(".clearUnitPrice").children().val("");
			    	$(".clearRental").html("");
				}
			}
		});
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
				//租赁约定改变
				if($("input[name='billingArea']:checked").val() == "1"){
					$(".charges-tab-blank").find(".area").html($(this).val() +'<input value="'+$(this).val()+'" name="leaseBillingArea" type="hidden" readonly="readonly">');
					$(".clearUnitPrice").children().val("");
			    	$(".clearRental").html("");
				}
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
				//租赁约定改变
				if($("input[name='billingArea']:checked").val() == "2"){
					$(".charges-tab-blank").find(".area").html($(this).val() +'<input value="'+$(this).val()+'" name="leaseBillingArea" type="hidden" readonly="readonly">');
					$(".clearUnitPrice").children().val("");
		    	    $(".clearRental").html("");
				}
			}
		});
		/**
		 * 选择租赁资源弹出框
		 */
		$("#leasResources").popwin({
			titleText: "选择租赁资源", //弹出框名
			popwidth:"900",//设置宽度
			btnText:["取消","确认"],//按钮的文本
			drag:true,//是否拖动
			popUrl: null,
			callback: function(){
				$(".modal").modal("hide");
				var selectName=$(".guaranSelect option:selected").text();//显示类型
				var qu=$(".leasBox .floorDong .canRentCls").parent().parent().find("b").text();//显示区
				var dong=$(".leasBox .floorDong .canRentCls").text();
				$(".leasBox .canRentCls2").each(function(){
					var lastButtomText=$(this).find('span').text();
					//var textAll2=selectName+'-'+qu+'-'+dong+'-'+lastButtomText+',';
					//textAll=textAll+textAll2;
					//textAll += lastButtomText+",";
					if(resourceNames.indexOf(lastButtomText) < 0){
						resourceNames.push(lastButtomText);
					}
				});
				var textAll='';
				for(var i in resourceNames){
					textAll += resourceNames[i]+",";
				}
				$("#leasingResource").val(textAll);
				var appendStr="";
	            $("input[name='subBox']:checkbox").each(function(){ 
	                if($(this).attr("checked")){
	                	appendStr += $(this).val()+","
	                }
	            });
	            //ids 拼装格式  id_builtArea_innerArea_leaseArea
	            if(resourceIds.length > 10){
	            	resourceIds=[];
	            	resourceNames=[];
	            	$(".resourcesO li").each(function() {; 
	                	$(this).removeClass('canRentCls2');
	            	});
	            	$("#leasingResourceIds").val("");
	            	$("#leasingResource").val("");
	            	$.eAlert("提示", "最多选择10个资源");
	            	return;
	            }
	            var array = appendStr.split(",");
	            //var ids = "";
	            for(var i in array){
	            	if(array[i] == ""){
	            		break;
	            	}
	            	var temp = array[i].split("_");
	            	for(var j in temp){
	            		if (temp[j] == "" || temp[j] == "null" || temp[j] == null){
	            			temp[j] = 0;
	            		}
	            	}
	            	if(resourceIds.indexOf(temp[0]) < 0){
	            		resourceIds.push(temp[0]);
	            	}
	            }
	            var ids = '';
	            for(var i in resourceIds){
	            	ids += resourceIds[i]+",";
	            }
	            getResourceIds(ids);
	            $("#leasingResourceIds").val(ids);
	            setResources2OtherFee();
			}
		});
		function setResources2OtherFee(){
			 var leasingResourceStr = $("#leasingResource").val().trim();
			 $("#templateOthersFeeBody tr").find(".leasingResourceOtherFee").each(function(){
				$(this).text(leasingResourceStr);
				$(this).attr("title",leasingResourceStr);
			 });
		}
		function getResourceIds(params){
			$.ajax({
		          type: "POST",
		          url: CONTEXT+"contractManage/queryByResourceIds",
		          data: {ids: params},
		          dataType: "json",
		          success: function(data){
		         	 if(data.success){
		         		 $("#floorArea").val(data.result.builtAreaCount);
		         		 $("#innerArea").val(data.result.innerAreaCount);
		         		 $("#freeArea").val(data.result.leaseAreaCount);
		         		 $(".clearUnitPrice").children().val("");
				    	 $(".clearRental").html("");
		         		 var areaCheck = $("input[name='billingArea']:checked").val();
			 		     if(areaCheck == "0"){
			 			    $("#leaseTable").find(".area").html(data.result.leaseAreaCount);
			 			    $(".charges-tab-blank").find(".area").html(data.result.leaseAreaCount +'<input value="'+data.result.leaseAreaCount+'" name="leaseBillingArea" type="hidden" readonly="readonly">');
			 		     }else if(areaCheck == "1"){
			 			    $(".charges-tab-blank").find(".area").html(data.result.builtAreaCount +'<input value="'+data.result.builtAreaCount+'" name="leaseBillingArea" type="hidden" readonly="readonly">');
			 		     }else if(areaCheck == "2"){
			 			    $(".charges-tab-blank").find(".area").html(data.result.innerAreaCount +'<input value="'+data.result.innerAreaCount+'" name="leaseBillingArea" type="hidden" readonly="readonly">');
			 		     }
		         	 }
		          }
			});
		}
		
		$("#leasingResource").on("click",function(){
			$("#leasResources").modal();
			loadAreaAndBuilding({});
			//资源类型变更触发事件
			$("#resourceType").change(function(){
				resourceIds = [];
				resourceNames = [];
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
					//loadAreaAndBuilding({"resourceTypeId":$(this).val()});
				}
			});
		});
		
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
		}loadMarketResourceType({});
		//填充资源类型
		function fillResourceType(records){
			for (var key in records){
				var $option = $('<option value="' + records[key].id + '">' + records[key].name + '</option>');
				$("#resourceType").append($option);
			}
		}
		
		
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
				var $b = $('<b>' + records[key].name + '</b>');  //去掉b元素里面的 style="border-top:none;"
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
		
		/*alert("ssssddddd");
		$(".leasBox .floor-num .floorls").removeClass('canRentCls');
		$(this).addClass('canRentCls');
		$(".leasBox .floorResources").width().css("display","block");
		var wh = $('.resources-rows');
		var whs = wh.length*154+'px';
		$(".floorResources").width(whs);
		//console.log(whs)*/
		
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
			$.getJSON(CONTEXT+"financeShould/getSpareUnitAndResource",params,function(data){
				if(data.success){
					//$("#buildingDiv").html("");
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
				var resources = records[key].resources ;
				//$(".leasBox .floorResources").css("display","block");
				$(".floorResources").width(records.length * 154 + "px");
				$(".floorDongUl .floorls").click(function(){
					var wh = $('.resources-rows');
					var whs = wh.length*154+'px';
					$(".floorResources").width(whs);
				})
				for (var rkey in resources) {
					//判断之前是否已经被选中
					var $li = $();
					if(resourceIds.length > 0){
						for(var i in resourceIds){
							if(Number(resourceIds[i]) == resources[rkey].id){
								$li = $('<li class="canRent canRentCls2" rid="'+ resources[rkey].id +'" rname="'+ resources[rkey].name +'" ><span>'+ resources[rkey].name +'</span>  '+ convertLeaseStatus(resources[rkey].leaseStatus) +'  '+ resources[rkey].leaseArea +'m²<div style="display:none"><input class="Echeckbox" type="checkbox" checked="checked" value="'+resources[rkey].id+'_'+resources[rkey].builtArea+'_'+resources[rkey].innerArea+'_'+resources[rkey].leaseArea+'" name="subBox"/></div></li>');
								break;
							}else{
								$li = $('<li class="canRent" rid="'+ resources[rkey].id +'" rname="'+ resources[rkey].name +'" ><span>'+ resources[rkey].name +'</span>  '+ convertLeaseStatus(resources[rkey].leaseStatus) +'  '+ resources[rkey].leaseArea +'m²<div style="display:none"><input class="Echeckbox" type="checkbox" value="'+resources[rkey].id+'_'+resources[rkey].builtArea+'_'+resources[rkey].innerArea+'_'+resources[rkey].leaseArea+'" name="subBox"/></div></li>');
							}
						}
						$ul.append($li);
					}else{
						$li = $('<li class="canRent" rid="'+ resources[rkey].id +'" rname="'+ resources[rkey].name +'" ><span>'+ resources[rkey].name +'</span>  '+ convertLeaseStatus(resources[rkey].leaseStatus) +'  '+ resources[rkey].leaseArea +'m²<div style="display:none"><input class="Echeckbox" type="checkbox" value="'+resources[rkey].id+'_'+resources[rkey].builtArea+'_'+resources[rkey].innerArea+'_'+resources[rkey].leaseArea+'" name="subBox"/></div></li>');
						$ul.append($li);
					}
					$li.on("click",function(){
						if($(this).hasClass('canRentCls2')){
							
							var nameIndex = resourceNames.indexOf($(this).children().html());
							resourceNames.splice(nameIndex, 1);
							
							var idIndex = resourceIds.indexOf($(this).attr("rid"));
							resourceIds.splice(idIndex, 1);
							
							$(this).removeClass('canRentCls2')
							$(this).children().children().attr("checked", false);
						}else{
							if(resourceNames.indexOf($(this).children().html()) < 0){
								resourceNames.push($(this).children().html());
							}

							if(resourceIds.indexOf($(this).attr("rid")) < 0){
								resourceIds.push($(this).attr("rid"));
							}
							$(this).addClass('canRentCls2');
							$(this).children().children().attr("checked", true);
						}
					});
					
					//var $checkbox = $('<div style="display:none"><input class="Echeckbox" type="checkbox" value="'+resources[rkey].id+'_'+resources[rkey].builtArea+'_'+resources[rkey].innerArea+'_'+resources[rkey].leaseArea+'" name="subBox"/></div>');
					//$li.append($checkbox);
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
		/***************	选择租赁资源END	****************/
		
		
		/*$("body").on("click",".canRent",function(){
			if($(this).hasClass('canRentCls2')){
				$(this).removeClass('canRentCls2')
				$(this).children().children().attr("checked", false);
			}else{
				$(this).addClass('canRentCls2');
				$(this).children().children().attr("checked", true);
			}
		});*/
		//		点击哪栋
		/*$("body").on("click",".leasBox .floorDong .floorls",function(){
			$(".leasBox .floorDong .floorls").removeClass('canRentCls');
			$(this).addClass('canRentCls');
			$(".leasBox .floor-num").css("display","block");
		});
//				点击哪层
		$("body").on("click",".leasBox .floor-num .floorls",function(){
			$(".leasBox .floor-num .floorls").removeClass('canRentCls');
			$(this).addClass('canRentCls');
			$(".leasBox .floorResources").css("display","block");
		});*/
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
		});

		
	
	     /*********************************begin********************************/
		

		//上传
		 $("#contractFile3").uploadify({
			 method:'post',
			 width:250,
		     height:30,
		     swf:'lib/uploadify/uploadify.swf',
		     uploader:'uploadFile',
		     buttonText:'请选择合同附件',
		     multi:true,
		     fileSizeLimit:'10MB',
		     fileTypeDesc:'请选择pdf jpg gif zip rar文件',
		     fileTypeExts: '*.pdf;*.jpg;*.gif;*.zip;*.rar',
		     fileObjName:'file',
		     removeCompleted : true,
		     overrideEvents: ['onSelectError', 'onDialogClose'],
             onDialogClose:function(queueData){
            	 //判断上传的总个数是否大于10个，如果大于，终止当前上传
            	if((queueData.filesSelected+$("#uploadDiv .contractUpload").length)>10){
            		$("#contractFile3").uploadify('cancel', '*');
            		$.eAlert("操作提示","上传总数不能超过10个！");
		 		}
             },
             
             onSelectError:function(file, errorCode, errorMsg){
                 switch(errorCode) {
                     case -110:
                    	 $.eAlert("操作提示","文件 ["+file.name+"] 大小超出系统限制的"+$('#contractFile3').uploadify('settings','fileSizeLimit')+"大小！");
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
//						arrayFee.push(itemNameId);
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
						//standardName=standardName+"("+chargeUnitPrice+standards[skey].chargeUnitName+")";
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
		  /*********************************begin********************************/		
	   //租赁约定
	   //var data = []
	   function addRos(leaseCount) {
		   var rentableArea = 0;
		   var areaCheck = $("input[name='billingArea']:checked").val();
		   if(areaCheck == "0"){
			   rentableArea = $("#freeArea").val();
		   }else if(areaCheck == "1"){
			   rentableArea = $("#floorArea").val();
		   }else if(areaCheck == "2"){
			   rentableArea = $("#innerArea").val();
		   }
		   
	   	   var rowS =  '<tr id="" class="charges-tab-blank">'
							    +'<td class="eq-add">'+leaseCount+'</td>'
							    +'<td>'
							    +'	<div class="date-icon border-none-right border-lt-right">'
								+'   <input type="text" class="form-control start-date" placeholder="起始日期" name="startDay">'		
								+'</div>'	
							    +'</td>'
							    +'<td>'
							    +'	<div class="date-icon border-none-right border-lt-right">'
								+'   <input type="text" class="form-control start-date" placeholder="截止日期" name="endDay">'	
								+'	</div>'
							    +'</td>'
							    +'<td>'
								+'<select class="form-control wids" name="billingUnit" id="billingUnit">'	
								+'	   <option value="0" selected="selected">元/月/平</option>'
								+'     <option value="1">元/季/平</option>'	
								+'	   <option value="2">元/半年/平</option>'
								+'	   <option value="3">元/年/平</option>'
								+'	</select>'
							    +'</td>'
							    +'<td class="clearUnitPrice"><input class="rents edinput" type="text" name="unitPrice" ></td>'
							    +'<td class="area">'+rentableArea+'<input value="'+rentableArea+'" type="hidden" name="leaseBillingArea" readonly="readonly" style="width:80px"></td>'
							    +'<td class="totalM clearRental" ></td>'
							    +'</tr>';
			
			$('.charges-tab-style .tb-trs').append(rowS);
			countMoney();
			$(".start-date").datepicker({
				format: "yyyy-mm-dd",
				autoclose: true
			});			  
	  }
	  function addRos_edit(leaseCount, obj) {
		   var rentableArea = 0;
		   var areaCheck = $("input[name='billingArea']:checked").val();
		   if(areaCheck == "0"){
			   rentableArea = $("#freeArea").val();
		   }else if(areaCheck == "1"){
			   rentableArea = $("#floorArea").val();
		   }else if(areaCheck == "2"){
			   rentableArea = $("#innerArea").val();
		   }
		   var option = '<option value="0" ';
		   if(obj.billingUnit == 0 || obj.billingUnit == "0"){
			   option += ' selected = "selected" ';
		   }
		   option += '>元/月/平</option>';	
		   option += '<option value="1" ';
		   if(obj.billingUnit == 1 || obj.billingUnit == "1"){
			   option += ' selected = "selected" ';
		   }
		   option += '>元/季/平</option>';
			   
		   option += '<option value="2" ';
		   if(obj.billingUnit == 2 || obj.billingUnit == "2"){
			   option += ' selected = "selected" ';
		   }
		   option += '>元/半年/平</option>' ;
			   
		   option += '<option value="3" ';
		   if(obj.billingUnit == 3 || obj.billingUnit == "3"){
			   option += ' selected = "selected" ';
		   }
		   option += '>元/年/平</option>';
	   	   var rowS =  '<tr id="" class="charges-tab-blank">'
							    +'<td class="eq-add">'+ (Number(leaseCount)+1) +'</td>'
							    +'<td>'
							    +'	<div class="date-icon border-none-right border-lt-right">'
								+'   <input type="text" class="form-control start-date" placeholder="起始日期" name="startDay" value="'+obj.startDay.substring(0,10)+'">'		
								+'</div>'	
							    +'</td>'
							    +'<td>'
							    +'	<div class="date-icon border-none-right border-lt-right">'
								+'   <input type="text" class="form-control start-date" placeholder="截止日期" name="endDay" value="'+obj.endDay.substring(0,10)+'">'	
								+'	</div>'
							    +'</td>'
							    +'<td>'
								+'<select class="form-control wids" name="billingUnit" id="billingUnit">'+option	
								/*+'	   <option value="0" selected="selected">元/月/平</option>'
								+'     <option value="1">元/季/平</option>'	
								+'	   <option value="2">元/半年/平</option>'
								+'	   <option value="3">元/年/平</option>'*/
								+'</select>'
							    +'</td>'
							    +'<td class="clearUnitPrice"><input value="'+obj.unitPrice+'" class="rents edinput" type="text" name="unitPrice" ></td>'
							    +'<td class="area">'+rentableArea+'<input value="'+rentableArea+'" type="hidden" name="billingArea" readonly="readonly" style="width:80px"></td>'
							    +'<td class="totalM clearRental" >'+formatMoney(obj.rental)+'<input value="'+obj.rental+'" name="rental" type="hidden"></td>'
							    +'</tr>';
			
			$('.charges-tab-style .tb-trs').append(rowS);
			countMoney();
			$(".start-date").datepicker({
				format: "yyyy-mm-dd",
				autoclose: true
			});	
			$('.charges-tab-blank').on('click',function() {
				$(this).addClass('addcss').siblings().removeClass('addcss');
				//$(this).remove();	
			})
	   }
	  // 租赁约定 点击行
	  $('#addTr').click(function() {
		  var leaseCount = $("#leaseTable tr").length;
		  if($("#leasingResourceIds").val() == ""){
			  $.eAlert("提示", "请先选择租赁资源!");
			  return;
		  }
		  addRos(leaseCount)
		  $('.charges-tab-blank').on('click',function() {
			  $(this).addClass('addcss').siblings().removeClass('addcss');
			  //$(this).remove();	
		  })
	  })
	  
	  $('#removeTr').on('click',function() {
		   if(!$(this).parents('.charges-max-box').find('.charges-tab-blank').hasClass('addcss')){
				$.eAlert("操作提示","请添加或选择一行删除!");
				return false; 
		   }else{
			   $(this).parents('.charges-max-box').find('.addcss').remove(); 
		   }
		   var num=$(".eq-add"), tNum=1;
		   num.each(function(){
				$(this).html(tNum);
				tNum++;
		   });
	  });
      //免租约定
      function leaseDad(freeCount) {
    	  var lesRos = '<tr class="charges-tab-blank">'
    		  +'<td class="leaseNum">'+freeCount+'</td>'
    		  +'<td>'
    		  +'<div class="date-icon border-none-right">'  
    		  +'<input type="text" class="form-control start-date start-cet" placeholder="起始日期" name="freeStartDay">'	
    		  +'  </div>'
    		  +'</td>'
    		  +'<td><input class="rents" type="text" name="freeMonths"/></td>'
    		  +'<td><input class="rents" type="text" name="freeDays"/></td>'
    		  +'<td class="freeEndDay"></td>'
    		  +'</tr>';
    	  $('.charges-tab-style .leaseDs').append(lesRos);
    	  countDateMonth();
    	  countDateDay();
    	  $(".start-date").datepicker({
				format: "yyyy-mm-dd",
				autoclose: true
    	  });	
    	  /**
    	   * 免租约定 起始日期发生改变
    	   */
			$("input[name='freeStartDay']").on("change",function() {
				var reckonMonth = $(this).parent().parent().parent().find("input[name='freeMonths']").val();
				var reckonDay =  $(this).parent().parent().parent().find("input[name='freeDays']").val();
				reckonDay = reckonDay == "" ? 0 : reckonDay;
				reckonMonth = reckonMonth == "" ? 0 : reckonMonth;
				if(reckonDay == 0 && reckonMonth == 0){
				  $(this).parent().parent().find(".freeEndDay").html("");
				  return;
				}
				var nowDate = $(this).val();
				var now = new Date(nowDate);
				var years = now.getFullYear();
				var months = now.getMonth()+1;
				var days = now.getDate();
				//加月份
				NextMonth = addMonth(months+"/"+days+"/"+years, Number(reckonMonth));
				years = NextMonth.getFullYear();
				months = NextMonth.getMonth()+1;
			  days = NextMonth.getDate();
				//加日期
				NextDay = addDay(months+"/"+days+"/"+years, Number(reckonDay));
				years = NextDay.getFullYear();
				months = NextDay.getMonth()+1;
				days = NextDay.getDate();

				if(Number(months) < 10){
				  months = "0"+months;
				}
				if(Number(days) < 10){
				  days = "0"+days;
				}
				var resDate = years+"-"+months+"-"+days;
				$(this).parent().parent().parent().find(".freeEndDay").html(resDate+'<input class="rents" type="hidden" value="'+resDate+'" name="freeEndDay"/>');
			})				    
      };
      function leaseDad_edit(freeCount, obj) {
        	var lesRos = '<tr class="charges-tab-blank">'
  							    +'<td class="leaseNum">'+ (Number(freeCount)+1) +'</td>'
  							    +'<td>'
  							    +'	<div class="date-icon border-none-right">'  
  								+'		<input type="text" class="form-control start-date start-cet" value="'+obj.startDay.substring(0,10)+'" placeholder="起始日期" name="freeStartDay">'	
  								+'  </div>'
  							    +'</td>'
  							    +'<td><input class="rents" type="text" name="freeMonths" value="'+obj.freeMonths+'"/></td>'
							    +'<td><input class="rents" type="text" name="freeDays" value="'+obj.freeDays+'"/></td>'
							    +'<td class="freeEndDay">'+obj.endDay.substring(0,10)+' <input class="rents" type="hidden" name="freeEndDay" value="'+obj.endDay.substring(0,10)+'"/></td>'
							    +'</tr>';
  			$('.charges-tab-style .leaseDs').append(lesRos);
  			countDateMonth();
  			countDateDay();
  			$(".start-date").datepicker({
  				format: "yyyy-mm-dd",
  				autoclose: true
  			});	
  			$('.charges-tab-blank').on('click',function() {
				$(this).addClass('addcss').siblings().removeClass('addcss');
				//$(this).remove();	
			})			    
      };
      
      $('#leaseDad').click(function() {
    	  var freeCount = $("#freeTable tr").length;
    	  leaseDad(freeCount);
    	  $('.charges-tab-blank').on('click',function() {
    		  $(this).addClass('addcss').siblings().removeClass('addcss');
    		  //$(this).remove();	 	
  		 })
	  })
	  
	  $("#leaseRed").on("click",function(){
		  if(!$(this).parents(".charges-max-box").find(".charges-tab-blank").hasClass("addcss")){
			  $.eAlert("操作提示","请添加或选择一行删除!");
		  }else{
			  $(this).parents(".charges-max-box").find(".addcss").remove();
			  var num=$(".leaseNum"), tNum=1;
			  num.each(function(){
				  $(this).html(tNum);
				  tNum++;
			  });
		  }
	  });
      
      //按约定总金额-缴费约定
      function paymentAdd(paymentCount) {
    	  var lesRos = '<tr class="charges-tab-blank">'
		    +'<td class="paymentNum">'+paymentCount+'</td>'
		    +'<td>'
		    +'<div class="date-icon border-none-right">'
		    +'	<input type="text" class="form-control start-date start-cet" placeholder="起始日期" name="paymentTime">'
		    +'	<i class="fa fa-calendar"></i>'
		    +'</div>'
		    +'</td>'
		    +'<td><input class="rents" type="text" name="paymentAmt"/></td>'
		    +'</tr>';
			$('#econManageDT').append(lesRos);
			$(".start-date").datepicker({
				format: "yyyy-mm-dd",
				autoclose: true
			});	
							    
      };
      function paymentAdd_edit(paymentCount, obj) {
        	var lesRos = '<tr class="charges-tab-blank">'
  		    +'<td class="paymentNum">'+ (Number(paymentCount)+1) +'</td>'
  		    +'<td>'
  		    +'<div class="date-icon border-none-right">'
  		    +'	<input type="text" class="form-control start-date start-cet" placeholder="起始日期" name="paymentTime" value="'+obj.paymentTime.substring(0,10)+'">'
  		    +'	<i class="fa fa-calendar"></i>'
  		    +'</div>'
  		    +'</td>'
  		    +'<td><input class="rents" type="text" name="paymentAmt" value="'+obj.paymentAmt+'"/></td>'
  		    +'</tr>';
  			$('#econManageDT').append(lesRos);
  			$(".start-date").datepicker({
  				format: "yyyy-mm-dd",
  				autoclose: true
  			});	
  			$('.charges-tab-blank').on('click',function() {
				$(this).addClass('addcss').siblings().removeClass('addcss');
				//$(this).remove();	
			})
      };
      
      
      
      $('#paymentAdd').click(function() {
    	  var paymentCount = $("#econManageDT tr").length;
    	  paymentAdd(paymentCount);
    	  $('.charges-tab-blank').on('click',function() {
    		  $(this).addClass('addcss').siblings().removeClass('addcss');
  		  })
	  })
	  
	  $('#paymentRem').on('click',function() {
    	  if(!$(this).parents('.total_amount').find('.charges-tab-blank').hasClass('addcss')){
    		  $.eAlert("操作提示","请添加或选择一行删除!");
    	  }else{
    		  $(this).parents('.total_amount').find('.addcss').remove();  
    		  var num=$(".paymentNum"), tNum=1;
			  num.each(function(){
				  $(this).html(tNum);
				  tNum++;
			  });
    	  }
	  })
      
      
      
      
      //约定总金额和周期收费切换
      $('#cycle_charge').on('click',function() {
      	$('.contract-rents').hide();
      	$('.billing_area').show()
      })
      $('#ent_agreement').on('click',function() {
      	$('.contract-rents').show();
      	$('.billing_area').hide()
      })
      
       //租赁约定單價改變
      function countMoney(){
    	  $(".contract .edinput").on("input",function() {
    		  var price=$(this).val(),
	 	         fromSelect=$(this).parent().parent().find(".form-control option:selected").val(),
	 	         area=$(this).parent().parent().find(".area").children().val();
	 	     /*alert(area+"	fromSelect:"+fromSelect);*/
    		  if(isNaN(price)){
    			  $(this).val("0");
    			  price=0;
    		  }
	 	      var resPrice = 0;
	 	     if(area == "0"){
	 	    	 $(this).parent().parent().find(".totalM").html('0<input value="0" name="rental" type="hidden" readonly="readonly" style="width:100px">');
	 	     }else{
	 	    	 $(this).parent().parent().find(".totalM").html(formatMoney((Number(price) * Number(area)).toFixed(2) ) + '<input value="'+ dealAmount(Number(price) * Number(area))+'" name="rental" type="hidden" readonly="readonly" style="width:100px">');
	 	     }
    	  })
      }countMoney();
      
      //选择计费面积处理业务
      $("input[name='billingArea']").click(function(){
    	  if($("#leasingResourceIds").val() == ""){
    		  $.eAlert("提示", "请先选择租赁资源!");
    		  return;
    	  }
    	  $(".clearUnitPrice").children().val("");
    	  $(".clearRental").html("");
    	  if($(this).val() == "0"){
    		  $(".charges-tab-blank").find(".area").html($("#freeArea").val() +'<input value="'+$("#freeArea").val()+'" name="leaseBillingArea" type="hidden" readonly="readonly" style="width:80px">');
    	  }else if($(this).val() == "1"){
    		  $(".charges-tab-blank").find(".area").html($("#floorArea").val() +'<input value="'+$("#floorArea").val()+'" name="leaseBillingArea" type="hidden" readonly="readonly" style="width:80px">');
    	  }else if($(this).val() == "2"){
    		  $(".charges-tab-blank").find(".area").html($("#innerArea").val() +'<input value="'+$("#innerArea").val()+'" name="leaseBillingArea" type="hidden" readonly="readonly" style="width:80px">');
    	  }
      })
       var isSubmit = true;
      /**
       * 拼装json请求参数
       */
      function getParams(){
    	  isSubmit = true;
    	  var params = {};
    	  //合同基本信息
    	  var mainDTO = $("#mainDto_form").serializeJson();
    	  //mainDTO.id = contractId;
    	  //收费方式	chargingWays
    	  mainDTO.chargingWays = $("input[name='chargingWays']:checked").val();
    	  //计费面积	billingArea
    	  mainDTO.billingArea = $("input[name='billingArea']:checked").val();
    	  //缴费周期	paymentCycle
    	  mainDTO.paymentCycle = $("input[name='paymentCycle']:checked").val();
    	  //缴费日期	paymentDayType
    	  mainDTO.paymentDayType = $("input[name='paymentDayType']:checked").val();
    	  //附加条款
    	  mainDTO.additionalTerms=$("#additionalTerms").val().trim();
    	  mainDTO.marketResourceTypeId = $("#resourceType option:selected").val();
    	  mainDTO.categoryName = $("#categoryId option:selected").text();
    	  params.mainDTO = mainDTO;
    	  //按周期收费-租赁约定
    	  var leaseArray = [];
    	  //按周期收费-免租约定记录
    	  var freeArray = [];
    	  if(mainDTO.chargingWays == "0" || mainDTO.chargingWays ==0){
    		  leaseArray = getLeaseArray();
    		  if(!isSubmit) return;
    		  freeArray = getFreeArray();
    		  if(!isSubmit) return;
    		//缴费日期(天)	paymentDays
        	  var paymentDays = $("input[name='paymentDayType']:checked").next().next().next().children().val();
        	  if(paymentDays == ''){
        		  $.eAlert("提示", "存在必填项未填!");
    	    	  isSubmit = false;
    	    	  return;
        	  }else{
        		  isSubmit = true;
        	  }
        	  if(!/^[0-9]*[1-9][0-9]*$/.test(paymentDays)){
        		  $.eAlert("提示", "缴费日期必须为正整数!");
    	    	  isSubmit = false;
    	    	  return;
        	  }else{
        		  isSubmit = true;
        	  }
        	  mainDTO.paymentDays = paymentDays;
        	  var countArea = 0;
    		  var areaCheck = $("input[name='billingArea']:checked").val();
    		  if(areaCheck == "0"){
    			  countArea = $("#freeArea").val();
    		  }else if(areaCheck == "1"){
    			  countArea = $("#floorArea").val();
    		  }else if(areaCheck == "2"){
    			  countArea = $("#innerArea").val();
    		  }
    		  mainDTO.countArea = countArea;
    	  }
    	  params.leaseList = leaseArray;
    	  params.freeList = freeArray;
    	  //按约定总金额-缴费约定记录
    	  var paymentArray = [];
    	  if(mainDTO.chargingWays == "1" || mainDTO.chargingWays ==1){
    		  paymentArray = getPaymentArray();
    		  if(!isSubmit) return;
    		  //合同总金额
    		  var totalAmt = $("input[name='totalAmt']").val();
    		  if(totalAmt == ""){
    			  $.eAlert("提示", "合同总金额必填!");
    	    	  isSubmit = false;
    	    	  return;
    		  }else{
    			  isSubmit = true;
    		  }
    		  if(totalAmt.length > 12){
    			  $.eAlert("提示", "合同总金额过大");
    	    	  isSubmit = false;
    	    	  return;
    		  }else{
    			  isSubmit = true;
    		  }
    		  if(isNaN(totalAmt)){
    			  $.eAlert("提示", "合同总金额必须为数字!");
    			  $("input[name='totalAmt']").val("");
    	    	  isSubmit = false;
    	    	  return;
    		  }else{
    			  isSubmit = true;
    		  }
    		  if(Number(totalPaymentAmt) != Number(totalAmt)){
    			  $.eAlert("提示", "缴费金额累加必须等于合同总金额!");
    	    	  isSubmit = false;
    	    	  return;
    		  }else{
    			  isSubmit = true;
    		  }
    		  params.mainDTO.totalAmt = dealAmount(totalAmt);
    		  params.mainDTO.billingArea = null;
    		  params.mainDTO.paymentCycle = null;
    		  params.mainDTO.paymentDayType = null;
    		  params.mainDTO.paymentDays = null;
    	  }
    	  
    	  params.paymentList = paymentArray;
    	  //按周期收费-其他费约定记录
    	  params.othersFeeList = getOtherFeeData();
    	  //附件
    	  params.accessoriesList =getUploadInfo() ;
    	  params.changeDto = {};
    	  return params;
      }
      function getLeaseArray(){
       	 var leaseArray = [];
       	 var upEndDay;
       	 $("#leaseTable tr").each(function(trindex,tritem){//遍历每一行
   		  		var leaseObj = {};
   				if(trindex == 0){
   					return;
   				}
   			    $(tritem).find("input[name='startDay']").each(function(tdindex,tditem){
   			    	if(tditem.value == ''){
   			    		$.eAlert("提示", "租赁约定的起始日期不能为空!");
   			    		isSubmit = false;
   			    		return;
   			    	}else{
   			    		isSubmit = true;
   			    	}
   			    	//起租日期
  			    	var startLeasingDay = Number($("input[name='startLeasingDay']").val().replace(new RegExp("-","gm"),""));
  					var startDay = Number(tditem.value.replace(new RegExp("-","gm"),""));
  					
  					if(startDay < startLeasingDay){
  						$.eAlert("提示", "租赁约定起始日期小于合同开始时间!");
  			    		isSubmit = false;
  			    		return;
  					}else{
  			    		isSubmit = true;
  			    	}
  					
  					//判断第二条记录的起始日期  是否大于上一条的截止日期
  					if(upEndDay){
  						if(startDay <= upEndDay.replace(new RegExp("-","gm"),"")){
  							$.eAlert("提示", "租赁约定起始日期必须大于上一条记录的截止日期!");
  							isSubmit = false;
  							return;
  						}else{
  							isSubmit = true;
  						}
  					}
   			    	leaseObj.startDay = tditem.value;
   				})
   				if(!isSubmit) return;
   			    $(tritem).find("input[name='endDay']").each(function(tdindex,tditem){
   			    	if(tditem.value == ''){
   			    		$.eAlert("提示", "租赁约定的截止日期不能为空!");
   			    		isSubmit = false;
   			    		return;
   			    	}else{
   			    		isSubmit = true;
   			    	}
   			    	var startDate = Number(leaseObj.startDay.replace(new RegExp("-","gm"),""));
   			    	var endLeasingDay = Number($("input[name='endLeasingDay']").val().replace(new RegExp("-","gm"),""));
   					var endDate = Number(tditem.value.replace(new RegExp("-","gm"),""));
   			    	if(endDate < startDate){
   			    		$.eAlert("提示", "截止日期必须大于起始日期");
   			    		isSubmit = false;
   			    		return;
   			    	}else{
   			    		isSubmit = true;
   			    	}
   			    	if(endDate > endLeasingDay){
   			    		$.eAlert("提示", "截止时间大于合同结束时间!");
   			    		isSubmit = false;
   			    		return;
   			    	}else{
   			    		isSubmit = true;
   			    	}
   			    	leaseObj.endDay = tditem.value;
   			    	upEndDay = tditem.value;
   				});
   			    if(!isSubmit) return;
   			    $(tritem).find("#billingUnit option:selected").each(function(tdindex,tditem){
   			    	leaseObj.billingUnit = tditem.value;
   				})
   				$(tritem).find("input[name='unitPrice']").each(function(tdindex,tditem){
   					if(tditem.value == ''){
   			    		$.eAlert("提示", "请输入租金单价!");
   			    		isSubmit = false;
   			    		return;
   			    	}else{
   			    		isSubmit = true;
   			    	}
   			    	if(isNaN(tditem.value)){
   			    		$.eAlert("提示", "租金单价必须为数字!");
   			    		tditem.value = "";
   			    		isSubmit = false;
   			    		return;
   			    	}else{
   			    		isSubmit = true;
   			    	}
   			    	leaseObj.unitPrice = dealAmount(tditem.value);
   				});
   				if(!isSubmit) return;
   				$(tritem).find("input[name='leaseBillingArea']").each(function(tdindex,tditem){
   					leaseObj.billingArea = tditem.value;
   				})
   				$(tritem).find("input[name='rental']").each(function(tdindex,tditem){
  					if(tditem.value.length > 12){
  						$.eAlert("提示", "租赁约定的租金值过大!");
  			    		//tditem.value = "";
  			    		isSubmit = false;
  			    		return;
  			    	}else{
  			    		isSubmit = true;
  					}
  					leaseObj.rental = tditem.value;
  				})
  				if(!isSubmit) return;
   				  leaseObj.contractNo = $("#contractNo").val();
   			      leaseObj.startDayStr=leaseObj.startDay
   			      leaseObj.endDayStr=leaseObj.endDay
   				  leaseObj.isDeleted = 1;
   				  leaseArray.push(leaseObj); 
   		      })
       	 return leaseArray;
       }
      function getFreeArray(){
    	  var freeArray = [];
    	  $("#freeTable tr").each(function(trindex,tritem){//遍历每一行
    		  var freeObj = {};
  			  if(trindex == 0){
  				  return;
  			  }
  		      $(tritem).find("input[name='freeStartDay']").each(function(tdindex,tditem){
  		    	if(tditem.value == ''){
		    		$.eAlert("提示", "免租约定的起始日期不能为空!");
		    		isSubmit = false;
		    		return;
		    	}else{
		    		isSubmit = true;
		    	}
  		    	  freeObj.startDay = tditem.value;
  			  });
  			  if(!isSubmit) return;
  		      $(tritem).find("input[name='freeEndDay']").each(function(tdindex,tditem){
  		    	  freeObj.endDay = tditem.value;
  			  })
  		      $(tritem).find("input[name='freeMonths']").each(function(tdindex,tditem){
  		    	   var freeMonths = tditem.value == "" ? 0 : tditem.value;
  		    	   freeObj.freeMonths = freeMonths;
  			  });
  			  if(!isSubmit) return;
  			  $(tritem).find("input[name='freeDays']").each(function(tdindex,tditem){
  				if(tditem.value == '' && (freeObj.freeMonths == "" || freeObj.freeMonths == undefined)){
		    		  $.eAlert("提示", "免租约定的免租月数或免租天数必填一个!");
		    		  isSubmit = false;
		    		  return;
		    	   }else{
		    		  isSubmit = true;
		    	   }
  				  var freeDays = tditem.value == "" ? 0 : tditem.value;
  				  freeObj.freeDays = freeDays;
  			  })
  			  freeObj.contractNo = $("#contractNo").val();
  		      freeObj.isDeleted = 1;
  		      freeObj.startDayStr=freeObj.startDay
  		      freeObj.endDayStr=freeObj.endDay
  			  freeArray.push(freeObj); 
  	      })
  	      return freeArray;
      }
      var totalPaymentAmt = 0;
      function getPaymentArray(){
    	  totalPaymentAmt = 0;
    	  var paymentArray = [];
    	  $("#econManageDT tr").each(function(trindex,tritem){//遍历每一行
			  var paymentObj = {};
			  if(trindex == 0){
				  return;
			  }
		      $(tritem).find("input[name='paymentTime']").each(function(tdindex,tditem){
		    	  if(tditem.value == ''){
		    		  $.eAlert("提示", "缴费约定的缴费期限不能为空!");
		    		  isSubmit = false;
		    		  return;
			      }else{
			    	  isSubmit = true;
			      }
		    	  paymentObj.paymentTime = tditem.value;
			  })
			  if(!isSubmit) return;
		      $(tritem).find("input[name='paymentAmt']").each(function(tdindex,tditem){
		    	  if(tditem.value == ''){
		    		  $.eAlert("提示", "缴费约定的缴费金额不能为空!");
		    		  isSubmit = false;
		    		  return;
			      }else{
			    	  isSubmit = true;
			      }
		    	  if(isNaN(tditem.value)){
		    		  $.eAlert("提示", "缴费约定的缴费金额必须为数字!");
		    		  tditem.val = "0";
		    		  isSubmit = false;
		    		  return;
			      }else{
			    	  isSubmit = true;
			      }
		    	  totalPaymentAmt += Number(tditem.value);
		    	  paymentObj.paymentAmt = tditem.value;
			  })
			  if(!isSubmit) return;
			  paymentObj.contractNo = $("#contractNo").val();
		      paymentObj.isDeleted = 1;
		      paymentObj.paymentTimeStr= paymentObj.paymentTime
		      paymentArray.push(paymentObj); 
	      })
	      return paymentArray;
      }
      /**
       * 其它费项数据
       */
      function getOtherFeeData(){
			var otherFeeArray = new Array();
			//var contractId = 1;
			var contractNo=$("#contractNo").val().trim();
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
					total = Math.ceil($(this).parent().find(".rentMode2").val().trim()*100)/100 +"";
				}
				//如果是走表类，不显示计算方法和金额，在走表类抄表处单独处理
				if(itemCategoryId=="2"){
					rentMode="";
					total="";
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
		
		 //封装附件数据信息
		 function getUploadInfo(){
			 var accessoriesArray = new Array();
			 var fileUrl="";
			 var fileName="";
			 var contractNo=$("#contractNo").val().trim();
			 $("#uploadDiv").find(".contractUpload").each(function(){
				 fileUrl= $(this).attr("dataUrl").trim();
				 fileName = $(this).text().trim();
				 var rowAccess={
						 "contractId":contractId,
						 "contractNo":contractNo,
						 "fileUrl":fileUrl,
						 "fileName":fileName,
						 "isDeleted":0
				 };
				 accessoriesArray.push(rowAccess);
			 });
			 return accessoriesArray;
		 }
      
		 /**
          * 经营分类
   		  */
		 function getCategory(categoryId){
    	  	$.ajax({
    		  	type: "POST",
    		  	url: CONTEXT+"contractManage/getCategory",
    		  	dataType: "json",
    		  	async:false,
    		  	success: function(data){
    			  	if(data.success){
    				  	var res = data.result;
    				  	var str = "";
    				  	for(var i in res){
    					  	str += '<option value="'+res[i].categoryId+'"'
    					  	if(categoryId == res[i].categoryId){
    						  	str +=' selected=selected';
    					  	}
    					  	str += '>'+res[i].cateName+'</option>';
    				  	}
    				  	$("#categoryId").append(str);
    			  	} else {
    				  	$.eAlert("提示信息", data.msg);
    			  	}
    		  	}
    	  	});
      	}
      
      	/**
       	* 免租约定-计算截止日期
       	*/
      	function countDateMonth(){
    	  	$("input[name='freeMonths']").on("input",function() {
    		  	var reckonMonth = $(this).val();
    		  	var reckonDay =  $(this).parent().parent().find("input[name='freeDays']").val();
    		  	if(Number(reckonMonth) < 0 || !/^[0-9]*[1-9][0-9]*$/.test(reckonMonth)){
    			  	$(this).val("");
    			  	reckonMonth = 0;
    		  	}
    		  	reckonDay = reckonDay == "" ? 0 : reckonDay;
    		  	reckonMonth = reckonMonth == "" ? 0 : reckonMonth;
    		  	if(reckonDay == 0 && reckonMonth == 0){
    			  	$(this).parent().parent().find(".freeEndDay").html("");
    			  	return;
    		  	}
    		  	var nowDate = $(this).parent().parent().find("input[name='freeStartDay']").val();
    		  	if(nowDate == ""){
    			  	$.eAlert("","请先输日期");
				  	$(this).val("");
				  	return;
    		  	}
    		  	var now = new Date(nowDate);
    		  	var years = now.getFullYear();
    		  	var months = now.getMonth()+1;
    		  	var days = now.getDate();
    		  	//加月份
    		  	NextMonth = addMonth(months+"/"+days+"/"+years, Number(reckonMonth));
    		  	years = NextMonth.getFullYear();
    		  	months = NextMonth.getMonth()+1;
			  	days = NextMonth.getDate();
    		  	//加日期
    		  	NextDay = addDay(months+"/"+days+"/"+years, Number(reckonDay));
    		  	years = NextDay.getFullYear();
    		  	months = NextDay.getMonth()+1;
    		  	days = NextDay.getDate();
    		  
		  		if(Number(months) < 10){
    			  	months = "0"+months;
    		  	}
    		  	if(Number(days) < 10){
    			  	days = "0"+days;
    		  	}
    		  	var resDate = years+"-"+months+"-"+days;
    		  	$(this).parent().parent().find(".freeEndDay").html(resDate+'<input class="rents" type="hidden" value="'+resDate+'" name="freeEndDay"/>');
    	  	})
      	}countDateMonth();
      
      
  		function countDateDay(){
	  		$("input[name='freeDays']").on("input",function() {
    		  	var reckonMonth =  $(this).parent().parent().find("input[name='freeMonths']").val();
    		  	var reckonDay = $(this).val();
    		  	if(Number(reckonDay) < 0 || !/^[0-9]*[1-9][0-9]*$/.test(reckonDay)){
			  		$(this).val("");
    			  	reckonDay = 0;
    		  	}
    		  	reckonDay = reckonDay == "" || Number(reckonDay) < 0 ? 0 : reckonDay;
    		  	reckonMonth = reckonMonth == "" || Number(reckonMonth) < 0 ? 0 : reckonMonth;
    		  	if(reckonDay == 0 && reckonMonth == 0){
    			  	$(this).parent().parent().find(".freeEndDay").html("");
    			  	return;
    		  	}
    		  	var nowDate = $(this).parent().parent().find("input[name='freeStartDay']").val();
    		  	if(nowDate == ""){
				  	$.eAlert("","请先输日期");
				  	$(this).val("");
				  	return;
			  	}
    		  	var now = new Date(nowDate);
    		  	var years = now.getFullYear();
    		  	var months = now.getMonth()+1;
    		  	var days = now.getDate();
    		  	//加月份
    		  	NextMonth = addMonth(months+"/"+days+"/"+years, Number(reckonMonth));
    		  	years = NextMonth.getFullYear();
    		  	months = NextMonth.getMonth()+1;
    			days = NextMonth.getDate();
    		  //加日期
    		  	NextDay = addDay(months+"/"+days+"/"+years, Number(reckonDay));
    		  	years = NextDay.getFullYear();
    		  	months = NextDay.getMonth()+1;
    		  	days = NextDay.getDate();
    		  
    		  	if(Number(months) < 10){
    			  	months = "0"+months;
    		  	}
    		  	if(Number(days) < 10){
    		  		days = "0"+days;
    		  	}
    		  	var resDate = years+"-"+months+"-"+days;
    		  	$(this).parent().parent().find(".freeEndDay").html(resDate+'<input class="rents" type="hidden" value="'+resDate+'" name="freeEndDay"/>');
    	  	})
      	}countDateDay();
      
      	function addDay(date, day){
			var a = new Date(date)
			a = a.valueOf()
			a = a + day * 24 * 60 * 60 * 1000 -24
			a = new Date(a)
			return a;
      	}	
    		
		function addMonth(date, month){
			var a = new Date(date);
			a.setMonth(a.getMonth()+ month);
			return a;
		}
      
    	
    	function validateForm(){
			$("#mainDto_form").validate({ 
				onkeyup:false,
			    rules:{  
			    	"leasingResource":{required:true},  
			    	"contractNo":{required:true, maxlength:32 },  
			    	"customerName":{ required:true, maxlength:20},  
			    	"partyA":{required:true,maxlength:60},  
			    	"partyB":{required:true,maxlength:60},
			    	"startLeasingDay":{required:true, date:true},
			    	"endLeasingDay":{required:true, date:true, contractEndDate:true},
			    	"leasingForfeit":{required:true, number:true, leasingForfeitToFixed:true, maxlength:12, min:0},
			    	"shopForfeit":{required:true, number:true, shopForfeitToFixed:true, maxlength:12, min:0},
			    	"shopName":{maxlength:20},
			    	"freeArea":{number:true, freeAreaToFixed:true, maxlength:12, min:0},
			    	"floorArea":{number:true, floorAreaToFixed:true, maxlength:12, min:0},
			    	"innerArea":{number:true, innerAreaToFixed:true, maxlength:12, min:0},
			    	"trustees":{maxlength:20},
			    	"dateOfContract":{date:true},
			    	"customerMobile":{number:true, maxlength:11, minlength:11, checkMobile:true}
			    },
			    messages:{
			    	"leasingResource":{required:'必选'},  
			    	"customerMobile":{number:'请输入合法手机号', maxlength:"请输入合法手机号"}, 
			    	"contractNo":{required:'必填'},  
			    	"customerName":{required:'必填'},  
			    	"startLeasingDay":{required:'必填'},  
			    	"endLeasingDay":{required:'必填'},
			    	"leasingForfeit":{required:'必填'},
			    	"shopForfeit":{required:'必填'},  
			    	"partyA":{required:'必填', maxlength:'限输入1-60个字符' },
			    	"shopName":{maxlength: "限输入1-20个字符"},
			    	"partyB":{required:'必填', maxlength:'限输入1-60个字符' }
			    }
			});
		}validateForm();
		
    	//绑定表单验证方法$
    	$("#save_btn").click(function(){
    		var params = getParams();
    		if($("#mainDto_form").valid() && isSubmit == true && $("#costItemform").valid()){
    			saveContract(params);
    		}
    	});
		
		jQuery.validator.addMethod("contractEndDate", function(value, element) {
			var startDate = new Date($("input[name='startLeasingDay']").val().replace(/-/g,"/"));
			var endDate = new Date($("input[name='endLeasingDay']").val().replace(/-/g,"/"));
			var date = new Date(); 
			return endDate > startDate ? true : false;
		}, "结束日期不能小于或等于开始日期");
		jQuery.validator.addMethod("leasingForfeitToFixed", function(value, element) {
			$("input[name='leasingForfeit']").val(dealAmount(value));
			return true;
		}, "");
		jQuery.validator.addMethod("shopForfeitToFixed", function(value, element) {
			$("input[name='shopForfeit']").val(dealAmount(value));
			return true;
		}, "");
		jQuery.validator.addMethod("freeAreaToFixed", function(value, element) {
			$("input[name='freeArea']").val(dealAmount(value));
			return true;
		}, "");
		jQuery.validator.addMethod("floorAreaToFixed", function(value, element) {
			$("input[name='floorArea']").val(dealAmount(value));
			return true;
		}, "");
		jQuery.validator.addMethod("innerAreaToFixed", function(value, element) {
			$("input[name='innerArea']").val(dealAmount(value));
			return true;
		}, "");
		jQuery.validator.addMethod("checkMobile", function(value, element) {
			
			if(value != ""){
				if(value.substring(0,1) != "1"){
					return false;
				}else return true;
			}else return true;
		}, "手机号必须以1开头");
		function dealAmount(formatValue){
			return (Math.ceil(formatValue*100)/100).toFixed(2);
		}
		
		/**
		 * 保存合同
		 */
		//绑定表单验证方法
		function saveContract(params){
			console.log(JSON.stringify(params));
			$.ajax({
				type: "POST",
				url: CONTEXT+"contractManage/save",
				data: {params:JSON.stringify(params)},
				dataType: "json",
				success: function(data){
					if(data.success){
						$.eAlert("提示信息", "操作成功!");
						location.href="index#contractManage";
					} else {
						$.eAlert("提示信息", data.msg);
					}
				}
			});
		}
    	
    	
    	
    	
	});
}