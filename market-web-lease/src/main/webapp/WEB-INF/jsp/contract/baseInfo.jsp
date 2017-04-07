<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/pub/tags.inc" %>
<!-- 合同基础信息 -->
	<div class="col-md-12 col-pd-13">
		<div class="contract-info">
			<div class="contract-title-block">
				<span class="contract-info-h3">合同基本信息</span>
			</div>
			<input type="hidden" value="${mainDTO.approvalStatus}" id="changeApprovalStatus">
			<input type="hidden" id="contractId" name="contractId" value="${mainDTO.id }">
			<div class="col-md-6 col-info-left">
				<ul class="col-info-lease-l">
				    <li><span class="list-sp">租赁资源：</span>
						<span id="resource_text" class="fees-trator-tx" title="${mainDTO.leasingResource}">${mainDTO.leasingResource}</span></li>
					<li><span class="list-sp">客户名称：</span>
						<span class="fees-trator-tx">${mainDTO.customerName}</span></li>
					<li><span class="list-sp">甲方：</span>
						<span class="fees-trator-tx">${mainDTO.partyA}</span></li>
					<li><span class="list-sp">起租日期：</span>
					    <span class="fees-trator-tx">
					       <fmt:formatDate value="${mainDTO.startLeasingDay}" pattern="yyyy-MM-dd"/>					  					    
					    </span>
					</li>
					<li><span class="list-sp">延迟交租罚金：</span>
						<span class="fees-trator-tx"><fmt:formatNumber type="number" value="${mainDTO.leasingForfeit}" pattern="#,##0.00" maxFractionDigits="2"/>元/日</span></li>					
				</ul>
			</div>
			<div class="col-md-6 col-info-right">
				<ul class="col-info-lease-r">
				    <li><span class="list-sp">收费方式：</span>
						<span class="fees-trator-tx">${mainDTO.chargingWays==0?'按周期收费':'约定总金额'}</span></li></li>
					<li><span class="list-sp">合同编号：</span>
						<span class="fees-trator-tx">${mainDTO.contractNo}</span></li>
					<li><span class="list-sp">乙方：</span>
						<span class="fees-trator-tx">${mainDTO.partyB}</span></li>
					<li><span class="list-sp">结束日期：</span>
					<span class="fees-trator-tx">
					    <fmt:formatDate value="${mainDTO.endLeasingDay}" pattern="yyyy-MM-dd"/>					  					    					    						
					</span>					
					</li>
					<li><span class="list-sp">延迟还铺罚金：</span>
						<span class="fees-trator-tx"><fmt:formatNumber type="number" value="${mainDTO.shopForfeit}" pattern="#,##0.00" maxFractionDigits="2"/>元/日</span>				
					</li>
				</ul>
			</div>
		</div>
</div>