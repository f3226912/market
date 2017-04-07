<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page isELIgnored="true"%>
<div class="header">参数设置</div>
	<div class="costDefinition contract">
    <div class="col-sm-12">
        <section class="panel">
            <div class="contract-title-block">
                <span class="contract-info-h3">&nbsp;&nbsp;&nbsp;市场详情&nbsp;&nbsp;&nbsp;</span>
            </div>
        	<form id="form-editComUser" action="post/addPost" class="form-horizontal clearfix">
			<div class="text-center overflow-h magin-auto padding-t-b-35 w-40 efrom">
			           		<ul class="overflow-h">
			           			<li>
			           				<div class="fl w-35">市场编号：</div>
			           				<div class="fl w-60">				
			           					<input id="code" name="code" type="text" class="form-control" readonly/>
									</div>
			           			</li>
			           			<li>
			           				<div class="fl w-35">市场名称：</div>
			           				<div class="fl w-60">				
										<input id="name" name="name" type="text" class="form-control" readonly/>
									</div>
			           			</li>
			           			<li>
			           				<div class="fl w-35">市场简称：</div>
			           				<div class="fl w-60">				
										<input id="nameShort" name="nameShort" type="text" class="form-control" readonly/>
									</div>
			           			</li>
			           			<li>
			           				<div class="fl w-35">所属公司： </div>
			           				<div class="fl w-60">				
										<input id="companyName" name="companyName" type="text" class="form-control" readonly/>
									</div>
			           			</li>
			           			<li>
			           				<div class="fl w-35">开业时间： </div>
			           				<div class="fl w-60">				
										<input id="openTime" name="openTime" type="text" class="form-control" readonly/>
									</div>
			           			</li>
			           			<li>
			           				<div class="fl w-35">市场状态:</div>
			           				<div class="fl w-60">
    									<input id="marketStatus" name="marketStatus" type="text" class="form-control" readonly/>
			           				</div>
			           			</li>
			           			<li>
			           				<div class="fl w-35">所在地： </div>
			           				<div class="fl w-60">				
										<input id="pca" name="pca" type="text" class="form-control" readonly/>
										<textarea id="address" style="margin-top:10px;" name="address" rows="1" cols="20" class="form-control" readonly></textarea>
									</div>
			           			</li>
			           			
			           		</ul>
		    					<button data-dismiss="modal" class="btn btn-default" type="button" onclick="javascript:window.location='index#market'">返回</button>
			           </div>
			</form>
        </section>
    </div>
</div>

