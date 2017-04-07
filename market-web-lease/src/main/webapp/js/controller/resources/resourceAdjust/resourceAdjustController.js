function _call(templateUrl,param){
	$("#main-wrapper").loadPage(templateUrl,
			["css/form-imput.css","css/resourMage.css","lib/css/datepicker.css"],
			["lib/jquery.tmpl.min.js","lib/bootstrap-datepicker.js"],function(){
		if(Route.params){
			var  objbuild = new Object();
			objbuild["buildId"] = Route.params.id;
			$('#buildName').html(Route.params.name);
			loadBuildResource(objbuild);
		}
		$(".data-date").datepicker({
			format: 'yyyy-mm-dd',
			autoclose : true,
			//todayBtn : true,//是否在底部显示“今天”按钮
			clearBtn:true,//是否在底部显示“清除”按钮
			todayHighlight : true,//是否高亮当前时间
			keyboardNavigation : true,//是否允许键盘选择时间
			language : 'zh-CN',//选择语言，前提是该语言已导入
            forceParse : true,
            pickerPosition : "bottom-right",//显示的位置，还支持bottom-left
            viewSelect : 0,//默认和minView相同
            showMeridian : true//是否加上网格
		});
		
		function loadBuildResource(obj){
			$.ajax({
				type: "GET",
				dataType: "json",
				data:obj,
				url: CONTEXT+"resourceAdjust/getResourceAdjustTable",
				async: false,
				success: function (data) {
					if(data.success){
						$('#floorTitle').children().remove();
						$('#unitTitle').children().remove();
						$('#resource').children().remove();
						$('#floorTitleTemp').tmpl({floors:data.result.floors}).appendTo('#floorTitle');
						$('#unitTitleTemp').tmpl({units:data.result.units}).appendTo('#unitTitle');
						$('#resourceTemp').tmpl({resources:data.result.resources}).appendTo('#resource');
					}else {
						$.eAlert(data.msg);
					}
				},
				error: function(data) {
					$.eAlert(data.msg);
				}
			});
		}
		
		var checkboxed = []; //保存被选中的资源
		
		//全选资源
		$('#selectAll').click(function(){
			$("[class=lease-resoure]").find(":checkbox").prop("checked",this.checked);
		});
		//资源 类型及面积设置
		$("#setResourceType").on("click",function(){
			$(":checkbox")[0].checked = false;
			var checked = $("input[type='checkbox']:checked");
			if(checked.length != 0 ){
				checkboxed = []; //循环前，清空数组
				for(var i= 0 ;i<checked.length;i++){
					id = checked[i].id;
					checkboxed.push(id);
				}
				var obj = new Object();
				obj["resourceIds"] = checkboxed.join(",");
				$.ajax({
					type: "GET",
					dataType: "json",
					data:obj,
					url: CONTEXT+"resourceAdjust/getResourceByIds",
					async: false,
					success: function (data) {
						var flag = true;
						for(var i = 0;i<data.result.length;i++){
							if(data.result[i].leaseStatus == 3){
								flag = false;
								$.eAlert("提示","只能对资源状态为“待放租/待租”资源类型及面积进行设置");
								return;
							}
						}
						if(flag){
							$('#form-setResourceTypeArea')[0].reset();
							$('#modal-setResourceTypeArea').modal('show');
						}
					},
					error: function(data) {
						$.eAlert("提示","请求异常");
					}
				});
			}else{
				$.eAlert("提示","请选择租赁资源!");
			}
		});
		
		//资源拆分
		$('#splitResource').on("click",function(){
			$(":checkbox")[0].checked = false;
			var checked = $("input[type='checkbox']:checked");
			if(checked.length != 0 ){
				if(checked.length != 1){
					$.eAlert("提示","只允许对单个租赁资源进行拆分，请重新选择!");
					return;
				}else{
					checkboxed = []; //循环前，清空数组
					checkboxed.push(checked[0].id);
					setOriginSplitResource(checked[0].id); //设置源资源
				}
			}else{
				$.eAlert("提示","请选择租赁资源!");
			}
		});
		
		//资源合并
		$("#mergeResource").on("click",function(){
			$(":checkbox")[0].checked = false;
			var checked = $("input[type='checkbox']:checked");
			if(checked.length != 0 ){
				if(checked.length != 1){
					checkboxed = []; //循环前，清空数组
					for(var i= 0 ;i<checked.length;i++){
						checkboxed.push(checked[i].id);
					}
					setOriginMergeResource(checkboxed.join(",")); //设置源资源
				}else{
					$.eAlert("提示","合并的租赁资源必须在两个以上，请重新选择!");
				}
			}else{
				$.eAlert("提示","请选择租赁资源!");
			}
		});
		//资源删除
		$("#deleteResource").on("click",function(){
			$(":checkbox")[0].checked = false;
			var checked = $("input[type='checkbox']:checked");
			if(checked.length != 0 ){
				checkboxed = []; //循环前，清空数组
				for(var i= 0 ;i<checked.length;i++){
					checkboxed.push(checked[i].id);
				}
				var ids = checkboxed.join(",");
				var obj = new Object();
				obj["resourceIds"] = ids;
				$.ajax({
					type: "GET",
					dataType: "json",
					data:obj,
					url: CONTEXT+"resourceAdjust/getResourceByIds",
					async: false,
					success: function (data) {
						var flag = true;
						for(var i=0;i<data.result.length;i++){
							if(data.result[i].leaseStatus != 1){
								$.eAlert("提示","只能对“待放租”资源进行删除");  
								flag = false;
								return;
							}
						}
						if(flag){
							$.eConfirm("提示","你确定要删除租赁资源吗？",function(){
								$.ajax({
									type: "POST",
									dataType: "json",
									data:obj,
									url: CONTEXT+"resourceAdjust/delResourcesByIds",
									async: false,
									success: function (data) {
										if(data.success){
											loadBuildResource(objbuild); //刷新资源
											$.eAlert("提示",data.msg);
										}else {
											$.eAlert("提示",data.msg);
										}
									},
									error: function(data) {
										$.eAlert("提示","请求异常");
									}
								});
							});
						}
					},
					error: function(data) {
						$.eAlert("提示","请求异常");
					}
				});
			}else{
				$.eAlert("提示","请选择租赁资源!");
			}
		});
		
		//保存资源类型及面积设置
		$('#saveResourceTypeAndArea').on("click",function(){
			var obj=new Object();  
			var resourceTypeAreaData = $('#form-setResourceTypeArea').serializeArray();
			$.each(resourceTypeAreaData,function(index,param){
				obj[param.name]=param.value,true;
			});
			obj["resourceIds"] = checkboxed.join(",");
			$.ajax({
				type: "POST",
				dataType: "json",
				data:obj,
				url: CONTEXT+"resourceAdjust/setTypeAndArea",
				async: false,
				success: function (data) {
					if(data.success){
						loadBuildResource(objbuild);
						$('#modal-setResourceTypeArea').modal('hide');
						$.eAlert("提示",data.msg);
					}else {
						$.eAlert("提示",data.msg);
					}
				},
				error: function(data) {
					$.eAlert("提示","请求异常");
				}
			});
		});
		
		//设置源拆分资源
		function setOriginSplitResource(resourceId){
			$('#form-splitLeaseValidateTime')[0].reset();
			var len = $('#splitResources tr').length;
			if(len != 1){
				for(var i = 1;i<len;i++){
					$('#splitResources tr')[1].remove();
				}
			}
			$('#splitResources tr td input').eq(0).val("");
			$('#splitResources tr td input').eq(1).val("");
			$('#splitResources tr td input').eq(2).val("");
			$('#splitResources tr td input').eq(3).val("");
			$('#splitResources tr td select').eq(0).val("1");
			$('#splitResources tr td select').eq(1).val("1");
			var obj = new Object();
			obj["resourceId"] = resourceId;
			$.ajax({
				type: "GET",
				dataType: "json",
				data:obj,
				url: CONTEXT+"resourceAdjust/getResourceById",
				async: false,
				success: function (data) {
					if(data.success){
						if(data.result.leaseStatus != 1){
							$.eAlert("提示","只能对“待放租”资源进行拆分处理，请重新选择");
							return;
						}else{
							var shortCode = data.result.shortCode;
							var builtArea = data.result.builtArea;
							var innerArea = data.result.innerArea;
							var leaseArea = data.result.leaseArea;
							var rentMode;
							var resourceType;
							if(data.result.rentMode == 1){
								rentMode = "指定金额";
							}else  if(data.result.rentMode == 2){
								rentMode = "手工录入";
							}else if(data.result.rentMode == 3){
								rentMode = "建筑面积";
							}else if(data.result.rentMode == 4){
								rentMode = "套内面积  ";
							}else if(data.result.rentMode == 5){
								rentMode = "可出租面积  ";
							}
							if(data.result.resourceTypeId == 1){
								resourceType = "商铺";
							}else if(data.result.resourceTypeId == 2){
								resourceType = "停车位";
							}
							if(!shortCode){
								shortCode = "";
							}
							if(!builtArea){
								builtArea = "";
							}
							if(!innerArea){
								innerArea = "";
							}
							if(!leaseArea){
								leaseArea = "";
							}
							if(!rentMode){
								rentMode = "";
							}
							if(!resourceType){
								resourceType = "";
							}
							$('#originSplitResource').children().remove();
							var originSplitR = "<tr><td>"+shortCode+"</td><td>"+builtArea+"</td><td>"+innerArea+"</td><td>"+rentMode+"</td><td>"+leaseArea+"</td><td>"+resourceType+"</td></tr>";
							$('#originSplitResource').append(originSplitR);
							$('#modal-resourceSplit').modal('show');
						}
					}else {
						$.eAlert("提示",data.msg);
					}
				},
				error: function(data) {
					$.eAlert("提示","请求异常");
				}
			});
		}
		
		//设置源合并资源
		function setOriginMergeResource(resourceIds){
			var obj = new Object();
			obj["resourceIds"] = resourceIds;
			$.ajax({
				type: "GET",
				dataType: "json",
				data:obj,
				url: CONTEXT+"resourceAdjust/getResourceByIds",
				async: false,
				success: function (data) {
					if(data.success){
						$('#originMergeResource').children().remove();
						var mergeBuiltArea = 0;
						var mergeInnerArea = 0;
						var mergeLeaseArea = 0;
						var flag = true;
						var resourceTypeId = null;
						for(var i=0;i<data.result.length;i++){
							if(data.result[i].floorId != data.result[0].floorId){
								$.eAlert("提示","只能对“相同楼层”资源进行合并处理，请重新选择");
								flag = false;
								return;
							}
							if(data.result[i].leaseStatus != 1){
								$.eAlert("提示","只能对“待放租”资源进行合并处理，请重新选择");
								flag = false;
								return;
							}
							if(resourceTypeId == null && data.result[i].resourceTypeId != null){
								resourceTypeId = data.result[i].resourceTypeId;
							}
							if(data.result[i].resourceTypeId !=null &&data.result[i].resourceTypeId != resourceTypeId){
								$.eAlert("提示","只能对“相同类型”资源进行合并处理，请重新选择");
								flag = false;
								return;
							}
							var shortCode = data.result[i].shortCode;
							var builtArea = data.result[i].builtArea;
							var innerArea = data.result[i].innerArea;
							var leaseArea = data.result[i].leaseArea;
							var rentMode;
							var resourceType;
							if(data.result[i].rentMode == 1){
								rentMode = "指定金额";
							}else  if(data.result[i].rentMode == 2){
								rentMode = "手工录入";
							}else if(data.result[i].rentMode == 3){
								rentMode = "建筑面积";
							}else if(data.result[i].rentMode == 4){
								rentMode = "套内面积  ";
							}else if(data.result[i].rentMode == 5){
								rentMode = "可出租面积  ";
							}
							if(data.result[i].resourceTypeId == 1){
								resourceType = "商铺";
							}else if(data.result[i].resourceTypeId == 2){
								resourceType = "停车位";
							}
							if(!shortCode){
								shortCode = "";
							}
							if(!builtArea){
								builtArea = "";
							}
							if(!innerArea){
								innerArea = "";
							}
							if(!leaseArea){
								leaseArea = "";
							}
							if(!rentMode){
								rentMode = "";
							}
							if(!resourceType){
								resourceType = "";
							}
							var originMergeR = "<tr><td>"+shortCode+"</td><td>"+builtArea+"</td><td>"+innerArea+"</td><td>"+rentMode+"</td><td>"+leaseArea+"</td><td>"+resourceType+"</td></tr>";
							$('#originMergeResource').append(originMergeR);
							mergeBuiltArea += data.result[i].builtArea;
							mergeInnerArea += data.result[i].innerArea;
							mergeLeaseArea += data.result[i].leaseArea;
						}
						if(flag){
							$('#form-mergeLeaseValidateTime')[0].reset();
							$('#mergeResource tr td input').eq(0).val("");
							$('#mergeResource tr td input').eq(1).val(mergeBuiltArea);
							$('#mergeResource tr td input').eq(2).val(mergeInnerArea);
							$('#mergeResource tr td input').eq(3).val(mergeLeaseArea);
							$('#mergeResource tr td select').eq(0).val("1");
							$('#mergeResource tr td select').eq(1).val("1");
							$('#modal-resourceMerge').modal('show');
						}
					}else {
						$.eAlert("提示",data.msg);
					}
				},
				error: function(data) {
					$.eAlert("提示","请求异常");
				}
			});
		}
		
		//添加拆分资源
		$('#addSplitResource').on('click',function(){
			var addTr = "<tr><td><input name='splitShortCode' type='text' class='form-control'></td><td><input name='splitBuiltArea' type='number' class='form-control'></td><td><input name='splitInnerArea' type='number' class='form-control'></td><td><select name='splitRentMode' class='form-control'><option value='1'>指定金额</option><option value='2'>手工录入</option><option value='3'>建筑面积</option><option value='4'>套内面积</option><option value='5'>可出租面积</option></select></td><td><input name='splitLeaseArea' type='number' class='form-control'></td><td><select name='splitResourceTypeId'  class='form-control'><option value='1'>商    铺</option><option value='2'>停车位</option></select></td></tr>";
			$('#splitResources').append(addTr);
		});
		
		//删除拆分资源
		$('#deleteSplitResource').on('click',function(){
			var len = $('#splitResources tr').length ;
			if(len != 1){
				$('#splitResources tr')[len-1].remove();
			}
		});
		
		//保存资源拆分
		$('#saveResourceSplit').on('click',function(){
			var srcs = [];
			var trs = $('#splitResources tr');
			var flag = true;
			for(var i = 0;i<trs.length;i++){
				var tdArr = trs.eq(i).find("td");
				var resObj = new Object();
				var shortCode = tdArr.eq(0).find('input').val();
				var builtArea  = tdArr.eq(1).find('input').val();
				var innerArea = tdArr.eq(2).find('input').val();
				var rentMode = tdArr.eq(3).find('select').val();
				var leaseArea = tdArr.eq(4).find('input').val();
				var resourceTypeId = tdArr.eq(5).find('select').val();
				resObj["shortCode"] = shortCode;
				resObj["builtArea"] = builtArea;
				resObj["innerArea"] = innerArea;
				resObj["rentMode"] = rentMode;
				resObj["leaseArea"] = leaseArea;
				resObj["resourceTypeId"] = resourceTypeId;
				srcs.push(resObj);
				if(!shortCode || !resourceTypeId){
					flag = false;
					$.eAlert("提示","资源简码以及资源类型必填");
					return;
				}
			}
			if(flag){
				var obj = new Object();
				obj["leaseValidateTime"] = $('#splitLeaseValidateTime').val();
				obj["resourceId"] = checkboxed.join(",");
				obj["srcs"] = JSON.stringify(srcs);
				submitSplitResources(obj);
			}
		});
		//提交拆分结果到服务端
		function submitSplitResources(obj){
			$.ajax({
				type: "POST",
				dataType: "json",
				data:obj,
				url: CONTEXT+"resourceAdjust/resourceSplit",
				async: false,
				success: function (data) {
					if(data.success){
						loadBuildResource(objbuild); //刷新资源
						$('#modal-resourceSplit').modal('hide');
						$.eAlert("提示",data.msg);
					}else {
						$.eAlert("提示",data.msg);
					}
				},
				error: function(data) {
					$.eAlert("提示","请求异常");
				}
			});
		}
		
		//保存资源合并
		$('#saveResourceMerge').on('click',function(){
			var leaseValidateTime = $('#mergeLeaseValidateTime').val();
			var resourceIds = checkboxed.join(",");
			var shortCode = $('#mergeResource tr td input').eq(0).val();
			var builtArea = $('#mergeResource tr td input').eq(1).val();
			var innerArea = $('#mergeResource tr td input').eq(2).val();
			var rentMode = $('#mergeResource tr td select').eq(0).val();
			var leaseArea = $('#mergeResource tr td input').eq(3).val();
			var resourceTypeId = $('#mergeResource tr td select').eq(1).val();
			var obj = new Object();
			obj["leaseValidateTime"] = leaseValidateTime; 
			obj["resourceIds"] = resourceIds;
			obj["shortCode"] = shortCode;
			obj["builtArea"] = builtArea;
			obj["innerArea"] = innerArea;
			obj["leaseArea"] = leaseArea;
			obj["resourceTypeId"] = resourceTypeId;
			obj["rentMode"] = rentMode;
			if(!shortCode || !resourceTypeId){
				$.eAlert("提示","资源简码以及资源类型必填");
				return;
			}
			submitMergeResource(obj);
		});
		//提交合并结果到服务端
		function submitMergeResource(obj){
			$.ajax({
				type: "POST",
				dataType: "json",
				data:obj,
				url: CONTEXT+"resourceAdjust/resourceMerge",
				async: false,
				success: function (data) {
					if(data.success){
						loadBuildResource(objbuild); //刷新 资源
						$('#modal-resourceMerge').modal('hide');
						$.eAlert("提示",data.msg);
					}else {
						$.eAlert("提示",data.msg);
					}
				},
				error: function(data) {
					$.eAlert("提示","请求异常");
				}
			});
		}
	});
}