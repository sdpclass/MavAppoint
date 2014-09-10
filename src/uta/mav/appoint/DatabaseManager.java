package uta.mav.appoint;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;

public class DatabaseManager {
	
	public DatabaseManager(){
    }

	
	//create a connection to a database.
	public static Connection ConnectDB(){
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
		Connection con = DatabaseManager.ConnectDB();
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
	public static int CheckUser(GetSet set) throws SQLException{
		Connection conn = DatabaseManager.ConnectDB();
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
}
