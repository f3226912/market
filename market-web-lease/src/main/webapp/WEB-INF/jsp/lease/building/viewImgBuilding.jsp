<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page isELIgnored="true"%>

<section class="header">平面图设置</section>
<div class="market uploadPlane form-page" style="margin-top: 40px;">
	<div class="contract-info clearfix ">
	<div class="contract-title-block">
		<span id="marketName" class="contract-info-h3"></span>
		<div class="right-icon">
			<a id="set"><i class="fa fa-edit"></i>绘制租赁资源</a>
			<a href="index#uploadImgBuilding"><i class="fa fa-upload"></i>上传平面图</a>
		</div>
	</div>
	<div class="uploadplane-main clearfix">
		<div class="upload-flow">
			<!-- 区域列表 -->
			<ul id="areaWrap"></ul>
		</div>
		<div class="setplane-cont">
        	<!-- 资源按钮组 -->
			<ul id="marketBtns" class="resoure-plane">
			</ul>					
				<div class="table-tip">
                    <!-- 总资源数量 -->
					<p class="table-tip-text"></p>			
			</div>
			<div class="resource-map drag-wrap" id="dragBox">
				<img src="" width="800" class="map-src" alt="图片加载中……">
				<ul id="planeList" class="plane-list"></ul>
			</div>
			<p id="axis"></p>
		</div>
	</div>
	</div>
	<div class="form-group col-lg-12 text-center mtop30">
		<button id="cancel" data-dismiss="modal" class="btn btn-default" type="button">取消</button>
		<button class="btn btn-danger ml15" id="save" type="button" data-dismiss="modal">保存</button>
	</div>
	<div class="modal-pop" id="modal-pop">
		<div class="panel-body">
			<div class="adv-table editable-table">
				<table class="table table-striped table-hover table-bordered" id="tb">
					<thead>
						<tr class="ml-ct">
							<th><input type="checkbox" id="checkboxAll"/></th>
							<th>租赁资源名称</th>
							<th>绘制状态</th>
						</tr>
					</thead>
					<tbody id="templateBody">
					</tbody>
				</table>
			</div>
		</div>
	</div>
</div>

<script id="noXTemp" type="text/x-jquery-tmpl">
    <tr class="ml-ct">
        <td style="text-align: center !important;"><input id="check_${id}" draw="false" class="Echeckbox" name="areaCheckbox" type="checkbox" value="${id}"/></td>
        <td>${name}</td>
        <td>未绘制</td>
    </tr>
</script>

<script id="hasXTemp" type="text/x-jquery-tmpl">
    <tr class="ml-ct">
        <td style="text-align: center !important;"><input id="check_${id}" draw="true" init="drawed" class="Echeckbox" name="areaCheckbox" type="checkbox" checked="checked" value="${id}"/></td>
        <td>${name}</td>
        <td>已绘制</td>
    </tr>
</script>

<script id="drawTemp" type="text/x-jquery-tmpl">
{{each(i,d) datas}}
    {{if d.leaseStatus == 3}}
        <li class="daichuzu-tip map-tip drawed" id="draw_${d.id}" name="${d.name}" style="left:${d.x}px;top: ${d.y}px;">
			<div class="plane-tips">
            </div>
        </li>
    {{else}}
	    {{if d.leaseStatus == 1}}
        <li class="daizu-tip map-tip drawed" id="draw_${d.id}" name="${d.name}" style="left:${d.x}px;top: ${d.y}px;">
        {{else d.leaseStatus == 2}}
        <li class="yizu-tip map-tip drawed" id="draw_${d.id}" name="${d.name}" style="left:${d.x}px;top: ${d.y}px;">
        {{/if}}
		     <div class="plane-tips">
	         </div>
        </li>
    {{/if}}
{{/each}}
</script>
<script id="tipTemp" type="text/x-jquery-tmpl">
{{each(i,d) datas}}
    {{if d.leaseStatus == "已租"}}
        <p class="map-tips-list">资源编码：${d.shortCode}</p> 
        <p class="map-tips-list">商铺面积：${d.leaseArea} m*2</p>
        <p class="map-tips-list">经营状态：${d.leaseStatus}</p>
        <p class="map-tips-list">客户：${d.customerName}</p>
        <p class="map-tips-list">联系电话：${d.customerMobile}</p>
        <a href="javascript:(function(){Route.params={id:${contractId}};location.href='index#contractManageDetail';})();"><p class="tips-name">查看合同详情</p></a>
    {{else}}
	    <p class="map-tips-list">资源编码：${d.shortCode}</p> 
	    <p class="map-tips-list">商铺面积：${d.leaseArea} m*2</p>
	    <p class="map-tips-list">经营状态：${d.leaseStatus}</p>
    {{/if}}
{{/each}}
</script>

<script id="areaTemp" type="text/x-jquery-tmpl">
{{each(i,d) datas}}
   <li id="${id}" title="${name}">${name}</li>
{{/each}}
</script>
