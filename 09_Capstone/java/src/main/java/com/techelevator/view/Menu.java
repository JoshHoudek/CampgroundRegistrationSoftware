package com.techelevator.view;

import java.util.List;
import java.util.Scanner;

import com.techelevator.ReservationSystem;
import com.techelevator.campground.Campground;
import com.techelevator.campground.Park;
import com.techelevator.campground.jdbc.JDBCParkDAO;
import java.text.DateFormatSymbols;

public class Menu {

	ReservationSystem ourReservation;
	String userChoice;
	long userSelectedPark;
	String parkName;
	
	
	public String getUserSelectionFromChoice() {
		
		Scanner scanner = new Scanner(System.in);
		
		String userChoice = scanner.nextLine();
		
		//scanner.close();
		return userChoice;
		
		
	}
	

	
	public void listAllParks(List<Park> parkList) {
		
		System.out.println("Select a Park for Further Details");
					
		if(parkList.size() > 0) {
			for(Park park : parkList) {
				System.out.println(park.getPark_id() + ") " + park.getPark_name());
			}
		} else {
			System.out.println("\n*** No results ***");
		}
		System.out.println("Q) Quit");
	}

	public void listSelectedParkInfo(Park selectedPark) {
		userSelectedPark = selectedPark.getPark_id();
		System.out.println("PARK INFO");
		System.out.println(selectedPark.getPark_name()+ " National Park");
		System.out.println("Location: " + selectedPark.getPark_location());
		System.out.println("Established: " + selectedPark.getEstablish_date());
		System.out.println("Area: " + selectedPark.getPark_area());
		System.out.println("Annual Visitors: " + selectedPark.getPark_visitors());
		System.out.println(selectedPark.getPark_description());
		
		System.out.println("1) View Campgrounds");
		System.out.println("2) Search for Reservation");
		System.out.println("3) Return to previous screen");
		//parkInformationScreenMenu();
	}
	
	public void parkInformationScreenMenu(ReservationSystem reservation, String userSelectedParkName) {
		parkName = userSelectedParkName;
		userChoice = getUserSelectionFromChoice();
		ourReservation = reservation;
		
		try {
			
			if (Integer.parseInt(userChoice) == 1) {
				System.out.println("go to method that displays specific campgrounds");
				displayCampgroundsInSelectedPark();
			}else if (Integer.parseInt(userChoice) == 2) {
				System.out.print("go to method that searches for reservation");
			}else if (Integer.parseInt(userChoice) == 3) {
				System.out.print("return user to previous screen");
			} else
			{throw new Exception();}
			}
			catch(Exception e) {
				System.out.println("error");
			}
	}
	
	public void displayCampgroundsInSelectedPark() {
		String toMonth;
		String fromMonth;
		
		System.out.println(parkName + " National Park Campgrounds" + "\n");
		System.out.println("test" + new DateFormatSymbols().getMonths()[4]);
		
		System.out.println(String.format("%-3s%-25s%-12s%-12s%s", "#", "Name", "Open", "Close", "Daily Fee"));
		
		List<Campground> listOfCampgroundsAtUsersSelectedPark = ourReservation.getAllCampgroundsByParkSelection(userSelectedPark);
		if(listOfCampgroundsAtUsersSelectedPark.size() > 0) {
			for(Campground campground : listOfCampgroundsAtUsersSelectedPark) {
				fromMonth = new DateFormatSymbols().getMonths()[campground.getOpen_from_mm() - 1];
				toMonth = new DateFormatSymbols().getMonths()[campground.getOpen_to_mm() - 1];
				System.out.println(String.format("%-3s%-25s%-12s%-12s%s", campground.getCampground_id() + ") ",  campground.getCampground_name(),  fromMonth,
						 toMonth,  "  $" + campground.getDaily_fee() + "0"));
			}
		} else {
			System.out.println("\n*** No results ***");
		}
		System.out.println("\nSelect a Command");
		System.out.println("1) Search for Available Reservation");
		System.out.println("2) Return to Previous Screen");
		
	}
	
}
