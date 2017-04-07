<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/pub/constants.inc" %>
<%@ include file="/WEB-INF/jsp/pub/tags.inc" %>
<!DOCTYPE html>
<html lang="en">
<head>
	<base href="${CONTEXT}" >
	<%@ include file="/WEB-INF/jsp/pub/head.inc" %>
	<meta charset="utf-8">
	<meta name="viewport" content="width=device-width, initial-scale=1.0">
	<meta name="description" content="">
	<meta name="author" content="ThemeBucket">

	<meta http-equiv="X-UA-Compatible" content="IE=EmulateIE9">
	<meta http-equiv="X-UA-Compatible" content="IE=9">

	<!-- <link rel="shortcut icon" href="images/favicon.png"> -->
	<title>智慧农批-市场管理</title>
	<!--Core CSS -->
    <link href="lib/css/bootstrap.min.css" rel="stylesheet">
    <link href="lib/css/bootstrap-reset.css" rel="stylesheet">
    <link href="lib/font-awesome/css/font-awesome.css" rel="stylesheet">
    <link href="lib/css/jquery-jvectormap-1.2.2.css" rel="stylesheet">
    <!-- Custom styles for this template -->
    <link href="lib/css/style.css" rel="stylesheet">
    <link href="css/form-imput.css" rel="stylesheet">
    <link href="css/common/index-custom.css" rel="stylesheet">
    <link href="lib/css/style-responsive.css" rel="stylesheet"/>

    <!-- Just for debugging purposes. Don't actually copy this line! -->
    <!--[if lt IE 9]>
    <script src="lib/ie8-responsive-file-warning.js"></script><![endif]-->
    <!-- HTML5 shim and Respond.js IE8 support of HTML5 elements and media queries -->
    <!--[if lt IE 9]>
    <script src="lib/html5shiv.js"></script>
    <script src="lib/respond.min.js"></script>
    <![endif]-->

</head>
<body>
<section id="container">
<!--header start-->
<header class="header fixed-top clearfix">
<!--logo start-->
<div class="brand">

	<a href="index#home" class="logo">
		<p><img src="images/logo.png" alt="谷登ERP后台管理系统"></p>
	</a>
 <!--<div class="sidebar-toggle-box">
		<div class="fa fa-bars"></div>
	</div> -->
</div>
<!--logo end-->
<input id="modifyPwd" type="hidden" value="${modifyPwd }"/>
<div class="nav notify-row" id="top_menu">
	<!--notification start -->
	<ul class="nav top-menu">
		<!-- notification dropdown start-->
		<li class="dropdown">
			<a data-toggle="dropdown" class="dropdown-toggle" href="javascript:;">
				当前公司：<span id="company-current" data-value="${user.currentCompany.id }">${user.currentCompany.fullName }</span>
				<b class="caret"></b>
			</a>
			<ul id="company" class="dropdown-menu extended logout">
				<c:forEach items="${user.pCompany }" var="pCompany">
					<li><a href="javascript:;" data-value="${pCompany.id}">${pCompany.fullName}</a></li>
				</c:forEach>
			</ul>
		</li>
		<!-- notification dropdown end -->
	</ul>
	<!--notification end -->
</div>
<div class="clearfix">
	<!--search & user info start-->
	<ul class="nav pull-right top-menu top-menu-right">
		<!-- user login dropdown start-->
		<li class="dropdown">
			<a data-toggle="dropdown" class="dropdown-toggle" href="javascript:;">
				<span id="market-current" data-value="${user.currentMarket.id }" >${user.currentMarket.fullName }</span>
				<b class="caret"></b>
			</a>
			<ul id="market" class="dropdown-menu extended logout">
				<c:forEach items="${user.currentMarketList }" var="pMarket">
					<li><a href="javascript:;" data-value="${pMarket.id}">${pMarket.fullName}</a></li>
				</c:forEach>
			</ul>
		</li>
		<li class="dropdown">
			<a data-toggle="dropdown" class="dropdown-toggle" href="#">
				<span>${user.name }</span>
				<b class="caret"></b>
			</a>
			<ul class="dropdown-menu extended logout">
				<li><a href="javascript:(function(){Route.params={tab:1};location.href='index#profile';})();"><i class=" fa fa-suitcase"></i>我的账号</a></li>
				<li><a href="javascript:(function(){Route.params={tab:2};location.href='index#setting';})();"><i class="fa fa-cog"></i> 修改密码</a></li>
				<li><a href="logout"><i class="fa fa-key"></i> 注销登录</a></li>
			</ul>
		</li>
		<li id="header_notification_bar" class="dropdown">
			<a data-toggle="dropdown" class="dropdown-toggle" href="#">
				<i><img src="images/icon/msg.png" alt=""></i>
				<span name="noReadCount" class="badge bg-warning">3</span>
			</a>
			<ul class="dropdown-menu extended logout">
				<li><a href="javascript:(function(){location.href='index#sysMessage';})();"><i class=" fa fa-suitcase"></i>我的消息</a></li>
				<li><a href="javascript:(function(){location.href='index#jumpToGtasks';})();"><i class="fa fa-cog"></i>我的待办</a></li>
				<li><a href="javascript:(function(){location.href='index#contractRemind';})();"><i class="fa fa-clock-o"></i>预警</a></li>
			</ul>
		</li>
		<!-- user login dropdown end -->
	</ul>
	<!--search & user info end-->
</div>
</header>
<!--header end-->

<!--main content start-->
<section id="main-content">
	<section id="main-wrapper" class="wrapper">
	</section>
<!--main content end-->
</section>
<!-- Placed js at the end of the document so the pages load faster -->
<!--Core js-->
<script src="lib/jquery.js"></script>
<script src="lib/jquery-ui-1.10.1.custom.min.js"></script>
<script src="lib/bootstrap.js"></script>
<script src="lib/jquery.dcjqaccordion.2.7.js"></script>
<script src="lib/jquery.scrollTo.min.js"></script>
<script src="lib/jquery.slimscroll.js"></script>
<script src="lib/jquery.nicescroll.js"></script>
<script src="lib/validate/jquery.form.js"></script>
<script src="lib/validate/jquery.mockjax.js"></script>
<script src="lib/validate/jquery.validate.min.js"></script>
<script src="lib/localization/messages_zh.js"></script>
<!--[if lte IE 8]><script language="javascript" type="text/javascript" src="lib//flot-chart/excanvas.min.js"></script><![endif]-->

<!--common script init for all pages-->
<script src="lib/scripts.js"></script>
<script src="js/common/gd-util.js"></script>
<script src="js/main.js"></script>
<script src="js/common/router.js"></script>
<script src="js/common/router-lease.js"></script>
<script type="text/javascript">
	var Debug = false,
		_min = "",_version = "", _time = Date.parse(new Date());
	//缓存一些基本的用户信息
	
	var _USER = ${_user};
	//是否拥有某个按钮权限
	function hasButtonAuth(code){
		if(_USER.buttons){
			for(var i = 0;i<_USER.buttons.length;i++){
				var button = _USER.buttons[i];
				if(button.code == code){
					return true;
				}
			}
		}
		return false;
	}
	
</script>
<script>
	// 生成左侧菜单面板
	/*$.leftSideBar('[{"title":["系统管理","fa-home"],"sub":[["公司管理","index#3"],["Demo","index#2"]]},'
		+ '{"title":["首页面板","index#1", "fa-home"]},'
		+ '{"title":["统计图表","fa-sitemap"],"sub":[["折线-统计图","index#chart"],["测试","index#4"]]},'
		+ '{"title":["租约管理","fa-sitemap"],"sub":[["意向书台账","index#chart"],["合同管理","index#test"],["租金及费用调整","index#test"],["合同管理","index#test"],["变更结算系统","index#test"]]},'
		+ '{"title":["登录","page/login.html", "fa-power-off"]}]');*/
	
	function setNoReadMessageCount(){
		$.getJSON(CONTEXT+"sysMessage/getNoReadMessageCount",function(data){

			$("span[name='noReadCount']").text(data.result);
		});	
	}
	setNoReadMessageCount();
		
	/**
	*选择刷新公司,并同时刷新下级联动市场
	**/
	function refreshCompany(companyId){
		Route.company=companyId;
		$.ajax({
			type: "POST",
			dataType: "json",
			url: CONTEXT+"refresh/company",
			data: {"id":companyId},
			success: function (data) {
				if(data.success){
				}
			}
		});
	}
	
	/**
	*选择刷新市场
	**/
	function refreshMarket(marketId){
		Route.market=marketId;
		$.ajax({
			type: "POST",
			dataType: "json",
			url: CONTEXT+"refresh/market",
			data: {"id":marketId},
			success: function (data) {
				if(data.success){
				}
			}
		});
	}
	
	/* YZM 判断市场值得长度 */
	if($("#market li").length == 0){
		$("#market").removeClass("dropdown-menu");
		$("#market").siblings(".dropdown-toggle").find("b").removeClass("caret");
	}else{
		$("#market").addClass("dropdown-menu");
		$("#market").siblings(".dropdown-toggle").find("b").addClass("caret")
	}
	
	/**
	*选择公司，同时变换市场下拉框内容
	*/
	function selectCompany(companyId){
		Route.market="";
		$.ajax({
			type: "GET",
			dataType: "json",
			url: CONTEXT+"select/company",
			data: {"id":companyId},
			success: function (data) {
				if(data.success){
					$("#market").html("");
					$("#market-current").data("value","");
					$("#market-current").html("");
					var marketList = data.result;
					if(marketList.length == 0){
						$("#market").removeClass("dropdown-menu");
						$("#market").siblings(".dropdown-toggle").find("b").removeClass("caret");	
					}else{
						$("#market").addClass("dropdown-menu");
						$("#market").siblings(".dropdown-toggle").find("b").addClass("caret");
					}
					$.each(marketList, function(i, market){
						var id=market.id;
						var name=market.fullName;
						if(i==0){
							$("#market-current").data("value",id);
							$("#market-current").html(name);
							Route.market=id;
						}
						var html = '<li><a href="javascript:;" data-value="'+id+'">'+name+'</a></li>';
						$("#market").append(html);
					});
				}
			}
		});
	}
		
	function refresh(flag){
		$("#market").on("click", "a", function(){
			if(flag){
				$.reloadRoute();
			}
			$("#market-current").data("value",$(this).data("value"));
			$("#market-current").html($(this).html());
			refreshMarket($(this).data("value"));
		});
		$("#company").on("click", "a", function(){
			if(flag){
				$.reloadRoute();
			}
			$("#company-current").data("value",$(this).data("value"));
			$("#company-current").html($(this).html());
			refreshCompany($(this).data("value"));
			selectCompany($(this).data("value"));
		});
		Route.market=$("#market-current").data("value");
		Route.company=$("#company-current").data("value");
	}
	refresh(true);
	var menu;
	//加载左侧菜单
	var initMenu = function(){
		$.ajax({
			type : 'GET',
			url : CONTEXT+"menu/route",
			async: false,//需要同步，异步造成菜单样式BUG
			data : {
			},
			dataType : 'json',
			success : function(data) {
				if (data.success) {
					// 生成左侧菜单面板
					menu = JSON.stringify(data.result);
					if(menu.indexOf("index#report")>0){
						if(!location.hash){
							window.location.href= CONTEXT + "index#report";
						}
					}
				}
			}
		});
	}
	initMenu();
	// 生成左侧菜单面板
	$.leftSideBar(menu);
// 	if(location.hash=="#home"){
		if($("#modifyPwd").val()){
			$.eAlert("登录","您的密码为初始密码，请尽快修改");
			window.location.href="index#modify-pwd";//修改密码界面
		}
// 	}
		jQuery.extend(jQuery.validator.messages, {
			required: "必填",
			range:"输入[{0}-{1}]的值"
		});
</script>
</body>
</html>