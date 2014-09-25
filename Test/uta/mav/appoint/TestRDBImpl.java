package uta.mav.appoint;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.sql.Connection;
import java.util.ArrayList;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import uta.mav.appoint.beans.AdvisingSchedule;
import uta.mav.appoint.db.RDBImpl;


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
			assertEquals(dbm.checkUser(set),0);
			//check valid student
			set.setEmailAddress("teststudent@mavs.uta.edu");
			set.setPassword("test1234");
			assertEquals(dbm.checkUser(set),2);
			//check valid advisor
			set.setEmailAddress("testadvisor@uta.edu");
			set.setPassword("test1234");
			assertEquals(dbm.checkUser(set),1);
			//check valid admin
			set.setEmailAddress("testadmin@uta.edu");
			set.setPassword("test1234");
			assertEquals(dbm.checkUser(set),3);
			//check valid faculty
			set.setEmailAddress("testfaculty@uta.edu");
			set.setPassword("test1234");
			assertEquals(dbm.checkUser(set),4);
			
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
			ArrayList<AdvisingSchedule> array = dbm.getAdvisorSchedule("all");
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
		int id = -1;
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
		fail("Not yet implemented");
	}

	@Test
	public void testCancelAppointment() {
		int id = -1;
		assertTrue(dbm.cancelAppointment(id) == false);
		id = 10;
		assertTrue(dbm.cancelAppointment(id) == true);
	}

}
