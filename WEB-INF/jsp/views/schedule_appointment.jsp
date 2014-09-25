<jsp:include page='<%=(String) request.getAttribute("includeHeader")%>' />

<form action="schedule" method="POST">
<div>
<input type="hidden" name="id" value="<%=request.getParameter("id")%>">
UTA Student ID: <input type="text" name="studentid"> <br>
Appointment Type: <input type="text" name="type"><br>
<input type="submit" value="Submit" />
</div>
</form>

<%@include file="templates/footer.jsp" %>