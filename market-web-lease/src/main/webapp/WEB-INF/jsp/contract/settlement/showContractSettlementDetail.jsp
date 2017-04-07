<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" isELIgnored="false"%>
<%@ include file="/WEB-INF/jsp/pub/tags.inc" %>

<%@ include file="../baseInfo.jsp"%>
<style>
 .amount-ret .error ,.amount-fine .error{ margin-left:10px !important;}
</style>

<c:choose>
	<c:when test="${entity.approvalStatus=='1' or entity.approvalStatus=='3' or isWfApproval != null and isWfApproval == 'true'}">
		<div class="col-md-12 col-pd-13">
	    	<div class="contract-info">
	            <div class="contract-title-block">
	              	<span class="contract-info-h3">&nbsp;&nbsp;&nbsp;&nbsp;结算信息&nbsp;&nbsp;&nbsp;&nbsp;</span>
	            </div>
	            <div class="payment-cycle">
	                <span><em class="payment-cycle-sp">*</em><b class="col-ft-bold" style="font-weight: normal;">结算类型：</b></span>
	                <c:if test="${entity.statementsType == 0 || entity.statementsType == null}">
	                	<label for="radio-1-7"><input type="radio" id="radio-1-7" name="statementsType" value="0" class="regular-rdi-cause" checked="checked" disabled="disabled"/>
						<span class="radio-mt-cause"></span><em class="radio-em">合同到期</em></label>
						<label for="radio-1-8"><input type="radio" id="radio-1-8" name="statementsType" value="1" class="regular-rdi-cause" disabled="disabled"/>
						<span class="radio-mt-cause"></span><em class="radio-em">提前解约</em></label>	
	                </c:if>
	                <c:if test="${entity.statementsType == 1}">
	                	<label for="radio-1-7"><input type="radio" id="radio-1-7" name="statementsType" value="0" class="regular-rdi-cause" disabled="disabled"/>
						<span class="radio-mt-cause"></span><em class="radio-em">合同到期</em></label>
						<label for="radio-1-8"><input type="radio" id="radio-1-8" name="statementsType" value="1" class="regular-rdi-cause" disabled="disabled" checked="checked"/>
						<span class="radio-mt-cause"></span><em class="radio-em">提前解约</em></label>	
	                </c:if>
	            </div>
	            <div class="amount-ipt2">
	              	<p class="amount-ret"><em class="payment-cycle-sp">*</em>退还保证金：&nbsp;&nbsp;<fmt:formatNumber type="number" value="${entity.deposit}" pattern="#,##0.00" maxFractionDigits="2"/><span class="sp-lt-amount" style="position:relative; left:5px">元</span></p>
	              	<p class="amount-fine" style="margin-left:90px;"><em class="payment-cycle-sp">*</em>罚款金额：&nbsp;&nbsp;<fmt:formatNumber type="number" value="${entity.forfeit}" pattern="#,##0.00" maxFractionDigits="2"/><span class="sp-lt-amount" style="position:relative; left:5px;">元</span></p>
	            </div>
	            <div class="contract-administrator"><span class="administrator-ls" style="padding-left:64px;">经办人：${entity == null? trustees : entity.trustees}</span><span class="administrator-date" style="padding-left:120px;">经办时间：&nbsp;&nbsp;<fmt:formatDate type="both" value="${entity == null? currentTime : entity.createTime}"/> </span></div>
	           	<div class="change-details" style="float:left">
	              	<span class="change-details-sp" style="position:relative; top:16px;"><i class="change-details-i">*</i>结算说明 :</span>
	            	<textarea name="info" rows="5" cols="120" class="text-area" style="margin-left:95px;" disabled="disabled">${entity.info}</textarea>   
	        	</div>
	    	</div>
	    </div>
	</c:when>
	
	<c:otherwise>
		<form id="settlementDeatil">   
		    <div class="col-md-12 col-pd-13">
		    	<input type="hidden" name="id" value="${entity.id}" id="statementsId"/>
		    	<div class="contract-info">
		            <div class="contract-title-block">
		              	<span class="contract-info-h3">&nbsp;&nbsp;&nbsp;&nbsp;结算信息&nbsp;&nbsp;&nbsp;&nbsp;</span>
		            </div>
		            <div class="payment-cycle">
		                <span><em class="payment-cycle-sp">*</em><b class="col-ft-bold" style="font-weight: normal;">结算类型：</b></span>
		                <c:if test="${entity.statementsType == 0 || entity.statementsType == null}">
			                <label for="radio-1-7w"><input type="radio" id="radio-1-7w" name="statementsType" value="0" class="regular-rdi-cause" checked="checked"/>
							<span class="radio-mt-cause"></span><em class="radio-em">合同到期</em></label>
							<label for="radio-1-8w"><input type="radio" id="radio-1-8w" name="statementsType" value="1" class="regular-rdi-cause"/>
							<span class="radio-mt-cause"></span><em class="radio-em">提前解约</em></label>	
		                </c:if>
		                <c:if test="${entity.statementsType == 1}">
		                	<label for="radio-1-7w"><input type="radio" id="radio-1-7w" name="statementsType" value="0" class="regular-rdi-cause"/>
							<span class="radio-mt-cause"></span><em class="radio-em">合同到期</em></label>
							<label for="radio-1-8w"><input type="radio" id="radio-1-8w" name="statementsType" value="1" class="regular-rdi-cause" checked="checked"/>
							<span class="radio-mt-cause"></span><em class="radio-em">提前解约</em></label>	
		                </c:if>
		            </div>
		            <div class="amount-ipt2">
		              	<p class="amount-ret" style="position:relative; top:0;"><em class="payment-cycle-sp">*</em>退还保证金：&nbsp;&nbsp;<input class="total-amount" type="text" name="deposit" value='<fmt:formatNumber type="number" value="${entity.deposit}" pattern="0.00" maxFractionDigits="2"/>' style="width:160px; padding-left:10px;"/><span class="sp-lt-amount" style="position:absolute; left:235px; top:7px;">元</span></p>
		              	<p class="amount-fine" style="position:relative; top:0;"><em class="payment-cycle-sp">*</em>罚款金额：&nbsp;&nbsp;<input class="total-amount" type="text" name="forfeit" value='<fmt:formatNumber type="number" value="${entity.forfeit}" pattern="0.00" maxFractionDigits="2"/>' style="width:160px; padding-left:10px;"/><span class="sp-lt-amount" style="position:absolute; left:222px; top:7px;">元</span></p>
		            </div>
		            <div class="contract-administrator"><span class="administrator-ls" style="padding-left:64px;">经办人：${entity == null? trustees : entity.trustees}</span><span class="administrator-date" style="padding-left: 259px;" >经办时间：&nbsp;&nbsp;<fmt:formatDate type="both" value="${entity == null? currentTime : entity.createTime}"/> </span></div>
		           	<div class="change-details" style="float:left">
		              	<span class="change-details-sp"><i class="change-details-i">*</i>结算说明 :</span>
		            	<textarea name="info" rows="5" cols="100" class="text-area" style="margin-left:19px;">${entity.info}</textarea>   
		        	</div>
		    	</div>
		    </div>  
		    <input type="hidden" name="contractId" value="${mainDTO.id}">
		</form>
	</c:otherwise>
</c:choose>

<input type="hidden" id="settlementStatus" name="settlementStatus" value="${entity.approvalStatus}">
<input type="hidden" id="statementsId" name="statementsId" value="${entity.id }">
