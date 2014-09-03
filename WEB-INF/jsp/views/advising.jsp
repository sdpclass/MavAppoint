<%@include file="templates/header.jsp" %>
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
		
		<div class="pull-left form-inline">
			<div class="btn-group">
				<button class="btn btn-primary" data-calendar-nav="prev"><< Prev</button>
				<button class="btn btn-default" data-calendar-nav="today">Today</button>
				<button class="btn btn-primary" data-calendar-nav="next">Next >></button>
			</div>
		</div>
		
		<div class="pull-right form-inline">
			<div class="btn-group">
				<button class="btn btn-warning" data-calendar-view="year">Year</button>
				<button class="btn btn-warning active" data-calendar-view="month">Month</button>
				<button class="btn btn-warning" data-calendar-view="week">Week</button>
				<button class="btn btn-warning" data-calendar-view="day">Day</button>
			</div>
		</div>
		
	</div>
	
	<br/>
	<div class="date-display span12">
		<h3></h3>
	</div>
	<br/>
	
	<div class="row">
		<div class="col-md-12">
			<div id="calendar"></div>
		</div>
	</div>
	<br/><br/><hr>
</div>
<%@include file="templates/footer.jsp" %>