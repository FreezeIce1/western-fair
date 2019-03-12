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
				<li class="active"><a href="">参展优势</a></li>
				<li><a href="">参展提示</a></li>
				<li><a href="">参展费用</a></li>
				<li><a href="">展位申请</a></li>
				<li><a href="">展商服务</a></li>
				<li><a href="">推荐展商</a></li>

			</ul>
		</div>
		<div class="right_content">
			<div class="location">
				<span>您现在的位置</span>:<span>关于教博会</span>><span>参展提示</span>
			</div>
			<div class="content">
				<h4>参展类别</h4>
				<span class="circle"></span><span class="line"></span>
				<div class="context_guide flex">
					<div class="context_guide_content">
						<img src="img/zhijiaozhanqu.png" alt="">
						<p>应用型本科院校、高等职业技术院校、职中及职业教育集团、协会、联盟等；教育培训及辅导机构；语言培训；技能培训；艺术培训；管理培训；IT培训；公务员培训机构；成人及继续教育机构；资格认证机构；证书颁发机构。</p>
						 <span>职教展区</span>
					</div>
					<div class="context_guide_content">
						<img src="img/yidaiyilu.png" alt="">
						<p>一带一路蓉欧沿线国家院校；国内普通高等院校；各国驻华使馆、驻川渝领事馆、海外政府教育机构、协会等；国际合作项目；海外院校；海外语言培训学校和机构；留学服务机构（中介机构、游学、银行、航空公司、保险）；留学教育贷款、保险；海归同学项目展示等。</p>
						 <span>职教展区</span> 
					</div>
					<div class="context_guide_content">
						<img src="img/zhihuijiaoyu.png" alt="">
						<p>教育信息化技术及装备：互联网+教育、教育类VR、STEM教育、慕课、电子课堂、创客教育、教育云、教育大数据、智慧校园及平安校园建设、在线教育及远程教育、教学机器人、益智玩具、微课、3D打印设备、各类教育装备等。</p>
						 <span>职教展区</span>
					</div>
				</div>
			</div>
			<div class="content">
				<h4>参展规则</h4>
				<span class="circle"></span><span class="line"></span>
				<div class="context_guide flex">
					
				</div>
			</div>
			<div class="content">
				<h4>参展程序流程</h4>
				<span class="circle"></span><span class="line"></span>
				<div class="context_guide flex">
					
				</div>
			</div>
		</div>
	</div>


	<jsp:include page="about/foot.jsp"></jsp:include>

</body>
</html>