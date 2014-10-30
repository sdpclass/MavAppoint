package uta.mav.appoint.login;

public class AdvisorUser extends LoginUser{
	String pname;
	
	public AdvisorUser(String em, String p){
		super(em);
		pname = p;
	}
	
	@Override
	public String getHeader(){
		return "advisor_header";
	}

	/**
	 * @return the pname
	 */
	public String getPname() {
		return pname;
	}
}
