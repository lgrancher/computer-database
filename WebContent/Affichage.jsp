<jsp:include page="include/header.jsp" />
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<%@ page import="java.util.ArrayList" %>
<section id="main">
	<h1 id="homeTitle">${fn:length(listeComputer)} computers</h1>
	<div id="actions">
		<form action="FilterByName" method="GET">
			<input type="search" id="searchbox" name="search"
				value="" placeholder="Search name">
			<input type="submit" id="searchsubmit"
				value="Filter by name"
				class="btn primary">
		</form>
		<a class="btn success" id="add" href="ListeCompany">Add Computer</a>
	</div>
		<table>
			<thead>
				<tr><c:set var="attribut" value="${listeComputer[0]['class']['declaredFields']}"/>
					<c:forEach var="titre" items="${attribut}">
						<th>${titre.name}</th>			
					</c:forEach>
					<th>Modifier</th>
				</tr>
			</thead>
			<tbody>
				<c:forEach var="computer" items="${listeComputer}">
				<tr>
					<td>${computer.id}</td>
					<td>${computer.name}</td>
					<td>${computer.introduced}</td>
					<td>${computer.discontinued}</td>
					<td>${computer.company.name}</td>
					<td><a class="btn success" id="modif" href="UpdateComputer?id=${computer.id}">Modifier</a></td>
				</tr>
				</c:forEach>
			</tbody>
		</table>
</section>

<jsp:include page="include/footer.jsp" />