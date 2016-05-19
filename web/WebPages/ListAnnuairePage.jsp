<%--
  Created by IntelliJ IDEA.
  User: Cyril
  Date: 15/05/2016
  Time: 00:41
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Annuaires</title>
</head>
<body style="height: 100%;width: 100%;margin: 0;">
    <div style="height:50px; width: 100%;">
        <p>Annuaire Selectionné: <%= request.getParameter("annuaireName") %></p>
    </div>
    <div style="height: calc( 100% - 50px ); width: 100%; background-color:gray;">
        <form>
            <select size="<%= request.getAttribute("annuairesSize") %>" name="listAnnuaire">
                <%= request.getAttribute("annuairesOptions") %>
            </select>
        </form>
    </div>
</body>
</html>
