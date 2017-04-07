<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!-- 合同变更详情 -->
    <%@ include file="baseInfo.jsp"%>
	<%-- <input value="${mainDTO.changeDto.id }" id="changeDtoNewId" type="text"/> --%>
	<div class="col-md-12 col-pd-13">
		<div class="contract-info">
			<div class="contract-title-block">
				<span class="contract-info-h3">&nbsp;&nbsp;&nbsp;&nbsp;租金约定&nbsp;&nbsp;&nbsp;&nbsp;</span>
			</div>
			<c:if test="${mainDTO.chargingWays==0}">
                 <%@ include file="rent_CycleDetail.jsp"%>
            </c:if>
            
            <c:if test="${mainDTO.chargingWays==1}">
                 <%@ include file="rent_TotalAmountDetail.jsp"%>
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
					<c:forEach items="${othersFeeList}" varStatus="i" var="othersFee" >					
					<tr class="charges-tab-blank">
						<td>${i.index+1}</td>
						<td title="${mainDTO.leasingResource}" style="width:250px; display: block; line-height:38px; overflow: hidden; white-space:nowrap; text-overflow:ellipsis;">${mainDTO.leasingResource}</td>
						<td>${othersFee.itemCategory}</td>
						<td>${othersFee.itemName}</td>
						<td>${othersFee.freightBasis}</td>
						<c:if test="${othersFee.itemCategoryId eq 2 }">
							<td></td>
							<td></td>
						</c:if>
						<c:if test="${othersFee.itemCategoryId ne 2 }">
							<td>${othersFee.rentModeName}</td>
							<td> <fmt:formatNumber type="number" value="${othersFee.total}" pattern="#,##0.00" maxFractionDigits="2"/></td>
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
				<span class="contract-info-h3">&nbsp;&nbsp;&nbsp;&nbsp;变更信息&nbsp;&nbsp;&nbsp;&nbsp;</span>
			</div>
			<div class="change-details">
				<input type="hidden" name="contractChangeId" id="contractChangeId" value="${mainDTO.id }">
				<div class="terms-box">
					<p class="additional-terms">变更原因:
					<c:if test="${mainDTO.changeReasion eq 0 }">
							租金水平调整
					</c:if>
					<c:if test="${mainDTO.changeReasion eq 1 }">
							费项增减
					</c:if>
					<c:if test="${mainDTO.changeReasion eq 2 }">
							租金减免
					</c:if>
					</p>
				</div>
				<div class="terms-file">
					<p class="additional-terms">经办人：${mainDTO.trustees}</p>					
				</div>
				<div class="terms-file">
					<p class="additional-terms">经办时间：  <fmt:formatDate value="${mainDTO.createTime}" pattern="yyyy-MM-dd"/>				</p>					
				</div>
				<div class="terms-file">
					<p class="additional-terms">变更说明：${mainDTO.info}</p>					
				</div>
			</div>

		</div>
	</div>