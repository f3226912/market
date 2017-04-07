<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<style>
	.nav-tabs>li{
		margin-top:5px;
	}
</style>
<section class="header"><span>我的账号</span></section>
<section class="panel">
	<header class="tab">
		<ul class="nav nav-tabs">
			<li id="tab-userInfo" class="active" style="margin-left:10px">
				<a data-toggle="tab" href="#userInfo">我的账号</a>
			</li>
			<li id="tab-setting" class="" style="margin-left:3px">
				<a data-toggle="tab" href="#setting">修改密码</a>
			</li>
		</ul>
	</header>
</section>
<input id="tab" type="hidden" value="${tab}"/>
<div class="tab-content text-center">
	<div id="userInfo" class="tab-pane active">
	<form id="form-edit" class="form-horizontal">
		<div class="form-page">
			<div class="form-group col-lg-5">
				<label class="control-label col-md-4">
					账号： 
				</label>
				<div class="col-md-6 text-left">
					<input value="${user.code }" readonly="readonly" class="form-control">
				</div>
			</div>
			<div class="form-group col-lg-5">
				<label class="control-label col-md-4">
					<i class="payment-cycle-sp">*</i>姓名： 
				</label>
				<div class="col-md-6 text-left">
					<input id="name" name="name" value="${user.name }" maxlength="20" class="form-control" autocomplete="off">
				</div>
			</div>
			<div class="form-group col-lg-5">
				<label class="control-label col-md-4">
					集团： 
				</label>
				<div class="col-md-6 text-left">
					<input value="${user.groupName }" readonly="readonly" class="form-control">
				</div>
			</div>
			<div class="form-group col-lg-5">
				<label class="control-label col-md-4">
					公司： 
				</label>
				<div class="col-md-6 text-left">
					<input value="${user.companyName }" readonly="readonly" class="form-control">
				</div>
			</div>
			<div class="form-group col-lg-5">
				<label class="control-label col-md-4">
					市场： 
				</label>
				<div class="col-md-6 text-left">
					<input value="${user.marketName }" readonly="readonly" class="form-control">
				</div>
			</div>
			<div class="form-group col-lg-5">
				<label class="control-label col-md-4">
					部门： 
				</label>
				<div class="col-md-6 text-left">
					<input value="${user.departmentName }" readonly="readonly" class="form-control">
				</div>
			</div>
				<div class="form-group col-lg-5">
				<label class="control-label col-md-4">
					岗位： 
				</label>
				<div class="col-md-6 text-left">
					<input value="${user.postName }" readonly="readonly" class="form-control">
				</div>
			</div>
			<div class="form-group col-lg-5">
				<label class="control-label col-md-4">
					<i class="payment-cycle-sp">*</i>手机： 
				</label>
				<div class="col-md-6 text-left">
					<input id="mobile" name="mobile" value="${user.mobile }" maxlength="11" class="form-control" autocomplete="off">
				</div>
			</div>
			<div class="form-group col-lg-5">
				<label class="control-label col-md-4">
					工号： 
				</label>
				<div class="col-md-6 text-left">
					<input id="deptNo" name="deptNo" value="${user.deptNo }" maxlength="20" class="form-control" autocomplete="off">
				</div>
			</div>
			<div class="form-group col-lg-5">
				<label class="control-label col-md-4">
					座机： 
				</label>
				<div class="col-md-6 text-left">
					<input id="landline" name="landline" value="${user.landline }" maxlength="20" class="form-control" autocomplete="off">
				</div>
			</div>
			<div class="form-group col-lg-5">
				<label class="control-label col-md-4">
					邮箱： 
				</label>
				<div class="col-md-6 text-left">
					<input id="mail" name="mail" value="${user.mail }" maxlength="20" class="form-control" autocomplete="off">
				</div>
			</div>
			<div class="form-group col-lg-10 text-center mtop30">
				<button class="btn btn-danger ml15" id="saveForm" type="submit" data-dismiss="modal">保存</button>
			</div>
		</div>
	</form>
	</div>
	<div id="setting" class="tab-pane">
		<form id="form-pwd" style="overflow: hidden;" class="form-horizontal">
			<div class="form-group" style="width:41%;margin:0 auto;">
				<label class="control-label col-md-4">
					<i class="payment-cycle-sp">*</i>当前密码： 
				</label>
				<div class="col-md-6 text-left">
					<input id="cPwd" name="cPwd" type="password"  maxlength="20" class="form-control">
				</div>
			</div>
			<div class="form-group" style="width:41%;margin:20px auto;">
				<label class="control-label col-md-4">
					<i class="payment-cycle-sp">*</i>设置新密码： 
				</label>
				<div class="col-md-6 text-left">
					<input id="nPwd" name="nPwd" type="password"  maxlength="20" class="form-control">
				</div>
			</div>
			<div class="form-group" style="width:41%;margin:20px auto;">
				<label class="control-label col-md-4">
					<i class="payment-cycle-sp">*</i>确认新密码： 
				</label>
				<div class="col-md-6 text-left">
					<input id="confPwd" name="confPwd" type="password"  maxlength="20" class="form-control">
				</div>
			</div>
			<div class="form-group text-center mtop30">
				<button class="btn btn-danger ml15" id="savePwd" type="submit" data-dismiss="modal">保存</button>
			</div>
		</form>
	</div>
</div>



