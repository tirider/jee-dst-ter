<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="core" %>

<!DOCTYPE html>
<html class="js csstransforms no-csstransforms3d csstransitions desktop js" lang="en">
	<head>
		<title>Security - Document Search Tool</title>
		<meta http-equiv="content-type" content="text/html; charset=UTF-8">
		<meta name="viewport" content="width=device-width, initial-scale=1.0, maximum-scale=1.0, user-scalable=0">
		<link rel="shortcut icon" href="<core:url value="/favicon.ico" />" type="image/x-icon">		

		<link rel="stylesheet" href="<core:url value="/resources/css/template.css" />" type="text/css" media="all">
		<link rel="stylesheet" href="<core:url value="/resources/css/color.css" />"> 
		<link rel="stylesheet" href="<core:url value="/resources/css/css.css" />" type="text/css">
		<link rel="stylesheet" href="<core:url value="/resources/css/layout.css" />" type="text/css" >
		
		<script type="text/javascript" src="<core:url value="/resources/js/jquery-1.js" />"></script>
	</head>
	<body>
		<div id="container" >
			
			<div id="header">
				<core:import url="/resources/layouts/header.jsp" />
			</div>
			
			<div id="body">
			
				<div class="site-wrapper">
					<div class="site-body">		
						<div class="page-title">
							<div class="pe-container">
								<h1>Security Control</h1>
							</div>
						</div> 
						<div class="pe-spacer size70"></div>
						<div class="pe-container">
							<div class="row-fluid">
							
								<section class="span8 pe-block">
									<p class="intro" align="justify">
										The <span class="accent">security control panel</span> will allows you to <span class="accent">change your password</span>, in case of lost or  
										simple decision coming from yourself.<br/>
										
										<br/> 
										
										When creating your new password, make sure it follows the requirements listed at the right.
										If your typed password meets such requirements, then you will be informed that your 
										password has change. Otherwise you will be notified to create a different one. 
									</p>
									
									<form method="post" action="<core:url value="/ChangePasswordService?service=passchang&lang=en" />" class="peThemeContactForm">
										<div id="personal" class="bay form-horizontal testimonial">
											<h3>Change Password</h3>
											<h6>Changing password yourself</h6>
											<div class="${empty form.errors ? 'hide' : 'alert alert-info'}" style="background-color: #F9F9F9;border-color: #AAAAAA;">
												<span style="color: red;display: block;">${form.errors['currentpassword']}</span>
												<span style="color: red;display: block;">${form.errors['newpassword']}</span>
												<span style="color: red;display: block;">${form.errors['newpasswordconfirmation']}</span>
												<span style="color: red;display: block;">${form.errors['general']}</span>
											</div>	
											<div class="${message == 'success' ? 'alert alert-info' : 'hide'}" style="background-color: #F9F9F9;border-color: #AAAAAA;">
												<span style="color: #84BD32; display: block;">Your password has been updated successfully</span>
											</div>																					
											<div class="control-group">
												<label for="currentpassword" class="control-label">Current Password</label>
												<div class="controls">
													<input id="currentpassword" name="currentpassword" type="password" class="required span9" >
													<span class="help-inline" style="${empty form.errors['currentpassword'] ? 'color:#84BD32;' : 'color:red;'}">*</span>
												</div>
											</div>
											
											<div class="control-group">
												<label for="inputName" class="control-label">New Password</label>
												<div class="controls">
													<input id="newpassword" name="newpassword" type="password" class="span9">
													<span class="help-inline" style="${empty form.errors['newpassword'] ? 'color:#84BD32;' : 'color:red;'}" >*</span>
												</div>
											</div>
											
											<div class="control-group">
												<label for="newpasswordconfirmation" class="control-label">Confirm New Password</label>
												<div class="controls">
													<input id="newpasswordconfirmation" name="newpasswordconfirmation" type="password" class="span9">
													<span class="help-inline" style="${empty form.errors['newpassword'] ? 'color:#84BD32;' : 'color:red;'}" >*</span>
												</div>
											</div>
										</div>
										<div class="control-group">
											<div class="controls">
												<button type="submit" class="contour-btn red">Update password</button>
											</div>
										</div>
									</form>
								</section>
					
								<div class="span4 sidebar">
									<div class="inner-spacer-left-lrg">
										<!--contact sidebar-->
										<!-- contact details widget -->
										<div class="widget widget_contact">
											<h3>Password roles</h3>
											<div class="address">
												<h5>1. It is 6-30 characters long.</h5>
												<h5>2. You use only ASCII characters (characters found on a standard US keyboard).</h5>
												<h5>3. Password contains least 6 different characters.</h5>
												<h5>4. It contains at least 1 number or symbol.</h5>
												<h5>5. It contains at least 1 capital and 1 lower-case letter.</h5>
											</div>
											<div class="email">
												<br/>
												<h3 style="margin-bottom: 1px;">Your current email</h3>
												<h5>${session.email}</h5>
											</div>
										</div>
									</div>
								</div>
								
							</div>	
											
						</div>
						
					</div>
				</div>
				
			</div>
			<div id="footer">
				<core:import url="/resources/layouts/footer.jsp" />
			</div>
			
		</div>		
	</body>
</html>