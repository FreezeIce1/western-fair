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
            <div><a class="navbar-brand" style="font-size:32px;" href="user.html">用户维护</a></div>
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
				  <li class="active">修改</li>
				</ol>
			<div class="panel panel-default">
              <div class="panel-heading">表单数据<div style="float:right;cursor:pointer;" data-toggle="modal" data-target="#myModal"><i class="glyphicon glyphicon-question-sign"></i></div></div>
			  <div class="panel-body">
				<form id="partnerForm" role="form" enctype="multipart/form-data">
				<input type="hidden"class="form-control" id="id" value="${partner.id}"  >
				  <div class="form-group">
					<label for="exampleInputPassword1">合作对象名称</label>
					<input type="text" class="form-control" id="name" value="${partner.name}" placeholder="请输入合作对象名称">
				  </div>
				  <div class="form-group">
					<label for="exampleInputPassword1">图片地址</label><br/>
						<img src="${pageContext.request.contextPath}/${partner.image}" width="80px" height="80px"/>
				
					  	 <input id="file" type="file" name="file"  ><br/>        
				  </div>
				  <div class="form-group">
					<label for="exampleInputPassword1">网站地址</label>
					<input type="text" class="form-control" id="url" value="${partner.url}" placeholder="请输入链接地址">
				  </div>
				  <div class="form-group">
					<label for="exampleInputEmail1">合作类型</label>
					
					 <select class="form-control" id="type">
					 <c:forEach items="${partnerType}" var="partnerType">
					 	<option value="${partnerType.typeid }"   <c:if test="${partnerType.typeid==typeid}">
                selected='selected'</c:if>>${partnerType.typename }</option>
					 </c:forEach>
					</select>
				</div> 
					<%-- <div style="display:inline-block;margin-right:20px;">				
					    <input type="radio" name="type" value="1" <c:if test="${partner.type==1}">checked</c:if> >合作伙伴	  
					</div>
					<div style="display:inline-block;">									    
						<input type="radio" name="type" value="2" <c:if test="${partner.type==2}">checked</c:if> >历届展商	   
					</div>	
					<div style="display:inline-block;">									    
						<input type="radio" name="type" value="3" <c:if test="${partner.type==3}">checked</c:if> >媒体	   
					</div>	
				  </div> --%>
				  <button id="updateBtn" type="button" class="btn btn-success"><i class="glyphicon glyphicon-pencil"></i> 修改</button>
				  <button id="resetBtn" type="button" class="btn btn-danger"><i class="glyphicon glyphicon-refresh"></i> 重置</button>
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
            	$("#partnerForm")[0].reset();
            });
            
            $("#updateBtn").click(function(){
            //	alert("type:"+$("input[name='type']:checked").val() );
           		var id=$("#id").val();
            	var name = $("#name").val();
            	var file=$("input[name='file']").val();
            	var url=$("#url").val();
            	var type=$("#type").val();
            	  var patn = /\.jpg$|\.jpeg$|\.png$|\.gif$/i;
            	if(name == ""){
            		layer.msg("名称不能为空，请输入", {time:2000, icon:5, shift:6}, function(){  	   	   
	    	    	   });
            		
            		return;
            	}
            	if(file==""){
            		layer.msg("请选择要上传的图片", {time:2000, icon:5, shift:6}, function(){  	   	   
	    	   });
         		return;
            	}else if(!patn.test(file)){
            		layer.msg("请选择格式为 jpg,jpeg,png,gif的图片", {time:2000, icon:5, shift:6}, function(){  	   	   
     	    	   });
            		return;
 	    	   }
            	if(url==""){
            		layer.msg("url不能为空，请输入", {time:2000, icon:5, shift:6}, function(){  	   	   
	    	    	   });
         		
         		return;
            	}
            	var form=new FormData($("#partnerForm")[0]);//拿到表单对象
            	form.append("id",id);
            	form.append("name",name);
            	form.append("url",url);form.append("type",type);
            	//异步提交新增的用户信息
            	$.ajax({
            		type : "POST",
            		url	 : "${pageContext.request.contextPath}/partner/update",
            		data :form,
            		processData:false,
                    contentType:false,
            		beforeSend : function(){
      					loadingIndex = layer.msg('处理中', {icon: 16});
      				},
      				success : function(result){
      	    		   layer.close(loadingIndex);
      	    		   if(result.success){
      	    			 layer.msg("信息修改成功", {time:2000, icon:6}, function(){ 
      	    				 window.location.href = "${pageContext.request.contextPath}/partner/index";
	    	    	   	 });  
      	    			 
      	    		   }else{
      	    			 layer.msg("信息修改失败", {time:2000, icon:5, shift:6}, function(){  	   	   
  	    	    	   		});  
      	    		   }
      	    		}
            	});
            });
        </script>
  </body>
</html>
