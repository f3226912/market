<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page isELIgnored="true"%>
<base href="${CONTEXT}" >
<div class="system-modal">
    <section class="header"><span>我的消息</span>
        <div class="right-icon">
          
        </div>
    </section>
    
    <div class="panel-body">
        <div class="adv-table editable-table">
            <div class="space15"></div>
            <table class="table table-striped table-hover t-custom" id="tb">
                <thead>
                <tr class="ml-ct">
                    <th>消息名称</th>
                    <th>发送时间</th>
                    <th>发起人</th>
                    <th>状态</th>
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
	{{each(i, record) recordList}}
		<tr class="ml-ct">
			<td style="text-align:left;">
			{{if record.hasRead == '0'}}
				<a name="business" messageId="${record.id}" controller="${record.forwardUrl}" businessJson="${record.businessJson}" href="javascript:;" title="${record.messageContent}" style="color:blue;">${record.messageTitile}</a>
			{{else record.hasRead == '1'}}
				<a name="business" messageId="${record.id}" controller="${record.forwardUrl}" businessJson="${record.businessJson}" href="javascript:;" title="${record.messageContent}" style="color:#ccc;">${record.messageTitile}</a>
			{{/if}}
			</td>
			<td>${record.sendTime}</td>
			<td>${record.senderName}</td>
			<td>
			{{if record.hasRead == '0'}}
				<span class="text-muted">未读</span>
			{{else record.hasRead == '1'}}
				<span class="text-muted" style="color:#ccc">已读</span>
			{{/if}}
			</td>
    	</tr>
	{{/each}}
 
</script>
