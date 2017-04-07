<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/pub/tags.inc" %>
<%@ page isELIgnored="true"%>
<div class="system-modal" style="overflow-x: hidden;">
<section class="header"><span>权限管理</span>
</section>
<form id="form-edit" class="form-horizontal">
<p class="title">选择岗位</p>
<input id="id" name="id" type="hidden" class="form-control">
<div class="main-search">
	 <div class="main-ipt-types" style="padding-bottom: 1px;">
			<div class="">
			<div class="form-group col-sm-6">
				<label class="control-label col-sm-4" for="department">部门：</label>
				<div class="input-group col-sm-7">
					<input id="orgId" name="orgId" type="hidden" class="form-control">
					<input class="form-control" type="text" id="orgName" name="orgName" readonly="readonly" required placeholder="从部门树选择部门"/>
					<div class="input-group-btn">
						<button type="button" class="btn btn-white dropdown-toggle" data-toggle="dropdown"><span class="caret"></span></button>
						<div class="dropdown-menu pull-right">
							<ul id="tree" class="ztree no-border"></ul>
						</div>
					</div>
				</div>
				</div>
			<div class="form-group" style="margin-bottom:0;">
<!-- 					<button class="btn btn-danger " type="button">查询</button> -->
			</div>
			</div>
			<div class="form-group">
				<span class="control-label col-sm-2">岗位：</span>
				<div id="post-list" ></div>
<!-- 				<label for="radio-1-1"><input type="radio" id="radio-1-1" name="radio-1-set" class="regular-radio" checked/> -->
<!-- 				<span class="radio-mt10"></span><em class="radio-em">财务总监</em></label> -->
<!-- 				<label for="radio-1-2"><input type="radio" id="radio-1-2" name="radio-1-set" class="regular-radio" /> -->
<!-- 				<span class="radio-mt10"></span><em class="radio-em">财务总监</em></label> -->
<!-- 				<label for="radio-1-3"><input type="radio" id="radio-1-3" name="radio-1-set" class="regular-radio" /> -->
<!-- 				<span class="radio-mt10"></span><em class="radio-em">财务总监</em></label> -->
			</div>
	 </div>
 </div>
<input id="funcStr" name="funcStr" type="hidden"  class="form-control"/>
<input id="dataStr" name="dataStr" type="hidden"  class="form-control"/>
<p class="title">权限分配</p>
<section class="panel">
	<header class="tab">
		<ul class="nav nav-tabs">
			<li id="tab-postFunAuth" class="active">
				<a data-toggle="tab" href="#funAuth">功能权限</a>
			</li>
			<li id="tab-postDataAuth" class="">
				<a data-toggle="tab" href="#dataAuth">数据权限</a>
			</li>
		</ul>
	</header>
	<div class="tab-content">
		<div id="funAuth" class="tab-pane" style="margin-top:0;">
			<div class="tab-title clearfix">
				<div class="col-sm-6" style="padding-left:136px;height:40px;line-height:40px;">系统功能</div>
				<div class="col-sm-6" style="padding-top:5px;">
					<div class="checkbox">
						<label>
							<input id="check-funAuth" type="checkbox"> 全选
						</label>
					</div>
				</div>
			</div>
			<ul id="tree2" class="ztree"></ul>
		</div>
		<div id="dataAuth" class="tab-pane">
			<div class="tab-title clearfix">
				<div class="col-sm-6" style="padding-left:136px;height:40px;line-height:40px;">数据权限</div>
				<div class="col-sm-6" style="padding-top:5px;">
					<div class="checkbox">
						<label>
							<input id="check-dataAuth" type="checkbox"> 全选
						</label>
					</div>
				</div>
			</div>
			<ul id="tree3" class="ztree"></ul>
		</div>
	</div>
</section>
</div>


<div class="form-group col-lg-10 text-center mtop30 sys-btn-style">
	<gd:btn btncode="BtnSysAuthSave"><button class="btn btn-danger ml15" id="saveForm" type="button" data-dismiss="modal">保存</button>&nbsp;</gd:btn>
	<button id="cancel-form" data-dismiss="modal" class="btn btn-default" type="button">重置</button>
</div>
</form>

