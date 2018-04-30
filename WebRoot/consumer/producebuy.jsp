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
    
    <title>My JSP 'producebuy.jsp' starting page</title>
    
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
     <button onclick="window.location='servlet/selectServlet?action=selectorder&&type=consumer'">查看订单</button> 
   <button onclick="window.location='/ItemWarehouse/consumer/index.jsp'">返回</button> 
   <button onclick="window.location='servlet/selectServlet?action=selectarticle&type=consumer'">查看物品</button> 
    <% consumer coumer = (consumer) session.getAttribute("consumer"); %>
   <form method="post" id="producebuy"> 
    <table width="400" border="1" cellspacing="0" id="table">
    <tr>
        <th width="100">店铺</th>
        <th width="100">物品</th> 
        <th width="100">购买数量</th> 
        <th width="100">地点</th>   
      </tr>
    </table>
    </form>
    <button id="produce_buy" onclick="lianjie()">通知生产并购买</button>
    <button id="add" onclick="biaogeadd()">添加</button>
  </body>
  <script type="text/javascript">
     function biaogeadd(){
    var table = document.getElementById("table");    
    oneRow = table.insertRow();//插入一行  
    cell1= oneRow.insertCell();//单单插入一行是不管用的，需要插入单元格  
    cell2=oneRow.insertCell(); 
    cell3=oneRow.insertCell(); 
    cell4=oneRow.insertCell();
    cell1.innerHTML="<input type='text' name='store_name'>";  
    cell2.innerHTML="<input type='text' name='article'>"; 
    cell3.innerHTML="<input type='text' name='num'>";
    cell4.innerHTML="<input type='text' name='place'>";
   }
   function lianjie(){
       document.getElementById("producebuy").action="servlet/updateServlet?action=producebuy&type=consumer";
       document.getElementById("producebuy").submit(); 
  }     
   </script>
  </body>
</html>
