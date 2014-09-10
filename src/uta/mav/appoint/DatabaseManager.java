package uta.mav.appoint;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class DatabaseManager {
	
	private static DatabaseManager DB;
	
	private DatabaseManager(){
    }
	
	
	//singleton
	public static DatabaseManager getInstance(){
		if (DB == null){
			return (new DatabaseManager());
		}
		else
			return DB;
	}

	
	//create a connection to a database.
	public Connection ConnectDB(){
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
	
	
	//function used to test the database connection - not really used now
	public static void SQLFunction(String command, String line){
		Connection con = DatabaseManager.getInstance().ConnectDB();
		if (con == null){
			System.out.println("Could not establish connection.");
			return;
			}
		
		ResultSet rs;
		ResultSetMetaData rsmd;
		try{
        Statement statement = con.createStatement();
        if (line != null){
        statement.executeUpdate(command + " " + line);
        }
        else{
        	rs = statement.executeQuery(command);
        	rsmd = rs.getMetaData();
        	int columnsNumber = rsmd.getColumnCount();
        	for (int i=1; i <= columnsNumber; i++){
    			if (i > 1)
    				System.out.print(",    ");
    			System.out.print(rsmd.getColumnName(i));
    		}
        	System.out.println("");
        	while(rs.next()){
        		for (int i=1; i <= columnsNumber; i++){
        			if (i > 1)
        				System.out.print(",   ");
        			String columnValue = rs.getString(i);
        			System.out.print(columnValue);
        		}
        		System.out.println("");
        	}
        }
        con.close();
		}
        catch(Exception e){
        	System.out.println(e);
        }
		
}
	//user login checking, check username and password against database
	//then return role if a match is found
	public int CheckUser(GetSet set) throws SQLException{
		Connection conn = DatabaseManager.getInstance().ConnectDB();
		String command = "Select COUNT(*),role from user where email=? and password=?";
		PreparedStatement statement = conn.prepareStatement(command); 
		statement.setString(1,set.getEmailAddress());
		statement.setString(2,set.getPassword());
		
		ResultSet res = statement.executeQuery();
		
		int count = 0;
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
				}
				else if (res.getString(2).toLowerCase().equals("faculty")){
					count = 4;
				}
			}
				
		}
		conn.close();
		return count;
	}
	
	public int AddUser(GetSet set){
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
			Connection conn = DatabaseManager.getInstance().ConnectDB();
			String command = "SELECT pname FROM USER,ADVISOR_SETTINGS WHERE ROLE=? AND user.userid = advisor_settings.userid";
			PreparedStatement statement = conn.prepareStatement(command);
			statement.setString(1,"advisor");
			ResultSet res = statement.executeQuery();
			while (res.next()){
				arraylist.add(res.getString(1));
			}
		}
		catch(SQLException sq){
		
		}
		return arraylist;
	}
	
	public ArrayList<GetSet> getAdvisorSchedule(String name){
		ArrayList<GetSet> array = new ArrayList<GetSet>();
		try {
			Connection conn = this.ConnectDB();
			PreparedStatement statement;
			if (name.equals("all")){
			String command = "SELECT pname,advising_date,advising_starttime,advising_endtime FROM user,advising_schedule,advisor_settings "
							+ "WHERE user.userid=advisor_settings.userid AND user.userid=advising_schedule.userid AND studentid is null";
			statement = conn.prepareStatement(command);
			statement.setString(1,null);
			}
			else{
				String command = "SELECT pname,advising_date,advising_starttime,advising_endtime FROM user,advising_schedule,advisor_settings "
								+ "WHERE user.userid=advisor_settings.userid AND user.userid=advising_schedule.userid AND advisor_settings.pname=? AND studentid is null";
				statement = conn.prepareStatement(command);
				statement.setString(1,name);
				statement.setString(2,null);
				}
			ResultSet res = statement.executeQuery();
			while(res.next()){
				GetSet set = new GetSet();
				set.setName(res.getString(1));
				set.setDate(res.getInt(2));
				set.setStarttime(res.getInt(3));
				set.setEndtime(res.getInt(4));
				array.add(set);
			}
		}
		catch(Exception e){
			
		}
		return array;
	}
}
