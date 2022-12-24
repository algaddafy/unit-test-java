package test.java.edu.sc.cse4495.MeetingPlanner;

import main.java.edu.sc.cse4495.MeetingPlanner.Calendar;
import main.java.edu.sc.cse4495.MeetingPlanner.Meeting;
import main.java.edu.sc.cse4495.MeetingPlanner.Person;
import main.java.edu.sc.cse4495.MeetingPlanner.TimeConflictException;
import org.junit.Test;

import static org.junit.Assert.*;

public class PersonTest {
	// Add test methods here. 
    // You are not required to write tests for all classes.
    private Person person;
    private Calendar calendar;
    @Test
    public void isBusyWhenPersonDoesNotHaveMeetingAtGivenTimeThenReturnFalse() throws TimeConflictException {
        assertFalse(person.isBusy(1, 1, 1, 2));
    }

    @Test
    public void isBusyWhenPersonHasMeetingAtGivenTimeThenReturnTrue() throws TimeConflictException {
        Meeting meeting = new Meeting(1, 1, 1, 2);
        calendar.addMeeting(meeting);

        assertTrue(person.isBusy(1, 1, 1, 2));
    }

    @Test
    public void printAgendaWhenMonthIsValidAndThereAreNoMeetingsThenReturnEmptyString() {
        String expected = "Agenda for 1:\n";
        String actual = person.printAgenda(1);
        assertEquals(expected, actual);
    }

    @Test
    public void printAgendaWhenMonthIsNotValidThenThrowException() {
        try {
            person.printAgenda(0);
            fail("Expected exception not thrown");
        } catch (Exception e) {
            assertEquals("Month does not exist.", e.getMessage());
        }

        try {
            person.printAgenda(13);
            fail("Expected exception not thrown");
        } catch (Exception e) {
            assertEquals("Month does not exist.", e.getMessage());
        }
    }

    @Test
    public void printAgendaWhenMonthIsValidThenReturnStringWithMeetingsOfTheMonth() {
        Meeting meeting1 = new Meeting(1, 1);
        Meeting meeting2 = new Meeting(1, 2);
        Meeting meeting3 = new Meeting(2, 1);
        Meeting meeting4 = new Meeting(2, 2);

        try {
            calendar.addMeeting(meeting1);
            calendar.addMeeting(meeting2);
            calendar.addMeeting(meeting3);
            calendar.addMeeting(meeting4);
        } catch (TimeConflictException e) {
            fail("Should not throw exception");
        }

        String expected =
                "Agenda for 1:\n" + meeting1.toString() + "\n" + meeting2.toString() + "\n";

        assertEquals(expected, person.printAgenda(1));
    }

}
