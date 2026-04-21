<%@ page pageEncoding="UTF-8"%>
<%@ include file="header.jsp" %>

<div class="row justify-content-center">
    <div class="col-md-5">
        <div class="card">
            <div class="card-body p-4">
                <h2 class="card-title text-center mb-4">
                    <i class="bi bi-box-arrow-in-right"></i> Connexion
                </h2>

                <c:if test="${errorMessage != null}">
                    <div class="alert alert-danger"><i class="bi bi-exclamation-circle"></i> <c:out value="${errorMessage}" /></div>
                </c:if>
                <c:if test="${successMessage != null}">
                    <div class="alert alert-success"><i class="bi bi-check-circle"></i> <c:out value="${successMessage}" /></div>
                </c:if>

                <c:url var="loginProcessUrl" value="/login" />
                <form action="${loginProcessUrl}" method="post">
                    <sec:csrfInput />
                    <div class="mb-3">
                        <label for="username" class="form-label">Email</label>
                        <input type="email" class="form-control" id="username" name="username" required>
                    </div>
                    <div class="mb-3">
                        <label for="password" class="form-label">Mot de passe</label>
                        <input type="password" class="form-control" id="password" name="password" required>
                    </div>
                    <button type="submit" class="btn btn-primary w-100">Se connecter</button>
                </form>

                <div class="text-center mt-3">
                    <a href="<c:url value='/register' />">Pas encore de compte ? S'inscrire</a>
                </div>
                <div class="text-center mt-2">
                    <a href="<c:url value='/forgot-password' />" class="text-muted">Mot de passe oublié ?</a>
                </div>
            </div>
        </div>
    </div>
</div>

<%@ include file="footer.jsp" %>
