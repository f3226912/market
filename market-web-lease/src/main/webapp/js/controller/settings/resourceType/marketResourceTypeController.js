function _call(templateUrl,param){
	$("#main-wrapper").loadPage(templateUrl,
			["lib/css/DT_bootstrap.css","css/form-imput.css","css/system.css","css/form-imput.css"],
			["lib/jquery-migrate.js","js/common/pageBar.js","lib/jquery.tmpl.min.js"],
			function(){
		var params=Route.params;//获取页面传值对象
		var PAGE_SIZE =20;
		var checked_row, res_data;
		
		//初始化分页控件
		function initPageBar(result){
			// 分页工具组件
			$("#pagebar").page({
				pageIndex : 1,
				pageSize : PAGE_SIZE,
				total : result.total,
				callback : function(index, PAGE_SIZE){
					$('#templateBody').html(""); // 清空内容
					// 点击回调处理
					loadData({"pageNum":index,"pageSize":PAGE_SIZE}, false);
				}
			});
			PAGE_SIZE = $("#sizeSelect").val();
		}
		
		/*function initSelectChangeEvent(){
			$("#sizeSelect").change(function(){
				PAGE_SIZE = $("#sizeSelect").val();
				loadData({ "pageNum":1, "pageSize":PAGE_SIZE, }, true);
			});
		}*/
		
		//加载数据
		function loadData(page, isInit){
//			$.getJSON(CONTEXT+"marketResourceType/findByPage",page,function(data){
			$.post(CONTEXT+"marketResourceType/findByPage",page,function(data) {
				if(data.success){
					if(isInit){
						initPageBar(data.result);
					}
					$("#templateBody").html("");
					//console.log(data.result.data);
					$('#template').tmpl({typeList:data.result.recordList}).appendTo('#templateBody');
//					$("#total").html(data.result.total);
//					$("#pageSize").html(PAGE_SIZE);
					
//					initTrClickEvent();
// 					initSelectChangeEvent();

					//initDeleteEvent();
				} else {
					$.eAlert("提示：", data.msg);
				}
			});
		}
		
		//绑定行点击事件
		function initTrClickEvent(){
			$(" #templateBody .ml-ct").click(function(){
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
		
		//绑定删除事件
		//function initDeleteEvent(){
			$("#delete_btn").click(function(){
	            //
	            var isvalid = false;  //检测当前页面是否有可删除的数据
				var trs=$("#templateBody").find("tr");
				$.each(trs,function(x,y){
//					if($(y).find("td[name='sysType']").text() != '否'){
//						$(y).find("td input:first").prop('checked',false);
//					}else{
						if($(y).find("td input:first").prop('checked')){
							var resourceTypeId = $(y).find("td input:first").val();
//							console.debug("x : " + x + ", y : " + resourceTypeId);
							$.ajaxSettings.async = false; //设置getJson同步
							$.getJSON(CONTEXT+"marketResourceType/validateDelete", {"resourceTypeId" : resourceTypeId}, function(data){
								if(data.result){ //验证当前资源类型是否关联资源，如果是则不能删除
									$(y).find("td input:first").prop('checked',false);
								}else{
									isvalid=true;
								} 
							}); 
							$.ajaxSettings.async = false; //设置getJson同步
						}else{
							isvalid=true;
						}
//					}
				});
				if(!isvalid){
					$("#checkboxAll").prop('checked',false);
					$.eAlert("提示", "删除必须是无关联有效资源的资源类型！");
					return;
				}
				var checkboxs=$(":checkbox:checked"); 
				if(checkboxs.length==0){
					$.eAlert("提示", "请选择非系统级的且无关联有效资源的行删除!");
					return;
				}

				checkboxs=$(":checkbox[id!=checkboxAll]:checked"); //所有选中的checkbox 
				var allLength=$(":checkbox[id!=checkboxAll]").length; //所有的列表数据checkbox的长度
				if(allLength!=checkboxs.length){
					$("#checkboxAll").prop('checked',false);
				}

//				var ids=$.map(checkboxs,function(x,y){return $(x).val()}).join(',');
	            
	            //
				var isBreak = false;
				var idArray = new Array();
	            $("input[name='subBox']:checkbox").each(function(){
	                if($(this).attr("checked") == "checked"){
	                	//系统级别不能删除
//	                	alert("xitong : " + $(this).parent().siblings("td[name='sysType']").attr("value"));
	                	if ($(this).parent().siblings("td[name='sysType']").attr("value") == 1) {
	                		isBreak = true;
	                		return false;
	                	}
	                    idArray.push($(this).val());
	                }
	            });

	            if (isBreak) {
	            	$.eAlert("提示信息", "系统级别不能删除");
	            	checkboxs.prop("checked", false);
            		return;
	            }
				if(idArray.length == 0){
					$.eAlert("提示信息", "请先选中至少一行进行操作!");
					return;
				}
				
				$.eConfirm("提示", "你确定删除吗？", function(){
					$.ajax({
						type : "post",
						traditional : true,
						url : CONTEXT+"marketResourceType/delete",
						data : {"idArray" : idArray},
						success : function (data) {
							if(data.success){
								loadData({"pageNum":1,"pageSize":PAGE_SIZE}, true);
								$.eAlert("提示", "删除成功");
							} else {
								$.eAlert("提示",data.msg);
							}
						}
					});
				});
			});
		//}

		
		$("#checkboxAll").click(function(){
			if($("#checkboxAll").get(0).checked){
				$(".Echeckbox").prop('checked', true);
				$(".costDefinition #templateBody .ml-ct").addClass("color-eee");
			}else{
				$(".Echeckbox").prop('checked', false);
				$(".costDefinition #templateBody .ml-ct").removeClass("color-eee");
			}
		});
		
		//默认初始化数据
		loadData({"pageNum":1,"pageSize":PAGE_SIZE}, true);
		
	});
}
