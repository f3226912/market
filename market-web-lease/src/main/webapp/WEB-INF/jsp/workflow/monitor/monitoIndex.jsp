<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ page isELIgnored="true"%>
<%@ taglib prefix="gd" uri="http://www.gdeng.cn/gd"%>
<!DOCTYPE HTML>
<html>
<div class="col-md-max contract">
	<div class="main-contracht-tp" style="overflow: hidden;">
		<span class="detailsLc motion-of-contract">流程列表</span>
		<div class="input-group m-bot15" style="float:right;">
			<div class="export-s">
			<gd:btn btncode="BtnWfMonitoReset">
				<span class="add-export" id="taskActorChange" style="cursor:pointer"><i class="fa fa-pencil-square-o"></i>重设责任人</span>
			</gd:btn>
			<gd:btn btncode="BtnWfMonitoTerminate">
				<span class="add-export" id="terminate" style="cursor:pointer"><i class="fa fa-trash-o"></i>作废流程</span>
			</gd:btn>
			<gd:btn btncode="BtnWfMonitoExport">
				<span class="add-export" id="exportData" style="cursor:pointer"><i class="fa fa-download"></i>导出</span>
			</gd:btn>
			</div>
		</div>
	</div>
	<div class="col-md-12 col-pd-box" style="margin-top:0;margin-bottom:10px;">
		<span class="col-name-lc"><lebel class="col-lab">流程名称：</lebel>
			<input class="main-ipt" id="orderNo" placeholder="请输入流程名称" /></span> <span
			class="business_obj"><lebel class="business_obj_font">业务对象：</lebel>
			<select class="business_sel" id="busType">
				<option value="">全部</option>
		</select> </span> <span class="business_ser" id="query">
		<gd:btn btncode="BtnWfMonitoQuery"><button class="business_btn">搜索</button></gd:btn></span>
	</div>
	<!--插入中间主题内容-->
	<!--插入中间主题内容结束-->
	<!--合同审批流程-->
	<div class="col-md-12 col-pd-13">
		<div class="workflow process_monitoring">
			<table id="econManageDT" class="charges-tab-style" cellspacing="0">
				<tr class="charges-tab-bg">
					<td class="charges-tab-bor">序号</td>
					<td>流程名称</td>
					<td>业务对象</td>
					<td>当前步骤</td>
				</tr>
				<tbody id="monitoBody"></tbody>
			</table>
			<!-- 分页组件 -->
			<div id="pagebar"></div>

			
			<!--流程监控-选择责任人-->

			<!--流程监控-作废流程-->
			<form id="terminateForm" 
				class="form-horizontal clearfix">
				<div class="modal fade" id="box">
					<div class="modal-dialog">
						<div class="modal-content" style="width: 900px">
							<div class="modal-header">
								<h4 class="modal-title">流程作废</h4>
							</div>
							<div id="remObsolete">
								<div class="obsolet-wy">
									<span class="obsolet_sp"><i class="obsolet_i">*</i>作废原因：</span>
									<textarea class="obsolet_area" maxlength="360" id="remark" name="remark" placeholder="请输入作废原因(最多360个中文)"></textarea>
									<p class="ob_font">提示：流程作废后，系统自动发送消息给发起人、当前步骤责任人</p>
								</div>
							</div>
							<div class="modal-footer">
								<button type="button" id="close" class="btn btn-default"
									data-dismiss="modal">取消</button>
								<button type="submit" class="btn btn-default bule">保存</button>
							</div>
						</div>
					</div>
				</div>
			</form>
			<!--流程监控-作废流程-->
		</div>
	</div>
</div>
<!-- Modal -->
<!--流程监控-选择责任人-->
<div class="modal fade" id="selectNt" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
  <div class="modal-dialog" style="width:900px">
    <div class="modal-content">
      <div class="modal-header">
        <button type="button" class="close" data-dismiss="modal"><span aria-hidden="true">&times;</span><span class="sr-only">Close</span></button>
        <h4 class="modal-title" id="myModalLabel">重置责任人</h4>
      </div>
      <div class="modal-body" id="selectNtBody">
 	  </div>
      <div class="modal-footer">
        <button type="button" class="btn btn-default" data-dismiss="modal">取消</button>
        <button type="submit" id="resetsSave" class="btn btn-primary">保存</button>
      </div>
    </div>
  </div>
</div>
<script id="monitoScript" type="text/x-jquery-tmpl">
	{{each(i, wfOrder) wfOrderList}}
		{{if i%2==0}}
			<tr class="charges-tab-n">
			<td class="num">{{= i+1}}</td>
			<td>{{= wfOrder.orderNo}}</td>
			<td>{{= wfOrder.busType}}</td>
			<td>{{= wfOrder.wfTaskDTO.displayName}}</td>
    	</tr>
		{{/if}}
		{{if i%2!=0}}
			<tr class="charges-tab-blank">
			<td class="num">{{= i+1}}</td>
			<td>{{= wfOrder.orderNo}}</td>
			<td>{{= wfOrder.busType}}</td>
			<td>{{= wfOrder.wfTaskDTO.displayName}}</td>
    	</tr>
		{{/if}}
	{{/each}}

</script>
<script id="noDataScript" type="text/x-jquery-tmpl">
		<th class="charges-tab-blank">
			<td colspan="4" class="ob_font">抱歉！！！未能查询到符合条件的数据!</td>
    	</th>

</script>
<script id="selectNtScript" type="text/x-jquery-tmpl">
	{{each(i, task) taskActorList}}
		<div class="select_box clearfloat">
			<p class="lf_p" name="oldActor" data-id="{{= task.actorId}}">原责任人： &nbsp;&nbsp;&nbsp;{{= task.actorDesc}}</p>
			<p class="rt_p">新责任人： &nbsp;&nbsp;&nbsp;
			<input class="form-control select-ipt" type="text" data-oid="{{= task.actorId}}" data-oname="{{= task.actorDesc}}" data-task="{{= task.taskId}}"
			 readonly="readonly" placeholder="选择"/>
			</p>
		</div>
	{{/each}}

</script>

<script id="treeDemosScript" type="text/x-jquery-tmpl">
	{{each(i, user) userList}}
		<li class="listName"><span class="list_sp">{{= user.name}}</span><span class="list_nm">{{= user.code}}</span></li>
	{{/each}}

</script>
</html>
