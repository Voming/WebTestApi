<%@page import="kh.mclass.jdbc.model.vo.Dept"%>
<%@page import="java.util.List"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>
	<br>
	<%
	List<Dept> list = (List<Dept>) request.getAttribute("deptlist");

	for (Dept vo : list) {
	%>
	<%=vo.getDeptno()%>
	|
	<%=vo.getDname()%>
	|
	<%=vo.getLoc()%>
	<br>
	<%
	}
	%>
</body>
</html>