<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!-- 按周期收费 -->
		  <div class="charges-paid">
					<span>
					<b class="col-Fees-bold">收费方式：
					   <c:if test="${mainDTO.chargingWays==0}">按周期收费</c:if>
					   <c:if test="${mainDTO.chargingWays==1}">约定总金额</c:if>					
					</b>
					</span>
				</div>
				<div class="charges-paid">
					<span><b class="col-Fees-bold">计费面积：
					  <c:if test="${mainDTO.billingArea==0}">可租面积</c:if>
					  <c:if test="${mainDTO.billingArea==1}">建筑面积</c:if>
					  <c:if test="${mainDTO.billingArea==2}">套内面积</c:if>
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
							 <c:forEach items="${leaseList}" varStatus="i" var="lease" >
							   <tr class="charges-tab-blank">
									<td id="eq-add">${i.index+1}</td>
									<td> <fmt:formatDate value="${lease.startDay}" pattern="yyyy-MM-dd"/>	</td>
									<td><fmt:formatDate value="${lease.endDay}" pattern="yyyy-MM-dd"/>	</td>
									<td>
									    <c:if test="${lease.billingUnit==0}">元/月/平</c:if>
										<c:if test="${lease.billingUnit==1}">元/季/平</c:if>
										<c:if test="${lease.billingUnit==2}">元/半年/平</c:if>
										<c:if test="${lease.billingUnit==3}">元/年/平</c:if>
									</td>
									<td><fmt:formatNumber type="number" value="${lease.unitPrice}" pattern="#,#00.00#" maxFractionDigits="2"/></td>
									<td><fmt:formatNumber type="number" value="${lease.billingArea}" pattern="#,#00.00#" maxFractionDigits="2"/></td>
									<td><fmt:formatNumber type="number" value="${lease.rental}" pattern="#,#00.00#" maxFractionDigits="2"/></td>
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
							<c:forEach items="${freeList}" varStatus="i" var="free" >
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
						     <c:if test="${mainDTO.paymentCycle==0}">月</c:if>
						     <c:if test="${mainDTO.paymentCycle==1}">季度</c:if>
						     <c:if test="${mainDTO.paymentCycle==2}">半年</c:if>
						     <c:if test="${mainDTO.paymentCycle==3}">年</c:if>
						</b>
						</span>
					</div>
					<div class="payment-cycle">
						<span>
						<b class="col-ft-bold">缴费日期：
						     <c:if test="${mainDTO.paymentDayType==0}">缴费期初${mainDTO.paymentDays}天</c:if>
						     <c:if test="${mainDTO.paymentDayType==1}">缴费期末${mainDTO.paymentDays}天</c:if>
						     <c:if test="${mainDTO.paymentDayType==2}">下一个缴费期初${mainDTO.paymentDays}天</c:if>
						</b>
						</span>
					</div>
				</div>