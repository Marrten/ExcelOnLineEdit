<%@ page contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%
    String path = request.getContextPath();
    String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">

    <title>导入excel页面</title>
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
    <link rel="stylesheet" type="text/css" href="${base}/css/bootstrap.css">
    <link  href="${base}/css/fileinput.css" media="all" rel="stylesheet" type="text/css" />
    <script type="text/javascript" src="${base}/js/jquery-3.1.0.js"></script>
    <script type="text/javascript" src="${base}/js/bootstrap.js"></script>
   </head>
  <body>
  <div class="container-fluid">
      <div class="row-fluid">
          <div class="span12">
    <form action="${base}/excel/upload" method="post" enctype="multipart/form-data">
        <legend>导入Excel</legend>
    <input type="file" name="file" id="upfile"/><br>
    <input type="submit" value="提交"/>
    </form>
          </div>
      </div>
  </div>
  </body>
    <script type="text/javascript">
        $('#upfile').change(function(){
            $('#filetxt').val($('#upfile').val());
        });
    </script>
</html>
