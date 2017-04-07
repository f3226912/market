<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page isELIgnored="true"%>
<%@ include file="/WEB-INF/jsp/pub/tags.inc" %>
<div class="system-modal">
    <section class="header">
    	<span>区域管理</span>
        <div class="right-icon">
        	<gd:btn btncode="BtnResourcesAreaAdd">
    			<a href="javascript:(function(){if(Route.market){location.href='index#addMarketArea';}else{$.eAlert('提示信息','请选择市场');return;}})();"><i class="fa fa-plus-square"></i>新增</a>
			</gd:btn>
			<!-- 
			<gd:btn btncode="BtnResourcesAreaImg">
           	 <a href="index#viewImgMarketArea"><i class="fa fa-picture-o"></i>平面图设置</a>
			</gd:btn>
			-->
            <a href="index#viewImgMarketArea"><i class="fa fa-picture-o"></i>平面图设置</a>
            <gd:btn btncode="BtnResourcesAreaDelete">
           	 	<a href="javascript:void(0)" id="delete_btn"><i class="fa fa-trash-o"></i>删除</a>
			</gd:btn>
        </div>
    </section>
    <!-- <div class="col-md-max">
      <div class="main-search">
           <div class="main-ipt-types">
              <form class="form-input clearfix">
                <div class="form-group">
                </div>
                <div class="form-group">
                </div>
            </form>
           </div>
       </div>
    </div> -->
    <div class="panel-body table-panel">
        <div class="adv-table editable-table ">
            <table class="table table-hover t-custom" id="tb">
                <thead>
                <tr class="ml-ct">
	<!-- <th></th> --> 
                     <th width="40%">区域编码</th>
                     <th width="50%">区域名称</th>
                     <th width="10%">优先级序号</th>
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
    
	{{each(i, marketArea) marketAreas}}
		<tr class="ml-ct">
			<td>${marketArea.code}</td>
			<td>
				<a class="check-auth" href="javascript:(function(){Route.params={marketAreaId:${marketArea.id}};location.href='index#viewMarketArea';})();">${marketArea.name}</a>&nbsp;
			</td>
			<td>${marketArea.sort}</td>
    	</tr>
	{{/each}}

</script>