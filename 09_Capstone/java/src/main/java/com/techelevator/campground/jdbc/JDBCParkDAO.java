package com.techelevator.campground.jdbc;

import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;

import com.techelevator.campground.Park;
import com.techelevator.campground.ParkDAO;

public class JDBCParkDAO implements ParkDAO {
	
	
	private JdbcTemplate jdbcTemplate;

	public JDBCParkDAO(DataSource dataSource) {
		this.jdbcTemplate = new JdbcTemplate(dataSource);
	}

	
	
	@Override
	public List<Park> getAllParks() {
		
		String sqlQuery = "SELECT park_id, name, location, establish_date, area, visitors, description FROM park";
		
		ArrayList<Park> parkList = new ArrayList<>();
		
		SqlRowSet results = jdbcTemplate.queryForRowSet(sqlQuery);
		while (results.next()) {
			
			parkList.add(mapRowToPark(results));
		}
		
		return parkList;
	
	}
	
	
	
	
	
	
	private Park mapRowToPark(SqlRowSet results) {
		Park thePark = new Park();
		thePark.setPark_id(results.getLong("park_id"));
		thePark.setPark_name(results.getString("name"));
		thePark.setPark_location(results.getString("location"));
		thePark.setEstablish_date(results.getDate("establish_date"));
		thePark.setPark_area(results.getInt("area"));
		thePark.setPark_visitors(results.getInt("visitors"));
		thePark.setPark_description(results.getString("description"));

		return thePark;

}



	@Override
	public Object[] getAllParksAsObjects() {

		String sqlQuery = "SELECT park_id, name, location, establish_date, area, visitors, description FROM park";
		
		ArrayList<Park> parkList = new ArrayList<>();
		
		SqlRowSet results = jdbcTemplate.queryForRowSet(sqlQuery);
		while (results.next()) {
			
			parkList.add(mapRowToPark(results));
		}
		Object[] parkListOfObjects = new Object[parkList.size()];
		
		for (int i = 0; i<parkList.size(); i++) {
			parkListOfObjects[i] = parkList.get(i);
			
		}
		
		return parkListOfObjects;	
		}
	
}
