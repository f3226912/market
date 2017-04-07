//过滤器配置
var filter = [];
//
var search_keyword = "";
//路由配置
var Route = {
	trigger: true,
	config:{
		"home" : {
			templateUrl: "home",
			controller: "homeController", // 对应js控制器文件名，省略.js
			title: "谷登ERP后台管理系统首页"
		}
	},
	//获取route配置
	getRoute:function(route){
		// var str = route.split("/");
		// var reg = eval("/^" + str[0] + "(\(\/:|\/)[a-z0-9A-Z\-\_]+){"+(str.length-1)+"}$/;");
		// for (var key in this.config){
		// 	if (reg.test(key)){
		// 		this.config[key].routeKey = key;
		// 		return this.config[key];
		// 	}
		// }
		// if(this.config[route]){
		// 	this.config[route].routeKey = route;
		// 	return this.config[route];
		// } 

		// var str = route.split("/");
		
		for (var key in this.config){
			var reg = "/^";
			var str = key.split("/");
			for(var idx in str){
				if(str[idx].indexOf(":") == -1){
			    	if(idx != 0){
			    		reg += "\\/";
			    	}
			        reg += str[idx];
			    }else{
			     	reg += "\\/[a-z0-9A-Z\\-\\_\\=\\?]+";
			     	// reg += "\\/[a-z0-9A-Z\\-\\_\\=\\?\\%,:{}\\[\\]]+";
			    }
			}
			reg = eval(reg+"$/");
			if (reg.test(route)){
				this.config[key].routeKey = key;
				return this.config[key];
			}
		}
		if(this.config[route]){
			this.config[route].routeKey = route;
			return this.config[route];
		}
	},
	setRoute:function(){
		//TBD
	}
};

window.onhashchange = function(){
	if(!Route.trigger){
		Route.trigger = true;
		return false;
	}
	if(location.hash != "#gameList"){
		search_keyword = "";
	}

	window.scrollTo(0,0);
	//page为宣传页的切屏
	// if(location.hash.indexOf('page') != -1){
	// 	return false; 
	// }
	
	// 打开左侧菜单栏对应项
	var obj = $('a[href="index' + location.hash + '"]').eq(0);
	$(".sidebar-menu a.active").removeClass("active");
	obj.parents('.sub').show().prev().addClass("active");
	obj.addClass("active");

	try{
		var _route = location.hash.split("#")[1].split("/"),
			routConfig =Route.getRoute(location.hash.split("#")[1]),
			_param = routConfig.routeKey.replace(/(\/:|\/)/g,",").split(","),
			param ={};

		//生成参数对象
		for(var key in _param){          
			if( _param[key] != _route[key]){
				param[_param[key]] = _route[key];
			}
		}
		
		// if($.indexOf(filter,routConfig["controller"]) != -1 && !$.getCookie("username")){
		// 	//强制登录
		// 	window.location.href = Links.login + "?redirect="+encodeURIComponent(location.href)+"&broker=fuzemall&l=zh-cn";
		// }else{
			//动态设置title
			// $.setTitle(routConfig["title"]);
			//调用controller渲染页面
			// require(["mall/js/controller/"+routConfig["controller"]+_min],function(_call){
			// 	_call && _call(routConfig.templateUrl,param);
			// });
		// }
		console.log("---------")
		$.getScript(CONTEXT + "js/controller/"+routConfig["controller"]+".js", function(){
			_call && _call(routConfig.templateUrl,param);
		console.log("++++++++++++++=")
		});
	}catch(error){
		// require(["mall/js/controller/404Controller"+_min],function(_call){
		// 	_call && _call("page/404.html",{});
		// });
		$.getScript(CONTEXT + "/js/controller/404Controller.js", function(){
			_call && _call("404",{});
		});
	}	
};