<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
    
    <title>My JSP 'store.jsp' starting page</title>
    
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<!--
	<link rel="stylesheet" type="text/css" href="styles.css">
	-->

  </head>
  
  <body>
       <form action="servlet/registerServlet"  method="post">
       账号:<input type="text" name="name" />
       密码:<input  type="password" name="password" />
       电话:<input type="text" name="phone" />
       邮箱:<input  type="text" name="email" /> 
       <input type="hidden" name="action" value="register"> 
       <input type="hidden" name="type" value="store">
       <input type="submit" value="注册"> 
      </form>
      <text>${cookie.consumer.value}</text>
  </body>
</html>
