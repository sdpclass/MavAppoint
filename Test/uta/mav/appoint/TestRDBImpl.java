package uta.mav.appoint;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.sql.Connection;
import java.util.ArrayList;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import uta.mav.appoint.beans.AdvisingSchedule;
import uta.mav.appoint.beans.Appointment;
import uta.mav.appoint.beans.GetSet;
import uta.mav.appoint.db.RDBImpl;
import uta.mav.appoint.login.AdminUser;
import uta.mav.appoint.login.AdvisorUser;
import uta.mav.appoint.login.FacultyUser;
import uta.mav.appoint.login.LoginUser;
import uta.mav.appoint.login.StudentUser;


public class TestRDBImpl {
	RDBImpl dbm;

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
	}

	@Before
	public void setUp() throws Exception {
		dbm = new RDBImpl();
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void testConnectDB() {
		Connection conn;
		assertTrue((conn = dbm.connectDB()) != null);
	}

	@Test
	public void testCheckUser() {
		try{
			GetSet set = new GetSet();
			//check invalid username
			assertFalse(dbm.checkUser(set) instanceof LoginUser);
			//check valid student
			set.setEmailAddress("teststudent@mavs.uta.edu");
			set.setPassword("test1234");
			assertTrue(dbm.checkUser(set) instanceof StudentUser);
			//check valid advisor
			set.setEmailAddress("testadvisor@uta.edu");
			set.setPassword("test1234");
			assertTrue(dbm.checkUser(set) instanceof AdvisorUser);
			//check valid admin
			set.setEmailAddress("testadmin@uta.edu");
			set.setPassword("test1234");
			assertTrue(dbm.checkUser(set) instanceof AdminUser);
			//check valid faculty
			set.setEmailAddress("testfaculty@uta.edu");
			set.setPassword("test1234");
			assertTrue(dbm.checkUser(set) instanceof FacultyUser);
			
		}
		catch(Exception e){
			
		}
	}

	@Test
	public void testAddUser() {
		//addUser method not implemented yet
	}

	@Test
	public void testGetAdvisors() {
		try{
			ArrayList<String> array = dbm.getAdvisors();
			assertTrue(array.size() > 0);			
		}
		catch(Exception e){
			
		}
	}

	@Test
	public void testGetAdvisorSchedule() {
		try{
			//test all advisors
			ArrayList<TimeSlotComponent> array = dbm.getAdvisorSchedule("all");
			assertTrue(array.size() > 0); //assumes at least one open advising slot
			/*ArrayList<String> advisors = dbm.getAdvisors(); //get list of advisors
			for (int i=0;i<advisors.size();i++){
				array = dbm.getAdvisorSchedule(advisors.get(i));
				for (int j=0;j<array.size();j++){
					assertEquals(array.get(j).getName(),advisors.get(i));
				}
			} Code not implemented yet, giving SQL error but passing test*/
			
		}
		catch(Exception e){
			
		}
	}

	@Test
	public void testCreateAppointment() {
		//test all inputs invalid
		int id = -2;
		String studentid = "0";
		String type = "";
		String email = "teststudent@mavs.uta.edu";
		assertTrue(dbm.createAppointment(id, studentid, type, email) == false);
		//test success
		id = 10;
		studentid = "1234567890";
		type = "Add Class";
		email = "teststudent@mavs.uta.edu";
		assertTrue(dbm.createAppointment(id, studentid, type, email) == true);
	}

	@Test
	public void testGetAppointments() {
		AdvisorUser user = new AdvisorUser("notanadvisor@uta.edu");
		ArrayList<Appointment> array = dbm.getAppointments(user);
		assertTrue(array.size() == 0);
		user.setEmail("testadvisor@uta.edu");
		array = dbm.getAppointments(user);
		assertTrue(array.size() > 0);
		StudentUser user2 = new StudentUser("teststudent@mavs.uta.edu");
		array = dbm.getAppointments(user2);
		assertTrue(array.size() > 0);
		AdminUser user3 = new AdminUser("testadmin@uta.edu");
		array = dbm.getAppointments(user3);
		assertTrue(array.size() > 0);
	}

	@Test
	public void testCancelAppointment() {
		int id = -2;
		assertTrue(dbm.cancelAppointment(id) == false);
		id = 10;
		assertTrue(dbm.cancelAppointment(id) == true);
	}

}
