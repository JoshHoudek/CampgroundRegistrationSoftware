package com.techelevator;

import static org.junit.Assert.*;

import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.List;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.springframework.jdbc.datasource.SingleConnectionDataSource;

import com.techelevator.campground.Reservation;
import com.techelevator.campground.jdbc.JDBCParkDAO;
import com.techelevator.campground.jdbc.JDBCReservationDAO;

public class JDBCReservationDAOTest {
	
	String pattern = "yyyy-MM-dd";
	SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);


	private JDBCReservationDAO reservationDAO;

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

		reservationDAO = new JDBCReservationDAO(dataSource);

	}

	/* After each test, we rollback any changes that were made to the database so that
	 * everything is clean for the next test */
	@After
	public void rollback() throws SQLException {
		dataSource.getConnection().rollback();
	}

	@Test
	public void testAvailableReservations() {
		LocalDate testDate = LocalDate.of(2019, 6, 5);
		LocalDate testToDate = LocalDate.of(2019, 6, 10);
		//"'2019-06-05'"
	List<Reservation>resList = reservationDAO.getAvailableReservationSlotsFromUserDate(1, testDate, testToDate);
	Assert.assertEquals(5, resList.size());
	Assert.assertEquals(1, resList.get(0).getSite_id());
	Assert.assertEquals(5, resList.get(4).getSite_id());
	}
	
	
	@Test
	public void testAvailableReservationsCampground2() {
		LocalDate testDate = LocalDate.of(2019, 6, 5);
		LocalDate testToDate = LocalDate.of(2019, 6, 10);
		//"'2019-06-05'"
	List<Reservation>resList = reservationDAO.getAvailableReservationSlotsFromUserDate(2, testDate, testToDate);
	Assert.assertEquals(5, resList.size());
	Assert.assertEquals(277, resList.get(0).getSite_id());
	Assert.assertEquals(281, resList.get(4).getSite_id());
	}
	
	
	@Test
	public void testMakeReservation() {
		
		LocalDate testDate = LocalDate.of(2019, 6, 5);
		LocalDate testToDate = LocalDate.of(2019, 6, 10);
		String name = "Test Family";
	//	long reservation_id = 101;
		long site_id = 277;
		
		List<Reservation>myReservation = reservationDAO.makeReservation(site_id, name, testDate, testToDate);
	//	System.out.println(myReservation.get(0).getReservation_id());
		
		Assert.assertEquals(1, myReservation.size());
		Assert.assertEquals(277, myReservation.get(0).getSite_id());

		
	}
}
