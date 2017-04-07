function _call(templateUrl,param){
	$("#main-wrapper").loadPage(templateUrl,
			["lib/css/DT_bootstrap.css","css/parameter.css","css/contract.css"],
			["lib/jquery-migrate.js","js/common/pageBar.js","lib/jquery.tmpl.min.js"],
  function(){
		Route.param={pageSize:5};
		
		$.loadRouteModal("contractEndRemind","#contractEndTime", function(){
			$("#econtract").css("padding","0px");
		    $("#a2").html("<span class='contract-info-h3'>&nbsp;&nbsp;&nbsp;&nbsp;合同提醒&nbsp;&nbsp;&nbsp;&nbsp;</span>"+
     	 	"<ul id='eUl' style='float: right;margin:15px 29px 0 0;'>"+
            "<li><a style='color:#e86d4d;' id='contractEndTime_btn' href='javascript:(function (){Route.param={pageSize:20};location.href=\"index#contractEndRemind\";})();'>更多</a></li>  </ul>");
			$("#pagebar").hide();
		});
		$.loadRouteModal("financeEarlyWaring", "#financeEndTime", function(){
			$("#fcontract").css("padding","0px");
		    $("#fa2").html("<span class='contract-info-h3'>&nbsp;&nbsp;&nbsp;&nbsp;收款提醒&nbsp;&nbsp;&nbsp;&nbsp;</span>"+
     	 	"<ul id='eUl' style='float: right;margin:15px 29px 0 0;'>"+
            "<li><a style='color:#e86d4d;' id='contractEndTime_btn' href='javascript:(function (){Route.param={pageSize:20};location.href=\"index#financeEarlyWaring\";})();'>更多</a></li>  </ul>");
		    $("#pagebar2").hide();
		});
	});
}
