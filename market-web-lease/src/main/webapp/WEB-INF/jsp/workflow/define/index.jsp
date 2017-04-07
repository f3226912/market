<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page isELIgnored="false"%>
<%@ taglib prefix="gd" uri="http://www.gdeng.cn/gd"%>
<%@ include file="/WEB-INF/jsp/pub/constants.inc" %>
<!DOCTYPE HTML>
<html>
<div class="col-md-max contract workflow">
	<div class="main-search">
		<div class="main-contracht-tp" style="overflow: hidden;">
			
				<span class="motion-of-contract">流程列表</span>
			
			<div class="input-group m-bot15" style="float:right;">
				<div class="export-s">
				<gd:btn btncode="BtnWfProcessAdd">
				<span style="cursor:pointer" id="addBtn" onClick="javascript:window.open('wfProcessDefine/jumpToProcessAdd');" class="add-export"><i class="fa fa-plus-square fa-cor"></i>新增</span></gd:btn>
				<gd:btn btncode="BtnWfProcessExport">
				<span style="cursor:pointer" class="remove-export" id="exportData"><i class="fa fa-download"></i> 导出</span></gd:btn>
				</div>
			</div>

		</div>
		<div class="main-ipt-types">
			<form class="form-input clearfix">
				<div class="form-group">
					<span class="fl" style="line-height:32px;margin-right:10px;display:block;">流程名称:</span>
					<input id="displayName" type="text" class="form-control wid150" style="display:block;float:left;" placeholder="请输入流程模板名称">
				</div>
				<div class="form-group">
					<span class="fl" style="line-height:32px;margin-right:10px;">业务对象:</span>
					<select class="form-control wid150" style="float:left;" id="busType">
						<option value="">全部</option>
					</select>
				</div>
				<div class="form-group">
					<span class="fl" style="line-height:32px;margin-right:10px;">启用状态:</span>
					<select class="form-control wid150" style="float:left;" id="state">
						<option value="">全部</option>
						<option value="1">启用</option>
						<option value="0">禁用</option>
					</select>
				</div>
				<div class="form-group">
					<gd:btn btncode="BtnWfProcessQuery"><button id="query" class="btn btn-danger" type="button">搜索</button></gd:btn>
				</div>
			</form>
		</div>
	</div>
	<!-- <div class="col-md-12 col-pd-10"> -->
		<!--<div class="more-search">更多搜索<span class="mores">+</span></div>-->
	<!-- </div> -->
	<div class="wrp-box" style="margin-top:30px;">
		<!-- page start-->
		<div style="width: 96%;margin: 0 auto;">
			<div class="adv-table editable-table ">
				<div class="space15"></div>
				<table class="table table-striped table-hover t-custom" id="tb">
					<thead>
						<tr class="ml-ct">
							<th>序号</th>
							<th>流程模板名称</th>
							<th>业务对象</th>
							<th>启用状态</th>
							<th>所属市场</th>
							<th>修改人</th>
							<th class="center">修改时间</th>
						</tr>
					</thead>
					<tbody id="wfProcessBody">
						
					</tbody>
				</table>
				 <!-- 分页组件 -->
                <div id="pagebar"></div>
			</div>
		</div>
	</div>

	<!-- page end-->
<form id="form-addProcess"  class="form-horizontal clearfix">
	<div class="modal fade" id="box">
	  <div class="modal-dialog">
	    <div class="modal-content">
	      <div class="modal-header">
	        <h4 class="modal-title white">基本信息</h4>
	        <h4 class="modal-title">步骤定义</h4>
	      </div>
	      <div class="modal-body overflow-h etab1">
	      	<div class="col-md-6 fees-box-zj">
                <span class="list-sp"><em class="inf-em">*</em>流程模板名称：</span>
                <span class="list-sp-textdd">
                	<input class="long" maxlength="60" type="text" id="displayNameAdd" name="displayNameAdd" placeholder="请输入流程模板名称(最多60个中文)">
                </span>
             </div>
	        <div class="col-md-6">
                <span class="list-sp"><em class="inf-em">*</em>所属市场：</span>
                <span class="list-sp-textdd">
                <select name="orgIdAdd" id="orgIdAdd" class="list-sp-text">
				</select>
                </span>
             </div>
             <div class="col-md-6">
                <span class="list-sp"><em class="inf-em"></em>业务对象：</span>
                <span class="list-sp-textdd">
                <select class="list-sp-text" id="busTypeAdd" name="busTypeAdd"></select>
                </span>
             </div>
             <div class="col-md-6">
                <span class="list-sp"><em class="inf-em">*</em>流程监控人：</span>
                <span class="list-sp-textdd" id="changeUser">
                	<input id="monitorIdAdd" name="monitorIdAdd" type="hidden">
                	<input type="text" id="monitorDescAdd" name="monitorDescAdd" readonly>
                </span>
             </div>
             <div class="col-md-6">
                	<span class="list-sp"><em class="payment-cycle-sp"></em>启动状态：</span>
				    <label for="state1" id="cycle_charge">
					    <input type="radio" name="stateAdd" id="state1" value="1" class="regular-radio" checked>
					    <span class="radio-mt10"></span><em class="radio-em-fees">启用</em>
				    </label>
				    <label for="state0" id="cycle_charge">
				    <input type="radio" name="stateAdd" value="0" id="state0" class="regular-radio">
				    <span class="radio-mt10"></span><em class="radio-em-fees">禁用</em></label>
             </div>
             <div class="col-md-6 fees-box-zj veryHeight">
                	<span class="list-sp" style="float:left;margin-top:5px;"><em class="payment-cycle-sp"></em>流程说明：</span>
                	<textarea maxlength="200" id="processDescAdd" name ="processDescAdd" placeholder="请输入流程说明(最多200个中文)"></textarea>
             </div>
	      </div>
	      <div class="modal-footer">
	        <button type="button" id="close" class="btn btn-default" data-dismiss="modal">取消</button>
	        <gd:btn btncode="BtnWfProcessEditSave"><button type="button" id="save" class="btn btn-default bule">保存</button></gd:btn>
	        <gd:btn btncode="BtnWfProcessEditDelete"><button type="button" id="deleteBtn" class="btn btn-default green">刪除</button></gd:btn>
	      </div>
	    </div>
	  </div>
	</div>
	</form>
</div>
<script id="wfProcessScript" type="text/x-jquery-tmpl">
	{{each(i, wfProcess) wfProcessList}}

		<tr class="ml-ct" onClick="javascript:window.open('wfProcessDefine/jumpToProcessEdit?id={{= wfProcess.id}}');" title="双击进行编辑">
			<td>{{= i+1}}</td>
			<td>{{= wfProcess.displayName}}</td>
			<td>{{= wfProcess.busTypeDesc}}</td>
			<td>{{= wfProcess.stateDesc}}</td>
			<td>{{= wfProcess.orgDesc}}</td>
			<td>{{= wfProcess.modificator}}</td>
			<td>{{= wfProcess.modifiedTime}}</td>
    	</tr>
	{{/each}}

</script>

<script id="noDataScript" type="text/x-jquery-tmpl">
		<tr class="ml-ct">
			<td colspan="7" class="ob_font">抱歉！！！未能查询到符合条件的数据!</td>
    	</tr>

</script>
</html>
