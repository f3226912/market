<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/pub/tags.inc" %>
<%@ page isELIgnored="true"%>
<style>
  .dataTables_paginate.paging_bootstrap.pagination{float:none;}
  #pagebar{ text-align:center;}
  #pagebar .fl{margin-top:9px !important;}
</style>
<div class="system-modal">
	<section class="header"><span>岗位管理</span>
		<div class="right-icon">
			<gd:btn btncode="BtnSysPostAdd"><a href="index#addPost"><i class="fa fa-plus-square"></i>新增</a></gd:btn>
<!-- 			<a href="index#tree"><i class="fa fa-download"></i>导出</a> -->
<!-- 			<a href="index#tree"><i class="fa fa-download"></i>zTree组织结构树示例</a> -->
		</div>
	</section>
	<div class="col-md-max">
	<div class="main-search">
		<div class="main-ipt-types">
			<form class="form-input clearfix">
				<div class="form-group">
					<input id="name" type="text" class="form-control wid150" placeholder="岗位名称" maxlength="20" style="width:257px !important;">
				</div>
				<div class="form-group">
					<gd:btn btncode="BtnSysPostQuery"><button id="query" class="btn btn-danger" type="button" style="width:70px !important;">查询</button></gd:btn>
				</div>
			</form>
		</div>
	</div>
	</div>
	<div class="panel-body">
		<div class="adv-table editable-table ">
			<div class="space15"></div>
			<table class="table table-striped table-hover t-custom" id="tb">
				<thead>
				<tr class="ml-ct">
					<th>NO.</th>
					<th>岗位名称</th>
					<th>所属部门</th>
					<th>用户数</th>
					<th>岗位说明</th>
					<th class="center">操作</th>
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
	
	{{each(i, post) posts}}
		<tr class="ml-ct">
			<td>${i+1}</td>
			<td>${post.name}</td>
			<td>${post.orgFullName}</td>
			<td>${post.userCount}</td>
			<td>${post.remark}</td>
			<td>
				<gd:btn btncode="BtnSysPostAuth"><a class="check-auth" href="javascript:(function(){Route.params={p:${post.id},tab:3};location.href='index#post-user';})();">查看权限</a>&nbsp;</gd:btn>
				<gd:btn btncode="BtnSysPostUser"><a class="check-auth" href="javascript:(function(){Route.params={p:${post.id},tab:2};location.href='index#post-user';})();">查看用户</a>&nbsp;</gd:btn>
				<gd:btn btncode="BtnSysPostEdit"><a class="edit" href="javascript:(function(){Route.params={p:${post.id}};location.href='index#edit-post';})();">编辑</a>&nbsp;</gd:btn>
				<gd:btn btncode="BtnSysPostDelete"><a class="delete" postId="${post.id}" href="javascript:;">删除</a></gd:btn>
			</td>
		</tr>
	{{/each}}

</script>
<script id="noDataScript" type="text/x-jquery-tmpl">
		<tr class="charges-tab-blank">
			<td colspan="6" class="ob_font" style="text-align: center;">暂无数据!</td>
    	</tr>
</script>