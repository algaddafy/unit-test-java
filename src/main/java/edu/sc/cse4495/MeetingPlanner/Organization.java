package main.java.edu.sc.cse4495.MeetingPlanner;

import java.util.ArrayList;

public class Organization {
	/** This class is used to initialize and store a list of employees
	 * and rooms available for meetings. 
	 * 
	 *  It gets a bunch of initialization code out of the PlannerInterface.
	 */
	
	private ArrayList<Person> employees;
	private ArrayList<Room> rooms;

	/**
	 * Default constructor - sets up some rooms and people.
	 */
	public Organization(){
		employees = new ArrayList<Person>();
		employees.add(new Person("Greg Gay"));
		employees.add(new Person("Manton Matthews"));
		employees.add(new Person("John Rose"));
		employees.add(new Person("Ryan Austin"));
		employees.add(new Person("Csilla Farkas"));
		
		rooms = new ArrayList<Room>();
		rooms.add(new Room("2A01"));
		rooms.add(new Room("2A02"));
		rooms.add(new Room("2A03"));
		rooms.add(new Room("2A04"));
		rooms.add(new Room("2A05"));
	}
	
	public ArrayList<Person> getEmployees() {
		return employees;
	}

	public ArrayList<Room> getRooms() {
		return rooms;
	}
	
	/**
	 * Search for and retrieve a room.
	 * @param ID - ID of the room to retrieve.
	 * @return Room - The requested Room object.
	 * @throws Exception - If the room does not exist.
	 */
	public Room getRoom(String ID) throws Exception{
		Room room = null;
		for(Room toCheck : rooms){
			if(toCheck.getID().equals(ID)){
				room = toCheck;
				return room;
			}
		}
		
		throw new Exception("Requested room does not exist");
	}
	
	/**
	 * Search for and retrieve a person.
	 * @param name - The name of the person to retrieve.
	 * @return Person - The requested Person object.
	 * @throws Exception - If the person does not exist.
	 */
	public Person getEmployee(String name) throws Exception{
		Person employee = null;
		for(Person toCheck : employees){
			if(toCheck.getName().equals(name)){
				employee = toCheck;
				return employee;
			}
		}
		
		throw new Exception("Requested employee does not exist");
	}
}
