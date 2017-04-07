<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="gd" uri="http://www.gdeng.cn/gd"%>
<section class="header">计量表收费详情</section>
<div class="form-page contract">
	<form id="form-addMeterReading" novalidate action="" class="form-horizontal clearfix cmxform">	
		 <div class="col-md-12 col-pd-13">
           <div class="contract-info">
              <div class="contract-title-block">
		           <span class="contract-info-h3">计量表收费详情</span>
		     </div>
		     <!--22222222222  -->
		     <div class="col-md-6 col-info-left">
		          <ul class="col-info-lease-l">
		              <li>
		                <span class="list-sp">计量表编号： </span>
						<span class="text-left fees-trator-tx" >${financeGaugeChargeDto.measureCode }</span>
		              </li>
		              <li>
		                <span class="list-sp">计量表分类： </span>
						<span class="text-left fees-trator-tx" >${financeGaugeChargeDto.measureTypeName}</span>
		              </li>
		              <li>
		                <span class="list-sp"><em class="inf-em">*</em>抄表日期： </span>
						<span class="text-left date-icon fees-trator-tx" >
						  <c:if test="${financeGaugeChargeDto.status eq '0' }">
							<input type="text" id="detailNoteDate" name="noteDate" class="form-control start-date wid150 fees-ipt-tx" required value="<fmt:formatDate value='${financeGaugeChargeDto.noteDate}' pattern='yyyy-MM-dd'/>" style="width: 338px !important">
							<i class="fa fa-calendar"></i>
							</c:if>
							<c:if test="${financeGaugeChargeDto.status eq '1' }">
								<fmt:formatDate value='${financeGaugeChargeDto.noteDate}' pattern='yyyy-MM-dd'/>
							</c:if>
						</span>
		              </li>
		              <li>
		                <span class="list-sp">使用期间： </span>
						<span class="text-left date-icon fees-trator-tx" >
						  <c:choose>
							<c:when test="${financeGaugeChargeDto.lastNoteDate == null}">
							  <fmt:formatDate value='${financeGaugeChargeDto.noteDate}' pattern="yyyy-MM-dd"/> ~ <fmt:formatDate value='${financeGaugeChargeDto.noteDate}' pattern="yyyy-MM-dd"/>
							</c:when>
							<c:when test="${financeGaugeChargeDto.lastNoteDate != null}">
							  <fmt:formatDate value='${financeGaugeChargeDto.lastNoteDate}' pattern="yyyy-MM-dd"/> ~ <fmt:formatDate value='${financeGaugeChargeDto.noteDate}' pattern="yyyy-MM-dd"/>
							</c:when>
						 </c:choose>
						</span>
		              </li>
		              <li>
		                <span class="list-sp">上次读数： </span>
						<span class="text-left fees-trator-tx" id="detailLast">
						    <fmt:formatNumber type="number" value="${financeGaugeChargeDto.last}" pattern="0.00" maxFractionDigits="2"/>  
						</span>
		              </li>
		              <li>
		                <span class="list-sp"><em class="inf-em">*</em>本次读数：</span>
						<span class="text-left fees-trator-tx" id="showDetailCurrent">
						    <c:if test="${financeGaugeChargeDto.status eq '0' }">
							<input id="detailCurrent" name="current" type="text" class="form-control fees-ipt-tx" value="<fmt:formatNumber type='number' value='${financeGaugeChargeDto.current}' pattern='0.00' maxFractionDigits='2'/>" required>
							</c:if>
							<c:if test="${financeGaugeChargeDto.status eq '1' }">
								<fmt:formatNumber type="number" value="${financeGaugeChargeDto.current}" pattern="0.00" maxFractionDigits="2"/>  
							</c:if>  
						</span>
		              </li>
		              <li>
		                <span class="list-sp">本次用量： </span>
						<span class="text-left fees-trator-tx" id="detailQuantity">
						    
						</span>
		              </li>
		              <li>
		                <span class="list-sp">损耗用量： </span>
						<span class="text-left fees-trator-tx">
						    <c:if test="${financeGaugeChargeDto.status eq '0' }">
							<input id="detailWastage" name="wastage" type="text" class="form-control fees-ipt-tx" value="<fmt:formatNumber type='number' value='${financeGaugeChargeDto.wastage}' pattern='0.00' maxFractionDigits='2'/> ">
							</c:if>
							<c:if test="${financeGaugeChargeDto.status eq '1' }">
								<fmt:formatNumber type="number" value="${financeGaugeChargeDto.wastage}" pattern="0.00" maxFractionDigits="2"/>  
							</c:if>
						</span>
		              </li>
		              <li>
		                <span class="list-sp"><em class="inf-em">*</em>收费金额： </span>
						<span class="text-left fees-trator-tx" id="detailAmount">
						    <fmt:formatNumber type="number" value="${financeGaugeChargeDto.amount}" pattern="0.00" maxFractionDigits="2"/>
						</span>
		              </li>
		              <li style="padding-left: 115px;">
						<span class="text-left fees-trator-tx" id="detailPriceList" style="background:#f5f5f5; display:inline-block; color: #494949; padding-left: 15px;">
						    <c:if test="${financeGaugeChargeDto.sectionalCharge eq '1' }">
								<c:if test="${fn:length(priceList)-2 ge 0}">
									<c:forEach items="${priceList}" var="price" begin="0" end="${fn:length(priceList)-2}" varStatus="status">
										${price}<br/>
									</c:forEach>
								</c:if>
							</c:if>
						</span>
		              </li>
		              
		          </ul>
		     
		     </div>
		     <div class="col-md-6 col-info-right">
				<ul class="col-info-lease-r">
				    <li>
				       <span class="list-sp">收款状态：</span>
					   <span class="fees-trator-tx">
					      <c:if test="${financeGaugeChargeDto.status eq '1' }">已收款 </c:if>
				          <c:if test="${financeGaugeChargeDto.status eq '0' }">未收款</c:if> 
					   </span>
					</li>
					<li>
				       <span class="list-sp">所在区域：</span>
					   <span class="fees-trator-tx">
					      ${financeGaugeChargeDto.areaName} 
					   </span>
					</li>
					<li>
				       <span class="list-sp">所在楼栋：</span>
					   <span class="fees-trator-tx">
					      ${financeGaugeChargeDto.buildName} 
					   </span>
					</li>
					<li>
				       <span class="list-sp">关联资源：</span>
					   <span class="fees-trator-tx">
					      ${financeGaugeChargeDto.resourceNames} 
					   </span>
					</li>
					<li>
				       <span class="list-sp">客户名称：</span>
					   <span class="fees-trator-tx">
					      ${financeGaugeChargeDto.customerName} 
					   </span>
					</li>
					<li>
				       <span class="list-sp">乙方：</span>
					   <span class="fees-trator-tx">
					      ${financeGaugeChargeDto.partyB} 
					   </span>
					</li>
					<!--抄表人：没有数据-->
					<li>
				       <span class="list-sp">抄表人：</span>
					   <span class="fees-trator-tx">
					      ${financeGaugeChargeDto.noteUser}
					   </span>
					</li>
					<li>
				       <span class="list-sp">备注：</span>
					   <span class="fees-trator-tx">
					      ${financeGaugeChargeDto.remark} 
					   </span>
					</li>
				</ul>
			</div>
			
		     <!--22222222222  -->
            <%-- <div class="form-group col-lg-5">
			<label class="control-label col-md-4">
					计量表编号： 
			</label>
			<div class="col-md-6 text-left">
				${financeGaugeChargeDto.measureCode }
			</div>
		</div> --%>
		<%-- <div class="form-group col-lg-5">
			<label class="control-label col-md-4">
					收款状态： 
				</label>
			<div class="col-md-6 text-left">
				<c:if test="${financeGaugeChargeDto.status eq '1' }">已收款 </c:if>
				<c:if test="${financeGaugeChargeDto.status eq '0' }">未收款</c:if> 
			</div>
		</div> --%>
		<%-- <div class="form-group col-lg-5">
			<label class="control-label col-md-4">
					计量表分类： 
				</label>
			<div class="col-md-6 text-left">
				${financeGaugeChargeDto.measureTypeName}
			</div>
		</div> --%>
		<%-- <div class="form-group col-lg-5">
			<label class="control-label col-md-4">
					所在区域： 
				</label>
			<div class="col-md-6 text-left">
				${financeGaugeChargeDto.areaName}
			</div>
		</div> --%>
		<%-- <div class="form-group col-lg-5">
			<label class="control-label col-md-4">
					抄表日期： 
				</label>
        		<div class="form-group date-icon border-none-right">
        			<c:if test="${financeGaugeChargeDto.status eq '0' }">
						<input type="text" id="detailNoteDate" name="noteDate" class="form-control start-date wid150" required value="<fmt:formatDate value='${financeGaugeChargeDto.noteDate}' pattern='yyyy-MM-dd'/>">
						<i class="fa fa-calendar"></i>
					</c:if>
					<c:if test="${financeGaugeChargeDto.status eq '1' }">
						<fmt:formatDate value='${financeGaugeChargeDto.noteDate}' pattern='yyyy-MM-dd'/>
					</c:if>
				</div>	     
		</div> --%>
		<%-- <div class="form-group col-lg-5">
			<label class="control-label col-md-4">
				所在楼栋： 
				</label>
			<div class="col-md-6 text-left">
				${financeGaugeChargeDto.buildName}
			</div>
		</div> --%>
		<%-- <div class="form-group col-lg-5">
			<label class="control-label col-md-4">
					使用期间： 
				</label>
			<div class="col-md-6 text-left">
            <c:choose>
				<c:when test="${financeGaugeChargeDto.lastNoteDate == null}">
					<fmt:formatDate value='${financeGaugeChargeDto.noteDate}' pattern="yyyy-MM-dd"/> ~ <fmt:formatDate value='${financeGaugeChargeDto.noteDate}' pattern="yyyy-MM-dd"/>
				</c:when>
				<c:when test="${financeGaugeChargeDto.lastNoteDate != null}">
					<fmt:formatDate value='${financeGaugeChargeDto.lastNoteDate}' pattern="yyyy-MM-dd"/> ~ <fmt:formatDate value='${financeGaugeChargeDto.noteDate}' pattern="yyyy-MM-dd"/>
				</c:when>
			</c:choose>	
			</div>
		</div> --%>
		<%-- <div class="form-group col-lg-5">
			<label class="control-label col-md-4">
					关联资源： 
				</label>
			<div class="col-md-6 text-left">
				${financeGaugeChargeDto.resourceNames}
			</div>
		</div> --%>
		<%-- <div class="form-group col-lg-5">
			<label class="control-label col-md-4">
					上次读数： 
				</label>
			<div class="col-md-6 text-left" id="detailLast">
				<fmt:formatNumber type="number" value="${financeGaugeChargeDto.last}" pattern="0.00" maxFractionDigits="2"/>  
			</div>
		</div> --%>
		<%-- <div class="form-group col-lg-5">
			<label class="control-label col-md-4">
					客户名称： 
				</label>
			<div class="col-md-6 text-left">
				${financeGaugeChargeDto.customerName}
			</div>
		</div> --%>
		<%-- <div class="form-group col-lg-5">
			<label class="control-label col-md-4">
					<i class="payment-cycle-sp">*</i>本次读数： 
				</label>
			<div class="col-md-6 text-left" id="showDetailCurrent">
				<c:if test="${financeGaugeChargeDto.status eq '0' }">
					<input id="detailCurrent" name="current" type="text" class="form-control" value="<fmt:formatNumber type='number' value='${financeGaugeChargeDto.current}' pattern='0.00' maxFractionDigits='2'/>" required>
				</c:if>
				<c:if test="${financeGaugeChargeDto.status eq '1' }">
					<fmt:formatNumber type="number" value="${financeGaugeChargeDto.current}" pattern="0.00" maxFractionDigits="2"/>  
				</c:if>
			</div>
		</div> --%>
		<%-- <div class="form-group col-lg-5">
			<label class="control-label col-md-4">
					乙方： 
				</label>
			<div class="col-md-6 text-left">
				${financeGaugeChargeDto.partyB}
			</div>
		</div> --%>
		<!-- <div class="form-group col-lg-5">
			<label class="control-label col-md-4">
					本次用量： 
				</label>
			<div class="col-md-6 text-left" id="detailQuantity">
				
			</div>
		</div> -->
		<%-- <div class="form-group col-lg-5">
			<label class="control-label col-md-4">
					损耗用量： 
				</label>
			<div class="col-md-6 text-left">
				<c:if test="${financeGaugeChargeDto.status eq '0' }">
					<input id="detailWastage" name="wastage" type="text" class="form-control" value="<fmt:formatNumber type='number' value='${financeGaugeChargeDto.wastage}' pattern='0.00' maxFractionDigits='2'/> ">
				</c:if>
				<c:if test="${financeGaugeChargeDto.status eq '1' }">
					<fmt:formatNumber type="number" value="${financeGaugeChargeDto.wastage}" pattern="0.00" maxFractionDigits="2"/>  
				</c:if>
			</div>
		</div> --%>
		<%-- <div class="form-group col-lg-5">
			<label class="control-label col-md-4">
					备注： 
				</label>
			<div class="col-md-6 text-left">
				${financeGaugeChargeDto.remark}
			</div>
		</div> --%>
		<%-- <div class="form-group col-lg-5">
			<label class="control-label col-md-4">
					收费金额： 
				</label>
			<div class="col-md-6 text-left" id="detailAmount">
				<fmt:formatNumber type="number" value="${financeGaugeChargeDto.amount}" pattern="0.00" maxFractionDigits="2"/>  
			</div>
		</div> --%>
		 <%-- <div class="form-group col-lg-5">
			
		</div>
		<div class="form-group col-lg-5" id="detailPriceList">
			<c:if test="${financeGaugeChargeDto.sectionalCharge eq '1' }">
				<c:if test="${fn:length(priceList)-2 ge 0}">
					<c:forEach items="${priceList}" var="price" begin="0" end="${fn:length(priceList)-2}" varStatus="status">
						${price}
					</c:forEach>
				</c:if>
			</c:if>
		</div> --%> 
		
		<div class="form-group col-lg-10 text-center mtop30" style="margin-left:80px;">
			<button data-dismiss="modal" class="btn btn-default" type="button" id="cancel" style="border: none; width:180px; height:50px; background:#dadada; font-size:20px;">取消</button>
			<gd:btn btncode="BtnFinanceGaugeChargeDetailEdit"><button class="btn btn-danger ml15" id="saveForm" type="button" data-dismiss="modal" style="border: none; width:180px; height:50px; background:#e86d4c; font-size:20px; margin-left:26px !important;">确认</button></gd:btn>
		</div>
		<input type="hidden" id="detailStatus" value="${financeGaugeChargeDto.status}">
		<input type="hidden" id="detailSectionalCharge" value="${financeGaugeChargeDto.sectionalCharge}">
		<input type="hidden" id="detailNewPrice" value="${financeGaugeChargeDto.newPrice}">
		<input type="hidden" id="detailExpStandardId" value="${financeGaugeChargeDto.expStandardId}">
	   </div>
	  </div>
	</form>
</div>