<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="core" %>

<!DOCTYPE html><!-- #84BD32 green -->
<html class="js csstransforms no-csstransforms3d csstransitions desktop js" lang="en"> 
	<head>
		<title>Home - Document Search Tool</title>  
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
								<h1>Start Page</h1>
							</div>
						</div> 
						<br /><br/>
						<%-- <div class="pe-spacer size70"></div> --%>
					
						<div class="pe-container">
							<!--block 1-->
							<div class="row-fluid pe-block">
								
								<section class="row-fluid">
									<div class="span6">
										<section class="row-fluid">
											<div class="span12"><h3 style="color: #555555;">Welcome to Document Search Tool</h3></div>
										</section>
										<p align="justify">
										This product  makes the process of searching for scientific information easy and fast. It  recommends you
								     	results based on your personal <span class="accent">keywords</span> and <span class="accent">profile</span>.	
								     	</p>
								     	<br/>
								     	<div class="divider dotted"></div>
										
										<section class="row-fluid">
											<div class="span12"><h3 style="color: #555555;">Your account is more than just a searching tool</h3></div>
										</section>
										<p align="justify">
										Furthermore, you're naturally going to<span class="accent"> share, store, organize, collaborate and discover</span> scientific information of your interest.
										Use this product to go directly to a reduced number of results containing what you really want, 
										gathered from people who share a profile like yours.
										</p>
										<!-- <div class="divider dotted"></div>  -->
										
											
									</div>
					
									<div class="span6">	
										<!--feature-->
										<div class="feature pe-block">
											<span class="featureIcon">
												<i>1</i>
											</span>
											<div class="feature-title">
												<h3 style="color: #555555;">Keywords</h3>
												<h6>What to search ?</h6>
											</div>
											<div class="featureContent">
												<p align="justify">
												You're going to use keywords to comunicate with the search engine. Keywords are 
												presented like word(s), identifying the content of your search results.  
												</p>
											</div>
										</div>
										<!--end feature-->
										<!--feature-->
										<div class="feature pe-block">
											<span class="featureIcon">
												<i>2</i>
											</span>
											<div class="feature-title">
												<h3 style="color: #555555;">Profile</h3>
												<h6>Where to search ?</h6>
											</div>
											<div class="featureContent">
												<p align="justify">
												The profile, identifies users when some search is carried out.
												You will create or upgrade it by <span class="accent">constantly uploading</span> useful research articles		
												on your topic of interest. 
												It'll be automatically calculated considering your uploaded items. 
												<br/><span class="accent">"The more recent documents you upload, the more interesting results you'll have"</span>
												</p>
											</div>
										</div>
									</div>
								</section>
							</div><!--end block 1-->
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