<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="gd" uri="http://www.gdeng.cn/gd"%>
<div class="contract col-md-max">
    <%-- <%@ include file="../baseInfoDetail_a.jsp"%> --%>
    <div class="main-search">
		<div class="main-contracht-tp details-of-contract">合同管理</div>
		  <div class="witchingMarket">
                    <p class="select_Mar">查看历史版本<i class="fa fa-sort-down down_ft"></i></p>
                    <i class="fa fa-sort-up font_max"></i>
                    <div class="scrllo_ht">
	                    <ul class="market_lis">
	                   <%--   <c:forEach items="${contractDTO.changeList}" varStatus="i" var="change" >
	                      <li class="market_lt"><a href="javascript:(function(){Route.params={id:${change.contractId};location.href='index#contractManageDetail';})();">${change.createTime}${change.contractNo}</a></li>
	                      </c:forEach> --%>
	                    </ul>
                    </div>
               </div>
	</div>
    <div id="baseInfoDetail_a">
        
    </div>
    
    <div class="col-md-12 save-box">
       	<ul id="btn_list" style="margin-left:65px;">
       	</ul>
    </div>
</div>
<form id="settlementAudit">
	<div id="myModalPop"></div>
	<input type="hidden" id="contractId" name="contractId">
	<input type="hidden" id="contractNo" name="contractNo">
	<input type="hidden" name="approvalType" value="0">
	<input type="hidden" name="approvalMethod" value="2">
</form>
<div id="printSettingSelectPage" align="center"></div>
<div id="processList" align="center"></div>

<script type="text/javascript">
/**
 * 根据合同状态加载对应的操作按钮
 */
function loadBtn(workType){
	var btnStr = "";
	btnStr ='<li class="approval-2" id="gobackContractLi"><a href="index#contractManage"><span class="approval-s">返回</span></a></li>'
       		+'<gd:btn btncode="BtnContractManageDetailCopy"><li class="approval-2" id="copyContractLi" style="display:none;"><a id="copy_btn" href="javascript:void(0)"><span class="approval-s">合同复制</span></a></li></gd:btn>'
       		+'<gd:btn btncode="BtnContractManageDetailChange"><li class="approval-2" id="changeContractLi" style="display:none;"><a id="change_btn" href="javascript:void(0)"><span class="approval-s">合同变更</span></a></li></gd:btn>'
       		+'<gd:btn btncode="BtnContractManageDetailEdit"><li class="approval-2" id="updateContractLi" style="display:none;"><a id="edit_btn" href="javascript:void(0)"><span class="approval-s">合同修改</span></a></li></gd:btn>'
	//审批模式  1  工作流审批   2 线下审批
	if(workType == 1 || workType == "1"){
		btnStr += '<gd:btn btncode="BtnContractManageDetailChgApp"><li class="approval-2" id="launchApproveContractLi" style="display:none;"><a id="workflow_btn" onclick="" href="javascript:void(0)"><span class="approval-s">发起审批</span></a></li></gd:btn>';
	}else{
		btnStr += '<gd:btn btncode="BtnContractManageDetailApprove"><li class="approval-2" id="approveContractLi" style="display:none;"><a data-toggle="modal" data-target="#myModalPop"><span class="approval-s">审核合同</span></a></li></gd:btn>';
	}
	btnStr += '<gd:btn btncode="BtnContractManageDetailSettlement"><li class="approval-2" id="statementsContractLi" style="display:none;"><a id="statement_btn" href="javascript:void(0)"><span class="approval-s">合同结算</span></a></li></gd:btn>';
	btnStr += '<gd:btn btncode="BtnContractManageDetailPrint"><li class="print-3" id="printContractLi"><a class="print-s" href="javascript:;" id="btn-print"><i class="fa fa-print"></i>&nbsp;&nbsp;打印</a></li></gd:btn>';
		$("#btn_list").append(btnStr);
}
</script>