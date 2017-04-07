<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page isELIgnored="true"%>
<%@ include file="/WEB-INF/jsp/pub/tags.inc" %>
<base href="${CONTEXT}" >
<div class="system-modal">
    <section class="header"><span>组织管理</span>
        <div class="right-icon">
           <gd:btn btncode="BtnSysOrgAddCompany">
				<a href="javascript:;" id="addCompany"><i class="fa fa-plus-square"></i>新增公司</a>
           </gd:btn>
           <gd:btn btncode="BtnSysOrgAddMarket">
           		<a href="javascript:;" id="addMarket"><i class="fa fa-plus-square"></i>新增市场</a>
           </gd:btn>
           <gd:btn btncode="BtnSysOrgAddDepartment">
           		<a href="javascript:;" id="addDepartment"><i class="fa fa-plus-square"></i>新增部门</a>
           </gd:btn>
        </div>
    </section>
    
    <div class="panel-body clearfix">
        <div class="zTreeDemoBackground left fl" style="margin: 0 20px;">
            <ul id="treeDemo" class="ztree" style="width:350px;"></ul>
        </div>
        <div class="adv-table editable-table fl" style="width: 60%;">
            <div class="space15"></div>
            <table class="table table-striped table-hover t-custom" id="tb">
                <thead>
                <tr class="ml-ct">
                    <th>用户账号</th>
                    <th>姓名</th>
                    <th>岗位</th>
                    <th>手机</th>
                    <th>邮箱</th>
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
<div id="popwinCompany" style="display:none;">
	<form id="companyForm" class="form-horizontal validate-error">
	<div class="form-group">
		<label class="control-label col-md-3">上级组织:</label>
		<div class="col-md-6">
			<p class="form-control-static" name="parentName"></p>
		</div>
	</div>
	<div class="form-group">
		<label class="control-label col-md-3">组织类型:</label>
		<div class="col-md-6">
			<p name="typeName" class="form-control-static">公司</p>
		</div>
	</div>
	<div class="form-group">
		<label class="control-label col-md-3"><i class="payment-cycle-sp">*</i>组织全称:</label>
		<div class="col-md-6">
			<p><input type="text" name="fullName" class="form-control" maxlength="50" required/></p>
		</div>
	</div>
	<div class="form-group">
		<label class="control-label col-md-3">组织简称:</label>
		<div class="col-md-6">
			<input type="text" name="shortName" maxlength="50" class="form-control"/>
		</div>
	</div>
	<div class="form-group">
		<label class="control-label col-md-3">说明:</label>
		<div class="col-md-6">
			<textarea rows="5" name="remark" maxlength="100" cols="50" class="text-area"></textarea>
		</div>
	</div>
	<input type="hidden" name="parentId"/>
	<input type="hidden" name="id"/>
	<input type="hidden" name="type"/>
	</form>
</div>


<div id="popwinMarket" style="display:none; ">
	<form id="marketForm" class="form-horizontal validate-error">
	<div class="form-group">
		<label class="control-label col-md-3"><i class="payment-cycle-sp">*</i>市场编号:</label>
		<div class="col-md-6">
			<p><input type="text" name="marKetEntity.code" class="form-control" maxlength="32" required /></p>
		</div>
	</div>
	<div class="form-group">
		<label class="control-label col-md-3"><i class="payment-cycle-sp">*</i>市场全称:</label>
		<div class="col-md-6">
			<p><input type="text" name="fullName" class="form-control" maxlength="64" required/></p>
		</div>
	</div>
	<div class="form-group">
		<label class="control-label col-md-3"><i class="payment-cycle-sp">*</i>市场简称:</label>
		<div class="col-md-6">
			<input type="text" name="shortName" class="form-control" maxlength="64" required/>
		</div>
	</div>
	<div class="form-group">
		<label class="control-label col-md-3">所属公司:</label>
		<div class="col-md-6">
			<p name="parentName" class="form-control-static"></p>
		</div>
	</div>
	<div class="form-group">
		<label class="control-label col-md-3">组织类型:</label>
		<div class="col-md-6">
			<p class="form-control-static">市场</p>
		</div>
	</div>
	<div class="form-group">
		<label class="control-label col-md-3">开业日期:</label>
		<div class="col-md-6 date-form">
			<input id="openTime"  name="marKetEntity.openTime" type="text" class="form-control" readonly/>
			<i class="fa fa-calendar"></i>
		</div>
	</div>
	<div class="form-group">
		<label class="control-label col-md-3">市场状态:</label>
		<div class="col-md-6">
			<select class="form-control wid100" name="marKetEntity.marketStatus">
				<option value="">请选择</option>
				<option value="1">运营中</option>
				<option value="2">筹备中</option>
				<option value="3">关闭</option>
			</select>
		</div>
	</div>
	<div class="form-group">
		<label class="control-label col-md-3"><i class="payment-cycle-sp">*</i>所在地址:</label>
		<div class="col-md-6">
			<select class="form-control" name ="marKetEntity.provinceId" style="width:100px;display:inline-block;" id="province" required></select>&nbsp;省
			<select class="form-control" name ="marKetEntity.cityId" style="width:100px;display:inline-block;" id="city" required></select>&nbsp;市
			<select class="form-control" name ="marKetEntity.areaId" style="width:100px;display:inline-block;" id="area" required></select>&nbsp;区
		</div>
	</div>
	<input type="hidden" name="parentId"/>
	<input type="hidden" name="type"/>
	<input type="hidden" name="id"/>
	
	<div class="form-group">
		<label class="control-label col-md-3"></label>
		<div class="col-md-6">
			<textarea rows="5" cols="50" maxlength="200" required name="marKetEntity.address" class="text-area" placeholder="详细地址"></textarea>
		</div>
	</div>
	</form>
</div>
<!-- page end-->
<script id="template" type="text/x-jquery-tmpl">
	{{each(i, user) orgs}}
		<tr class="ml-ct">
			<td>${user.code}</td>
			<td>${user.name}</td>
			<td>${user.postName}</td>
			<td>${user.mobile}</td>
			<td>${user.mail}</td>
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
    	</tr>
	{{/each}}
 
</script>

<script id="areaTemplate" type="text/x-jquery-tmpl">
	<option value="${areaId}">${areaName}</option>
</script>