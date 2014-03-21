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
					<label for="name">Computer name*:</label>
					<div class="input">
					
					<c:if test="${verifyName == 'true'}">
						<input value="${computerDTO.name}" type="text" name="name" data-validation="required" />
					</c:if>
					
					<c:if test="${verifyName != 'true'}">
						<input value="${computer.name}" type="text" name="name" data-validation="required" />
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
						<input type="date" name="introducedDate" value="${computerDTO.introduced}" data-validation="date"
							data-validation-optional="true" data-validation-help="yyyy-mm-dd" />
					</c:if>
					
					<c:if test="${verifyIntroduced != 'true'}">
						<input type="date" name="introducedDate" value="${computer.introduced}" data-validation="date"
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
						<input type="date" name="discontinuedDate" value="${computerDTO.discontinued}" data-validation="date"
							data-validation-optional="true" data-validation-help="yyyy-mm-dd" />
					</c:if>
					
					 <c:if test="${verifyDiscontinued != 'true'}">
						<input type="date" name="discontinuedDate" value="${computer.discontinued}" data-validation="date"
							data-validation-optional="true" data-validation-help="yyyy-mm-dd" />
					</c:if>
					
					</div>
					 <c:if test="${verifyDiscontinued == 'false'}">
						<span class="label label-danger">You have not given a correct introduced date (YYYY-MM-DD)</span>
					</c:if>
				</div>
				<div class="form-group">
					<label for="company">Company Name:</label>
					<div class="input">
						<select name="company">
							<option value="0"></option>
							<c:forEach var="companies" items="${listeCompany}">
							<c:choose>
							 <c:when test="${verifyName!=null && computerDTO.idCompany == companies.id}">
								<option value="${companies.id}" selected>${companies.name}</option>
							</c:when>
							
							<c:when test="${verifyName==null && companies.id == company.id}">
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