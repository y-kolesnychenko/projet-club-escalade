<%@ page pageEncoding="UTF-8"%>
<%@ include file="header.jsp" %>

<h2>Créer un compte</h2>

<c:if test="${errorMessage != null}">
    <p style="color:red;"><c:out value="${errorMessage}" /></p>
</c:if>

<form action="<c:url value='/register' />" method="post">
    <sec:csrfInput />
    <p>
        <label for="firstname">Prénom :</label><br>
        <input type="text" id="firstname" name="firstname" required>
    </p>
    <p>
        <label for="lastname">Nom :</label><br>
        <input type="text" id="lastname" name="lastname" required>
    </p>
    <p>
        <label for="email">Email :</label><br>
        <input type="email" id="email" name="email" required>
    </p>
    <p>
        <label for="password">Mot de passe :</label><br>
        <input type="password" id="password" name="password" required>
    </p>
    <p>
        <button type="submit">Créer le compte</button>
    </p>
</form>

<p><a href="<c:url value='/login' />">Déjà un compte ? Se connecter</a></p>

<%@ include file="footer.jsp" %>