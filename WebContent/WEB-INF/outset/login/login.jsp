<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" session="false" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="core" %>
<!DOCTYPE html>
<html >
	<head>
		<title>Authentication - Document Search Tool</title>
		<meta charset="utf-8">		
		<meta http-equiv="content-type" content="text/html; charset=UTF-8">
		<meta name="viewport" content="width=device-width, initial-scale=1.0">
		<link rel="shortcut icon" href="<core:url value="/favicon.ico" />" type="image/x-icon">
		
		<link href="<core:url value="/resources/css/lg/bootstrap.css" />" rel="stylesheet">
		<link href="<core:url value="/resources/css/lg/social.css" />" rel="stylesheet">
		<link href="<core:url value="/resources/css/lg/font-awesome.css" />" rel="stylesheet">
		<link href="<core:url value="/resources/css/lg/bootstrap-responsive.css" />" rel="stylesheet">
		<link rel="stylesheet" href="<core:url value="/resources/css/template.css" />" type="text/css" media="all">
		<link rel="stylesheet" href="<core:url value="/resources/css/color.css" />"> 
		<link rel="stylesheet" href="<core:url value="/resources/css/css.css" />" type="text/css">
		<link rel="stylesheet" href="<core:url value="/resources/css/layout.css" />" type="text/css" > 	
		
		<script src="<core:url value="/resources/js/jquery-1.js"/>"></script>
		<script>$(document).ready(function(){ $("#email").focus(); });</script>		
	</head>
	<body>
		<div id="container">
			<div id="header">
				<core:import url="/resources/layouts/header-starter.jsp" />
			</div>
			<div id="body">
				<form method="post" action="<core:url value="/LoginService" />" class="form-register testimonial" style="border-width: 1px">
					<h2 class="form-heading" style="color: #666666;"><strong>Authentication</strong></h2>
					<p align="center">Type your email address and password below</p>
					<div class="${empty form.errors ? 'hide' : 'alert alert-info'}" style="background-color: #F9F9F9;border-color: #AAAAAA;">
						<span style="color: red;display: block;">${form.errors['auth']}</span>
						<span style="color: red;display: block;">${form.errors['email']}</span>
						<span style="color: red;display: block;">${form.errors['password']}</span>
					</div>
					<div class="input-prepend input-fullwidth">
						<span class="add-on" style="${empty form.errors['email'] ? 'color:black;' : 'color:red;'}">*</span>
						<div class="input-wrapper">
							<input id="email" name="email" value="<core:out value="${user.email}"/>" class="input-block-level" placeholder="Email" type="email" >
						</div>
					</div>
					<div class="input-prepend input-fullwidth">
						<span class="add-on" style="${empty form.errors['password'] ? 'color:black;' : 'color:red;'}">*</span>
						<div class="input-wrapper">
							<input id="password" name="password" class="input-block-level" placeholder="Password" type="password">
						</div>
					</div>
					<div class="form-actions">
						<div class="row-fluid" style="margin-bottom: 5px;">
							<button class="contour-btn red span12" type="submit">Log In</button><!-- class="btn btn-primary pull-right span6" -->
						</div>
					</div>
					<hr size="1" />
					<div class="forget-password" style="text-align: center;">
						<strong class="text-center">Forgot password ? </strong><a href="<core:url value="/ResetPasswordService?service=reset&lang=en" />" id="link-forgot">Click here to reset</a>
					</div>
					<div class="row-fluid" style="text-align: center;">
						<strong class="text-center">You do not have an account ? </strong><a href="<core:url value="/InscriptionService?service=signup&lang=en" />" id="link-forgot">Register</a>
					</div>								
				</form>				
			</div>
			
			<div id="startupfooter">
				<core:import url="/resources/layouts/footer.jsp" />
			</div>
			
		</div>
	</body>	
</html>