<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
  <%@ include file="/WEB-INF/includes/common.jsp" %>
  <title>11111</title>
  </head>
  <body>
  	<c:forEach var="u" items="${userList}">
  		<p>${u.userName}&nbsp;&nbsp;&nbsp;&nbsp;${u.trueName}</p>
  	</c:forEach>
  </body>
</html>
