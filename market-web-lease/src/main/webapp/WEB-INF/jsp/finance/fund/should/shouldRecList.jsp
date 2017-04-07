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
.form-group select{
	display:inline;
	background-color:#fff !important;
}
.form-group input{
	display:inline;
}
.date-icon .fa{
	right: 10px !important;
}
.export-s a{
	color:#1a1a1a;
}
.contract .export-s{margin-top:0 !important;}
.ml-icon{margin-top: -3px;}
a.cos_cl{color:#e86d4d;}
.contract .fa-plus-square:before{color:#999!important;}
</style>
       <div class="col-md-max contract">
          <div class="main-search">
               <div class="main-contracht-tp">
               	       <span class="motion-of-contract">应收款管理<span style="font-size:12px; padding-left:10px;">共<strong style="color:#ea785b" id="recordCount">123</strong>笔 </span></span>
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
						<gd:btn  btncode="BtnFianceFeeSholudListSimpleQuery"><button id="switchBtn" type="button" class="btn btn-white ml-icon"><i class="fa fa-search red-icon"></i></button></gd:btn>
						<span class="high-search"> 高级搜索<i class="fa fa-angle-down"></i></span>
						<gd:btn  btncode="BtnFianceFeeSholudListNewTempItem"><div class="export-s"><i class="fa fa-plus-square"></i> <a href="javascript:(function(){if (checkMarket()){location.href='index#newTempMoney';}})();" class="cos_cl">新增临时收款</a></div></gd:btn>
						<gd:btn  btncode="BtnFianceFeeSholudListNewBackItem"><div class="export-s"><i class="fa fa-plus-square"></i> <a href="javascript:(function(){if (checkMarket()){location.href='index#newBackMoney';}})();" class="cos_cl">新增退款</a></div></gd:btn>
						<gd:btn  btncode="BtnFianceFeeSholudListExportData"><div class="export-s"><i class="fa fa-download"></i> <a href="javascript:void(0)" id="financeShouldExport" class="cos_cl" >导出</a></div></gd:btn>
					</div>
					
               </div>
               <div class="main-ipt-types" style="width:100%; float:left;  margin-top:-3px;">
               	  <form class="form-input clearfix">
					<div class="form-group">
					    <span class="fzlt_10">费项类型:</span>
						<select id="feeItemTypeId" class="form-control wid150">
							<option value="">费项类型</option>
							<option value="1">周期性费项</option>
							<option value="2">走表类费项</option>
							<option value="3">一次性费项</option>
							<option value="4">临时性费项</option>
						</select>
					</div>
					<div class="form-group">
					    <span class="fzlt_10">款项类型:</span>
						<select id="fundType" class="form-control wid150">
							<option value="">款项类型</option>
							<option value="0">应收款</option>
							<option value="1">临时收款</option>
							<option value="2">退款</option>
						</select>
					</div>
					<div class="form-group date-icon border-none-right">
					    <span class="fzlt_10">缴费期限:</span>
						<input type="text" class="form-control start-date wid150" id="startTime" name="startTime" placeholder="签署日期">
						<i class="fa fa-calendar"></i>
					</div>
					<div class="form-group date-icon border-none-right">
						<input type="text" class="form-control start-date wid150" id="endTime" name="endTime" placeholder="结束日期">
						<i class="fa fa-calendar"></i>
					</div>
					<div class="form-group">
						<gd:btn  btncode="BtnFianceFeeSholudListAdvancedQuery"><button class="btn btn-danger" type="button" id="search">搜索</button></gd:btn>
					</div>
				</form>
               </div>
           </div>
            <div class="wrp-box" style="margin-top:28px;"></div>
            <div id="pagebar" class="ml1_0"></div>
        </div>
<script type="text/javascript">
function checkMarket(){
	if(!Route.market){
		$.eAlert("操作提示","您没有当前市场的权限, 不能新增");
		return false ;
	}
	return true;
}
</script>        
<script id="template" type="text/x-jquery-tmpl">
          <table class="charges-tab-style" cellspacing="0" style="width:97%; display: table; margin:30px auto;border: solid 1px #ddd;table-layout:fixed;">
	            <tbody class="tb-trs">
					 <tr class="charges-tab-bg">
						<td class="charges-tab-bor" style="width:30px;">序号</td>
						<td style="width:200px; text-align:center !important">合同编号</td>
						<td style="width:200px; text-align:center !important">租赁资源</td>
						<td style="text-align:center !important">费项类型</td>
						<td style="text-align:center !important">费项名称</td>
						<td style="text-align:center !important">缴费期限</td>
						<td style="text-align:center !important">计费起始日期</td>
						<td style="text-align:center !important">计费结束日期</td>
						<td style="text-align:center !important">应收金额</td>
					  </tr>
				{{each(i, record) recordList}}
					  <tr class="ficancial-tab" onclick="javascript:(function(){Route.params={id:${record.id}};location.href='index#finNeedRecDetail';})();">
						<td id="eq-add">${i+1}</td>
						<td title="${record.contractNo}">${record.contractNo}</td>
						<td title="${record.resourceNames}" class="sp-td">${record.resourceNames}</td>
						<td>${record.feeItemTypeName}</td>
						<td>${record.feeItemName}</td>
						<td class="dateFormat" data-date="${record.timeLimit}">${record.timeLimit}</td>
						<td class="dateFormat" data-date="${record.startTime}">${record.startTime}</td>
						<td class="dateFormat" data-date="${record.endTime}">${record.endTime}</td>
						<td>${record.accountPayable}</td>
					  </tr>
				{{/each}}
					  
			    </tbody>
	        </table>


</script>
