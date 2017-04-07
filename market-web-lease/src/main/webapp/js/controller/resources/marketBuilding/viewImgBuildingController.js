function _call(templateUrl,param){
	$("#main-wrapper").loadPage(templateUrl,
		["css/form-imput.css","css/resourMage.css"],
		["lib/jquery.tmpl.min.js","lib/jquery.dataTables.js","lib/DT_bootstrap.js"],
		function(){
		if(!Route.params){
			location.href = "index#marketBuilding";
		}
		$("#marketName").html($("#market-current").html() + " - " + Route.params.builName);
		// 获取资源数据
		var resourceId, resourData, currentFloor, mapData = {}, dotData = {};
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
				"iDisplayLength": 10,
				"sDom": "<<'col-lg-10'f>r>t<'row'<'col-lg-12 pop-page'p l i>>",
	            // "sPaginationType": "bootstrap",
	            "oLanguage": {
	            	"sProcessing": "加载中......",
	            	"sZeroRecords": "没有找到符合条件的数据",
	            	"sEmptyTable": "表中无数据存在！",
	                "sLengthMenu": "_MENU_ 条/页",                 
	                "sInfoEmpty": "共有<font color='red'>0</font>条记录",
	                "oPaginate": {
	                    "sPrevious": "<",
	                    "sNext": ">"
	                },
	                "sInfo": "共 _TOTAL_ 条",
	                "sSearch": "租赁资源名称"
	            },
	            "aoColumnDefs": [{
	                    'bSortable': false,
	                    'aTargets': [0]
	                }
	            ]
			});
		}
		var dt;
		var param = {
			"marketId" : Route.market,
			"buildingId" : Route.params.builId,
			"areaId": Route.params.areaId
		}
		$.ajax({
			url: CONTEXT+"planeGraph/listFloorImage",
			data: JSON.stringify(param),
			type: "POST",
			dataType: "json",
			contentType: "application/json",
			success: function(data){
				$.eLoading(false);
				if(data.statusCode == "0"){
					var types = data.resTypes;
					resourceId = types[0].id;
					for(var i = 0,len = types.length; i < len; i++){
						if(types[i].name == ""){
							continue;
						}
						$("#marketBtns").append('<li data=' + types[i].id + 
							'>' + types[i].name + '</li>');
					}
					// 渲染区域列表
					Route.params.floorList = data.floors;
					$('#areaTemp').tmpl({datas:data.floors}).appendTo('#areaWrap');
					// 区域楼层切换效果
				    $("#areaWrap").on("click","li",function(){
				    	$(this).addClass("curr-flow").siblings().removeClass("curr-flow");
				    	Route.params.floorId = this.id;
				    	currentFloor = this.id;
				    	draw(this.id, resourceId);
				    });
				    $("#areaWrap li").eq(0).click();
					// 切换资源更新地图描点资源数据
					$('#marketBtns').on('click', 'li', function(){
						resourceId = $(this).attr('data');
						$(this).addClass("currChose").siblings().removeClass("currChose");
						draw(currentFloor, resourceId);
					});
					$('#marketBtns li').eq(0).addClass("currChose");
				} else {
					$.eAlert("错误提示", data.msg);
				}
			}
		});
		//绘制描点地图
		function draw(floorId, typeId){
			var paramObj = param;
			paramObj.typeId = typeId;
			paramObj.floorId = floorId;
			$('.table-tip-text').empty();
			$.ajax({
				url: CONTEXT+"planeGraph/listFloorImage",
				data: JSON.stringify(paramObj),
				type: "POST",
				dataType: "json",
				contentType: "application/json",
				success: function(data){
					$.eLoading(false);
					if(data.statusCode == "0"){
				    	var imgUrl = data.imageUrl;
				    	if(imgUrl != "" && imgUrl != null){
							$(".map-src").attr("src", imgUrl);
				    	}else{
				    		$(".map-src").attr("src", "images/map.jpg");
				    	}
						$('.table-tip-text').html('资源总数量：' + data.summary.total 
							+ '<span class="yizu-tip">已租：' + data.summary.alreadyRentedCnt + '</span>'
							+ '<span class="daizu-tip">待租：' + data.summary.forRentCnt + '</span>'
							+ '<span class="daichuzu-tip">待放租：' + data.summary.ineffectiveCnt + '</span>');
						mapData[floorId] = data.resources;
						// 绘制区域描点
						$('#planeList').html($('#drawTemp').tmpl({datas:data.resources}));
					}else {
						$.eAlert("错误提示", data.msg);
					}
				}
			});
		}

		// 描点数据表格弹出框
		$(".modal-pop").popwin({
			titleText: "选择租赁资源", //弹出框名
			popwidth:"800",//设置宽度
			btnText:["取消","确认"],//按钮的文本
			drag:false,//是否拖动
			popUrl: null,//使用远程路径
			callback: function(){ //点击确认回调
				$("#planeList").off('click', 'li');
				$("#planeList").off('hover', 'li');
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
					stop: function( event, ui ) {
						var target = $(event.target);
						// target.removeClass("drag-tip-box");
					},
				});
			}
		});
		var initT = true;
		$("#set").on("click",function(){
			if(initT){
				$.ajax({
					url: CONTEXT+"planeGraph/listResources",
					data: JSON.stringify({
						"marketId": Route.market,
						"floorId": currentFloor
					}),
					type: "POST",
					dataType: "json",
					contentType: "application/json",
					success: function(data){
						if(data.statusCode == "0"){
							init = true;
							// 渲染描点数据表格
							$('#templateBody').empty();
							$('#hasXTemp').tmpl(mapData[currentFloor]).appendTo('#templateBody');
							$('#noXTemp').tmpl(data.resources).appendTo('#templateBody');
							if(dt){
								dt.fnClearTable(0);
								dt.dataTable().fnDestroy();
							}
							dt = initTable();
						}else {
							$.eAlert("错误提示", data.msg);
						}
					}
				});
				initT = false;
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

		// 当鼠标放在描点上时获取描点信息
		$("#planeList").on('hover', 'li', function(event){
			$(this).find('.plane-tips').empty();
			var id = this.id.split('_')[1],
				name = $(this).attr('name');
			if(dotData[id]){
				$(this).find('.plane-tips').html($('#tipTemp').tmpl({datas:dotData[id]}));
				return;
			}
			$.ajax({
				url: CONTEXT+"planeGraph/floorResStatistics",
				data: JSON.stringify({
						"marketId": Route.market,
						"floorId": currentFloor,
						"resourceIds": [id]
					}),
				type: "POST",
				dataType: "json",
				contentType: "application/json",
				success: function(data){
					if(data.statusCode == "0"){
						data.data[0].name = name;
						dotData[id] = data.data;
						$(event.target).find('.plane-tips').html($('#tipTemp').tmpl({datas:data.data}));
					}
				}
			});
		});

		// 删除正在绘制的描点
		$("#dragBox").on("click", ".drag-del", function(){
			$(this).parent().remove();
			var id = $(this).parent().attr("id").split("_")[1];
			$("#check_" + id).attr("draw", false).attr('checked', false);
			$("#check_" + id).parent().next().next().html("未绘制");
		});

		// 保存坐标数据，绘制结束
		$("#save").click(function(){
			var axisArr = param;
			axisArr.resList = [];
			$( ".draggable" ).draggable("disable");
			$( ".draggable" ).each(function(){
				axisArr.resList.push({
					"id": this.id.split("_")[1],
					"x": this.offsetLeft,
					"y": this.offsetTop
				});
			});
			$("#tb input[draw=false][init=drawed]").each(function(i,n){
				axisArr.resList.push({
					"id": $(this).val(),
					"x": "",
					"y": ""
				});
			});
			$.ajax({
				url: CONTEXT+"graph/saveResourcePoint",
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
			location.href = "index#marketBuilding";
		});
		/*END*/
		});
}