function _call(templateUrl,param){
	$("#main-wrapper").loadPage(templateUrl,
			["css/form-imput.css","css/resourMage.css"],
			["lib/jquery.tmpl.min.js","lib/jquery.dataTables.js","lib/DT_bootstrap.js"],
			function(){
		if($("#market-current").html() != ""){
			$("#marketName").html($("#market-current").html());
		}else{
			$("#marketName").html("平面图设置");
		}
		
		// 获取资源数据
		$.eLoading(true);
		var areaArr = [];
		var initTable = function(){
			return $('#tb').dataTable({
				"bRetrieve":true,
				"bDestroy":true,
				"bPaginate": true, 
				"aLengthMenu": [
				    ["5  条/页", "10  条/页", "15  条/页", "20  条/页"],
				    ["5  条/页", "10  条/页", "15  条/页", "20  条/页"] // change per page values here
				],
				"iDisplayLength": 5,
	            "sDom": "<<'col-lg-8'f>r>t<'row'<'col-lg-12 pop-page'p l i>>",
	            // "sPaginationType": "bootstrap",
	            "oLanguage": {
	            	"sProcessing": "加载中......",
	            	"sZeroRecords": "没有找到符合条件的数据",
	            	"sEmptyTable": "表中无数据存在！",
	                "sLengthMenu": "_MENU_",                 
	                "sInfoEmpty": "共有<font color='red'>0</font>条记录",
	                "oPaginate": {
	                    "sPrevious": "<",
	                    "sNext": ">"
	                },
	                "sInfo": "共 _TOTAL_ 条",
	                "sSearch": "区域名称"
	            },
	            "aoColumnDefs": [{
	                    'bSortable': false,
	                    'aTargets': [0]
	                }
	            ]
			});
		}
		// 初始化平面图资源，描点信息
		var mapData, resourceId, areaData = {};
	  	$.ajax({
			url: CONTEXT+"planeGraph/listMarketImage",
			data: '{"marketId": "' + Route.market + '"}',
			type: "POST",
			dataType: "json",
			contentType: "application/json",
			success: function(data){
				$.eLoading(false);
				if(data.statusCode == "0"){
					mapData = data;
			    	var imgUrl = data.imageUrl;
			    	if(imgUrl != "" && imgUrl != null){
						$(".map-src").attr("src", imgUrl);
			    	}else{
			    		$(".map-src").attr("src", "images/map.jpg");
			    	}
					var types = data.resTypes;
					areaData[""] = {};
					areaData[""].total = data.summary;
					for(var i = 0,len = types.length; i < len; i++){
						if(types[i].name == ""){
							continue;
						}
						areaData[types[i].id] = {};
						$("#marketBtns").append('<li data-id="' + types[i].id + '">' + types[i].name + '</li>');
					}
					// 绘制区域描点
					$('#drawTemp').tmpl(data.areas).appendTo('#planeList');
					
					// 切换资源更新地图描点资源数据
					$('#marketBtns').on('click', 'li', function(){
						$('#marketBtns li').removeClass('currChose');
						$(this).addClass('currChose');
						resourceId = $(this).attr('data-id');
						$('.table-tip-text').empty();
						if(!jQuery.isEmptyObject(areaData[resourceId])){
							var d = areaData[resourceId].total;
							$('.table-tip-text').html('资源总数量：' + d.total 
								+ '<span class="yizu-tip">已租：' + d.alreadyRentedCnt + '</span>'
								+ '<span class="daizu-tip">待租：' + d.forRentCnt + '</span>'
								+ '<span class="daichuzu-tip">待放租：' + d.ineffectiveCnt + '</span>');
							return;
						}
						$.ajax({
							url: CONTEXT+"planeGraph/listMarketImage",
							data: JSON.stringify({
								"marketId": Route.market,
								"typeId": resourceId
							}),
							type: "POST",
							dataType: "json",
							contentType: "application/json",
							success: function(data){
								$.eLoading(false);
								if(data.statusCode == "0"){
									areaData[resourceId].total = data.summary;
									$('.table-tip-text').html('资源总数量：' + data.summary.total 
										+ '<span class="yizu-tip">已租：' + data.summary.alreadyRentedCnt + '</span>'
										+ '<span class="daizu-tip">待租：' + data.summary.forRentCnt + '</span>'
										+ '<span class="daichuzu-tip">待放租：' + data.summary.ineffectiveCnt + '</span>');
								}else {
									$.eAlert("错误提示", data.msg);
								}
							}
						});
					});
					$('#marketBtns li').eq(0).click();
					// 当鼠标放在描点上时获取描点信息
					$("#planeList").on('mouseover', 'li', function(event){
						$(this).find('.plane-tips').empty();
						var id = this.id.split('_')[1],
							name = $(this).attr('name');
						if(areaData[resourceId] && areaData[resourceId][id]){
							$(this).find('.plane-tips').html($('#tipTemp').tmpl(areaData[resourceId][id]));
							return;
						}
						$.ajax({
							url: CONTEXT+"planeGraph/marketResStatistics",
							data: JSON.stringify({
									"marketId": Route.market,
									"areaIds": [id],
									"typeId": resourceId
								}),
							type: "POST",
							dataType: "json",
							contentType: "application/json",
							success: function(data){
								if(data.statusCode == "0"){
									data.data[0].name = name;
									areaData[resourceId][id] = data.data;
									$(event.target).find('.plane-tips').html($('#tipTemp').tmpl(data.data));
								}
							}
						});
					});
				} else {
					$.eAlert("错误提示", data.msg);
				}
			}
		});
		// 描点列表数据弹出框
		$(".modal-pop").popwin({
			titleText: "选择区域", //弹出框名
			popwidth:"800",//设置宽度
			btnText:["取消","确认"],//按钮的文本
			drag:false,//是否拖动
			popUrl: null,//使用远程路径
			callback: function(){ //点击确认回调
				$("#planeList").off('click', 'li');
				$(".modal-pop").modal("hide");//隐藏方法
				$(".drawed").each(function(i, n){
					$(this).empty();
					$(this).addClass("draggable").removeClass("drawed");
					$(this).append('<span class="drag-tip drag-del"><i class="fa fa-trash-o"></i></span>'
							+ '<span class="drag-tip">'+ $(this).attr('name') +'</span>');
				});
				$(".Echeckbox").each(function(i, n){
					var id = $(this).val();
					if($(this).get(0).checked){
						if($(this).attr("draw") == "false"){
							var name = $(this).parent().next();
							$(".plane-list").append('<li id="draw_' + id + '" class="draggable curr-list">'
								+'<span class="drag-tip drag-del"><i class="fa fa-trash-o"></i></span>'
								+'<span class="drag-tip">'+ name.html() +'</span></li>');
							$(this).attr("draw", true);
						}
						// 修改绘制状态
						$(this).parent().next().next().html("已绘制");
					}else{
						$("#draw_" + id).remove();
						$(this).attr("draw", false);
						// 修改绘制状态
						$(this).parent().next().next().html("未绘制");
					}
				});
				$( ".draggable" ).draggable({ 
					containment: "#dragBox",
					scroll: false,
					start: function( event, ui ) {
						var target = $(event.target);
						target.addClass("drag-tip-box");
					},
					// drag: function(event, ui) {
					// 	// var target = $(event.target);
					// 	// console.log(target);
					// 	$("#axis").html("x : " + (ui.position.left) + " y : " + (ui.position.top));
					// },
					stop: function( event, ui ) {
						var target = $(event.target);
						// target.removeClass("drag-tip-box");
					},
				});
			}
		});

		var init = false;
		$("#set").on("click",function(){
			if(!init){
				$.ajax({
					url: CONTEXT+"planeGraph/listAreas",
					data: JSON.stringify({
						"marketId": Route.market
					}),
					type: "POST",
					dataType: "json",
					contentType: "application/json",
					success: function(data){
						if(data.statusCode == "0"){
							init = true;
							// 渲染描点数据表格
							$('#hasXTemp').tmpl(mapData.areas).appendTo('#templateBody');
							$('#noXTemp').tmpl(data.areaList).appendTo('#templateBody');
							initTable();
						}else {
							$.eAlert("错误提示", data.msg);
						}
					}
				});
			}
			$("#save").show();
			$(".modal-pop").modal();
		});

		$("#checkboxAll").click(function(){
			if($("#checkboxAll").get(0).checked){
				$(".Echeckbox").prop('checked', true);
				$(".costDefinition #templateBody .ml-ct").addClass("color-eee");
			}else{
				$(".Echeckbox").prop('checked', false);
				$(".costDefinition #templateBody .ml-ct").removeClass("color-eee");
			}
		});

		// 点击描点进入对应更高级平面图
		$("#planeList").on('click', 'li', function(){
			Route.params = {
				areaId : this.id.split('_')[1]
			}
			location.href = "index#viewImgMarketArea";
		});
		// 删除正在绘制的描点
		$("#planeList").on("click", ".drag-del", function(){
			$(this).parent().remove();
			var id = $(this).parent().attr("id").split("_")[1];
			$("#check_" + id).attr("draw", false).attr('checked', false);
			$("#check_" + id).parent().next().next().html("未绘制");
		});

		// 保存坐标数据，绘制结束
		var axisArr = {
			"marketId": Route.market,
			"areaList": []
		}
		$("#save").click(function(){
			$( ".draggable" ).draggable("disable");
			$( ".draggable" ).each(function(){
				axisArr.areaList.push({
					"areaId": this.id.split("_")[1],
					"x": this.offsetLeft,
					"y": this.offsetTop
				});
			});
			$("#tb input[draw=false][init=drawed]").each(function(i,n){
				axisArr.areaList.push({
					"areaId": $(this).val(),
					"x": "",
					"y": ""
				});
			});
			$.ajax({
				url: CONTEXT+"graph/saveAreaPoint",
				data: JSON.stringify(axisArr),
				type: "POST",
				dataType: "json",
				contentType: "application/json",
				success: function(data){
					if(data.statusCode == "0"){
						$.eAlert("成功提示", "保存成功");
						$.reloadRoute();
					} else {
						$.eAlert("错误提示", data.msg);
					}
				}
			});
			// $( ".draggable" ).removeClass("drag-tip-box");
			console.log(JSON.stringify(axisArr));
			location.href = "index#" + location.hash.split("#")[1].split("/");
		});
		// 取消按钮
		$("#cancel").click(function(){
			location.href = "index#market";
		});
	});
}