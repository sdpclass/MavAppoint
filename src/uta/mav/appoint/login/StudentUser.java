package uta.mav.appoint.login;

public class StudentUser extends LoginUser{
	

	public StudentUser(String em){
		super(em);
	}
	

	@Override
	public String getHeader(){
		return "student_header";
	}
}
