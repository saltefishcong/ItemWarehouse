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
    
    <title>My JSP 'buy.jsp' starting page</title>
    
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
      <button onclick="window.location='servlet/selectServlet?action=selectarticle&&type=consumer'">继续购物</button>
        <form id="article" method="post">       
     <table width="600" border="1" cellspacing="0" id="table">
     <tr >
        <th width="20"></th>
        <th width="100">店铺</th>
        <th width="100">名称</th>
        <th width="100">物品单价</th>
        <th width="100">购买数量</th>
        <th width="100">地点</th>
        <th width="100">减少的数量</th>      
      </tr>
       <%  request.setCharacterEncoding("utf-8");
         if(request.getAttribute("buycar") instanceof String){
             String list=request.getAttribute("buycar").toString();
             out.print(list);
         }else{      
           List<buycar>  list= (List<buycar>)request.getAttribute("buycar");   
           if(list!=null&&!list.isEmpty()) {
           for(int i=0;i<list.size();i++)
          { buycar car=list.get(i);
          %>      
        <tr class="item">
        <td ><input type="checkbox"  name="carid" value="<%=car.getId() %>"></td>   
        <td ><%=car.getStore_name() %><input type="hidden"  name="store_name" value="<%=car.getStore_name() %>"></td>   
        <td ><%=car.getArticle() %><input type="hidden"  name="article" value="<%=car.getArticle() %>"></td>
        <td><%=car.getMoney() %><input type="hidden"  name="money" value="<%=car.getMoney() %>"></td>
        <td><%=car.getNum() %><input type="hidden"  name="num" value="<%=car.getNum() %>"></td>  
        <td><input type="text"  name="place"></td> 
        <td><input type="text"  name="cutbacknum"></td>  
      </tr>
       <% }}} %>   
    </table> 
     </form>
      <button id="buy" onclick="lianjie(this)">购买</button>
      <button id="delete" onclick="lianjie(this)">删除</button> 
      <button id="update" onclick="lianjie(this)">更改</button>    
      <button id="produce" onclick="producebuy()">通知商家生产</button>
  </body>
    <script type="text/javascript">
      function lianjie(e){
      if(e.id=='buy'){    
         operating("servlet/updateServlet?action=buy&type=consumer");
      }else if(e.id=='delete'){ 
         operating("servlet/updateServlet?action=deletebuycar&type=consumer");
     }else{
        operating("servlet/updateServlet?action=updatebuycar&type=consumer");
     }   
  } 
  function operating(a){
      console.log(a);
       const list = document.querySelector("#table");
       const items = document.querySelectorAll("tr.item");
       const per = list.children[0];
       for(let i = 0; i < items.length; i++) {
       	const tmp = items[i].querySelector('[type=checkbox]');
       	if(!tmp.checked) {
       		per.removeChild(items[i]);
       	}
       }
       document.getElementById("article").action=a;
       document.getElementById("article").submit();
  }  
  function producebuy(){
     alert('通知商家生产意味着一定需要这些商品,商家生产完了会自动购买生产的商品!');
     window.location='/ItemWarehouse/consumer/producebuy.jsp';
  }  
  </script>
</html>
