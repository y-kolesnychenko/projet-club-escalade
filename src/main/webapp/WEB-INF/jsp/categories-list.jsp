<%@ page pageEncoding="UTF-8"%>
<%@ include file="header.jsp" %>

<h1 class="mb-2"><i class="bi bi-tags"></i> Catégories</h1>
<p class="text-muted mb-4">Liste des différentes catégories de sorties</p>

<div class="row g-3">
    <c:forEach items="${categories}" var="category">
        <div class="col-md-4 col-sm-6">
            <a href="<c:url value='/categories/${category.id}' />" class="text-decoration-none">
                <div class="card h-100">
                    <div class="card-body d-flex align-items-center justify-content-between">
                        <span class="fw-semibold text-dark">
                            <i class="bi bi-tag text-primary"></i> <c:out value="${category.name}" />
                        </span>
                        <i class="bi bi-chevron-right text-muted"></i>
                    </div>
                </div>
            </a>
        </div>
    </c:forEach>
</div>

<%@ include file="footer.jsp" %>
