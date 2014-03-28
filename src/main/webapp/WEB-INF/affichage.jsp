<jsp:include page="../include/header.jsp" />
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib prefix="import" tagdir="/WEB-INF/tags"%>
<%@ page import="java.util.ArrayList"%>

<section id="main">
	<h2 id="homeTitle">${page.noOfRecords} computers</h2>
	<div id="actions">
		<form action="index" method="GET">
			<div class="row">
				<div class="col-xs-6 col-sm-4">
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
	<c:choose>
		<c:when test="${fn:length(page.listeElements)>0}">
		<table class="table table-hover">
			<thead>
				<tr>
					<import:titres />
					<th>Operations</th>
				</tr>
			</thead>
			<tbody>
				<c:forEach var="computer" items="${page.listeElements}">
					<tr>
						<td class="id">${computer.id}</td>
						<td class="name">${computer.name}</td>
						<td class="date">${computer.introduced}</td>
						<td class="date">${computer.discontinued}</td>
						<td class="name">${computer.companyDTO.name}</td>
						<td class="operations"><import:update
								computerId="${computer.id}" /> <import:delete
								computer="${computer}" /></td>
					</tr>
				</c:forEach>
			</tbody>
		</table>
		</c:when>
		<c:otherwise> 
			<h2>No computer matching this search !</h2>
		</c:otherwise>
	</c:choose>
	<import:pagination />
	<input type="hidden" id="erreur" value="${erreur}" />
	<input type="hidden" id="type" value="${type}" />
	
</section>

<script>
var idErreur = $("#erreur").val();
var type = $("#type").val();

if(idErreur!="")
{
	if(type==="update")
		alert("The computer "+idErreur+" can't be updated, it has been deleted");
	
	else if(type==="delete")
		alert("The computer "+idErreur+" has been already deleted");
}
</script>

<jsp:include page="../include/footer.jsp" />