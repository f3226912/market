<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/pub/tags.inc" %>
       <div class="col-md-max">
          <div class="main-search">
               <div class="main-contracht-tp details-of-contract">收款管理-临时应收款详情</div>
            
           </div>
        <section class="wrapper">
            <form id="tempAddForm" action="#" class="form-horizontal clearfix">
            <input id="contractNo" name="contractNo" type="hidden" value="${contract.contractNo}"/>
            <input id="contractVersion" name="contractVersion" type="hidden" value="${contract.id}"/>
            <div class="col-md-12 col-pd-13">
                 <div class="contract-info">
                 	 <div class="contract-title-block">
                 	 	<span class="contract-info-h3">合同基本信息</span>
                 	 </div>
                 	 <div class="col-md-6 col-info-left">
                 	 	<ul class="col-info-lease-l">
                 	 		<li>租赁资源：${contract.leasingResource}</li>
                 	 		<li>客商名称：${contract.customerName}</li>
                 	 		<li>甲方：${contract.partyA}</li>
                 	 		<li>起租日期：${contract.startLeasingDayString}</li>
                 	 		<li>延迟交租罚金：${contract.leasingForfeit}元/日</li>
                 	 	</ul>
                 	 </div>
                 	 <div class="col-md-6 col-info-right">
                 	 	 <ul class="col-info-lease-r">
                 	 		<li>收费方式：
                 	 			<c:choose>
									<c:when test="${contract.chargingWays== 0}">按周期收费</c:when>
									<c:when test="${contract.chargingWays== 1}">约定总金额</c:when>
								</c:choose>
							</li>
                 	 		<li>合同编号：${contract.contractNo}</li>
                 	 		<li>乙方：${contract.partyB}</li>
                 	 		<li>结束日期：${contract.endLeasingDayString}</li>
                 	 		<li>延迟还铺罚金：${contract.shopForfeit}元/日</li>
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
                 	 		<li>款项名称：${financeFee.feeItemName}</li>
                 	 		<li>经办人：${financeFee.createUserId}</li>
                 	 	</ul>
                 	 </div>
                 	 <div class="col-md-6 col-info-right">
                 	 	 <ul class="col-info-lease-r">
                 	 		<li>本次实收金额：${financeFee.accountPayable}</li>
                 	 		<li>经办时间：${sysDate}</li>
                 	 	</ul>
                 	 </div>
                 </div>
            </div>   
            </form>
        </section>
        <!--打印-->
        <div class="col-md-12 save-box">
        	<ul>
        		<!-- <li class="print-3"><a class="print-s" href="javascript:void(0)" id="saveTForm"><i class="fa fa-credit-card"></i>&nbsp;&nbsp;收款</a></li> -->
        		<li class="print-3"><a class="print-s" href="javascript:void(0)" id="btn-print-should-temp"><i class="fa fa-print"></i>&nbsp;&nbsp;打印</a></li>
        	</ul>
        </div>
        <!-- <div id="printSettingSelectPage_should_temp" align="center"></div> -->
</div>
