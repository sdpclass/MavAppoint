package uta.mav.appoint.db.command;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class CancelAppointment extends SQLCmd{
	String uid;
	int id;
	
	public CancelAppointment(int id){
		this.id = id;
		uid = "-1";
	}
	
	@Override
	public void queryDB() {
		Boolean result = false;
		try{
			String command = "SELECT count(*),advising_date,advising_starttime,advising_endtime,uid from appointments where id=?";
			PreparedStatement statement=conn.prepareStatement(command);
			statement.setInt(1,id);
			ResultSet rs = statement.executeQuery();
			while(rs.next()){
				if (rs.getInt(1) == 1){
					uid = rs.getString(5);
					command = "DELETE FROM appointments where id=?";
					statement=conn.prepareStatement(command);
					statement.setInt(1, id);
					statement.executeUpdate();
					command = "UPDATE advising_schedule SET studentid=null where advising_date=? AND advising_starttime >=? AND advising_endtime <=?";
					statement=conn.prepareStatement(command);
					statement.setString(1, rs.getString(2));
					statement.setString(2,rs.getString(3));
					statement.setString(3, rs.getString(4));
					statement.executeUpdate();				}
			}
			conn.close();	
		}
		catch(SQLException e){
			System.out.printf("Error in Database: " + e.toString());
		}
	}

	@Override
	public void processResult() {
		result.add(uid);
	}

}
