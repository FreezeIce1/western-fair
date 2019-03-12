<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html lang="en">
<head>
<meta charset="UTF-8">
<title>Document</title>
<link rel="stylesheet" type="text/css"
	href="${pageContext.request.contextPath}/css/list_page.css">
		<link rel="stylesheet" href="${pageContext.request.contextPath}/bootstrap/css/bootstrap.min.css">
	
	
</head>
<body>
	<jsp:include page="/jsp/about/header.jsp"></jsp:include>

	<div class="slide_pic">
		<img src="../img/slidepic.png"> <span class="word">合作伙伴</span>
	</div>

	<!-- 主体 -->
	<div class="container">
		<div class="left_nav">
			<ul class="left_nav_list" id="leftList">
			 <c:forEach items="${partnerTypes}" var="partnerType">
			 <c:choose>
			  	 <c:when test="${partnerType.typeid == requestScope.partnerType.typeid}"> <!--如果 --> 
				 	<li class="active">   
			 	 </c:when>      
    	 		 <c:otherwise>  <!--否则 -->    
					<li >
  				 </c:otherwise> 
			</c:choose>
			<a href="${pageContext.request.contextPath}/partnerType/getPartnerData?typeid=${partnerType.typeid}">${partnerType.typename}</a></li>
					
					 </c:forEach>
			<!-- 	<li class="active"><a href="">合作伙伴</a></li>
				<li><a href="">历届展商</a></li>
				<li><a href="">合作媒体</a></li> -->
			</ul>
		</div>
		<div class="right_content">
			<div class="location">
			<input type="hidden" id="id" value="${requestScope.partnerType.typeid}">
				<span>您现在的位置</span>:<span> ${requestScope.partnerType.typename}</span>
			</div>
			<div class="content">
				<h3>${requestScope.partnerType.typename}</h3>
				<span class="circle"></span><span class="line"></span>
				<div class="context context_partner" >			
					<ul class="flex" id="partnerData">			
					</ul>		
				</div>
				<div style=" text-align: center">
				<ul class="pagination"  id="partnerPagination">
								
				</ul>
				</div>
			</div>
		</div>
	</div>


	<jsp:include page="about/foot.jsp"></jsp:include>

</body>
 <script type="text/javascript">
	$(function () {
		 pageQuery(1);
	});
	
    //分页查询      		
	function pageQuery(pageno){
		var typeid=$("#id").val();
		var jsonData = {"pageno" : pageno,"pagesize" : 12,"queryText" : '',"partnerType":typeid};
		
		//发送异步请求来进行查询
		$.ajax({ 
			type : "POST",
			url  : "${pageContext.request.contextPath}/partner/pageQuery",
			data : jsonData,
			success : function(result){
			
	  		   //服务器后端传递的数据是以json格式返回的{success:""}  result代表服务器返回的数据
	  		   if(result.success){
	  			  //用户信息分页查询成功  局部刷新页面数据
	  			 
	  			  var partnerContent = "";
	  				var pageContent = "";
	  			   var partnerPage = result.data;
      	    	   var partnersList = partnerPage.datas;

      	    	  //循环遍历partners 即当前页要显示的数据
	    			  //partners代表当前要遍历的数据  另一个是回调函数user代表当前遍历到的对象
	    			  $.each(partnersList,function(i,partner){
	    				
   	             // <img src="${pageContext.request.contextPath}'+"//"+partner.image+'" width="80px" height="80px"/>   
   	             partnerContent += '  <li style="width:195px;height:108px; float:left;list-style-type:none"><a href="'+partner.url+'"><img src="${pageContext.request.contextPath}'+"//"+partner.image+'" title="'+partner.name+'"/></a> </li>';
   	       		 
	    			  });
	    			
	    			  if(pageno > 1){
	    				  pageContent += '<li><a onclick="pageQuery('+(pageno-1)+');" href="#">上一页</a></li>';
	    			  }else if(pageno == 1){
	    				  pageContent += '<li class="disabled"><a href="#">上一页</a></li>';
	    			  }
	    			  
	    			  for(var i=1; i<=partnerPage.totalno;i++){
	    				  if(pageno == i){
	    					pageContent += '<li class="active"><a href="#">'+i+'</a></li>';
	    				  }else{
	    					pageContent += '<li><a href="#" onclick="pageQuery('+i+');">'+i+'</a></li>';
	    				  }
	    				  
	    			  }
	    			  
	    			  if(pageno < partnerPage.totalno){
	    				  pageContent += '<li><a onclick="pageQuery('+(pageno+1)+');" href="#">下一页</a></li>';
	    			  }else if(pageno == partnerPage.totalno){
	    				  pageContent += '<li class="disabled"><a href="#">下一页</a></li>';
	    			  }
	    			  
	    			   $("#partnerData").html(partnerContent);
	    			   $(".pagination").html(pageContent);
	    		   }
	    	   }
				
			});

	}
    //查询类型
    //
</script> 
</html>