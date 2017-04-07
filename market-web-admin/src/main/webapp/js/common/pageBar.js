/**
 * 表格分页控件
**/
$.fn.extend({
	page: function(ops) {
		var disabled = "", select = "", html = "", selectHtml = "", sumHtml = "";
		// 条数选择下拉列表
		selectHtml = '<select id="sizeSelect" size="1"  class="select form-control col-sm-3 fl" style="width:auto;">';
		var arr = {5: 5,10: 10, 20: 20, 50: 50, 100: 100};
		if(arr[ops.pageSize]){
			ops.pageSize = ops.pageSize;
		}else{
			ops.pageSize = 20;
		}
		for(var i in arr){
			if(i == ops.pageSize){
				select = 'selected="selected"';
			}else{
				select = '';
			}
			selectHtml += '<option value="' + arr[i] + '" ' + select + '>' + i + '  条/页&nbsp;</option>';
		}
		var maxIndex = Math.ceil(ops.total / ops.pageSize);
		selectHtml += '</select>';

		// 总条数显示块
		sumHtml = '<div id="pageinfo" class="info fl">'
             + 	'<span>共' + ops.total + '</span>条 - '
             + 	'<span>' + maxIndex + '</span>页'
             + '</div>';

        html = "<div class=\"dataTables_paginate paging_bootstrap pagination\"><ul class=\"fl\">";
		// var html = '<div class="center">'
		// 		 + "<div class=\"dataTables_paginate paging_bootstrap pagination \"><ul>";

		function pagehtml() {
			disabled = "";
			if (ops.pageIndex == 1) {
				disabled = " disabled";
			}

			var str = "<li class=\"first btn-li" + disabled + "\"><a href=\"javascript:;\"><span class=\"hidden-480\"><span class=\"hidden-480\">首页</span></a></li>"
					+ "<li class=\"prev btn-li" + disabled + "\"><a href=\"javascript:;\"><span class=\"hidden-480\"><span class=\"hidden-480\">\<</span></a></li>";
			var count = maxIndex > 5 ? 5 : maxIndex;
			for (var i = 1; i <= count; i++) {
				var active = "";
				if (ops.pageIndex == i) {
					active = "class='active'";
				}
				if (ops.pageIndex > 2 && ops.pageIndex <= maxIndex - 2 && count == 5) {
					switch (i) {
						case 1:
							str += "<li><a href='javascript:;'>" + (ops.pageIndex - 2) + "</a></li>";
							break;
						case 2:
							str += "<li><a href='javascript:;'>" + (ops.pageIndex - 1) + "</a></li>";
							break;
						case 3:
							str += "<li class='active'><a href='javascript:;'>" + ops.pageIndex + "</a></li>";
							break;
						case 4:
							str += "<li><a href='javascript:;'>" + (ops.pageIndex + 1) + "</a></li>";
							break;
						case 5:
							str += "<li><a href='javascript:;'>" + (ops.pageIndex + 2) + "</a></li>";
							break;
					}
				} else if (ops.pageIndex == maxIndex - 1 && count == 5) {
					switch (i) {
						case 1:
							str += "<li><a href='javascript:;'>" + (ops.pageIndex - 3) + "</a></li>";
							break;
						case 2:
							str += "<li><a href='javascript:;'>" + (ops.pageIndex - 2) + "</a></li>";
							break;
						case 3:
							str += "<li><a href='javascript:;'>" + (ops.pageIndex - 1) + "</a></li>";
							break;
						case 4:
							str += "<li class='active'><a href='javascript:;'>" + ops.pageIndex + "</a></li>";
							break;
						case 5:
							str += "<li><a href='javascript:;'>" + (ops.pageIndex + 1) + "</a></li>";
							break;
					}
				} else if (ops.pageIndex == maxIndex && count == 5) {
					switch (i) {
						case 1:
							str += "<li><a href='javascript:;'>" + (ops.pageIndex - 4) + "</a></li>";
							break;
						case 2:
							str += "<li><a href='javascript:;'>" + (ops.pageIndex - 3) + "</a></li>";
							break;
						case 3:
							str += "<li><a href='javascript:;'>" + (ops.pageIndex - 2) + "</a></li>";
							break;
						case 4:
							str += "<li><a href='javascript:;'>" + (ops.pageIndex - 1) + "</a></li>";
							break;
						case 5:
							str += "<li class='active'><a href='javascript:;'>" + ops.pageIndex + "</a></li>";
							break;
					}
				} else {
					str += "<li " + active + "><a href='javascript:;'>" + i + "</a></li>";
				}
			}
			disabled = "";
			if (ops.pageIndex == maxIndex) {
				disabled = " disabled";
			}
			str += "<li class=\"next btn-li" + disabled + "\"><a href=\"javascript:;\"><span class=\"hidden-480\"><span class=\"hidden-480\">\></span></a></li>"
				+ "<li class=\"last btn-li" + disabled + "\"><a href=\"javascript:;\"><span class=\"hidden-480\"><span class=\"hidden-480\">末页</span></a></li>";
			return str;
		}


		html += "</ul>" + selectHtml
             + sumHtml
             + "</div>";
		$(this).html(html);

		$(this).find("ul").html(pagehtml(ops.pageIndex));
		var obj = $(this);
		// 条数选择框变化事件
		$("#sizeSelect").change(function(){
			$("#pagebar").page({
				pageIndex : 1,
				total : ops.total,
				pageSize: $(this).val(),
				callback : ops.callback
			});
			ops.callback(1, $(this).val());
		});
		//单击分页码事件
		$(this).find("li:not(.disabled,.active)").click(function() {
			clickIndex($(this).text());
		});
		function clickIndex(index) {
			// 点击首页
			if(index == "首页"){
				ops.pageIndex = 1;
			}
			// 点击末页
			else if(index == "末页"){
				ops.pageIndex = maxIndex;
			}
			// 点击上一页
			else if(index == "<"){
				if(ops.pageIndex - 1 > 0){
					ops.pageIndex -= 1;
				}
				else if(ops.pageIndex > 1){
					ops.pageIndex--;
				}
			}
			// 点击下一页
			else if(index == ">"){
				if(ops.pageIndex + 1 <= maxIndex){
					ops.pageIndex += 1;
				}
				else if(ops.pageIndex < maxIndex){
					ops.pageIndex++;
				}
			}
			else{
				ops.pageIndex = parseInt(index);
			}
			ops.callback(ops.pageIndex, ops.pageSize);
			
			obj.find("ul").html(pagehtml());
			obj.find("li:not(.disabled,.active)").unbind("click").bind("click", function() {
				clickIndex($(this).text());
			});
		}
	}
});