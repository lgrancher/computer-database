<%@ attribute name="computerId" required="true" type="java.lang.Long" description="id du computer a modifier" %>

<a type="button" class="btn btn-success" id="modif"
	href="UpdateComputer?id=${computerId}&sort=${page.sort}&currentPage=${page.currentPage}&search=${page.search}"><span
	class="glyphicon glyphicon-pencil"></span></a>