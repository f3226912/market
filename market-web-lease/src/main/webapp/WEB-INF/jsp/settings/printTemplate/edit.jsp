<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="gd" uri="http://www.gdeng.cn/gd"%>
			<style>
 					.uploadify{opacity:0;width:503px !important;}
 					.uploadify object,.uploadify div{width:503px !important;top:0;left:0;}
 					.fl{float:left}
 					.w-100{width:100%}
 					.einput-s1{
 						width:80%;
 						height: 32px;
					    padding: 5px 5px 5px 10px;
					    border: 1px solid #D7D7D7;
					    font-size: 12px;
					    color: #999;
					    border-radius: 4px;
 					}
 					.ebutton1{
 						width: 17%;
					    background-color: #53b27a;
					    color: #FFFFFF;
					    height: 32px;
					    line-height: 32px;
					    float: left;
					    font-size: 12px;
					    margin-left: 3%;
					    border-radius: 2px;
					    text-align: center;
 					}
 			</style>
<section class="header">套打模板设置</section>
<div class="form-page" style="padding: 10px">
	<form id="form-editPrintTemplate" action="printSetting/saveAdd" class="form-horizontal clearfix cmxform">
		<input type="hidden" id="id" name="id" value="${dto.id }"/>
		<div class="form-group col-lg-12">
			<label class="control-label col-md-4">
				<i class="payment-cycle-sp">*</i>模板编码：
			</label>
			<div class="col-md-6 text-left">
				<input id="templateCode" name="templateCode" type="text" class="form-control" value="${dto.templateCode }">
			</div>
		</div>
		
		<div class="form-group col-lg-12">
			<label class="control-label col-md-4">
				<i class="payment-cycle-sp">*</i>模板名称： 
			</label>
			<div class="col-md-6 text-left">
				<input id="templateName" name="templateName" type="text" class="form-control" value="${dto.templateName }">
			</div>
		</div>

		<div class="form-group col-lg-12">
			<label class="control-label col-md-4">
				<i class="payment-cycle-sp">*</i>模板文件： 
			</label>
			
			<div class="col-md-6 text-left">	
				<div class="fl w-100">
					<input class="einput-s1 fl" type="text" value="" name="resourceName" id="resourceName">		
					<div id="btnSelectResource" class="ebutton1 cursor-p fl">选择</div>
				</div>	
 				<input type="file" name="file" id="uploadify"/>
 				<input type="hidden" id="templateFile" name="templateFile" value="${dto.templateFile }"/>	
 				<input type="hidden" id="templateUrl" name="templateUrl" value="${dto.templateUrl }"/>
 				目前仅支持word 2003版本<br><br>
 				<div id="uploadFile">
 				
	 				<c:if test="${templateUrl != ''}">
	 					<a href="${host}${dto.templateUrl}">${dto.templateFile }</a>
	 				</c:if>
	 			</div>
 			</div>
			
		</div>
		
		<div class="form-group col-lg-12">
			<label class="control-label col-md-4">
				备注： 
			</label>
			<div class="col-md-6 text-left">
				<textarea class="form-control" name="info" rows="10" cols="">${dto.info }</textarea>
			</div>
		</div>
		
		<div class="form-group col-lg-10 text-center mtop30">
			<button data-dismiss="modal" class="btn btn-default" onclick="javascript:(function(){Route.params={active:2};location.href='index#print';})()" type="button">取消</button>
			<gd:btn btncode="BtnPrintSettingEdit"><button class="btn btn-danger ml15" id="saveForm" type="submit" data-dismiss="modal">确认</button></gd:btn>
		</div>
	</form>
</div>