function _call(templateUrl,param){
	$("#main-wrapper").loadPage(templateUrl,
			["css/form-imput.css","css/contract.css"],
			["lib/bootstrap-datepicker.js","lib/bootstrap-datetimepicker.js","lib/distpicker.data.js","js/common/pageBar.js","lib/jquery.tmpl.min.js"],
	function(){
   var highSelectType=0;		
   var disableExport = false;
   var PAGE_SIZE = 10;
		// 此处编写所有处理代码
		/*时间*/
	$('.form_datetime-component').datetimepicker({
		format: "yyyy mm dd - hh:ii",
		autoclose: true
	});
	$(".start-date").datepicker({
		format: "yyyy-mm-dd",
		autoclose: true
	});
	
	var selt = $('.dropdown-lt-20').find('li');
	var searchBol=0;
	selt .click(function() {
		var icont =' <span class="caret red-icon"></span>';
		$('#toggle-box').html($(this).children().html()+icont);
		searchBol=$(this).val();
	})
	$("#btn-search").click(function(){
		loadData(selectTypeData(), true);
	})
    $("#high-btn-search").click(function(){
		loadData(selectTypeData(), true);
	})
	//数据导出
		$("#exportData").click(
						function() {
						   var queryParams = selectTypeData();
						   
							var paramList = "leasingResource="+queryParams.leasingResource
							                +"&customerName="+queryParams.customerName
							                +"&contractNo="+queryParams.contractNo
							                +"&approvalStatus="+queryParams.approvalStatus
							                +"&statementsType="+queryParams.statementsType
							                +"&createTimeStart="+queryParams.createTimeStart
							                +"&createTimeEnd="+queryParams.createTimeEnd;
							if (disableExport) {
								alert("已进行过一次数据导出,导出功能已禁用,请10秒钟过后再点...");	
								return;
							}
							disableExport = true;
							$.ajax({
										url : CONTEXT + 'contractSettlement/checkExportParams',
										data : queryParams,
										type : 'post',
										success : function(data) {
											// 检测通过
											if (data.code==10000) {
													//slideMessage("数据正在导出中, 请耐心等待...");
													// 启动下载
													$.download(CONTEXT
															+ 'contractSettlement/exportData',
															paramList, 'post');
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
   
    //搜索显示隐藏
    $('.high-search').click(function() {
    	if($('.main-ipt-types').is(':visible')){	
    		$('.main-ipt-types').slideUp();
    		highSelectType=0;
    		$('.high-search').html(' 高级搜索<i class="fa fa-angle-up"></i>')
    	}else{
    		highSelectType=1;
    		$('.main-ipt-types').slideDown();
    		$('.high-search').html(' 高级搜索<i class="fa fa-angle-down"></i>')
    	}
    });
	/*$('#template').tmpl().appendTo('.wrp-box');*/
		// 分页工具条
	//初始化分页控件
	function initPageBar(result){
		// 分页工具组件
		$("#pagebar").page({
			pageIndex : 1,
			pageSize : PAGE_SIZE,
			total : result.total,
			callback : function(index,pageSize){
				$('#wrp-box').html(""); // 清空内容
				PAGE_SIZE=pageSize;
				// 点击回调处理
				var tempjson = selectTypeData();
				tempjson.pageNum =index;
				loadData(tempjson, false);
			}
		});
	}
	
	//加载数据
	function loadData(page, isInit){
		$.ajax({
			url:CONTEXT+"contractSettlement/queryPage",
			data:page,
			type: "POST",
			dataType:'json',
			success:function(data){
				if(data.success){
					if(isInit){
						initPageBar(data.result);
					}
					$("#wrp-box").html("");
					console.log(data.result.total);
					$('#template').tmpl({rows:data.result.recordList}).appendTo('#wrp-box');
				} else {
					alert(data.msg);
				}
			},
			error:function(er){
				alert(data.msg);
             }
			});
	}
		
		function selectTypeData(){
			//租赁资源leasingResource，客户名称customerName，合同编号contractNo，
			var leasingResource="";
			var customerName="";
			var contractNo="";
			//审批状态approvalStatus，合同状态contractStatus，起始时间startLeasingDay，结束时间endLeasingDay
			var approvalStatus="";
			var statementsType="";
			var createTimeStart="";
			var createTimeEnd="";
			if(searchBol==0){
				leasingResource=$("#searchInput").val();
			}else if(searchBol==1){
				customerName=$("#searchInput").val();
			}else if(searchBol==2){
				contractNo=$("#searchInput").val();
			}
			 approvalStatus=$("#ContractSatements_approvalStatus").val();
			 statementsType=$("#ContractSatements_statementsType").val();
			 createTimeStart=$("#ContractSatements_createTimeStart").val();
			 createTimeEnd=$("#ContractSatements_createTimeEnd").val();
				if(createTimeEnd.length>0||createTimeEnd!=""){
					createTimeEnd=createTimeEnd+" 23:59:59"
				}
		  return {"pageNum":1,
			      "pageSize":PAGE_SIZE,
			      "leasingResource":leasingResource,
			      "customerName":customerName,
			      "contractNo":contractNo,
			      "approvalStatus":approvalStatus,
			      "statementsType":statementsType,
			      "createTimeStart":createTimeStart,
			      "createTimeEnd":createTimeEnd
			      };
		}
		document.onkeydown = function (event) {
            var e = event || window.event || arguments.callee.caller.arguments[0];
            if (e && e.keyCode == 13) {
            	loadData(selectTypeData(), true);
            }
        };
		
		//默认初始化数据
		loadData(selectTypeData(), true);
		
	});
}