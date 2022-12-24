package test.java.edu.sc.cse4495.MeetingPlanner;

import main.java.edu.sc.cse4495.MeetingPlanner.*;
import org.junit.Before;
import org.junit.Test;


import static org.junit.Assert.*;

public class CalendarTest {
	// Add test methods here. 
    // You are not required to write tests for all classes.
	private Calendar calendar;
	private Meeting meeting;

	@Before
	public void setUp() throws Exception {
		calendar = new Calendar();
	}
	@Test
	public void testAddMeeting_holiday() {
		// Create Midsommar holiday
		Meeting christmas = new Meeting(6, 26, "Midsommar");
		Calendar calendar = new Calendar();
		// Add to calendar object.
		try {
			calendar.addMeeting(christmas);
			// Verify that it was added.
			Boolean added = calendar.isBusy(6, 26, 0, 23);
			assertTrue("Midsommar should be marked as busy on the calendar",added);
		} catch(TimeConflictException e) {
			fail("Should not throw exception: " + e.getMessage());
		}
	}

	@Test
	public void printAgendaWhenMonthIsInvalid() {
		calendar.printAgenda(0);
		fail("Expected exception not thrown");

		calendar.printAgenda(13);
		fail("Expected exception not thrown");
	}

	@Test
	public void printAgendaWhenMonthIsValid() throws TimeConflictException {
		meeting = new Meeting(1, 1, "Meeting 1");
		calendar.addMeeting(meeting);
		meeting = new Meeting(1, 2, "Meeting 2");
		calendar.addMeeting(meeting);
		meeting = new Meeting(2, 1, "Meeting 3");
		calendar.addMeeting(meeting);

		String expected =
				"Agenda for 1:\n" + "1/1, 0 - 23,: Meeting 1\n" + "1/2, 0 - 23,: Meeting 2\n";

		assertEquals(expected, calendar.printAgenda(1));
	}

	@Test
	public void clearSchedule() throws TimeConflictException {
		meeting = new Meeting(1, 1);
		calendar.addMeeting(meeting);
		calendar.clearSchedule(1, 1);
		assertEquals(0, calendar.getMeeting(1, 1, 0).getDay());
	}

	@Test
	public void checkTimesWhenDayIsNotValid() {
		try {
			calendar.checkTimes(1, 32, 0, 1);
			fail("Expected TimeConflictException");
		} catch (TimeConflictException e) {
			assertEquals("Day does not exist.", e.getMessage());
		}
	}

	@Test
	public void checkTimesWhenStartTimeIsNotValid() {
		try {
			calendar.checkTimes(1, 1, -1, 1);
			fail("Expected exception not thrown");
		} catch (TimeConflictException e) {
			assertEquals("Illegal hour.", e.getMessage());
		}
	}

	@Test
	public void checkTimesWhenStartTimeGreaterThanOrEqualToEndTime() {
		try {
			calendar.checkTimes(1, 1, 1, 0);
			fail("Expected TimeConflictException");
		} catch (TimeConflictException e) {
			assertEquals("Meeting starts before it ends.", e.getMessage());
		}
	}

	@Test
	public void checkTimesWhenMonthIsNotValid() {
		try {
			calendar.checkTimes(0, 1, 1, 2);
			fail("Expected TimeConflictException");
		} catch (TimeConflictException e) {
			assertEquals("Month does not exist.", e.getMessage());
		}
	}

	@Test
	public void checkTimesWhenEndTimeIsNotValid() {
		try {
			calendar.checkTimes(1, 1, 1, 24);
			fail("Expected exception not thrown");
		} catch (TimeConflictException e) {
			assertEquals("Illegal hour.", e.getMessage());
		}
	}



	@Test
	public void testIsBusy(){
		Calendar calendar = new Calendar();
//		boolean result = calendar.isBusy(0, 0, 0, 0);
//		assertEquals(true, result);

		Throwable e = assertThrows(TimeConflictException.class,()->{calendar.isBusy(0, 0, 0, 0);});
		assertEquals(e.getMessage(),"Test");
	}
//
//	@Test
//	public void testCheckTimes() throws TimeConflictException {
//		Calendar calendar = new Calendar();
//		calendar.checkTimes(0, 0, 0, 0);
//	}

}
