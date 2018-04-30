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
    
    <title>My JSP 'order.jsp' starting page</title>
    
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
  <button onclick="window.location='servlet/selectServlet?action=selectarticle&type=store'">查看物品</button> 
   <button onclick="window.location='/ItemWarehouse/store/index.jsp'">返回</button> 
   <form action="servlet/selectServlet?action=selectordername&type=store" method="post"><input type="text" name="seriesorder" /> <input type="submit" value="搜索物品订单纪录"> </form>
     <table width="1000" border="1" cellspacing="0">
     <tr >
          <th width="100">店铺</th>
        <th width="100">客户</th>
        <th width="100">物品</th>  	
        <th width="100">数量</th> 
        <th width="100">金额</th> 
        <th width="100">地点</th>
        <th width="100">满意度</th>
        <th width="100">评价</th> 
        <th width="100">时间</th>  
        <th width="100">状态</th> 
      </tr>
       <%  request.setCharacterEncoding("utf-8");
         if(request.getAttribute("order") instanceof String){
             String list=request.getAttribute("order").toString();
             out.print(list);
         }else{      
           List<order>  list= (List<order>)request.getAttribute("order");   
           List<comment>  comlist= (List<comment>)request.getAttribute("comment");   
           if(list!=null&&!list.isEmpty()) {
           String comment_text="";
           String satisfaction="";
           int p=0;
           for(int i=0;i<list.size();i++)
          { order or=list.get(i);     
          if(!comlist.isEmpty()){
          if(p<comlist.size()+1){
               for(int o=0;o<comlist.size();o++){
               comment com=comlist.get(o);
               if(com.getOrder_id()==or.getId()){
                   comment_text=com.getComment_text();
                   satisfaction=String.valueOf(com.getSatisfaction()); 
                   p++; 
                   break;
                }  
                comment_text="用户暂时没有评论!";
              } 
             }                            
         }else{
            comment_text="用户暂时没有评论!";
           }        
          %>            
       <tr>    
        <td ><%=or.getStore_name() %></td>
        <td><%=or.getConsumer_Identification() %></td>
        <td><%=or.getStore_article_name() %></td>  
        <td><%=or.getNum() %></td> 
        <td><%=or.getPrice() %></td> 
        <td><%=or.getPlace() %></td> 
        <td><%=satisfaction %></td>
        <td><%=comment_text %></td>   
        <td><%=or.getDate() %></td>  
        <td><%=or.getStatus() %></td>      
      </tr>
       <%  comment_text="";
          satisfaction=""; 
       }}} %> 
    </table>     
  </body>
</html>
