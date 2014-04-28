<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<div class="navbar navbar-default navbar-fixed-bottom">
		<div class="container-fluid">
			<div class="pagination">
				<%--For displaying Previous link except for the 1st page --%>
				<c:if test="${page.number != 0}">
					<span class="num"><a
						href="index?sort=${sort}&currentPage=${page.number - 1}&search=${search}"
						style="cursor: pointer; color: grey"><span
							class="glyphicon glyphicon-chevron-left"></span> <spring:message code="previous"/></a></span>
				</c:if>


				<c:if test="${page.number == 0}">
					<span class="num"><span
						class="glyphicon glyphicon-chevron-left"></span> <spring:message code="previous"/></span>
				</c:if>


				<c:forEach begin="0" end="${page.totalPages-1}" var="i">
					<c:choose>
						<c:when test="${page.number eq i}">
							<span class="num">${i+1}</span>
						</c:when>
						<c:otherwise>
							<span class="num"><a
								href="index?sort=${sort}&currentPage=${i}&search=${search}"
								style="cursor: pointer; color: grey">${i+1}</a></span>
						</c:otherwise>
					</c:choose>
				</c:forEach>


				<%--For displaying Next link --%>
				<c:if test="${page.number lt page.totalPages-1}">
					<span class="num"><a
						href="index?sort=${sort}&currentPage=${page.number + 1}&search=${search}"
						style="cursor: pointer; color: grey"><spring:message code="next"/> <span
							class="glyphicon glyphicon-chevron-right"></span></a></span>
				</c:if>

				<c:if test="${page.number == page.totalPages-1}">
					<span class="num"><spring:message code="next"/> <span
						class="glyphicon glyphicon-chevron-right"></span></span>
				</c:if>

			</div>
		</div>
	</div>