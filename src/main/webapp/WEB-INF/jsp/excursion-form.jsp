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

<form action="${formAction}" method="post">
    <sec:csrfInput />

    <p>
        <label for="name">Nom :</label><br>
        <input type="text" id="name" name="name" value="<c:out value='${excursion.name}' />" required>
    </p>

    <p>
        <label for="description">Description :</label><br>
        <textarea id="description" name="description" rows="4" cols="50" required><c:out value="${excursion.description}" /></textarea>
    </p>

    <p>
        <label for="webSite">Site web :</label><br>
        <input type="url" id="webSite" name="webSite" value="<c:out value='${excursion.webSite}' />">
    </p>

    <p>
        <label for="date">Date :</label><br>
        <input type="date" id="date" name="date" value="${excursion.date}" required>
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
</form>

<%@ include file="footer.jsp" %>
