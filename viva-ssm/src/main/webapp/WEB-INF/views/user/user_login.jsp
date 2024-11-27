<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
  	<%@ include file="/WEB-INF/includes/common.jsp" %>
	<title><spring:message code="_user_login_page"/></title>
  </head>
  <body>
  	<a href="${webroot}/base/i18nNew?language=zw">中文</a>
  	<a href="${webroot}/base/i18nNew?language=ft">繁體</a>
  	<a href="${webroot}/base/i18nNew?language=yw">English</a>
  	<form action="${webroot}/um/user/login" method="POST">
  		<table>
    	<tr>
	    	<td><span id="_user_name"><spring:message code="_user_name"/>:</span></td>
	    	<td><input type="text" name="username"/></td>
    	</tr>
    	<tr>
	    	<td><span id="_pwd"><spring:message code="_pwd"/>:</span></td>
	    	<td><input type="text" name="pwd"/></td>
    	</tr>
    	<tr>
	    	<td><input id="login_btn" type="submit" value="<spring:message code='_login'/>"/></td>
	    	<td><input id="rst_btn" type="reset" value="<spring:message code='_rst'/>"/></td>
    	</tr>
    	</table>
  	</form>
  </body>
</html>