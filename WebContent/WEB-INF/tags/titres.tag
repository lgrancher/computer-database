<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<c:set var="attribut" value="${computerWrapper.listeComputer[0]['class']['declaredFields']}" />
<c:forEach var="titre" items="${attribut}">
	<th><a
		href="index?sort=${titre.name}&currentPage=1&search=${computerWrapper.search}">${titre.name}</a></th>
</c:forEach>