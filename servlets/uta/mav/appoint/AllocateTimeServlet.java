package uta.mav.appoint;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import uta.mav.appoint.beans.AllocateTime;
import uta.mav.appoint.db.DatabaseManager;
import uta.mav.appoint.login.AdvisorUser;
import uta.mav.appoint.login.LoginUser;

public class AllocateTimeServlet extends HttpServlet {
	HttpSession session;
	String header;
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		session = request.getSession();
		LoginUser user = (LoginUser)session.getAttribute("user");
		if (user != null){
			try{
					header = "templates/" + user.getHeader() + ".jsp";
			}
			catch(Exception e){
				
			}
		}
		else{
			header = "templates/header.jsp";
		}
		request.setAttribute("includeHeader", header);
		request.getRequestDispatcher("/WEB-INF/jsp/views/allocate_time.jsp").forward(request, response);

	}
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		session = request.getSession();
		String date = request.getParameter("Date");
		String startTime = request.getParameter("StartTime");
		String endTime = request.getParameter("EndTime");
		LoginUser user = (LoginUser)session.getAttribute("user");
		AllocateTime at = new AllocateTime();
		at.setDate(date);
		at.setEndTime(endTime);
		at.setStartTime(startTime);
		at.setEmail(user.getEmail());
		try{
			if (user instanceof AdvisorUser){
				DatabaseManager dbm = new DatabaseManager();
				Boolean result = dbm.addTimeSlot(at);
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

	int countTimeSlots(String startTime, String endTime){
		int count = 0;
		String[] start = startTime.split(":");
		String[] end = endTime.split(":");
		
		int st_h = Integer.parseInt(start[0]);
		int st_m = Integer.parseInt(start[1]);
		int et_h = Integer.parseInt(end[0]);
		int et_m = Integer.parseInt(end[1]);
		
		if (st_h == et_h){
			for(int j=st_m;j<et_m;j=j+5){
					count += 1;
				}
			return count;
		}
		for (int i=st_h;i<=et_h;i++){
			if (i == et_h){
				for(int j=0;j<et_m;j=j+5){
					count += 1;
				}
			}
			else if (i != et_h && count != 0){
				for (int j=0;j<60;j=j+5){
					count += 1;
				}
			}
			else{
				for (int j=st_m;j<60;j=j+5){
					count += 1;
				}
			}
		}
		return count;
	}
}
