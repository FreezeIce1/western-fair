<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html lang="en">
<head>
	<meta charset="UTF-8">
	<title>Document</title>
	<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/list_page.css">
</head>
<body>
	<jsp:include page="/jsp/about/header.jsp"></jsp:include>

	<div class="slide_pic">
		<img src="../img/slidepic.png">
		<span class="word">友情链接</span>
	</div>
	
	<!-- 主体 -->
	<div class="container">
		<div class="left_nav">
			<ul class="left_nav_list">
				<li class="active"><a href="">友情链接</a></li>

			</ul>
		</div>
		<div class="right_content">
			<div class="location">
				<span>您现在的位置</span>:<span>友情链接</span>
			</div>
			<div class="content">
				<h3>友情链接</h3>
				<span class="circle"></span><span class="line"></span>
				<div class="context context_partner">
					<ul class="flex" id="linkData">
						
					</ul>
				</div>
			</div>
		</div>
	</div>


	<jsp:include page="about/foot.jsp"></jsp:include>

</body>
</html>