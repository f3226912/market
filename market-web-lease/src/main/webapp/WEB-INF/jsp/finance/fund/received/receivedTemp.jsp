<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/pub/tags.inc" %>
<style>
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
</style>
       <div class="col-md-max">
            <div class="header">收款管理<span style="font-size:14px; padding-left:16px">临时收款</span></div>
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
                 	 		<li><span class="wid">甲方</span>：${contract.partyA}</li>
                 	 		<li><span class="wid">起租日期：</span>${contract.startLeasingDayString}</li>
                 	 		<li><span class="wid">延迟交租罚金：</span>${contract.leasingForfeitString}元/日</li>
                 	 	</ul>
                 	 </div>
                 	 <div class="col-md-6 col-info-right">
                 	 	 <ul class="col-info-lease-r">
                 	 	 	<li></li>
<%--                  	 		<li>收费方式：
                 	 			<c:choose>
									<c:when test="${contract.chargingWays== 0}">按周期收费</c:when>
									<c:when test="${contract.chargingWays== 1}">约定总金额</c:when>
								</c:choose>
							</li>     --%>             	 		
                 	 		<li><span class="wid">合同编号：</span>${contract.contractNo}</li>
                 	 		<li><span class="wid">乙方：</span>${contract.partyB}</li>
                 	 		<li><span class="wid">结束日期：</span>${contract.endLeasingDayString}</li>
                 	 		<li><span class="wid">延迟还铺罚金：</span>${contract.shopForfeitString}元/日</li>
                 	 	</ul>
                 	 </div>
                 </div>
            </div>
            
            <div class="col-md-12 col-pd-13">
                 <div class="contract-info">
                 	 <div class="contract-title-block">
                 	 	<span class="contract-info-h3">收款信息</span>
                 	 </div>
                 	 <div class="col-md-6 col-info-left">
                 	 	<ul class="col-info-lease-l">
                 	 		<li><span class="wid">款项名称：</span>${financeFee.feeItemName}</li>
                 	 		<li><span class="wid">交款人：</span>${financeFee.payer}</li>
                 	 		<li><span class="wid">经办人：</span>${user.name}</li>
                 	 		<li><span class="wid">收款说明：</span>${financeFee.remark}</li>
                 	 	</ul>
                 	 </div>
                 	 <div class="col-md-6 col-info-right">
                 	 	 <ul class="col-info-lease-r">
                 	 		<li><span class="wid">本次实收金额：</span>${financeFee.accountPayedString}</li>
                 	 		<li><span class="wid">手机号码：</span>${financeFee.phone}</li>
                 	 		<li><span class="wid">经办时间：</span>${financeFee.createTimeString}</li>
                 	 	</ul>
                 	 </div>
                 </div>
            </div>   
        </section>
        <!--打印-->
        <div class="col-md-12 save-box">
        	<input type="hidden" id="receivedFeeId" value="${financeFee.id}">
        	<ul>
        		<gd:btn  btncode="BtnFianceFeeReceivedTempDetailPrint"><li class="print-3" style="width:180px; height:50px; border-radius: 3px; margin:0 auto;text-align:center;border:1px solid #53b279;background-color:#FFFFFF;"><a class="print-s" href="javascript:void(0);" id="btn-print-received-temp" style="color:#53b279;background-color:#FFFFFF;"><i class="fa fa-print"></i>&nbsp;&nbsp;打印</a></li></gd:btn>
        	</ul>
        </div>
        <div id="printSettingSelectPage_has_temp" align="center"></div>
</div>
