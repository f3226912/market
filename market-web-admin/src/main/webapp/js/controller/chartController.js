function _call(templateUrl,param){
	$("#main-wrapper").loadPage(templateUrl,['css/DT_bootstrap.css'],['lib/echarts.js'],function(){
			
	    // Step:3 conifg ECharts's path, link to echarts.js from current page.
	    // Step:3 为模块加载器配置echarts的路径，从当前页面链接到echarts.js，定义所需图表路径
	    require.config({
	        paths: {
	            echarts: 'lib'
	        }
	    });
	    
	    // Step:4 require echarts and use it in the callback.
	    // Step:4 动态加载echarts然后在回调函数中开始使用，注意保持按需加载结构定义图表路径
	    require(
	        [
	            'echarts',
	            'echarts/chart/bar',
	            'echarts/chart/line',
	            'echarts/chart/pie'
	        ],
	        function (ec) {
	            //--- 折柱 ---
	            var myChart = ec.init(document.getElementById('main'));
	            myChart.setOption({
	                color:[ '#1e90ff', '#ff6347'],
	                tooltip : {
	                    trigger: 'axis',
	                },
	                legend: {
	                    data:['蒸发量','降水量']
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
	                        data : ['1月','2月','3月','4月','5月','6月','7月','8月','9月','10月','11月','12月']
	                    }
	                ],
	                yAxis : [
	                    {
	                        type : 'value',
	                        splitArea : {show : true}
	                    }
	                ],
	                series : [
	                    {
	                        name:'蒸发量',
	                        type:'bar',
	                        data:[2.0, 4.9, 7.0, 23.2, 25.6, 76.7, 135.6, 162.2, 32.6, 20.0, 6.4, 3.3]
	                    },
	                    {
	                        name:'降水量',
	                        type:'bar',
	                        data:[2.6, 5.9, 9.0, 26.4, 28.7, 70.7, 175.6, 182.2, 48.7, 18.8, 6.0, 2.3]
	                    }
	                ]
	            });
	            
	            // --- 饼状图 ---
	            var myChart = ec.init(document.getElementById('mains'));
	                    myChart.setOption({
	                        color:[
	                         '#1e90ff', '#ff6347', '#7b68ee', '#00fa9a', '#ffd700', 
	                        ],
	                        title: {
	                            text: '谷登用户访问来源',
	                            subtext: '',
	                            x: 'center',
	                        },
	                        tooltip: {
	                            trigger: 'item',
	                            formatter: "{a} <br/>{b} : {c} ({d}%)"
	                        },
	                        legend: {
	                            orient: 'horizontal',
	                            x: '10px',
	                            y: '100px',
	                            data: ['直接访问', '邮件营销', '联盟广告', '视频广告', '搜索引擎']
	                            
	                        },
	                        series: [{
	                            name: '',
	                            type: 'pie',
	                            radius: '55%',
	                            center: ['50%', '60%'],
	                            data: [{
	                                value: 335,
	                                name: '直接访问'
	                            }, {
	                                value: 310,
	                                name: '邮件营销'
	                            }, {
	                                value: 234,
	                                name: '联盟广告'
	                            }, {
	                                value: 135,
	                                name: '视频广告'
	                            }, {
	                                value: 1548,
	                                name: '搜索引擎'
	                            }],
	                            itemStyle: {
	                                emphasis: {
	                                    shadowBlur: 10,
	                                    shadowOffsetX: 0,
	                                    shadowColor: 'rgba(0, 0, 0, 0.5)'
	                                },
	                            }
	                        }]
	                    });
	        }
	    );

		
	});
}