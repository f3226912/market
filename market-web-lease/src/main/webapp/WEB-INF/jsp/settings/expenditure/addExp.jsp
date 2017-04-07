<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page isELIgnored="true"%>
<%@ taglib prefix="gd" uri="http://www.gdeng.cn/gd"%>
<section class="contract">
	<div class="header">参数设置</div>
	<div class="addCost">
		<div class="col-sm-12">
	        <section class="panel">
	        	<!-- title -->
				<div class="contract-title-block">
	                <span class="contract-info-h3">&nbsp;&nbsp;&nbsp;&nbsp;新增费项&nbsp;&nbsp;&nbsp;&nbsp;</span>
	            </div>
				<!-- 表单 -->
				<form id="form-add" action="" class="form-horizontal clearfix">
					<div class="text-center overflow-h magin-auto padding-t-b-35 w-40 efrom">
		           		<!-- 表单信息块 -->
		           		<ul class="overflow-h">
		           			<li>
		           				<div class="fl w-35"><i class="payment-cycle-sp">*</i>费项名称:</div>
		           				<div class="fl w-60"><input type="text" name="name" /></div>
		           			</li>
		           			<li>
		           				<div class="fl w-35"><i class="payment-cycle-sp">*</i>费项类型:</div>
		           				<div class="fl w-60">
		           					<select id="expType" name="expType" class="form-control">
		           					</select>
		           				</div>
		           			</li>
		           			<li>
		           				<div class="fl w-35"><i class="payment-cycle-sp">&nbsp;</i>费项说明:</div>
		           				<div class="fl w-60"><textarea style="padding:5px 10px" name="expDetail"></textarea></div>
		           			</li>
		           		</ul>
		           		
		           		<!-- 按钮块，操作表单 -->
		           		<button type="button" class="btn btn-ash btn-big" onclick="javascript:window.location='index#expenditure'">取消</button>
		           		<gd:btn btncode="BtnExpAddSave"><button type="submit" class="btn btn-danger btn-big">保存</button></gd:btn>
	           		</div>
				</form>
			</section>
		</div>
	</div>
</section>
	
<script id="template" type="text/x-jquery-tmpl">
	<option value="">请选择</option>
    {{each(i, row) rows}}
		<option value="${row.value}">${row.remark}</option>
	{{/each}}
</script>