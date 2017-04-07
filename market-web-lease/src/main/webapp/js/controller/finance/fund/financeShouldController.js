function _call(templateUrl,param){
	$("#main-wrapper").loadPage(templateUrl,
			["css/form-imput.css","css/finance.css"],
			["lib/bootstrap-datepicker.js","lib/bootstrap-datetimepicker.js","lib/distpicker.data.js","js/common/pageBar.js","lib/jquery.tmpl.min.js"],				
	function(){
		
		// 此处编写所有处理代码
		//分页默认大小
		var	PAGE_SIZE = 20;
		
		// 1-简单, 2-高级
		var queryType = 1;
		
		/*时间*/
		$('.form_datetime-component').datetimepicker({
			format: "yyyy mm dd - hh:ii",
			autoclose: true
		});
		$(".start-date").datepicker({
			format: "yyyy-mm-dd",
			autoclose: true
		  });
	
		var selt = $('.dropdown-lt-20').find('li')
		selt .click(function() {
			var icont =' <span class="caret red-icon"></span>';
			$('#toggle-box').html($(this).children().html()+icont);
			$("#conditionType").val(this.value);
		})
	

		$("#conditionValue").keydown(function(event){
			if(event.keyCode == 13) {
				combineSearch({"pageNum" : 1, "pageSize" : PAGE_SIZE}, true) ;
			    // 常用keyCode： 空格 32   Enter 13   ESC 27
			 }
		});
		//简单查询
		$("#switchBtn").click(function() {
			combineSearch({"pageNum" : 1, "pageSize" : PAGE_SIZE}, true) ;
		});
		//高级搜索
		$("#search").click(function(){
			combineSearch({"pageNum" : 1, "pageSize" : PAGE_SIZE}, true) ;
		});
		
		function simpleSearch(pageIndex, freshPage){
			var params = {
					"pageNum" : pageIndex,
					"pageSize" : PAGE_SIZE,
					"queryType" : queryType, /*1-简单, 2-高级*/
					"conditionType" : $("#conditionType").val(), /*合同编号/租赁资源/费项名称*/ 
					"conditionValue" : encodeURIComponent($("#conditionValue").val()),
					"startTime" : $("#startTime").val(),
					"endTime" : $("#endTime").val()
			} ;
			loadData(params, freshPage);
		}
		
		function advanceSearch(pageIndex, freshPage){
			var params = {
					"pageNum" : pageIndex,
					"queryType" : queryType, /*1-简单, 2-高级*/
					"pageSize" : PAGE_SIZE,
					"fundType" : $("#fundType").val(),
					"feeItemTypeId" : $("#feeItemTypeId").val(),
					"startTime" : $("#startTime").val(),
					"endTime" : $("#endTime").val()
					} ;
			loadData(params, freshPage);
		}
		
		function combineSearch(pageParams, refreshPage){
			var params = {
					"pageNum" : pageParams.pageNum,
					"pageSize" : pageParams.pageSize,
					"conditionType" : $("#conditionType").val(), /*合同编号/租赁资源/费项名称*/ 
					"conditionValue" : encodeURIComponent($("#conditionValue").val()),
					"fundType" : $("#fundType").val(),
					"feeItemTypeId" : $("#feeItemTypeId").val(),
					"startTime" : $("#startTime").val(),
					"endTime" : $("#endTime").val()
			} ;
			loadData(params, refreshPage);
		}
		
		//初始化分页控件
		function initPageBar(result){
			// 分页工具组件
			$("#pagebar").page({
				pageIndex : 1,
				pageSize : PAGE_SIZE,
				total : result.total,
				callback : function(index, size){
					$('.wrp-box').html("");
					if (PAGE_SIZE != size){
						PAGE_SIZE = size;
					}
					var pageParams = {"pageNum" : index, "pageSize" : size} ;
					combineSearch(pageParams, false) ;
				}
			});
		}
		
		//加载数据
		function loadData(params, isInit){
			$.getJSON(CONTEXT+"financeShould/queryFeeRecord",params,function(data){
				if(data.success){
					if(isInit){
						initPageBar(data.result);
					}
					$(".wrp-box").html("");
					$('#template').tmpl({recordList:data.result.recordList}).appendTo('.wrp-box');
					$("#recordCount").html(data.result.total);
					$(".dateFormat").each(function(i){
						var date = $(this).data("date");
						$(this).text(dateFormat(date));
					});
					
				} else {
					$.eAlert(data.msg);
				}
			});
		}
		
		function dateFormat(source){
			if (source){
				return source.split(" ")[0];
			}
			return "";
		}
		
		//将对象的属性转化成字符串形式(以&分隔的键值对(例如: key1=value1&key2=value2))
		function convertParamsToDelimitedList(params){
			var result = "";
			for (var item in params){
				if (params[item]){
					result += "&" + item + "=" + params[item];
				}
			}
			if (result){
				result = result.substring(1) ;
			}
			return result ;
		}
		
		var disableExport = false;
		//绑定导出事件
		$("#financeShouldExport").click(function(){
			if(!Route.market){
				$.eAlert("操作提示","您没有当前市场的权限, 不能导出数据");
				return false ;
			}
			var condition ;
			if (queryType == 1){
				condition = {
						"pageNum" : 1,
						"pageSize" : PAGE_SIZE,
						"queryType" : queryType, /*1-简单, 2-高级*/
						"conditionType" : $("#conditionType").val(), /*合同编号/租赁资源/费项名称*/ 
						"conditionValue" : encodeURIComponent($("#conditionValue").val()),
						"startTime" : $("#startTime").val(),
						"endTime" : $("#endTime").val()
				} ;
			}else{
				condition = {
					"pageNum" : 1,
					"queryType" : queryType, /*1-简单, 2-高级*/
					"pageSize" : PAGE_SIZE,
					"fundType" : $("#fundType").val(),
					"feeItemTypeId" : $("#feeItemTypeId").val(),
					"startTime" : $("#startTime").val(),
					"endTime" : $("#endTime").val()
					} ;
			}
			var paramList = convertParamsToDelimitedList(condition);
			if (disableExport) {
				$.eAlert("操作提示","已进行过一次数据导出,导出功能已禁用,请10秒钟过后再点...");	
				return;
			}
			disableExport = true;
			$.ajax({
				url: CONTEXT+'financeShould/checkExportParams',
				data : condition,
				type:'post',
				success : function(data){
					//检测通过
					if (data && data.success){
						//启动下载
						$.download(CONTEXT+'financeShould/exportData',paramList,'post' );
					}else{
						$.eAlert("操作提示",data.msg);
					}
				},
				error : function(data){
					$.eAlert("操作提示",data.msg);
				}
			});
			// 10秒后导出按钮重新可用
			setInterval(function(){	disableExport = false;}, 10000);
			
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
				jQuery('<form action="' + url + '" method="' + (method || 'post') + '">' + 
						inputs + '</form>').appendTo('body').submit().remove();
			};
		}; 
		
		//默认初始化数据
		loadData({"pageNum":1,"pageSize":PAGE_SIZE}, true);
		
		//搜索显示隐藏
	    $('.high-search').click(function() {
	    	if($('.main-ipt-types').is(':visible')){	
	    		closeAdvanced();
//	    		$('.main-ipt-types').slideUp();
//	    		$('.high-search').html(' 高级搜索<i class="fa fa-angle-up"></i>')
	    	}else{
	    		openAdvanced();
//	    		$('.main-ipt-types').slideDown();
//	    		$('.high-search').html(' 高级搜索<i class="fa fa-angle-down"></i>')
	    	}
	    });  
	    //
	    function closeAdvanced(){
	    	$('.main-ipt-types').slideUp();
	    	$('.high-search').html(' 高级搜索<i class="fa fa-angle-up"></i>');
	    	//关闭高级搜索, 启用简单查询; 1-简单, 2-高级
	    	queryType = 1 ;
	    }
	    function openAdvanced(){
	    	$('.main-ipt-types').slideDown();
    		$('.high-search').html(' 高级搜索<i class="fa fa-angle-down"></i>');
    		//启用高级搜索; 1-简单, 2-高级
    		queryType = 2 ;
	    }
		
	});//end of anonymity function
}
