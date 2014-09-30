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
import uta.mav.appoint.login.LoginUser;

/**
 * Servlet implementation class ViewAppointmentServlet
 */
@WebServlet("/ViewAppointmentServlet")
public class ViewAppointmentServlet extends HttpServlet{
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
		LoginUser user = (LoginUser)session.getAttribute("user");
		if (user != null){
			try{
				header = "templates/" + user.getHeader() + ".jsp";
				DatabaseManager dbm = new DatabaseManager();
				ArrayList<Appointment> appointments = dbm.getAppointments(user);
				if (appointments.size() != 0&&appointments != null){
					session.setAttribute("appointments", appointments);
				}
			}
			catch(Exception e){
				System.out.printf(e.toString());
			}
		}
		else{
			header = "templates/header.jsp";
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

}
