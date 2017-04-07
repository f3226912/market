<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page isELIgnored="true"%>
<div class="system-modal">
 		 	<div class="col-md-max">
		      <div class="main-search">
		           <div class="main-ipt-types">
		              <form class="form-input clearfix" id="printTemplateSearchForm">
		                <div class="form-group">
		                      模板名称:<input id="templateName" name="templateName" type="text" class="form-control" style="width:235px" placeholder="模板名称">
		                </div>
		               
		                <div class="form-group">
		                    <button id="query" class="btn btn-danger" type="button">查询</button>
		                </div>
		            </form>
		           </div>
		       </div>
		    </div>
            <div class="">
                <div class="adv-table editable-table ">
                    <div class="clearfix">
                    </div>
                    <div class="space15"></div>
                	<table class="table table-striped table-hover t-custom" id="tb">
                        <thead>
                        <tr class="ml-ct">
                        	<th width="5%"><input type="checkbox" id="printTemplateCheckAll"></th>
                            <th width="5%">序号</th>
                            <th width="20%">模板编号</th>
                            <th width="20%">模板名称</th>
                            <th width="20%">模板包</th>
                            <th width="30%">备注</th>
                        </tr>
                        </thead>
                        <tbody id="templateBody">
                        </tbody>
                    </table>
                    <!-- 分页组件 -->
                	<div id="pagebar"></div>
                </div>
            </div>
    </div>
<!-- page end-->
<script id="template" type="text/x-jquery-tmpl">
    
	{{each(i, template) templates}}
		<tr class="ml-ct">
			<td style="text-align: center!important;"><input name="printTemplateCheckbox" type="checkbox" value="${template.id}"/></td>
			<td><a style="color: #0049dd;" href="javascript:(function(){Route.params={id:${template.id}};location.href='index#printTemplateEdit';})();">${i+1}</a></td>
			<td><a style="color: #0049dd;" href="javascript:(function(){Route.params={id:${template.id}};location.href='index#printTemplateEdit';})();">${template.templateCode}</a></td>
			<td><a style="color: #0049dd;" href="javascript:(function(){Route.params={id:${template.id}};location.href='index#printTemplateEdit';})();">${template.templateName}</a></td>
			<td><a style="color: #0049dd;" href="${template.templateUrl}">${template.templateFile}</a></td>
			<td><a style="color: #0049dd;" href="javascript:(function(){Route.params={id:${template.id}};location.href='index#printTemplateEdit';})();">${template.info}</a></td>
    	</tr>
	{{/each}}

</script>