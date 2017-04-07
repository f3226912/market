<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page isELIgnored="true"%>
<%@ taglib prefix="gd" uri="http://www.gdeng.cn/gd"%>
<%@ include file="/WEB-INF/jsp/pub/tags.inc" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!-- page start-->
<div class="contract">
<div class="col-md-max">
  <div class="main-search">
       <div class="main-contracht-tp">
       	       <span class="motion-of-contract">租赁合同一览</span>
				<div class="input-group m-bot15">
					<div class="input-group-btn">
					</div>
					<div class="export-s" >
					   	<gd:btn btncode="BtnBiLeaseListExportData"><a id="exportData"><i class="fa fa-download"></i>导出</a></gd:btn>
					</div>
			</div>

			
       </div>
           <div class="main-ipt-types" style="display:block;" >
               	  <form class="form-input clearfix">
               	  		
				<div class="form-group">
						<select class="form-control wid150" id="marketResourceTypeId"  >
						
						</select>
					</div>
						<div class="form-group date-icon border-none-right">
						<input type="text" class="form-control start-date wid150"id="startTime"  placeholder="起租日期">
						<i class="fa fa-calendar"></i>
					</div>
					<div class="form-group date-icon border-none-right">
						<input type="text" class="form-control start-date wid150" id="endTime"  placeholder="结束日期">
						<i class="fa fa-calendar"></i>
					</div>
			
					<div class="form-group">
							<gd:btn btncode="BtnBiLeaseListQuery"><button class="btn btn-danger" type="button" id="query">搜索</button></gd:btn>
					
					</div>
				</form>
               </div>
   </div>
</div>
<div class="row">
    <div class="panel-body">
        <div class="adv-table editable-table ">
            <div class="space15"></div>
            <table class="table table-striped table-hover t-custom" id="tb">
                <thead>
                <tr class="ml-ct">
                            <th>NO.</th>
                            <th>资源类型</th>
                            <th>区域</th>
                            <th>资源总数</th>
                            <th>已租数量</th>
                            <th>出租率(数量)%</th>
                            <th>可租面积 ㎡</th>
                            <th>已租面积 ㎡</th>
                            <th>出租率(面积)%</th>
                            <th>租金收入 元</th>
                            <th>租赁坪效(总面积)元/㎡</th>
                            <th>租赁坪效(已租面积)元/㎡</th>
                            
                </tr>
                </thead>
                <tbody id="templateBody">
                </tbody>
            </table>
            <!-- 分页组件 -->
            <div id="pagebar"></div>
        </div>
    </div>
</div>
</div>
<!-- page end-->
<script id="template" type="text/x-jquery-tmpl">
	{{each(i, biLease) biLeaseList}}
		<tr class="ml-ct">
            <td>{{if (i+1)!=biLeaseList.length}}${i + 1}{{else}}{{/if}}</td>
			<td>${biLease.marketResourceTypeName}</td>
			<td>${biLease.areaName}</td>
			<td>${biLease.countResource}</td>
			<td>${biLease.countRented}</td>
			<td>${biLease.rentalRate}</td>
			<td>${biLease.areaAvailableForRent}</td>
			<td>${biLease.leasedArea}</td>
			<td>${biLease.rentalRateArea}</td>
			<td>${biLease.rentalIncome}</td>
			<td>${biLease.leasePxZmj}</td>
			<td>${biLease.leasePxYzmj}</td>
    	</tr>
	{{/each}}
   
</script>
