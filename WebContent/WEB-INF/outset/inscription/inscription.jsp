<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" session="false" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="core" %>

<!DOCTYPE html>
<html>
	<head>
		<title>Registration - Document Search Tool</title>
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
		<script>$(document).ready(function(){ $("#username").focus(); });</script>				
	</head>
	<body>
		<div id="container">
			<div id="header">
				<core:import url="/resources/layouts/header-starter.jsp" />
			</div>
			<div id="body">
				<form method="post" action="<core:url value="/InscriptionService?service=signup&lang=en" />" class="form-register testimonial" style="border-width: 1px">
					<h2 class="form-heading" style="color: #666666;"><strong>Registration</strong></h2><!--  #84BD32; -->
					<p align="justify">You are about setting up your credentials to become an user of this product. To start up,  please complete all fields below.</p>
					<div class="${empty form.errors ? 'hide' : 'alert alert-info'}" style="background-color: #F9F9F9;border-color: #AAAAAA;">
						<span style="color: red;display: block;">${form.errors['username']}</span>
						<span style="color: red;display: block;">${form.errors['password']}</span>
						<span style="color: red;display: block;">${form.errors['passwordconfirmation']}</span>
						<span style="color: red;display: block;">${form.errors['email']}</span>
						<span style="color: red;display: block;">${form.errors['group']}</span>
					</div>
					<div id="register-container">
						<div class="input-prepend input-fullwidth">
							<span class="add-on" style="${empty form.errors['username'] ? 'color:black;' : 'color:red;'}">*</span>
							<div class="input-wrapper">
								<input type="text" id="username" name="username" placeholder="Username" value="${user.username}" />
							</div>
						</div>
						<div class="input-prepend input-fullwidth">
							<span class="add-on" style="${empty form.errors['password'] ? 'color:black;' : 'color:red;'}">*</span>
							<div class="input-wrapper">
								<input type="password" id="password" name="password" placeholder="Password" />
							</div>
						</div>
						<div class="input-prepend input-fullwidth">
							<span class="add-on" style="${empty form.errors['password'] ? 'color:black;' : 'color:red;'}">*</span>
							<div class="input-wrapper">
								<input type="password" id="passwordconfirmation" name="passwordconfirmation" placeholder="Password confirmation" />
							</div>
						</div>				
						<div class="input-prepend input-fullwidth">
							<span class="add-on" style="${empty form.errors['email'] ? 'color:black;' : 'color:red;'}">*</span>
							<div class="input-wrapper">
								<input id="email" name="email"  placeholder="Email" type="email"  value="${user.email}" />
							</div>
						</div>
					</div>
					<div class="form-actions">
						<div class="row-fluid" style="margin-bottom: 5px;">
							<button class="contour-btn red span12" type="submit">Register</button><!-- btn btn-success pull-right span12 -->
						</div>
					</div>
					<hr>
					<div class="row-fluid" style="text-align: center;">
						<strong class="text-center">Access to the Log In page </strong><a href="<core:url value="/LoginService?service=login&lang=en" />" id="link-forgot">here</a>
					</div>	
				</form> 				 								
			</div>
			
			<div id="startupfooter">
				<core:import url="/resources/layouts/footer.jsp" />
			</div>
			
		</div>
	</body>
</html>