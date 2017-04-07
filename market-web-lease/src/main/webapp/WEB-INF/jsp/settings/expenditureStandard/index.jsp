<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page isELIgnored="true"%>
<%@ taglib prefix="gd" uri="http://www.gdeng.cn/gd"%>
<style>
      /*重构样式*/
    .table.table ,.ml-ct th ,.form-inline .form-control { text-align: center !important;}
    .adv-table .dataTables_filter label input { width: 90%!important;}
</style>

<!-- page start-->
<section class="contract">
<div class="header">参数设置</div>
<div class="costDefinition">
    <div class="col-sm-12">
        <section class="panel">
            <div class="contract-title-block">
                <span class="contract-info-h3">&nbsp;&nbsp;&nbsp;&nbsp;计费标准管理&nbsp;&nbsp;&nbsp;&nbsp;</span>
            		<ul>
            			<li><i class="fa fa-trash-o"></i><gd:btn btncode="BtnExpStandardDelete"><a id="delete_btn" href="javascript:void(0)">删除</a></gd:btn></li>
            			<li><i class="fa fa-plus-square fa-cor"></i><gd:btn btncode="BtnExpStandardAdd"><a id="addExpStandard" href="javascript:void(0);">新增计费标准</a></gd:btn></li>
            		</ul>
            </div>
            <div class="panel-body">
                <div class="adv-table editable-table ">
                    <div class="space15"></div>
                    <table class="table table-striped table-hover table-bordered" id="tb">
                        <thead>
                        <tr class="ml-ct">
                            <th>序号</th>
                            <th>计费标准编码</th>
                            <th>计费标准名称</th>
                            <th>计算方法</th>
                            <th>费项</th>
                            <th>费项类型</th>
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
<!-- page end-->
<script id="template" type="text/x-jquery-tmpl">
    {{each(i, expStandard) expStandards}}
		<tr class="ml-ct">
			<td>${i+1}</td>
			<td>${expStandard.code}</td>
			<td><gd:btn btncode="BtnExpStandardDetail"><a href="javascript:(function(){Route.params={id:${expStandard.id},expType:${expStandard.expType}};location.href='index#editExpStandard';})();">${expStandard.name}</gd:btn></td>
			<td>${expStandard.rentModeName}</td>
			<td>${expStandard.expName}</td>
			<td>${expStandard.expTypeName}</td>
    	</tr>
	{{/each}} 
</script>