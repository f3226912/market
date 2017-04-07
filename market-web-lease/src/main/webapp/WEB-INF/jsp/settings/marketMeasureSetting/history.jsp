<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page isELIgnored="true"%>
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
							<th>关联资源</th>
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
		<div class="row-fluid">
			 <div class="col-lg-12" id="pagebar"></div>
		</div>
	</div>
</div>



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
			<td>${row.resourceName}</td>
		</tr>
	{{/each}}
</script>