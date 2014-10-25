package uta.mav.appoint.login;

public class FacultyUser extends LoginUser{
	

	public FacultyUser(String em){
		super(em);
	}


	@Override
	public String getHeader(){
		return "faculty_header";
	}
}
