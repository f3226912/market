function _call(templateUrl,param){
	$("#main-wrapper").loadPage(templateUrl,
			["css/system.css","lib/css/datepicker.css","css/resourMage.css",],
			["lib/chart/echarts.min.js","lib/jquery-migrate.js","js/common/pageBar.js","lib/jquery.tmpl.min.js","lib/bootstrap-datepicker.js"],
	function(){
			//时间		
		function CurentDate()  
		{   
		    var now = new Date();  
		         
		    var year = now.getFullYear();       //年  
		    var month = now.getMonth();     //月    
		    var clock = year + "-";      
		    if(month < 10) clock += "0";         
		    clock += month;  
		    return(clock);   
		}  
		
		function StartDate()  
		{   
		    var now = new Date();        
		    now.setMonth(now.getMonth()-11);
		    var year = now.getFullYear();       //年  
		    var month = now.getMonth();     //月    
		    var clock = year + "-";      
		    if(month < 10) clock += "0";         
		    clock += month;  
		    return(clock);    
		}  
		
		$("input[name='startDate']").attr("value",StartDate()); 
		$("input[name='endDate']").attr("value",CurentDate());
		
		//初始化时间控件
		$(".date").datepicker({
			  autoclose: true,
			  format: "yyyy-mm",
			  minViewMode: 1,
			  todayBtn: false
		});
		var startDate=0,endDate=0;
		$(".startDate").datepicker({
			  autoclose: true,
			  format: "yyyy-mm",
			  minViewMode: 1,
			  todayBtn: false
		}).on("changeDate",function(ev){
			var newData = new Date().getTime();
			//console.log("aaa"+newData)
			
			 startDate = ev.date.valueOf();
			 endDate =  (new Date($(this).parents(".date-icon").next(".date-icon").find(".endDate ").val())).getTime();
			// console.log("aaa"+startDate)
			if(endDate != 0 && endDate < startDate){
				$(this).focus();
			 	$(this).val("");
			 	$.eAlert("提示","开始时间不能大于结束时间，请重新选择")
		        }else if(startDate>newData){
		        	$.eAlert("提示","开始时间不能大于今天，请重新选择");
		        	$(this).focus();
				 	$(this).val("");
		        }
		})
		$(".endDate").datepicker({
			  autoclose: true,
			  format: "yyyy-mm",
			  minViewMode: 1,
			  todayBtn: false
		}).on("changeDate",function(ev){
			startDate = (new Date($(this).parents(".date-icon").prev(".date-icon").find(".startDate ").val())).getTime();
			endDate = ev.date.valueOf();
			//console.log("startDate :"+startDate)
			//console.log("endDate :"+endDate)
			if(startDate !=0 && endDate < startDate){		           
		            $(this).focus();
		            $(this).val("");
		            $.eAlert("提示","结束时间不能小于开始时间，请重新选择")
		        }
		});
		  //初始化下拉列表数据
        //查询全部类型
  		function findAllOtherResType() {
  			$.ajax({
  				type : "post",
  				url : CONTEXT+"marketResourceType/findAllByCondition",
  				data : {"status" : 1},
  				success : function (data) {
  					if(data.success){
  						var resTypeList = data.result;
  						
  						var optStr;
  						var buildItem='<option value="">全部类型</option>';
  						if(resTypeList==null){
  							$("[name='resourceType']").html(buildItem);
  							return false;
  						}
  						for (var i=0; i<resTypeList.length; i++) {
  							var resType = resTypeList[i];
  							buildItem+='<option value=' + resType.id + '>' + resType.name + '</option>';
  						}
  						$("[name='resourceType']").html(buildItem);
  					}
  				}
  			});
  		}        

  		findAllOtherResType();
  		
  		//初始化费项列表
  		function initExpIdEvent(){
  			var datas;
  			$.getJSON(CONTEXT+"report/getExpIdList",null,
  				function(data){
  				datas=data.result;
  				//console.debug(data);
  				var expId='<option value="">全部</option>';
  				if(datas){
  					$.each(datas,function(x,y){
  						// console.debug(y.id);
  						expId+='<option value="'+y.id+'">'+y.name+'</option>';
  					});
  				}
  				$("[name='expId']").html(expId);
  			});	
  		}
  		initExpIdEvent();
  		
  		function initAreaBuildEvent(){
  			var datas;
  			$.getJSON(CONTEXT+"marketResource/area_build",null,function(data){
  				datas=data.result;
  				//console.debug(data);
  				var areas='<option value="">全部区域</option>';
  				if(datas){
  					$.each(datas,function(x,y){
  						// console.debug(y.id);
  						areas+='<option value="'+y.id+'">'+y.name+'</option>';
  					});
  				}
  				$("[name='areaId']").html(areas);
  			});
  			
  			
  		}
  		initAreaBuildEvent();
		//$.eLoading(true);
	  //加载资源类型列表-----------------------------------------------------------------------
	    function loadMarketResourceType(){
			  $.ajax({  
			        type: "GET",  
			        url: CONTEXT+"report/getMarketResourceType",  
			        data: null,  
			        dataType:"json",  
			        async:false,  
			        cache:false,  
			        success: function(data){  		
			        	
			        	if(data.result==null){
			        		
			        		$("#divResourceList").hide();
			        		$("#dragBox").hide();
			        		return false;
			        	}
							$("#divResourceList").html("");
							$('#templateResourceList').tmpl({resourceTypeList:data.result}).appendTo('#divResourceList');
			        },  
			        error: function(json){  
			        	$.eAlert(data.msg);
			        }  
			    });  
		}
	    loadMarketResourceType();
		
	  //加载关键指标数据-----------------------------------------------------------------------
		function loadDataKeyIndex(resourceTypeId){
			  $.ajax({  
			        type: "GET",  
			        url: CONTEXT+"report/getReportKeyIndex", //orderModifyStatus  
			        data: {"resourceTypeId":resourceTypeId},  
			        dataType:"json",  
			        async:false,  
			        cache:false,  
			        success: function(data){  
							$("#indexKey").html("");
							if(data==null || data.result.length==0){
								return false;
							}
							$('#template').tmpl({report:data.result}).appendTo('#indexKey');
			        },  
			        error: function(json){  
			        	$.eAlert(data.msg);
			        }  
			    });  
		}
		  loadDataKeyIndex(null);
		 //获取市场租赁情况总览信息
		var mapData, resourceTypeId, areaData = {};
	  	$.ajax({
			url: CONTEXT+"planeGraph/listMarketImage",
			data: '{"marketId": "' + Route.market + '"}',
			type: "POST",
			dataType: "json",
			contentType: "application/json",
			success: function(data){
				if(data.statusCode == "0"){
			    	var imgUrl = data.imageUrl;
			    	if(imgUrl != "" && imgUrl != null){
						$(".map-src").attr("src", imgUrl);
			    	}else{
			    		$(".map-src").attr("src", "images/map.jpg");
			    	}
					mapData = data;
					$('#drawTemp').tmpl(mapData.areas).appendTo('#planeList');
					// 切换资源更新描点数据
					$("#divResourceList").on("click","li",function(){
						// $('#planeList').empty();
						$(this).addClass("currChose").siblings().removeClass("currChose");
						resourceTypeId = $(this).attr('data');
						if(!areaData[resourceTypeId]){
							areaData[resourceTypeId] = {};
						}
						//关键表
						loadDataKeyIndex(resourceTypeId);
						
					});
					$('#divResourceList ul li').eq(0).click();	
					$("#planeList").on('mouseover', 'li', function(event){
						$(this).find('.plane-tips').empty();
						var id = this.id.split('_')[1],
							name = $(this).attr('name');
						if(areaData[resourceTypeId] && areaData[resourceTypeId][id]){
							$(this).find('.plane-tips').html($('#tipTemp').tmpl(areaData[resourceTypeId][id]));
									// console.log("----------------")
									// console.log(areaData)
							return;
						}
						$.ajax({
							url: CONTEXT+"planeGraph/marketResStatistics",
							data: JSON.stringify({
									"marketId": Route.market,
									"areaIds": [id],
									"typeId": resourceTypeId
								}),
							type: "POST",
							dataType: "json",
							contentType: "application/json",
							success: function(data){
								if(data.statusCode == "0"){
									data.data[0].name = name;
									areaData[resourceTypeId][id] = data.data;
									$(event.target).find('.plane-tips').html($('#tipTemp').tmpl(data.data));
								}
							}
						});
					});
					// 点击描点进入对应更高级平面图
					$("#planeList").on('click', 'li', function(event){
						Route.params = {
							areaId : this.id.split('_')[1]
						}
						location.href = "index#viewImgMarketArea";
					});
				} else {
					$.eAlert("错误提示", data.msg);
				}
			}
		});
		// 绘制区域描点
		// function loadResoureName(resourceCode){	
		// 	var d = mapData.object[resourceCode];
		// 	$('#drawTemp').tmpl({datas:d}).appendTo('#planeList');	
		// }
		  //loadResoureName()
	        	
	  		  //加载费项收缴情况----------------------------------------------------------------------
	  		  
	        	
	        	
	  		 
	        	function report1(reportExpfeeInfoDate,reportExpfeeInfoAccountPayable,reportExpfeeInfoAccountPayed,reportExpfeeInfoAccountDidPay,reportExpfeeInfoCaptureRate){
	            //--- 折柱 ---
	            var myChart = echarts.init(document.getElementById('report1'));
	            myChart.setOption({
	                tooltip : {
	                    trigger: 'axis',
	                },
	                legend: {
	                    data:['应收款','已收款','未收款','收缴率']
	                },
	                toolbox: {
	                    show : true,
	                    feature : {
	                        mark : {show: true},
	                        dataView : {show: true, readOnly: false},
	                        magicType : {show: true, type: ['line', 'bar']},
	                        restore : {show: true},
	                        saveAsImage : {show: true}
	                    }
	                },
	                calculable : true,
	                xAxis : [
	                    {
	                        type : 'category',
	                        data :eval(JSON.stringify(reportExpfeeInfoDate))
	                    }
	                ],
	                yAxis : [
	                    {
	                        type : 'value',
	                        splitLine: {show: false }
	                    },{
	                        type : 'value',
	                        splitLine: {show: false }
	                    },
	                ],
	                series : [
	                    {
	                        name:'应收款',
	                        type:'bar',
	                        data:eval(JSON.stringify(reportExpfeeInfoAccountPayable))
	                    },
	                    {
	                        name:'已收款',
	                        type:'bar',
	                        data:eval(JSON.stringify(reportExpfeeInfoAccountPayed))
	                    }
	                    ,
	                    {
	                        name:'未收款',
	                        type:'bar',
	                        data:eval(JSON.stringify(reportExpfeeInfoAccountDidPay))
	                    }
	                    ,
	                    {
	                        name:'收缴率',
	                        type:'line',
	                        yAxisIndex: 1,
	                        data:eval(JSON.stringify(reportExpfeeInfoCaptureRate))
	                    }
	                ]
	            });
	        	}
	            function loadReport1(){
		  			  $.ajax({  
		  			        type: "POST",  
		  			        url: CONTEXT+"report/getReportExpfeeInfo",  
		  			        data: $("#form_report1").serialize(),  
		  			        success: function(data){  
		  			          //费项收缴情况-时间
		  			  		  var reportExpfeeInfoDate=new Array();
		  			  		  //费项收缴情况-应收款
		  			  		  var reportExpfeeInfoAccountPayable=new Array();
		  			  		  //费项收缴情况-已收款
		  			  		  var reportExpfeeInfoAccountPayed=new Array();
		  			  		  //费项收缴情况-未收款
		  			  		  var reportExpfeeInfoAccountDidPay=new Array();
		  			  		  //费项收缴情况-收缴率
		  			  		  var reportExpfeeInfoCaptureRate=new Array();
		  			        	$.each(data.result,function(idx,item){ 
		  			        		//alert("idx"+idx+"name:"+item.accountPayable+",accountPayed:"+item.accountPayed); 
		  			        		reportExpfeeInfoDate[idx]=item.dataDate;
		  			        		reportExpfeeInfoAccountPayable[idx]=item.accountPayable;
		  			        		reportExpfeeInfoAccountPayed[idx]=item.accountPayed;
		  			        		reportExpfeeInfoAccountDidPay[idx]=item.accountDidPay;
		  			        		reportExpfeeInfoCaptureRate[idx]=item.captureRate;
		  			        		}); 
		  			        	report1(reportExpfeeInfoDate,reportExpfeeInfoAccountPayable,reportExpfeeInfoAccountPayed,reportExpfeeInfoAccountDidPay,reportExpfeeInfoCaptureRate);
		  			        },  
		  			        error: function(json){  
		  			        	$.eAlert(data.msg);
		  			        }  
		  			    });  
		  		}
	            
	            //初始化报表
	            loadReport1();
	  
	           //未收款情况-------------------------------------------------------------
	            
	            
	            function report2(array){
	            // --- 饼状图 ---
	            var myChart = echarts.init(document.getElementById('report2'));
	            myChart.setOption({
	            	  tooltip: {
	            	        trigger: 'item',
	            	        formatter: "{a} <br/>{b}: {c} ({d}%)"
	            	    },
	            	    series: [
	            	        {
	            	            name:'未收款情况',
	            	            type:'pie',
	            	            radius: ['50%', '70%'],
	            	            avoidLabelOverlap: false,
	            	            label: {
	            	                normal: {
	            	                    show: false,
	            	                    position: 'center'
	            	                },
	            	                emphasis: {
	            	                    show: true,
	            	                    textStyle: {
	            	                        fontSize: '30',
	            	                        fontWeight: 'bold'
	            	                    }
	            	                }
	            	            },
	            	            labelLine: {
	            	                normal: {
	            	                    show: true
	            	                }
	            	            },
	            	            data:eval(JSON.stringify(array))
	            	        }
	            	    ]
	                    });
	            }
	            function loadReport2(){
		  			  $.ajax({  
		  			        type: "POST",  
		  			        url: CONTEXT+"report/getReportDidfeeInfo",  
		  			        data: $("#form_report2").serialize(),   
		  			        success: function(data){  
		  			        	var array=[];
		  			        	 function ObjStory(value,name){
		  			            	this.value=value;
		  			            	this.name=name;
		  			            }
		  			        	$.each(data.result,function(idx,item){ 
		  			        		array.push(new ObjStory(item.accountDidPay,item.areaName));
		  			        		}); 
		  			        	report2(array);
		  			        },  
		  			        error: function(json){  
		  			        	$.eAlert(data.msg);
		  			        }  
		  			    });  
		  		}
	            //初始化report2中饼图
	            loadReport2()
	            //加载report2中表格
	            
	            function loadreport2Table(){
	  			  $.ajax({  
	  			        type: "POST",  
	  			        url: CONTEXT+"report/getReportDidfeeTableInfo",  
	  			        data: $("#form_report2").serialize(),  
	  			        success: function(data){  
	  							$("#divReport2Table").html("");
	  							var color =['#c23531','#2f4554', '#61a0a8', '#d48265', '#91c7ae','#749f83',  '#ca8622', '#bda29a','#6e7074', '#546570', '#c4ccd3'];
	  							data.color=color;
	  							if(data.result.length==null||data.result.length==0){
	  								return false;
	  							}
	  							$('#templateReport2Table').tmpl({report2Table:data}).appendTo('#divReport2Table');
	  							
	  			        },  
	  			        error: function(json){  
	  			        	$.eAlert(data.msg);
	  			        }  
	  			    });  
	  		}
	          //初始化report2中表格
	            loadreport2Table();
	         
	            
	            //优惠情况分析-----------------------------------------------------------------
	       	 	
	            
	            function report3(report3Date,report3AccountPayed,report3DiscountAmount,report3CaptureRate){
		            //--- 折柱 ---
		            var myChart = echarts.init(document.getElementById('report3'));
		            myChart.setOption({
		               
		                tooltip : {
		                    trigger: 'axis',
		                },
		                legend: {
		                    data:['已收款','优惠金额','优惠比例']
		                },
		                toolbox: {
		                    show : true,
		                    feature : {
		                        mark : {show: true},
		                        dataView : {show: true, readOnly: false},
		                        magicType : {show: true, type: ['line', 'bar']},
		                        restore : {show: true},
		                        saveAsImage : {show: true}
		                    }
		                },
		                calculable : true,
		                xAxis : [
		                    {
		                        type : 'category',
		                        data :eval(JSON.stringify(report3Date))
		                    }
		                ],
		                yAxis : [
		                    {
		                        type : 'value',
		                        splitLine: {show: false }
		                    },
		                    {
		                        type : 'value',
		                        splitLine: {show: false }
		                    }
		                ],
		                series : [
		                    {
		                        name:'已收款',
		                        type:'bar',
		                        data:eval(JSON.stringify(report3AccountPayed))
		                    }
		                    ,
		                    {
		                        name:'优惠金额',
		                        type:'bar',
		                        data:eval(JSON.stringify(report3DiscountAmount))
		                    }
		                    ,
		                    {
		                        name:'优惠比例',
		                        type:'line',
		                        yAxisIndex: 1,
		                        data:eval(JSON.stringify(report3CaptureRate))
		                    }
		                ]
		            });
		        	}
	           
	            function report3Pie(array){
		            //--- 折柱 ---
		            var myChart = echarts.init(document.getElementById('report3Pie'));
		            myChart.setOption({
		               
		            	title : {
		                    x:'center'
		                },
		                tooltip : {
		                    trigger: 'item',
		                    formatter: "{a} <br/>{b} : {c} ({d}%)"
		                },
		                series : [
		                    {
		                        type: 'pie',
		                        radius : '55%',
		                        center: ['50%', '60%'],
		                        data:eval(JSON.stringify(array)),
		                        itemStyle: {
		                            emphasis: {
		                                shadowBlur: 10,
		                                shadowOffsetX: 0,
		                                shadowColor: 'rgba(0, 0, 0, 0.5)'
		                            }
		                        }
		                    }
		                ]
		            });
		        	}
	            
	            
	            function loadReport3(){
		  			  $.ajax({  
		  			        type: "POST",  
		  			        url: CONTEXT+"report/getReportPreferentialInfo",  
		  			        data:$("#form_report3").serialize(),  
		  			        success: function(data){  
		  			        //优惠情况分析-时间
		  			  		  var report3Date=new Array();
		  			  		  //优惠情况分析-已收款
		  			  		  var report3AccountPayed=new Array();
		  			  		  //优惠情况分析-优惠金额
		  			  		  var report3DiscountAmount=new Array();
		  			  		  //优惠情况分析-优惠比率
		  			  		  var report3CaptureRate=new Array();
		  			        	$.each(data.result,function(idx,item){ 
		  			        		//alert("idx"+idx+"name:"+item.accountPayable+",accountPayed:"+item.accountPayed); 
		  			        		report3Date[idx]=item.dataDate;
		  			        		report3AccountPayed[idx]=item.accountPayed;
		  			        		report3DiscountAmount[idx]=item.discountAmount;
		  			        		report3CaptureRate[idx]=item.captureRate;
		  			        		}); 
		  			        	report3(report3Date,report3AccountPayed,report3DiscountAmount,report3CaptureRate);
		  			        },  
		  			        error: function(json){  
		  			        	$.eAlert(data.msg);
		  			        }  
		  			    });  
		  			  
		  			$.ajax({  
	  			        type: "POST",  
	  			        url: CONTEXT+"report/getReportPreferentialInfoPie",  
	  			        data:$("#form_report3").serialize(),  
	  			        success: function(data){  
	  			        	var array=[];
	  			        	 function ObjStory(value,name){
	  			            	this.value=value;
	  			            	this.name=name;
	  			            }
	  			        	$.each(data.result,function(idx,item){
	  			        		if(item.accountPayed!=null){
	  			        			array.push(new ObjStory(item.accountPayed,'已收款'));
	  			        		}
	  			        		if(item.discountAmount!=null){
	  			        		array.push(new ObjStory(item.discountAmount,'优惠金额'));
	  			        		}
	  			        		}); 
	  			        	report3Pie(array);
	  			        },  
	  			        error: function(json){  
	  			        	$.eAlert(data.msg);
	  			        }  
	  			    });  
		  		}
	            //初始化报表
	            loadReport3();
	  
	    		
	    		
	    		
	    		
	    //绑定事件-----------------------------------------------------------------------------
	            
	            
	            
		
	    //图表1查询事件       
	    $('#report1Query').click(function() {
	    	loadReport1();
	    	});
        //图表2查询事件       
	    $('#report2Query').click(function() {
	    	loadReport2();
	    	loadreport2Table();
	    	});
        //图表3查询事件       
	    $('#report3Query').click(function() {
	    	loadReport3();
	    	});
	    
	 
	});
}