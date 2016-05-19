<%--
		  Created by IntelliJ IDEA.
		  User: Cyril
		  Date: 13/05/2016
		  Time: 11:01
		  To change this template use File | Settings | File Templates.
		--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!doctype html public "-//w3c//dtd html 4.0 transitional//en">
<html>
<head>
    <title> LoginServlet </title>
</head>
<body bgcolor="#f0f0f0">
<h1 align="center"> LoginServlet </h1>
<h2> Host and port: <%= request.getAttribute("host") %> : <%= request.getAttribute("port") %> </h2>
<%= request.getAttribute("Error") %>
<form action="Login" method="POST">
    <label for="ident">Identifiant:</label>
    <input id="ident" type="text" name="identifiant">
    <br/>
    <label for="pass">Password:</label>
    <input id="pass" type="password" name="password"/>
    <input type="submit" value="Submit"/>
</form>
</body>
</html>