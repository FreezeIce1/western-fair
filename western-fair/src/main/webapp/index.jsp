<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
	<meta charset="UTF-8">
	<title>Document</title>
	
	<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/index.css">
	<script type="text/javascript" src="${pageContext.request.contextPath}/js/jquery.js"></script>
	<script src="${pageContext.request.contextPath}/jquery/jquery-2.1.1.min.js"></script>
	
	<script type="text/javascript" src="${pageContext.request.contextPath}/js/index.js"></script>
	<script type="text/javascript" src="${pageContext.request.contextPath}/js/jquery.SuperSlide.2.1.1.js"></script>
	
	<script src="http://cdn.static.runoob.com/libs/angular.js/1.4.6/angular.min.js"></script>
</head>
<body>
	<!-- 通用css/ -->
	<!-- 导航栏 -->
		<div class="nav">
			<img src="img/logo.png">
			<div class="nav_right">
				<div class="add">
					<span class="collec">加入收藏</span>
					<span>中</span>|<span>英</span>
				</div>
				<ul class="header_nav">
					<li class="active_nav"><a href="">首页</a></li>
					<li><a href="about/introduce.html">关于教博会</a>
						<ul class="dropdown_list">
							<li><a href="">展会介绍</a></li>
							<li><a href="">组织机构</a></li>
							<li><a href="">展品范围</a></li>
							<li><a href="">宣传推广</a></li>
							<li><a href="">精彩回顾</a></li>
						</ul>
					</li>

					<li><a href="schedule.html">活动日程</a>
						<ul class="dropdown_list">
							<li><a href="">活动一览表</a></li>
							<li><a href="">同期活动</a></li>
							
						</ul></li>
					<li><a href="exposition_guide.html">参展指南</a>
						<ul class="dropdown_list">
							<li><a href="">参展优势</a></li>
							<li><a href="">参展提示</a></li>
							<li><a href="">参展费用</a></li>
							<li><a href="">展位申请</a></li>
							<li><a href="">展商服务</a></li>
							<li><a href="">推荐展商</a></li>
						</ul>
					</li>
					<li><a href="">观众指南</a></li>
					<li><a href="${pageContext.request.contextPath}/news/getNewsData?id=1">新闻中心</a>
						<ul class="dropdown_list">
							<li><a href="">展会新闻</a></li>
							<li><a href="">行业资讯</a></li>
							<li><a href="">通知公告</a></li>
						</ul>
					</li>
					<li><a href="partner.html">合作伙伴</a></li>
					<li><a href="">服务中心</a></li>
					<li><a href="connect.html">联系我们</a></li>
				</ul>
			</div>
		</div>
	<!-- 大轮播图 -->
	<div class="slide_pic">
		<div class="hd">
			<ul>
				<li></li>
				<li></li>
				<li></li>
			</ul>
		</div>
		<div class="bd">
			<ul>
				<li><a href=""></a></li>
				<li><a href=""></a></li>
				<li><a href=""></a></li>
			</ul>
		</div>
	</div>
	<script type="text/javascript">
		$(".slide_pic").slide({ mainCell:".bd ul", titCell:".hd ul", effect:"fold", autoPlay:true, autoPage:"<li><a></a></li>", trigger:"click"});
	</script>
		

		<!-- 展会介绍 -->
		<div class="introduce">
			<div class="introduce_left">
				<!-- <div class="introduce_pic">
					<img src="img/org_logo.png" class="introduce_bg">
					<img src="img/zhanhuijieshao.png" class="introduce_zhanhuijieshao">
				</div>
				<article class="introduce_words">
					<h3>第三届中国西部国际教育博览会暨第二届四川职业教育国际博览会</h3>
					<p>建设教育强国是中华民族复兴的基础工程，必须把教育事业放在优先位置，加快教育现代化，办好人民满意的教育。</p>
					<p>致力于打造中国西部地区规模大、活动丰富、产业链完善的教育行业盛会，已成为中国西部教育交流、发展、进步的重要平台。</p>
					<p>提高职业教育的价值和曝光度，让博览会作为西部职业教育核心平台推动社会各方形成合力,让现代教育助推经济社会取得更大更好发展，同时为建设学习型社会，大力提高国民素质作出贡献。</p>
				</article> 
					 -->
				<div class="header_news" id="newsData">
					<!-- <h2>新闻头条：对“孩子必须出趟国”的攀比应保持清醒</h2>
					<p>没到暑假，舆论场上总是流水的新闻热点，铁打的教育话题.... <a href="">[更多]</a></p> -->
				</div>
				
				<!-- 头条新闻 -->
				<script type="text/javascript">
				$(function() {
					//发送异步请求来进行查询
					$.ajax({ 
						type : "POST",
						url  : "${pageContext.request.contextPath}/news/topNews",
						success : function(result){
				  			var tableContent = "";
		    				var newsList = result;
				  			
		      	                
			      	              tableContent += '<h2>'+result.title+'</h2>';
			      	              tableContent += '<p>'+result.content+' <a href="${pageContext.request.contextPath}/news/showNews?newsid='+result.newsid+'">[更多]</a></p>';
			      	     
				  			
				  			$("#newsData").html(tableContent);
				  		   
				  	   }
					});
				});
				</script>
				
				
				
				<div class="news_list" id="lastedNews">
				
				</div>
				
				<!-- 头条新闻 -->
				<script type="text/javascript">
				$(function() {
					//发送异步请求来进行查询
					$.ajax({ 
						type : "POST",
						url  : "${pageContext.request.contextPath}/news/lastedNews",
						success : function(result){
				  			var tableContent = "";
		    				var newsList = result;
		    				$.each(newsList,function(i,news){
		    					var year = news.updatetime.substring(0,4);
		    					var day = news.updatetime.substring(5,10)
				  				tableContent +='<div class="news_item"><div class="news_item_time flex column"><span class="news_year">'+year+'</span><span class="news_date">'+day+'</span></div>';
				  				tableContent +='<div class="news_item_content flex column"><p><a href="${pageContext.request.contextPath}/news/showNews?newsid='+news.newsid+'">'+news.title+'</a></p><span>'+news.newsType.name+'</span></div></div>';
		    				 });
				  			$("#lastedNews").html(tableContent);
				  		   
				  	   }
					});
				});
			</script>
				
				
				
				
			</div>
			
			<!-- 新闻获取 -->
		




		<div class="introduce_right">
				<!-- <div class="introduce_right_top">
					<p class="font_bold">距展会开幕还有</p>
					<p class="font_bold"><span class="num"></span>天</p>
				</div>
				<script type="text/javascript">
					var date1 = new Date();
					var date2 = new Date('2018-9-20');
					var date = (date2.getTime() - date1.getTime()) / (24 * 60 * 60 * 1000);
					
					$(".num").text(parseInt(date));
				</script> -->
				<div class="introduce_right_top">
					<div style="padding: 25px 20px 20px 20px">
						<div class="flex"><i class="exhibitionaddress"></i><p class="font_bold" style="margin:4px">展会地址：</p></div>
						<p style="margin:0;text-align: left;padding-left: 35px">成都世纪城新国际会展中心</p>
					</div>
					<div style="padding: 25px 0 30px 0;margin: 0 20px;border-bottom: 1px solid #eeeeee;">
						<div class="flex">
							<i class="exhibitontime"></i>
							<p class="font_bold" style="margin:4px">展会时间：</p>
						</div>
						<p style="margin:0;text-align: left;padding-left: 35px">9月20日-9月24日</p>
					</div>
					
					<input type="" name="" placeholder="请输入关键字"><span><img src="img/search_button.png"></span>
				</div>
				<div class="introduce_right_bot">
					<div class="enter zhanshang">
						<a href="">
							<img src="img/zhanshang.png" id="zhanshang1">
							<img src="img/zhanshang1.png" id="zhanshang2"> 
							<p id="zhanshang" style="display: inline-block;">我是展商</p>
						</a>
						
					</div>
					<div class="enter guanzhong">
						<a href="">
							<img src="img/guanzhong.png" id="guanzhong1">
							<img src="img/guanzhong1.png" id="guanzhong2"> 
							<p id="guanzhong" style="display: inline-block;">我是观众</p>
						</a>
					</div>
				</div>
			</div>
		</div>
		<!-- 入口 -->

		<!-- 适配性使用em实现弹性布局 -->
		<!-- <div class="enter">
			<div class="zhanshang enter_pic">
				<img src="img/ZSrukou.png" class="big_pic">
				<img src="img/zsbaoming.png" class="baoming">
			</div>
			<div class="guanzhong enter_pic">
				<img src="img/guanzhongrukou.png" class="big_pic">
				<img src="img/guanzhongbaoming.png" class="baoming">
			</div>
		</div> -->
		<!-- 展会范围 -->
		<!-- 展会动效暂未实现 -->
		<div class="range">
			<div class="menu_title">
				<div class="pos_relative mar10" style="position: relative;margin:0 auto"><p class="font-30">展会范围</p>
					<div class="title_bottom"></div>
				</div>
			</div>
			<div class="range_main">
				<div class="zhanhui">
					<img src="img/zhijiaozhanqu.png">
					<div class="zhanhui_p"><p><a href="">职教展区</a></p></div>
				</div>
				<div class="zhanhui">
					<img src="img/yidaiyilu.png">
					<div class="zhanhui_p"><p>
						<a href="">一带一路教育展区</a></p></div>
				</div>
				<div class="zhanhui">
					<img src="img/zhihuijiaoyu.png">
					<div class="zhanhui_p"><p><a href="">智慧教育展区</a></p></div>
				</div>
			</div>
		</div>
		
		<!-- 活动日程 -->
		<div class="schedule">
			<div class="menu_title">
				<div class="pos_relative mar10" style="position: relative;margin:0 auto"><p class="font-30">活动日程</p>
					<div class="title_bottom"></div>
				</div>
			</div>
			<div class="schedule_main" id="activityContent1">
				<!-- <div class="schedule_day">
					<div class="green_cicle"></div>
					<div class="day_title" id="activityContent1" onclick="setTab(1)">
						<span>
							
						</span> 
						<span id="cross"></span>
					</div>
				</div> -->
				<script type="text/javascript">
					$(function () {
						 pageQuery1(1);
					});
	
				    //分页查询      		
					function pageQuery1(pageno){
						var jsonData = {"pageno" : pageno,"pagesize" : 3,"queryText" : ''};
						
						//发送异步请求来进行查询
						$.ajax({ 
							type : "POST",
							url  : "${pageContext.request.contextPath}/activity/pageQuery",
							data : jsonData,
							success : function(result){
								
								
					  		   //服务器后端传递的数据是以json格式返回的{success:""}  result代表服务器返回的数据
					  		   if(result.success){
					  			  //用户信息分页查询成功  局部刷新页面数据 
					  			 var activityContent1 = "";
					  			// var activityContent2 = "";
					 
					  			  var activityPage = result.data;
					  			  var activities = activityPage.datas;
					  			  
					  			
					  			  
					  			  //循环遍历links 即当前页要显示的数据
					  			  //links代表当前要遍历的数据  另一个是回调函数link代表当前遍历到的对象
					  			  $.each(activities,function(i,activity){
					  				/* if(i< 1){
					  					//activityContent1 += "<p class='footer_content'><a style='color:white;' target='_blank'>"+activity.name+"</a></p>"
					  					activityContent1 += "<span>"+activity.name+"</span> "
					  					
					  					activityContent2 += "<ul><li><i class='icon blue'></i><span>时间："+activity.time+"</span></li><li><i class='icon orange'></i><span>地点："+activity.address+"</span></li><li><i class='icon red'></i><span>活动介绍："+activity.introduce+"</span></li></ul></div>"				
					  				} */
					  				
					  				activityContent1 += "<div class='schedule_day'>"
					  				+"<div class='green_cicle'></div>"
									+"<div class='day_title'  onclick='setTab(1)'>"
									+"<span>"
									+activity.name		
									+"</span>"
									+"<span id='cross'></span>"
									+"</div>"
									+"</div>"
								
									+"<div class='schedule_list' id='tag_"+i+"'>"
									+"<ul>"
									+"<li>"
									+"<i class='icon blue'></i>"
									+"<span>时间："+activity.time+"</span>"
									+"</li>"
									+"<li>"
									+"<i class='icon orange'></i>"
									+"<span>地点："+activity.address+"</span>"
									+"</li>"
									+"<li>"
									+"<i class='icon red'></i>"
									+"<span>活动介绍："+activity.introduce+"</span>"
									+"</li>"
									+"</ul>"
									+"</div>"
					  				
					  				
					  				
					  			  });
					  			$("#activityContent1").html(activityContent1);
					  			//$("#activityContent2").html(activityContent2);
					  			   
					  		   }
					  	   }
						});
					}
				</script>
				<!-- ul list -->
				<!-- <div class="schedule_list" id="tag_1">
					<ul id="activityContent2">
					</ul>
				</div> -->
			<script type="text/javascript">
				$(function(){
					var trlist =$(".table").find("tr");
					console.log(trlist);
			    	if(isBrowser() == "IE"){
			    	console.log(trlist.length);
			         for(var i = 0;i<trlist.length;i++){
			         	
			         }
			    }
			})
			</script>
				<!-- <div class="schedule_day">
					<div class="green_cicle"></div>
					<div class="day_title">
						<span>
							第二届中德职业教育高峰论坛
						</span>
						<span id="cross"></span>
					</div>
				</div>
				<div class="schedule_day">
					<div class="green_cicle"></div>
					<div class="day_title">
						<span>
							2018四川职业教育发展论坛
						</span>
						<span id="cross"></span>
					</div>
				</div>
				<div class="schedule_day">
					<div class="green_cicle"></div>
					<div class="day_title">
						<span>
							2018中小学升学规划专题讲座
						</span>
						<span id="cross"></span>
					</div>
				</div>
			</div> 
		</div>-->
		<!-- 往届回顾 -->
		<div class="review">
			<div class="menu_title">
				<div class="pos_relative mar10" style="position: relative;margin:0 auto"><p class="font-30">往届回顾</p>
					<div class="title_bottom"></div>
				</div>
			</div>
			<div class="lineF">
				<div class="boxF" style="position: relative;left: 100px">
                	<div class="boxS">
                  	  <div class="boxT" style="background-image: url(img/review1.jpg);">
                   	     <div class="overlay">
                   	         <a href="#"></a>
                   	     </div>
                 	   </div>
                	</div>
            	</div>
            	<div class="boxF" style="position: relative;top: 150px;left: 50px;">
                	<div class="boxS">
                  	  <div class="boxT" style="background-image: url(img/review2.jpg);">
                   	     <div class="overlay">
                   	         <a href="#"></a>
                   	     </div>
                 	   </div>
                	</div>
            	</div>
            	<div class="boxF" >
                	<div class="boxS">
                  	  <div class="boxT" style="background-image: url(img/review3.jpg);">
                   	     <div class="overlay">
                   	         <a href="#"></a>
                   	     </div>
                 	   </div>
                	</div>
            	</div>
            	<div class="boxF" style="position: relative;top: 150px;left: -50px" >
                	<div class="boxS">
                  	  <div class="boxT" style="background-image: url(img/review4.jpg);">
                   	     <div class="overlay">
                   	         <a href="#"></a>
                   	     </div>
                 	   </div>
                	</div>
            	</div>
            	<div class="boxF" style="position: relative;right:100px;">
                	<div class="boxS">
                  	  <div class="boxT" style="background-image: url(img/review5.jpg);">
                   	     <div class="overlay">
                   	         <a href="#"></a>
                   	     </div>
                 	   </div>
                	</div>
            	</div>
            	
			</div>
		</div>
		<!-- 服务中心 -->
		<div class="server">
			<div class="menu_title">
				<div class="pos_relative mar10" style="position: relative;margin:0 auto"><p class="font-30">服务中心</p>
					<div class="title_bottom"></div>
				</div>
			</div>
			<div class="server_main">
				<div style="position: relative;"><img src="img/server_bg1.png" alt="">
					<span>周边服务</span>
					</div>
				<div style="position: relative;"><img src="img/server_bg2.png" alt="">
					<span>餐饮服务</span>
				</div>
				
				<div style="position: relative;"><img src="img/server_bg3.png" alt="">
					<span >交通指南</span>
				</div>
				
			</div>
		</div>
		<!-- partner|media|历届展商 -->
		<div class="comprehensive">
			<div class="menu_title">
				<div class="pos_relative mar10" style="position: relative;margin:0 auto"><p class="font-30">合作伙伴|历届展商|合作媒体</p>
					<div class="title_bottom_parnter"></div>
				</div>
			</div>

			<div class="company flex" style="width: 80%;margin: 0 auto;flex-wrap: wrap;justify-content: space-around;-ms-justify-content:space-around;">
				<img src="img/company/ziyang.png" alt="">
				<img src="img/company/baoxian.png" alt="">
				<img src="img/company/songde.png" alt="">
				<img src="img/company/languang.png" alt="">
				<img src="img/company/zhongguoyinhang.png" alt="">
				<img src="img/company/zhongguoyinhang.png" alt="">
				<img src="img/company/languang.png" alt="">
				<img src="img/company/songde.png" alt="">
				<img src="img/company/baoxian.png" alt="">
				<img src="img/company/ziyang.png" alt="">
			</div>
		</div>
	
	
	<!-- footer -->
	<div class="footer_container">
		<div class="footer">
			<div class="about_us">
				<p class="footer_title">关于我们</p>
				<p class="footer_content">联系我们：028-86947026</p>
				<p class="footer_content">电子邮箱：xbjyblh@163.com</p>
				<p class="footer_content">展会地址：成都市会展中心</p>
			</div>
			<div class="youqinglianjie" id="linkData">
				<p class='footer_title'>友情链接</p>
				<div style="float:left;margin-right:20px;" id="linkData1">
				</div>
				
				<div  style="float:left;" id="linkData2">
				</div>
				
				<p class="footer_content"><a style="color:white;" href="${pageContext.request.contextPath}/jsp/link.jsp">更多 ></a></p>

			</div>
			<div class="technology">
				<p class="footer_title">技术支持</p>
				<p class="footer_content">四川臻佳科技有限公司</p>
			</div>
		</div>
	</div>

</body>

<script type="text/javascript">
	
</script>

<script type="text/javascript">
	$(function () {
		 pageQuery(1);
	});

    //分页查询      		
	function pageQuery(pageno){
		
		var jsonData = {"pageno" : pageno,"pagesize" : 8,"queryText" : ''};
		
		//发送异步请求来进行查询
		$.ajax({ 
			type : "POST",
			url  : "${pageContext.request.contextPath}/link/pageQuery",
			data : jsonData,
			success : function(result){
				
	  		   //服务器后端传递的数据是以json格式返回的{success:""}  result代表服务器返回的数据
	  		   if(result.success){
	  			  //用户信息分页查询成功  局部刷新页面数据
	  			 
	  			  var linkContent1 = "";
	  			  var linkContent2 = "";
	 
	  			  var linkPage = result.data;
	  			  var links = linkPage.datas;
	  			  
	  			
	  			  
	  			  //循环遍历links 即当前页要显示的数据
	  			  //links代表当前要遍历的数据  另一个是回调函数link代表当前遍历到的对象
	  			  $.each(links,function(i,link){
	  				if(i<=3){
	  					linkContent1 += "<p class='footer_content'><a style='color:white;' target='_blank' href='"+link.url+"'>"+link.name+"</a></p>"
	  				}else{
	  					linkContent2 += "<p class='footer_content'><a style='color:white;' target='_blank' href='"+link.url+"'>"+link.name+"</a></p>"
	  				}
	  			  });
	  			  
	  			
	  			   $("#linkData1").html(linkContent1);
	  			   $("#linkData2").html(linkContent2);
	  		   }
	  	   }
		});
	}
</script>
</html>
