package com.techelevator.campground;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;

public interface ReservationDAO {
	
	
	public List<Reservation> getAvailableReservationSlotsFromUserDate(long campground_id, LocalDate from_date, LocalDate to_date);

	public List<Reservation> makeReservation(long site_id, String name, LocalDate from_date, LocalDate to_date);

}
