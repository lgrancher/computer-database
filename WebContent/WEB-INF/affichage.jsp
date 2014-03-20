<jsp:include page="../include/header.jsp" />
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>

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
				<a type="button" class="btn btn-info" id="all" href="index"><span
					class="glyphicon glyphicon-th-list"></span> All Computers</a>
			</div>
			<!-- /.row -->

		</form>

		<a type="button" class="btn btn-warning" id="add" href="AddComputer?currentPage= ${computerWrapper.currentPage}"><span
					class="glyphicon glyphicon-plus"></span> Add
			Computer</a>
	</div>
	<table class="table table-hover">
		<thead>
			<tr>
				<c:set var="attribut"
					value="${computerWrapper.listeComputer[0]['class']['declaredFields']}" />
				<c:forEach var="titre" items="${attribut}">
					<th><a
						href="index?sort=${titre.name}&currentPage=1&search=${computerWrapper.search}">${titre.name}</a></th>
				</c:forEach>
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
					<td class="operations"><a type="button"
						class="btn btn-success" id="modif"
						href="UpdateComputer?id=${computer.id}&sort=${computerWrapper.sort}&currentPage=${computerWrapper.currentPage}&search=${computerWrapper.search}"><span
							class="glyphicon glyphicon-pencil"></span> Update</a> <a
						type="button" class="btn btn-danger" id="supp"
						href="DeleteComputer?id=${computer.id}&sort=${computerWrapper.sort}&currentPage=${computerWrapper.currentPage}&search=${computerWrapper.search}"
						OnClick="return confirm('Do you want to delete the computer ${computer.name} ?')"><span
							class="glyphicon glyphicon-remove"></span> Delete</a></td>
				</tr>
			</c:forEach>
		</tbody>
	</table>

	<!-- pagination -->
	<div class="navbar navbar-default navbar-fixed-bottom">
		<div class="container-fluid">
			<div class="pagination">

				<%--For displaying Previous link except for the 1st page --%>
				<c:if test="${computerWrapper.currentPage != 1}">
					<span class="num"><a
						href="index?sort=${sort}&currentPage=${computerWrapper.currentPage - 1}&search=${computerWrapper.search}"
						style="cursor: pointer; color: grey"><span
							class="glyphicon glyphicon-chevron-left"></span> Previous</a></span>
				</c:if>


				<c:if test="${computerWrapper.currentPage == 1}">
					<span class="num"><span
						class="glyphicon glyphicon-chevron-left"></span> Previous</span>
				</c:if>


				<c:forEach begin="1" end="${computerWrapper.noOfPages}" var="i">
					<c:choose>
						<c:when test="${computerWrapper.currentPage eq i}">
							<span class="num">${i}</span>
						</c:when>
						<c:otherwise>
							<span class="num"><a
								href="index?sort=${computerWrapper.sort}&currentPage=${i}&search=${computerWrapper.search}"
								style="cursor: pointer; color: grey">${i}</a></span>
						</c:otherwise>
					</c:choose>
				</c:forEach>


				<%--For displaying Next link --%>
				<c:if test="${computerWrapper.currentPage lt computerWrapper.noOfPages}">
					<span class="num"><a
						href="index?sort=${computerWrapper.sort}&currentPage=${computerWrapper.currentPage + 1}&search=${computerWrapper.search}"
						style="cursor: pointer; color: grey">Next <span
							class="glyphicon glyphicon-chevron-right"></span></a></span>
				</c:if>

				<c:if test="${computerWrapper.currentPage == computerWrapper.noOfPages}">
					<span class="num">Next <span
						class="glyphicon glyphicon-chevron-right"></span></span>
				</c:if>

			</div>
		</div>
	</div>

</section>
<jsp:include page="../include/footer.jsp" />