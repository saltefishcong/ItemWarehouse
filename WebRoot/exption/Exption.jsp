<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
    
    <title>My JSP 'Exption.jsp' starting page</title>
    
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
       <div id="exption"></div>
    <%     String exption=(String)request.getAttribute("exption");
     %>
     <button onclick="window.location='/ItemWarehouse/store/login.jsp'">登陆</button>
      <button onclick="window.location='/ItemWarehouse/store/store.jsp'">注册</button>
  </body>
  <script type="text/javascript">
   document.getElementById("exption").innerHTML=" <%=exption%>"; 
   alert(" <%=exption%>");    
  </script>
</html>
