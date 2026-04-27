<%@ page pageEncoding="UTF-8"%>
<%@ include file="header.jsp" %>

<h1 class="mb-4"><i class="bi bi-person-circle"></i> Mon compte</h1>

<c:if test="${successMessage != null}">
    <div class="alert alert-success alert-dismissible fade show">
        <i class="bi bi-check-circle"></i> <c:out value="${successMessage}" />
        <button type="button" class="btn-close" data-bs-dismiss="alert"></button>
    </div>
</c:if>
<c:if test="${errorMessage != null}">
    <div class="alert alert-danger alert-dismissible fade show">
        <i class="bi bi-exclamation-circle"></i> <c:out value="${errorMessage}" />
        <button type="button" class="btn-close" data-bs-dismiss="alert"></button>
    </div>
</c:if>

<div class="row g-4">
    <div class="col-md-6">
        <div class="card">
            <div class="card-body">
                <h4 class="card-title mb-3"><i class="bi bi-info-circle"></i> Informations</h4>
                <table class="table table-borderless mb-0">
                    <tr>
                        <th class="text-muted">Prénom</th>
                        <td><c:out value="${member.firstname}" /></td>
                    </tr>
                    <tr>
                        <th class="text-muted">Nom</th>
                        <td><c:out value="${member.lastname}" /></td>
                    </tr>
                    <tr>
                        <th class="text-muted">Email</th>
                        <td><c:out value="${member.email}" /></td>
                    </tr>
                    <tr>
                        <th class="text-muted">Sorties créées</th>
                        <td>
                            <span class="badge bg-primary">${excursionsCount}</span>
                            <a href="<c:url value='/excursions/my' />" class="ms-2">Voir mes sorties</a>
                        </td>
                    </tr>
                </table>
            </div>
        </div>
    </div>

    <div class="col-md-6">
        <div class="card">
            <div class="card-body">
                <h4 class="card-title mb-3"><i class="bi bi-key"></i> Changer le mot de passe</h4>
                <form action="<c:url value='/account/change-password' />" method="post">
                    <sec:csrfInput />
                    <div class="mb-3">
                        <label for="currentPassword" class="form-label">Mot de passe actuel</label>
                        <input type="password" class="form-control" id="currentPassword" name="currentPassword" required>
                    </div>
                    <div class="mb-3">
                        <label for="newPassword" class="form-label">Nouveau mot de passe</label>
                        <input type="password" class="form-control" id="newPassword" name="newPassword" required>
                    </div>
                    <div class="mb-3">
                        <label for="confirmPassword" class="form-label">Confirmer le nouveau mot de passe</label>
                        <input type="password" class="form-control" id="confirmPassword" name="confirmPassword" required>
                    </div>
                    <button type="submit" class="btn btn-primary">
                        <i class="bi bi-check-circle"></i> Modifier
                    </button>
                </form>
            </div>
        </div>
    </div>
</div>

<%@ include file="footer.jsp" %>