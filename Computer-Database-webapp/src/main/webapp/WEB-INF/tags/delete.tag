<%@ attribute name="computer" required="true" type="java.lang.Object" description="computer a supprimer" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>

<spring:message code="delete" var="deleteComputer"/>
<a type="button" class="btn btn-danger" id="supp"
	href="DeleteComputer?id=${computer.id}&sort=${page.sort}&currentPage=${page.currentPage}&search=${page.search}"
	OnClick="return confirm('${deleteComputer} ${computer.name} ?')"><span
	class="glyphicon glyphicon-remove"></span></a>