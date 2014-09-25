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
	public int checkUser(GetSet set) throws SQLException{
		int count = 0;
		try{
		Connection conn = this.connectDB();
		String command = "SELECT COUNT(*),ROLE FROM USER WHERE EMAIL=? and PASSWORD=?";
		PreparedStatement statement = conn.prepareStatement(command); 
		statement.setString(1,set.getEmailAddress());
		statement.setString(2,set.getPassword());
		ResultSet res = statement.executeQuery();
		while(res.next()){
			if (!(res.getInt(1) == 0)){
				if (res.getString(2).toLowerCase().equals("advisor")){
					count = 1;
				}
				else if (res.getString(2).toLowerCase().equals("student")){
					count = 2;
				}
				else if (res.getString(2).toLowerCase().equals("admin")){
					count = 3;
				} else {
					count = 4;
				}
			}		
		}
		conn.close();
		}
		catch(SQLException e){
			System.out.printf(e.toString());
		}
		return count;
	}
	
	public int addUser(GetSet set){
		/*int check = 0;
		Connection conn = DatabaseManager.ConnectDB();
		String command = "INSERT INTO USER (email,password,role) VALUES(email=?,password=?,role=?)";
		return check;
		*/
		return 0;
	}
	
	public ArrayList<String> getAdvisors() throws SQLException{
		ArrayList<String> arraylist = new ArrayList<String>();
		try{
			Connection conn = this.connectDB();
			String command = "SELECT pname FROM USER,ADVISOR_SETTINGS WHERE ROLE=? AND USER.userid = ADVISOR_SETTINGS.userid";
			PreparedStatement statement = conn.prepareStatement(command);
			statement.setString(1,"advisor");
			ResultSet res = statement.executeQuery();
			while (res.next()){
				arraylist.add(res.getString(1));
			}

			conn.close();
		}
		catch(SQLException sq){
			System.out.printf(sq.toString());
		}
		return arraylist;
	}
	
	public ArrayList<AdvisingSchedule> getAdvisorSchedule(String name){
		ArrayList<AdvisingSchedule> array = new ArrayList<AdvisingSchedule>();
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
								+ "WHERE USER.userid=ADVISOR_SETTINGS.userid AND USER.userid=ADVISING_SCHEDULE.userid AND ADVISOR_SETTINGS.pname=? AND studentid is null";
				statement = conn.prepareStatement(command);
				statement.setString(1,name);
				statement.setString(2,null);
				}
			ResultSet res = statement.executeQuery();
			while(res.next()){
				AdvisingSchedule set = new AdvisingSchedule();
				set.setName(res.getString(1));
				set.setDate(res.getString(2));
				set.setStarttime(res.getString(3));
				set.setEndtime(res.getString(4));
				set.setUniqueid(res.getInt(5));
				array.add(set);
			}
			conn.close();
		}
		catch(Exception e){
			System.out.printf(e.toString());
		}
		return array;
	}

	public Boolean createAppointment(int id,String studentid,String type, String email){
		Boolean result = false;
		int student_id = 0;
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
			command = "SELECT COUNT(*),userid,advising_date,advising_starttime,advising_endtime FROM advising_schedule WHERE ID=?";
			statement = conn.prepareStatement(command);
			statement.setInt(1, id);
			rs = statement.executeQuery();
			int i = 1;
			while(rs.next()){
				if (rs.getInt(1) == 1){
					command = "INSERT INTO appointments (id,advisor_userid,student_userid,advising_date,advising_starttime,advising_endtime,appointment_type,studentid)"
							+"VALUES(?,?,?,?,?,?,?,?)";
					statement = conn.prepareStatement(command);
					statement.setInt(1, id);
					statement.setInt(2,rs.getInt(2));
					statement.setInt(3,student_id);
					statement.setString(4,rs.getString(3));
					statement.setString(5,rs.getString(4));
					statement.setString(6,rs.getString(5));
					statement.setString(7,type);
					statement.setInt(8,Integer.parseInt(studentid));
					statement.executeUpdate();
					command = "UPDATE advising_schedule SET studentid=? where id=?";
					statement=conn.prepareStatement(command);
					statement.setInt(1,Integer.parseInt(studentid));
					statement.setInt(2, id);
					statement.executeUpdate();
					result = true;
				}
			}
		}
		catch(Exception e){
			System.out.printf(e.toString());
		}
		return result;
	}

	public ArrayList<Appointment> getAppointments(String email, int role){
		ArrayList<Appointment> appointments = new ArrayList<Appointment>();
		try{
			Connection conn = this.connectDB();
			PreparedStatement statement;
			String command = "";
			switch (role){
			case 1:
				command = "SELECT advisor_settings.pname,advisor_settings.email,advising_date,advising_starttime,advising_endtime,appointment_type,id FROM USER,APPOINTMENTS,ADVISOR_SETTINGS "
						+ "WHERE USER.email=? AND user.userid=appointments.advisor_userid AND advisor_settings.userid=appointments.advisor_userid";
				break;
			case 2:
				command = "SELECT advisor_settings.pname,advisor_settings.email,advising_date,advising_starttime,advising_endtime,appointment_type,id FROM USER,APPOINTMENTS,ADVISOR_SETTINGS "
						+ "WHERE USER.email=? AND user.userid=appointments.student_userid AND advisor_settings.userid=appointments.advisor_userid";
				break;
			case 3:
				command = "SELECT advisor_settings.pname,advisor_settings.email,advising_date,advising_starttime,advising_endtime,appointment_type,id FROM APPOINTMENTS,ADVISOR_SETTINGS "
						+"WHERE advisor_settings.userid=appointments.userid";
				break;
			case 4:
				break;
			}
			statement = conn.prepareStatement(command);
			if(role != 3){
				statement.setString(1, email);
			}
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
			String command = "SELECT count(*) from appointments where id=?";
			statement=conn.prepareStatement(command);
			statement.setInt(1,id);
			ResultSet rs = statement.executeQuery();
			while(rs.next()){
				if (rs.getInt(1) == 1){
					command = "DELETE FROM appointments where id=?";
					statement=conn.prepareStatement(command);
					statement.setInt(1, id);
					statement.executeUpdate();
					command = "UPDATE advising_schedule SET studentid=null where id=?";
					statement=conn.prepareStatement(command);
					statement.setInt(1, id);
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
}

