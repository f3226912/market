function _call(templateUrl, param) {
	$("#main-wrapper").loadPage(templateUrl, 
			["lib/css/DT_bootstrap.css","css/form-imput.css","css/system.css","css/form-imput.css","css/resourMage.css"],
			["lib/jquery-migrate.js","js/common/pageBar.js","lib/jquery.tmpl.min.js","js/common/gd-util.js"],
		function() {
		
			if(!Route.params){
				location.href = "index#marketResourceType";
				return;
			}
		
				var params = Route.params;
				
				// 获取页面传递的marketResourceTypeId
				var marketResourceTypeId = params.marketResourceTypeId;
				console.debug(marketResourceTypeId);

				// 查询所有市场到下拉框
//				fillMarketSelect();
				
				// 填充
				getMarketResourceTypeById(marketResourceTypeId);
				

				// 表单保存
//				$("#saveForm").click(function() {
				function saveData() {
					$.eLoading(true);
					$.ajax({
						type : "POST",
						dataType : "json",
						url : CONTEXT + "marketResourceType/update",
						data : $('#form-editMarketResourceType').serialize(),
						success : function(data) {
							if (data.result) {
								$.eAlert("提示：", "修改资源类型成功");
								window.location = "index#marketResourceType";
							} else {
								$.eAlert("提示", data.msg);
							}
						}
					});
					$.eLoading(false);
//				});
				}
				
				
				//绑定表单验证方法
				function validateForm(){
					$("#form-editMarketResourceType").validate({  
					    rules:{  
					    	code:{  
					            required:true,
					            maxlength:50,
					            checkCode :true
					        },  
					        name:{  
					            required:true,
					            maxlength:50,
					            checkName :true
					        },  
					        sort:{  
					        	required:true,
					        	min:1,
					        	checkSort :true
					        },  
					    },  
					    messages:{
					        code:{  
					            required:'必填',
					            maxlength:'限输入1-50个字符'
					        },  
					        name:{  
					            required:'必填',
					            maxlength:'限输入1-100个字符'
					        },  
					        sort:{  
					        	required:'必填',
					        	min:"最小为1"
					        },  
					    },  
					});
				}
				
				$.validator.setDefaults({
				    submitHandler: function() {
				    	//alert("提交事件!");
				    	saveData();
				    }
				});
				
				//校验code
				jQuery.validator.addMethod("checkCode", function(value, element) {
					var id = $("input[name='id']").val();
					var code = $("input[name='code']").val();
				    var deferred = $.Deferred();//创建一个延迟对象
				    $.ajax({
				    	type : "post",
				    	url: CONTEXT+"marketResourceType/updateValidator",
				    	data : {"id" : id, "type" : "code", "code" : code, "status" : 1},
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
				}, "编号已存在");
				
				//校验name
				jQuery.validator.addMethod("checkName", function(value, element) {
					var id = $("input[name='id']").val();
					var name = $("input[name='name']").val();
				    var deferred = $.Deferred();//创建一个延迟对象
				    $.ajax({
				    	type : "post",
				    	url: CONTEXT+"marketResourceType/updateValidator",
				    	data : {"id" : id, "type" : "name", "name" : name, "status" : 1},
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
				}, "名称已存在");
				
				//校验code
				jQuery.validator.addMethod("checkSort", function(value, element) {
					var id = $("input[name='id']").val();
					var sort = $("input[name='sort']").val();
				    var deferred = $.Deferred();//创建一个延迟对象
				    $.ajax({
				    	type : "post",
				    	url: CONTEXT+"marketResourceType/updateValidator",
				    	data : {"id" : id, "type" : "sort", "sort" : sort, "status" : 1},
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
				}, "序号已存在");
				
				validateForm();  //绑定表单校验

			});

	function fillMarketSelect() {
		$.ajax({
			type : "POST",
			dataType : "json",
			url : CONTEXT + "marketResourceType/getAllMarket",
			success : function(data) {
				var marketList = data.result;
				if (marketList.length > 0) {
					$(marketList).each(
							function(index) {
								var marketId = marketList[index].id;
								var marketCode = marketList[index].code;
								var marketName = marketList[index].name;
								var option = $("<option>").val(marketId).text(
										marketCode + "--" + marketName);
								$("select[name='marketId']").append(option);
							});
					//
					getMarketResourceTypeById();
				}
			},
			error : function(data) {
				$.eAlert("提示：", data.msg);
			}
		});
	}

	function getMarketResourceTypeById(marketResourceTypeId) {
		$.ajax({
			type : "GET",
			dataType : "json",
			url : CONTEXT + "marketResourceType/getMarketResTypeById?id=" + marketResourceTypeId,
			success : function(data) {
				var marketResourceType = data.result;
				$("input[name='id']").val(marketResourceType.id);
				$("input[name='code']").val(marketResourceType.code);
				$("input[name='name']").val(marketResourceType.name);
				$("input[name='sort']").val(marketResourceType.sort);
//				$("input[name='marketId']").val(marketResourceType.id);

			},
			error : function(data) {
				$.eAlert("提示：", data.msg);
			}
		});
	}

}