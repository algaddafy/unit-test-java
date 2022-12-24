package test.java.edu.sc.cse4495.MeetingPlanner;

import main.java.edu.sc.cse4495.MeetingPlanner.*;
import org.junit.Test;
import static org.junit.Assert.assertEquals;

public class RoomTest {
	// Add test methods here. 
    // You are not required to write tests for all classes.

    @Test
    public void getIDShouldReturnTheIDOfTheRoom() {
        Room room = new Room("Room1");
        assertEquals("Room1", room.getID());
    }
//    @Test
//    void testAddMeeting() {
//        try {
//            room.addMeeting(new Meeting(0, 0, 0, 0, new ArrayList<>(List.of(new Person("name"))), new Room("ID"), "description"));
//        } catch (TimeConflictException e) {
//            throw new RuntimeException(e);
//        }
//    }


}
