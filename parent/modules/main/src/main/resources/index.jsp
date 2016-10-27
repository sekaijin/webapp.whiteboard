<%@page import="fr.sekaijin.osgi.web.main.api.MenuItem"%>
<%@page import="fr.sekaijin.osgi.web.main.registrar.MenuItemRegistrar"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Main Menu</title>
</head>
<body><%=request.getContextPath()%> 
	<ul>
		<%
			for (String module : MenuItemRegistrar.getMenuItems().keySet()) {
				for (MenuItem menuItem : MenuItemRegistrar.getMenuItems().get(module)) {
		%><li><img src="<%=module%>/static/<%=menuItem.getIcon()%>" />
			<a href="<%=module%>/<%=menuItem.getPath()%>"><%=menuItem.getModuleName()%>=><%=menuItem.getKey()%></a></li>
		<%
				}
			}%>
	</ul>
</body>
</html>