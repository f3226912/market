<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page isELIgnored="true"%>
<%@ include file="/WEB-INF/jsp/pub/tags.inc" %>
<section class="contract" >
	<div class="header">参数设置</div>
	<div class="chargingStandardDT">
		<div class="col-sm-12">
			<section class="panel">
				<div class="contract-title-block">
					<span class="contract-info-h3">&nbsp;&nbsp;&nbsp;&nbsp;新增计量表&nbsp;&nbsp;&nbsp;&nbsp;</span>
				</div>
				<form id="addForm" class="form-horizontal clearfix cmxform">
					<input type="hidden" value="" name="resourceId" id="resourceId" />
					<input type="hidden" value="" name="expId" id="expId" />
					<input type="hidden" value="" name="expName" id="expName"/>
				
					<div class="text-center overflow-h magin-auto padding-t-b-35 w-84 efrom">
						<ul id="wrap" class="overflow-h">
							<li>
								<div class="fl w-35 text-left"><i class="payment-cycle-sp">*</i>计量表编号:</div>
								<div class="fl w-60">
									<input type="text" value="" name="code"/>
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
									<select class="form-control" name="measureTypeId" id="measureTypeId">
										
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
									<input type="text" value="" name="maxVal" />
								</div>
							</li>
							<li>
								<div class="fl w-35 text-left"><i class="payment-cycle-sp">*</i>表状态:</div>
								<label for="radio-1-1" style="padding-left:0">
									<input type="radio"  id="radio-1-1"  name="status"  value="1" class="regular-radio" >
									    <span class="radio-mt10"></span><em class="radio-em" >启用</em>
								</label>
							    <label for="radio-1-2">
							    	<input type="radio" id="radio-1-2" name="status"  value="0" class="regular-radio" checked="checked">
										<span class="radio-mt10"></span><em class="radio-em" style="padding-right:0">不启用</em>
								</label>
							</li>
						</ul>
						<button type="button" onclick="javascript:window.location='index#measureSetting'" class="btn btn-ash btn-big">取消</button>
						<gd:btn btncode="BtnMeasureAddSave">
							<button type="submit" class="btn btn-danger btn-big" id="add_btn">保存</button>
						</gd:btn>
						
					</div>
				</form>
			</section>
		</div>
	</div>
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
			<td style="text-align: center !important;"><input class="Echeckbox" name="checkbox" type="checkbox" value="${row.id}_${row.name}"></td>
			<td>${row.name}</td>
			<td class="center">${row.areaName}</td>
			<td>${row.buildingName}</td>
		</tr>
	{{/each}}
</script>
