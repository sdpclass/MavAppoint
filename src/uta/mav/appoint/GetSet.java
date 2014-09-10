package uta.mav.appoint;
/******************************************
 * @author John
 * Basic dataset package. If you need to compare values against anything from
 * a sql table, add it as a variable here and use it to compare
 *
 */
public class GetSet {
	private String password;
	private String emailAddress;
	/**
	 * @return the userName
	 */
	/**
	 * @return the password
	 */
	public String getPassword() {
		return password;
	}
	/**
	 * @param password the password to set
	 */
	public void setPassword(String password) {
		this.password = password;
	}
	/**
	 * @return the emailAddress
	 */
	public String getEmailAddress() {
		return emailAddress;
	}
	/**
	 * @param emailAddress the emailAddress to set
	 */
	public void setEmailAddress(String emailAddress) {
		this.emailAddress = emailAddress;
	}
	
	
}

