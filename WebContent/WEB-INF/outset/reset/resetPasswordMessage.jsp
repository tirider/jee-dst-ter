<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" session="false" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="core" %>

<!DOCTYPE html>
<html lang="en">
	<head>
		<title>Password Reset - Document Recommendation Tool</title>
		<meta charset="utf-8">		
		<meta http-equiv="content-type" content="text/html; charset=UTF-8">
		<meta name="viewport" content="width=device-width, initial-scale=1.0">
		<meta http-equiv="refresh" content="10;url=<core:url value="/LoginService" />" >
		<link rel="shortcut icon" href="<core:url value="/favicon.ico" />" type="image/x-icon">
		
		<link href="<core:url value="/resources/css/lg/bootstrap.css" />" rel="stylesheet">
		<link href="<core:url value="/resources/css/lg/social.css" />" rel="stylesheet">
		<link href="<core:url value="/resources/css/lg/font-awesome.css" />" rel="stylesheet">
		<link href="<core:url value="/resources/css/lg/bootstrap-responsive.css" />" rel="stylesheet">
		<link href="<core:url value="/resources/css/color.css" />" rel="stylesheet">
		<link href="<core:url value="/resources/css/template.css" />" rel="stylesheet">
		<link href="<core:url value="/resources/css/layout.css" />" rel="stylesheet" type="text/css">
	</head>
	<body>
	
		<!-- calling header -->
		<div id="header">
			<core:import url="/resources/layouts/header-starter.jsp" />
		</div>
			
		<!-- fixed content -->
		<div class="container">
			<div>
				<p align="center"><b>Your request has been parsed correctly</b></p>
				<p align="center">We have sent you an email with all information you need.<br />
				If you do not receive one in five minutes please check your spam folder.</p>
				<p align="center">In few seconds you will be automatically redirected  to the authentication page</p>
			</div>		 	 
		</div>			
			
		<!-- calling footer -->
		<div id="startupfooter">
			<core:import url="/resources/layouts/footer.jsp" />
		</div>
	</body>
</html>