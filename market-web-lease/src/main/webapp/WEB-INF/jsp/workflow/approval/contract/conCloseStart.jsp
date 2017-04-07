<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<div class="col-md-max contract">
          <div class="main-search">
               <div class="main-contracht-tp details-of-contract" style="text-align:center">合同结算审批表单</div>
           </div>
           <!--插入中间主题内容-->
           <div id="baseInfoDetail"></div>
           <!--插入中间主题内容结束-->
            <!--合同审批流程-->
            <div class="col-md-12 col-pd-13">
                 <div class="workflow contract-workflow">
                 	<form id="startProcessForm" method="post" novalidate class="form-horizontal clearfix cmxform">
                 		<input name="orderNo" type="hidden" value="${requestScope.orderNo }"  />
                 		<input name="contractMainId" type="hidden" value="${requestScope.contractMainId }"  />
                 		<input name="processId" type="hidden" value="${requestScope.processId }"  />
	                 	<div class="approva_ds">
	                 		<div class="work-recording">
	                 			<h3 class="recording_jl">发起</h3>
	                 		</div>
	                 		<div class="approval_te">
	                 			<div class="approval_tep clearfloat">
	                 				<p class="approval_lf">
	                 					<span class="approval_sp">流程名称：</span>
	                 					<span class="approval_col">${requestScope.orderNo }</span>
	                 				</p>
	                 				<p class="approval_rt">
	                 					<span class="approval_sp"></span>
	                 					<span class="approval_col"></span>
	                 				</p>
	                 			</div>
	                 			
		                        <div class="approval-box">
	                 		       <p class="workflow-terms"><em class="payment-cycle-sp">*</em>发起说明：</p>
	                 		       <textarea name="opinion" rows="5" cols="120" class="text-area" maxlength="120"></textarea>   
	                 		   </div>
	                 		  
	                 		</div>
	                 	</div>
	                 	<!--关闭——确认-->
				        <div class="col-md-12 save-box">
				        	<ul class="workflow_lis">
				        		<li class="save-1"><button class="workflow_btn" type="button" id="closeWinBtn">关闭</button></li>
				        		<li class="approval-2"><button class="workflow_cfm" type="submit">发起</button></li>
				        	</ul>
				        </div>
			        </form>
                </div>
          </div>   
        
</div>

	