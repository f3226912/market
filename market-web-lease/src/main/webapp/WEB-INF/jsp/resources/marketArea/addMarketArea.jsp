<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page isELIgnored="true"%>
<%@ include file="/WEB-INF/jsp/pub/tags.inc" %>
<section class="header">新增区域</section>
<div class="form-page add-area-cont">
	<form id="form-addMarketArea" action="#" class="form-horizontal clearfix">			
		<div class="row rowAddMark">
		<input id="rowIndex"  name="rowIndex" type="hidden" value="0">
		<div class="form-group col-sm-4">
			<label class="control-label col-sm-5 text-right">
					<i class="payment-cycle-sp">*</i>区域编码： 
				</label>
			<div class="col-sm-7 text-left">
				<input id="code-0" placeholder="1-50个字符" maxlength="50"  name="marketAreas[0].code" type="text" class="form-control code">
				<label class="error" ></label>
			</div>
		</div>
		<div class="form-group col-sm-4">
			<label class="control-label col-sm-5 text-right">
					<i class="payment-cycle-sp">*</i>区域名称： 
				</label>
			<div class="col-sm-7 text-left">
				<input  id="name-0" placeholder="1-50个字符" maxlength="50"  name="marketAreas[0].name" type="text" class="form-control name">
				<label class="error" ></label>
			</div>
		</div>
		<div class="form-group col-sm-4">
			<label class="control-label col-sm-5 text-right">
					<i class="payment-cycle-sp">*</i>优先级序号： 
				</label>
			<div class="col-sm-7 text-left">
				<input id="sort-0"  name="marketAreas[0].sort" type="text" class="form-control sort">
				<label class="error" ></label>
			</div>
		</div>
		<a id="addAreaBtn" class="add-area-btn addAreaBtn"><i class="fa fa-list-alt"></i>新增区域</a>
		</div>		
		<div class="form-group col-lg-12 text-center mtop30">
			<button data-dismiss="modal" id="cancle" class="btn btn-default" type="button">取消</button>
			<gd:btn btncode="BtnResourcesAreaAddSave">
				<button class="btn btn-danger ml15" id="saveForm" type="button" data-dismiss="modal">保存</button>
			</gd:btn>
		</div>
</div>

<div class="addAreaForm" style="display: none;">
	<div class="row rowAddMark">
		<div class="form-group col-md-4">
			<label class="control-label col-sm-5 text-right">
					<i class="payment-cycle-sp">*</i>区域编码： 
				</label>
			<div class="col-sm-7 text-left">
				<input  name="marketAreas[0].code" placeholder="1-50个字符" maxlength="50" type="text" class="form-control code">
				<label class="error" ></label>
			</div>
		</div>
		<div class="form-group col-md-4">
			<label class="control-label col-sm-5 text-right">
					<i class="payment-cycle-sp">*</i>区域名称： 
				</label>
			<div class="col-sm-7 text-left">
				<input name="marketAreas[0].name" placeholder="1-50个字符" maxlength="50" type="text" class="form-control name" >
				<label class="error" ></label>
			</div>
		</div>
		<div class="form-group col-md-4">
			<label class="control-label col-sm-5 text-right">
					<i class="payment-cycle-sp">*</i>优先级序号： 
				</label>
			<div class="col-sm-7 text-left">
				<input  name="marketAreas[0].sort" type="text" class="form-control sort" >
				<label class="error" ></label>
			</div>
		</div>
		<a id="addAreaBtn" class="add-area-btn addAreaBtn"><i class="fa fa-list-alt"></i>新增区域</a>
		
		</div>
		</form>
		
</div>