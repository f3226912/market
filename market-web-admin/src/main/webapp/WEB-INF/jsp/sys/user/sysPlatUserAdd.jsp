<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<section class="header">新增用户</section>
<div class="form-page validate-error" style="padding-top:10px;">
	<form id="form-addPlatUser" action="post/addPost" class="form-horizontal cmxform clearfix">
		<div class="row">
		<div class="form-group col-lg-5">
			<label class="control-label col-md-4">
					账号类型： 
				</label>
			<div class="col-md-6 text-left ">
				<label class="radio-inline">
					<input type="radio" value="1" name="type"> 平台
				</label>
				<label class="radio-inline">
					<input type="radio" value="2" checked name="type"> 公司
				</label>
			</div>
		</div>
		<div class="form-group col-lg-5">
			<label class="control-label col-md-4">
					<i class="payment-cycle-sp">*</i>登录账号： 
				</label>
			<div class="col-md-6 text-left">
				<input id="code" name="code" type="text" class="form-control" required maxLength="20" autocomplete="off">
			</div>
		</div>
		</div>
		<div class="row">
		<div class="form-group col-lg-5">
			<label class="control-label col-md-4">
					<i class="payment-cycle-sp">*</i>所属公司： 
				</label>
			<div class="col-md-6 text-left">
				<select name="groupId" id="groupId" required class="form-control">
				</select>
			</div>
			
		</div>
		<div class="form-group col-lg-5">
			<label class="control-label col-md-4">
					<i class="payment-cycle-sp">*</i>姓名： 
				</label>
			<div class="col-md-6 text-left">
				<input id="name" name="name" type="text" class="form-control" required maxLength="20" autocomplete="off">
			</div>
		</div>
		</div>
		<div class="row">
		<div class="form-group col-lg-5">
			<label class="control-label col-md-4">
					<i class="payment-cycle-sp">*</i>手机： 
				</label>
			<div class="col-md-6 text-left">
				<input id="mobile" name="mobile" type="text" class="form-control" required maxLength="11" number="true" autocomplete="off">
			</div>
		</div>
		<div class="form-group col-lg-5">
			<label class="control-label col-md-4">
					座机： 
				</label>
			<div class="col-md-6 text-left">
				<input id="landline" name="landline" type="text" class="form-control" maxLength="20" autocomplete="off">
			</div>
		</div>
		</div>
		<div class="row">
		<div class="form-group col-lg-5">
			<label class="control-label col-md-4">
					邮箱： 
				</label>
			<div class="col-md-6 text-left">
				<input id="mail" name="mail" type="text" class="form-control" maxLength="20" autocomplete="off">
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
			<button class="btn btn-danger ml15" id="addForm" type="button" data-dismiss="modal">确认</button>
		</div>
	</form>
	<div style="display:none;" id="selectCompany">
		<option value="">--请选择--</option>
	</div>
	<div style="display:none;" id="selectPlat">
		<option value="0">谷登平台</option>
	</div>
</div>