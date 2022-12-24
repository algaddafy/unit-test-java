package test.java.edu.sc.cse4495.MeetingPlanner;

import main.java.edu.sc.cse4495.MeetingPlanner.Organization;
import main.java.edu.sc.cse4495.MeetingPlanner.Person;
import main.java.edu.sc.cse4495.MeetingPlanner.Room;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

public class OrganizationTest {
	// Add test methods here. 
    // You are not required to write tests for all classes.

    @Test
    public void getEmployeeWhenNameIsFound() {
        Organization organization = new Organization();
        try {
            Person employee = organization.getEmployee("Greg Gay");
            assertEquals("Greg Gay", employee.getName());
        } catch (Exception e) {
            fail("Exception should not be thrown");
        }
    }

    @Test
    public void getEmployeeWhenNameIsNotFoundThenThrowException() {
        Organization organization = new Organization();
        try {
            organization.getEmployee("John Doe");
            fail("Expected exception not thrown");
        } catch (Exception e) {
            assertEquals("Requested employee does not exist", e.getMessage());
        }
    }

    @Test
    public void getRoomWhenRoomExists() throws Exception {
        Organization organization = new Organization();
        Room room = organization.getRoom("2A01");
        assertEquals("2A01", room.getID());
    }

    @Test
    public void getRoomWhenRoomDoesNotExistThenThrowException() {
        Organization organization = new Organization();
        try {
            organization.getRoom("2A06");
            fail("Expected exception not thrown");
        } catch (Exception e) {
            assertEquals("Requested room does not exist", e.getMessage());
        }
    }


//    Organization organization = new Organization();
//    @Test
//    public void testGetRoom() throws Exception {
//
//        Room result = organization.getRoom("ID");
//        assertEquals(new Room("ID"), result);
//    }
//
//    @Test
//    public void testGetEmployee() throws Exception {
//        Person result = organization.getEmployee("name");
//        assertEquals(new Person("name"), result);
//    }
}
