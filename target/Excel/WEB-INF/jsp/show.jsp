<%--
  Created by IntelliJ IDEA.
  User: lq
  Date: 2016/12/14/0014
  Time: 14:31
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<html>
<head>
    <base href="<%=basePath%>">
    <title>文件列表</title>
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
    <table class="table table-condensed table table-bordered" style="width: 800px;table-layout:fixed;margin-top: 50px" align="center">
        <tr>
            <c:forEach items="${title}" var="ti">
                <td style="width:5%" >${ti}</td>
            </c:forEach>
        </tr>
        <c:forEach items="${data}" var="ds">
            <tr>
                <c:forEach items="${ds}" var="d">
                    <td style="width:5%">${d}</td>
                </c:forEach>
            </tr>
        </c:forEach>
    </table>

</body>
<%--<script>
    function showExcel(){
        var map = $("#map").val();
        console.log(map);
        var table = new Array();
        table.push('<table class="table table-condensed table table-bordered" style="width:20% ;height:auto;border:1px solid;"  id="table"><tbody id="tbody">');
        $.each(map, function (index, tr) {
            table.push('<tr class="tr" style="height: 30px">');
            $.each(tr, function (index,td) {
                table.push('<td class="td" style="width:5%";height:auto;">'+td+'</td>');
            });
            table.push('</tr>');
        });
        //table.push('<input type="hidden" value='+filename+' id="filename" ></input>');
        document.getElementById('table-body').innerHTML = table.join('');
    }
</script>--%>
</html>
