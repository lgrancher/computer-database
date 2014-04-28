<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
<title>Computer Database</title>
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<!-- Bootstrap -->

<link href="css/magic-bootstrap.css" rel="stylesheet" media="screen">
<link href="css/bootstrap.css" rel="stylesheet" media="screen">
<link rel="icon" type="image/jpg" href="pictures/favicon.jpg" />
<script
	src="//ajax.googleapis.com/ajax/libs/jquery/1.10.2/jquery.min.js"></script>
<script
	src="//cdnjs.cloudflare.com/ajax/libs/jquery-form-validator/2.1.38/jquery.form-validator.min.js"></script>


</head>
<body>
	<div class="navbar navbar-inverse navbar-fixed-top">
		<div class="container-fluid">
			<div class="navbar-header">
				<h2>
					<span class="titreBar"><a href="index?currentPage=1"> <spring:message
								code="title" /></a></span>
				</h2>
			</div>
			<div class="navbar-collapse collapse">
				<ul class="nav navbar-nav navbar-right">
					<li><a id="flag" href="?language=fr"><img
							src="pictures/drapeau-fr.png" alt="fr" /></a></li>
					<li><a id="flag" href="?language=en"><img
							src="pictures/drapeau-en.png" alt="en" /></a></li>
					<li><c:url value="/j_spring_security_logout" var="logoutUrl" />

						<!-- csrt for log out-->
						<form action="${logoutUrl}" method="post" id="logoutForm">
							<input type="hidden" name="${_csrf.parameterName}"
								value="${_csrf.token}" />
						</form> <script>
							function formSubmit() {
								document.getElementById("logoutForm").submit();
							}
						</script> <c:if test="${pageContext.request.userPrincipal.name != null}">
							<h4 class="logout">
								<spring:message code="welcome"/> ${pageContext.request.userPrincipal.name} | <a
									class="logout" href="javascript:formSubmit()"> <spring:message code="logout"/> <span
									class="glyphicon glyphicon-remove-sign"></span></a>
							</h4>
						</c:if></li>
				</ul>
			</div>
		</div>
	</div>