package uta.mav.appoint;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import uta.mav.appoint.beans.AdvisingSchedule;
import uta.mav.appoint.db.DatabaseManager;
import uta.mav.appoint.helpers.LoginInterface;

/**
 * Servlet implementation class AdvisingServlet
 */
@WebServlet("/AdvisingServlet")
public class AdvisingServlet extends HttpServlet implements LoginInterface{
	private static final long serialVersionUID = 1L;
	HttpSession session;
	String header;
	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		session = request.getSession();
		String role = (String)session.getAttribute("role");
		if (role != null){
			try{
					//must be logged in to see advisor schedules - safety concern
					DatabaseManager dbm = new DatabaseManager();
					ArrayList<String> array =  dbm.getAdvisors();
					if (array.size() != 0){
						session.setAttribute("advisors", array);
					}
					else{
						//no advisors for department?
					}
					ArrayList<AdvisingSchedule> schedules = dbm.getAdvisorSchedule("all");
					if (schedules.size() != 0){
						session.setAttribute("schedules", schedules);
					}
			}
			catch(Exception e){
				
			}
		}
		try{
			header = displayHeader(Integer.parseInt(role));
		}
		catch(NumberFormatException e){
			header = displayHeader(0);
		}
		request.setAttribute("includeHeader", header);
		request.getRequestDispatcher("/WEB-INF/jsp/views/advising.jsp").forward(request, response);
	}


	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		session = request.getSession();
		String role = (String)session.getAttribute("role");
		if (role != null){
			try{
					//must be logged in to see advisor schedules - safety concern
					DatabaseManager dbm = new DatabaseManager();
					ArrayList<String> array = dbm.getAdvisors(); 
					if (array.size() != 0){
						session.setAttribute("advisors", array);
					}
					else{
						//no advisors for department?
					}
					
					//get advisor schedules
					String advisor = request.getParameter("advisor_button");
					if (advisor != null){
						ArrayList<AdvisingSchedule> schedule = dbm.getAdvisorSchedule(advisor);
					}
			}
			catch(Exception e){
				
			}
		}
			try{
				header = displayHeader(Integer.parseInt(role));
			}
			catch(NumberFormatException e){
				header = displayHeader(0);
			}
		request.setAttribute("includeHeader", header);
		request.getRequestDispatcher("/WEB-INF/jsp/views/advising.jsp").forward(request, response);
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
