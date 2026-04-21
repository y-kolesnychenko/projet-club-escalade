<%@ page pageEncoding="UTF-8"%>
<%@ include file="header.jsp" %>

<h1 class="mb-4"><i class="bi bi-search"></i> Rechercher des sorties</h1>

<div class="card mb-4">
    <div class="card-body">
        <form action="<c:url value='/excursions/search' />" method="get">
            <input type="hidden" name="submitted" value="true">
            <div class="row g-3">
                <div class="col-md-6">
                    <label for="name" class="form-label">Nom</label>
                    <input type="text" class="form-control" id="name" name="name" value="${searchName}" placeholder="Rechercher par nom...">
                </div>
                <div class="col-md-6">
                    <label for="categoryId" class="form-label">Catégorie</label>
                    <select class="form-select" id="categoryId" name="categoryId">
                        <option value="">-- Toutes --</option>
                        <c:forEach items="${categories}" var="cat">
                            <option value="${cat.id}" ${cat.id == searchCategoryId ? 'selected' : ''}>
                                <c:out value="${cat.name}" />
                            </option>
                        </c:forEach>
                    </select>
                </div>
                <div class="col-md-3">
                    <label for="startDate" class="form-label">Date début</label>
                    <input type="date" class="form-control" id="startDate" name="startDate" value="${searchStartDate}">
                </div>
                <div class="col-md-3">
                    <label for="endDate" class="form-label">Date fin</label>
                    <input type="date" class="form-control" id="endDate" name="endDate" value="${searchEndDate}">
                </div>
                <div class="col-md-6">
                    <label for="keyword" class="form-label">Mot-clé (description)</label>
                    <input type="text" class="form-control" id="keyword" name="keyword" value="${searchKeyword}" placeholder="Rechercher dans la description...">
                </div>
                <div class="col-12">
                    <button type="submit" class="btn btn-primary">
                        <i class="bi bi-search"></i> Rechercher
                    </button>
                </div>
            </div>
        </form>
    </div>
</div>

<c:if test="${results != null}">
    <div class="d-flex align-items-center justify-content-between mb-3">
        <h2>Résultats</h2>
        <span class="badge bg-secondary">${results.totalElements} résultat(s)</span>
    </div>

    <c:if test="${results.totalElements == 0}">
        <div class="alert alert-info">
            <i class="bi bi-info-circle"></i> Aucune sortie ne correspond à vos critères.
        </div>
    </c:if>

    <c:if test="${results.totalElements > 0}">
        <div class="row row-cols-1 row-cols-md-2 row-cols-lg-4 g-4">
            <c:forEach items="${results.content}" var="excursion">
                <div class="col">
                    <div class="card h-100">
                        <div class="card-body d-flex flex-column">
                            <h5 class="card-title fw-semibold text-truncate"><c:out value="${excursion.name}" /></h5>
                            <div class="mb-2">
                                <span class="badge bg-primary badge-category"><c:out value="${excursion.category.name}" /></span>
                            </div>
                            <h6 class="card-subtitle mb-2 text-muted"><i class="bi bi-calendar"></i> <c:out value="${excursion.date}" /></h6>
                            <p class="card-text text-muted small text-truncate">
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

        <c:if test="${results.totalPages > 1}">
            <nav aria-label="Pagination" class="mt-4">
                <ul class="pagination justify-content-center">
                    <li class="page-item ${results.number == 0 ? 'disabled' : ''}">
                        <a class="page-link" href="<c:url value='/excursions/search?name=${searchName}&categoryId=${searchCategoryId}&startDate=${searchStartDate}&endDate=${searchEndDate}&keyword=${searchKeyword}&page=${results.number - 1}' />">
                            <i class="bi bi-chevron-left"></i>
                        </a>
                    </li>

                    <c:forEach begin="0" end="${results.totalPages - 1}" var="i">
                        <c:choose>
                            <c:when test="${i == 0 or i == results.totalPages - 1 or (i >= results.number - 2 and i <= results.number + 2)}">
                                <li class="page-item ${i == results.number ? 'active' : ''}">
                                    <a class="page-link" href="<c:url value='/excursions/search?name=${searchName}&categoryId=${searchCategoryId}&startDate=${searchStartDate}&endDate=${searchEndDate}&keyword=${searchKeyword}&page=${i}' />">${i + 1}</a>
                                </li>
                            </c:when>
                            <c:when test="${i == 1 or i == results.totalPages - 2}">
                                <li class="page-item disabled"><span class="page-link">...</span></li>
                            </c:when>
                        </c:choose>
                    </c:forEach>

                    <li class="page-item ${results.number >= results.totalPages - 1 ? 'disabled' : ''}">
                        <a class="page-link" href="<c:url value='/excursions/search?name=${searchName}&categoryId=${searchCategoryId}&startDate=${searchStartDate}&endDate=${searchEndDate}&keyword=${searchKeyword}&page=${results.number + 1}' />">
                            <i class="bi bi-chevron-right"></i>
                        </a>
                    </li>
                </ul>
            </nav>
        </c:if>
    </c:if>
</c:if>

<%@ include file="footer.jsp" %>