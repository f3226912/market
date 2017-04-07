function _call(templateUrl,param){
	$("#main-wrapper").loadPage(templateUrl,
			["css/form-imput.css","css/system.css","css/workflow.css","css/contract.css"],
			["lib/jquery.ztree.core.min.js","lib/jquery.ztree.exedit.min.js",
			 "js/common/pageBar.js","lib/jquery.tmpl.min.js","js/controller/selectPerson.js"],
			function(){
	   //放在已选用户里面
	   var userNm = $('.user_area');
	   var ztreNm = $('.ztree_list');
	   ztreNm.on('click','.listName', function() {
	   	if(!$(this).hasClass("currName")) {
	   		  var name=$(this).find(".list_sp").text();
	   		  var sp = $('<span class="sp_bg">'+name+'<i class="fa fa-times"></i></span>');
	   		  $(".user_area").append(sp)
	   		  $(this).addClass("currName");
	   		  sp.on('click',function() {
	   		  $(this).remove();
	   		})
	   	 }
	   	
	  })
	  
		var PAGE_SIZE = 20;
		var disableExport = false;
		var checked_row;
		var res_data;
		//查询条件
		var condition = {};
		
		//初始化分页控件
		function initPageBar(result){
			// 分页工具组件
			$("#pagebar").page({
				pageIndex : 1,
				pageSize : PAGE_SIZE,
				total : result.total,
				callback : function(pageNum,pageSize){
					
					$('#templateBody').html(""); // 清空内容
					// 点击回调处理
					loadData({"pageNum":pageNum,"pageSize":pageSize}, false);
				}
			});
		}
		
		//加载数据
		function loadData(page, isInit){
			$.getJSON(CONTEXT+"wfMonito/getList4Page",page,function(data){
				if(data.success){
					if(isInit){
						initPageBar(data.result);
					}
					$("#monitoBody").html("");
					if(data.result.total > 0){
						$('#monitoScript').tmpl({wfOrderList:data.result.recordList}).appendTo('#monitoBody');
					}else {
						$('#noDataScript').tmpl({wfOrderList:data.result.recordList}).appendTo('#monitoBody');
					}
					
					res_data = data.result.recordList;
					checked_row = undefined;
					initTrClickEvent();
				} else {
					$.eAlert("提示信息",data.msg);
				}
			});
		}
		//绑定行点击事件
		function initTrClickEvent(){
			$("#monitoBody tr").click(function(){
				$("#monitoBody tr").css({"background":"white"}); 
				$(this).css({"background":"#eeeeee"});
				checked_row = this.rowIndex - 1;
			});
		}
		//绑定查询事件
		$("#query").click(function(){
			var orderNo = $("#orderNo").val();
			var busType = $("#busType").val();
			condition = {
					"pageNum":1,"pageSize":PAGE_SIZE,
					"orderNo":orderNo,
					"busType":busType
			};
			loadData(condition, true);
		});
		$.getJSON(CONTEXT+"wfMonito/getWfBusTypeList",function(data){
			if(data.success){
				var obj = data.result;
				var sel = $("#busType");
				sel.empty();
				sel.append("<option value=''>全部</option>");
				for(var i=0;i<obj.length;i++){
					var option = $("<option></option>");
					option.val(data.result[i].busType).text(data.result[i].busTypeDesc).appendTo(sel);
				}
			} else {
				$.eAlert("提示信息",data.msg);
			}
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
			}
			;
		};
		//数据导出
		$("#exportData").click(function(){
			var orderNo = $("#orderNo").val();
			var busType = $("#busType").val();
			condition = {
					"pageNum":1,"pageSize":PAGE_SIZE,
					"orderNo":orderNo,
					"busType":busType
			};
			if (disableExport) {
				$.eAlert("已进行过一次数据导出,导出功能已禁用,请10秒钟过后再点...");	
				return;
			}
			disableExport = true;
			$.ajax({
				url : CONTEXT + 'wfMonito/checkExportParams',
				data : condition,
				type : 'post',
				success : function(data) {
					// 检测通过
					if (data.code==10000) {
							//slideMessage("数据正在导出中, 请耐心等待...");
							// 启动下载
							$.download(CONTEXT
									+ 'wfMonito/exportData',
									condition, 'post');
					} else {
						$.eAlert("提示信息",data.msg);
					}
				},
				error : function(data) {
					$.eAlert("提示信息",data.msg);
				}
			});
			// 10秒后导出按钮重新可用
			setInterval(function(){
				disableExport = false;
			}, 10000);
		});
		//默认初始化数据
		loadData({"pageNum":1,"pageSize":PAGE_SIZE}, true);
		var orderId = "";
		//绑定重设当前责任人事件
		$("#taskActorChange").click(function(){
			if(checked_row==undefined){
				$.eAlert("提示信息","请选中一行操作");
			}else{
				debugger;
				orderId = res_data[checked_row].id;
				//获取流程当前任务节点及责任人
				$.getJSON(CONTEXT+"wfMonito/getActorList",{"orderId":orderId},function(data){
					if(data.success){
						//是否有多个责任人
						var taskActorList = data.result;
						if(taskActorList.length > 1){//多个责任人
							$("#selectNtBody").html("");
							$('#selectNtScript').tmpl({taskActorList:taskActorList}).appendTo('#selectNtBody');
							//重置责任人(多人)弹出框
							$("#selectNt").modal();
						}else{
							$.selectPerson(
									{
										data:[{id:taskActorList[0].actorId,name:taskActorList[0].actorDesc}],
										callBack:function(res){
											var actorId = res[0].id;
											var taskId = taskActorList[0].taskId;
											var oldActor = taskActorList[0].actorId;
											var val = taskId+"-"+actorId+"-"+oldActor;
											var data = {
													"orderId":orderId,
													"val":val
											};
											actorChange(data);
										},
										select:'single',
										type:'person',
										actor:'原责任人'
							 	});
						}
					} else {
						$.eAlert("提示信息",data.msg);
					}
				});
				
			}
		});
		
		$("#terminate").on("click",function(){
			if(checked_row==undefined){
				$.eAlert("提示信息","请选中一行操作");
			}else{
				$(".workflow #box").modal();
			}
		})
		
		//流程作废
		function terminate(){
			var id = res_data[checked_row].id;
			var remark = $("#remark").val();
			var pramas = {
					"id":id,
					"remark":remark
			};
			$.post(CONTEXT+"wfMonito/terminate",pramas,function(data){
				if(data.success){
					$.eAlert("提示信息","流程作废成功");
					loadData({"pageNum":1,"pageSize":PAGE_SIZE}, true);
					$(".workflow #box").modal("hide");
					$("#terminateForm")[0].reset();
				}else{
					$.eAlert("提示信息",data.msg);
				}
			});
		};
		
		$("#terminateForm").validate({
		    rules:{  
		    	remark:{  
		            required:true,
		            maxlength:360
		        }, 
		    },  
		    messages:{ 
		    	remark:{  
		            required:'必填',
		            maxlength:'限输入1-360个字符'
		        } 
		    }, 
		    submitHandler: function() {
		    	terminate();
		    }
		});	
		//多个责任人时弹出用户选择框
		$("#selectNt").on("click",".select-ipt",function(){
			var oldActorId = $(this).attr("data-oid");
			var oldActorName = $(this).attr("data-oname");
			var taskId = $(this).attr("data-task");
			var $this =$(this);
			$.selectPerson(
					{
						data:[{id:oldActorId,name:oldActorName}],
						callBack:function(res){
							var actorId = res[0].id;
							var actorName = res[0].name;
							$this.val(actorName);
							$this.attr({ data:taskId+"-"+actorId+"-"+oldActorId});
						},
						select:'single',
						type:'person',
						actor:'原责任人'
			 	});
		});
		//多责任人重置保存事件
		$("#resetsSave").click(function(){
			var val = "";
			$(".select-ipt").each(function(){
				var data = $(this).attr("data");
				if(data == undefined){
					return;
				}
				val += data + ",";
			});
			var data = {
					"orderId":orderId,
					"val":val
			};
			actorChange(data);
			$("#selectNt").modal("hide");
		});
		
		//重置事件
		function actorChange(data){
			$.ajax({
				traditional : true,
				url : CONTEXT + 'wfMonito/taskActorChanged',
				data : data,
				type : 'post',
				success : function(data) {
					if(data.success){
						$.eAlert("提示信息","重置责任人成功");
						loadData({"pageNum":1,"pageSize":PAGE_SIZE}, true);
					}
				},
				error : function(data) {
					$.eAlert("提示信息",data);
				}
			});
		}
	});
}