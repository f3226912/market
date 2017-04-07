<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page isELIgnored="true"%>
<%@ include file="/WEB-INF/jsp/pub/tags.inc" %>
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
                <span class="contract-info-h3">&nbsp;&nbsp;&nbsp;&nbsp;计量表分类&nbsp;&nbsp;&nbsp;&nbsp;</span>
            		<ul>
            			<li>
            			<gd:btn btncode="BtnMeasureTypeDelete">
            				<i class="fa fa-trash-o"></i><a id="delete_btn" href="javascript:void(0)">删除</a>
						</gd:btn>
            			</li>
            			<li>
            			<gd:btn btncode="BtnMeasureTypeAdd">
            				<i class="fa fa-plus-square fa-cor"></i><a id="add_btn" href="javascript:void(0)">新增计量表分类</a>
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
                            <th>序号</th>
                            <th>计量表分类编码</th>
                            <th>计量表分类名称</th>
                            <th>相关费项</th>
                            <th>是否系统级</th>
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
    {{each(i, row) rows}}
		<tr class="ml-ct">
			<td>${i+1}</td>
			<td>${row.code}</td>
			<td><a href="javascript:(function(){Route.params={id:${row.id}, code:'${row.code}', expId:'${row.expId}', name:'${row.name}', sysType:'${row.sysType}'};location.href='index#editMeasureType';})();">${row.name}</a></td>
			<td>${row.expName}</td>
			<td>${row.sysTypeStr}</td>
    	</tr>
	{{/each}}
</script>