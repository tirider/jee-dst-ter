<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="core" %>

<!DOCTYPE html><!-- #84BD32 green -->
<html> 
	<head>
		<title>Document Upload - Document Recommendation Tool</title>  
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
								<h1>Upload Document</h1>
							</div>
						</div> 
						
						<div class="pe-spacer size70"></div>
					
						<div class="pe-container">
							<div style="height: 15px"></div>
							<h4 align="center">Your document has been uploaded</h4>
							<h4 align="center">Would you like to upload a new one ? <a href="<core:url value="/UploadAction?service=creatprofile&lang=en" />">Yes</a> / <a href="<core:url value="/DocumentsInboxService?service=checkdocuments&lang=en" />">Not</a></h4>
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