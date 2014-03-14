<jsp:include page="../include/header.jsp" />
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>

<%@ page import="java.util.ArrayList"%>
<section id="main">
	<h1 id="homeTitle">${noOfRecords} computers</h1>
	<div id="actions">
		<form action="index" method="GET">
			<input type="search" id="searchbox" name="search" value="" placeholder="Search name"> 
			<input type="submit" id="searchsubmit" value="Filter by name" class="btn primary">
			<a class="btn primary" id="all" href="index">All Computers</a>
		</form>
		
		<a class="btn success" id="add" href="AddComputer">Add Computer</a>
	</div>
	<table>
		<thead>
			<tr>
				<c:set var="attribut"
					value="${listeComputer[0]['class']['declaredFields']}" />
				<c:forEach var="titre" items="${attribut}">
					<th><a href="index?sort=${titre.name}&currentPage=1&search=${search}">${titre.name}</a></th>
				</c:forEach>
				<th>Operations</th>
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
					<td><a class="btn success" id="modif"
						href="UpdateComputer?id=${computer.id}">Update</a> <a
						class="btn danger" id="supp" href=DeleteComputer?id=${computer.id}
						OnClick="return confirm('Do you want to delete the computer ${computer.name} ?')">Delete</a>
					</td>
				</tr>
			</c:forEach>
		</tbody>
	</table>

	<!-- pagination -->
	<table border="1">
		<tr>

			<%--For displaying Previous link except for the 1st page --%>
			<c:if test="${currentPage != 1}">
				<td><a href="index?sort=${sort}&currentPage=${currentPage - 1}&search=${search}" style="cursor: pointer; color: grey">Previous</a></td>
			</c:if>
			
			<c:forEach begin="1" end="${noOfPages}" var="i">
				<c:choose>
					<c:when test="${currentPage eq i}">
						<td>${i}</td>
					</c:when>
					<c:otherwise>
						<td><a href="index?sort=${sort}&currentPage=${i}&search=${search}" style="cursor: pointer; color: grey">${i}</a></td>
					</c:otherwise>
				</c:choose>
			</c:forEach>


			<%--For displaying Next link --%>
			<c:if test="${currentPage lt noOfPages}">
				<td><a href="index?sort=${sort}&currentPage=${currentPage + 1}&search=${search}" style="cursor: pointer; color: grey">Next</a></td>
			</c:if>
			
		</tr>
	</table>
	
</section>
<jsp:include page="../include/footer.jsp" />