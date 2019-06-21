package com.techelevator.campground.jdbc;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.sql.DataSource;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;

import com.techelevator.campground.Reservation;
import com.techelevator.campground.ReservationDAO;

public class JDBCReservationDAO implements ReservationDAO {
	
	private JdbcTemplate jdbcTemplate;
	
	public JDBCReservationDAO(DataSource dataSource) {
		this.jdbcTemplate = new JdbcTemplate(dataSource);
	}
	
	

	@Override
	public List<Reservation> getAvailableReservationSlotsFromUserDate(long campground_id, LocalDate from_date, LocalDate to_date) {
		//String newdate = "2019-06-12";
		String sqlQuery = "SELECT site_id, site.campground_id, max_occupancy, accessible, max_rv_length, utilities, campground.daily_fee " + 
				"FROM site " + 
				"JOIN campground ON campground.campground_id = site.campground_id " + 
				"WHERE site.campground_id = ? AND site.site_id NOT IN " + 
				"(SELECT site.site_id " + 
				"FROM site " + 
				"JOIN reservation ON site.site_id = reservation.site_id " + 
				"WHERE (campground_id = ? AND reservation.from_date BETWEEN ? AND ? ) " +
				"AND (campground_id = ? AND reservation.to_date BETWEEN ? AND ? )) " +
				"ORDER BY site.site_id ASC " + 
				"LIMIT 5; ";
		
		ArrayList<Reservation> reservationList = new ArrayList<>();
		
		SqlRowSet results = jdbcTemplate.queryForRowSet(sqlQuery, campground_id, campground_id, from_date, to_date, campground_id, from_date, to_date);
		
			while (results.next()) {
			
				reservationList.add(mapRowToReservation(results));
		}

		return reservationList;
		
	}
	
	

	@Override
	public List<Reservation> makeReservation(long site_id, String name, LocalDate from_date, LocalDate to_date) {
		String sqlQueryMakeReservation = "INSERT INTO reservation (site_id, name, from_date, to_date) "+
				"VALUES (?, ?, ?, ?); ";
		
		//jdbcTemplate.queryForRowSet(sqlQueryMakeReservation, site_id, name, from_date, to_date);
		
		jdbcTemplate.update(sqlQueryMakeReservation, site_id, name, from_date, to_date);
		String sqlQueryReturnMadeRes = "SELECT * FROM RESERVATION ORDER BY reservation_id DESC LIMIT 1;";
		
		SqlRowSet returnedReservation = jdbcTemplate.queryForRowSet(sqlQueryReturnMadeRes);

		
		ArrayList<Reservation> madeReservation = new ArrayList<>();
				
			while (returnedReservation.next()) {
			
				madeReservation.add(mapRowToMakeNewReservation(returnedReservation));
		}

		return madeReservation;	
	}

	
	
	
	private Reservation mapRowToReservation(SqlRowSet results) {
		Reservation theReservation = new Reservation();
		
		//theReservation.setReservation_id(results.getLong("reservation_id"));
		theReservation.setCampground_id(results.getLong("campground_id"));
		theReservation.setSite_id(results.getLong("site_id"));
		//theReservation.setName(results.getString("name"));
		//theReservation.setFrom_date(LocalDate.parse(results.getString("from_date")));
		//theReservation.setTo_date(LocalDate.parse(results.getString("to_date")));
		
//		theReservation.setTo_date(results.getString("to_date"));
		//theReservation.setCreate_date(results.getString("create_date"));


		return theReservation;

}
	
	private Reservation mapRowToMakeNewReservation(SqlRowSet results) {
		Reservation newReservation = new Reservation();
		
		newReservation.setReservation_id(results.getLong("reservation_id"));
		newReservation.setSite_id(results.getLong("site_id"));
		newReservation.setName(results.getString("name"));
		//newReservation.setFrom_date(results.getDate("from_date"));
		//newReservation.setTo_date(results.getString("to_date"));
		//newReservation.setCreate_date(results.getString("create_date"));


		return newReservation;

}
	
	
}
