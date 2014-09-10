<jsp:include page='<%=(String) request.getAttribute("includeHeader")%>' />
<script>
$(document).ready(function(){
	$('#calendar').fullCalendar({
		header: {
			left:'month,basicWeek,basicDay',
			right: 'today, prev,next',
			center: 'title'
		},
		dayClick: function(date,jsEvent,view){
			alert('Clicked on: '+ date.format());
		}
	})
});
</script>
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
		
	<br/><br/><hr>
</div>
<%@include file="templates/footer.jsp" %>