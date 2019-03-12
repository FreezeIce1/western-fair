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
	<jsp:include page="about/header.jsp"></jsp:include>

	<div class="slide_pic">
		<img src="../img/slidepic.png">
		<span class="word">联系我们</span>
	</div>
	
	<!-- 主体 -->
	<div class="container">
		<div class="left_nav">
			<ul class="left_nav_list">
				<li class="active"><a href="">联系我们</a></li>
				
			</ul>
		</div>
		<div class="right_content">
			<div class="location">
				<span>您现在的位置</span>:<span>联系我们</span>
			</div>
			<div class="content">
				<h3>联系我们</h3>
				<span class="circle"></span><span class="line"></span>
				<div class="context_connect flex">
					<div>
						<div class="blue_word">
							<p>展位预定</p>
							<p>BOOTH RESERVATION</p>
						</div>
						<div class="tele">
							<p><span>廖老师</span> Ms.Liao</p>
							<p>028-<span>86947026</span></p>
							<p>liaolisi@wcif.cn</p>
						</div>
					</div>
					<div>
						<div class="blue_word flex flex-between">
							<div>
								<p>论坛活动</p>
								<p>FORUM & EVENTS</p>
							</div>
							<div>
								<p>参会参观</p>
								<p>EXHIBITORS & VISITORS</p>
							</div>
						</div>
						<div class="tele center">
							<p><span>陶老师 </span>Mr.Tao</p>
							<p>028-<span>86210204</span></p>
							<p>taohai@wcif.cn</p>
						</div>
					</div>

					<div>
						<div class="blue_word">
							<p>媒体合作</p>
							<p>MEDIA PARTNERS</p>
						</div>
						<div class="tele">
							<p><span>杨老师</span> Ms.Yang</p>
							<p>028-<span>84546613</span></p>
							<p>yangluxi@wcif.cn</p>
						</div>
					</div>
				</div>
			</div>
		</div>
	</div>


	<jsp:include page="about/foot.jsp"></jsp:include>

</body>
</html>