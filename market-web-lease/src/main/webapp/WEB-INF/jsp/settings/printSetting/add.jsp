<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<section class="header">新增套打设置</section>
<div class="form-page" style="padding: 10px">
	<form id="form-addPrintSetting" action="printSetting/saveAdd1" class="form-horizontal clearfix cmxform">
		<div class="form-group col-lg-12">
			<label class="control-label col-md-4">
				市场名称：
			</label>
			<div class="col-md-6 text-left">
				<input id="marketId" name="marketId" type="hidden" class="form-control" value="${market.id }">
				<input id="marketName" name="marketName" type="text" class="form-control" value="${market.fullName }" readonly="readonly">
			</div>
		</div>
		
		<div class="form-group col-lg-12">
			<label class="control-label col-md-4">
				<i class="payment-cycle-sp">*</i>套打名称： 
			</label>
			<div class="col-md-6 text-left">
				<input id="printName" name="printName" type="text" class="form-control" value="" >
			</div>
		</div>
		
		<div class="form-group col-lg-12">
			<label class="control-label col-md-4">
				<i class="payment-cycle-sp">*</i>应用业务： 
			</label>
			<div class="col-md-6 text-left">
				<select class="form-control" id="bizType" name="bizType">
					<option value="0">合同管理</option>
					<option value="1">合同变更</option>
					<option value="2">合同结算</option>
					<option value="3">合同付款</option>
				</select>
			</div>
		</div>
		
		<div class="form-group col-lg-12">
			<label class="control-label col-md-4">
				<i class="payment-cycle-sp">*</i>套打模板： 
			</label>
			<div class="col-md-6 text-left">
				<select class="form-control" id="templateId" name="templateId">
					<c:forEach items="${templateList }" var="template">
						<option value="${template.id }">${template.templateName }</option>
					</c:forEach>
				</select>
			</div>
		</div>
		
		<div class="form-group col-lg-12 text-center mtop30" style="width:100%;padding:0;">
			<button data-dismiss="modal" class="btn btn-default" onclick="javascript:(function(){Route.params={active:1};location.href='index#print';})()" type="button">取消</button>
			<button class="btn btn-danger ml15" id="saveForm" type="submit" data-dismiss="modal">确认</button>
		</div>
	</form>
</div>