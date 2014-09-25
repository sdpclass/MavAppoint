package uta.mav.appoint;

import static org.junit.Assert.fail;
import static org.junit.Assert.*;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

public class TestViewAppointment {
	private ViewAppointmentServlet servlet; 
	
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
	}

	@Before
	public void setUp() throws Exception {
		servlet = new ViewAppointmentServlet();
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void testDoGetHttpServletRequestHttpServletResponse() {
		fail("Not yet implemented");
	}

	@Test
	public void testDoPostHttpServletRequestHttpServletResponse() {
		fail("Not yet implemented");
	}
	
	@Test
	public void testDisplayHeader(){
		String header = "templates/header.jsp";
		int role = -1;
		assertEquals(header,servlet.displayHeader(role));
		header = "templates/advisor_header.jsp";
		role = 1;
		assertEquals(header,servlet.displayHeader(role));
		header = "templates/student_header.jsp";
		role = 2;
		assertEquals(header,servlet.displayHeader(role));
		header = "templates/admin_header.jsp";
		role = 3;
		assertEquals(header,servlet.displayHeader(role));
		header = "templates/faculty_header.jsp";
		role = 4;
		assertEquals(header,servlet.displayHeader(role));	
	}
}
