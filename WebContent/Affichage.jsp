<jsp:include page="include/header.jsp" />
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<%@ page import="java.util.ArrayList" %>
<section id="main">
	<h1 id="homeTitle">456 Computers found</h1>
	<div id="actions">
		<form action="" method="GET">
			<input type="search" id="searchbox" name="search"
				value="" placeholder="Search name">
			<input type="submit" id="searchsubmit"
				value="Filter by name"
				class="btn primary">
		</form>
		<a class="btn success" id="add" href="addComputer.jsp">Add Computer</a>
	</div>

		<table>
			<thead>
				<tr>
					<c:forEach var="titres" items="${reponse[0]}" >
					<th>${titres}</th>
					</c:forEach>
				</tr>
			</thead>
			<tbody>
				<c:forEach var="tableau" items="${reponse}" begin="1">
				<tr>
					<c:forEach var="ligne" items="${tableau}" >
					<td>${ligne}</td>
					</c:forEach>
				</tr>
				</c:forEach>
			</tbody>
		</table>
</section>

<jsp:include page="include/footer.jsp" />