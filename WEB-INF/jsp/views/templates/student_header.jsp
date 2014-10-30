<%@include file="top_header.jsp" %>
			<div>
				<ul class="nav navbar-nav">
				<li><a href="advising"> Advising </a></li>
				<li><a href="appointments"> Appointments </a></li>
				</ul>
				<ul class="nav navbar-nav navbar-right">
				<li><a href="#">You are logged in as a Student.</a></li>
				<li><a href="logout"><span class="glyphicon glyphicon-log-in">Logout</a></li>
				</ul>
			</div>
		</div>
	</nav>
	<%@ page import= "uta.mav.appoint.beans.Appointment" %>
	<% Appointment nextAppt = (Appointment)session.getAttribute("studentapp");
	if (nextAppt != null){%>
	<nav class="navbar navbar-inverse navbar-fixed-bottom">
	<div id="inversenavbar" class="container-fluid" style="background-color:orange">
			<div>
				<table class="table">
    			<thead>
        		<tr>
            	<th><b>Upcoming Advising Appointment:</b></th>
            	<th>Appointment Date</th>
				<th>Advising Type</th>
				<th>Start Time</th>
				<th>End Time</th>
				</tr>
    			</thead>
    			<tr>
    				<td>
    				<td><%=nextAppt.getAdvisingDate()%></td>
					<td><%=nextAppt.getAppointmentType()%></td>
					<td><%=nextAppt.getAdvisingStartTime()%></td>
					<td><%=nextAppt.getAdvisingEndTime()%></td>
					</tr>
				</table>
			</div>
		</div>
	</nav>
	<%}%>
</div>