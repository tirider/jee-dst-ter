<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" session="false" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="core" %>
<!DOCTYPE html>
<html lang="en">
	<head>
		<title>Password Reset - Document Search Tool</title>
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
				<form method="post" action="<core:url value="/ResetPasswordService?service=reset&lang=en" />" class="form-forgot testimonial" style="border-width: 1px">
					<h2 class="form-heading" style="color: #666666;"><strong>Reset Password</strong></h2>
					<p align="justify">
						Enter the email address you used when creating the account and click <b>Send</b>. 
						You will receive a message noting information about your password change.
					</p>
					<div class="${empty form.errors ? 'hide' : 'alert alert-info'}" style="background-color: #F9F9F9;border-color: #AAAAAA;">
						<span style="color: red;display: block;">${form.errors['email']}</span>
						<span style="color: red;display: block;">${form.errors['general']}</span>
					</div>					
					<div class="${empty message ? 'hide' : 'alert alert-info'}" style="background-color: #F9F9F9;border-color: #AAAAAA;">
						<span style="color: red;display: block;">${message}</span>
					</div>		
					<div class="input-prepend input-fullwidth">
						<span class="add-on" style="${empty form.errors['email'] ? 'color:black;' : 'color:red;'}">*</span>
						<div class="input-wrapper">
							<input type="email" id="email" name="email" placeholder="Email" value="${user.email}" />
						</div>
					</div>
					<div class="form-actions">
						<div class="row-fluid" style="margin-bottom: 5px;">
							<button class="contour-btn red span12" type="submit">Send</button>
						</div>
					</div>
					<hr>
					<div class="row-fluid">
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