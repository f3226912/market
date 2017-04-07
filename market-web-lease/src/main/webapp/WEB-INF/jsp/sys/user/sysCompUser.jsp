<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/pub/tags.inc" %>
<%@ page isELIgnored="true"%>
<style>
#pagebar{ text-align:center;}
#pagebar .fl{ margin-top:10px;}
 .dataTables_paginate.paging_bootstrap.pagination{float:none !important;}
</style>
<div class="system-modal">
    <section class="header"><span>用户管理</span>
        <div class="right-icon">
            <gd:btn btncode="BtnSysUserAdd"><a href="index#jumpToCompUserAdd"><i class="fa fa-plus-square"></i>新增</a></gd:btn>
<!--             <a href="index#tree"><i class="fa fa-download"></i>导出</a> -->
        </div>
    </section>
    <div class="col-md-max">
      <div class="main-search">
           <div class="main-ipt-types">
              <form class="form-input clearfix">
                <div class="form-group">
					<input id="code" id="code" type="text" class="form-control wid150" maxlength="20" placeholder="请输入用户账号">
				</div>
				<div class="form-group">
					<input id="name" name="name" type="text" class="form-control wid150" maxlength="20" placeholder="请输入用户姓名">
				</div>
				<div class="form-group">
					<input id="postName" name="postName" type="text" class="form-control wid150" maxlength="20" placeholder="请输入所属岗位">
				</div>
                <div class="form-group">
                    <gd:btn btncode="BtnSysUserQuery"><button id="query" class="btn btn-danger" type="button">查询</button></gd:btn>
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
	                    <th>用户账号</th>
	                    <th>姓名</th>
	                    <th>所属公司</th>
	                    <th>所属市场</th>
	                    <th>所属部门</th>
	                    <th>岗位</th>
	                    <th>手机号码</th>
	                    <th>状态</th>
	                    <th class="center">操作</th>
                    </tr>
                    </thead>
                    <tbody id="userBody">
                    </tbody>
                </table>

                <!-- 分页组件 -->
                <div id="pagebar"></div>
            </div>
        </div>
</div>

<!-- page end-->
<!-- page end-->
<script id="userInfoScript" type="text/x-jquery-tmpl">
	{{each(i, user) users}}
		<tr class="ml-ct">
			<td>${i+1}</td>
			<td>${user.code}</td>
			<td>${user.name}</td>
			<td style="word-wrap: break-word;">${user.companyName}</td>
			<td style="word-wrap: break-word;">${user.marketName}</td>
			<td style="word-wrap: break-word;">${user.departmentName}</td>
			<td style="word-wrap: break-word;" title="${user.postName}">${user.postName}</td>
			<td>${user.mobile}</td>
			{{if user.status == 1}}
				<td>启用</td>
        	{{else user.status == 3}}
            	<td>禁用</td>
        	{{else}}
            	<td>未知</td>
			{{/if}}
			<td>
				{{if user.status == 1}}
					<gd:btn btncode="BtnSysUserDisabled"><a class="disabled" href="javascript:;" data-status="3" data-desc="禁用" data-userid="${user.id}">禁用</a></gd:btn>
        		{{else user.status == 3}}
            		<gd:btn btncode="BtnSysUserDisabled"><a class="disabled" href="javascript:;" data-status="1" data-desc="启用" data-userId="${user.id}">启用</a></gd:btn>
				{{/if}}
				<gd:btn btncode="BtnSysUserReset"><a class="reset" href="javascript:;" userId="${user.id}">重置密码</a></gd:btn>
				<gd:btn btncode="BtnSysUserDetail"><a class="detail" href="javascript:(function(){Route.params={id:${user.id}};location.href='index#jumpToCompUserDetail';})();">详情</a></gd:btn>
				<gd:btn btncode="BtnSysUserEdit"><a class="edit" href="javascript:(function(){Route.params={id:${user.id}};location.href='index#jumpToCompUserEdit';})();">编辑</a>&nbsp;</gd:btn>
				<gd:btn btncode="BtnSysUserDelete"><a class="delete" href="javascript:;" userId="${user.id}">删除</a></gd:btn>
			</td>
    	</tr>
	{{/each}}

</script>

<script id="noDataScript" type="text/x-jquery-tmpl">
		<tr class="charges-tab-blank">
			<td colspan="10" class="ob_font" style="text-align: center;">暂无数据!</td>
    	</tr>
</script>
