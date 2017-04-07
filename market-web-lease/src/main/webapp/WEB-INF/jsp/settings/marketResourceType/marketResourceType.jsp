<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page isELIgnored="true"%>
<%@ taglib prefix="gd" uri="http://www.gdeng.cn/gd"%>
<div class="system-modal">
    <section class="header"><span>市场资源类型管理</span>
        <div class="right-icon">
        	<gd:btn btncode="BtnMarketResourceTypeToAdd">
            	<a href="javascript:(function(){if(!Route.market){$.eAlert('提示','当前公司下未关联市场，暂不能执行新增操作！');return;}location.href='index#marketResourceTypeToAddView';})();"><i class="fa fa-plus"></i>新增</a>
            </gd:btn>
            <gd:btn btncode="BtnMarketResourceTypeDelete">
            	<a id="delete_btn" href="javascript:void(0)" onclick="deleteResType();"><i class="fa fa-times"></i>删除</a>
            </gd:btn>
        </div>
    </section>
    <div class="panel-body">
            <div class="adv-table editable-table ">
                <div class="space15"></div>
                <table class="table table-striped table-hover t-custom" id="tb">
                    <thead>
                    <tr class="ml-ct">
                    	<th><input type="checkbox" id="checkboxAll"/></th>
                        <th>序号</th>
                        <th>资源类型编号</th>
                        <th>资源类型名称</th>
                        <th>顺序号</th>
                        <th>是否系统级</th>
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
    
	{{each(i, type) typeList}}
		<tr class="ml-ct">
			<td style="text-align:center !important;"><input class="Echeckbox" type="checkbox" value="${type.id}" name='subBox'/></td>
			<td>${i + 1}</td>
			<td>${type.code}</td>
			<td class="lsCurrent">
				<a href="javascript:(function(){if(${type.sysType} == 1) {$.eAlert('提示', '系统资源类型不允许被修改!'); return;}Route.params={marketResourceTypeId:${type.id}};location.href='index#marketResourceTypeToEditView';})();"  style="color: #664de8;">
					${type.name}
				</a>
			</td>
			<td>${type.sort}</td>
			<td name="sysType" value="${type.sysType}">${type.sysTypeStr}</td>
    	</tr>
	{{/each}}

</script>