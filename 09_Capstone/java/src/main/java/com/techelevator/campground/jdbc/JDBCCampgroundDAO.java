package com.techelevator.campground.jdbc;

import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;

import com.techelevator.campground.Campground;
import com.techelevator.campground.CampgroundDAO;

public class JDBCCampgroundDAO implements CampgroundDAO {

	private JdbcTemplate jdbcTemplate;

	public JDBCCampgroundDAO(DataSource dataSource) {
		this.jdbcTemplate = new JdbcTemplate(dataSource);
	}

	@Override
	public List<Campground> getCampgroundsByParkId(long id) {
		ArrayList<Campground> campgroundParkId = new ArrayList<>();
		String sqlQuery = "SELECT campground_id, campground.park_id, campground.name, open_from_mm, open_to_mm, daily_fee FROM campground JOIN park ON park.park_id = campground.park_id WHERE campground.park_id = ?";
		SqlRowSet results = jdbcTemplate.queryForRowSet(sqlQuery, id);
		
		while (results.next()) {
			
			Campground theCampground = mapRowToCampground(results);
			
			campgroundParkId.add(theCampground);
			
		}
		
		return campgroundParkId;
	}

	private Campground mapRowToCampground(SqlRowSet results) {
		Campground theCampground = new Campground();
		theCampground.setCampground_id(results.getLong("campground_id"));
		theCampground.setPark_id(results.getLong("park_id"));
		theCampground.setCampground_name(results.getString("name"));
		theCampground.setOpen_from_mm(results.getInt("open_from_mm"));
		theCampground.setOpen_to_mm(results.getInt("open_to_mm"));
		theCampground.setDaily_fee(results.getBigDecimal("daily_fee"));

		return theCampground;
	}

	
	
}
