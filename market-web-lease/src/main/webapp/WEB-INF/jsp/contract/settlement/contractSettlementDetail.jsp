<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="gd" uri="http://www.gdeng.cn/gd"%>
<div class="col-md-max contract">
	<div class="main-search">
		<div class="main-contracht-tp details-of-contract">合同结算管理</div>
	</div>
    
    <div id="settlementDetail">      
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
	<input type="hidden" name="approvalType" value="2">
	<input type="hidden" name="approvalMethod" value="2">
</form>

<div id="printSettingSelectPage" align="center"></div>

<div id="processList" align="center"></div>
<script type="text/javascript">
/**
 * 根据合同状态加载对应的操作按钮
 */
function loadBtn(workType, saveType){
	var btnStr = "";
	btnStr ='<li class="approval-2" id="gobackContractLi"><a href="index#contractSettlement"><span class="approval-s">返回</span></a></li>';
	if(saveType == 1 || saveType == "1"){
		btnStr += '<gd:btn btncode="BtnContractSettlementDetailSave"><li class="approval-2" id="saveSettlement"  style="display:none;"><a class="save-ct">保存</a></li></gd:btn>';
	}else if(saveType == 2 || saveType == "2"){
		btnStr += '<gd:btn btncode="BtnContractSettlementDetailEdit"><li class="approval-2" id="editSettlement"  style="display:none;"><a class="save-ct">保存</a></li></gd:btn>';
	}
	//审批模式  1  工作流审批   2 线下审批
	if(workType == 1 || workType == "1"){
		btnStr += '<gd:btn btncode="BtnContractSettlementDetChgApp"><li class="approval-2" id="launchApproveContractLi" style="display:none;"><a id="workflow_btn" onclick="" href="javascript:void(0)"><span class="approval-s">发起审批</span></a></li></gd:btn>';
	}else{
		btnStr += '<gd:btn btncode="BtnContractSettlementDetApprove"><li class="approval-2" id="approveContractLi" style="display:none;"><a data-toggle="modal" data-target="#myModalPop"><span class="approval-s">审核合同</span></a></li></gd:btn>';
	}
	btnStr += '<gd:btn btncode="BtnContractSettlementDetailPrint"><li class="print-3" id="printContractLi"><a class="print-s" href="javascript:;" id="btn-print"><i class="fa fa-print"></i>&nbsp;&nbsp;打印</a></li></gd:btn>';
		$("#btn_list").append(btnStr);
}
</script>