<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="core" %>

<!DOCTYPE html><!-- #84BD32 green -->
<html class="js csstransforms no-csstransforms3d csstransitions desktop js" lang="en"> 
	<head>
		<title>Document Analyzer - Document Search Tool</title>  
		<meta http-equiv="content-type" content="text/html; charset=UTF-8">
		<meta name="viewport" content="width=device-width, initial-scale=1.0, maximum-scale=1.0, user-scalable=0">
		<link rel="shortcut icon" href="<core:url value="/favicon.ico" />" type="image/x-icon">		

		<link rel="stylesheet" href="<core:url value="/resources/css/template.css" />" type="text/css" media="all">
		<link rel="stylesheet" href="<core:url value="/resources/css/color.css" />"> 
		<link rel="stylesheet" href="<core:url value="/resources/css/css.css" />" type="text/css">
		<link rel="stylesheet" href="<core:url value="/resources/css/layout.css" />" type="text/css" >
		
		<script type="text/javascript" src="<core:url value="/resources/js/jquery-1.js" />"></script>
<%-- 		<script type="text/javascript" src="<core:url value="/resources/js/javascript.js" />"></script> --%>
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
								<h1>Profile Setup</h1>
							</div>
						</div> 
						
						<br/><br/>
					
						<div class="pe-container">
							<div>
								<p>
									You are about <span class="accent">creating</span> or <span class="accent">upgrading</span> your profile. It consists in <span class="accent">uploading</span> your 
									most recents articles on your topic of interest. 
									By doing this operation, you are <span class="accent">storing</span>, <span class="accent">examinating</span> and <span class="accent">sharing</span> information. 
									Additionally, you are
									<span class="accent">collaborating</span> with users who want to make possible the populariry of this product.
									<br/><br/>
									Those documents you upload, will become your <span class="accent">identification profile</span>, when some search is 
									being performed. They will not be available on your searches. It means that you wont 
									be able to find your own items but the ones shared by other users, who share a profile like yours. 									
								</p>
							</div>
							<h3>CHOOSE YOUR ARTICLE</h3>
							
							<div id="div_container_titlex">
								<form id="form-upload-file" action="/jee-dst-ter/app/uploadservice/uploadaction" enctype="multipart/form-data" method="post">
									<input type="file" id="file" name="file" class="btn" size="25" style="height: 25px;"/> <br/><br/>
									<input type="submit" name="submit" class="btn" value="Analyze Document" style="height: 30px;"/>
								</form>
								<span style="color: red;font-size: 12px;">${file.errors['size']}</span>
								<span style="color: red;font-size: 12px;">${file.errors['signature']}</span>
								<span style="color: red;font-size: 12px;">${file.errors['pages']}</span>
							</div>
							<p>
								<b>Note that your documents have to meet the following characteristics :</b>
								<br/>- Should be (<span class="accent">PDF</span>) formated.
								<br/>- Should be a <span class="accent">Research Article</span>.
								<br/>- Should not exceeds the maximum file size (<span class="accent"> 5MB</span>) allowed by the server. 
							</p>
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