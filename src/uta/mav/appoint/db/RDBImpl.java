package uta.mav.appoint.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import uta.mav.appoint.PrimitiveTimeSlot;
import uta.mav.appoint.TimeSlotComponent;
import uta.mav.appoint.beans.AllocateTime;
import uta.mav.appoint.beans.Appointment;
import uta.mav.appoint.beans.AppointmentType;
import uta.mav.appoint.beans.GetSet;
import uta.mav.appoint.db.command.CheckUser;
import uta.mav.appoint.db.command.GetAdvisors;
import uta.mav.appoint.db.command.SQLCmd;
import uta.mav.appoint.db.command.UpdateAppointment;
import uta.mav.appoint.flyweight.TimeSlotFlyweightFactory;
import uta.mav.appoint.helpers.TimeSlotHelpers;
import uta.mav.appoint.login.AdminUser;
import uta.mav.appoint.login.AdvisorUser;
import uta.mav.appoint.login.LoginUser;
import uta.mav.appoint.login.StudentUser;

public class RDBImpl implements DBImplInterface{

	public Connection connectDB(){
		try
	    {
	    Class.forName("com.mysql.jdbc.Driver").newInstance();
	    String jdbcUrl = "jdbc:mysql://localhost:3306/MavAppointDB";
	    String userid = "mavappoint";
	    String password = "test1234";
	    Connection conn = DriverManager.getConnection(jdbcUrl,userid,password);
	    return conn;
	    }
	    catch (Exception e){
	        System.out.println(e.toString());
	    }
	    return null;
	}
	
			
	//user login checking, check username and password against database
	//then return role if a match is found
	//using command pattern
	public LoginUser checkUser(GetSet set) throws SQLException{
		LoginUser user = null;
		try{
			SQLCmd cmd = new CheckUser(set.getEmailAddress(), set.getPassword());
			cmd.execute();
			user = (LoginUser)(cmd.getResult()).get(0);
			
		}
		catch(Exception e){
			System.out.println(e);
		}
		return user;
	}
	
	public Boolean updateAppointment(Appointment a, String id){
		Boolean result = false;
		try{
			SQLCmd cmd = new UpdateAppointment(a.getAppointmentType(),id,a.getAppointmentId());
			cmd.execute();
			result = (Boolean)(cmd.getResult()).get(0);
		}
		catch(Exception e){
			
		}
		return result;
	}
	
	public int addUser(GetSet set){
		/*int check = 0;
		Connection conn = DatabaseManager.ConnectDB();
		String command = "INSERT INTO USER (email,password,role) VALUES(email=?,password=?,role=?)";
		return check;
		*/
		return 0;
	}
	
	//using command pattern
	public ArrayList<String> getAdvisors() throws SQLException{
		ArrayList<String> arraylist = new ArrayList<String>();
		try{
			SQLCmd cmd = new GetAdvisors();
			cmd.execute();
			ArrayList<Object> tmp = cmd.getResult();
			for (int i=0;i<tmp.size();i++){
				arraylist.add(((String)tmp.get(i)));
			}
		}
		catch(Exception sq){
			System.out.printf(sq.toString());
		}
		return arraylist;
	}
	
	public ArrayList<TimeSlotComponent> getAdvisorSchedule(String name){
		ArrayList<TimeSlotComponent> array = new ArrayList<TimeSlotComponent>();
		try {
			Connection conn = this.connectDB();
			PreparedStatement statement;
			if (name.equals("all")){
			String command = "SELECT pname,advising_date,advising_starttime,advising_endtime,id FROM user,advising_schedule,advisor_settings "
							+ "WHERE user.userid=advisor_settings.userid AND user.userid=advising_schedule.userid AND studentid is null";
			statement = conn.prepareStatement(command);
			}
			else{
				String command = "SELECT pname,advising_date,advising_starttime,advising_endtime,id FROM USER,ADVISING_SCHEDULE,ADVISOR_SETTINGS "
								+ "WHERE USER.userid=ADVISOR_SETTINGS.userid AND USER.userid=ADVISING_SCHEDULE.userid AND USER.userid=ADVISING_SCHEDULE.userid AND ADVISOR_SETTINGS.pname=? AND studentid is null";
				statement = conn.prepareStatement(command);
				statement.setString(1,name);
				}
			
			ResultSet res = statement.executeQuery();
			while(res.next()){
				//Use flyweight factory to avoid build cost if possible
				PrimitiveTimeSlot set = (PrimitiveTimeSlot)TimeSlotFlyweightFactory.getInstance().getFlyweight(res.getString(2),res.getString(3));
				set.setName(res.getString(1));
				set.setDate(res.getString(2));
				set.setStartTime(res.getString(3));
				set.setEndTime(res.getString(4));
				set.setUniqueId(res.getInt(5));
				array.add(set);
			}
			array = TimeSlotHelpers.createCompositeTimeSlot(array);
			conn.close();
		}
		catch(Exception e){
			System.out.printf(e.toString());
		}
		return array;
	}

	public Boolean createAppointment(int id,String studentid,String type, String email,String pname, String date, String start, String end){
		Boolean result = false;
		int student_id = 0;
		int advisor_id = 0;
		try{
			Connection conn = this.connectDB();
			PreparedStatement statement;
			String command = "SELECT userid from user where email=?";
			statement=conn.prepareStatement(command);
			statement.setString(1,email);
			ResultSet rs = statement.executeQuery();
			while(rs.next()){
				student_id = rs.getInt(1);
			}
			command = "SELECT userid FROM advisor_settings WHERE advisor_settings.pname=?";
			statement=conn.prepareStatement(command);
			statement.setString(1, pname);
			rs = statement.executeQuery();
			while(rs.next()){
				advisor_id = rs.getInt(1);
			}
			//check for slots already taken
			command = "SELECT COUNT(*) FROM advising_schedule WHERE userid=? AND advising_date=? AND advising_starttime=? AND advising_endtime=? AND studentid is not null";
			statement = conn.prepareStatement(command);
			statement.setInt(1, advisor_id);
			statement.setString(2, date);
			statement.setString(3, start);
			statement.setString(4, end);
			rs = statement.executeQuery();
			while(rs.next()){
				if (rs.getInt(1) < 1){
					
					
					command = "INSERT INTO appointments (id,advisor_userid,student_userid,advising_date,advising_starttime,advising_endtime,appointment_type,studentid)"
							+"VALUES(?,?,?,?,?,?,?,?)";
					statement = conn.prepareStatement(command);
					statement.setInt(1, id);
					statement.setInt(2,advisor_id);
					statement.setInt(3,student_id);
					statement.setString(4,date);
					statement.setString(5,start);
					statement.setString(6,end);
					statement.setString(7,type);
					statement.setInt(8,Integer.parseInt(studentid));
					statement.executeUpdate();
					command = "UPDATE advising_schedule SET studentid=? where userid=? AND advising_date=? and advising_starttime >= ? and advising_endtime <= ?";
					statement=conn.prepareStatement(command);
					statement.setInt(1,Integer.parseInt(studentid));
					statement.setInt(2, advisor_id);
					statement.setString(3, date);
					statement.setString(4,start);
					statement.setString(5, end);
					statement.executeUpdate();
					result = true;
				}
				conn.close();
			}
		}
		catch(Exception e){
			System.out.printf(e.toString());
		}
		return result;
	}

	public ArrayList<Appointment> getAppointments(AdvisorUser user){
		ArrayList<Appointment> appointments = new ArrayList<Appointment>();
		try{
			Connection conn = this.connectDB();
			PreparedStatement statement;
			String command = "SELECT advisor_settings.pname,advisor_settings.email,advising_date,advising_starttime,advising_endtime,appointment_type,id FROM USER,APPOINTMENTS,ADVISOR_SETTINGS "
						+ "WHERE USER.email=? AND user.userid=appointments.advisor_userid AND advisor_settings.userid=appointments.advisor_userid";
			statement = conn.prepareStatement(command);
			statement.setString(1, user.getEmail());
			ResultSet rs = statement.executeQuery();
			while(rs.next()){
				Appointment set = new Appointment();
				set.setPname(rs.getString(1));
				set.setAdvisorEmail(rs.getString(2));
				set.setAdvisingDate(rs.getString(3));
				set.setAdvisingStartTime(rs.getString(4));
				set.setAdvisingEndTime(rs.getString(5));
				set.setAppointmentType(rs.getString(6));
				set.setAppointmentId(rs.getInt(7));
				appointments.add(set);
			}
			conn.close();
		}
		catch(Exception e){
			System.out.printf(e.toString());
		}
		
		return appointments;
	}

	public ArrayList<Appointment> getAppointments(StudentUser user){
		ArrayList<Appointment> appointments = new ArrayList<Appointment>();
		try{
			Connection conn = this.connectDB();
			PreparedStatement statement;
			String command = "SELECT advisor_settings.pname,advisor_settings.email,advising_date,advising_starttime,advising_endtime,appointment_type,id FROM USER,APPOINTMENTS,ADVISOR_SETTINGS "
						+ "WHERE USER.email=? AND user.userid=appointments.student_userid AND advisor_settings.userid=appointments.advisor_userid";
			statement = conn.prepareStatement(command);
			statement.setString(1, user.getEmail());
			ResultSet rs = statement.executeQuery();
			while(rs.next()){
				Appointment set = new Appointment();
				set.setPname(rs.getString(1));
				set.setAdvisorEmail(rs.getString(2));
				set.setAdvisingDate(rs.getString(3));
				set.setAdvisingStartTime(rs.getString(4));
				set.setAdvisingEndTime(rs.getString(5));
				set.setAppointmentType(rs.getString(6));
				set.setAppointmentId(rs.getInt(7));
				appointments.add(set);
			}
			conn.close();
		}
		catch(Exception e){
			System.out.printf(e.toString());
		}
		
		return appointments;
	}

	public ArrayList<Appointment> getAppointments(AdminUser user){
		ArrayList<Appointment> appointments = new ArrayList<Appointment>();
		try{
			Connection conn = this.connectDB();
			PreparedStatement statement;
			String command = "SELECT advisor_settings.pname,advisor_settings.email,advising_date,advising_starttime,advising_endtime,appointment_type,id FROM appointments INNER JOIN advisor_settings "
						+"WHERE advisor_settings.userid = appointments.advisor_userid";
			statement = conn.prepareStatement(command);
			ResultSet rs = statement.executeQuery();
			while(rs.next()){
				Appointment set = new Appointment();
				set.setPname(rs.getString(1));
				set.setAdvisorEmail(rs.getString(2));
				set.setAdvisingDate(rs.getString(3));
				set.setAdvisingStartTime(rs.getString(4));
				set.setAdvisingEndTime(rs.getString(5));
				set.setAppointmentType(rs.getString(6));
				set.setAppointmentId(rs.getInt(7));
				appointments.add(set);
			}
			conn.close();
		}
		catch(Exception e){
			System.out.printf(e.toString());
		}
		
		return appointments;
	}
	
	public Boolean cancelAppointment(int id){
		Boolean result = false;
		try{
			Connection conn = this.connectDB();
			PreparedStatement statement;
			String command = "SELECT count(*),advising_date,advising_starttime, advising_endtime from appointments where id=?";
			statement=conn.prepareStatement(command);
			statement.setInt(1,id);
			ResultSet rs = statement.executeQuery();
			while(rs.next()){
				if (rs.getInt(1) == 1){
					command = "DELETE FROM appointments where id=?";
					statement=conn.prepareStatement(command);
					statement.setInt(1, id);
					statement.executeUpdate();
					command = "UPDATE advising_schedule SET studentid=null where advising_date=? AND advising_starttime >=? AND advising_endtime <=?";
					statement=conn.prepareStatement(command);
					statement.setString(1, rs.getString(2));
					statement.setString(2,rs.getString(3));
					statement.setString(3, rs.getString(4));
					statement.executeUpdate();
					result = true;
				}
			conn.close();
			}
		}
		catch(SQLException e){
			System.out.printf(e.toString());
		}
		return result;
	}
	
	public Boolean addTimeSlot(AllocateTime at){
		Boolean result = false;
		int userid = 0;
		int count = TimeSlotHelpers.count(at.getStartTime(),at.getEndTime());
		try{
			Connection conn = this.connectDB();
			PreparedStatement statement;
			String command = "SELECT userid FROM user where email=?";
			statement=conn.prepareStatement(command);
			statement.setString(1, at.getEmail());
			ResultSet rs = statement.executeQuery();
			while(rs.next()){
				userid = rs.getInt(1);
			}
			conn.close();
		}
		catch(Exception e){
			System.out.printf("First command" + e);
		}
		try{
			Connection conn = this.connectDB();
			PreparedStatement statement;
			String command = "SELECT COUNT(*) FROM  ADVISING_SCHEDULE WHERE advising_date=? AND advising_starttime >=? AND advising_endtime <=? AND userid=?";
			statement = conn.prepareStatement(command);
				statement.setString(1,at.getDate());
				statement.setString(2,at.getStartTime());
				statement.setString(3,at.getEndTime());
				statement.setInt(4,userid);
				ResultSet rs = statement.executeQuery();
			while(rs.next()){
				if (rs.getInt(1)>=1){
					result = false;
					return result;
				}
			}
		}
		catch(Exception e){
			
		}
		try{
			Connection conn = this.connectDB();
			PreparedStatement statement;
			String command = "INSERT INTO ADVISING_SCHEDULE (advising_date,advising_starttime,advising_endtime,studentid,userid) VALUES(?,?,?,null,?)";
			statement = conn.prepareStatement(command);
			for (int i=0;i<count;i++){
				statement.setString(1,at.getDate());
				statement.setString(2,TimeSlotHelpers.add(at.getStartTime(),i));
				statement.setString(3,TimeSlotHelpers.add(at.getStartTime(),i+1));
				statement.setInt(4,userid);
				statement.executeUpdate();
			}
			result = true;
			conn.close();
		}
		catch(Exception e){
			
		}
		return result;
	}
	
	public ArrayList<AppointmentType> getAppointmentTypes(String pname){
			ArrayList<AppointmentType> ats = new ArrayList<AppointmentType>();
			try{
			Connection conn = this.connectDB();
			PreparedStatement statement;
			String command = "SELECT type,duration FROM  appointment_types,advisor_settings WHERE appointment_types.userid=advisor_settings.userid AND advisor_settings.pname=?";
			statement = conn.prepareStatement(command);
			statement.setString(1,pname);
			ResultSet rs = statement.executeQuery();
			while(rs.next()){
				AppointmentType at = new AppointmentType();
				at.setType(rs.getString(1));
				at.setDuration(rs.getInt(2));
				ats.add(at);
			}
			conn.close();
		}
		catch(Exception e){
			System.out.println(e);
		}
		return ats;
	
	}
}

