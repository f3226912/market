<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="gd" uri="http://www.gdeng.cn/gd"%>
<%@ page isELIgnored="true"%>
<!-- page start-->
<style>
 #pagebar .fl{margin-left:0 !important;}
 #pagebar .dataTables_info{margin-top:0;}
 .checkbox-none ,.check_cur{display:none;}
 .checkbox-none + span ,.check_cur + span {
    -webkit-appearance: none;
    border: solid 1px #bfbfbf;
    width: 15px;
    height: 15px;
    display: inline-block;
    position: relative;
    top: 3px;
    cursor: pointer;
  }
  .checkbox-none:checked + span:after ,.check_cur:checked + span:after{
	content: ' ';
	width:15px;
    height:15px;
	position: absolute;
	left:-1px;
	top:-1px;
	background: #53b279;
	border:solid 1px #53b279;
	font-size: 32px;
	
}
.table> tbody> tr> td, .table> tfoot> tr> td, .table> thead> tr> td, table> tbody> tr> td, table> tfoot> tr> td, table> thead> tr> td{
 padding: 0 4px 0 1px !important;
}
.check_current{ margin-left:6px;}
.contract .export-s{margin-top:0 !important;}
</style>

<div class="col-md-max contract">
  <div class="main-search">
       <div class="main-contracht-tp">
       	       <span class="motion-of-contract">计量表抄表收费记录</span>
				<div class="input-group m-bot15">
					<div class="input-group-btn">
					</div>
				<gd:btn btncode="BtnFinanceGaugeChargeCheques"><div class="export-s" id="financeGuageChargeCheques"><i class="fa fa-credit-card" style="color:#999; font-size:14px;"></i>&nbsp; <span  style="color:#e86d4d">收款</span></div></gd:btn>
				<gd:btn btncode="BtnFinanceGaugeChargeExport"><div class="export-s" id="financeGuageChargeExport"><i class="fa fa-download"></i>&nbsp; <span style="color:#e86d4d">导出</span></div></gd:btn>
			</div>
			
       </div>
          <div class="main-ipt-types" style="padding-left:15px; display:block">
               	  <form class="form-input clearfix">
					<div class="form-group" style="margin-right:6px">
					区域/楼栋：
						<select class="form-control wid120 display-inline" id="areaId">
						
						</select>
					</div>
					<div class="form-group" style="margin-right:6px">
						<select class="form-control wid120 display-inline" id="buildingId">
							<option value="">全部楼栋</option>
						</select>
					</div>
					
					<div class="form-group" style="margin-right:5px">
					计量表分类：
						<select class="form-control wid120 display-inline" id="measureTypeId">
							
						</select>
					</div>
					
					<div class="form-group" style="margin-right:6px">
					收款状态：
						<select class="form-control wid120 display-inline" id="status">
							<option selected="selected"  value="">全部</option>
							<option value="0">未收款</option>
							<option value="1">已收款</option>
						</select>
					</div>
					<div>
					    <span style="float: left; margin-top: 7px;">抄表日期：</span>
						<div class="form-group date-icon border-none-right" style="margin-right:6px">
							<input type="text" class="form-control start-date wid120" id="addNoteDateStart" placeholder="起始日期">
							<i class="fa fa-calendar"></i>
						</div>
						<div class="form-group date-icon border-none-right" style="margin-right:6px">
							<input type="text" class="form-control start-date wid120" id="addNoteDateEnd" placeholder="结束日期">
							<i class="fa fa-calendar"></i>
						</div>
						<gd:btn btncode="BtnFinanceGaugeChargeQuery"><button class="btn btn-danger" type="button" id="query" style="float:left;">搜索</button></gd:btn>
					</div>
					<!-- <div class="form-group" style="margin-left:8px">
						
					</div> -->
				</form>
               </div>
   </div>
</div>
<div class="row costDefinition row_ml0" style="margin:0px auto;padding:0px;">
    <div class="panel-body" style="padding:0px">
        <div class="adv-table editable-table " style="margin:15px">
            <div class="space15"></div>
            <table class="table table-striped table-hover t-custom" id="tb" style="width:100%; display: table; margin:30px auto;border: solid 1px #ddd;table-layout:fixed;">
                <thead>
                <tr class="ml-ct">
                	<th style="width:30px"><label for="checkboxAll"><input type="checkbox" id="checkboxAll" class="checkbox-none"/><span class="checkbox-layer"></span></label></th>
                    <th style="width:30px">NO.</th>
                    <th style="width:65px;">计量表编号</th>
                    <th style="width:65px;">计量表分类</th>
                    <th style="width:65px;">抄表日期</th>
                    <th style="width:65px;">本次读数</th>
                    <th style="width:65px;">上次读数</th>
                    <th style="width:65px;">损耗用量</th>
                    <th style="width:65px;">计费单价</th>
                    <th style="width:65px;">收费金额</th>
                    <th style="width:65px;">是否收款</th>
                    <th style="width:65px;">客户名称</th>
                    <th style="width:50px;">乙方</th>
                    <th style="width:130px;">关联资源</th>
                    <th style="width:65px;">抄表人</th>
                </tr>
                </thead>
                <tbody id="templateBody">
                </tbody>
            </table>
            <!-- 分页组件 -->
            <div id="pagebar" class="ml1_0"></div>
        </div>
    </div>
</div>
<!-- page end-->
<script id="template" type="text/x-jquery-tmpl">
	{{each(i, financeGuageCharge) financeGuageCharges}}
		<tr class="ml-ct">
		<td><label for="checkboxCurrent"><input class="Echeckbox check_cur" id="" type="checkbox" data="${financeGuageCharge.id}"/><span class="check_current"></span></label></td>
		<td style="padding-left:5px!important; width:74px; overflow:hidden;">${i+1}</td>
		<td style="width:74px; overflow: hidden!important;" title="${financeGuageCharge.measureCode}" style="color:#165ae4"><a href="javascript:void(0)" onclick="javascript:(function(){Route.params={measureId:${financeGuageCharge.measureId}};location.href='index#measureSettingForward';})();">${financeGuageCharge.measureCode}</a></td>
		<td style="width:74px; overflow: hidden!important;" title="${financeGuageCharge.measureTypeName}">${financeGuageCharge.measureTypeName}</td>
		<td>${financeGuageCharge.noteDateStr}</td>
		<td>${dealFormatResult(financeGuageCharge.current)}</td>
		<td>${dealFormatResult(financeGuageCharge.last)}</td>
		<td style="width:74px;overflow: hidden;" title="${dealFormatResult(financeGuageCharge.wastage)}">{{if financeGuageCharge.wastage==0}}-{{else}}${dealFormatResult(financeGuageCharge.wastage)}{{/if}}</td>
		<td style="width:74px;overflow: hidden;" title="${dealFormatResult(financeGuageCharge.price)}">${dealFormatResult(financeGuageCharge.price)}</td>
		<td style="width:74px;overflow: hidden;" title="${dealFormatResult(financeGuageCharge.amount)}"><gd:btn btncode="BtnFinanceGaugeChargeDetail"><a href="javascript:(function(){Route.params={id:${financeGuageCharge.id}};location.href='index#financeGuageChargeDetail';})();"></gd:btn>${dealFormatResult(financeGuageCharge.amount)}<gd:btn btncode="BtnFinanceGaugeChargeDetail"></a></gd:btn></td>
		<td class="tdstatus">{{if financeGuageCharge.status==1}}已收款{{else}}未收款{{/if}}</td>
		<td style="width:74px;overflow: hidden;" title="${financeGuageCharge.customerName}">${financeGuageCharge.customerName}</td>
		<td style="width:74px;overflow: hidden;" title="${financeGuageCharge.partyB}">${financeGuageCharge.partyB}</td>
		<td title="${financeGuageCharge.resourceNames}" style="color:#165ae4; width:100px;overflow: hidden;"><a href="javascript:void(0)" onclick="javascript:(function(){Route.params={resourceId:${financeGuageCharge.resourceName}};location.href='index#measureSettingResourceForward';})();">${financeGuageCharge.resourceNames}</a></td>
		<td>${financeGuageCharge.noteUser}</td>
    	</tr>
	{{/each}}

</script>

<script id="measureTypeIdTemp" type="text/x-jquery-tmpl">
	<option value="">全部</option>
    {{each(i, row) rows}}
		<option value="${row.id}">${row.name}</option>
	{{/each}}
</script>
<script id="areaIdTemp" type="text/x-jquery-tmpl">
	<option value="">全部区域</option>
    {{each(i, row) rows}}
		<option value="${row.id}">${row.name}</option>
	{{/each}}
</script>
<script id="buildIdTemp" type="text/x-jquery-tmpl">
	<option value="">全部楼栋</option>
    {{each(i, row) rows}}
		<option value="${row.id}">${row.name}</option>
	{{/each}}
</script>
