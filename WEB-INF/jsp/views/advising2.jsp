<jsp:include page='<%=(String) request.getAttribute("includeHeader")%>' />
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
				 	<input type=hidden name=advisor_button>
		    		<%@ page import= "java.util.ArrayList" %>
		    		
		    		<!-- begin processing advisors  -->
		    		<% ArrayList<String> array = (ArrayList<String>)session.getAttribute("advisors");
		    			if (array != null){ %>
		    				<button class="btn btn-warning" id=all onclick="all()">All</button>
		    				<script> function all(){
		    							document.advisor_button.value = "all";
		    							advisor_form.submit();
		    						 }
		    				</script>
		    			<%	for (int i=0;i<array.size();i++){ %>
		    					<button class="btn btn-warning" id=button<%=i%> onclick="button<%=i%>()"><%=array.get(i)%></button>
								<script> function button<%=i%>(){
										document.advisor_button.value = "button <%=i%>"
										advisor_form.submit();
								}</script>
						<%	}
		    			} 
		    			else{%>
		    				<label> Log in to see Advisor schedules. </label>
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
		<%@ page import= "uta.mav.appoint.beans.AdvisingSchedule" %>
		<!--  begin processing schedules -->
		<% ArrayList<AdvisingSchedule> schedules = (ArrayList<AdvisingSchedule>)session.getAttribute("schedules");
		    			if (schedules != null){%>
		    				<script>
		    				$(document).ready(function(){
		    					$('#calendar').fullCalendar({
		    						header: {
		    							left:'month,basicWeek,basicDay',
		    							right: 'today, prev,next',
		    							center: 'title'
		    						},
		    						eventClick: function(event,element){
		    							window.open("schedule?id="+event.id,"_self");
		    						},
		    					events: [
		 		    		<% int i = 0;
									for (i=0;i<schedules.size();i++){
		 						%> 
		 							{
		 								title:'Advising',
		 								start:'<%=schedules.get(i).getDate()+"T"+schedules.get(i).getStarttime()%>',
		 								end:'<%=schedules.get(i).getDate()+"T"+schedules.get(i).getEndtime()%>',
		 								id:<%=schedules.get(i).getUniqueid()%>
		 							}<%if(i != (schedules.size()-1)){%>,<%}%>
		 					 <%}%>	]
		    					});
		    				});
	 						</script>	
		 						<%}%>
		 							
		 		    	
	<br/><br/><hr>
</div>
<%@include file="templates/footer.jsp" %>