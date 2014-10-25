package uta.mav.appoint.login;

public class AdvisorUser extends LoginUser{
	
	public AdvisorUser(String em){
		super(em);
	}
	
	@Override
	public String getHeader(){
		return "advisor_header";
	}
}
