package uta.mav.appoint.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import uta.mav.appoint.GetSet;
import uta.mav.appoint.beans.AdvisingSchedule;
import uta.mav.appoint.beans.Appointment;


public class DatabaseManager {
		DBImplInterface imp = new RDBImpl();
	
			
	//user login checking, check username and password against database
	//then return role if a match is found
	public int checkUser(GetSet set) throws SQLException{
		return imp.checkUser(set);
		}
	
	public int addUser(GetSet set) throws SQLException{
		return imp.addUser(set);
	}
	
	public ArrayList<String> getAdvisors() throws SQLException{
		return imp.getAdvisors();
	}
	
	public ArrayList<AdvisingSchedule> getAdvisorSchedule(String name) throws SQLException{
		return imp.getAdvisorSchedule(name);
	}

	public Boolean createAppointment(int id,String studentid,String type, String email) throws SQLException{
		return imp.createAppointment(id, studentid, type, email);
	}

	public ArrayList<Appointment> getAppointments(String email, int role) throws SQLException{
		return imp.getAppointments(email, role);
	}

	public Boolean cancelAppointment(int id) throws SQLException{
		return imp.cancelAppointment(id);
	}
}

