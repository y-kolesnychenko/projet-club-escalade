<%@ page pageEncoding="UTF-8"%>
<%@ include file="header.jsp" %>

<h1>Mes sorties</h1>

<c:if test="${successMessage != null}">
    <p style="color:green;"><c:out value="${successMessage}" /></p>
</c:if>

<p><a href="<c:url value='/excursions/create' />">Créer une nouvelle sortie</a></p>

<c:if test="${empty excursions}">
    <p>Vous n'avez créé aucune sortie.</p>
</c:if>

<c:if test="${not empty excursions}">
    <table border="1">
        <thead>
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
                        <a href="<c:url value='/excursions/detail/${excursion.id}' />">
                            <c:out value="${excursion.name}" />
                        </a>
                    </td>
                    <td><c:out value="${excursion.category.name}" /></td>
                    <td><c:out value="${excursion.date}" /></td>
                    <td>
                        <a href="<c:url value='/excursions/edit/${excursion.id}' />">Modifier</a>
                        |
                        <form action="<c:url value='/excursions/delete/${excursion.id}' />" method="post" style="display:inline;">
                            <sec:csrfInput />
                            <button type="submit" onclick="return confirm('Supprimer cette sortie ?');">Supprimer</button>
                        </form>
                    </td>
                </tr>
            </c:forEach>
        </tbody>
    </table>
</c:if>

<%@ include file="footer.jsp" %>
