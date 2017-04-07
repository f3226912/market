<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/pub/tags.inc" %>
<div class="system-modal">
	<section class="header">编辑用户</section>
	<form id="form-editComUser" class="form-horizontal clearfix cmxform">
	<section class="panel">
		<header class="tab">
			<ul class="nav nav-tabs">
				<li id="tab-userInfo" class="active">
					<a data-toggle="tab" href="#userInfo">基本信息</a>
				</li>
				<li id="tab-postInfo" class="">
					<a data-toggle="tab" href="#postInfo">分配岗位</a>
				</li>
			</ul>
		</header>
		<input id="id" name="id" value="${user.id }" type="hidden" class="form-control">
		<div class="tab-content text-center">
			<div id="userInfo" class="tab-pane active validate-error">
				<div class="form-group col-lg-12">
					<label class="control-label col-md-4">
						账号： 
					</label>
					<div class="col-md-6 text-left">
						<input value="${user.code }" readonly="readonly" class="form-control">
					</div>
				</div>
				<div class="form-group col-lg-12">
					<label class="control-label col-md-4">
						<i class="payment-cycle-sp">*</i>姓名： 
					</label>
					<div class="col-md-6 text-left">
						<input id="name" name="name" value="${user.name }" maxlength="20" class="form-control" autocomplete="off">
					</div>
				</div>
				<div class="form-group col-lg-12">
					<label class="control-label col-md-4">
						<i class="payment-cycle-sp">*</i>手机： 
					</label>
					<div class="col-md-6 text-left">
						<input id="mobile" name="mobile" value="${user.mobile }" maxlength="11" class="form-control" autocomplete="off">
					</div>
				</div>
				<div class="form-group col-lg-12">
					<label class="control-label col-md-4">
						<i class="payment-cycle-sp">*</i>部门： 
					</label>
					<div class="input-group col-sm-7">
						<input id="orgId" name="orgId" value="${user.departmentId }" type="hidden" class="form-control">
						<input class="form-control" type="text" id="orgName" name="orgName" value="${user.departmentName }" readonly="readonly" placeholder="从部门树选择部门" style="width: 274px; margin-left: 15px; border-radius: 2px"/>
						<div class="input-group-btn">
							<button type="button" class="btn btn-white dropdown-toggle" data-toggle="dropdown" style="margin-left: -161px;"><span class="caret"></span></button>
							<div class="dropdown-menu pull-right" style="right:68px;">
								<ul id="tree" class="ztree no-border" style=" padding-left: 0;"></ul>
							</div>
						</div>
					</div>
				</div>
				<div class="form-group col-lg-12">
					<label class="control-label col-md-4">
						工号： 
					</label>
					<div class="col-md-6 text-left">
						<input id="deptNo" name="deptNo" value="${user.deptNo }" maxlength="20" class="form-control" autocomplete="off">
					</div>
				</div>
				<div class="form-group col-lg-12">
					<label class="control-label col-md-4">
						座机： 
					</label>
					<div class="col-md-6 text-left">
						<input id="landline" name="landline" value="${user.landline }" maxlength="20" class="form-control" autocomplete="off">
					</div>
				</div>
				<div class="form-group col-lg-12">
					<label class="control-label col-md-4">
						邮箱： 
					</label>
					<div class="col-md-6 text-left">
						<input id="mail" name="mail" value="${user.mail }" maxlength="20" class="form-control" autocomplete="off">
					</div>
				</div>
				<div class="form-group col-lg-12">
					<label class="control-label col-md-4">
						<i class="payment-cycle-sp">*</i>状态： 
					</label>
					<div class="col-md-6 text-left">
					<label for="radio-1-151"><input type="radio" id="radio-1-151" name="status" class="regular-radios" value="3" <c:if test="${user.status ==3 }">checked="checked"</c:if>>
						<span class="radio-mtd"></span><em class="radio-ems" style="font-weight:400;">禁用</em></label>
					<label for="radio-1-050" style="margin-left:30px;"><input type="radio" id="radio-1-050" name="status" value="1" <c:if test="${user.status ==1 }">checked="checked"</c:if> class="regular-radios">
						<span class="radio-mtd"></span><em class="radio-ems" style="font-weight:400;">启用</em></label>
					</div>
				</div>
			</div>
			<input id="posts" name="posts" type="hidden"/>
			<div id="postInfo" class="tab-pane clearfloat">
				<div class="post-manage">
					<div class="post-ser">
						<!-- <input id="postName" name="postName" placeholder="部门树" readonly="readonly" class="post-serR" />
						<button id="queryPost" class="lab-post" type="button">选择部门</button> -->
					<label class="control-label col-md-4" style="margin-top: -5px;">
						<i class="payment-cycle-sp">*</i>部门： 
					</label>
					<div class="input-group col-sm-7">
						<input id="orgId2" name="orgId2" type="hidden" class="form-control">
						<input class="form-control" id="orgName2" name="orgName2" readonly="readonly" placeholder="从部门树选择部门" style="margin-top:9px;"/>
						<div class="input-group-btn">
							<button type="button" class="btn btn-white dropdown-toggle" data-toggle="dropdown"><span class="caret"></span></button>
							<div class="dropdown-menu pull-right">
								<ul id="tree2" class="ztree no-border" style=" padding-left: 0;"></ul>
							</div>
						</div>
					</div>
					</div>
					<div class="post-listT">
						 <ul class="post-lt">
						 </ul>
					</div>
				</div>
				<div class="post-icot">
					<ul class="post-T">
						<li class="postRight">>></li>
						<li class="postLeft"><<</li>
					</ul>
				</div>
				<div class="post-manage">
					<div class="post-ser">
						<p class="post-fp">已分配岗位</p>
					</div>
					<div class="post-listT">
						 <ul class="post-rt">
						 </ul>
					</div>
				</div>	
			</div>
		</div>
		</section>
		<div class="form-group col-lg-10 text-center mtop30" style="margin-left:114px;">
		<button data-dismiss="modal" class="btn btn-default" onclick="javascript:window.location='index#companyUser'" type="button" style="width:120px; height:40px; font-size:20px;">取消</button>
		<gd:btn btncode="BtnSysUserEditSave"><button class="btn btn-danger ml15" id="editForm" type="submit" data-dismiss="modal" style="width:120px; height:40px; font-size:20px;">保存</button></gd:btn>
	</div>
	</form>
</div>