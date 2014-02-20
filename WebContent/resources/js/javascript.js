jQuery(document).ready(function() {
	$('#form-upload-file').submit(function(event) 
	{
//		if( $('#file').val() !=""){
			var jForm = new FormData();
			
			jForm.append("file", $('#file').get(0).files[0]);
			
			$.ajax({
				type : 'POST',
		        url : '/app/uploadservice/uploadaction',
		        data: jForm,
		       	mimeType:"multipart/form-data",
		        contentType: false,
		        cache: false,
		        processData:false,
		        success : function(data) { 
		        	if(data.unsigned == false) {
		        		alert("Cool it doesn't works !");
					}			        		
		        	else {
		        		alert("Cool it works !");
					}			        		
		        	
		        },
		        error : function(data) { 
		        	 
		        }
			});
//			return false;
//		}
//		alert("Nop nopss!!!");
		        					
		event.preventDefault();
		event.unbind();
	});
});