<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="gd" uri="http://www.gdeng.cn/gd"%>
<style>
	table > tbody > tr:hover{
		background:#fff !important;
	}
	.table> tbody> tr> td, .table> tfoot> tr> td, .table> thead> tr> td, table> tbody> tr> td, table> tfoot> tr> td, table> thead> tr> td{
		border-top:none !important;
	}
</style>
<section class="header">编辑资源类型</section>
<div class="form-page">
	<form id="form-editMarketResourceType" class="form-horizontal clearfix">
		<!-- 隐藏区域 -->
		<input name="id" type="hidden">
		<table style="width: 1110px;">
			<tr>
				<td>
					<div class="form-group">
						<label class="control-label col-md-5"> <i
							class="payment-cycle-sp">*</i>资源类型编码：
						</label>
						<div class="col-md-3 text-left">
							<input name="code" type="text" class="form-control"
								placeholder="1-50个字符" required maxlength="20">
						</div>
					</div>
					<div class="form-group">
						<label class="control-label col-md-5"> <i
							class="payment-cycle-sp">*</i>资源类型名称：
						</label>
						<div class="col-md-3 text-left">
							<input name="name" type="text" class="form-control"
								placeholder="1-50个字符" required maxlength="50">
						</div>
					</div>
				</td>
			</tr>
			<tr>
				<td>
					<div class="form-group">
						<label class="control-label col-md-5"> <i
							class="payment-cycle-sp">*</i>优先级序号：
						</label>
						<div class="col-md-3 text-left">
							<input name="sort" type="number" class="form-control"
								placeholder="请输入数字" maxlength="10">
						</div>
					</div>
				</td>
			</tr>
		</table>
		<!-- <div class="form-group col-lg-5">
			<label class="control-label col-md-4">
					<i class="payment-cycle-sp">*</i>所属市场： 
				</label>
			<div class="col-md-6 text-left">
				<select name ="marketId" class="form-control" >
				</select>
			</div>
		</div> -->


		<div class="form-group col-lg-12 text-center mtop30">
			<button data-dismiss="modal" class="btn btn-default" type="button"
				onclick="javascript:window.history.go(-1)">取消</button>
			<gd:btn btncode="BtnMarketResourceTypeEdit">
				<button class="btn btn-danger ml15" id="saveForm" type="submit"
					data-dismiss="modal">确认</button>
			</gd:btn>
		</div>
	</form>
</div>

<div class="panel-body">
	<div class="zTreeDemoBackground left">
		<ul id="treeDemo" class="ztree"></ul>
	</div>
</div>