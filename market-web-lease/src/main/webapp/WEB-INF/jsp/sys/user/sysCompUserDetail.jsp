<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/pub/tags.inc" %>
<section class="header">用户详情</section>
<div class="form-page mtop30">
	<form id="form-editComUser" action="post/addPost" class="form-horizontal clearfix">
		<div class="form-group col-lg-5">
			<label class="control-label col-md-4">
				登录账号： 
			</label>
			<div class="col-md-6 text-left">
				<input value="${user.code }" readonly class="form-control">
			</div>
		</div>
		<div class="form-group col-lg-5">
			<label class="control-label col-md-4">
				姓名： 
			</label>
			<div class="col-md-6 text-left">
				<input value="${user.name }" readonly class="form-control">
			</div>
		</div>
		<div class="form-group col-lg-5">
			<label class="control-label col-md-4">
				所属公司： 
			</label>
			<div class="col-md-6 text-left">
				<input value="${user.companyName }" readonly class="form-control">
			</div>
		</div>
		<div class="form-group col-lg-5">
			<label class="control-label col-md-4">
				所属市场： 
			</label>
			<div class="col-md-6 text-left">
				<input value="${user.marketName }" readonly class="form-control">
			</div>
		</div>
		<div class="form-group col-lg-5">
			<label class="control-label col-md-4">
				所属部门： 
			</label>
			<div class="col-md-6 text-left">
				<input value="${user.departmentName }" readonly class="form-control">
			</div>
		</div>
		<div class="form-group col-lg-5">
			<label class="control-label col-md-4">
				所属岗位： 
			</label>
			<div class="col-md-6 text-left">
				<input value="${user.postName }" readonly class="form-control">
			</div>
		</div>
		<div class="form-group col-lg-5">
			<label class="control-label col-md-4">
				工号： 
			</label>
			<div class="col-md-6 text-left">
				<input value="${user.deptNo }" readonly class="form-control">
			</div>
		</div>
		<div class="form-group col-lg-5">
			<label class="control-label col-md-4">
				手机： 
			</label>
			<div class="col-md-6 text-left">
				<input value="${user.mobile }" readonly class="form-control">
			</div>
		</div>
		<div class="form-group col-lg-5">
			<label class="control-label col-md-4">
				座机： 
			</label>
			<div class="col-md-6 text-left">
				<input value="${user.landline }" readonly class="form-control">
			</div>
		</div>
		<div class="form-group col-lg-5">
			<label class="control-label col-md-4">
				邮箱： 
			</label>
			<div class="col-md-6 text-left">
				<input value="${user.mail }" readonly class="form-control">
			</div>
		</div>
		<div class="form-group col-lg-5">
			<label class="control-label col-md-4">
				账号状态： 
			</label>
			<div class="col-md-6 text-left">
				<input value="${user.statusDesc }" readonly class="form-control">
			</div>
		</div>
		<div class="form-group col-lg-10 text-center mtop30 sys-btn-style">
			<button id="back" class="btn btn-danger ml15" type="button" data-dismiss="modal">返回</button>
		</div>
	</form>
</div>