<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="gd" uri="http://www.gdeng.cn/gd"%>
<%@ page isELIgnored="true"%>
<!-- page start-->
<style>
 #pagebar .fl{margin-left:0 !important;}
 #pagebar .dataTables_info{margin-top:0;}
 .form-input .form-group{ margin-right:5px !important;}
 .contract .export-s{margin-top:0 !important;}
</style>
<div class="col-md-max contract">
  <div class="main-search">
       <div class="main-contracht-tp">
       	       <span class="motion-of-contract">计量表抄表<span style="font-size:12px; padding-left:10px;">共<strong style="color:#ea785b" id="recordCount">123</strong>笔 </span></span> <!-- <span id="total"></span> -->
				<div class="input-group m-bot15">
					<div class="input-group-btn">
					</div>
				
				<gd:btn btncode="BtnMeterReadingStatement"><div class="export-s" id="statement"><i class="fa fa-table" style="color:#999; font-size:16px;"></i>&nbsp; <span style="color:#e86d4d">结转为待付款</span></div></gd:btn>
			</div>
			
       </div>
          <div class="main-ipt-types" style="display:block;padding-left: 15px;">
               	  <form class="form-input clearfix">
					<div class="form-group">
					区域/楼栋：
						<select class="form-control wid150 display-inline" style="display:inline;" id="areaId">
						
						</select>
					</div>
					<div class="form-group">
						<select class="form-control wid150 display-inline" style="display:inline;" id="buildingId">
							<option value="">全部楼栋</option>
						</select>
					</div>
					<div class="form-group">
					计量表分类：
						<select class="form-control wid150 display-inline" style="display:inline;" id="measureTypeId">
							
						</select>
					</div>
					
					<div class="form-group date-icon border-none-right">
					上次抄表日期：
						<input style="display:inline;" type="text" id="addNoteDateStart" class="form-control start-date wid150 display-inline" placeholder="起始日期">
						<i class="fa fa-calendar"></i>
					</div>
					<div class="form-group date-icon border-none-right">
						<input type="text" id="addNoteDateEnd" class="form-control start-date wid150" placeholder="结束日期">
						<i class="fa fa-calendar"></i>
					</div>
					<div class="form-group">
						<gd:btn btncode="BtnMeterReadingQuery"><button class="btn btn-danger" type="button" id="query">搜索</button></gd:btn>
					</div>
				</form>
               </div>
   </div>
</div>
<div class="row row_ml0">
    <div class="panel-body">
        <div class="adv-table editable-table" style="padding:0 15px">
            <div class="space15"></div>
            <table class="table table-striped table-hover t-custom" id="tb">
                <thead>
                <tr class="ml-ct">
                    <th>NO.</th>
                    <th>计量表编号</th>
                    <th>计量表分类</th>
                    <th>本次读数</th>
                    <th>损耗用量</th>
                    <th>上次读数</th>
                    <th>上次抄表日期</th>
                    <th>关联资源</th>
                    <th>所在楼栋</th>
                    <th>所在区域</th>
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
<!-- Modal -->
<div class="modal fade" id="myModal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
  <div class="modal-dialog">
    <div class="modal-content">
      <div class="modal-header">
        <button type="button" class="close" data-dismiss="modal"><span aria-hidden="true">&times;</span><span class="sr-only">Close</span></button>
        <h4 class="modal-title">抄表</h4>
      </div>
      <div class="modal-body">
      	<form id="formMeterReading" novalidate class="form-horizontal cmxform">
        <div class="form-group">   
        	<label class="control-label col-md-3"><i class="payment-cycle-sp">*</i>本次读数:</label>   
        	<div class="col-md-6 text-left">    
        		<input type="text" class="form-control inputVal1" name="current" required/>
        	</div>  
        	</div>
        	<div class="form-group">   
        	<label class="control-label col-md-3"><i class="payment-cycle-sp">*</i>损耗用量:</label>   
        	<div class="col-md-6 text-left">    
        		<input type="text" class="form-control inputVal2" name="wastage" required/>	
        		<p>损耗用量=(本次读数-上次抄表读数)*损耗率</p>
        	</div>  
        	</div>
        	<div class="form-group">   
        	<label class="control-label col-md-3"><i class="payment-cycle-sp">*</i>抄表日期:</label>   
        	<div class="col-md-6 text-left">    
        		<div class="form-group date-icon border-none-right">
						<input type="text" id="addNoteDate" class="form-control start-date wid150 inputVal3" required style="width: 271px !important; margin-left: 13px !important;">
						<i class="fa fa-calendar" style="right: 25px;"></i>
					</div>	     
        	</div>  
        	</div>
        	<div class="form-group">   
        	<label class="control-label col-md-3">备注:</label>   
        	<div class="col-md-6 text-left">    
        		<textarea type="text" class="form-control inputVal4" rows="6" maxlength="100"/></textarea>	     
        	</div>  
        	</div>
        	
        	<div class="form-group">    
        	<div class="col-md-6 text-left">    
        		<input type="hidden" class="form-control inputVal5" />	
        	</div>  
        	</div>
        	<input type="hidden" id="addLast">
        	<input type="hidden" id="addWastagerate">
        	<input type="hidden" id="currentMaxValue">
        </form>
      </div>
      
      <div class="modal-footer">
        <button type="button" class="btn btn-default" data-dismiss="modal">取消</button>
        <button type="button" class="btn btn-primary" id="saveTbale">保存</button>
      </div>
      </div>
    </div>
  </div>


<!-- page end-->
<script id="template" type="text/x-jquery-tmpl">
	{{each(i, meterReading) meterReadings}}
		<tr class="ml-ct">
		<td>${i+1}</td>
		<td style="color:#165ae4"> <a href="javascript:void(0)" onclick="javascript:(function(){Route.params={measureId:${meterReading.measureId}};location.href='index#measureSettingForward';})();">${meterReading.measureCode}</a></td>
		<td>${meterReading.measureTypeName}</td>
		<td class="lsCurrent ed fontColor tableEdit"><gd:btn btncode="BtnMeterReadingMeter"><a style="cursor: pointer;"></gd:btn>{{if meterReading.current==0}}抄表{{else}}${meterReading.current}{{/if}}<gd:btn btncode="BtnMeterReadingMeter"></a></gd:btn></td>
		<td class="lsWastage ed">{{if meterReading.wastage==0}}-{{else}}${meterReading.wastage}{{/if}}</td>
		<td>${dealFormatResult(meterReading.last)}</td>
		<td>${meterReading.lastNoteDateStr}</td>
		<td style="color:#165ae4"><a href="javascript:void(0)" onclick="javascript:(function(){Route.params={resourceId:${meterReading.resourceName}};location.href='index#measureSettingResourceForward';})();">${meterReading.resourceNames}</a></td>
		<td>${meterReading.buildName}</td>
		<td>${meterReading.areaName}</td>
		<input type = "hidden" class="lsNoteDate ed">
		<input type = "hidden" class="lsRemark ed">
		<input type = "hidden" class="lsMeasureId ed" value="${meterReading.measureId}">
		<input type = "hidden" class="lsMaxMeasureVal ed" value="${meterReading.maxMeasureVal}">
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