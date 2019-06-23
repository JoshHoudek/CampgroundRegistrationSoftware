package com.techelevator;

import java.sql.SQLException;
import java.time.LocalDate;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.springframework.jdbc.datasource.SingleConnectionDataSource;

import com.techelevator.campground.CampgroundDAO;
import com.techelevator.campground.ParkDAO;
import com.techelevator.campground.ReservationDAO;
import com.techelevator.campground.jdbc.JDBCCampgroundDAO;
import com.techelevator.campground.jdbc.JDBCParkDAO;
import com.techelevator.campground.jdbc.JDBCReservationDAO;

import junit.framework.Assert;

public class ReservationSystemTest {
	ReservationSystem reservation;
	ParkDAO parkDAO;
	CampgroundDAO campgroundDAO;
	ReservationDAO reservationDAO;

	private static SingleConnectionDataSource dataSource;


	@BeforeClass
	public static void setupDataSource() {
		dataSource = new SingleConnectionDataSource();

		dataSource.setUrl("jdbc:postgresql://localhost:5432/campground");
		dataSource.setUsername("postgres");
		dataSource.setPassword("postgres1");
		/*
		 * The following line disables autocommit for connections returned by this
		 * DataSource. This allows us to rollback any changes after each test
		 */
		dataSource.setAutoCommit(false);
	}

	@Before
	public void setUp() throws Exception {

		parkDAO = new JDBCParkDAO(dataSource);
		campgroundDAO = new JDBCCampgroundDAO(dataSource);
		reservationDAO = new JDBCReservationDAO(dataSource);
	}

	/*
	 * After all tests have finished running, this method will close the DataSource
	 */
	@AfterClass
	public static void closeDataSource() throws SQLException {
		dataSource.destroy();
	}

	@After
	public void rollback() throws SQLException {
		dataSource.getConnection().rollback();
	}

	@Test
	public void testGetAllParks() {
		ReservationSystem reservationSystem = new ReservationSystem(dataSource);
		Assert.assertEquals(3, reservationSystem.getAllParks().size());

	}
	@Test
	public void testAllParksAsObjects() {
		ReservationSystem reservationSystem = new ReservationSystem(dataSource);
		Assert.assertEquals(3, reservationSystem.getAllParksAsObjects().length);

	}

	@Test
	public void testCorrectParkInfo() {
		ReservationSystem reservationSystem = new ReservationSystem(dataSource);
		Assert.assertEquals("Acadia", reservationSystem.getAllParks().get(0).getPark_name());
	}

	@Test
	public void testGetAllCampgroundsByParkSelection() {
		ReservationSystem reservationSystem = new ReservationSystem(dataSource);
		Assert.assertEquals(1, reservationSystem.getAllCampgroundsByParkSelection(3).size());
		Assert.assertEquals(3, reservationSystem.getAllCampgroundsByParkSelection(1).size());
		Assert.assertEquals(3, reservationSystem.getAllCampgroundsByParkSelection(2).size());
	}
	@Test
	public void testGetAllAvailableReservations() {
		ReservationSystem reservationSystem = new ReservationSystem(dataSource);
		LocalDate testDate = LocalDate.now();
		Assert.assertEquals(5, reservationSystem.getAllAvailableReservations(1, testDate, testDate).size());
	}
	@Test
	public void testGetConfirmationOfCreatedReservation() {
		ReservationSystem reservationSystem = new ReservationSystem(dataSource);
		LocalDate testDate = LocalDate.now();
		Assert.assertEquals(1, reservationSystem.getConfirmationOfCreatedReservation(1, "Test", testDate, testDate).size());
	}
	@Test
	public void testSearchForReservationID() {
		ReservationSystem reservationSystem = new ReservationSystem(dataSource);
		Assert.assertEquals(1, reservationSystem.searchForReservationID(1).size());
	}

}
