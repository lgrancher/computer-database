<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<div class="navbar navbar-default navbar-fixed-bottom">
		<div class="container-fluid">
			<div class="pagination">

				<%--For displaying Previous link except for the 1st page --%>
				<c:if test="${page.currentPage != 1}">
					<span class="num"><a
						href="index?sort=${page.sort}&currentPage=${page.currentPage - 1}&search=${page.search}"
						style="cursor: pointer; color: grey"><span
							class="glyphicon glyphicon-chevron-left"></span> Previous</a></span>
				</c:if>


				<c:if test="${page.currentPage == 1}">
					<span class="num"><span
						class="glyphicon glyphicon-chevron-left"></span> Previous</span>
				</c:if>


				<c:forEach begin="1" end="${page.noOfPages}" var="i">
					<c:choose>
						<c:when test="${page.currentPage eq i}">
							<span class="num">${i}</span>
						</c:when>
						<c:otherwise>
							<span class="num"><a
								href="index?sort=${page.sort}&currentPage=${i}&search=${page.search}"
								style="cursor: pointer; color: grey">${i}</a></span>
						</c:otherwise>
					</c:choose>
				</c:forEach>


				<%--For displaying Next link --%>
				<c:if test="${page.currentPage lt page.noOfPages}">
					<span class="num"><a
						href="index?sort=${page.sort}&currentPage=${page.currentPage + 1}&search=${page.search}"
						style="cursor: pointer; color: grey">Next <span
							class="glyphicon glyphicon-chevron-right"></span></a></span>
				</c:if>

				<c:if test="${page.currentPage == page.noOfPages}">
					<span class="num">Next <span
						class="glyphicon glyphicon-chevron-right"></span></span>
				</c:if>

			</div>
		</div>
	</div>