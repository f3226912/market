/**
 * 
 */
function _call(templateUrl,param){
	var params=Route.params;
	var id=params.id;
	$("#main-wrapper").loadPage(templateUrl + "/" +id,
			["css/form-imput.css","css/contract.css"],
			["lib/bootstrap-datepicker.js","lib/bootstrap-datetimepicker.js","lib/jquery.tmpl.min.js"],
	function(){
		$('.form_datetime-component').datetimepicker({
			format: "yyyy mm dd - hh:ii",
			autoclose: true
			});
			$(".start-date").datepicker({
				format: "yyyy-mm-dd",
				autoclose: true
			});
			$(".approval-s").modalPop({
				container:"popwintmpl",//
				titleText:"发起审批", //
		        callback:function(){
		        	alert('点击保存')
		        }
			});
			$("#agreementAdd").on("click",function(){
				$("#agreementAddPop").modal();
			})
			$("#agreementAddPop").popwin({
				titleText: "选择租赁资源", //弹出框名
				popwidth:"900",//设置宽度
				btnText:["取消","确认"],//按钮的文本
				drag:true,//是否拖动
				//formValid: "", //表单验证方法
				popUrl: null,
				callback: function(){
					$("#agreementAddPop").modal('hide');
					var efristSelect=$("#efristSelect").find("option:selected").text();
					var mainHtml='';
					$(".Einput-m").each(function(){
						if($(this).is(':checked')){
							var costType=$(this).parent().parent().parent().find(".ecm-tital").text();
							var Ecostselect=$(this).parent().parent().find(".guarantee-select").find("option:selected").text();
							var EcostName=$(this).parent().find(".checkbox-em").text();
							mainHtml2='<tr class="charges-tab-blank">'
							+'<td class="num"></td>'
							+'<td>'+efristSelect+'</td>'
							+'<td>'+costType+'</td>'
							+'<td>'+EcostName+'</td>'
							+'<td>'+Ecostselect+'</td>'
							+'<td>指定金额</td>'
							+'<td>1,244,550.00</td>'
							+'</tr>';
							mainHtml=mainHtml+mainHtml2;
							//console.log(costType);
						}
						
					})
					
					$("#econManageDT").append(mainHtml);
					contentLi();
				}
			});
			$(".list-sp-text").change(function() {
				 $(this).css("background","#FFFFCC");
			})
			function contentLi(){
				var num=$(".num"),
					tNum=1;
				num.each(function(){
					$(this).html(tNum);
					tNum++;
				})
			}
			
		   //租赁约定
		   //var data = []
		   function addRos() {
		   	  var rowS =  '<tr id="" class="charges-tab-blank">'
								    +'<td id="eq-add">1</td>'
								    +'<td>'
								    +'	<div class="date-icon border-none-right border-lt-right">'
									+'   <input type="text" class="form-control start-date" placeholder="起始日期">'		
									+'</div>'	
								    +'</td>'
								    +'<td>'
								    +'	<div class="date-icon border-none-right border-lt-right">'
									+'   <input type="text" class="form-control start-date" placeholder="截止日期">'	
									+'	</div>'
								    +'</td>'
								    +'<td>'
									+'<select class="form-control wids">'	
									+'	   <option selected="">合同状态</option>'
									+'   <option>合同状态</option>'	
									+'	   <option>合同状态</option>'
									+'	</select>'
								    +'</td>'
								    +'<td>10.00</td>'
								    +'<td><input class="rents" type="text"/></td>'
								    +'<td>1,244</td>'
								    +'</tr>';
				
				$('.charges-tab-style .tb-trs').append(rowS);
				$(".start-date").datepicker({
					format: "yyyy-mm-dd",
					autoclose: true
				});			  
		   }
		   
		  $('#addTr').click(function() {
		     addRos()
		     addChargesColor();
		  })
		  
		  function addChargesColor(){
		  	$('.charges-tab-blank').on('click',function() {
			  	$(this).addClass('addcss').siblings().removeClass('addcss');
			  	//$(this).remove();	
			  })
		  	$('#removeTr').on('click',function() {
			    $('.addcss').remove();
			  })
		  	}
	      //免租约定
	      function leaseDad() {
	      	var lesRos = '<tr class="charges-tab-blank">'
								    +'<td>1</td>'
								    +'<td>'
								    +'<div class="date-icon border-none-right">'  
									+'<input type="text" class="form-control start-date start-cet" placeholder="起始日期">'	
									+'  </div>'
								    +'</td>'
								    +'<td><input class="rents" type="text"/></td>'
								    +'<td><input class="rents" type="text"/></td>'
								    +'<td>2016-10-15</td>'
								    +'</tr>';
				$('.charges-tab-style .leaseDs').append(lesRos);
				$(".start-date").datepicker({
					format: "yyyy-mm-dd",
					autoclose: true
				});	
								    
	      };
	      
	      $('#leaseDad').click(function() {
		     leaseDad();
		     addLeaseColors()
		  })
		  
		  function addLeaseColors(){
		  	$('.charges-tab-blank').on('click',function() {
			  	$(this).addClass('addcss').siblings().removeClass('addcss');
			  	//$(this).remove();	
			  })
		  	$('#leaseRed').on('click',function() {
			    $('.addcss').remove();
			  })
		  	}
	      
	      
	    //选择其他费项
			$("#agreementAdd").on("click",function(){
				$("#agreementAddPop").modal();
			})
			$("#agreementAddPop").popwin({
				titleText: "选择其他费项", //弹出框名
				popwidth:"900",//设置宽度
				btnText:["取消","确认"],//按钮的文本
				drag:true,//是否拖动
				//formValid: "", //表单验证方法
				popUrl: null,
				callback: function(){
					$("#agreementAddPop").modal('hide');
					var efristSelect=$("#resource_text").text();
					var mainHtml='';
					$(".Einput-m").each(function(){
						if($(this).is(':checked')){
							var costType=$(this).parent().parent().parent().find(".ecm-tital").text();
							var Ecostselect=$(this).parent().parent().find(".guarantee-select").find("option:selected").text();
							var EcostName=$(this).parent().find(".checkbox-em").text();
							mainHtml2='<tr class="charges-tab-blank">'
					        +'<td class="num"></td>'
					        +'<td>'+efristSelect+'</td>'
					        +'<td>'+costType+'</td>'
					        +'<td>'+EcostName+'</td>'
					        +'<td>'+Ecostselect+'</td>'
					        +'<td>指定金额</td>'
					        +'<td>1,244,550.00</td>'
					        +'</tr>';
							mainHtml=mainHtml+mainHtml2;
						}
					})
					
					$("#econManageDT").append(mainHtml);
					conManageColor();
					contentLi();
				}
			});
			
			function contentLi(){
				var num=$(".num"),
					tNum=1;
				num.each(function(){
					$(this).html(tNum);
					tNum++;
				})
			}
			
			function conManageDelete(){
				$("#agreementRem").click(function(){
					$(".color-eee").remove();
					contentLi();
				});
			}conManageDelete(); 
			
			function conManageColor(){
				$("#econManageDT .charges-tab-blank").click(function(){
					$("#econManageDT .charges-tab-blank").removeClass("color-eee");
					$(this).addClass("color-eee");
					//contentLi()
				})
			}
			conManageColor();
	})
}