<%@ page pageEncoding="UTF-8"%>
<%@ include file="header.jsp" %>

<h1>Bienvenue au Club Escalade</h1>
<p>Découvrez nos sorties et rejoignez la communauté</p>

<ul>
    <li><strong>${categoriesCount}</strong> catégories - <a href="<c:url value='/categories' />">Voir</a></li>
    <li><strong>${excursionsCount}</strong> sorties - <a href="<c:url value='/excursions/search' />">Rechercher</a></li>
    <li><strong>${membersCount}</strong> membres</li>
</ul>

<%@ include file="footer.jsp" %>
