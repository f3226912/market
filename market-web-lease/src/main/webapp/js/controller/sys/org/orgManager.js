function _call(templateUrl,param){
	$("#main-wrapper").loadPage(templateUrl,
			["css/form-imput.css","css/system.css","lib/css/metroStyle.css","lib/css/datepicker.css"],
			["lib/jquery-migrate.js","js/common/pageBar.js","lib/jquery.tmpl.min.js","lib/jquery.ztree.core.min.js","lib/jquery.ztree.exedit.min.js","lib/bootstrap-datepicker.js"],
			function(){

		var PAGE_SIZE = 50;
		var CURRENT_CLICK_TREE_ID = '';
		var ADD_CLICK = false;//防重复提交，（不能绝对防住）
		//初始化分页控件
		function initPageBar(result){
			// 分页工具组件
			$("#pagebar").page({
				pageIndex : 1,
				pageSize : PAGE_SIZE,
				total : result.total,
				callback : function(pageNum,pageSize){
					$('#templateBody').html(""); // 清空内容
					// 点击回调处理

					loadData({"pageNum":pageNum,"pageSize":pageSize}, false);
				}
			});
		}
		
		//加载数据
		function loadData(page, isInit){
			//获取节点
			$.eLoading(true);
			var treeObj = $.fn.zTree.getZTreeObj("treeDemo");
			var nodes = treeObj.getSelectedNodes();
			var orgId = nodes[0].id;
			page.orgId = orgId;
			$.getJSON(CONTEXT+"sysOrg/queryUserPage",page,function(data){
				
				if(data.success){
					if(isInit){
						initPageBar(data.result);
					}
					$("#templateBody").html("");
					$('#template').tmpl({orgs:data.result.recordList}).appendTo('#templateBody');

				} else {
					$.eAlert("提示信息",data.msg);
				}
				$.eLoading(false);
			});
		}
	
		var setting = {
				view : {
					addHoverDom : function(treeId,treeNode){
						var sObj = $("#" + treeNode.tId + "_span");
						if ($("#editBtn_"+treeNode.tId).length>0 || $("#removeBtn_"+treeNode.tId).length>0) return;
						var groupId = window.parent._USER.groupId;
						
						if(treeNode.id == groupId){
							return;
						}
						//是否有编辑权限
						var addStr="";
						if(hasButtonAuth("BtnSysOrgEdit")){
							addStr = "<span class='button edit' id='editBtn_"
								+ treeNode.tId
								+ "' title='编辑组织'></span>";
						}
						//是否有删除权限
						if(hasButtonAuth("BtnSysOrgDelete")){
							addStr+="<span class='button remove' id='removeBtn_"
								+ treeNode.tId
								+ "' title='删除组织'></span>"
						}
						
						sObj.after(addStr);

						$("#treeDemo").find("#editBtn_"+treeNode.tId).off("click.org")
						.on("click.org",function(event){
							//阻止事件冒泡
							event.stopPropagation();
							if(treeNode.type == 1){
								initCompanyAndDepartment("编辑公司");
								$("#companyForm").find("p[name='typeName']").text("公司");
								//setEditOrg(treeNode.id,treeNode.type,treeNode.getParentNode());
								$("#popwinCompany").modal();
								
							} else if(treeNode.type == 2){
								initCompanyAndDepartment("编辑部门");
								$("#companyForm").find("p[name='typeName']").text("部门");
								$("#popwinCompany").modal();
							}
							else {
								initMartket("编辑市场");
								$("#popwinMarket").modal();
							}
							setEditOrg(treeNode.id,treeNode.type,treeNode.getParentNode());
							
						});
						$("#treeDemo").find("#removeBtn_"+treeNode.tId).off("click.org")
						.on("click.org",function(event){
							event.stopPropagation();
							$.eConfirm("删除组织","删除组织会导致相关业务数据丢失，确定该组织吗？",function(){
								//设置值
								$.eLoading(true);
								$.getJSON(CONTEXT+"sysOrg/deleteSysOrg",{id:treeNode.id,type:treeNode.type},function(data){
									if(data.success){
										//删除成功
										var treeObj = $.fn.zTree.getZTreeObj("treeDemo");
										treeObj.removeNode(treeNode,false);
										$.eAlert("删除组织","删除成功");
								
									} else {
										$.eAlert("删除组织",data.msg);
		
									}
									$.eLoading(false);
								});

							});
							
						});
					},
					removeHoverDom :function(treeId,treeNode){
				
						$("#editBtn_"+treeNode.tId).remove();
						$("#removeBtn_"+treeNode.tId).remove();
					},
					selectedMulti : false,
					showIcon : false,
					showLine : false
				},
				data : {
					simpleData : {
						enable : true
					}
				},
				edit : {
					enable : false 
				},
				callback : {
					
					onClick : function(event,treeId,treeNode){
						//如果已经被选中，不再刷新列表
						if(CURRENT_CLICK_TREE_ID == treeNode.id){
							return;
						}
						//刷新列表
						CURRENT_CLICK_TREE_ID = treeNode.id;
						
						if(treeNode.type == '1'){
							//如果公司
							$("#addCompany").show();
							$("#addMarket").show();
							$("#addDepartment").show();
						} else if(treeNode.type == '2'){
							//如果部门
							$("#addCompany").hide();
							$("#addMarket").hide();
							$("#addDepartment").show();
						} else if(treeNode.type == '3'){
							//如果市场
							$("#addCompany").hide();
							$("#addMarket").hide();
							$("#addDepartment").show();
						}
						loadData({pageNum:1,pageSize:PAGE_SIZE},true);
					}
				}
			};
		
		//初始化组织树
		function initOrgTree(){
			var dtd = $.Deferred(); // 生成Deferred对象
			$.eLoading(true);
			$.getJSON(CONTEXT+"sysOrg/initOrgTree",function(data){
				if(data.success){
					$.fn.zTree.init($("#treeDemo"), setting, data.result);
					dtd.resolve();
				} else {
					$.eAlert("错误信息","初始化组织架构树失败,"+data.msg);
				}
				$.eLoading(false);
			});
			return dtd.promise();
		}
		

		$(document).ready(function() {
			$.when(initOrgTree())
			.done(function(){
				//选中集团
				var groupId = window.parent._USER.groupId;
				var treeObj = $.fn.zTree.getZTreeObj("treeDemo");
				//获取集团节点
				var node = treeObj.getNodeByParam("id",groupId);
				//alert(JSON.stringify(node));
				//选中该节点
				//treeObj.selectNode(node);
				$("#"+node.tId+"_a").click();
				
			})
			.done(function(){
				//获取选中的节点，初始化列表
				loadData({pageNum:1,pageSize:PAGE_SIZE},true);
			});

		});
		
		function initCompanyAndDepartment(name){
			$("#popwinCompany").popwin({
				titleText: name, //弹出框名
				popwidth:"600",//设置宽度 
				btnText:["取消","保存"],//按钮的文本
				drag:false,//是否拖动
				callback : function(){
					if(ADD_CLICK){
						return;
					}
					ADD_CLICK = true;
					if($("#companyForm").valid()){
						//保存
						var param = $("#companyForm").serializeJson();
						$.when(saveOrg(param))
						.done(function(){
							$("#popwinCompany").modal("hide");
						})
						.fail();
					} else {
						ADD_CLICK = false;
					}
				
				}
			});
		}
		
		function initMartket(name){
			$("#popwinMarket").popwin({
				titleText: name, //弹出框名
				popwidth:"800",//设置宽度 
				btnText:["取消","保存"],//按钮的文本
				drag:false,//是否拖动
				callback : function(){
					if(ADD_CLICK){
						return;
					}
					ADD_CLICK = true;
					if($("#marketForm").valid()){
						//保存
						var param = $("#marketForm").serializeJson();
						//设置pca
						var p = $("#province").find("option:selected").text();
						var c = $("#city").find("option:selected").text();
						var a = $("#area").find("option:selected").text();
						param['marKetEntity.pca'] = p+'/'+c+'/'+a;
						$.when(saveOrg(param))
						.done(function(){
							$("#popwinMarket").modal("hide");
						})
						.fail();
					} else {
						ADD_CLICK = false;
					}
				
				}
			});
			$("#openTime").datepicker({
				format: "yyyy-mm-dd",
				autoclose: true
			});
			$("#marketForm").validate();
		}

		//新增公司
		$("#addCompany").click(function(){
			//设置上级公司
			initCompanyAndDepartment("新增公司");
			var treeObj = $.fn.zTree.getZTreeObj("treeDemo");
			var nodes = treeObj.getSelectedNodes();
			if(nodes <= 0){
				$.eAlert("提示信息","请先选择组织架构");
			}
			var id = nodes[0].id;
			var name = nodes[0].name;
			$("#companyForm").find("p[name='typeName']").text("公司");
			$("#companyForm").find("input[name='id']").val("");
			$("#companyForm").find("input[name='parentId']").val(id);
			$("#companyForm").find("p[name='parentName']").text(name);
			$("#companyForm").find("input[name='type']").val(1);
			$("#popwinCompany").modal();
		
		});
		
		//新增部门
		$("#addDepartment").click(function(){
			//设置上级公司
			initCompanyAndDepartment("新增部门");
			
			var treeObj = $.fn.zTree.getZTreeObj("treeDemo");
			var nodes = treeObj.getSelectedNodes();
			if(nodes <= 0){
				$.eAlert("提示信息","请先选择组织架构");
			}
			var id = nodes[0].id;
			var name = nodes[0].name;
			$("#companyForm").find("p[name='typeName']").text("部门");
			$("#companyForm").find("input[name='id']").val("");
			$("#companyForm").find("input[name='parentId']").val(id);
			$("#companyForm").find("p[name='parentName']").text(name);
			$("#companyForm").find("input[name='type']").val(2);

			$("#popwinCompany").modal();

		});
		
		//新增市场
		$("#addMarket").click(function(){
			//设置上级公司
			initMartket("新增市场");
			initAreaEvent();
			
			var treeObj = $.fn.zTree.getZTreeObj("treeDemo");
			var nodes = treeObj.getSelectedNodes();
			if(nodes <= 0){
				$.eAlert("提示信息","请先选择组织架构");
			}
			var id = nodes[0].id;
			var name = nodes[0].name;
			$("#marketForm").find("input[name='id']").val("");
			$("#marketForm").find("input[name='parentId']").val(id);
			$("#marketForm").find("p[name='parentName']").text(name);
			$("#marketForm").find("input[name='type']").val(3);
			$("#popwinMarket").modal();
		});
		
		/**
		 * 编辑组织，设置数据
		 */
		function setEditOrg(id,type,pNode){
			var dtd = $.Deferred(); // 生成Deferred对象
			$.eLoading(true);
			$.getJSON(CONTEXT+"sysOrg/querySysOrg",{id:id},function(data){
				if(data.success){
					//添加节点到父节点下
					var res = data.result;
					if(type == 1 || type == 2){
						$("#companyForm").find("input[name='parentId']").val("");
						$("#companyForm").find("p[name='parentName']").text(pNode.name);
						$("#companyForm").find("input[name='type']").val(type);
						$("#companyForm").find("input[name='fullName']").val(res.fullName);
						$("#companyForm").find("input[name='shortName']").val(res.shortName);
						$("#companyForm").find("textarea[name='remark']").text($.trim(res.remark));
						$("#companyForm").find("input[name='id']").val(res.id);
					} else {
						$("#marketForm").find("input[name='parentId']").val("");
						$("#marketForm").find("input[name='id']").val(res.id);
						$("#marketForm").find("p[name='parentName']").text(pNode.name);
						$("#marketForm").find("input[name='type']").val(3);
						$("#marketForm").find("input[name='fullName']").val(res.fullName);
						$("#marketForm").find("input[name='shortName']").val(res.shortName);
						$("#marketForm").find("input[name='marKetEntity.code']").val(res.marKetEntity.code);
						var date = new Date(res.marKetEntity.openTime);
						$("#marketForm").find("input[name='marKetEntity.openTime']").val(date.Format("yyyy-MM-dd"));
						$("#marketForm").find("select[name='marKetEntity.marketStatus']").val(res.marKetEntity.marketStatus);
						$("#marketForm").find("textarea[name='marKetEntity.address']").val($.trim(res.marKetEntity.address));
						//PCA
						initAreaEvent(res.marKetEntity.provinceId,res.marKetEntity.cityId,res.marKetEntity.areaId);
					}
					dtd.resolve();
				} else {
					$.eAlert("错误信息","获取组织信息失败");
					dtd.reject();
				}
				$.eLoading(false);
			});
			return dtd.promise();
		}
		
		function initAreaEvent(p,c,a){
			$("#province").off("change.area")
			.on("change.area",function(){
				var val = $("#province").val();
				setArea(val,"city");
			});
			$("#city").off("change.area")
			.on("change.area",function(){
				var val = $("#city").val();
				setArea(val,"area");
			});
			
			//初始化省份,市，区
			$.when(setArea("","province"))
			.done(function(){
				if($.trim(p) != ''){
					$("#province").val(p);
					console.log("province:"+p);
					$.when(setArea(p,"city"))
					.done(function(){
						if($.trim(c) != ''){
							$("#city").val(c);
							console.log("city:"+c);
							$.when(setArea(c,"area"))
							.done(function(){
								if($.trim(a) != ''){
									console.log("area:"+a);
									$("#area").val(a);
								}
							});
						}
					});
				}
			});
			function setArea(val,id){
				//设置值
				var dtd = $.Deferred(); // 生成Deferred对象
				$.getJSON(CONTEXT+"area/queryChildrenArea",{areaId:val},function(data){
					if(data.success){
						var opt = "<option value=''>请选择</option>";
						$("#"+id).html("");
						$("#"+id).append(opt);
						$('#areaTemplate').tmpl(data.result).appendTo($("#"+id));
						dtd.resolve();
					} else {
						$.eAlert("错误信息","获取区域失败");
						dtd.reject();
					}
					
				});
				return dtd.promise();
			}
		}
		
		
		function saveOrg(param){
			// console.info(JSON.stringify(param));
			var dtd = $.Deferred(); // 生成Deferred对象
			$.eLoading(true);
			$.getJSON(CONTEXT+"sysOrg/addSysOrg",param,function(data){
				ADD_CLICK = false;
				if(data.success){
					$.eAlert("提示信息","保存成功");
					
					if($.trim(param.id) != ''){
						//更新当前节点
						var treeObj = $.fn.zTree.getZTreeObj("treeDemo");
						var nodes = treeObj.getSelectedNodes();
						/*$.each(nodes, function(i, n){
							console.log( "updateNode #" + n.id+",name="+n.name );
						});*/
						var node = nodes[0];
						node.name = param.fullName;
						treeObj.updateNode(node);
					} else {
						//添加节点到父节点下
						var treeObj = $.fn.zTree.getZTreeObj("treeDemo");
						var nodes = treeObj.getSelectedNodes();
						/*$.each(nodes, function(i, n){
							console.log( "addNodes #" + n.id+",name="+n.name );
						});*/
						var newNode = {"name":param.fullName,"id":data.result,"type":param.type};
						treeObj.addNodes(nodes[0],newNode,true);
					}
					
					dtd.resolve();
				} else {
					$.eAlert("错误信息","保存失败,"+data.msg);
					dtd.reject();
				}
				$.eLoading(false);
			});
			return dtd.promise();
		}
	});
}
