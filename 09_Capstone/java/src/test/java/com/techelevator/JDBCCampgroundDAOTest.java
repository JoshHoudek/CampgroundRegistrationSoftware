package com.techelevator;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.springframework.jdbc.datasource.SingleConnectionDataSource;

import com.techelevator.campground.Campground;
import com.techelevator.campground.Park;
import com.techelevator.campground.jdbc.JDBCCampgroundDAO;
import com.techelevator.campground.jdbc.JDBCParkDAO;

public class JDBCCampgroundDAOTest extends DAOIntegrationTest {
private JDBCCampgroundDAO campgroundDAO;

	/* Using this particular implementation of DataSource so that
	 * every database interaction is part of the same database
	 * session and hence the same database transaction */
	private static SingleConnectionDataSource dataSource;

	/* Before any tests are run, this method initializes the datasource for testing. */
	@BeforeClass
	public static void setupDataSource() {
		dataSource = new SingleConnectionDataSource();
		dataSource.setUrl("jdbc:postgresql://localhost:5432/campground");
		dataSource.setUsername("postgres");
		dataSource.setPassword("postgres1");
		/* The following line disables autocommit for connections
		 * returned by this DataSource. This allows us to rollback
		 * any changes after each test */
		dataSource.setAutoCommit(false);

	}

	/* After all tests have finished running, this method will close the DataSource */
	@AfterClass
	public static void closeDataSource() throws SQLException {
		dataSource.destroy();
	}
	
	@Before
	public void setUp() throws Exception {

		campgroundDAO = new JDBCCampgroundDAO(dataSource);

	}

	/* After each test, we rollback any changes that were made to the database so that
	 * everything is clean for the next test */
	@After
	public void rollback() throws SQLException {
		dataSource.getConnection().rollback();
	}

	/* This method provides access to the DataSource for subclasses so that
	 * they can instantiate a DAO for testing */

	
	
	@Test
	public void testGetCampgroundsByParkId() {
		List<Campground> campgroundsByParkId = campgroundDAO.getCampgroundsByParkId(1);
		Assert.assertEquals("Blackwoods", campgroundsByParkId.get(0).getCampground_name());
		Assert.assertEquals(3, campgroundsByParkId.size());
		
		List<Campground> campgroundsByParkId2 = campgroundDAO.getCampgroundsByParkId(3);
		Assert.assertEquals("The Unnamed Primitive Campsites", campgroundsByParkId2.get(0).getCampground_name());
		Assert.assertEquals(1, campgroundsByParkId2.size());
		
	}
}
