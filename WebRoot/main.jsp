<%@ page language="java" import="java.util.*,zy.com.entity.FileInfo"
	pageEncoding="UTF-8"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<base href="<%=basePath%>">

<title>文件操作</title>

<meta http-equiv="pragma" content="no-cache">
<meta http-equiv="cache-control" content="no-cache">
<meta http-equiv="expires" content="0">
<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
<meta http-equiv="description" content="This is my page">
<meta http-equiv="pragma" content="no-cache">

<link href="css/bootstrap.css" rel='stylesheet' type='text/css' />

<link href="css/style.css" rel='stylesheet' type='text/css' />

<link href="css/font-awesome.css" rel="stylesheet">

<script src="js/jquery-1.11.1.min.js"></script>

<script src="js/bootstrap.js"></script>

<script src="js/jquery.form.js"></script>
</head>

<body>

	<div class="panel panel-primary" align="center"
		style="margin: 5em 100px 5em 100px;">
		<div class="panel-heading" style="padding: 1em 1em 1em 1em;">文件上传/下载/删除</div>
		<div class="panel-body">
			<form enctype="multipart/form-data"
				method="post" name="UploadFiles" id="UploadFiles">
				<div style="float: left;">
					<label for="largeinput" style="float: left;">上传文件2</label>
					<div class="form-group">
						<div class="btn btn-default btn-file">
							<input type="file" name="upfile" id="upfile" size="1">
						</div>
						<p class="help-block" style="text-align: left;">Max. 20MB</p>
					</div>

				</div>
				<div style="float: right;">
					<div class="form-group mb-n col-sm-4">
						<input type="submit" class="btn btn-danger" value="提交上传文件"text-align:center;">
					</div>
				</div>
				<div style="float: right;">
					<div class="form-group mb-n col-sm-4">
						<a href="${pageContext.request.contextPath}/Operate/getFileList"><input
							type="button" class="btn btn-success" value="刷新列表" text-align:center;"></a>
					</div>
				</div>
			</form>


			<table class="table table-bordered">
				<a href="ShowAllFiles"><caption>显示列表</caption></a>
				<thead>
					<TR>
						<td>文件名</td>
						<td>文件大小</td>
						<td>最后修改时间</td>
						<td>操作</td>
						<td>操作</td>
					</TR>
				</thead>
				<tbody>
					<c:forEach var="file" items="${filesList}">
						<tr>
							<td>${file.fileName}</td>
							<td>${file.fileSize}</td>
							<td>${file.fileDate}</td>
							<td><a href="DownloadFile?filename=${file.fileName}">下载</a></td>
							<td><a href="DeleteFile?filename=${file.fileName}">删除</a></td>
						</tr>
					</c:forEach>
				</tbody>
			</table>
		</div>
	</div>

	<script type="text/javascript">
		function upFile() {
			$("#UploadFiles")
				.ajaxSubmit(
					{
						type : 'POST',
						url : "${pageContext.request.contextPath}/Operate/uploadFile",
						contentType : "application/x-www-form-urlencoded; charset=utf-8",
						data : {
						},
						success : function(data) {
							alert(data + "成功");
							location.reload();
						},
						error : function(data) {
							alert("上传出错！");
						}
					});
		}
	</script>
</body>
</html>
