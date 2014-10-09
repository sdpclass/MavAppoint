<jsp:include page='<%=(String) request.getAttribute("includeHeader")%>' />
		<div id='calendar'></div>
   		<%@ page import= "java.util.ArrayList" %>
		<%@ page import= "uta.mav.appoint.TimeSlotComponent" %>
		<%@ page import= "uta.mav.appoint.PrimitiveTimeSlot" %>
		<%@ page import= "uta.mav.appoint.CompositeTimeSlot" %>
		
		<% TimeSlotComponent schedule = (TimeSlotComponent)session.getAttribute("timeslot");
		    			if (schedule != null){%>
		    				<script>
		    				$(document).ready(function(){
		    					$('#calendar').fullCalendar({
		    						defaultView : 'basicDay',
		    						viewRender: function(view, element){
		    							$('#calendar').fullCalendar('gotoDate','<%=schedule.getDate()%>');
		    						},
		    						displayEventEnd : {
		    							month: false,
		    							basicWeek: false,
		    							'default' : false,
		    						},
		    						eventClick: function(event,element){
		    							document.addAppt.id.value = event.id;
		    							$('#addApptModal').modal();
		    							},
		    					events: [
		    					    <%=schedule.getEvent(3)%>     
		    					        ]
		    					});
		    				});
	 						</script>	
		 					<%}%>
	<br/><br/><hr>
	<form name=addAppt action="schedule" method="post">
	<div class="modal fade" id="addApptModal" tabindex="-1">
		<div class="modal-dialog">
			<div class="modal-content">
				<div class="modal-header">
					<h4 class="modal-title" id=addApptTypeLabel">Add Appointment</h4>
				</div>
				<div class="modal-body">
						<input type="hidden" name="id" value="<%=request.getParameter("id")%>">
						UTA Student ID: <input type="text" name="studentid"> <br>
						Appointment Type: <input type="text" name="appointmenttype"><br>
						Description: <input type="text" name="description">
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
	
<%@include file="templates/footer.jsp" %>