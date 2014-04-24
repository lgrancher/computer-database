<jsp:include page="../include/header.jsp" />
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="joda" uri="http://www.joda.org/joda/time/tags" %>
<section id="main">
	<div class="col-lg-4 col-lg-offset-4">
		<form:form class="form-horizontal" modelAttribute="computerDTO" method="POST" action="AddComputer">
			<div class="text-center">
				<h2><span class="label label-default"><spring:message code="add"/></span></h2>
			</div>
			<fieldset>
				<div class="form-group">
					<label for="name"><spring:message code="computerName"/>*:</label>
					<div class="input">
					
					<spring:message code="errorName" var="errName"/>
					<form:input value="${computerDTO.name}" type="text" path="name" data-validation="required"  data-validation-error-msg="${errName}" />
					
					</div>
					<form:errors path="name" cssStyle="color: red;"/>
				</div>

				<spring:message code="date" var="dateValid"/>
				<spring:message code="dateAffich" var="dateAffich"/>
				<spring:message code="dateTrait" var="dateTrait"/>
				<div class="form-group">
					<label for="introduced"><spring:message code="introduced"/>:</label>
					<div class="input">
					
					<spring:message code="errorIntroduced" var="errIntroduced"/>
					<form:input type="text" id="introducedDate" path="introduced"
					value="${computerDTO.introduced}"
					data-validation="date infDiscontinued"
							data-validation-format="${dateTrait}"
							data-validation-help="${dateAffich}"
							data-validation-optional="true" 
							data-validation-error-msg="${errIntroduced}"/>
					</div>
					<form:errors path="introduced" cssStyle="color: red;"/>
				</div>
				<div class="form-group">
					<label for="discontinued"><spring:message code="discontinued"/>:</label>
					<div class="input">
					 
					
					<spring:message code="errorDiscontinued" var="errDiscontinued"/>
					<form:input type="text" id="discontinuedDate" path="discontinued" value="${computerDTO.discontinued}"
							data-validation="date supIntroduced"
							data-validation-format="${dateTrait}"
							data-validation-help="${dateAffich}"
							data-validation-optional="true" 
							data-validation-error-msg="${errDiscontinued}" />
					 </div>
					 <form:errors path="discontinued" cssStyle="color: red;"/>
				</div>
				<div class="form-group">
					<label for="companyDTO"><spring:message code="company"/>:</label>
					<div class="input">
						<select name="companyDTO" class="form-control">
							<option value="0"></option>
							<c:forEach var="companies" items="${listeCompany}">	
							 <c:if test="${companies.id == computerDTO.companyDTO.id}">					
								<option value="${companies.id}" selected>${companies.name}</option>
							</c:if>
							 <c:if test="${companies.id != companySelect}">					
								<option value="${companies.id}">${companies.name}</option>
							</c:if>
							</c:forEach>
						</select>
					</div>
					<h6>*<spring:message code="required"/></h6>
				</div>
			</fieldset>
			<div class="form-group">
				<div class="col-sm-offset-2 col-sm-10">
					<input type="submit" value="<spring:message code="confirm"/>" class="btn btn-success">
					<spring:message code="or"/> <a class="btn btn-danger" href="index" class="btn"><spring:message code="cancel"/></a>
				</div>
			</div>
			<input type="hidden" id="local" value="${pageContext.response.locale}" />
		</form:form>
	</div>
</section>

<spring:message code="errorIntroduced" var="errIntroduced"/>

<script type="text/javascript" src="js/validation.js"></script>
<jsp:include page="../include/footer.jsp" />