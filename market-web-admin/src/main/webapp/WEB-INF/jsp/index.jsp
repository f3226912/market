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
<input id="modifyPwd" type="hidden" value="${modifyPwd }"/>
    <a href="index#home" class="logo">
        <p><img src="images/logo.png" alt="谷登ERP后台管理系统"></p>
    </a>
   <!--  <div class="sidebar-toggle-box">
        <div class="fa fa-bars"></div>
    </div> -->
</div>
<!--logo end-->

<div class="nav notify-row" id="top_menu">
    <!--  notification start -->
    <ul class="nav top-menu">
        <!-- notification dropdown start-->
        <li class="dropdown">

        </li>
        <!-- notification dropdown end -->
    </ul>
    <!--  notification end -->
</div>
<div class="clearfix">
    <!--search & user info start-->
    <ul class="nav pull-right top-menu top-menu-right">
        <!-- user login dropdown start-->
        <li class="dropdown">
           
        </li>
        <li class="dropdown">
            <a data-toggle="dropdown" class="dropdown-toggle" href="#">
                <span>系统管理员</span>
                <b class="caret"></b>
            </a>
            <ul class="dropdown-menu extended logout">
                <li><a href="javascript:(function(){Route.params={tab:1};location.href='index#profile';})();"><i class=" fa fa-suitcase"></i>我的账号</a></li>
				<li><a href="javascript:(function(){Route.params={tab:2};location.href='index#setting';})();"><i class="fa fa-cog"></i> 修改密码</a></li>
				<li><a href="logout"><i class="fa fa-key"></i> 注销登录</a></li>
            </ul>
        </li>
        <li id="header_notification_bar" class="dropdown">
           
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
        _min = "",_version = "";
    if($("#modifyPwd").val()){
		$.eAlert("登录","您的密码为初始密码，请尽快修改");
		window.location.href="index#modify-pwd";//修改密码界面
	}
</script>
<script>

    // 生成左侧菜单面板
    $.leftSideBar('[{"title":["系统管理","fa-home"],"sub":[["公司管理","index#company"],["用户管理","index#platFormUser"]]}]');
    jQuery.extend(jQuery.validator.messages, {
		required: "必填",
		range:"输入[{0}-{1}]的值"
	});
</script>
</body>
</html>