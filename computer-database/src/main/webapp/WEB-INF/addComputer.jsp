<jsp:include page="../include/header.jsp" />
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<section id="main">
	<div class="col-lg-4 col-lg-offset-4">
		<form class="form-horizontal" action="AddComputer" method="POST">
			<div class="text-center">
				<h2><span class="label label-default">Add Computer</span></h2>
			</div>
			<fieldset>
				<div class="form-group">
					<label for="name">Computer name*:</label>
					<div class="input">
					
					<input value="${computerDTO.name}" type="text" name="name" data-validation="required" />
					
					</div>
					<c:if test="${verifyName == 'false'}">
						<span class="label label-danger">You have not answered all required fields</span>
					</c:if>
				</div>

				<div class="form-group">
					<label for="introduced">Introduced date:</label>
					<div class="input">
					
					<input type="date" id="introducedDate" name="introducedDate" value="${computerDTO.introduced}" data-validation="date infDiscontinued"
							data-validation-optional="true" data-validation-help="yyyy-mm-dd" />
					</div>
					
					<c:if test="${verifyIntroduced == 'false'}">
						<span class="label label-danger">You have not given a correct introduced date (YYYY-MM-DD)</span>
					</c:if>
				</div>
				<div class="form-group">
					<label for="discontinued">Discontinued date:</label>
					<div class="input">
					 
					<input type="date" id="discontinuedDate" name="discontinuedDate" value="${computerDTO.discontinued}" data-validation="date supIntroduced"
							data-validation-optional="true" data-validation-help="yyyy-mm-dd" />
					 </div>
					 <c:if test="${verifyDiscontinued == 'false'}">
						<span class="label label-danger">You have not given a correct discontinued date (YYYY-MM-DD and later than the introduced date)</span>
					</c:if>
				</div>
				<div class="form-group">
					<label for="company">Company Name:</label>
					<div class="input">
						<select name="company">
							<option value="0"></option>
							<c:forEach var="company" items="${listeCompany}">
							
							 <c:if test="${computerDTO.companyDTO.id == company.id}">
								<option value="${company.id}" selected>${company.name}</option>
							</c:if>
							
							<c:if test="${computerDTO.companyDTO.id  != company.id}">
								<option value="${company.id}">${company.name}</option>
							</c:if>
							
							</c:forEach>
						</select>
					</div>
					<h6>*required fields</h6>
				</div>
			</fieldset>
			<div class="form-group">
				<div class="col-sm-offset-2 col-sm-10">
					<input type="submit" value="Add" class="btn btn-success">
					or <a class="btn btn-danger" href="index" class="btn">Cancel</a>
				</div>
			</div>
		</form>
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