<%@ page contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<html>
  <head>
    <base href="<%=basePath%>">
    <title>可编辑文件列表</title>
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
				  <tr class="trr">
					  <th>文件名(点击文件名,可在线编辑)</th>
					  <th style="align-content:center">下载</th>
					  <th style="align-content:center">删除(点击文件可删除)</th>
				  </tr>
				  </thead>

				  <tbody>
				  <c:if test="${empty fileNames}">
					  <h4><span style="color: red;">列表为空,暂无文件</span><a href="${base}/upload.jsp">上传文件</a></h4>
				  </c:if>
				  <c:forEach items="${fileNames}" var="filename">
				  <tr class="trr">
					  	<td><input type="text" class="tr" onclick="showExcel(this)" value='${filename}' style="border:none;" data-toggle="modal" data-target="#myModal" readonly="true" /></td>
					    <td><a href="${base}/excel/download?filename=${filename}">下载</a></td>
					  	<td><input type="text" onclick="deleteExcel(this)" class="tr" value='${filename}' style="border:none;" readonly="true"/></td>
				  </tr>
				  </c:forEach>
				  </tbody>
			 </table>
		  </div>
	  </div>
  </div>
  		<%--模态框--%>
  <div class="modal fade" id="myModal" tabindex="-1"  role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
			 <div class="modal-dialog" align="center" style="width: auto">
				 <div class="modal-content" style="width: auto;" align="center">
					 <div class="modal-body" id="table-body" align="center"></div>
					 <div class="modal-footer" align="center" style="border: none">
						 <button type="button" class="btn btn-primary" onclick="addRow()">增加一行</button>
						 <button type="button" class="btn btn-primary" onclick="addCell()">增加一列</button>
						 <button type="button" class="btn btn-primary" data-dismiss="modal">关闭</button>
						 <button type="button" class="btn btn-primary" onclick="readHtml()">提交更改</button>
					 </div>
				 </div>
			 </div>
		 </div>
  </body>
  <script type="text/javascript">

	function showExcel(obj){
		var filename = obj.value;
		var param = "filename=" + filename;
		console.log(filename);
		$.ajax({
			url: "${base}/excel/show",
			data: param,
			dataType: "json",
			type: "POST",
			success: function (data) {
                $('#myModal').modal('show');
                var table = [];
                table.push('<table class="table table-condensed table table-bordered" style="width:20% ;height:auto;border:1px solid;"  id="table"><tbody id="tbody">');
                $.each(data, function (index, tr) {
                    table.push('<tr class="tr" style="height: 30px">');
                    $.each(tr, function (index,td) {
                        table.push('<td class="td" style="width:5%";height:auto;"><input type="text" style="border:none;height:35px;font-size: 10px;width:auto;word-wrap:break-word;" value='+td+' ></td>');
                    });
                    table.push('</tr>');
                });
                table.push('<input type="hidden" value='+filename+' id="filename" ></input>');
                document.getElementById('table-body').innerHTML = table.join('');
            },
			async: true,
			cache: false
		});
	}
    function addRow() {
        var tr = document.createElement('tr');
		tr.className = "tr";
		var tbody = document.getElementById("tbody");
        var rows = table.rows.length ;
        var cells = table.rows.item(0).cells.length ;
        tbody.appendChild(tr);
        for (var i = 0;i < cells;i++){
            var inputi = document.createElement('input');
            inputi.style.border = "none";
            inputi.style.height = "46px";
            inputi.style.font.size = "10px";
            var tdi = document.createElement('td');
            tdi.appendChild(inputi);
            tdi.className = "td";
            tdi.style.height = "46px";
            tdi.style.width = " 5%";
            tr.appendChild(tdi);
        }
    }
    function addCell() {
        $(".tr").append('<td style="height:30px;width:5%"class="td"><input type="text" style="border:none;height:46px;font-size:10px"></td>');
    }
    function readHtml() {
        var map = {};
        var rows = table.rows.length ;
        var cells = table.rows.item(0).cells.length ;
        for (var i = 0;i < rows;i++ ){
            var cellValue = [];
            for (var j = 0;j < cells;j++ ){
				valuej =  table.rows.item(i).cells.item(j).childNodes[0].value;
                cellValue[j] = valuej;
			}
			map[i.toString()]= cellValue;
		}
		var json = JSON.stringify(map);
        var params = JSON.stringify(json);

        var filename  = $("#filename").val();
        console.log(filename);
        $.ajax({
            url: "${base}/excel/write",
            data: {
                "json": params,"filename":filename
			},
            type: "POST",
           success: function () {
			alert("更新成功");
		   },
            async: true,
            cache: false
        });
    }
    function deleteExcel(obj) {
	    var filename = obj.value;
        var param = "filename=" + filename;
        if (confirm("确认删除"+filename+"吗？")){
			$.ajax({
				url: "${base}/excel/delete",
				data: param,
				dataType: "json",
				type: "POST",
				success: function (data) {
					//Location.href = "${base}/excel/list";
					alert("删除成功");
                    location.reload();
				},
				async: true,
				cache: false
				});
        }
    }
    $(".trr").bind("mouseover",function(){
        $(this).css("background-color","#eeeeee");
    })
    $(".trr").bind("mouseout",function(){
        $(this).css("background-color","#ffffff");
    })
  </script>	
</html>


