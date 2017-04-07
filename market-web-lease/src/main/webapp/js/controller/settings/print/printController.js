function _call(templateUrl,param){
	$("#main-wrapper").loadPage(templateUrl,
			["css/form-imput.css"],
			[],
	function(){
		$.loadRouteModal("printSetting", "#printSetting");
		$.loadRouteModal("printTemplate", "#printTemplate");
		
		// 套打设置删除事件绑定
		function bindSettingDelEvent(){
			$("#btn-delete").unbind("click");
			$("#btn-delete").click(function(){
				var checkboxs=$("input[name='printSettingCheckbox']:checked");
				var ids=$.map(checkboxs,function(x,y){return $(x).val();}).join(',');
				if(!ids){
					$.eAlert("提示","请选择要删除的行!");
					return;
				}
				$.eConfirm("提示","你确定删除吗？", function(){
					$.getJSON(CONTEXT+"printSetting/delete",{"ids":ids},function(data){
						if(data.success){
							$.eAlert("提示","删除成功");
							$.loadRouteModal("printSetting", "#printSetting");
						} else {
							$.eAlert("提示",data.msg);
						}
					});
				});
			});
		}
		
		// 套打模板删除事件绑定
		function bindTemplateDelEvent(){
			$("#btn-delete").unbind("click");
			$("#btn-delete").click(function(){
				var checkboxs=$("input[name='printTemplateCheckbox']:checked");
				var ids=$.map(checkboxs,function(x,y){return $(x).val();}).join(',');
				if(!ids){
					$.eAlert("提示","请选择要删除的行!");
					return;
				}
				$.eConfirm("提示","你确定删除吗？", function(){
					$.getJSON(CONTEXT+"printTemplate/delete",{"ids":ids},function(data){
						if(data.success){
							$.eAlert("提示","删除成功");
							$.loadRouteModal("printTemplate", "#printTemplate");
						} else {
							$.eAlert("提示",data.msg);
						}
					});
				});
			});
		}
	
		
		function openSettingTab() {
			$("#printSettingTitle").attr("class", "active");
			$("#printTemplateTitle").attr("class", "");
			$("#printSetting").attr("class", "tab-pane active");
			$("#printTemplate").attr("class", "tab-pane");
		}
		
		function openTemplateTab() {
			$("#printSettingTitle").attr("class", "");
			$("#printTemplateTitle").attr("class", "active");
			$("#printSetting").attr("class", "tab-pane");
			$("#printTemplate").attr("class", "tab-pane active");
		}
		
		function init() {
			var params=Route.params;//获取页面传值对象
			var active = 1;
			try{
				active = params.active;
			}catch(e){
				console.log(e);
			}
			if(active == 1) {
				openSettingTab();
				$("#btn-add").attr("href", "index#printSettingAdd");
				bindSettingDelEvent();
			}
			else if(active == 2) {
				openTemplateTab();
				$("#btn-add").attr("href", "index#printTemplateAdd");
				bindTemplateDelEvent();
			}
		}
		
		$("#printSettingTab").click(function(){
			$("#btn-add").attr("href", "index#printSettingAdd");
			bindSettingDelEvent();
		});
		
		$("#printTemplateTab").click(function(){
			$("#btn-add").attr("href", "index#printTemplateAdd");
			bindTemplateDelEvent();
		});
		
		init();
	});
};