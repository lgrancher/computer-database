<jsp:include page="../include/header.jsp" />
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>

<title>Login Page</title>

<section id="main">
	<div class="col-lg-6 col-lg-offset-3" id="login-box">

		<form class="form-horizontal" name='loginForm'
			action="<c:url value='j_spring_security_check' />" method='POST'>
			<div class="text-center">
				<h2>
					<span class="label label-default"><spring:message code="login"/> </span>
				</h2>
			</div>

			<div class="form-group">
				<div><label for="user"><spring:message code="user"/></label></div>
				<input type='text' name='username'
					value=''>
			</div>
			<div class="form-group">
				<div><label for="password"><spring:message code="password"/></label></div>
				<input type='password'
					name='password' />
			</div>
			
			<c:if test="${not empty error}">
				<div class="error"><spring:message code="errorLogin"/></div>
			</c:if>
			<c:if test="${not empty msg}">
				<div class="msg"><spring:message code="deconnect"/></div>
			</c:if>
			
			<div class="form-group">
				<div class="col-sm-offset-2 col-sm-10">
					<input name="submit" type="submit" value="<spring:message code="connect"/>"
						class="btn btn-success" />
				</div>
			</div>

			<input type="hidden" name="${_csrf.parameterName}"
				value="${_csrf.token}" />

		</form>
	</div>
</section>
<jsp:include page="../include/footer.jsp" />