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
    <h2>${results.totalElements} résultat(s)</h2>

    <c:if test="${results.totalElements == 0}">
        <p>Aucune sortie ne correspond à vos critères.</p>
    </c:if>

    <c:if test="${results.totalElements > 0}">
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
                <c:forEach items="${results.content}" var="excursion">
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

        <p>Page ${results.number + 1} / ${results.totalPages}</p>
        <p>
            <c:if test="${results.number > 0}">
                <a href="<c:url value='/excursions/search?name=${searchName}&categoryId=${searchCategoryId}&startDate=${searchStartDate}&endDate=${searchEndDate}&keyword=${searchKeyword}&page=${results.number - 1}' />">Précédent</a> |
            </c:if>
            <c:forEach begin="0" end="${results.totalPages - 1}" var="i">
                <c:choose>
                    <c:when test="${i == results.number}">
                        <strong>${i + 1}</strong>
                    </c:when>
                    <c:otherwise>
                        <a href="<c:url value='/excursions/search?name=${searchName}&categoryId=${searchCategoryId}&startDate=${searchStartDate}&endDate=${searchEndDate}&keyword=${searchKeyword}&page=${i}' />">${i + 1}</a>
                    </c:otherwise>
                </c:choose>
            </c:forEach>
            <c:if test="${results.number < results.totalPages - 1}">
                | <a href="<c:url value='/excursions/search?name=${searchName}&categoryId=${searchCategoryId}&startDate=${searchStartDate}&endDate=${searchEndDate}&keyword=${searchKeyword}&page=${results.number + 1}' />">Suivant</a>
            </c:if>
        </p>
    </c:if>
</c:if>

<%@ include file="footer.jsp" %>