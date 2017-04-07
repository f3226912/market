<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/pub/tags.inc" %>
<%@ taglib prefix="gd" uri="http://www.gdeng.cn/gd"%>
<style type="text/css">
.wid{width:120px;display:block;float:left;text-align: right;margin-right:10px;}
.bor{border:1px solid #d7d7d7;border-radius:3px; width:240px; padding-left:5px;}
.contract ul li{overflow: hidden;}
.contract .col-info-lease-l li, .contract .col-info-lease-r li{
	height:auto !important;
	width:450px;
	white-space:nowrap; 
	overflow:hidden; 
	text-overflow:ellipsis;
}
.contract .save-box ul{
	width:392px;
	float:none !important;
	margin:38px auto !important;
	overflow:hidden;
	padding-left:0px;
	text-align: center;
}
label.error{position: relative; left: -40px;}
#phone-error{position: relative; left: -88px; }
#remark-error{position: relative; top:-10px;}
</style>
       <div class="col-md-max contract">
          <div class="main-search">
               <div class="main-contracht-tp details-of-contract">应收款管理</div>
           </div>
        <section class="contract">
            <div class="col-md-12 col-pd-13">
                 <div class="contract-info">
                 	 <div class="contract-title-block">
                 	 	<span class="contract-info-h3">合同基本信息</span>
                 	 </div>
                 	 <div class="col-md-6 col-info-left">
                 	 	<ul class="col-info-lease-l">
                 	 		<li><span class="wid">租赁资源：</span><span title="${contract.leasingResource}">${contract.leasingResource}</span></li>
                 	 		<li><span class="wid">客商名称：</span>${contract.customerName}</li>
                 	 		<li><span class="wid">甲方：</span>${contract.partyA}</li>
                 	 		<li><span class="wid">起租日期：</span>${contract.startLeasingDayString}</li>
                 	 		<li><span class="wid">延迟交租罚金：</span>${contract.leasingForfeitString}元/日</li>
                 	 	</ul>
                 	 </div>
                 	 <div class="col-md-6 col-info-right">
                 	 	 <ul class="col-info-lease-r">
                 	 		<li><span class="wid">收费方式：</span>
                 	 			<c:choose>
									<c:when test="${contract.chargingWays== 0}">按周期收费</c:when>
									<c:when test="${contract.chargingWays== 1}">约定总金额</c:when>
								</c:choose>
							</li>
                 	 		<li><span class="wid">合同编号：</span>${contract.contractNo}</li>
                 	 		<li><span class="wid">乙方：</span>${contract.partyB}</li>
                 	 		<li><span class="wid">结束日期：</span>${contract.endLeasingDayString}</li>
                 	 		<li><span class="wid">延迟还铺罚金：</span>${contract.shopForfeitString}元/日</li>
                 	 	</ul>
                 	 </div>
                 </div>
            </div>
            <div class="col-md-12 col-pd-13">
            <form id="shouldAddForm" action="#" class="form-horizontal clearfix">
                 <div class="contract-info">
                 	 <div class="contract-title-block">
                 	 	<span class="contract-info-h3">收款信息</span>
                 	 </div>
                 	 <input id="id" name="id" type="hidden" value="${financeFee.id}"/>
                 	 <div class="col-md-6 col-info-left">
                 	 	<input id="recordStatus"  type="hidden" value="${financeFee.status}"/>
                 	 	<ul class="col-info-lease-l">
                 	 		<li><span class="wid">款项名称：</span>${financeFee.feeItemName}</li>
                 	 		<li><span class="wid">计费起始日期：</span>${financeFee.startTimeString}</li>
                 	 		<li><span class="wid">金额：</span>${financeFee.amountString}</li>
                 	 		<li><span class="wid">应收金额：</span>${financeFee.accountPayableString}<input id="accountPayable"  type="hidden" value="${financeFee.accountPayable}"/></li>
                	 		<c:choose>
								<c:when test="${financeFee.status== 0}">
		                 	 		<li><span class="wid"><em class="inf-em">*</em>交款人：</span><input id="reciever" class="bor" name="reciever" type="text"/></li>
								</c:when>
								<c:when test="${financeFee.status== 1}">
									<li><span class="wid"><em class="inf-em">*</em>交款人：</span>${financeFee.payer}<input id="reciever" class="bor" name="reciever" type="hidden" value="${financeFee.payer}"/></li>
								</c:when>
							</c:choose>	                 	 		
                 	 		<li><span class="wid">经办人：</span>${sysUser}<input id="agent" class="bor" name="agent" type="hidden" value="${financeFee.createUserId}"/></li>
                 	 	</ul>
                 	 </div>
                 	 <div class="col-md-6 col-info-right">
                 	 	 <ul class="col-info-lease-r">
                 	 		<li><span class="wid">缴费期限：</span>${financeFee.timeLimitString}</li>
                 	 		<li><span class="wid">计费结束日期：</span>${financeFee.endTimeString}</li>
                 	 		<li><span class="wid">优惠金额：</span>${financeFee.accountRebateString}</li>
                	 		<c:choose>
								<c:when test="${financeFee.status== 0}">
		                 	 		<li><span class="wid"><em class="inf-em">*</em>本次实收金额：</span><input id="accountPayed" class="bor" name="accountPayed" type="text"/></li>
								</c:when>
								<c:when test="${financeFee.status== 1}">
									<li><span class="wid"><em class="inf-em">*</em>本次实收金额：</span>${financeFee.accountPayed}<input id="accountPayed"  name="accountPayed" type="hidden" value="${financeFee.accountPayed}"/></li>
								</c:when>
							</c:choose>
                	 		<c:choose>
								<c:when test="${financeFee.status== 0}">
		                 	 		<li><span class="wid"><em class="inf-em">*</em>手机号码：</span><input id="phone" class="bor" name="phone" type="text"/></li>
								</c:when>
								<c:when test="${financeFee.status== 1}">
									<li><span class="wid"><em class="inf-em">*</em>手机号码：</span>${financeFee.accountPayed}<input id="phone" class="bor" name="phone" type="hidden" value="${financeFee.phone}"/></li>
								</c:when>
							</c:choose>							
                 	 		<li><span class="wid">经办时间：</span>${sysDate}<input id="agentTime" class="bor" name="agentTime" type="hidden" value="${sysDate}"/></li>
                 	 	</ul>
                 	 </div>
                 	<!--<div class="change-details">-->
                 		<!--<span class="change-details-sp">--><!-- <i class="change-details-i">*</i>收款说明 : --><!--</span>-->
               	 		<!--<c:choose>
							<c:when test="${financeFee.status== 0}">
		                 		<textarea rows="5" cols="" class="text-area bor" id="remark" name="remark" style=""></textarea>   
							</c:when>
							<c:when test="${financeFee.status== 1}">
								<textarea rows="5" cols="" class="text-area bor" id="remark" name="remark" style="" readonly>${financeFee.remark}</textarea>
							</c:when>
						</c:choose>	                 		-->
                 	<!--</div>-->
                 	 <div class="col-md-12 style="margin-top:30px;">
                 	 	  <span class="wid"><em class="inf-em">*</em>收款说明：</span>
                 	 	  <div style="float:left;">
                 	 	    <c:choose>
							 <c:when test="${financeFee.status== 0}">
				                 <textarea rows="5" cols="" class="text-area bor" id="remark" name="remark" style="width:783px;"></textarea>   
							 </c:when>
							 <c:when test="${financeFee.status== 1}">
								 <textarea rows="5" cols="" class="text-area bor" id="remark" name="remark" style="width:783px;" readonly>${financeFee.remark}</textarea>
							 </c:when>
						     </c:choose>	
						  </div>
                 	 </div>
                 </div>
                 </form>
            </div>   
        </section>
        <!--打印-->
        <div class="col-md-12 save-box">
        	<input type="hidden" id="shouldRecFeeId" value="${financeFee.id}">
        	<ul>
        		<gd:btn  btncode="BtnFianceFeeShouldDetailPayUp"><li class="print-3"><a class="print-s" href="javascript:void(0)" id="saveSForm"><i class="fa fa-credit-card"></i>&nbsp;&nbsp;收款</a></li></gd:btn>
        		<gd:btn  btncode="BtnFianceFeeSholudDetailPrint"><li class="print-3"><a class="print-s" href="javascript:void(0)" id="btn-print-should"><i class="fa fa-print"></i>&nbsp;&nbsp;打印</a></li></gd:btn>
        	</ul>
        </div>
        <div id="printSettingSelectPage_should" align="center"></div>
</div>

