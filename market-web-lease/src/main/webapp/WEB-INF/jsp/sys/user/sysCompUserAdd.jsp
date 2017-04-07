<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/pub/tags.inc" %>
<div class="system-modal">
	<section class="header">新增用户</section>
	<form id="form-addComUser" class="form-horizontal clearfix cmxform" method="post">
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

		<div class="tab-content text-center">
			<div id="userInfo" class="tab-pane active validate-error">
				<div class="form-page clearfix" style="margin-top:0;">
						<div class="form-group col-lg-12">
							<label class="control-label col-md-4">
									<i class="payment-cycle-sp">*</i>登录账号： 
								</label>
							<div class="col-md-6 text-left">
								<input id="code" name="code" type="text" maxlength="20" class="form-control" value="" autocomplete="off">
							</div>
						</div>
						<div class="form-group col-lg-12">
							<label class="control-label col-md-4">
									<i class="payment-cycle-sp">*</i>姓名： 
								</label>
							<div class="col-md-6 text-left">
								<input id="name" name="name" type="text" maxlength="20" class="form-control" value="" autocomplete="off">
							</div>
						</div>
						<div class="form-group col-lg-12">
							<label class="control-label col-md-4">
									<i class="payment-cycle-sp">*</i>手机： 
								</label>
							<div class="col-md-6 text-left">
								<input id="mobile" name="mobile" type="text" maxlength="11" class="form-control" value="" autocomplete="off">
							</div>
						</div>
						<div class="form-group col-lg-12">
							<label class="control-label col-md-4">
								<i class="payment-cycle-sp">*</i>所属部门： 
							</label>
							<div class="input-group col-sm-7">
								<input id="orgId" name="orgId" type="hidden" class="form-control">
								<input class="form-control" type="text" id="orgName" name="orgName" readonly="readonly" placeholder="从部门树选择部门" style="width: 253px; margin-left: 15px; border-radius: 2px"/>
								<div class="input-group-btn">
									<button type="button" class="btn btn-white dropdown-toggle" data-toggle="dropdown" style="position: relative;right: 70px; width: 48px"><span class="caret" style=" position: absolute;"></span></button>
									<div class="dropdown-menu pull-right" style="right:70px;">
										<ul id="tree" class="ztree no-border" style="padding-left:0"></ul>
									</div>
								</div>
							</div>
							<label class="control-label col-md-4 orgName-error" >
								<i class="payment-cycle-sp"></i>
							</label>
							<div class="col-md-6 text-left orgName-error" style="display: none;"><label style="margin: 5px 0;color: #B94A48;font-weight: 400;">请选择部门</label></div>
						</div>
						<div class="form-group col-lg-12">
							<label class="control-label col-md-4">
									工号： 
								</label>
							<div class="col-md-6 text-left">
								<input id="deptNo" name="deptNo" type="text" maxlength="20" class="form-control" value="" autocomplete="off">
							</div>
						</div>
						<div class="form-group col-lg-12">
							<label class="control-label col-md-4">
									座机： 
								</label>
							<div class="col-md-6 text-left">
								<input id="landline" name="landline" type="text" maxlength="20" class="form-control" value="" autocomplete="off">
							</div>
						</div>
						<div class="form-group col-lg-12">
							<label class="control-label col-md-4">
									邮箱： 
								</label>
							<div class="col-md-6 text-left">
								<input id="mail" name="mail" type="text" maxlength="20" class="form-control" value="" autocomplete="off">
							</div>
						</div>
						<div class="form-group col-lg-12">
							<label class="control-label col-md-4">
								<i class="payment-cycle-sp">*</i>状态： 
							</label>
							<div class="col-md-6 text-left">
							<label for="radio-1-151"><input type="radio" id="radio-1-151" name="status" class="regular-radios" value="3">
								<span class="radio-mtd"></span><em class="radio-ems" style="font-weight:400;">禁用</em></label>
							<label for="radio-1-050" style="margin-left:30px;"><input type="radio" id="radio-1-050" name="status" value="1" checked class="regular-radios">
								<span class="radio-mtd"></span><em class="radio-ems" style="font-weight:400;">启用</em></label>
							</div>
						</div>
				</div>
			</div>
			<input id="posts" name="posts" type="hidden"/>
			<div id="postInfo" class="tab-pane clearfloat">
				<div class="post-manage">
					<div class="post-ser">
						<!-- <input id="postName" name="postName" placeholder="岗位名称" class="post-serR" />
						<button id="queryPost" class="lab-post" type="button">查询</button> -->
						<label class="control-label col-md-4" style="margin-top:-6px;">
						    <i class="payment-cycle-sp">*</i>部门： 
					    </label>
						<div class="input-group col-sm-7">
						  <input id="orgId2" name="orgId2" type="hidden" class="form-control">
						  <input class="form-control" id="orgName2" name="orgName2" readonly="readonly" placeholder="从部门树选择部门" style="margin-top:9px; border-radius: 2px;"/>
						  <div class="input-group-btn">
							<button type="button" class="btn btn-white dropdown-toggle" data-toggle="dropdown"><span class="caret"></span></button>
							<div class="dropdown-menu pull-right">
						    <ul id="tree2" class="ztree no-border" style="padding-left:0"></ul>
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
	<div class="form-group col-sm-12 text-center sys-btn-style">
		<button data-dismiss="modal" class="btn btn-default" onclick="javascript:window.location='index#companyUser'" type="button">取消</button>
		<gd:btn btncode="BtnSysUserAddSave"><button class="btn btn-danger ml15" id="saveForm" type="submit" data-dismiss="modal" >保存</button></gd:btn>
	</div>
	</form>
</div>