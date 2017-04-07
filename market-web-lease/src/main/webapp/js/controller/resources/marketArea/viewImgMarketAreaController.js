function _call(templateUrl,param){
	$("#main-wrapper").loadPage(templateUrl,
		["css/form-imput.css","css/resourMage.css"],
		["lib/jquery.tmpl.min.js","lib/jquery.dataTables.js","lib/DT_bootstrap.js"],
		function(){
		$("#marketName").html($("#market-current").html());
		var resourceId, currentArea, mapData, dotData = {};
		$.eLoading(true);
		var areaArr = [];
		var initTable = function(){
			return $('#tb').dataTable({
				"bRetrieve":true,
				"bDestroy":true,
				"bPaginate": true, 
				"bFilter" : true, 
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
	                "sSearch": "楼栋名称"
	            },
	            "aoColumnDefs": [{
	                    'bSortable': false,
	                    'aTargets': [0]
	                }
	            ]
			});
		}
		var dt;
		$.ajax({
			url: CONTEXT+"planeGraph/listAreaImage",
			data: '{"marketId": "' + Route.market + '"}',
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
						dotData[types[i].id] = {};
						$("#marketBtns").append('<li data=' + types[i].id + 
							'>' + types[i].name + '</li>');
					}
					// 渲染区域列表
	    			Route.areaList = data.areas;
					$('#areaTemp').tmpl({datas:data.areas}).appendTo('#areaWrap');
					// 区域楼层切换效果
				    $("#areaWrap").on("click","li",function(){
				    	$(this).addClass("curr-flow").siblings().removeClass("curr-flow");
				    	Route.areaId = this.id;
				    	currentArea = this.id;
				    	draw(this.id, resourceId, true);
				    });
				    // 根据参数切换到对应区域
				    if(Route.params){
				    	$("#areaWrap li[id=" + Route.params.areaId + "]").click();
				    }else{
	    				$("#areaWrap li").eq(0).click();
				    }
					// 切换资源更新地图描点资源数据
					$('#marketBtns').on('click', 'li', function(){
						resourceId = $(this).attr('data');
						$(this).addClass("currChose").siblings().removeClass("currChose");
						draw(currentArea, resourceId, false);
					});
					$('#marketBtns li').eq(0).click();
				} else {
					$.eAlert("错误提示", data.msg);
				}
			}
		});
		//绘制描点地图
		function draw(areaId, typeId, isArea){
			$('.table-tip-text').empty();
			$.ajax({
				url: CONTEXT+"planeGraph/listAreaImage",
				data: JSON.stringify({
					"marketId": Route.market,
					"areaId" : areaId,
					"typeId" : typeId
				}),
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
						if(isArea){
							$('#planeList').empty();
							console.log("-------isArea-----------");
							mapData = data.buildings;
							// 绘制区域描点
							$('#drawTemp').tmpl(mapData).appendTo('#planeList');
						}
					}else {
						$.eAlert("错误提示", data.msg);
					}
				}
			});
		}
		   
		// 描点数据表格弹出框
		$(".modal-pop").popwin({
			titleText: "选择楼栋", //弹出框名
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
					url: CONTEXT+"planeGraph/listBuildings",
					data: JSON.stringify({
						"marketId": Route.market,
						"areaId": currentArea
					}),
					type: "POST",
					dataType: "json",
					contentType: "application/json",
					success: function(data){
						if(data.statusCode == "0"){
							init = true;
							// 渲染描点数据表格
							$('#templateBody').empty();
							$('#hasXTemp').tmpl(mapData).appendTo('#templateBody');
							$('#noXTemp').tmpl(data.buildingList).appendTo('#templateBody');
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
		$("#planeList").on('mouseover', 'li', function(event){
			$(this).find('.plane-tips').empty();
			var id = this.id.split('_')[1],
				name = $(this).attr('name');
			if(dotData[resourceId] && dotData[resourceId][id]){
				$(this).find('.plane-tips').html($('#tipTemp').tmpl(dotData[resourceId][id]));
				return;
			}
			$.ajax({
				url: CONTEXT+"planeGraph/areaResStatistics",
				data: JSON.stringify({
						"marketId": Route.market,
						"areaId": currentArea,
						"buildingIds": [id],
						"typeId": resourceId
					}),
				type: "POST",
				dataType: "json",
				contentType: "application/json",
				success: function(data){
					if(data.statusCode == "0"){
						data.data[0].name = name;
						dotData[resourceId][id] = data.data;
						$(event.target).find('.plane-tips').html($('#tipTemp').tmpl(data.data));
					}
				}
			});
		});

		// 点击描点进入对应更高级平面图
		$("#planeList").on('click', 'li', function(){
			Route.params = {
				builName: $(this).attr('name'),
				builId: this.id.split('_')[1],
				areaId: currentArea
			}
			location.href = "index#viewImgBuilding";
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
			var axisArr = {
				"marketId": Route.market,
				"areaId": Route.areaId,
				"buildingList": []
			}
			$( ".draggable" ).draggable("disable");
			$( ".draggable" ).each(function(){
				axisArr.buildingList.push({
					"id": this.id.split("_")[1],
					"x": this.offsetLeft,
					"y": this.offsetTop
				});
			});
			$("#tb input[draw=false][init=drawed]").each(function(i,n){
				axisArr.buildingList.push({
					"id": $(this).val(),
					"x": "",
					"y": ""
				});
			});
			$.ajax({
				url: CONTEXT+"graph/saveBuildingPoint",
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
			console.log(JSON.stringify(axisArr));
			location.href = "index#" + location.hash.split("#")[1].split("/");
		});
		// 取消按钮
		$("#cancel").click(function(){
			location.href = "index#index#marketArea";
		});
		/*END*/
		});
}