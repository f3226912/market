<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/pub/constants.inc" %>
<%@ include file="/WEB-INF/jsp/pub/head.inc" %>
<link rel="stylesheet" href="${CONTEXT}/lib/snaker/css/popWin.css" type="text/css" media="all" />
 <!--流程监控-重置责任人弹窗内容开始-->
<div id="selectPostContent" class="respons-i">
    <div class="mask_layer"></div>

    <div class="contPerson clearfloat">
       <h3 class="set_usre">人员选择</h3>
       <div class="cot_rit">
        <div class="resetPerson_bs">
            <h3 class="treetitA">组织架构</h3>
            <ul id="treeDemo" class="ztree"></ul>
        </div>
        <div class="resetPerson_rt">
            <h3 class="treetitB">人员列表</h3>
          <ul id="treeDemos" class="ztree_list">

          </ul>
        </div>
        <div class="user_selected clearfloat">
           
        </div>
        <div class="btn_bt">
            <button id="selectPersonWinCancelBtn" class="btn_cancel">取消</button>
            <button id="selectPersonWinSavelBtn" class="btn_determine">保存</button>
        </div>
       </div>
    </div>
</div>  
<!--流程监控-重置责任人弹窗内容结束-->

<script src="${CONTEXT}/lib/snaker/js/jquery-1.8.3.js" type="text/javascript"></script>
<script src="${CONTEXT}/lib/jquery.ztree.core.min.js" type="text/javascript"></script>
<script src="${CONTEXT}/lib/jquery.ztree.exedit.min.js" type="text/javascript"></script>
<script src="${CONTEXT}/lib/snaker/js/selectPersonWin.js" type="text/javascript"></script>


