<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page isELIgnored="true"%>
<section class="header">参数设置</section>
    <div class="market uploadPlane form-page" style="margin-top: 40px;">
    <div class="contract-info clearfix">
    <div class="contract-title-block">
        <span id="marketName" class="contract-info-h3"></span>
        <div class="right-icon">
            <a id="set"><i class="fa fa-edit"></i>绘制区域</a>
            <a href="index#marketImageAdd"><i class="fa fa-upload"></i>上传平面图</a>
        </div>
    </div>
                <!-- 资源按钮组 -->
                <ul id="marketBtns" class="resoure-plane text-center">
                    <li data-id="" class="currChose">全部</li>
                </ul>
                <div class="table-tip">
                    <!-- 总资源数量 -->
                    <p class="table-tip-text text-center"></p>
                </div>
                <!-- 描点地图 -->
                <div id="dragBox" class="drag-wrap">
                    <img src="" class="map-src" alt="图片加载中……" />
                    <ul id="planeList" class="plane-list">
                    </ul>
                </div>
                <div class="form-group col-lg-12 text-center mtop30">
                    <button id="cancel" data-dismiss="modal" class="btn btn-default" type="button">取消</button>
                    <button class="btn btn-danger ml15" id="save" type="button" data-dismiss="modal">保存</button>
                </div>
            </section>
            </div>
        </div>
    </div>

    <!-- 描点数据列表弹出框 -->
    <div class="modal-pop">
            <div class="adv-table editable-table ">
                <div class="space15"></div>
                <table class="table table-hover table-bordered" id="tb">
                    <thead>
                        <tr class="ml-ct">
                            <th><input type="checkbox" id="checkboxAll"/></th>
                            <th>区域名称</th>
                            <th>绘制状态</th>
                        </tr>
                    </thead>
                    <tbody id="templateBody">
                    </tbody>
                </table>
            </div>
    </div>
</section>

<script id="noXTemp" type="text/x-jquery-tmpl">
    <tr class="ml-ct">
        <td style="text-align: center !important;"><input id="check_${id}" draw="false" class="Echeckbox" name="areaCheckbox" type="checkbox" value="${id}"/></td>
        <td>${name}</td>
        <td>未绘制</td>
    </tr>
</script>

<script id="hasXTemp" type="text/x-jquery-tmpl">
    <tr class="ml-ct">
        <td style="text-align: center !important;"><input id="check_${id}" draw="true" init="drawed" class="Echeckbox" name="areaCheckbox" type="checkbox" checked="checked" value="${id}"/></td>
        <td>${name}</td>
        <td>已绘制</td>
    </tr>
</script>

<script id="drawTemp" type="text/x-jquery-tmpl">
    <li class="curr-list drawed" id="draw_${id}" name="${name}" style="left:${x}px;top: ${y}px;">
        <div class="plane-tips">
            <p class="tips-name">${name}</p>
        </div>
    </li>
</script>
<script id="tipTemp" type="text/x-jquery-tmpl">
    <p class="tips-name">${name}</p>
    <p class="all-num">总数量：${total}</p> 
    <p class="yizu-tip plane-tips-list">已租：${alreadyRentedCnt}</p> 
    <p class="daizu-tip plane-tips-list">待租：${forRentCnt}</p>
    <p class="daichuzu-tip plane-tips-list">待放租：${ineffectiveCnt}</p>
</script>