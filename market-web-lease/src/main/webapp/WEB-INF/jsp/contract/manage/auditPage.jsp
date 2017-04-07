<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/pub/tags.inc" %>

	<div class="form-group">
		<label class="control-label col-md-3"><i class="payment-cycle-sp">*</i>审核结果:</label>
		<div class="col-md-6 text-left">
			<label for="radio-2-7">
               <input type="radio" id="radio-2-7" name="approvalStatus"  value="0" class="regular-rdi-cause" checked="checked" />
			   <span class="radio-mt-cause"></span><em class="radio-em">通过</em></label>
			<label for="radio-2-8">
				<input type="radio" id="radio-2-8" name="approvalStatus" value="1" class="regular-rdi-cause"/>
			    <span class="radio-mt-cause"></span><em class="radio-em">驳回</em></label>
		</div>
	</div>
	<div class="form-group">
		<label class="control-label col-md-3">经办人:</label>
		<div class="col-md-6">
			<p class="form-control-static">${userName}</p>
		</div>
	</div>
	<div class="form-group">
		<label class="control-label col-md-3">审核时间:</label>
		<div class="col-md-6">
			<p class="form-control-static">${currentTime}</p>
		</div>
	</div>
	<div class="form-group">
		<label class="control-label col-md-3"><i class="payment-cycle-sp">*</i>审核意见:</label>
		<div class="col-md-6">
			<textarea rows="5" cols="50" name="info" class="text-area" required></textarea>
		</div>
	</div>
