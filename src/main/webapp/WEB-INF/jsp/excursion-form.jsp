<%@ page pageEncoding="UTF-8"%>
<%@ include file="header.jsp" %>

<c:choose>
    <c:when test="${isNew}">
        <h1>Créer une sortie</h1>
        <c:url var="formAction" value="/excursions/create" />
    </c:when>
    <c:otherwise>
        <h1>Modifier la sortie</h1>
        <c:url var="formAction" value="/excursions/edit/${excursion.id}" />
    </c:otherwise>
</c:choose>

<form:form method="POST" action="${formAction}" modelAttribute="excursion">

    <form:errors path="*" cssStyle="color:red;" element="div" />

    <p>
        <label for="name">Nom :</label><br>
        <form:input path="name" id="name" required="required" />
        <form:errors path="name" cssStyle="color:red;" />
    </p>

    <p>
        <label for="description">Description :</label><br>
        <form:textarea path="description" id="description" rows="4" cols="50" required="required" />
        <form:errors path="description" cssStyle="color:red;" />
    </p>

    <p>
        <label for="webSite">Site web :</label><br>
        <form:input path="webSite" id="webSite" />
        <form:errors path="webSite" cssStyle="color:red;" />
    </p>

    <p>
        <label for="date">Date :</label><br>
        <form:input path="date" id="date" type="date" required="required" />
        <form:errors path="date" cssStyle="color:red;" />
    </p>

    <p>
        <label for="categoryId">Catégorie :</label><br>
        <select id="categoryId" name="categoryId" required>
            <option value="">-- Choisir --</option>
            <c:forEach items="${categories}" var="cat">
                <option value="${cat.id}" ${excursion.category != null && excursion.category.id == cat.id ? 'selected' : ''}>
                    <c:out value="${cat.name}" />
                </option>
            </c:forEach>
        </select>
    </p>

    <p>
        <button type="submit">
            <c:choose>
                <c:when test="${isNew}">Créer</c:when>
                <c:otherwise>Enregistrer</c:otherwise>
            </c:choose>
        </button>
        <a href="<c:url value='/excursions/my' />">Annuler</a>
    </p>

</form:form>

<%@ include file="footer.jsp" %>
