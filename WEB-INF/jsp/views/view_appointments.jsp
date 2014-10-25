<jsp:include page='<%=(String) request.getAttribute("includeHeader")%>' />

		<div class="align-center form-inline">
			<div class="btn-group">
				 	<form action="appointments" method="post" name="cancel">
				 	<input type=hidden name=cancel_button id="cancel_button">
		    		<%@ page import= "java.util.ArrayList" %>
		    		<%@ page import= "uta.mav.appoint.beans.Appointment" %>
		    		<!-- begin processing appointments  -->
		    		<% ArrayList<Appointment> array = (ArrayList<Appointment>)session.getAttribute("appointments");
		    			if (array != null){%>
							<%for (int i=0;i<array.size();i++){ %>
								<div class="panel panel-default">
		    					<%=array.get(i).toString() %>
		    					<button type="button" id=button1<%=i%> onclick="button<%=i%>()">Cancel</button>
								<script> function button<%=i%>(){
										document.getElementById("cancel_button").value = "<%=array.get(i).getAppointmentId()%>"; 
										cancel.submit();
								}</script>
								</div>
						<%	}
		    			}
		    			%> 
					 <!-- end processing advisors -->	 
					</form>

			</div>
		</div>


<%@include file="templates/footer.jsp" %>