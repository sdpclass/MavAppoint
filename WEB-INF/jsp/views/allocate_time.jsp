<jsp:include page='<%=(String) request.getAttribute("includeHeader")%>' />
	<div id='calendar'></div>
		<%@ page import= "uta.mav.appoint.TimeSlotComponent" %>
		<%@ page import= "uta.mav.appoint.PrimitiveTimeSlot" %>
		<%@ page import= "uta.mav.appoint.CompositeTimeSlot" %>
		<%@ page import= "uta.mav.appoint.beans.AdvisingSchedule" %>
		<%@ page import= "uta.mav.appoint.beans.Appointment" %>
		<%@ page import= "java.util.ArrayList" %>
	
		<!--  begin processing schedules -->
		<% ArrayList<TimeSlotComponent> schedules = (ArrayList<TimeSlotComponent>)session.getAttribute("schedules");
		   ArrayList<Appointment> appointments = (ArrayList<Appointment>)session.getAttribute("appointments");
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
		    						eventMouseOver: function(event,jsEvent,view){
		    							$('.fc-event-inner', this).append('<div id=\"'+event.id+'\" class=\"hover-end\">'+$.fullCalendar.formatDate(event.end, 'h:mmt')+'</div>');
		    						},
		    						eventMouseout: function(event, jsEvent, view) {
		    						    $('#'+event.id).remove();
		    						},
		    						dayClick: function(date,jsEvent,view){
		    							document.getElementById("opendate").value = date.format('YYYY-MM-DD');
		    							$("#addTimeSlotModal").modal();
		    						},
		    						eventClick: function(event,element){
		    							if (event.id > 0){
		    							document.getElementById("StartTime2").value = event.start.format('HH:mm');
		    							document.getElementById("EndTime2").value = event.end.format('HH:mm');
		    							document.getElementById("pname").value = event.title;
		    							document.getElementById("Date").value = event.start.format('YYYY-MM-DD');
		    							$("#deleteTimeSlotModal").modal();
		    							}
		    							else{
		    								updateAppt.submit();
		    							}
		    						},
		    					events: [
		 		    		<% int i = 0;
									for (i=0;i<schedules.size();i++){%> 
		 								{
		 									title:'<%=schedules.get(i).getName()%>',
		 									start:'<%=schedules.get(i).getDate()+"T"+schedules.get(i).getStartTime()%>',
		 									end:'<%=schedules.get(i).getDate()+"T"+schedules.get(i).getEndTime()%>',
		 									id:<%=i%>,
		 									backgroundColor: 'blue'
		 								}
		 								<%if(i != (schedules.size()-1)||appointments != null){%>,<%}%>
		 					 		<%}	
									if (appointments != null){
										for(i=0;i<appointments.size();i++){%>
		 									{
 												title:'<%=appointments.get(i).getAppointmentType()%>',
 												start:'<%=appointments.get(i).getAdvisingDate()+"T"+appointments.get(i).getAdvisingStartTime()%>',
 												end:'<%=appointments.get(i).getAdvisingDate()+"T"+appointments.get(i).getAdvisingEndTime()%>',
 												id:<%=-i%>,
 												backgroundColor: 'orange'
 											}
 											<%if(i != (appointments.size()-1)){%>,<%}
 										}
									}%>		 					 
		 					 			]
		    					});
		    				});
	 						</script>	
		 						<%}%>

	<form name=addTimeSlot action="availability" method="post">
	<div class="modal fade" id="addTimeSlotModal" tabindex="-1">
		<div class="modal-dialog">
			<div class="modal-content">
				<div class="modal-header">
					<h4 class="modal-title" id=addTimeSlotLabel">Add Time Slots</h4>
				</div>
				<div class="modal-body">
						<label for="StartTime">Start Time:</label>
		 				<input type="time" class="form-control" name=StartTime step="300">
		 				<label for="EndTime">End Time:</label>
		 				<input type="time" class="form-control" name=EndTime step="300">
		 				<label for="Date">Date:</label>
		 				<input type="text" class="form-control" name=opendate id="opendate">
		 				<label for="Repeat">Weekly repeat duration:</label>
						<input type="text" class="form-control" name=Repeat
						value="0">
				</div>
				<div class="modal-footer">
					<button type="button" class="btn btn-default"
						data-dismiss="modal"> Close 
					</button>
					<input type="submit" value="Submit">
				</div>
			</div>
		</div>
	</div>
	</form>
	<form name=deleteTimeSlot action="ts-manage" method="post" onsubmit="return validate()">
	<div class="modal fade" id="deleteTimeSlotModal" tabindex="-1">
		<div class="modal-dialog">
			<div class="modal-content">
				<div class="modal-header">
					<h4 class="modal-title" id="deleteTimeSlotTitle">Delete Time Slot</h4>
				</div>
				<div class="modal-body">
						<label for="StartTime">Start Time:</label>
		 				<input type="time" class="form-control" name=StartTime2 id="StartTime2" step="300">
		 				<label for="EndTime">End Time:</label>
		 				<input type="time" class="form-control" name=EndTime2 id="EndTime2" step="300">
		 				<label for="Date">Date:</label>
		 				<input type="date" class="form-control" name=Date id="Date">
		 				<input type="hidden" name=pname id="pname">
				</div>
				<div class="modal-footer">
					<button type="button" class="btn btn-default"
						data-dismiss="modal"> Close 
					</button>
					<input type="submit" value="Submit">
				</div>
			</div>
		</div>
	</div>
	</form>
	<script>
	function validate(){
		return confirm('Are you sure you want to delete?');	
	}
	</script>
<%@include file="templates/footer.jsp" %>