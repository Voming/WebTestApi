<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>
<c:set var="num" value="100" />
${num} 
<br>
<% int num1 = 10, num2 = 20; %>
<c:set var="sum" value="<%= num1 + num2 %>" />
${sum}
</body>
</html>