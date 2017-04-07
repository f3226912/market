<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page isELIgnored="true"%>
<style>
      /*重构样式*/
    .table.table ,.ml-ct th ,.form-inline .form-control { text-align: center !important;}
    .adv-table .dataTables_filter label input { width: 90%!important;}
</style>
<section class="header">新增公司</section>
<div class="form-page">
	<div style="height:80px;"></div>
	<form id="form-addCompany" action="" class="form-horizontal clearfix">
		<div class="form-group col-lg-10">
			<label class="control-label col-md-4">
					<i class="payment-cycle-sp">*</i>公司名称： 
				</label>
			<div class="col-md-6 text-left">
				<input id="fullName" name="fullName" maxlength="50" required type="text" class="form-control" value="">
			</div>
		</div>
		<input type="hidden" name="type" value="1"/>
		<div class="form-group col-lg-10 text-center mtop30 sys-btn-style">
			<button data-dismiss="modal" id="cancelForm" class="btn btn-default" type="button">取消</button>
			<button class="btn btn-danger ml15" id="saveForm" type="button" data-dismiss="modal">保存</button>
		</div>
	</form>
</div>

<script type="text/javascript">

</script>
