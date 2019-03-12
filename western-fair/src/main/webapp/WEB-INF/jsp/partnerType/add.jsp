<%@ page pageEncoding="UTF-8" %>
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
	<link rel="stylesheet" href="${pageContext.request.contextPath}/css/doc.min.css">
	<style>
	.tree li {
        list-style-type: none;
		cursor:pointer;
	}
	</style>
  </head>

  <body>

    <nav class="navbar navbar-inverse navbar-fixed-top" role="navigation">
      <div class="container-fluid">
        <div class="navbar-header">
            <div><a class="navbar-brand" style="font-size:32px;" href="user.html">合作伙伴</a></div>
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
				<ol class="breadcrumb">
				  <li><a href="#">首页</a></li>
				  <li><a href="#">数据列表</a></li>
				  <li class="active">新增</li>
				</ol>
			<div class="panel panel-default">
              <div class="panel-heading">表单数据<div style="float:right;cursor:pointer;" data-toggle="modal" data-target="#myModal"><i class="glyphicon glyphicon-question-sign"></i></div></div>
			  <div class="panel-body">
				<form role="form" id="partnerTypeForm" >
				  <div class="form-group">
					<label for="exampleInputPassword1">类别名称</label>
					<input type="text" class="form-control" id="typename" placeholder="请输入类别名称">
				  </div>
				 
				  <button id="insertBtn" type="button" class="btn btn-success"><i class="glyphicon glyphicon-plus"></i> 新增</button>
				  <button type="button" id="resetBtn"  class="btn btn-danger"><i class="glyphicon glyphicon-refresh"></i> 重置</button>
				</form>
			  </div>
			</div>
        </div>
      </div>
    </div>
	<div class="modal fade" id="myModal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
	  <div class="modal-dialog">
		<div class="modal-content">
		  <div class="modal-header">
			<button type="button" class="close" data-dismiss="modal"><span aria-hidden="true">&times;</span><span class="sr-only">Close</span></button>
			<h4 class="modal-title" id="myModalLabel">帮助</h4>
		  </div>
		  <div class="modal-body">
			<div class="bs-callout bs-callout-info">
				<h4>测试标题1</h4>
				<p>测试内容1，测试内容1，测试内容1，测试内容1，测试内容1，测试内容1</p>
			  </div>
			<div class="bs-callout bs-callout-info">
				<h4>测试标题2</h4>
				<p>测试内容2，测试内容2，测试内容2，测试内容2，测试内容2，测试内容2</p>
			  </div>
		  </div>
		  <!--
		  <div class="modal-footer">
			<button type="button" class="btn btn-default" data-dismiss="modal">Close</button>
			<button type="button" class="btn btn-primary">Save changes</button>
		  </div>
		  -->
		</div>
	  </div>
	</div>
    <script src="${pageContext.request.contextPath}/jquery/jquery-2.1.1.min.js"></script>
    <script src="${pageContext.request.contextPath}/bootstrap/js/bootstrap.min.js"></script>
	<script src="${pageContext.request.contextPath}/script/docs.min.js"></script>
	<script src="${pageContext.request.contextPath}/layer/layer.js"></script>
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
            });
            $("#resetBtn").click(function(){
            	//reset方法是jquery对象不具有的  但dom对象是具有
            	//所以要将jquery对象转为dom对象  加个索引[0]就可以了
            	$("#partnerTypeForm")[0].reset();
            });
            $("#insertBtn").click(function(){
            	var typename = $("#typename").val();
            	
            	if(typename == ""){
            		layer.msg("类别名称不能为空，请输入", {time:2000, icon:5, shift:6}, function(){  	   	   
	    	    	   });
            		
            		//用户名和邮箱的验证暂时省略
            		return;
            	}	
            	//异步提交新增的用户信息
            	$.ajax({
            		type : "POST",
            		url	 : "${pageContext.request.contextPath}/partnerType/insert",
            		data :{ 
            			typename  : $("#typename").val(),
        			},
            	
            		beforeSend : function(){
      					loadingIndex = layer.msg('处理中', {icon: 16});
      				},
      				success : function(result){
      	    		   layer.close(loadingIndex);
      	    		   if(result.success){
      	    			 layer.msg("信息保存成功", {time:2000, icon:6}, function(){ 
      	    				 window.location.href = "${pageContext.request.contextPath}/partnerType/index";
	    	    	   	 });  
      	    			 
      	    		   }else{
      	    			 layer.msg("信息保存失败", {time:2000, icon:5, shift:6}, function(){  	   	   
  	    	    	   		});  
      	    		   }
      	    		}
            	});
            	
            });

        	function checkLoginAcct(){
        		var loginacct = $("#loginacct").val();
        		//异步验证用户登录名是否存在
        		$.ajax({
            		type : "POST",
            		url	 : "${pageContext.request.contextPath}/user/checkLoginAcct",
            		data :{
            			"loginacct" : loginacct,            			
            		},  		
      				success : function(result){ 
      	    		   if(result.data == "exist"){
      	    			 layer.msg("该登录账号已经存在", {time:2000, icon:5, shift:6}, function(){  	   	   
  	    	    	   		});  
      	    		   }
      	    		}
            	});
        	}
        </script>
  </body>
</html>
