<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="gd" uri="http://www.gdeng.cn/gd"%>
<section class="header">套打设置</section>
<div class="form-page" style="padding: 10px">
	<form id="form-editPrintSetting" action="printSetting/saveAdd" class="form-horizontal clearfix cmxform">
		<input type="hidden" id="id" name="id" value="${dto.id }">
		<div class="form-group col-lg-12">
			<label class="control-label col-md-4">
				市场名称：
			</label>
			<div class="col-md-6 text-left">
				<input id="marketId" name="marketId" type="hidden" class="form-control" value="${dto.marketId }">
				<input id="marketName" name="marketName" type="text" class="form-control" value="${dto.marketName }" readonly="readonly">
			</div>
		</div>
		
		<div class="form-group col-lg-12">
			<label class="control-label col-md-4">
				<i class="payment-cycle-sp">*</i>套打名称： 
			</label>
			<div class="col-md-6 text-left">
				<input id="printName" name="printName" type="text" class="form-control" value="${dto.printName }">
			</div>
		</div>
		
		<div class="form-group col-lg-12">
			<label class="control-label col-md-4">
				<i class="payment-cycle-sp">*</i>应用业务： 
			</label>
			<div class="col-md-6 text-left">
				<select class="form-control" id="bizType" name="bizType">
					<option value="">请选择应用业务</option>
					<option value="0" ${dto.bizType == 0 ? "selected" : "" }>合同管理</option>
					<option value="1" ${dto.bizType == 1 ? "selected" : "" }>合同变更</option>
					<option value="2" ${dto.bizType == 2 ? "selected" : "" }>合同结算</option>
					<option value="3" ${dto.bizType == 3 ? "selected" : "" }>合同付款</option>
				</select>
			</div>
		</div>
		
		<div class="form-group col-lg-12">
			<label class="control-label col-md-4">
				<i class="payment-cycle-sp">*</i>套打模板： 
			</label>
			<div class="col-md-6 text-left">
				<select class="form-control" id="templateId" name="templateId">
					<option value="">请选择套打模板</option>
					<c:forEach items="${templateList }" var="template">
						<option value="${template.id }" ${dto.templateId == template.id ? "selected" : "" }>${template.templateName }</option>
					</c:forEach>
				</select>
			</div>
		</div>
		
		<div class="form-group col-lg-10 text-center mtop30">
			<button data-dismiss="modal" class="btn btn-default" onclick="javascript:(function(){Route.params={active:1};location.href='index#print';})()" type="button">取消</button>
			<gd:btn btncode="BtnPrintSettingEdit"><button class="btn btn-danger ml15" id="saveForm" type="submit" data-dismiss="modal">确认</button></gd:btn>
		</div>
	</form>
</div>