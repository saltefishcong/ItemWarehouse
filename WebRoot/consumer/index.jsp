<%@ page contentType="text/html; charset=UTF-8"%>
<%@ page language="java" import="java.util.*,com.Itemhouse.eity.*" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
    
    <title>My JSP 'index.jsp' starting page</title>
    
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
   <%  consumer coumer=(consumer)session.getAttribute("consumer");
       if(coumer!=null){
        out.print(coumer.getName()+" "+coumer.getIdentification());
       } %>
   <button onclick="window.location='/ItemWarehouse/consumer/updateinfo.jsp'">更改信息</button>   
   <button onclick="window.location='servlet/selectServlet?action=selectorder&&type=consumer'">查看订单</button>
   <button onclick="window.location='servlet/selectServlet?action=selectarticle&&type=consumer'">购买</button>  
   <button onclick="window.location='/ItemWarehouse/consumer/login.jsp'">登陆</button>  
   <button onclick="window.location='servlet/systemServlet?type=consumer'">注销</button>   
  </body>
</html>
