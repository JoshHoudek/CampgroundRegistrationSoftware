package com.techelevator.campground;

import java.util.List;

public interface ParkDAO {
	
	
	// Gets all parks as a list from the database
	// returns a list of Park Objects
	
	public List<Park> getAllParks();
	

}
