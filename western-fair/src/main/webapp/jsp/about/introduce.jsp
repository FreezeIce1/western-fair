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
	
	<jsp:include page="header.jsp"></jsp:include>
	<div class="slide_pic">
		<img src="${pageContext.request.contextPath}/img/slidepic.png">
		<span class="word">关于教博会</span>
	</div>
	
	<!-- 主体 -->
	<div class="container">
		<div class="left_nav">
			<ul class="left_nav_list">
				<li class="active"><a href="">展会介绍</a></li>
				<li><a href="">组织机构</a></li>
				<li><a href="">展品范围</a></li>
				<li><a href="">宣传推广</a></li>
				<li><a href="">精彩回顾</a></li>
			</ul>
		</div>
		<div class="right_content">
			<div class="location">
				<span>您现在的位置</span>:<span>关于教博会</span>><span>展会介绍</span>
			</div>
			<div class="content">
				<h3>展会介绍</h3>
				<span class="circle"></span><span class="line"></span>
				<div class="context">
					<p>“第三届中国西部国际教育博览会暨第二届四川职业教育国际博览会”秉承“建设教育强国是中华名族复兴的基础工程，必须把教育事业放在优先位置，加快教育现代化，办好人民满意的教育”的教育方针，致力于打造中国西部地区规模大、活动丰富、产业链完善的教育行业盛会，已成为中国西部教育交流、发展、进步的重要平台。在职业教育方面，四川职业教育国际博览会本着优先发展教育事业，完善职业教育和培训体系，深化产教融合、校企合作，推动“一带一路”职业教育走出去的办展思路，将通过开展交流合作、精品展示、项目推荐、学术论坛、技能大赛等活动，搭建平台，扩大影响，从而提高职业教育的价值和曝光度，让博览会作为西部职业教育核心平台推动社会各方形成合力，让职业教育助推经济社会取得更大更好发展，同时为建设学习型社会，大力提高国民素质做出贡献。</p>
				</div>
			</div>
		</div>
	</div>


	<jsp:include page="foot.jsp"></jsp:include>

</body>
</html>