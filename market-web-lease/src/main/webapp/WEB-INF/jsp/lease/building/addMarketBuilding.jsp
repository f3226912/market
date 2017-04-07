<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="gd" uri="http://www.gdeng.cn/gd"%>
<%@ page isELIgnored="true"%>
<section class="header">新增楼栋</section>
<div class="form-page validate-error">
	<form action="#" id="saveFrom" class="form-horizontal clearfix">
		<div class="row">
		<div class="resour-group col-lg-5">
			<label class="control-label col-md-4">
					<i class="payment-cycle-sp">*</i>楼栋编码： 
				</label>
			<div class="col-md-8 text-left">
				<input type="text"  name="code" id="code" class="form-control">
			</div>
		</div>
		<div class="resour-group col-lg-5">
			<label class="control-label col-md-4">
					<i class="payment-cycle-sp">*</i>楼栋区域：
				</label>
			<div class="col-md-8 text-left">
				<select  name="areaId" id="areaId" class="form-control">
				<option  value="">请选择</option>
				</select>
			</div>
		</div>
		</div>
		<div class="row">
		<div class="resour-group col-lg-5">
			<label class="control-label col-md-4">
					<i class="payment-cycle-sp">*</i>楼栋名称： 
				</label>
			<div class="col-md-8 text-left">
				<input type="text" name="name" id="name" class="form-control">
			</div>
		</div>
		<div class="resour-group col-lg-5">
			<label class="control-label col-md-4">
				<i class="payment-cycle-sp"></i>楼栋性质：
				</label>
			<div class="col-md-8 text-left">
				<select  name="nature" id="nature" class="form-control">
				<option value="">请选择</option>
				</select>
			</div>
		</div>
		</div>
		<div class="form-group col-lg-12 text-center mtop30">
			<button data-dismiss="modal" class="btn btn-default" type="button" id="cancel">取消</button>
			<gd:btn btncode="BtnBuilAdd"><button class="btn btn-danger ml15" id="save_btn" type="submit"  data-dismiss="modal">确认</button></gd:btn>
		</div>
	</form>
</div>