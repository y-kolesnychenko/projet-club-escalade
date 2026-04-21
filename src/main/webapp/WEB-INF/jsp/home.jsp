<%@ page pageEncoding="UTF-8"%>
<%@ include file="header.jsp" %>

<div class="hero-section text-center">
    <h1><i class="bi bi-mountain"></i> Bienvenue au Club Escalade</h1>
    <p class="lead mt-3">Découvrez nos sorties et rejoignez la communauté</p>
</div>

<div class="row g-4">
    <div class="col-md-4">
        <div class="card stat-card h-100">
            <div class="card-body d-flex flex-column">
                <h2>${categoriesCount}</h2>
                <p class="text-muted mb-3">Catégories</p>
                <a href="<c:url value='/categories' />" class="btn btn-primary mt-auto">
                    <i class="bi bi-tags"></i> Voir les catégories
                </a>
            </div>
        </div>
    </div>
    <div class="col-md-4">
        <div class="card stat-card h-100">
            <div class="card-body d-flex flex-column">
                <h2>${excursionsCount}</h2>
                <p class="text-muted mb-3">Sorties</p>
                <a href="<c:url value='/excursions/search' />" class="btn btn-primary mt-auto">
                    <i class="bi bi-search"></i> Rechercher
                </a>
            </div>
        </div>
    </div>
    <div class="col-md-4">
        <div class="card stat-card h-100">
            <div class="card-body d-flex flex-column">
                <h2>${membersCount}</h2>
                <p class="text-muted mb-3">Membres</p>
                <div class="mt-auto">
                    <sec:authorize access="isAnonymous()">
                        <a href="<c:url value='/register' />" class="btn btn-outline-primary w-100">
                            <i class="bi bi-person-plus"></i> Rejoindre
                        </a>
                    </sec:authorize>
                </div>
            </div>
        </div>
    </div>
</div>

<%@ include file="footer.jsp" %>
