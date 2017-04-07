<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page isELIgnored="true"%>
<!DOCTYPE HTML>
<html>
<div class="system-modal">
    <section class="header"><span>我的待办</span>
    </section>
    <div class="panel-body">
            <div class="adv-table editable-table ">
                <div class="space15"></div>
                <table class="table table-striped table-hover t-custom" id="tb">
                    <thead>
                    <tr class="ml-ct">
	                    <th>待办名称</th>
	                    <th>发送时间</th>
	                    <th>发起人</th>
                    </tr>
                    </thead>
                    <tbody id="taskBody">
                    </tbody>
                </table>

                <!-- 分页组件 -->
                <div id="pagebar"></div>
            </div>
        </div>
</div>
<!-- page end-->
<!-- page end-->
<script id="taskScript" type="text/x-jquery-tmpl">
	{{each(i, task) taskList}}
		<tr class="ml-ct">
			<td>
				<a href="javascript:(function(){Route.params={taskId:'${task.id}',orderId:'${task.orderId}'};location.href='index#${forwardUrl}';})();" style="color:blue;">${task.orderNo}</a>
			</td>
			<td>${task.createTime}</td>
			<td>${task.creatorDesc}</td>
    	</tr>
	{{/each}}

</script>
</html>
