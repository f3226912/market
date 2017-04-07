function _call(templateUrl,param){
	$("#main-wrapper").loadPage(templateUrl,
			["css/form-imput.css","css/workflow.css","lib/css/metroStyle.css","css/contract.css"],
			["js/controller/selectPerson.js","js/common/pageBar.js","lib/jquery.tmpl.min.js"],
	function(){
		var PAGE_SIZE = 20;
		var disableExport = false;
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
			$.getJSON(CONTEXT+"wfProcessDefine/getList4Page",page,function(data){
				if(data.success){
					if(isInit){
						initPageBar(data.result);
					}
					$("#wfProcessBody").html("");
					if(data.result.total > 0){
						$('#wfProcessScript').tmpl({wfProcessList:data.result.recordList}).appendTo('#wfProcessBody');
					}else{
						$('#noDataScript').tmpl({wfProcessList:data.result.recordList}).appendTo('#wfProcessBody');
					}
				} else {
					$.eAlert("提示信息",data.msg);
				}
			});
		}
		//绑定查询事件
		$("#query").click(function(){
			var displayName = $("#displayName").val();
			var busType = $("#busType").val();
			var state = $("#state").val();
			condition = {
					"pageNum":1,"pageSize":PAGE_SIZE,
					"displayName":displayName,
					"busType":busType,
					"state":state
			};
			loadData(condition, true);
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
			var displayName = $("#displayName").val();
			var busType = $("#busType").val();
			var state = $("#state").val();
			condition = {
					"pageNum":1,"pageSize":PAGE_SIZE,
					"displayName":displayName,
					"busType":busType,
					"state":state
			};
			if (disableExport) {
				$.eAlert("提示信息","已进行过一次数据导出,导出功能已禁用,请10秒钟过后再点...");
				return;
			}
			disableExport = true;
			$.ajax({
				url : CONTEXT + 'wfProcessDefine/checkExportParams',
				data : condition,
				type : 'post',
				success : function(data) {
					// 检测通过
					if (data.code==10000) {
							//slideMessage("数据正在导出中, 请耐心等待...");
							// 启动下载
							$.download(CONTEXT
									+ 'wfProcessDefine/exportData',
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
		
		//获取业务类型
		$.getJSON(CONTEXT+"wfProcessDefine/getWfBusTypeList",function(data){
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
				eAlert("提示信息",data.msg);
			}
		});
	});
}