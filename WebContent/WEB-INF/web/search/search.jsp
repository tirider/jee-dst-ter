<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="core" %>

<!DOCTYPE html><!-- #84BD32 green -->
<html class="js csstransforms no-csstransforms3d csstransitions desktop js" lang="en"> 
	<head>
		<title>Search - Document Search Tool</title>  
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
						<div class="row-fluid pe-block">
							<div class="page-title">
								<div class="pe-container">
									<h1>Search Engine</h1>
								</div>
							</div> 
							
							<br/><br/>
						
							<div class="pe-container">
								<div >
									<p>
									<span class="accent">Searching</span> allows you to <span class="accent">view</span> and <span class="accent">download</span> a set of documents coming from users
									sharing a profile like yours.
									<span class="accent">No more wondering about effective or successful searching terms, the program does it for you"</span>.
									<br/>
									Do not distress if you do not find any useful results. A reason  
									could be, that no any other profile shares the information to answer your question at the moment.
									</p>
								</div>				
							    <div style="margin: auto;width:34%;text-align:left">
									<form action="<core:url value="/ResearchService" />" method="post" >
										<div class="input-append" >
											<input id="text" name="text" value="${text}" class="" placeholder="Type your search here" type="search">
											<button class="btn" type="submit">Search</button>
										</div> 
									</form>				    
							    </div>
								<p align="center" style="margin-bottom: 10px;"><span>${message}</span></p>
								<div>
									<core:forEach var="document" items="${documents}">
										<div align="left">
											<p style="color: #000;">
												<span style="font-size: 11px;color: #777;">[${document.format}]</span>
												<a target="blank" href="<core:url value="/FileViewerService?service=viewf&dc=${document.id}&lang=en" />"><span style="text-decoration: underline;">${document.title}</span></a><br/>
												<span style="color:#777777"><strong>${document.url}</strong></span><br/>
												<span style="color: black;">${document.content}...</span>
											</p>
										</div>
									</core:forEach>					
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