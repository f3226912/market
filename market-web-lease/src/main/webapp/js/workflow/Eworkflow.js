function changeColor(){
	$(".header div").click(function(){
		var val=$(this).attr("value");
		$(".header div").removeClass("BGwhith");
		$(this).addClass("BGwhith");
		$(".Ebody").hide();
		if(val=="0"){
			$("#body1").show();
		}else{
			$("#body2").show();
		}
	});
}changeColor();

function eAlert(title,con){
	$(".Ewrap .s-box .bottom").unbind();
	$(".markAll").show();
	$(".Ewrap .s-box .header").text(title);
	$(".Ewrap .s-box .body").text(con);
	$(".Ewrap .s-box .bottom").click(function(){
		$(".markAll").hide();
	})
}
function eAlertClose(title,con){
	$(".Ewrap .s-box .bottom").unbind();
	$(".markAll").show();
	$(".Ewrap .s-box .header").text(title);
	$(".Ewrap .s-box .body").text(con);
	$(".Ewrap .s-box .bottom").click(function(){
		$(".markAll").hide();
		window.close();
	})
}
function eConfirm(title,con,fun){
	$(".Ewrap .s-box .bottom").unbind();
	$(".markAll2").show();
	$(".Ewrap .s-box .header").text(title);
	$(".Ewrap .s-box .body").text(con);
	$(".Ewrap .s-box #cancel").click(function(){
		$(".markAll2").hide();
	})
	$(".Ewrap .s-box #confirm").click(function(){
		fun();
	})
}
