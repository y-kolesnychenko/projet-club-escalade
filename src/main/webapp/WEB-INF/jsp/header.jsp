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
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet">
    <link href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.11.3/font/bootstrap-icons.min.css" rel="stylesheet">
    <c:url var="cssUrl" value="/css/style.css" />
    <link rel="stylesheet" href="${cssUrl}">
</head>
<body>
<nav class="navbar navbar-expand-lg navbar-dark bg-dark">
    <div class="container">
        <a class="navbar-brand" href="<c:url value='/' />">
            <i class="bi bi-mountain"></i> Club Escalade
        </a>
        <button class="navbar-toggler" type="button" data-bs-toggle="collapse" data-bs-target="#navbarNav">
            <span class="navbar-toggler-icon"></span>
        </button>
        <div class="collapse navbar-collapse" id="navbarNav">
            <ul class="navbar-nav me-auto">
                <li class="nav-item">
                    <a class="nav-link" href="<c:url value='/categories' />">
                        <i class="bi bi-tags"></i> Catégories
                    </a>
                </li>
                <li class="nav-item">
                    <a class="nav-link" href="<c:url value='/excursions/search' />">
                        <i class="bi bi-search"></i> Rechercher
                    </a>
                </li>
                <sec:authorize access="isAuthenticated()">
                    <li class="nav-item">
                        <a class="nav-link" href="<c:url value='/excursions/my' />">
                            <i class="bi bi-list-check"></i> Mes sorties
                        </a>
                    </li>
                    <li class="nav-item">
                        <a class="nav-link" href="<c:url value='/excursions/create' />">
                            <i class="bi bi-plus-circle"></i> Nouvelle sortie
                        </a>
                    </li>
                </sec:authorize>
            </ul>
            <ul class="navbar-nav">
                <sec:authorize access="isAnonymous()">
                    <li class="nav-item">
                        <a class="nav-link" href="<c:url value='/login' />">
                            <i class="bi bi-box-arrow-in-right"></i> Connexion
                        </a>
                    </li>
                </sec:authorize>
                <sec:authorize access="isAuthenticated()">
                    <li class="nav-item">
                        <span class="nav-link text-light">
                            <i class="bi bi-person-circle"></i> <sec:authentication property="name" />
                        </span>
                    </li>
                    <li class="nav-item">
                        <c:url var="logoutUrl" value="/logout" />
                        <form action="${logoutUrl}" method="post" class="d-inline">
                            <sec:csrfInput />
                            <button type="submit" class="nav-link btn btn-link text-light">
                                <i class="bi bi-box-arrow-right"></i> Déconnexion
                            </button>
                        </form>
                    </li>
                </sec:authorize>
            </ul>
        </div>
    </div>
</nav>
<div class="container my-4">
