<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page isELIgnored="true"%>
<%@ include file="/WEB-INF/jsp/pub/tags.inc" %>
<section class="contract">
	<div class="header">参数设置</div>
<div class="addCost">
    <div class="col-sm-12">
        <section class="panel">
            <div class="contract-title-block">
                <span class="contract-info-h3">&nbsp;&nbsp;&nbsp;新增计量表分类&nbsp;&nbsp;&nbsp;</span>
            </div>
        	<form id="form-add" action="#" class="form-horizontal clearfix">
	            <div class="text-center overflow-h magin-auto padding-t-b-35 w-40 efrom">
	           		<ul class="overflow-h">
	           			<li>
	           				<div class="fl w-35"><i class="payment-cycle-sp">*</i>分类编号:</div>
	           				<div class="fl w-60"><input type="text" value="" name="code"/></div>
	           			</li>
	           			<li>
	           				<div class="fl w-35"><i class="payment-cycle-sp">*</i>计量表分类名称:</div>
	           				<div class="fl w-60"><input type="text" value="" name="name"/></div>
	           			</li>
	           			<li>
	           				<div class="fl w-35"><i class="payment-cycle-sp">*</i>走表类费项:</div>
	           				<div class="fl w-60">
	           					<select class="form-control" name="expId" id="expId">
	           						  
	           					</select>
	           				</div>
	           			</li>
	           			
	           		</ul>
	           		<button type="button" class="btn btn-ash btn-big" onclick="javascript:window.location='index#measureType'">取消</button>
	           		<gd:btn btncode="BtnMeasureTypeAddSave">
		           		<button type="submit" class="btn btn-danger btn-big" id="saveForm">保存</button>
					</gd:btn>
	           </div>
           </form>
        </section>
    </div>
</div>
</section>
<script id="template" type="text/x-jquery-tmpl">
	<option value="">请选择</option>
    {{each(i, row) rows}}
		<option value="${row.id}">${row.name}</option>
	{{/each}}
</script>
