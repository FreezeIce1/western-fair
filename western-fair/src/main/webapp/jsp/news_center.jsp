<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html lang="en">
<head>
	<meta charset="UTF-8">
	<title>Document</title>
	<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/list_page.css">
	<link rel="stylesheet" href="${pageContext.request.contextPath}/bootstrap/css/bootstrap.min.css">
</head>
<body>
	<jsp:include page="about/header.jsp"></jsp:include>

	<div class="slide_pic">
		<img src="../img/slidepic.png">
		<span class="word">新闻中心</span>
	</div>
	
	<!-- 主体 -->
	<div class="container">
		<div class="left_nav">
			<ul class="left_nav_list">
				<c:forEach items="${typeList}" var="typeList">
				<c:choose>
			  	 <c:when test="${typeList.id == newsType.id}"> <!--如果 --> 
				 	<li class="active">   
			 	 </c:when>      
    	 		 <c:otherwise>  <!--否则 -->    
					<li >
  				 </c:otherwise> 
				</c:choose>
					<a href="${pageContext.request.contextPath}/news/getNewsData?id=${typeList.id}">${typeList.name}</a></li>
				</c:forEach>
			</ul>
		</div>
		<div class="right_content">
			<div class="location">
				<input id="newsTypeId" type="hidden" value="${newsType.id }">
				<span>您现在的位置</span>:<span>新闻中心</span>><span>${newsType.name }</span>
			</div>
			<div class="content">
				<h4>${newsType.name }</h4>
				<span class="circle"></span><span class="line"></span>
				<div class="context_news_center">
					<ul id="newsData">
					</ul>
				</div>
				
				<div style=" text-align: center">
					<ul class="pagination">
					</ul>
				</div>
				
			</div>
		</div>
	</div>


	<jsp:include page="about/foot.jsp"></jsp:include>
	
	
	<script src="${pageContext.request.contextPath}/jquery/jquery-2.1.1.min.js"></script>
    <script src="${pageContext.request.contextPath}/bootstrap/js/bootstrap.min.js"></script>
	<script src="${pageContext.request.contextPath}/script/docs.min.js"></script>
	<script src="${pageContext.request.contextPath}/layer/layer.js"></script>
	<script type="text/javascript">
	var likeflg = false;
    
	$(function () {
		 pageQuery(1);
	});
	    
	   
    
    // 分页查询
    function pageQuery( pageno ) {
    	var newsTypeId=$("#newsTypeId").val();
    	var loadingIndex = null;
    	
    	var jsonData = {"pageno" : pageno, "pagesize" : 10, "newsType" : newsTypeId};
    	if ( likeflg == true ) {
    		jsonData.queryText = $("#queryText").val();
    		jsonData.newsType = $("#newsType").val();
    	}
    	
    	$.ajax({
    		type : "POST",
    		url  : "${pageContext.request.contextPath}/news/pageQuery",
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
    				
    				var newsPage = result.data;
    				var newsList = newsPage.datas;
    				
    				$.each(newsList, function(i, news){
      	                
      	              tableContent += '<li><a href="${pageContext.request.contextPath}/news/showNews?newsid='+news.newsid+'"><b></b><span>'+news.title+'</span><span class="fr">'+news.updatetime+'</span></a></li>';
      	                
    				});
    				
    				if ( pageno > 1 ) {
    					pageContent += '<li><a href="#" onclick="pageQuery('+(pageno-1)+')">上一页</a></li>';
    				}
    				
    				for ( var i = 1; i <= newsPage.totalno; i++ ) {
    					if ( i == pageno ) {
    						pageContent += '<li class="active"><a  href="#">'+i+'</a></li>';
    					} else {
    						pageContent += '<li ><a href="#" onclick="pageQuery('+i+')">'+i+'</a></li>';
    					}
    				}
    				
    				if ( pageno < newsPage.totalno ) {
    					pageContent += '<li><a href="#" onclick="pageQuery('+(pageno+1)+')">下一页</a></li>';
    				}

    				$("#newsData").html(tableContent);
    				$(".pagination").html(pageContent);
    			} else {
                    layer.msg("新闻活动分页查询失败", {time:2000, icon:5, shift:6}, function(){
                    	
                    });
    			}
    		}
    	});
    }

    

	</script>

</body>
</html>