function _call(templateUrl,param){
	$("#main-wrapper").loadPage(templateUrl,
			["lib/css/DT_bootstrap.css","css/form-imput.css","css/system.css","css/form-imput.css","css/resourMage.css"],
			["lib/jquery-migrate.js","js/common/pageBar.js","lib/jquery.tmpl.min.js","js/common/gd-util.js"],
  function(){
		var PAGE_SIZE = 10;
		var PAGE_SIZE_LEASE = 10;
		
		//其它资源初始化分页控件
		function initPageBar(result){
			// 分页工具组件
			$("#pagebar").page({
				pageIndex : 1,
				pageSize : PAGE_SIZE,
				total : result.total,
				callback : function(index, PAGE_SIZE){
					$('#templateBody').html(""); // 清空内容
					// 点击回调处理
					loadData({"pageNum":index,"pageSize":PAGE_SIZE}, false);
				}
			});
		}
		
		//其它资源加载数据
		function loadData(page, isInit){
			var para = $("#marketResource").serializeJson(); //自动将form表单封装成json 此方法在gd-utils.js中写着
			para=$.extend({},para,page);
//			console.debug(para);
			$.post(CONTEXT+"marketResource/queryOther",para,function(data){
				if(data.success){
					if(data.result && isInit){
						initPageBar(data.result);
					}
					$("#otherResourceBody").html("");
//					$('#marketResourceScript').tmpl({marketResources:data.result.recordList}).appendTo('#otherResourceBody');
					if(data.result && data.result.recordList && data.result.recordList.length>0){
						$('#marketResourceScript').tmpl({marketResources:data.result.recordList}).appendTo('#otherResourceBody');
						//绑定行点击背景事件
						$("tbody tr").bind("click",function(){
							var checkbox=$(this).find(":checkbox");
							if($(this).attr("style")){
								checkbox.prop("checked",false);
								$(this).attr("style","");
							}else{
								checkbox.prop("checked",true);
								$(this).attr("style","background:#eee");
							}
						});
					}else{
						$("#otherResourceBody").html("<tr><td colspan='9'>没有找到相关数据。</td></tr>");
					}
					
				} else {
					$.eAlert("提示",data.msg);
				}
			},"json");
		}
		
		//绑定行点击事件
		function initTrClickEvent(){
			$(" #otherResourceBody .ml-ct").click(function(){
				var bol=$(this).find(".Echeckbox").get(0).checked;
				if(bol==true){
					$(this).find(".Echeckbox").prop('checked', false);
					$(this).removeClass("color-eee");
				}else{
					$(this).find(".Echeckbox").prop('checked', true);
					$(this).addClass("color-eee");
				}
			});
			
			$(".Echeckbox").click(function(event){
				var bol=$(this).get(0).checked;
				if(bol==true){
					$(this).prop('checked', false);
				}else{
					$(this).prop('checked', true);
				}
			});
			
		}
		
		//绑定查询事件
		function initSearchEvent(){
			$("#query").click(function(){
				loadData({"pageNum":1,"pageSize":PAGE_SIZE}, true);
			});
		}
		
		//放租操作
		function releaseEvent(){
			$("#release").click(function(){
				//start
				var isvalid=false;  //检测当前页面是否有可放租的数据
				var trs=$("#otherResourceBody").find("tr");
				$.each(trs,function(x,y){
					if($(y).find("td:last").text() != '待放租'){
						$(y).find("td input:first").prop('checked',false);
					}else{
						isvalid=true;
					}
				});

				if(!isvalid){
					$("#checkAll").prop('checked',false);
					$.eAlert("提示","当前页面没有可放租资源!");
					return;
				}
				var checkboxs=$(":checkbox:checked"); 
				if(checkboxs.length==0){
					$.eAlert("提示","请选择租赁状态为【待放租】的行!");
					return;
				}

				checkboxs=$(":checkbox[id!=checkAll]:checked"); //所有选中的checkbox 
				var allLength=$(":checkbox[id!=checkAll]").length; //所有的列表数据checkbox的长度
				if(allLength!=checkboxs.length){
					$("#checkAll").prop('checked',false);
				}
				//end
				
				var idArray = $.map(checkboxs,function(x,y){return $(x).val()}).join(',');
				
				$.eConfirm("提示", "你确定放租吗？", function(){
					$.ajax({
						type : "post",
						traditional : true,
						url : CONTEXT+"marketResource/rent",
						data : {"idArray" : idArray},
						success : function (data) {
							if(data.success){
								$("#checkAll").prop('checked',false);
								loadData({"pageNum":1,"pageSize":PAGE_SIZE}, true);
								$.eAlert("提示信息", "放租成功");
							} else {
								$.eAlert("提示信息", data.msg);
							}
						}
					});
				});
			});
		}
		
		//回收操作
		function receiveEvent(){
			$("#receive").click(function(){
				//start
				var isvalid=false;  //检测当前页面是否有可放租的数据
				var trs=$("#otherResourceBody").find("tr");
				$.each(trs,function(x,y){
					if($(y).find("td:last").text()!='待租'){
						$(y).find("td input:first").prop('checked',false);
					}else{
						if($(y).find("td input:first").prop('checked')){
							var id=$(y).find("td input:first").val();
//							console.debug(id);
							$.ajaxSettings.async = false; //设置getJson同步
							$.getJSON(CONTEXT+"marketResource/validContractStatus/"+id,null,function(data){
//								console.debug(data.result);
								if(data.result){ //验证当前资源的是否关联了合同，并且状态为待执行，或者执行中，如果关联则不能进行回收操作
									$(y).find("td input:first").prop('checked',false);
								}else{
									isvalid=true;
								} 
							}); 
							$.ajaxSettings.async = false; //设置getJson同步
						}else{
							isvalid=true;
						}
					}
				});

				if(!isvalid){
					$("#checkAll").prop('checked',false);
					$.eAlert("提示","回收必须是待租资源且无关联未执行合同，当前页面没有可回收的数据!");
					return;
				}
				var checkboxs=$(":checkbox:checked"); 
				if(checkboxs.length==0){
					$.eAlert("提示","请选择租赁状态为【待租】的行!");
					return;
				}

				checkboxs=$(":checkbox[id!=checkAll]:checked"); //所有选中的checkbox 
				var allLength=$(":checkbox[id!=checkAll]").length; //所有的列表数据checkbox的长度
				if(allLength!=checkboxs.length){
					$("#checkAll").prop('checked',false);
				}

				//end
				var idArray = $.map(checkboxs,function(x,y){return $(x).val()}).join(',');
				
				$.eConfirm("提示", "你确定回收吗？", function(){
					$.ajax({
						type : "post",
						traditional : true,
						url : CONTEXT+"marketResource/recovery",
						data : {"idArray" : idArray},
						success : function (data) {
							if(data.success){
								$("#checkAll").prop('checked',false);
								loadData({"pageNum":1,"pageSize":PAGE_SIZE}, true);
								$.eAlert("提示信息", "回收成功");
							} else {
								$.eAlert("提示信息", data.msg);
							}
						}
					});
				});
			});
		}
		
		//绑定删除事件
		function initDeleteEvent(){
			$("#delete").click(function(){
				//start
				var isvalid=false;  //检测当前页面是否有可放租的数据
				var trs=$("#otherResourceBody").find("tr");
				$.each(trs,function(x,y){
					if($(y).find("td:last").text()!='待放租'){
						$(y).find("td input:first").prop('checked',false);
					}else{
						isvalid=true;
					}
				});

				if(!isvalid){
					$("#checkAll").prop('checked',false);
					$.eAlert("提示","只能对'待放租'的资源进行删除,当前页面没有可删除的数据!");
					return;
				}
				var checkboxs=$(":checkbox:checked"); 
				if(checkboxs.length==0){
					$.eAlert("提示","请选择'待放租'的资源行进行删除!");
					return;
				}

				checkboxs=$(":checkbox[id!=checkAll]:checked"); //所有选中的checkbox 
				var allLength=$(":checkbox[id!=checkAll]").length; //所有的列表数据checkbox的长度
				if(allLength!=checkboxs.length){
					$("#checkAll").prop('checked',false);
				}
				var idArray = $.map(checkboxs,function(x,y){return $(x).val()}).join(',');
				//end
				
				$.eConfirm("提示", "你确定删除吗？", function(){
					$.ajax({
						type : "post",
						traditional : true,
						url : CONTEXT+"marketResource/batchDelete",
						data : {"idArray" : idArray},
						success : function (data) {
							if(data.success){
								$("#checkAll").prop('checked',false);
								loadData({"pageNum":1,"pageSize":PAGE_SIZE}, true);
								$.eAlert("提示信息", "删除成功");
							} else {
								$.eAlert("提示信息", data.msg);
							}
						}
					});
				});
			});
		}

       
		//绑定全选、取消全选事件
		function initChecked(){
			$("#checkAll").click(function(){
				$("input[type='checkbox']").prop("checked",this.checked);
			});
		}
		
		//绑定选择区域，选择楼栋事件
		function initAreaBuildEvent(){
			var datas;
			$.ajaxSettings.async = false; //设置getJson同步
			$.getJSON(CONTEXT+"marketResource/area_build",null,function(data){
				datas=data.result;
				//console.debug(data);
				var areas='<option value="">全部区域</option>';
				if(datas){
					$.each(datas,function(x,y){
//						console.debug(y.id);
						areas+='<option value="'+y.id+'">'+y.name+'</option>';
					});
				}
				$("[name='areaId']").html(areas);
			});
			$.ajaxSettings.async = true;//设置getJson异步
			
			$("[name='areaId']").change(function(){
				var areaId=$(this).val();
				var builds=$.map(datas,function(x,y){if(x.id==areaId) return x.buildings});//根据id获取对应的楼栋
				var buildItem='<option value="">全部楼栋</option>';
				if(builds){
					$.each(builds,function(x,y){
						//console.debug(y.id);
						buildItem+='<option value="'+y.id+'">'+y.name+'</option>';
					});
				}
				$("[name='budingId']").html(buildItem);
				
			});
		}
		
		//查询全部其它资源类型
		function findAllOtherResType() {
			var buildItem='<option value="">全部其它资源类型</option>';
			$.ajax({
				type : "post",
				url : CONTEXT+"marketResourceType/findAllByCondition",
				data : {"status" : 1, "sysType" : 0},
				success : function (data) {
//					console.debug(data);
					if(data.success && data.result){
						var resTypeList = data.result;
						var optStr;
						for (var i=0; i<resTypeList.length; i++) {
							var resType = resTypeList[i];
							buildItem+='<option value=' + resType.id + '>' + resType.name + '</option>';
						}
					}
					$("[name='resourceTypeId']").html(buildItem);
				}
			});
		}
		
		$("#checkboxAll").click(function(){
			if($("#checkboxAll").get(0).checked){
				$(".Echeckbox").prop('checked', true);
				$(".costDefinition #templateBody .ml-ct").addClass("color-eee");
			}else{
				$(".Echeckbox").prop('checked', false);
				$(".costDefinition #templateBody .ml-ct").removeClass("color-eee");
			}
		});
		
		//绑定新增事件
		function initAddOrUpdateEvent(){
			$("#add").click(function(){
				$('#modal-setResource').modal('show');
			});
		}
		
		//绑定表单验证方法
		function validateForm(){
			$("#form-setResource").validate({  
			    rules:{  
			    	areaId:{  
			            required:true
			        },  
			        budingId:{  
			            required:true
			        },  
			        code:{  
			            required:true,
			            maxlength:100
			        },  
			        shortCode:{  
			            required:true,
			            maxlength:100
			        },  
			        name:{  
			            required:true,
			            maxlength:100
			        },  
			        unitId:{  
			        	required:true,
			        	range:[1,20]
			        },  
			        builtArea:{  
			        	required:true,
			        	range:[1,100000000]
			        },  
			        floorId:{  
			        	required:true,
			        	range:[1,200]
			        },  
			        innerArea:{  
			        	required:true,
			        	range:[1,100000000]
			        }, 
			        leaseArea:{  
			        	required:true,
			        	range:[1,100000000]
			        },
			        resourceTypeId:{  
			            required:true,
			        }, 
			        buildingUnitName:{
			        	required:true,
			        },
			        unitFloorName:{
			        	required:true,
			        },
			    },  
			    messages:{
			    	areaId:{  
			            required:'区域必选'
			        },  
			        budingId:{  
			            required:'楼栋必选'
			        },  
			        code:{  
			            required:'必填',
			            maxlength:'限输入1-100个字符'
			        },  
			        shortCode:{  
			            required:'必填',
			            maxlength:'限输入1-100个字符'
			        },  
			        name:{  
			            required:'必填',
			            maxlength:'限输入1-100个字符'
			        },  
			        unitId:{  
			        	required:'必填',
			        	range:'1-20之间的数值'
			        },  
			        builtArea:{  
			        	required:'必填',
			        	range:'范围：0-100000000'
			        },  
			        floorId:{  
			        	required:'必填',
			        	range:'限输入1-200之间的数值'
			        },  
			        innerArea:{  
			        	required:'必填',
			        	range:'范围：0-100000000'
			        }, 
			        leaseArea:{  
			        	required:'必填',
			        	range:'范围：0-100000000'
			        },
			        resourceTypeId:{  
			            required:'必填',
			        }, 
			        buildingUnitName:{
			        	 required:'必填',
			        },
			        unitFloorName:{
			        	required:'必填',
			        },
			    },  
			});
		}
		
		//表单保存
		function saveData(){
//			var param = $("#form-setResource").serializeObject(); //自动将form表单封装成json
			var param = $("#form-setResource").serializeJson(); //自动将form表单封装成json 此方法在gd-utils.js中写着
//			console.debug(param);
			var id = param.id;
//			console.debug("id : " + id);
			if (id) {
				$.post(CONTEXT+"marketResource/update", param, function(data){
					if(data.success){
						$.eAlert("提示","修改成功");
						/*$('#modal-setResource').modal("hide");
						$("#form-setResource").reset();//重置，将数据清空
						loadData({"pageNum":1,"pageSize":PAGE_SIZE}, true);*/
						location.href = 'index#otherResource';
						} else {
						$.eAlert("提示",data.msg);
					}
				},"json");
			} else {
				$.post(CONTEXT+"marketResource/add", param, function(data){
//					console.debug(data);
					if(data.success){
						$.eAlert("提示","保存成功");
						/*$('#modal-setResource').modal("hide");
						$("#form-setResource").reset();//重置，将数据清空
						loadData({"pageNum":1,"pageSize":PAGE_SIZE}, true);*/
						location.href = 'index#otherResource';
						} else {
						$.eAlert("提示",data.msg);
					}
				},"json");
			}
		}
		
		//初始化 viewOtherMarketResource.jsp 页面
		function initViewPage(){
			if(Route.params){
				var id=Route.params.id;
				if(id){
					$.getJSON(CONTEXT+"marketResource/view/"+id,null,function(data){
//						console.debug(data);
						if(data.result){
							$("#form-setResource").setForm(data.result);//将返回数据设置到文本框
							var resource =data.result.resourceMeasureUnions;
							$("#measurediv").hide();
							if(resource&&resource.length>0){
								$.each(resource,function(x,y){
									var td="<td>"+y.expenditure_name+"</td><td>"+y.measure_type_name+"</td><td>"+y.measure_type_code+"</td>";
									$("#measure_datas").html(td);
								});
								$("#measurediv").show();
							}
						}

					});
				}

			}
		}
		
		$.validator.setDefaults({
		    submitHandler: function() {
		    	saveData();
		    }
		});
		
		
		//租赁单初始化分页控件
		function initPageBarLease(result){
			// 分页工具组件
			$("#pagebarLease").page({
				pageIndex : 1,
				pageSize : PAGE_SIZE,
				total : result.total,
				callback : function(index, PAGE_SIZE){
					$('#leaseOrderBody').html(""); // 清空内容
					// 点击回调处理
					loadDataLease({"pageNum":index,"pageSize":PAGE_SIZE_LEASE}, false);
				}
			});
		}
		
		//租赁单加载数据
		function loadDataLease(page, isInit){
			page = $.extend({},{pageNum: 1, pageSize: 10},{leasingResourceId : Route.params.id});  //拼接传参
			$.post(CONTEXT+"contractLeasingresource/findByPage", page, function(data){
				if(data.success){
					if(isInit){
						initPageBarLease(data.result);
					}
					$("#leaseOrderBody").html("");
//					console.debug(data.result.recordList);
					$('#contractLeasingresourceScript').tmpl({contractLeasingresList : data.result.recordList}).appendTo('#leaseOrderBody');
				
				} else {
					$.eAlert("提示",data.msg);
				}
			},"json");
		}
		
		$("#leaseOrderBtn").click(function(){
			loadDataLease({"pageNum":1,"pageSize":PAGE_SIZE_LEASE}, true);
		});
		
		//点击租赁单查询
		function findLeaseOrder() {
			loadDataLease({"pageNum":1,"pageSize":PAGE_SIZE_LEASE}, true);
		}
		
		//绑定全选、取消全选事件
		function initChecked(){
			$("#checkAll").click(function(){
				var checkall=$("input[type='checkbox']");
                if(this.checked){
                	checkall.prop("checked",true);
                	$("tbody tr").attr("style","background:khaki");
                }else{
                	checkall.prop("checked",false);
                	$("tbody tr").attr("style","");
                }
			});
		}
		
		//默认初始化数据
		loadData({"pageNum":1,"pageSize":PAGE_SIZE}, true);
		
		initTrClickEvent();  //绑定复选框点击事件
		initSearchEvent(); //查询事件绑定
		initDeleteEvent();//删除事件绑定
		releaseEvent();//放租操作
		receiveEvent();//回收操作
		initChecked();//去选、取消全选事件
		initAreaBuildEvent();//动态联动区域和楼栋
		findAllOtherResType();  //根据条件查询所有资源类型
		initAddOrUpdateEvent();  // 新增or修改模态框
		validateForm();  //绑定表单校验
		initViewPage();  //初始化 viewOtherMarketResource.jsp 页面
	});
}

var resourceId;

//查看详情，编辑
function viewData(id){
	resourceId = id;
//	console.debug(CONTEXT);
	$.getJSON(CONTEXT+"marketResource/view/"+id,null,function(data){
//		 console.debug(data.result);
		if(data.result){
			$("#form-setResource").setForm(data.result);
			//关联计量表
			var resource =data.result.resourceMeasureUnions;
			$("#measurediv").hide();
			if(resource&&resource.length>0){
				$.each(resource,function(x,y){
					var td="<td>"+y.expenditure_name+"</td><td>"+y.measure_type_name+"</td><td>"+y.measure_type_code+"</td>";
					$("#measure_datas").html(td);
				});
				$("#measurediv").show();
			}
		}
	});
	$('#modal-setResource').modal('show');
}

//租赁单列表
/*$("#leaseOrderBtn").click(function(){
	alert("租赁单列表");
	loadDataLease({"pageNum":1,"pageSize":PAGE_SIZE}, true);
});
function leaseOrderClick() {
	alert("租赁单列表11");
	loadDataLease({"pageNum":1,"pageSize":PAGE_SIZE}, true);
}
//加载租赁单数据
function loadDataLease(page, isInit){
//var para = $("#marketResource").serializeObject(); //自动将form表单封装成json
 $.extend(page, {leasingResourceId : resourceId});
console.debug(page);
$.post(CONTEXT + "contractLeasingresource/findByPage",page, function(data){
	if(data.success){
		$("#leaseOrderBody").html("");
		if(isInit){
			initLeaseOrderPageBar(data.result);
		}
		$('#contractLeasingresourceScript').tmpl({contractLeasingresList:data.result.recordList}).appendTo('#leaseOrderBody');
	} else {
		alert(data.msg);
	}
},"json");
}
//初始化租赁单分页控件
function initLeaseOrderPageBar(result){
	console.debug("--------"+PAGE_SIZE);
// 分页工具组件
$("#leaseOrderPagebar").page({
	pageIndex : 1,
	pageSize : PAGE_SIZE,
	total : result.total,
	callback : function(index, PAGE_SIZE){
		$('#leaseOrderBody').html(""); // 清空内容
		// 点击回调处理
	
		loadDataLease({"pageNum":index,"pageSize":PAGE_SIZE}, false);
	}
});
}*/
