<jsp:include page="../include/header.jsp" />
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="import" tagdir="/WEB-INF/tags"%>
<section id="main">
	<div class="col-lg-4 col-lg-offset-4">
		<form:form class="form-horizontal" modelAttribute="computerDTO" id="form" method="POST"
			action="UpdateComputer">
			<div class="text-center">
				<h2>
					<span class="label label-default">Update Computer</span>
				</h2>
			</div>
			<fieldset>
			
				<div class="form-group">
					<label for="name">Computer name*:</label>
					<div class="input">
						<form:input value="${computerDTO.name}" path="name" type="text"
							data-validation="required" />
					</div>
					<form:errors path="name" cssStyle="color: red;" />
				</div>

				<div class="form-group">
					<label for="introduced">Introduced date:</label>
					<div class="input">
						<form:input type="date" id="introducedDate" path="introduced"
							value="${computerDTO.introduced}"
							data-validation="date infDiscontinued"
							data-validation-optional="true" data-validation-help="yyyy-mm-dd" />
					</div>
					<form:errors path="introduced" cssStyle="color: red;" />
				</div>
				
				<div class="form-group">
					<label for="discontinued">Discontinued date:</label>
					<div class="input">
						<form:input type="date" id="discontinuedDate" path="discontinued"
							value="${computerDTO.discontinued}"
							data-validation="date supIntroduced"
							data-validation-optional="true" data-validation-help="yyyy-mm-dd" />
					</div>
					<form:errors path="discontinued" cssStyle="color: red;" />
				</div>
				
				<div class="form-group">
					<label for="company">Company Name:</label>
					<div class="input">
						<select name="companyDTO" class="form-control">
							<option value="0"></option>
							<c:forEach var="companies" items="${listeCompany}">
								<c:if test="${companies.id == companySelect}">
									<option value="${companies.id}" selected>${companies.name}</option>
								</c:if>
								<c:if test="${companies.id != companySelect}">
									<option value="${companies.id}">${companies.name}</option>
								</c:if>
							</c:forEach>
						</select>
					</div>
					<h6>*required fields</h6>
				</div>

				<input type="hidden" value="${computerDTO.id}" name="id">
				<input type="hidden" value="${page.currentPage}" name="currentPage">
				<input type="hidden" value="${page.search}" name="search"> <input
					type="hidden" value="${page.sort}" name="sort">
			</fieldset>
			<div class="form-group">
				<div class="col-sm-offset-2 col-sm-10">
					<input type="submit" name="submit" value="Update"
						class="btn btn-success"> or
					<import:retrieve />
				</div>
			</div>
		</form:form>
	</div>
</section>
<script>
	$.formUtils
			.addValidator({
				name : 'supIntroduced',
				validatorFunction : function(value, $el, config, language,
						$form) {
					return value >= $('#introducedDate').val()
							|| $('#discontinuedDate').val() === "";
				},
				errorMessage : 'The discontinued date must be later than the introduced date',
				errorMessageKey : 'wrong date'
			});

	$.formUtils
			.addValidator({
				name : 'infDiscontinued',
				validatorFunction : function(value, $el, config, language,
						$form) {
					return value <= $('#discontinuedDate').val()
							|| $('#discontinuedDate').val() === "";
				},
				errorMessage : 'The introduced date must be ealier than the discontinued date',
				errorMessageKey : 'wrong date'
			});

	$.validate();
</script>

<jsp:include page="../include/footer.jsp" />