<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page isELIgnored="true"%><div class="system-modal contract">
<style>
	.e1109{
		overflow: hidden;
        white-space: nowrap;
        text-overflow:ellipsis;
	}
</style>
<div class="system-modal contract" id="fcontract" style="padding:0 30px;">
<div class="contract-info">
                 	 <div class="contract-title-block" id="fa2">
                 	 	<span class="contract-info-h3">&nbsp;&nbsp;&nbsp;&nbsp;收款提醒&nbsp;&nbsp;&nbsp;&nbsp;</span>
			            <!-- <ul id="eUl" style="float: right;margin:15px 29px 0 0;">
			            <li><i class="fa fa-trash-o"></i><a style="color:#e86d4d;" id="contractEndTime_btn" href="javascript:(function(){Route.param={pageSize:20};location.href='index#financeEarlyWaring';})();">更多</a></li>
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
                            <th>缴费期限</th>
                            <th>乙方</th>
                            <th>手机号码</th>
                            <th>客户名称</th>
                    </tr>
                    </thead>
                    <tbody id="moneyBody">
                    </tbody>
                </table>

                <!-- 分页组件 -->
                <div id="pagebar2"></div>
            </div>
        </div>
                 </div>
    
</div>

<!-- page end-->
<script id="moneytemplate" type="text/x-jquery-tmpl">
    
	{{each(i, record) recordList}}
		<tr class="ml-ct">
			<td>${i+1}</td>
			<td title="${record.contractNo}"><div class="e1109" style="width:160px">${record.contractNo}</div></td>
			<td title="${record.resourceNames}"><div class="e1109" style="width:322px">${record.resourceNames}</div></td>
			<td>${record.timeLimit}</td>
			<td title="${record.partyB}"><div class="e1109" style="width:83px">${record.partyB}</div></td>
            <td>${record.customerMobile}</td>
			<td title="${record.customerName}"><div class="e1109" style="width:86px">${record.customerName}</div></td>
    	</tr>
	{{/each}}

</script>

