<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/pub/tags.inc" %>
<style>
.span-select-down{line-height:30px !important;}
label.error{position: relative; left: -40px;}
#phone-error{position: relative; left: -88px; }
#remark-error{position: relative; top:-10px;}
</style>
       <div class="col-md-max contract">
          <div class="main-search">
               <div class="main-contracht-tp details-of-contract">收款管理</div>
            
           </div>
        
            <form id="tempAddForm" action="#" class="form-horizontal clearfix">
            <input id="contractNo" name="contractNo" type="hidden" value=""/>
            <input id="contractVersion" name="contractVersion" type="hidden" value=""/>
            <div class="col-md-12 col-pd-13">
                 <div class="contract-info form_col">
                 	 <div class="contract-title-block">
                 	 	<span class="contract-info-h3">合同基本信息</span>
                 	 </div>
                 	 <div class="col-md-12 fees-box-zp" style="position:relative">
                 	     <span class="list-sp">租赁资源：</span>
                 	     <span class="list-sp-textdd"><input id="resourceName" class="list-sp-text" name="resourceName" type="text" readonly style="position:relative; top:-5px;" /></span>
                 	 	 <input id="resourceId" name="resourceId" type="hidden" />
                 	 	 <div style="position: absolute; top:3px; right:74px; color: green; font-weight: bold;">选择</div>
                 	  </div>
                 	 <div class="col-md-6 col-info-left">
                 	 	<ul class="col-info-lease-l">
                 	 		<li><span class="list-sp">客商名称：</span><span class="fees-trator-tx" id="customerNameView"></span></li>
                 	 		<li><span class="list-sp">甲方：</span><span class="fees-trator-tx" id="partyAView"></span></li>
                 	 		<li><span class="list-sp">起租日期：</span><span class="fees-trator-tx" id="startLeasingDayView"></span></li>
                 	 		<li><span class="list-sp">延迟交租罚金：</span><span class="fees-trator-tx" id="leasingForfeitView">元/日</span></li>
                 	 	</ul>
                 	 </div>
                 	 <div class="col-md-6 col-info-right">
                 	 	 <ul class="col-info-lease-r">
                 	 		<li><span class="list-sp">合同编号：</span><span class="fees-trator-tx" id="contractNoView"></span></li>
                 	 		<li><span class="list-sp">乙方：</span><span class="fees-trator-tx" id="partyBView"></span></li>
                 	 		<li><span class="list-sp">结束日期：</span><span class="fees-trator-tx" id="endLeasingDayView"></span></li>
                 	 		<li><span class="list-sp">延迟还铺罚金：</span><span class="fees-trator-tx" id="shopForfeitView">元/日</span></li>
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
                 	 	<!-- 计费标准id -->
                 	 	<input id="freightBasisId" name="freightBasisId" type="hidden" />
                 	 	<input id="feeItemId" name="feeItemId" type="hidden" />
                 	 	<ul class="col-info-lease-l">
                 	 		<li><span class="list-sp"><em class="inf-em">*</em>款项名称：</span><span class="fees-trator-tx"><input id="feeItemName" class="fees-ipt-tx tor-tw" name="feeItemName" type="text"/></span></li>
                 	 		<li><span class="list-sp">交款人：</span><span class="fees-trator-tx"><input id="reciever" class="fees-ipt-tx tor-tw" name="reciever" type="text"/></span></li>
                 	 		<li><span class="list-sp">经办人：</span><span class="fees-trator-tx">${sysUser}<input id="agent"  name="agent" type="hidden" value="${sysUser}"/></span></li>
                 	 		<!-- <li>收款说明：</li> -->
                 	 	</ul>
                 	 </div>
                 	 <div class="col-md-6 col-info-right">
                 	 	 <ul class="col-info-lease-r">
                 	 		<li><span class="list-sp">本次实收金额：</span><span class="fees-trator-tx"><input id="accountPayed" class="fees-ipt-tx tor-tw" name="accountPayed" type="text"/></span></li>
                 	 		<li><span class="list-sp">手机号码：</span><span class="fees-trator-tx"><input id="phone" class="fees-ipt-tx tor-tw" name="phone" type="text"/></span></li>
                 	 		<li><span class="list-sp">经办时间：</span><span class="fees-trator-tx">${sysDate}<input id="agentTime"  name="agentTime" type="hidden" value="${sysDate}"/></span></li>
                 	 	</ul>
                 	 </div>
                 	<div class="change-details">
                 		<span class="change-details-sp">&nbsp;&nbsp;&nbsp; 收款说明：</span>
                 		<textarea rows="5" cols="107" class="text-area" id="remark" name="remark" style="margin-top:-10px; margin-left: 3px;"></textarea>
                 	</div>                     	 
                 </div>
            </div>   
            </form>
        <!--打印-->
        <div class="col-md-12 save-box">
        	<ul style="margin-left:310px;">
	        	<gd:btn  btncode="BtnFianceFeeSholudTempDetailPayUp"><li class="print-3"><a class="print-s" href="javascript:void(0)" id="saveTForm"><i class="fa fa-credit-card"></i>&nbsp;&nbsp;收款</a></li></gd:btn>
        		<gd:btn  btncode="BtnFianceFeeSholudTempDetailPrint"><li class="print-3"><a class="print-s" href="javascript:void(0)" id="btn-print-should-temp"><i class="fa fa-print"></i>&nbsp;&nbsp;打印</a></li></gd:btn>
        	</ul>
        </div>
        <div id="printSettingSelectPage_should_temp" align="center"></div>
        
	<!--选择其他费项开始-->
	<div class="" id="feeItemDiv">
	    <input id="feeItemIdBack" name="feeItemIdBack" type="hidden" />
	    <input id="feeItemNameBack" name="feeItemNameBack" type="hidden" />
		<input id="freightBasisIdBack" name="freightBasisIdBack" type="hidden" />
		<div class="floor-fees">
			<div class="fees-name-box">
				<p class="fees-p-rows">
					<span class="fees-name-left">费项名称</span>
					<span class="fees-name-right">资源</span>
				</p>
				<p class="fee-items ecm-tital" id="tempHeader">临时性费项</p>
				<!-- 请勿删除如下注释的div -->
	<!-- 			<div class="fees-checkbox-box">
					<label for="checkbox-ns"><input type="checkbox" id="checkbox-ns" class="checkbox-none Einput-m" checked="checked"/>
				    <span class="checkbox-layer"></span><em class="checkbox-em">行业保证金</em>
				    </label>
				    <span class="span-select-down">
				    	<select class="guarantee-select">
				    		<option value="1">行业保证金（指定金额 1000元）</option>
				    		<option value="2">行业保证金（指定金额 2000元）</option>
				    		<option value="3">行业保证金（指定金额 3000元）</option>
				    		<option value="4">行业保证金（指定金额 4000元）</option>
				    		<option value="5">行业保证金（指定金额 5000元）</option>
				    	</select>
				    </span>
				</div>		-->
				<!-- <div class="fees-checkbox-box">
					<label for="checkbox-n1"><input type="checkbox" id="checkbox-n1" class="checkbox-none Einput-m" checked="checked"/>
				    <span class="checkbox-layer"></span><em class="checkbox-em" >租赁押金</em></label>
				    <span class="span-select-down">
				    	<select class="guarantee-select">
				    		<option value="1">租赁押金(手工录入)</option>
				    	</select>
				    </span>
				</div>  -->
			</div>
			
		</div>
	</div>
	<!--选择其他费项结束-->  
      
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
					<div class="resources-container"> <!--修改最外层包一个div元素  -->
					 <div class="floorResources" style="display:none;">
						<h3 class="resources" id="resourceDiv">资源</h3>
						
		<!-- 			<div class="resources-rows">
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
						 -->
					 </div>	
					</div>
				</div>
			</div>
		</div>
	</div>             
        
</div>
