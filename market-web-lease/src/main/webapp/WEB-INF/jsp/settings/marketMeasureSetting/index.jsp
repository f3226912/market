<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page isELIgnored="true"%>
<%@ include file="/WEB-INF/jsp/pub/tags.inc" %>
<section class="meterManagement contract">
	<div class="header">参数设置</div>
	<form class="form-input clearfix" id="queryForm">
		<div class="form-group">
			计量表分类:
			<select class="form-control wid150 display-inline" name="measureTypeId" id="measureTypeNameTemplateBody">
				
			</select> 
		</div>
		<div class="form-group">
			表状态:
			<select class="form-control wid150 display-inline" name="status">
				<option selected="selected" value="">全部</option>
				<option value="1">启用</option>
				<option value="0">停用</option>
			</select>
		</div>
		<div class="form-group" >
			计量表编号:
			<input type="text" name="code" class="form-control wid150 display-inline">
		</div>
		<div class="form-group">
			关联资源:
			<input type="text" name="resourceName" class="form-control wid150 display-inline">
		</div>
		<div class="form-group">
			<button class="btn btn-danger" type="button" id="query_btn">查询</button>
		</div>
	</form>
	
	<div class="costDefinition">
	    <div class="col-sm-12">
	        <section class="panel">
	            <div class="contract-title-block">
	                <span class="contract-info-h3">&nbsp;&nbsp;&nbsp;&nbsp;计量表管理&nbsp;&nbsp;&nbsp;&nbsp;</span>
	            		<ul>
	            			<li>
	            				<gd:btn btncode="BtnMeasureDelete">
 					            	<i class="fa fa-trash-o"></i><a id="delete_btn" href="javascript:void(0)">删除</a>
		            			</gd:btn>
	            			</li>
	            			<li>
	            				<gd:btn btncode="BtnMeasureBatchDisable">
 					            	<i class="fa fa-ban"></i><a id="disable_btn" href="javascript:void(0)">停用</a>
		            			</gd:btn>
	            			</li>
	            			<li>
		            			<gd:btn btncode="BtnMeasureBatchEnable">
		            				<i class="fa fa-key"></i><a id="enable_btn" href="javascript:void(0)">启用</a>
		            			</gd:btn>
	            			</li>
	            			<li>
		            			<gd:btn btncode="BtnMeasureBatchAdd">
		            				<i class="fa fa-plus-square fa-cor"></i><a id="batchAdd_btn" href="javascript:void(0)">批量新增</a></li>
		            			</gd:btn>
	            			<li>
	            			 <gd:btn btncode="BtnMeasureAdd">
	            				 <i class="fa fa-plus-square fa-cor"></i><a id="add_btn" href="javascript:void(0)">新增</a>
	            			 </gd:btn>
	            			</li>
	            		</ul>
	            </div>
	            <div class="panel-body">
	                <div class="adv-table editable-table ">
	                    <div class="space15"></div>
	                    <table class="table table-striped table-hover table-bordered" id="tb">
	                        <thead>
	                        <tr class="ml-ct">
	                        	<th><input type="checkbox" id="checkboxAll"/></th>
	                            <th>序号</th>
	                            <th>计量表编号</th>
	                            <th>计量表分类</th>
	                            <th>表状态</th>
	                            <th>关联费项</th>
	                            <th>关联资源</th>
	                        </tr>
	                        </thead>
	                        <tbody id="templateBody">
	                        </tbody>
	                    </table>
	                    
	                </div>
	                
	            </div>
	            
	        </section>
	        <div class="row-fluid">
	             <div class="col-lg-12" id="pagebar"></div>
	        </div>
	    </div>
	</div>
</section>

<script id="template" type="text/x-jquery-tmpl">
    {{each(i, row) rows}}
		<tr class="ml-ct">
			<td style="text-align:center !important;"><input class="Echeckbox" onclick="clickBox(this)" type="checkbox" value="${row.id}" data-status="${row.status}" name='subBox'/></td>
			<td>${i+1}</td> 
			<td><a href="javascript:(function(){Route.params={id:${row.id}, code:'${row.code}', expId:${row.expId}, measureTypeId:${row.measureTypeId}, resourceId:'${row.resourceId}', maxVal:'${row.maxVal}', status:'${row.status}', resourceName:'${row.resourceName}',expName:'${row.expName}'};location.href='index#measureSettingInfo';})();">${row.code}</a></td>
			<td>${row.measureTypeName}</td>
			<td>${row.statusStr}</td>
			<td>${row.expName}</td>
			<td>${row.resourceName}</td>
		</tr>
	{{/each}}
</script>
<script id="measureTypeNameTemplate" type="text/x-jquery-tmpl">
	<option value="">请选择</option>
    {{each(i, row) rows}}
		<option value="${row.id}">${row.name}</option>
	{{/each}}
</script>