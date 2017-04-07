function _call(templateUrl,param){
	$("#main-wrapper").loadPage(templateUrl,
			["lib/css/DT_bootstrap.css","css/system.css","css/resourMage.css"],
			["lib/jquery-migrate.js","js/common/pageBar.js","lib/jquery.tmpl.min.js","js/common/gd-util.js"],
			function(){

		var PAGE_SIZE =10;

		//初始化分页控件
		function initPageBar(result){
			// 分页工具组件
			$("#pagebar").page({
				pageIndex : 1,
				pageSize : PAGE_SIZE,
				total : result.total,
				callback : function(index,PAGE_SIZE){
					$('#templateBody').html(""); // 清空内容
					// 点击回调处理
					loadData({"pageNum":index,"pageSize":PAGE_SIZE}, false);
				}
			});
		}

		//加载数据
		function loadData(page, isInit){
			var para = $("#marketResource").serializeJson(); //自动将form表单封装成json 此方法在gd-utils.js中写着
			para=$.extend({},para,page);
			$.post(CONTEXT+"marketResource/query",para,function(data){
				console.debug(data);
				if(data.success){
					$("#marketResourceBody").html("");
					if(data.result&&isInit){
						initPageBar(data.result);
					}
					try{
						if(data.result&&data.result.recordList&&data.result.recordList.length>0){
							$('#marketResourceScript').tmpl({marketResources:data.result.recordList}).appendTo('#marketResourceBody');
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
							$("#marketResourceBody").html("<tr><td colspan='10'>没有找到相关数据。</td></tr>");
						}

					}
					catch(e){
						console.debug("此异常是因为页面没有此标签引起，无需处理");
					};

				} else {
					alert(data.msg);
				}
			},"json");
		}
		//绑定选择区域，选择楼栋事件
		function initAreaBuildEvent(){
			var datas;
			$.getJSON(CONTEXT+"marketResource/area_build",null,function(data){
				datas=data.result;
				//console.debug(data);
				var areas='<option value="">全部区域</option>';
				if(datas){
					$.each(datas,function(x,y){
						console.debug(y.id);
						areas+='<option value="'+y.id+'">'+y.name+'</option>';
					});
				}
				$("[name='areaId']").html(areas);
			});

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
		//绑定查询事件
		function initSearchEvent(){
			$("#query").click(function(){
				loadData({"pageNum":1,"pageSize":PAGE_SIZE}, true);
			});
		}
		//放租操作
		function releaseEvent(){
			$("#release").click(function(){
				var isvalid=false;  //检测当前页面是否有可放租的数据
				var trs=$("#marketResourceBody").find("tr");
				$.each(trs,function(x,y){
					if($(y).find("td:last").text()!='待放租'){
						$(y).find("td input:first").prop('checked',false);
						$(y).attr("style","")
					}else{
						isvalid=true;
					}
				});

				if(!isvalid){
					$("#checkAll").prop('checked',false);
					$.eAlert("提示","当前页面没有可放租的数据!");
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
				/*if(checkboxs.length==0){
					$.eAlert("提示","当前页面没有可放租的数据!");
					return;
				}*/

				var ids=$.map(checkboxs,function(x,y){return $(x).val()}).join(',');
				var para={"ids":ids,"leaseStatus":2,"oldStatus":1};
				$.eConfirm("提示","你确定放租吗？", function(){
					$.getJSON(CONTEXT+"marketResource/release_receive",para,function(data){

						if(data.success){
							$("#checkAll").prop('checked',false);
							loadData({"pageNum":1,"pageSize":$("#sizeSelect").val()}, false);
							$.eAlert("提示","放租成功!");
						} else {
							//alert(data.msg);
							$.eAlert("提示",data.msg);
						}
					});
				})
			});
		}
		//回收操作
		function receiveEvent(){
			$("#receive").click(function(){
				var isvalid=false;  //检测当前页面是否有可放租的数据
				var trs=$("#marketResourceBody").find("tr");
				$.each(trs,function(x,y){
					if($(y).find("td:last").text()!='待租'){
						$(y).find("td input:first").prop('checked',false);
						$(y).attr("style","");
					}else{
						if($(y).find("td input:first").prop('checked')){
							var id=$(y).find("td input:first").val();
							$.ajaxSettings.async = false; //设置getJson同步
							$.getJSON(CONTEXT+"marketResource/validContractStatus/"+id,null,function(data){
								console.debug(data.result);
								if(data.result){ //验证当前资源的是否关联了合同，并且状态为待执行，或者执行中，如果关联则不能进行回收操作
									$(y).find("td input:first").prop('checked',false);
									$(y).attr("style","");
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

				var ids=$.map(checkboxs,function(x,y){return $(x).val()}).join(',');

				var para={"ids":ids,"leaseStatus":1,"oldStatus":2};
				$.eConfirm("提示","你确定回收吗？", function(){
					$.getJSON(CONTEXT+"marketResource/release_receive",para,function(data){

						if(data.success){
							$("#checkAll").prop('checked',false);
							loadData({"pageNum":1,"pageSize":$("#sizeSelect").val()}, false);
							$.eAlert("提示","回收成功!");
						} else {
							alert(data.msg);
						}
					});
				})
			});
		}
		//绑定删除事件
		function initDeleteEvent(){
			$("#delete").click(function(){
				var isvalid=false;  //检测当前页面是否有可放租的数据
				var trs=$("#marketResourceBody").find("tr");
				$.each(trs,function(x,y){
					if($(y).find("td:last").text()!='待放租'){
						$(y).find("td input:first").prop('checked',false);
						$(y).attr("style","");
					}else{
						isvalid=true;
					}
				});

				if(!isvalid){
					$("#checkAll").prop('checked',false);
					$.eAlert("提示","只能对'只能对待放租状态的资源进行删除,当前页面没有可删除的数据!");
					return;
				}
				var checkboxs=$(":checkbox:checked"); 
				if(checkboxs.length==0){
					$.eAlert("提示","只能对待放租状态的资源进行删除，待租、已租状态的资源不能删除!");
					return;
				}

				checkboxs=$(":checkbox[id!=checkAll]:checked"); //所有选中的checkbox 
				var allLength=$(":checkbox[id!=checkAll]").length; //所有的列表数据checkbox的长度
				if(allLength!=checkboxs.length){
					$("#checkAll").prop('checked',false);
				}
				var ids=$.map(checkboxs,function(x,y){return $(x).val()}).join(',');
				$.eConfirm("提示","你确定删除吗？", function(){
					$.getJSON(CONTEXT+"marketResource/remove",{"ids":ids},function(data){
						if(data.success){
							$("#checkAll").prop('checked',false);
							$.eAlert("提示","删除成功");
							loadData({"pageNum":1,"pageSize":$("#sizeSelect").val()}, false);
						} else {
							alert(data.msg);
						}
					});
				});
			});
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

		//绑定新增事件
		function initAddOrUpdateEvent(){
			$("#add").click(function(){
				$("#form-setResource")[0].reset();//先重置表单数据，再来重新组装
				$('#modal-setResource').modal('show');
			});
		}


		//详情
		loadData({"pageNum":1,"pageSize":PAGE_SIZE}, true);//默认初始化数据
		initAreaBuildEvent();//绑定区域和楼栋
		initSearchEvent(); //查询事件绑定
		initDeleteEvent();//删除事件绑定
		releaseEvent();//放租操作
		receiveEvent();//回收操作
		initAddOrUpdateEvent();//添加,修改，查看操作
		initChecked();//去选、取消全选事件
	
		
	});
}

//查看详情，编辑  此function暂时不用
function viewData(id){
	//console.debug(CONTEXT);
	$.getJSON(CONTEXT+"marketResource/view/"+id,null,function(data){
		console.debug(data.result);
		if(data.result){
			$("#form-setResource")[0].reset();//先重置表单数据，再来重新组装
			$("#form-setResource").setForm(data.result);//将返回数据设置到文本框
			var resource =data.result.resourceMeasureUnions;
			$("#measurediv").hide();
			if(resource){
				$.each(resource,function(x,y){
					var td="<tr><td>"+y.expenditure_name+"</td><td>"+y.measure_type_name+"</td><td>"+y.measure_type_code+"</td></tr>";
					$("#measure_datas").html(td);
				});
				$("#measurediv").show();
			}
		}

	});
	$('#modal-setResource').modal('show');
}



