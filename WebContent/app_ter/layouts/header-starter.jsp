<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="core" %>

<style>
	div#startuplogo
	{
		margin: auto; margin-top:50px; margin-bottom: 40px; 
		width: 450px;	
	}
	
	div#startuplogoimg img
	{
		margin:auto;
	}
	
	div#startuplogotext p
	{
		text-align:center; font-size: 25px;
	}	
	
	div#startuplogotext p a
	{
		color: #777777;
	}
</style>

<!-- start sticky bar-->
<div class="sticky-bar"> 
	<!-- top bar -->
	<div class="menu-bar"> 
		<div class="pe-container"> 
			
			<div id="startuplogo">
				<div id="startuplogoimg">
					<img src="<core:url value="/resources/img/logo.jpeg"/>" width="100" height="100">
				</div>
				<div id="startuplogotext">
					<p><a href="<core:url value="/LoginService?service=login&lang=en" />">Document Search Tool</a></p>
				</div>
			</div>	
							
		</div>
		<!--fin container-->	
	</div> 
	<!--fin top bar-->
</div>
<!--end sticky bar-->