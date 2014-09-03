<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>

<html>
    <head profile="http://www.w3.org/2005/10/profile">
		<title>Add New Patient – DementiaWatch Web Client</title>
		<meta http-equiv="content-type" content="text/html; charset=UTF-8">
		<link rel="icon" type="image/jpg" href="images/DementiaLogo.png">
		<link rel="stylesheet" type="text/css" href="css/mystyle.css">
		<link rel="stylesheet" href="http://yui.yahooapis.com/pure/0.5.0/pure-min.css">	
		<link rel="stylesheet" type="text/css" href="css/message.css">
		<script src="scripts/maps.js" type="text/javascript"></script>

		<%
			//Has a patient just been successfully changed?
			//Displays the success banner
			int success = 0;
			try { success = Integer.parseInt(request.getParameter("success")); }
			catch (Exception e) { success = 0; }		
			
			if (success == 1) {
				out.println("<script src='scripts/jquery-2.1.1.min.js'></script>");
				out.println("<script type='text/javascript'>");
					out.println("$(document).ready(function() {");
						out.println("$('#message').fadeIn('slow');");
						out.println("$('#message a.close-notify').click(function() {");
							out.println("$('#message').fadeOut('slow');");
							out.println("return false;");
						out.println("});");
					out.println("});");
				out.println("</script>");
			}
		%>		
		
		<%
			//If not logged in - redirect to error page and cancel processing od remaining jsp
			if (session.getAttribute("userid") == null) { response.sendRedirect("Error.jsp?error=5"); return; }
		%>
    </head>
	
    <body>
	<div id="container">
		
		<div id="header">
			<div id="header-left">
				<p>Location: <a href="PatientList.jsp">Patient List</a> > Add New Patient</p>
			</div>
			<div id="header-right">
				<p><a href="ChangePassword.jsp">Change PW</a> | <a href="Logout.jsp">Logout</a></p>				
			</div>
			<div id="header-middle">
				<p>DementiaWatch Web Client</p>
			</div>			
		</div>
	
		<div id="content">
		
			<!-- Success banner. Visibility toggled with jquery-->
			<div id='message' style="display: none;">
				<span>The patient has been successfully created.</span>
				<a href="#" class="close-notify">X</a>
			</div>
			
			<h1>Create New Patient</h1>
			
			<%			

				out.println("<form class='pure-form pure-form-aligned' action='processing/UpdatePatientDetails.jsp?patientid=0' method='post'>");
					out.println("<fieldset>");
					
						out.println("<div class='pure-control-group'>");
							out.println("<label for='patID'>Patient ID</label>");
							out.println("<input name='patID' type='text' placeholder='Not Yet Assigned' disabled>");
						out.println("</div>");
						
						out.println("<div class='pure-control-group'>");
							out.println("<label for='firstName'>First Name</label>");
							out.println("<input name='firstName' type='text' placeholder='First Name' required>");
						out.println("</div>");
						
						out.println("<div class='pure-control-group'>");
							out.println("<label for='surname'>Surname</label>");
							out.println("<input name='surname' type='text' placeholder='Surname' required>");
						out.println("</div>");
						
						out.println("<div class='pure-control-group'>");							
							out.println("<label for='gender'>Gender</label>");
							out.println("<select name='gender'>");
								out.println("<option>Male</option>");
								out.println("<option>Female</option>");
							out.println("</select>");		
						out.println("</div>");
						
						out.println("<div class='pure-control-group'>");
							out.println("<label for='age'>Age</label>");
							out.println("<input name='age' type='number' placeholder='00' required>");
						out.println("</div>");							
						
						out.println("<div class='pure-control-group'>");
							out.println("<label for='bloodType'>Blood Type</label>");
							out.println("<input name='bloodType' type='text' placeholder='O-'>");
						out.println("</div>");		

						out.println("<div class='pure-control-group'>");
							out.println("<label for='medication'>Medication</label>");
							out.println("<input name='medication' type='text' placeholder='Medication'>");
						out.println("</div>");		
						
						out.println("<div class='pure-control-group'>");
							out.println("<label for='address'>Home Address</label>");
							out.println("<input name='address' type='text' placeholder='Home Address' required>");
						out.println("</div>");		
						
						out.println("<div class='pure-control-group'>");
							out.println("<label for='suburb'>Home Suburb</label>");
							out.println("<input name='suburb' type='text' placeholder='Home Suburb' required>");
						out.println("</div>");									
						
						out.println("<div class='pure-control-group'>");
							out.println("<label for='conNum'>Contact Number</label>");
							out.println("<input name='conNum' type='text' placeholder='Contact Number' required>");
						out.println("</div>");		

						out.println("<div class='pure-control-group'>");
							out.println("<label for='emergName'>Emergency Contact Name</label>");
							out.println("<input name='emergName' type='text' placeholder='Emergency Name'>");
						out.println("</div>");		
						
						out.println("<div class='pure-control-group'>");
							out.println("<label for='emergAddress'>Emergency Contact Address</label>");
							out.println("<input name='emergAddress' type='text' placeholder='Emergency Address'>");
						out.println("</div>");		

						out.println("<div class='pure-control-group'>");
							out.println("<label for='emergSuburb'>Emergency Contact Suburb</label>");
							out.println("<input name='emergSuburb' type='text' placeholder='Emergency Suburb'>");
						out.println("</div>");		

						out.println("<div class='pure-control-group'>");
							out.println("<label for='emergNum'>Emergency Contact Number</label>");
							out.println("<input name='emergNum' type='text' placeholder='Emergency Contact Number' required>");
						out.println("</div>");	

						out.println("<div class='pure-controls'>");
							out.println("<button type='submit' class='pure-button pure-button-primary'>Submit</button>");
						out.println("</div>");
						
					out.println("</fieldset>");
				out.println("</form>");							
				
			%>			
			
		</div>	
		
	</div>
    </body>
</html>