<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page isELIgnored="true"%>
<div class="system-modal">
    <section class="header"><span>用户管理</span>
        <div class="right-icon">
            <a href="index#jumpToPlatUserAdd"><i class="fa fa-plus-square"></i>新增</a>
        </div>
    </section>
    <div class="col-md-max">
      <div class="main-search">
           <div class="main-ipt-types">
              <form id="searchForm" class="form-input clearfix">
                <div class="form-group">
				<input id="code" name="code" type="text" class="form-control wid150" maxlength="20" placeholder="请输入用户账号">
				</div>
				<div class="form-group">
					<input id="name" name="name" type="text" class="form-control wid150" maxlength="20" placeholder="请输入用户姓名">
				</div>
				<div class="form-group">
					<input id="mobile" name="mobile" type="text" class="form-control wid150" maxlength="20" placeholder="手机号码">
				</div>
				<div class="form-group">
					<input id="companyName" name="companyName" type="text" class="form-control wid150" maxlength="50" placeholder="所属公司">
				</div>
				<div class="form-group">
					<select name="type" class="form-control wid150">
						<option value="">请选择</option>
						<option value="1">平台</option>
						<option value="2">公司</option>
					</select>
				</div>
                <div class="form-group">
                    <button id="query" class="btn btn-danger" type="button">查询</button>
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
	                    <th>手机号码</th>
	                    <th>账号类型</th>
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
<script id="userInfoScript" type="text/x-jquery-tmpl">
	{{each(i, user) users}}
		<tr class="ml-ct">
			<td>${i+1}</td>
			<td>${user.code}</td>
			<td>${user.name}</td>
			<td>
				{{if user.groupId == '0'}}
					谷登平台
				{{else}}
					${user.groupName}
				{{/if}}
			</td>
			<td>${user.mobile}</td>
			<td>
				{{if user.type == '1'}}
					平台
				{{else user.type == '2'}}
					公司
				{{else user.type == '3'}}
					普通用户
				{{/if}}
			</td>
			<td>
				{{if user.status == '0'}}
					<span class="text-muted">已删除</span>
				{{else user.status == '2'}}
					<span class="text-muted">锁定</span>
				{{else user.status == '3'}}
					<span class="text-muted">禁用</span>
				{{else user.status == '1'}}
					<a class="text-danger" href="javascript:;">启用</a>&nbsp;
				{{/if}}
			</td>
			<td type="${user.type}">
				{{if user.status == 1}}
					<a class="disabled" href="javascript:;" data-status="3" data-desc="禁用" data-userid="${user.id}">禁用</a>
        		{{else user.status == 3}}
            		<a class="disabled" href="javascript:;" data-status="1" data-desc="启用" data-userId="${user.id}">启用</a>
				{{/if}}
				<a class="reset" href="javascript:;" userId="${user.id}">重置密码</a>
				<a class="detail" href="javascript:(function(){Route.params={id:${user.id}};location.href='index#jumpToPlatUserDetail';})();">详情</a>
				<a class="edit" href="javascript:(function(){Route.params={id:${user.id}};location.href='index#jumpToPlatUserEdit';})();">编辑</a>&nbsp;
				<a class="delete" href="javascript:;" userId="${user.id}">删除</a>
			</td>
    	</tr>
	{{/each}}

</script>
