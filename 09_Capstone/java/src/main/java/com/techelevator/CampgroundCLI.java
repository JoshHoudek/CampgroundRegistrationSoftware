package com.techelevator;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Date;
import java.util.List;

import javax.sql.DataSource;

import org.apache.commons.dbcp2.BasicDataSource;

import com.techelevator.campground.Campground;
import com.techelevator.campground.Park;
import com.techelevator.view.Menu;
import com.techelevator.view.NewMenu;


public class CampgroundCLI {

	
	private String Park_Info_Screen_View_Campgrounds = "View Campgrounds";
	private String Park_Info_Screen_Search_for_Reservation = "Search for Reservation";
	private String Park_Info_Screen__Return_to_Previous_Screen = "Return to Previous Screen";
	private String[] Park_Info_Menu_Options = new String[] {Park_Info_Screen_View_Campgrounds,Park_Info_Screen_Search_for_Reservation,Park_Info_Screen__Return_to_Previous_Screen};
	
	private String Park_Campground_Menu_Search_for_Available_Reservation = "Search for Available Reservation";
	private String Park_Campground_Menu_Return_to_Previous_Screen = "Return to Previous Screen";
	private String[] Park_Campground_Menu_Options = new String[] {Park_Campground_Menu_Search_for_Available_Reservation,Park_Campground_Menu_Return_to_Previous_Screen};
	

	private Menu menu = new Menu();
	private String userChoice = null;
	private NewMenu ljmenu;
	
	public static void main(String[] args) {
		
		BasicDataSource dataSource = new BasicDataSource();
		dataSource.setUrl("jdbc:postgresql://localhost:5432/campground");
		dataSource.setUsername("postgres");
		dataSource.setPassword("postgres1");

		CampgroundCLI application = new CampgroundCLI(); 
		
		
		application.run(dataSource);
		
	}
	
	// CONSTRUCTOR///
	public CampgroundCLI() {
		
		this.ljmenu = new NewMenu(System.in, System.out);

	}

	
	
//// APPLICATION BELOW//////

	public void run(DataSource dataSource) {

		ReservationSystem reservation = new ReservationSystem(dataSource);

		// main menu loop
		while (userChoice == null) {

			Object[] allParksAsObjects = reservation.getAllParksAsObjects();
			System.out.println("Welcome to the National Parks Reservation System!");
			Object myParkChoice = ljmenu.getChoiceFromParkOptions(allParksAsObjects);

			viewParkInfoMenu(reservation, myParkChoice);

			while (userChoice.equals("View Campgrounds")) {

				viewCampgroundMenu(myParkChoice, dataSource);

			}
			if (userChoice.equals("Search for Reservation")) {
				searchForReservation(reservation);
				userChoice = "Return to Previous Screen";
			}
			if (userChoice.equals("Return to Previous Screen")) {
				userChoice = null;
			}

		}
	}

	private void searchForReservation(ReservationSystem reservation) {
		System.out.println("Enter the reservation number you'd like to view");

		long reservationID = Long.parseLong(menu.getUserSelectionFromChoice());

		ljmenu.displaySearchedReservation(reservation.searchForReservationID(reservationID));

	}
	
	public void viewParkInfoMenu(ReservationSystem reservation, Object userObject) {
		Park myParkChoice = (Park) userObject;

		reservation.displayUsersParkInfo(myParkChoice);

		userChoice = (String) ljmenu.getChoiceFromOptions(Park_Info_Menu_Options);

	}
	
	
	public void viewCampgroundMenu(Object userObject, DataSource dataSource) {

		ReservationSystem reservation = new ReservationSystem(dataSource);

		reservation.displayCampgroundsInSelectedPark(userObject);
		String userChoice = (String) ljmenu.getChoiceFromOptions(Park_Campground_Menu_Options);

		if (userChoice.equals("Search for Available Reservation")) {
			viewReservationMenu(userObject, reservation, dataSource);
		} else {
			viewParkInfoMenu(reservation, userObject);

		}
	}
	



	public void viewReservationMenu(Object userObject, ReservationSystem reservation, DataSource dataSource) {

		System.out.println("Search for Campground Reservation\n");
		reservation.displayCampgroundsInSelectedPark(userObject);
		System.out.println("Which campground (enter 0 to cancel)? _");

		List<Campground> campGroundIDList = reservation.getCampgroundsAsList(userObject);

		String userSelection = menu.getUserSelectionFromChoice();
		
		while (!userSelection.matches("[0-9]+")) {
			System.out.print("Please enter a valid campground. Thank you!");
			userSelection = menu.getUserSelectionFromChoice();
		}
		
		long campground_idLong = Long.parseLong(userSelection);

		int campground_id_int = (int) campground_idLong;

		if (campground_id_int >= campGroundIDList.get(0).getCampground_id()
				&& campground_id_int <= (campGroundIDList.get(0).getCampground_id() + campGroundIDList.size() - 1)) {

			// check if the campground is valid, and add option to cancel with 0
			System.out.println("What is the arrival date? (YYYY/MM/DD)");

			String from_date = menu.getUserSelectionFromChoice();

			while (!(from_date.matches("\\d{4}/\\d{2}/\\d{2}"))) {
				System.out.println("Please enter a valid date in the format (YYYY/MM/DD)");
				from_date = menu.getUserSelectionFromChoice();
			}
			
			//attempted data validation...
			//isThisDateValid(from_date, "YYYY/MM/DD");
			
			LocalDate convertedFromDate = menu.convertToDate(from_date);
			
			LocalDate currentDate = LocalDate.now();
			
			/*while (convertedFromDate.isBefore(currentDate)) {
				System.out.println("You cannot book a stay in the past. Please enter a valid date starting from today minimum");
				convertedToDate = convertedFromDate.plusDays(1);
			}*/
			if (convertedFromDate.isBefore(currentDate)) {
				System.out.println("NICE TRY - Your booking date has been set for today :-)");
				convertedFromDate = LocalDate.now();
			}
			System.out.println("What is the departure date? (YYYY/MM/DD)");

			
			String to_date = menu.getUserSelectionFromChoice();

			
			
			//check that the user entered a valid date format below
			while (!(to_date.matches("\\d{4}/\\d{2}/\\d{2}"))) {
				System.out.println("Please enter a valid date in the format (YYYY/MM/DD)");
				to_date = menu.getUserSelectionFromChoice();
			}
			
			LocalDate convertedToDate = menu.convertToDate(to_date);
			
			while (convertedToDate.isBefore(convertedFromDate)) {
				System.out.println("You must stay one night minimum! Please enter a date later than the one your visit starts on");
				convertedToDate = convertedFromDate.plusDays(1);
			}
			
//			if (convertedToDate.isBefore(convertedFromDate)) {
//				System.out.println("You must stay one night minimum! Your stay has been extended one day :-)");
//				convertedToDate = convertedFromDate.plusDays(1);
//			}

			menu.displayAvailbleReservations(reservation, campground_idLong, convertedFromDate, convertedToDate);
			// throw to method that shows search results, and asks which site should be
			// reserved

			menu.makeANewReservation(reservation, convertedFromDate, convertedToDate);

		} else if (campground_id_int == 0) {
			viewCampgroundMenu(userObject, dataSource);

		} else {
			System.out.println("**NOT A VALID SELECTION** TRY AGAIN!");
			viewReservationMenu(userObject, reservation, dataSource);
		}
	}
	
/*public boolean isThisDateValid(String dateToValidate, String dateFromat){
		
		if(dateToValidate == null){
			return false;
		}
		
		SimpleDateFormat sdf = new SimpleDateFormat(dateFromat);
		sdf.setLenient(false);
		
		try {
			
			//if not valid, it will throw ParseException
			Date date = sdf.parse(dateToValidate);
			System.out.println(date);
		
		} catch (ParseException e) {
			
			e.printStackTrace();
			return false;
		}
		
		return true;
	}*/
	
}


			