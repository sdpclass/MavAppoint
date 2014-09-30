package uta.mav.appoint;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

public class TestAllocateTime {

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
	}

	@Before
	public void setUp() throws Exception {
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void testCountTimeSlots() {
		String testStartTime = "9:00";
		String testEndTime = "9:15";
		AllocateTimeServlet ats = new AllocateTimeServlet();
		int result = ats.countTimeSlots(testStartTime, testEndTime);
		assertEquals(result,3);
		testEndTime = "10:15";
		result = ats.countTimeSlots(testStartTime, testEndTime);
		assertEquals(result,15);
		testEndTime = "11:15";
		result = ats.countTimeSlots(testStartTime, testEndTime);
		assertEquals(result,27);
	
	
	}

}
