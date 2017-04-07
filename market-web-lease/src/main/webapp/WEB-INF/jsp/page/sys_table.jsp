<style>
      /*重构样式*/
    .table.table ,.ml-ct th ,.form-inline .form-control { text-align: center !important;}
    .adv-table .dataTables_filter label input { width: 90%!important;}
</style>

<!-- page start-->

<div class="row">
    <div class="col-sm-12">
        <section class="panel">
            <header class="panel-heading">
                谷登科技
                <span class="tools pull-right">
                    <a href="javascript:;" class="fa fa-chevron-down"></a>
                    <a href="javascript:;" class="fa fa-cog"></a>
                    <a href="javascript:;" class="fa fa-times"></a>
                 </span>
            </header>
            <div class="panel-body">
                <div class="adv-table editable-table ">
                    <div class="clearfix">
                        <div class="btn-group">
                            <button id="editable-sample_new" class="btn btn-primary">
                                添加 <i class="fa fa-plus"></i>
                            </button>
                        </div>
                    </div>
                    <div class="space15"></div>
                    <table class="table table-striped table-hover table-bordered" id="tb">
                        <thead>
                        <tr class="ml-ct">
                            <th>谷登产品</th>
                            <th>谷登价格</th>
                            <th>发布日期</th>
                            <th>到期日期</th>
                            <th>编辑状态</th>
                            <th>表格操作</th>
                        </tr>
                        </thead>
                        <tbody id="templateBody">
                        </tbody>
                    </table>
                    <div class="row-fluid">
                        <div class="col-lg-6">
                            <div id="pageinfo" class="dataTables_info">总共<span id="total"></span>条记录，
                                每页显示<span id="length"></span>条数据</div>
                        </div>
                        <div class="col-lg-6" id="pagebar">
                            
                        </div>
                    </div>
                </div>
            </div>
        </section>
    </div>
</div>
<!-- page end-->
<script id="template" type="text/x-jquery-tmpl">
    <tr class="ml-ct">
        <td>${ID}</td>
        <td>${ID}</td>
        <td>${}</td>
        <td class="center">2016-10-20</td>
        <td><a class="edit" href="javascript:;">编辑</a></td>
        <td><a class="delete" href="javascript:;">删除</a></td>
    </tr>
</script>