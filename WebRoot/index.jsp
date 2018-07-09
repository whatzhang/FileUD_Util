<%@ page language="java" import="java.util.*"
	pageEncoding="UTF-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<title>文件操作</title>
</head>
<body>
	<%
		request.getRequestDispatcher("/getFileList").forward(request, response);
	%>
</body>
</html>
