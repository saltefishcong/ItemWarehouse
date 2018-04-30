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
    
    <title>My JSP 'articlelist.jsp' starting page</title>
    
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
    <button onclick="window.location='servlet/selectServlet?action=mybuycar&&type=consumer'">查看购物车</button>   
   <form action="servlet/selectServlet?action=selectarticlename&type=consumer" method="post"><input type="text" name="article_name" /> <input type="submit" value="搜索物品"> </form>  
     <form id="article" method="post">
     <table width="600" border="1" cellspacing="0" id="table">   
     <tr >
        <th width="20"></th>
        <th width="100">店铺</th>
        <th width="100">名称</th>
        <th width="100">金钱</th>
        <th width="100">库存</th>
        <th width="100">加入购物车</th>     
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
        <td ><%=atr.getStore_name() %><input type="hidden"  name="store_name" value="<%=atr.getStore_name() %>"></td>   
        <td ><%=atr.getArticle() %><input type="hidden"  name="article" value="<%=atr.getArticle() %>"></td>
        <td><%=atr.getMoney() %><input type="hidden"  name="money" value="<%=atr.getMoney() %>"></td>
        <td><%=atr.getNum() %><input type="hidden"  name="onlinenum" value="<%=atr.getNum() %>"></td>
        <td><input type="text" name="num"></td>        
      </tr>
       <% }}} %>      
    </table>  
    </form>
    <button id="addbuycar" onclick="lianjie(this)">添加购物车</button>
  </body>
   <script type="text/javascript">      function lianjie(e){
       const list = document.querySelector("#table");
       const items = document.querySelectorAll("tr.item");
       const per = list.children[0];
       console.log(items.length);
       for(var i = 0; i < items.length; i++) {
       	const tmp = items[i].querySelector('[type=checkbox]');
       	if(!tmp.checked) {
       		per.removeChild(items[i]);
       	}
       }
       document.getElementById("article").action="servlet/updateServlet?action=addbuycar&type=consumer";
       document.getElementById("article").submit();
  }     
  </script>
</html>
