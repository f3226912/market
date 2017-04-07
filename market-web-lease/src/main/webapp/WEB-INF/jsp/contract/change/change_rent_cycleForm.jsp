<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!-- 按周期收费 -->
                      <div class="charges-max-box">
	                 	   <div class="charges-paid-bt">
	                 	 	  <span class="charges-paid-sz">租赁约定</span>
	                 	 	  <ul class="charges-add-remove">
	                 	 		<li id="addTr" class="charges-add"><i class="fa fa-plus-square"></i>新增</li>
	                 	 		<li id="removeTr" class="charges-remove"><i class="fa fa-trash"></i>删除</li>
	                 	 	  </ul>
	                 	   </div>
	                 	   <table class="charges-tab-style" cellspacing="0">
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
							  <c:forEach items="${leaseList}" varStatus="i" var="lease" >
							  <tr class="charges-tab-blank">
							    <td id="eq-add">${i.index}</td>
							    <td>
							    	<div class="date-icon border-none-right border-lt-right">
									   <input type="text" class="form-control start-date" placeholder="起始日期" value="${lease.startDay}">
									 
									</div>
							    </td>
							    <td>
							    	<div class="date-icon border-none-right border-lt-right">
									   <input type="text" class="form-control start-date" placeholder="截止日期" value="${lease.endDay}">
									</div>
							    </td>
							    <td>
									<select class="form-control wids">
									   <option value="0" selected="${lease.billingUnit==0?'selected':''}">元/月/平</option>
									   <option value=1" selected="${lease.billingUnit==1?'selected':''}">元/季/平</option>
									   <option value="2" selected="${lease.billingUnit==2?'selected':''}">元/半年/平</option>
									   <option value="3" selected="${lease.billingUnit==3?'selected':''}">元/年/平</option>
									</select>
							    </td>
							    <td>${lease.unitPrice}</td>
							    <td><input class="rents" type="text"/></td>
							    <td>1,244</td>
							  </tr>
							  </c:forEach>
							  </tbody>
	                 	 	</table>
                 	    </div>
                 	    <div class="charges-max-box">
	                 	   <div class="charges-paid-bt">
	                 	 	  <span class="charges-paid-sz">免租约定</span>
	                 	 	  <ul class="charges-add-remove">
	                 	 		<li id="leaseDad" class="charges-add"><i class="fa fa-plus-square"></i>新增</li>
	                 	 		<li id="leaseRed" class="charges-remove"><i class="fa fa-trash"></i>删除</li>
	                 	 	  </ul>
	                 	   </div>
	                 	   <table class="charges-tab-style" cellspacing="0">
	                 	   	<tbody class="leaseDs">
		         	 		 <tr class="charges-tab-bg">
							    <td class="charges-tab-bor">序号</td>
							    <td>起始日期</td>
							    <td>免租月数</td>
							    <td>免租天数</td>
							    <td>截止日期</td>
							  </tr>
							  <tr class="charges-tab-blank">
							    <td>1</td>
							    <td>
							      <div class="date-icon border-none-right">
									<input type="text" class="form-control start-date start-cet" placeholder="起始日期">
								  </div>
							    </td>
							    <td><input class="rents" type="text"/></td>
							    <td><input class="rents" type="text"/></td>
							    <td>2016-10-15</td>
							  </tr>
							  </tbody>
	                 	 	</table>
                 	    </div>
                 	    <div class="charges-max-box">
	                 	   <div class="charges-paid-btm">
	                 	 	  <span class="charges-paid-sz">缴费约定</span>
	                 	   </div>
	                 	 	<div class="payment-cycle">
	                 	 	  	<span><em class="payment-cycle-sp">*</em><b class="col-ft-bold">缴费周期：</b></span>
							    <label for="radio-1-1"><input type="radio" id="radio-1-1" name="radio-1-set" class="regular-radio" checked/>
							    <span class="radio-mt10"></span><em class="radio-em">月</em></label>
							    <label for="radio-1-2"><input type="radio" id="radio-1-2" name="radio-1-set" class="regular-radio" />
								<span class="radio-mt10"></span><em class="radio-em">季度</em></label>
								<label for="radio-1-3"><input type="radio" id="radio-1-3" name="radio-1-set" class="regular-radio" />
								<span class="radio-mt10"></span><em class="radio-em">半年</em></label>
								<label for="radio-1-4"><input type="radio" id="radio-1-4" name="radio-1-set" class="regular-radio" />
								<span class="radio-mt10"></span><em class="radio-em">年</em></label>
	                 	 	 </div>
	                 	 	 <div class="payment-cycle">
	                 	 	 	<span><em class="payment-cycle-sp">*</em><b class="col-ft-bold">缴费日期：</b></span>
                        		<label for="radio-1-5"><input type="radio" id="radio-1-5" name="payment-date" class="regular-radios" checked>
								<span class="radio-mtd"></span><em class="radio-ems">缴费期初</em><span class="day-pd60"><input type="text" class="radio-em-day">天</span></label>
								<label for="radio-1-6"><input type="radio" id="radio-1-6" name="payment-date" class="regular-radios"/>
								<span class="radio-mtd"></span><em class="radio-ems">缴费期末</em><span class="day-pd60"><input type="text" class="radio-em-day">天<span></label>
                                <label for="radio-1-7"><input type="radio" id="radio-1-7" name="payment-date" class="regular-radios"/>
								<span class="radio-mtd"></span><em class="radio-ems">下一个缴费期初</em><span class="day-pd60"><input type="text" class="radio-em-day">天<span></label>	               	 	                  	 	 	                 	 	 
	                 	 	 </div>
                        </div>		  