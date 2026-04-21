<%@ page pageEncoding="UTF-8"%>
<%@ include file="header.jsp" %>

<nav aria-label="breadcrumb">
    <ol class="breadcrumb">
        <li class="breadcrumb-item"><a href="<c:url value='/categories' />">Catégories</a></li>
        <li class="breadcrumb-item"><a href="<c:url value='/categories/${excursion.category.id}' />"><c:out value="${excursion.category.name}" /></a></li>
        <li class="breadcrumb-item active"><c:out value="${excursion.name}" /></li>
    </ol>
</nav>

<div class="d-flex align-items-start justify-content-between mb-3">
    <h1><c:out value="${excursion.name}" /></h1>
    <span class="badge bg-primary badge-category mt-2">
        <c:out value="${excursion.category.name}" />
    </span>
</div>

<div class="info-bar mb-4">
    <div class="info-bar-item">
        <i class="bi bi-calendar-event fs-4 text-primary"></i>
        <div>
            <strong>Date</strong><br>
            <c:out value="${excursion.date}" />
        </div>
    </div>
    <div class="info-bar-item">
        <i class="bi bi-tag fs-4 text-primary"></i>
        <div>
            <strong>Catégorie</strong><br>
            <c:out value="${excursion.category.name}" />
        </div>
    </div>
    <c:if test="${isAuthenticated}">
        <div class="info-bar-item">
            <i class="bi bi-person fs-4 text-primary"></i>
            <div>
                <strong>Créateur</strong><br>
                <c:out value="${excursion.organizer.firstname}" /> <c:out value="${excursion.organizer.lastname}" />
            </div>
        </div>
    </c:if>
</div>

<div class="row g-4">
    <div class="col-md-8">
        <div class="card">
            <div class="card-body">
                <h4 class="card-title mb-3">Description</h4>
                <p class="card-text"><c:out value="${excursion.description}" /></p>
            </div>
        </div>
    </div>
    <div class="col-md-4">
        <c:if test="${isAuthenticated}">
            <div class="card">
                <div class="card-body">
                    <h5 class="card-title mb-3">Informations</h5>
                    <c:if test="${not empty excursion.webSite}">
                        <p>
                            <i class="bi bi-globe text-primary"></i>
                            <a href="${excursion.webSite}" target="_blank"><c:out value="${excursion.webSite}" /></a>
                        </p>
                    </c:if>
                    <c:if test="${empty excursion.webSite}">
                        <p class="text-muted">
                            <i class="bi bi-globe"></i> Pas de site web renseigné
                        </p>
                    </c:if>
                    <p>
                        <i class="bi bi-person text-primary"></i>
                        Organisé par <strong><c:out value="${excursion.organizer.firstname}" /> <c:out value="${excursion.organizer.lastname}" /></strong>
                    </p>
                </div>
            </div>
        </c:if>
        <c:if test="${not isAuthenticated}">
            <div class="card">
                <div class="card-body text-center">
                    <p class="text-muted">Connectez-vous pour voir toutes les informations</p>
                    <a href="<c:url value='/login' />" class="btn btn-primary">
                        <i class="bi bi-box-arrow-in-right"></i> Se connecter
                    </a>
                </div>
            </div>
        </c:if>
    </div>
</div>

<%@ include file="footer.jsp" %>
