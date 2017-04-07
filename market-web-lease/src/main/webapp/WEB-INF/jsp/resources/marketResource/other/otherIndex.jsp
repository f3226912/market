<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page isELIgnored="true"%>
<%@ taglib prefix="gd" uri="http://www.gdeng.cn/gd"%>
<div class="system-modal market-style">
    <section class="header">
    	<span class="">其它资源管理</span>
	        <div class="right-icon">
	            <!-- <a href="javascript:void(0)" id="add"><i class="fa fa-list-alt"></i>新增其它资源</a> -->
	            <gd:btn btncode="BtnMarketResourceOtherToAdd">
	            	<a href="javascript:(function(){if(!Route.market){$.eAlert('提示','当前公司下未关联市场，暂不能执行添加操作！');return;}Route.params={};location.href='index#viewOtherMarketResource';})();"><i class="fa fa-plus-square"></i>新增其它资源</a>
	            </gd:btn>
	            <gd:btn btncode="BtnMarketResourceOtherRent">
	            	<a href="javascript:void(0)" id="release"><i class="fa fa-external-link"></i>放租</a>
	            </gd:btn>
	            <gd:btn btncode="BtnMarketResourceOtherRecovery">
	            	<a href="javascript:void(0)" id="receive"><i class="fa fa-retweet"></i>回收</a>
	            </gd:btn>
	            <gd:btn btncode="BtnMarketResourceOtherDelete">
	            	<a href="javascript:void(0)" id="delete"><i class="fa fa-trash-o"></i>删除</a>
	            </gd:btn>
	        </div>
    </section>
    <div class="col-md-max">
      <div class="main-search">
           <div class="main-ipt-types">
              <form id="marketResource" class="form-input clearfix">
                <div class="form-group">
                	<span class="form-control-text">租赁状态：</span>
					<select class="form-control wid130" name="leaseStatus">
							<option value="">全部租赁状态</option>
							<option value="1">待放租</option>
							<option value="2">放租</option>
							<option value="3">已租</option>
					</select>
				</div>
				<div class="form-group">
					<span class="form-control-text">区域／楼栋：</span>
					<select class="form-control wid130" name="areaId">
					</select>
				</div>
				<div class="form-group">
				    <select class="form-control wid130" name="budingId">
				    	<option value="">全部楼栋</option>
					</select>
				</div>
				<div class="form-group">
					<span class="form-control-text">租赁类型：</span>
				    <select class="form-control wid130" name="resourceTypeId">
					</select>
				</div>
				<div class="form-group">
					<span class="form-control-text">租赁资源名称：</span>
					<input  name="name" type="text" class="form-control" placeholder="租赁资源名称" maxlength="100" style="width:90px">
				</div>
                <div class="form-group">
                    <button id="query" class="btn btn-danger" type="button">查询</button>
                </div>
            </form>
           </div>
       </div>
    </div> 
    <div class="form-page">
            <div class="adv-table editable-table resoure-table">
                <table class="table table-striped table-hover t-custom" id="tb">
                    <thead>
                    <tr class="ml-ct">
                       <th width="6%"><input type="checkbox" id="checkAll" class="checkbox-range" ><span class="checkbox-span"></span></th>
                            <th width="6%">序号</th>
                            <th width="14%">资源编码</th>
                            <th width="14%">租赁资源名称</th>
                            <th width="14%">资源类型</th>
                            <th width="13%">所属楼栋</th>
                            <th width="13%">所属区域</th>
                            <th width="10%">可出租面积</th>
                            <th width="10%">租赁状态</th>
                    </tr>
                    </thead>
                     <tbody id="otherResourceBody">
                      </tbody>
                </table>

                <!-- 分页组件 -->
                <div id="pagebar"></div>
            </div>
        </div>
        
        <!-- 资源新增 该模态框暂时不用 -->
		<div class="modal fade" id="modal-setOtherResource" tabindex="-1"
			role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
			<div class="modal-dialog" style="width: 900px">
				<div class="modal-content">
					<div class="modal-header">
						<button type="button" class="close" data-dismiss="modal"
							aria-hidden="true">&times;</button>
						<h4 class="modal-title" id="myModalLabel">其它资源信息</h4>
					</div>
					<div class="modal-body">
						<section class="panel">
							<header class="">
								<ul class="nav nav-tabs">
									<li class="active"><a data-toggle="tab" href="#home">基本资料</a>
									</li>
									<li id="leaseOrderBtn" class="" onclick="leaseOrderClick();"><a data-toggle="tab" href="#about">租赁单</a></li>
								</ul>
							</header>
							<div class="panel-body">
								<div class="tab-content">
									<div id="home" class="tab-pane active">
										<div class="form-page">
											<form id="form-setOtherResource" method="post" novalidate
												class="form-horizontal clearfix cmxform">
												<input type="hidden" name="id" value="" />
												<div class="form-group col-lg-5">
													<label class="control-label col-md-6"> <i
														class="payment-cycle-sp">*</i>所属区域：
													</label>
													<div class="col-md-6 text-left">
														<select class="form-control wid150" required name="areaId">
														</select>
													</div>
												</div>
												<div class="form-group col-lg-5">
													<label class="control-label col-md-6"> <i
														class="payment-cycle-sp">*</i>所属楼栋：
													</label>
													<div class="col-md-6 text-left">
														<select class="form-control wid150" required
															name="budingId">
															<option value="">全部楼栋</option>
														</select>
													</div>
												</div>
												<div class="form-group col-lg-5">
													<label class="control-label col-md-6"> <i
														class="payment-cycle-sp">*</i>资源编码：
													</label>
													<div class="col-md-6 text-left">
														<input name="code" type="text" placeholder="100个字符"
															required class="form-control">
													</div>
												</div>
												<div class="form-group col-lg-5">
													<label class="control-label col-md-6"> <i
														class="payment-cycle-sp">*</i>资源简码：
													</label>
													<div class="col-md-6 text-left">
														<input name="shortCode" type="text" placeholder="100个字符"
															required class="form-control">
													</div>
												</div>
												<div class="form-group col-lg-5">
													<label class="control-label col-md-6"> <i
														class="payment-cycle-sp">*</i>租赁资源名称：
													</label>
													<div class="col-md-6 text-left">
														<input name="name" type="text" placeholder="100个字符"
															required class="form-control">
													</div>
												</div>
												<div class="form-group col-lg-5">
													<label class="control-label col-md-6"> <i
														class="payment-cycle-sp">*</i>单元：
													</label>
													<div class="col-md-6 text-left">
														<input name="unitId" type="number" min="1" max="20"
															placeholder="1-20之间的数值" required class="form-control">
													</div>
												</div>
												<div class="form-group col-lg-5">
													<label class="control-label col-md-6"> <i
														class="payment-cycle-sp">*</i>建筑面积：
													</label>
													<div class="col-md-6 text-left">
														<input name="builtArea" type="number" min="1"
															max="100000000" placeholder="0-100000000" required
															class="form-control" /> <em class="unit-tips">m²</em>
													</div>
												</div>
												<div class="form-group col-lg-5">
													<label class="control-label col-md-6"> <i
														class="payment-cycle-sp">*</i>楼层：
													</label>
													<div class="col-md-6 text-left">
														<input name="floorId" type="number" min="1" max="200"
															placeholder="1-200之间的数值" required class="form-control">
													</div>
												</div>
												<div class="form-group col-lg-5">
													<label class="control-label col-md-6"> <i
														class="payment-cycle-sp">*</i>套内面积：
													</label>
													<div class="col-md-6 text-left">
														<input name="innerArea" type="number" min="1"
															max="100000000" placeholder="0-100000000" required
															class="form-control"> <em class="unit-tips">m²</em>
													</div>
												</div>
												<div class="form-group col-lg-5">
													<label class="control-label col-md-6"> <i
														class="payment-cycle-sp">*</i>可出租面积：
													</label>
													<div class="col-md-6 text-left">
														<input name="leaseArea" type="number" min="1"
															max="100000000" placeholder="0-100000000" required
															class="form-control"> <em class="unit-tips">m²</em>
													</div>
												</div>
												<div class="form-group col-lg-5">
													<label class="control-label col-md-6"> 
														<i class="payment-cycle-sp">*</i>资源类型：
													</label>
													<div class="col-md-6 text-left">
														<select class="form-control wid150" required name="resourceTypeId">
														</select>
													</div>
												</div>
												<div class="form-group col-lg-5">
													<label class="control-label col-md-6"> <i
														class="payment-cycle-sp">*</i>租赁状态：
													</label>
													<div class="col-md-6 text-left">
														<select class="form-control wid150" name="leaseStatus">
															<option value="1">待放租</option>
															<option value="2">放租</option>
															<option value="3">已租</option>
														</select>
													</div>
												</div>
												<div id="measurediv" class="form-group col-lg-5"
													style="display: none;">
													<label class="control-label col-md-6"
														style="font-weight: bold;"> <i
														class="payment-cycle-sp"></i>关联计量表信息
													</label>
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
	
												<div class="modal-footer" style="clear: left;">
													<button type="button" class="btn btn-default" data-dismiss="modal" 
														onclick="javascript:window.history.go(-1)">取消</button>
													<input type="submit" class="btn btn-primary" value="保存"
														id="saveResource" />
												</div>
											</form>
										</div>
									</div>
									<div id="about" class="tab-pane">
										<div class="panel-body">
								            <div class="adv-table editable-table ">
								                <div class="space15"></div>
								                <table class="table table-striped table-hover t-custom" id="tb">
								                    <thead>
								                    <tr class="ml-ct">
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
								                <!-- <div id="leaseOrderPagebar"></div> -->
								                <div id="pagebar"></div>
								            </div>
								        </div>
									</div>
	
								</div>
							</div>
						</section>
					</div>
	
				</div>
			</div>
		</div>
		<!-- 资源新增 -->
        
</div>

<!-- page end-->
<!-- page end-->
<script id="marketResourceScript" type="text/x-jquery-tmpl">
	{{each(i, marketResource) marketResources}}
		<tr class="ml-ct">
			<td class="text-center"><input type="checkbox" value="${marketResource.id}" class="Echeckbox checkbox-range" name='subBox' /><span class="checkbox-span"></span></td>
			<td>${i+1}</td>
			<td>${marketResource.code}</td>
			<td><a href="javascript:(function(){Route.params={id:${marketResource.id}};location.href='index#viewOtherMarketResource';})();" style="color: #664de8;">${marketResource.name}</a></td>
			<td>${marketResource.resourceTypeName}</td>
			<td>${marketResource.buildingName}</td>
			<td>${marketResource.areaName}</td>
			<td>${marketResource.leaseArea}</td>
			<td>${marketResource.leaseStatusName}</td>
    	</tr>
	{{/each}}
</script>
<script id="contractLeasingresourceScript" type="text/x-jquery-tmpl">
	{{each(i, contractLeasingres) contractLeasingresList}}
		<tr class="ml-ct">
			<td>${i+1}</td>
			<td>${contractLeasingres.contractNo}</td>
			<td>${contractLeasingres.contractStatus}</td>
			<td>${contractLeasingres.customerName}</td>
			<td>${contractLeasingres.resourceTypeName}</td>
			<td>${contractLeasingres.startLeasingDay}</td>
			<td>${contractLeasingres.contractCreateTime}</td>
			<td>${contractLeasingres.endLeasingDay}</td>
			<td>${contractLeasingres.dateOfContract}</td>
			<td>${contractLeasingres.trustees}</td>
    	</tr>
	{{/each}}
</script>

<script id="addMarketResource" type="text/x-jquery-tmpl">
	<form class="cmxform" novalidate id="commentForm">
	<fieldset>
    <legend>输入您的名字，邮箱，URL，备注。</legend>
    <div class="form-group">
      <label class="control-label col-md-3" for="cname">Name (必需, 最小两个字母)</label>
      <div class="col-md-6">
      	<input id="cname" class="form-control" name="name" minlength="2" type="text" required>
    </div>
    </div>
    <div class="form-group">
      <label class="control-label col-md-3" for="cemail">E-Mail (必需)</label>
      <div class="col-md-6">
      <input id="cemail" class="form-control" value="{{动态设置值}}" type="email" name="email" required>
    </div>
    </div>
    <div class="form-group">
      <label class="control-label col-md-3" for="curl">URL (可选)</label>
      <div class="col-md-6">
      <input class="form-control" id="curl" value="{{动态设置值}}" type="url" name="url">
    </div>
    </div>
    <div class="form-group">
      <label class="control-label col-md-3" for="ccomment">备注 (必需)</label>
      <div class="col-md-6">
      <textarea class="form-control" id="ccomment" name="comment" required></textarea>
    </div>
    </div>
    <p>
      <input class="submit" type="submit" value="Submit">
    </p>
  </fieldset>
  </form>
</script>