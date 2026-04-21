<%@ page pageEncoding="UTF-8"%>
<%@ include file="header.jsp" %>

<div class="d-flex align-items-center justify-content-between mb-4">
    <h1><i class="bi bi-list-check"></i> Mes sorties</h1>
    <a href="<c:url value='/excursions/create' />" class="btn btn-primary">
        <i class="bi bi-plus-circle"></i> Nouvelle sortie
    </a>
</div>

<c:if test="${successMessage != null}">
    <div class="alert alert-success alert-dismissible fade show">
        <i class="bi bi-check-circle"></i> <c:out value="${successMessage}" />
        <button type="button" class="btn-close" data-bs-dismiss="alert"></button>
    </div>
</c:if>

<c:if test="${empty excursions}">
    <div class="card">
        <div class="card-body text-center py-5">
            <i class="bi bi-inbox fs-1 text-muted"></i>
            <p class="text-muted mt-3">Vous n'avez créé aucune sortie.</p>
            <a href="<c:url value='/excursions/create' />" class="btn btn-primary">
                <i class="bi bi-plus-circle"></i> Créer ma première sortie
            </a>
        </div>
    </div>
</c:if>

<c:if test="${not empty excursions}">
    <div class="table-responsive">
        <table class="table table-hover align-middle">
            <thead class="table-light">
                <tr>
                    <th>Nom</th>
                    <th>Catégorie</th>
                    <th>Date</th>
                    <th>Actions</th>
                </tr>
            </thead>
            <tbody>
                <c:forEach items="${excursions}" var="excursion">
                    <tr>
                        <td>
                            <a href="<c:url value='/excursions/detail/${excursion.id}' />" class="text-decoration-none fw-semibold">
                                <c:out value="${excursion.name}" />
                            </a>
                        </td>
                        <td><span class="badge bg-primary badge-category"><c:out value="${excursion.category.name}" /></span></td>
                        <td><c:out value="${excursion.date}" /></td>
                        <td>
                            <a href="<c:url value='/excursions/edit/${excursion.id}' />" class="btn btn-sm btn-outline-secondary me-1">
                                <i class="bi bi-pencil"></i> Modifier
                            </a>
                            <form action="<c:url value='/excursions/delete/${excursion.id}' />" method="post" class="d-inline">
                                <sec:csrfInput />
                                <button type="submit" class="btn btn-sm btn-outline-danger" onclick="return confirm('Supprimer cette sortie ?');">
                                    <i class="bi bi-trash"></i> Supprimer
                                </button>
                            </form>
                        </td>
                    </tr>
                </c:forEach>
            </tbody>
        </table>
    </div>
</c:if>

<%@ include file="footer.jsp" %>
