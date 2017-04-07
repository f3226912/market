<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="gd" uri="http://www.gdeng.cn/gd"%>
<section class="header">楼栋详情</section>
<div class="form-page">
	<form id="form-addBuild" class="form-horizontal clearfix">
		<div class="col-lg-5 resour-group">
			<label class="control-label col-md-4">
					<i class="payment-cycle-sp">*</i>楼栋编码： 
				</label>
			<div class="col-md-8 text-left">
				<input name="code" id="code" type="text" class="form-control">
			</div>
		</div>
		<div class="col-lg-5 resour-group">
			<label class="control-label col-md-4">
					<i class="payment-cycle-sp">*</i>楼栋名称：
				</label>
			<div class="col-md-8 text-left">
				<input name="name" id="buildName" type="text" class="form-control">
			</div>
		</div>
		<div class="col-lg-5 resour-group">
			<!-- <label class="control-label col-md-4">
					<i class="payment-cycle-sp">*</i>楼栋区域： 
				</label>
			<div class="col-md-8 text-left">
				<input name="code" id="areaName" type="text" class="form-control">
			</div> -->
			<label class="control-label col-md-4">
					<i class="payment-cycle-sp">*</i>楼栋区域：
				</label>
			<div class="col-md-8 text-left">
				<select  name="areaId" id="areaId" class="form-control">
				<option value="">请选择</option>
				</select>
			</div>
		</div>
		<div class="col-lg-5 resour-group">
		<!-- 	<label class="control-label col-md-4">
					<i class="payment-cycle-sp"></i>楼栋性质： 
				</label>
			<div class="col-md-8 text-left">
				<input name="code" id="nature" type="text" class="form-control">
			</div> -->
				<label class="control-label col-md-4">
				<i class="payment-cycle-sp"></i>楼栋性质：
				</label>
			<div class="col-md-8 text-left">
				<select  name="nature" id="nature" class="form-control">
					<option value="">请选择</option>
				</select>
			</div>
		</div>
		<div class="form-group col-lg-12 text-center mtop30">
		<button data-dismiss="modal" class="btn btn-default" type="button" id="cancel">取消</button>
			  <gd:btn btncode="BtnBuildEdit"> 
			<button class="btn btn-danger ml15" id="saveForm" type="submit" data-dismiss="modal">确认</button>
		 </gd:btn>  
		</div>
	</form>
</div>
