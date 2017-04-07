<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/pub/tags.inc" %>
<%@ page isELIgnored="true"%>
<style>
.sp-td{
	width:100px;
	overflow: hidden;
    white-space: nowrap;
    text-overflow: ellipsis;
   }
.ml1_0 .fl{margin-left:16px !important;}
.contract .export-s{margin-top:0 !important;}
.ml-icon{margin-top: -3px;}
</style>
       <div class="col-md-max contract">
          <div class="main-search">
               <div class="main-contracht-tp">
               	       <span class="motion-of-contract">实收款管理<span style="font-size:12px; padding-left:10px;">共<strong style="color:#ea785b" id="recordCount">123</strong>笔 </span></span>
               	       <input id="queryType" type="hidden" value="">
						<div class="input-group m-bot15">
							<div class="input-group-btn">
								<button type="button" id="toggle-box" class="btn btn-white dropdown-toggle" data-toggle="dropdown">合同编号 <span class="caret red-icon"></span></button>
								<ul class="dropdown-menu dropdown-lt-20">
									<li id="item_c" value="1">
										<a href="javascript:void(0)" >合同编号</a>
									</li>
									<li id="item_r" value="2">
										<a href="javascript:void(0)" >租赁资源</a>
									</li>
									<li id="item_f" value="3">
										<a href="javascript:void(0)" >费项名称</a>
									</li>
								</ul>
							</div>
						<input id="conditionType" type="hidden" value="1">
						<input id="conditionValue" type="text" class="form-control wid150 border-none-right" style="margin-top: 17px; margin-left: -1px;">
						<gd:btn btncode="BtnFianceFeeReceivedListSimpleQuery"><button id="switchBtn" type="button" class="btn btn-white ml-icon"><i class="fa fa-search red-icon"></i></button></gd:btn>
						<span class="high-search"> 高级搜索<i class="fa fa-angle-down"></i></span>
						<gd:btn btncode="BtnFianceFeeReceivedListExportData"><div class="export-s"><i class="fa fa-download"></i> <a href="javascript:void(0)" id="financeHasExport" style="color:#e86d4d;" >导出</a></div></gd:btn>
					</div>
					
               </div>
               <div class="main-ipt-types" style="width:100%; float:left; margin-top:-3px;">
               	  <form class="form-input clearfix">
					<div class="form-group">
						费项类型:
						<select id="feeItemTypeId" class="form-control wid150" style="display:inline">
							<option value="">费项类型</option>
							<option value="1">周期性费项</option>
							<option value="2">走表类费项</option>
							<option value="3">一次性费项</option>
							<option value="4">临时性费项</option>
						</select>
					</div>
					<div class="form-group">
						款项类型:
						<select id="fundType" class="form-control wid150" style="display:inline">
							<option value="">款项类型</option>
							<option value="0">应收款</option>
							<option value="1">临时收款</option>
							<option value="2">退款</option>
						</select>
					</div>
					<div class="form-group date-icon border-none-right">
						缴费期限:
						<input type="text" style="display:inline" class="form-control start-date wid150" id="startTime" name="startTime" placeholder="签署日期">
						<i class="fa fa-calendar"></i>
					</div>
					<div class="form-group date-icon border-none-right">
						<input type="text" class="form-control start-date wid150" id="endTime" name="endTime" placeholder="结束日期">
						<i class="fa fa-calendar"></i>
					</div>
					<div class="form-group">
						<gd:btn btncode="BtnFianceFeeReceivedListAdvancedQuery"><button class="btn btn-danger" type="button" id="search">搜索</button></gd:btn>
					</div>
				</form>
               </div>
           </div>
            <div class="wrp-box" style="margin-top:28px;"></div>
            <div id="pagebar" class="ml1_0"></div>
        </div>

<!-- 
	{{each(i, record) recordList}}
		<tr class="ml-ct">
			<td>${i+1}</td>
			<td>${record.contractNo}</td>
			<td>${record.resourceNames}</td>
			<td>${record.feeItemTypeName}</td>
			<td>${record.feeItemName}</td>
			<td>${record.timeLimit}</td>
			<td>${record.startTime}</td>
			<td>${record.endTime}</td>
			<td>${record.accountPayable}</td>
			<td>${record.accountPayed}</td>
    	</tr>
	{{/each}}
-->
<!-- page end-->
<script id="template" type="text/x-jquery-tmpl">

          <table class="charges-tab-style" cellspacing="0" style="width:97%; display: table; margin:30px auto;border: solid 1px #ddd;table-layout:fixed;">
	            <tbody class="tb-trs">
					 <tr class="charges-tab-bg">
						<td class="charges-tab-bor" style="width:30px;">序号</td>
						<td style="width:220px; text-align:center !important">合同编号</td>
						<td style="width:200px; text-align:center !important">租赁资源</td>
						<td style="text-align:center !important">费项类型</td>
						<td style="text-align:center !important">费项名称</td>
						<td style="text-align:center !important">缴费期限</td>
						<td style="text-align:center !important">计费起始日期</td>
						<td style="text-align:center !important">计费结束日期</td>
						<td style="text-align:center !important">应收金额</td>
						<td style="text-align:center !important">实收金额</td>
					  </tr>
				{{each(i, record) recordList}}
					  <tr class="ficancial-tab" onclick="javascript:(function(){Route.params={id:${record.id}};location.href='index#finHasRecDetail';})();">
						<td id="eq-add">${i+1}</td>
						<td title="${record.contractNo}">${record.contractNo}</td>
						<td title="${record.contractNo}" class="sp-td">${record.resourceNames}</td>
						<td>${record.feeItemTypeName}</td>
						<td>${record.feeItemName}</td>
						<td class="dateFormat" data-date="${record.timeLimit}">${record.timeLimit}</td>
						<td class="dateFormat" data-date="${record.startTime}">${record.startTime}</td>
						<td class="dateFormat" data-date="${record.endTime}">${record.endTime}</td>
						<td>${record.accountPayable}</td>
						<td>${record.accountPayed}</td>
					  </tr>
				{{/each}}
					  
			    </tbody>
	        </table>

</script>
