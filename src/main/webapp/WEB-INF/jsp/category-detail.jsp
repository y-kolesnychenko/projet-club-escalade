<%@ page pageEncoding="UTF-8"%>
<%@ include file="header.jsp" %>

<nav aria-label="breadcrumb">
    <ol class="breadcrumb">
        <li class="breadcrumb-item"><a href="<c:url value='/categories' />">Catégories</a></li>
        <li class="breadcrumb-item active"><c:out value="${category.name}" /></li>
    </ol>
</nav>

<div class="d-flex align-items-center justify-content-between mb-4">
    <div>
        <h1><c:out value="${category.name}" /></h1>
        <span class="badge bg-primary badge-category">${excursionsPage.totalElements} sorties</span>
    </div>
</div>

<c:if test="${excursionsPage.totalElements == 0}">
    <div class="alert alert-info">
        <i class="bi bi-info-circle"></i> Aucune sortie dans cette catégorie.
    </div>
</c:if>

<c:if test="${excursionsPage.totalElements > 0}">
    <div class="row row-cols-1 row-cols-md-2 row-cols-lg-4 g-4">
        <c:forEach items="${excursionsPage.content}" var="excursion">
            <div class="col">
                <div class="card h-100">
                    <div class="card-body d-flex flex-column">
                        <h5 class="card-title fw-semibold text-truncate" title="${excursion.name}">
                            <c:out value="${excursion.name}" />
                        </h5>
                        <h6 class="card-subtitle mb-2 text-muted">
                            <i class="bi bi-calendar"></i> <c:out value="${excursion.date}" />
                        </h6>
                        <p class="card-text text-muted small">
                            <c:out value="${excursion.description}" />
                        </p>
                        <a href="<c:url value='/excursions/detail/${excursion.id}' />" class="btn btn-sm btn-outline-primary mt-auto">
                            Voir <i class="bi bi-arrow-right"></i>
                        </a>
                    </div>
                </div>
            </div>
        </c:forEach>
    </div>

    <c:if test="${excursionsPage.totalPages > 1}">
        <nav aria-label="Pagination" class="mt-4">
            <ul class="pagination justify-content-center">
                <li class="page-item ${excursionsPage.number == 0 ? 'disabled' : ''}">
                    <a class="page-link" href="<c:url value='/categories/${category.id}?page=${excursionsPage.number - 1}' />">
                        <i class="bi bi-chevron-left"></i>
                    </a>
                </li>

                <c:forEach begin="0" end="${excursionsPage.totalPages - 1}" var="i">
                    <c:choose>
                        <c:when test="${i == 0 or i == excursionsPage.totalPages - 1 or (i >= excursionsPage.number - 2 and i <= excursionsPage.number + 2)}">
                            <li class="page-item ${i == excursionsPage.number ? 'active' : ''}">
                                <a class="page-link" href="<c:url value='/categories/${category.id}?page=${i}' />">${i + 1}</a>
                            </li>
                        </c:when>
                        <c:when test="${i == 1 or i == excursionsPage.totalPages - 2}">
                            <li class="page-item disabled"><span class="page-link">...</span></li>
                        </c:when>
                    </c:choose>
                </c:forEach>

                <li class="page-item ${excursionsPage.number >= excursionsPage.totalPages - 1 ? 'disabled' : ''}">
                    <a class="page-link" href="<c:url value='/categories/${category.id}?page=${excursionsPage.number + 1}' />">
                        <i class="bi bi-chevron-right"></i>
                    </a>
                </li>
            </ul>
        </nav>
    </c:if>
</c:if>

<%@ include file="footer.jsp" %>