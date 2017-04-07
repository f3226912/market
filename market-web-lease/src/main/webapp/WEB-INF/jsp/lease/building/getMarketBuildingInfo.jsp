<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="gd" uri="http://www.gdeng.cn/gd"%>
<%@ page isELIgnored="true"%>
<div class="system-modal">
    <section class="header"><span>楼栋管理</span>
        <div class="right-icon">
          <a href="javascript:(function(){if(Route.market){location.href='index#addMarketBuilding';}else{$.eAlert('提示信息','请选择市场');return;}})();">
          <i class="fa fa-plus-square"></i>新增楼栋</a>
          <a href="javascript:(function(){if(Route.market){location.href='javascript:javascript:void(0)';}else{$.eAlert('提示信息','请选择市场');return;}})();" id="batch_btn">
          <i class="fa fa-pencil-square-o"></i>批量生成商铺/停车场</a>
            <a href="javascript:javascript:void(0)" id="resourceAdjust"><i class="fa fa-exchange"></i>调整租赁资源</a>
            <a href="javascript:;" id="mapBtn"><i class="fa fa-picture-o"></i>平面图设置</a>
            <gd:btn btncode="BtnBuilDel"><a href="javascript:(function(){if(Route.market){location.href='javascript:void(0)';}else{$.eAlert('提示信息','请选择市场');return;}})();" id="delete">
          <i class="fa fa-trash-o"></i>删除</a></gd:btn>
        </div>
    </section>
    <!-- <div class="col-md-max">
      <div class="main-search">
           <div class="main-ipt-types">
              <form class="form-input clearfix">
            </form>
           </div>
       </div>
    </div> -->
    <div class="panel-body table-panel">
            <div class="adv-table editable-table ">
                <table class="table table-hover t-custom" id="tb">
                    <thead>
                    <tr class="ml-ct">
                        <th width="10%">序号</th>
	                    <th width="30%">楼栋编码</th>
	                    <th width="20%">楼栋名称</th>
	                    <th width="20%">市场区域</th>
	                    <th width="20%">楼栋性质</th>
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
    
	{{each(i, build) buildings}}
		<tr class="ml-ct">
			<td>${i+1}</td>
			<td>${build.code}</td>
			<td><a href="javascript:(function(){Route.params={id:${build.id}};location.href='index#marketBuildingdetail';})()" style="color: #664de8;">${build.name}</a></td>
			<td>${build.areaCode}</td>
            <td>${build.builNature}</td>
    	</tr>
	{{/each}}

</script>
<script id="popwintmpl11" type="text/x-jquery-tmpl">
	<div class="form-group">
		<label class="control-label col-md-3">审核时间:</label>
		<div class="col-md-6">
			<p class="form-control-static">2016-09-27 9:00</p>
		</div>
	</div>
</script>