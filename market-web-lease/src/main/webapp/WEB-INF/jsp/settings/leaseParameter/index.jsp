<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<style>
.contract .costDefinition .zuyueul li{
overflow:inherit;
position:relative;
}
.zuyueul label.error{
	position:absolute;
	top:18px;
	left:120px;
}
</style>
<section class="contract">
	<div class="header">参数设置</div>
	<div class="costDefinition">
		<div class="col-sm-12">
			<section class="panel">
				<div class="contract-title-block">
					<span class="contract-info-h3">&nbsp;&nbsp;&nbsp;&nbsp;租约管理参数设置&nbsp;&nbsp;&nbsp;&nbsp;</span>
				</div>
				<form id="form-edit" action="" class="form-horizontal clearfix">
                <div class="zuyueheader">合同相关提醒</div>
                <ul class="zuyueul">
                	<li class="clearfix">
                		<div class="edivf">合同到期前:</div>
                		<div><input id="deadlineTime" name="deadlineTime" type="text"/>月,无租赁合同预警;</div>
                	</li>
                	<li class="clearfix">
                		<div class="edivf">合同收取租金提前:</div>
                		<div><input id="rentTime" name="rentTime"  type="text"/>天提醒;</div>
                	</li>
                </ul>
                <div class="zuyueheader">审批模式</div>
                <ul class="zuyueul zuyueul2">
                	<li>
                		<div>合同审批模式:</div>
                		<label for="radio-1-1">
                		<input type="radio" id="radio-1-1" name="checkWorkType" value="1" class="regular-radio" checked="">
										<span class="radio-mt10"></span><em class="radio-em">工作流审批</em></label>
					 	<label for="radio-1-2">
						<input type="radio" id="radio-1-2" name="checkWorkType" value="2" class="regular-radio">
										<span class="radio-mt10"></span><em class="radio-em">线下审批</em></label>
                	</li>
                	<li>
                		<div>合同变更审批模式:</div>
                		<label for="radio-2-1"><input type="radio" id="radio-2-1" name="changeWorkType" value="1" class="regular-radio" checked="">
										<span class="radio-mt10"></span><em class="radio-em">工作流审批</em></label>
									  <label for="radio-2-2"><input type="radio" id="radio-2-2" name="changeWorkType" value="2" class="regular-radio">
										<span class="radio-mt10"></span><em class="radio-em">线下审批</em></label>
                	</li>
                	<li>
                		<div>合同结算审批模式:</div>
                		<label for="radio-3-1"><input type="radio" id="radio-3-1" name="settlementWorkType" value="1"  class="regular-radio" checked="">
										<span class="radio-mt10"></span><em class="radio-em">工作流审批</em></label>
									  <label for="radio-3-2"><input type="radio" id="radio-3-2" name="settlementWorkType" value="2"  class="regular-radio">
										<span class="radio-mt10"></span><em class="radio-em">线下审批</em></label>
                	</li>
                </ul>
						
						<input type="hidden"  id="marketId" name="marketId" />
						<input type="hidden" id="orgId" name="orgId" />
					
						
						
						
						<div style="text-align:center;">
						
						<!-- index#leaseParameter -->
						<button  type="button" class="btn btn-ash btn-big" onclick="javascript:window.location='index#parameter'" >取消</button>
						<button id="saveForm" type="button" class="btn btn-danger btn-big">保存</button>
						</div>
						
					</div>
				</form>
			</section>
		</div>
	</div>
</section>

