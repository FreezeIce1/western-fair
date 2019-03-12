<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html lang="en">
<head>
	<meta charset="UTF-8">
	<title>Document</title>
	<link rel="stylesheet" type="text/css" href="css/index.css">
</head>
<body>
	<div class="nav">
			<img src="${pageContext.request.contextPath}/img/logo.png">
			<div class="nav_right">
				<div class="add">
					<span class="collec">加入收藏</span>
					<span>中</span>|<span>英</span>
				</div>
				<ul class="nav_list">
					<li><a href="${pageContext.request.contextPath}/index.jsp">首页</a></li>
					<li><a href="${pageContext.request.contextPath}/jsp/about/introduce.jsp">关于教博会</a></li>
					<li><a href="${pageContext.request.contextPath}/jsp/connect.jsp">活动日程</a></li>
					<li><a href="${pageContext.request.contextPath}/jsp/exposition_guide.jsp">参展指南</a></li>
					<li><a href="">观众指南</a></li>
					<li><a href="${pageContext.request.contextPath}/news/getNewsData?id=1">新闻中心</a></li>
					<li><a href="${pageContext.request.contextPath}/jsp/partner.jsp">合作伙伴</a></li>
					<li><a href="">服务中心</a></li>
					<li><a href="${pageContext.request.contextPath}/jsp/connect.jsp">联系我们</a></li>
				</ul>
			</div>
		</div>
</body>
</html>