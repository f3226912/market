function _call(templateUrl,param){
	$("#main-wrapper").loadPage(templateUrl,
			["lib/css/DT_bootstrap.css","css/form-imput.css","css/system.css","css/form-imput.css","css/resourMage.css"],
			["lib/jquery-migrate.js","js/common/pageBar.js","lib/jquery.tmpl.min.js","js/common/gd-util.js"],
  function(){
		var PAGE_SIZE = 10;
		
		//租赁单初始化分页控件
		function initPageBar(result){
			// 分页工具组件
			$("#pagebar").page({
				pageIndex : 1,
				pageSize : PAGE_SIZE,
				total : result.total,
				callback : function(index, PAGE_SIZE){
					$('#leaseOrderBody').html(""); // 清空内容
					// 点击回调处理
					loadData({"pageNum":index,"pageSize":PAGE_SIZE}, false);
				}
			});
		}
		
		//租赁单加载数据
		function loadData(page, isInit){
			page = $.extend({}, page, {resourceId : Route.params.id});  //拼接传参
			$.post(CONTEXT+"marketLeasePostage/findByPage", page, function(data){
				if(data.success){
					if(isInit){
						initPageBar(data.result);
					}
					$("#leaseOrderBody").html("");
					if(data.result && data.result.recordList) {
						$('#contractLeasingresourceScript').tmpl({contractLeasingresList : data.result.recordList}).appendTo('#leaseOrderBody');
					} else {
						$("#leaseOrderBody").html("<tr><td colspan='11'>没有找到相关数据。</td></tr>");
					}
				} else {
					$.eAlert("提示", data.msg);
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
			$.ajax({
				async: false,
				type : "post",
				url : CONTEXT+"marketResourceType/findAllByCondition",
				data : {"status" : 1, "sysType" : 0},
				success : function (data) {
					if(data.success && data.result){
						var resTypeList = data.result;
						var optStr;
						var buildItem='<option value="">全部其它资源类型</option>';
						for (var i=0; i<resTypeList.length; i++) {
							var resType = resTypeList[i];
							buildItem+='<option value=' + resType.id + '>' + resType.name + '</option>';
						}
						$("[name='resourceTypeId']").html(buildItem);
					}
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
			            maxlength:100,
			            checkCode :true
			        },  
			        shortCode:{  
			            required:true,
			            maxlength:100
			           // checkShortCode :true
			        },  
			        name:{  
			            required:true,
			            maxlength:100,
			            checkName :true
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
			            required:true
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
			        	range:'范围：0-100000000',
						min:'不少于1的数值'
			        },  
			        floorId:{  
			        	required:'必填',
			        	range:'限输入1-200的数值',
			        },  
			        innerArea:{  
			        	required:'必填',
			        	range:'范围：0-100000000',
						min:'不少于1的数值'
			        }, 
			        leaseArea:{  
			        	required:'必填',
			        	range:'范围：0-100000000',
						min:'不少于1的数值'
			        },
			        resourceTypeId:{  
			            required:'资源类型必选'
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
			
			/*var param = $("#form-setResource").serializeJson(); //自动将form表单封装成json 此方法在gd-utils.js中写着
			console.debug(param);
			var id = param.id;
			console.debug("id : " + id);
			if (id) {
				$.post(CONTEXT+"marketResource/update", param, function(data){
					if(data.success){
						$.eAlert("提示","修改成功");
						$('#modal-setResource').modal("hide");
						$("#form-setResource").reset();//重置，将数据清空
						loadData({"pageNum":1,"pageSize":PAGE_SIZE}, true);
						location.href = 'index#otherResource';
						} else {
						$.eAlert("提示",data.msg);
					}
				},"json");
			} else {
				alert("add");
				$.post(CONTEXT+"marketResource/add", param, function(data){
					if(data.success){
						$.eAlert("提示","保存成功");
						location.href = 'index#otherResource';
						} else {
						$.eAlert("提示",data.msg);
					}
				},"json");
			}*/
			$.eLoading(true);
			var para = $("#form-setResource").serializeJson(); //自动将form表单封装成json 此方法在gd-utils.js中写着
			$.post(CONTEXT+"marketResource/saveOther",para,function(data){
//				console.debug(para);
				if(data.success){
					loadData({"pageNum":1,"pageSize":PAGE_SIZE}, true);
					$.eAlert("提示","保存成功");
					location.href='index#otherResource';
				} else {
					$.eAlert("提示",data.msg);
				}
			},"json");
			$.eLoading(false);
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
					//如果是修改，租赁状态不能改变
					var domObj = $("[name=leaseStatus]")[0];
					$(domObj).attr("disabled", true);
				}else{
					$("#leaseOrderBtn").remove(); //如果是新增数据，则不需要显示租赁单选项卡
				}

			} else {
				location.href = 'index#otherResource';
			}
			
		}
		
		$.validator.setDefaults({
		    submitHandler: function() {
		    	saveData();
		    }
		});
		
		//点击租赁单查询
		function findLeaseOrder() {
			loadData({"pageNum":1,"pageSize":PAGE_SIZE}, true);
		}
		
		$("#leaseOrderBtn").click(function(){
			loadData({"pageNum":1,"pageSize":PAGE_SIZE}, true);
		});
		
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
		
		initTrClickEvent();  //绑定复选框点击事件
		initSearchEvent(); //查询事件绑定
		initChecked();//去选、取消全选事件
		initAreaBuildEvent();//动态联动区域和楼栋
		findAllOtherResType();  //根据条件查询所有资源类型
		validateForm();  //绑定表单校验
		initViewPage();  //初始化 viewOtherMarketResource.jsp 页面
	});
}


