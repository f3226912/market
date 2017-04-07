<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page isELIgnored="true"%>
	<%@ taglib prefix="gd" uri="http://www.gdeng.cn/gd"%>
	<style>
	.esb{
		overflow:hidden;
		text-overflow:ellipsis;
		-o-text-overflow:ellipsis;
		white-space:nowrap;
		margin:0;
	}
</style>
<!-- page start-->
<div class="contract">
<div class="col-md-max">
  <div class="main-search">
       <div class="main-contracht-tp">
       	       <span class="motion-of-contract">已过期合同</span>
				<div class="input-group m-bot15">
					<div class="input-group-btn">
					</div>
					<div class="export-s">
					<gd:btn btncode="BtnBiOverContractMainExport">
					<a class="fa fa-download"  id="exportData" >导出</a></gd:btn> </div>
			</div>

			
       </div>
           <div class="main-ipt-types" style="display:block;">
               	  <form class="form-input clearfix">
					<div class="form-group">
						<input type="text" class="form-control wid150" id="leasingResource" placeholder="租赁资源">
					</div>
					<div class="form-group">
						<input type="text" class="form-control wid150" id="contractNo" placeholder="合同编号">
					</div>

					
				<div class="form-group">
						<select class="form-control wid150" id="contractStatus"  >
							<option selected="" value="">合同状态</option>
							<option value="0">待执行</option>
							<option value="1">执行中</option>
							<option value="2">已结算</option>
						</select>
					</div>
					<div class="form-group">
						<gd:btn btncode="BtnBiOverContractMainQuery"><button class="btn btn-danger" type="button" id="query" >搜索</button>
					</gd:btn>
					</div>
				</form>
               </div>
   </div>
</div>
</div>
<div class="">
    <div class="panel-body">
        <div class="adv-table editable-table ">
            <div class="space15"></div>
            <table class="table table-striped table-hover t-custom" id="tb">
                <thead>
                <tr class="ml-ct">
                            <th>NO.</th>
                            <th>合同编号</th>
                            <th style="width:300px">租赁资源</th>
                            <th>累计租金收入</th>
                            <th>乙方</th>
                            <th>计费面积</th>
                            <th>起租日期</th>
                            <th>结束日期</th>
                            <th>合同状态</th>
                            
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
<!-- page end-->
<script id="template" type="text/x-jquery-tmpl">
	{{each(i, contract) contractList}}
		<tr class="ml-ct">
            <td>${i + 1}</td>
			<td><div class="esb" style="max-width:120px; !important;">${contract.contractNo}</div></td>
			<td title="${contract.leasingResource}"><div class="esb" style="width:300px; !important;">${contract.leasingResource}<div></td>
			<td>${contract.accountPayed}</td>
			<td title="${contract.partyB}"><div class="esb" style="width:100px; !important;">${contract.partyB}</div></td>
			<td>${contract.countArea}</td>
			<td>${contract.startLeasingDay}</td>
			<td>${contract.endLeasingDay}</td>
			<td>${contract.contractStatus}</td>

    	</tr>
	{{/each}}

</script>
