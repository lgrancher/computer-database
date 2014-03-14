<jsp:include page="../include/header.jsp" />
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<section id="main">
	<div class="col-lg-4 col-lg-offset-4">
		<form class="form-horizontal" action="AjoutComputer" method="POST">
			<div class="text-center">
				<label class="btn btn-default btn-lg ">Add Computer</label>
			</div>
			<fieldset>
				<div class="form-group">
					<label for="name">Computer name:</label>
					<div class="input">
						<input type="text" name="name" data-validation="required" />
					</div>
				</div>

				<div class="form-group">
					<label for="introduced">Introduced date:</label>
					<div class="input">
						<input type="date" name="introducedDate" data-validation="date"
							data-validation-optional="true" data-validation-help="yyyy-mm-dd" />
					</div>
				</div>
				<div class="form-group">
					<label for="discontinued">Discontinued date:</label>
					<div class="input">
						<input type="date" name="discontinuedDate" data-validation="date"
							data-validation-optional="true" data-validation-help="yyyy-mm-dd" />
					</div>
				</div>
				<div class="form-group">
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
	$.validate({});
</script>

<jsp:include page="../include/footer.jsp" />