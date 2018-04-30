<%@ page language="java" import="java.util.*,com.Itemhouse.eity.*" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
    
    <title>My JSP 'produce.jsp' starting page</title>
    
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
    <button onclick="window.location='servlet/selectServlet?action=selectorder&type=store'">查看订单</button> 
   <button onclick="window.location='/ItemWarehouse/store/index.jsp'">返回</button> 
    <button onclick="window.location='servlet/selectServlet?action=selectproduce&type=store'">生产纪录</button> 
   <button onclick="window.location='servlet/selectServlet?action=selectarticle&type=store'">查看物品</button> 
    <% store str = (store) session.getAttribute("store"); %>
   <form method="post" id="add_article"> 
    <table width="400" border="1" cellspacing="0" id="table">
    <tr>
        <th width="100">物品</th>
        <th width="100">数量</th>   
      </tr>
    </table>
     
     <input type="hidden" name="name" value="<%=str.getName() %>" > 
     <input type="hidden" name="type" value="store"> 
    </form>
    <button id="baochuan" onclick="lianjie()">生产</button>
    <button id="add" onclick="biaogeadd()">添加</button>
  </body>
  <script type="text/javascript">
     function biaogeadd(){
    var table = document.getElementById("table");    
    oneRow = table.insertRow();//插入一行  
    cell1= oneRow.insertCell();//单单插入一行是不管用的，需要插入单元格  
    cell2=oneRow.insertCell(); 
    cell1.innerHTML = "<input type='text' name='articlename'>";   
    cell2.innerHTML="<input type='text' name='articlenum'>"; 
   }
   function lianjie(){
       document.getElementById("add_article").action="servlet/updateServlet?action=produce&type=store";
       document.getElementById("add_article").submit(); 
  }   
  </script>
</html>
