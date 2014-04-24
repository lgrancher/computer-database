<jsp:include page="../include/header.jsp" />
<%@ taglib prefix="joda" uri="http://www.joda.org/joda/time/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib prefix="import" tagdir="/WEB-INF/tags"%>
<%@ page import="java.util.ArrayList"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>

<section id="main">
	<h2 id="homeTitle">${page.noOfRecords} <spring:message code="computers"/></h2>
	<div id="actions">
		<form action="index" method="GET">
			<div class="row">
				<div class="col-xs-6 col-sm-4">
					<div class="input-group">
						<input type="text" class="form-control" name="search"> <span
							class="input-group-btn">
							<button class="btn btn-info" type="submit">
								<span class="glyphicon glyphicon-search"></span> <spring:message code="filter"/>
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
				</tr>
			</thead>
			<tbody>
				<c:forEach var="computer" items="${page.listeElements}">
					<tr>
						<spring:message code="date" var="dateValid"/>
						<td class="id">${computer.id}</td>
						<td class="name">${computer.name}</td>
						<td class="date">
						 <joda:parseDateTime var="introduced" pattern="${dateValid}" value="${computer.introduced}" />
 						 <joda:format value="${introduced}" />
 						</td>
						<td class="date">
						 <joda:parseDateTime var="discontinued" pattern="${dateValid}" value="${computer.discontinued}" />
 						 <joda:format value="${discontinued}" />
 						</td>
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
			<h2><spring:message code="noComputer"/></h2>
		</c:otherwise>
	</c:choose>
	<import:pagination />
	<input type="hidden" id="erreur" value="${erreur}" />
	<input type="hidden" id="type" value="${type}" />
	
</section>
<spring:message code="computer" var="comp"/>
<spring:message code="updateComputerDeleted" var="errorUpdate"/>
<spring:message code="deletedComputerDeleted" var="errorDeleted"/>
<script>
var idErreur = $("#erreur").val();
var type = $("#type").val();

if(idErreur!="")
{
	if(type==="update")
		alert("${comp} "+idErreur+" ${errorUpdate}");
	
	else if(type==="delete")
		alert("${comp} "+idErreur+" ${errorDeleted}");
}
</script>

<jsp:include page="../include/footer.jsp" />