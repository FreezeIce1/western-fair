<%@page pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="zh-CN">
  <head>
    <meta charset="UTF-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <meta name="description" content="">
    <meta name="author" content="">

	<link rel="stylesheet" href="${pageContext.request.contextPath}/bootstrap/css/bootstrap.min.css">
	<link rel="stylesheet" href="${pageContext.request.contextPath}/css/font-awesome.min.css">
	<link rel="stylesheet" href="${pageContext.request.contextPath}/css/main.css">
	<link rel="stylesheet" href="${pageContext.request.contextPath}/ztree/zTreeStyle.css">
	<style>
	.tree li {
        list-style-type: none;
		cursor:pointer;
	}
	table tbody tr:nth-child(odd){background:#F4F4F4;}
	table tbody td:nth-child(even){color:#C00;}
	</style>
  </head>

  <body>

    <nav class="navbar navbar-inverse navbar-fixed-top" role="navigation">
      <div class="container-fluid">
        <div class="navbar-header">
          <div><a class="navbar-brand" style="font-size:32px;" href="#">角色维护</a></div>
        </div>
        <div id="navbar" class="navbar-collapse collapse">
          <%@ include file="/WEB-INF/jsp/common/top.jsp" %>
        </div>
      </div>
    </nav>

    <div class="container-fluid">
      <div class="row">
        <div class="col-sm-3 col-md-2 sidebar">
			<div class="tree">
				<%@ include file="/WEB-INF/jsp/common/menu.jsp" %>
			</div>
        </div>
        <div class="col-sm-9 col-sm-offset-3 col-md-10 col-md-offset-2 main">
			<div class="panel panel-default">
			  <div class="panel-heading">
				<h3 class="panel-title"><i class="glyphicon glyphicon-th"></i> 数据列表</h3>
			  </div>
			  <div class="panel-body">
			      <button class="btn btn-success" onclick="doAssign()">分配许可</button>
                  <ul id="permissionTree" class="ztree"></ul>
			  </div>
			</div>
        </div>
      </div>
    </div>

    <script src="${pageContext.request.contextPath}/jquery/jquery-2.1.1.min.js"></script>
    <script src="${pageContext.request.contextPath}/bootstrap/js/bootstrap.min.js"></script>
	<script src="${pageContext.request.contextPath}/script/docs.min.js"></script>
	<script src="${pageContext.request.contextPath}/layer/layer.js"></script>
	<script src="${pageContext.request.contextPath}/ztree/jquery.ztree.all-3.5.min.js"></script>
        <script type="text/javascript">
            $(function () {
			    $(".list-group-item").click(function(){
				    if ( $(this).find("ul") ) {
						$(this).toggleClass("tree-closed");
						if ( $(this).hasClass("tree-closed") ) {
							$("ul", this).hide("fast");
						} else {
							$("ul", this).show("fast");
						}
					}
				});
			    
			    var setting = {
			    	//表示启用复选框
		    		check : {
		    		    enable : true 
		    		},
		    		async: {
		    			enable: true,
		    			url:"${pageContext.request.contextPath}/permission/loadAssignData?roleid=${param.id}",
		    			autoParam:["id", "name=n", "level=lv"]
		    		},
					view: {
						selectedMulti: false,
						addDiyDom: function(treeId, treeNode){  //treeNode对应的就是我们的permission对象
							var icoObj = $("#" + treeNode.tId + "_ico"); // tId = permissionTree_1, $("#permissionTree_1_ico")
							if ( treeNode.icon ) {
								icoObj.removeClass("button ico_docu ico_open").addClass(treeNode.icon).css("background","");
							}
                            
						}
					}
			    };
			   
			    $.fn.zTree.init($("#permissionTree"), setting);
            });
            function doAssign() {
            	var treeObj = $.fn.zTree.getZTreeObj("permissionTree");
            	//获取树形结构中被选中的按钮
            	var nodes = treeObj.getCheckedNodes(true);
            	if ( nodes.length == 0 ) {
                    layer.msg("请选择需要分配的许可信息", {time:2000, icon:5, shift:6}, function(){
                    	
                    });
            	} else {
            		var d = "roleid=${param.id}";
            		$.each(nodes, function(i, node){
            			d += "&permissionids="+node.id
            		});
            		$.ajax({
            			type : "POST",
            			url  : "${pageContext.request.contextPath}/role/doAssign",
            			data : d,
            			success : function (result) {
            				if ( result ) {
                                layer.msg("分配许可信息成功", {time:2000, icon:6}, function(){
                                	
                                });
            				} else {
                                layer.msg("分配许可信息失败", {time:2000, icon:5, shift:6}, function(){
                                	
                                });
            				}
            			}
            		});
            	}
            }
        </script>
  </body>
</html>
