<%@ attribute name="computer" required="true" type="java.lang.Object" description="computer a supprimer" %>

<a type="button" class="btn btn-danger" id="supp"
	href="DeleteComputer?id=${computer.id}&sort=${page.sort}&currentPage=${page.currentPage}&search=${page.search}"
	OnClick="return confirm('Do you want to delete the computer ${computer.name} ?')"><span
	class="glyphicon glyphicon-remove"></span></a>