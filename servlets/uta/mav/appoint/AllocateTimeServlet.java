package uta.mav.appoint;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import uta.mav.appoint.beans.AllocateTime;
import uta.mav.appoint.beans.Appointment;
import uta.mav.appoint.db.DatabaseManager;
import uta.mav.appoint.helpers.TimeSlotHelpers;
import uta.mav.appoint.login.AdvisorUser;
import uta.mav.appoint.login.LoginUser;

public class AllocateTimeServlet extends HttpServlet {
	HttpSession session;
	String header;
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		session = request.getSession();
		try{
			AdvisorUser user = (AdvisorUser)session.getAttribute("user");
			if (user != null && user instanceof AdvisorUser){
					header = "templates/" + user.getHeader() + ".jsp";
					DatabaseManager dbm = new DatabaseManager();
					ArrayList<TimeSlotComponent> schedules = dbm.getAdvisorSchedule(user.getPname());
					if (schedules.size() != 0){
						session.setAttribute("schedules", schedules);
					}
					ArrayList<Appointment> appointments = dbm.getAppointments(user);
					if (appointments.size() != 0){
						session.setAttribute("appointments", appointments);
					}
				}
			else{
				header = "templates/header.jsp";
				request.setAttribute("includeHeader", header);
				request.getRequestDispatcher("/WEB-INF/jsp/views/login.jsp").forward(request, response);
			}
		}
		catch(Exception e){
			header = "templates/header.jsp";
			System.out.println(e);
		}
		request.setAttribute("includeHeader", header);
		request.getRequestDispatcher("/WEB-INF/jsp/views/allocate_time.jsp").forward(request, response);

	}
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		session = request.getSession();
		String date = request.getParameter("opendate");
		String startTime = request.getParameter("StartTime");
		String endTime = request.getParameter("EndTime");
		LoginUser user = (LoginUser)session.getAttribute("user");
		String repeat = request.getParameter("Repeat");
		int rep;
		try{
			rep = Integer.parseInt(repeat);
		}
		catch(Exception e){
			rep = 0;
		}
		AllocateTime at = new AllocateTime();
		at.setDate(date);
		at.setEndTime(endTime);
		at.setStartTime(startTime);
		at.setEmail(user.getEmail());
		try{
			if (user instanceof AdvisorUser){
				DatabaseManager dbm = new DatabaseManager();
				Boolean result = dbm.addTimeSlot(at);
				//increase the day of month by 7 for repeat
				for (int i=0;i<rep;i++){
					at.setDate(TimeSlotHelpers.addDate(at.getDate(),1));
					result = dbm.addTimeSlot(at);
				}
				if (result == true){
					response.setHeader("Refresh","2; URL=index");
					request.getRequestDispatcher("/WEB-INF/jsp/views/success.jsp").forward(request,response);
				}
				else
					response.setHeader("Refresh","2; URL=index");
					request.getRequestDispatcher("/WEB-INF/jsp/views/failure.jsp").forward(request,response);
			}
			else
				response.sendRedirect("login");
		}
		catch(Exception e){
			System.out.printf(e.toString());
		}
	}
}
