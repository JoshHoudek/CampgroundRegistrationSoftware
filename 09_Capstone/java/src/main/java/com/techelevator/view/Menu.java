package com.techelevator.view;

import java.util.List;
import java.util.Scanner;

import com.techelevator.ReservationSystem;
import com.techelevator.campground.Park;
import com.techelevator.campground.jdbc.JDBCParkDAO;

public class Menu {

	ReservationSystem reservation;
	String userChoice;
		
	
	
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
	
	public void parkInformationScreenMenu() {
		userChoice = getUserSelectionFromChoice();
		
		try {
			
			if (Integer.parseInt(userChoice) == 1) {
				System.out.print("go to method that displays specific campgrounds");
				
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
	
	public void displayCampgroundsSubMenu() {
		
	}
	
}
