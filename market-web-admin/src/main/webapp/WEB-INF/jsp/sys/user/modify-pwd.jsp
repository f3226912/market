<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<section class="header"><span>修改密码</span></section>
<form id="form-pwd" class="form-horizontal">
	<div class="form-group col-lg-5">
		<label class="control-label col-md-4">
			<i class="payment-cycle-sp">*</i>设置新密码： 
		</label>
		<div class="col-md-6 text-left">
			<input id="nPwd" name="nPwd" type="password" maxlength="20" class="form-control">
		</div>
	</div>
	<div class="form-group col-lg-5">
		<label class="control-label col-md-4">
			<i class="payment-cycle-sp">*</i>确认新密码： 
		</label>
		<div class="col-md-6 text-left">
			<input id="confPwd" name="confPwd" type="password" maxlength="20" class="form-control">
		</div>
	</div>
	<div class="form-group col-lg-10 text-center mtop30">
		<button class="btn btn-danger ml15" id="savePwd" type="button" data-dismiss="modal">保存</button>
	</div>
</form>



