<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" isELIgnored="false"%>
<%@ include file="/WEB-INF/jsp/pub/tags.inc" %>
<div class="col-md-max contract">
          <div class="main-search">
               <div class="main-contracht-tp details-of-contract" style="text-align:center">合同审批表单</div>
           </div>
           <!--插入中间主题内容-->
           <div id="baseInfoDetail"></div>
           <!--插入中间主题内容结束-->
            <!--合同审批流程-->
            <div class="col-md-12 col-pd-13">
                 <div class="workflow contract-workflow">
                  	<form id="processApprovalForm" method="post" novalidate class="form-horizontal clearfix cmxform">
	                 	<input name="taskId" type="hidden" value="${requestScope.approvalBaseInfo.taskId }" />
	                 	<input id="contractMainId" name="contractMainId" type="hidden" value="${requestScope.contractMainId}" />
	                 	<div class="contract-workApproval">
	                 		<div class="work-recording">
	                 			<h3 class="recording_jl">审批记录</h3>
	                 		</div>
	                 		<div class="recording_yw clearfloat">
	                 			<dl class="recording_dl">
								    <c:forEach items="${requestScope.approFlows }" var="approFlow">
		                			   <dt class="recording_unit">
									   		<i class="dots1"></i>${approFlow.approFlowInfos[0].taskDisplayName }
									   </dt>
									   <c:forEach items="${approFlow.approFlowInfos }" var="flowInfo">
										   <dd class="reasonable_ls">
										   		<span class="recording_lt" title="${flowInfo.opinion }">${flowInfo.opinion }</span>
										   		<span class="recording_rt">${flowInfo.operateBaseInfo } ${flowInfo.finshTime }</span>
										   </dd>
									   </c:forEach>
	                 			     </c:forEach>
								</dl>
	                 		</div>
	                 	</div>
	                 	<div class="approva_ds">
	                 		<div class="work-recording">
	                 			<h3 class="recording_jl">审批</h3>
	                 		</div>
	                 		<div class="approval_te">
	                 			<div class="approval_tep clearfloat">
	                 				<p class="approval_lf">
	                 					<span class="approval_sp">流程名称：</span>
	                 					<span class="approval_col">${requestScope.approvalBaseInfo.orderNo }</span>
	                 				</p>
	                 				<p class="approval_rt">
	                 					<span class="approval_sp"></span>
	                 					<span class="approval_col"></span>
	                 				</p>
	                 			</div>
	                 			<div class="approval_tep clearfloat">
	                 				<p class="approval_lf">
	                 					<span class="approval_sp">当前责任人：</span>
	                 					<span class="approval_col">${requestScope.approvalBaseInfo.taskActors }</span>
	                 				</p>
	                 				<p class="approval_rt">
	                 					<span class="approval_sp">发起人：</span>
	                 					<span class="approval_col">${requestScope.approvalBaseInfo.creatorName }</span>
	                 				</p>
	                 			</div>
	                 			<div class="approval_tep clearfloat">
	                 				<p class="approval_lf">
	                 					<span class="approval_sp">当前步骤名称：</span>
	                 					<span class="approval_col">${requestScope.approvalBaseInfo.taskDisplayName }</span>
	                 				</p>
	                 				<p class="approval_rt">
	                 					<span class="approval_sp">当前步骤类型：</span>
	                 					<span class="approval_col">${requestScope.approvalBaseInfo.taskPerformType }</span>
	                 				</p>
	                 			</div>
	                 			<div class="workflow-cycle">
		                           <span class="workflow-ltsp"><em class="payment-cycle-sp">*</em><b class="col-ft-bold" style="font-weight: normal;">审批结果：</b></span>
	                               <label for="radio-rentsA"><input type="radio" name="result" value="0" id="radio-rentsA" name="radio-002-cause" class="regular-rdi-rents" checked="checked" />
							       <span class="radio-mt-rents"></span><em class="radio-em">通过</em></label>
							       <label for="radio-rentsB"><input type="radio" name="result" value="2" id="radio-rentsB" name="radio-002-cause" class="regular-rdi-rents"/>
							       <span class="radio-mt-rents"></span><em class="radio-em">驳回</em></label>	
		                        </div>
		                        <div class="approval-box">
	                 		       <p class="workflow-terms"><em class="payment-cycle-sp">*</em>审批意见：</p>
	                 		       <textarea name="opinion" rows="5" cols="120" class="text-area" maxlength="120"></textarea>   
	                 		   </div>
	                 		   <div class="approval-box">
	                 		       <label class="workflow-lb">消息抄送：</label>
	                 		       <input id="msgReceivers" name="msgReceivers" type="hidden" />
	                 		       <input id="msgReceiversName" class="approval-ipt" type="text" readonly="readonly"/>   
	                 		   </div>
	                 		</div>
	                 	</div>
	                 	<!--关闭——确认-->
				        <div class="col-md-12 save-box">
				        	<ul class="workflow_lis">
				        		<li class="save-1"><button class="workflow_btn" type="button" id="closeWinBtn">关闭</button></li>
				        		<li class="approval-2"><button class="workflow_cfm" type="submit">确认</button></li>
				        	</ul>
				        </div>
			        </form>
                 </div>
          </div>   
        
</div>
