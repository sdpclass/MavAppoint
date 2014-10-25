<jsp:include page='<%=(String) request.getAttribute("includeHeader")%>' />
<style>
.panel-heading {
    padding: 5px 15px;
}

.panel-footer {
	padding: 1px 15px;
	color: #A0A0A0;
}

.profile-img {
	width: 96px;
	height: 96px;
	margin: 0 auto 10px;
	display: block;
	-moz-border-radius: 50%;
	-webkit-border-radius: 50%;
	border-radius: 50%;
}
</style>

<div class="container">
	<nav class="navbar" role="navigation">
    	  <div class="container">
		    <!-- Brand and toggle get grouped for better mobile display -->
			<a class="navbar-brand-centered" href="#">
            </a>
		    <div class="navbar-header">
		      <button type="button" class="navbar-toggle" data-toggle="collapse" data-target="#navbar-brand-centered">
		        <span class="sr-only">Toggle navigation</span>
		        <span class="icon-bar"></span>
		        <span class="icon-bar"></span>
		        <span class="icon-bar"></span>
		      </button>
		      
		    </div>

		    
		  </div><!-- /.container-fluid -->
		</nav>

	<div class="page-header">
		
		<div class="pull-right form-inline">
			<div class="btn-group">
				 	<form action="advising" method="post" name="advisor_form">
				 	<input type=hidden name=advisor_button id="advisor_button">
		    		<%@ page import= "java.util.ArrayList" %>
		    		
		    		<!-- begin processing advisors  -->
		    		<% ArrayList<String> array = (ArrayList<String>)session.getAttribute("advisors");
		    			if (array != null){ %>
		    				<button type="button" id="all1" onclick="alladvisors()">All</button>
		    				<script> function alladvisors(){
		    							document.getElementById("advisor_button").value = "all";
		    							advisor_form.submit();
		    						 }
		    				</script>
		    			<%	for (int i=0;i<array.size();i++){ %>
		    					<button type="button" id="button1<%=i%>" onclick="button<%=i%>()"><%=array.get(i)%></button>
								<script> function button<%=i%>(){
										document.getElementById("advisor_button").value = "<%=array.get(i)%>";
										advisor_form.submit();
								}</script>
						<%	}
		    			} 
		    			else{%>
		    				<div class="container" style="margin-top:40px">
								<div class="row">
									<div class="col-sm-6 col-md-4 col-md-offset-4">
										<div class="panel panel-default">
											<div class="panel-heading">
												<strong> Sign in to continue</strong>
											</div>
											<div class="panel-body">
												<form role="form" action="#" method="POST">
													<fieldset>
														<div class="row">
															<div class="center-block">
																<img class="profile-img"
																	src="https://lh5.googleusercontent.com/-b0-k99FZlyE/AAAAAAAAAAI/AAAAAAAAAAA/eu7opA4byxI/photo.jpg?sz=120" alt="">
															</div>
														</div>
														<div class="row">
															<div class="col-sm-12 col-md-10  col-md-offset-1 ">
																<div class="form-group">
																	<div class="input-group">
																		<span class="input-group-addon">
																			<i class="glyphicon glyphicon-user"></i>
																		</span> 
																		<input type="text" class="form-control" name=emailAddress placeholder="yourname@mavs.uta.edu">
																	</div>
																</div>
																<div class="form-group">
																	<div class="input-group">
																		<span class="input-group-addon">
																			<i class="glyphicon glyphicon-lock"></i>
																		</span>
																		<input type="password" class="form-control" name=password>
																	</div>
																</div>
																<div class="form-group">
																	<input type="submit" class="btn btn-lg btn-primary btn-block" value="Sign in">
																</div>
															</div>
														</div>
													</fieldset>
												</form>
											</div>
											<div class="panel-footer ">
												Don't have an account! <a href="#" onClick=""> Sign Up Here </a>
											</div>
										</div>
									</div>
								</div>
							</div>
					 <% } %>
					 <!-- end processing advisors -->	 
					</form>

			</div>
		</div>
		
	</div>
	
	<br/>
	<div class="date-display span12">
		<h3></h3>
	</div>
	<br/>
	
			<div id='calendar'></div>
		<%@ page import= "uta.mav.appoint.TimeSlotComponent" %>
		<%@ page import= "uta.mav.appoint.PrimitiveTimeSlot" %>
		<%@ page import= "uta.mav.appoint.CompositeTimeSlot" %>
		<%@ page import= "uta.mav.appoint.beans.AdvisingSchedule" %>
		
		<!--  begin processing schedules -->
		<% ArrayList<TimeSlotComponent> schedules = (ArrayList<TimeSlotComponent>)session.getAttribute("schedules");
		    			if (schedules != null){%>
		    				<script>
		    				$(document).ready(function(){
		    					$('#calendar').fullCalendar({
		    						header: {
		    							left:'month,basicWeek,basicDay',
		    							right: 'today, prev,next',
		    							center: 'title'
		    						},
		    						displayEventEnd : {
		    							month: true,
		    							basicWeek: true,
		    							'default' : true,
		    						},
		    						eventClick: function(event,element){
		    							document.getElementById("id1").value = event.id;
		    							document.getElementById("pname").value = event.title;
		    							addAppt.submit();	
		    						},
		    					events: [
		 		    		<% int i = 0;
									for (i=0;i<schedules.size();i++){
		 						%> 
		 							{
		 								title:'<%=schedules.get(i).getName()%>',
		 								start:'<%=schedules.get(i).getDate()+"T"+schedules.get(i).getStartTime()%>',
		 								end:'<%=schedules.get(i).getDate()+"T"+schedules.get(i).getEndTime()%>',
		 								id:<%=i%>
		 							}<%if(i != (schedules.size()-1)){%>,<%}%>
		 					 <%}%>	]
		    					});
		    				});
	 						</script>	
		 						<%}%>

	<form name=addAppt action="schedule" method="get">
		<input type="hidden" name=id1 id="id1">
		<input type="hidden" name=pname id="pname">
	</form>		 							
		 		    	
	<br/><br/><hr>
</div>
<%@include file="templates/footer.jsp" %>