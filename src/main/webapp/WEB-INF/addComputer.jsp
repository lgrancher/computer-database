<jsp:include page="../include/header.jsp" />
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<section id="main">
	<div class="col-lg-4 col-lg-offset-4">
		<form:form class="form-horizontal" modelAttribute="computerDTO" method="POST" action="AddComputer">
			<div class="text-center">
				<h2><span class="label label-default">Add Computer</span></h2>
			</div>
			<fieldset>
				<div class="form-group">
					<label for="name">Computer name*:</label>
					<div class="input">
					
					<form:input value="${computerDTO.name}" type="text" path="name" data-validation="required" />
					
					</div>
					<form:errors path="name" cssStyle="color: red;"/>
				</div>

				<div class="form-group">
					<label for="introduced">Introduced date:</label>
					<div class="input">
					
					<form:input type="date" id="introducedDate" path="introduced" value="${computerDTO.introduced}" data-validation="date infDiscontinued"
							data-validation-optional="true" data-validation-help="yyyy-mm-dd" />
					</div>
					<form:errors path="introduced" cssStyle="color: red;"/>
				</div>
				<div class="form-group">
					<label for="discontinued">Discontinued date:</label>
					<div class="input">
					 
					<form:input type="date" id="discontinuedDate" path="discontinued" value="${computerDTO.discontinued}" data-validation="date supIntroduced"
							data-validation-optional="true" data-validation-help="yyyy-mm-dd" />
					 </div>
					 <form:errors path="discontinued" cssStyle="color: red;"/>
				</div>
				<div class="form-group">
					<label for="companyDTO">Company Name:</label>
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
			</fieldset>
			<div class="form-group">
				<div class="col-sm-offset-2 col-sm-10">
					<input type="submit" value="AddComputer" class="btn btn-success">
					or <a class="btn btn-danger" href="index" class="btn">Cancel</a>
				</div>
			</div>
		</form:form>
	</div>
</section>

<script>

$.formUtils.addValidator
({
    name : 'supIntroduced',
    validatorFunction : function(value, $el, config, language, $form) 
    {
        return value>=$('#introducedDate').val() || $('#introducedDate').val() === "";
    },
    errorMessage : 'The discontinued date must be later than the introduced date',
    errorMessageKey: 'wrong date'
});

$.formUtils.addValidator
(
{
    name : 'infDiscontinued',
    validatorFunction : function(value, $el, config, language, $form) 
    {
        return value<=$('#discontinuedDate').val() || $('#discontinuedDate').val() === "";
    },
    errorMessage : 'The introduced date must be ealier than the discontinued date',
    errorMessageKey: 'wrong date'
});

$.validate();

</script>

<jsp:include page="../include/footer.jsp" />