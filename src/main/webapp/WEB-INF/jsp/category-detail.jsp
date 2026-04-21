<%@ page pageEncoding="UTF-8"%>
<%@ include file="header.jsp" %>

<h1><c:out value="${category.name}" /></h1>
<p>${excursionsPage.totalElements} sorties dans cette catégorie</p>

<c:if test="${excursionsPage.totalElements == 0}">
    <p>Aucune sortie dans cette catégorie.</p>
</c:if>

<c:if test="${excursionsPage.totalElements > 0}">
    <table border="1">
        <thead>
            <tr>
                <th>Nom</th>
                <th>Date</th>
                <th>Description</th>
                <th>Détail</th>
            </tr>
        </thead>
        <tbody>
            <c:forEach items="${excursionsPage.content}" var="excursion">
                <tr>
                    <td><c:out value="${excursion.name}" /></td>
                    <td><c:out value="${excursion.date}" /></td>
                    <td><c:out value="${excursion.description}" /></td>
                    <td>
                        <a href="<c:url value='/excursions/detail/${excursion.id}' />">Voir</a>
                    </td>
                </tr>
            </c:forEach>
        </tbody>
    </table>

    <p>Page ${excursionsPage.number + 1} / ${excursionsPage.totalPages}</p>
    <p>
        <c:if test="${excursionsPage.number > 0}">
            <a href="<c:url value='/categories/${category.id}?page=${excursionsPage.number - 1}' />">Précédent</a> |
        </c:if>
        <c:forEach begin="0" end="${excursionsPage.totalPages - 1}" var="i">
            <c:choose>
                <c:when test="${i == excursionsPage.number}">
                    <strong>${i + 1}</strong>
                </c:when>
                <c:otherwise>
                    <a href="<c:url value='/categories/${category.id}?page=${i}' />">${i + 1}</a>
                </c:otherwise>
            </c:choose>
        </c:forEach>
        <c:if test="${excursionsPage.number < excursionsPage.totalPages - 1}">
            | <a href="<c:url value='/categories/${category.id}?page=${excursionsPage.number + 1}' />">Suivant</a>
        </c:if>
    </p>
</c:if>

<p><a href="<c:url value='/categories' />">Retour aux catégories</a></p>

<%@ include file="footer.jsp" %>