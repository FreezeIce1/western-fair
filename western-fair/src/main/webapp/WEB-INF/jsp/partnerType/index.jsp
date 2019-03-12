<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="zh-CN">
  <head>
    <meta charset="UTF-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <meta name="description" content="">
    <meta name="author" content="">

	<!-- 
		http://localhost:8080/atcrowdfunding-web/partner/index
		当前资源的访问路径是：http://localhost:8080/atcrowdfunding-web/partner
		相对路径是基准路径为参考的  基准路径就是当前资源的访问路径
	 -->
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
          <div><a class="navbar-brand" style="font-size:32px;" href="#">合作伙伴</a></div>
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
      <input id="queryText" class="form-control has-success" type="text" placeholder="请输入查询条件">
    </div>
  </div>
  <button id="queryBtn" type="button" class="btn btn-warning"><i class="glyphicon glyphicon-search"></i> 查询</button>
</form>
<button type="button" class="btn btn-danger"  onclick="deletePartnerTypes()" style="float:right;margin-left:10px;"><i class=" glyphicon glyphicon-remove"></i> 删除</button>
<button type="button" class="btn btn-primary" style="float:right;margin-left:10px;" onclick="window.location.href='${pageContext.request.contextPath}/partnerType/add'"><i class="glyphicon glyphicon-plus"></i> 新增</button>
<br>
 <hr style="clear:both;">
          <div class="table-responsive">
          	<form id="partnerTypeForm">
            <table class="table  table-bordered">
              <thead>
                <tr >
                  <th width="30">#</th>
				  <th width="30"><input type="checkbox" id="allSelBox"></th>
                  <th>id</th>
                  <th>类型名称</th>
                 <th>创建时间</th>
                 <th>修改时间</th>
                  <th width="100">操作</th>
                </tr>
              </thead>
             
              <tbody id="partnerTypeData" style="  text-align: center; vertical-align: middle; ">
              	
              </tbody>
             
			  <tfoot>
			     <tr>
				     <td colspan="9" align="center">
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
	<script type="text/javascript" src="${pageContext.request.contextPath}/plugins/zDialog/zDialog.js"></script>
    <script type="text/javascript" src="${pageContext.request.contextPath}/plugins/zDialog/zDrag.js"></script>
    <script type="text/javascript" src="${pageContext.request.contextPath}/plugins/zDialog/zProgress.js"></script>
    <script language="javascript" type="text/javascript" src="${pageContext.request.contextPath}/plugins/My97DatePicker/WdatePicker.js"></script>
        <script type="text/javascript">
        	//增加是否是模糊查询的标志
        	var likeflg = false;
        	//表示页面内容加载执行完成以后
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
			    	if( queryText == ""){
			    		//表示不是模糊查询
			    		likeflg = false;
			    	}else{
			    		//表示不是模糊查询
			    		likeflg = true;
			    	}
			    	
			    	pageQuery(1);
			    });
			    
			    $("#allSelBox").click(function(){
			    	//获取全选框的选中状态
			    	var flg = this.checked;
			    	
			    	
			    	$("#partnerTypeData :checkbox").each(function(){
			    		this.checked = flg;
			    	});
			    });
            });
            $("tbody .btn-success").click(function(){
                window.location.href = "assignRole.html";
            });
            $("tbody .btn-primary").click(function(){
                window.location.href = "edit.html";
            });
           
      		//分页查询      		
      		function pageQuery(pageno){
      			var loadingIndex = null;
      			
      			var jsonData = {"pageno" : pageno,"pagesize" : 10};
      			if(likeflg == true){
      				jsonData.queryText = $("#queryText").val();
      			}
      			//发送异步请求来进行查询
      			$.ajax({ 
      				type : "POST",
      				url  : "${pageContext.request.contextPath}/partnerType/pageQuery",
      				data : jsonData,
      				beforeSend : function(){
      					loadingIndex = layer.msg('处理中', {icon: 16});
      				},
      				success : function(result){
      	    		   layer.close(loadingIndex);
      	    		   //服务器后端传递的数据是以json格式返回的{success:""}  result代表服务器返回的数据
      	    		   if(result.success){
      	    			  //用户信息分页查询成功  局部刷新页面数据
      	    			 
      	    			  var tableContent = "";
      	    			  var pageContent = "";
      	    			  
      	    			  var partnerTypePage = result.data;
      	    			  var partnerTypes = partnerTypePage.datas;
      	    			  
      	    			  //循环遍历partners 即当前页要显示的数据
      	    			  //partners代表当前要遍历的数据  另一个是回调函数user代表当前遍历到的对象
      	    			  $.each(partnerTypes,function(i,partnerType){
      	    				tableContent += '<tr>';
	      	                tableContent += '  <td>'+(i+1)+'</td>';
	      					tableContent += '  <td><input type="checkbox" name="partnerTypeid" value="'+partnerType.typeid+'"></td>';
	      	                tableContent += '  <td>'+partnerType.typeid+'</td>'; 
	      	                tableContent += '  <td>'+partnerType.typename+'</td>';
	      	            	tableContent += '  <td>'+partnerType.typeaddtime+'</td>';
	      	           	if(partnerType.typeupdatetime!=null){
		      	          	tableContent += '  <td>'+partnerType.typeupdatetime+'</td>' ;
		      	          
	    					}else{
	    						tableContent += '  <td></td>' ;
	    		      	          
	    					}
	      	                tableContent += '  <td>';
	      					
	      					tableContent += '      <button type="button"  onclick="goUpdatePage('+partnerType.typeid+')" class="btn btn-primary btn-xs"><i class=" glyphicon glyphicon-pencil"></i></button>';
	      		 			tableContent += '	  <button type="button"  onclick="deletePartnerType('+partnerType.typeid+',\''+partnerType.typename+'\')"  class="btn btne-danger btn-xs"><i class=" glyphicon glyphicon-remove"></i></button>';
     					tableContent += '  </td>';
	      	                tableContent += '</tr>';
      	    			  });
      	    			  
      	    			  if(pageno > 1){
      	    				  pageContent += '<li><a onclick="pageQuery('+(pageno-1)+');" href="#">上一页</a></li>';
      	    			  }else if(pageno == 1){
      	    				  pageContent += '<li class="disabled"><a href="#">上一页</a></li>';
      	    			  }
      	    			  
      	    			  for(var i=1; i<=partnerTypePage.totalno;i++){
      	    				  if(pageno == i){
      	    					pageContent += '<li class="active"><a href="#">'+i+'</a></li>';
      	    				  }else{
      	    					pageContent += '<li><a href="#" onclick="pageQuery('+i+');">'+i+'</a></li>';
      	    				  }
      	    				  
      	    			  }
      	    			  
      	    			  if(pageno < partnerTypePage.totalno){
      	    				  pageContent += '<li><a onclick="pageQuery('+(pageno+1)+');" href="#">下一页</a></li>';
      	    			  }else if(pageno == partnerTypePage.totalno){
      	    				  pageContent += '<li class="disabled"><a href="#">下一页</a></li>';
      	    			  }
      	    			  
      	    			   $("#partnerTypeData").html(tableContent);
      	    			   $(".pagination").html(pageContent);
      	    		   }else{
      	    			   
      	    			   layer.msg("类别信息分页查询失败", {time:2000, icon:5, shift:6}, function(){  	   	   
      	    	    	   });
      	    		   }
      	    	   }
      				
      			});
      		}
      		
      			function goUpdatePage(typeid){
      			window.location.href="${pageContext.request.contextPath}/partnerType/edit?typeid="+typeid;
      		}
      		
      		
      	 	function deletePartnerType(typeid,typename){
      			layer.confirm("删除类别信息【"+typename+"】，是否继续",  {icon: 3, title:'提示'}, function(cindex){
      				//点击确定按钮要调用的回调函数
      				//删除用户信息
      				//使用ajax技术进行删除
      				$.ajax({
      					type : "POST",
      					url : "${pageContext.request.contextPath}/partnerType/delete",
      					data : {"typeid" : typeid},
      					dataType:"json",
      					success:function(data){
      						
      						if(data.success=="true"){
      							//表示删除用户成功  重新分页查询用户信息
      							pageQuery(1);
      						}else if(data.success="no"){
      							layer.msg("该类别含有关联数据不能删除", {time:2000, icon:5, shift:6}, function(){  	   	   
            	    	    	   });
      						}else if(data.success="false"){
      						
      							layer.msg("删除失败", {time:2000, icon:5, shift:6}, function(){  	   	   
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
      		function deletePartnerTypes(){
      			var boxes = $("#partnerTypeData :checked");
      			
      			if( boxes.length == 0){
      				layer.msg("请选择要删除的类别信息", {time:2000, icon:5, shift:6}, function(){  	   	   
	    	    	   });
      			}else{
      				layer.confirm("删除选择的类别信息，是否继续",  {icon: 3, title:'提示'}, function(cindex){
						//删除选择的用户信息
          				$.ajax({
          					type : "POST", 
          					url : "${pageContext.request.contextPath}/partnerType/deletePartnerTypes",
          					data : $("#partnerTypeForm").serialize(),  //序列化表单的内容
          					dataType:"json",
          					success:function(data){
          					
          						if(data.success=="true"){
          							//表示删除用户成功  重新分页查询用户信息
          							pageQuery(1);
          						}else if(data.success="no"){
          						
          							layer.msg("该类别含有关联数据不能删除", {time:2000, icon:5, shift:6}, function(){  	   	   
                	    	    	   });
          						}else if(data.success="false"){
          						
          							layer.msg("删除失败", {time:2000, icon:5, shift:6}, function(){  	   	   
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