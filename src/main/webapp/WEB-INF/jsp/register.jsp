<%@ page pageEncoding="UTF-8"%>
<%@ include file="header.jsp" %>

<h2>Créer un compte</h2>

<c:if test="${errorMessage != null}">
    <p style="color:red;"><c:out value="${errorMessage}" /></p>
</c:if>

<form:form method="POST" action="${pageContext.request.contextPath}/register" modelAttribute="member">

    <form:errors path="*" cssStyle="color:red;" element="div" />

    <p>
        <label for="firstname">Prénom :</label><br>
        <form:input path="firstname" id="firstname" required="required" />
        <form:errors path="firstname" cssStyle="color:red;" />
    </p>

    <p>
        <label for="lastname">Nom :</label><br>
        <form:input path="lastname" id="lastname" required="required" />
        <form:errors path="lastname" cssStyle="color:red;" />
    </p>

    <p>
        <label for="email">Email :</label><br>
        <form:input path="email" id="email" type="email" required="required" />
        <form:errors path="email" cssStyle="color:red;" />
    </p>

    <p>
        <label for="password">Mot de passe :</label><br>
        <form:password path="password" id="password" required="required" />
        <form:errors path="password" cssStyle="color:red;" />
    </p>

    <p>
        <button type="submit">Créer le compte</button>
    </p>

</form:form>

<p><a href="<c:url value='/login' />">Déjà un compte ? Se connecter</a></p>

<%@ include file="footer.jsp" %>
