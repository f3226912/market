<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page isELIgnored="true"%>
<%@ include file="/WEB-INF/jsp/pub/tags.inc" %>
<section class="header">调整租赁资源</section>
<div class="form-page">
	<form action="#" class="form-horizontal clearfix">
		<div class="contract-info clearfix">
			<div class="contract-title-block">
				<span class="contract-info-h3" id="buildName"> </span>
				<div class="right-icon mtop15">
					<a><input id="selectAll" type="checkbox">全选</a>
					<gd:btn btncode="BtnSetTypeAndArea">
					<a id="setResourceType"><i class="fa fa-cog"></i>资源类型及面积设置</a>
					</gd:btn>
					<gd:btn btncode="BtnResourceSplit">
					<a id="splitResource"><i class="fa fa-files-o"></i>租赁资源拆分</a>
					</gd:btn>
					<gd:btn btncode="BtnResourceMerge">
					<a id="mergeResource"><i class="fa fa-columns"></i>租赁资源合并</a>
					</gd:btn>
					<gd:btn btncode="BtnDelResourcesByIds">
					<a id="deleteResource"><i class="fa fa-trash-o"></i>删除</a>
					</gd:btn>
				</div>
			</div>
			<div class="step-table set-layer-main">
			<div class="layer-table mtop30">
				<div class="title-table">
				<table >
					<thead>
						<tr>
							<th>楼层名称</th>
						</tr>
					</thead>
					<tbody id="floorTitle">
						
					</tbody>
				</table>
				</div>
				<div class="cont-table">
				<table class="">
					<thead id="unitTitle">
					</thead>
					<tbody id="resource">
						
					</tbody>
				</table>
				</div>
			</div>
				<div class="table-tip">
					<p>当前租赁资源状态： <span class="daichuzu-tip">待放租</span><span class="daizu-tip">待租</span> <span class="yizu-tip">已租</span>          </p>
					<p>当前租赁资源类型： <span class="shop-tips">商铺</span> <span class="park-tips">停车位</span> </p>
				</div>
			</div>
		</div>
	</form>
</div>


<!-- 资源类型及面积设置 -->
<div class="modal fade" id="modal-setResourceTypeArea" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
	<div class="modal-dialog" style="width: 800px;">
		<div class="modal-content">
			<div class="modal-header">
				<button type="button" class="close" data-dismiss="modal" aria-hidden="true">
					&times;
				</button>
				<h4 class="modal-title" id="myModalLabel">
					资源类型及面积设置
				</h4>
			</div>
			<div class="modal-body">
				<div class="form-page">
					<form id="form-setResourceTypeArea"  class="form-horizontal clearfix">
						<div class="form-group col-lg-5">
							<label class="control-label col-md-6">
								建筑面积： 
							</label>
							<div class="col-md-6 text-left">
								<input name="builtArea" type="number" class="form-control">
							</div>
						</div>
						<div class="form-group col-lg-5">
							<label class="control-label col-md-6">
								套内面积： 
							</label>
							<div class="col-md-6 text-left">
								<input name="innerArea" type="number" class="form-control">
							</div>
						</div>
						<div class="form-group col-lg-5">
							<label class="control-label col-md-6">
								可出租面积： 
							</label>
							<div class="col-md-6 text-left">
								<input name="leaseArea" type="number" class="form-control">
							</div>
						</div>
						<div class="form-group col-lg-5">
							<label class="control-label col-md-6">
								<i class="payment-cycle-sp">*</i>资源类型： 
							</label>
							<div class="col-md-6 text-left">
								<select name="resourceTypeId"  class="form-control">
										<option value="1">商    铺</option>
										<option value="2">停车位</option>
								</select>
							</div>
						</div>
					</form>
				</div>
			</div>
			<div class="modal-footer">
				<button type="button" class="btn btn-default" data-dismiss="modal">取消
				</button>
				<button type="button" class="btn btn-primary" id="saveResourceTypeAndArea">
					保存
				</button>
			</div>
		</div>
	</div>
</div>
<!-- 资源拆分 -->
<div class="modal fade" id="modal-resourceSplit" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
	<div class="modal-dialog" style="width: 900px;">
		<div class="modal-content">
			<div class="modal-header">
				<button type="button" class="close" data-dismiss="modal" aria-hidden="true">
					&times;
				</button>
				<h4 class="modal-title" id="myModalLabel">
					租赁资源拆分
				</h4>
			</div>
			<div class="modal-body">
				<form id="form-splitLeaseValidateTime"  class="form-horizontal" role="form">
					<div class="form-group">
						<label class="control-label" for="splitLeaseValidateTime">
							<!-- <i class="payment-cycle-sp">*</i> -->&nbsp;变更日期： 
						</label>
						<div class=" inline-block date-icon form_datetime-component">
							<input id="splitLeaseValidateTime" type="text" class="form-control data-date wid200" readonly="" size="16" data-date-format="yyyy-mm-dd">
							<i class="fa fa-calendar"></i>
						</div>
					</div> 
				</form>
				<div>
					<table class="table table-bordered">
						<caption>原租赁资源</caption>
						<thead>
							<tr>
								<th>资源简码</th>
								<th>建筑面积m2</th>
								<th>套内面积m2</th>
								<th>计租模式</th>
								<th>可出租面积m2</th>
								<th>资源类型</th>
							</tr>
						</thead>
						<tbody id="originSplitResource">
							
						</tbody>
					</table>
				</div>
				<div>
					<table class="table table-bordered">
						<caption>
							<span>拆分后租赁资源</span>
							<a href="javascript:void(0);" id="deleteSplitResource" style="float:right;margin-right: 10px;">删除租赁资源</a>
							<a href="javascript:void(0);" id="addSplitResource" style="float:right;margin-right: 10px;">增加租赁资源</a>
						</caption>
						<thead>
							<tr>
								<th><i class="payment-cycle-sp">*</i>资源简码</th>
								<th>建筑面积m2</th>
								<th>套内面积m2</th>
								<th width="120">计租模式</th>
								<th>可出租面积m2</th>
								<th width="120"><i class="payment-cycle-sp">*</i>资源类型</th>
							</tr>
						</thead>
						<tbody id="splitResources">
							<tr>
								<td><input name="splitShortCode" 		type="text" class="form-control"></td>
								<td><input name="splitBuiltArea" 		type="number" class="form-control"></td>
								<td><input name="splitInnerArea" 		type="number" class="form-control"></td>
								<td>
									<select name="splitRentMode" class="form-control">
										<option value="1">指定金额</option>
										<option value="2">手工录入</option>
										<option value="3">建筑面积</option>
										<option value="4">套内面积</option>
										<option value="5">可出租面积</option>
									</select>
								</td>
								<td><input name="splitLeaseArea" 		type="number" class="form-control"></td>
								<td>
									<select name="splitResourceTypeId"  class="form-control">
										<option value="1">商    铺</option>
										<option value="2">停车位</option>
									</select>
								</td>
							</tr>
						</tbody>
					</table>
				</div>
			</div>
			<div class="modal-footer">
				<button type="button" class="btn btn-default" data-dismiss="modal">
					取消
				</button>
				<button type="button" class="btn btn-primary" id="saveResourceSplit">
					保存
				</button>
			</div>
		</div>
	</div>
</div>
<!--  资源合并  -->
<div class="modal fade" id="modal-resourceMerge" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
	<div class="modal-dialog" style="width: 900px;">
		<div class="modal-content">
			<div class="modal-header">
				<button type="button" class="close" data-dismiss="modal" aria-hidden="true">
					&times;
				</button>
				<h4 class="modal-title" id="myModalLabel">
					租赁资源合并
				</h4>
			</div>
			<div class="modal-body">
				<form id="form-mergeLeaseValidateTime"  class="form-horizontal" role="form">
						<div class="form-group">
							<label class="control-label" for="mergeLeaseValidateTime">
								<!-- <i class="payment-cycle-sp">*</i> -->&nbsp;变更日期： 
							</label>
							<div class=" inline-block date-icon form_datetime-component">
								<input id="mergeLeaseValidateTime" type="text" class="form-control data-date wid200" readonly="" size="16" data-date-format="yyyy-mm-dd">
								<i class="fa fa-calendar"></i>
							</div>
						</div> 
				</form> 
				<div>
					<table class="table table-bordered">
						<caption>原租赁资源</caption>
						<thead>
							<tr>
								<th>资源简码</th>
								<th>建筑面积m2</th>
								<th>套内面积m2</th>
								<th>计租模式</th>
								<th>可出租面积m2</th>
								<th>资源类型</th>
							</tr>
						</thead>
						<tbody id="originMergeResource">
							
						</tbody>
					</table>
				</div>
				<div>
					<table class="table table-bordered">
						<caption>
							<span>合并后租赁资源</span>
						</caption>
						<thead>
							<tr>
								<th><i class="payment-cycle-sp">*</i>资源简码</th>
								<th>建筑面积m2</th>
								<th>套内面积m2</th>
								<th width="120">计租模式</th>
								<th>可出租面积m2</th>
								<th width="120"><i class="payment-cycle-sp">*</i>资源类型</th>
							</tr>
						</thead>
						<tbody id="mergeResource">
							<tr>
								<td><input name="shortCode" type="text" class="form-control"></td>
								<td><input name="builtArea" type="number" class="form-control"></td>
								<td><input name="innerArea" type="number" class="form-control"></td>
								<td>
									<select name="rentMode" class="form-control">
										<option value="1">指定金额</option>
										<option value="2">手工录入</option>
										<option value="3">建筑面积</option>
										<option value="4">套内面积</option>
										<option value="5">可出租面积</option>
									</select>
								</td>
								<td><input name="leaseArea" type="number" class="form-control"></td>
								<td>
									<select name="resourceTypeId"  class="form-control">
										<option value="1">商    铺</option>
										<option value="2">停车位</option>
									</select>
								</td>
							</tr>
						</tbody>
					</table>
				</div>
			</div>
			<div class="modal-footer">
				<button type="button" class="btn btn-default" data-dismiss="modal">取消
				</button>
				<button type="button" class="btn btn-primary" id="saveResourceMerge">
					保存
				</button>
			</div>
		</div>
	</div>
</div>



<!-- 楼层 -->
<script id="floorTitleTemp" type="text/x-jquery-tmpl">
{{each(i, floor) floors}}
	<tr>
		<td>${floor.floorName}</td>
	</tr>
{{/each}}
</script>
<!-- 单元 -->
<script id="unitTitleTemp" type="text/x-jquery-tmpl">
	<tr>
		{{each(i, unit) units}}
			<th width="250">${unit.unitName}</th>
		{{/each}}
	</tr>
</script>
<!--租赁资源-->
<script id="resourceTemp" type="text/x-jquery-tmpl">
	{{each(i, resource) resources}}
		<tr>
			{{each(j, floorSrc) resource.floorSrcs}}
				<td nowrap="nowrap">
				{{each(h, unitfloorSrc) floorSrc.unitfloorSrcs}}
					{{if unitfloorSrc.leaseStatus == 1}}
					<div class="lease-resoure">
					{{else unitfloorSrc.leaseStatus == 2}}
					<div class="lease-resoure daizu-flag">
					{{else unitfloorSrc.leaseStatus == 3}}
					<div class="lease-resoure yizu-flag">
					{{/if}}
					<i class="lease-flag"></i>
					<label class="lease-label">
						{{if unitfloorSrc.leaseStatus == 1}}
						<input type="checkbox" id=${unitfloorSrc.id} value=${unitfloorSrc.id}>
						{{else}}
						<input type="checkbox" disabled=true id=${unitfloorSrc.id} value=${unitfloorSrc.id}>
						{{/if}}
						
						${unitfloorSrc.shortCode}
					</label>
					{{if unitfloorSrc.resourceTypeId == 2}}
					<i class="park-icon"></i>
					{{else unitfloorSrc.resourceTypeId == 1}}
					<i class="park-icon shops-icon"></i>
					{{/if}}
					</div>
				{{/each}}
				</td>
			{{/each}}
		</tr>
	{{/each}}
</script>