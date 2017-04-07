<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page isELIgnored="true"%>
<style>
	.eI{
		    position: relative !important;
			display: block;
			float: left;
			margin: 8px 5px 0px 5px;
			top:0 !important;
			left:20% !important;
	} 
	.indexWrap .report2 .ones-2{
		    margin-top:0px !important;
	} 
	.indexWrap .report2 .ones-2 table td{
		border:none !important;
		text-align:center !important;
	}
</style>
<div class="indexWrap col-md-12">
<div class="onesWrap market ">
		<div class="panel-heading etital">市场租赁情况总览</div>
		<div id="divResourceList" class="table"></div>
		<div class="resource-map drag-wrap" id="dragBox">
				<img src="" class="map-src" alt="图片加载中……" width="800">
				<ul id="planeList" class="plane-list">
					</ul>
				</div>
				<p id="axis"></p>
	</div>
	

	<div class="onesWrap">
		<div class="panel-heading etital">关键指标</div>
		<div id="indexKey"></div>
	</div>

	<!--Step:1 Prepare a dom for ECharts which (must) has size (width & hight)-->
	<!--Step:1 为ECharts准备一个具备大小（宽高）的Dom-->
	<div class="onesWrap">
		<div class="panel-heading etital">费项收缴情况</div>
		<div>
			<form id="form_report1" class="form-input clearfix choose">
				<div class="form-group">资源类型：</div>
				<div class="form-group">
					<select name="resourceType" class="form-control wid120">
					</select>
				</div>
				<div class="form-group">费项：</div>
				<div class="form-group">
					<select name="expId" class="form-control wid120">
					</select>
				</div>
				<div class="form-group">区域：</div>
				<div class="form-group">
					<select name="areaId" class="form-control wid120">
					</select>
				</div>
				<div class="form-group">统计时间：</div>
				<div class="form-group date-icon border-none-right">
					<input name="startDate" type="text" class="form-control startDate wid150" style="display:block!important;" placeholder="起始日期">
					<i class="fa fa-calendar"></i>
				</div>
				<div class="form-group date-icon border-none-right">
					<input type="text" name="endDate" class="form-control endDate wid150" style="display:block!important;" placeholder="结束日期">
					<i class="fa fa-calendar"></i>
				</div>
				<div class="form-group">
					<button class="btn btn-danger" id="report1Query" type="button">查询</button>
				</div>
			</form>
		</div>

		<div id="report1" style="height:500px;border:1px solid #ccc;padding:10px;"></div>
	</div>

	<div class="onesWrap">
		<div class="panel-heading etital">未收款情况</div>

		<div>
			<form id="form_report2" class="form-input clearfix choose">
				<div class="form-group">资源类型：</div>
				<div class="form-group">
					<select name="resourceType" class="form-control wid120">
					</select>
				</div>
				<div class="form-group">费项：</div>
				<div class="form-group">
					<select name="expId" class="form-control wid120">
					</select>
				</div>
				<div class="form-group">统计时间：</div>
				<div class="form-group date-icon border-none-right">
					<input type="text"  name="startDate" class="form-control startDate wid150" style="display:block!important;" placeholder="起始日期">
					<i class="fa fa-calendar"></i>
				</div>
				<div class="form-group date-icon border-none-right">
					<input type="text" name="endDate" class="form-control endDate wid150" style="display:block!important;" placeholder="结束日期">
					<i class="fa fa-calendar"></i>
				</div>
				<div class="form-group">
					<button class="btn btn-danger" id="report2Query" type="button">查询</button>
				</div>
			</form>
		</div>
		<div class="report2">
			<div id="report2" style="height:350px;"></div>
			<div class="ones-2" id="divReport2Table">
				
			</div>
		</div>
		
	</div>

	<div class="onesWrap" style="overflow: hidden;">
		<div class="panel-heading etital"><img src=""/>优惠情况分析</div>
		<div>
			<form  id="form_report3" class="form-input clearfix choose">
				<div class="form-group">资源类型：</div>
				<div class="form-group">
					<select name="resourceType" class="form-control wid120">
					</select>
				</div>
				<div class="form-group">费项：</div>
				<div class="form-group">
					<select name="expId" class="form-control wid120">
					</select>
				</div>
				<div class="form-group">区域：</div>
				<div class="form-group">
					<select name="areaId" class="form-control wid120">
					</select>
				</div>
				<div class="form-group">统计时间：</div>
				<div class="form-group date-icon border-none-right">
					<input type="text" name="startDate" class="form-control startDate wid150" style="display:block!important;" placeholder="起始日期">
					<i class="fa fa-calendar"></i>
				</div>
				<div class="form-group date-icon border-none-right">
					<input type="text" name="endDate" class="form-control endDate wid150" style="display:block!important;" placeholder="结束日期">
					<i class="fa fa-calendar"></i>
				</div>
				<div class="form-group">
					<button class="btn btn-danger" id="report3Query" type="button">查询</button>
				</div>
			</form>
		</div>

		<div id="report3" style="width:60%;height:500px;float:left;"></div>
		<div id="report3Pie" style="width:40%;height:500px;float:left;"></div>
	</div>
</div>
<script id="template" type="text/x-jquery-tmpl">
	<ul class="key">
		<li class="bgc-fa s-box">
			<div class="w50 text-left fl">待租面积</div>
			<div class="w50 text-right fl">${report.stayArea }m²</div>
		</li>
		<li class="bgc-fa s-box">
			<div class="w50 text-left fl">当年租金收入</div>
			<div class="w50 text-right fl">${report.accountPayed }元</div>
		</li>
		<li class="bgc-fa s-box">
			<div class="w50 text-left fl">未放租面积</div>
			<div class="w50 text-right fl">${report.unrentableArea }m²</div>
		</li>
		<li class="bgc-fa s-box">
			<div class="w50 text-left fl">当月租金收入</div>
			<div class="w50 text-right fl">${report.accountPayedMon }元</div>
		</li>
		<li class="bgc-fa s-box">
			<div class="w50 text-left fl">已租面积</div>
			<div class="w50 text-right fl">${report.alreadyArea }m²</div>
		</li>
		<li class="bgc-fa s-box">
			<div class="w50 text-left fl">当年收缴率</div>
			<div class="w50 text-right fl">${report.accountRate }%</div>
		</li>
		<li class="bgc-fa s-box">
			<div class="w50 text-left fl">上月出租率</div>
			<div class="w50 text-right fl">${report.periodRate }%</div>
		</li>
		<li class="bgc-fa s-box">
			<div class="w50 text-left fl">当月收缴率 </div>
			<div class="w50 text-right fl">${report.accountMonRate }%</div>
		</li>
	</ul>
</script>

<script id="templateResourceList" type="text/x-jquery-tmpl">

<ul class="resoure-plane text-center">
	<li data="" class="currChose">全部</li>
	{{each(i, resourceType) resourceTypeList}}
		<li data="${resourceType.id}" data-code="${resourceType.code}">${resourceType.name}</li>
	{{/each}}
</ul>	
</script>
<script id="templateReport2Table" type="text/x-jquery-tmpl">
<table >
					<tr class="all">
						<th></th>
						<th>区域</th>
						<th>未收款</th>
						<th>占比</th>
						<th>租金收缴率</th>
						<th></th>
					</tr>
					<tbody >
		{{each(i, table) report2Table.result}}
		　<tr>
						<td>${i+1}</td>
				 		<td><i style="background-color:${report2Table.color[i]}" class="eI"></i>${table.areaName}</td>
				 		<td>${table.accountDidPay}</td>
				 		<td>${table.rate}</td>
				 		<td>
				 			<div class="progress">
							  <div class="progress-bar" role="progressbar" aria-valuenow="${table.captureRate}" aria-valuemin="0" aria-valuemax="100" style="width: ${table.captureRate}%;background-color:${report2Table.color[i]};">
							  </div>
							</div>
							
				 		</td>
						<td>${table.captureRate}%</td>
		</tr>
		{{/each}}
<tbody>
</table>
</script>
<script id="drawTemp" type="text/x-jquery-tmpl">
    	<a href="index#viewImgMarketArea">
        <li class="curr-list drawed" id="draw_${id}" name="${name}" style="left:${x}px;top: ${y}px;">
            <div class="plane-tips">
                <p class="tips-name">${name}</p>
               <!--  <p class="all-num">总数量：${total}</p> 
                <p class="daizu-tip plane-tips-list">已租：${alreadyRentedCnt}</p> 
                <p class="yizu-tip plane-tips-list">待租：${forRentCnt}</p>
                <p class="daichuzu-tip plane-tips-list">待放租：${ineffectiveCnt}</p> -->
            </div>
        </li>
        </a>
</script>
<script id="tipTemp" type="text/x-jquery-tmpl">
    <p class="tips-name">${name}</p>
    <p class="all-num">总数量：${total}</p> 
    <p class="yizu-tip plane-tips-list">已租：${alreadyRentedCnt}</p> 
    <p class="daizu-tip plane-tips-list">待租：${forRentCnt}</p>
    <p class="daichuzu-tip plane-tips-list">待放租：${ineffectiveCnt}</p>
</script>
