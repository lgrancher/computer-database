<jsp:include page="../include/header.jsp" />
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>

<%@ page import="java.util.ArrayList"%>
<section id="main">
	<h2 id="homeTitle">${noOfRecords} computers</h2>
	<div id="actions">
		<form action="index" method="GET">
			<input type="search" id="searchbox" name="search" value=""
				placeholder="Search name"> <input type="submit"
				id="searchsubmit" value="Filter by name" type="button"
				class="btn btn-info"> <a type="button" class="btn btn-info"
				id="all" href="index">All Computers</a>
		</form>

		<a type="button" class="btn btn-warning" id="add" href="AddComputer">Add
			Computer</a>
	</div>
	<table class="table table-hover">
		<thead>
			<tr>
				<c:set var="attribut"
					value="${listeComputer[0]['class']['declaredFields']}" />
				<c:forEach var="titre" items="${attribut}">
					<th><a
						href="index?sort=${titre.name}&currentPage=1&search=${search}">${titre.name}</a></th>
				</c:forEach>
				<th>Operations</th>
			</tr>
		</thead>
		<tbody>
			<c:forEach var="computer" items="${listeComputer}">
				<tr>
					<td class="id">${computer.id}</td>
					<td class="name">${computer.name}</td>
					<td class="date">${computer.introduced}</td>
					<td class="date">${computer.discontinued}</td>
					<td class="name">${computer.company.name}</td>
					<td class="operations"><a type="button" class="btn btn-success" id="modif"
						href="UpdateComputer?id=${computer.id}">Update</a> <a
						type="button" class="btn btn-danger" id="supp"
						href=DeleteComputer?id=${computer.id}
						OnClick="return confirm('Do you want to delete the computer ${computer.name} ?')">Delete</a>
					</td>
				</tr>
			</c:forEach>
		</tbody>
	</table>

	<!-- pagination -->

	<table class="table" id="pagination">
		<tr>

			<%--For displaying Previous link except for the 1st page --%>
			<c:if test="${currentPage != 1}">
				<td><a
					href="index?sort=${sort}&currentPage=${currentPage - 1}&search=${search}"
					style="cursor: pointer; color: grey">Previous</a></td>
			</c:if>

			
			<c:if test="${currentPage == 1}">
				<td>Previous</td>
			</c:if>


			<c:forEach begin="1" end="${noOfPages}" var="i">
				<c:choose>
					<c:when test="${currentPage eq i}">
						<td>${i}</td>
					</c:when>
					<c:otherwise>
						<td><a
							href="index?sort=${sort}&currentPage=${i}&search=${search}"
							style="cursor: pointer; color: grey">${i}</a></td>
					</c:otherwise>
				</c:choose>
			</c:forEach>


			<%--For displaying Next link --%>
			<c:if test="${currentPage lt noOfPages}">
				<td><a
					href="index?sort=${sort}&currentPage=${currentPage + 1}&search=${search}"
					style="cursor: pointer; color: grey">Next</a></td>
			</c:if>
			
			<c:if test="${currentPage == noOfPages}">
				<td>Next</td>
			</c:if>

		</tr>
	</table>

</section>
<jsp:include page="../include/footer.jsp" />