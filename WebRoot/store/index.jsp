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
     <%  store str=(store)session.getAttribute("store");
       if(str!=null){
        out.print(str.getName()+" "+str.getEmail()+" "+str.getGrade()+" "+str.getDate()+" "+str.getMoney()+" "+str.getPhone());
       } %>
          <button onclick="window.location='servlet/selectServlet?action=selectarticle&type=store'">查看产品</button>
          <button onclick="window.location='servlet/selectServlet?action=selectorder&type=store'">查看订单</button>    
          <button onclick="window.location='/ItemWarehouse/store/password.jsp'">修改密码</button>    
           <button onclick="window.location='servlet/systemServlet?type=store'">注销</button>
  </body>
</html>
