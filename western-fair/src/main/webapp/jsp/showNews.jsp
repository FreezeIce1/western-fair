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
	<div class="article" >
		
		<div class="right_content">
			<div class="location">
				<input id="newsTypeId" type="hidden" value="${newsType.id }">
				<span>您现在的位置</span>:<span><a href="${pageContext.request.contextPath}/news/getNewsData?id=1">新闻中心</a></span>><span><a href="${pageContext.request.contextPath}/news/getNewsData?id=${newsType.id }">${newsType.name }</a></span>
			</div>
			<div class="content">
				<h4>${newsType.name }</h4>
				<span class="circle"></span><span class="line"></span>
				<div class="content">
				<h3 align="center">${news.title }</h3>
				<span class="circle"></span><span class="line"></span>
				<br>
				<div class="context_schedule">
				
				<table>
				<tr>
				<td><span class="context">来源：${news.source }</span></td>
				<td><span class="context">作者：${news.author }</span></td>
				<td><span class="context">日期:${news.updatetime }</span></td>
				</tr>
				</table>
				</div>
					
				
				<br>
				<span class="circle"></span><span class="line"></span>
				<div class="context">
					<p>${news.content }</p>
				</div>
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
	

	</script>

</body>
</html>