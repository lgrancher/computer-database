<jsp:include page="../include/header.jsp" />
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<section id="main">

	<h1>Update Computer</h1>

	<form id="form" method="POST" action="">
		<fieldset>
			<div class="clearfix">
				<label for="name">Computer name :</label>
				<div class="input">
					<input type="text" name="name" data-validation="required" value="<c:out value="${computer.name}"/>" class="required"/>
				</div>
			</div>
	
			<div class="clearfix">
				<label for="introduced">Introduced date:</label>
				<div class="input">
					<input type="date" name="introducedDate" data-validation="date" data-validation-help="yyyy-mm-dd" value="<c:out value="${computer.introduced}"/>"/>
				</div>
			</div>
			<div class="clearfix">
				<label for="discontinued">Discontinued date:</label>
				<div class="input">
					<input type="date" name="discontinuedDate" data-validation="date" data-validation-help="yyyy-mm-dd" value="<c:out value="${computer.discontinued}"/>"/>
				</div>
			</div>
			<div class="clearfix">
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
			
			<input type="hidden" value="${computer.id}" name="id"/>
		</fieldset>
		<div class="actions">
			<input type="submit" name="submit" value="Update" class="btn primary">
			or <a href="index" class="btn">Cancel</a>
		</div>
	</form>
</section>
<script>
 
$.validate
({
	modules : 'location, date, security, file'
});
  
</script>

<jsp:include page="../include/footer.jsp" />