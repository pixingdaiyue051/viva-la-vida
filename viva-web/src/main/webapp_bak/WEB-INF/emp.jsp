<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="com.tequeno.dto.HtResultModel"%>
<%@ page import="java.io.File"%>
<%@ page import="java.util.List"%>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>emp</title>
    <%
        Object result = request.getAttribute("result");
        HtResultModel model = (HtResultModel) result;
        List<File> fileList = (List<File>) request.getAttribute("fileList");
    %>
</head>
<body>
    <div>
        <p><%= model.isSuccess() %></p>
        <p><%= model.getCode() %></p>
        <p><%= model.getMsg() %></p>
        <p><%= model.getData() %></p>
    </div>
    <div>-------------------------------------------</div>
    <form action="/emp/add" method="post" enctype="multipart/form-data">
        <div><input name="name" placeholder="姓名"/></div>
        <div>
            <input id="gender1" type="radio" name="gender" value="男"><label for="gender1">男</label></input>
            <input id="gender2" type="radio" name="gender" value="女"><label for="gender2">女</label></input>
        </div>
        <div><input type="file" name="avatar" placeholder="头像"/></div>
        <div><input type="submit" value="提交"/><input type="reset" value="重置"/></div>
    </form>
    <div>-------------------------------------------</div>
    <table>
        <% for(File f : fileList) { %>
        <tr>
            <td><%= f.getName() %></td>
            <td><a href="/download?file=<%=f.getName()%>"/>下载</td>
        </tr>
        <% } %>
    </table>
    <div>-------------------------------------------</div>
    <form action="/upload" method="post" enctype="multipart/form-data">
        <div><input type="file" name="file"/></div>
        <div><input type="submit" value="上传"/><input type="reset" value="重置"/></div>
    </form>
    <div>-------------------------------------------</div>
</body>
</html>