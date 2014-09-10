package uta.mav.appoint;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * Servlet implementation class AdvisingServlet
 */
@WebServlet("/AdvisingServlet")
public class AdvisingServlet extends HttpServlet {
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
					ArrayList<String> array = DatabaseManager.getInstance().getAdvisors();
					if (array.size() != 0){
						session.setAttribute("advisors", array);
					}
					else{
						//no advisors for department?
					}
			}
			catch(Exception e){
				
			}
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
		request.getRequestDispatcher("/WEB-INF/jsp/views/advising.jsp").forward(request, response);
	}


	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		session = request.getSession();
		String role = (String)session.getAttribute("role");
		if (role != null){
			try{
					//must be logged in to see advisor schedules - safety concern
					ArrayList<String> array = DatabaseManager.getInstance().getAdvisors();
					if (array.size() != 0){
						session.setAttribute("advisors", array);
					}
					else{
						//no advisors for department?
					}
					
					//get advisor schedules
					String advisor = request.getParameter("advisor_button");
					if (advisor != null){
						ArrayList<GetSet> schedule = DatabaseManager.getInstance().getAdvisorSchedule(advisor);
					}
			}
			catch(Exception e){
				
			}
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
		request.getRequestDispatcher("/WEB-INF/jsp/views/advising.jsp").forward(request, response);
	}

}
