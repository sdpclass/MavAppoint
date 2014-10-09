<jsp:include page='<%=(String) request.getAttribute("includeHeader")%>' />
<div class="container">
	<form action="availability" method="post">
	<div class="row">
	<div class="col-md-4 col-lg-4">
		<div class="form-group">
		 <label for="StartTime">Start Time:</label>
		 <input type="text" class="form-control" name=StartTime
		 placeholder="14:00">
		</div>
		<div class="form-group">
		 <label for="EndTime">End Time:</label>
		 <input type="text" class="form-control" name=EndTime
		 placeholder="16:00">
		</div>
		<div class="form-group">
		 <label for="Date">Date:</label>
		 <input type="text" class="form-control" name=Date
		 placeholder="yyyy-MM-dd">
		</div>
		<div class="form-group">
		<label for="Repeat">Duration of Repeat:</label>
		<input type="text" class="form-control" name=Repeat
		value="0">
		</div>
		
	</div>
	</div>
	<p><button type="submit" class="btn btn-primary">Submit</button></p>
</form>
</div>
<%@include file="templates/footer.jsp" %>