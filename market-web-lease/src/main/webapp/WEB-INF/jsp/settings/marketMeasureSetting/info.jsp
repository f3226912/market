<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page isELIgnored="true"%>
<%@ include file="/WEB-INF/jsp/pub/tags.inc" %>
<section class="contract">
	<div class="header">参数设置</div>
	<section class="panel" style="border:none;margin-top:5px;">
		<header class="tab">
			<ul class="nav nav-tabs">
		        <li id="printSetting" class="active" style="margin-left:13px;">
		            <a data-toggle="tab" href="#funAuth">计量表信息</a>
		        </li>
		        <li id="printTemplate" class="">
		            <a data-toggle="tab" href="#InfoAuth">历史抄表记录</a>
		        </li>
		    </ul>
		</header>
		<div class="tab-content text-center">
			<div id="funAuth" class="tab-pane active">	
				<div class="chargingStandardDT">
					<div class="col-sm-12">
						<section class="panel">
							<div class="contract-title-block">
								<span class="contract-info-h3">&nbsp;&nbsp;&nbsp;&nbsp;计量表信息&nbsp;&nbsp;&nbsp;&nbsp;</span>
							</div>
							<form id="editForm" class="form-horizontal clearfix cmxform">
								<input type="hidden" value="" name="resourceId" id="resourceId" />
								<input type="hidden" value="" name="expId" id="expId" />
								<input type="hidden" value="" name="expName" id="expName"/>
								<input type="hidden" value="" name="id" id="id"/>
							
								<div class="text-center overflow-h magin-auto padding-t-b-35 w-84 efrom">
									<ul id="wrap" class="overflow-h">
										<li>
											<div class="fl w-35 text-left"><i class="payment-cycle-sp">*</i>计量表编号:</div>
											<div class="fl w-60">
												<input type="text" value="" name="code" id="code"/>
											</div>
										</li>
										<li>
											<div class="fl w-35 text-left"><i class="payment-cycle-sp">*</i>走表类费项:</div>
											<div class="fl w-60">
												<input type="text" value="" name="expNameTemp" id="expNameTemp" readonly="readonly"/>
											</div>
										</li>
										<li>
											<div class="fl w-35 text-left"><i class="payment-cycle-sp">*</i>计量表分类:</div>
											<div class="fl w-60">
												<select class="form-control valid" name="measureTypeId" id="measureTypeId">
												</select>
											</div>
										</li>
										<li>
											<div class="fl w-35 text-left"><i class="payment-cycle-sp">*</i>关联资源:</div>
											<div class="fl w-60">
												<input class="Einput-s" type="text" value="" name="resourceName" id="resourceName"/>
												<div id="btnSelectResource" class="ebutton cursor-p">选择</div>
											</div>
										</li>
										<li>
											<div class="fl w-35 text-left"><i class="payment-cycle-sp">*</i>最大读数:</div>
											<div class="fl w-60">
												<input type="text" value="" name="maxVal" id="maxVal" />
											</div>
										</li>
										<li>
											<div class="fl w-35 text-left"><i class="payment-cycle-sp">*</i>表状态:</div>
											<label for="radio-1-1">
												<input type="radio" id="radio-1-1" name="status" class="regular-radio" value="1" checked="">
											    <span class="radio-mt10"></span><em class="radio-em">启用</em>
											</label>
										    <label for="radio-1-2">
											    <input type="radio" id="radio-1-2" name="status" class="regular-radio" value="0" id="disable_radio">
												<span class="radio-mt10"></span><em class="radio-em" style="padding-right:0">不启用</em>
											</label>
										</li>
									</ul>
									<button type="button" onclick="javascript:window.location='index#measureSetting'" class="btn btn-ash btn-big">取消</button>
									<gd:btn btncode="BtnMeasureEditSave">
										<button type="submit" class="btn btn-danger btn-big" id="edit_btn">保存</button>
									</gd:btn>
								</div>
							</form>
						</section>
					</div>
				</div>
			</div>
			
			<div id="InfoAuth" class="tab-pane">
				<div class="costDefinition">
					<div class="col-sm-12">
						<section class="panel">
							<div class="contract-title-block">
								<span class="contract-info-h3">&nbsp;&nbsp;&nbsp;&nbsp;历史抄表记录&nbsp;&nbsp;&nbsp;&nbsp;</span>
									
							</div>
							<div class="panel-body">
								<div class="adv-table editable-table ">
									<div class="space15"></div>
									<table class="table table-striped table-hover table-bordered" id="tb">
										<thead>
										<tr class="ml-ct">
											<th>序号</th>
											<th>抄表日期</th>
											<th>本次读数</th>
											<th>上次读数</th>
											<th>本次用量</th>
											<th>损耗用量</th>
											<th>收费金额</th>
											<th>收款状态</th>
											<th>客户名称</th>
											<th>乙方</th>
											<th>资源名称</th>
										</tr>
										</thead>
										<tbody id="historyTemplateBody">
										</tbody>
									</table>
								</div> 
							</div>
							<div class="row-fluid">
					           <div class="col-lg-12" id="historyPagebar"></div>
					      </div>
						</section>
					</div>
				</div>
			</div>
		</div>
	</section>
	
</section>


<div class="modal fade addMeterM" id="myModal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
  <div class="modal-dialog">
    <div class="modal-content" style="width:700px;">
      <div class="modal-header">
        <button type="button" class="close" data-dismiss="modal"><span aria-hidden="true">&times;</span><span class="sr-only">Close</span></button>
        <h4 class="modal-title" id="myModalLabel">选择租赁资源</h4>
      </div>
       <div class="eHeader" >
        	<form class="form-input clearfix" id="queryForm">
				<div class="form-group" style="margin-left:20px;">
					区域/楼栋:
					<select class="form-control wid130 display-inline" id="areaId" name="areaId">
					</select>
				</div>
				<div class="form-group">
					<select class="form-control wid130 display-inline" id="budingId" name="budingId">
					</select>
				</div>
				<div class="form-group">
					<input type="text" class="form-control wid250 display-inline" placeholder="租赁资源名称" name="resourceName_query" id="resourceName_query">
				</div>
				<div class="form-group">
					<button class="btn btn-danger" type="button" id="query_btn">查询</button>
				</div>
			</form>
        </div>
      <div class="modal-body costDefinition">
       <div class="adv-table editable-table ">
			<div class="space15"></div>
			<table class="table table-striped table-hover table-bordered" id="tb">
				<thead>
					<tr class="ml-ct">
						<th>选择</th>
						<th>租赁资源名称</th>
						<th>所属楼栋</th>
						<th>所属区域</th>
					</tr>
				</thead>
				<tbody id="templateBody">
					
				</tbody>
			</table>
		</div>
      </div>
      <div class="row-fluid">
           <div class="col-lg-12" id="pagebar"></div>
      </div>
      <div class="modal-footer">
        <button type="button" class="btn btn-default" data-dismiss="modal">取消</button>
        <button type="button" class="btn btn-danger saveForm" id="enter_btn">确认</button>
      </div>
    </div>
  </div>
</div>

<script id="template" type="text/x-jquery-tmpl">
    {{each(i, row) rows}}
		<tr class="ml-ct">
			<td><input class="Echeckbox" name="checkbox" type="checkbox" value="${row.id}_${row.name}"></td>
			<td>${row.name}</td>
			<td class="center">${row.areaName}</td>
			<td>${row.buildingName}</td>
		</tr>
	{{/each}}
</script>
<script id="historyTemplate" type="text/x-jquery-tmpl">
    {{each(i, row) rows}}
		<tr class="ml-ct">
			<td>${i+1}</td>
			<td>${formateDate(row.noteDate,'yyyy-MM-dd')}</td>
			<td>${row.current}</td>
			<td>${row.last}</td>
			<td>${row.thisQuantity}</td>
			<td>${row.wastage}</td>
			<td>${row.amount}</td>
			<td>${row.status}</td>
			<td>${row.customerName}</td>
			<td>${row.partyB}</td>
			<td>${row.resourceNames}</td>
		</tr>
	{{/each}}
</script>
