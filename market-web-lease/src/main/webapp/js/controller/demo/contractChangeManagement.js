function _call(templateUrl,param){
	$("#main-wrapper").loadPage(templateUrl,
			["css/form-imput.css","css/contract.css"],
			["lib/bootstrap-datepicker.js","lib/bootstrap-datetimepicker.js","lib/jquery.tmpl.min.js"],
	
	function(){
		// 此处编写所有处理代码
		/*时间*/
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
			titleText:"审核合同", //
	        callback:function(){
	        	alert('点击保存')
	        }
		});
		$("#agreementAdd").modalPop({
			container:"sss",//
			titleText:"审核合同", //
	        callback:function(){
	        	alert('点击保存')
	        }
		});
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
							    +'<td><input class="rents" type="text"/></td>'
							    +'<td>10.00</td>'
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
      
      
	});
}