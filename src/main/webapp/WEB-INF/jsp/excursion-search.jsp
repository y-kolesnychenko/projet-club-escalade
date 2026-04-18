<%@ page pageEncoding="UTF-8"%>
<%@ include file="header.jsp" %>

<h1>Rechercher des sorties</h1>

<form action="<c:url value='/excursions/search' />" method="get">
    <p>
        <label for="name">Nom :</label><br>
        <input type="text" id="name" name="name" value="${searchName}">
    </p>
    <p>
        <label for="categoryId">Catégorie :</label><br>
        <select id="categoryId" name="categoryId">
            <option value="">-- Toutes --</option>
            <c:forEach items="${categories}" var="cat">
                <option value="${cat.id}" ${cat.id == searchCategoryId ? 'selected' : ''}>
                    <c:out value="${cat.name}" />
                </option>
            </c:forEach>
        </select>
    </p>
    <p>
        <label for="startDate">Date début :</label><br>
        <input type="date" id="startDate" name="startDate" value="${searchStartDate}">
    </p>
    <p>
        <label for="endDate">Date fin :</label><br>
        <input type="date" id="endDate" name="endDate" value="${searchEndDate}">
    </p>
    <p>
        <label for="keyword">Mot-clé (description) :</label><br>
        <input type="text" id="keyword" name="keyword" value="${searchKeyword}">
    </p>
    <p>
        <button type="submit">Rechercher</button>
    </p>
</form>

<c:if test="${results != null}">
    <hr>
    <h2>${results.size()} résultat(s)</h2>

    <c:if test="${empty results}">
        <p>Aucune sortie ne correspond à vos critères.</p>
    </c:if>

    <c:if test="${not empty results}">
        <table border="1">
            <thead>
                <tr>
                    <th>Nom</th>
                    <th>Catégorie</th>
                    <th>Date</th>
                    <th>Description</th>
                    <th>Détail</th>
                </tr>
            </thead>
            <tbody>
                <c:forEach items="${results}" var="excursion">
                    <tr>
                        <td><c:out value="${excursion.name}" /></td>
                        <td><c:out value="${excursion.category.name}" /></td>
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
</c:if>

<%@ include file="footer.jsp" %>
