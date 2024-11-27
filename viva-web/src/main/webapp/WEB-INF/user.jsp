<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="com.tequeno.dto.HtResultModel"%>
<%@ page import="java.time.LocalDateTime"%>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>user</title>
</head>
<body>
    <h2><%= LocalDateTime.now()%></h2>
    <% 
        Object result = request.getAttribute("result");
        HtResultModel model = (HtResultModel) result;
        Object idx = request.getAttribute("idx");
    %>
    <%= model.isSuccess() %> </br>
    <%= model.getCode() %> </br>
    <%= model.getMsg() %> </br>
    <%= model.getData() %> </br>
    <%= idx %> </br>
</body>
</html>