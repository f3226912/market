<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/pub/constants.inc" %>
<%@ include file="/WEB-INF/jsp/pub/tags.inc" %>
<base href="${CONTEXT}" >
<%@ include file="/WEB-INF/jsp/pub/head.inc" %>
<style>
      /*重构样式*/
    .table.table ,.ml-ct th ,.form-inline .form-control { text-align: center !important;}
    .adv-table .dataTables_filter label input { width: 90%!important;}
</style>

<!--Step:1 Prepare a dom for ECharts which (must) has size (width & hight)-->
<!--Step:1 为ECharts准备一个具备大小（宽高）的Dom-->
<div id="main" style="height:500px;border:1px solid #ccc;padding:10px;"></div>
<div id="mains" style="height:500px;border:1px solid #ccc;padding:10px;"></div>

