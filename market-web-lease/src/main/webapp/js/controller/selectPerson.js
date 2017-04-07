
/**
 * data       初始化数据，多选时有用 
 * type       person 选人  post 选岗位
 * select     single 单选  multi 多选
 * $.selectPerson({callBack:function(res){},data:[{id:'123',name:'龙在'},{id:'1233',name:'龙在112'}]
 *				,select:'single',type:'post'
 *	});
 * @param $
 */
(function($){
	$.asynCss(["lib/css/metroStyle.css","css/form-imput.css","css/selectPerson.css"]);
	$.asynJs(["lib/jquery.ztree.core.min.js","lib/jquery.ztree.exedit.min.js"]);

	$.extend($,{
		selectPerson :function(options){
			//select single 单选   multi 多选
			// type person 选人  post 选岗位
			var option = {popwidth:'700',callBack:function(){},data:[],select:'multi',type:'person'};
			$.extend(option,options);

			var isSingle = option.select == 'single';
			var titleText = option.type == 'person' ? '选择用户' : '选择岗位';
			var typeListName = option.type == 'person' ? '用户列表' : '岗位列表';
			var selectTypeName = option.type == 'person' ? '已选择用户' : '已选择岗位';
			var singleShowName = option.type == 'person' ? '原用户' : '原岗位';
			if(option.actor != ''){
				singleShowName = option.actor;
			}
			option.titleText = option.titleText||titleText;
			option.typeListName = option.typeListName||typeListName;
			option.selectTypeName = option.selectTypeName||selectTypeName;
			option.singleShowName = option.singleShowName||singleShowName;
			$("#selectPerson").remove();
			
			//组装内容
			var doc = '<div id="selectPerson" class="ml10">' +
							'<div class="singleShow">'+option.singleShowName+':<span class="sourceName"></span></div>'+
							'<div class="contPerson clearfix">'+
								'<div class="resetPerson_bs">'+
									'<h3 class="treetitA">组织架构</h3>'+
									'<ul id="selectPerson_tree" class="ztree"></ul>'+
								'</div>'+
								'<div class="resetPerson_rt">'+
									'<h3 class="treetitB">'+option.typeListName+'</h3>'+
									'<ul class="ztree_list">'+
									'</ul>'+
								'</div>'+
								'<div class="user_selected">'+
									'<h3 class="user_sz">'+option.selectTypeName+'</h3>'+
									'<div class="user_area"></div>'+
								'</div>'+
							'</div>'+
						'</div>';
			//将内容添加到html中
			$("body").append(doc);
			//如果单选
			if(option.select == 'single'){
				$("#selectPerson").find(".user_selected").remove();
			}
			$("#selectPerson").popwin({
				titleText: option.titleText, //弹出框名
				popwidth:option.popwidth,//设置宽度
				btnText:["取消","保存"],//按钮的文本
				drag:true,//是否拖动
				popUrl: null,
				callback: function(){
					var _selected = getSelectPerson();
					console.log("返回已选择的数据:"+JSON.stringify(_selected));
				
					option.callBack(_selected);
					$("#selectPerson").modal("hide");
				}
			});
	
			var CURRENT_CLICK_TREE_ID = '';
			//树的所代有码
			var setting = {
	            view: {
	                selectedMulti: true,
	                showIcon: false,
	                showLine: false
	            },
	            data: {
	                simpleData: {
	                    enable: true
	                }
	            },
	            callback: {
	            	onClick : function(event,treeId,treeNode){
						//如果已经被选中，不再刷新列表
						if(CURRENT_CLICK_TREE_ID == treeNode.id){
							return;
						}
						//刷新列表
						CURRENT_CLICK_TREE_ID = treeNode.id;
						if(treeNode.type == '2'){
							loadData();
						}
						
					}
	            },
	            check: {
	                enable: true
	            }
	        };

			//初始化组织
			function initOrgTree(){
				var dtd = $.Deferred(); // 生成Deferred对象
				$.eLoading(true);
				$.getJSON(CONTEXT+"sysOrg/initGroupTree",function(data){
					if(data.success){
						$.fn.zTree.init($("#selectPerson_tree"), setting, data.result);
						dtd.resolve();
					} else {
						$.eAlert("错误信息","初始化组织架构树失败,"+data.msg);
					}
					$.eLoading(false);
				});
				return dtd.promise();
			}

			$.when(initOrgTree())
			.done(function(){
				//选中集团
				
			})
			.done(function(){
				//获取选中的节点，初始化列表

			});
			//加载数据
			function loadData(){
				//获取节点
				$.eLoading(true);
				var treeObj = $.fn.zTree.getZTreeObj("selectPerson_tree");
				var nodes = treeObj.getSelectedNodes();
				var orgId = nodes[0].id;
				var url = '';
				if(option.type == 'post'){
					//加载岗位
					url = "post/getPostListByOrg";
				} else {
					//加载用户 
					url = "sysCompanyUser/getUserListByOrg";
				}
				$.getJSON(CONTEXT+url,{"orgId":orgId},function(data){
					
					if(data.success){
						$("#selectPerson").find("ul.ztree_list").html("");
						var liAar = [];
						if(data.result){
							for(var i = 0;i<data.result.length;i++){
								var record = data.result[i];
								if(option.type == 'post'){
									liAar.push('<li class="listName" uid="'+record.id+'"><span class="list_sp">'+record.name+'</span></li>');
								} else {
									liAar.push('<li class="listName" uid="'+record.id+'"><span class="list_sp">'+record.name+'</span><span class="list_nm">'+record.code+'</span></li>');
								}
								
							}
						}
						$("#selectPerson").find("ul.ztree_list").html(liAar.join(''));
					} else {
						$.eAlert("提示信息",data.msg);
					}
					$.eLoading(false);
				});
			}
			
			// 放在已选用户里面
			var ztreNm = $('.ztree_list');
			ztreNm.on('click', '.listName', function() {
				//如果是单选
				if(option.select == 'single'){
					$(this).parent().children().removeClass("selected");
					$(this).addClass("selected");
					
				} else {
					//多选
					var $userArea = $('.user_area');
					var uid = $(this).attr("uid");
					var count = $userArea.find("span[uid='"+uid+"']").length;
					if(count>0){
						return;
					}
					var name = $(this).find(".list_sp").text();
					var sp = $('<span class="sp_bg" uid="'+uid+'" uname="'+name+'">' + name
							+ '<i class="fa fa-times"></i></span>');
					$userArea.append(sp);

					sp.on('click', function() {
						$(this).remove();
					})
				}
				
			})
			
			//初始化已选择的人员
			var initSelected = function(){
				$("#selectPerson").find(".singleShow").hide();
				if(option.data.length>0){
					if(!isSingle){
						var $userArea = $('.user_area');
						for(var i = 0;i<option.data.length;i++){
							var d = option.data[i];
							var sp = $('<span class="sp_bg" uid="'+d.id+'" uname="'+d.name+'">' + d.name
									+ '<i class="fa fa-times"></i></span>');
							$userArea.append(sp);
							sp.on('click', function() {
								$(this).remove();
							})
						}
					} else {
						$("#selectPerson").find(".singleShow").show();
						$("#selectPerson").find(".singleShow").find(".sourceName").text(option.data[0].name);

					}
					
				}
			}
			

			// 获取已选择的人员
			var getSelectPerson = function(){
				var res = [];
				if(isSingle){
					var $li = $('.ztree_list').find("li.selected");
					if($li.length > 0){
						var id = $li.attr("uid");
						var name = $li.find(".list_sp").text();
						res.push({"id":id,"name":name});
					}
				} else {
					var $span = $('.user_area').children();
					if($span.length>0){
						$span.each(function(i){
							var id = $(this).attr("uid");
							var name = $(this).attr("uname");
							res.push({"id":id,"name":name});
						});
					}
				}
				return res;
			}
			initSelected();
			$("#selectPerson").modal();
		}
	});
})(jQuery);