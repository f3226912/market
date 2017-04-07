<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page isELIgnored="true"%>
<section class="header">岗位详情</section>

<section class="panel" style="margin-bottom:0;">
	<header class="tab">
		<ul class="nav nav-tabs">
			<li id="tab-postInfo" class="">
				<a data-toggle="tab" href="#postInfo">岗位信息</a>
			</li>
			<li id="tab-postUser" class="active">
				<a data-toggle="tab" href="#postUser">用户信息</a>
			</li>
			<li id="tab-postFunAuth" class="">
				<a data-toggle="tab" href="#funAuth">功能权限</a>
			</li>
			<li id="tab-postDataAuth" class="">
				<a data-toggle="tab" href="#dataAuth">数据权限</a>
			</li>
		</ul>
	</header>
</section>

<div class="tab-content">
	<div id="postInfo" class="tab-pane clearfix" style="margin-top:30px">
		<div class="form-group col-lg-5">
			<label class="control-label col-md-4">
				岗位名称：
			</label>
			<div id="name" class="col-md-6 text-left">
			</div>
		</div>
		<div class="form-group col-lg-5">
			<label class="control-label col-md-4">
				所属部门： 
			</label>
			<div id="orgFullName" class="input-group col-sm-7">
				所属部门
			</div>
		</div>
		<div class="form-group col-lg-5">
			<label class="control-label col-md-4">
					岗位说明： 
				</label>
			<div id="remark" class="col-md-6 text-left">
			</div>
		</div>
	</div>
	
	<div id="postUser" class="tab-pane active" style="margin-top:15px">
		<div class="">
		    <div class="panel-body">
		        <div class="adv-table editable-table ">
		            <div class="space15"></div>
		            <table class="table table-striped table-hover t-custom" id="tb">
		                <thead>
		                <tr class="ml-ct">
		                    <th>用户账号</th>
		                    <th>姓名</th>
		                    <th>所属公司</th>
		                    <th>所属市场</th>
		                    <th>所属部门</th>
		                    <th>岗位</th>
		                    <th>手机号码</th>
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
	</div>
	<div id="funAuth" class="tab-pane system-modal">
		<div class="tab-title clearfix">
			<div class="col-sm-6" style="padding-left:136px;height:40px;line-height:40px;">系统功能</div>
		</div>
		<ul id="tree2" class="ztree"></ul>
	</div>
	<div id="dataAuth" class="tab-pane system-modal">
		<div class="tab-title clearfix">
			<div class="col-sm-6" style="padding-left:136px;height:40px;line-height:40px;">数据权限</div>
		</div>
		<ul id="tree3" class="ztree"></ul>
	</div>
</div>

<!-- page end-->
<script id="template" type="text/x-jquery-tmpl">
	{{each(i, user) users}}
		<tr class="ml-ct">
			<th>${user.code}</th>
            <th>${user.name}</th>
            <th>${user.companyName}</th>
            <th>${user.marketName}</th>
            <th>${user.departmentName}</th>
            <th>${user.postName}</th>
            <th>${user.mobile}</th>
			{{if user.status==0}}
            	<th>禁用</th>
        	{{else}}
            	<th>启用</th>
        	{{/if}}
    	</tr>
	{{/each}}

</script>

<script id="noDataScript" type="text/x-jquery-tmpl">
		<tr class="charges-tab-blank">
			<td colspan="8" class="ob_font" style="text-align: center;">暂无数据!</td>
    	</tr>
</script>