<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page isELIgnored="true"%>
<%@ taglib prefix="gd" uri="http://www.gdeng.cn/gd"%>
<section class="contract">
	<div class="header">参数设置</div>
	<div class="chargingStandardDT">
		<div class="col-sm-12">
			<section class="panel">
				<div class="contract-title-block">
					<span class="contract-info-h3">&nbsp;&nbsp;&nbsp;&nbsp;新增计费标准&nbsp;&nbsp;&nbsp;&nbsp;</span>
				</div>
				<form id="form-add"  class="form-horizontal clearfix cmxform">
					<div class="text-center overflow-h magin-auto padding-t-b-35 w-84 efrom">
						<ul id="wrap" class="overflow-h">
							<li>
					           	<div class="fl w-35 text-left"><i class="payment-cycle-sp">*</i>费项类型:</div>
					           	<div class="fl w-60">
						           	<select id="expType"  name="expType"  class="form-control" >
						           	</select>
	           					</div>
	           				</li>
	           				<li>
				           		<div class="fl w-35 text-left"><i class="payment-cycle-sp">*</i>费项名称:</div>
				           		<div class="fl w-60">
				           			<select id="expId"  name="expId" class="form-control">
				           			</select>
				           		</div>
				           	</li>
				           		<li>
					           	<div class="fl w-35 text-left"><i class="payment-cycle-sp">*</i>计费标准编码:</div>
					           	<div class="fl w-60"><input name="code"  type="text" value="" /></div>
				           	</li>
				           	<li>
					           	<div class="fl w-35 text-left"><i class="payment-cycle-sp">*</i>计费标准名称:</div>
					           	<div class="fl w-60"><input name="name"  type="text" value="" /></div>
				           	</li>
				           	<li id="rentModeContent">
					           	<div class="fl w-35 text-left"><i class="payment-cycle-sp">*</i>计算方法:</div>
					           	<div class="fl w-60">
					           		<select id="rentMode"  name="rentMode" class="form-control">
					           		</select>
					           	</div>
				           	</li>
				           	<li id="chargeAmount">
					           	<div class="fl w-35 text-left"><i class="payment-cycle-sp">*</i>费项金额:</div>
					           	<div class="fl w-60"><input type="text" name="chargeAmount"/></div>
				           	</li>
				            <li id="chargeUnitPrice" style="display:none;">
					           	<div class="fl w-35 text-left" style="height: 30px"><i class="payment-cycle-sp">*</i>计费单价:</div>
					           <div class="fl w-60"><input type="text" name="chargeUnitPrice"/></div>
				            </li>
			             	<li id="chargeUnitContent">
					           	<div class="fl w-35 text-left"><i class="payment-cycle-sp">*</i>计费单位:</div>
					           	<div class="fl w-60">
						           	<select name="chargeUnit"  id="chargeUnit" class="form-control">
					           		</select>
				           		</div>
				           </li>
				           <li id="wastageRateLi" style="display:none;">
					           	<div class="fl w-35 text-left">损耗率（%）</div>
					           	<div class="fl w-60"><input type="text" name="wastageRate" /></div>
				           </li>
				           <li id="sectionalChargeLi" style="display:none;">
								<div class="fl w-35 text-left"><i class="payment-cycle-sp">*</i>是否分段计费:</div>
								<label for="radio-1-1">
									<input type="radio"  id="radio-1-1"  name="sectionalCharge"  value="1" class="regular-radio" checked="checked">
								    <span class="radio-mt10"></span><em class="radio-em" >是</em>
								</label>
							    <label for="radio-1-2">
							    	<input type="radio" id="radio-1-2""  name="sectionalCharge"  value="0" class="regular-radio">
									<span class="radio-mt10"></span><em class="radio-em">否</em>
								</label>
						  </li>
						</ul>
						<div id="meterTable" class="step-table"  style="display:none;">
							<hr />
							<div class="etable">
								分段用量及价格设置
								<ul>
									<li id="deleteChargeSection"><i class="fa fa-trash-o"></i>
										<gd:btn btncode="BtnAddSecChargeDelete"><a style="cursor:pointer;">删除</a></gd:btn>
									</li>
									<li id="addChargeSection"><i class="fa fa-list"></i>
										<gd:btn btncode="BtnAddSecChargeAdd"><a style="cursor:pointer;">新增</a></gd:btn>
									</li>
								</ul>
							</div>
							<table>
								<thead>
									<tr>
										<th>序号</th>
										<th>下限用量(>X)</th>
										<th>上线用量(≤X)</th>
										<th>计费单价</th>
									</tr>
								</thead>
								<tbody id="meterTableBody" >
									<tr>
										<td>1</td>
										<td><input type="text" name="secionalCharges[0].downValue"  class="form-control wid150" value="0.00" readonly="readonly"> </td>
										<td><input type="text" name="secionalCharges[0].upValue"   class="form-control wid150" ></td>
										<td><input type="text" name="secionalCharges[0].chargeUnitPrice" class="form-control wid150"></td>
									</tr>
									<tr>
										<td>2</td>
										<td><input type="text" name="secionalCharges[1].downValue"  class="form-control wid150" ></td>
										<td><input type="text" name="secionalCharges[1].upValue"  class="form-control wid150"  value="99999999999.00" readonly="readonly"></td>
										<td><input type="text" name="secionalCharges[1].chargeUnitPrice"  class="form-control wid150"></td>
									</tr>
								</tbody>
							</table>
						</div>
						<button type="button" class="btn btn-ash btn-big" onclick="javascript:window.location='index#expenditureStandard'">取消</button>
						<gd:btn btncode="BtnExpStandardAddSave"><button type="submit" class="btn btn-danger btn-big">保存</button></gd:btn>
					</div>
				</form>
			</section>
		</div>
	</div>
</section>

