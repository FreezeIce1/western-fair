<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
	<script src="${pageContext.request.contextPath}/jquery/jquery-2.1.1.min.js"></script>
</head>
<body>
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