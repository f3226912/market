function _call(templateUrl,param){
	$("#main-wrapper").loadPage(templateUrl,
			["lib/css/DT_bootstrap.css","css/form-imput.css","css/system.css","css/form-imput.css","css/resourMage.css"],
			["lib/jquery-migrate.js","js/common/pageBar.js","lib/jquery.tmpl.min.js","js/common/gd-util.js"],
			function(){

		var PAGE_SIZE = 10;

		//初始化分页控件
		function initPageBar(result){
			// 分页工具组件
			$("#pagebar").page({
				pageIndex : 1,
				pageSize : PAGE_SIZE,
				total : result.total,
				callback : function(index,PAGE_SIZE){
//					$('tbody[name="leaseOrderBody"]').html(""); // 清空内容
					$("#leaseOrderBody").html("");
					// 点击回调处理
					loadData({"pageNum":index,"pageSize":PAGE_SIZE}, false);
				}
			});
		}
		
		//其它资源加载数据
		function loadData(page, isInit){
			page = $.extend({}, page, {resourceId : Route.params.id});  //拼接传参
			$.post(CONTEXT+"marketLeasePostage/findByPage", page, function(data){
				if(data.success){
					if(isInit){
						initPageBar(data.result);
					}
//					$('tbody[name="leaseOrderBody"]').html("");
					$("#leaseOrderBody").html("");
					try{
						console.debug();
						if(data.result && data.result.recordList) {
							$('#contractLeasingresourceScript').tmpl({contractLeasingresList : data.result.recordList}).appendTo('#leaseOrderBody');
						} else {
							$("#leaseOrderBody").html("<tr><td colspan='11'>没有找到相关数据。</td></tr>");
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
		
		$("li[name='marketResourceBtn']").click(function(){
			//店铺section和停车位section切换
			var typeid = Route.params.removeMarketType;
			var id = Route.params.id;
			if(typeid){
				$("#"+typeid).remove();
			}
			
			loadData({"pageNum":1,"pageSize":PAGE_SIZE}, true);
		});
		
		
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
						console.debug(y.id);
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

		//绑定全选、取消全选事件
		function initChecked(){
			$("#checkAll").click(function(){
				$("input[type='checkbox']").prop("checked",this.checked);
			});

		}

		//绑定新增事件
		function initAddOrUpdateEvent(){
			$("#add").click(function(){
				$("#form-setResource")[0].reset();//先重置表单数据，再来重新组装
				$('#modal-setResource').modal('show');
			});
		}

		//绑定表单验证方法
		function validateForm(){
			$("#form-setResource").validate({  
				rules:{  
					code:{  
			            checkCode :true
			        }, 
			     /*   shortCode:{  
			            checkShortCode :true
			        },  */
			        name:{  
			            checkName :true
			        },  
					builtArea:{  
						range:[1,100000000]
					},  
					innerArea:{  
						range:[1,100000000]
					}, 
					leaseArea:{  
						range:[1,100000000]
					} 
				},  
				messages:{
					builtArea:{  
						range:'范围：0-100000000',
						min:'不少于1的数值'
					},  
					innerArea:{  
						range:'范围：0-100000000',
						min:'不少于1的数值'
					}, 
					leaseArea:{  
						range:'范围：0-100000000',
						min:'不少于1的数值'
					}  
				},  
			});
			$("#form-setCarResource").validate({  
				rules:{  
					builtArea:{  
						range:[1,100000000]
					},  
					innerArea:{  
						range:[1,100000000]
					}, 
					leaseArea:{  
						range:[1,100000000]
					} 
				},  
				messages:{
					builtArea:{  
						range:'范围：0-100000000',
						min:'不少于1的数值'
					},  
					innerArea:{  
						range:'范围：0-100000000',
						min:'不少于1的数值'
					}, 
					leaseArea:{  
						range:'范围：0-100000000',
						min:'不少于1的数值'
					}  
				},  
			});
		}
		//表单保存
		function saveData(){
			$.eLoading(true);
			var para="";
            if($("#form-setResource").serializeJson().hasOwnProperty('id')){
            	 para = $("#form-setResource").serializeJson(); //自动将form表单封装成json 此方法在gd-utils.js中写着
            }else{
            	 para = $("#form-setCarResource").serializeJson(); 
            }
			$.post(CONTEXT+"marketResource/save",para,function(data){
				console.debug(para);
				if(data.success){
					loadData({"pageNum":1,"pageSize":PAGE_SIZE}, true);
					$.eAlert("提示","保存成功");
					location.href='index#'+Route.params.currentype;
					//$('#modal-setResource').modal("hide");
					//$("#form-setResource").reset();//重置，将数据清空
				} else {
					$.eAlert("提示",data.msg);
				}
			},"json");
			$.eLoading(false);
		}

		//初始化 viewMarketArea.jsp 页面
		function initViewPage(){
			
			if(Route.params&&Route.params.removeMarketType){
				var typeid=Route.params.removeMarketType;
				var id=Route.params.id;
				if(typeid){
				  $("#"+typeid).remove();
				}
				if(id){
					$("[name=leaseStatus]").prop("disabled",true);
					$.getJSON(CONTEXT+"marketResource/view/"+id,null,function(data){
						console.debug(data.result);
						if(data.result){
							$("#form-setResource").setForm(data.result);//将返回数据设置到文本框
							$("#form-setCarResource").setForm(data.result);//将返回数据设置到文本框
							var resource =data.result.resourceMeasureUnions;
							$("#measurediv").hide();
							if(resource&&resource.length>0){
								$.each(resource,function(x,y){
									var td="<tr><td>"+y.expenditure_name+"</td><td>"+y.measure_type_name+"</td><td>"+y.measure_type_code+"</td></tr>";
									$("#measure_datas").html(td);
								});
								$("#measurediv").show();
							}
						}
					});
				}else{
					$("[name=leaseStatus]").find("option[value=3]").remove();//新增的时候去除已租状态
					$("[name=marketResourceBtn]").remove(); //如果是新增数据，则不需要显示租赁单选项卡
				}

			}else
			{
				location.href='index#marketResource';
			}

		}
		
		//详情
//		loadData({"pageNum":1,"pageSize":PAGE_SIZE}, true);//默认初始化数据
		
		$.validator.setDefaults({
			submitHandler: function() {
				//alert("提交事件!");
				saveData();
			}
		});
		
		initAreaBuildEvent();//绑定区域和楼栋
		initViewPage();
		initAddOrUpdateEvent();//添加,修改，查看操作
		initChecked();//去选、取消全选事件
		validateForm();//绑定表单验证方法
		
		$("#btn_cancel").click(function(){
			if(Route.params.currentype){
			location.href='index#'+Route.params.currentype;
			}
			else{
			location.href='index#marketResource';
			}
		});//取消按钮
		
		//校验code
		jQuery.validator.addMethod("checkCode", function(value, element) {
			var url, data;
			var code = $("input[name='code']").val();
			var id = $("input[name='id']").val();
			//有id说明是更新，无则新增
			if (id) {
				url = CONTEXT+"marketResource/updateValidator";
				data = {"id" : id, "type" : "code", "code" : code, "status" : 1};
			} else {
				url = CONTEXT+"marketResource/findAllCountByCondition";
				data = {"code" : code, "status" : 1};
			}
		    var deferred = $.Deferred();//创建一个延迟对象
		    $.ajax({
		    	type : "post",
		    	url: url,
		    	data : data,
		        async:false,//要指定不能异步,必须等待后台服务校验完成再执行后续代码
		        dataType:"json",
		        success:function(data) {
		        	if (data.result) {
		        		if (data.data != 0) {
		        			deferred.reject();
		        		} else {
		        			deferred.resolve();
		        		}
		        	} else {
		        		deferred.reject();
		        	}
		        }
		    });
		    //deferred.state()有3个状态:pending:还未结束,rejected:失败,resolved:成功
		    return deferred.state() == "resolved" ? true : false;
		}, "资源编码已存在");
		
		//校验shortCode
		jQuery.validator.addMethod("checkShortCode", function(value, element) {
			var url, data;
			var shortCode = $("input[name='shortCode']").val();
			var id = $("input[name='id']").val();
			//有id说明是更新，无则新增
			if (id) {
				url = CONTEXT+"marketResource/updateValidator";
				data = {"id" : id, "type" : "shortCode", "shortCode" : shortCode, "status" : 1};
			} else {
				url = CONTEXT+"marketResource/findAllCountByCondition";
				data = {"shortCode" : shortCode, "status" : 1};
			}
		    var deferred = $.Deferred();//创建一个延迟对象
		    $.ajax({
		    	type : "post",
		    	url: url,
		    	data : data,
		        async:false,//要指定不能异步,必须等待后台服务校验完成再执行后续代码
		        dataType:"json",
		        success:function(data) {
		        	if (data.result) {
		        		if (data.data != 0) {
		        			deferred.reject();
		        		} else {
		        			deferred.resolve();
		        		}
		        	} else {
		        		deferred.reject();
		        	}
		        }
		    });
		    //deferred.state()有3个状态:pending:还未结束,rejected:失败,resolved:成功
		    return deferred.state() == "resolved" ? true : false;
		}, "资源简码已存在");
		
		//校验name
		jQuery.validator.addMethod("checkName", function(value, element) {
			var url, data;
			var name = $("input[name='name']").val();
			var id = $("input[name='id']").val();
			//有id说明是更新，无则新增
			if (id) {
				url = CONTEXT+"marketResource/updateValidator";
				data = {"id" : id, "type" : "name", "name" : name, "status" : 1};
			} else {
				url = CONTEXT+"marketResource/findAllCountByCondition";
				data = {"name" : name, "status" : 1};
			}
			console.debug(data);
		    var deferred = $.Deferred();//创建一个延迟对象
		    $.ajax({
		    	type : "post",
		    	url: url,
		    	data : data,
		        async:false,//要指定不能异步,必须等待后台服务校验完成再执行后续代码
		        dataType:"json",
		        success:function(data) {
		        	if (data.result) {
		        		if (data.data != 0) {
		        			deferred.reject();
		        		} else {
		        			deferred.resolve();
		        		}
		        	} else {
		        		deferred.reject();
		        	}
		        }
		    });
		    //deferred.state()有3个状态:pending:还未结束,rejected:失败,resolved:成功
		    return deferred.state() == "resolved" ? true : false;
		}, "资源名称已存在");
		
	});
}

//查看详情，编辑  此function暂时不用
function viewData(id){
	console.debug(CONTEXT);
	$.getJSON(CONTEXT+"marketResource/view/"+id,null,function(data){
		console.debug(data);
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



