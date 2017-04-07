<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page isELIgnored="true"%>
<section class="meterManagement">
	<div class="header">参数设置</div>
	
	<div class="costDefinition contract">
	    <div class="col-sm-12">
	        <section class="panel">
	            <div class="contract-title-block">
	                <span class="contract-info-h3">&nbsp;&nbsp;&nbsp;&nbsp;市场管理&nbsp;&nbsp;&nbsp;&nbsp;</span>
	            		<ul>
	            		<div class="right-icon" style="text-align:right;">
	            			<!-- <gd:btn btncode="BtnSettingsMarketImg">
								<a href="index#graph" class="cursor-p"><i class="fa fa-download"></i>平面图设置</a>
							</gd:btn> -->
		            		<a href="index#graph" class="cursor-p"><i class="fa fa-download"></i>平面图设置</a>
						</div>
	            		</ul>
	            </div>
	            <div class="panel-body">
	                <div class="adv-table editable-table ">
	                    <div class="space15"></div>
	                    <table class="table table-striped table-hover table-bordered" id="tb">
	                        <thead>
	                        <tr class="ml-ct">
	                        	<th>市场编号</th>
	                            <th>市场简称</th>
	                            <th>开业日期</th>
	                            <th>市场状态</th>
	                            <th>所在地</th>
	                        </tr>
	                        </thead>
	                        <tbody id="templateBody">
	                        </tbody>
	                    </table>
	                    
	                </div>
	                
	            </div>
	            
	        </section>
	        <div class="row-fluid">
	             <div class="col-lg-12" id="pagebar"></div>
	        </div>
	    </div>
	</div>
</section>
<script id="template" type="text/x-jquery-tmpl">
	{{each(i, market) markets}}
		<tr class="ml-ct">
			<td>${market.code}</td>
			<td>
				<a class="check-auth" href="javascript:(function(){Route.params={marketId:${market.id}};location.href='index#viewMarket';})();">${market.nameShort}</a>&nbsp;
			</td>
			<td>${formateDate(market.openTime,'yyyy-MM-dd')}</td>
			<td>${market.marketStatusStr}</td>
			<td>${market.pca}</td>



    	</tr>
	{{/each}}
</script>