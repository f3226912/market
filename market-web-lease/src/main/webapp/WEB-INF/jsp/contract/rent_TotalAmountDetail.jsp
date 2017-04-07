<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
 <!--按约定总金额收费-->
                 <div class="contract-rents">             
	                <div class="amount-ipt2">
	                	<p class="amount-ret"><em class="payment-cycle-sp">*</em>合同总金额：&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<span class="sp-lt-amount">  <fmt:formatNumber type="number" value="${mainDTO.totalAmt}" pattern="#,##0.00" maxFractionDigits="2"/>元</span></p>
	                </div>
                 	<div class="total_amount" style="padding-bottom:30px;">
                 	     <div class="rents_agreement clearfloat">
                 	     	<p class="rents_agreement_p" style="padding:10px 0 10px 63px">缴费约定</p>
                 	     </div>
                 		<table id="econManageDT" class="charges-tab-style" cellspacing="0">
		         	 		 <tr class="charges-tab-bg">
							    <td class="charges-tab-bor">序号</td>
							    <td>缴费期限</td>
							    <td>缴费金额</td>
							  </tr>
							  <c:forEach items="${paymentList}" varStatus="i" var="payment" >
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
<!--按约定总金额收费结束-->
