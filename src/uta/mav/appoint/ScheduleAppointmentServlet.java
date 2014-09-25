package uta.mav.appoint;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import uta.mav.appoint.beans.AdvisingSchedule;
import uta.mav.appoint.db.DatabaseManager;

public class ScheduleAppointmentServlet extends HttpServlet{
	private static final long serialVersionUID = -5925080374199613248L;
	HttpSession session;
	String header;
	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		session = request.getSession();
		String role = (String)session.getAttribute("role");
		if (role != null){
			if (role.equals("1")){
				header = "templates/advisor_header.jsp";
				}
			if (role.equals("2")){
				header = "templates/student_header.jsp";
				}
			if (role.equals("3")){
				header = "templates/admin_header.jsp";
				}
			if (role.equals("4")){
				header = "templates/faculty_header.jsp";
				}
			
		}
		else{
			header = "templates/header.jsp";
		}
		
		request.setAttribute("includeHeader", header);
		request.getRequestDispatcher("/WEB-INF/jsp/views/schedule_appointment.jsp").forward(request, response);
	}


	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		try{
		int id = Integer.parseInt(request.getParameter("id"));
		String studentid = request.getParameter("studentid");
		String type = request.getParameter("type");
		String email = session.getAttribute("emailAddress").toString();
		DatabaseManager dbm = new DatabaseManager();
		Boolean result = dbm.createAppointment(id,studentid,type,email);
		if (result == true){
			response.setHeader("Refresh","5; URL=advising");
			request.getRequestDispatcher("/WEB-INF/jsp/views/success.jsp").forward(request,response);
		}
		else{
			response.setHeader("Refresh","15; URL=advising");
			request.getRequestDispatcher("/WEB-INF/jsp/views/failure.jsp").forward(request,response);
		}
			
		}
		catch(Exception e){
			System.out.printf(e.toString());
		}
	}

}

