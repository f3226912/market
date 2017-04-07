<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<style>
.contract .col-info-lease-l li, .contract .col-info-lease-r li{ padding-bottom:38px;}
label.error{ position: relative; left:-118px;}
.fees-box-zp label.error{position: relative; top: -1px; left: -77px;}
.date-icon #startLeasingDay-error ,.date-icon #endLeasingDay-error{position: relative; top: -31px; left: 216px;}
.contract #-error{ position: absolute; top: 0px; left: -25px; width: 100px;}
.clearRental{ width:100px;}
.contract .fees-trator-tx{ position:relative;}
.contract .fa-calendar{ position: absolute; top: 36px!important;}
.border-none-right .fa-calendar{ position: absolute; top: 6px!important;}
.ipt_m{ position:absolute; top:0; float:left; right: 15px; font-style:normal}
#startLeasingDay ,#endLeasingDay{position: relative;left: 0px;top: 0px;}
</style> 
       <!-- <input id="click_btn" type="button" value="请求数据"/> -->
       <div class="col-md-max contract">
          <div class="main-search">
               <div class="main-contracht-tp details-of-contract">合同管理</div>
          </div>
		<form action="" id="mainDto_form">
			<input type="hidden" name="leasingResourceIds" id="leasingResourceIds" value="">
			<input name="leasingResource" type="hidden" id="leasingResource">
			<input name="freeArea" type="hidden" id="freeArea">
			<input name="floorArea" type="hidden" id="floorArea">
			<input name="innerArea" type="hidden" id="innerArea">
			<input type="hidden" id="marketResourceTypeId">
          <div class="col-md-12 col-pd-13">
               <div class="contract-info">
               	 <div class="contract-title-block">
               	 	<span class="contract-info-h3">合同基本信息</span>
               	 </div>
               	 <div class="col-md-12 fees-box-zp" style="position:relative">
               	 	  <span class="list-sp"><em class="inf-em">*</em>11租赁资源：</span>
               	 	  <span class="fees-trator-tx" id="leasingResource_div">
            	 	  </span>
               	 </div>
               	 <div class="col-md-6 col-info-left">
               	 	<ul class="col-info-lease-l">
               	 		<li>
               	 			<span class="list-sp"><em class="inf-em"></em>客户名称：</span>
               	 			<span class="fees-trator-tx" id="customerName">
               	 			<!-- <input class="fees-ipt-tx" type="text" name="customerName"/> -->
               	 			</span>
               	 		</li>
               	 		<li>
               	 			<span class="list-sp"><em class="inf-em"></em>甲方：</span>
               	 			<span class="fees-trator-tx" id="partyA">
               	 			<!-- <input class="fees-ipt-tx" type="text" name="partyA"/>--></span> 
               	 		</li>
               	 		<li>
               	 			<span class="list-sp"><em class="inf-em"></em>起租日期：</span>
               	 			<div class="form-group date-icon widLf" id="startLeasingDay">
							<!-- <input type="text" class="form-control start-date" name="startLeasingDay" placeholder="起租日期">
							<i class="fa fa-calendar"></i> -->
						</div>
               	 		</li>
               	 		<li>
               	 			<span class="list-sp"><em class="inf-em"></em>延迟交租罚金：</span>
               	 			<span class="fees-trator-tx" id="leasingForfeit">
               	 			<!-- <input class="fees-ipt-tx" type="text" name="leasingForfeit"/> -->
               	 			元/日</span>
               	 		</li>
               	 	</ul>
               	 </div>
               	 <div class="col-md-6 col-info-right">
               	 	 <ul class="col-info-lease-r">
               	 		<li>
               	 			<span class="list-sp"><em class="inf-em"></em>合同编号：</span>
               	 			<span class="fees-trator-tx" id="contractNo">
               	 			<!-- <input class="fees-ipt-tx" type="text" name="contractNo" id="contractNo"/> -->
               	 			</span>
               	 		</li>
               	 		<li>
               	 			<span class="list-sp"><em class="inf-em"></em>乙方：</span>
               	 			<span class="fees-trator-tx" id="partyB">
               	 			<!-- <input class="fees-ipt-tx" type="text" name="partyB"/> --></span>
               	 		</li>
               	 		<li>
               	 			<span class="list-sp"><em class="inf-em"></em>结束日期：</span>
               	 			<div class="form-group date-icon widLf" id="endLeasingDay">
							<!-- <input type="text" class="form-control start-date" placeholder="结束日期" name="endLeasingDay"> 
							<i class="fa fa-calendar"></i>-->
							</div>
               	 		</li>
               	 		<li>
               	 			<span class="list-sp"><em class="inf-em"></em>延迟还铺罚金：</span>
               	 			<span class="fees-trator-tx" id="shopForfeit">
               	 			<!-- <input class="fees-ipt-tx" type="text" name="shopForfeit"/> -->
               	 			</span>
               	 		</li>
               	 	</ul>
               	 </div>
               </div>
          </div>
          </form>
            
          <div class="col-md-12 col-pd-13">
               <div class="contract-info">
               	 <div class="contract-title-block">
               	 	<span class="contract-info-h3">&nbsp;&nbsp;&nbsp;&nbsp;租金约定&nbsp;&nbsp;&nbsp;&nbsp;</span>
               	 </div>
               	 <div class="charges-Fees">
                	<span><em class="payment-cycle-sp">*</em><b class="col-Fees-bold">收费方式：</b></span>
			    <label for="radio-fees" id="cycle_charge"><input type="radio" id="radio-fees" name="chargingWays" class="regular-radio" value="0" checked/>
			    <span class="radio-mt10"></span><em class="radio-em-fees">按周期收费</em></label>
			    <label for="radio-fees-1" id="ent_agreement"><input type="radio" id="radio-fees-1" name="chargingWays" class="regular-radio" value="1"/>
				<span class="radio-mt10"></span><em class="radio-em-fees">按约定总金额</em></label>	
               	 </div>
               	 <!--11111111111111111-->
               	<div class="billing_area">
               	 <div class="charges-paid">
                	<span><em class="payment-cycle-sp">*</em><b class="col-Fees-bold">计费面积：</b></span>
			    <label for="radio-charges-1"><input type="radio" id="radio-charges-1" name="billingArea" class="regular-radio" value="0" checked/>
			    <span class="radio-mt10"></span><em class="radio-em-fees">可租面积&nbsp;&nbsp;&nbsp;</em></label>
			    <label for="radio-charges-2"><input type="radio" id="radio-charges-2" name="billingArea" class="regular-radio" value="1"/>
				<span class="radio-mt10"></span><em class="radio-em-fees">建筑面积</em></label>
				<label for="radio-charges-3"><input type="radio" id="radio-charges-3" name="billingArea" class="regular-radio" value="2"/>
				<span class="radio-mt10"></span><em class="radio-em-fees">套内面积</em></label>	
               	 </div>
               	    <div class="charges-max-box">
                	   <div class="charges-paid-bt">
                	 	  <span class="charges-paid-sz">租赁约定</span>
                	 	  <ul class="charges-add-remove">
                	 		<li id="addTr" class="charges-add"><i class="fa fa-plus-square"></i>新增</li>
                	 		<li id="removeTr" class="charges-remove"><i class="fa fa-trash"></i>删除</li>
                	 	  </ul>
                	   </div>
                	   <form action="" id="leaseListForm">
                	   <table class="charges-tab-style" cellspacing="0" id="leaseTable">
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
					  <!-- <tr class="charges-tab-blank">
					    <td id="eq-add">1</td>
					    <td>
					    	<div class="date-icon border-none-right ">
							   <input type="text" class="form-control start-date" placeholder="起始日期" name="startDay">
							   <i class="fa fa-calendar"></i>
							</div>
					    </td>
					    <td>
					    	<div class="date-icon border-none-right border-lt-right">
							   <input type="text" class="form-control start-date" placeholder="截止日期" name="endDay">
							   <i class="fa fa-calendar"></i>
							</div>
					    </td>
					    <td>
							<select class="form-control wids" name="billingUnit" id='billingUnit'>
							   <option value="0" selected="selected">元/月/平</option>
							   <option value="1">元/季/平</option>
							   <option value="2">元/半年/平</option>
							   <option value="3">元/年/平</option>
							</select>
					    </td>							    
					    <td class="clearUnitPrice"><input class="rents edinput" type="text" name="unitPrice" ></td>
					    <td class="area"><input value="0" name="leaseBillingArea" readonly="readonly" style="width:80px"></td>
					    <td class="totalM clearRental"><input value="0" name="rental" readonly="readonly" style="width:100px"></td>							    
					  </tr> -->
					  
					  </tbody>
                	 	</table>
                	 	</form>
               	    </div>
               	    <div class="charges-max-box">
                	   <div class="charges-paid-bt">
                	 	  <span class="charges-paid-sz">免租约定</span>
                	 	  <ul class="charges-add-remove">
                	 		<li id="leaseDad" class="charges-add"><i class="fa fa-plus-square"></i>新增</li>
                	 		<li id="leaseRed" class="charges-remove"><i class="fa fa-trash"></i>删除</li>
                	 	  </ul>
                	   </div>
                	   <table class="charges-tab-style" cellspacing="0" id="freeTable">
                	   	<tbody class="leaseDs">
         	 		 <tr class="charges-tab-bg">
					    <td class="charges-tab-bor">序号</td>
					    <td>起始日期</td>
					    <td>免租月数</td>
					    <td>免租天数</td>
					    <td>截止日期</td>
					  </tr>
					  <!-- <tr class="charges-tab-blank">
					    <td>1</td>
					    <td>
					      <div class="date-icon border-none-right">
							<input type="text" class="form-control start-date start-cet" placeholder="起始日期" name="freeStartDay">
							<i class="fa fa-calendar"></i>
						  </div>
					    </td>
					    <td><input class="rents" type="text" name="freeMonths"/></td>
					    <td><input class="rents" type="text" name="freeDays"/></td>
					    <td><input class="rents" type="text" name="freeEndDay"/></td>
					  </tr> -->
					  </tbody>
                	 	</table>
               	    </div>
               	    <div class="charges-max-box">
                	   <div class="charges-paid-btm">
                	 	  <span class="charges-paid-sz">缴费约定</span>
                	   </div>
                	 	<div class="payment-cycle">
                	 	  	<span><em class="payment-cycle-sp">*</em><b class="col-ft-bold">缴费周期：</b></span>
					    <label for="radio-1-1"><input type="radio" id="radio-1-1" name="paymentCycle" class="regular-radio" value="0" checked/>
					    <span class="radio-mt10"></span><em class="radio-em">月</em></label>
					    <label for="radio-1-2"><input type="radio" id="radio-1-2" name="paymentCycle" class="regular-radio" value="1"/>
						<span class="radio-mt10"></span><em class="radio-em">季度</em></label>
						<label for="radio-1-3"><input type="radio" id="radio-1-3" name="paymentCycle" class="regular-radio" value="2"/>
						<span class="radio-mt10"></span><em class="radio-em">半年</em></label>
						<label for="radio-1-4"><input type="radio" id="radio-1-4" name="paymentCycle" class="regular-radio" value="3"/>
						<span class="radio-mt10"></span><em class="radio-em">年</em></label>
                	 	 </div>
                	 	 <div class="payment-cycle">
                	 	 	<span><em class="payment-cycle-sp">*</em><b class="col-ft-bold">缴费日期：</b></span>
                      		<label for="radio-1-5"><input type="radio" id="radio-1-5" name="paymentDayType" value="0" class="regular-radios" checked>
							<span class="radio-mtd"></span><em class="radio-ems">缴费期初</em><span class="day-pd60"><input type="text" class="radio-em-day" name="paymentDays">天</span></label>
							<label for="radio-1-6"><input type="radio" id="radio-1-6" name="paymentDayType" value="1" class="regular-radios"/>
							<span class="radio-mtd"></span><em class="radio-ems">缴费期末</em><span class="day-pd60"><input type="text" class="radio-em-day" name="paymentDays">天<span></label>
                	 	    <label for="radio-1-7"><input type="radio" id="radio-1-7" name="paymentDayType" value="2" class="regular-radios"/>
							<span class="radio-mtd"></span><em class="radio-ems">下一个缴费期初</em><span class="day-pd60"><input type="text" class="radio-em-day" name="paymentDays">天<span></label>	               	 	 
                	 	 </div>
               	    </div>
               	  </div>
               <!--按约定总金额收费-->
               <div class="contract-rents" style="display: none;">             
               <div class="amount-ipt2">
               	<p class="amount-ret"><em class="payment-cycle-sp">*</em>合同总金额：&nbsp;&nbsp;
               	<input class="total-amount" type="text" name="totalAmt" id="totalAmt"/><span class="sp-lt-amount">元</span></p>
               </div>
               	<div class="total_amount" style="padding-bottom:30px;">
               	     <div class="rents_agreement clearfloat">
               	     	<p class="rents_agreement_p" style="padding-left:63px;">缴费约定</p>
               	 	    <ul class="charges-add-remove charges-paid-dw">
                	        <li id="paymentAdd" class="charges-add"><i class="fa fa-plus-square"></i>新增</li>
                	 	    <li id="paymentRem" class="charges-remove"><i class="fa fa-trash"></i>删除</li>
                	    </ul>
               	     </div>
               		<table id="econManageDT" class="charges-tab-style" cellspacing="0">
         	 		 <tr class="charges-tab-bg">
					    <td class="charges-tab-bor">序号</td>
					    <td>缴费期限</td>
					    <td>缴费金额</td>
					  </tr>
					  <!-- <tr class="charges-tab-blank">
					    <td class="num">1</td>
					    <td>
					      <div class="date-icon border-none-right">
							<input type="text" class="form-control start-date start-cet" placeholder="起始日期" name="paymentTime">
							<i class="fa fa-calendar"></i>
						  </div>
					    </td>
					    <td><input class="rents" type="text" name="paymentAmt"/></td>
					  </tr> -->
					  
               	 	</table>
               	</div>
               	
               </div>
              <!--按约定总金额收费结束-->
               	    
               </div>
               
          </div>
            
            
          <!--选择其他费项开始-->
	 <div class="col-md-12 col-pd-13"> 
                 <div class="contract-info">
                 	 <div class="contract-title-block">
                 	 	<span class="contract-info-h3">其他费项约定</span>
                 	 	<ul class="charges-add-remove charges-paid-dw">
	                 	    <li id="agreementAdd" class="charges-add"><i class="fa fa-plus-square"></i>新增</li>
	                 	 	<li id="agreementRem" class="charges-remove"><i class="fa fa-trash"></i>删除</li>
	                 	</ul>
                 	 </div>
                 	 <div class="charges-max-box charges-max-other">
                 	 <form id="costItemform" novalidate class="cmxform">
	                 	   <table id="econManageDT2" class="charges-tab-style" cellspacing="0">
		         	 		 <tr class="charges-tab-bg">
							    <td class="charges-tab-bor">序号</td>
							    <td style="width:250px;">租赁资源</td>
							    <td>费项类型</td>
							    <td>费项名称</td>
							    <td>计费标准</td>
							    <td>计算方法</td>
							    <td>金额</td>
							  </tr>
							   <tbody id="templateOthersFeeBody">
               				   </tbody>
							 <!--  <tr class="charges-tab-blank">
							    <td class="num2">1</td>
							    <td>农产品大市场一期-4栋D1入口AAA广告位</td>
							    <td>一次性收费</td>
							    <td>行业保证金</td>
							    <td>计费标准名称(单价+计费单位)</td>
							    <td>指定金额</td>
							    <td>1,244,550.00</td>
							  </tr>
							  -->
	                 	 	</table>
	                 	 	</form>
                 	    </div>
                 	
                 </div>
            </div>
           <div class="col-md-12 col-pd-13">
                 <div class="contract-info">
                 	<div class="contract-title-block">
                 	 	<span class="contract-info-h3">&nbsp;&nbsp;&nbsp;&nbsp;变更信息&nbsp;&nbsp;&nbsp;&nbsp;</span>
                 	</div>
                 	<div class="payment-cycle" id="changeReasion">
                 		<input id="changeDtoId" value="" type="hidden">
	                    <span><em class="payment-cycle-sp">*</em><b class="col-ft-bold" style="font-weight: normal;">变更原因：</b></span>
                        <label for="radio-01-07"><input type="radio" id="radio-01-07" name="changeReasion" class="regular-rdi-cause" checked="checked" value="0"/>
						<span class="radio-mt-cause"></span><em class="radio-em">租金水平调整</em></label>
						<label for="radio-01-08"><input type="radio" id="radio-01-08" name="changeReasion" class="regular-rdi-cause" value="1"/>
						<span class="radio-mt-cause"></span><em class="radio-em">费项增减</em></label>
						<label for="radio-01-09"><input type="radio" id="radio-01-09" name="changeReasion" class="regular-rdi-cause" value="2"/>
						<span class="radio-mt-cause"></span><em class="radio-em">租金减免</em></label>	
	                </div>
                 	<div class="contract-administrator"><span class="administrator-ls">经办人：<span id="changeTrustees"></span></span><span class="administrator-date">经办时间：<span id="changeReviewedTime"></span></span></div>
                 	<div class="change-details" style="float:left;">
                 		<span class="change-details-sp"  style="position:relative; left:13px; top:17px;"><!-- <i class="change-details-i">*</i> -->变更说明 :</span>
                 		<textarea style="margin-left:100px;" rows="5" cols="120" id="contractChangeInfo" class="text-area" maxlength="120"></textarea>   
                 	</div>
                 </div>
            </div>   
        
	<!--选择其他费项结束-->  
    <!--开始-->
<div class="" id="agreementAddPop">
	<div class="floor-fees">
	       <p class="fees-p-rows" id="tempHeader">
				<span class="fees-name-left">费项名称</span>
				<span class="fees-name-right">计费标准</span>
			</p>
		<!-- <div class="fees-name-box">
			<p class="fee-items ecm-tital">一次性费项</p>
			<div class="fees-checkbox-box">
				<label for="checkbox-ns"><input type="checkbox" id="checkbox-ns" class="checkbox-none Einput-m" checked="checked"/>
			    <span class="checkbox-layer"></span><em class="checkbox-em">行业保证金</em></label>
			    <span class="span-select-down">
			    	<select class="guarantee-select">
			    		<option value="1">行业保证金（指定金额 1000元）</option>
			    		<option value="2">行业保证金（指定金额 2000元）</option>
			    		<option value="3">行业保证金（指定金额 3000元）</option>
			    		<option value="4">行业保证金（指定金额 4000元）</option>
			    		<option value="5">行业保证金（指定金额 5000元）</option>
			    	</select>
			    </span>
			</div>
			<div class="fees-checkbox-box">
				<label for="checkbox-n1"><input type="checkbox" id="checkbox-n1" class="checkbox-none Einput-m"/>
			    <span class="checkbox-layer"></span><em class="checkbox-em">租赁押金</em></label>
			    <span class="span-select-down">
			    	<select class="guarantee-select">
			    		<option value="1">租赁押金(手工录入)</option>
			    	</select>
			    </span>
			</div>
			<div class="fees-checkbox-box">
				<label for="checkbox-n2"><input type="checkbox" id="checkbox-n2" class="checkbox-none Einput-m"/>
			    <span class="checkbox-layer"></span><em class="checkbox-em">其他</em></label>
			    <span class="span-select-down">
			    	<select class="guarantee-select">
			    		<option value="1"></option>
			    	</select>
			    </span>
			</div>
		</div>
		<div class="fees-name-box">
			<p class="fee-items ecm-tital">周期性费项</p>
			<div class="fees-checkbox-box">
				<label for="checkbox-ns1"><input type="checkbox" id="checkbox-ns1" class="checkbox-none Einput-m"/>
			    <span class="checkbox-layer"></span><em class="checkbox-em">市场管理费</em></label>
			    <span class="span-select-down">
			    	<select class="guarantee-select">
			    		<option value="1">市场管理费(按可租面积20元/平方)</option>
			    	</select>
			    </span>
			</div>
			<div class="fees-checkbox-box">
				<label for="checkbox-ns2"><input type="checkbox" id="checkbox-ns2" class="checkbox-none Einput-m"/>
			    <span class="checkbox-layer"></span><em class="checkbox-em">物流管理费</em></label>
			    <span class="span-select-down">
			    	<select class="guarantee-select">
			    		<option value="1">物流管理费（按建筑面积1.5元/平方）</option>
			    	</select>
			    </span>
			</div>
			<div class="fees-checkbox-box">
				<label for="checkbox-ns3"><input type="checkbox" id="checkbox-ns3" class="checkbox-none Einput-m"/>
			    <span class="checkbox-layer"></span><em class="checkbox-em">其他</em></label>
			    <span class="span-select-down">
			    	<select class="guarantee-select">
			    		<option value="1"></option>
			    	</select>
			    </span>
			</div>
		</div>
		<div class="fees-name-box">
			<p class="fee-items ecm-tital">走表类费项</p>
			<div class="fees-checkbox-box">
				<label for="checkbox-ns4"><input type="checkbox" id="checkbox-ns4" class="checkbox-none Einput-m"/>
			    <span class="checkbox-layer"></span><em class="checkbox-em">电费</em></label>
			    <span class="span-select-down">
			    	<select class="guarantee-select">
			    		<option value="1">电费（按阶梯价）</option>
			    	</select>
			    </span>
			</div>
			<div class="fees-checkbox-box">
				<label for="checkbox-ns5"><input type="checkbox" id="checkbox-ns5" class="checkbox-none Einput-m"/>
			    <span class="checkbox-layer"></span><em class="checkbox-em">水费</em></label>
			    <span class="span-select-down">
			    	<select class="guarantee-select">
			    		<option value="1">水费（5元/吨）</option>
			    	</select>
			    </span>
			</div>
			<div class="fees-checkbox-box">
				<label for="checkbox-ns6"><input type="checkbox" id="checkbox-ns6" class="checkbox-none Einput-m"/>
			    <span class="checkbox-layer"></span><em class="checkbox-em">其他</em></label>
			    <span class="span-select-down">
			    	<select class="guarantee-select">
			    		<option value="1"></option>
			    	</select>
			    </span>
			</div>
		</div>
		
	 -->
	</div>
</div>
<!--结束-->       
 
            <!-- <div class="col-md-12 col-pd-13"> 
                 <div class="contract-info">
                 	 <div class="contract-title-block">
                 	 	<span class="contract-info-h3">其他费项约定</span>
                 	 	<ul class="charges-add-remove charges-paid-dw">
	                 	    <li id="agreementAdd" class="charges-add"><i class="fa fa-plus-square"></i>新增</li>
	                 	 	<li id="agreementRem" class="charges-remove"><i class="fa fa-trash"></i>删除</li>
	                 	</ul>
                 	 </div>
                 	 <div class="charges-max-box charges-max-other">
	                 	   <table id="econManageDT" class="charges-tab-style" cellspacing="0">
		         	 		 <tr class="charges-tab-bg">
							    <td class="charges-tab-bor">序号</td>
							    <td>租赁资源</td>
							    <td>费项类型</td>
							    <td>费项名称</td>
							    <td>计费标准</td>
							    <td>计算方法</td>
							    <td>金额</td>
							  </tr>
							  <tr class="charges-tab-blank">
							    <td class="num">1</td>
							    <td>农产品大市场一期-4栋D1入口AAA广告位</td>
							    <td>一次性收费</td>
							    <td>行业保证金</td>
							    <td>计费标准名称(单价+计费单位)</td>
							    <td>指定金额</td>
							    <td>1,244,550.00</td>
							  </tr>
							  <tr class="charges-tab-blank">
							    <td>2</td>
							    <td>农产品大市场一期-4栋D1</td>
							    <td>周期性收费</td>
							    <td>市场管理费</td>
							    <td>计费标准名称(单价+计费单位)</td>
							    <td>手工录入</td>
							    <td>13,412,345.00</td>
							  </tr>
							  <tr class="charges-tab-blank">
							    <td>3</td>
							    <td>农产品大市场一期-4栋D1-D3</td>
							    <td>走表类费项</td>
							    <td>市场管理费</td>
							    <td>计费标准名称(单价+计费单位)</td>
							    <td>按建筑面积</td>
							    <td>2,324,131,320.00</td>
							  </tr>
							  <tr class="charges-tab-blank">
							    <td>4</td>
							    <td>农产品大市场一期-4栋D1</td>
							    <td>一次性收费</td>
							    <td>电费</td>
							    <td>计费标准名称(单价+计费单位)</td>
							    <td>按可出租面积</td>
							    <td>13,412,345.00</td>
							  </tr>
							  <tr class="charges-tab-blank">
							    <td>5</td>
							    <td>农产品大市场一期-4栋D1都都都d</td>
							    <td>走表类费项</td>
							    <td>电费</td>
							    <td>计费标准名称(单价+计费单位)</td>
							    <td>指定金额</td>
							    <td>2,324,131,320.00</td>
							  </tr>
	                 	 	</table>
                 	    </div>
                 	
                 </div>
            </div> -->
            <!-- <div class="col-md-12 col-pd-13">
                 <div class="contract-info">
                 	<div class="contract-title-block">
                 	 	<span class="contract-info-h3">&nbsp;&nbsp;&nbsp;&nbsp;变更信息&nbsp;&nbsp;&nbsp;&nbsp;</span>
                 	</div>
                 	

                 	<div class="change-details">
                 		<div class="terms-box">
                 		 <p class="additional-terms">附加条款 :</p>
                 		  <textarea rows="5" cols="120" class="text-area"></textarea>   
                 		</div>
                 		<div class="terms-file">
                 		  <p class="additional-terms">合同附件 :&nbsp;&nbsp;<input id="File1" runat="server" type="file" /></p>
                 		  <textarea rows="5" cols="120" class="text-area"></textarea>
                 		</div>
                 	</div>
                 	
                 </div>
            </div>    -->
        
        <!--保存——发起——打印-->
        <div class="col-md-12 save-box" style="padding-left: 250px;">
        	<ul>
        		<li class="save-1"><span class="save-ct" id="save_btn">保存</span></li>
        		<!-- <li class="approval-2"><span class="approval-s">发起审批</span></li>
        		<li class="print-3"><span class="print-s"><i class="fa fa-print"></i>&nbsp;&nbsp;打印</span></li> -->
        	</ul>
        </div>
        
        
        
        <!--选择租赁资源开始-->
<!-- <div class="" id="agreementAddPop">
	<div class="floor-fees">
		<div class="fees-name-box">
			<p class="fees-p-rows">
				<span class="fees-name-left">费项名称</span>
				<span class="fees-name-right">资源</span>
			</p>
			<p class="fee-items ecm-tital">一次性费项</p>
			<div class="fees-checkbox-box">
				<label for="checkbox-ns"><input type="checkbox" id="checkbox-ns" class="checkbox-none Einput-m" checked="checked"/>
			    <span class="checkbox-layer"></span><em class="checkbox-em">行业保证金</em></label>
			    <span class="span-select-down">
			    	<select class="guarantee-select">
			    		<option value="1">行业保证金（指定金额 1000元）</option>
			    		<option value="2">行业保证金（指定金额 2000元）</option>
			    		<option value="3">行业保证金（指定金额 3000元）</option>
			    		<option value="4">行业保证金（指定金额 4000元）</option>
			    		<option value="5">行业保证金（指定金额 5000元）</option>
			    	</select>
			    </span>
			</div>
			<div class="fees-checkbox-box">
				<label for="checkbox-n1"><input type="checkbox" id="checkbox-n1" class="checkbox-none Einput-m"/>
			    <span class="checkbox-layer"></span><em class="checkbox-em">租赁押金</em></label>
			    <span class="span-select-down">
			    	<select class="guarantee-select">
			    		<option value="1">租赁押金(手工录入)</option>
			    	</select>
			    </span>
			</div>
			<div class="fees-checkbox-box">
				<label for="checkbox-n2"><input type="checkbox" id="checkbox-n2" class="checkbox-none Einput-m"/>
			    <span class="checkbox-layer"></span><em class="checkbox-em">其他</em></label>
			    <span class="span-select-down">
			    	<select class="guarantee-select">
			    		<option value="1"></option>
			    	</select>
			    </span>
			</div>
		</div>
		<div class="fees-name-box">
			<p class="fee-items ecm-tital">周期性费项</p>
			<div class="fees-checkbox-box">
				<label for="checkbox-ns1"><input type="checkbox" id="checkbox-ns1" class="checkbox-none Einput-m"/>
			    <span class="checkbox-layer"></span><em class="checkbox-em">市场管理费</em></label>
			    <span class="span-select-down">
			    	<select class="guarantee-select">
			    		<option value="1">市场管理费(按可租面积20元/平方)</option>
			    	</select>
			    </span>
			</div>
			<div class="fees-checkbox-box">
				<label for="checkbox-ns2"><input type="checkbox" id="checkbox-ns2" class="checkbox-none Einput-m"/>
			    <span class="checkbox-layer"></span><em class="checkbox-em">物流管理费</em></label>
			    <span class="span-select-down">
			    	<select class="guarantee-select">
			    		<option value="1">物流管理费（按建筑面积1.5元/平方）</option>
			    	</select>
			    </span>
			</div>
			<div class="fees-checkbox-box">
				<label for="checkbox-ns3"><input type="checkbox" id="checkbox-ns3" class="checkbox-none Einput-m"/>
			    <span class="checkbox-layer"></span><em class="checkbox-em">其他</em></label>
			    <span class="span-select-down">
			    	<select class="guarantee-select">
			    		<option value="1"></option>
			    	</select>
			    </span>
			</div>
		</div>
		<div class="fees-name-box">
			<p class="fee-items ecm-tital">走表类费项</p>
			<div class="fees-checkbox-box">
				<label for="checkbox-ns4"><input type="checkbox" id="checkbox-ns4" class="checkbox-none Einput-m"/>
			    <span class="checkbox-layer"></span><em class="checkbox-em">电费</em></label>
			    <span class="span-select-down">
			    	<select class="guarantee-select">
			    		<option value="1">电费（按阶梯价）</option>
			    	</select>
			    </span>
			</div>
			<div class="fees-checkbox-box">
				<label for="checkbox-ns5"><input type="checkbox" id="checkbox-ns5" class="checkbox-none Einput-m"/>
			    <span class="checkbox-layer"></span><em class="checkbox-em">水费</em></label>
			    <span class="span-select-down">
			    	<select class="guarantee-select">
			    		<option value="1">水费（5元/吨）</option>
			    	</select>
			    </span>
			</div>
			<div class="fees-checkbox-box">
				<label for="checkbox-ns6"><input type="checkbox" id="checkbox-ns6" class="checkbox-none Einput-m"/>
			    <span class="checkbox-layer"></span><em class="checkbox-em">其他</em></label>
			    <span class="span-select-down">
			    	<select class="guarantee-select">
			    		<option value="1"></option>
			    	</select>
			    </span>
			</div>
		</div>
		
	</div>
</div> -->
<!--选择租赁资源结束-->
<!--发起审批开始-->
<div id="popwintmpl">
	<div class="form-group">
		<label class="control-label col-md-3"><i class="payment-cycle-sp">*</i>审核结果:</label>
		<div class="col-md-6 text-left">
			<label for="radio-1-10">
               <input type="radio" id="radio-1-10" name="radio-1-cause" class="regular-rdi-layer" checked="checked" />
			   <span class="radio-mt-layer"></span><em class="radio-em">通过</em></label>
			<label for="radio-1-11">
				<input type="radio" id="radio-1-11" name="radio-1-cause" class="regular-rdi-layer"/>
			    <span class="radio-mt-layer"></span><em class="radio-em">驳回</em></label>
		</div>
	</div>
	<div class="col-md-6 col-md-boxs">	
		<div class="form-group-lt">经办人：&nbsp;&nbsp;郑小明</div>
		<div class="form-group-rt">审核时间：2016-09-01 9:00:00</div>
	</div>
	<div class="form-groups-tr">
		<label class="control-label col-md-3"><i class="payment-cycle-sp">*</i>审核意见:</label>
		<div class="col-md-6">
			<textarea rows="5" cols="50" class="text-area"></textarea>
		</div>
	</div>
</div>
<!--发起审批结束-->
 
</div>


      

       