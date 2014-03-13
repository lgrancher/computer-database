<jsp:include page="../include/header.jsp" />
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<section id="main">

	<h1>Add Computer</h1>

	<form action="AjoutComputer" method="POST">
		<fieldset>
			<div class="clearfix">
				<label for="name">Computer name:</label>
				<div class="input">
					<input type="text" name="name" data-validation="required" />
				</div>
			</div>

			<div class="clearfix">
				<label for="introduced">Introduced date:</label>
				<div class="input">
					<input type="date" name="introducedDate" data-validation="date" data-validation-optional="true" data-validation-help="yyyy-mm-dd" />
				</div>
			</div>
			<div class="clearfix">
				<label for="discontinued">Discontinued date:</label>
				<div class="input">
					<input type="date" name="discontinuedDate" data-validation="date" data-validation-optional="true" data-validation-help="yyyy-mm-dd" />
				</div>
			</div>
			<div class="clearfix">
				<label for="company">Company Name:</label>
				<div class="input">
					<select name="company">
						<option value="0" selected></option>
						<c:forEach var="company" items="${listeCompany}">
							<option value="${company.id}">${company.name}</option>
						</c:forEach>
					</select>
				</div>
			</div>
		</fieldset>
		<div class="actions">
			<input type="submit" value="Add" class="btn primary"> or <a
				href="index" class="btn">Cancel</a>
		</div>
	</form>
</section>

<script>
	$.validate({});
</script>

<jsp:include page="../include/footer.jsp" />