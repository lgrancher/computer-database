<jsp:include page="../include/header.jsp" />
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="import" tagdir="/WEB-INF/tags" %>
<section id="main">
	<div class="col-lg-4 col-lg-offset-4">
		<form class="form-horizontal" id="form" method="POST" action="UpdateComputer">
			<div class="text-center">
				<label class="btn btn-default btn-lg ">Update Computer</label>
			</div>
			<fieldset>
				<div class="form-group">
					<label for="name">Computer name : </label>
					<div class="input">
						<input type="text" name="name" data-validation="required"
							value="<c:out value="${computer.name}"/>" class="required" />
					</div>
				</div>

				<div class="form-group">
					<label for="introduced">Introduced date:</label>
					<div class="input">
						<input type="date" name="introducedDate" data-validation="date"
							data-validation-help="yyyy-mm-dd" data-validation-optional="true"
							value="<c:out value="${computer.introduced}"/>" />
					</div>
				</div>
				<div class="form-group">
					<label for="discontinued">Discontinued date:</label>
					<div class="input">
						<input type="date" name="discontinuedDate" data-validation="date"
							data-validation-help="yyyy-mm-dd" data-validation-optional="true"
							value="<c:out value="${computer.discontinued}"/>" />
					</div>
				</div>
				<div class="form-group">
					<label for="company">Company Name:</label>
					<div class="input">
						<select name="company">
							<option value="0" selected></option>
							<c:forEach var="companies" items="${listeCompany}">
								<c:if test="${company.id == companies.id}">
									<option value="${companies.id}" selected>${companies.name}</option>
								</c:if>
								<c:if test="${company.id != companies.id}">
									<option value="${companies.id}">${companies.name}</option>
								</c:if>
							</c:forEach>
						</select>
					</div>
				</div>

				<input type="hidden" value="${computer.id}" name="id">
				<input type="hidden" value="${computerWrapper.currentPage}" name="currentPage">
				<input type="hidden" value="${computerWrapper.search}" name="search">
				<input type="hidden" value="${computerWrapper.sort}" name="sort">
			</fieldset>
			<div class="form-group">
				<div class="col-sm-offset-2 col-sm-10">
					<input type="submit" name="submit" value="Update"
						class="btn btn-success"> or <import:retrieve />
				</div>
			</div>
		</form>
	</div>
</section>
<script>
	$.validate({});
</script>

<jsp:include page="../include/footer.jsp" />