package main.java.edu.sc.cse4495.MeetingPlanner;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;

public class PlannerInterface {
	private Organization org;

	/**
	 * Constructor, sets up organization data structure.
	 */
	public PlannerInterface(){
		org = new Organization();
	}

	/**
	 * Driver for the calendar system.
	 * @param args - Unused for this class
	 */
	public static void main(String[] args){	
		PlannerInterface menu = new PlannerInterface();
		System.out.println("Welcome to the Meeting Scheduling Interface.");
		menu.mainMenu();
	}

	/**
	 * Prints the main menu and handles user input for 
	 * main menu commands.
	 */
	public void mainMenu() {
		System.out.println("1. Schedule a meeting");
		System.out.println("2. Request vacation time");
		System.out.println("3. Check room availability");
		System.out.println("4. Check person availability");
		System.out.println("5. Print agenda for a room");
		System.out.println("6. Check agenda for a person");
		System.out.println("0. Exit\n");

		//Get user input
		try {
			int userInput = Integer.parseInt(inputOutput("Please press the number that corresponds to what you would like to do."));

			if (userInput >= 0 && userInput <=6) {
				if (userInput == 1) {
					this.scheduleMeeting();
				}
				if (userInput == 2){
					this.scheduleVacation();
				}
				if (userInput == 3){
					this.checkRoomAvailability();
				}
				if (userInput == 4){
					this.checkEmployeeAvailability();
				}
				if (userInput == 5){
					this.checkAgendaRoom();
				}
				if (userInput == 6){
					this.checkAgendaPerson();
				}
				if (userInput == 0) System.exit(0);
			} else {
				System.out.println("Please enter a number from 0 - 6");
				this.mainMenu();
			}
		} catch (NumberFormatException e) {
			System.out.println("Please enter a number from 0 - 6");
			this.mainMenu();
		}
	}

	/**
	 * Passes a prompt to the user and returns the user specified 
	 * string.
	 * @param message - User input.
	 * @return String - Entered string.
	 */
	private String inputOutput(String message) {
		System.out.println(message);
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		String returnString = "";
		try {
			returnString = br.readLine();
		}
		catch (IOException e){
			System.out.println("Error reading in value");
			this.mainMenu();
		}
		return returnString;
	}

	/**
	 * Allows the user to schedule a meeting by entering 
	 * information about the meeting.
	 */
	public void scheduleMeeting() {

		//Read in month
		int month = Integer.parseInt(inputOutput("\nEnter the month of the meeting (1-12): "));

		//Read in day
		int day = Integer.parseInt(inputOutput("\nEnter the day of the meeting (1-31): "));

		//Read in start time
		int startTime = Integer.parseInt(inputOutput("\nEnter the starting hour of the meeting (0-23): "));

		//Read in end time
		int endTime = Integer.parseInt(inputOutput("\nEnter the ending hour of the meeting (0-23): "));

		System.out.println("The rooms open at that time are:");
		//Display the rooms open at that time
		for(Room r: org.getRooms()){
			try{
				if(!(r.isBusy(month, day, startTime, endTime))){
					System.out.println(r.getID());
				}
			}catch(TimeConflictException e){
				System.out.println(e.getMessage());
				this.mainMenu();
			}
		}

		//Read in room ID
		Room where = new Room();
		boolean selected=false;
		while(!selected){
			try{
				String id =inputOutput("\nEnter the desired room ID, or cancel to cancel: ");
				if(id.equals("cancel")){
					this.mainMenu();
				}else{
					where = org.getRoom(id);
				}
				selected=true;
			}catch(Exception e){
				System.out.println(e.getMessage());
			}
		}	

		System.out.println("The people available to attend at that time are:");
		//Display the people available at that time
		for(Person p: org.getEmployees()){
			try{
				if(!(p.isBusy(month, day, startTime, endTime))){
					System.out.println(p.getName());
				}
			}catch(TimeConflictException e){
				System.out.println(e.getMessage());
				this.mainMenu();
			}
		}

		//Read in person names
		String name = "";
		ArrayList<Person> attendees = new ArrayList<Person>();
		while(!name.equals("done")){
			name = inputOutput("\nEnter a person's name, or done if finished: ");
			if(!name.equals("done")){
				try{
					Person who = org.getEmployee(name);
					attendees.add(who);
				}catch(Exception e){
					System.out.println(e.getMessage());
				}
			}
		}

		//Read in description
		String description  = (inputOutput("\nEnter a description for the meeting: "));

		Meeting meeting = new Meeting(month,day,startTime,endTime,attendees,where,description);
		try{
			where.addMeeting(meeting);
			for(Person employee: attendees){
				employee.addMeeting(meeting);
			}

		}catch(TimeConflictException e){
			System.out.println(e.getMessage());
		}

		this.mainMenu();
	}

	/**
	 * Allows the user to schedule vacation time for an employee.
	 */
	public void scheduleVacation() {

		//Read in start month
		int sMonth = Integer.parseInt(inputOutput("\nEnter the month that the vacation starts (1-12): "));

		//Read in start day
		int sDay = Integer.parseInt(inputOutput("\nEnter the day the vacation starts (1-31): "));

		//Read in end month
		int eMonth = Integer.parseInt(inputOutput("\nEnter the month that the vacation ends (1-12): "));

		//Read in end day
		int eDay = Integer.parseInt(inputOutput("\nEnter the day the vacation ends (1-31): "));

		System.out.println("Which person will be taking the vacation:");
		//Display the people available at that time
		for(Person p: org.getEmployees()){
			System.out.println(p.getName());
		}

		//Read in person names
		ArrayList<Person> attendees = new ArrayList<Person>();
		String name = inputOutput("\nEnter a person's name, or cancel to cancel the request: ");
		Person who = new Person();
		if(!name.equals("cancel")){
			try{
				who = org.getEmployee(name);
				attendees.add(who);
			}catch(Exception e){
				System.out.println(e.getMessage());
			}
		}

		// See if there are any conflicts during the period
		boolean conflict=false;
		try{
			if(sMonth!=eMonth){
				// Start month
				for(int day=sDay; day<=31;day++){
					if(who.isBusy(sMonth,day,0,23)){
						if(!who.getMeeting(sMonth,day,0).getDescription().equals("Day does not exist")){
							System.out.println("There is a conflict for date "+sMonth+"/"+day+":\n"+who.printAgenda(sMonth, day));
							conflict=true;
						}
					}
				}
				// Any intervening months
				for(int month=sMonth+1; month<eMonth;month++){
					for(int day=1; day<=31;day++){
						if(who.isBusy(month,day,0,23)){
							if(!who.getMeeting(month,day,0).getDescription().equals("Day does not exist")){
								System.out.println("There is a conflict for date "+month+"/"+day+":\n"+who.printAgenda(month, day));
								conflict=true;
							}
						}
					}
				}
				// End month
				for(int day=1;day<=eDay;day++){
					if(who.isBusy(eMonth,day,0,23)){
						if(!who.getMeeting(eMonth,day,0).getDescription().equals("Day does not exist")){
							System.out.println("There is a conflict for date "+eMonth+"/"+day+":\n"+who.printAgenda(eMonth, day));
							conflict=true;
						}
					}
				}
			}else{
				for(int day=sDay; day<=eDay;day++){
					if(who.isBusy(sMonth,day,0,23)){
						if(!who.getMeeting(sMonth,day,0).getDescription().equals("Day does not exist")){
							System.out.println("There is a conflict for date "+sMonth+"/"+day+":\n"+who.printAgenda(sMonth, day));
							conflict=true;
						}
					}
				}
			}
		}catch(TimeConflictException e){
			System.out.println(e.getMessage());
			this.mainMenu();
		}

		if(conflict){
			this.mainMenu();
		}else{
			// Book the time
			if(sMonth!=eMonth){
				// Start month
				for(int day=sDay; day<=31;day++){
					Meeting meeting = new Meeting(sMonth,day,0,23,attendees,new Room(),"vacation");
					try{
						who.addMeeting(meeting);
					}catch(TimeConflictException e){
						System.out.println(e.getMessage());
						this.mainMenu();
					}
				}
				// Any intervening months
				for(int month=sMonth+1; month<eMonth;month++){
					for(int day=1; day<=31;day++){
						Meeting meeting = new Meeting(month,day,0,23,attendees,new Room(),"vacation");
						try{
							who.addMeeting(meeting);
						}catch(TimeConflictException e){
							System.out.println(e.getMessage());
							this.mainMenu();
						}
					}
				}
				// End month
				for(int day=1;day<=eDay;day++){
					Meeting meeting = new Meeting(eMonth,day,0,23,attendees,new Room(),"vacation");
					try{
						who.addMeeting(meeting);
					}catch(TimeConflictException e){
						System.out.println(e.getMessage());
						this.mainMenu();
					}
				}
			}else{
				for(int day=sDay; day<=eDay;day++){
					Meeting meeting = new Meeting(sMonth,day,0,23,attendees,new Room(),"vacation");
					try{
						who.addMeeting(meeting);
					}catch(TimeConflictException e){
						System.out.println(e.getMessage());
						this.mainMenu();
					}
				}
			}
		}

		this.mainMenu();
	}

	/**
	 * Allows a user to check the availability of a room at a given time.
	 */
	public void checkRoomAvailability() {

		//Read in month
		int month = Integer.parseInt(inputOutput("\nEnter the month of the meeting (1-12): "));

		//Read in day
		int day = Integer.parseInt(inputOutput("\nEnter the day of the meeting (1-31): "));

		//Read in start time
		int startTime = Integer.parseInt(inputOutput("\nEnter the starting hour of the meeting (0-23): "));

		//Read in end time
		int endTime = Integer.parseInt(inputOutput("\nEnter the ending hour of the meeting (0-23): "));

		System.out.println("The rooms open at that time are:");
		//Display the rooms open at that time
		for(Room r: org.getRooms()){
			try{
				if(!(r.isBusy(month, day, startTime, endTime))){
					System.out.println(r.getID());
				}
			}catch(TimeConflictException e){
				System.out.println(e.getMessage());
				this.mainMenu();
			}
		}

		this.mainMenu();
	}

	/**
	 * Allows the user to check the availability of a given employee
	 * at a particular time.
	 */
	public void checkEmployeeAvailability() {

		//Read in month
		int month = Integer.parseInt(inputOutput("\nEnter the month of the meeting (1-12): "));

		//Read in day
		int day = Integer.parseInt(inputOutput("\nEnter the day of the meeting (1-31): "));

		//Read in start time
		int startTime = Integer.parseInt(inputOutput("\nEnter the starting hour of the meeting (0-23): "));

		//Read in end time
		int endTime = Integer.parseInt(inputOutput("\nEnter the ending hour of the meeting (0-23): "));

		System.out.println("The people available to attend at that time are:");
		//Display the people available at that time
		for(Person p: org.getEmployees()){
			try{
				if(!(p.isBusy(month, day, startTime, endTime))){
					System.out.println(p.getName());
				}
			}catch(TimeConflictException e){
				System.out.println(e.getMessage());
				this.mainMenu();
			}
		}

		this.mainMenu();
	}

	/**
	 * Allows the user to view the agenda of a room on a particular month
	 * or day.
	 */
	public void checkAgendaRoom() {

		//Read in month
		int month = Integer.parseInt(inputOutput("\nEnter the month (1-12): "));

		//Read in day
		String day = inputOutput("\nEnter the day (1-31), or all to see the whole month: ");

		System.out.println("Which of the following rooms are you interested in?");
		//Display the rooms open at that time
		for(Room r: org.getRooms()){
			System.out.println(r.getID());
		}

		//Read in person names
		String name = inputOutput("\nEnter a room ID, or cancel to cancel the request: ");
		if(!name.equals("cancel")){
			try{
				Room who = org.getRoom(name);
				if(!day.equals("all")){
					System.out.println(who.printAgenda(month, Integer.parseInt(day)));
				}else{
					System.out.println(who.printAgenda(month));
				}
			}catch(Exception e){
				System.out.println(e.getMessage());
			}
		}
		this.mainMenu();
	}

	/**
	 * Allows the user to view the agenda for a particular employee
	 * on a particular month or day.
	 */
	public void checkAgendaPerson() {

		//Read in month
		int month = Integer.parseInt(inputOutput("\nEnter the month (1-12): "));

		//Read in day
		String day = inputOutput("\nEnter the day (1-31), or all to see the whole month: ");

		System.out.println("Which of the following people are you interested in?");
		//Display the rooms open at that time
		for(Person p: org.getEmployees()){
			System.out.println(p.getName());
		}

		//Read in person names
		String name = inputOutput("\nEnter a name, or cancel to cancel the request: ");
		if(!name.equals("cancel")){
			try{
				Person who = org.getEmployee(name);
				if(!day.equals("all")){
					System.out.println(who.printAgenda(month, Integer.parseInt(day)));
				}else{
					System.out.println(who.printAgenda(month));
				}
			}catch(Exception e){
				System.out.println(e.getMessage());
			}
		}
		this.mainMenu();
	}
}
