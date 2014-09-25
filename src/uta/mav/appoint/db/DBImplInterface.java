package uta.mav.appoint.db;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;

import uta.mav.appoint.GetSet;
import uta.mav.appoint.beans.AdvisingSchedule;
import uta.mav.appoint.beans.Appointment;

public interface DBImplInterface {
	public Boolean cancelAppointment(int id) throws SQLException;
	public ArrayList<Appointment> getAppointments(String email, int role) throws SQLException;
	public Boolean createAppointment(int id,String studentid,String type, String email) throws SQLException;
	public ArrayList<AdvisingSchedule> getAdvisorSchedule(String name) throws SQLException;
	public int addUser(GetSet set) throws SQLException;
	public ArrayList<String> getAdvisors() throws SQLException;
	public int checkUser(GetSet set) throws SQLException;
	public Connection connectDB();
}
