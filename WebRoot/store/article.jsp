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
    
    <title>My JSP 'article.jsp' starting page</title>
    
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
  <button onclick="window.location='servlet/selectServlet?action=selectorder&type=store'">查看订单</button> 
   <button onclick="window.location='/ItemWarehouse/store/index.jsp'">返回</button> 
    <form id="article" method="post">
    <input type="hidden" name="type" value="store">
     <table width="400" border="1" cellspacing="0" id="table">
     <tr >
        <th width="20"></th>
        <th width="100">名称</th>
        <th width="100">金钱</th>
        <th width="100">库存</th>    
      </tr>
       <%  request.setCharacterEncoding("utf-8");
         if(request.getAttribute("article") instanceof String){
             String list=request.getAttribute("article").toString();
             out.print(list);
         }else{      
           List<article>  list= (List<article>)request.getAttribute("article");   
           if(list!=null&&!list.isEmpty()) {
           for(int i=0;i<list.size();i++)
          { article atr=list.get(i);
          %>      
       <tr class="item">
        <td ><input type="checkbox"  name="articleid[]" value="<%=atr.getId() %>"></td>        
        <td ><%=atr.getArticle() %><input type="hidden" value="<%=atr.getArticle() %>" name="articlename"></td>
        <td><input type="text" value="<%=atr.getMoney() %>" name="articlemoney"></td>
        <td><%=atr.getNum() %></td>       
      </tr>
       <% }}} %> 
    </table>     
    </form>    
     <button id="update" onclick="lianjie(this)">更改</button>
    <button id="delete" onclick="lianjie(this)">删除</button>
    <button id="add" onclick="window.location='/ItemWarehouse/store/addarticle.jsp'">添加</button>
     <button id="produce" onclick="window.location='/ItemWarehouse/store/produce.jsp'">生产</button>
  </body>
  <script type="text/javascript">
  
   if("<%=exption%>" != 'null'){
   document.getElementById("exption").innerHTML=" <%=exption%>";
   } 
  function lianjie(e){
     if(e.id=="delete"){
       document.getElementById("article").action="servlet/updateServlet?action=deletearticle&type=store";
       document.getElementById("article").submit(); 
     }else{
       const list = document.querySelector("#table");
       const items = document.querySelectorAll("tr.item");
       const per = list.children[0];
       console.log(items.length);
       for(let i = 0; i < items.length; i++) {
       	const tmp = items[i].querySelector('[type=checkbox]');
       	if(!tmp.checked) {
       		per.removeChild(items[i]);
       	}
       }
       document.getElementById("article").action="servlet/updateServlet?action=updatearticle&type=store";
       document.getElementById("article").submit();
     }
  }     
  </script>
</html>
