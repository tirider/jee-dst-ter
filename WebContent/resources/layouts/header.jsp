<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="core" %>

<!-- Add jQuery library -->
<script type="text/javascript" src="<core:url value="/resources/themes/fancybox/lib/jquery-1.10.1.min.js" />"></script>

<!-- Add fancyBox main js -->
<script type="text/javascript" src="<core:url value="/resources/themes/fancybox/source/jquery.fancybox.js?v=2.1.5" />"></script>

<!-- Add fancyBox main css -->
<link rel="stylesheet" type="text/css" href="<core:url value="/resources/themes/fancybox/source/jquery.fancybox.css?v=2.1.5" />" media="screen" />

<!-- Add Fancybox functinos-->
<script type="text/javascript" src="<core:url value="/resources/themes/fancybox/fancybox.functions.js" />"></script>

<%--class="fancybox fancybox.iframe" --%>
<style>
	div.logoimage
	{
		width: 60px; display: inline;float: left;	
	}
	div.logotext
	{
		width: 220px; padding:0px; margin:0px; dispaly: inline; float: right; margin-top:10px; margin-right: 10px;
		font-size: 20px; 
	}	
	div.logotext a
	{
		color: #777777;	
	}
</style>
<!-- start sticky bar-->
<div lang="en" class="sticky-bar"> 
	
	<!-- start info bar -->
	<div class="info-bar"> 
		<div class="pe-container"> 
			<div class="row-fluid">
				<div class="span5 tagline">You are connected as <span id="welcomeuser"><span class="accent">${empty session.username ? 'null' : session.username}</span></span> !</div>
				<div class="span7">
					<div>
						<div class="email"><i class="icon-pencil"></i><a href="mailto:admin@localhost">Email Us</a></div>
						<div class="email"><span class="accent">* </span><a href="<core:url value="/ChangePasswordService"/>" >Security</a></div>
						<div class="phone"><span class="accent">* </span><a href="<core:url value="/LogoutAction"/>" >Sign Out</a></div>
					</div>
				</div>
			</div>
		</div>
	</div>
	<!--end info bar-->
	
	<!-- start top bar -->
	<div class="menu-bar"> 
		<div class="pe-container"> 
			<header class="row-fluid">
				
				<!-- LOGO -->
				<div class="logo span4">
					<div class="logoimage">
						<img src="<core:url value="/resources/img/logo.jpeg"/>" width="60" height="60">
					</div>
					<div class="logotext">
						<a href="<core:url value="/WelcomeService"/>">Document Search Tool</a>
					</div>
				</div>
				
				<div class="menu-wrap span8">
					<!--main menu-->
					<div class="mainNav clearfix">
						<!--main nav-->
						<ul class="nav" id="navigation">
							<li class="menu-item dropdown ">
								<a href="<core:url value="/WelcomeService"/>">HOME<span class="subtitle"><span class="accent">Start Page</span></span></a>
								<ul class="dropdown-menu"></ul>
							</li>
							<li class="menu-item dropdown ">
								<a href="<core:url value="/ResearchService"/>">SEARCH<span class="subtitle"><span class="accent">Search Engine</span></span></a>
								<ul class="dropdown-menu"></ul>
							</li>
							<li class="menu-item dropdown ">
								<a href="<core:url value="/IndexationService"/>">UPLOAD<span class="subtitle"><span class="accent">Profile Setup</span></span></a>
								<ul class="dropdown-menu"></ul>
							</li>
							<li class="menu-item dropdown ">
								<a href="<core:url value="/DocumentsInboxService"/>">MY DOCUMENTS<span class="subtitle"><span class="accent">My Identification Profile</span></span></a>
								<ul class="dropdown-menu"></ul>
							</li>
						</ul>
					</div>				
				</div>
			</header><!-- fin header  -->
		</div><!--fin container-->	
	</div> 
	<!--end top bar-->
	
</div>
<!--end sticky bar-->