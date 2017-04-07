function _call(templateUrl,param){

	$("#main-wrapper").loadPage(templateUrl,
			["lib/css/DT_bootstrap.css","css/contract.css"],
			["lib/jquery-migrate.js","js/common/pageBar.js","lib/jquery.tmpl.min.js"],
			function(){

	   var PAGE_SIZE = 20;
		
	
		
		//初始化分页控件
		function initPageBar(result){
			// 分页工具组件
			$("#pagebar").page({
				pageIndex : 1,
				pageSize : PAGE_SIZE,
				total : result.total,
				callback : function(index, size){
					//console.log(size)
					$('#templateBody').html(""); // 清空内容
					// 点击回调处理
					//if(checkMarket()){
					PAGE_SIZE=size;
					loadData({"pageNum":index,"pageSize":size,"type":"0"}, false);
					//}
				}
			});
		}
		
		//加载数据
		function loadData(page, isInit){
				var leasingResource = $("#leasingResource").val();
				var contractNo = $("#contractNo").val();
				var contractStatus = $("#contractStatus").val();
				//组装参数
				
				page.type=0;
				page.leasingResource=leasingResource;
				page.contractNo=contractNo;
				page.contractStatus=contractStatus;
			
//			$.getJSON(CONTEXT+"marketResourceType/findByPage",page,function(data){
			$.post(CONTEXT+"biContractMain/findByPage",page,function(data) {
				if(data.success){
					if(isInit){
						initPageBar(data.result);
					}
					$("#templateBody").html("");
					console.log(data.result.data);
					$('#template').tmpl({contractList:data.result.recordList}).appendTo('#templateBody');
					
					//单页选择显示数下拉框事件绑定
					$("#sizeSelect").change(function(){
						queryData2();
					});
				} else {
					alert(data.msg);
				}
			});
		}
		
		//绑定查询事件
		var condition = {};
	    var disableExport = false;


		//绑定数据导出按钮事件
		$("#exportData").click(
				function() {
					//if(checkMarket()){

					//获取搜索栏搜索条件值
					var leasingResource = $("#leasingResource").val();
					var contractNo = $("#contractNo").val();
					var contractStatus = $("#contractStatus").val();
					//组装参数
					
					condition = {
							"leasingResource":leasingResource,
							"contractNo":contractNo,
							"contractStatus":contractStatus,
							"type":"0"
					};
					if (disableExport) {
						alert("已进行过一次数据导出,导出功能已禁用,请10秒钟过后再点...");	
						return;
					}
				                 	disableExport = true;
		                            //进行导出结果集大小校验
				    				$.ajax({
										url : CONTEXT + 'biContractMain/checkExportParams',
										data : condition,
										type : 'post',
										success : function(data) {
											// 检测通过
											if (data.code==10000) {
													//slideMessage("数据正在导出中, 请耐心等待...");
													// 启动下载
												
													$.download(CONTEXT
															+ 'biContractMain/export',
															condition, 'post');
											} else {
												alert(data.msg);
											}
										},
										error : function(data) {
											alert(data);
										}
									});
							
					// 10秒后导出按钮重新可用
					setInterval(function(){
						disableExport = false;
					}, 10000);
					//}
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
		//绑定删除事件
	
       
		//绑定新增事件
		//搜索按钮事件绑定
		$("#query").click(function(){

			queryData2();
		});
		
		
		function queryData2(){
			var leasingResource = $("#leasingResource").val();
			var contractNo = $("#contractNo").val();
			var contractStatus = $("#contractStatus").val();
			PAGE_SIZE = $("#sizeSelect").val();

			//组装参数
			 condition = {
					"pageNum":1, "pageSize":PAGE_SIZE,
					"leasingResource":leasingResource,
					"contractNo":contractNo,
					"contractStatus":contractStatus,
					"type":"0"
			};
				//if(checkMarket()){
			loadData(condition, true);
				//}
			
		}
		//校验市场ID是否存在，如果无法获取到市场ID则弹出无法获取市场ID提示窗口
		//该函数使用范围包括导出excel 页面初始化 查询
		function checkMarket(){
			$.ajax({
				 url:CONTEXT + 'biContractMain/checkMarket',
				 data:"",
				 type:"post",
				 success:function(data){
					 
					 if(data=="0"){
							$.eAlert("提示信息", "请选择市场！");
					 return false
					 }else{
						 return true;
					 }
					 ;
				 },
				 error:function(data){
					 alert("系统异常！");
					 return false;
				 }
				 
			});
		}
		//默认初始化数据
		//if(checkMarket()){
		loadData({"pageNum":1,"pageSize":PAGE_SIZE,"type":"0"}, true);
		//}
		
		
	});
}


