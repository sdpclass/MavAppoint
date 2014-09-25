package uta.mav.appoint;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import uta.mav.appoint.helpers.LoginInterface;

/**
 * Servlet implementation class IndexServlet
 */
public class IndexServlet extends HttpServlet implements LoginInterface{
	private static final long serialVersionUID = 1L;
	HttpSession session;
	private String header;
	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		session = request.getSession();
		String role = (String)session.getAttribute("role");
		if (role != null){
			try{
				header = displayHeader(Integer.parseInt(role));
			}
			catch(NumberFormatException e){
				header = displayHeader(0);
			}
		}
		request.setAttribute("includeHeader", header);
		request.getRequestDispatcher("/WEB-INF/jsp/views/index.jsp").forward(request, response);
	}
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		session = request.getSession();
		String auth = (String)session.getAttribute("authenticated");
		if (auth != null){
			if (auth.equals("1")){
				header = "templates/loggedin_header.jsp";
				}
		}
		request.setAttribute("includeHeader", header);
		request.getRequestDispatcher("/WEB-INF/jsp/views/index.jsp").forward(request, response);
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

	
	