package uta.mav.appoint.login;

public class AdminUser extends LoginUser{
	

	public AdminUser(String em){
		super(em);
	}
	

	@Override
	public String getHeader(){
		return "admin_header";
	}
}
