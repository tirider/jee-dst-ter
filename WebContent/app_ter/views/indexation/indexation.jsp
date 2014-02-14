<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="core" %>

<!DOCTYPE html><!-- #84BD32 green -->
<html class="js csstransforms no-csstransforms3d csstransitions desktop js" lang="en"> 
	<head>
		<title>Document Upload - Document Search Tool</title>  
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
		<div id="container">
			
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
						
						<br/><br />
					
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
							<h3>DOCUMENT INFORMATION</h3>
							<div class="footer-container container">
								<div class="clear"></div>
								<form action="<core:url value="/IndexationService?service=buildprofile&lang=en" />" method="post">
									<p style="font-size: 12px">
										<input id="filecode" name="filecode" type="hidden" value="${file.filecode}" readonly="readonly"/>
										
										<label for="filename">File Name :</label>
										<input id="filename" name="filename" type="text" value="${file.filename}" readonly="readonly" size="30"/>
										
										<input id="filetempname" name="filetempname" type="hidden" value="${file.filetempname}" readonly="readonly"/><br/>
										
										<input id="fileformat" name="fileformat" type="hidden" value="${file.fileformat}" readonly="readonly"/>
										
										<label for="filenumberofpages">Number Of Pages :</label>
										<input id="filenumberofpages" name="filenumberofpages" type="text" value="${file.filenumberofpages}" readonly="readonly" size="30"/><br/>
													
										<label for="filesize">File Size :</label>
										<input id="filesize" name="filesize" type="text" value="${file.filesize}" readonly="readonly" size="30"/>
										<label for="filesize">KB</label>
							        </p>
							        <div style="text-align: left;">
							        	<input type="submit" id="indexer" value="Upload Document" class="btn">
							        </div>
								</form>
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