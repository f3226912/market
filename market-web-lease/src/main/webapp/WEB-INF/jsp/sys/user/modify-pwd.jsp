<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<section class="header"><span>修改密码</span></section>
<style>
	.eNinput {
	    position: relative;
	}
	.eNinput label.error {
	    position: absolute;
	    top: 0;
	    right: 10%;
	    margin: 0;
	    line-height:30px;
	}
</style>
<form id="form-pwd" class="form-horizontal" style="margin-top: 50px;">
	<div class="form-group" style="width:41%;margin:0 auto;">
		<label class="control-label col-md-4">
			<i class="payment-cycle-sp">*</i>设置新密码： 
		</label>
		<div class="col-md-6 text-left eNinput">
			<input id="nPwd" name="nPwd" type="password" maxlength="20" class="form-control">
		</div>
	</div>
	<div class="form-group" style="width:41%;margin:20px auto;">
		<label class="control-label col-md-4">
			<i class="payment-cycle-sp">*</i>确认新密码： 
		</label>
		<div class="col-md-6 text-left eNinput">
			<input id="confPwd" name="confPwd" type="password" maxlength="20" class="form-control">
		</div>
	</div>
	<div class="form-group col-lg-12 text-center mtop30">
		<button class="btn btn-danger ml15" id="savePwd" type="submit" data-dismiss="modal" style="font-size: 22px; padding: 8px 10px;width: 180px;text-align: center;">保存</button>
	</div>
</form>



