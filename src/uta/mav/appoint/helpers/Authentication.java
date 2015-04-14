package uta.mav.appoint.helpers;

import java.io.BufferedReader;
import java.io.FileReader;

import com.sun.xml.internal.ws.transport.http.ResourceLoader;

public class Authentication {
	
	private static String username = null;
	private static String password = null;
	
	public Authentication(){
		
	}
	
	public static String getUserNameInstance(){
		if (username!=null&&password!=null){
			return username;
		}
		Authentication auth = new Authentication();
		auth.readFile();
		return username;
	}
	
	public static String getUserPasswordInstance(){
		if (username!=null&&password!=null){
			return password;
		}
		Authentication auth = new Authentication();
		auth.readFile();
		return password;
	}
	
	private void readFile(){
		try{
			String path = this.getClass().getResource("auth.txt").toString();
			System.out.println(path);
			path = path.substring(6,path.length());
			System.out.println(path);
			BufferedReader in = new BufferedReader(new FileReader(path));
			String[] data;
			data = in.readLine().split(",");
			username = data[0];
			password = data[1];
			System.out.println(username + " " + password);
		}
		catch(Exception e){
			System.out.println(e.toString());
		}
		
	}
	
	
}
