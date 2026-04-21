<%@ page pageEncoding="UTF-8"%>
<%@ include file="header.jsp" %>

<div class="row justify-content-center">
    <div class="col-md-5">
        <div class="card">
            <div class="card-body p-4">
                <h2 class="card-title text-center mb-4">
                    <i class="bi bi-person-plus"></i> Créer un compte
                </h2>

                <c:if test="${errorMessage != null}">
                    <div class="alert alert-danger"><i class="bi bi-exclamation-circle"></i> <c:out value="${errorMessage}" /></div>
                </c:if>

                <form:form method="POST" action="${pageContext.request.contextPath}/register" modelAttribute="member">

                    <form:errors path="*" cssClass="alert alert-danger" element="div" />

                    <div class="mb-3">
                        <label for="firstname" class="form-label">Prénom</label>
                        <form:input path="firstname" id="firstname" cssClass="form-control" required="required" />
                        <form:errors path="firstname" cssClass="text-danger small" />
                    </div>

                    <div class="mb-3">
                        <label for="lastname" class="form-label">Nom</label>
                        <form:input path="lastname" id="lastname" cssClass="form-control" required="required" />
                        <form:errors path="lastname" cssClass="text-danger small" />
                    </div>

                    <div class="mb-3">
                        <label for="email" class="form-label">Email</label>
                        <form:input path="email" id="email" type="email" cssClass="form-control" required="required" />
                        <form:errors path="email" cssClass="text-danger small" />
                    </div>

                    <div class="mb-3">
                        <label for="password" class="form-label">Mot de passe</label>
                        <form:password path="password" id="password" cssClass="form-control" required="required" />
                        <form:errors path="password" cssClass="text-danger small" />
                    </div>

                    <button type="submit" class="btn btn-primary w-100">Créer le compte</button>

                </form:form>

                <div class="text-center mt-3">
                    <a href="<c:url value='/login' />">Déjà un compte ? Se connecter</a>
                </div>
            </div>
        </div>
    </div>
</div>

<%@ include file="footer.jsp" %>
