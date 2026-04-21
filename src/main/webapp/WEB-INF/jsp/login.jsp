<%@ page pageEncoding="UTF-8"%>
<%@ include file="header.jsp" %>

<h2>Connexion</h2>

<c:if test="${errorMessage != null}">
    <p style="color:red;"><c:out value="${errorMessage}" /></p>
</c:if>
<c:if test="${successMessage != null}">
    <p style="color:green;"><c:out value="${successMessage}" /></p>
</c:if>

<c:url var="loginProcessUrl" value="/login" />
<form action="${loginProcessUrl}" method="post">
    <sec:csrfInput />
    <p>
        <label for="username">Email :</label><br>
        <input type="email" id="username" name="username" required>
    </p>
    <p>
        <label for="password">Mot de passe :</label><br>
        <input type="password" id="password" name="password" required>
    </p>
    <p>
        <button type="submit">Se connecter</button>
    </p>
</form>
<p><a href="<c:url value='/register' />">Pas encore de compte ? S'inscrire</a></p>
<p><a href="<c:url value='/forgot-password' />">Mot de passe oublié ?</a></p>

<%@ include file="footer.jsp" %>
