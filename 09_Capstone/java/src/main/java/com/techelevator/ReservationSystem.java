package com.techelevator;

import java.util.List;

import javax.sql.DataSource;

import com.techelevator.campground.Campground;
import com.techelevator.campground.CampgroundDAO;
import com.techelevator.campground.Park;
import com.techelevator.campground.ParkDAO;
import com.techelevator.campground.jdbc.JDBCCampgroundDAO;
import com.techelevator.campground.jdbc.JDBCParkDAO;

public class ReservationSystem {
	private ParkDAO parkDAO;
	private CampgroundDAO campgroundDAO;
	
	public ReservationSystem (DataSource datasource) {
		 
		parkDAO = new JDBCParkDAO(datasource); 
		campgroundDAO = new JDBCCampgroundDAO(datasource);  
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
	
	
}
