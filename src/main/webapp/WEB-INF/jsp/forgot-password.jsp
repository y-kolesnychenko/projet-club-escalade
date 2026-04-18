<%@ include file="header.jsp" %>

<h2>Mot de passe oublié</h2>

<c:if test="${errorMessage != null}">
    <p style="color:red;"><c:out value="${errorMessage}" /></p>
</c:if>

<p>Entrez votre adresse email. Un mot de passe temporaire vous sera envoyé.</p>

<c:url var="forgotUrl" value="/forgot-password" />
<form action="${forgotUrl}" method="post">
    <sec:csrfInput />
    <p>
        <label for="email">Email :</label><br>
        <input type="email" id="email" name="email" required>
    </p>
    <p>
        <button type="submit">Envoyer</button>
    </p>
</form>

<p><a href="<c:url value='/login' />">Retour à la connexion</a></p>

<%@ include file="footer.jsp" %>
