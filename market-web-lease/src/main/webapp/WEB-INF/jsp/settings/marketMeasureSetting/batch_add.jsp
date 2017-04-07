<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page isELIgnored="true"%>
<%@ include file="/WEB-INF/jsp/pub/tags.inc" %>

<style>
	.efrom ul li .Einput-s {
	    width: 100% !important;
	}
</style>
<section class="contract addMeterM">
	<div class="header">参数设置</div>
	<div class="chargingStandardDT">
		<div class="col-sm-12">
			<section class="panel">
				<div class="contract-title-block">
					<span class="contract-info-h3">&nbsp;&nbsp;&nbsp;&nbsp;批量新增计量表&nbsp;&nbsp;&nbsp;&nbsp;</span>
				</div>
				<form id="batchAddForm" class="form-horizontal clearfix cmxform">
					<input type="hidden" value="" name="resourceId" />
					<input type="hidden" value="" name="expId" id="expId" />
					<input type="hidden" value="" name="expName" id="expName"/>
				
					<div class="text-center overflow-h magin-auto padding-t-b-35 w-84 efrom">
						<ul id="wrap" class="overflow-h">
							<li>
								<div class="fl w-35 text-left"><i class="payment-cycle-sp">*</i>计量表分类:</div>
								<div class="fl w-60">
									<select name="measureTypeId" id="measureTypeId" class="form-control valid">
									</select>
								</div>
							</li>
							<li>
								<div class="fl w-35 text-left"><i class="payment-cycle-sp">*</i>表编号前缀:</div>
								<div class="fl w-60">
									<input type="text" name="prefix" />
								</div>
							</li>
							
							<li>
								<div class="fl w-35 text-left"><i class="payment-cycle-sp">*</i>起始编号:</div>
								<div class="fl w-60">
									<input type="text" value="" name="startNum" />
								</div>
							</li>
							<li>
								<div class="fl w-35 text-left"><i class="payment-cycle-sp">*</i>截止编号:</div>
								<div class="fl w-60">
									<input type="text" name="endNum" />
								</div>
							</li>
							
							<li>
								<div class="fl w-35 text-left"><i class="payment-cycle-sp">*</i>最大读数:</div>
								<div class="fl w-60">
									<input type="text" name="maxVal" />
								</div>
							</li>
							
							<li>
								<div class="fl w-35 text-left"><i class="payment-cycle-sp">*</i>走表类费项:</div>
								<div class="fl w-60">
									<input type="text" name="expNameTemp" id="expNameTemp" readonly="readonly"/>
								</div>
							</li>
							
							<li>
								<div class="fl w-35 text-left"><i class="payment-cycle-sp">*</i>表状态:</div>
								<label for="radio-1-1" style="float:left">
									<input type="radio" id="radio-1-1" name="status" class="regular-radio" value="1" >
								    <span class="radio-mt10"></span><em class="radio-em">启用</em>
								</label>
							    <label for="radio-1-2">
								    <input type="radio" id="radio-1-2" name="status" class="regular-radio" value="0" checked="checked">
									<span class="radio-mt10"></span><em class="radio-em" style="padding-right:0">不启用</em>
								</label>
							</li>
							
						</ul>
						<button type="button" onclick="javascript:window.location='index#measureSetting'" class="btn btn-ash btn-big">取消</button>
						<gd:btn btncode="BtnMeasureBatchAddSave">
							<input type="submit" class="btn btn-danger btn-big" id="add_btn" value="保存"></input>
						</gd:btn>
						
					</div>
				</form>
			</section>
		</div>
	</div>
</section>

