<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page isELIgnored="true"%>
<style>
      /*重构样式*/
    .table.table ,.ml-ct th ,.form-inline .form-control { text-align: center !important;}
    .adv-table .dataTables_filter label input { width: 90%!important;}
</style>
<div class="system-modal">
    <section class="header"><span>公司管理</span>
        <div class="right-icon">
            <a href="javascript:;" id="addCompany"><i class="fa fa-plus-square"></i>新增</a>
        </div>
    </section>
    <div class="col-md-max">
      <div class="main-search">
           <div class="main-ipt-types">
              <form id="querySearch" class="form-input clearfix">
                <div class="form-group">
                    <input id="fullName" type="text" class="form-control wid150" maxlength="50" placeholder="公司名称">
                </div>
                <div class="form-group">
                    <button id="queryCompany" class="btn btn-danger" type="button">查询</button>
                </div>
                
            </form>
           </div>
       </div>
    </div>
</div>
<div>

            <div class="panel-body">
                <div class="adv-table editable-table ">
                    <table class="table table-striped table-hover t-custom" id="tb">
                        <thead>
                        <tr class="ml-ct">
                            <th>NO.</th>
                            <th>公司名称</th>
                            <th>操作</th>

                        </tr>
                        </thead>
                        <tbody id="templateBody">
                        </tbody>
                    </table>

                    <!-- 分页组件 -->
                    <div id="pagebar"></div>
                </div>

</div>
<!-- page end-->
<script id="template" type="text/x-jquery-tmpl">
    
	{{each(i, org) orgs}}
		<tr class="ml-ct">
			<td>${i+1}</td>
			<td>${org.fullName}</td>

			<td><a class="edit" orgId="${org.id}" href="javascript:;">编辑</a>
			</td>
    	</tr>
	{{/each}}

</script>