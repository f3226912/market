<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" isELIgnored="false"%>
<%@ include file="/WEB-INF/jsp/pub/tags.inc" %>
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
                 	
                 	<!--关闭——确认-->
			        <div class="col-md-12 save-box">
			        	<ul class="workflow_lis">
			        		<li class="save-1"><button class="workflow_btn" type="button" id="closeWinBtn">关闭</button></li>
			        		<!--<li class="approval-2"><button class="workflow_cfm">确认</button></li>-->
			        	</ul>
			        </div>
			       
			        <!--此处填写弹窗内容结束-->
                 </div>
          </div>   
        
</div>