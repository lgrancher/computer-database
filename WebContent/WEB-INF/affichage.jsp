<jsp:include page="../include/header.jsp" />
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="import" tagdir="/WEB-INF/tags"%>
<%@ page import="java.util.ArrayList"%>

<section id="main">
	<h2 id="homeTitle">${computerWrapper.noOfRecords} computers</h2>
	<div id="actions">
		<form action="index" method="GET">
			<div class="row">
				<div class="col-lg-2">
					<div class="input-group">
						<input type="text" class="form-control" name="search"> <span
							class="input-group-btn">
							<button class="btn btn-info" type="submit">
								<span class="glyphicon glyphicon-search"></span> Filter by name
							</button>
						</span>
					</div>
				</div>
				<import:allComputer />
			</div>
		</form>

		<import:add />
	</div>
	<table class="table table-hover">
		<thead>
			<tr>
				<import:titres />
				<th>Operations</th>
			</tr>
		</thead>
		<tbody>
			<c:forEach var="computer" items="${computerWrapper.listeComputer}">
				<tr>
					<td class="id">${computer.id}</td>
					<td class="name">${computer.name}</td>
					<td class="date">${computer.introduced}</td>
					<td class="date">${computer.discontinued}</td>
					<td class="name">${computer.company.name}</td>
					<td class="operations"><import:update
							computerId="${computer.id}" /> <import:delete
							computer="${computer}" /></td>
				</tr>
			</c:forEach>
		</tbody>
	</table>

	<import:pagination />

</section>
<jsp:include page="../include/footer.jsp" />