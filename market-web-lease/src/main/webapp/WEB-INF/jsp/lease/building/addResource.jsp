<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ page isELIgnored="true"%>
<%@ taglib prefix="gd" uri="http://www.gdeng.cn/gd"%>
<section class="header">生成租赁资源</section>
<div class="form-page">
	<form action="#" class="form-horizontal clearfix" id="">
		<div class="contract-info clearfix " id="step1">
			<div class="contract-title-block">
				<span class="contract-info-h3">第一步：设置楼层及单元数目 </span>
			</div>
			<div class="resour-group col-lg-5">
				<label class="control-label col-md-4"> 楼栋编码： </label>
				<div class="col-md-8 text-left">
					<input type="text" class="form-control" id="builCode" value="">
				</div>
			</div>
			<div class="resour-group col-lg-5">
				<label class="control-label col-md-4"><i
					class="payment-cycle-sp">*</i>楼栋层数： </label>
				<div class="col-md-8 text-left">
					<input type="text" class="form-control layerNum" id="buildingCount">
					<label class="error" style="margin:0;display:block"></label>
				</div>
			</div>
			<div class="resour-group col-lg-5">
				<label class="control-label col-md-4"> </i>楼栋名称：
				</label>
				<div class="col-md-8 text-left">
					<input type="text" class="form-control" value="" id="buildName">
				</div>
			</div>
			<div class="resour-group col-lg-5">
				<label class="control-label col-md-4"> <i
					class="payment-cycle-sp">*</i>单元数目：
				</label>
				<div class="col-md-8 text-left">
					<input type="text" class="form-control unitNum" id="unitCount">
					<label class="error" style="margin:0;display:block"></label>
				</div>
			</div>
			<div class="form-group col-lg-12 text-center mtop15 mbot30">
				<button class="btn btn-default" type="button" id="step1NextCancel">取消</button>
				<button class="btn btn-primary ml15 next-step" type="button"
					id="step1Next">下一步</button>

			</div>
		</div>
		<!-- </form> -->
		<!--page end-->
			<!-- <form action="#" class="form-horizontal clearfix"> -->
		<div class="contract-info clearfix hidden" id="step2">
			<div class="contract-title-block">
				<span class="contract-info-h3">第二步：设置楼层名称及每层数 </span>
			</div>
			<div style="overflow-x:auto">
			<div class="step-table">
				<table>
					<thead id="templateHead"></thead>
					<tbody id="templateBody">
					</tbody>
				</table>
			</div>
		    </div>
			<div class="form-group col-lg-12 text-center mtop15 mbot30">
				<button class="btn btn-default" type="button" id="step2NextCancel">取消</button>
				<button class="btn btn-primary ml15 prev-step" type="button"
					id="step2Prev">上一步</button>
				<button class="btn btn-primary ml15 next-step" type="button"
					id="step2Next">下一步</button>
			</div>
		</div>
	<!-- 	</form> -->
		<!--第三步-->
		<!-- <form action="#" class="form-horizontal clearfix"> -->
		<div class="contract-info clearfix hidden" id="step3">
			<div class="contract-title-block">
				<span class="contract-info-h3">第三步：设置楼层名称及每层数 </span>
			</div>
			<div class="step-table set-layer-main">
				<section class="form-inline">
					<span>资源简码生成规则：</span> 
					<select id="rule" class="form-control wid200">
						<option value="2">楼层+顺序号（按单元＋楼层）</option>
						<option value="1">顺序号（按单元＋楼层）</option>
						<option value="3">楼层+顺序号（按楼栋）</option>
					</select>
					<select id="formatNo" class="form-control wid200">
						<option value="2">两位</option>
						<option value="3">三位</option>
					</select>

					<p class="tip-show">
						选择上述规则产生的资源编码示例：<em class="red-tip">市场－区域－楼栋－单元－层－资源编码</em>
					</p>
					<p>
						<span class="hidetext">选择上述规则产生的资源编码示例：</span><em class="red-tip">whbsc-A区－1-1－101</em>
					</p>
				</section>

				<div class="layer-table mtop30">
					<div class="title-table">
						<table>
							 <thead>
								<!-- <tr>
									<th>楼层名称</th>
								</tr> -->
							</thead> 
							<tbody id="titTableBody">

							</tbody>
						</table>
					</div>
					<div class="cont-table">
						<table id="houseTb">
							<thead id="contUnitBody">
								<!-- <tr>
									<th>A单元</th>
									<th>B单元</th>
									<th>C单元</th>
									<th>D单元</th>
								</tr> -->
							</thead>
							<tbody id="contTableBody">

							</tbody>
						</table>
					</div>
				</div>
			</div>
			<div class="form-group col-lg-12 text-center mtop15 mbot30">
				<button class="btn btn-default" type="button" id="cancel">取消</button>
				<button class="btn btn-primary ml15 prev-step" type="button"
					id="step3Prev">上一步</button>
				<gd:btn btncode="BtnBuilBatchAdd"><button class="btn btn-danger ml15" type="button" id="confirm">确认</button></gd:btn>
			</div>
		</div>
	</form>
</div>
<!--page end-->

<!--第二步中列表-->
<script id="template02Tit" type="text/x-jquery-tmpl">
	
<tr>
	<th rowspan="2" class="first-row-span">
		<div class="row-span">单元名称</div>
		<div class="col-span">楼层名称 </div> 
		<canvas id="myCanvas" width="100%" height="100%" "></canvas>
	</th>

	{{each(a,unit) unitCount}}
		<th><input type="text" class="form-control wid150" name="unitName"><span class="error-tips"></span></th>
	{{/each}}
</tr>

<tr>
   {{each(b,unit) unitCount}}
	<th>户数</th>
   {{/each}}
</tr>

</script>
<!--第二步中户数-->
<script id="template" type="text/x-jquery-tmpl">

{{each(c,building) buildingCount}}
      <tr>
	     <td nowrap="nowrap"><input type="text" class="form-control wid150"   name="building" value="${building}"><span class="error-tips"></span></td>
	 {{each(d,unit) unitCount}}
		<td nowrap="nowrap"><input type="text" class="form-control wid150" name="householdsNum" value=""><span class="error-tips"></span><i class="step-icon"></i></td>
	 {{/each}}
	</tr>
{{/each}}

</script>
<!--第三步中的楼层列表-->
<script id="templateTit" type="text/x-jquery-tmpl">
<tr>
	<th rowspan="2" class="first-row-span">
		<div class="col-span">楼层名称 </div> 
	</th>
	<tr>

{{each(c,builName) buildingNames}}
      <tr>
	     <td><input type="text" readonly class="form-control" name="builName" id="builName" value="${builName}"></td>
	 </tr>
{{/each}}
</script>
<!--第三步中的单元列表-->
<script id="templateUit" type="text/x-jquery-tmpl">
<tr>
{{each(b,unitNam) unitNames}}
	<th><input type="text" readonly class="form-control wid150" name="unitNameTree" id="unitName" value="${unitNam}"></th>
{{/each}}
    </tr>

</script>