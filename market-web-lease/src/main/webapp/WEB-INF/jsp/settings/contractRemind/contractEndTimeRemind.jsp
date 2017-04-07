<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page isELIgnored="true"%>
<style>
	.e1109{
		overflow: hidden;
        white-space: nowrap;
        text-overflow:ellipsis;
	}
</style>
<div class="system-modal contract" id="econtract" style="padding:0 30px;">
<div class="contract-info">
                 	 <div class="contract-title-block" id="a2">
                 	 	<span class="contract-info-h3">&nbsp;&nbsp;&nbsp;&nbsp;合同提醒&nbsp;&nbsp;&nbsp;&nbsp;</span>
                 	 	<!-- <ul id="eUl" style="float: right;margin:15px 29px 0 0;">
			            <li><i class="fa fa-trash-o"></i><a style="color:#e86d4d;" id="contractEndTime_btn" href="javascript:(function(){Route.param={pageSize:20};location.href='index#contractEndRemind';})();">更多</a></li>
			            </ul> -->
                 	 </div>
                 	 <div class="panel-body" style="margin:30px auto 0;width:98%;">
            <div class="adv-table editable-table ">
                <div class="space15"></div>
                <table class="table table-striped table-hover t-custom" id="tb">
                    <thead>
                    <tr class="ml-ct">
                       <th>序号</th>
                            <th>合同编号</th>
                            <th>租赁资源</th>
                            <th>合同结束日期</th>
                            <th>乙方</th>
                            <th>手机号码</th>
                            <th>客户名称</th>
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
    
</div>
<!-- page end-->
<script id="template" type="text/x-jquery-tmpl">
    
	{{each(i, row) rows}}
		<tr class="ml-ct">
			<td>${i+1}</td>
			<td title="${row.contractNo}"><div class="e1109" style="width:160px"><a href="javascript:(function(){Route.params={id:${row.id}};location.href='index#contractManageDetail';})();">${row.contractNo}</a></div></td>
			<td title="${row.leasingResource}"><div class="e1109" style="width:322px">${row.leasingResource}</div></td>
			<td><div class="e1109">${row.endLeasingDay}</div></td>
			<td title="${row.partyB}"><div class="e1109" style="width:83px">${row.partyB}</div></td>
            <td>${row.customerMobile}</td>
			<td title="${row.customerName}"><div class="e1109" style="width:86px">${row.customerName}</div></td>
    	</tr>
	{{/each}}

</script>

