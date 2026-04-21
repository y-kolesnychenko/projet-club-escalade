<%@ page pageEncoding="UTF-8"%>
<%@ include file="header.jsp" %>

<div class="row justify-content-center">
    <div class="col-md-5">
        <div class="card">
            <div class="card-body p-4">
                <h2 class="card-title text-center mb-4">
                    <i class="bi bi-key"></i> Mot de passe oublié
                </h2>

                <c:if test="${errorMessage != null}">
                    <div class="alert alert-danger"><i class="bi bi-exclamation-circle"></i> <c:out value="${errorMessage}" /></div>
                </c:if>

                <p class="text-muted text-center">Entrez votre adresse email. Un mot de passe temporaire vous sera envoyé.</p>

                <c:url var="forgotUrl" value="/forgot-password" />
                <form action="${forgotUrl}" method="post">
                    <sec:csrfInput />
                    <div class="mb-3">
                        <label for="email" class="form-label">Email</label>
                        <input type="email" class="form-control" id="email" name="email" required>
                    </div>
                    <button type="submit" class="btn btn-primary w-100">Envoyer</button>
                </form>

                <div class="text-center mt-3">
                    <a href="<c:url value='/login' />">Retour à la connexion</a>
                </div>
            </div>
        </div>
    </div>
</div>

<%@ include file="footer.jsp" %>
