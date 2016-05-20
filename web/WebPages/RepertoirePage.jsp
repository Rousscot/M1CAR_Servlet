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
    <title>Repertoire: <%= request.getAttribute("annuaireName") %></title>
</head>
<body style="height: 100%;width: 100%;margin: 0;">
<div style="height:50px; width: 100%;">
    <p>Entrée sélectionné: <%= request.getAttribute("entryName") %>
    </p>
</div>
<div style="height: calc( 100% - 50px ); width: 100%; background-color:gray;">
    <div style="width:25%;float:left;">
        <h2>Sélectionner une personne</h2>
        <form action="annuaire" method="POST">
            <select size="<%= request.getAttribute("entriesSize") %>" name="entry">
                <%= request.getAttribute("entryOptions") %>
            </select>
            <br>
            <br>
            <input type="submit" name="action:Selection" value="Sélectionner"/>
        </form>
    </div>
    <div style="width:25%;float:left;">
        <h2>Créer/Modifier une nouvelle entrée</h2>
        <form action="annuaire" method="POST">
            <label for="entry">Nom:
                <input id="entry" type="text" name="name" value="<%= request.getAttribute("entryName") %>">
            </label>
            <br>
            <label for="email">Email:
                <input id="email" type="text" name="email" value="<%= request.getAttribute("email") %>">
            </label>
            <br>
            <label for="url">URL:
                <input id="url" type="text" name="url" value="<%= request.getAttribute("url") %>">
            </label>
            <br>
            <label for="descr">Description:
                <input id="descr" type="text" name="descr" value="<%= request.getAttribute("descr") %>">
            </label>
            <br>
            <input type="submit" name="action:Add" value="Ajouter"/>
            <input type="submit" name="action:Update" value="Modifier"/>
        </form>
    </div>
    <div style="width:25%;float:left;">
        <h2>Retirer une personne</h2>
        <form action="annuaire" method="POST">
            <input type="submit" name="action:Delete" value="Supprimer"/>
        </form>
    </div>
</div>
</body>
</html>
