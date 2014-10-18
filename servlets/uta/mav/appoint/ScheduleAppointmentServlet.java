package uta.mav.appoint;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import uta.mav.appoint.beans.AppointmentType;
import uta.mav.appoint.db.DatabaseManager;
import uta.mav.appoint.login.LoginUser;

public class ScheduleAppointmentServlet extends HttpServlet{
	private static final long serialVersionUID = -5925080374199613248L;
	HttpSession session;
	String header;
	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		session = request.getSession();
		LoginUser user = (LoginUser)session.getAttribute("user");
		if (user != null){
			try{
				header = "templates/" + user.getHeader() + ".jsp";
				int id = Integer.parseInt(request.getParameter("id1"));
				ArrayList<TimeSlotComponent> array = (ArrayList<TimeSlotComponent>)session.getAttribute("schedules");
				DatabaseManager dbm = new DatabaseManager();
				ArrayList<AppointmentType> ats = dbm.getAppointmentTypes(request.getParameter("pname"));
				session.setAttribute("timeslot", array.get(id));
				session.setAttribute("appointmenttypes", ats);
			}
			catch(Exception e){
				
			}
		}
		else{
			header = "templates/header.jsp";
		}
		request.setAttribute("includeHeader", header);
		request.getRequestDispatcher("/WEB-INF/jsp/views/schedule_appointment.jsp").forward(request, response);
	}


	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		session = request.getSession();
		try{
		int id = Integer.parseInt(request.getParameter("id2"));
		String studentid = request.getParameter("studentid");
		String type = request.getParameter("apptype");
		String pname = request.getParameter("pname");
		String[] parts = (request.getParameter("start")).split(" ");
		String date = parts[3] + "-" + convertDate(parts[1]) + "-" + parts[2];
		parts = parts[4].split(":");
		String start = parts[0] + ":" + parts[1];
		parts = (request.getParameter("end")).split(" ");
		parts = parts[4].split(":");
		String end = parts[0] + ":" + parts[1];
		String email = ((LoginUser)session.getAttribute("user")).getEmail();
		System.out.println();
		System.out.println(id);
		System.out.println(studentid);
		System.out.println(type);
		System.out.println(pname);
		System.out.println(date);
		System.out.println(start);
		System.out.println(end);
		System.out.println(email);
		System.out.println();
		
		
		DatabaseManager dbm = new DatabaseManager();
		Boolean result = dbm.createAppointment(id,studentid,type,email,pname,date, start, end);
		if (result == true){
			response.setHeader("Refresh","2; URL=advising");
			request.getRequestDispatcher("/WEB-INF/jsp/views/success.jsp").forward(request,response);
		}
		else{
			response.setHeader("Refresh","2; URL=advising");
			request.getRequestDispatcher("/WEB-INF/jsp/views/failure.jsp").forward(request,response);
		}
			
		}
		catch(Exception e){
			System.out.printf(e.toString());
		}
	}
	
	public String convertDate(String d){
		if (d.equals("Jan")){
			return "1";
		}if (d.equals("Feb")){
			return "2";
		}if (d.equals("Mar")){
			return "3";
		}if (d.equals("Apr")){
			return "4";
		}if (d.equals("May")){
			return "5";
		}if (d.equals("Jun")){
			return "6";
		}if (d.equals("Jul")){
			return "7";
		}if (d.equals("Aug")){
			return "8";
		}if (d.equals("Sep")){
			return "9";
		}if (d.equals("Oct")){
			return "10";
		}if (d.equals("Nov")){
			return "11";
		}if (d.equals("Dec")){
			return "12";
		}
		return null;
	}

}

