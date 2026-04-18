<%@ page pageEncoding="UTF-8"%>
<%@ include file="header.jsp" %>

<h1><c:out value="${excursion.name}" /></h1>

<table border="1">
    <tr>
        <th>Date</th>
        <td><c:out value="${excursion.date}" /></td>
    </tr>
    <tr>
        <th>Catégorie</th>
        <td><c:out value="${excursion.category.name}" /></td>
    </tr>
    <tr>
        <th>Description</th>
        <td><c:out value="${excursion.description}" /></td>
    </tr>
    <c:if test="${isAuthenticated}">
        <tr>
            <th>Site web</th>
            <td>
                <c:if test="${not empty excursion.webSite}">
                    <a href="${excursion.webSite}" target="_blank"><c:out value="${excursion.webSite}" /></a>
                </c:if>
                <c:if test="${empty excursion.webSite}">
                    Non renseigné
                </c:if>
            </td>
        </tr>
        <tr>
            <th>Créateur</th>
            <td><c:out value="${excursion.organizer.firstname}" /> <c:out value="${excursion.organizer.lastname}" /></td>
        </tr>
    </c:if>
</table>

<p>
    <a href="<c:url value='/categories/${excursion.category.id}' />">Retour à la catégorie</a>
    <sec:authorize access="isAuthenticated()">
        | <a href="<c:url value='/excursions/search' />">Rechercher</a>
    </sec:authorize>
</p>

<%@ include file="footer.jsp" %>
