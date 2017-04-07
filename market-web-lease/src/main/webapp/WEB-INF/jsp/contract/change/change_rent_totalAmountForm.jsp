<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!-- 按约定总金额收费 -->
<div class="contract-rents">             
	                <div class="amount-ipt2">
	                	<p class="amount-ret"><em class="payment-cycle-sp">*</em>合同总金额：&nbsp;&nbsp;<input class="total-amount" type="text"/><span class="sp-lt-amount">元</span></p>
	                </div>
                 	<div class="total_amount">
                 	     <div class="rents_agreement clearfloat">
                 	     	<p class="rents_agreement_p">缴费约定</p>
                 	 	    <ul class="charges-add-remove charges-paid-dw">
	                 	        <li id="rentsAdd" class="charges-add"><i class="fa fa-plus-square"></i>新增</li>
	                 	 	    <li id="rentsRem" class="charges-remove"><i class="fa fa-trash"></i>删除</li>
	                 	    </ul>
                 	     </div>
                 		<table id="econManageDT" class="charges-tab-style" cellspacing="0">
		         	 		 <tr class="charges-tab-bg">
							    <td class="charges-tab-bor">序号</td>
							    <td>缴费期限</td>
							    <td>缴费金额</td>
							  </tr>
							  <tr class="charges-tab-blank">
							    <td class="num">1</td>
							    <td>
							      <div class="date-icon border-none-right">
									<input type="text" class="form-control start-date start-cet" placeholder="起始日期">
									<i class="fa fa-calendar"></i>
								  </div>
							    </td>
							    <td><input class="rents" type="text"/></td>
							  </tr>
							  <tr class="charges-tab-blank">
							    <td class="num">1</td>
							    <td>
							      <div class="date-icon border-none-right">
									<input type="text" class="form-control start-date start-cet" placeholder="起始日期">
									<i class="fa fa-calendar"></i>
								  </div>
							    </td>
							    <td><input class="rents" type="text"/></td>
							  </tr>
							 
	                 	 	</table>
                 	</div>               	
</div>