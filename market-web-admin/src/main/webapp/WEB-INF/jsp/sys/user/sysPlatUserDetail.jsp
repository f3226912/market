<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<section class="header">用户详情</section>
<div class="form-page">
	<form id="form-editComUser" action="post/addPost" class="form-horizontal clearfix">
		<div class="row">
		<div class="form-group col-lg-5">
			<label class="control-label col-md-4">
					账号类型： 
				</label>
			<div class="col-md-6 text-left">
				<label class="radio-inline">
					<input type="radio" value="1" name="type" disabled> 平台
				</label>
				<label class="radio-inline">
					<input type="radio" value="2" checked name="type" disabled> 公司
				</label>
			</div>
		</div>
		<div class="form-group col-lg-5">
			<label class="control-label col-md-4">
					<i class="payment-cycle-sp">*</i>登录账号： 
				</label>
			<div class="col-md-6 text-left">
				<input id="code" name="code" type="text" class="form-control" readonly>
			</div>
		</div>
		</div>
		<div class="row">
		<div class="form-group col-lg-5">
			<label class="control-label col-md-4">
					<i class="payment-cycle-sp">*</i>所属公司： 
			</label>
			<div class="col-md-6 text-left">
				<input id="groupName" name="groupName" type="text" class="form-control" readonly>
			</div>
		</div>
		<div class="form-group col-lg-5">
			<label class="control-label col-md-4">
					<i class="payment-cycle-sp">*</i>姓名： 
				</label>
			<div class="col-md-6 text-left">
				<input id="name" name="name" type="text" class="form-control" readonly>
			</div>
		</div>
		</div>
		<div class="row">
		<div class="form-group col-lg-5">
			<label class="control-label col-md-4">
					<i class="payment-cycle-sp">*</i>手机： 
				</label>
			<div class="col-md-6 text-left">
				<input id="mobile" name="mobile" type="text" class="form-control" readonly>
			</div>
		</div>
		<div class="form-group col-lg-5">
			<label class="control-label col-md-4">
					座机： 
				</label>
			<div class="col-md-6 text-left">
				<input id="landline" name="landline" type="text" class="form-control" readonly>
			</div>
		</div>
		</div>
		<div class="row">
		<div class="form-group col-lg-5">
			<label class="control-label col-md-4">
					邮箱： 
				</label>
			<div class="col-md-6 text-left">
				<input id="mail" name="mail" type="text" class="form-control" readonly>
			</div>
		</div>
		<div class="form-group col-lg-5">
			<label class="control-label col-md-4">
					状态： 
				</label>
			<div class="col-md-6 text-left">
				<label class="radio-inline">
					<input type="radio" value="3" name="status"> 禁用
				</label>
				<label class="radio-inline">
					<input type="radio" value="1" checked name="status"> 启用
				</label>
			</div>
		</div>
		</div>
		
		
		<div class="form-group col-sm-10 text-center mtop30 sys-btn-style">
			<button data-dismiss="modal" class="btn btn-default" type="button" onclick="javascript:window.location='index#platFormUser'">取消</button>
			<button class="btn btn-danger ml15" id="editForm" type="button" data-dismiss="modal" onclick="javascript:window.location='index#platFormUser'">确认</button>
		</div>
	</form>
</div>