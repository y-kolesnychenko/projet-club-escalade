<%@ page pageEncoding="UTF-8"%>
<%@ include file="header.jsp" %>

<h1><c:out value="${category.name}" /></h1>
<p>${category.excursions.size()} sorties dans cette catégorie</p>

<c:if test="${empty category.excursions}">
    <p>Aucune sortie dans cette catégorie.</p>
</c:if>

<c:if test="${not empty category.excursions}">
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
            <c:forEach items="${category.excursions}" var="excursion">
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
</c:if>

<p><a href="<c:url value='/categories' />">Retour aux catégories</a></p>

<%@ include file="footer.jsp" %>