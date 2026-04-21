<%@ page pageEncoding="UTF-8"%>
<%@ include file="header.jsp" %>

<c:choose>
    <c:when test="${isNew}">
        <h1 class="mb-4"><i class="bi bi-plus-circle"></i> Créer une sortie</h1>
        <c:url var="formAction" value="/excursions/create" />
    </c:when>
    <c:otherwise>
        <h1 class="mb-4"><i class="bi bi-pencil"></i> Modifier la sortie</h1>
        <c:url var="formAction" value="/excursions/edit/${excursion.id}" />
    </c:otherwise>
</c:choose>

<div class="row justify-content-center">
    <div class="col-md-8">
        <div class="card">
            <div class="card-body">
                <form:form method="POST" action="${formAction}" modelAttribute="excursion">

                    <form:errors path="*" cssClass="alert alert-danger" element="div" />

                    <div class="mb-3">
                        <label for="name" class="form-label">Nom</label>
                        <form:input path="name" id="name" cssClass="form-control" required="required" />
                        <form:errors path="name" cssClass="text-danger small" />
                    </div>

                    <div class="mb-3">
                        <label for="description" class="form-label">Description</label>
                        <form:textarea path="description" id="description" cssClass="form-control" rows="4" required="required" />
                        <form:errors path="description" cssClass="text-danger small" />
                    </div>

                    <div class="mb-3">
                        <label for="webSite" class="form-label">Site web</label>
                        <form:input path="webSite" id="webSite" cssClass="form-control" placeholder="https://..." />
                        <form:errors path="webSite" cssClass="text-danger small" />
                    </div>

                    <div class="mb-3">
                        <label for="date" class="form-label">Date</label>
                        <form:input path="date" id="date" type="date" cssClass="form-control" required="required" />
                        <form:errors path="date" cssClass="text-danger small" />
                    </div>

                    <div class="mb-3">
                        <label for="categoryId" class="form-label">Catégorie</label>
                        <select id="categoryId" name="categoryId" class="form-select" required>
                            <option value="">-- Choisir --</option>
                            <c:forEach items="${categories}" var="cat">
                                <option value="${cat.id}" ${excursion.category != null && excursion.category.id == cat.id ? 'selected' : ''}>
                                    <c:out value="${cat.name}" />
                                </option>
                            </c:forEach>
                        </select>
                    </div>

                    <div class="d-flex gap-2">
                        <button type="submit" class="btn btn-primary">
                            <c:choose>
                                <c:when test="${isNew}"><i class="bi bi-plus-circle"></i> Créer</c:when>
                                <c:otherwise><i class="bi bi-check-circle"></i> Enregistrer</c:otherwise>
                            </c:choose>
                        </button>
                        <a href="<c:url value='/excursions/my' />" class="btn btn-outline-secondary">Annuler</a>
                    </div>

                </form:form>
            </div>
        </div>
    </div>
</div>

<%@ include file="footer.jsp" %>
