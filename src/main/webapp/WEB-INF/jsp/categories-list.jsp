<%@ page pageEncoding="UTF-8"%>
<%@ include file="header.jsp" %>

<h1>Catégories</h1>
<p>Liste des différentes catégories de sorties</p>
<ul>
    <c:forEach items="${categories}" var="category">
        <li>
            <a href="<c:url value='/categories/${category.id}' />">
                <c:out value="${category.name}" />
            </a>
        </li>
    </c:forEach>
</ul>

<%@ include file="footer.jsp" %>