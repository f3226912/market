<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page isELIgnored="true"%>
<%@ taglib prefix="gd" uri="http://www.gdeng.cn/gd"%>
<section class="contract">
	<div class="header">参数设置</div>
	<div class="col-md-12 col-pd-13">
		<div class="contract-info">
			<div class="contract-title-block">
				<span class="contract-info-h3">&nbsp;&nbsp;&nbsp;&nbsp;市场准备&nbsp;&nbsp;&nbsp;&nbsp;</span>
			</div>
			<ul class="par-ind-con">
				<li><gd:btn btncode="BtnMarketQuery"><a href="index#market">市场管理</a></gd:btn></li>
				<li><gd:btn btncode="BtnExpQuery"><a href="index#expenditure">费项定义</a></gd:btn></li>
				<li><gd:btn btncode="BtnExpStandardQuery"><a href="javascript:(function(){Route.params={};location.href='index#expenditureStandard';})();">计费标准管理</a></gd:btn></li>
				<li><gd:btn btncode="BtnMeasureTypeQuery"><a href="index#measureType">计量表分类</a></gd:btn></li>
				<li><gd:btn btncode="BtnMeasureQuery"><a href="index#measureSetting">计量表管理</a></gd:btn></li>
			</ul>
		</div>
		<div class="contract-info">
			<div class="contract-title-block">
				<span class="contract-info-h3">&nbsp;&nbsp;&nbsp;&nbsp;资源管理&nbsp;&nbsp;&nbsp;&nbsp;</span>
			</div>
			<ul class="par-ind-con">
				<li><a href="index#marketResourceType">资源类型管理</a></li>
			</ul>
		</div>
		<div class="contract-info" style="margin-bottom: 50px;">
			<div class="contract-title-block">
				<span class="contract-info-h3">&nbsp;&nbsp;&nbsp;&nbsp;租约管理&nbsp;&nbsp;&nbsp;&nbsp;</span>
			</div>
			<ul class="par-ind-con">
				<li><a href="index#leaseParameter">租约管理参数设置</a></li>
			</ul>
		</div>
	</div>
</section>