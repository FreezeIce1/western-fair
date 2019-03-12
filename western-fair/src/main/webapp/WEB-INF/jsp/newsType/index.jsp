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
          <div><a class="navbar-brand" style="font-size:32px;" href="#">类型</a></div>
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
<form class="form-inline" role="form" style="float:left;">
  <div class="form-group has-feedback">
    <div class="input-group">
      <div class="input-group-addon">查询条件</div>
      <input id="queryText" class="form-control has-success" type="text" placeholder="请输入关键字">
    </div>
  </div>
  <button id="queryBtn" type="button" class="btn btn-warning"><i class="glyphicon glyphicon-search"></i> 查询</button>
  
</form>
<button type="button" class="btn btn-danger" onclick="deleteNewsTypeList()" style="float:right;margin-left:10px;"><i class=" glyphicon glyphicon-remove"></i> 删除</button>
<button type="button" class="btn btn-primary" style="float:right;" onclick="window.location.href='${pageContext.request.contextPath}/newsType/add'"><i class="glyphicon glyphicon-plus"></i> 新增</button>
<br>
 <hr style="clear:both;">
          <div class="table-responsive">
            <form id="newsTypeForm">
            <table class="table  table-bordered">
              <thead>
                <tr >
                  <th width="30">#</th>
				  <th width="30"><input type="checkbox" id="allSelBox"></th>
                  <th>类型名称</th>
                  <th>创建时间</th>
                  <th width="100">操作</th>
                </tr>
              </thead>
              
              <tbody id="newsTypeData">
                  
              </tbody>
              
			  <tfoot>
			     <tr >
				     <td colspan="10" align="center">
						<ul class="pagination">

							 </ul>
					 </td>
				 </tr>

			  </tfoot>
            </table>
            </form>
          </div>
			  </div>
			</div>
        </div>
      </div>
    </div>

    <script src="${pageContext.request.contextPath}/jquery/jquery-2.1.1.min.js"></script>
    <script src="${pageContext.request.contextPath}/bootstrap/js/bootstrap.min.js"></script>
	<script src="${pageContext.request.contextPath}/script/docs.min.js"></script>
	<script src="${pageContext.request.contextPath}/layer/layer.js"></script>
        <script type="text/javascript">
            var likeflg = false;
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
			    
			    pageQuery(1);
			    
			    $("#queryBtn").click(function(){
			    	var queryText = $("#queryText").val(); 
			    	if ( queryText == "") {
			    		likeflg = false;
			    	} else {
			    		likeflg = true;
			    	}
			    	
			    	pageQuery(1);
			    });
			    
			    $("#allSelBox").click(function(){
			    	var flg = this.checked;
			    	$("#newsTypeData :checkbox").each(function(){
			    		this.checked = flg;
			    	});
			    });
            });
            
            // 分页查询
            function pageQuery( pageno ) {
            	var loadingIndex = null;
            	
            	var jsonData = {"pageno" : pageno, "pagesize" : 10};
            	if ( likeflg == true ) {
            		jsonData.queryText = $("#queryText").val();
            	}
            	
            	$.ajax({
            		type : "POST",
            		url  : "${pageContext.request.contextPath}/newsType/pageQuery",
            		data : jsonData,
            		beforeSend : function(){
            			loadingIndex = layer.msg('处理中', {icon: 16});
            		},
            		success : function(result) {
            			layer.close(loadingIndex);
            			if ( result.success ) {
            				
            				// 局部刷新页面数据
            				var tableContent = "";
            				var pageContent = "";
            				
            				var newsTypePage = result.data;
            				var newsTypeList = newsTypePage.datas;
            				
            				$.each(newsTypeList, function(i, newsType){
            	                tableContent += '<tr>';
	          	                tableContent += '  <td>'+(i+1)+'</td>';
	          					tableContent += '  <td><input type="checkbox" name="id" value="'+newsType.id+'"></td>';
	          	                tableContent += '  <td>'+newsType.name+'</td>';
	          	                tableContent += '  <td>'+newsType.addtime+'</td>';
	          	                tableContent += '  <td>';
	          					tableContent += '      <button type="button" onclick="goUpdatePage('+newsType.id+')" class="btn btn-primary btn-xs"><i class=" glyphicon glyphicon-pencil"></i></button>';
	          					tableContent += '	  <button type="button" onclick="deleteNewsType('+newsType.id+',\''+newsType.name+'\')" class="btn btn-danger btn-xs"><i class=" glyphicon glyphicon-remove"></i></button>';
	          					tableContent += '  </td>';
	          	                tableContent += '</tr>';
            				});
            				
            				if ( pageno > 1 ) {
            					pageContent += '<li><a href="#" onclick="pageQuery('+(pageno-1)+')">上一页</a></li>';
            				}
            				
            				for ( var i = 1; i <= newsTypePage.totalno; i++ ) {
            					if ( i == pageno ) {
            						pageContent += '<li class="active"><a  href="#">'+i+'</a></li>';
            					} else {
            						pageContent += '<li ><a href="#" onclick="pageQuery('+i+')">'+i+'</a></li>';
            					}
            				}
            				
            				if ( pageno < newsTypePage.totalno ) {
            					pageContent += '<li><a href="#" onclick="pageQuery('+(pageno+1)+')">下一页</a></li>';
            				}

            				$("#newsTypeData").html(tableContent);
            				$(".pagination").html(pageContent);
            			} else {
                            layer.msg("类型分页查询失败", {time:2000, icon:5, shift:6}, function(){
                            	
                            });
            			}
            		}
            	});
            }
            //分页查询
            function goUpdatePage(id){
      			window.location.href="${pageContext.request.contextPath}/newsType/edit?id="+id;
      		}
            //删除类型
            function deleteNewsType(id,name){
      			layer.confirm("删除类型【"+name+"】，是否继续",  {icon: 3, title:'提示'}, function(cindex){
      				//点击确定按钮要调用的回调函数
      				//删除用户信息
      				//使用ajax技术进行删除
      				$.ajax({
      					type : "POST",
      					url : "${pageContext.request.contextPath}/newsType/delete",
      					data : {"id" : id},
      					success:function(result){
      						if(result.success){
      							//表示删除用户成功  重新分页查询用户信息
      							pageQuery(1);
      						}else{
      							layer.msg("删除类型删除失败,可能是该类型正在使用!", {time:2000, icon:5, shift:6}, function(){  	   	   
            	    	    	   });
      						}
      					}
      				});
      				
    			    layer.close(cindex);
    			}, function(cindex){
    			    layer.close(cindex);
    			});
      		}
            
          //批量删除
      		function deleteNewsTypeList(){
      			var boxes = $("#newsTypeData :checked");
      			
      			if( boxes.length == 0){
      				layer.msg("请选择要删除的类型", {time:2000, icon:5, shift:6}, function(){  	   	   
	    	    	   });
      			}else{
      				layer.confirm("删除选择的类型，是否继续",  {icon: 3, title:'提示'}, function(cindex){
						//删除选择的用户信息
          				$.ajax({
          					type : "POST",
          					url : "${pageContext.request.contextPath}/newsType/deleteNewsTypeList",
          					data : $("#newsTypeForm").serialize(),  //序列化表单的内容
          					success:function(result){
          						if(result.success){
          							//表示删除用户成功  重新分页查询用户信息
          							pageQuery(1);
          						}else{
          							layer.msg("类型删除失败", {time:2000, icon:5, shift:6}, function(){  	   	   
                	    	    	   });
          						}
          					}
          				});
          				
        			    layer.close(cindex);
        			}, function(cindex){
        			    layer.close(cindex);
        			});
      			}
      		}
        </script>
  </body>
</html>
