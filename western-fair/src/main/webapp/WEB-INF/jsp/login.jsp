<%@ page pageEncoding="UTF-8" %>

<!DOCTYPE html>
<html lang="zh-CN">
  <head>
    <meta charset="UTF-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <meta name="description" content="">
    <meta name="keys" content="">
    <meta name="author" content="">
	<link rel="stylesheet" href="bootstrap/css/bootstrap.min.css">
	<link rel="stylesheet" href="css/font-awesome.min.css">
	<link rel="stylesheet" href="css/login.css">
	<style>

	</style>
	<script type="text/javascript">
		function changeCode() {  
		    document.getElementById("CreateCheckCode").src = document  
		            .getElementById("CreateCheckCode").src  
		            + "?nocache=" + new Date().getTime();  
		}  
	</script>
  </head>
  <body>
    <nav class="navbar navbar-inverse navbar-fixed-top" role="navigation">
      <div class="container">
        <div class="navbar-header">
          <div><a class="navbar-brand" href="index.html" style="font-size:32px;">西博会后台管理系统</a></div>
        </div>
      </div>
    </nav>

    <div class="container">
      <h1 style="color:red">${param.errorMsg}</h1>
	  <!-- 没有以"/"开头的路径都是相对路径  以"/"开头的都是绝对路径 -->
      <form id="loginForm" action="doLogin" method="post" class="form-signin" role="form">
        <h2 class="form-signin-heading"><i class="glyphicon glyphicon-user"></i> 用户登录</h2>
		  <div class="form-group has-success has-feedback">
			<input type="text" class="form-control" id="loginacct" placeholder="请输入登录账号" name="loginacct" autofocus>
			<span class="glyphicon glyphicon-user form-control-feedback"></span>
		  </div>
		  <div class="form-group has-success has-feedback">
			<input type="password" class="form-control" id="userpswd" placeholder="请输入登录密码" name="userpswd" style="margin-top:10px;">
			<span class="form-control-feedback"></span>
		  </div>
		  <div class="form-group has-success has-feedback">
		  	<div style="padding-left:0;width:120px;display:inline-block;margin-right:50px;">
		  		<input type="text" class="form-control" id="checkCode" placeholder="请输入验证码" name="checkCode">		
		  	</div>
		  	<div style="width:120px;display:inline-block;">
		  		<a href="#" class="login-text03" id="changeCode" onclick="changeCode()">
				<img src="${pageContext.request.contextPath}/checkCode" width="100" height="32" style="height:43px;cursor:pointer;" id="CreateCheckCode"></a>
		  	</div>		  	
		  </div>
        <div class="checkbox">
          <label>
          		<input type="checkbox" value="remember-me">  记住密码
          </label>
          <label style="float:right">
            <a href="reg.html">我要注册</a>
          </label>
        </div>
        <a class="btn btn-lg btn-success btn-block" onclick="dologin()" > 登录</a>
      </form>
    </div>
    <script src="jquery/jquery-2.1.1.min.js"></script>
    <script src="bootstrap/js/bootstrap.min.js"></script>
    <script src="layer/layer.js"></script>
    <script>
    function dologin() {
       //非空校验
       var loginacct = $("#loginacct").val();
       //即使表单元素中没有输入任何值  表单元素中的value取值不会是null 取值是空字符串
       if(loginacct == null || loginacct == ""){
    	   //alert("用户登录账号不能为空，请输入");
    	   //time:用来设置弹出框出现的时间
    	   //icon:设置图标  不同的数字代表不同的图标
    	   //shift:设置弹出框出现的效果  不同的数字代表不同的效果
    	   layer.msg("用户登录账号不能为空，请输入", {time:2000, icon:5, shift:6}, function(){
    		   
    	   });
    	   return;
       }
       
       var userpswd = $("#userpswd").val();
       if(userpswd == null || userpswd == ""){
    	   //alert("用户登录密码不能为空，请输入");
    	   layer.msg("用户登录密码不能为空，请输入", {time:2000, icon:5, shift:6}, function(){
    		   
    	   });
    	   return;
       }
       
       var checkCode = $("#checkCode").val();
       if(checkCode == null || checkCode == ""){
    	   //alert("用户登录密码不能为空，请输入");
    	   layer.msg("用户验证码不能为空，请输入", {time:2000, icon:5, shift:6}, function(){
    		   
    	   });
    	   return;
       }
       
       
       //提交表单
       //$("#loginForm").submit();
       //使用Ajax来提交数据 $.ajax(json格式的数据)
       /*
       type：设置请求的方式
       url:设置请求路劲
       data:设置请求传送的数据
       beforeSend:设置服务器还未处理完请求时的效果--出现处理中的图标
       success：设置服务器处理完请求后的效果  -- 关掉出现处理中图标的效果
       */
       var loadingIndex = null;
       $.ajax({
    	   type : "POST",
    	   url  : "doAjaxLogin",
    	   data : {
    		   "loginacct" : loginacct,
    		   "userpswd"  : userpswd,
    		   "checkCode" : checkCode
    	   },
    	   beforeSend : function(){
    		   loadingIndex = layer.msg('处理中', {icon: 16});
    	   },
    	   success : function(result){
    		   layer.close(loadingIndex);
    		   //服务器后端传递的数据是以json格式返回的{success:""}  result代表服务器返回的数据
    		   if(result.success){
    			   //根据浏览器浏览器返回的结果可知  用户登录成功
    			   window.location.href="main";
    			   
    		   }else{
    			   layer.msg(result.data, {time:2000, icon:5, shift:6}, function(){  	   	   
    	    	   });
    		   }
    	   }
       });
    }
    </script>
  </body>
</html>