package test.java.edu.sc.cse4495.MeetingPlanner;

import main.java.edu.sc.cse4495.MeetingPlanner.Meeting;
import main.java.edu.sc.cse4495.MeetingPlanner.Person;
import org.junit.Test;


public class MeetingTest {
	// Add test methods here. 
    // You are not required to write tests for all classes.
    Meeting meeting = new Meeting();
    @Test
    public void testAddAttendee() {
        Meeting meeting = new Meeting();
        meeting.addAttendee(new Person("Manton Matthews"));
    }

    @Test
    public void testRemoveAttendee() {
        meeting.removeAttendee(new Person("John Rose"));
    }

}
