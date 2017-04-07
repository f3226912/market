<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/pub/tags.inc" %>
<style>
 #orgName-error{ 
	position: relative;
    top: 9px;
    left: -92px;
    z-index: 9;
}
</style>
<section class="header">编辑岗位</section>

<section class="panel system-modal" style="margin-bottom:0;">
	<header class="tab">
		<ul class="nav nav-tabs">
			<li id="tab-postInfo" class="active">
				<a data-toggle="tab" href="#postInfo">基本信息</a>
			</li>
			<li id="tab-postInfo" class="">
				<a data-toggle="tab" href="#funAuth">功能权限</a>
			</li>
			<li id="tab-postInfo" class="">
				<a data-toggle="tab" href="#dataAuth">数据权限</a>
			</li>
		</ul>
	</header>
</section>

<div class="form-page system-modal">
	<form id="form-edit" class="form-horizontal clearfix">
	<input name="id" type="hidden" value="${post.id }" class="form-control">
		<div class="tab-content validate-error">
			<div id="postInfo" class="tab-pane active">
				<div class="form-group col-lg-12">
					<label class="control-label col-md-4">
						<i class="payment-cycle-sp">*</i>所属部门： 
					</label>
					<div class="input-group col-sm-7" style="margin-bottom:15px;">
						<input id="orgId" name="orgId" type="hidden" class="form-control">
						<input id="orgId" name="orgId" type="hidden" value="${post.orgId }"  class="form-control">
						<input class="form-control" type="text" id="orgName" name="orgName" value="${post.orgFullName }" readonly="readonly" placeholder="从部门树选择部门" style="width:243px; border-radius:2px;"/>
						<div class="input-group-btn" style="position:relative;">
							<button type="button" class="btn btn-white dropdown-toggle" data-toggle="dropdown" style="position:absolute; top:0; right:114px; "><span class="caret"></span></button>
							<div class="dropdown-menu pull-right" style="position:absolute; right: 112px;">
								<ul id="tree" class="ztree no-border" style="padding-left:0px;"></ul>
							</div>
						</div>
					</div>
				</div>
				<div class="form-group col-lg-12">
					<label class="control-label col-md-4">
							<i class="payment-cycle-sp">*</i>岗位名称：
						</label>
					<div class="col-md-6 text-left">
						<input name="name" type="text" value="${post.name }" maxlength="20" class="form-control" style="width:270px; position: relative; right:15px;" autocomplete="off">
					</div>
				</div>
				<div class="form-group col-lg-12" style="margin-top:15px;">
					<label class="control-label col-md-4">岗位说明：</label>
					<div class="col-md-6 text-left">
						<textarea name="remark" class="form-control" rows="5" cols="150" style="position: relative; right: 14px;" maxlength="200" autocomplete="off">${post.remark }</textarea>
					</div>
				</div>
			</div>
		
			<div id="funAuth" class="tab-pane">
				<div class="tab-title row">
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
				<div class="tab-title row">
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
		
		<input id="funcStr" name="funcStr" type="hidden"  class="form-control"/>
		<input id="dataStr" name="dataStr" type="hidden"  class="form-control"/>
		
		<div class="form-group col-lg-10 text-center mtop30" style="margin-left:30px;">
			<button id="cancel-form" data-dismiss="modal" class="btn btn-default" type="button" style="width:120px; height:40px; font-size:20px;">取消</button>
			<gd:btn btncode="BtnSysPostEditSave"><button class="btn btn-danger ml15" id="saveForm" type="submit" data-dismiss="modal" style="width:120px; height:40px; font-size:20px;">保存</button></gd:btn>
		</div>
	</form>
</div>
