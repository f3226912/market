<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/pub/constants.inc" %>
<!DOCTYPE html>
<html lang="en">
<head>
	<base href="${CONTEXT}" >
	<%@ include file="/WEB-INF/jsp/pub/head.inc" %>
    <meta charset="utf-8">

    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <meta name="description" content="">
    <meta name="author" content="ThemeBucket">
    <link rel="shortcut icon" href="favicon.ico">

    <title>谷登科技智慧农批系统</title>
    <!-- Custom styles for this template -->
    <link href="lib/css/bootstrap.min.css" rel="stylesheet">
    <link href="lib/css/bootstrap-reset.css" rel="stylesheet">
    <link href="lib/font-awesome/css/font-awesome.css" rel="stylesheet">
    <link href="lib/css/jquery-jvectormap-1.2.2.css" rel="stylesheet">
    <!-- Custom styles for this template -->
    <link href="lib/css/style.css" rel="stylesheet">
    <link href="lib/css/style-responsive.css" rel="stylesheet"/>
    
    <link rel="stylesheet" href="lib/css/swiper.min.css"/>
    <link href="css/common/index-custom.css" rel="stylesheet">
    <link rel="stylesheet" href="css/login.css"/>
    <style>
    	.mark-wrap{
    		top:0;
    		left:0;
    	}
    	.header{
    		background:#ffffff;
    	}
    </style>
</head>

  <body class="login-body">
	<div class="wrap">
		<div class="header"><img src="images/login/loginLogo.png"/></div>
		<div class="content">
			<div class="left">
				<div class="swiper-container">
			        <div class="swiper-wrapper">
			            <div class="swiper-slide"><img src="images/login/banner-1.jpg"/></div>
			            <div class="swiper-slide"><img src="images/login/banner-2.jpg"/></div>
			            <div class="swiper-slide"><img src="images/login/banner-3.jpg"/></div>
			        </div>
			        <div class="swiper-pagination"></div>
			    </div>
			</div>
			<div class="right">
				<span>用户登录</span>
				<form id="form-login" class="form-horizontal clearfix cmxform" method="post">
					<div class="inputAll">
						<div class="icon"><img src="images/icon/loginMen.png"/></div>
						<div class="input"><input id="code" name="code" maxlength="20" type="text" placeholder="请输入登录帐号"/></div>
					</div>
					<div class="inputAll">
						<div class="icon"><img src="images/icon/loginPassword.png"/></div>
						<div class="input"><input id="pwd" name="pwd" maxlength="20" type="password" placeholder="请输入登录密码"/></div>
					</div>
					<div class="login-error" style="display: none;margin-left: 19px;margin-top: 5px;margin-bottom: -19px;">
						<label class="login-error login-error-msg" style="color: #BF5F5F;">错误提示</label>
					</div>
					<div class="bottom">登录</div>
				</form>
			</div>
		</div>
		<div class="footer">
			粤ICP备15098440号 &nbsp;版权所有©深圳谷登科技有限公司
		</div>
	</div>
    <script src="lib/jquery.js"></script>
    <script src="lib/scripts.js"></script>
    <script src="lib/bootstrap.min.js"></script>
    <script src="lib/swiper.min.js"></script>
    <script src="lib/validate/jquery.form.js"></script>
    <script src="js/common/gd-util.js"></script>
    <script>
    var swiper = new Swiper('.swiper-container', {
        pagination: '.swiper-pagination',
        autoplay : 3000,
        loop : true,
        autoplayDisableOnInteraction : false,
        paginationClickable: true
    });
    $(function(){
    	$(document).keydown(function(event){
	    	if(event.keyCode==13){
	    	$(".login-body .content .right .bottom").click();
    		}
    	});
    });

	$(".bottom").click(function(){
		$("#form-login").ajaxSubmit({
			type: "POST",
			dataType:"json",
			url:CONTEXT+"login",
			success: function (data) {
				if(data.success){
					$(".login-error").hide();
					$.eLoading(true);
					window.location.href = CONTEXT +"index";
				} else {
					$(".login-error-msg").html(data.msg);
					$(".login-error").show();
				}
			},
			error : function(data) {
				$(".login-error-msg").html(data.msg);
				$(".login-error").show();
			}
		})
	});
	</script>
  </body>
</html>
