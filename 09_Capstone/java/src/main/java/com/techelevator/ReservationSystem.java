package com.techelevator;

import java.text.DateFormatSymbols;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

import com.techelevator.campground.Campground;
import com.techelevator.campground.CampgroundDAO;
import com.techelevator.campground.Park;
import com.techelevator.campground.ParkDAO;
import com.techelevator.campground.Reservation;
import com.techelevator.campground.ReservationDAO;
import com.techelevator.campground.jdbc.JDBCCampgroundDAO;
import com.techelevator.campground.jdbc.JDBCParkDAO;
import com.techelevator.campground.jdbc.JDBCReservationDAO;

public class ReservationSystem {
	private ParkDAO parkDAO;
	private CampgroundDAO campgroundDAO;
	private ReservationDAO reservationDAO;
	private List<Integer> campgroundIDlist = new ArrayList<Integer>();
	
	public ReservationSystem (DataSource datasource) {
		 
		parkDAO = new JDBCParkDAO(datasource); 
		campgroundDAO = new JDBCCampgroundDAO(datasource);  
		reservationDAO = new JDBCReservationDAO(datasource);
	}
	
	public List<Park> getAllParks() {
		List<Park> allParks = parkDAO.getAllParks();
			
		return allParks;
	}
		
	
	
	public Object[] getAllParksAsObjects() {
		
		Object[] allParks = parkDAO.getAllParksAsObjects();
		
		return allParks; 
	}

	public List<Campground> getAllCampgroundsByParkSelection(long id){
		List<Campground> campgroundsInUsersSelectedPark = campgroundDAO.getCampgroundsByParkId(id);
		
		return campgroundsInUsersSelectedPark;
	}
	
	public void getAllCampgroundsFromUserChoice(Object usersPark){
		
		Park myPark = (Park) usersPark;
		long myParkID = myPark.getPark_id();
		List<Campground> parkList = campgroundDAO.getCampgroundsByParkId(myParkID);
		
	}
	
	
	public List<Reservation> getAllAvailableReservations(long campground_id, LocalDate from_date, LocalDate to_date) {
		
		List<Reservation> allAvailableReservations = reservationDAO.getAvailableReservationSlotsFromUserDate(campground_id, from_date, to_date);
		
		return allAvailableReservations;
	}
	
	
	public List<Reservation> getConfirmationOfCreatedReservation (long site_id, String name, LocalDate from_date, LocalDate to_date) {
		
		List<Reservation> newlyCreatedReservation = reservationDAO.makeReservation(site_id, name, from_date, to_date);
		
		return newlyCreatedReservation;
	}
	
	public void displayUsersParkInfo(Object usersObject) {
		Park usersPark = (Park) usersObject;
		//long userSelectedParkID = usersPark.getPark_id();
		System.out.println("PARK INFO");
		System.out.println(usersPark.getPark_name()+ " National Park");
		System.out.println("Location: " + usersPark.getPark_location());
		System.out.println("Established: " + usersPark.getEstablish_date());
		System.out.println("Area: " + usersPark.getPark_area());
		System.out.println("Annual Visitors: " + usersPark.getPark_visitors());
		System.out.println(usersPark.getPark_description());
		
	}
	
	public void displayCampgroundsInSelectedPark(Object usersObject) {
		String toMonth;
		String fromMonth;
		
		Park usersPark = (Park) usersObject;
		System.out.println(usersPark.getPark_name() + " National Park Campgrounds" + "\n");
		
		
		System.out.println(String.format("%-3s%-34s%-12s%-12s%s", "#", "Name", "Open", "Close", "Daily Fee"));
		List<Campground> listOfCampgroundsAtUsersSelectedPark = getAllCampgroundsByParkSelection(usersPark.getPark_id());
		
		if(listOfCampgroundsAtUsersSelectedPark.size() > 0) {
		for(Campground campground : listOfCampgroundsAtUsersSelectedPark) {
			fromMonth = new DateFormatSymbols().getMonths()[campground.getOpen_from_mm() - 1];
			toMonth = new DateFormatSymbols().getMonths()[campground.getOpen_to_mm() - 1];
			System.out.println(String.format("%-3s%-34s%-12s%-12s%s","#" + campground.getCampground_id(),  campground.getCampground_name(),  fromMonth,
					 toMonth,  "  $" + campground.getDaily_fee() + "0"));
			campgroundIDlist.add(Integer.parseInt(campground.getCampground_id().toString()));
			}
		} else {
			System.out.println("\n No results!");
		}
	}
	
	
	public List<Campground> getCampgroundsAsList (Object usersObject) {
		Park usersPark = (Park) usersObject;
		List<Campground> listOfCampgroundsAtUsersSelectedPark = getAllCampgroundsByParkSelection(usersPark.getPark_id());
		

		return listOfCampgroundsAtUsersSelectedPark;
		
		
		
		
	}
public List<Reservation> searchForReservationID(long reservation_id) {
	List<Reservation> searchedReservationId = reservationDAO.searchForReservation(reservation_id);

	return searchedReservationId;
}

}
