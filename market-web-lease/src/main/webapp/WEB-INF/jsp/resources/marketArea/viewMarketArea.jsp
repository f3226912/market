<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<section class="header">区域详情</section>
<style>
	.eNinput{
		position: relative;
	}
	.eNinput label.error{
		position: absolute;
    	top: 0;
    	right: 10%;
		margin:0;
		line-height:30px;
	}
</style>
<div class="form-page">
	<form id="updateForm" action="marketArea/update" novalidate class="form-horizontal clearfix">
		<input  id="id"  name="id" type="hidden" class="form-control">
		<div class="form-group col-lg-5">
			<label class="control-label col-md-4">
					<i class="payment-cycle-sp">*</i>区域编码： 
				</label>
			<div class="col-md-6 text-left eNinput">
				<input id="code" name="code" type="text" class="form-control"  required>
			</div>
		</div>
		<div class="form-group col-lg-5">
			<label class="control-label col-md-4">
					<i class="payment-cycle-sp">*</i>区域名称： 
				</label>
			<div class="col-md-6 text-left eNinput">
				<input id="name" name="name" type="text" class="form-control"  required>
			</div>
		</div>
		<div class="form-group col-lg-5">
			<label class="control-label col-md-4">
					<i class="payment-cycle-sp">*</i>优先级序号： 
				</label>
			<div class="col-md-6 text-left eNinput">
				<input  id="sort"  name="sort" type="text" class="form-control"  required>
			</div>
		</div>
		
		<div class="form-group col-lg-10 text-center mtop30">
			<button data-dismiss="modal" id="cancle" class="btn btn-default" type="button">取消</button>
			<gd:btn btncode="BtnResourcesAreaEditSave">
				<button class="btn btn-danger ml15" id="saveForm" type="submit" data-dismiss="modal">保存</button>
			</gd:btn>
		</div>
	</form>
</div>
