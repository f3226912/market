<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page isELIgnored="true"%>
<%@ taglib prefix="gd" uri="http://www.gdeng.cn/gd"%>
<section class="header">资源详情</section>
<div class="form-page ditail-resoure-page">
	<section id="otherResource" class="panel">
		<header class="">
			<ul class="nav nav-tabs">
				<li class="active"><a data-toggle="tab" href="#home">基本资料</a></li>
				<li id="leaseOrderBtn" class=""><a data-toggle="tab" href="#about">租赁单</a></li>
			</ul>
		</header>
		<div class="panel-body">
			<div class="tab-content">
				<div id="home" class="tab-pane active">
					<div class="form-page mtop15">
						<form id="form-setResource" method="post" novalidate
							class="form-horizontal clearfix cmxform">
							<input type="hidden" name="id" value="" />
							<div class="contract-info clearfix">
							<div class="contract-title-block">
								<span class="contract-info-h3">基本信息 </span>
							</div>
							<div class="row">
							<div class="form-group col-lg-5">
								<label class="control-label col-md-5"> <i
									class="payment-cycle-sp">*</i>所属区域：
								</label>
								<div class="col-md-7 text-left">
									<select class="form-control" required name="areaId">
									</select>
								</div>
							</div>
							<div class="form-group col-lg-5">
								<label class="control-label col-md-5"> <i
									class="payment-cycle-sp">*</i>所属楼栋：
								</label>
								<div class="col-md-7 text-left">
									<select class="form-control" required name="budingId">
										<option value="">全部楼栋</option>
									</select>
								</div>
							</div>
							</div>
							<div class="row">
							<div class="form-group col-lg-5">
								<label class="control-label col-md-5"> <i
									class="payment-cycle-sp">*</i>资源编码：
								</label>
								<div class="col-md-7 text-left">
									<input name="code" type="text" placeholder="1-100个字符" required
										maxlength="100" class="form-control">
								</div>
							</div>
							<div class="form-group col-lg-5">
								<label class="control-label col-md-5"> <i
									class="payment-cycle-sp">*</i>资源简码：
								</label>
								<div class="col-md-7 text-left">
									<input name="shortCode" type="text" placeholder="1-100个字符"
										required maxlength="100" class="form-control">
								</div>
							</div>
							</div>
							<div class="row">
							<div class="form-group col-lg-5">
								<label class="control-label col-md-5"> <i
									class="payment-cycle-sp">*</i>租赁资源名称：
								</label>
								<div class="col-md-7 text-left">
									<input name="name" type="text" placeholder="1-100个字符" required 
										maxlength="100" class="form-control">
								</div>
							</div>
							<!-- <div class="form-group col-lg-5">
								<label class="control-label col-md-6"> <i
									class="payment-cycle-sp">*</i>单元：
								</label>
								<div class="col-md-6 text-left">
									<input name="unitId" type="number" min="1" max="20"
										placeholder="1-20之间的数值" required class="form-control">
								</div>
							</div> -->
							<div class="form-group col-lg-5">
								<label class="control-label col-md-5"> <i
									class="payment-cycle-sp">*</i>单元名称：
								</label>
								<div class="col-md-7 text-left">
									<input name=buildingUnitName type="text" placeholder="1-10个字符"  maxlength="10" required class="form-control"/>
								</div>
							</div>
							</div>
							<div class="row">
							<div class="form-group col-lg-5">
								<label class="control-label col-md-5"> <i
									class="payment-cycle-sp">*</i>建筑面积：
								</label>
								<div class="col-md-7 text-left">
									<input name="builtArea" type="number" min="1" max="100000000"
										placeholder="0-100000000" required class="form-control" /> <em
										class="unit-tips">m²</em>
								</div>
							</div>
							<!-- <div class="form-group col-lg-5">
								<label class="control-label col-md-6"> <i
									class="payment-cycle-sp">*</i>楼层：
								</label>
								<div class="col-md-6 text-left">
									<input name="floorId" type="number" min="1" max="200"
										placeholder="1-200之间的数值" required class="form-control">
								</div>
							</div> -->
							<div class="form-group col-lg-5">
								<label class="control-label col-md-5"> <i
									class="payment-cycle-sp">*</i>楼层名称：
								</label>
								<div class="col-md-7 text-left">
									<input name="unitFloorName" type="text" placeholder="1-10个字符" maxlength="10" required class="form-control"/>
								</div>
							</div>
							</div>
							<div class="row">
							<div class="form-group col-lg-5">
								<label class="control-label col-md-5"> <i
									class="payment-cycle-sp">*</i>套内面积：
								</label>
								<div class="col-md-7 text-left">
									<input name="innerArea" type="number" min="1" max="100000000"
										placeholder="0-100000000" required class="form-control">
									<em class="unit-tips">m²</em>
								</div>
							</div>
							<div class="form-group col-lg-5">
								<label class="control-label col-md-5"> <i
									class="payment-cycle-sp">*</i>可出租面积：
								</label>
								<div class="col-md-7 text-left">
									<input name="leaseArea" type="number" min="1" max="100000000"
										placeholder="0-100000000" required class="form-control">
									<em class="unit-tips">m²</em>
								</div>
							</div>
							</div>
							<div class="row">
							<div class="form-group col-lg-5">
								<label class="control-label col-md-5">
									<i class="payment-cycle-sp">*</i>资源类型：
								</label>
								<div class="col-md-7 text-left">
									<select class="form-control" required name="resourceTypeId">
									</select>
								</div>
							</div>
							<div class="form-group col-lg-5">
								<label class="control-label col-md-5"> <i
									class="payment-cycle-sp">*</i>租赁状态：
								</label>
								<div class="col-md-7 text-left">
									<select class="form-control" name="leaseStatus">
										<option value="1">待放租</option>
										<option value="2">放租</option>
									</select>
								</div>
							</div>
							</div>
							</div>
							<div id="measurediv" class="form-group contract-info mtop30"
								style="display: none;">
								<!-- <label class="control-label col-md-6" style="font-weight: bold;">
									<i class="payment-cycle-sp"></i>关联计量表信息
								</label> -->
								<div class="contract-title-block">
									<span class="contract-info-h3">关联计量表信息 </span>
								</div>
								<div class="col-md-12 text-left">
									<table class="table table-striped table-hover t-custom">
										<thead>
											<tr class="ml-ct">
												<th>费项名称</th>
												<th>计量分类名称</th>
												<th>计量分类编码</th>
											</tr>
										</thead>
										<tbody id="measure_datas">
										</tbody>
									</table>
								</div>
							</div>
							<div class="modal-footer form-group mtop30" style="clear: left;">
								<button type="button" class="btn btn-default"
									data-dismiss="modal" id="btn_cancel" onclick="javascript:window.history.go(-1)">取消</button>
								<gd:btn btncode="BtnMarketResourceOtherSave">
									<input type="submit" class="btn btn-danger" value="保存" id="saveResource" />
								</gd:btn>
							</div>
						</form>
					</div>
				</div>
				
				<!-- 租赁单内容 -->
				<div id="about" class="tab-pane">
					<div class="panel-body">
			            <div class="adv-table editable-table ">
			                <div class="space15"></div>
			                <table class="table table-striped table-hover t-custom" id="tb">
			                    <thead>
			                    <tr class="ml-ct">
			                       <th><input type="checkbox" id="checkAll"></th>
		                            <th>序号</th>
		                            <th>合同号</th>
		                            <th>合同状态</th>
		                            <th>商户</th>
		                            <th>租赁资源</th>
		                            <th>接铺日期</th>
		                            <th>开业日期</th>
		                            <th>结束日期</th>
		                            <th>签署日期</th>
		                            <th>经办人</th>
			                    </tr>
			                    </thead>
			                     <tbody id="leaseOrderBody">
			                      </tbody>
			                </table>
			                
			                <!-- 分页组件 -->
			                <div id="pagebar"></div>
			            </div>
			        </div>
				
				</div>
				
			</div>
		</div>
	</section>
</div>

<script id="contractLeasingresourceScript" type="text/x-jquery-tmpl">
	{{each(i, contractLeasingres) contractLeasingresList}}
		<tr class="ml-ct">
			<td><input type="checkbox" value="${contractLeasingres.id}" class="Echeckbox" name="subBox"/></td>
			<td>${i + 1}</td>
			<td>${contractLeasingres.contractNo}</td>
			<td>
				{{if contractLeasingres.contractStatus == 0}}
					待执行
				{{else contractLeasingres.contractStatus == 1}}
					执行中
				{{else contractLeasingres.contractStatus == 2}}
					已结算
				{{/if}}
			</td>
			<td>${contractLeasingres.customerName}</td>
			<td>${contractLeasingres.resourceTypeName}</td>
			<td>${formateDate(contractLeasingres.startLeasingDay,'yyyy-MM-dd')}</td>
			<td>${formateDate(contractLeasingres.contractCreateTime,'yyyy-MM-dd')}</td>
			<td>${formateDate(contractLeasingres.endLeasingDay,'yyyy-MM-dd')}</td>
			<td>${formateDate(contractLeasingres.dateOfContract,'yyyy-MM-dd')}</td>
			<td>${contractLeasingres.trustees}</td>
    	</tr>
	{{/each}}
</script>
