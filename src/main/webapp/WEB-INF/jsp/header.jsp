<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%@ taglib prefix="fmt" uri="jakarta.tags.fmt" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<!DOCTYPE html>
<html lang="fr">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Club Escalade</title>
</head>
<body>
<nav>
    <a href="<c:url value='/' />">Accueil</a> |
    <a href="<c:url value='/categories' />">Catégories</a> |
    <a href="<c:url value='/excursions/search' />">Rechercher</a>
    <sec:authorize access="isAuthenticated()">
        | <a href="<c:url value='/excursions/my' />">Mes sorties</a>
        | <a href="<c:url value='/excursions/create' />">Nouvelle sortie</a>
    </sec:authorize>

    <span style="float:right;">
        <sec:authorize access="isAnonymous()">
            <a href="<c:url value='/login' />">Connexion</a>
        </sec:authorize>
        <sec:authorize access="isAuthenticated()">
            <sec:authentication property="name" /> |
            <c:url var="logoutUrl" value="/logout" />
            <form action="${logoutUrl}" method="post" style="display:inline;">
                <sec:csrfInput />
                <button type="submit">Déconnexion</button>
            </form>
        </sec:authorize>
    </span>
</nav>
<hr>
