<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html lang="en">
<head>
	<meta charset="UTF-8">
	<title>Document</title>
	<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/list_page.css">
	<link rel="stylesheet" href="${pageContext.request.contextPath}/bootstrap/css/bootstrap.min.css">
	<link rel="stylesheet" href="${pageContext.request.contextPath}/css/font-awesome.min.css">
	<link rel="stylesheet" href="${pageContext.request.contextPath}/css/main.css">
	<script type="text/javascript" src="${pageContext.request.contextPath}/js/jquery.js"></script>
	<script src="${pageContext.request.contextPath}/jquery/jquery-2.1.1.min.js"></script>
	
	<script type="text/javascript" src="${pageContext.request.contextPath}/js/index.js"></script>
	<script type="text/javascript" src="${pageContext.request.contextPath}/js/jquery.SuperSlide.2.1.1.js"></script>
	
	<script src="http://cdn.static.runoob.com/libs/angular.js/1.4.6/angular.min.js"></script>
</head>
<body>
	<jsp:include page="about/header.jsp"></jsp:include>

	<div class="slide_pic">
		<img src="../img/slidepic.png">
		<span class="word">活动日程</span>
	</div>
	
	<!-- 主体 -->
	<div class="container">
		<div class="left_nav">
			<ul class="left_nav_list">
				<li class="active"><a href="">活动一览表</a></li>
				<li><a href="">同期活动</a></li>
			</ul>
		</div>
		<div class="right_content">
			<div class="location">
				<span>您现在的位置</span>:<span>活动日程</span>><span>活动一览表</span>
			</div>
			<div class="content">
				<h4>活动一览表</h4>
				<span class="circle"></span><span class="line"></span>
				<div class="context_schedule" style="width:1024px">
					<table>
						<thead id="activityContent2">
						</thead>
						<tfoot>
			     			<tr>
				     			<td colspan="9" align="center">
									<ul class="pagination">
								
									</ul>
					 			</td>
				 			</tr>
			  			</tfoot>
						<!-- <tr>
							<td>2018亚洲教育论坛年会</td>
							<td>成都世界城新国际会展中心</td>
							<td>9月20日10：00</td>
						</tr>
						<tr>
							<td>第二届中德职业教育高峰论坛</td>
							<td>成都世界城新国际会展中心</td>
							<td>9月20日14：00</td>
						</tr>
						<tr>
							<td>2018亚洲教育论坛年会</td>
							<td>成都世界城新国际会展中心</td>
							<td>9月20日10：00</td>
						</tr>
						<tr>
							<td>第二届中德职业教育高峰论坛</td>
							<td>成都世界城新国际会展中心</td>
							<td>9月20日14：00</td>
						</tr> -->
					</table>
				</div>
			</div>
		</div>
	</div>
	
	<script type="text/javascript">
					$(function () {
						 pageQuery2(1);
					});
	
				    //分页查询      		
					function pageQuery2(pageno){
						//var loadingIndex = null;
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
					  			 var activityContent2 = "<tr>"
									+"<th>活动日程</th>"
									+"<th>活动地点</th>"
									+"<th>时间</th>"
								+"</tr>";
								var pageContent = "";
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
					  				
					  				activityContent2 += "<tr>"
										+"<td>"+activity.name+"</td>"
										+"<td>"+activity.address+"</td>"
										+"<td>"+activity.time+"</td>"
										+"</tr>"
					  			});
					  			 if(pageno > 1){
		    	    				  pageContent += '<li><a onclick="pageQuery2('+(pageno-1)+');" href="#">上一页</a></li>';
		    	    			  }else if(pageno == 1){
		    	    				  pageContent += '<li class="disabled"><a href="#">上一页</a></li>';
		    	    			  }
		    
		    	    			  for(var i=1; i<=activityPage.totalno;i++){
		    	    				  if(pageno == i){
		    	    					pageContent += '<li class="active"><a href="#">'+i+'</a></li>';
		    	    				  }else{
		    	    					pageContent += '<li><a href="#" onclick="pageQuery2('+i+');">'+i+'</a></li>';
		    	    				  }
		    	    				  
		    	    			  }
		    	    			  if(pageno < activityPage.totalno){
		    	    				  pageContent += '<li><a onclick="pageQuery2('+(pageno+1)+');" href="#">下一页</a></li>';
		    	    			  }else if(pageno == activityPage.totalno){
		    	    				  pageContent += '<li class="disabled"><a href="#">下一页</a></li>';
		    	    			  } 
					  			$("#activityContent2").html(activityContent2);
					  			$(".pagination").html(pageContent);
					  			//$("#activityContent2").html(activityContent2);
					  			   
					  		   }
					  	   }
						});
					}
				</script>
	
	<jsp:include page="about/foot.jsp"></jsp:include>

</body>
</html>