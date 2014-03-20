<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
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