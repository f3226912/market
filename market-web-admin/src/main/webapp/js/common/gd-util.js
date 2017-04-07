/**
 *  工具，方法定义
 *  desc: 扩展jQuery静态方法
 **/
;
(function($) {
	//获取指定url完整路径
	var getPathFolder = function(url) {
		var url = (!url.match(/^([^?#&]+)[?#&]/)) ? url : RegExp.$1;
		var pos = url.lastIndexOf('/');
		if(pos > 0) {
			url = url.substr(0, pos + 1);
		} else {
			url += '/';
		}
		return url;
	}
	var ROOT_PATH = getPathFolder(window.location.href);
	$.extend($, {
		ROOT_PATH: ROOT_PATH,
		getPathFolder: getPathFolder,
		getQueryStringJson: function(name) {
			var ret = {};
			window.location.search.substr(1).replace(/(\w+)=(\w+)/ig, function(a, b, c) {
				ret[b] = unescape(c);
			});
			return ret;
		},
		//设置cookie
		setCookie: function(name, value, time) {
			var cookie = name + "=" + encodeURIComponent(value);
			if(typeof time === "number") {
				cookie += "; max-age=" + time;
			}
			document.cookie = cookie;
		},
		//获取cookie
		getCookie: function(key) {
			var cookie = {};
			var all = document.cookie;
			if(all === "") {
				return null;
			}
			var list = all.split("; ");
			for(var i = 0; i < list.length; i++) {
				var singleCookie = list[i];
				var p = singleCookie.indexOf("=");
				var name = singleCookie.substring(0, p);
				var value = singleCookie.substring(p + 1);
				value = decodeURIComponent(value);
				if(key && key == name) {
					return value;
				}
			}
			return null;
		},
		//初始化指定页面
		goPage: function(url) {
			if(location.hash != "#" + url && location.hash != url) {
				location.hash = url;
			} else {
				$(window).trigger('hashchange'); // 触发hash路由变化监听事件
			}
		},
		/**
		 *   生成左侧菜单栏
		 *   @params menuJson: json数据格式
		 **/
		leftSideBar: function(menuJson) {
			menuArr = JSON.parse(menuJson);
			var html = '<aside>' +
				'<div id="sidebar" class="nav-collapse">' +
				'<div class="leftside-navigation">' +
				'<ul class="sidebar-menu" id="nav-accordion">';

			for(var i = 0, len = menuArr.length; i < len; i++) {
				var item = menuArr[i];
				if(item.sub) {
					html += '<li class="sub-menu">' +
						'<a href="javascript:;">' +
						'<i class="fa ' + item.title[1] + '"></i>' +
						'<span>' + item.title[0] + '</span>' +
						'</a>' +
						'<ul class="sub">'
					for(var j = 0, c = item.sub.length; j < c; j++) {
						html += '<li><a href="' + item.sub[j][1] + '">' + item.sub[j][0] + '</a></li>';
					}
					html += '</ul>' +
						'</li>';
				} else {
					html += '<li>' +
						'<a href="' + item.title[1] + '">' +
						'<i class="fa ' + item.title[2] + '"></i>' +
						'<span>' + item.title[0] + '</span>' +
						'</a>' +
						'</li>';
				}
			}
			html += '</ul></div></div></aside>';
			$('#container').prepend(html);
		},
		// 异步批量加载css文件
		asynCss: function(css_arr) {
			for(var i = 0, n = css_arr.length; i < n; i++) {
				var link = document.createElement("link");
				link.type = "text/css";
				link.rel = "stylesheet";
				link.href = ROOT_PATH + css_arr[i] + "?v=" + (_version ? _version : '');
				$("#container")[0].appendChild(link);
				// document.getElementsByTagName("head")[0].appendChild( link );
			}
		},
		// 异步批量加载js文件
		asynJs: function(js_arr, callback) {
			var count = 0;
			if(js_arr.length == 0) {
				callback && callback();
			}
			for(var i = 0, n = js_arr.length; i < n; i++) {
				var link = document.createElement("script");
				link.src = ROOT_PATH + js_arr[i] + "?v=" + (_version ? _version : '');
				$("#container")[0].appendChild(link);
				// document.getElementsByTagName("head")[0].appendChild( link );
				link.onload = function() {
					++count;
					if(count == js_arr.length) {
						callback && callback();
					}
				};
			}
		},
		//提示框  headText是头部文字，mainText是主要文字
		eAlert: function(headText, mainText) {
			$("#Delete2").remove();
			var mainHtml = '<div class="modal fade" id="Delete2" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">' +
				'<div class="modal-dialog dialog">' +
				'<div class="modal-content">' +
				'<div class="modal-header">' +
				'<button type="button" class="close Close" data-dismiss="modal"><span aria-hidden="true">&times;</span><span class="sr-only">Close</span></button>' +
				'<h5 class="modal-title bold" id="myModalLabel">' + headText + '</h5>' +
				'</div>' +
				'<div class="modal-body">' +
				mainText +
				'</div>' +
				'<div class="modal-footer">' +
				'<button type="button" class="btn btn-danger Close2" data-dismiss="modal">确定</button>' +
				'</div>' +
				'</div>' +
				'</div>' +
				'</div>';
			$("body").append(mainHtml);
			$("#Delete2").modal();
//			$(".Close2").click(function() {
//				$("#Delete2").modal('hide');
//				$("#Delete2").remove();
//			});
		},

		/**
		 *   確認按鈕按钮组件
		 *   desc：headText是头部文字，mainText是主要文字，fun是点击确认后的方法名
		 **/
		eConfirm: function(headText, mainText, fun) {
			var mainHtml = '<div class="modal fade" id="Delete" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">' +
				'<div class="modal-dialog dialog">' +
				'<div class="modal-content">' +
				'<div class="modal-header">' +
				'<button type="button" class="close Close" data-dismiss="modal"><span aria-hidden="true">&times;</span><span class="sr-only">Close</span></button>' +
				'<h5 class="modal-title bold" id="myModalLabel">' + headText + '</h5>' +
				'</div>' +
				'<div class="modal-body"><div class="contfire-cont">' +
				mainText +
				'</div></div>' +
				'<div class="modal-footer">' +
				'<button type="button" class="btn btn-default Close demo-btn" data-dismiss="modal">取消</button><button type="button" class="btn btn-danger demo-btn ml15" id="deleteConfirm" data-dismiss="modal">确认</button>'

			+'</div>' +
			'</div>' +
			'</div>' +
			'</div>';
			$("body").append(mainHtml);
			$("#Delete").modal();
			$("#deleteConfirm").click(function() {
				$("#Delete").modal('hide');
				$("#Delete").remove();
				fun();
			});
			$(".Close").click(function() {
				$("#Delete").modal('hide');
				$("#Delete").remove();
			});
		},
		//一个加载弹出层，传true弹出，传false删除
		eLoading: function(i) {
			if(i == false) {
				$(".markAll").remove();
				return;
			}
			if($(".markAll").length == 0){
				var mainHtml = '<div class="mark-wrap markAll">' +
					'<div class="mark"><img src="images/icon/loading.gif"/></div></div>';
				$("body").append(mainHtml);
			}
		},
		// 动态载入指定路由模块页面到指定的容器中 $.loadRouteModal("routeUrl", "#divDom");
		loadRouteModal: function(routeUrl, divDom, func){
			var urlObj =Route.getRoute(routeUrl);
			$.getScript($.ROOT_PATH + "js/controller/"+urlObj["controller"]+".js", function(){
				var index_s = _call.toString().indexOf("#"),
					index_e = _call.toString().indexOf("\"", index_s),
					str = _call.toString();
				var	reg = new RegExp(str.substring(index_s, index_e));
				str = str.replace(reg, divDom);
				if(typeof(func) === "function"){
					var i = str.lastIndexOf("});");
					str = str.substring(0, i) + ";(" + func.toString() + ")();});}";
				}
				_call = new Function("(" + str + ")('" + urlObj.templateUrl + "',null);");
				_call();
			});
		},
		// 无刷新重载当前模块页面
		reloadRoute: function(){
			this.loadRouteModal(location.hash.split("#")[1].split("/"), "#main-wrapper");
		}
	});
})(jQuery);

/**
 *   组件定义
 *   desc: 将插件扩展到jQuery类实例对象上
 **/
;
(function($) {

	// 载入远程 HTML 文件代码并插入至 DOM 中
	$.fn.loadPage = function(page_url, css_arr, js_arr, callback) {
		//添加加载页面的参数标识loadPageFlag。
		$(this).load(page_url, {"loadPageFlag": "commonLoadPageFrame"}, function() {
			$.asynCss(css_arr);
			$.asynJs(js_arr, callback);
		});
	};
	//表格添加列
	$.fn.addTableCol = function(options) {
		var settings = $.extend({
			tableCont: null, //表格容器
			callback: null //回调
		}, options);

		return this.each(function() {
			var _self = $(this);
			var _editThis;
			$("#addTablePop").remove();
			var $container = $(settings.tableCont);
			var theadLen = $container.find("thead").length;
			var formLabel = [];
			if(theadLen > 0) {
				for(var i = 0; i < $container.find("thead th").length - 2; i++) {
					formLabel.push($container.find("thead th").eq(i).text());
				}
			}
			var popHtml = '<div class="modal fade" id="addTablePop" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true"><div class="modal-dialog dialog">' +
				'<div class="modal-content"><div class="modal-header">' +
				'<button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>' +
				'<h4 class="modal-title">添加</h4></div>' +
				'<div class="modal-body"><form action="#" class="form-horizontal ">';
			for(var i = 0; i < formLabel.length; i++) {
				popHtml += '<div class="form-group">' +
					'<label class="control-label col-md-3">' + formLabel[i] + '</label>' +
					'<div class="col-md-6"><input type="text" class="form-control"></div>' +
					'</div>'
			}
			popHtml += '</form></div><div class="modal-footer text-left"><div class="form-group ">' +
				'<button class="btn btn-danger col-md-offset-3" id="saveTable" type="button">保存</button>' +
				'<button data-dismiss="modal" class="btn btn-default" type="button">取消</button>' +
				'</div></div></div></div></div>';
			//var contHtml = '';

			$("body").append(popHtml);
			_self.on("click", function() {
				$("#addTablePop").modal();
			});
			//保存
			$("#saveTable").on("click", function() {
					var newText = [];
					var trHtml;
					$(".form-horizontal input").each(function(index, item) {
						newText.push($(this).val())
					});

					if($(this).hasClass("editSave")) {
						for(var i = 0; i < newText.length; i++) {
							trHtml += '<td>' + newText[i] + '</td>'
						}
						_editThis.parents("td").prevAll().remove();
						_editThis.parents("td").before(trHtml);
						$(this).removeClass("editSave");
						$("#addTablePop").modal("hide");
						$('#addTablePop').on('hidden.bs.modal', function(e) {
							settings.callback();
						})
					} else {
						trHtml = '<tr class="ml-ct">';
						for(var i = 0; i < newText.length; i++) {
							trHtml += '<td>' + newText[i] + '</td>'
						}
						trHtml += '<td><a class="edit" href="javascript:;">编辑</a></td><td><a class="delete" href="javascript:;">删除</a></td></tr>'
						$container.find("tbody tr:eq(0)").before(trHtml);
						$("#addTablePop").modal("hide");
						$('#addTablePop').on('hidden.bs.modal', function(e) {
							settings.callback();
						})
					}
				})
				//编辑
			$container.on("click", ".edit", function() {
				_editThis = $(this);
				$("#addTablePop").modal();
				$("#saveTable").addClass("editSave")
				var oldText = [];
				for(var i = 0; i < $(this).parents("td").prevAll().length; i++) {
					oldText.push($(this).parents("tr").find("td").eq(i).text());
				}
				$(".form-horizontal input").each(function(index, item) {
					$(this).val(oldText[index]);
				});
			})
		})
	};
	//编辑表格列弹出框
	$.fn.editTableCol = function(options) {
		var settings = $.extend({
			callback: null //回调
		}, options);

		return this.each(function() {
			var _self = $(this);
			$("#editTablePop").remove();
			var $container = _self.parents("table");
			var theadLen = $container.find("thead").length;
			var formLabel = [];
			if(theadLen > 0) {
				for(var i = 0; i < $container.find("thead th").length; i++) {
					formLabel.push($container.find("thead th").eq(i).text());
				}
			}
			var popHtml = '<div class="modal fade" id="editTablePop" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true"><div class="modal-dialog dialog">' +
				'<div class="modal-content"><div class="modal-header">' +
				'<button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>' +
				'<h4 class="modal-title">编辑</h4></div>' +
				'<div class="modal-body"><form action="#" class="form-horizontal ">';
			for(var i = 0; i < formLabel.length; i++) {
				popHtml += '<div class="form-group">' +
					'<label class="control-label col-md-3">' + formLabel[i] + '</label>' +
					'<div class="col-md-6"><input type="text" class="form-control"></div>' +
					'</div>'
			}
			popHtml += '</form></div><div class="modal-footer text-left"><div class="form-group ">' +
				'<button class="btn btn-danger col-md-offset-3" id="saveTable" type="button">保存</button>' +
				'<button data-dismiss="modal" class="btn btn-default" type="button">取消</button>' +
				'</div></div></div></div></div>';
			//var contHtml = '';
			console.log($("#editTablePop").length)
			$("body").append(popHtml);
			_self.on("click", function() {
				var editSelf = $(this);
				$("#editTablePop").modal();
				var oldText = [];
				
				for(var i = 0; i < $(this).parents("tr").find("td").length; i++) {
					oldText.push($(this).parents("tr").find("td").eq(i).text());
				}
				console.log("oldText " + oldText)
				$(".form-horizontal input").each(function(index, item) {
					$(this).val(oldText[index]);
				});
				var newText = [];
				$("#editTablePop").on("click", "#saveTable", function() {
					var trHtml;
					
					$(".form-horizontal input").each(function(index, item) {
						newText.push($(this).val())
					});
					$("#editTablePop").modal("hide");					
				})
				$('#editTablePop').on('hidden.bs.modal', function(e) {
					for(var i = 0; i < newText.length; i++) {
						editSelf.parents("tr").find("td").eq(i).text(newText[i])
					}
					if(settings.callback()&&typeof(settings.callback())=="function"){
						settings.callback()
					}
				})
			})
		})
	};
	//弹出框
	$.fn.modalPop = function(options) {
		var settings = $.extend({
			container: "",
			titleText: "", //表格容器
			formValid: "", //表单验证方法
			popUrl: null,
			callback: function(){
				$(".modal").modal("hide");
			} //回调
		}, options);

		return this.each(function() {
			var _self = $(this);
			$("#modalPopwin").remove();
			var popHtml = '<div class="modal fade" id="modalPopwin" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true"><div class="modal-dialog">' +
				'<div class="modal-content"><div class="modal-header">' +
				'<button type="button" class="close" data-dismiss="modal" aria-hidden="true">×</button>' +
				'<button type="button" class="close max-winpop"><i class="fa fa-plus-square-o"></i></button>' +
				'<h4 class="modal-title">' + settings.titleText + '</h4></div>' +
				'<div class="modal-body" >' +
				'<div class="form-horizontal" id="templatepop"></div>' +
				'</div><div class="modal-footer text-left"><div class="form-group ">' +
				'<button data-dismiss="modal" class="btn btn-default" type="button">取消</button>' +
				'<button class="btn btn-danger saveForm" id="saveForm" type="button">确认</button>' +
				'</div></div></div></div></div>';
				
				function dragPop(){
					var mouseStartPoint = {
				"left": 0,
				"top": 0
			};
			var mouseEndPoint = {
				"left": 0,
				"top": 0
			};
			var mouseDragDown = false;
			var oldP = {
				"left": 0,
				"top": 0
			};
			var moveTartet;
			
				$(document).on("mousedown", ".modal-header", function(e) {
					if($(e.target).hasClass("close")) //点关闭按钮不能移动对话框  
						return;
					mouseDragDown = true;
					moveTartet = $(this).parent();
					mouseStartPoint = {
						"left": e.clientX,
						"top": e.clientY
					};
					oldP = moveTartet.offset();
				});
				$(document).on("mouseup", function(e) {
					mouseDragDown = false;
					moveTartet = undefined;
					mouseStartPoint = {
						"left": 0,
						"top": 0
					};
					oldP = {
						"left": 0,
						"top": 0
					};
				});
				$(document).on("mousemove", function(e) {
					if(!mouseDragDown || moveTartet == undefined) return;
					var mousX = e.clientX;
					var mousY = e.clientY;
					if(mousX < 0) mousX = 0;
					if(mousY < 0) mousY = 25;
					mouseEndPoint = {
						"left": mousX,
						"top": mousY
					};
					var width = moveTartet.width();
					var height = moveTartet.height();
					mouseEndPoint.left = mouseEndPoint.left - (mouseStartPoint.left - oldP.left); //移动修正，更平滑  
					mouseEndPoint.top = mouseEndPoint.top - (mouseStartPoint.top - oldP.top);
					moveTartet.offset(mouseEndPoint);
				});
				}
			$("body").append(popHtml);
			_self.on("click", function() {
				$("#modalPopwin").modal();
				if(settings.popUrl) {
					$("#templatepop").load(settings.popUrl, function() {})
				} else {
					$("#" + settings.container).tmpl().appendTo('#templatepop');
				}
				/*最大化*/
				//console.log("最大 "+settings.formValid)
				$(".max-winpop").on("click", function() {
					$(this).parents(".modal-dialog").toggleClass("max-dialog");
					$("#modalPopwin").css("padding-left", "0!important")
				})
				$("#saveForm").bind("click", function(){
					settings.callback();
				})
			})
			$('#modalPopwin').on('hidden.bs.modal', function(e) {
				$("#saveForm").unbind("click");
				$("#templatepop").empty();
			})
		})
	};
	//弹出框
	$.fn.popwin = function(options) {
		var settings = $.extend({
			titleText: "弹出框", //弹出框名
			popwidth:"600",//设置宽度
			btnText:["取消","确认"],//按钮的文本
			drag:true,//是否拖动
			//formValid: "", //表单验证方法
			popUrl: null,
			callback: function(){
				//$(".modal").modal("hide");
			}
		}, options);

		return this.each(function(){
			var _self = $(this);
			var oldHtml = _self.html();
			if(_self.find(".modal-body").length != 0){
				oldHtml = _self.find(".modal-body").html();
			}
			_self.addClass("modal fade");
			_self.attr("tabindex","-1");
			_self.attr("role","-1");
			_self.attr("aria-hidden","true");
			_self.html("");
			
			var popHtml = '<div class="modal-dialog" style="width:'+settings.popwidth+'px">'
						+   '<div class="modal-content">'
						+    '<div class="modal-header">'
						+        '<button type="button" class="close" data-dismiss="modal">'
						+        '<span aria-hidden="true">&times;</span>'
						+        '<span class="sr-only">Close</span></button>'
						+        '<h4 class="modal-title">'+settings.titleText+'</h4>'
						+      '</div>'
						+      '<div class="modal-body form-horizontal"></div><div class="modal-footer">'
						+        '<button type="button" class="btn btn-default" data-dismiss="modal">'+settings.btnText[0]+'</button>'
						+        '<button type="button" class="btn btn-danger savePop">'+settings.btnText[1]+'</button>'
						+      '</div></div></div>';
						
							_self.empty().append(popHtml);
							_self.find(".modal-body").html(oldHtml);
						
						
		if(settings.popUrl) {
			_self.find(".modal-body").load(settings.popUrl, function() {})
		}		
		function dragPop(){
			var mouseStartPoint = {
				"left": 0,
				"top": 0
			};
			var mouseEndPoint = {
				"left": 0,
				"top": 0
			};
			var mouseDragDown = false;
			var oldP = {
				"left": 0,
				"top": 0
			};
			var moveTartet;			
			$(document).on("mousedown", ".modal-header", function(e) {
				if($(e.target).hasClass("close")) //点关闭按钮不能移动对话框  
					return;
				mouseDragDown = true;
				moveTartet = $(this).parent();
				mouseStartPoint = {
					"left": e.clientX,
					"top": e.clientY
				};
				oldP = moveTartet.offset();
			});
			$(document).on("mouseup", function(e) {
				mouseDragDown = false;
				moveTartet = undefined;
				mouseStartPoint = {
					"left": 0,
					"top": 0
				};
				oldP = {
					"left": 0,
					"top": 0
				};
			});
			$(document).on("mousemove", function(e) {
				if(!mouseDragDown || moveTartet == undefined) return;
				var mousX = e.clientX;
				var mousY = e.clientY;
				if(mousX < 0) mousX = 0;
				if(mousY < 0) mousY = 25;
				mouseEndPoint = {
					"left": mousX,
					"top": mousY
				};
				var width = moveTartet.width();
				var height = moveTartet.height();
				mouseEndPoint.left = mouseEndPoint.left - (mouseStartPoint.left - oldP.left); //移动修正，更平滑  
				mouseEndPoint.top = mouseEndPoint.top - (mouseStartPoint.top - oldP.top);
				moveTartet.offset(mouseEndPoint);
			});
	}
		if(settings.drag){
			$(".modal-header").css("cursor","move");
			dragPop();
		}
		_self.off("click").on("click",".savePop",function(){
			settings.callback();
		})
		
		})
	};
	//表单序列化转化成JSON
	$.fn.serializeJson = function() {
		var serializeObj = {};
		$(this.serializeArray()).each(function() {
			serializeObj[this.name] = this.value;
		});
		return serializeObj;
	};
	
	/**
	 * 将josn对象赋值给form
	 * @param {dom} 指定的选择器
	 * @param {obj} 需要给form赋值的json对象
	 * @method serializeJson
	 * by weiwenke
	 * */
	$.fn.setForm = function(jsonValue){
	  var obj = this;
	  $.each(jsonValue,function(name,ival){
	    var $oinput = obj.find("[name='"+name+"']")[0];
	    if(!$oinput){
	    	return;
	    }
	    if($oinput.tagName=='SELECT'){ //如果是下拉框
	    	var option=$($oinput).find("option[value='"+ival+"']");
	    	    option.selected();// 选中
	    	    $($oinput).trigger('change');//触发change事件，来确保级联等数据正常显示
	        
	   }
	    else if($oinput.tagName=='INPUT'){ //如果是文本框
	    	if($($oinput).attr("type")=="checkbox"){
			      if(ival !== null){
			        var checkboxObj = $("[name="+name+"]");
			        var checkArray = ival.split(";");
			        for(var i=0;i<checkboxObj.length;i++){
			          for(var j=0;j<checkArray.length;j++){
			            if(checkboxObj[i].value == checkArray[j]){
			              checkboxObj[i].click();
			            }
			          }
			        }
			      }
			    }
			    else if($($oinput).attr("type")=="radio"){
			    	$($oinput).each(function(){
			        var radioObj = $("[name="+name+"]");
			        for(var i=0;i<radioObj.length;i++){
			          if(radioObj[i].value == ival){
			            radioObj[i].click();
			          }
			        }
			      });
			    }
			    else if($($oinput).attr("type")=="textarea"){
			    	$($oinput).html(ival);
			    }
			    else{
			    	$($oinput).val(ival);
			    }
	    }//其他类型自己可扩展 by wwk
	    
	  })
	}
})(jQuery);

//获取当前日期并格式化
Date.prototype.Format = function (fmt) { 
    var o = {
        "M+": this.getMonth() + 1, //月份 
        "d+": this.getDate(), //日 
        "h+": this.getHours(), //小时 
        "m+": this.getMinutes(), //分 
        "s+": this.getSeconds(), //秒 
        "q+": Math.floor((this.getMonth() + 3) / 3), //季度 
        "S": this.getMilliseconds() //毫秒 
    };
    if (/(y+)/.test(fmt)) fmt = fmt.replace(RegExp.$1, (this.getFullYear() + "").substr(4 - RegExp.$1.length));
    for (var k in o)
    if (new RegExp("(" + k + ")").test(fmt)) fmt = fmt.replace(RegExp.$1, (RegExp.$1.length == 1) ? (o[k]) : (("00" + o[k]).substr(("" + o[k]).length)));
    return fmt;
}


/*
* 格式化金额（千分位） 小数位向上取整
*/
function formatMoney(money) {
	var n = (Math.ceil(money*100)/100).toFixed(2);
    var re = /(\d{1,3})(?=(\d{3})+(?:\.))/g;
    return n.replace(re, "$1,");
}
