<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>

<th><a href="index?sort=id&currentPage=1&search=${page.search}"><spring:message code="id"/></a></th>
<th><a href="index?sort=name&currentPage=1&search=${page.search}"><spring:message code="name"/></a></th>
<th><a href="index?sort=introduced&currentPage=1&search=${page.search}"><spring:message code="introduced"/></a></th>
<th><a href="index?sort=discontinued&currentPage=1&search=${page.search}"><spring:message code="discontinued"/></a></th>
<th><a href="index?sort=company&currentPage=1&search=${page.search}"><spring:message code="company"/></a></th>
<th></th>