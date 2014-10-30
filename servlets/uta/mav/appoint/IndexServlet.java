package uta.mav.appoint;

import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import uta.mav.appoint.beans.Appointment;
import uta.mav.appoint.db.DatabaseManager;
import uta.mav.appoint.login.LoginUser;
import uta.mav.appoint.login.StudentUser;

/**
 * Servlet implementation class IndexServlet
 */
public class IndexServlet extends HttpServlet{
	private static final long serialVersionUID = 1L;
	HttpSession session;
	private String header;
	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		session = request.getSession();
		LoginUser user;
		try{
			user = (StudentUser)session.getAttribute("user");
		}
		catch(Exception e){
			user = (LoginUser)session.getAttribute("user");
				
		}
		if (user != null){
			try{
				header = "templates/" + user.getHeader() + ".jsp";
			if (user instanceof StudentUser){
				DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
				Date date = new Date();
				DatabaseManager dbm = new DatabaseManager();
				Appointment app = dbm.getAppointment(dateFormat.format(date),user.getEmail());
				session.setAttribute("studentapp",app);
			}
			}
			catch(Exception e){
				System.out.println(e);
			}
		}
		else{
			header = "templates/header.jsp";
		}
		
		request.setAttribute("includeHeader", header);
		request.getRequestDispatcher("/WEB-INF/jsp/views/index.jsp").forward(request, response);
	}
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//nothing posts to index yet
		}
}

	
	