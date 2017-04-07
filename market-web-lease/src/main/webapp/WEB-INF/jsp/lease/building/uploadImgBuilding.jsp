<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page isELIgnored="true"%>
<section class="header">上传平面图</section>
<div class="uploadPlane form-page" style="margin-top: 40px;">
<div class="contract-info clearfix">
<form id="form-addFloorImage" action="planeGraph/uploadFloor" method="post" class="form-horizontal clearfix">
	<div class="contract-title-block">
		<span class="contract-info-h3">上传楼层平面图</span>
		<div class="right-icon">
			<a><i class="fa fa-upload"></i>上传</a>
			<div class="col-md-6 text-left" style="position: absolute;opacity: 0;right: 60px;top:0;">		
		 				<input type="file" name="file" id="uploadify1"/>
		 				<input type="hidden"  id="floorImage" name="floorImage"/>
		 				<!-- 
		 				http://gudeng-test.oss-cn-shenzhen.aliyuncs.com/2016/10/24/3882561b80704deda16ac129422e0e68.jpg 
		 				-->
		 				<input type="hidden" id="floorFile" name="floorFile"/>
		 				<input type="hidden" id="floorId" name="floorId"/>	
 			</div>
		</div>
	</div>
	<div class="uploadplane-main clearfix">
		<div class="upload-flow" >
			<!-- 区域列表 -->
			<ul id="areaWrap"></ul>			
		</div>
		<div class="plane-cont">
			<img id="imgId" src="images/imageload.png">
		</div>
		
	</div>
	<div class="form-group col-lg-12 text-center mtop30">
			<button data-dismiss="modal" class="btn btn-default" onclick="javascript:window.location='index#marketBuilding'" type="button">取消</button>
			<button class="btn btn-danger ml15" id="saveForm" type="submit">确认</button>
		</div>
		</form>
	</div>
</div>
<script id="areaTemp" type="text/x-jquery-tmpl">
   <li id="${id}">${name}</li>
</script>
