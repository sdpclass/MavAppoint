package uta.mav.appoint;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

@RunWith(Suite.class)
@SuiteClasses({ TestAdminLogin.class, TestAdvisorLogin.class,
		TestCaseInvalidLogin.class, TestFacultyLogin.class,
		TestStudentLogin.class })
public class AllTests {

}
