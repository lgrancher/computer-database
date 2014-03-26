<jsp:include page="../include/header.jsp" />
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="import" tagdir="/WEB-INF/tags" %>
<section id="main">
	<div class="col-lg-4 col-lg-offset-4">
		<form class="form-horizontal" id="form" method="POST" action="UpdateComputer">
			<div class="text-center">
				<h2><span class="label label-default">Update Computer</span></h2>
			</div>
			<fieldset>
			<div class="form-group">
					<label for="name">Computer name*:</label>
					<div class="input">
					
					<c:if test="${verifyName == 'true'}">
						<input value="${computerDTOnew.name}" type="text" name="name" data-validation="required" />
					</c:if>
					
					<c:if test="${verifyName != 'true'}">
						<input value="${computerDTOold.name}" type="text" name="name" data-validation="required" />
					</c:if>
					</div>
					<c:if test="${verifyName == 'false'}">
						<span class="label label-danger">You have not answered all required fields</span>
					</c:if>
				</div>
				
				<div class="form-group">
					<label for="introduced">Introduced date:</label>
					<div class="input">
					
					<c:if test="${verifyIntroduced == 'true'}">
						<input type="date" id="introducedDate" name="introducedDate" value="${computerDTOnew.introduced}" data-validation="date infDiscontinued"
							data-validation-optional="true" data-validation-help="yyyy-mm-dd" />
					</c:if>
					
					<c:if test="${verifyIntroduced != 'true'}">
						<input type="date" id="introducedDate" name="introducedDate" value="${computerDTOold.introduced}" data-validation="date infDiscontinued"
							data-validation-optional="true" data-validation-help="yyyy-mm-dd" />
					</c:if>
					
					</div>
					<c:if test="${verifyIntroduced == 'false'}">
						<span class="label label-danger">You have not given a correct introduced date (YYYY-MM-DD)</span>
					</c:if>
				</div>
				<div class="form-group">
					<label for="discontinued">Discontinued date:</label>
					<div class="input">
					
					 <c:if test="${verifyDiscontinued == 'true'}">
						<input type="date" id="discontinuedDate" name="discontinuedDate" value="${computerDTOnew.discontinued}" data-validation="date supIntroduced"
							data-validation-optional="true" data-validation-help="yyyy-mm-dd" />
					</c:if>
					
					 <c:if test="${verifyDiscontinued != 'true'}">
						<input type="date" id="discontinuedDate" name="discontinuedDate" value="${computerDTOold.discontinued}" data-validation="date supIntroduced"
							data-validation-optional="true" data-validation-help="yyyy-mm-dd" />
					</c:if>
					
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
							<c:forEach var="companies" items="${listeCompany}">
							<c:choose>
							 <c:when test="${verifyName!=null && computerDTOnew.companyDTO.id == companies.id}">
								<option value="${companies.id}" selected>${companies.name}</option>
							</c:when>
							
							<c:when test="${verifyName==null && companies.id == companyDTO.id}">
								<option value="${companies.id}" selected>${companies.name}</option>
							</c:when>
							
							 <c:otherwise>
								<option value="${companies.id}">${companies.name}</option>
							</c:otherwise>
							</c:choose>
							</c:forEach>
						</select>
					</div>
					<h6>*required fields</h6>
				</div>

				<input type="hidden" value="${computerDTOold.id}" name="id">
				<input type="hidden" value="${page.currentPage}" name="currentPage">
				<input type="hidden" value="${page.search}" name="search">
				<input type="hidden" value="${page.sort}" name="sort">
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


$.formUtils.addValidator
({
    name : 'supIntroduced',
    validatorFunction : function(value, $el, config, language, $form) 
    {
        return value>=$('#introducedDate').val() || $('#discontinuedDate').val() === "";
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