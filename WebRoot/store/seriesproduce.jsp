<%@ page language="java" import="java.util.*,com.Itemhouse.eity.*" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
    
    <title>My JSP 'seriesproduce.jsp' starting page</title>
    
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
   <button onclick="window.location='/ItemWarehouse/store/produce.jsp'">继续生产</button> 
   <button onclick="window.location='/ItemWarehouse/store/index.jsp'">返回</button>
    <button onclick="window.location='servlet/selectServlet?action=selectproduce&type=store'">返回生产纪录</button> 
    <table width="920" border="1" cellspacing="0">
     <tr >
        <th width="100">物品</th>
        <th width="100">数量</th>
        <th width="100">金额</th>  	
        <th width="100">时间</th> 
      </tr>
      <%  request.setCharacterEncoding("utf-8");
         if(request.getAttribute("produce") instanceof String){
             String list=request.getAttribute("produce").toString();
             out.print(list);
         }else{      
           List<produce>  list= (List<produce>)request.getAttribute("produce");   
           if(list!=null&&!list.isEmpty()) {
           for(int i=0;i<list.size();i++)
          { produce pro=list.get(i);
          %>      
       <tr>    
        <td ><%=pro.getArticle_name() %></td>
        <td><%=pro.getNum() %></td>
        <td><%=pro.getPrice() %></td>  
        <td><%=pro.getDate() %></td>     
      </tr>
       <% }}} %> 
    </table>     
  </body>
</html>
