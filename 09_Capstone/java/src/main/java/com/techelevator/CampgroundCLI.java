package com.techelevator;

import java.util.List;

import javax.sql.DataSource;

import org.apache.commons.dbcp2.BasicDataSource;

import com.techelevator.campground.Campground;
import com.techelevator.campground.CampgroundDAO;
import com.techelevator.campground.Park;
import com.techelevator.campground.ParkDAO;
import com.techelevator.campground.jdbc.JDBCCampgroundDAO;
import com.techelevator.campground.jdbc.JDBCParkDAO;
import com.techelevator.view.Menu;


public class CampgroundCLI {

	private ParkDAO parkDAO;
	private CampgroundDAO campgroundDAO;
	private boolean notQuit = true;
	private Menu menu = new Menu();
	private String userChoice = null;
	
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
	
	}
	
	
	
//// APPLICATION BELOW///////

	public void run(DataSource dataSource) {
		
		ReservationSystem reservation = new ReservationSystem(dataSource);
		
		List<Park> allParks = reservation.getAllParks();
		
		menu.listAllParks(allParks);
		
		while (notQuit) {
			
			userChoice = menu.getUserSelectionFromChoice();
			try {
				
			if (Integer.parseInt(userChoice) <= allParks.size()) {
				menu.listSelectedParkInfo(allParks.get(Integer.parseInt(userChoice)-1));				
				menu.parkInformationScreenMenu(reservation, allParks.get(Integer.parseInt(userChoice)-1).getPark_name());
				
				//System.out.print("methodthatdisplays user choice data");
			}else if (userChoice.equals("Q")) {
				notQuit = false;
			} else
			{throw new Exception();}
			}
			catch(Exception e) {
				System.out.println("error");
			}
			
			userChoice = menu.getUserSelectionFromChoice();
			
			if (Integer.parseInt(userChoice) == 1) {
				
				System.out.println("YES IT WORKED LOL");
			} else if (Integer.parseInt(userChoice) == 3) {
				
				break;
			}
				
//			long campground_id = menu.displayCampgroundsInSelectedPark();
//			LocalDate myDate1 = menu.DateMethodHere
//			LocalDate myDate2 = menu.DateMethodHere
//			reservation.getAllAvailableReservations(campground_id, from_date, to_date);
//				
			
		
		
		}
	}
	
	
	
	
	/*	while (notQuit) {
			
			userChoice = menu.getUserSelectionFromChoice();
			
			//allParks.get(userChoice).getPark_name()
			
			
			if (Integer.parseInt(userChoice) <= allParks.size()) {
				System.out.print("methodthatdisplays user choice data");
			}else if (userChoice.equals("Q")) {
				notQuit = false;
			} 
			
			;
			
			
			(userchoice is a park id) {
				run method from meu that displays this park ids info
			};
			
		}
		
		}

	*/
	
	
	
	
	
	
	private void getCampgroundByParkId() {
		System.out.println("Campgrounds by Park Id");
		List<Campground> campgroundsByParkId = campgroundDAO.getCampgroundsByParkId(1);
		listCampgroundsById(campgroundsByParkId);
	}
	
	private void listCampgroundsById(List<Campground> campgroundsByParkId) {
		System.out.println();
		if(campgroundsByParkId.size() > 0) {
			for(Campground camp : campgroundsByParkId) {
				System.out.println(camp.getCampground_id() + ") " + camp.getCampground_name());
			}
		} else {
			System.out.println("\n*** No results ***");
		}
	}

	
/*	private void listArcadia(List<Park> allParks) {
		System.out.println("PARK INFO");
		System.out.println(allParks.get(0).getPark_name()+ " National Park");
		System.out.println("Location: " + allParks.get(0).getPark_location());
		System.out.println("Established: " + allParks.get(0).getEstablish_date());
		System.out.println("Area: " + allParks.get(0).getPark_area());
		System.out.println("Annual Visitors: " + allParks.get(0).getPark_visitors());
		System.out.println(allParks.get(0).getPark_description());
		}
	*/

}
