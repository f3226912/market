<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page isELIgnored="true"%>
<section class="">
	<div class="header">系统预警管理</div>
	<div class="col-md-12 col-pd-13">
		<div class="contract-info">
			<div class="contract-title-block">
			</div>
		    <div id="contractEndTime"></div>
		</div>
		<div class="contract-info">
			<div class="contract-title-block">
				<!-- <span class="contract-info-h3">&nbsp;&nbsp;&nbsp;&nbsp;收款到期&nbsp;&nbsp;&nbsp;&nbsp;</span>
				<ul style="float: right;">
	            <li><i class="fa fa-trash-o"></i><a id="contractEndTime_btn" href="javascript:(function(){Route.param={pageSize:20};location.href='index#financeEarlyWaring';})();">更多</a></li>
	            </ul> -->
			</div>
			<div id="financeEndTime"></div>
		</div>
		
	</div>
</section>

<script type="text/javascript">
	$(function(){
	    var a1='<ul style="float: right;margin:15px 29px 0 0;"><li><i class="fa fa-trash-o"></i><a style="color:#e86d4d;" id="contractEndTime_btn" href="javascript:(function(){Route.param={pageSize:20};location.href="index#contractEndRemind";})();">更多</a></li></ul>';
		var a2='<ul style="float: right;margin:15px 29px 0 0;"><li><i class="fa fa-trash-o"></i><a style="color:#e86d4d;" id="contractEndTime_btn" href="javascript:(function(){Route.param={pageSize:20};location.href="index#financeEarlyWaring";})();">更多</a></li></ul>';
		$("#a1").append(a1);
		$("#a2").append(a2); 
	})
</script>