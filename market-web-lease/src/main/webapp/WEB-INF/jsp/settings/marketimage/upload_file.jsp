<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<style>
	.uploadify-settimg{
		overflow: inherit;
	}
	.form-page .uploadify-queue{
		top: 30px;
	    left: -450px;
	    width: 500px;
	    position: absolute;
	}
</style>
<section class="header"></section>
<div class="form-page" style="padding: 10px">
<div class="contract-info clearfix ">
	<form id="form-addMarketImage" action="planeGraph/upload" method="post" class="form-horizontal clearfix">
		<div class="contract-title-block">
			<span class="contract-info-h3">市场平面图 </span>
		</div>
		<div class="form-group col-lg-12 load-setting">
			<label class="control-label col-md-3">
				上传市场平面图： 
			</label>
			<div class="col-md-8 text-left">
				<input type="text" class='file-text form-control' id="marketFileText" value="">
				<button type="button" class="btn btn-danger upload-btn">上传</button>
				<div class="uploadify-settimg">		
 				<input type="file" name="file" id="uploadify"/>
 				<input type="hidden"  id="marketImage" name="marketImage"/>
 				<input type="hidden" id="marketFile" name="marketFile"/>
 				</div>	
 			</div>
			
		</div>
		
		<div class="form-group col-lg-12 text-center" style="padding:40px 0 30px;">
			<button class="btn btn-default" onclick="javascript:window.location='index#graph'" type="button">取消</button>
			<button class="btn btn-danger ml15" id="saveForm" type="button">保存</button>
		</div>
	</form>
	</div>
</div>