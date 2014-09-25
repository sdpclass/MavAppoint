package uta.mav.appoint;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import uta.mav.appoint.beans.Appointment;
import uta.mav.appoint.db.DatabaseManager;
import uta.mav.appoint.helpers.LoginInterface;

/**
 * Servlet implementation class ViewAppointmentServlet
 */
@WebServlet("/ViewAppointmentServlet")
public class ViewAppointmentServlet extends HttpServlet implements LoginInterface{
	private static final long serialVersionUID = 1L;
    HttpSession session;   
    String header;
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ViewAppointmentServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		session = request.getSession();
		String role = (String)session.getAttribute("role");
		String header = "";
		if (role != null){
			try{
				DatabaseManager dbm = new DatabaseManager();
				ArrayList<Appointment> appointments = dbm.getAppointments(session.getAttribute("emailAddress").toString(),Integer.parseInt(session.getAttribute("role").toString()));
				if (appointments.size() != 0&&appointments != null){
					session.setAttribute("appointments", appointments);
				}
			}
			catch(Exception e){
				System.out.printf(e.toString());
			}
		}
		try{
			header = displayHeader(Integer.parseInt(role));
		}
		catch(NumberFormatException e){
			header = displayHeader(0);
		}
		request.setAttribute("includeHeader", header);
		request.getRequestDispatcher("/WEB-INF/jsp/views/view_appointments.jsp").forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		try{
			int id = Integer.parseInt(request.getParameter("cancel_button"));
			DatabaseManager dbm = new DatabaseManager();
			Boolean result = dbm.cancelAppointment(id);
			if (result == true){
				response.setHeader("Refresh","5; URL=advising");
				request.getRequestDispatcher("/WEB-INF/jsp/views/success.jsp").forward(request,response);
			}
			else {
				response.setHeader("Refresh","15; URL=advising");
				request.getRequestDispatcher("/WEB-INF/jsp/views/failure.jsp").forward(request,response);
			}
		}
		catch(Exception e){
			System.out.printf(e.toString());
		}
	}
	
@Override
public String displayHeader(int role){
		String header;
		switch (role){
		case 1:
			header = "templates/advisor_header.jsp";
			break;
		case 2:
			header = "templates/student_header.jsp";
			break;
		case 3:
			header = "templates/admin_header.jsp";
			break;
		case 4:
			header = "templates/faculty_header.jsp";
			break;
		default:
			header = "templates/header.jsp";
	}
		return header;
	}
}
