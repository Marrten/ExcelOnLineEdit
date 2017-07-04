<%@ page contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<html>
  <head>
    <base href="<%=basePath%>">
    <title>只读文件列表</title>
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
    <link rel="stylesheet" type="text/css" href="${base}/css/bootstrap.css">
    <script type="text/javascript" src="${base}/js/jquery-3.1.0.js"></script>
    <script type="text/javascript" src="${base}/js/bootstrap.js"></script>
  </head>

  <body>
  <div class="container-fluid">
	  <div class="row-fluid">
		  <div class="span12">
			  <table class="table">
				  <thead>
				  <tr class="tr">
					  <th>文件名(点击文件名，可在线查看)</th>
					  <th>操作</th>
				  </tr>
				  </thead>
					<c:forEach items="${fileNames}" var="filename">
				  <tbody>
				  <tr class="tr">
					  	<td>${filename}</td>
					  	<td><a href="${base}/excel/read?filename=${filename}">查看</a></td>
				  </tr>
				  </tbody>
					</c:forEach>
			 </table>
		  </div>
	  </div>
  </div>
  </body>
  <script type="text/javascript">
      $(".tr").bind("mouseover",function(){
          $(this).css("background-color","#eeeeee");
      })
      $(".tr").bind("mouseout",function(){
          $(this).css("background-color","#ffffff");
      })

  </script>
</html>


