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
    <p>Annuaire Selectionné: <%= request.getAttribute("annuaireName") %>
    </p>
</div>
<div style="height: calc( 100% - 50px ); width: 100%; background-color:gray;">
    <div style="width:25%;float:left;">
        <h2>Sélectionner un annuaire</h2>
        <form action="listAnnuaire" method="POST">
            <select size="<%= request.getAttribute("annuairesSize") %>" name="annuaire">
                <%= request.getAttribute("annuairesOptions") %>
            </select>
            <br>
            <br>
            <input type="submit" name="action:Selection" value="Sélectionner"/>
        </form>
    </div>
    <div style="width:25%;float:left;">
        <h2>Créer un nouvel annuaire</h2>
        <form action="listAnnuaire" method="POST">
            <label for="annu">Nom de l'annuaire:</label>
            <input id="annu" type="text" name="annuaireName">
            <br>
            <input type="submit" name="action:Add" value="Ajouter"/>
        </form>
    </div>
    <div style="width:25%;float:left;">
        <h2>Supprimer un annuaire</h2>
        <form action="listAnnuaire" method="POST">
            <input type="submit" name="action:Delete" value="Supprimer"/>
        </form>
    </div>
    <div style="width:25%;float:left;">
        <h2>Accéder à un annuaire</h2>
        <form action="listAnnuaire" method="POST">
            <input type="submit" name="action:Access" value="Accéder"/>
        </form>
    </div>
</div>
</body>
</html>
