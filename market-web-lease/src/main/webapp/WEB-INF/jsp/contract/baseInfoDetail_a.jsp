<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/pub/tags.inc" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<style>
	.contract .col-info-lease-l li{
		overflow:hidden;text-overflow:ellipsis;white-space:nowrap;
	}
</style>
<!-- 合同管理详情 -->
<input type="hidden" value="${contractDTO.mainDTO.contractStatus}" id="detailContractStatus">
<input type="hidden" value="${contractDTO.mainDTO.approvalStatus}" id="detailApprovalStatus">
<input type="hidden" value="${contractDTO.updateButtonPower}" id="detailUpdateButtonPower">
<input type="hidden" value="${contractDTO.settlementButtonPower}" id="detailSettlementButtonPower">
	<div class="col-md-12 col-pd-13">
		<div class="contract-info">
			<div class="contract-title-block">
				<span class="contract-info-h3">合同基本信息</span>
			</div>
			<input type="hidden" id="contractId" name="contractId" value="${contractDTO.mainDTO.id }">
			<div class="col-md-6 col-info-left">
				<ul class="col-info-lease-l">
				    <li><span class="list-sp">租赁资源：</span>
						<span class="fees-trator-tx" title="${contractDTO.mainDTO.leasingResource}">${contractDTO.mainDTO.leasingResource}</span></li>
					<li><span class="list-sp">客户名称：</span>
						<span class="fees-trator-tx">${contractDTO.mainDTO.customerName}</span></li>
					<li><span class="list-sp">甲方：</span>
						<span class="fees-trator-tx">${contractDTO.mainDTO.partyA}</span></li>
					<li><span class="list-sp">起租日期：</span>
					    <span class="fees-trator-tx">
				    <fmt:formatDate value="${contractDTO.mainDTO.startLeasingDay}" pattern="yyyy-MM-dd"/>					  					    

					    </span>
					</li>
					<li><span class="list-sp">延迟交租罚金：</span>
						<span class="fees-trator-tx"><fmt:formatNumber type="number" value="${contractDTO.mainDTO.leasingForfeit}" pattern="#,##0.00" maxFractionDigits="2"/>元/日</span></li>
					<li><span class="list-sp">商铺名称：</span> <span
						class="fees-trator-tx">${contractDTO.mainDTO.shopName}</span></li>
					<li><span class="list-sp">手机号码：</span> <span
						class="fees-trator-tx">${contractDTO.mainDTO.customerMobile}</span></li>
					<li><span class="list-sp">建筑面积：</span> <span
						class="fees-trator-tx"><fmt:formatNumber type="number" value="${contractDTO.mainDTO.floorArea}" pattern="#,##0.00" maxFractionDigits="2"/>㎡</span></li>
					<li><span class="list-sp">经办人：</span> <span
						class="fees-trator-tx">${contractDTO.mainDTO.trustees}</span></li>
				</ul>
			</div>
			<div class="col-md-6 col-info-right">
				<ul class="col-info-lease-r">
				    <li></li>
					<li><span class="list-sp">合同编号：</span>
						<span class="fees-trator-tx">${contractDTO.mainDTO.contractNo}</span></li>
					<li><span class="list-sp">乙方：</span>
						<span class="fees-trator-tx">${contractDTO.mainDTO.partyB}</span></li>
					<li><span class="list-sp">结束日期：</span>
					<span class="fees-trator-tx">

					    <fmt:formatDate value="${contractDTO.mainDTO.endLeasingDay}" pattern="yyyy-MM-dd"/>					  					    					    						

					</span>					
					</li>
					<li><span class="list-sp">延迟还铺罚金：</span>
						<span class="fees-trator-tx"><fmt:formatNumber type="number" value="${contractDTO.mainDTO.shopForfeit}" pattern="#,##0.00" maxFractionDigits="2"/>元/日</span></li>
					<li><span class="list-sp">经营范围：</span><span
						class="fees-trator-tx">${contractDTO.mainDTO.categoryName}</span></li>
					<li><span class="list-sp">可租面积：</span> <span
						class="fees-trator-tx"><fmt:formatNumber type="number" value="${contractDTO.mainDTO.freeArea}" pattern="#,##0.00" maxFractionDigits="2"/>㎡</span></li>
					<li><span class="list-sp">套内面积：</span> <span
						class="fees-trator-tx"><fmt:formatNumber type="number" value="${contractDTO.mainDTO.innerArea}" pattern="#,##0.00" maxFractionDigits="2"/>㎡</span></li>
					<li><span class="list-sp">签约时间：</span>
						 <span class="fees-trator-tx">
					           <fmt:formatDate value="${contractDTO.mainDTO.dateOfContract}" pattern="yyyy-MM-dd"/>					  					    					    											   					
						 </span>
					 </li>
					</li>
				</ul>
			</div>
		</div>
	</div>

	<div class="col-md-12 col-pd-13">
		<div class="contract-info">
			<div class="contract-title-block">
				<span class="contract-info-h3">&nbsp;&nbsp;&nbsp;&nbsp;租金约定&nbsp;&nbsp;&nbsp;&nbsp;</span>
			</div>
			<!--按周期收费-->			
            <c:if test="${contractDTO.mainDTO.chargingWays==0}">
	            <div class="charges-paid">
					<span><b class="col-Fees-bold">收费方式：<c:if test="${contractDTO.mainDTO.chargingWays==0}">按周期收费</c:if><c:if test="${contractDTO.mainDTO.chargingWays==1}">约定总金额</c:if></b></span>
				</div>
				<div class="charges-paid">
					<span><b class="col-Fees-bold">计费面积：
					  <c:if test="${contractDTO.mainDTO.billingArea==0}">可租面积</c:if>
					  <c:if test="${contractDTO.mainDTO.billingArea==1}">建筑面积</c:if>
					  <c:if test="${contractDTO.mainDTO.billingArea==2}">套内面积</c:if>
					</b></span>
				</div>
				<div class="charges-max-box">
					<div class="charges-paid-bt">
						<span class="charges-paid-sz">租赁约定</span>
					</div>
					<table class="charges-tab-style" cellspacing="0">
						<tbody class="tb-trs">
							<tr class="charges-tab-bg">
								<td class="charges-tab-bor">序号</td>
								<td>起始日期</td>
								<td>截止日期</td>
								<td>计费单位</td>
								<td>租金单价</td>
								<td>出租面积</td>
								<td>租金</td>
							</tr>
							 <c:forEach items="${contractDTO.leaseList}" varStatus="i" var="lease" >
							   <tr class="charges-tab-blank">
									<td id="eq-add">${i.index+1}</td>
									<td><fmt:formatDate value="${lease.startDay}" pattern="yyyy-MM-dd"/></td>
									<td><fmt:formatDate value="${lease.endDay}" pattern="yyyy-MM-dd"/></td>
									<td>
										<c:if test="${lease.billingUnit==0}">元/月/平</c:if>
										<c:if test="${lease.billingUnit==1}">元/季/平</c:if>
										<c:if test="${lease.billingUnit==2}">元/半年/平</c:if>
										<c:if test="${lease.billingUnit==3}">元/年/平</c:if>
									</td>
									<td><fmt:formatNumber type="number" value="${lease.unitPrice}" pattern="#,##0.00" maxFractionDigits="2"/></td>
									<td><fmt:formatNumber type="number" value="${lease.billingArea}" pattern="#,##0.00" maxFractionDigits="2"/></td>
									<td><fmt:formatNumber type="number" value="${lease.rental}" pattern="#,##0.00" maxFractionDigits="2"/></td>
							   </tr>
	                         </c:forEach>
						</tbody>
					</table>
				</div>
				<div class="charges-max-box">
					<div class="charges-paid-bt">
						<span class="charges-paid-sz">免租约定</span>
					</div>
					<table class="charges-tab-style" cellspacing="0">
						<tbody class="leaseDs">
							<tr class="charges-tab-bg">
								<td class="charges-tab-bor">序号</td>
								<td>起始日期</td>
								<td>免租月数</td>
								<td>免租天数</td>
								<td>截止日期</td>
							</tr>
							<c:forEach items="${contractDTO.freeList}" varStatus="i" var="free" >
								<tr class="charges-tab-blank">
									<td>${i.index+1}</td>
									<td><fmt:formatDate value="${free.startDay}" pattern="yyyy-MM-dd"/></td>
									<td>${free.freeMonths}</td>
									<td>${free.freeDays}</td>
									<td><fmt:formatDate value="${free.endDay}" pattern="yyyy-MM-dd"/></td>
								</tr>
							</c:forEach>
						</tbody>
					</table>
				</div>
				<div class="charges-max-box">
					<div class="charges-paid-btm">
						<span class="charges-paid-sz">缴费约定</span>
					</div>
					<div class="payment-cycle">
						<span>
						<b class="col-ft-bold">缴费周期：
						     <c:if test="${contractDTO.mainDTO.paymentCycle==0}">月</c:if>
						     <c:if test="${contractDTO.mainDTO.paymentCycle==1}">季度</c:if>
						     <c:if test="${contractDTO.mainDTO.paymentCycle==2}">半年</c:if>
						     <c:if test="${contractDTO.mainDTO.paymentCycle==3}">年</c:if>
						</b>
						</span>
					</div>
					<div class="payment-cycle">
						<span>
						<b class="col-ft-bold">缴费日期：
						     <c:if test="${contractDTO.mainDTO.paymentDayType==0}">缴费期初${contractDTO.mainDTO.paymentDays}天</c:if>
						     <c:if test="${contractDTO.mainDTO.paymentDayType==1}">缴费期末${contractDTO.mainDTO.paymentDays}天</c:if>
						     <c:if test="${contractDTO.mainDTO.paymentDayType==2}">下一个缴费期初${contractDTO.mainDTO.paymentDays}天</c:if>
						</b>
						</span>
					</div>
				</div>
            </c:if>
            
             <!--按约定总金额收费-->
            <c:if test="${contractDTO.mainDTO.chargingWays==1}">              
                                 <div class="contract-rents">        
	                <div class="amount-ipt2">
	                	<p class="amount-ret"><em class="payment-cycle-sp">*</em>合同总金额：&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<span class="sp-lt-amount">  <fmt:formatNumber type="number" value="${contractDTO.mainDTO.totalAmt}" pattern="#,##0.00" maxFractionDigits="2"/>元</span></p>
	                </div>
                 	<div class="total_amount" style="padding-bottom:30px;">
                 	     <div class="rents_agreement clearfloat">
                 	     	<p class="rents_agreement_p" style="padding:10px 0 10px 63px;">缴费约定</p>
                 	     </div>
                 		<table id="econManageDT" class="charges-tab-style" cellspacing="0">
		         	 		 <tr class="charges-tab-bg">
							    <td class="charges-tab-bor">序号</td>
							    <td>缴费期限</td>
							    <td>缴费金额</td>
							  </tr>
							  <c:forEach items="${contractDTO.paymentList}" varStatus="i" var="payment" >
							  <tr class="charges-tab-blank">
							    <td class="num">${i.index+1}</td>
							    <td>
							      	<fmt:formatDate value="${payment.paymentTime}" pattern="yyyy-MM-dd"/>					  					    							      
							    </td>
							    <td>
							        <fmt:formatNumber type="number" value="${payment.paymentAmt}" pattern="#,##0.00" maxFractionDigits="2"/>
							    </td>
							  </tr>
							  </c:forEach>							  
	                 	 	</table>
                 	</div>                 	
                 </div>
            </c:if>
		</div>
	</div>
	<div class="col-md-12 col-pd-13">
		<div class="contract-info">
			<div class="contract-title-block">
				<span class="contract-info-h3">其他费项约定</span>
			</div>
			<div class="charges-max-box charges-max-other">
				<table class="charges-tab-style" cellspacing="0">
					<tr class="charges-tab-bg">
						<td class="charges-tab-bor">序号</td>
						<td style="width:250px;">租赁资源</td>
						<td>费项类型</td>
						<td>费项名称</td>
						<td>计费标准</td>
						<td>计算方法</td>
						<td>金额</td>
					</tr>
				    <c:forEach items="${contractDTO.othersFeeList}" varStatus="i" var="othersFee" >					
					<tr class="charges-tab-blank">
						<td>${i.index+1}</td>
						<td title="${othersFee.leasingResource}" style="width:250px; display: block; line-height:38px; overflow: hidden; white-space:nowrap; text-overflow:ellipsis;">${othersFee.leasingResource}</td>
						<td>${othersFee.itemCategory}</td>
						<td>${othersFee.itemName}</td>
						<td>${othersFee.freightBasis}</td>
						<c:if test="${othersFee.itemCategoryId eq 2 }">
							<td></td>
							<td></td>
						</c:if>
						<c:if test="${othersFee.itemCategoryId ne 2 }">
							<td>${othersFee.rentModeName}</td>
							<td><fmt:formatNumber type="number" value="${othersFee.total}" pattern="#,##0.00" maxFractionDigits="2"/></td>
						</c:if>
					</tr>	
					</c:forEach>					
				</table>
			</div>

		</div>
	</div>
	<div class="col-md-12 col-pd-13">
		<div class="contract-info">
			<div class="contract-title-block">
				<span class="contract-info-h3">附加条款&附件</span>
			</div>


			<div class="change-details">
				<div class="terms-box">
					<p class="additional-terms">附加条款 :${contractDTO.mainDTO.additionalTerms}</p>
				</div>
				<div class="terms-file">
					<p class="additional-terms">
						合同附件 :&nbsp;&nbsp;
					    <c:forEach items="${contractDTO.accessoriesList}" varStatus="i" var="accessorie" >					
						<a href="${contractDTO.fileCompleteUrl}${accessorie.fileUrl}">${accessorie.fileName}</a>&nbsp;&nbsp;
						</c:forEach>
					</p>					
				</div>
			</div>

		</div>

