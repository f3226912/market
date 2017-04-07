<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/pub/tags.inc" %>
<style>
label.error{position: relative; left: -40px;}
#phone-error{position: relative; left: -88px; }
#remark-error{position: relative; top:-10px;}
</style>
       <div class="col-md-max contract">
          <div class="main-search">
               <div class="main-contracht-tp details-of-contract">退款管理</div>
            
           </div>
       
            <form id="backAddForm" action="#" class="form-horizontal clearfix">
            <input id="contractNo" name="contractNo" type="hidden" value=""/>
            <input id="contractVersion" name="contractVersion" type="hidden" value=""/>
            <div class="col-md-12 col-pd-13">
                 <div class="contract-info form_col">
                 	 <div class="contract-title-block">
                 	 	<span class="contract-info-h3">合同基本信息</span>
                 	 </div>
                 	  <div class="col-md-12 fees-box-zp" style="position:relative">
                 	     <span class="list-sp">租赁资源：</span>
                 	     <span class="list-sp-textdd"><input id="resourceName" class="list-sp-text" name="resourceName" type="text" readonly  style="position:relative; top:-5px;" /></span>
                 	 	 <input id="resourceId" name="resourceId" type="hidden"/>
                         <div style="position: absolute; top:3px; right:74px; color: green; font-weight: bold;">选择</div>
                 	  </div>
                 	 <div class="col-md-6 col-info-left">
                 	 	<ul class="col-info-lease-l">
                 	 		<li><span class="list-sp">客商名称：</span><span class="fees-trator-tx" id="customerNameView"></span></li>
                 	 		<li><span class="list-sp">甲方：</span><span class="fees-trator-tx" id="partyAView"></span></li>
                 	 		<li><span class="list-sp">起租日期：</span><span class="fees-trator-tx" id="startLeasingDayView"></span></li>
                 	 		<li><span class="list-sp">延迟交租罚金：</span><span class="fees-trator-tx" id="leasingForfeitView" style="width:auto; padding-right:5px;"></span><span>元/日<span></li>
                 	 	</ul>
                 	 </div>
                 	 <div class="col-md-6 col-info-right">
                 	 	 <ul class="col-info-lease-r">
                 	 		<!-- <li><span class="list-sp">收费方式：</span><span id="chargingWaysView"></span></li> -->
                 	 		<li><span class="list-sp">合同编号：</span><span class="fees-trator-tx" id="contractNoView"></span></li>
                 	 		<li><span class="list-sp">乙方：</span><span class="fees-trator-tx" id="partyBView"></span></li>
                 	 		<li><span class="list-sp">结束日期：</span><span class="fees-trator-tx" id="endLeasingDayView"></span></li>
                 	 		<li><span class="list-sp">延迟还铺罚金：</span><span class="fees-trator-tx" id="shopForfeitView" style="width:auto; padding-right:5px;"></span><span>元/日<span></li>
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
                 	 		<li><span class="list-sp"><em class="inf-em">*</em>款项名称：</span><span class="fees-trator-tx">${expenditure.name}<input id="feeItemId" class="fees-ipt-tx tor-tw" name="feeItemId" type="hidden" value="${expenditure.id}"/></span></li>
                 	 		<li><span class="list-sp">交款人：</span><span class="fees-trator-tx"><input id="reciever" class="fees-ipt-tx tor-tw" name="reciever" type="text"/></span></li>
                 	 		<li><span class="list-sp">经办人：</span><span class="fees-trator-tx">${sysUser}<input id="agent" class="fees-ipt-tx tor-tw" name="agent" type="hidden" value="${sysUser}"/></span></li>
                 	 		<!-- <li><span class="list-sp"></li> -->
                 	 	</ul>
                 	 </div>
                 	 <div class="col-md-6 col-info-right">
                 	 	 <ul class="col-info-lease-r">
                 	 		<li><span class="list-sp"><em class="inf-em">*</em>本次实收金额：</span><span class="fees-trator-tx"><input id="accountPayed" class="fees-ipt-tx tor-tw" name="accountPayed" type="text" /></span></li>
                 	 		<li><span class="list-sp">手机号码：</span><span class="fees-trator-tx"><input id="phone" class="fees-ipt-tx tor-tw" name="phone" type="text"/></span></li>
                 	 		<li><span class="list-sp">经办时间：</span><span class="fees-trator-tx">${sysDate}<input id="agentTime" class="fees-ipt-tx tor-tw" name="agentTime" type="hidden" value="${sysDate}"/></span></li>
                 	 	</ul>
                 	 </div>
                 	<div class="change-details">
                 		<span class="change-details-sp"><em class="inf-em">*</em>退款说明：</span>
                 		<textarea rows="5" cols="107" class="text-area" id="remark" name="remark" style="margin-top:-10px; margin-left: 3px;"></textarea>
                 	</div>                     	 
                 </div>
            </div>   
            </form>
        <!--打印-->
        <div class="col-md-12 save-box">
        	<ul style="margin-left:310px;">
        		<gd:btn  btncode="BtnFianceFeeSholudBackDetailPayUp"><li class="print-3"><a class="print-s" href="javascript:void(0)" id="saveBForm"><i class="fa fa-credit-card"></i>&nbsp;&nbsp;退款</a></li></gd:btn>
        		<gd:btn  btncode="BtnFianceFeeSholudBackDetailPrint"><li class="print-3"><a class="print-s" href="javascript:void(0)" id="btn-print-should-back"><i class="fa fa-print"></i>&nbsp;&nbsp;打印</a></li></gd:btn>
        	</ul>
        </div>
        <div id="printSettingSelectPage_should_back" align="center"></div>
	<!--选择租赁资源-->
	<div id="leasResources">
		<div class="leasBox clearfloat">
			<div class="leaSelect">
				<label class="typesOf">资源类型：</label>
				<select id="resourceType" class="guaranSelect">
	<!-- 			    <option value="1" selected="selected">商铺</option>
				    <option value="2">停车位</option>
				    <option value="3">广告位</option> -->
				</select>
			</div>
			<div class="floorArea clearfloat">
				<div class="floorA-left">
					<div class="floorLoudong">
						<h3 class="floorLoudong_bold">区域\楼栋</h3>
						<div class="floor-max-width" id="buildingDiv">
		<!-- 					 <div class="floorAZone">
							   <b style="border-top:none;">A区</b>
							   <ul class="floorDong">
							     <li class="floorls">1栋</li>
							     <li class="floorls">2栋</li>
							     <li class="floorls">3栋</li>
							   </ul>
							 </div> -->
		<!-- 					 <div class="floorAZone">
							   <b>B区</b>
							   <ul class="floorDong">
							     <li class="floorls">1栋</li>
							     <li class="floorls">2栋</li>
							     <li class="floorls">3栋</li>
							   </ul>
							 </div>
							 <div class="floorAZone">
							   <b>C区</b>
							   <ul class="floorDong">
							     <li class="floorls">1栋</li>
							     <li class="floorls">2栋</li>
							     <li class="floorls">3栋</li>
							   </ul>
							 </div> -->
						
						</div>
					</div>
					<div class="floor-num" style="display:none;">
						<h3 class="floors-t">楼层</h3>
						<div class="floorAZone" id="floorDiv">
						   <ul class="floorDongUl" id="floorUl">
	<!-- 					     <li class="floorls">5</li>
						     <li class="floorls">4</li>
						     <li class="floorls">3</li>
						     <li class="floorls">2</li>
						     <li class="floorls">1</li> -->
						   </ul>
						</div>
					</div>
					<div class="resources-container">
					  <div class="floorResources" style="display:none;">
						<h3 class="resources" id="resourceDiv">资源</h3>
						
		<!-- 				<div class="resources-rows">
						   <p class="resourcesUnit">一单元</p>
						   <ul class="resourcesO">
						     <li class="canRent"><span>501</span>  可租  1000m²</li>
						     <li class="canRent"><span>501</span>  可租  1000m²</li>
						     <li class="canRent"><span>501</span>  可租  1000m²</li>
						     <li class="canRent"><span>501</span>  可租  1000m²</li>
						     <li class="canRent"><span>501</span>  可租  1000m²</li>
						     </ul>
						</div>
						<div class="resources-rows">
						   <p class="resourcesUnit">二单元</p>
						   <ul class="resourcesO">
						     <li class="canRent"><span>501</span>  可租  1000m²</li>
						     <li class="canRent"><span>501</span>  可租  1000m²</li>
						     <li class="canRent"><span>501</span>  可租  1000m²</li>
						     <li class="canRent"><span>501</span>  可租  1000m²</li>
						     <li class="canRent"><span>501</span>  可租  1000m²</li>
						     </ul>
						</div>
						<div class="resources-rows">
						   <p class="resourcesUnit">三单元</p>
						   <ul class="resourcesO">
						     <li class="canRent"><span>501</span>  可租  1000m²</li>
						     <li class="canRent"><span>501</span>  可租  1000m²</li>
						     <li class="canRent"><span>501</span>  可租  1000m²</li>
						     <li class="canRent"><span>501</span>  可租  1000m²</li>
						     <li class="canRent"><span>501</span>  可租  1000m²</li>
						     </ul>
						</div>
						<div class="resources-rows">
						   <p class="resourcesUnit">四单元</p>
						   <ul class="resourcesO">
						     <li class="canRent"><span>501</span>  可租  1000m²</li>
						     <li class="canRent"><span>501</span>  可租  1000m²</li>
						     <li class="canRent"><span>501</span>  可租  1000m²</li>
						     <li class="canRent"><span>501</span>  可租  1000m²</li>
						     <li class="canRent"><span>501</span>  可租  1000m²</li>
						     </ul>
						</div> -->
						</div>
					</div>
				</div>
			</div>
		</div>
	</div> 
	        
 </div>

        