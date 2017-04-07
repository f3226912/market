<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="gd" uri="http://www.gdeng.cn/gd"%>
<%@ page isELIgnored="true"%>
<input value="0" type="hidden" id="selectType" />
       <div class="col-md-max contract">
          <div class="main-search">
               <div class="main-contracht-tp">
               	       <span class="motion-of-contract">合同管理</span>
						<div class="input-group m-bot15">
							<div class="input-group-btn">
								<button type="button" id="toggle-box" class="btn btn-white dropdown-toggle" data-toggle="dropdown">租赁资源<span class="caret red-icon" style="margin-left:4px"></span></button>
								<ul class="dropdown-menu dropdown-lt-20 son_ul">
									<li value="0">
										<a href="javascript:;">租赁资源</a>
									</li>
									<li value="1">
										<a href="javascript:;">客商名称</a>
									</li>
									<li value="2">
										<a href="javascript:;">客商手机</a>
									</li>
								</ul>
							</div>
						<input type="text" class="form-control wid150 border-none-right" id="searchInput" style="margin-top:17px;"/>
						<button type="button" class="btn btn-white ml-icon" style="margin-top: -4px;"><i class="fa fa-search red-icon" id="btn-search"></i></button>
						<span class="high-search"> 高级搜索<i class="fa fa-angle-down"></i></span>
						<div class="export-s" style="margin-top:0;">
							<gd:btn btncode="BtnContractManageAdd"><span class="add-export"><i class="fa fa-plus-square fa-cor" style="font-size:18px; position:relative; top:2px;"></i><a href="index#addContract" style="color:#e86d4d; padding-left:8px;">新增</a></span></gd:btn>
							<gd:btn btncode="BtnContractManageExport"><span class="remove-export" id="exportData"><i class="fa fa-download" style="font-size:18px; position:relative; top:2px;"></i><span style="color:#e86d4d; padding-left:8px;">导出</span> </span></gd:btn>
						</div>
					</div>
					
               </div>
               <div class="main-ipt-types" style="display:none;">
               	  <form class="form-input clearfix">
					<div class="form-group">
						<input type="text" class="form-control wid150" id="contractMain_contractNo" placeholder="合同编号">
					</div>
					<div class="form-group">
						<select class="form-control wid150" id="contractMain_approvalStatus"  >
							<option selected="" value="">审批状态</option>
							<option value="0">待审批</option>
							<option value="1">审批中</option>
							<option value="2">已驳回</option>
							<option value="3">已通过</option>
						</select>
					</div>
					<div class="form-group">
						<select class="form-control wid150" id="contractMain_contractStatus"  >
							<option selected="" value="">合同状态</option>
							<option value="0">待执行</option>
							<option value="1">执行中</option>
							<option value="2">已结算</option>
						</select>
					</div>
					<div class="form-group date-icon border-none-right">
						<input type="text" class="form-control start-date wid150"id="contractMain_startLeasingDay"  placeholder="起租日期">
						<i class="fa fa-calendar"></i>
					</div>
					<div class="form-group date-icon border-none-right">
						<input type="text" class="form-control start-date wid150" id="contractMain_endLeasingDay"  placeholder="结束日期">
						<i class="fa fa-calendar"></i>
					</div>
					<div class="form-group">
						<gd:btn btncode="BtnContractManageQuery"><button class="btn btn-danger" type="button" id="high-btn-search">搜索</button></gd:btn>
					</div>
				</form>
               </div>
           </div>
       
          <!--   <div class="col-md-12 col-pd-10">
                <div class="more-search">更多搜索<span class="mores">+</span></div>
            </div> -->
            <div class="wrp-box" id="wrp-box"></div>
            <div id="pagebar" class="ml1_0"></div>
        <!-- page end-->
       
        </div>

<!-- page end-->
<script id="template" type="text/x-jquery-tmpl">
 <div class="row"> 
{{each(i, row) rows}}
		<gd:btn btncode="BtnContractManageDetail"><a href="javascript:(function(){Route.params={id:${row.id}, contractNo:'${row.contractNo}'};location.href='index#contractManageDetail';})();"></gd:btn>
            <div class="col-md-6">
            <!--pagination start-->
            <section class="panel">
                <header class="panel-heading">
                <span class="fz-clr-a1">合同编号：
                   <span class="fz-green">${row.contractNo}</span>
                  {{if row.approvalStatus==2}}
                   <span class="fz_red">
                  {{else}}
                   <span class="fz_co">
                   {{/if}}
                      ${row.approvalStatusValue}
                 </span>

                </span>  
                <span class="tools pull-right">
            {{if row.contractStatus==2}}
            	<span class="ipt fulfil-color">
            {{else row.contractStatus==1}}
            	 <span class="ipt ipt-color">
            {{else row.contractStatus==0}}
            	 <span class="ipt">
        	{{/if}}
                  ${row.contractStatusValue}</span>
                </span>

                </header>
                <div class="panel-body">
                    <p class="col-md-12 lsg-res">租赁资源：
                       <span class="res-clr"  title="${row.leasingResource}" >${row.leasingResource}</span>
                    </p>
                    <div class="col-md-6">
                      <ul class="col-box-ul">
                     	<li>
                     	   <span class="col-li-left">起租日期：</span>
                     	   <span class="col-li-right">${row.startLeasingDayString}</span>
                     	</li>
                     	<li>
                     	   <span class="col-li-left">乙方：</span>
                     	   <span class="col-li-right">${row.partyB}</span>
                     	</li>
                     	<li>
                     	   <span class="col-li-left">合同面积：</span>
                     	   <span class="col-li-right">${row.countArea}m²</span>
                     	</li>
                     	<li>
                     	   <span class="col-li-left">经办人：</span>
                     	   <span class="col-li-right">${row.trustees}</span>
                     	</li>
                      </ul>
                    </div>
                    <div class="col-md-6">
                      <ul class="col-box-ul">
                     	<li>
                     	   <span class="col-li-left">结束日期：</span>
                     	   <span class="col-li-right">${row.endLeasingDayString}</span>
                     	</li>
                     	
                     	<li>
                     	     <span class="col-li-left">手机号码：</span>
                     	   <span class="col-li-right">${row.customerMobile}</span>
                     	</li>
						<li>
                     	   <span class="col-li-left">客户名称：</span>
                     	   <span class="col-li-right">${row.customerName}</span>
                     	</li>
                     	<li>
                     	   <span class="col-li-left">签约日期：</span>
                     	   <span class="col-li-right">${row.dateOfContractString}</span>
                     	</li>
                      </ul>
                    </div>
                  </div>
            </section>
             <!--pagination end-->
            </div>
            <gd:btn btncode="BtnContractManageDetail"></a></gd:btn>
         	{{/each}}  
         </div>



</script>

