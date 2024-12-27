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
    <p>
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
    </p>
    <div>-------------------------------------------</div>
    <form action="/user/add" method="post" enctype="multipart/form-data">
        <div><input name="name" placeholder="姓名"/></div>
        <div>
            <input id="gender1" type="radio" name="gender" value="男"><label for="gender1">男</label></input>
            <input id="gender2" type="radio" name="gender" value="女"><label for="gender2">女</label></input>
        </div>
        <div><input type="file" name="avatar" placeholder="头像"/></div>
        <div><input type="submit" value="提交"/><input type="reset" value="重置"/></div>
    </form>
    <div>-------------------------------------------</div>
    <form action="/upload" method="post" enctype="multipart/form-data">
        <div><input type="file" name="file"/></div>
        <div><input type="submit" value="上传"/>&nbsp;&nbsp;<input type="reset" value="重置"/></div>
    </form>
    <div>-------------------------------------------</div>
    <form action="/download" method="post">
        <div><input name="file"/></div>
        <div><input type="submit" value="下载"/>&nbsp;&nbsp;<input type="reset" value="重置"/></div>
    </form>
</body>
</html>