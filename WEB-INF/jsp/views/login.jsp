<%@ include file="templates/header.jsp" %>

<div class="container">
	<form action="login" method="post">
	<div class="row">
	<div class="col-md-4 col-lg-4">
		<div class="form-group">
		 <label for="emailAddress">Email Address</label>
		 <input type="text" class="form-control" name=emailAddress
		 placeholder="yourname@mavs.uta.edu">
		</div>
		<div class="form-group">
		 <label for="password">Password</label>
		 <input type="password" class="form-control" name=password>
		</div>
	</div>
	</div>
	<p><button type="submit" class="btn btn-primary">Submit</button></p>
</form>
</div>
<%@ include file="templates/footer.jsp" %>