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
    <title> Login </title>
</head>
<body bgcolor="#f0f0f0">
<h1 align="center"> Logged </h1>
<h2> Host and port: <%= request.getAttribute("host") %> : <%= request.getAttribute("port") %> </h2>
<h2> Identifiant: <%= request.getParameter("identifiant") %> </h2>
<h2> Password: <%= request.getParameter("password") %> </h2>
</body>
</html>