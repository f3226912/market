<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="gd" uri="http://www.gdeng.cn/gd"%>
<div class="system-modal edx">
	<section class="header"><span>套打设置</span></section>
    <div class="">
		<div class="system-modal">
		  	<section class="panel">
		      <header class="">
		          <ul class="nav nav-tabs taodaUl">
		              <li id="printSettingTitle" class="active">
		                  <a data-toggle="tab" href="#printSetting" id="printSettingTab">套打设置</a>
		              </li>
		              <li id="printTemplateTitle" class="">
		                  <a data-toggle="tab" href="#printTemplate" id="printTemplateTab">套打模板设置</a>
		              </li>
		              <li style="float:right;line-height:47px;margin-right:25px">
		              	<div class="right-icon">
				            <gd:btn btncode="BtnPrintSettingAdd"><a id="btn-add" href="index#printSettingAdd"><i class="fa fa-list-alt"></i> 新增</a>&nbsp;&nbsp;</gd:btn>
				            <gd:btn btncode="BtnPrintSettingDelete"><a id="btn-delete" href="javascript:void(0)"><i class="fa fa-eraser"></i> 删除</a></gd:btn>
				        </div>
		              </li>
		          </ul>
		      </header>
		      <div class="panel-body">
		          <div class="tab-content">
		              <div id="printSetting" class="tab-pane active"></div>
		              <div id="printTemplate" class="tab-pane"></div>
		          </div>
		      </div>
		  	</section>
		</div>
	</div>
</div>
