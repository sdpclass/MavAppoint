package uta.mav.appoint.login;

import uta.mav.appoint.db.DatabaseManager;

public class LoginUser {
	String email;
	
	public LoginUser(String em) {
		email = em;
	}
	
	/*
	 * @return the header - override in extended classes for proper header display
	 */
	public String getHeader(){
		return "header";
	}

	/**
	 * @return the email
	 */
	public String getEmail() {
		return email;
	}


	/**
	 * @param email the email to set
	 */
	public void setEmail(String email) {
		this.email = email;
	}
}
