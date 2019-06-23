package com.techelevator;

import java.sql.SQLException;
import java.util.List;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.springframework.jdbc.datasource.SingleConnectionDataSource;

import com.techelevator.campground.Park;
import com.techelevator.campground.jdbc.JDBCParkDAO;

public class JDBCParkDAOTest extends DAOIntegrationTest {
	private JDBCParkDAO parkDAO;

	/*
	 * Using this particular implementation of DataSource so that every database
	 * interaction is part of the same database session and hence the same database
	 * transaction
	 */
	private static SingleConnectionDataSource dataSource;

	/*
	 * Before any tests are run, this method initializes the datasource for testing.
	 */
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

	/*
	 * After all tests have finished running, this method will close the DataSource
	 */
	@AfterClass
	public static void closeDataSource() throws SQLException {
		dataSource.destroy();
	}

	@Before
	public void setUp() throws Exception {

		parkDAO = new JDBCParkDAO(dataSource);

	}

	/*
	 * After each test, we rollback any changes that were made to the database so
	 * that everything is clean for the next test
	 */
	@After
	public void rollback() throws SQLException {
		dataSource.getConnection().rollback();
	}

	/*
	 * This method provides access to the DataSource for subclasses so that they can
	 * instantiate a DAO for testing
	 */

	@Test
	public void testGetAllParks() {
		List<Park> allParks = parkDAO.getAllParks();
		Assert.assertEquals("Acadia", allParks.get(0).getPark_name());
		Assert.assertEquals(3, allParks.size());
		Assert.assertEquals("Arches", allParks.get(1).getPark_name());
		Assert.assertEquals("Cuyahoga Valley", allParks.get(2).getPark_name());

	}

	@Test
	public void testEstDate() {
		List<Park> allParks = parkDAO.getAllParks();
		Assert.assertEquals("1919-02-26", allParks.get(0).getEstablish_date().toString());
		Assert.assertEquals("1929-04-12", allParks.get(1).getEstablish_date().toString());
		Assert.assertEquals("2000-10-11", allParks.get(2).getEstablish_date().toString());
	}

	@Test
	public void testVisitors() {
		List<Park> allParks = parkDAO.getAllParks();
		Assert.assertEquals(2563129, allParks.get(0).getPark_visitors());
		Assert.assertEquals(1284767, allParks.get(1).getPark_visitors());
		Assert.assertEquals(2189849, allParks.get(2).getPark_visitors());

	}

	@Test
	public void testLocation() {
		List<Park> allParks = parkDAO.getAllParks();
		Assert.assertEquals("Maine", allParks.get(0).getPark_location());
		Assert.assertEquals("Utah", allParks.get(1).getPark_location());
		Assert.assertEquals("Ohio", allParks.get(2).getPark_location());
	}

	@Test
	public void testVisitors3() {
		List<Park> allParks = parkDAO.getAllParks();
		Assert.assertEquals(47389, allParks.get(0).getPark_area());
		Assert.assertEquals(76518, allParks.get(1).getPark_area());
		Assert.assertEquals(32860, allParks.get(2).getPark_area());
	}
}