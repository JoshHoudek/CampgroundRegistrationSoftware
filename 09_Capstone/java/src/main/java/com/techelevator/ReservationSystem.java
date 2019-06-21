package com.techelevator;

import java.time.LocalDate;
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
	
	public ReservationSystem (DataSource datasource) {
		 
		parkDAO = new JDBCParkDAO(datasource); 
		campgroundDAO = new JDBCCampgroundDAO(datasource);  
		reservationDAO = new JDBCReservationDAO(datasource);
	}
	
	public List<Park> getAllParks() {
		List<Park> allParks = parkDAO.getAllParks();
		//listAllParks(allParks);		
		return allParks;
	}
		

	public List<Campground> getAllCampgroundsByParkSelection(long id){
		List<Campground> campgroundsInUsersSelectedPark = campgroundDAO.getCampgroundsByParkId(id);
		
		return campgroundsInUsersSelectedPark;
	}
	
	
	public List<Reservation> getAllAvailableReservations(long campground_id, LocalDate from_date, LocalDate to_date) {
		
		List<Reservation> allAvailableReservations = reservationDAO.getAvailableReservationSlotsFromUserDate(campground_id, from_date, to_date);
		
		return allAvailableReservations;
	}
	
	
	public List<Reservation> getConfirmationOfCreatedReservation (long site_id, String name, LocalDate from_date, LocalDate to_date) {
		
		List<Reservation> newlyCreatedReservation = reservationDAO.makeReservation(site_id, name, from_date, to_date);
		
		return newlyCreatedReservation;
	}
	
	
	
}
