package uta.mav.appoint.db;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;

import uta.mav.appoint.TimeSlotComponent;
import uta.mav.appoint.beans.AllocateTime;
import uta.mav.appoint.beans.Appointment;
import uta.mav.appoint.beans.GetSet;
import uta.mav.appoint.login.AdminUser;
import uta.mav.appoint.login.AdvisorUser;
import uta.mav.appoint.login.LoginUser;
import uta.mav.appoint.login.StudentUser;

public interface DBImplInterface {
	public Boolean cancelAppointment(int id) throws SQLException;
	public ArrayList<Appointment> getAppointments(AdvisorUser user) throws SQLException;
	public ArrayList<Appointment> getAppointments(StudentUser user) throws SQLException;
	public ArrayList<Appointment> getAppointments(AdminUser user) throws SQLException;
	public Boolean createAppointment(int id,String studentid,String type, String email) throws SQLException;
	public ArrayList<TimeSlotComponent> getAdvisorSchedule(String name) throws SQLException;
	public int addUser(GetSet set) throws SQLException;
	public ArrayList<String> getAdvisors() throws SQLException;
	public LoginUser checkUser(GetSet set) throws SQLException;
	public Boolean addTimeSlot(AllocateTime at) throws SQLException;
	public Connection connectDB();
}
