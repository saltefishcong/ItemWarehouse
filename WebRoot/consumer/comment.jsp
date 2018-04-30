<%@ page contentType="text/html; charset=UTF-8"%>
<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
    
    <title>My JSP 'comment.jsp' starting page</title>
    
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
  <button onclick="window.location='/ItemWarehouse/consumer/index.jsp'">返回</button>  
  <button onclick="window.location='/ItemWarehouse/consumer/order.jsp'">查看订单</button>  
  <%   request.setCharacterEncoding("utf-8");
       String orderid= request.getParameter("orderid");
       String store=request.getParameter("store");
   %>
     <form method="post" action="servlet/updateServlet">
    <textarea rows="2" cols="10" name="commenttext"></textarea>
    <input name="satisfaction" type="text"> 
       <input type="hidden" name="type" value="consumer">
       <input type="hidden" name="action" value="addcomment">
       <input type="hidden" name="orderid" value="<%=orderid %>"> 
        <input type="hidden" name="store" value="<%=store %>"> 
       <input type="submit" value="提交"> 
       </form>
  </body>
  </script>
</html>
