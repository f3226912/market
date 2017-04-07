<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<style>
#contractFile1-queue{opacity:1!important; filter:Alpha(opacity=1)!important;}
.uploadify ,.terms_no{opacity:0; filter:Alpha(opacity=0);}
#uploadDiv{border:1px solid #ccc; padding-top:3px; margin-top:-15px; width:877px; height:120px}
</style>
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
                 	 	<span class="contract-info-h3">附加条款&附件</span>
                 	</div>
                 	

                 	<div class="change-details" style="padding-bottom:0;">
                 		<div class="terms-box">
                 		 <p class="additional-terms">附加条款 :</p>
                 		  <textarea rows="5" cols="120" id="additionalTerms" name="additionalTerms" class="text-area" maxlength="300"></textarea>   
                 		</div>
                 		<div class="terms-file" style="position:relative; top:-30px;">
                 		  <p style="position:relative; top:50px; left:0;">合同附件 :&nbsp;&nbsp;<b style="color:#165ae4">上传附件</b></p>
                 		  <p class="additional-terms"><span class="terms_no">合同附件</span><input id="contractFile1" runat="server" type="file" /></p>
                 		  <div id="uploadDiv">
	                 		 
			 				<!--  <input type="text"  id="templateUrl1" value="dddd" name="templateUrl1"/>
			 				 
			 				http://gudeng-test.oss-cn-shenzhen.aliyuncs.com/2016/10/24/3882561b80704deda16ac129422e0e68.jpg 
			 				
			 				<input type="hidden" id="templateFile1" name="templateFile1"/>	-->
                 		  </div>
                 		</div>
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
 