function _call(templateUrl,param){
	console.log(templateUrl);
	$("#main-wrapper").loadPage(templateUrl,
			["lib/css/DT_bootstrap.css","css/contract.css","lib/css/datepicker.css"],
			["lib/jquery-migrate.js","lib/bootstrap-datepicker.js","lib/bootstrap-datetimepicker.js","lib/distpicker.data.js","js/common/pageBar.js","lib/jquery.tmpl.min.js"],

			function(){
     console.log(templateUrl);
		var PAGE_SIZE = 20;
		
		//初始化分页控件
		function initPageBar(result){
			// 分页工具组件
			$("#pagebar").page({
				pageIndex : 1,
				pageSize : PAGE_SIZE,
				total : result.total,
				callback : function(index,size){
					$('#templateBody').html(""); // 清空内容
					// 点击回调处理
					//if(checkMarket()){
					PAGE_SIZE=size;
					//console.log(size);
					loadData({"pageNum":index,"pageSize":PAGE_SIZE}, false);}
				//}
			});
		}
		
		//加载数据
		function loadData(page, isInit){
			var  startTime= $("#startTime").val();
			var endTime = $("#endTime").val();
			var marketResourceTypeId = $("#marketResourceTypeId").val();
			//alert(marketResourceTypeId)
			page.startTime=startTime;
			page.endTime=endTime;
			page.marketResourceTypeId=marketResourceTypeId;
//			$.getJSON(CONTEXT+"marketResourceType/findByPage",page,function(data){
			$.post(CONTEXT+"biLeaseList/findByPage",page,function(data) {
				if(data.success){
					if(isInit){
						initPageBar(data.result);
					}
					$("#templateBody").html("");
					console.log(data.result.data);
					$('#template').tmpl({biLeaseList:data.result.recordList}).appendTo('#templateBody');
					//initDeleteEvent();
					//选择单页显示条数下拉框事件绑定
			    	$("#sizeSelect").change(function(){
			    		queryData3();
			    	});
				} else {
					alert(data.msg);
				}
			});
		}
		

        $.ajax({
        	url:CONTEXT+"biLeaseList/getAllTypes",
        	data:"",
        	type:"post",
        	success:function(data){
        		for(var i=0;i<data.length;i++){
        			if(data[i].name=="商铺"){
        				$("#marketResourceTypeId").append("<option value="+data[i].id+" selected ='selected'>"+data[i].name+"</option>")	
        			}else{
        			$("#marketResourceTypeId").append("<option value="+data[i].id+">"+data[i].name+"</option>")
        			}
        		}
        	},
        	error:function(){
        		
        	}
        	
        });
        
    	//绑定查询事件
		var condition = {};
		//导出状态标识
	    var disableExport = false;
	    

		//绑定数据导出按钮事件
		$("#exportData").click(
				function() {
					//alert(111)
					//if(checkMarket()){
					//获取搜索栏搜索条件值
					var startTime = $("#startTime").val();
					var endTime = $("#endTime").val();
					var marketResourceTypeId = $("#marketResourceTypeId").val();
					//组装参数
					
					condition = {
							"startTime":startTime,
							"endTime":endTime,
							"marketResourceTypeId":marketResourceTypeId
					};
					if (disableExport) {
						alert("已进行过一次数据导出,导出功能已禁用,请10秒钟过后再点...");	
						return;
					}
				                 	disableExport = true;
		                            //进行导出结果集大小校验
				    				$.ajax({
										url : CONTEXT + 'biLeaseList/checkExportParams',
										data : condition,
										type : 'post',
										success : function(data) {
											
											// 检测通过
											if (data.code==10000) {
													//slideMessage("数据正在导出中, 请耐心等待...");
													// 启动下载
													$.download(CONTEXT
															+ 'biLeaseList/export',
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
		
		};
        //搜索按钮事件绑定
		$("#query").click(function(){
			queryData3();
		});

    	//查询
		function queryData3(){
			var  startTime= $("#startTime").val();
			var endTime = $("#endTime").val();
			var marketResourceTypeId = $("#marketResourceTypeId").val();
			PAGE_SIZE = $("#sizeSelect").val();
//			alert(marketResourceTypeId)
			//组装参数
			var condition = {
					"pageNum":1, "pageSize":PAGE_SIZE,
					"startTime":startTime,
					"endTime":endTime,
					"marketResourceTypeId":marketResourceTypeId
			};
//			if(checkMarket()){
			//alert(JSON.stringify(condition))
			loadData(condition, true);
//			}
		}
	
		$("#startTime").datepicker({
			 autoclose: true,
			  format: "yyyy-mm",
			  minViewMode: 1,
			  todayBtn: false
	});
		$("#endTime").datepicker({
			 autoclose: true,
			  format: "yyyy-mm",
			  minViewMode: 1,
			  todayBtn: false
		});
		//校验市场ID是否存在，如果无法获取到市场ID则弹出无法获取市场ID提示窗口
		//该函数使用范围包括导出excel 页面初始化 查询
//		function checkMarket(){
//			$.ajax({
//				 url:CONTEXT + 'biLeaseList/checkMarket',
//				 data:"",
//				 type:"post",
//				 success:function(data){
//					
//					 if(data=="0"){
//					 return false
//					 }else{
//						 return true;
//					 }
//					 ;
//				 },
//				 error:function(data){
//					 alert("系统异常！");
//					 return false;
//				 }
//				 
//			});
//		}
	
		//默认初始化数据
		loadData({"pageNum":1,"pageSize":PAGE_SIZE}, true);
		
	});
}


