<jsp:include page='<%=(String) request.getAttribute("includeHeader")%>' />
<div class="container">
	<form action="#" method="post">
	<div class="row">
	<div class="col-md-4 col-lg-4">
		<div class="form-group">
		
		 <label for="emailAddress">Email Address</label>
		 <input type="text" class="form-control" id=emailAddress
		 placeholder="yourname@uta.edu">
		</div>
		
		<!-- To do:Change to enum (Appointment, daily, both) and pull current settings  -->
		<div class="form-group">
		 <label for="notificationEmail">Notification Email</label>
		 <input type="text" class="form-control" id=notifationEmail
		 placeholder="Per appointment or daily">
		</div>
		
		<!-- To do: change this to an enum, pull info from a database with current settings -->
		<div class="form-group">
		 <label for="reminderEmail">Notification Email</label>
		 <input type="text" class="form-control" id=reminderEmail
		 placeholder="30 Minutes">
		</div>
	</div>
	</div>
	<p><a href="#" data-toggle="modal" data-target="#addApptType">Customize Appointments</a>
	<button type="submit" class="btn btn-primary">Submit</button></p>
	<form>
	<div class="modal fade" id="addApptType" tabindex="-1">
		<div class="modal-dialog">
			<div class="modal-content">
				<div class="modal-header">
					<button type="button" class="close"
					
						data-dismiss="modal"></button>
					<h4 class="modal-title" id=addApptTypeLabel">
						Add Appointment Type
					</h4>
				</div>
				<div class="modal-body">
					
				</div>
				<div class="modal-footer">
					<button type="button" class="btn btn-default"
						data-dismiss="modal"> Close 
					</button>
					<button type="button" class="btn btn-primary">
						Submit
					</button>
				</div>
			</div>
		</div>
	</div>
	</form>
</form>
</div>	
<%@include file="templates/footer.jsp"%>