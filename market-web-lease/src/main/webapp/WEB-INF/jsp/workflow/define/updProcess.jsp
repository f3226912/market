<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/pub/constants.inc" %>
<%@ include file="/WEB-INF/jsp/pub/head.inc" %>
<%@ taglib uri="/WEB-INF/tagTld/c.tld" prefix="c"%> 
<!DOCTYPE HTML>
<html>
<style>
	em{font-style: normal;}
</style>
	
<head>
		<meta charset="utf-8" />
		<title>流程定义</title>
		<link rel="stylesheet" href="${CONTEXT}/css/Eworkflow.css" type="text/css" media="all" />
		<link rel="stylesheet" href="${CONTEXT}/lib/snaker/css/snaker.css" type="text/css" media="all" />
		<style type="text/css">
			svg {
				max-width: 1300px;
			}
		</style>
	</head>
	<body>
		<div class="Ewrap" style="font-size:13px;">
			<div class="markAll" style="display: none;">
				<div class="mark"></div>
				<div class="s-box">
					<div class="header"></div>
					<div class="body"></div>
					<span class="bottom">确认</span>
				</div>
			</div>
			<div class="markAll2" style="display: none;">
				<div class="mark"></div>
				<div class="s-box">
					<div class="header"></div>
					<div class="body"></div>
					<span class="bottom fl color-h" id="cancel">取消</span>
					<span class="bottom fl" id="confirm">确认</span>
				</div>
			</div>
			<div class="header">
				<div class="BGwhith" value="0" style="margin-left:15px;">基本信息</div>
				<div value="1">步骤定义</div>
			</div>
			<div id="body1" class="Ebody">
			      	<div class="wrapOne">
		                <span class="list-sp fl"><em class="inf-em">*</em>流程模板名称：</span>
		                <span class="list-sp-textdd">
		                	<input id="name" name="name" type="hidden" value="${dto.name }">
		                	<input class="long" maxlength="60" type="text" value="${dto.displayName }" id="displayNameAdd" name="displayNameAdd" placeholder="请输入流程模板名称(最多60个中文)">
		                </span>
		             </div>
			        <div class="wrapTwo">
		                <span class="list-sp fl"><em class="inf-em">*</em>所属市场：</span>
		                <span class="list-sp-textdd">
		                <select name="orgIdAdd" id="orgIdAdd" class="list-sp-text" value="${dto.orgId }">
		                	<c:forEach items="${marketList}" var="marketList">
		                		<c:if test="${dto.orgId == marketList.id}">
		                			<option selected="selected" value="${marketList.id}">${marketList.fullName}</option>
		                		</c:if>
		                		<c:if test="${dto.orgId != marketList.id}">
		                			<option value="${marketList.id}">${marketList.fullName}</option>
		                		</c:if>
		                	</c:forEach>
						</select>
		                </span>
		             </div>
		             <div class="wrapTwo">
		                <span class="list-sp fl"><em class="inf-em"></em>业务对象：</span>
		                <span class="list-sp-textdd">
		                <select class="list-sp-text" id="busTypeAdd" name="busTypeAdd">
		                	<c:forEach items="${busTypeList}" var="busTypeList">
		                		<c:if test="${dto.busType == busTypeList.busType}">
		                			<option selected="selected" value="${busTypeList.busType}">${busTypeList.busTypeDesc}</option>
		                		</c:if>
		                		<c:if test="${dto.busType != busTypeList.busType}">
		                			<option value="${busTypeList.busType}">${busTypeList.busTypeDesc}</option>
		                		</c:if>
		                	</c:forEach>
		                </select>
		                </span>
		             </div>
		             <div class="wrapTwo">
		                <span class="list-sp fl"><em class="inf-em">*</em>流程监控人：</span>
		                <span class="list-sp-textdd" id="changeUser">
		                	<input id="monitorIdAdd" name="monitorIdAdd" type="hidden" value="${dto.monitorId }">
		                	<input type="text" id="monitorDescAdd" name="monitorDescAdd" readonly value="${dto.monitorDesc }">
		                </span>
		             </div>
		             <div class="wrapTwo">
		                	<span class="list-sp fl"><em class="payment-cycle-sp"></em>启动状态：</span>
							    <label for="state1" id="cycle_charge" style="margin-right:10px;">
								    <input type="radio" name="stateAdd" id="state1" value="1" class="regular-radio" <c:if test="${dto.state =='1' }">checked</c:if>>
								    <span class="radio-mt10"></span><em class="radio-em-fees">启用</em>
							    </label>
						    	<label for="state0" id="cycle_charge">
						    	<input type="radio" name="stateAdd" value="0" id="state0" class="regular-radio" <c:if test="${dto.state =='0' }">checked</c:if>>
						    	<span class="radio-mt10"></span><em class="radio-em-fees">禁用</em>
						    	</label>
		             </div>
		             <div class="wrapOne">
		                	<span class="list-sp fl" style="float:left;margin-top:5px;"><em class="payment-cycle-sp"></em>流程说明：</span>
		                	<textarea maxlength="200" id="processDescAdd" name ="processDescAdd" placeholder="请输入流程说明(最多200个中文)">${dto.processDesc }</textarea>
		             </div>
		             <div class="footer">
		             	<div class="bottom color-h" id="close">取消</div>
		             	<div class="bottom color-r" id="save">保存</div>
		             	<div class="bottom color-g" id="delete">删除</div>
		             </div>
		             <iframe id="wfSelectPersonWin" class="wfPopWin" src="${CONTEXT }/wfProcessDefine/toWfSelectPersonWin" scrolling="no">
					</iframe>
			</div>
			<div id="body2" class="Ebody" style="display:none;">
				
				<div id="toolbox">
					<div id="toolbox_handle">工具集</div>
					<%-- 保存功能不在工具集中操作 --%>
					<%-- 	<div class="node" id="save"><img src="${CONTEXT}/lib/snaker/js/snaker/images/save.gif" />&nbsp;&nbsp;保存</div>
					<div><hr /></div> --%>
					<div class="node selectable" id="pointer">
					    <img src="${CONTEXT}/lib/snaker/js/snaker/images/select16.gif" />&nbsp;&nbsp;Select
					</div>
					<div class="node selectable" id="path">
					    <img src="${CONTEXT}/lib/snaker/js/snaker/images/16/flow_sequence.png" />&nbsp;transition
					</div>
					<div><hr/></div>
					
					<div class="node state" id="start" type="start">
						<img src="${CONTEXT}/lib/snaker/js/snaker/images/16/start_event_empty.png" />&nbsp;&nbsp;start
					</div>
					<div class="node state" id="end" type="end">
						<img src="${CONTEXT}/lib/snaker/js/snaker/images/16/end_event_terminate.png" />&nbsp;&nbsp;end
					</div>
					<div class="node state" id="task" type="task">
						<img src="${CONTEXT}/lib/snaker/js/snaker/images/16/task_empty.png" />&nbsp;&nbsp;task
					</div>
					<div class="node state" id="fork" type="decision">
						<img src="${CONTEXT}/lib/snaker/js/snaker/images/16/gateway_exclusive.png" />&nbsp;&nbsp;decision
					</div>
					
					<%-- 以下这些节点的功能暂时都不开放。 --%>
					<%-- <div class="node state" id="task" type="custom">
						<img src="${CONTEXT}/lib/snaker/js/snaker/images/16/task_empty.png" />&nbsp;&nbsp;custom
					</div>
					<div class="node state" id="task" type="subprocess">
						<img src="${CONTEXT}/lib/snaker/js/snaker/images/16/task_empty.png" />&nbsp;&nbsp;subprocess
					</div>
					<div class="node state" id="fork" type="fork">
						<img src="${CONTEXT}/lib/snaker/js/snaker/images/16/gateway_parallel.png" />&nbsp;&nbsp;fork
					</div>
					<div class="node state" id="join" type="join">
						<img src="${CONTEXT}/lib/snaker/js/snaker/images/16/gateway_parallel.png" />&nbsp;&nbsp;join
					</div> --%>
				</div>
				
				<div id="properties" style="position: fixed;">
					<div id="properties_handle">属性</div>
					<table class="properties_all" cellpadding="0" cellspacing="0"></table>
					<div>&nbsp;</div>
				</div>
				
				<div id="snakerflow"></div>
				
				<iframe id="wfSelectPostWin" class="wfPopWin" src="${CONTEXT }/wfProcessDefine/toWfSelectPostWin" scrolling="no">
				</iframe>
				
			</div>
		</div>
	</body>
	<script src="${CONTEXT}/lib/snaker/js/raphael-min.js" type="text/javascript"></script>
	<script src="${CONTEXT}/lib/snaker/js/jquery-ui-1.8.4.custom/js/jquery.min.js" type="text/javascript"></script>
	<script src="${CONTEXT}/lib/snaker/js/jquery-ui-1.8.4.custom/js/jquery-ui.min.js" type="text/javascript"></script>
	<script src="${CONTEXT}/lib/snaker/js/snaker/snaker.designer.js" type="text/javascript"></script>
	<script src="${CONTEXT}/lib/snaker/js/snaker/snaker.model.js" type="text/javascript"></script>
	<script src="${CONTEXT}/lib/snaker/js/snaker/snaker.editors.js" type="text/javascript"></script>
	<script src="${CONTEXT}/js/workflow/Eworkflow.js" type="text/javascript" charset="utf-8"></script>
	<script src="${CONTEXT}/js/workflow/updProcess.js" type="text/javascript" charset="utf-8"></script>
	<script type="text/javascript">
		var processModelJson = "${dto.processModelJson}";
		var id = "${dto.id}";
	</script>
</html>