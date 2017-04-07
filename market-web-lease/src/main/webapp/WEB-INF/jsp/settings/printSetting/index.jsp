<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page isELIgnored="true"%>
<div class="system-modal">
 		 	<div class="col-md-max">
		      <div class="main-search">
		           <div class="main-ipt-types">
		              <form class="form-input clearfix" id="printSettingSearchForm">
		                <div class="form-group">
		                   	套打名称: <input id="printName" name="printName" type="text" class="form-control wid150" placeholder="套打名称">
		                </div>
		               <div class="form-group">
							应用业务:<select class="form-control wid150" id="bizType" name="bizType">
								<option value="">请选择业务类型</option>
								<option value="0">合同管理</option>
								<option value="1">合同变更</option>
								<option value="2">合同结算</option>
								<option value="3">合同付款</option>
							</select>
						</div>
		                <div class="form-group">
		                                                   所属市场:<select class="form-control wid150" id="marketId" name="marketId">
								<option value="">请选择所属市场</option>
							</select>
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
                            <th><input type="checkbox" id="printSettingCheckAll"></th>
                            <th>序号</th>
                            <th>市场名称</th>
                            <th>业务类型</th>
                            <th>套打名称</th>
                            <th>模板编码</th>
                            <th>模板名称</th>
                        </tr>
                        </thead>
                        <tbody id="printSettingTemplateBody">
                        </tbody>
                    </table>
                    <!-- 分页组件 -->
                	<div id="printSettingPagebar" style="text-align:center;"></div>
                </div>
            </div>
    	</div>
<!-- page end-->
<script id="printSettingTemplate" type="text/x-jquery-tmpl">
    
	{{each(i, setting) settings}}
		<tr class="ml-ct">
		<td style="text-align: center!important;"><input name="printSettingCheckbox" type="checkbox" value="${setting.id}"/></td>
			<td><a style="color: #0049dd;" href="javascript:(function(){Route.params={id:${setting.id}};location.href='index#printSettingEdit';})();">${i+1}</a></td>
			<td><a style="color: #0049dd;" href="javascript:(function(){Route.params={id:${setting.id}};location.href='index#printSettingEdit';})();">${setting.marketName}</a></td>
			<td><a style="color: #0049dd;" href="javascript:(function(){Route.params={id:${setting.id}};location.href='index#printSettingEdit';})();">${setting.bizTypeStr}</a></td>
			<td><a style="color: #0049dd;" href="javascript:(function(){Route.params={id:${setting.id}};location.href='index#printSettingEdit';})();">${setting.printName}</a></td>
			<td><a style="color: #0049dd;" href="javascript:(function(){Route.params={id:${setting.id}};location.href='index#printSettingEdit';})();">${setting.templateCode}</a></td>
			<td><a style="color: #0049dd;" href="javascript:(function(){Route.params={id:${setting.id}};location.href='index#printSettingEdit';})();">${setting.templateName}</a></td>
    	</tr>
	{{/each}}

</script>